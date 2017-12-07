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

import static my.edu.tarc.kusm_wa14student.communechat.LoginActivity.username;
import static my.edu.tarc.kusm_wa14student.communechat.internal.DisplayUserTask.username_B;


public class NewConversationTask extends AsyncTask<String, Void, Void> {
        private void postData(String conversation_name, String user_name, String user_name_B) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://10.0.2.2:1234/webservices/insert_conversation.php");

            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);

                nameValuePairs.add(new BasicNameValuePair("conversation_name", conversation_name));
                nameValuePairs.add(new BasicNameValuePair("user_name", user_name));
                nameValuePairs.add(new BasicNameValuePair("user_name_B", user_name_B));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);

            } catch (Exception e) {
                Log.e("log_tag", "Error:  " + e.toString());
            }
        }

        @Override
        protected Void doInBackground(String... strings) {
            String userB = strings[0];

            postData(userB, username, userB);
            Log.e("checkuserA",username);
            Log.e("checkuserB",userB);
            return null;
        }
    }

