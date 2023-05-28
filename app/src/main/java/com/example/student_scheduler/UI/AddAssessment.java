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
import com.example.student_scheduler.entities.Assessment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

/**
 * This activity allows the user to add a new assessment to a course and save it to the database.
 */
public class AddAssessment extends AppCompatActivity {

    // UI elements
    Button startDatePicker;
    Button endDatePicker;
    EditText addAssessmentTitle;
    Spinner assessmentTypeSpinner;

    // Date picker
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar startCalendar = Calendar.getInstance();
    final Calendar endCalendar = Calendar.getInstance();

    // Variables
    String assessmentTitleEdit;
    String assessmentEndEdit;
    String assessmentStartEdit;
    int assessmentID;
    int courseID;
    Assessment assessment;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);

        // Initialize UI elements
        startDatePicker = findViewById(R.id.start_date_picker);
        endDatePicker = findViewById(R.id.end_date_picker);
        addAssessmentTitle = findViewById(R.id.assessment_title_edit);
        assessmentTypeSpinner = findViewById(R.id.assessment_type_spinnner);

        // Initialize date format
        String formattedDate = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formattedDate, Locale.US);

        // Set initial text for date picker
        startDatePicker.setText(getString(R.string.select_date));
        endDatePicker.setText(R.string.select_date);


        // Configure click listener for start date picker
        startDatePicker.setOnClickListener(view -> {
            String info = startDatePicker.getText().toString();
            try {
                startCalendar.setTime(Objects.requireNonNull(simpleDateFormat.parse(info)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            new DatePickerDialog(AddAssessment.this, startDate,
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
            new DatePickerDialog(AddAssessment.this, endDate,
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
        courseID = getIntent().getIntExtra("course_id", courseID);
        assessmentID = getIntent().getIntExtra("assessment_id", -1);
        assessmentTitleEdit = getIntent().getStringExtra("assessment_title");
        assessmentEndEdit = getIntent().getStringExtra("assessment_end");
        assessmentStartEdit = getIntent().getStringExtra("assessment_start");

        // Update UI field with intent extra
        addAssessmentTitle.setText(assessmentTitleEdit);

        // Cancel button
        Button cancel = findViewById(R.id.cancel_button);
        cancel.setOnClickListener(view -> finish());

        // Assessment type spinner
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.type, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assessmentTypeSpinner.setAdapter(typeAdapter);

        // Save button
        repository = new Repository(getApplication());
        Button saveAssessment = findViewById(R.id.add_assessment);
        saveAssessment.setOnClickListener(view -> {
            assessment = new Assessment(0, courseID,
                    addAssessmentTitle.getText().toString(),
                    assessmentTypeSpinner.getSelectedItem().toString(),
                    startDatePicker.getText().toString(),
                    endDatePicker.getText().toString());
            repository.insert(assessment);

            // Show confirmation message and finish the activity
            Toast.makeText(getApplication(), "Assessment was successfully added.",
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
    @Override
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