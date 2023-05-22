package com.example.student_scheduler.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
/**
 * This class represents the assessments table in the database.
 */
@Entity(tableName = "assessments")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessmentID;
    private int courseID;
    private String assessmentType;
    private String assessmentTitle;
    private String assessmentEndDate;

    public Assessment(int assessmentID, int courseID, String assessmentTitle, String assessmentType,
                      String assessmentEndDate) {
        this.assessmentID = assessmentID;
        this.courseID = courseID;
        this.assessmentTitle = assessmentTitle;
        this.assessmentType = assessmentType;
        this.assessmentEndDate = assessmentEndDate;
    }

    public Assessment() {

    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public String getAssessmentEndDate() {
        return assessmentEndDate;
    }

    public void setAssessmentEndDate(String assessmentEndDate) {
        this.assessmentEndDate = assessmentEndDate;
    }
}
