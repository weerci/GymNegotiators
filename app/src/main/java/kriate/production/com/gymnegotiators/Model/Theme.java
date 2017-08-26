package kriate.production.com.gymnegotiators.Model;

import android.databinding.ObservableBoolean;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.solovyev.android.checkout.Sku;
import org.solovyev.android.checkout.Skus;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import kriate.production.com.gymnegotiators.fit.Phrases;

/**
 * Created by dima on 29.06.2017.
 */

public class Theme {
    public Theme() {
    }

    private final ObservableBoolean isPurchased = new ObservableBoolean();
    public ObservableBoolean getIsPurchased() {
        return isPurchased;
    }

    // Идентификатор темы, совпадает с названием услуги в googlePlay
    private String id;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    // Имя темы
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Описание темы
    private String desk;
    public String getDesk() {
        return desk;
    }
    public void setDesk(String desk) {
        this.desk = desk;
    }

    // Список фраз темы, с их комментариями и вариантами ответов
    private List<Phrases> phrases = new ArrayList<>();
    public List<Phrases> getPhrases() {
        return phrases;
    }
    public void setPhrases(List<Phrases> phrases) {
        this.phrases = phrases;
    }

    // Информация о покупки с googlePlay
    private Sku sku;
    public Sku getSku() {
        return sku;
    }
    public void setSku(Sku sku) {
        this.sku = sku;
    }

    private String photo;
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    // Фотография сопоставленная теме
    private Bitmap photoBit;
    public Bitmap getPhotoBit() {
        return photoBit;
    }
    public void setPotoBit(String photoBit) {
        byte[] data = Base64.decode(photoBit, Base64.DEFAULT);
        this.photoBit = BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    // Версия темы, нужна для обновления локального кеша приложения при обновлении полей темы на сервере
    private int version;
    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }
}
