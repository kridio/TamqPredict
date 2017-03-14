package tw.gov.epa.taqmpredict.gps.area;

import android.content.Context;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import tw.gov.epa.taqmpredict.base.Constants;
import tw.gov.epa.taqmpredict.gps.GPSTrackerService;
import tw.gov.epa.taqmpredict.util.PreferencesUtil;

/**
 * Created by user on 2017/2/6.
 */

public class AreaRequestPresenter implements IAreaRequestPresenter{
    private final String TAG = AreaRequestPresenter.class.getSimpleName();

//    private IMainView mView;
    private AreaRequestService mAreaRequestService;
    private GPSTrackerService mGpsTrackerService;
    private Context mContext;

    Observer<String> mObserver = new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(String s) {
            String[] lat_lng = s.split(",");
            getArea(Double.parseDouble(lat_lng[0]),Double.parseDouble(lat_lng[1]));
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };


    Observer<String> hfObserver;

    public AreaRequestPresenter(Context context,Observer observer){
        mContext = context;
        mAreaRequestService = new AreaRequestService(mContext,this);
        mGpsTrackerService = new GPSTrackerService(mContext,mObserver);
        hfObserver = observer;
    }

    public void start(){
        mGpsTrackerService.startLocation();
    }

    public void stop(){
        mGpsTrackerService.stopLocation();
    }

    @Override
    public void getArea(double lat,double lng) {
//        String area = mAreaRequestService.getAreaByGoogleApi(lat,lng);
//        sendArea(area);
        mAreaRequestService.getAreaByHttp(lat,lng);
    }

    public void sendArea(String area){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext(area);
                e.onComplete();
            }
        }).subscribe(hfObserver);
    }
}
