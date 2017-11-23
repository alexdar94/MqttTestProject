package my.edu.tarc.kusm_wa14student.communechat.model;

/**
 * Created by Xeosz on 26-Sep-17.
 */

public class User {
  public String user_id;
  public String user_name;
  public String password;
  public int NRIC;
  public String gender;
  public String user_pic_url;

  public User(String user_id, String user_name, String password, int NRIC, String gender, String user_pic_url) {
    this.user_id = user_id;
    this.user_name = user_name;
    this.password = password;
    this.NRIC = NRIC;
    this.gender = gender;
    this.user_pic_url = user_pic_url;
  }

  public String getUser_pic_url() {
    return user_pic_url;
  }

  public void setUser_pic_url(String user_pic_url) {
    this.user_pic_url = user_pic_url;
  }

  public String getUser_id() {
    return user_id;
  }

  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }

  public String getUser_name() {
    return user_name;
  }

  public void setUser_name(String user_name) {
    this.user_name = user_name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getNRIC() {
    return NRIC;
  }

  public void setNRIC(int NRIC) {
    this.NRIC = NRIC;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

}