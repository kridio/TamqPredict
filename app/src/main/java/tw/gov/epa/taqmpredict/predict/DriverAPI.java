package tw.gov.epa.taqmpredict.predict;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import tw.gov.epa.taqmpredict.predict.model.PredictData;

/**
 * Created by user on 2017/3/2.
 */

public interface DriverAPI {
    @GET("open")
    Call<PredictData> getPredictData(
            @Field("id") String id
    );
}
