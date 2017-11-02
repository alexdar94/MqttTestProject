package my.edu.tarc.kusm_wa14student.communechat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import my.edu.tarc.kusm_wa14student.communechat.adapter.ChatAdapter;
import my.edu.tarc.kusm_wa14student.communechat.internal.MqttHelper;
import my.edu.tarc.kusm_wa14student.communechat.model.ChatMessage;

public class ChatActivity extends AppCompatActivity {

    private ListView list_of_message;
    private Button send;
    private EditText input;
    private ArrayList<String> list = new ArrayList<>();
    private Bundle bundle = new Bundle();
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            //updateList(message);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        MqttHelper.subscribe("sensor/test");
        // Demo how u pass data from ConversationAdapter2 to Chat Activity
        Toast.makeText(this,"Start chatting with "+getIntent().getStringExtra("CONVERSATION_NAME")+" now!",Toast.LENGTH_LONG).show();


        list_of_message = (ListView)findViewById(R.id.listView_chat);
        send = (Button)findViewById(R.id.button);
        input = (EditText)findViewById(R.id.editTextChat);


        list = new ArrayList<String>();
        list.add("test");


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MqttHelper.publish("sensor/test", "003818 " + input.getText().toString());
                input.setText("");
            }
        });
        List<ChatMessage> chatmessage = new ArrayList<>();



        ChatAdapter adapter = new ChatAdapter(getApplicationContext(), 0, chatmessage);
        list_of_message.setAdapter(adapter);

    }

}
