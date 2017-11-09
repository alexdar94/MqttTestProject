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
import java.util.List;

import my.edu.tarc.kusm_wa14student.communechat.adapter.ConversationAdapter2;
import my.edu.tarc.kusm_wa14student.communechat.model.Conversation;


public class DisplayConversationTask2 extends AsyncTask<String, String, String> {
    HttpURLConnection connection = null;
    BufferedReader reader = null;
    InputStream inputStream = null;
    String result;
    StringBuffer finalBufferData;
    ConversationAdapter2 adapter;
    public static String conversationid;
    String conversationname;
    String createdAt;
    public static String user_id;
    int role;


    public DisplayConversationTask2(ConversationAdapter2 adapter){
        this.adapter = adapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @SuppressLint("LongLogTag")
    @Override
    protected String doInBackground(String... params) {

        String url_select = "http://10.0.2.2:1234/webservices/get_conversation.php";

        ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

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
            Log.e("fuck", inputStream.toString());
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

        List<Conversation> conversations = new ArrayList<>();
        try{
            JSONArray jArray = new JSONArray(result);
            for(int i = 0; i<jArray.length(); i++) {
                JSONObject jObject = jArray.getJSONObject(i);
                conversationid = jObject.getString("conversation_id");
                conversationname = jObject.getString("conversation_name");
                createdAt = jObject.getString("created_at");
                user_id = jObject.getString("user_id");
                role = jObject.getInt("role");

                Conversation data = new Conversation(conversationid, conversationname, role, createdAt, user_id, "https://www.idolator.com/wp-content/uploads/sites/10/2015/10/adele-hello.jpg", "welcome");
                Log.e("success",conversationid + conversationname + role + createdAt + user_id + "" + "");

                conversations.add(data);

            }
            adapter.setConversations(conversations);
            adapter.notifyDataSetChanged();
        }catch (JSONException e){
            Log.e("JSONException", "Error: " + e.toString());

        }


    }



}
