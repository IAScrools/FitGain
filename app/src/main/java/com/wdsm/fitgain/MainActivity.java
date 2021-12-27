package com.wdsm.fitgain;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvRegister;
    private EditText etLog, etPass;
    private Button bSing;
    private FirebaseAuth firebaseAuth;
    private String login, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();

        etLog = (EditText) findViewById(R.id.etLog);
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
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        }else{
                            Toast.makeText(MainActivity.this, "Failed to login!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.tRegister:
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
        }
    }
}