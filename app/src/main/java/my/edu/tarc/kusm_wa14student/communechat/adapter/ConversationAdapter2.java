package my.edu.tarc.kusm_wa14student.communechat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import my.edu.tarc.kusm_wa14student.communechat.R;
import my.edu.tarc.kusm_wa14student.communechat.fragments.ChatFragment;
import my.edu.tarc.kusm_wa14student.communechat.fragments.DisplayConversationTask2;
import my.edu.tarc.kusm_wa14student.communechat.model.Conversation;

/**
 * Created by silen on 10/29/2017.
 */

public class ConversationAdapter2 extends ArrayAdapter<Conversation> {
    public ConversationAdapter2(Context context, int k, List<Conversation> l) {
        super(context, k, l);
    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        //Get the conversation

        Conversation conversation = getItem(position);


        //conversation_name = conversation.getConversation_name();


        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_chat, parent, false);
        }

        TextView textviewConversation;

        textviewConversation = (TextView)convertView.findViewById(R.id.textViewConversation);

        textviewConversation.setText(conversation.getConversation_name());

        return convertView;
    }
}
