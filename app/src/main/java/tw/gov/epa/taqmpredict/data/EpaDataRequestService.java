package tw.gov.epa.taqmpredict.data;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import tw.gov.epa.taqmpredict.data.pojo.Record;

/**
 * Created by user on 2017/2/2.
 */

public interface EpaDataRequestService {
    @GET("?")
    Observable<Record> getEpaDataRecord(@Query("q") String records);
}
