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
    private static int _idx = 0; // Переменная содержащая текущую позицию проигрываемого файла в последовательности

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
        _currentPhraseIdx = 0;
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

    // Проигрывает переданную в параметре последовательность файлов
    private void playSequence(String[] sequence){
        _idx = 0;
        mPlayer.setOnCompletionListener(mp -> {
            if (_idx <= sequence.length) {
                _currentTrack = sequence[_idx];
                play();
                _idx++;
            } else {
                stop();
            }
        });
        _currentTrack = sequence[_idx];
        play();
        _idx++;
    }

    public enum Playing {answer, phrase}

    //endregion

    // Загружается отображение и прогирывание фразы под номером idx, либо продолжается воспроизведение, если MediaState.inPause is true
    public void start(Playing playing) {
        if (!canPlay.get())
            return;

        if(_theme.getPhrases().size() == 0){
            AppUtilities.showSnackbar(_activity, "Не загружены аудио файлы фраз", false);
            return;
        }

        if (_currentPhraseIdx >= _theme.getPhrases().size())
            return;

        try {
            switch (playing){
                case phrase:
                    if (_withComment)
                        playSequence(new String[]{_theme.getPhrases().get(_currentPhraseIdx).getCommentAudio(), _theme.getPhrases().get(_currentPhraseIdx).getAudio()});
                    else
                        playSequence(new String[]{_theme.getPhrases().get(_currentPhraseIdx).getAudio()});
                    break;
                case answer:
                    playSequence(new String[]{
                            _theme.getPhrases().get(_currentPhraseIdx).getVar1(),
                            _theme.getPhrases().get(_currentPhraseIdx).getVar2(),
                            _theme.getPhrases().get(_currentPhraseIdx).getVar3()});
                    break;
                default:
                    if (_currentState == MediaState.inPause) {
                        mPlayer.seekTo(_currentPosition);
                        mPlayer.start();
                    }
                    break;
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
        start(Playing.phrase);
    }

    // Прекращается проигрывание текущего фрагмента, состояние плейера сохраняется, при нажатии кнопки play воспроизведение продолжается с места останова
    public void pause() {
        if (!canPause.get())
            return;
        _currentPosition = mPlayer.getCurrentPosition();
        mPlayer.pause();

        _currentState = MediaState.inPause;
        setObservableFields(true, false, true);
    }

    // Останавливается проигрывание фразы или ответа, состояние плейера сбрасывается на начальное
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
