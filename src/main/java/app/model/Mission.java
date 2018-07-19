package app.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;

public class Mission implements Serializable {
    private String missionName;
    private Integer missionIconId;
    private List<String> taskIDs;

    public List<String> getTaskIDs() {
        return taskIDs;
    }

    public void setTaskIDs(List<String> taskIDs) {
        this.taskIDs = taskIDs;
    }

    private String description;
    private static final Integer NO_IMAGE_PROVIDED = -1;

    @Id
    private String _id;


    public Mission(String missionName, String description, Integer missionIconId) {
        this.missionName = missionName;
        this.missionIconId = missionIconId;
        this.description = description;
    }

    public Mission(String missionName, String description) {
        this.missionName = missionName;
        this.description = description;
        this.missionIconId = NO_IMAGE_PROVIDED;
    }

    public Mission(){}

    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public int getMissionIconId() {
        return missionIconId;
    }

    public void setMissionIconId(Integer missionIcon) {
        this.missionIconId = missionIcon;
    }

    public boolean hasImage(){
        return this.getMissionIconId() != NO_IMAGE_PROVIDED;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
