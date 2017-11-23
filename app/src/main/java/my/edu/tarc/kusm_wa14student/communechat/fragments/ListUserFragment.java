package my.edu.tarc.kusm_wa14student.communechat.fragments;

/**
 * Created by silen on 11/21/2017.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import my.edu.tarc.kusm_wa14student.communechat.R;
import my.edu.tarc.kusm_wa14student.communechat.adapter.UserAdapter;
import my.edu.tarc.kusm_wa14student.communechat.internal.DisplayUserTask;
import my.edu.tarc.kusm_wa14student.communechat.model.User;

public class ListUserFragment extends Fragment{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listuser, container, false);

        List<User> users = new ArrayList<>();

        RecyclerView userRecyclerView = view.findViewById(R.id.recyclerView_user);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        userRecyclerView.setLayoutManager(mLinearLayoutManager);

        UserAdapter adapter = new UserAdapter(getContext(), users);
        DisplayUserTask display = new DisplayUserTask(adapter);
        display.execute();

        userRecyclerView.setAdapter(adapter);
        return view;
    }
}
