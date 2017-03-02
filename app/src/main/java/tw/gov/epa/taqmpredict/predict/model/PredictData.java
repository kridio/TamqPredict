
package tw.gov.epa.taqmpredict.predict.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PredictData {

    @SerializedName("result")
    @Expose
    private List<Result> result = null;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

}
