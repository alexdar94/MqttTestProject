package my.edu.tarc.kusm_wa14student.communechat;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;

import my.edu.tarc.kusm_wa14student.communechat.adapter.ViewPagerAdapter;
import my.edu.tarc.kusm_wa14student.communechat.fragments.ChatFragment;
import my.edu.tarc.kusm_wa14student.communechat.fragments.ContactFragment;
import my.edu.tarc.kusm_wa14student.communechat.fragments.ConversationFragment;
import my.edu.tarc.kusm_wa14student.communechat.fragments.FeedbackFragment;
import my.edu.tarc.kusm_wa14student.communechat.fragments.ListUserFragment;
import my.edu.tarc.kusm_wa14student.communechat.fragments.SearchFragment;
import my.edu.tarc.kusm_wa14student.communechat.fragments.UserFragment;
import my.edu.tarc.kusm_wa14student.communechat.internal.MessageService;
import my.edu.tarc.kusm_wa14student.communechat.internal.MqttAPI;
import my.edu.tarc.kusm_wa14student.communechat.internal.MqttHelper;
import my.edu.tarc.kusm_wa14student.communechat.internal.ServiceGenerator;
import my.edu.tarc.kusm_wa14student.communechat.model.ACLRule;
import my.edu.tarc.kusm_wa14student.communechat.model.MqttUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.R.attr.type;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    //CustomViewPagerAdapter Variables;
    private ViewPagerAdapter adapter;
    private int NUMBER_OF_SCREENS = 5;
    private MenuItem prevMenuItem;

    //MainActivity Views
    private ViewPager viewPager;
    private BottomNavigationView bottomNavView;

    //Viewpager's Fragments
    private ContactFragment contactFragment;
    private SearchFragment searchFragment;
    private UserFragment userFragment;
    private ChatFragment chatFragment;
    private ConversationFragment conversationFragment;
    private ListUserFragment listUserFragment;
    private FeedbackFragment feedbackFragment;

    private BroadcastReceiver mMessageReceiver;

    //Static Mqtt Connection Variables
    private MqttHelper mqttHelper;
    private String clientId = "1000000000";
    private String serverUri = "tcp://m11.cloudmqtt.com:17391";
    private String mqttUsername = "ehvfrtgx";
    private String mqttPassword = "YPcMC08pYYpr";
    private String clientTopic = "sensor/test";
    private int QoS = 1;

    private SharedPreferences mPrefs;
    public MqttUser currentUser;
    public String currentChatTopic = "";
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve saved current user
        // from android local storage using Shared preference
        mPrefs = getSharedPreferences("COMMUNE_CHAT",MODE_PRIVATE);
        String current = mPrefs.getString("CURRENT_USER", "");

        Type type = new TypeToken<MqttUser>(){}.getType();
        currentUser= gson.fromJson(mPrefs.getString("CURRENT_USER", ""), type);

        //Start service
        startService(new Intent(MainActivity.this, MessageService.class));

        //Initialize views
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        bottomNavView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        contactFragment = new ContactFragment();
        chatFragment = new ChatFragment();
        searchFragment = new SearchFragment();
        userFragment = new UserFragment();
        conversationFragment = new ConversationFragment();
        listUserFragment = new ListUserFragment();
        feedbackFragment = new FeedbackFragment();

        adapter.addFragment(listUserFragment);
//        adapter.addFragment(contactFragment);
//        adapter.addFragment(chatFragment);
        adapter.addFragment(conversationFragment);
        adapter.addFragment(searchFragment);
        adapter.addFragment(userFragment);
        adapter.addFragment(feedbackFragment);

        adapter.notifyDataSetChanged();


        BottomNavigationViewHelper.removeShiftMode(bottomNavView);

        viewPager.setOffscreenPageLimit(NUMBER_OF_SCREENS);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNavView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: "+position);
                bottomNavView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavView.getMenu().getItem(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
       //Disable ViewPager Swipe
       viewPager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });

        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.contact_tab:
                        viewPager.setCurrentItem(0);
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.chat_tab:
                        viewPager.setCurrentItem(1);
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.search_tab:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.user_tab:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.feedback_tab:
                        viewPager.setCurrentItem(4);
                        break;

                }
                return false;
            }
        });

        viewPager.setAdapter(adapter);

    }

    public void gotoChatFragment(){
        viewPager.setCurrentItem(1);
    }


    static class BottomNavigationViewHelper {

        static void removeShiftMode(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                    //item.setShiftingMode(false);
                    // set once again checked value, so view will be updated
                    //item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {
                Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field");
            } catch (IllegalAccessException e) {
                Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode");
            }
        }
    }
}
