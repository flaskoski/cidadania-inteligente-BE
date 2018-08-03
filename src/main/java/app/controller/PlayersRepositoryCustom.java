package app.controller;

import app.model.Mission;
import app.model.MissionProgress;

import java.util.HashMap;

public interface PlayersRepositoryCustom {
    MissionProgress createMissionProgress(String playerUid, Mission mission) throws NullPointerException;

    long updateMissionProgress(String playerUid, Mission missionId, String taskId, Integer taskProgress);
    HashMap<String, Integer> findTasksProgressByMission(String playerUid, String missionId);

    //HashMap<String, Integer> findTasksProgressByMission(String playerUid, Mission mission);
}
