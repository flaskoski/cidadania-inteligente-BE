package app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.CountDownLatch;

import app.model.Mission;
import app.model.MissionProgress;
import app.model.Player;
import app.model.QuestionTask;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class PlayerController {

    @Autowired
    private PlayersRepository playersRepository;

    @RequestMapping("/player/taskProgress")
    //public Mission sendTasks(@RequestParam(value="uid", defaultValue="") String idToken) {
    public HashMap<String, Integer> sendTasksProgress(
            @RequestBody String[] params,
            @RequestHeader(value="Authorization") String idToken) {//@RequestHeader String idToken
        final HashMap<String, Integer>[] tasksProgress = new HashMap[]{new HashMap<String, Integer>()};
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

                        tasksProgress[0] = playersRepository.findTasksProgressByMission(playerUid, missionId);
                        //Release thread wait
                        latch.countDown();
                    }
                });
        try {
            latch.await();
        } catch (InterruptedException | NullPointerException e) {
            e.printStackTrace();
        }
        return tasksProgress[0];
    }

    @RequestMapping("/player")
    //public Mission sendTasks(@RequestParam(value="uid", defaultValue="") String idToken) {
    public Boolean updateTaskProgress(
            @RequestBody String[] params,
            @RequestHeader(value="Authorization") String idToken) {//@RequestHeader String idToken
        
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
                        HashMap<String, Integer> tasksProgress = new HashMap<>();
                        System.out.println("Updating task progress");
                        missionId = params[0];
                        String taskId= params[1];
                        Integer taskprogress = Integer.valueOf(params[2]);
                        for(Integer i=1; i<=3; i++){
                            System.out.println((i).toString()+":" + params[i-1]);
                        }

                        playersRepository.updateTaskProgress(playerUid, missionId, taskId, taskprogress);
                        //Release thread wait
                        latch.countDown();
                    }
                });
        try {
            latch.await();
        } catch (InterruptedException | NullPointerException e) {
            e.printStackTrace();
        }
        return true;
    }

}
