package my.edu.tarc.kusm_wa14student.communechat.fragments;

import my.edu.tarc.kusm_wa14student.communechat.R;
import my.edu.tarc.kusm_wa14student.communechat.adapter.ConversationAdapter2;
import my.edu.tarc.kusm_wa14student.communechat.internal.DisplayConversationTask2;
import my.edu.tarc.kusm_wa14student.communechat.internal.MqttHelper;
import my.edu.tarc.kusm_wa14student.communechat.model.Conversation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;




public class ConversationFragment extends Fragment  {

    private ConversationAdapter2 refresh;
    private BroadcastReceiver newConversationMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("ACK_NEW_CONVERSATION");
            String subConversationId = message.substring(6);
            MqttHelper.subscribe(subConversationId);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*LocalBroadcastManager.getInstance(getActivity().registerReceiver(
                newConversationMessageReceiver, new IntentFilter("MessageEvent")));*/
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversation, container, false);

        // Convert ur conversation JSON into Conversation java object
        // Add them into conversations
        // Then u will see the list of conversations
        //Populate this in ur async task then return it back here....
        List<Conversation> conversations = new ArrayList<>();

        RecyclerView mRecyclerView = view.findViewById(R.id.recyclerView_conversation);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        ConversationAdapter2 adapter = new ConversationAdapter2(getContext(), conversations);
        DisplayConversationTask2 display = new DisplayConversationTask2(adapter);
        display.execute();

        mRecyclerView.setAdapter(adapter);


        return view;
    }



}
