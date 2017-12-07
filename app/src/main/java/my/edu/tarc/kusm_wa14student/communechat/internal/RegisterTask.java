package my.edu.tarc.kusm_wa14student.communechat.internal;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import static my.edu.tarc.kusm_wa14student.communechat.LoginActivity.password;
import static my.edu.tarc.kusm_wa14student.communechat.LoginActivity.username;



public class RegisterTask extends AsyncTask<String, Void, Void> {
    private void postData(String NewUsername, String newPassword) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://10.0.2.2:1234/webservices/register.php");

        try {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

            nameValuePairs.add(new BasicNameValuePair("newUserName", NewUsername));
            nameValuePairs.add(new BasicNameValuePair("newPassword", newPassword));


            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);

        } catch (Exception e) {
            Log.e("log_tag", "Error:  " + e.toString());
        }
    }
    @Override
    protected Void doInBackground(String... strings) {
        postData(username, password);
        Log.e("ha",username);

        return null;
    }
}
