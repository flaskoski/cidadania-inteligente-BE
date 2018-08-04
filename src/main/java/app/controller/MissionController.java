package app.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

import app.firebase.FirebaseValidator;
import app.model.Mission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MissionController {

    @Autowired
    private MissionRepository missionRepository;

    @RequestMapping("/myMissions")
    //public Mission sendMissions(@RequestParam(value="uid", defaultValue="") String idToken) {
    public ArrayList<Mission> sendMissions(@RequestHeader(value="Authorization") String idToken) {//@RequestHeader String idToken
        final ArrayList<Mission> missions = new ArrayList<>();
        if(idToken != null)
            System.out.println("token caught!");
        if(FirebaseValidator.validateUser(idToken) == null)
            return null;

        for(Mission m : missionRepository.findAll()) {
            missions.add(m);
            System.out.println("id: "+ m.get_id());
        }

        System.out.println(missions.size());
        return missions;
    }

}
