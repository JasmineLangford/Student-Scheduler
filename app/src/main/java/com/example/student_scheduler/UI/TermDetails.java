package com.example.student_scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.student_scheduler.R;
import com.example.student_scheduler.database.Repository;
import com.example.student_scheduler.entities.Term;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

/**
 * This activity allows the user to view and update term information. It also provides options to
 * delete the term and navigate back to the previous screen.
 */
public class TermDetails extends AppCompatActivity {

    // UI elements
    Button startDatePicker;
    Button endDatePicker;
    EditText term_title;

    // Date Picker
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar startCalendar = Calendar.getInstance();
    final Calendar endCalendar = Calendar.getInstance();

    // Variables
    String termTitle;
    String termStart;
    String termEnd;
    int termID;
    Term term;
    CourseAdapter courseAdapter;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        // Initialize UI elements
        term_title = findViewById(R.id.term_title_edit);
        startDatePicker = findViewById(R.id.start_date_picker);
        endDatePicker = findViewById(R.id.end_date_picker);

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
            new DatePickerDialog(TermDetails.this, startDate,
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
            new DatePickerDialog(TermDetails.this, endDate,
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
        termID = getIntent().getIntExtra("term_id",termID);
        termTitle = getIntent().getStringExtra("term_title");
        termStart = getIntent().getStringExtra("term_start");
        termEnd= getIntent().getStringExtra("term_end");

        // Update UI field with intent extra
        term_title.setText(termTitle);
        startDatePicker.setText(termStart);
        endDatePicker.setText(termEnd);

        // Set focus to the term title
        term_title.requestFocus();

        // Display associated courses with the selected term
        RecyclerView courseListRecycler = findViewById(R.id.course_list_recycler);
        repository = new Repository(getApplication());
        courseAdapter = new CourseAdapter(this);
        courseListRecycler.setAdapter(courseAdapter);
        courseListRecycler.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourses(repository.getAssociatedCourses(termID));

        // Update button
        Button updateTerm = findViewById(R.id.update_term);
        updateTerm.setOnClickListener(view -> {
                term = new Term(termID, term_title.getText().toString(),
                        startDatePicker.getText().toString(), endDatePicker.getText().toString());
                repository.update(term);

            // Show confirmation message and finish the activity
            Toast.makeText(getApplication(), "Term was successfully updated.",
                        Toast.LENGTH_SHORT).show();
                finish();
        });

        // Enable back button in the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Set click listener for floating action button
        ExtendedFloatingActionButton termFab = findViewById(R.id.terms_extended_fab);
        termFab.setOnClickListener(this::showSubMenu);
    }

    /**
     * This method is called when the activity is resumed, and it sets up the RecyclerView
     * to display a list of all terms.
     */
    @Override
    protected void onResume() {
        super.onResume();

        RecyclerView courseListRecycler = findViewById(R.id.course_list_recycler);
        repository = new Repository(getApplication());
        courseAdapter = new CourseAdapter(this);
        courseListRecycler.setAdapter(courseAdapter);
        courseListRecycler.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourses(repository.getAssociatedCourses(termID));

        // Display if there are no associated courses
        if(courseListRecycler.getAdapter() != null && courseListRecycler.getAdapter().getItemCount() == 0){
            Toast.makeText(this,"No courses. Please add a course.", Toast.LENGTH_SHORT).show();

        }
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
     * This method is called when the user clicks on the assessment floating action button, which
     * displays the submenu.
     *
     * @param view The view that triggered the event.
     */
    @SuppressLint("NonConstantResourceId")
    public void showSubMenu(View view) {
        PopupMenu termPopupMenu = new PopupMenu(this, view);
        termPopupMenu.getMenuInflater().inflate(R.menu.term_menu, termPopupMenu.getMenu());
        termPopupMenu.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.add_course:
                    Intent toAddCourse = new Intent(TermDetails.this, AddCourse.class);
                    toAddCourse.putExtra("term_id", termID);
                    startActivity(toAddCourse);
                    break;
                case R.id.delete_term:
                    if (courseAdapter.getItemCount() > 0 ){
                        Toast.makeText(TermDetails.this, "Please delete associated " +
                                "courses before deleting this term.",Toast.LENGTH_SHORT).show();
                    } else {
                        deleteTerm();
                    }
                    break;
            }
            return true;
        });
        termPopupMenu.show();
    }

    /**
     * This method is called when the user selects the delete option from the term submenu,
     * which deletes the current term.
     */
    private void deleteTerm() {
        Term term = new Term(termID, term_title.getText().toString(),
                startDatePicker.getText().toString(), endDatePicker.getText().toString());
        repository.delete(term);
        Toast.makeText(this, "Term was successfully deleted.",
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