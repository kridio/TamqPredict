package tw.gov.epa.taqmpredict.db;

import java.util.List;

/**
 * Created by SilenceDut on 16/10/28.
 */

public class CityEntry {
    /**
     * city_info : [{"countyId":"4501(行政區代碼)","county":"花蓮市","SiteName":"花蓮"}]
     */

    private List<CityInfoEntity> city_info;

    public void setCity_info(List<CityInfoEntity> city_info) {
        this.city_info = city_info;
    }

    public List<CityInfoEntity> getCity_info() {
        return city_info;
    }

    public static class CityInfoEntity {
        /**
         * lstAddress.get(0).getCountryName();  //台灣
         * lstAddress.get(0).getCounty();  //台北市
         * lstAddress.get(0).getLocality();  //中正區
         * lstAddress.get(0).getThoroughfare();  //信陽街(包含路巷弄)
         * lstAddress.get(0).getFeatureName();  //會得到33(號)
         * lstAddress.get(0).getPostalCode();  //會得到100(郵遞區號)
         */
        private String countyId; //行政區代碼
        private String county; //鄉鎮市區
        private String cityName; //縣市
        private String siteName; //測站名稱

        public String getCountyId() {return countyId;}

        public void setCountyId(String countyId) {this.countyId = countyId;}

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getSiteName() {return siteName;}

        public void setSiteName(String siteName) {this.siteName = siteName;}

//        public String getLat() {
//            return lat;
//        }
//
//        public void setLat(String lat) {
//            this.lat = lat;
//        }
//
//        public String getLon() {
//            return lon;
//        }
//
//        public void setLon(String lon) {
//            this.lon = lon;
//        }
    }
}
