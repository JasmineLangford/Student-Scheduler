package com.example.student_scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.example.student_scheduler.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class CourseDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Extended FAB with sub menu
//        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ExtendedFloatingActionButton courseFab = findViewById(R.id.courses_extended_fab);
//        courseFab.setOnClickListener(this::showSubMenu);
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
     * This method is called when the floating action button is clicked. This popup menu provides
     * the user two options: 1) adding a new course or 2) deleting the course.
     */
    @SuppressLint("NonConstantResourceId")
    public void showSubMenu(View view) {
        PopupMenu coursePopupMenu = new PopupMenu(this, view);
        coursePopupMenu.getMenuInflater().inflate(R.menu.course_menu, coursePopupMenu.getMenu());
        coursePopupMenu.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.add_course:
                    Intent toAddCourse = new Intent(CourseDetails.this, AddCourse.class);
                    startActivity(toAddCourse);
                    break;
                case R.id.delete_course:
                    // TODO: add delete functionality
                    return true;
            }
            return false;
        });
        coursePopupMenu.show();
    }
}