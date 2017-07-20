package kriate.production.com.gymnegotiators.fit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dima on 20.07.2017.
 */

public class Phrases {
    @SerializedName("theme_id")
    @Expose
    private String theme_id;
    public String getTheme_id() {
        return theme_id;
    }
    public void setTheme_id(String theme_id) {
        this.theme_id = theme_id;
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

    @SerializedName("audio")
    @Expose
    private String audio;
    public String getAudio() {
        return audio;
    }
    public void setAudio(String audio) {
        this.audio = audio;
    }

    @SerializedName("phrase")
    @Expose
    private String phrase;
    public String getPhrase() {
        return phrase;
    }
    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

}
