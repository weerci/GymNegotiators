package kriate.production.com.gymnegotiators;

import android.app.Application;
import android.content.SharedPreferences;

import org.solovyev.android.checkout.Billing;
import kriate.production.com.gymnegotiators.Utils.Encryption;
import kriate.production.com.gymnegotiators.fit.FitService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dima on 30.06.2017.
 */

public class App extends Application {

    // region Init file имя файла настройки
    public static final String APP_PREFERENCES = "settings";
    public static final Boolean APP_PREFERENCES_WITH_COMMENT = true;
    private SharedPreferences mSettings;

    // endregion

    public App() {
        instance = this;
    }

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        billing.connect();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        fitService = retrofit.create(FitService.class);
        mSettings = getSharedPreferences(APP_PREFERENCES, instance.MODE_PRIVATE);
    }

    // Context
    public static App getContext() {
        if (instance == null)
            return instance = new App();
        else
            return instance;
    }

    //region Billing
    static final String MAIL = "grizzlidev@gmail.com";
    private final Billing billing = new Billing(this, new Billing.DefaultConfiguration() {
        @Override
        public String getPublicKey() {
            String encString =
                    "KjsgODMGKConESsWBQoAKxcUXy8mIyw8Oy0mJyQneCYgKCAubQQkLiYjLDsVHislERc0Kl0ZOy15" +
                    "MyReIhcoFS0BCx0jTgg0VSVbQ2cWB11UBFsPSxQmBRJDL0hcWSElRhUtCz4zOSMvHytVIUQ0HRg4" +
                    "PSR6JB80HTEHTRIJJSUIMmtXKCo4HmoiXissMA8NPzsEIgY7MFEjSl0EWkwoLjMYQh4bARhPHC8m" +
                    "MysXKysdACM5Xx8BEDUPLFNcRwwBGhEGKlcIPSRVOV03Iio5Ii9dDV8iJgIbWDVdKQ1ZIisiLzgD" +
                    "CzcSXyEIXSF2CSVfD0E6CjUmUT0DFCgmNwVGXmQnQCEAQz0gC1olAS0jbxQ7ABhVWCE6OggICj5K" +
                    "XDFTJycYSAEoJQ5kDCAMIhA9SEstDD0mPSYCATEYCnwqWS8FRBMQMAM4VyoSMzcXFjMIHlNWHgY6" +
                    "XilKICsuSj0KHzsqRidPVV0dMDwwKQNURg0EHxNTDhBCH2ouFl09AgsJGz0gICQnASU=";

            return   Encryption.decrypt(encString, MAIL);
        }
    });

    public Billing getBilling() {
        return billing;
    }
    //endregion

    static final String BASE_URL = "http://www.kriate.ru";
    private static FitService fitService;
    private Retrofit retrofit;

    public static FitService getFitService() {
        return fitService;
    }
    //region Fit


    //endregion
}
