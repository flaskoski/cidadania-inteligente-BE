package app.controller;

import app.model.Player;
import com.mongodb.WriteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.HashMap;
import java.util.Hashtable;

public class PlayersRepositoryCustomImpl implements PlayersRepositoryCustom{

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public long updateTaskProgress(String playerUid, String missionId, String taskId, Integer taskProgress){

        Query query = new Query(Criteria.where("firebaseId").is(playerUid));

        //To get Object from DB
        //Player currentPlayer = mongoTemplate.findOne(query, Player.class);
        //currentPlayer.getMissions().get(missionId).getTaskProgress().put(taskId, taskProgress);
        //System.out.println(currentPlayer.get_id());
        //mongoTemplate.save(currentPlayer);
        Update update = new Update();
        update.set("missions."+missionId+".taskProgress."+taskId, taskProgress);
//
        UpdateResult result = mongoTemplate.updateFirst(query, update, Player.class);

        return result.getModifiedCount();
    }

    @Override
    public HashMap<String, Integer> findTasksProgressByMission(String playerUid, String missionId) {
        Query query = new Query(Criteria.where("firebaseId").is(playerUid));
        query.fields().include("missions."+missionId+".taskProgress");
        Player p = mongoTemplate.findOne(query, Player.class);
        if(p.getMissions().size() > 0)
            return p.getMissions().get(missionId).getTaskProgress();
        else return null;
    }

}
