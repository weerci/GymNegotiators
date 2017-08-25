package kriate.production.com.gymnegotiators;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableShort;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.GridView;
import android.widget.ImageView;

import com.stfalcon.androidmvvmhelper.mvvm.activities.ActivityViewModel;

import org.solovyev.android.checkout.ActivityCheckout;
import org.solovyev.android.checkout.BillingRequests;
import org.solovyev.android.checkout.Checkout;
import org.solovyev.android.checkout.EmptyRequestListener;
import org.solovyev.android.checkout.Inventory;
import org.solovyev.android.checkout.ProductTypes;
import org.solovyev.android.checkout.Purchase;
import org.solovyev.android.checkout.RequestListener;
import org.solovyev.android.checkout.Sku;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import de.hdodenhof.circleimageview.CircleImageView;
import kriate.production.com.gymnegotiators.Model.Media;
import kriate.production.com.gymnegotiators.Model.Theme;
import kriate.production.com.gymnegotiators.Utils.AppUtilities;
import kriate.production.com.gymnegotiators.binding.ImageAdapter;
import kriate.production.com.gymnegotiators.binding.RecyclerThemeAdapter;
import kriate.production.com.gymnegotiators.fit.Content;
import kriate.production.com.gymnegotiators.fit.Phrases;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dima on 27.06.2017.
 */

public class ThemeActivityVM extends ActivityViewModel<ThemeActivity> {

    //region Observable fields
    public final ObservableField<Theme> selectedTheme = new ObservableField<>();
    public final ObservableField<Media> media = new ObservableField<>();
    public final ObservableBoolean isLoading = new ObservableBoolean();
    public final ObservableBoolean isPlaying = new ObservableBoolean();
    public final ObservableBoolean isPaused = new ObservableBoolean();
    public final ObservableField<String> phrase = new ObservableField<>();
    //endregion

    //region Fields and Methods

    private RecyclerView mRecyclerView;
    private RecyclerThemeAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private void reloadRecyclerView() {
        mRecyclerView = (RecyclerView) activity.findViewById(R.id.rv);

        // если мы уверены, что изменения в контенте не изменят размер layout-а RecyclerView
        // передаем параметр true - это увеличивает производительность
        mRecyclerView.setHasFixedSize(true);

        // используем linear layout manager
        mLayoutManager = new GridLayoutManager(activity, 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setClickable(true);
        // создаем адаптер
        mAdapter = new RecyclerThemeAdapter(Loader.getArrayTheme());
        mAdapter.setOnItemClickListener((position, item) -> {
            selectedTheme.set(Loader.getArrayTheme().get(position));
            CircleImageView civ = (CircleImageView) activity.findViewById(R.id.circleImageView);
            civ.setImageBitmap(selectedTheme.get().getPhotoBit());
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    private void loadPhrase() {
        isLoading.set(true);
        if (selectedTheme.get().getPhrases().size() == 0) {
            App.getFitService().getContent(selectedTheme.get().getId()).enqueue(new Callback<List<Phrases>>() {
                @Override
                public void onResponse(Call<List<Phrases>> call, Response<List<Phrases>> response) {
                    isLoading.set(false);
                    selectedTheme.get().setPhrases(response.body());
                    media.get().setTheme(selectedTheme.get());
                    media.get().start(Media.Playing.phrase);
                }

                @Override
                public void onFailure(Call<List<Phrases>> call, Throwable t) {
                    isLoading.set(false);
                    AppUtilities.showSnackbar(activity, "Ошибка загрузки фраз", false);
                }
            });
        } else {
            media.get().setTheme(selectedTheme.get());
            isLoading.set(false);
        }
    }

    //endregion

    public ThemeActivityVM(ThemeActivity activity, String status) {
        super(activity);

        media.set(new Media(activity, null));

        // Загружаются темы
        App.getFitService().getAllContent().enqueue(new Callback<List<Content>>() {
            @Override
            public void onResponse(Call<List<Content>> call, Response<List<Content>> response) {

                Loader.setMapTheme(response.body());

                // Загружаются данные о покупках
                mCheckout = Checkout.forActivity(activity, App.getContext().getBilling());
                mCheckout.start();
                reloadInventory();
                reloadRecyclerView();

                selectedTheme.set(Loader.getMapTheme().firstEntry().getValue());
                CircleImageView civ = (CircleImageView) activity.findViewById(R.id.circleImageView);
                civ.setImageBitmap(selectedTheme.get().getPhotoBit());
            }

            @Override
            public void onFailure(Call<List<Content>> call, Throwable t) {
                AppUtilities.showSnackbar(activity, "Ошибка загрузки темы", false);
            }
        });
    }

    @Override
    public void onDestroy() {
        mCheckout.stop();
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCheckout.onActivityResult(requestCode, resultCode, data);
    }

    //Производится покупка выбранной темы
    public void buyTheme() {
        Sku sku = selectedTheme.get().getSku();
        if (sku != null) {
            final Purchase purchase = mProduct.getPurchaseInState(sku, Purchase.State.PURCHASED);
            if (purchase != null) {
                isLoading.set(true);
                consume(purchase);
            } else {
                mCheckout.startPurchaseFlow(sku, null, new PurchaseListener());
            }
        }
    }

    public void play() {
        if (media.get().getCurrentState() == Media.MediaState.inPause)
            media.get().start(null);
        else
            loadPhrase();
    }

    public void pause() {
        media.get().pause();
    }

    public void stopPlay() {
        media.get().stop();
    }

    //region Billing

    private ActivityCheckout mCheckout;

    Inventory.Product mProduct;

    private void reloadInventory() {
        final Inventory.Request request = Inventory.Request.create();
        // load purchase info
        request.loadAllPurchases();
        // load SKU details
        request.loadSkus(ProductTypes.IN_APP, Loader.getSkus());
        mCheckout.loadInventory(request, new InventoryCallback());
    }

    private class InventoryCallback implements Inventory.Callback {
        @Override
        public void onLoaded(Inventory.Products products) {
            ArrayList<String> s = new ArrayList<String>();
            mProduct = products.get(ProductTypes.IN_APP);
            if (mProduct.supported) {
                for (Sku sku : mProduct.getSkus()) {
                    Theme theme = Loader.getMapTheme().get(sku.id.code);
                    if (theme != null) {
                        theme.setSku(sku);
                        theme.getIsPurchased().set(mProduct.isPurchased(sku));
                    }
                }
            }
            Loader.getMapTheme().get("theme").getIsPurchased().set(true);
        }
    }

    private class PurchaseListener extends EmptyRequestListener<Purchase> {
        @Override
        public void onSuccess(Purchase purchase) {
            super.onSuccess(purchase);
            reloadInventory();
        }

        @Override
        public void onError(int response, @Nonnull Exception e) {
            super.onError(response, e);
            reloadInventory();
        }
    }

    private <T> RequestListener<T> makeRequestListener() {
        return new RequestListener<T>() {
            @Override
            public void onSuccess(@Nonnull T result) {
                reloadInventory();
                isLoading.set(false);
            }

            @Override
            public void onError(int response, @Nonnull Exception e) {
                reloadInventory();
                isLoading.set(false);
            }
        };
    }

    private void consume(final Purchase purchase) {
        mCheckout.whenReady(new Checkout.EmptyListener() {
            @Override
            public void onReady(@Nonnull BillingRequests requests) {
                requests.consume(purchase.token, makeRequestListener());
            }
        });
    }

    //endregion
}

