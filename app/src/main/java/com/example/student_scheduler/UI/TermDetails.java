package com.example.student_scheduler.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.student_scheduler.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

/**
 * This activity allows the user to view details for a selected term. The data is populated into
 * EditText fields for the user to modify or delete. If there are associated courses with this term,
 * the user will not be allowed to delete the term until all courses have been deleted.
 * <p>
 * The user can also use the floating action button in the bottom right-hand corner to view
 * additional options related to the term.
 */
public class TermDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        // Display toolbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // FAB options for selection term
        FloatingActionButton term_options = findViewById(R.id.FAB_term_options);
        term_options.setOnClickListener(view -> showTermOptionDialog());
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

    /**
     * This method is called when the floating action button is clicked. This alert dialog provides
     * the user two options: 1) adding a new term or 2) viewing courses related to the selected term.
     */
    public void showTermOptionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("")
                .setItems(new String[]{"Add New Term", "View Courses"}, (dialog, which) -> {
                    if (which == 0) {
                        Intent intent = new Intent(TermDetails.this, AddTerm.class);
                        startActivity(intent);
                    } else if (which == 1) {
                        Intent intent = new Intent(TermDetails.this, CourseList.class);
                        startActivity(intent);
                    }
                });
        builder.create().show();
    }
}