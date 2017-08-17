package com.kriate.gymnegotiators;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import kriate.production.com.gymnegotiators.App;
import kriate.production.com.gymnegotiators.Model.Media;

/**
 * Created by dima on 26.07.2017.
 */

public class AudioTest extends ApplicationTestCase<Application> {
    public AudioTest() {
        super(Application.class);
    }

    @SmallTest
    public void init_state_test() throws Exception {
        Media media = Media.item(null);

        assertEquals(Media.MediaState.inStop, media.getCurrentState());
        assertEquals(true, media.canPlay.get());
        assertEquals(false, media.canPause.get());
        assertEquals(false, media.canStop.get());
    }
}
