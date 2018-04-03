package com.example.android.bajajhome;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Time;
import java.util.Calendar;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    private Button notAtHome, smartMode, manual,read;
    private TextView lightOne, lightTwo, fan, mode;
    private CountDownTimer timer;

    private final InputStream mmInStream=null;
    private final OutputStream mmOutStream=null;
    private SeekBar seekBar;
    private Switch aSwitch;
    private int intMode=2;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notAtHome = findViewById(R.id.not_at_home);
        smartMode = findViewById(R.id.smart_mode);
        manual = findViewById(R.id.manual);
        lightOne = findViewById(R.id.light_1);
        fan = findViewById(R.id.fan);
        mode = findViewById(R.id.mode);
        settingUpFonts();
        setUpTimer();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        settingUpSeekbar();
        updatingValuesFromSeekbar();
        seekBarSetup();
        reference.child("not_at_home").setValue(0);

        notAtHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode.setText("Current mode: NOT AT HOME");
                Toast.makeText(MainActivity.this,"Current mode: NOT AT HOME",Toast.LENGTH_SHORT).show();
                intMode=0;
                seekBar.setProgress(0);
                aSwitch.setChecked(false);
                reference.child("not_at_home").setValue(1);
                timer.start();
            }
        });
        smartMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode.setText("Current mode: SMART MODE");
                Toast.makeText(MainActivity.this,"Current mode: SMART MODE",Toast.LENGTH_SHORT).show();
                intMode = 1;
                int hours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                if(hours<7 || hours>=18){
                    aSwitch.setChecked(true);
                }
                else{
                    aSwitch.setChecked(false);
                }
                seekBar.setProgress(100);
                reference.child("not_at_home").setValue(0);
            }
        });
        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode.setText("Current mode: MANUAL");
                Toast.makeText(MainActivity.this,"Current mode: MANUAL",Toast.LENGTH_SHORT).show();
                intMode = 2;
                reference.child("not_at_home").setValue(0);
            }
        });

    }
    private void settingUpSeekbar(){
        seekBar = findViewById(R.id.seekbar);
        seekBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        seekBar.getThumb().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
    }
    private void updatingValuesFromSeekbar(){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int t;
                if(i==0){
                    t = 0;
                }
                else if(i<25){
                    t = 1;
                }
                else if(i<50){
                    t = 2;
                }
                else if(i<75){
                    t = 3;
                }
                else if(i<100){
                    t = 4;
                }
                else{
                    t = 5;
                }
                fan.setText("Fan speed: " + t);
                reference.child("fan").setValue(t);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    private void seekBarSetup(){
        aSwitch = findViewById(R.id.a_switch);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    lightOne.setText("Light is: ON");
                    reference.child("light").setValue(1);
                }
                else{
                    lightOne.setText("Light is: OFF");
                    reference.child("light").setValue(0);
                }
            }
        });
    }
    private void settingUpFonts(){
        Typeface typeface = Typeface.createFromAsset(getAssets(),"quicksandreg.ttf");
        notAtHome.setTypeface(typeface);
        smartMode.setTypeface(typeface);
        manual.setTypeface(typeface);
        lightOne.setTypeface(typeface);
        fan.setTypeface(typeface);
        mode.setTypeface(typeface);
    }
    private void setUpTimer(){
        timer = new CountDownTimer(20000,1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, "There is someone in your house.", Toast.LENGTH_LONG ).show();
            }
        };
    }
}
