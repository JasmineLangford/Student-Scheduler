package com.example.student_scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.student_scheduler.R;
import com.example.student_scheduler.database.Repository;
import com.example.student_scheduler.entities.Course;
import com.example.student_scheduler.entities.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

/**
 * This activity provides the user with a list of available terms displayed in a recyclerview,
 * which pulls data from the database. Upon selection of a term, details for that term will be
 * populated on the next screen.
 */
public class TermList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        // Display current terms in recyclerview
        RecyclerView termListRecycler = findViewById(R.id.term_list_recycler);
        final TermAdapter termAdapter = new TermAdapter(this);
        termListRecycler.setAdapter(termAdapter);
        termListRecycler.setLayoutManager(new LinearLayoutManager(this));
        Repository repository = new Repository(getApplication());
        List<Term> allTerms = repository.getAllTerms();
        termAdapter.setTerms(allTerms);

        // Display toolbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // FAB navigates to add new term
        FloatingActionButton termFAB = findViewById(R.id.FAB_add_term);
        termFAB.setOnClickListener(view -> {
            Intent intent = new Intent(TermList.this, AddTerm.class);
            startActivity(intent);
        });
    }

    // TODO: Delete this boolean
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // TODO: Delete this boolean
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addSampleData:
                Term term1 = new Term(0, "Term 1", "01/01/2023",
                        "06/30/2023");
                Repository repository = new Repository(getApplication());
                repository.insert(term1);
                Course course1 = new Course(0, 1, "Chemistry", "01/01/2023", "06/30/2023",
                        "In Progress", "Mr. Sunny", "456-234-6677", "sunny@wgu.edu", " ");
                repository.insert(course1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}