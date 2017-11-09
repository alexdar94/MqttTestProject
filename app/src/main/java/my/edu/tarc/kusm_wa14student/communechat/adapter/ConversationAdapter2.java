package my.edu.tarc.kusm_wa14student.communechat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import my.edu.tarc.kusm_wa14student.communechat.ChatActivity;
import my.edu.tarc.kusm_wa14student.communechat.R;
import my.edu.tarc.kusm_wa14student.communechat.internal.MqttHelper;
import my.edu.tarc.kusm_wa14student.communechat.model.Conversation;

/**
 * Created by silen on 10/29/2017.
 */
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ConversationAdapter2 extends RecyclerView.Adapter<ConversationAdapter2.ViewHolder> {
    private Context context;
    private static List<Conversation> conversations;

    public ConversationAdapter2(Context context, List<Conversation> conversations) {
        this.context = context;
        this.conversations = conversations;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView conversationName, lastMessage;
        ImageView conversationPic;

        public ViewHolder(View v) {
            super(v);
            conversationPic = v.findViewById(R.id.imageview_conversation_pic);
            conversationName = v.findViewById(R.id.textview_conversation_name);
            lastMessage = v.findViewById(R.id.textView_latest_message);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ChatActivity.class);
            // Pass ur object here, check out serializable for object passing
            intent.putExtra("CONVERSATION_NAME",conversations.get(getAdapterPosition()).conversation_name);
            context.startActivity(intent);
        }
    }

    @Override
    public ConversationAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_conversation, parent, false);
        return new ConversationAdapter2.ViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(ConversationAdapter2.ViewHolder holder, int position) {
        holder.conversationName.setText(conversations.get(position).conversation_name);
        holder.lastMessage.setText(conversations.get(position).last_message);
        Picasso.with(context).load(conversations.get(position).conversation_pic_url).resize(50, 50).centerCrop().into(holder.conversationPic);
    }

    @Override
    public int getItemCount() {
        return conversations.size();
    }


}