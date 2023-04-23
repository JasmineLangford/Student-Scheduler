package com.example.student_scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.student_scheduler.R;
import com.example.student_scheduler.database.Repository;
import com.example.student_scheduler.entities.Course;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

/**
 * This activity provides the user with a list of available courses that are associated with the
 * term they have selected. Upon selection of a course, details of the course will be populated on
 * the next screen.
 */
public class CourseList extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        // Display associated courses with the term
        RecyclerView courseListRecycler = findViewById(R.id.course_list_recycler);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        courseListRecycler.setAdapter(courseAdapter);
        courseListRecycler.setLayoutManager(new LinearLayoutManager(this));
        Repository repository = new Repository(getApplication());
        List<Course> allCourses = repository.getAllCourses();
        courseAdapter.setCourses(allCourses);

        // Display toolbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // FAB navigates to add new course
        FloatingActionButton courseFab = findViewById(R.id.FAB_add_course);
        courseFab.setOnClickListener(view -> {
            Intent intent = new Intent (CourseList.this, AddCourse.class);
            startActivity(intent);
        });
    }
//
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
}