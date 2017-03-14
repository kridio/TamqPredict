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

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import tw.gov.epa.taqmpredict.base.Constants;
import tw.gov.epa.taqmpredict.gps.area.model.AddressComponent;
import tw.gov.epa.taqmpredict.gps.area.model.AreaData;
import tw.gov.epa.taqmpredict.gps.area.model.Result;
import tw.gov.epa.taqmpredict.util.PreferencesUtil;

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

    private Context mContext;
    private String county="";

    AreaRequestPresenter mAreaRequestPresenter;

    public AreaRequestService(Context context,AreaRequestPresenter areaRequestPresenter){
        paramsMap = new HashMap<>();
        paramsMap.put(LANG_NAME,LANG);
        paramsMap.put(SENSOR_NAME,SENSOR);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        areaRequestApi = retrofit.create(AreaRequestApi.class);
        mContext = context;
        mAreaRequestPresenter =areaRequestPresenter;
    }

    public String getAreaByGoogleApi(double lat,double lng){
        List<Address> lstAddress=null;
        try {
            Geocoder gc = new Geocoder(mContext, Locale.TRADITIONAL_CHINESE);
            lstAddress = gc.getFromLocation(lat, lng, 1);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        if(lstAddress!=null && lstAddress.get(0)!=null){
            county = lstAddress.get(0).getLocality();
            Log.d(TAG,"----------------------------------");
            Log.d(TAG,lstAddress.get(0).getAdminArea());
            Log.d(TAG,lstAddress.get(0).getCountryName());
            Log.d(TAG,lstAddress.get(0).getLocality());
            Log.d(TAG,lstAddress.size()+"!");
            Log.d(TAG,"----------------------------------");
        }

        return county;
    }

    public void getAreaByHttp(double lat,double lng){
        String lat_lng = lat+","+lng;
        if(isLatLngValid(lat_lng)) {
            paramsMap.put(LATLNG_NAME, lat_lng);

            areaRequestApi.getAreaData(paramsMap)
                    .enqueue(new Callback<AreaData>() {
                        String administrative_area="";
                        @Override
                        public void onResponse(Call<AreaData> call, Response<AreaData> response) {
                            for (Result result : response.body().getResults()) {
                                for (AddressComponent address : result.getAddressComponents()) {
                                    logd("request long area:" + address.getLongName());
                                    logd("request short area:"+ address.getShortName());
                                    logd("request types:"+ address.getTypes().get(0));
                                    if(address.getTypes().get(0).equals("administrative_area_level_3")) {
                                        administrative_area = address.getShortName();
                                    }
                                }
                            }
                            mAreaRequestPresenter.sendArea(administrative_area);
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
