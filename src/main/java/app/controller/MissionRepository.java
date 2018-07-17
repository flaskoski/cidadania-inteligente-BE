package app.controller;

import app.model.Mission;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface MissionRepository extends MongoRepository<Mission, String> {

        public Mission findByMissionName(String missionName);
        //public List<Mission> findByLastName(String lastName);

}
