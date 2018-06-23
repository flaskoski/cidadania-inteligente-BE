package model;

import java.io.Serializable;

public abstract class Task implements Serializable {
    protected String title = "";
    public Boolean completed = false;
    String type = "task";

    static final int TIMER_OFF = -1;
    public static final int TYPE_QUESTION = 1;

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
