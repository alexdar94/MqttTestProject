package my.edu.tarc.kusm_wa14student.communechat.model;

/**
 * Created by Alex on 08/10/2017.
 */

public class MqttUser {
    public String username;
    public String password;

    public MqttUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
