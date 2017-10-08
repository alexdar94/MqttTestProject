package my.edu.tarc.kusm_wa14student.communechat.internal;

import java.util.List;

import my.edu.tarc.kusm_wa14student.communechat.model.ACLRule;
import my.edu.tarc.kusm_wa14student.communechat.model.MqttUser;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Alex on 08/10/2017.
 */

public interface MqttAPI {

    @Headers({"Content-Type: application/json"})
    @POST("user")
    Call<MqttUser> createNewUser(@Body MqttUser newUser);

    @GET("acl")
    Call<List<ACLRule>> listACLRules();

    @Headers({"Content-Type: application/json"})
    @POST("acl")
    Call<ACLRule> createNewACLRule(@Body ACLRule newACL);
}
