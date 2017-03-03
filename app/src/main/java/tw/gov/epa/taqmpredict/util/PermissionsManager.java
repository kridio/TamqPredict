package tw.gov.epa.taqmpredict.util;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.nobrain.android.permissions.AndroidPermissions;
import com.nobrain.android.permissions.Checker;

import tw.gov.epa.taqmpredict.MainActivity;

/**
 * Created by user on 2017/2/6.
 */

public class PermissionsManager {
    private static final String TAG = PermissionsManager.class.getSimpleName();

    public static final int REQUEST_CODE = 102;

    public static void checkPermission(MainActivity activity){
        AndroidPermissions.check(activity)
                .permissions(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .hasPermissions(new Checker.Action0() {
                    @Override
                    public void call(String[] permissions) {
                        String msg = "Permission has " + permissions[0];
                        Log.d(TAG, msg);
//                        Toast.makeText(activity,
//                                msg,
//                                Toast.LENGTH_SHORT).show();
                    }
                })
                .noPermissions(new Checker.Action1() {
                    @Override
                    public void call(String[] permissions) {
                        String msg = "Permission has no " + permissions[0];
                        Log.d(TAG, msg);
//                        Toast.makeText(activity,
//                                msg,
//                                Toast.LENGTH_SHORT).show();

                        ActivityCompat.requestPermissions(activity
                                , new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE}
                                , REQUEST_CODE);
                    }
                })
                .check();

    }
}
