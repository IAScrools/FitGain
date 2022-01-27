package com.wdsm.fitgain.Activities;

import static com.wdsm.fitgain.Utils.FitnessDataUtils.getUserFirestoreDocument;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.wdsm.fitgain.Entities.Book;
import com.wdsm.fitgain.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class UnlockedBooks extends AppCompatActivity {

    private Button bBack;
    private TextView logOut;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;

    private static final String TAG = "Getting book";
    private ListView booksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlocked_books);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        booksList = (ListView) findViewById(R.id.List);
        bBack = (Button) findViewById(R.id.bBack);
        logOut = (TextView) findViewById(R.id.tvLogOut);

        bBack.setOnClickListener(v -> startActivity(new Intent(UnlockedBooks.this, Home.class)));

        logOut.setOnClickListener(v -> {
            firebaseAuth.signOut();
            startActivity(new Intent(UnlockedBooks.this, Login.class));
        });

        getAllUserBooks();
    }

    private void getAllUserBooks(){
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<String> booksStringPreview = new ArrayList<>();
        ArrayList<String> booksStringFull = new ArrayList<>();
        ArrayList<String> booksStringContent = new ArrayList<>();

        getUserFirestoreDocument().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot user = task.getResult();
                    List<String> list = (List<String>) user.get("books");
                    if(list == null) {
                        return;
                    } else if (list.size() > 0) {
                        db.collection("Books")
                                .whereIn("Title", list)
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(QueryDocumentSnapshot document: task.getResult()){
                                        books.add(document.toObject(Book.class));
                                    }
                                    for (int i = 0; i < books.size(); i++) {
                                        booksStringPreview.add(books.get(i).toStringPreview());
                                        booksStringFull.add(books.get(i).toStringFull());
                                        booksStringContent.add(books.get(i).getContent());
                                    }
                                    Collections.sort(booksStringPreview);
                                    Collections.sort(booksStringFull);
                                    Collections.sort(booksStringContent);

                                    ArrayAdapter adapter = new ArrayAdapter<String>(UnlockedBooks.this,
                                            android.R.layout.simple_list_item_1, booksStringPreview);
                                    booksList.setAdapter(adapter);

                                    booksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            Intent i = new Intent(UnlockedBooks.this, UnlockBook.class);
                                            i.putExtra("Content", booksStringContent.get(position));
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
        });
    }
}