package kriate.production.com.gymnegotiators;

import android.app.Application;
import android.content.Context;

/**
 * Created by dima on 30.06.2017.
 */

public class App extends Application {

    public App() {
        instance = this;
    }

    private static App instance;

    public static App getContext() {
        if (instance == null)
            return new App();
        else
            return  instance;
    }

}
