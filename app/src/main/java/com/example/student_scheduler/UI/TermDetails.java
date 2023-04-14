package com.example.student_scheduler.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.example.student_scheduler.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Objects;

/**
 * This activity allows the user to view details for a selected term.
 */
public class TermDetails extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fabTermDetails = findViewById(R.id.FAB_term_details);
        fabTermDetails.setOnClickListener(view -> addNewCourse());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
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
     * This method is a dialog box that shows the selections available to the user once the floating
     * action button is clicked. The user has the option to modify, add or delete a term.
     */
    public void addNewCourse() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("")

                // User selections for floating action button
                .setItems(new String[]{"Add New Course"}, (dialog, which) -> {
                    Intent intent = new Intent(TermDetails.this, TermAdd.class);
                    startActivity(intent);
                });
        builder.create().show();
    }
}