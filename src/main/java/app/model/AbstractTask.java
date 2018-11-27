package app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Document(collection = "tasks")
public abstract class AbstractTask implements Serializable {
    protected String title = "";
    public Boolean completed = false;

    static final int TIMER_OFF = -1;

    @Id
    private String _id;

    //experience points that earns if completes the mission
    private Integer xp=0;
    //mission difficulty (from 1 to 10)
    private Integer difficulty=1;
    //tags to search the mission
    private List<String> tags;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }


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


    public Class<?> classType = this.getClass();
}
