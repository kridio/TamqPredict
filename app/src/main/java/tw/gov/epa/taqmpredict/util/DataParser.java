package tw.gov.epa.taqmpredict.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by user on 2017/1/23.
 */

public class DataParser {
    private String TAG = DataParser.class.getSimpleName();
    private final String FIELDS = "fields";
    private final String RECORDS = "records";

    private DataCache dataCache;

    public DataParser(DataCache dataCache){
        this.dataCache = dataCache;
    }

    public void parser_object(String jsonStr){
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            // Getting JSON Array node
            JSONArray records = jsonObj.getJSONArray(RECORDS);

            // looping through All Contacts
            for (int i = 0; i < records.length(); i++) {
                JSONObject c = records.getJSONObject(i);

                String id = c.getString("id");

                // tmp hash map for single contact
                HashMap<String, String> record_map = new HashMap<>();

                // adding each child node to HashMap key => value
                record_map.put("id", id);
            }

        } catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }
}
