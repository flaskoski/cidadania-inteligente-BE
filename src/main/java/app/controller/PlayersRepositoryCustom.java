package app.controller;

import app.model.Mission;
import app.model.MissionProgress;
import app.model.Player;

import java.util.HashMap;

public interface PlayersRepositoryCustom {
    Player createPlayerProgressDocument(String playerUid);

    MissionProgress createMissionProgress(String playerUid, Mission mission) throws NullPointerException;

    long updateMissionProgress(String playerUid, String missionId, String taskId, Integer taskProgress);
    HashMap<String, Integer> findTasksProgressByMission(String playerUid, String missionId);

    //HashMap<String, Integer> findTasksProgressByMission(String playerUid, Mission mission);
}
