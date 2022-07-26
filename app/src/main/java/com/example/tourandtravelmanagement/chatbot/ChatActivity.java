package com.example.tourandtravelmanagement.chatbot;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.example.tourandtravelmanagement.R;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

/**
 * Created by dhiogoboza on 27/04/16.
 */
public class ChatActivity extends FragmentActivity implements  View.OnKeyListener, RecognitionListener {

    private ListView mMessagesListView;
    private User mUser;
    TextToSpeech textToSpeech;
    private EditText mEditTextMessage;
    private MessagesAdapter mAdapter;

    Context ctx;
    private Intent recognizerIntent;
    public ChatActivity() {
        ctx = getApplicationContext();
    }
    private SpeechRecognizer speech = null;
    private static final int REQUEST_RECORD_PERMISSION = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_global_chat_fragment);

        speech = SpeechRecognizer.createSpeechRecognizer(this);

        speech.setRecognitionListener(this);



        //mRootView = (ViewGroup) inflater.inflate(R.layout.list_content, null);
        RelativeLayout fl = (RelativeLayout) findViewById(R.id.fgc_main_layout);
        fl.setLayoutParams(new FrameLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

        int userPosition = getIntent().getIntExtra(com.example.tourandtravelmanagement.chatbot.FragmentUsers.USER_POSITION, 0);
        mUser = Messenger.getInstance().getUsers().get(userPosition);

        setTitle(mUser.getName());

        mAdapter = Messenger.getInstance().getAdapterFromUser(mUser);

        mMessagesListView = (ListView) findViewById(R.id.fgc_messages_list_view);
        mMessagesListView.setAdapter(mAdapter);

       // findViewById(R.id.fgc_send_messages_button).setOnClickListener(this);

        ImageButton audio=findViewById(R.id.fgc_send_messages_button2);
        ImageButton sendmsg=findViewById(R.id.fgc_send_messages_button);
        mEditTextMessage = (EditText) findViewById(R.id.fgc_send_messages_text_view);

        mEditTextMessage.setOnKeyListener(this);

        sendmsg.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        com.example.tourandtravelmanagement.chatbot.Message message = new com.example.tourandtravelmanagement.chatbot.Message();
        message.setSender(Messenger.getInstance().getUser());
        message.setContent(mEditTextMessage.getText().toString());
        message.setPropertyMessage(true);

        mAdapter.add(message);

        mAdapter.notifyDataSetChanged();

        Messenger.getInstance().sendMessage(mEditTextMessage.getText().toString(), mUser);

        mEditTextMessage.setText("");

    }
});

        audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                        "en");
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
                ActivityCompat.requestPermissions
                        (ChatActivity.this,
                                new String[]{Manifest.permission.RECORD_AUDIO},
                                REQUEST_RECORD_PERMISSION);
            }
        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Messenger.getInstance().removeAdapterFromUser(mUser);
    }

//    @Override
//    public void onClick(View view) {
//        Message message = new Message();
//        message.setSender(Messenger.getInstance().getUser());
//        message.setContent(mEditTextMessage.getText().toString());
//        message.setPropertyMessage(true);
//
//        mAdapter.add(message);
//
//        mAdapter.notifyDataSetChanged();
//
//        Messenger.getInstance().sendMessage(mEditTextMessage.getText().toString(), mUser);
//
//        mEditTextMessage.setText("");
//    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
              //  onClick(null);

                return true;
            }
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    speech.startListening(recognizerIntent);
                } else {
                    Toast.makeText(ChatActivity.this, "Permission Denied!", Toast
                            .LENGTH_SHORT).show();
                }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (speech != null) {
            speech.destroy();

        }
    }
    @Override
    public void onBeginningOfSpeech() {


    }
    @Override
    public void onBufferReceived(byte[] buffer) {

    }
    @Override
    public void onEndOfSpeech() {


    }
    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);

        mEditTextMessage.setText(errorMessage);

    }
    @Override
    public void onEvent(int arg0, Bundle arg1) {

    }
    @Override
    public void onPartialResults(Bundle arg0) {

    }
    @Override
    public void onReadyForSpeech(Bundle arg0) {

    }
    @Override
    public void onResults(Bundle results) {

        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
        for (String result : matches)
            text += result + "\n";
        mEditTextMessage.setText(text);
    }
    @Override
    public void onRmsChanged(float rmsdB) {

    }
    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }
}