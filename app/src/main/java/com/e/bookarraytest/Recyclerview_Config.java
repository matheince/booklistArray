package com.e.bookarraytest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class Recyclerview_Config {
    private Context mContext;

    private BooksAdapter mBooksAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<Book> books, List<String> keys){
        mContext = context;
        mBooksAdapter = new BooksAdapter(books, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mBooksAdapter);


    }

    class BookItemView extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private TextView mAuthor;
        private TextView mISBN;
        private TextView mCategory;

        private String key;

        public BookItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.book_list_item, parent, false));
            mTitle = (TextView) itemView.findViewById(R.id.title_textview);
            mAuthor = (TextView) itemView.findViewById(R.id.author_txtview);
            mCategory = (TextView) itemView.findViewById(R.id.category_txtview);
            mISBN = (TextView) itemView.findViewById(R.id.isbn_txtview);

        }

        public void bind(Book book, String key) {
            mTitle.setText(book.getTitle());
            mAuthor.setText(book.getAuthor());
            mCategory.setText(book.getCategory_name());
            mISBN.setText(book.getIsbn());

            this.key = key;

        }
    }
    class BooksAdapter extends RecyclerView.Adapter<BookItemView>{
        private List<Book> mBookList;
        private List<String> mKeys;
        public BooksAdapter(List<Book> mBookList, List<String> mKeys){
            this.mBookList = mBookList;
            this.mKeys = mKeys;

        }

        @NonNull
        @Override
        public BookItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BookItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull BookItemView holder, int position) {
            holder.bind(mBookList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mBookList.size();
        }
    }

}

