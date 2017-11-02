package my.edu.tarc.kusm_wa14student.communechat.adapter;

import android.content.Context;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import my.edu.tarc.kusm_wa14student.communechat.R;
import my.edu.tarc.kusm_wa14student.communechat.model.ChatMessage;


public class ChatAdapter extends ArrayAdapter<ChatMessage> {
    private List<ChatMessage> messages;
    int lastPosition = -1;

    Context mContext;

    public ChatAdapter(Context context, int resource, List<ChatMessage> messages) {
        super(context, resource, messages);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewUser, textViewDate, textViewMessageText;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewUser = itemView.findViewById(R.id.textViewUser);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewMessageText = itemView.findViewById(R.id.textViewMessageText);
        }
    }
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat, parent, false);
        return new ChatAdapter.ViewHolder(inflatedView);
    }






}
