package com.example.tourandtravelmanagement.chatbot;

import android.util.Log;

import org.json.JSONException;

/**
 * Created by dhiogoboza on 26/04/16.
 */
public class JSONParser {

    private static final String TAG = "JSONParser";

    private static final String CONTENT_NODE = "content";
    private static final String USERNAME_NODE = "username";
    private static final String USER_ID_NODE = "userid";
    private static final String USER_COLOR_NODE = "color";
    private static final String PRIVATE_MSG_NODE = "private";
    private static final String USERS_NODE = "users";
    private static final String MSG_TYPE_NODE = "msgtype";
    private static final String SERVER_MESSAGE = "servermsg";
    private static final String CLEAN_FLAG = "CLEAN";


    public static void parseString(String data) {
        try {
            Messenger messenger = Messenger.getInstance();
          //  JSONObject jsonObject = new JSONObject(data);

          //  if (jsonObject.has(CONTENT_NODE)) {
                User user = parseUser();

                com.example.tourandtravelmanagement.chatbot.Message message = new com.example.tourandtravelmanagement.chatbot.Message();
                //Html.fromHtml(data);
                message.setContent(data);

                message.setSender(user);

                messenger.addUser(user);

                    messenger.addGlobalMessage(message);
            //Linkify.addLinks(myClickableUrl, Linkify.WEB_URLS);




        } catch (JSONException e) {
            Log.e(TAG, "Received data nto is in json format", e);
        }
    }

    private static User parseUser() throws JSONException {
        User user = new User();
        user.setName("DORA Server");
        user.setColor("red");
        user.setUserId("1");

//        user.setName(jsonObject.getString(USERNAME_NODE));
//        user.setColor(jsonObject.getString(USER_COLOR_NODE));
//        user.setUserId(jsonObject.getString(USER_ID_NODE));

        return user;
    }

}
