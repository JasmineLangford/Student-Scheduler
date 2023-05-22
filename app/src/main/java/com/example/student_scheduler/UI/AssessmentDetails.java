package com.example.student_scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.student_scheduler.R;
import com.example.student_scheduler.database.Repository;
import com.example.student_scheduler.entities.Assessment;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.Objects;

public class AssessmentDetails extends AppCompatActivity {

    EditText assessment_title;
    EditText assessment_end;
    Spinner assessmentTypeSpinner;

    String assessmentTitle;
    String assessmentEnd;

    int assessmentID;
    int courseID;
    Assessment assessment;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        repository = new Repository(getApplication());

        assessmentID = getIntent().getIntExtra("assessment_id", assessmentID);
        courseID = getIntent().getIntExtra("course_id", courseID);

        assessment_title = findViewById(R.id.assessment_title_edit);
        assessment_end = findViewById(R.id.assessment_end_edit);

        assessmentTitle = getIntent().getStringExtra("assessment_title");
        assessmentEnd = getIntent().getStringExtra("assessment_end");

        assessment_title.setText(assessmentTitle);
        assessment_end.setText(assessmentEnd);

        assessment_title.requestFocus();

        assessmentTypeSpinner = findViewById(R.id.assessment_type_spinnner);
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.type, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assessmentTypeSpinner.setAdapter(typeAdapter);

        String assessmentType = getIntent().getStringExtra("assessment_type");
        int position = typeAdapter.getPosition(assessmentType);
        assessmentTypeSpinner.setSelection(position);
        assessmentTypeSpinner.requestFocus();

        Button updateAssessment = findViewById(R.id.update_assessment);
        updateAssessment.setOnClickListener(view -> {
            assessment = new Assessment(assessmentID, courseID, assessment_title.getText().toString(),
                    assessmentTypeSpinner.getSelectedItem().toString(),
                    assessment_end.getText().toString());
            repository.update(assessment);

            Toast.makeText(this, "Assessment was successfully updated.", Toast.LENGTH_SHORT).show();
            finish();
        });

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ExtendedFloatingActionButton assessmentFAB = findViewById(R.id.assessment_extended_fab);
        assessmentFAB.setOnClickListener(this::showSubMenu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showSubMenu(View view) {
        PopupMenu assessmentPopupMenu = new PopupMenu(this, view);
        assessmentPopupMenu.getMenuInflater().inflate(R.menu.assessment_menu, assessmentPopupMenu.getMenu());
        assessmentPopupMenu.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.delete_assessment) {
                deleteCourse();
            }
            return true;
        });
        assessmentPopupMenu.show();
    }

    private void deleteCourse() {
        Assessment assessment = new Assessment(assessmentID, courseID,
                assessment_title.getText().toString(),
                assessmentTypeSpinner.getSelectedItem().toString(),
                assessment_end.getText().toString());
        repository.delete(assessment);
        Toast.makeText(this, "Assessment was successfully deleted.",
                Toast.LENGTH_SHORT).show();
        finish();
    }
}
