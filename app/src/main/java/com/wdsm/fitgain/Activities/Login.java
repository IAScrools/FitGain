package com.wdsm.fitgain.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.wdsm.fitgain.R;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private TextView tvRegister;
    private EditText etLog, etPass;
    private Button bSing;
    private FirebaseAuth firebaseAuth;
    private String login, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        firebaseAuth = FirebaseAuth.getInstance();

        etLog = (EditText) findViewById(R.id.etEmail);
        etPass = (EditText) findViewById(R.id.etPass);
        bSing = (Button) findViewById(R.id.bSign);
        tvRegister = (TextView) findViewById(R.id.tRegister);

        bSing.setOnClickListener(this);
        tvRegister.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bSign:
                login = etLog.getText().toString();
                password = etPass.getText().toString();
                firebaseAuth.signInWithEmailAndPassword(login, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(Login.this, Home.class));
                        }else{
                            Toast.makeText(Login.this, "Failed to login!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.tRegister:
                startActivity(new Intent(Login.this, Register.class));
        }
    }
}