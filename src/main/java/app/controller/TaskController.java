package app.controller;

import java.util.*;

import app.fileManagement.FileController;
import app.firebase.FirebaseValidator;
import app.model.AbstractTask;
import app.model.QuestionTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

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
            logger.info("token caught!");
        else return null;
//        if (StringUtils.isBlank(idToken)) {
//            throw new IllegalArgumentException("FirebaseTokenBlank");
//        }

        //Sort tasks by key (task0, task1, ...)
        Map<String,String> sortedParams = new TreeMap<>(params);

        String playerUid = FirebaseValidator.validateUser(idToken);
        if(playerUid == null)
            return null;

        logger.info("Retrieving tasks progress...");
        List<String> taskIDs = new ArrayList<>();
        Iterator<Map.Entry<String, String>> it = sortedParams.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String,String> entry = it.next();
            if(entry.getKey().toLowerCase().contains("taskid"))
                taskIDs.add(entry.getValue());
        }
        logger.info("TaskIDs: "+taskIDs.toString());
        for (String taskId : taskIDs){
            tasksRepository.findById(taskId).ifPresent(tasks::add);
        }

        logger.info(String.valueOf(tasks.size()));
        return tasks;
    }

}
