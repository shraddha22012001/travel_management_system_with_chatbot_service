package com.example.tourandtravelmanagement.chatbot;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tourandtravelmanagement.R;

import java.util.ArrayList;
import java.util.Locale;

public class dummychatactivity extends AppCompatActivity {

    private static final String TAG = "FragmentGlobalChat";

    private ListView mMessagesListView;
    private EditText mEditTextMessage;
    private com.example.tourandtravelmanagement.chatbot.MessagesAdapter mAdapter;
    TextToSpeech textToSpeech;
    public static int i=0;
    ImageButton image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummychatactivity);

        mMessagesListView=findViewById(R.id.fgc_messages_list_view);

        mAdapter = com.example.tourandtravelmanagement.chatbot.Messenger.getInstance().getGlobalMessagesAdapter();

        mMessagesListView.setAdapter(mAdapter);
        textToSpeech = new TextToSpeech(dummychatactivity.this.getApplicationContext(), (TextToSpeech.OnInitListener) this);

        image=findViewById(R.id.fgc_send_messages_button);

        ImageButton audio= findViewById(R.id.fgc_send_messages_button2);

        audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
                startActivityForResult(intent, 10);
            }
        });

        mEditTextMessage=findViewById(R.id.fgc_send_messages_text_view);

        mEditTextMessage.setOnKeyListener((View.OnKeyListener) this);


    }

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


    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
                onClick(null);

                return true;
            }
        }

        return false;
    }


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
            Toast.makeText(dummychatactivity.this.getApplicationContext(), "Failed to recognize speech!", Toast.LENGTH_LONG).show();
        }
    }

    public void onInit(int i) {

    }
}
