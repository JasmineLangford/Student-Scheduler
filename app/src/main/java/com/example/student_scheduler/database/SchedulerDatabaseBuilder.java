package com.example.student_scheduler.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.student_scheduler.dao.AssessmentDAO;
import com.example.student_scheduler.dao.CourseDAO;
import com.example.student_scheduler.dao.TermDAO;
import com.example.student_scheduler.entities.Assessment;
import com.example.student_scheduler.entities.Course;
import com.example.student_scheduler.entities.Term;

/**
 * This class represents the SQLite database for the scheduler application.
 */
@Database(entities = {Term.class, Course.class, Assessment.class}, version = 3, exportSchema = false)
public abstract class SchedulerDatabaseBuilder extends RoomDatabase {

    /**
     * Returns an instance of TermDAO, which provides access to the Term table in the database.
     */
    public abstract TermDAO termDAO();

    /**
     * Returns an instance of CourseDAO, which provides access to the Course table in the database.
     */
    public abstract CourseDAO courseDAO();

    /**
     * Returns an instance of AssessmentDAO, which provides access to the Assessment table in the
     * database.
     */
    public abstract AssessmentDAO assessmentDAO();

    public static volatile SchedulerDatabaseBuilder INSTANCE;

    static SchedulerDatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SchedulerDatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    SchedulerDatabaseBuilder.class, "SchedulerDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
