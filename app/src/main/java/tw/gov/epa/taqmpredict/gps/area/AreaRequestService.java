package tw.gov.epa.taqmpredict.gps.area;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import tw.gov.epa.taqmpredict.gps.area.model.AddressComponent;
import tw.gov.epa.taqmpredict.gps.area.model.AreaData;
import tw.gov.epa.taqmpredict.gps.area.model.Result;

/**
 * Created by user on 2017/1/25.
 */

public class AreaRequestService {
    private final static String TAG = AreaRequestService.class.getSimpleName();

    private final String url = "http://maps.google.com/maps/api/geocode/";
    private final String LANG_NAME = "language";
    private final String LANG = "zh-TW";
    private final String SENSOR_NAME = "sensor";
    private final String SENSOR = "true";
    private final String LATLNG_NAME = "latlng";
    private Map<String, String> paramsMap;
    private AreaRequestApi areaRequestApi;

    private Context mContedxt;

    public AreaRequestService(Context context){
        paramsMap = new HashMap<>();
        paramsMap.put(LANG_NAME,LANG);
        paramsMap.put(SENSOR_NAME,SENSOR);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        areaRequestApi = retrofit.create(AreaRequestApi.class);
        mContedxt = context;
    }

    public void getAreaByGoogleApi(double lat,double lng){
        try {
            Geocoder gc = new Geocoder(mContedxt, Locale.TRADITIONAL_CHINESE);
            List<Address> lstAddress = gc.getFromLocation(lat, lng, 1);
//            String returnAddress = lstAddress.get(0).getAddressLine(0);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void getAreaByHttp(String lat_lng){
        if(isLatLngValid(lat_lng)) {
            paramsMap.put(LATLNG_NAME, lat_lng);

            areaRequestApi.getAreaData(paramsMap)
                    .enqueue(new Callback<AreaData>() {
                        @Override
                        public void onResponse(Call<AreaData> call, Response<AreaData> response) {
                            for (Result result : response.body().getResults()) {
                                for (AddressComponent address : result.getAddressComponents()) {
                                    logd("request long area:" + address.getLongName());
                                    logd("request short area:"+ address.getShortName());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<AreaData> call, Throwable t) {

                        }
                    });
        }
        else{
            logd("lat_lng is not matching");
        }
    }

    public Boolean isLatLngValid(String lat_lng){
        return Pattern.compile("^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$")
               .matcher(lat_lng).matches();
    }

    public void logd(String log){
        Log.d(TAG,log);
    }
}
