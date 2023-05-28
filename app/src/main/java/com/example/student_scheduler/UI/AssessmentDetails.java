package com.example.student_scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

// ToDo: add alerts

/**
 * This activity allows the user to view and update assessment information. It also provides options
 * to delete the assessment and navigate back to the previous screen.
 */
public class AssessmentDetails extends AppCompatActivity {

    // UI elements
    Button startDatePicker;
    Button endDatePicker;
    EditText assessment_title;
    Spinner assessmentTypeSpinner;

    // Date picker
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar startCalendar = Calendar.getInstance();
    final Calendar endCalendar = Calendar.getInstance();

    // Variables
    String assessmentTitle;
    String assessmentStart;
    String assessmentEnd;
    int assessmentID;
    int courseID;
    Assessment assessment;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        // Initialize UI elements
        assessment_title = findViewById(R.id.assessment_title_edit);
        startDatePicker = findViewById(R.id.start_date_picker);
        endDatePicker = findViewById(R.id.end_date_picker);
        assessmentTypeSpinner = findViewById(R.id.assessment_type_spinnner);

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
            new DatePickerDialog(AssessmentDetails.this, startDate,
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
            new DatePickerDialog(AssessmentDetails.this, endDate,
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
        assessmentID = getIntent().getIntExtra("assessment_id", assessmentID);
        courseID = getIntent().getIntExtra("course_id", courseID);
        assessmentTitle = getIntent().getStringExtra("assessment_title");
        assessmentEnd = getIntent().getStringExtra("assessment_end");
        assessmentStart = getIntent().getStringExtra("assessment_start");

        // Update UI field with intent extra
        assessment_title.setText(assessmentTitle);
        endDatePicker.setText(assessmentEnd);
        startDatePicker.setText(assessmentStart);

        // Set focus to the assessment title
        assessment_title.requestFocus();

        // Assessment type spinner
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.type, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assessmentTypeSpinner.setAdapter(typeAdapter);

        String assessmentType = getIntent().getStringExtra("assessment_type");
        int position = typeAdapter.getPosition(assessmentType);
        assessmentTypeSpinner.setSelection(position);

        // Update button
        repository = new Repository(getApplication());
        Button updateAssessment = findViewById(R.id.update_assessment);
        updateAssessment.setOnClickListener(view -> {
            assessment = new Assessment(assessmentID, courseID,
                    assessment_title.getText().toString(),
                    assessmentTypeSpinner.getSelectedItem().toString(),
                    startDatePicker.getText().toString(),
                    endDatePicker.getText().toString());
            repository.update(assessment);

            // Show confirmation message and finish the activity
            Toast.makeText(this, "Assessment was successfully updated.", Toast.LENGTH_SHORT).show();
            finish();
        });

        // Enable back button in the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Set click listener for floating action button
        ExtendedFloatingActionButton assessmentFAB = findViewById(R.id.assessment_extended_fab);
        assessmentFAB.setOnClickListener(this::showSubMenu);
    }

    /**
     * This method is called to populate the options menu with menu items.
     *
     * @param menu The menu object representing the options menu.
     * @return True to display the menu.
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.assessment_submenu, menu);
        return true;
    }

    /**
     * Handles home button in the action bar. If the home button is selected, the activity is
     * finished and the user is taken back to the previous screen.
     *
     * @param item The selected menu item.
     * @return True if the item selection was handled.
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
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
                Intent firstIntent = new Intent(AssessmentDetails.this, MyReceiver.class);
                firstIntent.putExtra("key", "Assessment Reminder:" + " " + assessmentTitle + " " +
                        "is set to begin on" + " " + startDateFromScreen);
                PendingIntent firstSender = PendingIntent.getBroadcast(AssessmentDetails.this, ++MainActivity.numAlert, firstIntent, PendingIntent.FLAG_IMMUTABLE);
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
                Intent secondIntent = new Intent(AssessmentDetails.this, MyReceiver.class);
                secondIntent.putExtra("key", "Assessment Reminder:" + " " + assessmentTitle + " " +
                        "will be ending on" + " " + dateFromScreen);
                PendingIntent secondSender = PendingIntent.getBroadcast(AssessmentDetails.this, ++MainActivity.numAlert, secondIntent, PendingIntent.FLAG_IMMUTABLE);
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
    public void showSubMenu(View view) {
        PopupMenu assessmentPopupMenu = new PopupMenu(this, view);
        assessmentPopupMenu.getMenuInflater().inflate(R.menu.assessment_menu, assessmentPopupMenu.getMenu());
        assessmentPopupMenu.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.delete_assessment) {
                deleteAssessment();
            }
            return true;
        });
        assessmentPopupMenu.show();
    }

    /**
     * This method is called when the user selects the delete option from the assessment submenu,
     * which deletes the current assessment.
     */
    private void deleteAssessment() {
        Assessment assessment = new Assessment(assessmentID, courseID,
                assessment_title.getText().toString(),
                assessmentTypeSpinner.getSelectedItem().toString(),
                startDatePicker.getText().toString(),
                endDatePicker.getText().toString());
        repository.delete(assessment);
        Toast.makeText(this, "Assessment was successfully deleted.",
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
