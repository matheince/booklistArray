package com.e.bookarraytest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.e.bookarraytest.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import static java.lang.Boolean.getBoolean;

public class IntroActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        linearLayout = findViewById(R.id.splashactivity_layout);


        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        mFirebaseRemoteConfig.setDefaults(R.xml.default_config);


        mFirebaseRemoteConfig.fetch(0)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(IntroActivity.this, "Fetch and activate succeeded",
                                    Toast.LENGTH_SHORT).show();
                            mFirebaseRemoteConfig.activateFetched();  // Remote_Config 적용함....
                            //startActivity(Intent(IntroActivity.this, LoginActivity.class));
                            displayMessage();

                        } else {
                            Toast.makeText(IntroActivity.this, "Fetch failed",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                });

    }

    void displayMessage() {
        String splash_background = mFirebaseRemoteConfig.getString("splash_background");
        boolean caps = mFirebaseRemoteConfig.getBoolean("splash_message_caps");
        String splash_messages = mFirebaseRemoteConfig.getString("splash_message");
        linearLayout.setBackgroundColor(Color.parseColor(splash_background));


        if (caps) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(splash_messages)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                           // startActivity(new Intent(IntroActivity.this, LoginActivity.class));
                            finish();
                        }
                    });

            builder.create().show();


        }
        else {
            startActivity(new Intent(IntroActivity.this, LoginActivity.class));
            finish();
        }
    }
}
