
package tw.gov.epa.taqmpredict.gps.area.city.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CityInfo {
    @SerializedName("cityId")
    private String mCityId;
    @SerializedName("city_info")
    private List<CityInfo> mCityInfo;
    @SerializedName("county")
    private String mCounty;
    @SerializedName("countyPinyin")
    private String mCountyPinyin;
    @SerializedName("siteName")
    private String mSiteName;
    @SerializedName("siteNamePinyin")
    private String mSiteNamePinyin;

    public String getCityId() {
        return mCityId;
    }

    public void setCityId(String cityId) {
        mCityId = cityId;
    }

    public List<CityInfo> getCityInfo() {return mCityInfo;}

    public void setCityInfo(List<CityInfo> cityInfo) {
        mCityInfo = cityInfo;
    }

    public String getCounty() {
        return mCounty;
    }

    public void setCounty(String county) {
        mCounty = county;
    }

    public String getCountyPinyin() {
        return mCountyPinyin;
    }

    public void setCountyPinyin(String countyPinyin) {
        mCountyPinyin = countyPinyin;
    }

    public String getSiteName() {
        return mSiteName;
    }

    public void setSiteName(String siteName) {mSiteName = siteName+"區";}

    public String getSiteNamePinyin() {
        return mSiteNamePinyin;
    }

    public void setSiteNamePinyin(String siteNamePinyin) {
        mSiteNamePinyin = siteNamePinyin;
    }
}
