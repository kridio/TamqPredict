package tw.gov.epa.taqmpredict;

import android.Manifest;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nobrain.android.permissions.AndroidPermissions;
import com.nobrain.android.permissions.Result;

import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.SupportActivity;
import tw.gov.epa.taqmpredict.data.DataRequestService;
import tw.gov.epa.taqmpredict.data.DataRequestPresenter;
import tw.gov.epa.taqmpredict.data.IDataRequestPresenter;
import tw.gov.epa.taqmpredict.gps.GPSTrackerService;
import tw.gov.epa.taqmpredict.gps.area.AreaRequestPresenter;
import tw.gov.epa.taqmpredict.gps.area.AreaRequestService;
import tw.gov.epa.taqmpredict.data.DataCache;
import tw.gov.epa.taqmpredict.gps.area.IAreaRequestPresenter;
import tw.gov.epa.taqmpredict.util.PermissionsManager;

public class MainActivity extends SupportActivity implements IMainView{
    private static final String TAG = MainActivity.class.getSimpleName();

    private DataCache<String,String> dataCache;
    private GPSTrackerService gpsTrackerService;
    private AreaRequestService areaRequestService;
    private DataRequestService epaDataRequestService;

    private TextView tv_location;
    private Toolbar mToolbar;

    private IDataRequestPresenter epaDataRequestPresenter;
    private IAreaRequestPresenter areaRequestPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            loadRootFragment(R.id.fragment_main, MainFragment.newInstance());  // 加载根Fragment
        }

        Fragmentation.builder()
                .stackViewMode(Fragmentation.BUBBLE)
                .install();

        PermissionsManager.checkPermission(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        changeFragment(MainFragment.newInstance());

        gpsTrackerService = new GPSTrackerService(this);
        areaRequestService = new AreaRequestService();
        epaDataRequestService = new DataRequestService();

        epaDataRequestPresenter = new DataRequestPresenter(this,epaDataRequestService);
        areaRequestPresenter = new AreaRequestPresenter(this,areaRequestService,gpsTrackerService);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //set full screen
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        gpsTrackerService.startLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gpsTrackerService.stopLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void changeFragment(Fragment f) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main, f);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, final String[] permissions, int[] grantResults) {
        AndroidPermissions.result(MainActivity.this)
                .addPermissions(PermissionsManager.REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION)
                .putActions(PermissionsManager.REQUEST_CODE, new Result.Action0() {
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
}