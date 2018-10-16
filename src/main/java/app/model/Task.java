package app.model;

import java.io.Serializable;
import java.util.List;

public abstract class Task implements Serializable {
    protected String title = "";
    public Boolean completed = false;
    String type = "task";

    static final int TIMER_OFF = -1;
    public static final int TYPE_QUESTION = 1;

    //experience points that earns if completes the mission
    private Integer xp=0;
    //mission difficulty (from 1 to 10)
    private Integer difficulty=1;
    //tags to search the mission
    private List<String> tags;

    public Integer getXp() {
        return xp;
    }

    public void setXp(Integer xp) {
        this.xp = xp;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getType(){
        return this.type;
    }
    public void setType(String type){
        this.type = type;
    }

}
