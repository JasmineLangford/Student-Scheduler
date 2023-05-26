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
 * This activity allows the user to add a new course to the database and displays the new course on
 * the screen with the listed courses once the Save button is clicked.
 */
public class AddCourse extends AppCompatActivity{

    Button startDatePicker;
    Button endDatePicker;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendar = Calendar.getInstance();

    EditText addCourseTitle;
    EditText addInstructorName;
    EditText addPhone;
    EditText addEmail;
    EditText addCourseNotes;

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

        // DatePicker
        startDatePicker = findViewById(R.id.start_date_picker);
        endDatePicker = findViewById(R.id.end_date_picker);
        String formattedDate = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formattedDate, Locale.US);
        startDatePicker.setText(getString(R.string.select_date));
        endDatePicker.setText(getString(R.string.select_date));

        startDatePicker.setOnClickListener(view -> {
            String info = startDatePicker.getText().toString();
            try{
                myCalendar.setTime(Objects.requireNonNull(simpleDateFormat.parse(info)));
            }catch (ParseException e) {
                e.printStackTrace();
            }
            new DatePickerDialog(AddCourse.this,startDate,
                    myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        endDatePicker.setOnClickListener(view -> {
            String info = endDatePicker.getText().toString();
            try{
                myCalendar.setTime(Objects.requireNonNull(simpleDateFormat.parse(info)));
            }catch (ParseException e) {
                e.printStackTrace();
            }
            new DatePickerDialog(AddCourse.this,endDate,
                    myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });


        endDate = (datePicker, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateEndDate();

            String selectedEndDate = simpleDateFormat.format(myCalendar.getTime());
            endDatePicker.setText(selectedEndDate);
        };

        startDate = (datePicker, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateStartDate();

            String selectedStartDate = simpleDateFormat.format(myCalendar.getTime());
            startDatePicker.setText(selectedStartDate);
        };

        termID = getIntent().getIntExtra("term_id",termID);
        courseID = getIntent().getIntExtra("course_id", -1);

        // Editable fields
        addCourseTitle = findViewById(R.id.course_title_edit);
        addInstructorName = findViewById(R.id.instructor_name_edit);
        addEmail = findViewById(R.id.email_edit);
        addPhone = findViewById(R.id.phone_edit);
        addCourseNotes = findViewById(R.id.course_notes_edit);

        courseTitleEdit = getIntent().getStringExtra("course_title");
        courseStartEdit = getIntent().getStringExtra("course_start");
        courseEndEdit = getIntent().getStringExtra("course_end");
        instructorNameEdit = getIntent().getStringExtra("instructor_name");
        phoneEdit = getIntent().getStringExtra("instructor_phone");
        emailEdit = getIntent().getStringExtra("instructor_email");
        courseNoteEdit = getIntent().getStringExtra("course_notes");

        addCourseTitle.setText(courseTitleEdit);
        addInstructorName.setText(instructorNameEdit);
        addPhone.setText(phoneEdit);
        addEmail.setText(emailEdit);
        addCourseNotes.setText(courseNoteEdit);

        // Cancel and go back to term details
        Button cancel = findViewById(R.id.cancel_button);
        cancel.setOnClickListener(view -> finish());

        // Dropdown selection for course status
        Spinner courseStatusSpinner = findViewById(R.id.course_status_spinner2);
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.status, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseStatusSpinner.setAdapter(statusAdapter);

        // Save fields
        repository = new Repository(getApplication());
        Button saveCourse = findViewById(R.id.add_course);
        saveCourse.setOnClickListener(view -> {
                course = new Course(0,termID,addCourseTitle.getText().toString(),
                        startDatePicker.getText().toString(),endDatePicker.getText().toString(),
                        courseStatusSpinner.getSelectedItem().toString(),
                        addInstructorName.getText().toString(), addPhone.getText().toString(),
                        addEmail.getText().toString(), addCourseNotes.getText().toString());
                repository.insert(course);

                // Message to confirm add and back to term details
                Toast.makeText(getApplication(), "Course was successfully added.",
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

    // Update date for date picker
    private void updateStartDate(){
        String formattedDate = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formattedDate, Locale.US);

        endDatePicker.setText(simpleDateFormat.format(myCalendar.getTime()));
    }

    private void updateEndDate(){
        String formattedDate = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formattedDate, Locale.US);

        endDatePicker.setText(simpleDateFormat.format(myCalendar.getTime()));
    }
}