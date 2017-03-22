package tw.gov.epa.taqmpredict.ui.fragment.home;

/**
 * Created by user on 2017/3/15.
 */

public class SiteListData {
    public String cityHead="------";
    public String siteName;
    public int pm25_value;
    public boolean isSelected=false;
    public int order=-1;

    public String getCityHead() {
        return cityHead;
    }

    public void setCityHead(String cityHead) {
        this.cityHead = cityHead;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public int getPm25_value() {
        return pm25_value;
    }

    public void setPm25_value(int pm25_value) {
        this.pm25_value = pm25_value;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
