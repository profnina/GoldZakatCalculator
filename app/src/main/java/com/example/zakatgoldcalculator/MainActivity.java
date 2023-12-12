package com.example.zakatgoldcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar welcomeToolbar;
    Button btn_Start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get toolbar make class label object
        Toolbar welcomeToolbar = (Toolbar) findViewById(R.id.converterToolbar);
        setSupportActionBar(welcomeToolbar);
        getSupportActionBar().setTitle("Gold Zakat Calculator");

        btn_Start = findViewById(R.id.btn_Start);

        btn_Start.setOnClickListener(this);
    }

    //@Override
    public void onClick(View view) {
        if (view == btn_Start) {
            Intent intent = new Intent(this, ConverterActivity.class);

            startActivity(intent);
        }
    }
}