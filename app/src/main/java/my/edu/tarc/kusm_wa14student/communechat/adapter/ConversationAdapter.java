package my.edu.tarc.kusm_wa14student.communechat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import my.edu.tarc.kusm_wa14student.communechat.R;
import my.edu.tarc.kusm_wa14student.communechat.fragments.ChatFragment;

public class ConversationAdapter extends ArrayAdapter<String> {
    int lastPosition = -1;

    Context mContext;

    public ConversationAdapter(ArrayList<String> txt, int resources, Context context) {
        super(context, resources, txt);
        this.mContext=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatFragment.ViewHolder viewHolder;
        String txt = getItem(position);
        TextView tvName = null;
        final View result;
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact_frame, parent, false);
            tvName = (TextView) convertView.findViewById(R.id.listView_conversation);

            result = convertView;

        }
        else {
            viewHolder = (ChatFragment.ViewHolder) convertView.getTag();
            result = convertView;

        }

        Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in);
            // Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);

        lastPosition = position;


        tvName.setText("conversation Name :" + txt);


        return convertView;
    }
}
