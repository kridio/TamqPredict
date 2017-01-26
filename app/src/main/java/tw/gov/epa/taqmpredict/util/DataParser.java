package tw.gov.epa.taqmpredict.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 2017/1/23.
 */

public class DataParser {
    private String TAG = DataParser.class.getSimpleName();
    private final String FIELDS = "fields";
    private final String RECORDS = "records";
    private final String RESULT = "result";


    public void parser_object(String jsonStr){
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            // Getting JSON Array node
            JSONObject result = jsonObj.getJSONObject(RESULT);
            JSONArray records = result.getJSONArray(RECORDS);

            // looping through All Contacts
            for (int i = 0; i < records.length(); i++) {
                JSONObject c = records.getJSONObject(i);
                EpaData epaData = new EpaData();
                String siteName = c.getString(EpaData.SITENAME);
                String date = c.getString(EpaData.PUBLISH_TIME);
                epaData.setSiteName(siteName);
                epaData.setCountry(c.getString(EpaData.COUNTY));
                epaData.setPollutant(c.getString(EpaData.POLLUTANT));
                epaData.setStatus(c.getString(EpaData.STATUS));

            }

        } catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }
}
