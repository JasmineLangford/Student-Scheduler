package com.example.student_scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import com.example.student_scheduler.R;
import com.example.student_scheduler.database.Repository;
import com.example.student_scheduler.entities.Course;
import com.example.student_scheduler.entities.Term;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addSampleData:
                Term term = new Term(0, "Term 1", "01/01/2023", "06/30/2023");
                Repository repository = new Repository(getApplication());
                repository.insert(term);

                Course course = new Course(0, "Chemistry", "01/01/2023", "06/30/2023","In progress", "Mr.Sunny", "590-091-0456","sunny.sunshine@gmail.com","No notes for this course.");
                repository.insert(course);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}