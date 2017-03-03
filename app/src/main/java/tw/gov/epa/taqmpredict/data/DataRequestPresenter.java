package tw.gov.epa.taqmpredict.data;

import android.os.Environment;
import android.util.Log;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tw.gov.epa.taqmpredict.data.model.EpaData;
import tw.gov.epa.taqmpredict.data.model.Record;
import tw.gov.epa.taqmpredict.data.model.Result;
import tw.gov.epa.taqmpredict.gps.area.city.model.CityInfo;
import tw.gov.epa.taqmpredict.util.LogHelper;

/**
 * Created by user on 2017/2/6.
 */

public class DataRequestPresenter implements IDataRequestPresenter {
    private final String TAG = DataRequestPresenter.class.getSimpleName();

//    private IMainView mView;
    private DataRequestService mDataRequestService;

    private boolean isCache = false;
    private DataCache<String,String> dataCache;

    public DataRequestPresenter(//IMainView view,
                                DataRequestService dataRequestService){
//        mView = view;
        mDataRequestService = dataRequestService;
        dataCache = new DataCache<String, String>();
    }

    public void getEpaData(){
        getEpaData(null);
    }

    public void getEpaData(Map params){
        Log.d("TAG","getEpaData");
        mDataRequestService
                .setParams(params)
                .getEpaData()
                .enqueue(new Callback<EpaData>() {
                    @Override
                    public void onResponse(Call<EpaData> call, Response<EpaData> response) {
                        Result response_result = response.body().getResult();
                        List<CityInfo> inf_list = new ArrayList<CityInfo>();
                        for (Record record : response_result.getRecords()) {
                            CityInfo inf = new CityInfo();
                            inf.setCounty(record.getCounty());
                            inf.setSiteName(record.getSiteName());
                            try {
                                inf.setCountyPinyin(PinyinHelper.convertToPinyinString(inf.getCounty(),
                                        ",", PinyinFormat.WITH_TONE_MARK));
                                inf.setSiteNamePinyin(PinyinHelper.convertToPinyinString(inf.getSiteName(),
                                        ",", PinyinFormat.WITH_TONE_MARK));
                            } catch (PinyinException e) {
                                e.printStackTrace();
                            }
                            inf_list.add(inf);

                            Log.d(TAG, record.getPublishTime() + "\r\n" + record.getSiteName() + " " + record.getCounty() + "\r\n PM25:" + record.getPM25());
                        }
                        Gson gson = new Gson();

                        try {
                            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "test.txt");
                            file.createNewFile();
                            BufferedWriter out = new BufferedWriter(
                                    new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
                            gson.toJson(inf_list, out);
                            out.close();

                            //File file = new File(Environment.getExternalStorageDirectory() + File.separator + "test.txt");
                            //file.createNewFile();
                            //gson.toJson(inf_list, new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8")));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<EpaData> call, Throwable t) {

                    }
                });
    }
}
