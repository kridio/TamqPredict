package tw.gov.epa.taqmpredict;

import android.app.Application;

import com.google.gson.Gson;
import com.squareup.leakcanary.LeakCanary;

import tw.gov.epa.taqmpredict.db.DBManage;
import tw.gov.epa.taqmpredict.util.LogHelper;
import tw.gov.epa.taqmpredict.util.TaskExecutor;

/**
 * Created by Administrator on 2017/2/28.
 */

public class AirPollutionApplication extends Application {
    private static final String APP_ID = "";
    private static Gson sGson = new Gson();
    private static Application sApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;

        DBManage.getInstance().copyCitysToDB();

        TaskExecutor.executeTask(new Runnable() {
            @Override
            public void run() {

            }
        });

        initCrashReport();

        if (BuildConfig.DEBUG) {

            LogHelper.debugInit();
            LeakCanary.install(this);
        } else {
            LogHelper.releaseInit();
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        //ModelManager.unSubscribeAll();
    }

    private void initCrashReport() {
//        Beta.autoInit = true;
//        Beta.autoCheckUpgrade = false;
//        Beta.initDelay = 3 * 1000;
//        Beta.largeIconId = R.mipmap.icon;
//        Beta.smallIconId = R.mipmap.icon;
//        Beta.defaultBannerId = R.mipmap.icon;
//        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//        Bugly.init(getApplicationContext(), APP_ID, false);
    }

    public static Gson getGson() {
        return sGson;
    }

    public static Application getContext() {
        return sApplication;
    }
}
