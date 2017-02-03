package tw.gov.epa.taqmpredict.gps.area;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import tw.gov.epa.taqmpredict.gps.area.pojo.AddressComponent;
import tw.gov.epa.taqmpredict.gps.area.pojo.AreaData;
import tw.gov.epa.taqmpredict.gps.area.pojo.Result;

/**
 * Created by user on 2017/1/25.
 */

public class AreaRequest {
    private final static String TAG = AreaRequest.class.getSimpleName();

    private final String url = "http://maps.google.com/maps/api/geocode/";
    private final String LANG = "zh-TW";
    private final String SENSOR = "true";

    public void getArea(String lat_lng){
        lat_lng = "12,21";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AreaRequestService areaRequestService = retrofit.create(AreaRequestService.class);
        Call<AreaData> area = areaRequestService.getAreaData(lat_lng,LANG,SENSOR);

        area.enqueue(new Callback<AreaData>() {

            @Override
            public void onResponse(Call<AreaData> call, Response<AreaData> response) {
                for(Result result:response.body().getResults()){
                    for(AddressComponent address:result.getAddressComponents()){
                        Log.d("request long area:",address.getLongName());
                        Log.d("request short area:",address.getShortName());
                    }
                }
            }

            @Override
            public void onFailure(Call<AreaData> call, Throwable t) {

            }
        });

//        area.subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(areaData -> {
//                    Log.e(TAG+":Current Area ", areaData.getResults()
//                            .get(0)
//                            .getFormattedAddress());
//                });
    }

//    public double getLatitu(){
//        double latitude = 0;
//        latitude = gps.getLatitude();
//
//        return latitude;
//    }
//
//    public double getLongitude(){
//        double longitude = 0;
//        if(gps!=null && gps.canGetLocation()) {
//            longitude = gps.getLongitude();
//        }
//        return longitude;
//    }

//    @Override
//    protected Void doInBackground(Void... params) {
//        String areaJson = "";
//        double latitude = getLatitu();
//        double longitude = getLongitude();
//        if(latitude!=0 && longitude!=0) {
//            String lati_longi = String.valueOf(latitude) + "," + String.valueOf(longitude);
//            String url = "http://maps.google.com/maps/api/geocode/json?latlng="+lati_longi+"&language=zh-TW&sensor=true";
//            HttpHandler httpHandler = new HttpHandler();
//            areaJson = httpHandler.makeServiceCall(url);
//        }
//        Log.d(TAG,areaJson);
//
//        return null;
//    }
}
