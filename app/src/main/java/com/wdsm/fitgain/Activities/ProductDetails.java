package com.wdsm.fitgain.Activities;

import static com.wdsm.fitgain.Utils.FitnessDataUtils.getUserFirestoreDocument;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.wdsm.fitgain.R;

public class ProductDetails extends AppCompatActivity {

    private EditText bookFullInfo;
    private Button unlockBook;
    private Button bBack;
    private Button logOut;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "Getting Coins";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        firebaseAuth = FirebaseAuth.getInstance();

        Bundle b = getIntent().getExtras();
        String book = b.getString("Book");
        String content = b.getString("Content");
        String title = b.getString("Title");

        bookFullInfo = (EditText) findViewById(R.id.BookFullInfo);
        unlockBook = (Button) findViewById(R.id.UnlockBook);
        bBack = (Button) findViewById(R.id.bBack);
        logOut = (Button) findViewById(R.id.bLogOut);
        bookFullInfo.setKeyListener(null);
        bookFullInfo.setText(book);

        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductDetails.this, Products.class));
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(ProductDetails.this, Login.class));
            }
        });

        unlockBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference docCoins = getUserFirestoreDocument();
                docCoins.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot coins = task.getResult();
                            double userCoins = coins.getDouble("coins");
                            int coinsNeeded = b.getInt("Points");
                            if (userCoins >= coinsNeeded){
                                DocumentReference updateUser = getUserFirestoreDocument();
                                updateUser.update("coins", userCoins - coinsNeeded);
                                updateUser.update("books", FieldValue.arrayUnion(title));
                            }
                            Log.d(TAG, "Coins: " + userCoins);
                        }
                    }
                });
            }
        });
    }
}