package app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Hashtable;

@Document(collection = "players")
public class Player {

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    public Integer getXp() {
        return xp;
    }

    public void setXp(Integer xp) {
        this.xp = xp;
    }

    public HashMap<String, MissionProgress> getMissions() {
        return missions;
    }

    public void setMissions(HashMap<String, MissionProgress> missions) {
        this.missions = missions;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Player(){
        this.xp = 0;
        this.level = 1;
    }
    public Player(String firebaseId){
        this.firebaseId = firebaseId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    private Integer level;
    private HashMap<String, MissionProgress> missions;
    @Id
    private String _id;
    private Integer xp;
    private String firebaseId;
    private String username;
    public Integer addXp(Integer xpWon) {
        this.xp += xpWon;
        return this.xp;
    }
}
