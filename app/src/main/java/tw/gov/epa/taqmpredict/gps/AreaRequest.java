package tw.gov.epa.taqmpredict.gps;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import tw.gov.epa.taqmpredict.gps.GPSTracker;
import tw.gov.epa.taqmpredict.util.HttpHandler;

/**
 * Created by user on 2017/1/25.
 */

public class AreaRequest extends AsyncTask<Void, Void, Void> {
    private final static String TAG = AreaRequest.class.getSimpleName();
    private GPSTracker gps;
    public AreaRequest(Context context){
        gps = new GPSTracker(context);
    }

    public double getLatitu(){
        double latitude = 0;
        if(gps!=null && gps.canGetLocation()) {
            latitude = gps.getLatitude();
        }
        return latitude;
    }

    public double getLongitude(){
        double longitude = 0;
        if(gps!=null && gps.canGetLocation()) {
            longitude = gps.getLongitude();
        }
        return longitude;
    }

    @Override
    protected Void doInBackground(Void... params) {
        String areaJson = "";
        double latitude = getLatitu();
        double longitude = getLongitude();
        if(latitude!=0 && longitude!=0) {
            String lati_longi = String.valueOf(latitude) + "," + String.valueOf(longitude);
            String url = "http://maps.google.com/maps/api/geocode/json?latlng="+lati_longi+"&language=zh-TW&sensor=true";
            HttpHandler httpHandler = new HttpHandler();
            areaJson = httpHandler.makeServiceCall(url);
        }
        Log.d(TAG,areaJson);

        return null;
    }
}
