package com.example.student_scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.student_scheduler.R;
import com.example.student_scheduler.database.Repository;
import com.example.student_scheduler.entities.Course;
import com.example.student_scheduler.entities.Term;

import java.util.Objects;

/**
 * This activity allows the user to add a new course to the database and displays the new course on
 * the screen with the listed courses once the Save button is clicked.
 */
public class AddCourse extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText addCourseTitle;
    EditText addCourseStart;
    EditText addCourseEnd;
    Spinner addCourseStatus;
    EditText addInstructorName;
    EditText addPhone;
    EditText addEmail;
    EditText addCourseNotes;

    String courseTitleEdit;
    String courseStartEdit;
    String courseEndEdit;
    String[] courseStatusEdit;
    String instructorNameEdit;
    String phoneEdit;
    String emailEdit;
    String courseNoteEdit;

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
        addCourseTitle = findViewById(R.id.course_title_edit);
        addCourseStart = findViewById(R.id.course_start_edit);
        addCourseEnd = findViewById(R.id.course_end_edit);
        addInstructorName = findViewById(R.id.instructor_name_edit);
        addEmail = findViewById(R.id.email_edit);
        addPhone = findViewById(R.id.phone_edit);
        addCourseNotes = findViewById(R.id.course_notes_edit);

        courseTitleEdit = getIntent().getStringExtra("course_title");
        courseStartEdit = getIntent().getStringExtra("course_start");
        courseEndEdit = getIntent().getStringExtra("course_end");
        courseStatusEdit = getIntent().getStringArrayExtra("course_status");
        instructorNameEdit = getIntent().getStringExtra("instructor_name");
        phoneEdit = getIntent().getStringExtra("instructor_phone");
        emailEdit = getIntent().getStringExtra("instructor_email");
        courseNoteEdit = getIntent().getStringExtra("course_notes");
        addCourseTitle.setText(courseTitleEdit);
        addCourseStart.setText(courseStartEdit);
        addCourseEnd.setText(courseEndEdit);
        addInstructorName.setText(instructorNameEdit);
        addPhone.setText(phoneEdit);
        addEmail.setText(emailEdit);
        addCourseNotes.setText(courseNoteEdit);

        // Cancel and go back to list of terms
        Button cancel = findViewById(R.id.cancel_button);
        cancel.setOnClickListener(view -> finish());

        // Dropdown selection for course status
        Spinner courseStatusSpinner = findViewById(R.id.course_status_spinner2);
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.status, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseStatusSpinner.setAdapter(statusAdapter);
        courseStatusSpinner.setOnItemSelectedListener(this);

        String courseStatus = getIntent().getStringExtra("course_status");
        int position = statusAdapter.getPosition(courseStatus);
        courseStatusSpinner.setSelection(0);

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
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        repository = new Repository(getApplication());
        String selectedStatus = adapterView.getItemAtPosition(position).toString();
        course = new Course();
        repository.insert(course);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}