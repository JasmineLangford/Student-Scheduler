package com.example.student_scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.example.student_scheduler.R;
import com.example.student_scheduler.database.Repository;
import com.example.student_scheduler.entities.Assessment;
import com.example.student_scheduler.entities.Course;
import com.example.student_scheduler.entities.Term;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;
import java.util.Objects;

/**
 * This activity provides the user with a list of available terms displayed in a RecyclerView,
 * which pulls data from the database. Upon selection of a term, details for that term will be
 * populated on the next screen.
 * <p>
 * There is also a floating action button located at the bottom right for the user to add a new
 * term.
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

        // Extended FAB with sub menu
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ExtendedFloatingActionButton termFab = findViewById(R.id.terms_extended_fab);
        termFab.setOnClickListener(this::showSubMenu);
    }

    /**
     * This method is called when the activity is resumed, and it sets up the RecyclerView
     * to display a list of all terms.
     */
    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView termListRecycler = findViewById(R.id.term_list_recycler);
        final TermAdapter termAdapter = new TermAdapter(this);
        termListRecycler.setAdapter(termAdapter);
        termListRecycler.setLayoutManager(new LinearLayoutManager(this));
        Repository repository = new Repository(getApplication());
        List<Term> allTerms = repository.getAllTerms();
        termAdapter.setTerms(allTerms);
    }

    /**
     * This method is called when the floating action button is clicked. This popup menu provides
     * the user the option of adding a new term.
     */
    @SuppressLint("NonConstantResourceId")
    public void showSubMenu(View view) {
        PopupMenu termPopupMenu = new PopupMenu(this, view);
        termPopupMenu.getMenuInflater().inflate(R.menu.term_add, termPopupMenu.getMenu());
        termPopupMenu.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.add_term) {
                Intent toAddTerm = new Intent(TermList.this, AddTerm.class);
                startActivity(toAddTerm);
                return true;
            }
            return false;
        });
        termPopupMenu.show();
    }

    // TODO: Delete this boolean
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // TODO: Delete this boolean
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.addSampleData) {
            Repository repository = new Repository(getApplication());

            Term sampleTerm1 = new Term(0, "Term 1", "01/01/2023", "06/29/2023");
            repository.insert(sampleTerm1);
            Term sampleTerm2 = new Term(0, "Term 2", "07/01/2023", "12/29/2023");
            repository.insert(sampleTerm2);

            Course sampleCourse1 = new Course(0, 1, "Chemistry", "01/01/2023", "06/30/2023",
                    "In Progress", "Mr. Sunny", "652-985-9963", "sunny@wgu.edu", " ");
            repository.insert(sampleCourse1);
            Course sampleCourse2 = new Course(0, 1, "Anatomy", "01/01/2023", "06/30/2023",
                    "In Progress", "Mr. Sunny", "456-234-6677", "sunny@wgu.edu", " ");
            repository.insert(sampleCourse2);

            Assessment sampleAssessment1 = new Assessment(0, 2, "Performance Assessment", "Task 1", "03/29/2023");
            repository.insert(sampleAssessment1);
            Assessment sampleAssessment2 = new Assessment(0, 2, "Performance Assessment", "Task 2", "06/29/2023");
            repository.insert(sampleAssessment2);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}