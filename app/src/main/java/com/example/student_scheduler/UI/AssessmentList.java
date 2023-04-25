package com.example.student_scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import com.example.student_scheduler.R;
import com.example.student_scheduler.database.Repository;
import com.example.student_scheduler.entities.Assessment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

/**
 * This activity provides the user with a list of available assessments displayed in a recyclerview,
 * which pulls data from the database. Upon selection of an assessment, details for that term will
 * be populated on the next screen.
 */
public class AssessmentList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);

        // Display current assessments associated with the selected course
        RecyclerView assessmentListRecycler = findViewById(R.id.assessment_list_recycler);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        assessmentListRecycler.setAdapter(assessmentAdapter);
        assessmentListRecycler.setLayoutManager(new LinearLayoutManager(this));
        Repository repository = new Repository(getApplication());
        List<Assessment> allAssessments = repository.getAllAssessments();
        assessmentAdapter.setAssessments(allAssessments);

        // Display toolbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // FAB navigates to add a new assessment
        FloatingActionButton assessmentFAB = findViewById(R.id.FAB_add_assessment);
        assessmentFAB.setOnClickListener(view -> {
            Intent intent = new Intent(AssessmentList.this,AddAssessment.class);
            startActivity(intent);
        });
    }
}