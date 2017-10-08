package my.edu.tarc.kusm_wa14student.communechat.model;

/**
 * Created by Alex on 08/10/2017.
 */

public class ACLRule {
    String topic;
    Boolean read;
    Boolean write;
    String username;

    public ACLRule(String topic, Boolean read, Boolean write, String username) {
        this.topic = topic;
        this.read = read;
        this.write = write;
        this.username = username;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Boolean getWrite() {
        return write;
    }

    public void setWrite(Boolean write) {
        this.write = write;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
