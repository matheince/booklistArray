package com.e.bookarraytest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class LoginActivity extends AppCompatActivity {
    private Button login;
    private Button signUp;
    private EditText id;
    private EditText password;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private FirebaseAuth mauth;
    private FirebaseAuth.AuthStateListener mauthStateListener; // 로그인 여부
    private ProgressBar mprogressbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        mauth = FirebaseAuth.getInstance();
        mauth.signOut();

        String splash_button = mFirebaseRemoteConfig.getString(getString(R.string.rc_buttn_backgroundcolor));

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor(splash_button));
        }*/
        id = (EditText) findViewById(R.id.emailEdittext);
        password = (EditText) findViewById(R.id.passwordEdittext);


        login = (Button) findViewById(R.id.emailloginButton);
        signUp = (Button) findViewById(R.id.signupButton);
        login.setBackgroundColor(Color.parseColor(splash_button));
        signUp.setBackgroundColor(Color.parseColor(splash_button));

        mprogressbar = (ProgressBar)findViewById(R.id.progressBar);


        /*login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("leejung22", "요가");
                *//*FirebaseAuth.getInstance()
                        .signInWithEmailAndPassword(id.getText().toString() + "@mathience.com", password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                Book book = new Book();
                                Toast.makeText(LoginActivity.this, "로그인에 성공했습니다.",Toast.LENGTH_LONG).show();
                                if (id.getText().toString().contains("ms")) {
                                    //book.setAuthor(name.getText().toString()); //+"학부모");
                                    book.setTitle(id.getText().toString());
                                    //book.setIsbn(.getText().toString());
                                    //book.setCategory_name(mBook_Categories_spinner.getSelectedItem().toString());

                                    String uid = task.getResult().getUser().getUid();
                                    FirebaseDatabase.getInstance().getReference().child("teacher").child(uid).setValue(book);
                                    Intent intent = new Intent(LoginActivity.this, MainStaffActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    //book.setAuthor(name.getText().toString()); //+"학부모");
                                    book.setTitle(id.getText().toString());
                                    //book.setIsbn(.getText().toString());
                                    //book.setCategory_name(mBook_Categories_spinner.getSelectedItem().toString());

                                    String uid = task.getResult().getUser().getUid();
                                    FirebaseDatabase.getInstance().getReference().child("student").child(uid).setValue(book);
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();

                                }
                            }
                        });

                //loginEvent();
            }
        });*/

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty()) return;
                inProgress(true);
                mauth.signInWithEmailAndPassword(id.getText().toString() + "@mathience.com", password.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(LoginActivity.this, "정상적으로 로그인되었습니다.", Toast.LENGTH_LONG).show();
                                if (id.getText().toString().contains("ms")) {

                                    Intent intent = new Intent(LoginActivity.this, BookListActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                    return;
                                } else {
                                    Intent intent = new Intent(LoginActivity.this, BookListActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                    return;
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        inProgress(false);
                        Toast.makeText(LoginActivity.this, "로그인에 실패하였습니다."+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }

        });


    }

    private void inProgress(boolean x){
        if(x){
            mprogressbar.setVisibility(View.VISIBLE);
            login.setEnabled((false));
            signUp.setEnabled(false);

        }else
        {
            mprogressbar.setVisibility(View.GONE);
            login.setEnabled((true));
            signUp.setEnabled(true);
        }
    }
    private boolean isEmpty(){
        if(TextUtils.isEmpty(id.getText().toString())){
            id.setError("REQUIRED!");
            return true;
        }
        if(TextUtils.isEmpty(password.getText().toString())){
            password.setError("REQUIRED!!!");
            return true;
        }
        return false;
    }

}