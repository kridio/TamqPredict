package tw.gov.epa.taqmpredict.gps;

/**
 * Created by user on 2017/1/25.
 */

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.DetectedActivity;

import io.nlopez.smartlocation.OnActivityUpdatedListener;
import io.nlopez.smartlocation.OnGeofencingTransitionListener;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.geofencing.utils.TransitionGeofence;
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;

public class GPSTrackerService implements OnActivityUpdatedListener,OnLocationUpdatedListener,OnGeofencingTransitionListener {
    private final static String TAG = GPSTrackerService.class.getSimpleName();

    private Context context;
    private LocationGooglePlayServicesProvider provider;
    private double mLat = 0;
    private double mLng = 0;
    Observer mObserver;

    public GPSTrackerService(Context context,Observer observer){
        this.context = context;
        mObserver = observer;
    }

    public void startLocation() {
        provider = new LocationGooglePlayServicesProvider();
        provider.setCheckLocationSettings(true);

        SmartLocation smartLocation = new SmartLocation.Builder(context).logging(true).build();

        smartLocation.location(provider).start(this);
        smartLocation.activity().start(this);

        // Create some geofences
        //GeofenceModel mestalla = new GeofenceModel.Builder("1").setTransition(Geofence.GEOFENCE_TRANSITION_ENTER).setLatitude(39.47453120000001).setLongitude(-0.358065799999963).setRadius(500).build();
        //smartLocation.geofencing().add(mestalla).start(this);
    }

    public void stopLocation() {
        SmartLocation.with(context).location().stop();
        logd("Location stopped!");

        SmartLocation.with(context).activity().stop();
        logd("Activity Recognition stopped!");

        //SmartLocation.with(context).geofencing().stop();
        //logd("Geofencing stopped!");
    }

    @Override
    public void onActivityUpdated(DetectedActivity detectedActivity) {

    }

    @Override
    public void onGeofenceTransition(TransitionGeofence transitionGeofence) {

    }

    @Override
    public void onLocationUpdated(Location location) {
        logd("onLocationUpdated: "+location.getLatitude()+","+location.getLongitude());
        mLat = location.getLatitude();
        mLng = location.getLongitude();

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                if(mLat!=0 && mLng!=0) {
                    e.onNext(mLat + "," + mLng);
                    e.onComplete();
                }
            }
        }).subscribe(mObserver);
    }

    public double getLat(){
        return mLat;
    }

    public double getLng(){
        return mLng;
    }

    public void logd(String log){
        Log.d(TAG,log);
    }
}
