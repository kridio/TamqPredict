
package tw.gov.epa.taqmpredict.gps.area.model;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AreaData implements Parcelable
{

    @SerializedName("results")
    @Expose
    private List<Result> results = null;
    @SerializedName("status")
    @Expose
    private String status;
    public final static Creator<AreaData> CREATOR = new Creator<AreaData>() {


        @SuppressWarnings({
            "unchecked"
        })
        public AreaData createFromParcel(Parcel in) {
            AreaData instance = new AreaData();
            in.readList(instance.results, (Result.class.getClassLoader()));
            instance.status = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public AreaData[] newArray(int size) {
            return (new AreaData[size]);
        }

    }
    ;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(results);
        dest.writeValue(status);
    }

    public int describeContents() {
        return  0;
    }

}
