package app.model;

import java.util.HashMap;

public class MissionProgress {
    //CONSTANTS
    public static final Integer TASK_COMPLETED = 100;
    public static final Integer TASK_NOT_STARTED = 0;
    public static final Integer TASK_FAILED = -1;
    public static final Integer MISSION_NOT_STARTED = 0;
    public static final Integer MISSION_FINISHED = 1;
    public static final Integer MISSION_IN_PROGRESS = 2;


    //METHODS
    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    public HashMap<String, Integer> getTaskProgress() {
        return taskProgress;
    }

    public void setTaskProgress(HashMap<String, Integer> taskProgress) {
        this.taskProgress = taskProgress;
        updateStatus();
    }

    public Integer getStatus() {
        return status;
    }

//    public void setStatus(Integer status) {
//        this.status = status;
//    }

    public MissionProgress(String missionId, HashMap<String, Integer> taskProgress){
        this.missionId = missionId;
        this.taskProgress = taskProgress;
        updateStatus();
    }

    private void updateStatus() {
        if(! this.taskProgress.containsValue(TASK_NOT_STARTED)){
            status = MISSION_FINISHED;
        }else if(this.taskProgress.containsValue(TASK_COMPLETED)){
            status = MISSION_IN_PROGRESS;
        }else status = MISSION_NOT_STARTED;
    }

    //ATTRIBUTES
    private String missionId;
    private HashMap<String, Integer> taskProgress;
    private Integer status;

}
