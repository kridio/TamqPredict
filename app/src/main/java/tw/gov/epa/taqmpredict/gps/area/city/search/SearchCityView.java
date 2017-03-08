package tw.gov.epa.taqmpredict.gps.area.city.search;

import java.util.List;

import tw.gov.epa.taqmpredict.gps.area.city.model.CityInfoData;

/**
 * Created by user on 2017/3/6.
 */

public interface SearchCityView {
    void onMatched(String key);

    void onAllCities();
}
