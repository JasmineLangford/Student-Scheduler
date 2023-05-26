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
 * This activity allows the user to add a new assessment to the database and displays the new
 * assessment on the screen with the listed assessments once the Save button is clicked.
 */

public class AddAssessment extends AppCompatActivity {

    Button endDatePicker;
    DatePickerDialog.OnDateSetListener myDate;
    final Calendar myCalendar = Calendar.getInstance();

    EditText addAssessmentTitle;
    String assessmentTitleEdit;
    String assessmentEndEdit;

    int assessmentID;
    int courseID;
    Assessment assessment;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);

        // DatePicker
        endDatePicker = findViewById(R.id.end_date_picker);
        String formattedDate = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formattedDate, Locale.US);
        endDatePicker.setText(R.string.select_date);
        endDatePicker.setOnClickListener(view -> {
            String info = endDatePicker.getText().toString();
            try{
                myCalendar.setTime(Objects.requireNonNull(simpleDateFormat.parse(info)));
            }catch (ParseException e) {
                e.printStackTrace();
            }
            new DatePickerDialog(AddAssessment.this,myDate,
                    myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        myDate = (datePicker, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate();

            String selectedDate = simpleDateFormat.format(myCalendar.getTime());
            endDatePicker.setText(selectedDate);
        };

        courseID = getIntent().getIntExtra("course_id", courseID);
        assessmentID = getIntent().getIntExtra("assessment_id",-1);

        // Editable fields
        addAssessmentTitle = findViewById(R.id.assessment_title_edit);
        assessmentTitleEdit = getIntent().getStringExtra("assessment_title");
        assessmentEndEdit = getIntent().getStringExtra("assessment_end");
        addAssessmentTitle.setText(assessmentTitleEdit);

        // Cancel and go back to course details
        Button cancel = findViewById(R.id.cancel_button);
        cancel.setOnClickListener(view -> finish());

        // Dropdown selection for assessment type
        Spinner assessmentTypeSpinner = findViewById(R.id.assessment_type_spinnner);
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.type, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assessmentTypeSpinner.setAdapter(typeAdapter);

        // Save fields
        repository = new Repository(getApplication());
        Button saveAssessment = findViewById(R.id.add_assessment);
        saveAssessment.setOnClickListener(view -> {
            assessment = new Assessment(0,courseID,
                    addAssessmentTitle.getText().toString(),
                    assessmentTypeSpinner.getSelectedItem().toString(),endDatePicker.getText().toString());
                   //addAssessmentEnd.getText().toString());
            repository.insert(assessment);

            // Message to confirm add and back to term details
            Toast.makeText(getApplication(), "Assessment was successfully added.",
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
    private void updateDate(){
        String formattedDate = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formattedDate, Locale.US);

        endDatePicker.setText(simpleDateFormat.format(myCalendar.getTime()));
    }
}