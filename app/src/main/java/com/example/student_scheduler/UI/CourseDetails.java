package com.example.student_scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.student_scheduler.R;
import com.example.student_scheduler.database.Repository;
import com.example.student_scheduler.entities.Course;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.Objects;

/**
 * This activity allows the user to view details for a selected course. The data is populated into
 * EditText fields for the user to modify or delete.
 *
 * The user can also use the floating action button in the bottom right-hand corner to view
 * additional options related to the course.
 */
public class CourseDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText course_title;
    EditText course_start;
    EditText course_end;
    EditText instructor_name;
    EditText instructor_phone;
    EditText instructor_email;
    EditText course_notes;
    Spinner courseStatusSpinner;

    String courseTitle;
    String courseStart;
    String courseEnd;
    String instructorName;
    String instructorPhone;
    String instructorEmail;
    String courseNotes;

    int courseID;
    int termID;
    Course course;
    AssessmentAdapter assessmentAdapter;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        termID = getIntent().getIntExtra("term_id", 0);

        // Editable text fields
        course_title = findViewById(R.id.course_title_edit);
        course_start = findViewById(R.id.course_start_edit);
        course_end = findViewById(R.id.course_end_edit);
        instructor_name = findViewById(R.id.instructor_name_edit);
        instructor_phone = findViewById(R.id.phone_edit);
        instructor_email = findViewById(R.id.email_edit);
        course_notes = findViewById(R.id.course_notes_edit);

        courseTitle = getIntent().getStringExtra("course_title");
        courseStart = getIntent().getStringExtra("course_start");
        courseEnd = getIntent().getStringExtra("course_end");
        instructorName = getIntent().getStringExtra("instructor_name");
        instructorPhone = getIntent().getStringExtra("instructor_phone");
        instructorEmail = getIntent().getStringExtra("instructor_email");
        courseNotes = getIntent().getStringExtra("course_notes");

        course_title.setText(courseTitle);
        course_start.setText(courseStart);
        course_end.setText(courseEnd);
        instructor_name.setText(instructorName);
        instructor_phone.setText(instructorPhone);
        instructor_email.setText(instructorEmail);
        course_notes.setText(courseNotes);

        course_title.requestFocus();

        courseID = getIntent().getIntExtra("course_id",courseID);

        // Display associated assessments with course
        RecyclerView assessmentListRecycler = findViewById(R.id.assessment_list_recycler);
        repository = new Repository(getApplication());
        assessmentAdapter = new AssessmentAdapter(this);
        assessmentListRecycler.setAdapter(assessmentAdapter);
        assessmentListRecycler.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessments(repository.getAssociatedAssessments(courseID));

        // Dropdown selection for course status
        courseStatusSpinner = findViewById(R.id.course_status_spinner);
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.status, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseStatusSpinner.setAdapter(statusAdapter);

        String courseStatus = getIntent().getStringExtra("course_status");
        int position = statusAdapter.getPosition(courseStatus);
        courseStatusSpinner.setSelection(position);
        courseStatusSpinner.requestFocus();

        //Update selected course and confirm update
        Button updateCourse = findViewById(R.id.update_course);
        updateCourse.setOnClickListener(view -> {
                course = new Course(0,termID,course_title.getText().toString(),
                        course_start.getText().toString(),course_end.getText().toString(),
                        courseStatusSpinner.getSelectedItem().toString(),
                        instructor_name.getText().toString(), instructor_phone.getText().toString(),
                        instructor_email.getText().toString(), course_notes.getText().toString());
                repository.update(course);

                Toast.makeText(this,"Course was successfully updated.",Toast.LENGTH_SHORT).show();
                finish();
        });

        // Display toolbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Extended FAB with sub menu
        ExtendedFloatingActionButton courseFab = findViewById(R.id.courses_extended_fab);
        courseFab.setOnClickListener(this::showSubMenu);
    }

    /**
     * This method is called when the activity is resumed, and it sets up the RecyclerView
     * to display a list of all terms.
     */
    @Override
    protected void onResume() {
        super.onResume();

        RecyclerView assessmentListRecycler = findViewById(R.id.assessment_list_recycler);
        repository = new Repository(getApplication());
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        assessmentListRecycler.setAdapter(assessmentAdapter);
        assessmentListRecycler.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessments(repository.getAllAssessments());

        // Display if there are no associated courses
        if(assessmentListRecycler.getAdapter() != null &&
                assessmentListRecycler.getAdapter().getItemCount() == 0){
            Toast.makeText(this,"No assessments. Please add an assessment.",
                    Toast.LENGTH_SHORT).show();
        }
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

    /**
     * This method is called when the floating action button is clicked. This popup menu provides
     * the user two options: 1) adding a new course or 2) deleting the course.
     */
    public void showSubMenu(View view) {
        PopupMenu coursePopupMenu = new PopupMenu(this, view);
        coursePopupMenu.getMenuInflater().inflate(R.menu.course_menu, coursePopupMenu.getMenu());
        coursePopupMenu.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.add_assessment:
                    Intent toAddAssessment = new Intent(CourseDetails.this,
                            AddAssessment.class);
                    startActivity(toAddAssessment);
                    break;
                case R.id.delete_course:
                if (assessmentAdapter.getItemCount() > 0) {
                    Toast.makeText(CourseDetails.this, "Please delete associated " +
                            "assessments before deleting this course.", Toast.LENGTH_SHORT).show();
                } else {
                    deleteCourse();
                }
                break;
            }
            return true;
        });
        coursePopupMenu.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    private void deleteCourse() {
        Course course = new Course(courseID, termID, course_title.getText().toString(),
                course_start.getText().toString(), course_end.getText().toString(),
                courseStatusSpinner.getSelectedItem().toString(),
                instructor_name.getText().toString(), instructor_phone.getText().toString(),
                instructor_email.getText().toString(), course_notes.getText().toString());
        repository.delete(course);
        Toast.makeText(this, "Course was successfully deleted.",
                Toast.LENGTH_SHORT).show();
        finish();
    }
}