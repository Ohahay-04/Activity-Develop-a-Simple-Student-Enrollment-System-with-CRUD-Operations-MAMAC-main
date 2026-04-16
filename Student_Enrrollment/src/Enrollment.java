/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Asus
 */

public class Enrollment {
    private int enrollment_id;
    private String student_id;
    private String course_id;
    private String enrollment_date;

    // Default Constructor
    public Enrollment() {
    }

    // Getters and Setters
    public int getEnrollment_Id() {
        return enrollment_id;
    }

    public void setEnrollment_Id(int enrollment_id) {
        this.enrollment_id = enrollment_id;
    }

    public String getStudent_Id() {
        return student_id;
    }

    public void setStudent_Id(String student_id) {
        this.student_id = student_id;
    }

    public String getCourse_Id() {
        return course_id;
    }

    public void setCourse_Id(String course_id) {
        this.course_id = course_id;
    }

    public String getEnrollment_Date() {
        return enrollment_date;
    }

    public void setEnrollment_Date(String enrollment_date) {
        this.enrollment_date = enrollment_date;
    }
}