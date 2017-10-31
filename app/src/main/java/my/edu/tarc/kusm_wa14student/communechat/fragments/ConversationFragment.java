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

/**
 * Created by Alex on 01/11/2017.
 */

public class ConversationFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversation, container, false);

        // Convert ur conversation JSON into Conversation java object
        // Add them into conversations
        // Then u will see the list of conversations d sohai
        List<Conversation> conversations = new ArrayList<>();
        conversations.add(new Conversation("fuck","Hatano Yui", -1, -1, "https://upload.wikimedia.org/wikipedia/commons/0/0c/160528_Hatano_Yui_11m4s.jpg" ,"fuck"));
        conversations.add(new Conversation("fuck","S1 Aoi", -1, -1, "https://i.warosu.org/data/jp/img/0137/87/1437578483433.jpg" ,"fuck"));
        conversations.add(new Conversation("fuck","Tsukasa Aoi", -1, -1, "https://upload.wikimedia.org/wikipedia/commons/0/0e/%EC%95%84%EC%98%A4%EC%9D%B4%EC%B8%A0%EC%B9%B4%EC%82%AC%28Aoi_Tsukasa%29_%EA%B5%AD%EB%82%B4_%ED%8C%AC%EB%AF%B8%ED%8C%85_1.jpg" ,"fuck"));
        conversations.add(new Conversation("fuck","Sora Aoi", -1, -1, "http://2.bp.blogspot.com/_PhwWw_j2UkE/St64B_y-vWI/AAAAAAAAAVk/zlZFu5tfjjM/s400/Sora+Aoi.jpg" ,"fuck"));
        conversations.add(new Conversation("fuck","China Matsuoka", -1, -1, "https://i.pinimg.com/736x/12/cd/08/12cd083aaae6a4855083765d085154a2.jpg" ,"fuck"));
        conversations.add(new Conversation("fuck","S1 Aoi", -1, -1, "https://i.warosu.org/data/jp/img/0137/87/1437578483433.jpg" ,"fuck"));

        RecyclerView mRecyclerView = view.findViewById(R.id.recyclerView_conversation);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        ConversationAdapter2 adapter = new ConversationAdapter2(getContext(), conversations);
        mRecyclerView.setAdapter(adapter);
        return view;
    }
}
