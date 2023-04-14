package com.example.student_scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.student_scheduler.R;

import java.util.Objects;

public class Schedule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

       Button term_list = findViewById(R.id.view_terms);
        term_list.setOnClickListener(view -> {
            Intent intent = new Intent(Schedule.this, TermList.class);
            startActivity(intent);
        });

        Button course_list = findViewById(R.id.view_courses);
        course_list.setOnClickListener(view -> {
            Intent intent = new Intent(Schedule.this, CourseList.class);
            startActivity(intent);
        });

        Button assessment_list = findViewById(R.id.view_assessment);
        assessment_list.setOnClickListener(view -> {
            Intent intent = new Intent(Schedule.this, AssessmentList.class);
            startActivity(intent);
        });

    }

    /**
     * This method handles the click event for the back button in the action bar. When the back
     * button is clicked, `onBackPressed()` is called to go back to the previous activity.
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}