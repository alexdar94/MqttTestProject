package my.edu.tarc.kusm_wa14student.communechat.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.entity.UrlEncodedFormEntityHC4;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import my.edu.tarc.kusm_wa14student.communechat.MainActivity;
import my.edu.tarc.kusm_wa14student.communechat.R;
import my.edu.tarc.kusm_wa14student.communechat.adapter.ConversationAdapter;
import my.edu.tarc.kusm_wa14student.communechat.adapter.ConversationAdapter2;
import my.edu.tarc.kusm_wa14student.communechat.internal.JSONParser;
import my.edu.tarc.kusm_wa14student.communechat.internal.MqttHelper;
import my.edu.tarc.kusm_wa14student.communechat.model.Conversation;

public class ChatFragment extends Fragment  {

    private ListView conversationListView;
    private EditText editText;
    private Button btn;
    private TextView textViewNoCurrentChat;
    private LinearLayout linearLayoutChatView;
    SharedPreferences mPrefs;
    private ArrayList<String> list = new ArrayList<>();
    private ConversationAdapter2 adapter;
    private Bundle bundle = new Bundle();
    //UpdateListTask task = new UpdateListTask();
    //private static final String TAG_conversation_name = "conversation_name";
    String conversation_name;




    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            Log.e("BroadcastReceiver",message);
            new DisplayConversationTask().execute(message);
            updateList(message);
            new DisplayConversationTask2().execute();

            //task.execute(message);
        }
    };

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                mMessageReceiver, new IntentFilter("MessageEvent"));
    }

    /*@Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // If user see chat fragment
        // Check if there is current chat topic
        // Only show current chat topic messages
        if (isVisibleToUser) {
            String currentChatTopic = ((MainActivity)getActivity()).currentChatTopic;
            if (currentChatTopic.equals("")){
                linearLayoutChatView.setVisibility(View.INVISIBLE);
                textViewNoCurrentChat.setVisibility(View.VISIBLE);
            } else {
                linearLayoutChatView.setVisibility(View.VISIBLE);
                textViewNoCurrentChat.setVisibility(View.INVISIBLE);
            }
        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        conversationListView = (ListView) rootView.findViewById(R.id.listView_conversation);
        //editText = (EditText) rootView.findViewById(R.id.editTextChat);
        //btn = (Button) rootView.findViewById(R.id.button);
        textViewNoCurrentChat = rootView.findViewById(R.id.textView_no_current_chat);
        linearLayoutChatView = rootView.findViewById(R.id.linearLayout_chatView);


        list = new ArrayList<String>();
        list.add("ABC");

        //adapter = new ConversationAdapter(list, 0, getActivity());
        conversationListView.setAdapter(adapter);

        String currentChatTopic = ((MainActivity)getActivity()).currentChatTopic;
        MqttHelper.subscribe(currentChatTopic);


        conversationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //OnClick Animation
                Animation onClickAnimation = new AlphaAnimation(0.3f, 1.0f);
                onClickAnimation.setDuration(2000);
                view.startAnimation(onClickAnimation);

            }
        });

        // Inflate the layout for this fragment
        return rootView;
        }

    private void updateList(String msg) {
        list.add(msg);
        adapter.notifyDataSetChanged();
    }



    public static class ViewHolder {
        public TextView tvName;
        public TextView tvStatus;
        //ImageView info;
    }

    private class DisplayConversationTask extends AsyncTask<String, String, String> {
        String url = "http://localhost:1234/webservices/get_conversation.php";

        @Override
        protected String doInBackground(String... strings) {

            try{
                JSONObject conversation = new JSONObject();
                String conversationid = conversation.getString("conversation_id");
                String conversationname = conversation.getString("conversation_name");
                String createdAt = conversation.getString("created_at");
                String type = conversation.getString("type");


                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("conversationid", conversationid));
                params.add(new BasicNameValuePair("conversationname", conversationname));
                params.add(new BasicNameValuePair("createdAt", createdAt));
                params.add(new BasicNameValuePair("type", type));



                JSONParser jsonParser = new JSONParser();
                //Getting JSON from URL
                //JSONObject json = jsonParser.getJSONFromUrl(url);

                //JsonObject json = jsonParser.requestHttp();

                return "success";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "error";

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.equals("success")){



            }else if(result.equals("error")){
                Toast.makeText(getContext(), "Fail to load", Toast.LENGTH_LONG).show();
            }

        }
    }
    }






