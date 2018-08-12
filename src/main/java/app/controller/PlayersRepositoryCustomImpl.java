package app.controller;

import app.model.Mission;
import app.model.MissionProgress;
import app.model.Player;
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
    public long updateMissionProgress(String playerUid, Mission mission, String taskId, Integer taskProgress) throws NullPointerException{

        Query query = new Query(Criteria.where("firebaseId").is(playerUid));
        Update update = new Update();
        //To get Object from DB
        //Player currentPlayer = mongoTemplate.findOne(query, Player.class);
        //currentPlayer.getMissions().get(missionId).getTaskProgress().put(taskId, taskProgress);
        //System.out.println(currentPlayer.get_id());
        //mongoTemplate.save(currentPlayer);
        Player p = mongoTemplate.findOne(query, Player.class);
        MissionProgress missionProgress = p.getMissions().get(mission.get_id());
        missionProgress.getTaskProgress().put(taskId, taskProgress);

        //verify if the number of tasks in the missionProgress is different from the real mission
        if(missionProgress.getTaskProgress().size() != mission.getTaskIDs().size()){
            for(String id : mission.getTaskIDs()){
                if(missionProgress.getTaskProgress().get(id) == null)
                    missionProgress.getTaskProgress().put(id, MissionProgress.TASK_NOT_STARTED);
            }
        }
        missionProgress.getStatus();
        //update.set("missions." + mission + ".taskProgress." + taskId, taskProgress);
        update.set("missions." + mission.get_id(), missionProgress);

        UpdateResult result = mongoTemplate.updateFirst(query, update, Player.class);

        return result.getModifiedCount();
    }

    @Override
    public HashMap<String, Integer> findTasksProgressByMission(String playerUid, String missionId) {
        Query query = new Query(Criteria.where("firebaseId").is(playerUid));
        query.fields().include("missions."+missionId);
        Player p = mongoTemplate.findOne(query, Player.class);
        if(p.getMissions() != null && p.getMissions().size() > 0)
            return p.getMissions().get(missionId).getTaskProgress();
        else return new HashMap<>();
    }

}
