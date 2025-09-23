package model;
import java.util.*;

public class Post {
    private String id;
    private String userId;
    private String content;
    private Date timestamp;
    private int likes;
    private List<String> comments;


    private Post(PostBuilder builder){
        this.id=builder.id;
        this.userId=builder.userId;
        this.content=builder.content;
        this.timestamp=builder.timestamp;
        this.likes=builder.likes;
        this.comments=builder.comments;
    }







   //Builder
   public static class PostBuilder{
       private String id;
       private String userId;
       private String content;
       private Date timestamp;
       private int likes;
       private List<String> comments;

       public PostBuilder(){
           this.timestamp=new Date();
           this.likes=0;
           this.comments=new ArrayList<>();
       }
       public PostBuilder setId(String id){
           this.id=id;
           return this;
       }
       public PostBuilder setUserId(String userId){
           this.userId=userId;
           return this;
       }
       public PostBuilder setContent(String content){
           this.content=content;
           return this;
       }

       public PostBuilder setTimestamp(Date timestamp){
           this.timestamp=timestamp;
           return this;
       }
       public PostBuilder setLikes(int likes){
           this.likes=likes;
           return this;
       }

       public PostBuilder setComments(List<String> comments){
           this.comments=comments;
           return this;
       }
       public Post build(){
           return new Post(this);
       }

   }
}




