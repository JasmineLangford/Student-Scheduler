package com.example.student_scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * This activity allows the user to view and update course information. It also provides options
 * to delete the course and navigate back to the previous screen.
 */
public class CourseDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // UI elements
    Button startDatePicker;
    Button endDatePicker;
    EditText course_title;
    EditText instructor_name;
    EditText instructor_phone;
    EditText instructor_email;
    EditText course_notes;
    Spinner courseStatusSpinner;

    // Date Picker
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar startCalendar = Calendar.getInstance();
    final Calendar endCalendar = Calendar.getInstance();

    // Variables
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

        // Initialize UI elements
        course_title = findViewById(R.id.course_title_edit);
        startDatePicker = findViewById(R.id.start_date_picker);
        endDatePicker = findViewById(R.id.end_date_picker);
        instructor_name = findViewById(R.id.instructor_name_edit);
        instructor_phone = findViewById(R.id.phone_edit);
        instructor_email = findViewById(R.id.email_edit);
        course_notes = findViewById(R.id.course_notes_edit);
        courseStatusSpinner = findViewById(R.id.course_status_spinner);

        // Initialize date format
        String formattedDate = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formattedDate, Locale.US);

        // Configure click listener for start date picker
        startDatePicker.setOnClickListener(view -> {
            String info = startDatePicker.getText().toString();
            try {
                startCalendar.setTime(Objects.requireNonNull(simpleDateFormat.parse(info)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            new DatePickerDialog(CourseDetails.this, startDate,
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
            new DatePickerDialog(CourseDetails.this, endDate,
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
        courseID = getIntent().getIntExtra("course_id", courseID);
        courseTitle = getIntent().getStringExtra("course_title");
        courseStart = getIntent().getStringExtra("course_start");
        courseEnd = getIntent().getStringExtra("course_end");
        instructorName = getIntent().getStringExtra("instructor_name");
        instructorPhone = getIntent().getStringExtra("instructor_phone");
        instructorEmail = getIntent().getStringExtra("instructor_email");
        courseNotes = getIntent().getStringExtra("course_notes");

        // Update UI fields with intent extras
        course_title.setText(courseTitle);
        startDatePicker.setText(courseStart);
        endDatePicker.setText(courseEnd);
        instructor_name.setText(instructorName);
        instructor_phone.setText(instructorPhone);
        instructor_email.setText(instructorEmail);
        course_notes.setText(courseNotes);

        // Set focus to the course title
        course_title.requestFocus();

        // Set up RecyclerView for displaying assessment list
        RecyclerView assessmentListRecycler = findViewById(R.id.assessment_list_recycler);
        repository = new Repository(getApplication());
        assessmentAdapter = new AssessmentAdapter(this);
        assessmentListRecycler.setAdapter(assessmentAdapter);
        assessmentListRecycler.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessments(repository.getAssociatedAssessments(courseID));

        // Course status spinner
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.status, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseStatusSpinner.setAdapter(statusAdapter);

        String courseStatus = getIntent().getStringExtra("course_status");
        int position = statusAdapter.getPosition(courseStatus);
        courseStatusSpinner.setSelection(position);

        // Update button
        Button updateCourse = findViewById(R.id.update_course);
        updateCourse.setOnClickListener(view -> {
            course = new Course(courseID, termID, course_title.getText().toString(),
                    startDatePicker.getText().toString(), endDatePicker.getText().toString(),
                    courseStatusSpinner.getSelectedItem().toString(),
                    instructor_name.getText().toString(), instructor_phone.getText().toString(),
                    instructor_email.getText().toString(), course_notes.getText().toString());
            repository.update(course);

            // Show confirmation message and finish the activity
            Toast.makeText(this, "Course was successfully updated.", Toast.LENGTH_SHORT).show();
            finish();
        });

        // Enable back button in the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Set click listener for floating action button
        ExtendedFloatingActionButton courseFab = findViewById(R.id.courses_extended_fab);
        courseFab.setOnClickListener(this::showSubMenu);
    }

    /**
     * This method is called to populate the options menu with menu items.
     *
     * @param menu The menu object representing the options menu.
     * @return True to display the menu.
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.course_submenu, menu);
        return true;
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
        assessmentAdapter.setAssessments(repository.getAssociatedAssessments(courseID));

        // Display if there are no associated courses
        if (assessmentListRecycler.getAdapter() != null &&
                assessmentListRecycler.getAdapter().getItemCount() == 0) {
            Toast.makeText(this, "No assessments. Please add an assessment.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // ToDo: Javadoc comment
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            // Share course notes
            case R.id.share_notes:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, courseNotes);
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Send message title");
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;

            case R.id.notify_start:
                String startDateFromScreen = startDatePicker.getText().toString();
                String startDateFormat = "MM/dd/yyyy";
                SimpleDateFormat sdfStart = new SimpleDateFormat(startDateFormat, Locale.US);
                Date thisStartDate = null;
                try {
                    thisStartDate = sdfStart.parse(startDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long firstTrigger = thisStartDate.getTime();
                Intent firstIntent = new Intent(CourseDetails.this, MyReceiver.class);
                firstIntent.putExtra("key", "Course Reminder:" + " " +courseTitle + " " +
                        "is set to begin on" + " " + startDateFromScreen);
                PendingIntent firstSender = PendingIntent.getBroadcast(CourseDetails.this, ++MainActivity.numAlert, firstIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager firstAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                firstAlarmManager.set(AlarmManager.RTC_WAKEUP, firstTrigger, firstSender);
                return true;
            case R.id.notify_end:
                String dateFromScreen = endDatePicker.getText().toString();
                String endDateFormat = "MM/dd/yyyy";
                SimpleDateFormat sdfEnd = new SimpleDateFormat(endDateFormat, Locale.US);
                Date thisDate = null;
                try {
                    thisDate = sdfEnd.parse(dateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long secondTrigger = thisDate.getTime();
                Intent secondIntent = new Intent(CourseDetails.this, MyReceiver.class);
                secondIntent.putExtra("key", "Course Reminder:" + " " +courseTitle + " " +
                        "will be ending on" + " " + dateFromScreen);
                PendingIntent secondSender = PendingIntent.getBroadcast(CourseDetails.this, ++MainActivity.numAlert, secondIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager secondAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                secondAlarmManager.set(AlarmManager.RTC_WAKEUP, secondTrigger, secondSender);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    /**
     * This method is called when the user clicks on the assessment floating action button, which
     * displays the submenu.
     *
     * @param view The view that triggered the event.
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
                    toAddAssessment.putExtra("course_id", courseID);
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
                startDatePicker.getText().toString(), endDatePicker.getText().toString(),
                courseStatusSpinner.getSelectedItem().toString(),
                instructor_name.getText().toString(), instructor_phone.getText().toString(),
                instructor_email.getText().toString(), course_notes.getText().toString());
        repository.delete(course);
        Toast.makeText(this, "Course was successfully deleted.",
                Toast.LENGTH_SHORT).show();
        finish();
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