package com.e.bookarraytest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.e.bookarraytest.fragment.ChatFragment;
import com.e.bookarraytest.fragment.people_fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class BookListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.peoplefragment_recyclerview);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.book_list_Activity_bottomNavigationBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_student:
                        Log.d("leejung", "여기까지왔당.");

                        getSupportFragmentManager().beginTransaction().replace(R.id.book_list_Activity_framelayout, new people_fragment()).commit();
                    case R.id.action_chat:
                        getSupportFragmentManager().beginTransaction().replace(R.id.book_list_Activity_framelayout, new ChatFragment()).commit();

                        return false;
                }
                return false;
            }

        });

        new FirebaseDatabaseHelper().readBooks(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Book> books, List<String> keys) {


                new Recyclerview_Config().setConfig(mRecyclerView, BookListActivity.this, books, keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

    }
}
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.bottom_navigation_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_account:
                startActivity(new Intent(this, NewBookActivity.class));
                return true;
            case R.id.action_student:
                Log.d("leejung","Here I am");
                getSupportFragmentManager().beginTransaction().replace(R.id.book_list_Activity_framelayout, new people_fragment()).commit();
                return true;
            case R.id.action_chat:
                getSupportFragmentManager().beginTransaction().replace(R.id.book_list_Activity_framelayout, new ChatFragment()).commit();
                return true;

        }
        return super.onOptionsItemSelected(item);
   */


