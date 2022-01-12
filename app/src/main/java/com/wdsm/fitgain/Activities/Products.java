package com.wdsm.fitgain.Activities;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.wdsm.fitgain.Entities.Book;
import com.wdsm.fitgain.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Products extends AppCompatActivity {
    private EditText etBookTitle;
    private String bookTitle;
    private Button bSing;
    private FirebaseFirestore db;
    private static final String TAG = "Getting book";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        db = FirebaseFirestore.getInstance();

        bSing = (Button) findViewById(R.id.bSign);

        bSing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bookTitle = etBookTitle.getText().toString();

                }
            });

        db.collection("Books").whereEqualTo(bookTitle, "Title")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.toObject(Book.class));
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }
            });

//
//        Query bookQuery = ref.equalTo(bookTitle, "Title");
//        System.out.println(bookQuery);
//
//        Map<String, Object> bookMap = new HashMap<>();

    }
}