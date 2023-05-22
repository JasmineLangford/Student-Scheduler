package com.example.student_scheduler.database;

import android.app.Application;

import com.example.student_scheduler.dao.AssessmentDAO;
import com.example.student_scheduler.dao.CourseDAO;
import com.example.student_scheduler.dao.TermDAO;
import com.example.student_scheduler.entities.Assessment;
import com.example.student_scheduler.entities.Course;
import com.example.student_scheduler.entities.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class represents a repository that will interact with the scheduler database.
 */
public class Repository {
    private final TermDAO mTermDAO;
    private final CourseDAO mCourseDAO;
    private final AssessmentDAO mAssessmentDAO;
    private List<Term> mAllTerms;
    private List<Course> mAllCourses;
    private List<Assessment> mAllAssessments;
    private List<Course> mAssociatedCourses;
    private List<Assessment> mAssociatedAssessments;


    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /**
     * Constructs a new repository object using the given Application object.
     *
     * @param application The application object used to create the ScheduleDatabaseBuilder object.
     */
    public Repository(Application application) {
        SchedulerDatabaseBuilder db = SchedulerDatabaseBuilder.getDatabase(application);
        mTermDAO = db.termDAO();
        mCourseDAO = db.courseDAO();
        mAssessmentDAO = db.assessmentDAO();
    }

    public List<Term> getAllTerms() {
        databaseExecutor.execute(()-> mAllTerms = mTermDAO.getAllTerms());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }

    public List<Course> getAssociatedCourses(int termID) {
        databaseExecutor.execute(() -> mAssociatedCourses = mCourseDAO.getAssociatedCourses(termID));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAssociatedCourses;
    }

    public List<Course> getAllCourses() {
        databaseExecutor.execute(() -> mAllCourses = mCourseDAO.getAllCourses());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }

    public List<Assessment> getAllAssessments() {
        databaseExecutor.execute(() -> mAllAssessments = mAssessmentDAO.getAllAssessments());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    public List<Assessment> getAssociatedAssessments(int courseID) {
        databaseExecutor.execute(() -> mAssociatedAssessments = mAssessmentDAO.getAssociatedAssessments(courseID));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAssociatedAssessments;
    }

    public void insert(Term term) {
        databaseExecutor.execute(() -> mTermDAO.insert(term));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Term term) {
        databaseExecutor.execute(() -> mTermDAO.update(term));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Term term) {
        databaseExecutor.execute(() -> mTermDAO.delete(term));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insert(Course course) {
        databaseExecutor.execute(() -> mCourseDAO.insert(course));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Course course) {
        databaseExecutor.execute(() -> mCourseDAO.update(course));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Course course) {
        databaseExecutor.execute(() -> mCourseDAO.delete(course));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insert(Assessment assessment) {
        databaseExecutor.execute(() -> mAssessmentDAO.insert(assessment));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Assessment assessment) {
        databaseExecutor.execute(() -> mAssessmentDAO.update(assessment));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Assessment assessment) {
        databaseExecutor.execute(() -> mAssessmentDAO.delete(assessment));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
