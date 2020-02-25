package com.e.bookarraytest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.e.bookarraytest.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class SignUpActivity extends AppCompatActivity {

    private EditText email;
    private EditText name;
    private EditText password;
    private Button signup;
    private String splash_background;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        splash_background = mFirebaseRemoteConfig.getString(getString(R.string.rc_buttn_backgroundcolor));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(Color.parseColor(splash_background));
        }

        email = (EditText) findViewById(R.id.signupActivity_editText_id);
        name = (EditText) findViewById(R.id.signupActivity_editText_nickname);  //학생이름
        password = (EditText) findViewById(R.id.signupActivity_editText_password);
        signup = (Button)findViewById(R.id.signupActivity_editText_ok);
        signup.setBackgroundColor(Color.parseColor(splash_background));


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString() == null || name.getText().toString() == null || password.getText().toString() == null){
                    return;
                }
                FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(email.getText().toString() +"@mathience.com",password.getText().toString())
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Toast myToast =Toast.makeText(getApplicationContext(),"회원가입이 완료되었습니다.", Toast.LENGTH_LONG);
                                    myToast.show();
                                    UserModel userModel = new UserModel();
                                        String currentUid = task.getResult().getUser().getUid();
                                        userModel.email = email.getText().toString();
                                        userModel.username = name.getText().toString();
                                        userModel.currentUid = currentUid;
                                        FirebaseDatabase.getInstance().getReference().child("mathience").child("users").push().setValue(userModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                                    }

                                }

                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpActivity.this, "신입회원 가입에 실패하였습니다."+e.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });

            }
        });

    }
}
