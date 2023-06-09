package com.example.student_scheduler.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.student_scheduler.entities.Course;

import java.util.List;

/**
 * This interface defines the database operations that can be performed on the Course entity.
 */
@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM COURSES WHERE termID= :termID ORDER BY courseTitle ASC")
    List<Course> getAssociatedCourses(int termID);
}