package tw.gov.epa.taqmpredict.data;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import tw.gov.epa.taqmpredict.data.pojo.Record;

/**
 * Created by user on 2017/2/2.
 */

public class EpaDataRequest {
    private final static String TAG = EpaDataRequest.class.getSimpleName();
    /**
     * http://opendata.epa.gov.tw/webapi/api/rest/datastore/{resourceID}/?format={format}&limit={limit}&token={token}
     * {resourceID}:資料代號。各資料頁面內透過連結可取得
     * {format}:資料格式。json、xml、csv
     * {limit}:取最前筆數
     * {offset}:跳過筆數
     * {sort}:排序欄位
     * {token}:資料下載驗證碼，「權限登記」頁面進行登記後可取得。
     */
    private final String resourceID = "355000000I-001805";
    private final String format = "json";
    private final String token = "GFnGTmY/a0qiH1ClEPIQTg";
    private final String param = "format="+format+"&token="+token;
    private String url = "http://opendata.epa.gov.tw/webapi/api/rest/datastore/"+resourceID+"/";
    private String limit = "0";
    private String offset = "0";
    private String orderby = "SiteName";
    private DataCache<String,String> dataCache;

    public void getEpaDataRecord(){
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build();

        EpaDataRequestService epaDataRequestService = retrofit.create(EpaDataRequestService.class);
        Observable<Record> epaData = epaDataRequestService.getEpaDataRecord(param);

        epaData.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Record -> {
                    Log.e(TAG+":Current Epa Record ", Record.getPM25());
                });
    }
}
