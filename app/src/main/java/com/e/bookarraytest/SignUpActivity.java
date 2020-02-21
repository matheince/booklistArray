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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class SignUpActivity extends AppCompatActivity {

    private EditText id;
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

        id = (EditText) findViewById(R.id.signupActivity_editText_id);
        name = (EditText) findViewById(R.id.signupActivity_editText_nickname);  //학생이름
        password = (EditText) findViewById(R.id.signupActivity_editText_password);
        signup = (Button)findViewById(R.id.signupActivity_editText_ok);
        signup.setBackgroundColor(Color.parseColor(splash_background));


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id.getText().toString() == null || name.getText().toString() == null || password.getText().toString() == null){
                    return;
                }
                FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(id.getText().toString() +"@mathience.com",password.getText().toString())
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Toast myToast =Toast.makeText(getApplicationContext(),"회원가입이 완료되었습니다.", Toast.LENGTH_LONG);
                                    myToast.show();
                                    Book book = new Book();
                                    if(id.getText().toString().contains("ms")) {
                                        book.setAuthor(name.getText().toString());
                                        book.setTitle(id.getText().toString());
                                        book.setIsbn("원장");
                                        book.setCategory_name("W");


                                        //book.setIsbn(.getText().toString());
                                        //book.setCategory_name(mBook_Categories_spinner.getSelectedItem().toString());

                                        String uid = task.getResult().getUser().getUid();
                                        book.setUid(uid);
                                        FirebaseDatabase.getInstance().getReference().child("mathience").push().setValue(book);
                                        Intent intent = new Intent(SignUpActivity.this, BookListActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        book.setAuthor(name.getText().toString()); //+"학부모");
                                        book.setTitle(id.getText().toString());
                                        //book.setIsbn(.getText().toString());
                                        //book.setCategory_name(mBook_Categories_spinner.getSelectedItem().toString());

                                        String uid = task.getResult().getUser().getUid();
                                        book.setUid(uid);
                                        FirebaseDatabase.getInstance().getReference().child("mathience").push().setValue(book);
                                        Intent intent = new Intent(SignUpActivity.this, BookListActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }
                                }

                            }
                        });

            }
        });

    }
}
