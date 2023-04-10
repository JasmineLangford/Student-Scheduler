package UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.example.student_scheduler.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

/**
 * This activity provides the user with a list of terms along with the details of the term when
 * the expand arrow is clicked.
 */
public class TermList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        FloatingActionButton fabTerms = findViewById(R.id.floatingActionTermList);
        fabTerms.setOnClickListener(view -> showAddTermDialog());

        // Hide the action bar
        Objects.requireNonNull(getSupportActionBar()).hide();
    }
    public void showAddTermDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("")
                .setItems(new String[]{"Add New Term"}, (dialog, which) -> {
                    Intent intent = new Intent(TermList.this, TermDetails.class);
                    startActivity(intent);
                });
        builder.create().show();
    }
}