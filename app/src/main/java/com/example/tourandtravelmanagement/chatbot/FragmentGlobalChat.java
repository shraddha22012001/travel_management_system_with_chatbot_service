package com.example.tourandtravelmanagement.chatbot;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.tourandtravelmanagement.R;

import java.util.ArrayList;
import java.util.Locale;

public class FragmentGlobalChat extends com.example.tourandtravelmanagement.chatbot.ChatFragment implements View.OnClickListener, View.OnKeyListener, TextToSpeech.OnInitListener {

    private static final String TAG = "FragmentGlobalChat";

    private ListView mMessagesListView;
    private EditText mEditTextMessage;
    private com.example.tourandtravelmanagement.chatbot.MessagesAdapter mAdapter;
    TextToSpeech textToSpeech;
    public static int i=0;
    //Button btn1,btn2,btn3,btn4,btn5,btn6,btn7;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_global_chat_fragment, container, false);
        mMessagesListView = (ListView) view.findViewById(R.id.fgc_messages_list_view);

        mAdapter = com.example.tourandtravelmanagement.chatbot.Messenger.getInstance().getGlobalMessagesAdapter();

        mMessagesListView.setAdapter(mAdapter);
        textToSpeech = new TextToSpeech(getActivity().getApplicationContext(), this);

        view.findViewById(R.id.fgc_send_messages_button).setOnClickListener(this);


        ImageButton audio= view.findViewById(R.id.fgc_send_messages_button2);
        Button btn1= view.findViewById(R.id.update);
        Button btn2= view.findViewById(R.id.security);
        Button btn3= view.findViewById(R.id.findroute);
        Button btn4= view.findViewById(R.id.mainspot);
        Button btn5= view.findViewById(R.id.findparks);
        Button btn6= view.findViewById(R.id.findhotels);
        Button btn7= view.findViewById(R.id.findresort);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mEditTextMessage.setText("Update Ptofile");

                com.example.tourandtravelmanagement.chatbot.Message message = new com.example.tourandtravelmanagement.chatbot.Message();
                message.setSender(com.example.tourandtravelmanagement.chatbot.Messenger.getInstance().getUser());
                message.setContent("Update Profile");
                message.setPropertyMessage(true);

                mAdapter.add(message);

                mAdapter.notifyDataSetChanged();

                com.example.tourandtravelmanagement.chatbot.Messenger.getInstance().sendMessage("Update Profile");

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mEditTextMessage.setText("Set Security Questions");

                com.example.tourandtravelmanagement.chatbot.Message message = new com.example.tourandtravelmanagement.chatbot.Message();
                message.setSender(com.example.tourandtravelmanagement.chatbot.Messenger.getInstance().getUser());
                message.setContent("Set Security Questions");
                message.setPropertyMessage(true);

                mAdapter.add(message);

                mAdapter.notifyDataSetChanged();

                com.example.tourandtravelmanagement.chatbot.Messenger.getInstance().sendMessage("Set Security Questions");
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
  //              mEditTextMessage.setText("Find Route");

                com.example.tourandtravelmanagement.chatbot.Message message = new com.example.tourandtravelmanagement.chatbot.Message();
                message.setSender(com.example.tourandtravelmanagement.chatbot.Messenger.getInstance().getUser());
                message.setContent("Find Route");
                message.setPropertyMessage(true);

                mAdapter.add(message);

                mAdapter.notifyDataSetChanged();

                com.example.tourandtravelmanagement.chatbot.Messenger.getInstance().sendMessage("Find Route");
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
         //       mEditTextMessage.setText("Find main Spots");

                com.example.tourandtravelmanagement.chatbot.Message message = new com.example.tourandtravelmanagement.chatbot.Message();
                message.setSender(com.example.tourandtravelmanagement.chatbot.Messenger.getInstance().getUser());
                message.setContent("Main Spots");
                message.setPropertyMessage(true);

                mAdapter.add(message);

                mAdapter.notifyDataSetChanged();

                com.example.tourandtravelmanagement.chatbot.Messenger.getInstance().sendMessage("Main Spots");
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // mEditTextMessage.setText("Find Parks");

                com.example.tourandtravelmanagement.chatbot.Message message = new com.example.tourandtravelmanagement.chatbot.Message();
                message.setSender(com.example.tourandtravelmanagement.chatbot.Messenger.getInstance().getUser());
                message.setContent("Find Parks");
                message.setPropertyMessage(true);

                mAdapter.add(message);

                mAdapter.notifyDataSetChanged();

                com.example.tourandtravelmanagement.chatbot.Messenger.getInstance().sendMessage("Find Parks");
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    mEditTextMessage.setText("Find Hotels");

                com.example.tourandtravelmanagement.chatbot.Message message = new com.example.tourandtravelmanagement.chatbot.Message();
                message.setSender(com.example.tourandtravelmanagement.chatbot.Messenger.getInstance().getUser());
                message.setContent("Find Hotels");
                message.setPropertyMessage(true);

                mAdapter.add(message);

                mAdapter.notifyDataSetChanged();

                com.example.tourandtravelmanagement.chatbot.Messenger.getInstance().sendMessage("Find Hotels");
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   mEditTextMessage.setText("Find Resort");

                com.example.tourandtravelmanagement.chatbot.Message message = new com.example.tourandtravelmanagement.chatbot.Message();
                message.setSender(com.example.tourandtravelmanagement.chatbot.Messenger.getInstance().getUser());
                message.setContent("Find Resorts");
                message.setPropertyMessage(true);

                mAdapter.add(message);

                mAdapter.notifyDataSetChanged();

                com.example.tourandtravelmanagement.chatbot.Messenger.getInstance().sendMessage("Find Resorts");

            }
        });



        audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
                startActivityForResult(intent, 10);
            }
        });


        mEditTextMessage = (EditText) view.findViewById(R.id.fgc_send_messages_text_view);

        mEditTextMessage.setOnKeyListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        com.example.tourandtravelmanagement.chatbot.Message message = new com.example.tourandtravelmanagement.chatbot.Message();
        message.setSender(com.example.tourandtravelmanagement.chatbot.Messenger.getInstance().getUser());
        message.setContent(mEditTextMessage.getText().toString());
        message.setPropertyMessage(true);

        mAdapter.add(message);

        mAdapter.notifyDataSetChanged();

        com.example.tourandtravelmanagement.chatbot.Messenger.getInstance().sendMessage(mEditTextMessage.getText().toString());

        mEditTextMessage.setText("");
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
                onClick(null);

                return true;
            }
        }

        return false;
    }

    @Override
    public String getTAG() {
        return TAG;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case 10:
                    ArrayList<String> intFound = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    String valueis=intFound.get(0);
                    mEditTextMessage.setText(valueis);
                    onClick(null);
                    break;

            }
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Failed to recognize speech!", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onInit(int i) {

    }
}
