package tw.gov.epa.taqmpredict.db;

import java.util.List;

/**
 * Created by SilenceDut on 16/10/28.
 */

public class CityEntry {
    /**
     * city_info : [{"adminArea":"台北市","locatlity":"中正區"}]
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
         * lstAddress.get(0).getAdminArea();  //台北市
         * lstAddress.get(0).getLocality();  //中正區
         * lstAddress.get(0).getThoroughfare();  //信陽街(包含路巷弄)
         * lstAddress.get(0).getFeatureName();  //會得到33(號)
         * lstAddress.get(0).getPostalCode();  //會得到100(郵遞區號)
         */
//        private String prov;
        private String country;
        private String adminArea; //County
        private String locatlity; //SiteName
        private String lat;
        private String lon;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getAdminArea() {
            return adminArea;
        }

        public void setAdminArea(String adminArea) {
            this.adminArea = adminArea;
        }

        public String getLocatlity() {
            return locatlity;
        }

        public void setLocatlity(String locatlity) {
            this.locatlity = locatlity;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }


    }
}
