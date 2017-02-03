package tw.gov.epa.taqmpredict.gps.area;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import tw.gov.epa.taqmpredict.gps.area.pojo.AreaData;

/**
 * Created by user on 2017/2/2.
 */

public interface AreaRequestService {
    @GET("json")
    Call<AreaData> getAreaData(
            @Query("latlng") String latlng,
            @Query("language") String language,
            @Query("sensor") String sensor
    );
}
