
package tw.gov.epa.taqmpredict.predict.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("SiteName")
    @Expose
    private String siteName;
    @SerializedName("hr_1")
    @Expose
    private Integer hr1;
    @SerializedName("hr_6")
    @Expose
    private Integer hr6;
    @SerializedName("hr_12")
    @Expose
    private Integer hr12;
    @SerializedName("predict_history")
    @Expose
    private String predictHistory;
    @SerializedName("real_history")
    @Expose
    private String realHistory;
    @SerializedName("Time")
    @Expose
    private String time;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public Integer getHr1() {
        return hr1;
    }

    public void setHr1(Integer hr1) {
        this.hr1 = hr1;
    }

    public Integer getHr6() {
        return hr6;
    }

    public void setHr6(Integer hr6) {
        this.hr6 = hr6;
    }

    public Integer getHr12() {
        return hr12;
    }

    public void setHr12(Integer hr12) {
        this.hr12 = hr12;
    }

    public String getPredictHistory() {
        return predictHistory;
    }

    public void setPredictHistory(String predictHistory) {
        this.predictHistory = predictHistory;
    }

    public String getRealHistory() {
        return realHistory;
    }

    public void setRealHistory(String realHistory) {
        this.realHistory = realHistory;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}