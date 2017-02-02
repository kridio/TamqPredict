package tw.gov.epa.taqmpredict;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nobrain.android.permissions.AndroidPermissions;
import com.nobrain.android.permissions.Checker;
import com.nobrain.android.permissions.Result;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import tw.gov.epa.taqmpredict.data.EpaDataRequest;
import tw.gov.epa.taqmpredict.gps.area.AreaRequest;
import tw.gov.epa.taqmpredict.gps.GPSTracker;
import tw.gov.epa.taqmpredict.data.DataCache;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private DataCache<String,String> dataCache;
//    private DataProcessor dataProcessor;
    private GPSTracker gpsTracker;
    private AreaRequest areaRequest;
    private EpaDataRequest epaDataRequest;

    private TextView tv_location;
    private Toolbar mToolbar;

    public static final int REQUEST_CODE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();

        createView();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        changeFragment(MainFragment.newInstance());

        gpsTracker = new GPSTracker(this);
        gpsTracker.startLocation();

        areaRequest = new AreaRequest();

        epaDataRequest = new EpaDataRequest();

        dataCache = new DataCache<String, String>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //set full screen
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        areaRequest.getArea(gpsTracker.getLat_lng());
        epaDataRequest.getEpaDataRecord();

//        dataProcessor = new DataProcessor(dataCache);
//        dataProcessor.execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gpsTracker.stopLocation();
    }

    private void changeFragment(Fragment f) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main, f);
        transaction.commitAllowingStateLoss();
    }

    public void checkPermission(){
        AndroidPermissions.check(this)
                .permissions(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION)
                .hasPermissions(new Checker.Action0() {
                    @Override
                    public void call(String[] permissions) {
                        String msg = "Permission has " + permissions[0];
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this,
                                msg,
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .noPermissions(new Checker.Action1() {
                    @Override
                    public void call(String[] permissions) {
                        String msg = "Permission has no " + permissions[0];
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this,
                                msg,
                                Toast.LENGTH_SHORT).show();

                        ActivityCompat.requestPermissions(MainActivity.this
                                , new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}
                                , REQUEST_CODE);
                    }
                })
                .check();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, final String[] permissions, int[] grantResults) {
        AndroidPermissions.result(MainActivity.this)
                .addPermissions(REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION)
                .putActions(REQUEST_CODE, new Result.Action0() {
                    @Override
                    public void call() {
                        String msg = "Request Success : " + permissions[0];
                        Toast.makeText(MainActivity.this,
                                msg,
                                Toast.LENGTH_SHORT).show();

                    }
                }, new Result.Action1() {
                    @Override
                    public void call(String[] hasPermissions, String[] noPermissions) {
                        String msg = "Request Fail : " + noPermissions[0];
                        Toast.makeText(MainActivity.this,
                                msg,
                                Toast.LENGTH_SHORT).show();

                    }
                })
                .result(requestCode, permissions, grantResults);
    }

    public void createView(){
        tv_location = (TextView) findViewById(R.id.tv_location);
    }

    Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
        @Override
        public void subscribe(ObservableEmitter<String> e) throws Exception {

        }
    });
}