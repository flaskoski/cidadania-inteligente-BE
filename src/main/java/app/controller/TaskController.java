package app.controller;

import java.util.*;

import app.firebase.FirebaseValidator;
import app.model.AbstractTask;
import app.model.QuestionTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {

    @Autowired
    private TasksRepository tasksRepository;

    @RequestMapping("/tasks")
    //public Mission sendTasks(@RequestParam(value="uid", defaultValue="") String idToken) {
    public ArrayList<AbstractTask> sendTasks(/*@RequestParam(value="missionID", defaultValue="") String missionID, */
            @RequestParam Map<String,String> params,
            @RequestHeader HashMap<String, String> headers
            ) {//@RequestHeader String idToken   "Authorization"
        final ArrayList<AbstractTask> tasks= new ArrayList<>();
        String idToken = headers.get("Authorization".toLowerCase());
        if(idToken != null)
            System.out.print("token caught!");
        else return null;
//        if (StringUtils.isBlank(idToken)) {
//            throw new IllegalArgumentException("FirebaseTokenBlank");
//        }

        String playerUid = FirebaseValidator.validateUser(idToken);
        if(playerUid == null)
            return null;

        System.out.println("Retrieving tasks progress...");
        List<String> taskIDs = new ArrayList<>();
        Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String,String> entry = it.next();
            if(entry.getKey().toLowerCase().contains("taskid"))
                taskIDs.add(entry.getValue());
        }
        System.out.println("TaskIDs: "+taskIDs.toString());
        for (String taskId : taskIDs){
            tasksRepository.findById(taskId).ifPresent(tasks::add);
        }

        System.out.println(tasks.size());
        return tasks;
    }

}
