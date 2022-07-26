package com.example.tourandtravelmanagement.chatbot;

import android.app.Activity;
import android.os.StrictMode;
import android.util.Log;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by dhiogoboza on 25/04/16.
 */
public class Client {


    private static final String TAG = "ChatClient";
    private final User mUser;

    private com.example.tourandtravelmanagement.chatbot.Config config;

    private Socket mSocket;
    private OutputStream mOutput;
    private BufferedReader mBufferedReader;

    private Activity mActivity;

    private boolean appOpened = true;

    private Thread mReceiveMessagesThread;

    private final Runnable mReceiveMessagesRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                Log.i(TAG, "New connection accepted " + mSocket.getInetAddress() + ": " + mSocket.getPort());
                String data;
                do {
                    data = mBufferedReader.readLine();

                    Log.d(TAG, "Data received: " + data);

                    if (data != null) {
                        com.example.tourandtravelmanagement.chatbot.JSONParser.parseString(data);
                    } else {
                        String datais="{\"content\": \"hkgfgg\"}";
                        //JSONParser.parseString(datais);
                        appOpened = false;
                        Utilities.showMessage(mActivity, "Conexão perdida");
                    }

                    //mListener.onMessage();

                    //mBufferedWriter.newLine();
                    //mBufferedWriter.flush();

                } while (appOpened);
                //mSocket.close();

            } catch (IOException ex) {
                Log.e(TAG, "In loop", ex);
            }
        }
    };

    private boolean mConnected;

    public Client(Activity activity, User user) {
        config = new com.example.tourandtravelmanagement.chatbot.Config();

        mActivity = activity;

        mReceiveMessagesThread = new Thread(mReceiveMessagesRunnable);
        mUser = user;
    }

    public void connect() {

//sendData("hi welcome to dora");

    }

    public boolean disconnect() {
        Log.d(TAG, "Closing socket: " + mSocket);

        if (this.mSocket != null && this.mSocket.isConnected()) {
            try {
                mSocket.close();
                mSocket = null;

                return true;
            } catch (IOException ex) {
                Log.e(TAG, "Closing socket", ex);
            }
        }

        return true;
    }

    public void sendData(String data) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url= com.example.tourandtravelmanagement.chatbot.Config.outputoflocation;

        int val= FragmentGlobalChat.i;
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

        nameValuePairs.add(new BasicNameValuePair("msg", String.valueOf(data)));
        nameValuePairs.add(new BasicNameValuePair("count", String.valueOf(val)));
        FragmentGlobalChat.i++;

        String result= null;
        try {
            result = jSOnClassforData.forCallingServerString(url,nameValuePairs);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (result != null) {
            com.example.tourandtravelmanagement.chatbot.JSONParser.parseString(result);
        } else {
            String datais="{\"content\": \"hkgfgg\"}";
            //JSONParser.parseString(datais);
            appOpened = false;
            Utilities.showMessage(mActivity, "Conexão perdida");
        }


//        JSONArray jArray = null;
//        try {
//            jArray = new JSONArray(result.toString());
//            for(int i=0;i<jArray.length();i++) {
//                String alldata = jArray.get(i).toString();
//
//                String datasplit[] = alldata.split("_");
//               // latilongidata.add(alldata);
//
//
//
//
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }



//        try {
//            String toSend = data + "\r\n";
//
//            if (mOutput != null) {
//                mOutput.write(toSend.getBytes("ASCII"));
//                mOutput.flush();
//            } else {
//                throw new IOException("Client not connected");
//            }
//        } catch (IOException e) {
//            Log.e(TAG, "sending message", e);
//
//            Utilities.showMessage(mActivity, "Falha ao enviar a mensagem");
//        }
    }

    public boolean isConnected() {
        return mConnected;
    }

    public void setConfigUsername(String username) {
        config.setUsername(username);
        mUser.setName(username);
    }

    public void setConfigServerAddress(String serverAddress) {
        config.setServerAddress(serverAddress);
    }

    public void setConfigServerPort(int serverPort) {
        config.setServerPort(serverPort);
    }

    public void shutdown() {
        appOpened = false;
    }
}
