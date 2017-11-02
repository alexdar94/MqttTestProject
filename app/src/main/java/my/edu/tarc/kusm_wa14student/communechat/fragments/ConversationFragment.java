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

        DisplayConversationTask2 abc = new DisplayConversationTask2();
        abc.execute();


        List<Conversation> conversations = new ArrayList<>();
        conversations.add(new Conversation("abc","gg", -1, "", "" ,"https://i.ytimg.com/vi/SfLV8hD7zX4/maxresdefault.jpg", "erer"));
        conversations.add(new Conversation("abc","hh", -1, "", "" ,"https://www.idolator.com/wp-content/uploads/sites/10/2015/10/adele-hello.jpg", "erer"));
        conversations.add(new Conversation("abc","ii", -1, "", "" ,"https://static.pexels.com/photos/54632/cat-animal-eyes-grey-54632.jpeg", "rer"));
        conversations.add(new Conversation("abc","pp", -1, "", "" ,"https://i.ytimg.com/vi/SfLV8hD7zX4/maxresdefault.jpg", "qwe"));
        conversations.add(new Conversation("abc","Casd", -1, "", "" ,"https://static.pexels.com/photos/54632/cat-animal-eyes-grey-54632.jpeg", "asd"));
        conversations.add(new Conversation("abc","ee", -1, "", "" ,"https://www.idolator.com/wp-content/uploads/sites/10/2015/10/adele-hello.jpg", "abc"));







        RecyclerView mRecyclerView = view.findViewById(R.id.recyclerView_conversation);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        ConversationAdapter2 adapter = new ConversationAdapter2(getContext(), conversations);
        mRecyclerView.setAdapter(adapter);
        return view;
    }



}
