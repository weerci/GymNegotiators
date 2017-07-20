package kriate.production.com.gymnegotiators.fit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dima on 20.07.2017.
 */

public class Phrases {
    @SerializedName("phrase")
    @Expose
    private String phrase;
    public String getPhrase() {
        return phrase;
    }
    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    @SerializedName("ord")
    @Expose
    private String ord;
    public String getOrd() {
        return ord;
    }
    public void setOrd(String ord) {
        this.ord = ord;
    }
}
