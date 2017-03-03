package tw.gov.epa.taqmpredict.data;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import tw.gov.epa.taqmpredict.data.model.EpaData;

/**
 * Created by user on 2017/2/2.
 */

public class DataRequestService {
    private final static String TAG = DataRequestService.class.getSimpleName();

    private String url = "http://opendata.epa.gov.tw/webapi/api/rest/datastore/";
    private DataRequestApi dataRequestApi;

    /**
     * http://opendata.epa.gov.tw/webapi/api/rest/datastore/{resourceID}/?format={format}&limit={limit}&token={token}
     * {resourceID}:資料代號。各資料頁面內透過連結可取得
     * {format}:資料格式。json、xml、csv
     * {limit}:取最前筆數
     * {offset}:跳過筆數
     * {sort}:排序欄位
     * {token}:資料下載驗證碼，「權限登記」頁面進行登記後可取得。
     */
    private final String RESOURCEID = "355000000I-001805";
    private final String FORMAT_NAME = "format";
    private final String FORMAT = "json";
    private final String TOKEN_NAME = "token";
    private final String TOKEN = "GFnGTmY/a0qiH1ClEPIQTg";
    private String limit = "0";
    private String offset = "0";
    private String orderby = "SiteName";
    private Map<String, String> mDefaultParams;
    private Map<String, String> mParams;

    public DataRequestService(){
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build();

        dataRequestApi = retrofit.create(DataRequestApi.class);

        mDefaultParams = new HashMap<>();
        mDefaultParams.put(FORMAT_NAME,FORMAT);
        mDefaultParams.put(TOKEN_NAME,TOKEN);
    }

    public Call<EpaData> getEpaData(){
        return dataRequestApi.getEpaDataRecord(RESOURCEID,mDefaultParams);
    }

    public DataRequestService setParams(Map<String,String> params){
        if(params == null || (params!=null && params.isEmpty())) {
            mParams = mDefaultParams;
        }
        else{
            mParams = params;
        }
        return this;
    }
}
