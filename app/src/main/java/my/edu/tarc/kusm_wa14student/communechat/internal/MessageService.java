package my.edu.tarc.kusm_wa14student.communechat.internal;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import my.edu.tarc.kusm_wa14student.communechat.ChatActivity;
import my.edu.tarc.kusm_wa14student.communechat.LoginActivity;

/**
 * Created by Xeosz on 29-Sep-17.
 */

public class MessageService extends Service {

    private String TAG = "MessageService";
    public static String subConversationId;

    private boolean isRunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isRunning = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (isRunning == false) {
            isRunning = true;
            Log.e("start","listening");
            //Start MQTT Connection
            MqttHelper.startMqtt(getApplicationContext());

            //Mqtt Callback Handler
            MqttHelper.setCallback(new MqttCallbackExtended() {

                @Override
                public void connectComplete(boolean reconnect, String serverURI) {
                    Log.e("connectComplete","connectComplete");
                }

                @Override
                public void connectionLost(Throwable cause) {
                    Log.e("connectionLost","connectionLost");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    sendMessage(message.toString());
                    Log.e("messagearrived","sendMessage");
                    Log.e("messagearrived",topic+ ":"+ message.toString());

                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    Log.e("deliveryComplete","deliveryComplete");
                }
            });

            /* ---! Subscribe to default topic *
                Change if you don't want subscribe by default
             */
            //MqttHelper.subscribe(MqttHelper.defaultTopic);

        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

    // Send an Intent with an action named "MessageEvent".
    // The Intent sent should be received by the registered ReceiverActivity.
    private void sendMessage(String message) {
        Log.i(TAG, "Broadcasting message");
        Intent intent = new Intent("MessageEvent");
        Intent newCon = new Intent("ConversationEvent");
        Log.e("sendMessage",message);
        MqttMessageHandler handler = new MqttMessageHandler();
        handler.setReceived(message);
        if (handler.isReceiving()) {
            Log.e("CheckingSendMsg", (message.substring(0,6).equals("003823")) + "");
            Log.e("Checkingnewconversation", (message.substring(0,6).equals("003820")) + "");
            if(message.substring(0,6).equals("003823")){
                Log.e("SendMessageChecking",message);
                intent.putExtra("ACK_SEND_MESSAGE", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                Log.e("SendMessageResult",message);
                Log.e("abc",intent.toString());
//                Log.e("abc",newCon.toString());
            }else if(message.substring(0,6).equals("003820")){
                subConversationId = message.substring(6);
                MqttHelper.subscribe(subConversationId);
                newCon.putExtra("ACK_NEW_CONVERSATION", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(newCon);
                Log.e("NewConversationResult",message);
//                Log.e("abc",intent.toString());
                Log.e("abc",newCon.toString());
            }else{
                Log.e("","Nothing happen");
            }

        }

    }

}
