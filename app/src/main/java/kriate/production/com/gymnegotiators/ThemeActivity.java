package kriate.production.com.gymnegotiators;

import android.view.Menu;
import android.view.MenuItem;
import com.stfalcon.androidmvvmhelper.mvvm.activities.BindingActivity;
import kriate.production.com.gymnegotiators.databinding.ActivityThemeBinding;


public class ThemeActivity extends BindingActivity<ActivityThemeBinding, ThemeActivityVM> {

    private static final String KEY_STATUS = "STATUS";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_theme, menu);
        return true;
    }

    @Override
    public ThemeActivityVM onCreate() {
        setSupportActionBar(getBinding().toolbar);

        return new ThemeActivityVM(this, KEY_STATUS);
    }

    @Override
    public int getVariable() {
        return kriate.production.com.gymnegotiators.BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_theme;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
