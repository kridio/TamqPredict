package tw.gov.epa.taqmpredict.gps.area.city.model;

import tw.gov.epa.taqmpredict.base.Constants;

public class CityInfoData {

    private String mInitial = Constants.DEFAULT_STR;
    private String mCityId;
    private String mCounty;
    private String mCountyPinyin;
    private String mSiteName;
    private String mSiteNamePinyin;

    public CityInfoData(String cityId,String county, String countyPinyin, String siteName ,String siteName_pinyin) {
        this.mCityId = cityId;
        this.mCounty = county;
        this.mCountyPinyin = countyPinyin;
        this.mSiteName = siteName;
        this.mSiteNamePinyin = siteName_pinyin;
    }

    public String getInitial() {
        return mInitial;
    }

    public void setInitial(String initial) {
        this.mInitial = initial;
    }

    public String getCityId() {
        return mCityId;
    }

    public void setCityId(String cityId) {
        mCityId = cityId;
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

    public void setSiteName(String siteName) {mSiteName = siteName;}

    public String getSiteNamePinyin() {
        return mSiteNamePinyin;
    }

    public void setSiteNamePinyin(String siteNamePinyin) {
        mSiteNamePinyin = siteNamePinyin;
    }
}