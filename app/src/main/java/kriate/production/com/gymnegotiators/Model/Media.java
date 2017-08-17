package kriate.production.com.gymnegotiators.Model;

import android.app.Activity;
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
import java.util.Observable;
import java.util.PrimitiveIterator;
import java.util.StringTokenizer;

import kriate.production.com.gymnegotiators.App;
import kriate.production.com.gymnegotiators.Utils.AppUtilities;
import kriate.production.com.gymnegotiators.fit.Phrases;
import okhttp3.internal.Util;

/**
 * Created by dima on 26.07.2017.
 * Предназначен для проигрывания и отображения фраз, комментариев и ответов для выбранной темы.
 */

public class Media {

    //region Constructors

    private Activity _activity;
    private MediaPlayer mPlayer = new MediaPlayer();
    private String _currentTrack; // Проигрываемый трек, в нераспарсеном формате
    private int _currentPosition = 0; // Позиция в проигрываемом треке
    private int _currentPhraseIdx; // Текущая фраза, в списке фраз темы

    public Media(Activity activity, Theme theme)
    {
        _activity = activity;
        _theme = theme;
    }

    //endregion

    public ObservableBoolean canPlay = new ObservableBoolean(true);
    public ObservableBoolean canPause = new ObservableBoolean(false);
    public ObservableBoolean canStop = new ObservableBoolean(false);
    public ObservableField<String> phrase = new ObservableField<>();
    public ObservableField<String> comment = new ObservableField<>();
    public ObservableField<String> answers = new ObservableField<>();

    //region Properties

    private Theme _theme;
    public Theme getTheme() {
        return _theme;
    }
    public void setTheme(Theme _theme) {
        this._theme = _theme;
    }

    private MediaState _currentState = MediaState.inStop;
    public MediaState getCurrentState() {
        return _currentState;
    }

    private Boolean _withComment = true;
    public Boolean getWithComment() {
        return _withComment;
    }
    public void setWithComment(Boolean _withComment) {
        this._withComment = _withComment;
    }


    //endregion

    //region Helper

    private void erase() {
        setObservableFields(true, false, false);
        _currentState = MediaState.inStop;
        _currentTrack = null;
        _currentPosition = 0;
        _currentPhrase = null;
        mPlayer.release();
    }

    private void setObservableFields(boolean canIsPlay, boolean canIsPause, boolean canIsStop) {
        canPlay.set(canIsPlay);
        canPause.set(canIsPause);
        canStop.set(canIsStop);
    }

    private void play() {
        if (_currentTrack == null)
            return;

        try {
            File tempMp3 = File.createTempFile("track", ".mp3", App.getContext().getCacheDir());
            tempMp3.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempMp3);
            fos.write(Base64.decode(_currentTrack, Base64.DEFAULT));
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

    //endregion

    // Загружается отображение и прогирывание фразы под номером idx, либо продолжается воспроизведение, если MediaState.inPause is true
    public void start() {
        if (!canPlay.get())
            return;

        if(_theme.getPhrases().size() == 0){
            AppUtilities.showSnackbar(_activity, "Не загружены аудио файлы фраз", false);
            return;
        }

        if (_currentPhraseIdx >= _theme.getPhrases().size())
            return;

        try {
            if (_currentState == MediaState.inPause) {
                mPlayer.seekTo(_currentPosition);
                mPlayer.start();
            } else {
                if (_withComment) {
                    _currentTrack = _theme.getPhrases().get(_currentPhraseIdx).getCommentAudio();
                    play();
                    _currentTrack = _theme.getPhrases().get(_currentPhraseIdx).getAudio();
                    play();
                } else{
                    _currentTrack = _theme.getPhrases().get(_currentPhraseIdx).getAudio();
                    play();
                }
            }
            _currentState = MediaState.inPlay;
            setObservableFields(false, true, true);
        } catch (IllegalStateException e) {
            erase();
        }
    }

    // Отображается и проигрывается следующая фраза из темы
    public void next()
    {
        _currentPhraseIdx++;
        start();
    }

    // Отображаются и проигрываются варианты ответов
    public void answer()
    {
        if (!canPlay.get())
            return;

        if(_theme.getPhrases().size() == 0){
            AppUtilities.showSnackbar(_activity, "Не загружены аудио файлы фраз", false);
            return;
        }

        if (_currentPhraseIdx >= _theme.getPhrases().size())
            return;

        try {
            if (_currentState == MediaState.inPause) {
                mPlayer.seekTo(_currentPosition);
                mPlayer.start();
            } else {
                if (_withComment) {
                    _currentTrack = _theme.getPhrases().get(_currentPhraseIdx).getCommentAudio();
                    play();
                    _currentTrack = _theme.getPhrases().get(_currentPhraseIdx).getAudio();
                    play();
                } else{
                    _currentTrack = _theme.getPhrases().get(_currentPhraseIdx).getAudio();
                    play();
                }
            }
            _currentState = MediaState.inPlay;
            setObservableFields(false, true, true);
        } catch (IllegalStateException e) {
            erase();
        }
    }

    public void pause() {
        if (!canPause.get())
            return;
        _currentPosition = mPlayer.getCurrentPosition();
        mPlayer.pause();

        _currentState = MediaState.inPause;
        setObservableFields(true, false, true);
    }

    public void stop() {
        try {
            _currentState = MediaState.inStop;
            mPlayer.stop();
        } finally {
            erase();
        }
    }

    // Текущее состояние проигрывателя
    public enum MediaState {
        inPlay, inPause, inStop
    }

}
