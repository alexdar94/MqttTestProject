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

import java.io.BufferedReader;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import my.edu.tarc.kusm_wa14student.communechat.adapter.ChatAdapter;
import my.edu.tarc.kusm_wa14student.communechat.internal.MqttHelper;
import my.edu.tarc.kusm_wa14student.communechat.internal.MqttMessageHandler;
import my.edu.tarc.kusm_wa14student.communechat.model.ChatMessage;


import static my.edu.tarc.kusm_wa14student.communechat.LoginActivity.username;

import static my.edu.tarc.kusm_wa14student.communechat.internal.DisplayConversationTask2.conversationid;
import static my.edu.tarc.kusm_wa14student.communechat.internal.DisplayConversationTask2.user_id;

public class ChatActivity extends AppCompatActivity {

    private Button send;
    private EditText input;
    private List<ChatMessage> messages = new ArrayList<>();
    private ChatAdapter adapter;
    private Bundle bundle = new Bundle();
    String inputText;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            updateList(message);
            adapter.notifyDataSetChanged();
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

        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                mMessageReceiver, new IntentFilter("MessageEvent"));


        MqttHelper.subscribe(conversationid);

        // Demo how u pass data from ConversationAdapter2 to Chat Activity
        Toast.makeText(this,"Start chatting with "+getIntent().getStringExtra("CONVERSATION_NAME")+" now!",Toast.LENGTH_LONG).show();


        send = (Button)findViewById(R.id.button);
        input = (EditText)findViewById(R.id.editTextChat);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i= 0;
                inputText = input.getText().toString();
                MqttHelper.publish("1", "003818 " + inputText);
                Log.e("testing", inputText+ " " + username);
                new insertMessageTask().execute((Void) null);

                input.setText("");
            }
        });


        //messages.add(new ChatMessage(123, "qwerty", "Chunhan"));

        RecyclerView chat = (RecyclerView)findViewById(R.id.recyclerview_chat);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        chat.setLayoutManager(mLinearLayoutManager);

        adapter = new ChatAdapter(messages, getApplicationContext());
        chat.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void updateList(String msg){

        ChatMessage messaging = new ChatMessage(2, msg, username);
        messages.add(messaging);
        adapter.notifyDataSetChanged();

    }
    private class ReceiveChatListTask extends AsyncTask<String, Void, Void>{


        @Override
        protected Void doInBackground(String... strings) {
            if (!strings[0].isEmpty()){
                MqttMessageHandler handler = new MqttMessageHandler();
                Log.e("doInBackground","doInBackground");
                handler.setReceived(strings[0]);
                if(handler.mqttCommand == null){
                    switch (handler.mqttCommand){
                        case ACK_RECEIVE_MESSAGE:{
                            messages = handler.getChatMessageList();
                            for (ChatMessage temp: messages)
                                messages.add(temp);
                            break;
                        }

                    }
                }
            }
            return null;
        }
        }


    private class insertMessageTask extends AsyncTask<Void, Void, Boolean> {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        InputStream inputStream = null;



        private void postData(String messageText, String sender_id, String conversation_id ){
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://10.0.2.2:1234/webservices/insert_msg.php");


            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);

                nameValuePairs.add(new BasicNameValuePair("messageText", messageText));
                nameValuePairs.add(new BasicNameValuePair("sender_id", sender_id));
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
            postData(inputText, user_id, conversationid);
            Log.e("store msg to db",inputText+ " " + user_id + " " + conversationid);
            return null;
        }
    }

}
