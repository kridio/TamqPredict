package tw.gov.epa.taqmpredict.util;

import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.gson.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

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
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public DataProcessor(DataCache dataCache){
        this.dataCache =  dataCache;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        HttpHandler sh = new HttpHandler();
        String key = simpleDateFormat.format(new Date());
        if(dataCache!=null) {
            DataParser dataParser = new DataParser(this.dataCache);
            String jsonStr = dataCache.getJsonLruCache(key);

            if (jsonStr != null && !jsonStr.equals("")) {
                Log.e(TAG, "Response from cache: " + jsonStr);
                dataParser.parser_object(jsonStr);
            } else {
                // Making a request to url and getting response
                jsonStr = sh.makeServiceCall(url);
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
