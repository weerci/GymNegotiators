package kriate.production.com.gymnegotiators.Utils;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import kriate.production.com.gymnegotiators.R;

/**
 * Created by dima on 27.07.2017.
 */

public class ErrorActivity  extends Activity{
    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_theme);

        error = (TextView) findViewById(R.id.error_text);
        error.setText(getIntent().getStringExtra("error"));
    }
}
