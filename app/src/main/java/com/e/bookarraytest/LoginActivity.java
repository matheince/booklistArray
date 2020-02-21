package com.e.bookarraytest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class LoginActivity extends AppCompatActivity {
    private Button login;
    private Button signup;
    private EditText id;
    private EditText password;
    private String name;


    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private FirebaseAuth mauth;
    private FirebaseAuth.AuthStateListener mauthStateListener; // 로그인 여부
    private ProgressBar mprogressbar;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference mReference;
    private List<Book> books = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private String splash_background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        splash_background = mFirebaseRemoteConfig.getString(getString(R.string.rc_buttn_backgroundcolor));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(Color.parseColor(splash_background));
        }

        mauth = FirebaseAuth.getInstance();

        mauth.signOut();

        String splash_button = mFirebaseRemoteConfig.getString(getString(R.string.rc_buttn_backgroundcolor));

        signup = (Button)findViewById(R.id.signupButton);

        id = (EditText) findViewById(R.id.emailEdittext);
        password = (EditText) findViewById(R.id.passwordEdittext);
        //name = findViewById(R.id.signupActivity_editText_nickname);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_books);

        login = (Button) findViewById(R.id.emailloginButton);

        login.setBackgroundColor(Color.parseColor(splash_button));



        mprogressbar = (ProgressBar)findViewById(R.id.progressBar);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty()) return;
                inProgress(true);

               // final String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                FirebaseAuth.getInstance().signInWithEmailAndPassword(id.getText().toString()+"@mathience.com", password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                            //}
                        //})

                        //.addOnSuccessListener(new OnSuccessListener<AuthResult>() {

                          //  @Override
                           // public void onSuccess(AuthResult authResult) {
                                Log.d("leejung","여기까지");
                                Toast.makeText(LoginActivity.this, "정상적으로 로그인되었습니다.", Toast.LENGTH_LONG).show();
                                Book book = new Book();

                                if(id.getText().toString().contains("ms")) {
                                    book.setAuthor(name);
                                    book.setTitle(id.getText().toString());
                                    book.setIsbn("선생님");
                                    //book.setCategory_name("W");


                                    //book.setIsbn(.getText().toString());
                                    //book.setCategory_name(mBook_Categories_spinner.getSelectedItem().toString());

                                    //String uid = task.getUser().getUid();
                                    //book.setUid(uid);
                                    FirebaseDatabase.getInstance().getReference().child("mathience").push().setValue(book);
                                    Intent intent = new Intent(LoginActivity.this, BookListActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    book.setAuthor(name);
                                    book.setTitle(id.getText().toString());
                                    book.setIsbn("학생");
                                    //book.setCategory_name("W");


                                    //book.setIsbn(.getText().toString());
                                    //book.setCategory_name(mBook_Categories_spinner.getSelectedItem().toString());

                                    //String uid = authResult.getUser().getUid();
                                    //book.setUid(uid);
                                    FirebaseDatabase.getInstance().getReference().child("mathience").push().setValue(book);
                                    Intent intent = new Intent(LoginActivity.this, BookListActivity.class);
                                    startActivity(intent);
                                    finish();

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

        signup.setOnClickListener(new View.OnClickListener() {
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
            signup.setEnabled(false);

        }else
        {
            mprogressbar.setVisibility(View.GONE);
            login.setEnabled((true));
            signup.setEnabled(true);
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