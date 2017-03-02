package tw.gov.epa.taqmpredict.predict;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tw.gov.epa.taqmpredict.predict.model.PredictData;

/**
 * Created by user on 2017/3/2.
 */

public class DriverService {
    private String url = "https://drive.google.com/"; //https://www.googleapis.com/
    private String id = "0B_TvZKObPCRCa1VSdVJaWkU2Q2s";//"0B7Ld7OVhJc6HMlBWN2xVWDdabkk";
    private DriverAPI driveApi;

    public DriverService() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient())
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        driveApi = retrofit.create(DriverAPI.class);
    }

    public void getPredictData() {
        Call<PredictData> result = driveApi.getPredictData(id);
        result.enqueue(new Callback<PredictData>() {
            @Override
            public void onResponse(Call<PredictData> call, Response<PredictData> response) {
                Log.d("predict response:",response.body().getResult().toString());
            }

            @Override
            public void onFailure(Call<PredictData> call, Throwable t) {

            }
        });
    }

}
