package com.example.movieratingapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.time.LocalDate;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView releaseDate = findViewById(R.id.release);
        LocalDate rel_date = LocalDate.of(2017, 01, 13);
        releaseDate.setText(DateCalculation.findDifference(rel_date, LocalDate.now()));

    }
}