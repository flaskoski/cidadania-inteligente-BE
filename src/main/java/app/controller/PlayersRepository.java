package app.controller;

import app.model.Player;
import com.mongodb.WriteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayersRepository extends MongoRepository<Player, String>, PlayersRepositoryCustom {

    //   public QuestionTask findByTitle(String title);
        //public List<Mission> findByLastName(String lastName);

    //TODO put all that below on an implementation of another interface. Check https://www.mkyong.com/spring-data/spring-data-add-custom-method-to-repository/
    // @Autowired
//     MongoTemplate mongoTemplate;
//
//    public int updateDomainDisplayFlagOnly(String domain, boolean flag) {
//
//        Query query = new Query(Criteria.where("_id").is(domain));
//        Update update = new Update();
//        update.set("display", flag);
//
//        UpdateResult result = mongoTemplate.updateFirst(query, update, Player.class);
//
//        if(result!=null)
//            return (int) result.getMatchedCount();
//        else
//            return 0;
//
//    }
}
