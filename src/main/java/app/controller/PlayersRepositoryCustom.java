package app.controller;

import java.util.HashMap;

public interface PlayersRepositoryCustom {
    long updateTaskProgress(String playerUid, String missionId, String taskId, Integer taskProgress);
    HashMap<String, Integer> findTasksProgressByMission(String playerUid, String missionId);

}
