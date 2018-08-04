package app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import app.firebase.FirebaseValidator;
import app.model.Mission;
import app.model.MissionProgress;
import app.model.Player;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {

    @Autowired
    private PlayersRepository playersRepository;
    @Autowired
    private MissionRepository missionRepository;

    @RequestMapping("/player/allMissionsStatus")
    //public Mission sendTasks(@RequestParam(value="uid", defaultValue="") String idToken) {
    public HashMap<String, MissionProgress> sendAllMissionsStatus(
            @RequestHeader(value="Authorization") String idToken) {//@RequestHeader String idToken
        List<MissionProgress> missionsProgress = new ArrayList<>();
        String playerUid = FirebaseValidator.validateUser(idToken);
        if(playerUid == null)
            return null;
        System.out.println("Retrieving all missions progress");
        Optional<Player> p =playersRepository.findById(playerUid);
        return p.map(Player::getMissions).orElse(null);
    }

    @RequestMapping("/player/missionProgress")
    public MissionProgress sendMissionProgress(
            @RequestBody String[] params,
            @RequestHeader(value="Authorization") String idToken) {//@RequestHeader String idToken
        final MissionProgress missionProgress;
        String playerUid = FirebaseValidator.validateUser(idToken);
        if(playerUid == null)
            return null;
        String missionId;

        missionId = params[0];

        System.out.println("Retrieving tasks progress");
        System.out.println("MissionID:" + missionId);

        HashMap<String, Integer> tasksProgress = playersRepository.findTasksProgressByMission(playerUid, missionId);

        //If mission progress still doesnt exist no DB, create an empty one
        if(tasksProgress.size()==0) {
            Mission mission = missionRepository.findById(missionId).get();
            missionProgress = playersRepository.createMissionProgress(playerUid, mission);
        }
        else{
            missionProgress = new MissionProgress(tasksProgress);
        }
        return missionProgress;
    }

    @RequestMapping("/player")
    //public Mission sendTasks(@RequestParam(value="uid", defaultValue="") String idToken) {
    public Boolean updateMissionProgress(
            @RequestBody String[] params,
            @RequestHeader(value="Authorization") String idToken) {//@RequestHeader String idToken

        String playerUid = FirebaseValidator.validateUser(idToken);
        if(playerUid == null)
            return false;
        String missionId;
        System.out.println("Updating task progress");
        missionId = params[0];
        String taskId= params[1];
        Integer taskprogress = Integer.valueOf(params[2]);
        for(Integer i=1; i<=3; i++){
            System.out.println((i).toString()+":" + params[i-1]);
        }
        try {
            Mission mission = missionRepository.findById(missionId).get();
            playersRepository.updateMissionProgress(playerUid, mission, taskId, taskprogress);
            return true;
        }catch (NullPointerException e){
            return false;
        }

    }

}
