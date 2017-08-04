package kriate.production.com.gymnegotiators.Utils;

import org.solovyev.android.checkout.Inventory;
import org.solovyev.android.checkout.Sku;

import java.util.TreeMap;

/**
 * Created by dima on 04.08.2017.
 */

public class PayMay implements IPayMay {
    @Override
    public boolean isIsPurchased(Sku sku) {
        return false;
    }

    @Override
    public void buy(Sku sku) {

    }

    @Override
    public TreeMap<String, Inventory.Product> getListProduct() {
        return null;
    }

    @Override
    public void release(Sku sku) {

    }


}
