package my.edu.tarc.kusm_wa14student.communechat.fragments;

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

import my.edu.tarc.kusm_wa14student.communechat.R;
import my.edu.tarc.kusm_wa14student.communechat.adapter.ConversationAdapter2;



public class DisplayConversationTask2 extends AsyncTask<String, String, Void> {
    HttpURLConnection connection;
    BufferedReader reader;
    InputStream inputStream = null;
    String result;



    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }

    @SuppressLint("LongLogTag")
    @Override
    protected Void doInBackground(String... params) {
        String url = "http://localhost:1234/webservices/get_conversation.php";


        ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

        try{


            //Set up Http Post
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(param));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

            inputStream = httpEntity.getContent();

        } catch (UnsupportedEncodingException e1) {
            Log.e("UnsupportedEncodingException", e1.toString());
            e1.printStackTrace();
        } catch (ClientProtocolException e2) {
            Log.e("ClientProtocolException", e2.toString());
            e2.printStackTrace();
        } catch (IOException e) {
            Log.e("IOException", e.toString());
            e.printStackTrace();
        }

        try{
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
            StringBuilder sBuilder = new StringBuilder();

            String line = null;
            while ((line = bReader.readLine())!= null){
                sBuilder.append(line + "\n");
            }
            inputStream.close();
            result = sBuilder.toString();
        } catch (UnsupportedEncodingException e1) {
            Log.e("UnsupportedEncodingException", e1.toString());
            e1.printStackTrace();
        } catch (IOException e) {
            Log.e("IOException", e.toString());
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        //Parse Json Data
        try{
            JSONArray jArray = new JSONArray(result);
            for(int i = 0; i<jArray.length(); i++){
                JSONObject jObject = jArray.getJSONObject(i);
                String conversationid = jObject.getString("conversation_id");
                String user_id = jObject.getString("user_id");
                String role = jObject.getString("role");

            }

        }catch (JSONException e){
            Log.e("JSONException", "Error: " + e.toString());

        }



    }
}
