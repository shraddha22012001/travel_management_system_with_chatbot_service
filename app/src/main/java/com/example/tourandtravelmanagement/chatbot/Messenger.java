package com.example.tourandtravelmanagement.chatbot;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by dhiogoboza on 25/04/16.
 */
public class Messenger {

    private static final String TAG = "Messenger";
    private Client mClient;

    private static Messenger instance;
    private static Activity sActivity;

    private com.example.tourandtravelmanagement.chatbot.UsersAdapter mUsersAdapter;
    private MessagesAdapter mGlobalMessagesAdapter;
    private User user;

    public static void setActivity(Activity activity) {
        sActivity = activity;
    }

    private List<com.example.tourandtravelmanagement.chatbot.Message> globalMessages = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private HashMap<User, List<com.example.tourandtravelmanagement.chatbot.Message>> userMessages = new HashMap<>();
    private HashMap<User, MessagesAdapter> userAdapters = new HashMap<>();

    public static Messenger getInstance() {
        if (instance == null) {
            instance = new Messenger();
        }

        return instance;
    }

    private Messenger() {
        user = new User();
        mClient = new Client(sActivity, user);
        mUsersAdapter = new com.example.tourandtravelmanagement.chatbot.UsersAdapter(sActivity, this);
        mGlobalMessagesAdapter = new MessagesAdapter(sActivity, this);
    }

    public void sendMessage(String message) {


        final String messageToSend = message.replaceAll("\\r|\\n", "");

        new Thread() {
            @Override
            public void run() {
                // FIXME: use a better way
                mClient.sendData( messageToSend);
            }
        }.start();
    }

    public void sendMessage(String message, User dest) {
        message = message.replaceAll("\\r|\\n", "");

        mClient.sendData("{\"content\": \"" + message + "\", \"dest\": \"" + dest.getUserId() + "\" }");
    }

    public List<com.example.tourandtravelmanagement.chatbot.Message> getGlobalMessages() {
        return globalMessages;
    }

    public List<User> getUsers() {
        return users;
    }

    public Client getClient() {
        return mClient;
    }

    public com.example.tourandtravelmanagement.chatbot.UsersAdapter getUsersAdapter() {
        return mUsersAdapter;
    }

    public void addUser(User user) {
        if (!users.contains(user)) {
            Log.d(TAG, "adding user: " + user);

            users.add(user);
            sActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mUsersAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    public void addGlobalMessage(com.example.tourandtravelmanagement.chatbot.Message message) {

        globalMessages.add(message);

        sActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mGlobalMessagesAdapter.notifyDataSetChanged();
            }
        });
    }

    public void addUserMessage(final User user, com.example.tourandtravelmanagement.chatbot.Message message) {
        List<com.example.tourandtravelmanagement.chatbot.Message> messages = userMessages.get(user);

        if (messages == null) {
            messages = new ArrayList<>();
            userMessages.put(user, messages);
        }

        messages.add(message);

        sActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mUsersAdapter.notifyDataSetChanged();

                MessagesAdapter userAdapter = userAdapters.get(user);
                if (userAdapter != null) {
                    userAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void removeUser(String userId) {

        User toRemove = null;
        for (User user: getUsers()) {
            Log.d(TAG, "current user: " + user.getUserId());

            if (user.getUserId().equals(userId)) {
                toRemove = user;
                break;
            }
        }

        if (toRemove != null) {
            getUsers().remove(toRemove);
            sActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mUsersAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    public MessagesAdapter getGlobalMessagesAdapter() {
        return mGlobalMessagesAdapter;
    }

    public List<com.example.tourandtravelmanagement.chatbot.Message> getUserMessages(User user) {
        List<com.example.tourandtravelmanagement.chatbot.Message> messages = userMessages.get(user);

        if (messages == null) {
            messages = new ArrayList<>();
            userMessages.put(user, messages);
        }

        return messages;
    }

    public MessagesAdapter getAdapterFromUser(User user) {

        MessagesAdapter adapter = userAdapters.get(user);

        if (adapter == null) {
            adapter = new MessagesAdapter(sActivity, getUserMessages(user));
            userAdapters.put(user, adapter);
        }

        return adapter;
    }

    public void removeAdapterFromUser(User user) {
        userAdapters.remove(user);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static void destroyInstance() {
        if (instance != null) {
            instance.getClient().shutdown();
            instance = null;
        }

    }
}
