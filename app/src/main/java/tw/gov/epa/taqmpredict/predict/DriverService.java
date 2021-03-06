package tw.gov.epa.taqmpredict.predict;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tw.gov.epa.taqmpredict.predict.model.PredictData;
import tw.gov.epa.taqmpredict.predict.model.Result;
import tw.gov.epa.taqmpredict.ui.fragment.home.HomeFragment;

/**
 * Created by user on 2017/3/2.
 */

public class DriverService {
    private String url = "https://drive.google.com/"; //https://www.googleapis.com/
    //測試ID:"0B_TvZKObPCRCa1VSdVJaWkU2Q2s" 正式ID:"0B7Ld7OVhJc6HTDlya29QbEpRdEU"
    private String id = "0B7Ld7OVhJc6HTDlya29QbEpRdEU";//"0B_TvZKObPCRCa1VSdVJaWkU2Q2s";
    private String download = "download";
    private DriverAPI driveApi;
    HomeFragment mHm;

    public DriverService(HomeFragment hm) {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        driveApi = retrofit.create(DriverAPI.class);
        this.mHm = hm;
    }

    public void getPredictData() {
        Call<PredictData> result = driveApi.getPredictData(id,download);
        result.enqueue(new Callback<PredictData>() {
            @Override
            public void onResponse(Call<PredictData> call, Response<PredictData> response) {
                //Log.d("predict response:",response.body().getResult());
                for(Result res:response.body().getResult()){
                    Log.d("Driverservice:",res.getSiteName()+":"+res.getHr1());
                }

                mHm.setData(response.body().getResult());
            }

            @Override
            public void onFailure(Call<PredictData> call, Throwable t) {

            }
        });
    }
}
