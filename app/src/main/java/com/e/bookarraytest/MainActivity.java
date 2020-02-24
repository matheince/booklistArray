package com.e.bookarraytest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.e.bookarraytest.fragment.people_fragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_Activity_framelayout,new people_fragment()).commit();

    }
}
