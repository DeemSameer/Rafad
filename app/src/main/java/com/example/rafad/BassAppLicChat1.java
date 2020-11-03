package com.example.rafad;

import android.app.Application;

import com.sendbird.android.OpenChannel;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;
import com.sendbird.android.UserMessage;

class BaseApplication extends Application {

    private static final String APP_ID = "42BB2D2A-B54B-4D13-9042-40A535D503D3"; // US-1 Demo
    public static final String VERSION = "3.0.40";
    private static final String USER_ID ="123qwe";

    @Override
    public void onCreate() {
        super.onCreate();

        //SendBird.init(APP_ID, Context context);

        //Step 1: Initialize the Chat SDK
        SendBird.init(APP_ID, getApplicationContext());


        //Step 2: Connect to Sendbird server
        SendBird.connect(USER_ID, new SendBird.ConnectHandler() {
            @Override
            public void onConnected(User user, SendBirdException e) {
                if (e != null) {    // Error.
                    return;
                }
            }
        });


        //Step 3: Create a new open channel
        OpenChannel.createChannel(new OpenChannel.OpenChannelCreateHandler() {
            @Override
            public void onResult(OpenChannel openChannel, SendBirdException e) {
                if (e != null) {    // Error.
                    return;
                }
            }
        });

        OpenChannel.getChannel(CHANNEL_URL, new OpenChannel.OpenChannelGetHandler() {
            @Override
            public void onResult(OpenChannel openChannel, SendBirdException e) {
                if (e != null) {    // Error.
                    return;
                }

                openChannel.enter(new OpenChannel.OpenChannelEnterHandler() {
                    @Override
                    public void onResult(SendBirdException e) {
                        if (e != null) {    // Error.
                            return;
                        }
                    }
                });
            }
        });

        OpenChannel.sendUserMessage(MESSAGE, new BaseChannel.SendUserMessageHandler() {
            @Override
            public void onSent(UserMessage userMessage, SendBirdException e) {
                if (e != null) {    // Error.
                    return;
                }
            }
        });

    }
}