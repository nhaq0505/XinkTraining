package model;

import java.util.*;

public class User {
    private String id;
    private String name;
    private Map<String,String> profileInfo;

   public User(Builder builder){
       this.id=builder.getId();
       this.name=builder.getName();
       this.profileInfo=builder.getProfileInfo();


   }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getProfileInfo() {
        return new HashMap<>(profileInfo);
    }

    public void addProfileInfo(String key, String value) {
        profileInfo.put(key, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name + " (ID: " + id + ")";
    }



}
