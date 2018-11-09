package app.controller;

import java.util.*;

import app.firebase.FirebaseValidator;
import app.model.Mission;
import app.model.MissionProgress;
import app.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PlayerController {

    @Autowired
    private PlayersRepository playersRepository;
    @Autowired
    private MissionsRepository missionsRepository;

    /**Get all missions progress
     *
     * @param idToken - Player authentication
     * @return progress from all missions the player has done
     */
    @RequestMapping("/player/allMissionsProgress")
    public HashMap<String, MissionProgress> sendAllMissionsStatus(
            @RequestHeader(value="Authorization") String idToken) {//@RequestHeader String idToken
        String playerUid = FirebaseValidator.validateUser(idToken);
        if(playerUid == null)
            return null;
        System.out.println("Retrieving all missions progress from player...");
        Player player = playersRepository.findByFirebaseId(playerUid);
        if(player == null)
            player = playersRepository.createPlayerProgressDocument(playerUid);
        System.out.println("All missions progress received.");
        return player.getMissions();
    }

    /**Get mission progress from a specific mission
     *
     * @param missionId
     * @param idToken - Player authentication
     * @return the progress of the mission whose id was given as parameter
     */
    @RequestMapping("/player/missionProgress")
    public MissionProgress sendMissionProgress(
            @RequestHeader(value="missionID") String missionId,
            //@RequestBody String[] params,
            @RequestHeader(value="Authorization") String idToken) {//@RequestHeader String idToken
        final MissionProgress missionProgress;
        String playerUid = FirebaseValidator.validateUser(idToken);
        if(playerUid == null || missionId == null)
            return null;

        System.out.println("Retrieving mission progress from ID " + missionId + "...");


        HashMap<String, Integer> tasksProgress = playersRepository.findTasksProgressByMission(playerUid, missionId);

        //If mission progress still doesnt exist no DB, create an empty one
        if(tasksProgress.size()==0) {
            try {
                Mission mission = missionsRepository.findById(missionId).get();
                missionProgress = playersRepository.createMissionProgress(playerUid, mission);
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }
        else{
            missionProgress = new MissionProgress(tasksProgress);
        }
        System.out.println("Mission progress caught.");
        return missionProgress;
    }

    /**Updates a task progress on the DB.
     *
     * @param params - "missionId", "taskId" and "taskProgress" (as String)
     * @param idToken - Player authentication
     * @return true if the update was sucessfull.
     */
    @RequestMapping("/player/updateOne")
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
            playersRepository.updateMissionProgress(playerUid, missionId, taskId, taskprogress);
            return true;
        }catch (NullPointerException e){
            return false;
        }

    }

    /**Updates a task progress on the DB.
     *
     * @param params - "missionId", "taskId" and "taskProgress" (as String)
     * @param idToken - Player authentication
     * @return true if the update was sucessfull.
     */
    @RequestMapping("/player")
    //public Mission sendTasks(@RequestParam(value="uid", defaultValue="") String idToken) {
    public Player sendPlayerInfo(
            @RequestParam Map<String,String> params,
            @RequestHeader(value="Authorization") String idToken) {//@RequestHeader String idToken

        String playerUid = FirebaseValidator.validateUser(idToken);
        if(playerUid == null) {
            System.out.println("Player " + playerUid + " info requested but validation failed!");
            return null;
        }
        System.out.println("Player "+ playerUid + " info requested and OK");

        return playersRepository.getPlayerInfo(playerUid);
    }

}
