package kriate.production.com.gymnegotiators;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import org.solovyev.android.checkout.Billing;
import org.solovyev.android.checkout.Cache;
import org.solovyev.android.checkout.Checkout;
import org.solovyev.android.checkout.Inventory;
import org.solovyev.android.checkout.PlayStoreListener;
import org.solovyev.android.checkout.PurchaseVerifier;

import java.util.concurrent.Executor;

import kriate.production.com.gymnegotiators.Utils.Encryption;

/**
 * Created by dima on 30.06.2017.
 */

public class App extends Application {

    public App() {
        instance = this;
    }

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        billing.connect();
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
}
