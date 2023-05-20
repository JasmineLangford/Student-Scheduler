package com.example.student_scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.student_scheduler.R;
import com.example.student_scheduler.database.Repository;
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

        // Display current terms
        RecyclerView termListRecycler = findViewById(R.id.term_list_recycler);
        final TermAdapter termAdapter = new TermAdapter(this);
        termListRecycler.setAdapter(termAdapter);
        termListRecycler.setLayoutManager(new LinearLayoutManager(this));
        Repository repository = new Repository(getApplication());
        List<Term> allTerms = repository.getAllTerms();
        termAdapter.setTerms(allTerms);

        // Display if there are no terms
        if(termListRecycler.getAdapter() != null &&
                termListRecycler.getAdapter().getItemCount() == 0){
            Toast.makeText(this,"No terms. Please add a term.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method is called when the floating action button is clicked. This popup menu provides
     * the user the option of adding a new term.
     */
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
}