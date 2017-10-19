package my.edu.tarc.kusm_wa14student.communechat.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import my.edu.tarc.kusm_wa14student.communechat.LoginActivity;
import my.edu.tarc.kusm_wa14student.communechat.MainActivity;
import my.edu.tarc.kusm_wa14student.communechat.R;
import my.edu.tarc.kusm_wa14student.communechat.internal.MqttAPI;
import my.edu.tarc.kusm_wa14student.communechat.internal.MqttHelper;
import my.edu.tarc.kusm_wa14student.communechat.internal.ServiceGenerator;
import my.edu.tarc.kusm_wa14student.communechat.model.ACLRule;
import my.edu.tarc.kusm_wa14student.communechat.model.MqttUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment implements View.OnClickListener {
    String resultUsername="";

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        final EditText etSearchUser = view.findViewById(R.id.editText_searchUser);
        Button btnSearchUser = view.findViewById(R.id.button_searchUser);
        final TextView searchResult = view.findViewById(R.id.textView_searchResult);
        searchResult.setOnClickListener(this);

        btnSearchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String searchUserName = etSearchUser.getText().toString();
                if(!searchUserName.equals(((MainActivity)getActivity()).currentUser.username)){
                    MqttAPI service = ServiceGenerator.createService(MqttAPI.class);

                    service.findUser(searchUserName).enqueue(new Callback<MqttUser>() {
                        @Override
                        public void onResponse(Call<MqttUser> call, Response<MqttUser> response) {
                            if (response.isSuccessful()) {
                                resultUsername = searchUserName;
                                searchResult.setText("Click to chat with: " +searchUserName);
                            } else {
                                // error response, no access to resource?
                                resultUsername = "";
                                searchResult.setText("No user found");
                            }
                            Log.e("success", response.toString());
                        }

                        @Override
                        public void onFailure(Call<MqttUser> call, Throwable t) {
                            // something went completely south (like no internet connection)
                            resultUsername = "";
                            Log.e("Error createNewUser", t.getMessage());
                        }
                    });
                } else {
                    Toast.makeText(getContext(),"You are searching for yourself...",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // current user: userA, searched user: userB
            // When search result text view is clicked
            // combine current username and searched username as new topic
            // assign the new topic ("userAuserB") to userA and userB
            case R.id.textView_searchResult:
                final MqttAPI service = ServiceGenerator.createService(MqttAPI.class);

                final String currentUsername = ((MainActivity)getActivity()).currentUser.username;


                // Assign topic ("userAuserB") to current user
                service.createNewACLRule(new ACLRule(currentUsername+ "/" +resultUsername, true, true, currentUsername)).enqueue(new Callback<ACLRule>() {
                    @Override
                    public void onResponse(Call<ACLRule> call, Response<ACLRule> response) {
                        if (response.isSuccessful()) {

                            // Assign topic ("userAuserB") to searched user
                            service.createNewACLRule(new ACLRule(currentUsername + "/" +  resultUsername, true, true, resultUsername)).enqueue(new Callback<ACLRule>() {
                                @Override
                                public void onResponse(Call<ACLRule> call, Response<ACLRule> response) {
                                    if (response.isSuccessful()) {
                                        ((MainActivity)getActivity()).currentChatTopic = currentUsername+ "/" +resultUsername;
                                        ((MainActivity)getActivity()).gotoChatFragment();
                                        MqttHelper.subscribe(((MainActivity)getActivity()).currentUser.username + "/" + resultUsername);
                                        Toast.makeText(getContext(), "You can start chatting with "+resultUsername+ " now!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // error response, no access to resource?
                                    }
                                }

                                @Override
                                public void onFailure(Call<ACLRule> call, Throwable t) {
                                    // something went completely south (like no internet connection)
                                    Log.e("Error createNewACLRule", t.getMessage());

                                }
                            });
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<ACLRule> call, Throwable t) {
                        // something went completely south (like no internet connection)
                        Log.e("Error createNewACLRule", t.getMessage());

                    }
                });

                break;
        }
    }

}
