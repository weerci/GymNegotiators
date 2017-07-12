package kriate.production.com.gymnegotiators.Model;

import android.databinding.ObservableBoolean;

import org.solovyev.android.checkout.Sku;
import org.solovyev.android.checkout.Skus;

import java.util.Observable;

/**
 * Created by dima on 29.06.2017.
 */

public class Theme {
    public Theme(){}
    public Theme(String name, String desk, int photoId, int audioId) {
        this.name = name;
        this.desk = desk;
        this.photoId = photoId;
        this.audioId = audioId;
    }

    private String name;
    private String desk;
    private int photoId;
    private int audioId;
    private Sku sku;
    private final ObservableBoolean isPurchased = new ObservableBoolean();

    public ObservableBoolean getIsPurchased() {
        return isPurchased;
    }

    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesk() {
        return desk;
    }

    public void setDesk(String desk) {
        this.desk = desk;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public int getAudioId() {
        return audioId;
    }

    public void setAudioId(int audioId) {
        this.audioId = audioId;
    }
}
