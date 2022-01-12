package com.wdsm.fitgain.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.wdsm.fitgain.R;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private EditText etEmail, etLogin, etPass;
    private Button bSing, bBack;
    private FirebaseAuth firebaseAuth;
    private String email, login, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        etEmail = (EditText) findViewById(R.id.etEmail);
        etLogin = (EditText) findViewById(R.id.etLogin);
        etPass = (EditText) findViewById(R.id.etPass);
        bSing = (Button) findViewById(R.id.bSign);
        bBack = (Button) findViewById(R.id.bBack);

        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(Register.this, Login.class));
            }
        });

        bSing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString();
                login = etLogin.getText().toString();
                password = etPass.getText().toString();

                if(email.isEmpty()){
                    etEmail.setError("Email is required");
                    etEmail.requestFocus();
                    return;
                }
                if(login.isEmpty()){
                    etLogin.setError("Email is required");
                    etLogin.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    etEmail.setError("Please enter a correct email");
                    etEmail.requestFocus();
                    return;
                }
                if(password.isEmpty()){
                    etPass.setError("Password is required");
                    etPass.requestFocus();
                    return;
                }
                if(password.length() < 7){
                    etPass.setError("Min password length is 7");
                    etPass.requestFocus();
                    return;
                }
                CreateNewUser();
            }
        });
    }

    private void CreateNewUser(){
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Map<String, Object> userMap = new HashMap<>();
                    userMap.put("Email", email);
                    userMap.put("Login", login);
                    DocumentReference db = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    db.set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Register.this,"Register succeeded!",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Register.this,"Register failed!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    Intent intent = new Intent(Register.this, Home.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Register.this, "Register failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}