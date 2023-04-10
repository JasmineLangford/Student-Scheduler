package UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.student_scheduler.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TermDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        FloatingActionButton fabTermDetails = findViewById(R.id.FAB_term_details);
        fabTermDetails.setOnClickListener(view -> showTermSelections());
    }

    public void showTermSelections() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("")
                .setItems(new String[]{"Modify Term", "Add New Term", "Delete Term"},(dialog, which) -> {
                    Intent intent = new Intent(TermDetails.this, TermModify.class);
                    startActivity(intent);
                });
        builder.create().show();
    }
}