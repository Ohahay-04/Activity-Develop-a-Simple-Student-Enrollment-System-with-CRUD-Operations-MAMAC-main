import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO {

    // CREATE: Enroll a student into a course
    public void addEnrollment(Enrollment enrollment) {
        // MATCHED: Now correctly uses student_id and course_id
        String sql = "INSERT INTO enrolled_subject (student_id, course_id, enrollment_date) VALUES (?, ?, ?)";
        
        try (Connection conn = Dbconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, enrollment.getStudent_Id());
            stmt.setString(2, enrollment.getCourse_Id());
            stmt.setString(3, enrollment.getEnrollment_Date());
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage()); 
        }
    }

    // READ: Retrieve all enrollments to show in the JTable
    public List<Enrollment> getAllEnrollments() {
      List<Enrollment> list = new ArrayList<>();
        
        // THE FIX: We use SQL JOINs to link the IDs to their actual names in the other tables!
        String sql = "SELECT e.enrollment_id, " +
                     "CONCAT(s.first_name, ' ', s.last_name) AS full_student_name, " +
                     "c.course_name AS full_course_name, " +
                     "e.enrollment_date " +
                     "FROM enrolled_subject e " +
                     "JOIN student s ON e.student_id = s.student_id " +
                     "JOIN course c ON e.course_id = c.course_id";
        
        try (Connection conn = Dbconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Enrollment e = new Enrollment();
                
                e.setEnrollment_Id(rs.getInt("enrollment_id"));
                
                // Instead of pulling the raw ID, we pull the combined names from our SQL query
                e.setStudent_Id(rs.getString("full_student_name")); 
                e.setCourse_Id(rs.getString("full_course_name"));
                
                e.setEnrollment_Date(rs.getString("enrollment_date"));
                
                list.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // DELETE: Remove an enrollment based on enrollment_id
    public boolean deleteEnrollment(int enrollmentId) {
        String sql = "DELETE FROM enrolled_subject WHERE enrollment_id = ?";
        boolean isDeleted = false;
        
        try (Connection conn = Dbconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, enrollmentId);
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                isDeleted = true; // Successfully deleted
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return isDeleted;
    }
  public List<Enrollment> searchEnrollmentsByCategory(String category, String keyword) {
        List<Enrollment> list = new ArrayList<>();
        String sql = "";
        
        String baseQuery = "SELECT e.enrollment_id, " +
                           "CONCAT(s.first_name, ' ', s.last_name) AS full_student_name, " +
                           "c.course_name AS full_course_name, " +
                           "e.enrollment_date " +
                           "FROM enrolled_subject e " +
                           "JOIN student s ON e.student_id = s.student_id " +
                           "JOIN course c ON e.course_id = c.course_id ";
                           
        switch (category) {
            case "Enrollment ID":
                sql = baseQuery + "WHERE CAST(e.enrollment_id AS CHAR) LIKE ?";
                break;
            case "Student Name":
                sql = baseQuery + "WHERE s.first_name LIKE ? OR s.last_name LIKE ?";
                break;
            case "Course Name":
                sql = baseQuery + "WHERE c.course_name LIKE ?";
                break;
            case "Date":
                sql = baseQuery + "WHERE e.enrollment_date LIKE ?";
                break;
            default: // "All"
                sql = baseQuery + "WHERE CAST(e.enrollment_id AS CHAR) LIKE ? OR s.first_name LIKE ? OR s.last_name LIKE ? OR c.course_name LIKE ?";
                break;
        }

        try (Connection con = Dbconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            String searchPattern = "%" + keyword + "%";
            
            if (category.equals("Student Name")) {
                ps.setString(1, searchPattern);
                ps.setString(2, searchPattern);
            } else if (category.equals("Course Name") || category.equals("Date") || category.equals("Enrollment ID")) {
                ps.setString(1, searchPattern);
            } else { // All requires 4 slots now!
                ps.setString(1, searchPattern);
                ps.setString(2, searchPattern);
                ps.setString(3, searchPattern);
                ps.setString(4, searchPattern);
            }
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Enrollment e = new Enrollment();
                    e.setEnrollment_Id(rs.getInt("enrollment_id"));
                    e.setStudent_Id(rs.getString("full_student_name")); 
                    e.setCourse_Id(rs.getString("full_course_name"));
                    e.setEnrollment_Date(rs.getString("enrollment_date"));
                    list.add(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    }
