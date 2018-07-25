package app.controller;

public interface PlayersRepositoryCustom {
    long updateTaskProgress(String playerUid, String missionId, String taskId, Integer taskProgress);

}
