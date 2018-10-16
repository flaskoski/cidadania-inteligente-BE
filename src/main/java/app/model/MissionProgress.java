package app.model;

import org.springframework.data.annotation.Id;

import java.util.HashMap;
import java.util.List;

public class MissionProgress {
    //CONSTANTS
    public static final Integer TASK_COMPLETED = 100;
    public static final Integer TASK_NOT_STARTED = 0;
    public static final Integer TASK_FAILED = -1;
    public static final Integer MISSION_NOT_STARTED = 0;
    public static final Integer MISSION_FINISHED = 1;
    public static final Integer MISSION_IN_PROGRESS = 2;


    //METHODS
    public String getProgressId() {
        return progressId;
    }

    public void setProgressId(String progressId) {
        this.progressId = progressId;
    }

    public HashMap<String, Integer> getTaskProgress() {
        return taskProgress;
    }

    public void setTaskProgress(HashMap<String, Integer> taskProgress) {
        this.taskProgress = taskProgress;
        updateStatus();
    }

    public Integer getStatus() {
        updateStatus();
        return status;
    }

//    public void setStatus(Integer status) {
//        this.status = status;
//    }
    public MissionProgress(){}
    public MissionProgress(HashMap<String, Integer> taskProgress){
        this.taskProgress = taskProgress;
        if(taskProgress!= null)
            if(taskProgress.size() > 0)
                updateStatus();
    }

    private void setStatus(Integer status){
        this.status =status;
    }
    private void updateStatus() {
        if(! this.taskProgress.containsValue(TASK_NOT_STARTED)){
            status = MISSION_FINISHED;
        }else if(this.taskProgress.containsValue(TASK_COMPLETED)||
                this.taskProgress.containsValue(TASK_FAILED) ){
            status = MISSION_IN_PROGRESS;
        }else status = MISSION_NOT_STARTED;
    }

    //ATTRIBUTES
    @Id
    private String progressId;
    private HashMap<String, Integer> taskProgress;
    private Integer status;
    private Integer xp;

    public Integer getXp() {
        return xp;
    }

    public void setXp(Integer xp) {
        this.xp = xp;
    }

    public MissionProgress(List<String> taskIds) {
        taskProgress = new HashMap<>();
        for(String taskId : taskIds)
            this.taskProgress.put(taskId, TASK_NOT_STARTED);
        setStatus(MISSION_NOT_STARTED);
    }
}
