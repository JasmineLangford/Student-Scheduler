package UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.student_scheduler.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * This activity allows the user to view details for a selected term.
 */
public class TermDetails extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        FloatingActionButton fabTermDetails = findViewById(R.id.FAB_term_details);
        fabTermDetails.setOnClickListener(view -> showTermSelections());
    }

    /**
     * This method is a dialog box that shows the selections available to the user once the floating
     * action button is clicked. The user has the option to modify, add or delete a term.
     */
    public void showTermSelections() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("")

                // User selections for floating action button
                .setItems(new String[]{"Modify Term", "Add New Term", "Delete Term"},(dialog, which) -> {
                    Intent intent = new Intent(TermDetails.this, TermModify.class);
                    startActivity(intent);
                });
        builder.create().show();
    }
}