package my.edu.tarc.kusm_wa14student.communechat.internal;

import android.text.TextUtils;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alex on 08/10/2017.
 */

public class ServiceGenerator {

    public static final String API_BASE_URL = "https://api.cloudmqtt.com/";
    public static final String baseUrl = "https://api.cloudmqtt.com/";
    public static final String contentType = "application/json";
    public static final String adminUsername = "kniwwsgx";
    public static final String adminPassword = "16taQ24EBtVv";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    public static <S> S createService(Class<S> serviceClass) {
        String authToken = Credentials.basic(adminUsername, adminPassword);
        return createService(serviceClass, authToken);
    }

    public static <S> S createService(
            Class<S> serviceClass, final String authToken) {
        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor(authToken);

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);

                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }

        return retrofit.create(serviceClass);
    }
}
