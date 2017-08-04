package kriate.production.com.gymnegotiators.Utils;

import org.solovyev.android.checkout.Inventory;
import org.solovyev.android.checkout.Sku;
import java.util.TreeMap;

/**
 * Created by dima on 04.08.2017.
 */

public interface IPayMay {
    // Проверяет, куплен ли товар sku
    boolean isIsPurchased(Sku sku);

    // Производится покупка товара sku
    void buy(Sku sku);

    // Получение списка товаров
    TreeMap<String, Inventory.Product> getListProduct();

    // Продажа товара
    void release(Sku sku);

    // Интерфейс декларирует методы сохранения/извлечения товаров из базы
    interface ILoading{
        // Сохраняет список товаров в базе
        void saveProducts(Inventory.Products products);

        // Извлекает список товаров из базы
        Inventory.Products getProducts();
    }

    // Интерфейс декларирует метод синхронизации googleplay и локального (удаленного) хранилища товаров
    interface ISync{
        void sync();
    }
}


