package model;

import java.util.HashMap;
import java.util.Map;

public class Builder {
    private String id;
    private String name;
    private Map<String,String> profileInfo;

    public Builder(){
        this.profileInfo= new HashMap<>();

    }

    public Builder setId(String id){
        this.id = id;
        return this;
    }
    public Builder setName(String name){
        this.name = name;
        return this;
    }
    public Builder addProfileInfo(String key, String value) {
        this.profileInfo.put(key, value);
        return this;
    }

    public Builder setProfileInfo(Map<String, String> profileInfo) {
        this.profileInfo = new HashMap<>(profileInfo);
        return this;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getProfileInfo() {
        return profileInfo;
    }

    public User build(){
        return new User(this);
    }

}
