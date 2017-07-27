package kriate.production.com.gymnegotiators.Model;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.media.MediaPlayer;
import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.StringTokenizer;

import kriate.production.com.gymnegotiators.App;
import kriate.production.com.gymnegotiators.fit.Phrases;

/**
 * Created by dima on 26.07.2017.
 * Предназначен для проигрывания музыкальной фразы и отображения
 * связанного с ней текста в Observable поле currentPhrase.
 */

public class Media {

    //region Constructors

    private static Media _media;
    private MediaPlayer mPlayer = new MediaPlayer();

    public static Media item()
    {
        if ( _media == null)
            _media = new Media();
        return  _media;
    }

    //endregion

    public ObservableBoolean canPlay = new ObservableBoolean(true);
    public ObservableBoolean canPause = new ObservableBoolean(false);
    public ObservableBoolean canStop = new ObservableBoolean(false);
    public ObservableField<String> currentPhrase = new ObservableField<>();


    //region Properties

    private MediaState mCurrentState = MediaState.inStop;
    private int mCurrentTrack = 0;
    private int mCurrentPosition = 0;
    private List<Phrases> mPhrases = new ArrayList<>();

    //endregion

    //region Helper

    private void erase()
    {
        mPhrases.clear();
        setObservableFields(true, false, false);
        mCurrentState = MediaState.inStop;
        mCurrentTrack = 0;
        mCurrentPosition = 0;
        mPlayer.release();
    }

    private void setObservableFields(boolean canIsPlay, boolean canIsPause, boolean canIsStop){
        canPlay.set(canIsPlay);
        canPause.set(canIsPause);
        canStop.set(canIsStop);
    }

    private void playPhrase() {
        try {
            File tempMp3 = File.createTempFile("phrase", ".mp3", App.getContext().getCacheDir());
            tempMp3.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempMp3);
            fos.write(Base64.decode(mPhrases.get(mCurrentTrack).getAudio(), Base64.DEFAULT));
            fos.close();

            mPlayer.reset();

            FileInputStream fis = new FileInputStream(tempMp3);
            mPlayer.setDataSource(fis.getFD());

            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            erase();
        }
    }

    private void  startPlay(){
        mCurrentTrack = 0;
        mPlayer = new MediaPlayer();

        mPlayer.setOnCompletionListener(mp -> {
            if (mCurrentTrack < mPhrases.size()) {
                currentPhrase.set(mPhrases.get(mCurrentTrack).getPhrase());
                playPhrase();
                mCurrentTrack++;
            } else {
                stop();
            }
        });
        currentPhrase.set(mPhrases.get(mCurrentTrack).getPhrase());
        playPhrase();
        mCurrentTrack++;
    }

    //endregion

    public void play(List<Phrases> phrases)
    {
        if (!canPlay.get())
            return;

        mCurrentState = MediaState.inPlay;
        setObservableFields(false, true, true);

        if (mCurrentState == MediaState.inPause)
        {
            mPlayer.seekTo(mCurrentPosition);
            mPlayer.start();
        }
        else {
            mPhrases = phrases;
            startPlay();
        }
    }

    public void pause()
    {
        if (!canPause.get())
            return;

        mCurrentState = MediaState.inPause;
        setObservableFields(true, false, true);

        mCurrentPosition = mPlayer.getCurrentPosition();
        mPlayer.pause();

    }

    public void stop()
    {
        try {
            mCurrentState = MediaState.inStop;
            mPlayer.stop();
        }
        finally {
            erase();
        }
    }

    // Текущее состояние проигрывателя
    public enum MediaState {
        inPlay, inPause, inStop
    }

}
