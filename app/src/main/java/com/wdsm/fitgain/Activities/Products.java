package com.wdsm.fitgain.Activities;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.wdsm.fitgain.R;

import java.util.HashMap;
import java.util.Map;

public class Products extends AppCompatActivity {
    private EditText etBookTitle;
    private String bookTitle;
    private Button bSing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("Books");

        bSing = (Button) findViewById(R.id.bSign);

        bSing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    bookTitle = etBookTitle.getText().toString();
                }
            });

        Query bookQuery = ref.equalTo(bookTitle);
        Map<String, Object> bookMap = new HashMap<>();

    }
}