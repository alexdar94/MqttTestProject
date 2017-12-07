package my.edu.tarc.kusm_wa14student.communechat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import my.edu.tarc.kusm_wa14student.communechat.adapter.ChatAdapter;
import my.edu.tarc.kusm_wa14student.communechat.internal.MqttHelper;
import my.edu.tarc.kusm_wa14student.communechat.internal.MqttMessageHandler;
import my.edu.tarc.kusm_wa14student.communechat.model.ChatMessage;


import static my.edu.tarc.kusm_wa14student.communechat.LoginActivity.username;
import static my.edu.tarc.kusm_wa14student.communechat.adapter.ConversationAdapter2.conversationId;
import static my.edu.tarc.kusm_wa14student.communechat.internal.DisplayConversationTask2.conversationid;
import static my.edu.tarc.kusm_wa14student.communechat.internal.DisplayConversationTask2.user_name;
import static my.edu.tarc.kusm_wa14student.communechat.internal.MessageService.subConversationId;

public class ChatActivity extends AppCompatActivity {

    private Button send;
    private EditText input;
    private List<ChatMessage> messages = new ArrayList<>();
    private ChatAdapter adapter;
    private Bundle bundle = new Bundle();
    String inputText;
    static String message;
    static String usermsg;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            Log.e("chatactivity", message);
            message = intent.getStringExtra("ACK_SEND_MESSAGE");
            usermsg = message.substring(6);
            Log.e("chatactivity", message);
            updateList(usermsg);
            adapter.notifyDataSetChanged();
        }
    };
    private BroadcastReceiver mNewConversationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            message = intent.getStringExtra("ACK_NEW_CONVERSATION");
            usermsg = message.substring(6);
            updateList(usermsg);
//            adapter.notifyDataSetChanged();

        }
    };

    public static void clearAsyncTask(AsyncTask<?, ?, ?> asyncTask) {
        if (asyncTask != null) {
            if (!asyncTask.isCancelled()) {
                asyncTask.cancel(true);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

//        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
//                mNewConversationReceiver, new IntentFilter("ConversationEvent"));

        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                mMessageReceiver, new IntentFilter("MessageEvent"));


            // Demo how u pass data from ConversationAdapter2 to Chat Activity
        Toast.makeText(this, "Start chatting with " + getIntent().getStringExtra("USER_NAME") + " now!", Toast.LENGTH_LONG).show();


        send = (Button)findViewById(R.id.button);
        input = (EditText)findViewById(R.id.editTextChat);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputText = input.getText().toString();
//                Log.e("conversationid",conversationid);
//                Log.e("subconversationid",subConversationId);

//                if(message.substring(0,6) != "003823"){
//                    MqttHelper.publish(subConversationId, "003823" + inputText);
//                }else{
                    MqttHelper.publish(conversationId, "003823" + inputText);
//                }
                Log.e("testing", inputText+ " " + username);
                new insertMessageTask().execute((Void) null);
                input.setText("");

            }
        });

        RecyclerView chat = (RecyclerView)findViewById(R.id.recyclerview_chat);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        chat.setLayoutManager(mLinearLayoutManager);

        adapter = new ChatAdapter(messages, getApplicationContext());
        chat.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void updateList(String msg){

        ChatMessage messaging = new ChatMessage("", msg, username);
        messages.add(messaging);
        adapter.notifyDataSetChanged();

    }
    private class insertMessageTask extends AsyncTask<Void, Void, Boolean> {

        private void postData(String messageText, String user_name, String conversation_id ){
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://10.0.2.2:1234/webservices/insert_msg.php");

            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);

                nameValuePairs.add(new BasicNameValuePair("messageText", messageText));
                nameValuePairs.add(new BasicNameValuePair("user_name", user_name));
                nameValuePairs.add(new BasicNameValuePair("conversation_id", conversation_id));


                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);


            }
            catch(Exception e)
            {
                Log.e("log_tag", "Error:  "+e.toString());
            }

        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            postData(inputText, user_name, conversationid);
            Log.e("store msg to db",inputText+ " " + user_name + " " + conversationid);
            return null;
        }
    }



}
