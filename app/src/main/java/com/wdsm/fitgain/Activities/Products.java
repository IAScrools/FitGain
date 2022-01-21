package com.wdsm.fitgain.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.wdsm.fitgain.Entities.Book;
import com.wdsm.fitgain.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Products extends AppCompatActivity {
    private String bookTitle;
    private FirebaseFirestore db;
    private Button searchButton;
    private EditText bTitle;
    private ListView booksList;
    private static final String TAG = "Getting book";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        db = FirebaseFirestore.getInstance();

        searchButton = (Button) findViewById(R.id.SearchButton);
        bTitle = (EditText) findViewById(R.id.BookTitle);
        booksList = (ListView) findViewById(R.id.BooksList);

        bTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookTitle = bTitle.getText().toString();
                }
            });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookTitle = bTitle.getText().toString();
                findProducts();
            }
        });
    }


    public void findProducts() {
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<String> booksStringPreview = new ArrayList<>();
        ArrayList<String> booksStringFull = new ArrayList<>();

        if (bookTitle.length() > 2)
        {
            db.collection("Books").whereEqualTo("Title", bookTitle)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                    books.add(document.toObject(Book.class));
                                }
                                for (int i = 0; i < books.size(); i++) {
                                    booksStringPreview.add(books.get(i).toStringPreview());
                                    booksStringFull.add(books.get(i).toStringFull());
                                }

                                Collections.sort(booksStringPreview);
                                Collections.sort(booksStringFull);

                                ArrayAdapter adapter = new ArrayAdapter<String>(Products.this,
                                        android.R.layout.simple_list_item_1, booksStringPreview);
                                booksList.setAdapter(adapter);

                                booksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent i = new Intent(Products.this, ProductDetails.class);
                                        i.putExtra("Book", booksStringFull.get(position));
                                        startActivity(i);
                                    }
                                });
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
        else
        {
            db.collection("Books")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                    books.add(document.toObject(Book.class));
                                }
                                for (int i = 0; i < books.size(); i++) {
                                    booksStringPreview.add(books.get(i).toStringPreview());
                                    booksStringFull.add(books.get(i).toStringFull());
                                }

                                Collections.sort(booksStringPreview);
                                Collections.sort(booksStringFull);

                                ArrayAdapter adapter = new ArrayAdapter<String>(Products.this,
                                        android.R.layout.simple_list_item_1, booksStringPreview);
                                booksList.setAdapter(adapter);

                                booksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent i = new Intent(Products.this, ProductDetails.class);
                                        i.putExtra("Book", booksStringFull.get(position));
                                        startActivity(i);
                                    }
                                });
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
    }
}