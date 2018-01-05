package thinkers.com.marvin.Screens.Modal;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rajesharumugam on 19-03-2017.
 */

public class Issue {

    @SerializedName("issue_id")
    private String id;
    @SerializedName("issue_title")
    private String title;
    @SerializedName("issue_description")
    private String description;
    @SerializedName("issue_type")
    private String type;
    @SerializedName("issue_level")
    private String level;
    @SerializedName("issue_createdDate")
    private String creadtedDate;
    @SerializedName("issue_updated_date")
    private String updatedDate;

    public Issue(String id, String title, String description, String type, String creadtedDate, String level, String updatedDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.creadtedDate = creadtedDate;
        this.level = level;
        this.updatedDate = updatedDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCreadtedDate() {
        return creadtedDate;
    }

    public void setCreadtedDate(String creadtedDate) {
        this.creadtedDate = creadtedDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }
}
