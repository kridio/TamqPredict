package tw.gov.epa.taqmpredict.event;

import tw.gov.epa.taqmpredict.gps.area.city.model.CityInfoData;

/**
 * Created by user on 2017/3/8.
 */

public class ChoiceSiteEvent {
    CityInfoData mCityInfoData;
    public void setCityInfo(CityInfoData cityInfoData){
        mCityInfoData = cityInfoData;
    }
    public CityInfoData getCityInfo(){
        return mCityInfoData;
    }
}
