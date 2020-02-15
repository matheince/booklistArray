package com.e.bookarraytest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class NewBookActivity extends AppCompatActivity {
    private EditText mAuthor_editText;
    private EditText mTitle_editText;
    private EditText mISBN_editText;
    private Spinner mBook_Categories_spinner;
    private Button mAdd_btn;
    private Button mBack_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_book);
        mAuthor_editText = (EditText) findViewById(R.id.author_editText);
        mTitle_editText = (EditText) findViewById(R.id.title_editText);
        mISBN_editText = (EditText) findViewById(R.id.isbn_editText);
        mBook_Categories_spinner = (Spinner) findViewById(R.id.book_category_spinner);
        mAdd_btn = (Button)findViewById(R.id.update_button);
        mBack_btn = (Button) findViewById(R.id.back_button);

        mAdd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setAuthor(mAuthor_editText.getText().toString());
                book.setTitle(mAuthor_editText.getText().toString());
                book.setIsbn(mISBN_editText.getText().toString());
                book.setCategory_name(mBook_Categories_spinner.getSelectedItem().toString());

                new FirebaseDatabaseHelper().addBook(book, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Book> books, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {
                        Toast.makeText(NewBookActivity.this, "학생자료를" +
                                " 성공적으로 추가하였습니다!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
                mBack_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        return;

                    }
                });
            }
        });

    }
}
