package my.edu.tarc.kusm_wa14student.communechat.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import my.edu.tarc.kusm_wa14student.communechat.R;

import static my.edu.tarc.kusm_wa14student.communechat.LoginActivity.username;


public class FeedbackFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    EditText etFeedback;
    Button buttonSend;
    String inputFeedback;

    public FeedbackFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        etFeedback = view.findViewById(R.id.editText_feedback);
        buttonSend = view.findViewById(R.id.button_sendfeedback);



        buttonSend.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        insertFeedback feedback = new insertFeedback();
        feedback.execute();
        etFeedback.setText("");
        Toast.makeText(getContext(), "Thanks for your feedback...We improve as soon as possible", Toast.LENGTH_LONG).show();

    }
    public class insertFeedback extends AsyncTask<String, Void, Void>{
        private void postData(String name, String feedback){
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://10.0.2.2:1234/webservices/insert_feedback.php");

            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

                nameValuePairs.add(new BasicNameValuePair("name", name));
                nameValuePairs.add(new BasicNameValuePair("feedback", feedback));


                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);

            } catch (Exception e) {
                Log.e("log_tag", "Error:  " + e.toString());
            }
        }

        @Override
        protected Void doInBackground(String... strings) {
            inputFeedback = etFeedback.getText().toString();
            postData(username, inputFeedback);
            Log.e("feedback", username);
            Log.e("feedback", inputFeedback);
            return null;
        }
    }
}
