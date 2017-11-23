package my.edu.tarc.kusm_wa14student.communechat.internal;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import my.edu.tarc.kusm_wa14student.communechat.adapter.ConversationAdapter2;
import my.edu.tarc.kusm_wa14student.communechat.adapter.UserAdapter;
import my.edu.tarc.kusm_wa14student.communechat.model.Conversation;
import my.edu.tarc.kusm_wa14student.communechat.model.User;

import static my.edu.tarc.kusm_wa14student.communechat.LoginActivity.username;
import static my.edu.tarc.kusm_wa14student.communechat.internal.DisplayConversationTask2.user_name;


public class DisplayUserTask extends AsyncTask<String, String, String> {
    HttpURLConnection connection = null;
    BufferedReader reader = null;
    InputStream inputStream = null;
    String result;
    StringBuffer finalBufferData;
    UserAdapter adapter;
    public static String username_B;
    int role;


    public DisplayUserTask(UserAdapter adapter){
        this.adapter = adapter;
    }

    @SuppressLint("LongLogTag")
    @Override
    protected String doInBackground(String... params) {


        String url_select = "http://10.0.2.2:1234/webservices/get_user.php";

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            param.add(new BasicNameValuePair("user_name", username));


            try {
                // Set up HTTP post

                // HttpClient is more then less deprecated. Need to change to URLConnection
                HttpClient httpClient = new DefaultHttpClient();

                HttpPost httpPost = new HttpPost(url_select);
                httpPost.setEntity(new UrlEncodedFormEntity(param));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                // Read content & Log
                inputStream = httpEntity.getContent();
                Log.e("Checking", inputStream.toString());
                return EntityUtils.toString(httpResponse.getEntity());
            } catch (UnsupportedEncodingException e1) {
                Log.e("UnsupportedEncodingException", e1.toString());
                e1.printStackTrace();
            } catch (ClientProtocolException e2) {
                Log.e("ClientProtocolException", e2.toString());
                e2.printStackTrace();
            } catch (IllegalStateException e3) {
                Log.e("IllegalStateException", e3.toString());
                e3.printStackTrace();
            } catch (IOException e4) {
                Log.e("IOException", e4.toString());
                e4.printStackTrace();
            }

        // Convert response to string using String Builder
        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
            StringBuilder sBuilder = new StringBuilder();

            String line = null;
            while ((line = bReader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }

            inputStream.close();
            result = sBuilder.toString();
            return result;
        } catch (Exception e) {
            Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
        }
        return null;

    }

    @Override
    protected void onPostExecute(String result) {
        //Parse Json Data

        List<User> users = new ArrayList<>();
        try{
            JSONArray jArray = new JSONArray(result);
            for(int i = 0; i<jArray.length(); i++) {
                JSONObject jObject = jArray.getJSONObject(i);
                String userid= jObject.getString("user_id");
                username_B = jObject.getString("user_name");
                String password = jObject.getString("password");
                int NRIC = jObject.getInt("NRIC");
                String gender = jObject.getString("gender");

                User data = new User(userid, username_B, password, NRIC, gender, "http://accountingx.com/wp-content/uploads/2017/02/3729.png");
                Log.e("success",userid + username_B + password + NRIC + gender + "");

                users.add(data);

            }
            adapter.setUsers(users);
            adapter.notifyDataSetChanged();
        }catch (JSONException e){
            Log.e("JSONException", "Error: " + e.toString());

        }


    }



}
