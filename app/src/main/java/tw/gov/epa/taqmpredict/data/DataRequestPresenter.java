package tw.gov.epa.taqmpredict.data;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tw.gov.epa.taqmpredict.IMainView;
import tw.gov.epa.taqmpredict.data.model.EpaData;
import tw.gov.epa.taqmpredict.data.model.Record;
import tw.gov.epa.taqmpredict.data.model.Result;

/**
 * Created by user on 2017/2/6.
 */

public class DataRequestPresenter implements IDataRequestPresenter {
    private final String TAG = DataRequestPresenter.class.getSimpleName();

    private IMainView mView;
    private DataRequestService mDataRequestService;

    private boolean isCache = false;
    private DataCache<String,String> dataCache;

    public DataRequestPresenter(IMainView view,DataRequestService dataRequestService){
        mView = view;
        mDataRequestService = dataRequestService;
        dataCache = new DataCache<String, String>();
    }

    public void getEpaData(Map params){
        mDataRequestService
                .setParams(params)
                .getEpaData()
                .enqueue(new Callback<EpaData>() {
                    @Override
                    public void onResponse(Call<EpaData> call, Response<EpaData> response) {
                        Result response_result = response.body().getResult();
                        for (Record record : response_result.getRecords()) {
                            Log.d(TAG, record.getPublishTime() + "\r\n" + record.getSiteName() + " " + record.getCounty() + "\r\n PM25:" + record.getPM25());
                        }
                    }

                    @Override
                    public void onFailure(Call<EpaData> call, Throwable t) {

                    }
                });
    }
}
