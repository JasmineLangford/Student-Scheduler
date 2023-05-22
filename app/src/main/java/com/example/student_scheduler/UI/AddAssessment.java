package com.example.student_scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.student_scheduler.R;
import com.example.student_scheduler.database.Repository;
import com.example.student_scheduler.entities.Assessment;

import java.util.Objects;

public class AddAssessment extends AppCompatActivity {

    EditText addAssessmentTitle;
    EditText addAssessmentEnd;

    String assessmentTitleEdit;
    String assessmentEndEdit;

    int assessmentID;
    int courseID;
    Assessment assessment;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);

        courseID = getIntent().getIntExtra("course_id", courseID);
        assessmentID = getIntent().getIntExtra("assessment_id",-1);

        // Editable fields
        addAssessmentTitle = findViewById(R.id.assessment_title_edit);
        addAssessmentEnd = findViewById(R.id.assessment_end_edit);

        assessmentTitleEdit = getIntent().getStringExtra("assessment_title");
        assessmentEndEdit = getIntent().getStringExtra("assessment_end");

        addAssessmentTitle.setText(assessmentTitleEdit);
        addAssessmentEnd.setText(assessmentEndEdit);

        // Cancel and go back to course details
        Button cancel = findViewById(R.id.cancel_button);
        cancel.setOnClickListener(view -> finish());

        // Dropdown selection for assessment type
        Spinner assessmentTypeSpinner = findViewById(R.id.assessment_type_spinnner);
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.type, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assessmentTypeSpinner.setAdapter(typeAdapter);

        // Save fields
        repository = new Repository(getApplication());
        Button saveAssessment = findViewById(R.id.add_assessment);
        saveAssessment.setOnClickListener(view -> {
            assessment = new Assessment(0,courseID,
                    addAssessmentTitle.getText().toString(),
                    assessmentTypeSpinner.getSelectedItem().toString(),
                    addAssessmentEnd.getText().toString());
            repository.insert(assessment);

            // Message to confirm add and back to term details
            Toast.makeText(getApplication(), "Assessment was successfully added.",
                    Toast.LENGTH_SHORT).show();
            finish();
        });
        // Display toolbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
    /**
     * This method handles the click event for the back button in the action bar. When the back
     * button is clicked, `onBackPressed()` is called to go back to the previous activity.
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}