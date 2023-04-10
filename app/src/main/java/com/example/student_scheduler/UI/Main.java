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
public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // View Schedule button
        Button button = findViewById(R.id.view_schedule);
        button.setOnClickListener(view -> {
                Intent intent = new Intent(Main.this, TermList.class);
                startActivity(intent);
        });

        // Hide the action bar
        Objects.requireNonNull(getSupportActionBar()).hide();
    }
}