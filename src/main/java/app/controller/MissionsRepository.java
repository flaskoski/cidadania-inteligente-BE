package app.controller;

import app.model.Mission;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

//@Document(collection = "missions")
public interface MissionsRepository extends MongoRepository<Mission, String> {

        public Mission findByMissionName(String missionName);
        //public List<Mission> findByLastName(String lastName);

}
