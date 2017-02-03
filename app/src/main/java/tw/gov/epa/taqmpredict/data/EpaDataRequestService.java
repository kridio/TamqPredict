package tw.gov.epa.taqmpredict.data;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tw.gov.epa.taqmpredict.data.pojo.Record;

/**
 * Created by user on 2017/2/2.
 */

public interface EpaDataRequestService {
    @GET("{resourceid}/")
    Call<Record> getEpaDataRecord(
            @Path("resourceid") String resourceid,
            @Query("format") String format,
            @Query("token") String token
    );
}
