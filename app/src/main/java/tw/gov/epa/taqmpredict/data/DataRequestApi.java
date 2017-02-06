package tw.gov.epa.taqmpredict.data;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import tw.gov.epa.taqmpredict.data.model.EpaData;

/**
 * Created by user on 2017/2/2.
 */


public interface DataRequestApi {
    final String RESOURCE_ID = "resourceid";

    @GET("{"+RESOURCE_ID+"}/")
    Call<EpaData> getEpaDataRecord(
            @Path(RESOURCE_ID)String resourceid,
            @QueryMap Map<String, String> options
    );
}
