package com.example.student_scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.student_scheduler.R;
import com.example.student_scheduler.database.Repository;
import com.example.student_scheduler.entities.Course;

import java.util.Objects;

/**
 * This is the main activity in the student scheduler.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Test Data
        Course course1 = new Course(0,"Chemistry","01/01/2023",
                "02/28/2023", "In Progress","Mr.Sunshine",
                "244-245-5644","sunny@wgu.edu"," ");

        Repository repository = new Repository(getApplication());
        repository.insert(course1);

        // View Schedule button
        Button button = findViewById(R.id.view_schedule);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Schedule.class);
            startActivity(intent);
        });

        // Hide the action bar
        Objects.requireNonNull(getSupportActionBar()).hide();
    }
}