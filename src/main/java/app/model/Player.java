package app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Hashtable;

@Document(collection = "players")
public class Player {
    @Id
    private String _id;

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    private String firebaseId;
    private String username;
    private HashMap<String, MissionProgress> missions;

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

    public Player(){}
    public Player(String firebaseId){
        this.firebaseId = firebaseId;
    }

}
