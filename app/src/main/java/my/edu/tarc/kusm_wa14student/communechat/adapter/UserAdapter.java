package my.edu.tarc.kusm_wa14student.communechat.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import my.edu.tarc.kusm_wa14student.communechat.ChatActivity;
import my.edu.tarc.kusm_wa14student.communechat.R;
import my.edu.tarc.kusm_wa14student.communechat.internal.NewConversationTask;
import my.edu.tarc.kusm_wa14student.communechat.model.User;

/**
 * Created by silen on 11/21/2017.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context context;
    private static List<User> users;

    public UserAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView userName;
        ImageView userPic;

        public ViewHolder(View v) {
            super(v);
            userPic = v.findViewById(R.id.imageview_user_pic);
            userName = v.findViewById(R.id.textview_user_name);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ChatActivity.class);
            // Pass ur object here, check out serializable for object passing
            intent.putExtra("USER_NAME",users.get(getAdapterPosition()).user_name);
            context.startActivity(intent);

            NewConversationTask newConversation = new NewConversationTask();
            newConversation.execute();
        }
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new UserAdapter.ViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
        holder.userName.setText(users.get(position).user_name);
        Picasso.with(context).load(users.get(position).getUser_pic_url()).resize(50, 50).centerCrop().into(holder.userPic);

    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
