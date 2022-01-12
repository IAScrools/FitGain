package com.wdsm.fitgain.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.wdsm.fitgain.Entities.User;
import com.wdsm.fitgain.R;

import java.util.Objects;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                User u = User.getInstance();

                if(user == null){
                    startActivity(new Intent(Splash.this, Login.class));
                } else {
                    DocumentReference doc = db.collection("Users").document(user.getUid());
                    doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                assert document != null;
                                u.setEMail(Objects.requireNonNull(document.get("Email")).toString());
                                u.setLogin(Objects.requireNonNull(document.get("Login")).toString());
                                startActivity(new Intent(Splash.this, Home.class));
                            }
                        }
                    });
                }
            }
        });

    }
}