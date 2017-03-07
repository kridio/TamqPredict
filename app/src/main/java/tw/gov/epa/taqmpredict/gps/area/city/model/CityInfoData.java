package tw.gov.epa.taqmpredict.gps.area.city.model;

import tw.gov.epa.taqmpredict.base.Constants;

public class CityInfoData {

    private String mInitial = Constants.DEFAULT_STR;
    private String mCountyId;
    private String mCityName;
    private String mCityNamePinYin;
    private String mCounty;
    private String mCountyPinyin;
    private String mSiteName;
    private String mSiteNamePinyin;

    public CityInfoData(String countyId,String cityName,String cityNamePinYin,
                        String county, String countyPinyin, String siteName ,String siteName_pinyin) {
        this.mCountyId = countyId;
        this.mCityName = cityName;
        this.mCityNamePinYin = cityNamePinYin;
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
        return mCountyId;
    }

    public void setCityId(String cityId) {
        mCountyId = cityId;
    }

    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String mCityName) {
        this.mCityName = mCityName;
    }

    public String getCityNamePinYin() {
        return mCityNamePinYin;
    }

    public void setCityNamePinYin(String mCityNamePinYin) {
        this.mCityNamePinYin = mCityNamePinYin;
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
