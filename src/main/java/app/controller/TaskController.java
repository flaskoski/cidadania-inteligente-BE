package app.controller;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import app.model.QuestionTask;
import app.model.Task;
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
public class TaskController {

    @Autowired
    private TasksRepository tasksRepository;

    @RequestMapping("/tasks")
    //public Mission sendTasks(@RequestParam(value="uid", defaultValue="") String idToken) {
    public ArrayList<QuestionTask> sendTasks(/*@RequestParam(value="missionID", defaultValue="") String missionID, */
            @RequestBody ArrayList<String> taskIds,
            @RequestHeader(value="Authorization") String idToken) {//@RequestHeader String idToken
        final ArrayList<QuestionTask> tasks= new ArrayList<>();

        if(idToken != null)
            System.out.println("token caught!");
            System.out.println("TaskIDs: "+taskIds.toString());
//        if (StringUtils.isBlank(idToken)) {
//            throw new IllegalArgumentException("FirebaseTokenBlank");
//        }
        CountDownLatch latch = new CountDownLatch(1);
        ApiFutures.addCallback(FirebaseAuth.getInstance().verifyIdTokenAsync(idToken),
                new ApiFutureCallback<FirebaseToken>() {
                    @Override
                    public void onFailure(Throwable t) {
                        System.out.print("failure");
                        latch.countDown();
                    }
                    @Override
                    public void onSuccess(FirebaseToken decodedToken) {
                        System.out.println("token is valid.");

                        for(QuestionTask t: tasksRepository.findAll())
                            tasks.add(t);

                        latch.countDown();
                    }
                });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(tasks.size());
        return tasks;
    }

}
