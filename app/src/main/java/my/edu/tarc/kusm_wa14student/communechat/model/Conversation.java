package my.edu.tarc.kusm_wa14student.communechat.model;

import java.util.Date;

/**
 * Created by silen on 10/29/2017.
 */

public class Conversation {
    public String conversation_id;
    public String conversation_name;
    public int role;
    public String created_at;
    public String conversation_pic_url;
    public String last_message;
    public String user_id;



    public Conversation(String conversation_id, String conversation_name, int role, String created_at, String user_id, String conversation_pic_url, String last_message) {
        this.conversation_id = conversation_id;
        this.conversation_name = conversation_name;
        this.role = role;
        this.created_at = created_at;
        this.user_id = user_id;
        this.conversation_pic_url = conversation_pic_url;
        this.last_message = last_message;
    }

    public String getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(String conversation_id) {
        this.conversation_id = conversation_id;
    }

    public String getConversation_name() {
        return conversation_name;
    }

    public void setConversation_name(String conversation_name) {
        this.conversation_name = conversation_name;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int type) {
        this.role = role;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
