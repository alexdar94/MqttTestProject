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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import my.edu.tarc.kusm_wa14student.communechat.adapter.ChatAdapter;
import my.edu.tarc.kusm_wa14student.communechat.adapter.ConversationAdapter2;
import my.edu.tarc.kusm_wa14student.communechat.fragments.DisplayConversationTask2;
import my.edu.tarc.kusm_wa14student.communechat.internal.MqttHelper;
import my.edu.tarc.kusm_wa14student.communechat.internal.MqttMessageHandler;
import my.edu.tarc.kusm_wa14student.communechat.model.ChatMessage;
import my.edu.tarc.kusm_wa14student.communechat.model.Conversation;

import static my.edu.tarc.kusm_wa14student.communechat.LoginActivity.username;

public class ChatActivity extends AppCompatActivity {

    private Button send;
    private EditText input;
    private List<ChatMessage> messages = new ArrayList<>();
    private Bundle bundle = new Bundle();
    String inputText;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            ReceiveChatListTask chatListTask = new ReceiveChatListTask();
            //chatListTask.execute();
            //updateList(message);


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


        MqttHelper.subscribe("sensor/test");
        // Demo how u pass data from ConversationAdapter2 to Chat Activity
        Toast.makeText(this,"Start chatting with "+getIntent().getStringExtra("CONVERSATION_NAME")+" now!",Toast.LENGTH_LONG).show();


        send = (Button)findViewById(R.id.button);
        input = (EditText)findViewById(R.id.editTextChat);



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i= 0;
                inputText = input.getText().toString();
                MqttHelper.publish("sensor/test", "003818 " + username +input.getText().toString());
                ChatMessage message = new ChatMessage(3, inputText, username);
                Log.e("testing", input.getText().toString() + username);
                messages.add(message);
                input.setText("");
            }
        });


        //messages.add(new ChatMessage(123, "qwerty", "Chunhan"));


        RecyclerView chat = (RecyclerView)findViewById(R.id.recyclerview_chat);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        chat.setLayoutManager(mLinearLayoutManager);

        ChatAdapter adapter = new ChatAdapter(messages, getApplicationContext());
        chat.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    /*private void updateList(String msg){

        messages.add(msg);

    }*/
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



}
