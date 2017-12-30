package com.example.android.bajajhome;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private Button notAtHome, smartMode, manual,read;
    private TextView lightOne, lightTwo, fan, mode;

    private final InputStream mmInStream=null;
    private final OutputStream mmOutStream=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notAtHome = findViewById(R.id.not_at_home);
        smartMode = findViewById(R.id.smart_mode);
        manual = findViewById(R.id.manual);
        lightOne = findViewById(R.id.light_1);
        lightTwo = findViewById(R.id.light_2);
        fan = findViewById(R.id.fan);
        mode = findViewById(R.id.mode);



        notAtHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode.setText("Current mode: NOT AT HOME");
                Toast.makeText(MainActivity.this,"Current mode: NOT AT HOME",Toast.LENGTH_SHORT).show();
            }
        });
        smartMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode.setText("Current mode: SMART MODE");
                Toast.makeText(MainActivity.this,"Current mode: SMART MODE",Toast.LENGTH_SHORT).show();
            }
        });
        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode.setText("Current mode: MANUAL");
                Toast.makeText(MainActivity.this,"Current mode: MANUAL",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
