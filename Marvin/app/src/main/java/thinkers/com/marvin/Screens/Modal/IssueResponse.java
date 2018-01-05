package thinkers.com.marvin.Screens.Modal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rajesharumugam on 20-03-2017.
 */

public class IssueResponse {

    @SerializedName("result")
    private List<Issue> result;

    public List<Issue> getResult() {
        return result;
    }

    public void setResult(List<Issue> result) {
        this.result = result;
    }
}
