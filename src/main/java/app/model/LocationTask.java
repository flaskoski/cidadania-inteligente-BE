package app.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.security.InvalidParameterException;
import java.util.List;

/**
 * Created by Felipe on 11/25/2017.
 */
@Document(collection = "tasks")
public class LocationTask extends AbstractTask {
    private String description;

    /**
     *
     * @param title - Title of the task
     * @param description
     */
    public LocationTask(String title, String description, List<String> answers, Integer correctAnswer, Integer timeToAnswer) {
        this.description = description;
        checkIfParametersAreValid(answers, correctAnswer);
        this.title = title;
    }


    /**
     *
     * @param title - Title of the task
     * @param description
     */
    public LocationTask(String title, String description) {
        this.description = description;
        this.title = title;

    }

    public LocationTask(){

    }

    private void checkIfParametersAreValid(List<String> answers, Integer correctAnswer){
        if(answers.size() < 2 || answers.size() > 6 || (correctAnswer < 1 || correctAnswer > 6))
            throw new InvalidParameterException("Number of answers must be between 2 and 6!");
        if(correctAnswer > answers.size() )
            throw new InvalidParameterException("Correct Answer doesn't exist!");

    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}