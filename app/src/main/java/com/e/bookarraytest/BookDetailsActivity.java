package com.e.bookarraytest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class BookDetailsActivity extends AppCompatActivity {
    private EditText mAuthor_editText;
    private EditText mTitle_editText;
    private EditText mISBN_editText;
    private Spinner mCategory_spinner;

    private Button mUpdate_btn;
    private Button mDelete_btn;
    private Button mBack_btn;

    private String key;
    private String author;
    private String title;
    private String category;
    private String isbn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        key = getIntent().getStringExtra("key");
        author = getIntent().getStringExtra("author");
        title = getIntent().getStringExtra("title");
        category = getIntent().getStringExtra("category");
        isbn = getIntent().getStringExtra("isbn");


        mAuthor_editText = (EditText) findViewById(R.id.author_editText);
        mAuthor_editText.setText(author);
        mTitle_editText = (EditText) findViewById(R.id.title_editText);
        mTitle_editText.setText(title);
        mISBN_editText = (EditText) findViewById(R.id.isbn_editText);
        mISBN_editText.setText(isbn);
        mCategory_spinner = (Spinner) findViewById(R.id.book_category_spinner);

        mCategory_spinner.setSelection(getIndex_SpinnerItem(mCategory_spinner, category));


        mUpdate_btn = (Button) findViewById(R.id.update_button);
        mDelete_btn = (Button) findViewById(R.id.delete_button);
        mBack_btn = (Button) findViewById(R.id.back_button);


        mUpdate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setTitle(mTitle_editText.getText().toString());
                book.setAuthor(mAuthor_editText.getText().toString());
                book.setIsbn(mISBN_editText.getText().toString());
                book.setCategory_name(mCategory_spinner.getSelectedItem().toString());

                new FirebaseDatabaseHelper().updateBook(key, book, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Book> books, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {
                        Toast.makeText(BookDetailsActivity.this, "학생자료를" +
                                " 성공적으로 갱신하였습니다!", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });

            }
        });

        mDelete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FirebaseDatabaseHelper().deleteBook(key, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Book> books, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {
                        Toast.makeText(BookDetailsActivity.this, "학생자료를" +
                                " 성공적으로 삭제했습니다!", Toast.LENGTH_LONG).show();
                        finish();
                        return;


                    }
                });

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
    private int getIndex_SpinnerItem (Spinner spinner, String item){
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(item)) {
                index = i;
                break;
            }

        }
        return index;
    }
}