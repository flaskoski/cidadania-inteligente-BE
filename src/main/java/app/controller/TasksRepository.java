package app.controller;

import app.model.QuestionTask;
import app.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TasksRepository extends MongoRepository<QuestionTask, String> {

     //   public QuestionTask findByTitle(String title);
        //public List<Mission> findByLastName(String lastName);

}
