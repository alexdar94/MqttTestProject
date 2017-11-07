package my.edu.tarc.kusm_wa14student.communechat.fragments;

import my.edu.tarc.kusm_wa14student.communechat.MainActivity;
import my.edu.tarc.kusm_wa14student.communechat.R;
import my.edu.tarc.kusm_wa14student.communechat.adapter.ConversationAdapter2;
import my.edu.tarc.kusm_wa14student.communechat.model.Conversation;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class ConversationFragment extends Fragment  {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        DisplayConversationTask2 abc = new DisplayConversationTask2(adapter);
        abc.execute();

        mRecyclerView.setAdapter(adapter);
        return view;
    }



}
