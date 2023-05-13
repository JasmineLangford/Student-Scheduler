package com.example.student_scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;

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
public class CourseDetails extends AppCompatActivity {
    EditText course_title;
    EditText course_start;
    EditText course_end;
    EditText instructor_name;
    EditText instructor_phone;
    EditText instructor_email;
    String courseTitle;
    String courseStart;
    String courseEnd;
    String instructorName;
    String instructorPhone;
    String instructorEmail;
    int courseID;
    Course course;
    Repository repository;

    //Confirmation Message
    String confirmMessage = "Course was successfully updated.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        // Labels and edit text fields for selected course
        course_title = findViewById(R.id.course_title_edit);
        courseTitle = getIntent().getStringExtra("course_title");
        course_title.setText(courseTitle);
        course_title.requestFocus();

        course_start = findViewById(R.id.course_start_edit);
        courseStart = getIntent().getStringExtra("course_start");
        course_start.setText(courseStart);
        course_start.requestFocus();

        course_end = findViewById(R.id.course_end_edit);
        courseEnd = getIntent().getStringExtra("course_end");
        course_end.setText(courseEnd);
        course_end.requestFocus();

        instructor_name = findViewById(R.id.instructor_name_edit);
        instructorName = getIntent().getStringExtra("instructor_name");
        instructor_name.setText(instructorName);
        instructor_name.requestFocus();

        instructor_phone = findViewById(R.id.phone_edit);
        instructorPhone = getIntent().getStringExtra("instructor_phone");
        instructor_phone.setText(instructorPhone);
        instructor_phone.requestFocus();

        instructor_email = findViewById(R.id.email_edit);
        instructorEmail = getIntent().getStringExtra("instructor_email");
        instructor_email.setText(instructorEmail);
        instructor_email.requestFocus();

        courseID = getIntent().getIntExtra("course_id",-1);
        repository = new Repository(getApplication());

        // Display associated assessments with course
        RecyclerView assessmentListRecycler = findViewById(R.id.assessment_list_recycler);
        repository = new Repository(getApplication());
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        assessmentListRecycler.setAdapter(assessmentAdapter);
        assessmentListRecycler.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessments(repository.getAllAssessments());

        // Dropdown selection for course status
        Spinner courseStatusSpinner = findViewById(R.id.course_status_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, new String[]{"In Progress",
                        "Completed","Dropped","Plan to Take"});
        courseStatusSpinner.setAdapter(adapter);
        courseStatusSpinner.setPrompt("Select Status");

        String courseStatus = String.valueOf(getIntent().getStringArrayListExtra("course_status"));
        int position = adapter.getPosition(courseStatus);
        courseStatusSpinner.setSelection(position);
        courseStatusSpinner.requestFocus();

        // Update select course and confirm update
//        Button updateCourse = findViewById(R.id.update_course);
//        updateCourse.setOnClickListener(view -> {
//            if (courseID == -1) {
//                course = new Course(0,0,course_title.getText().toString(),
//                        course_start.getText().toString(),course_end.getText().toString(),
//                        course_status.getText().toString(),instructor_name.getText().toString(),
//                        instructor_phone.getText().toString(),instructor_email.getText().toString,;
//            }
//
//        });
        // Display toolbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Extended FAB with sub menu
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ExtendedFloatingActionButton courseFab = findViewById(R.id.courses_extended_fab);
        courseFab.setOnClickListener(this::showSubMenu);
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
    @SuppressLint("NonConstantResourceId")
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
                case R.id.add_course:
                    Intent toAddCourse = new Intent(CourseDetails.this,
                            AddCourse.class);
                    startActivity(toAddCourse);
                    break;
                case R.id.delete_course:
                    // TODO: add delete functionality
                    return true;
            }
            return false;
        });
        coursePopupMenu.show();
    }
}