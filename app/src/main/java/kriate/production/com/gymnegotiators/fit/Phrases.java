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

    @SerializedName("ord")
    @Expose
    private String ord;
    public String getOrd() {
        return ord;
    }

    @SerializedName("audio")
    @Expose
    private String audio;
    public String getAudio() {
        return audio;
    }

    @SerializedName("phrase")
    @Expose
    private String phrase;
    public String getPhrase() {
        return phrase;
    }

    @SerializedName("comment")
    @Expose
    private String comment;
    public String getComment() {
        return comment;
    }

    @SerializedName("commentAudio")
    @Expose
    private String commentAudio;
    public String getCommentAudio() {
        return commentAudio;
    }

    @SerializedName("var1")
    @Expose
    private String var1;
    public String getVar1() {
        return var1;
    }

    @SerializedName("varAudio1")
    @Expose
    private String varAudio1;
    public String getVarAudio1() {
        return varAudio1;
    }

    @SerializedName("var2")
    @Expose
    private String var2;
    public String getVar2() {
        return var2;
    }

    @SerializedName("varAudio2")
    @Expose
    private String varAudio2;
    public String getVarAudio2() {
        return varAudio2;
    }

    @SerializedName("var3")
    @Expose
    private String var3;
    public String getVar3() {
        return var3;
    }

    @SerializedName("varAudio3")
    @Expose
    private String varAudio3;
    public String getVarAudio3() {
        return varAudio3;
    }

}
