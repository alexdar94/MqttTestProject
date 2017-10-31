package my.edu.tarc.kusm_wa14student.communechat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Demo how u pass data from ConversationAdapter2 to Chat Activity
        Toast.makeText(this,"Start chatting with "+getIntent().getStringExtra("CONVERSATION_NAME")+" now!",Toast.LENGTH_LONG).show();
    }
}
