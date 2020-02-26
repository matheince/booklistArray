package com.e.bookarraytest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.e.bookarraytest.fragment.ChatFragment;
import com.e.bookarraytest.fragment.people_fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.main_Activity_bottomNavigationBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_account:
                    case R.id.action_chat:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_Activity_framelayout,new ChatFragment()).commit();
                        return true;
                    case R.id.action_student:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_Activity_framelayout,new people_fragment()).commit();
                        return true;
                }
                return false;

            }
        });



    }
}
