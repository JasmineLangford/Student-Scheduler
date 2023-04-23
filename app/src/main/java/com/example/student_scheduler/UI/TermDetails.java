package com.example.student_scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import com.example.student_scheduler.R;
import com.example.student_scheduler.database.Repository;
import com.example.student_scheduler.entities.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Objects;

/**
 * This activity allows the user to view details for a selected term.
 */
public class TermDetails extends AppCompatActivity {
    EditText editTermTitle;
    EditText editTermStart;
    EditText editTermEnd;
    String termTitleEdit;
    String termStartEdit;
    String termEndEdit;
    int termID;
    Term term;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        // Editable text fields
        editTermTitle = findViewById(R.id.term_title);
        editTermStart = findViewById(R.id.start_date);
        editTermEnd = findViewById(R.id.end_date);
        termTitleEdit = getIntent().getStringExtra("term_title");
        termStartEdit = getIntent().getStringExtra("term_start");
        termEndEdit = getIntent().getStringExtra("term_end");
        editTermTitle.setText(termTitleEdit);
        editTermStart.setText(termStartEdit);
        editTermEnd.setText(termEndEdit);

        // Save fields
        repository = new Repository(getApplication());
        termID = getIntent().getIntExtra("term_id",-1);
        Button button = findViewById(R.id.save_new_term);
        button.setOnClickListener(view -> {
            if (termID == -1) {
                term = new Term(0,editTermTitle.getText().toString(),
                        editTermStart.getText().toString(),editTermEnd.getText().toString());
                repository.insert(term);
            }
            else {
                term = new Term(0,editTermTitle.getText().toString(),
                        editTermStart.getText().toString(),editTermEnd.getText().toString());
                repository.update(term);
            }
        });

        // Display toolbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //
        FloatingActionButton coursesFAB = findViewById(R.id.to_course_details);
        coursesFAB.setOnClickListener(view -> {
            Intent intent = new Intent(TermDetails.this,CourseDetails.class);
            startActivity(intent);
        });
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

}