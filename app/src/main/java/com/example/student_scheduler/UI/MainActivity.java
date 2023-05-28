package com.example.student_scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.student_scheduler.R;

import java.util.Objects;

/**
 * This is the main activity in the student scheduler.
 */
public class MainActivity extends AppCompatActivity {

    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Button to enter main screen
        Button getStarted = findViewById(R.id.view_term);
        getStarted.setOnClickListener(view -> {
            Intent generateTerms = new Intent(MainActivity.this, TermList.class);
            startActivity(generateTerms);
        });

        // Enable back button in the action bar
        Objects.requireNonNull(getSupportActionBar()).hide();
    }
}


