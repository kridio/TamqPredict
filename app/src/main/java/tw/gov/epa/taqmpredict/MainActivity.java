package tw.gov.epa.taqmpredict;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import com.nobrain.android.permissions.AndroidPermissions;
import com.nobrain.android.permissions.Result;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation.helper.FragmentLifecycleCallbacks;
import tw.gov.epa.taqmpredict.ui.fragment.MainFragment;
import tw.gov.epa.taqmpredict.util.PermissionsManager;

public class MainActivity extends SupportActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            loadRootFragment(R.id.tamq_container, MainFragment.newInstance());  // load root Fragment
        }

        PermissionsManager.checkPermission(this);

        registerFragmentLifecycleCallbacks(new FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentSupportVisible(SupportFragment fragment) {
                Log.i("MainActivity", "onFragmentSupportVisible--->" + fragment.getClass().getSimpleName());
            }

            @Override
            public void onFragmentCreated(SupportFragment fragment, Bundle savedInstanceState) {
                super.onFragmentCreated(fragment, savedInstanceState);
            }
        });


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);

//        changeFragment(MainFragment.newInstance());

//        epaDataRequestPresenter = new DataRequestPresenter(this,epaDataRequestService);
//        areaRequestPresenter = new AreaRequestPresenter(this,areaRequestService,gpsTrackerService);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //set full screen
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            //TODO 按返回鍵，則執行退出確認
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

//    private void changeFragment(Fragment f) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.activity_main, f);
//        transaction.commitAllowingStateLoss();
//    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
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