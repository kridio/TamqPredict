package tw.gov.epa.taqmpredict;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import tw.gov.epa.taqmpredict.util.DataCache;
import tw.gov.epa.taqmpredict.util.DataProcessor;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private DataCache<String,String> dataCache;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateToolBar();
        setContentView(R.layout.activity_main);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        changeFragment(MainFragment.newInstance());

        dataCache = new DataCache<String, String>();
        new DataProcessor(dataCache).execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void updateToolBar(){
        //hiden toolbar
        //getSupportActionBar().hide();
        //hiden statusbar

        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void changeFragment(Fragment f) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main, f);
        transaction.commitAllowingStateLoss();
    }
}
