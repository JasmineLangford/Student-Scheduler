package com.example.student_scheduler.UI;

import androidx.appcompat.app.AlertDialog;
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

public class CourseList extends AppCompatActivity {
    private Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        RecyclerView recyclerView = findViewById(R.id.course_list_recycler);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        repository = new Repository(getApplication());
        List<Course> allCourses = repository.getAllCourses();
        FloatingActionButton fab = findViewById(R.id.FAB_add_course);
        courseAdapter.setCourses(allCourses);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (CourseList.this, CourseDetails.class);
                startActivity(intent);
            }
        });

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        //FloatingActionButton fabTermDetails = findViewById(R.id.FAB_add_course);
        //fabTermDetails.setOnClickListener(view -> showAddTermDialog());
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
     * This method contains the option to add a new term when the user clicks the floating action
     * button.
     */
    public void showAddTermDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("")
                .setItems(new String[]{"Add New Course"}, (dialog, which) -> {
                    Intent intent = new Intent(CourseList.this, AddCourse.class);
                    startActivity(intent);
                });
        builder.create().show();
    }
}