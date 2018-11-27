package app.controller;

import app.model.Mission;
import app.model.MissionProgress;
import app.model.Player;
import app.model.AbstractTask;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.HashMap;

public class PlayersRepositoryCustomImpl implements PlayersRepositoryCustom{

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    private MissionsRepository missionsRepository;
    @Autowired
    private TasksRepository tasksRepository;

    @Override
    public Player createPlayerProgressDocument(String playerUid){
        Player p = new Player(playerUid);
        mongoTemplate.insert(p);
        return p;
    }

    @Override
    public MissionProgress createMissionProgress(String playerUid, Mission mission){
        Query query = new Query(Criteria.where("firebaseId").is(playerUid));
        Update update = new Update();
        MissionProgress missionProgress = new MissionProgress(mission.getTaskIDs());
        //To get Object from DB
        //Player currentPlayer = mongoTemplate.findOne(query, Player.class);
        //currentPlayer.getMissions().get(missionId).getTaskProgress().put(taskId, taskProgress);
        //System.out.println(currentPlayer.get_id());
        //mongoTemplate.save(currentPlayer);
        //update.set("missions." + mission + ".taskProgress." + taskId, taskProgress);
        update.set("missions." + mission.get_id(), missionProgress);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Player.class);

        return missionProgress;
    }

    @Override
    public long updateMissionProgress(String playerUid, String missionId, String taskId, Integer taskProgress) throws NullPointerException{

        //To get Object from DB
        //Player currentPlayer = mongoTemplate.findOne(query, Player.class);
        //currentPlayer.getMissions().get(missionId).getTaskProgress().put(taskId, taskProgress);
        //System.out.println(currentPlayer.get_id());
        //mongoTemplate.save(currentPlayer);

        Mission mission = missionsRepository.findById(missionId).get();
        AbstractTask task = tasksRepository.findById(taskId).get();

        //find player progress
        Query query = new Query(Criteria.where("firebaseId").is(playerUid));
        Update update = new Update();
        Player p = mongoTemplate.findOne(query, Player.class);
        MissionProgress missionProgress = p.getMissions().get(mission.get_id());

        //set progress
        missionProgress.setOneTaskProgress(taskId, taskProgress);

        //verify if the number of tasks in the missionProgress is different from the real mission
        if(missionProgress.getTaskProgress().size() != mission.getTaskIDs().size()){
            for(String id : mission.getTaskIDs()){
                if(missionProgress.getTaskProgress().get(id) == null)
                    missionProgress.setOneTaskProgress(id, MissionProgress.TASK_NOT_STARTED);
            }
        }

        updatePlayerXp(p, mission.getXp(), task.getXp(),  missionProgress.getStatus(), taskProgress);
        //update.set("missions." + mission + ".taskProgress." + taskId, taskProgress);
        update.set("missions." + mission.get_id(), missionProgress);
        update.set("xp", p.getXp());

        UpdateResult result = mongoTemplate.updateFirst(query, update, Player.class);

        return result.getModifiedCount();
    }

    private void updatePlayerXp(Player p, Integer missionXp, Integer taskXp, Integer missionStatus, Integer taskProgress) {
        if( taskProgress == MissionProgress.TASK_COMPLETED )
            p.addXp(taskXp);
        if( missionStatus == MissionProgress.MISSION_FINISHED )
            p.addXp(missionXp);
        //return p;
    }

    @Override
    public HashMap<String, Integer> findTasksProgressByMission(String playerUid, String missionId) {
        Query query = new Query(Criteria.where("firebaseId").is(playerUid));
        query.fields().include("missions."+missionId);
        Player p = mongoTemplate.findOne(query, Player.class);
        if(p == null) //Player not in database
            p = createPlayerProgressDocument(playerUid);
        if(p.getMissions() != null && p.getMissions().size() > 0)
            return p.getMissions().get(missionId).getTaskProgress();
        else return new HashMap<>();
    }

    @Override
    public Player getPlayerInfo(String playerUid) {
        Query query = new Query(Criteria.where("firebaseId").is(playerUid));
        query.fields().include("xp");
        query.fields().include("level");
        Player p = mongoTemplate.findOne(query, Player.class);
        if(p == null) //Player not in database
            p = createPlayerProgressDocument(playerUid);
        return p;
    }


}
