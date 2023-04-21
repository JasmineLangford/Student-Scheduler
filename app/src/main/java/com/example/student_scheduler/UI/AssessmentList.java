package com.example.student_scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import com.example.student_scheduler.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AssessmentList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);

        FloatingActionButton assessmentFAB = findViewById(R.id.FAB_add_assessment);
        assessmentFAB.setOnClickListener(view -> {
            Intent intent = new Intent(AssessmentList.this,AssessmentDetails.class);
            startActivity(intent);
        });

    }
}