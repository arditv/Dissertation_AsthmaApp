package se16.qmul.ac.uk.finalapplication;

//Inspiration from source https://www.youtube.com/watch?v=Vcn4OuV4Ixg



public class Post {

    private String title, content, user_id;
    

   public Post(){

   }

    public Post(String title, String content, String user_id){
       this.title = title;
       this.content = content;
       this.user_id = user_id;

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserID() {
        return user_id;
    }

    public void setUserID(String user_id) {
        this.user_id = user_id;
    }


}
