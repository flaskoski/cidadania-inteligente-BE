package app.controller;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

import app.model.Mission;
import app.model.MissionProgress;
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
    public MissionProgress sendAllMissionsStatus(
            @RequestBody String[] params,
            @RequestHeader(value="Authorization") String idToken) {//@RequestHeader String idToken
        final MissionProgress[] missionProgress = new MissionProgress[1];
        CountDownLatch latch = new CountDownLatch(1);
        ApiFutures.addCallback(FirebaseAuth.getInstance().verifyIdTokenAsync(idToken),
                new ApiFutureCallback<FirebaseToken>() {
                    @Override
                    public void onFailure(Throwable t) {
                        System.out.println("failure");
                        latch.countDown();
                    }
                    @Override
                    public void onSuccess(FirebaseToken decodedToken) {
                        System.out.println(" Token is valid.");
                        String playerUid = decodedToken.getUid();
                        String missionId;

                        missionId = params[0];

                        System.out.println("Retrieving tasks progress");
                        System.out.println("MissionID:" + missionId);

                       // missionProgress[0] = new MissionProgress(playersRepository.findTasksProgressByMission(playerUid, missionId));
                        //Release thread wait
                        latch.countDown();
                    }
                });
        try {
            latch.await();
        } catch (InterruptedException | NullPointerException e) {
            e.printStackTrace();
        }
        return missionProgress[0];
    }

    @RequestMapping("/player/missionProgress")
    public MissionProgress sendMissionProgress(
            @RequestBody String[] params,
            @RequestHeader(value="Authorization") String idToken) {//@RequestHeader String idToken
        final MissionProgress[] missionProgress = new MissionProgress[1];
        CountDownLatch latch = new CountDownLatch(1);
        ApiFutures.addCallback(FirebaseAuth.getInstance().verifyIdTokenAsync(idToken),
                new ApiFutureCallback<FirebaseToken>() {
                    @Override
                    public void onFailure(Throwable t) {
                        System.out.println("failure");
                        latch.countDown();
                    }
                    @Override
                    public void onSuccess(FirebaseToken decodedToken) {
                        //TODO tirar todo o c[odigo daqui de dentro
                        System.out.println(" Token is valid.");
                        String playerUid = decodedToken.getUid();
                        String missionId;

                        missionId = params[0];

                        System.out.println("Retrieving tasks progress");
                        System.out.println("MissionID:" + missionId);

                        HashMap<String, Integer> tasksProgress = playersRepository.findTasksProgressByMission(playerUid, missionId);

                        //If mission progress still doesnt exist no DB, create an empty one
                        if(tasksProgress.size()==0) {
                            Mission mission = missionRepository.findById(missionId).get();
                            missionProgress[0] = playersRepository.createMissionProgress(playerUid, mission);
                        }
                        else{
                            missionProgress[0] = new MissionProgress(tasksProgress);
                        }
                        //Release thread wait
                        latch.countDown();
                    }
                });
        try {
            latch.await();
        } catch (InterruptedException | NullPointerException e) {
            e.printStackTrace();
        }
        return missionProgress[0];
    }

    @RequestMapping("/player")
    //public Mission sendTasks(@RequestParam(value="uid", defaultValue="") String idToken) {
    public Boolean updateMissionProgress(
            @RequestBody String[] params,
            @RequestHeader(value="Authorization") String idToken) {//@RequestHeader String idToken
        final Boolean[] updateSuccessful = {false};
        CountDownLatch latch = new CountDownLatch(1);
        ApiFutures.addCallback(FirebaseAuth.getInstance().verifyIdTokenAsync(idToken),
                new ApiFutureCallback<FirebaseToken>() {
                    @Override
                    public void onFailure(Throwable t) {
                        System.out.println("failure");
                        latch.countDown();
                    }
                    @Override
                    public void onSuccess(FirebaseToken decodedToken) {
                        System.out.println(" Token is valid.");
                        String playerUid = decodedToken.getUid();
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
                            updateSuccessful[0] = true;
                        }catch (NullPointerException e){}
                        //Release thread wait
                        latch.countDown();
                    }
                });
        try {
            latch.await();
        } catch (InterruptedException | NullPointerException e) {
            e.printStackTrace();
        }
        return updateSuccessful[0];
    }

}
