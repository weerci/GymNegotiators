package kriate.production.com.gymnegotiators.Model;

/**
 * Created by dima on 29.06.2017.
 */

public class Theme {

    public Theme(int id, String phrase, int photo, int audio) {
        this.id = id;
        this.phrase = phrase;
        this.photo = photo;
        this.audio = audio;
    }

    private int id;
    private String phrase;
    private int photo;
    private int audio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public int getAudio() {
        return audio;
    }

    public void setAudio(int audio) {
        this.audio = audio;
    }
}
