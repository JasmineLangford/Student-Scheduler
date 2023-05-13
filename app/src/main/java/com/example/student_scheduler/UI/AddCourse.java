package com.example.student_scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.student_scheduler.R;
import com.example.student_scheduler.database.Repository;
import com.example.student_scheduler.entities.Course;
import com.example.student_scheduler.entities.Term;

import java.util.Objects;

/**
 * This activity allows the user to add a new course to the database and displays the new course on
 * the screen with the listed courses once the Save button is clicked.
 */
public class AddCourse extends AppCompatActivity {

    EditText editCourseTitle;
    EditText editCourseStart;
    EditText editCourseEnd;
    EditText editCourseStatus;
    EditText editInstructorName;
    EditText editPhone;
    EditText editEmail;

    String courseTitleEdit;
    String courseStartEdit;
    String courseEndEdit;
    String courseStatusEdit;
    String instructorNameEdit;
    String phoneEdit;
    String emailEdit;

    int courseID;
    int termID;
    Course course;
    Term term;
    Repository repository;

    // Confirmation Message
    String confirmMessage = "New course was successfully added.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        // Editable text fields
        editCourseTitle = findViewById(R.id.course_title_edit);
        editCourseStart = findViewById(R.id.course_start_edit);
        editCourseEnd = findViewById(R.id.course_end_edit);
        editCourseStatus = findViewById(R.id.course_status_edit);
        editInstructorName = findViewById(R.id.instructor_name_edit);
        editEmail = findViewById(R.id.email_edit);
        editPhone = findViewById(R.id.phone_edit);

        courseTitleEdit = getIntent().getStringExtra("course_title");
        courseStartEdit = getIntent().getStringExtra("course_start");
        courseEndEdit = getIntent().getStringExtra("course_end");
        courseStatusEdit = getIntent().getStringExtra("course_status");
        instructorNameEdit = getIntent().getStringExtra("instructor_name");
        phoneEdit = getIntent().getStringExtra("instructor_phone");
        emailEdit = getIntent().getStringExtra("instructor_email");

        editCourseTitle.setText(courseTitleEdit);
        editCourseStart.setText(courseStartEdit);
        editCourseEnd.setText(courseEndEdit);
        editCourseStatus.setText(courseStatusEdit);
        editInstructorName.setText(instructorNameEdit);
        editPhone.setText(phoneEdit);
        editEmail.setText(emailEdit);

        // Cancel and go back to list of terms
        Button cancel = findViewById(R.id.cancel_button);
        cancel.setOnClickListener(view -> finish());

        // Save fields
//        repository = new Repository(getApplication());
//        termID = getIntent().getIntExtra("term_id", -1);
//        courseID = getIntent().getIntExtra("course_id", -1);
//        Button button = findViewById(R.id.save_new_course);
//        button.setOnClickListener(view -> {
//            if (courseID == -1) {
//                course = new Course(0,editCourseTitle.getText().toString(),
//                        editCourseStart.getText().toString(), editCourseEnd.getText().toString(),
//                        editCourseStatus.getText().toString(),
//                        editInstructorName.getText().toString(),editPhone.getText().toString(),
//                        editPhone.getText().toString(),termID);
//                repository.insert(course);
//
//                // Message to confirm add
//                Toast.makeText(getApplication(), confirmMessage, Toast.LENGTH_SHORT).show();
//
//                // Back to screen with list of terms
//                Intent intent = new Intent(this, CourseList.class);
//                startActivity(intent);
//            }
//        });
        // Display toolbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
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