package kriate.production.com.gymnegotiators;

import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stfalcon.androidmvvmhelper.mvvm.activities.ActivityViewModel;

import org.solovyev.android.checkout.ActivityCheckout;
import org.solovyev.android.checkout.Billing;
import org.solovyev.android.checkout.BillingRequests;
import org.solovyev.android.checkout.Checkout;
import org.solovyev.android.checkout.EmptyRequestListener;
import org.solovyev.android.checkout.Inventory;
import org.solovyev.android.checkout.ProductTypes;
import org.solovyev.android.checkout.Purchase;
import org.solovyev.android.checkout.RequestListener;
import org.solovyev.android.checkout.Sku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nonnull;

import kriate.production.com.gymnegotiators.Model.Theme;
import kriate.production.com.gymnegotiators.Utils.AppUtilities;
import kriate.production.com.gymnegotiators.binding.RecyclerBindingAdapter;
import kriate.production.com.gymnegotiators.binding.RecyclerConfiguration;

/**
 * Created by dima on 27.06.2017.
 */

public class ThemeActivityVM extends ActivityViewModel<ThemeActivity> {


    private ActivityCheckout mCheckout;

    private static List<String> getInAppSkus() {
        final List<String> skus = new ArrayList<>();
        skus.addAll(Arrays.asList(
                "kriate.production.com.gymnegotiators.boss_inapp",
                "kriate.production.com.gymnegotiators.buyer_inapp",
                "kriate.production.com.gymnegotiators.seller_inapp",
                "kriate.production.com.gymnegotiators.employer_inapp"
        ));
        return skus;
    }

    public ThemeActivityVM(ThemeActivity activity, String status) {
        super(activity);

        selectedTheme.set(Loader.getMapTheme().firstEntry().getValue());
        initRecycler();

        final Billing billing = App.getContext().getBilling();
        mCheckout = Checkout.forActivity(activity, billing);
        mCheckout.start();
        reloadInventory();
    }


    private void reloadInventory() {
        final Inventory.Request request = Inventory.Request.create();
        // load purchase info
        request.loadAllPurchases();
        // load SKU details
        request.loadSkus(ProductTypes.IN_APP, getInAppSkus());
        mCheckout.loadInventory(request, new InventoryCallback());
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

    public final RecyclerConfiguration recyclerConfiguration = new RecyclerConfiguration();
    private ArrayList<Theme> themes = new ArrayList<Theme>(Loader.getMapTheme().values());

    // Observable fields
    public final ObservableField<Theme> selectedTheme = new ObservableField<>();
    public final ObservableBoolean isLoading = new ObservableBoolean();

    private static final int LAYOUT_HOLDER = R.layout.item_photo;
    private static final int PHOTOS_COLUMN_COUNT = 3;

    private void initRecycler() {
        RecyclerBindingAdapter<Integer> adapter = getAdapter();

        recyclerConfiguration.setLayoutManager(new GridLayoutManager(activity, PHOTOS_COLUMN_COUNT));
        recyclerConfiguration.setItemAnimator(new DefaultItemAnimator());
        recyclerConfiguration.setAdapter(adapter);
    }

    private RecyclerBindingAdapter<Integer> getAdapter() {

        ArrayList<Integer> photos = new ArrayList<Integer>();
        for (Theme item : themes) {
            photos.add(item.getPhotoId());
        }

        RecyclerBindingAdapter<Integer> adapter = new RecyclerBindingAdapter<>(LAYOUT_HOLDER, BR.photoId, photos);

        adapter.setOnItemClickListener((position, item)
                -> selectedTheme.set(themes.get(position)));
        return adapter;
    }

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

    //region Billing

    Inventory.Product mProduct;

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

}

