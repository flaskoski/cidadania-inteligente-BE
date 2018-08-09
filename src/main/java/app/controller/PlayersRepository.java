package app.controller;

import app.model.MissionProgress;
import app.model.Player;
import com.mongodb.WriteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.HashMap;

public interface PlayersRepository extends MongoRepository<Player, String>, PlayersRepositoryCustom {

    //   public QuestionTask findByTitle(String title);
    //public List<Mission> findByLastName(String lastName);
    Player findByFirebaseId(String playerUid);
    //Check https://www.mkyong.com/spring-data/spring-data-add-custom-method-to-repository/
}
