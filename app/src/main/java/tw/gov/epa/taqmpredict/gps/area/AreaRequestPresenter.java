package tw.gov.epa.taqmpredict.gps.area;

import tw.gov.epa.taqmpredict.gps.GPSTrackerService;

/**
 * Created by user on 2017/2/6.
 */

public class AreaRequestPresenter implements IAreaRequestPresenter{
    private final String TAG = AreaRequestPresenter.class.getSimpleName();

//    private IMainView mView;
    private AreaRequestService mAreaRequestService;
    private GPSTrackerService mGpsTrackerService;

    private GPSTrackerService gpsTracker;

    public AreaRequestPresenter(//IMainView view,
                                AreaRequestService areaRequestService,
                                GPSTrackerService gpsTrackerService){
//        mView = view;
        mAreaRequestService = areaRequestService;
        mGpsTrackerService = gpsTrackerService;
    }

    @Override
    public void getArea() {
//        mAreaRequestService.getAreaByHttp();
    }
}
