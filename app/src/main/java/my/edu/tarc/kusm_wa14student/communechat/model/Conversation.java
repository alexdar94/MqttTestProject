package my.edu.tarc.kusm_wa14student.communechat.model;

import java.util.Date;

/**
 * Created by silen on 10/29/2017.
 */

public class Conversation {
    private String conversation_id;
    private String conversation_name;
    private int role;
    private long created_at;

    public Conversation() {
        this.conversation_id = conversation_id;
        this.conversation_name = conversation_name;
        this.role = role;

        created_at = new Date().getDate();
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

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }
}
