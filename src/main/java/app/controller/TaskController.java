package app.controller;

import java.util.ArrayList;
import java.util.Optional;

import app.firebase.FirebaseValidator;
import app.model.QuestionTask;
import app.model.Task;
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
            System.out.print("token caught!");
//        if (StringUtils.isBlank(idToken)) {
//            throw new IllegalArgumentException("FirebaseTokenBlank");
//        }

        String playerUid = FirebaseValidator.validateUser(idToken);
        if(playerUid == null)
            return null;

        System.out.println("TaskIDs: "+taskIds.toString());
        for (String taskId : taskIds){
            tasksRepository.findById(taskId).ifPresent(tasks::add);
        }

        System.out.println(tasks.size());
        return tasks;
    }

}
