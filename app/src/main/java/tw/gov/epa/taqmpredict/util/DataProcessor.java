package tw.gov.epa.taqmpredict.util;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by user on 2017/1/23.
 * Async task class to get json by making HTTP call
 */
public class DataProcessor extends AsyncTask<Void, Void, Void> {
    private String TAG = DataProcessor.class.getSimpleName();
    /**
     * http://opendata.epa.gov.tw/webapi/api/rest/datastore/{resourceID}/?format={format}&limit={limit}&token={token}
     * {resourceID}:資料代號。各資料頁面內透過連結可取得
     * {format}:資料格式。json、xml、csv
     * {limit}:取最前筆數
     * {offset}:跳過筆數
     * {sort}:排序欄位
     * {token}:資料下載驗證碼，「權限登記」頁面進行登記後可取得。
     */
    private String resourceID = "355000000I-001805";
    private String format = "json";
    private String limit = "0";
    private String offset = "0";
    private String orderby = "SiteName";
    private String token = "GFnGTmY/a0qiH1ClEPIQTg";
    private String url = "http://opendata.epa.gov.tw/webapi/api/rest/datastore/"+resourceID+"/?format="+format+"&token="+token;
    private DataCache<String,String> dataCache;


    public DataProcessor(DataCache dataCache){
        this.dataCache =  dataCache;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * Get data from server and store in cache
     * @param arg0
     * @return
     */
    @Override
    protected Void doInBackground(Void... arg0) {
        HttpHandler httpHandler = new HttpHandler();
        String key = dataCache.getRealTimeInfoKey();
        if(dataCache!=null) {
            DataParser dataParser = new DataParser();
            String jsonStr = dataCache.getJsonLruCache(key);

            if (jsonStr != null && !jsonStr.equals("")) {
                Log.e(TAG, "Response from cache: " + jsonStr);
                dataParser.parser_object(jsonStr);
            } else {
                // Making a request to url and getting response
                jsonStr = httpHandler.makeServiceCall(url);
                Log.e(TAG, "Response from url: " + jsonStr);

                if (jsonStr != null && !jsonStr.equals("")) {
                    dataCache.addJsonLruCache(key,jsonStr);
                    dataParser.parser_object(jsonStr);
                } else {
                    Log.e(TAG, "Couldn't get json from server.");
                }
            }
        }
        return null;
    }
}
