package com.example.rafad.notification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAASHhf-4g:APA91bElnQGsg11GMeJiUpQv6HgswP2qKMKLsjRyo8UJzDuQNgpB0uO_pICz4QOlHVxKy-xWRV2pngAsWAAPO8TdwwsOrW_Bh02CjpI5G0GBT_R3o57tjUxkWcg3T9FjnHZu69Aq5y64" // Your server key refer to video for finding your server key
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotifcation(@Body NotificationSender body);
}
