package com.example.student_scheduler.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.student_scheduler.R;
import com.example.student_scheduler.database.Repository;
import com.example.student_scheduler.entities.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

/**
 * This activity provides the user with a list of terms along with the details of the term when
 * the expand arrow is clicked.
 */
public class TermList extends AppCompatActivity {
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        // SAMPLE DATA
        Term term = new Term(0, "Term 1", "01/01/2023","06/20/2023");
        Repository repository = new Repository(getApplication());

        // Display current terms from repository
//        RecyclerView termListRecycler = findViewById(R.id.term_list_recycler);
//        final TermAdapter termAdapter = new TermAdapter(this);
//        termListRecycler.setAdapter(termAdapter);
//        termListRecycler.setLayoutManager(new LinearLayoutManager(this));
//        repository=new Repository(getApplication());
//        List<Term> allTerms = repository.getAllTerms();
//        termAdapter.setTerms(allTerms);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fabTermDetails = findViewById(R.id.FAB_add_course);
        fabTermDetails.setOnClickListener(view -> showAddTermDialog());
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
     * This method contains the option to add a new term when the user clicks the floating action
     * button.
     */
    public void showAddTermDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("")
                .setItems(new String[]{"Add New Term"}, (dialog, which) -> {
                    Intent intent = new Intent(TermList.this, AddTerm.class);
                    startActivity(intent);
                });
        builder.create().show();
    }
}