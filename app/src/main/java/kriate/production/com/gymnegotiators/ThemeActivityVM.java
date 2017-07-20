package kriate.production.com.gymnegotiators;

import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.media.MediaPlayer;
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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import de.hdodenhof.circleimageview.CircleImageView;
import kriate.production.com.gymnegotiators.Model.Theme;
import kriate.production.com.gymnegotiators.Utils.AppUtilities;
import kriate.production.com.gymnegotiators.binding.ImageAdapter;
import kriate.production.com.gymnegotiators.fit.Content;
import kriate.production.com.gymnegotiators.fit.Phrases;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dima on 27.06.2017.
 */

public class ThemeActivityVM extends ActivityViewModel<ThemeActivity> {

    public ThemeActivityVM(ThemeActivity activity, String status) {
        super(activity);
        App.getFitService().getAllContent().enqueue(new Callback<List<Content>>() {
            @Override
            public void onResponse(Call<List<Content>> call, Response<List<Content>> response) {

                Loader.setMapTheme(response.body());

                // Загружаются данные о покупках
                mCheckout = Checkout.forActivity(activity, App.getContext().getBilling());
                mCheckout.start();
                reloadInventory();

                GridView gridView = (GridView) activity.findViewById(R.id.grid_view);
                gridView.setAdapter(new ImageAdapter(activity, Loader.getMapTheme()));
                gridView.setOnItemClickListener((parent, v, position, id) -> {
                    selectedTheme.set(Loader.getArrayTheme().get(position));
                    if (selectedTheme.get().getIsPurchased().get() == true) {
                        CircleImageView civ = (CircleImageView) activity.findViewById(R.id.circleImageView);
                        civ.setImageBitmap(selectedTheme.get().getPhotoBit());
                        loadPhrase();
                    }
                });

                selectedTheme.set(Loader.getMapTheme().firstEntry().getValue());
                CircleImageView civ = (CircleImageView) activity.findViewById(R.id.circleImageView);
                civ.setImageBitmap(selectedTheme.get().getPhotoBit());
            }

            @Override
            public void onFailure(Call<List<Content>> call, Throwable t) {
                AppUtilities.showSnackbar(activity, "Ошибка произошла", false);
            }
        });

        mPlayer = new MediaPlayer();
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlay();
            }
        });
    }

    private void loadPhrase() {
        isPlaying.set(true);
        // Загружаем фразы
        App.getFitService().getContent(selectedTheme.get().getId()).enqueue(new Callback<List<Phrases>>() {
            @Override
            public void onResponse(Call<List<Phrases>> call, Response<List<Phrases>> response) {
                Loader.setPhrases(response.body());
            }

            @Override
            public void onFailure(Call<List<Phrases>> call, Throwable t) {
                AppUtilities.showSnackbar(activity, "Ошибка произошла", false);
            }
        }
        );
    }

    @Override
    public void onDestroy() {
        mCheckout.stop();
        if (mPlayer.isPlaying()) {
            stopPlay();
        }
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCheckout.onActivityResult(requestCode, resultCode, data);
    }

    // Observable fields
    public final ObservableField<Theme> selectedTheme = new ObservableField<>();
    public final ObservableField<String> selectedPrase = new ObservableField<>();
    public final ObservableBoolean isLoading = new ObservableBoolean();
    public final ObservableBoolean isPlaying = new ObservableBoolean();

    //Производится покупка выбранной темы**
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

    //region Player
    MediaPlayer mPlayer;

    public void stopPlay() {
        mPlayer.stop();
        try {
            mPlayer.prepare();
            mPlayer.seekTo(0);
        } catch (Throwable t) {
            AppUtilities.showSnackbar(activity, "Ошибка произошла", false);
        }
        isPlaying.set(false);

    }

    public void play() {
        isPlaying.set(true);
        mPlayer.start();
    }

    public void pause() {
        isPlaying.set(false);
        mPlayer.pause();
    }
    //endregion

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

