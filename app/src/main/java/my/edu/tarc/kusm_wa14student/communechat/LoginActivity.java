package my.edu.tarc.kusm_wa14student.communechat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;

import my.edu.tarc.kusm_wa14student.communechat.internal.MessageService;
import my.edu.tarc.kusm_wa14student.communechat.internal.MqttAPI;
import my.edu.tarc.kusm_wa14student.communechat.internal.MqttHelper;
import my.edu.tarc.kusm_wa14student.communechat.internal.ServiceGenerator;
import my.edu.tarc.kusm_wa14student.communechat.model.ACLRule;
import my.edu.tarc.kusm_wa14student.communechat.model.MqttUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements AsyncResponse {

    //Views
    EditText etPassword;
    AutoCompleteTextView etLogin;
    Button btnLogin;
    Button Signin;
    ProgressBar progressBar;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPrefs = this.getSharedPreferences("COMMUNE_CHAT", Context.MODE_PRIVATE);

        //Start service
        startService(new Intent(this, MessageService.class));

        //Initialize view
        etPassword = (EditText) findViewById(R.id.editText_password);
        etLogin = (AutoCompleteTextView) findViewById(R.id.editText_login);
        btnLogin = (Button) findViewById(R.id.button_login);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_login);
        progressBar.setVisibility(View.INVISIBLE);
        Signin = (Button)findViewById(R.id.buttonSignin);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = etLogin.getText().toString();
                final String password = etPassword.getText().toString();
                
                final MqttAPI service = ServiceGenerator.createService(MqttAPI.class);
                // Register user
                service.createNewUser(new MqttUser(username, password)).enqueue(new Callback<MqttUser>() {
                    @Override
                    public void onResponse(Call<MqttUser> call, Response<MqttUser> response) {
                        if (response.isSuccessful()) {
                            // user object available
                            MqttUser currentUser = new MqttUser(username, password);

                            HashMap<String,String> postData = new HashMap<String,String>();
                            postData.put("txtUsername", username);
                            postData.put("txtPassword", password);

                            PostResponseAsyncTask task = new PostResponseAsyncTask(LoginActivity.this, postData, (AsyncResponse) LoginActivity.this);
                            task.execute("http://10.0.2.2:1234/webservices/register.php");

                            // Save current user to Android local storage
                            // using shared preference (one of the android local storage options)
                            String userJSON = new Gson().toJson(currentUser);
                            SharedPreferences.Editor prefsEditor = mPrefs.edit();
                            prefsEditor.putString("CURRENT_USER", userJSON);
                            prefsEditor.commit();

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                            // Subscribe new user to own topic name
                            service.createNewACLRule(new ACLRule(username, true, true, username)).enqueue(new Callback<ACLRule>() {
                                @Override
                                public void onResponse(Call<ACLRule> call, Response<ACLRule> response) {
                                    if (response.isSuccessful()) {
                                        MqttHelper.subscribe(username);
                                        Toast.makeText(LoginActivity.this, "Subscribed successfully to " + username, Toast.LENGTH_SHORT).show();
                                    } else {
                                        // error response, no access to resource?
                                        Toast.makeText(LoginActivity.this, "Unable to subcribe to " + username, Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ACLRule> call, Throwable t) {
                                    // something went completely south (like no internet connection)
                                    Log.e("Error createNewACLRule", t.getMessage());

                                }
                            });

                        } else {
                            Toast.makeText(LoginActivity.this, "Account already existed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MqttUser> call, Throwable t) {
                        // something went completely south (like no internet connection)
                        Log.e("Error createNewUser", t.getMessage());
                        Toast.makeText(LoginActivity.this, "Account already existed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,String> postData = new HashMap<String,String>();
                String username = etLogin.getText().toString();
                String password = etPassword.getText().toString();
                postData.put("txtUsername", username);
                postData.put("txtPassword", password);
                PostResponseAsyncTask task = new PostResponseAsyncTask(LoginActivity.this, postData, (AsyncResponse) LoginActivity.this);
                task.execute("http://10.0.2.2:1234/webservices/Login.php");
            }
        });
    }

    @Override
    public void processFinish(String result) {
        final String LOG = "LoginActivity";

        MqttUser loginUser = new MqttUser(etLogin.getText().toString(), etPassword.getText().toString());

        Log.d(LOG, result);
        if(result.equals("success")){

            String userJSON = new Gson().toJson(loginUser);
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            prefsEditor.putString("CURRENT_USER", userJSON);
            prefsEditor.commit();
            MqttHelper.subscribe(etLogin.getText().toString());
            Toast.makeText(this, "Login successfully", Toast.LENGTH_LONG).show();
            Intent next = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(next);
        }else {
            Toast.makeText(this, "Login failed, Please try again", Toast.LENGTH_LONG).show();
        }
    }



    }
/*    private class AuthenicationTask extends AsyncTask<Void, Void, Boolean> {

        String username;
        String password;

        private AuthenicationTask(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean result = false;


            return result;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progressBar.setVisibility(View.GONE);
        }
    }

}*/
