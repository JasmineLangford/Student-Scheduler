package com.example.student_scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.student_scheduler.R;
import com.example.student_scheduler.database.Repository;
import com.example.student_scheduler.entities.Term;
import java.util.Objects;

public class AddTerm extends AppCompatActivity {

    EditText editTermTitle;
    EditText editTermStart;
    EditText editTermEnd;
    String termTitleEdit;
    String termStartEdit;
    String termEndEdit;
    int termID;
    Term term;
    Repository repository;

    // Confirmation Messages
    String confirmMessage = "New term was successfully added.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);

        // Editable text fields
        editTermTitle = findViewById(R.id.term_title_edit);
        editTermStart = findViewById(R.id.term_start_edit);
        editTermEnd = findViewById(R.id.term_end_edit);
        termTitleEdit = getIntent().getStringExtra("term_title");
        termStartEdit = getIntent().getStringExtra("term_start");
        termEndEdit = getIntent().getStringExtra("term_end");
        editTermTitle.setText(termTitleEdit);
        editTermStart.setText(termStartEdit);
        editTermEnd.setText(termEndEdit);

        // Cancel and go back to list of terms
        Button cancel = findViewById(R.id.cancel_button);
        cancel.setOnClickListener(view -> finish());

        // Save fields
        repository = new Repository(getApplication());
        termID = getIntent().getIntExtra("term_id",-1);
        Button button = findViewById(R.id.save_new_term);
        button.setOnClickListener(view -> {
            if (termID == -1) {
                term = new Term(0, editTermTitle.getText().toString(),
                        editTermStart.getText().toString(), editTermEnd.getText().toString());
                repository.insert(term);
                Toast.makeText(getApplication(),confirmMessage,Toast.LENGTH_SHORT).show();

                // Back to screen with list of terms
                Intent intent = new Intent(this, TermList.class);
                startActivity(intent);
            }
        });
        // Display toolbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
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