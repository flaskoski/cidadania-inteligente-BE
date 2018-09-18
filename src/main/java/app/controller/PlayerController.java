package app.controller;

import java.util.*;
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
import org.springframework.web.bind.annotation.*;

@RestController
public class PlayerController {

    @Autowired
    private PlayersRepository playersRepository;
    @Autowired
    private MissionRepository missionRepository;

    @RequestMapping("/player/allMissionsProgress")
    public HashMap<String, MissionProgress> sendAllMissionsStatus(
            @RequestHeader(value="Authorization") String idToken) {//@RequestHeader String idToken
        String playerUid = FirebaseValidator.validateUser(idToken);
        if(playerUid == null)
            return null;
        System.out.println("Retrieving all missions progress from player");
        HashMap<String, MissionProgress> missionsProgress = playersRepository.findByFirebaseId(playerUid).getMissions();
        return missionsProgress;
    }

    @RequestMapping("/player/missionProgress")
    public MissionProgress sendMissionProgress(
            @RequestHeader(value="missionID") String missionId,
            //@RequestBody String[] params,
            @RequestHeader(value="Authorization") String idToken) {//@RequestHeader String idToken
        final MissionProgress missionProgress;
        String playerUid = FirebaseValidator.validateUser(idToken);
        if(playerUid == null)
            return null;
        //String missionId;

        //missionId = params[0];

        System.out.println("Retrieving mission progress");


        HashMap<String, Integer> tasksProgress = playersRepository.findTasksProgressByMission(playerUid, missionId);

        //If mission progress still doesnt exist no DB, create an empty one
        if(tasksProgress.size()==0) {
            try {
                Mission mission = missionRepository.findById(missionId).get();
                missionProgress = playersRepository.createMissionProgress(playerUid, mission);
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }
        else{
            missionProgress = new MissionProgress(tasksProgress);
        }
        System.out.println("MissionID:" + missionId);
        return missionProgress;
    }

    @RequestMapping("/player")
    //public Mission sendTasks(@RequestParam(value="uid", defaultValue="") String idToken) {
    public Boolean updateMissionProgress(
            @RequestParam Map<String,String> params,
            @RequestHeader(value="Authorization") String idToken) {//@RequestHeader String idToken

        String playerUid = FirebaseValidator.validateUser(idToken);
        if(playerUid == null)
            return false;
        String missionId;
        System.out.println("Updating task progress");
        missionId = params.get("missionId");
        String taskId = params.get("taskId");
        Integer taskprogress = Integer.valueOf(params.get("taskProgress"));
//        String taskId= params[1];
//        Integer taskprogress = Integer.valueOf(params[2]);
        //for(Integer i=1; i<=3; i++){
            System.out.println("Update missionId:"+missionId+"taskProgress: "+taskprogress.toString()    );
        //}
        try {
            Mission mission = missionRepository.findById(missionId).get();
            playersRepository.updateMissionProgress(playerUid, mission, taskId, taskprogress);
            return true;
        }catch (NullPointerException e){
            return false;
        }

    }

}
