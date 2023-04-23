package com.example.student_scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

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
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button getStarted = findViewById(R.id.view_term);
//        Term term2 = new Term(0, "Term 2", "07/01/2023", "12/30/2023");
//        Repository repository = new Repository(getApplication());
//        repository.insert(term2);

        getStarted.setOnClickListener(view -> {
            Intent generateTerms = new Intent(MainActivity.this, TermList.class);
            startActivity(generateTerms);
        });
        // Hide the action bar
        Objects.requireNonNull(getSupportActionBar()).hide();
    }
}



//        // View terms on next screen
//        Button getStartedButton = findViewById(R.id.view_term);
//        getStartedButton.setOnClickListener(view -> {
//            Intent intent = new Intent(MainActivity.this, TermList.class);
//            startActivity(intent);
//        });


