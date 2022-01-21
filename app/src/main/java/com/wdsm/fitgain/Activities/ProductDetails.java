package com.wdsm.fitgain.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.wdsm.fitgain.R;

public class ProductDetails extends AppCompatActivity {

    private EditText bookFullInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Bundle b = getIntent().getExtras();
        String book = b.getString("Book");

        bookFullInfo = (EditText) findViewById(R.id.BookFullInfo);

        bookFullInfo.setText(book);
    }
}