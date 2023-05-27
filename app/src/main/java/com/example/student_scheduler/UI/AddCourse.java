package com.example.student_scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.student_scheduler.R;
import com.example.student_scheduler.database.Repository;
import com.example.student_scheduler.entities.Course;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

/**
 * This activity allows the user to add a new course to a term and save it to the database.
 */
public class AddCourse extends AppCompatActivity {

    // UI elements
    Button startDatePicker;
    Button endDatePicker;
    EditText addCourseTitle;
    EditText addInstructorName;
    EditText addPhone;
    EditText addEmail;
    EditText addCourseNotes;

    // Date Picker
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar startCalendar = Calendar.getInstance();
    final Calendar endCalendar = Calendar.getInstance();


    // Variables
    String courseTitleEdit;
    String courseStartEdit;
    String courseEndEdit;
    String instructorNameEdit;
    String phoneEdit;
    String emailEdit;
    String courseNoteEdit;
    int courseID;
    int termID;
    Course course;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        // Initialize UI elements
        startDatePicker = findViewById(R.id.start_date_picker);
        endDatePicker = findViewById(R.id.end_date_picker);
        Spinner courseStatusSpinner = findViewById(R.id.course_status_spinner2);
        addCourseTitle = findViewById(R.id.course_title_edit);
        addInstructorName = findViewById(R.id.instructor_name_edit);
        addEmail = findViewById(R.id.email_edit);
        addPhone = findViewById(R.id.phone_edit);
        addCourseNotes = findViewById(R.id.course_notes_edit);

        // Initialize date format
        String formattedDate = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formattedDate, Locale.US);

        // Set initial text for date picker
        startDatePicker.setText(getString(R.string.select_date));
        endDatePicker.setText(getString(R.string.select_date));

        // Configure click listener for start date picker
        startDatePicker.setOnClickListener(view -> {
            String info = startDatePicker.getText().toString();
            try {
                startCalendar.setTime(Objects.requireNonNull(simpleDateFormat.parse(info)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            new DatePickerDialog(AddCourse.this, startDate,
                    startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH),
                    startCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        // Configure date selection listener for start date picker
        startDate = (datePicker, year, monthOfYear, dayOfMonth) -> {
            startCalendar.set(Calendar.YEAR, year);
            startCalendar.set(Calendar.MONTH, monthOfYear);
            startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateStartDate();

            String selectedStartDate = simpleDateFormat.format(startCalendar.getTime());
            startDatePicker.setText(selectedStartDate);
        };

        // Configure click listener for end date picker
        endDatePicker.setOnClickListener(view -> {
            String info = endDatePicker.getText().toString();
            try {
                endCalendar.setTime(Objects.requireNonNull(simpleDateFormat.parse(info)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            new DatePickerDialog(AddCourse.this, endDate,
                    endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH),
                    endCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        // Configure date selection listener for end date picker
        endDate = (datePicker, year, monthOfYear, dayOfMonth) -> {
            endCalendar.set(Calendar.YEAR, year);
            endCalendar.set(Calendar.MONTH, monthOfYear);
            endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateEndDate();

            String selectedEndDate = simpleDateFormat.format(endCalendar.getTime());
            endDatePicker.setText(selectedEndDate);
        };

        // Retrieve details from intent
        termID = getIntent().getIntExtra("term_id", termID);
        courseID = getIntent().getIntExtra("course_id", -1);
        courseTitleEdit = getIntent().getStringExtra("course_title");
        courseStartEdit = getIntent().getStringExtra("course_start");
        courseEndEdit = getIntent().getStringExtra("course_end");
        instructorNameEdit = getIntent().getStringExtra("instructor_name");
        phoneEdit = getIntent().getStringExtra("instructor_phone");
        emailEdit = getIntent().getStringExtra("instructor_email");
        courseNoteEdit = getIntent().getStringExtra("course_notes");

        // Update UI fields with values from intent extras
        addCourseTitle.setText(courseTitleEdit);
        addInstructorName.setText(instructorNameEdit);
        addPhone.setText(phoneEdit);
        addEmail.setText(emailEdit);
        addCourseNotes.setText(courseNoteEdit);

        // Cancel button
        Button cancel = findViewById(R.id.cancel_button);
        cancel.setOnClickListener(view -> finish());

        // Course status spinner
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.status, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseStatusSpinner.setAdapter(statusAdapter);

        // Save button
        repository = new Repository(getApplication());
        Button saveCourse = findViewById(R.id.add_course);
        saveCourse.setOnClickListener(view -> {
            course = new Course(0, termID, addCourseTitle.getText().toString(),
                    startDatePicker.getText().toString(), endDatePicker.getText().toString(),
                    courseStatusSpinner.getSelectedItem().toString(),
                    addInstructorName.getText().toString(), addPhone.getText().toString(),
                    addEmail.getText().toString(), addCourseNotes.getText().toString());
            repository.insert(course);

            // Show confirmation message and finish the activity
            Toast.makeText(getApplication(), "Course was successfully added.",
                    Toast.LENGTH_SHORT).show();
            finish();
        });

        // Enable back button in the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Handles home button in the action bar. If the home button is selected, the activity is
     * finished and the user is taken back to the previous screen.
     *
     * @param item The selected menu item.
     * @return True if the item selection was handled.
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Updates the start date picker with the selected date.
     */
    private void updateStartDate() {
        String formattedDate = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formattedDate, Locale.US);

        startDatePicker.setText(simpleDateFormat.format(startCalendar.getTime()));
    }

    /**
     * Updates the end date picker with the selected date.
     */
    private void updateEndDate() {
        String formattedDate = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formattedDate, Locale.US);

        endDatePicker.setText(simpleDateFormat.format(endCalendar.getTime()));
    }
}