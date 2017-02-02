
package tw.gov.epa.taqmpredict.data.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record {

    @SerializedName("SiteName")
    @Expose
    private String siteName;
    @SerializedName("County")
    @Expose
    private String county;
    @SerializedName("AQI")
    @Expose
    private String aQI;
    @SerializedName("Pollutant")
    @Expose
    private String pollutant;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("SO2")
    @Expose
    private String sO2;
    @SerializedName("CO")
    @Expose
    private String cO;
    @SerializedName("CO_8hr")
    @Expose
    private String cO8hr;
    @SerializedName("O3")
    @Expose
    private String o3;
    @SerializedName("O3_8hr")
    @Expose
    private String o38hr;
    @SerializedName("PM10")
    @Expose
    private String pM10;
    @SerializedName("PM2.5")
    @Expose
    private String pM25;
    @SerializedName("NO2")
    @Expose
    private String nO2;
    @SerializedName("NOx")
    @Expose
    private String nOx;
    @SerializedName("NO")
    @Expose
    private String nO;
    @SerializedName("WindSpeed")
    @Expose
    private String windSpeed;
    @SerializedName("WindDirec")
    @Expose
    private String windDirec;
    @SerializedName("PublishTime")
    @Expose
    private String publishTime;
    @SerializedName("PM2.5_AVG")
    @Expose
    private String pM25AVG;
    @SerializedName("PM10_AVG")
    @Expose
    private String pM10AVG;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getAQI() {
        return aQI;
    }

    public void setAQI(String aQI) {
        this.aQI = aQI;
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

    public String getSO2() {
        return sO2;
    }

    public void setSO2(String sO2) {
        this.sO2 = sO2;
    }

    public String getCO() {
        return cO;
    }

    public void setCO(String cO) {
        this.cO = cO;
    }

    public String getCO8hr() {
        return cO8hr;
    }

    public void setCO8hr(String cO8hr) {
        this.cO8hr = cO8hr;
    }

    public String getO3() {
        return o3;
    }

    public void setO3(String o3) {
        this.o3 = o3;
    }

    public String getO38hr() {
        return o38hr;
    }

    public void setO38hr(String o38hr) {
        this.o38hr = o38hr;
    }

    public String getPM10() {
        return pM10;
    }

    public void setPM10(String pM10) {
        this.pM10 = pM10;
    }

    public String getPM25() {
        return pM25;
    }

    public void setPM25(String pM25) {
        this.pM25 = pM25;
    }

    public String getNO2() {
        return nO2;
    }

    public void setNO2(String nO2) {
        this.nO2 = nO2;
    }

    public String getNOx() {
        return nOx;
    }

    public void setNOx(String nOx) {
        this.nOx = nOx;
    }

    public String getNO() {
        return nO;
    }

    public void setNO(String nO) {
        this.nO = nO;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirec() {
        return windDirec;
    }

    public void setWindDirec(String windDirec) {
        this.windDirec = windDirec;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getPM25AVG() {
        return pM25AVG;
    }

    public void setPM25AVG(String pM25AVG) {
        this.pM25AVG = pM25AVG;
    }

    public String getPM10AVG() {
        return pM10AVG;
    }

    public void setPM10AVG(String pM10AVG) {
        this.pM10AVG = pM10AVG;
    }

}
