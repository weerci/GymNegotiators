package kriate.production.com.gymnegotiators.Model;

/**
 * Created by dima on 29.06.2017.
 */

public class Theme {
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

    public String getName() {
        return name;
    }

    public String getDesk() {
        return desk;
    }
    public int getPhotoId() {
        return photoId;
    }
    public int getAudioId() {
        return audioId;
    }
}
