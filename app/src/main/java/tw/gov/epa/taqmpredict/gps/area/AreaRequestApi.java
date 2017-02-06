package tw.gov.epa.taqmpredict.gps.area;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import tw.gov.epa.taqmpredict.gps.area.model.AreaData;

/**
 * Created by user on 2017/2/2.
 */

public interface AreaRequestApi {
    @GET("json")
    Call<AreaData> getAreaData(
            @QueryMap Map<String, String> options
    );
}
