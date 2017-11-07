package my.edu.tarc.kusm_wa14student.communechat.model;

import java.util.Date;

/**
 * Created by silen on 10/23/2017.
 */

public class ChatMessage {
    private int messageid;
    private String messageText;
    private String messageUser;

    /*public ChatMessage() {
    }*/

    public ChatMessage(int messageid, String messageText, String messageUser) {
        this.messageid = messageid;
        this.messageText = messageText;
        this.messageUser = messageUser;
    }

    public int getMessageid() {
        return messageid;
    }

    public void setMessageid(int messageid) {
        this.messageid = messageid;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

}
