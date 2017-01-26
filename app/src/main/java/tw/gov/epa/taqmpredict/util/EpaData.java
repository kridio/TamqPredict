package tw.gov.epa.taqmpredict.util;

import java.util.Date;

/**
 * Created by user on 2017/1/24.
 */

public class EpaData {
    public static String SITENAME = "SiteName";
    public static String COUNTY = "County";
    public static String POLLUTANT = "Pollutant";
    public static String STATUS = "Status";
    public static String PUBLISH_TIME = "PublishTime";
    public static String AQI = "AQI";
    public static String PM2DOT5 = "PM2.5";
    public static String PM10 = "PM10";
    public static String O3 = "O3";

    private String siteName;
    private String country;
    private String pollutant;
    private String status;
    private Date publishTime;
    private int aqi;
    private int pm2dot5;
    private int pm10;
    private int o3;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPollutant() {
        return pollutant;
    }

    public void setPollutant(String pollutant) {
        this.pollutant = pollutant;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public int getAqi() {
        return aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    public int getPm2dot5() {
        return pm2dot5;
    }

    public void setPm2dot5(int pm2dot5) {
        this.pm2dot5 = pm2dot5;
    }

    public int getPm10() {
        return pm10;
    }

    public void setPm10(int pm10) {
        this.pm10 = pm10;
    }

    public int getO3() {
        return o3;
    }

    public void setO3(int o3) {
        this.o3 = o3;
    }
}
