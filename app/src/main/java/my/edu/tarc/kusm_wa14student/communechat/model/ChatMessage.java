package my.edu.tarc.kusm_wa14student.communechat.model;

import java.util.Date;

/**
 * Created by silen on 10/23/2017.
 */

public class ChatMessage {
    private int messageid;
    private String messageText;
    private String messageUser;
    private long messageTime;

    public ChatMessage() {
        this.messageid = messageid;
        this.messageText = messageText;
        this.messageUser = messageUser;
        messageTime = new Date().getTime();
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

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
