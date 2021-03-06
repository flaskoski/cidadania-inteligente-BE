package app.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.security.InvalidParameterException;
import java.util.ArrayList;

@Document(collection = "tasks")
public class QuestionTask extends AbstractTask {
    private String question;
    private ArrayList<String> answers;
    private Integer timeToAnswer;
    private Integer correctAnswer;

    /**
     *
     * @param title - Title of the task
     * @param question
     * @param answers
     * @param timeToAnswer
     * @param correctAnswer - Answer choice that is correct, starting by 1.
     */
    public QuestionTask(String title, String question, ArrayList<String> answers, Integer correctAnswer, Integer timeToAnswer) {
        this.question = question;

        checkIfParametersAreValid(title, question, answers, correctAnswer);

        this.answers = answers;
        this.timeToAnswer = timeToAnswer;
        this.correctAnswer = correctAnswer;
        this.title = title;
    }
    /**
     *
     * @param title - Title of the task
     * @param question
     * @param answers
     * @param correctAnswer - Answer choice that is correct, starting by 1.
     */
    public QuestionTask(String title, String question, ArrayList<String> answers, Integer correctAnswer) {
        checkIfParametersAreValid(title, question, answers, correctAnswer);

        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.timeToAnswer = TIMER_OFF;
        this.title = title;
    }

    public QuestionTask(){


    }

    private void checkIfParametersAreValid(String title, String question, ArrayList<String> answers, Integer correctAnswer){
        if(answers.size() < 1 || answers.size() > 6 || (correctAnswer < 1 || correctAnswer > 6))
            throw new InvalidParameterException("Number of answers must be between 2 and 6!");
        if(correctAnswer > answers.size() )
            throw new InvalidParameterException("Correct Answer doesn't exist!");

    }
    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getType() {
        return "Pergunta";
    }
    @Override
    public void setTitle(String question) {
        this.title = title;
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public Integer getTimeToAnswer() {
        return timeToAnswer;
    }

    public void setTimeToAnswer(Integer timeToAnswer) {
        this.timeToAnswer = timeToAnswer;
    }

    public Integer getCorrectAnswer() {return correctAnswer;    }

    public void setCorrectAnswer(Integer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

}
