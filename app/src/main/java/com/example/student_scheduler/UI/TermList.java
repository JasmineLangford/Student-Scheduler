package com.example.student_scheduler.UI;

import androidx.appcompat.app.AlertDialog;
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
 * This activity provides the user with a list of terms along with the details of the term when
 * the expand arrow is clicked.
 */
public class TermList extends AppCompatActivity {
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        //Display current terms from repository
        RecyclerView termListRecycler = findViewById(R.id.term_list_recycler);
        final TermAdapter termAdapter = new TermAdapter(this);
        termListRecycler.setAdapter(termAdapter);
        termListRecycler.setLayoutManager(new LinearLayoutManager(this));
        repository=new Repository(getApplication());
        List<Term> allTerms = repository.getAllTerms();
        termAdapter.setTerms(allTerms);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        FloatingActionButton termsFAB = findViewById(R.id.to_term_details);
        termsFAB.setOnClickListener(view -> {
            Intent intent = new Intent(TermList.this,TermDetails.class);
            startActivity(intent);
        });
    }

//    /**
//     * This method handles the click event for the back button in the action bar. When the back
//     * button is clicked, `onBackPressed()` is called to go back to the previous activity.
//     */
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                onBackPressed();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addSampleData:
                Term term2 = new Term(0, "Term 2", "07/01/2023",
                        "12/30/2023");
                Repository repository = new Repository(getApplication());
                repository.insert(term2);
                Course course1 = new Course(0, 1, "Chemistry", "01/01/2023", "06/30/2023",
                        "In Progress", "Mr. Sunny", "456-234-6677", "sunny@wgu.edu", " ");
                repository.insert(course1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    /**
//     * This method contains the option to add a new term when the user clicks the floating action
//     * button.
//     */
//    public void showAddTermDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("")
//                .setItems(new String[]{"Add New Term"}, (dialog, which) -> {
//                    Intent intent = new Intent(TermList.this, AddTerm.class);
//                    startActivity(intent);
//                });
//        builder.create().show();
//    }
}