/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
 import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
 

public class CourseDAO {

    private String CourseName;

    
    public List<Course> getAllCourses() {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM course"; 
        
        
        try (Connection con = Dbconnection.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Course c = new Course();
                c.setCourseId(rs.getInt("course_id"));
                c.setCourseName(rs.getString("course_name"));
                
                
                c.setDescription(rs.getString("course_description")); 
                c.setCredits(rs.getInt("credits"));
                
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return list;
    }

    public void addCourse(Course course) {
        String sql = "INSERT INTO course (course_name,course_description,credits) VALUES (?, ?, ?)";
        try (Connection conn = Dbconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, course.getCourseName());
            stmt.setString(2, course.getDescription());
            stmt.setInt(3, course.getCredits());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

 
    public void updateCourseByName(Course course) throws Exception {
        // NOTE: Make sure "course_description" and "credits" are the EXACT column names in your MySQL database!
        String sql = "UPDATE course SET course_description=?, credits=? WHERE course_name=?";
        
        try (Connection conn = Dbconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, course.getDescription());
            stmt.setInt(2, course.getCredits());
            stmt.setString(3, course.getCourseName());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected == 0) {
                
                throw new Exception("Could not find the course '" + course.getCourseName() + "' in the database.");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Database error: " + e.getMessage());
        }
    }

    
 public void deleteCourseByName(Object courseId) {
        String sql = "DELETE FROM course WHERE course_id = ?";
        
        try (Connection conn = Dbconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            
            stmt.setObject(1, courseId); 
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
           JOptionPane.showMessageDialog(null, e);
        }
 }

    public List<Course> searchCoursesByCategory(String category, String keyword) {
        List<Course> list = new ArrayList<>();
        String sql = "";
        
        switch (category) {
            case "Course ID":
                sql = "SELECT * FROM course WHERE CAST(course_id AS CHAR) LIKE ?";
                break;
            case "Course Name":
                sql = "SELECT * FROM course WHERE course_name LIKE ?";
                break;
            case "Description":
                sql = "SELECT * FROM course WHERE course_description LIKE ?";
                break;
            case "Credits":
                sql = "SELECT * FROM course WHERE CAST(credits AS CHAR) LIKE ?";
                break;
            default: // "All"
                sql = "SELECT * FROM course WHERE CAST(course_id AS CHAR) LIKE ? OR course_name LIKE ? OR course_description LIKE ?";
                break;
        }

        try (Connection con = Dbconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            String searchPattern = "%" + keyword + "%";
            
            if (category.equals("All") || category.equals("default")) {
                ps.setString(1, searchPattern);
                ps.setString(2, searchPattern);
                ps.setString(3, searchPattern); 
            } else {
                ps.setString(1, searchPattern);
            }
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Course c = new Course();
                    c.setCourseId(rs.getInt("course_id"));
                    c.setCourseName(rs.getString("course_name"));
                    c.setDescription(rs.getString("course_description")); 
                    c.setCredits(rs.getInt("credits"));
                    list.add(c);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}