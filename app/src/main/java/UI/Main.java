package UI;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.student_scheduler.R;
import java.util.Objects;

/**
 * This is the main activity in the student scheduler.
 */
public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // View Schedule button
        Button button = findViewById(R.id.view_schedule);
        button.setOnClickListener(new View.OnClickListener() {

            /**
             * Method called when view schedule button is clicked.
             *
             * @param view The view that was clicked.
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main.this, TermList.class);
                startActivity(intent);
            }
        });

        // Hide the action bar
        Objects.requireNonNull(getSupportActionBar()).hide();
    }
}