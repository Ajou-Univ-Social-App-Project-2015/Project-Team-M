package kr.ac.ajou.media.project_team_m;

import java.io.Serializable;

public class UserInformation implements Serializable{
    private String name;
    private String id;

    // structure
    public UserInformation(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
