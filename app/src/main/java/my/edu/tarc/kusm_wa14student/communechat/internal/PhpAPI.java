package my.edu.tarc.kusm_wa14student.communechat.internal;

import java.util.List;

import my.edu.tarc.kusm_wa14student.communechat.model.ACLRule;
import my.edu.tarc.kusm_wa14student.communechat.model.Conversation;
import my.edu.tarc.kusm_wa14student.communechat.model.MqttUser;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by silen on 11/11/2017.
 */

public interface PhpAPI {

    @Headers({"Content-Type: application/json"})
    @POST("insert_conversation.php")
    Call<Conversation> createConversation(@Body Conversation newConversation);
}
