/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Asus
 */
public class Course {
    private int course_id;
    private String course_name ;
    private String description;
    private int credits;
   

    // Default Constructor
    public Course() {}

    // Getters and Setters [cite: 51]
    public int getCourseId() { return course_id; }
    public void setCourseId(int courseId) { this.course_id = courseId; }

    public String getCourseName() { return course_name; }
    public void setCourseName(String courseName) { this.course_name = courseName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }

    void setEmail(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

 
}
