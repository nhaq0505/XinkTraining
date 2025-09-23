package model;

import model.Post;
import model.User;
import model.Builder;

import java.util.*;

public class SocialNetwork {
    private Map<String, User> users;
    private Map<String, Set<String>> connections;
    private Map<String, List<Post>> userPosts;

    public SocialNetwork() {
        this.users = new HashMap<>();
        this.connections = new HashMap<>();
        this.userPosts = new HashMap<>();
    }

    public boolean addUser(User user){
        if (users.containsKey(user.getId())){
            return false;
        }
        users.put(user.getId(), user);
        connections.put(user.getId(), new HashSet<>());
        userPosts.put(user.getId(), new ArrayList<>());
        return true;
    }


    public boolean removeUser(String userId) {
        if (!users.containsKey(userId)) {
            return false;
        }

        // Xóa người dùng
        users.remove(userId);

        // Xóa các kết nối của người dùng
        connections.remove(userId);

        // Xóa người dùng khỏi danh sách kết nối của người khác
        for (Set<String> userConnections : connections.values()) {
            userConnections.remove(userId);
        }

        // Xóa bài đăng của người dùng
        userPosts.remove(userId);

        return true;
    }


    public boolean connect(String userid1,String userid2){
        if (!users.containsKey(userid1) || !users.containsKey(userid2)) {
            return false;
        }
        connections.get(userid1).add(userid2);
        connections.get(userid2).add(userid1);
        return true;
    }

    public boolean disconnect(String userid1,String userid2){
        if (!users.containsKey(userid1) || !users.containsKey(userid2)) {
            return false;
        }
        connections.get(userid1).remove(userid2);
        connections.get(userid2).remove(userid1);
        return true;
    }

    public boolean addPost(String userId,Post post){
        if (!users.containsKey(userId)) {
            return false;
        }
        userPosts.get(userId).add(post);
        return true;
    }

    public User getUser(String userId){
        return users.get(userId);
    }

    public Set<String> getConnections(String userId){
        if(!userId.contains(userId)){
            return Collections.emptySet();
        }
        return new HashSet<>(connections.get(userId));
    }
    public List<User> getAllUser(){
        return new ArrayList<>(users.values());
    }

    public List<Post> getUserPosts(String userId){
        if(!userId.contains(userId)){
            return Collections.emptyList();
        }
        return new ArrayList<>(userPosts.get(userId));
    }





}
