package app.controller;

import app.model.AbstractTask;
import app.model.QuestionTask;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface TasksRepository extends MongoRepository<AbstractTask, String> {
     //   public QuestionTask findByTitle(String title);
        //public List<Mission> findByLastName(String lastName);

}
