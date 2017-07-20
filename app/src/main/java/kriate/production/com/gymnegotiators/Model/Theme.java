package kriate.production.com.gymnegotiators.Model;

import android.databinding.ObservableBoolean;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.solovyev.android.checkout.Sku;
import org.solovyev.android.checkout.Skus;

import java.util.Observable;

/**
 * Created by dima on 29.06.2017.
 */

public class Theme {
    public Theme(){}

    private final ObservableBoolean isPurchased = new ObservableBoolean();
    public ObservableBoolean getIsPurchased() {
        return isPurchased;
    }

    private String id;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    private String desk;
    public String getDesk() {
        return desk;
    }
    public void setDesk(String desk) {
        this.desk = desk;
    }

    private int audioId;
    public int getAudioId() {
        return audioId;
    }
    public void setAudioId(int audioId) {
        this.audioId = audioId;
    }

    private Sku sku;
    public Sku getSku() {
        return sku;
    }
    public void setSku(Sku sku) {
        this.sku = sku;
    }

    private Bitmap photoBit;
    public Bitmap getPhotoBit() {
        return photoBit;
    }
    public void setPotoBit(String photoBit) {
        byte[] data = Base64.decode(photoBit, Base64.DEFAULT);
        this.photoBit = BitmapFactory.decodeByteArray(data, 0, data.length);
    }
}
