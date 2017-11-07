package my.edu.tarc.kusm_wa14student.communechat.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

import my.edu.tarc.kusm_wa14student.communechat.R;
import my.edu.tarc.kusm_wa14student.communechat.model.ChatMessage;



public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private List<ChatMessage> messages;
    private Context context;

    public ChatAdapter(List<ChatMessage> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat, parent, false);
        return new ChatAdapter.ViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.user.setText(messages.get(position).getMessageUser());
        holder.textmsg.setText(messages.get(position).getMessageText());

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView user;
        TextView textmsg;

        public ViewHolder(View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.textViewUser);
            textmsg = itemView.findViewById(R.id.textViewMessageText);


        }

    }


}









