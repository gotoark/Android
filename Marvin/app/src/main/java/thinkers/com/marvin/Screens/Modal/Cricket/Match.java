package thinkers.com.marvin.Screens.Modal.Cricket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rajesharumugam on 17-06-2017.
 */

public class Match {

    @SerializedName("unique_id")
    @Expose
    private Integer uniqueId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("squad")
    @Expose
    private Boolean squad;
    @SerializedName("team-2")
    @Expose
    private String team2;
    @SerializedName("team-1")
    @Expose
    private String team1;
    @SerializedName("matchStarted")
    @Expose
    private Boolean matchStarted;

    public Integer getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Integer uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getSquad() {
        return squad;
    }

    public void setSquad(Boolean squad) {
        this.squad = squad;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public Boolean getMatchStarted() {
        return matchStarted;
    }

    public void setMatchStarted(Boolean matchStarted) {
        this.matchStarted = matchStarted;
    }

}