package app.controller;

import java.util.ArrayList;

import app.firebase.FirebaseValidator;
import app.model.Mission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MissionController {

    @Autowired
    private MissionsRepository missionsRepository;

    /**
     *
     * @param idToken - Player authentication
     * @return List of all missions from the database. Null if any error (validation, parameter, database) occurs
     */
    @RequestMapping("/myMissions")
    //public Mission sendMissions(@RequestParam(value="uid", defaultValue="") String idToken) {
    public ArrayList<Mission> sendMissions(@RequestHeader(value="Authorization") String idToken) {//@RequestHeader String idToken
        final ArrayList<Mission> missions = new ArrayList<>();
        if(idToken == null) return null;
//        if(idToken != null)
//            System.out.println("token caught!");
        if(FirebaseValidator.validateUser(idToken) == null)
            return null;
        System.out.println("Retrieving all missions from DB...");
        for(Mission m : missionsRepository.findAll()) {
            missions.add(m);
            System.out.println("Mission id: "+ m.get_id());
        }
        return missions;
    }

}
