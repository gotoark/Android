package thinkers.com.marvin.Screens.Modal.Cricket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.security.Provider;
import java.util.List;

/**
 * Created by rajesharumugam on 17-06-2017.
 */

public class MatchResponse {
    @SerializedName("matches")
@Expose
private List<Match> matches = null;
    @SerializedName("v")
    @Expose
    private String v;
    @SerializedName("ttl")
    @Expose
    private Integer ttl;
    @SerializedName("provider")
    @Expose
    private Provider provider;
    @SerializedName("creditsLeft")
    @Expose
    private Integer creditsLeft;

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public Integer getTtl() {
        return ttl;
    }

    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Integer getCreditsLeft() {
        return creditsLeft;
    }

    public void setCreditsLeft(Integer creditsLeft) {
        this.creditsLeft = creditsLeft;
    }

}