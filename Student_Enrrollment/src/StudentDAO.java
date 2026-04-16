import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // CREATE: Insert a new student record [cite: 57]
    public void addStudent(Student student) {
        String sql = "INSERT INTO student (first_name, last_name, age, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = Dbconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setInt(3, student.getAge());
            stmt.setString(4, student.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ: Retrieve all students from the database [cite: 59]
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student";
        try (Connection conn = Dbconnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getInt("student_id"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setAge(rs.getInt("age"));
                student.setEmail(rs.getString("email"));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // UPDATE: Modify an existing student record [cite: 61]
    public void updateStudent(Student student) {
        String sql = "UPDATE student SET first_name=?, last_name=?, age=?, email=? WHERE student_id=?";
        try (Connection conn = Dbconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setInt(3, student.getAge());
            stmt.setString(4, student.getEmail());
            stmt.setInt(5, student.getStudentId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE: Remove a student based on student_id [cite: 63]
    public void deleteStudent(int studentId) {
        String sql = "DELETE FROM student WHERE student_id=?";
        try (Connection conn = Dbconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
  public List<Student> searchStudentsByCategory(String category, String keyword) {
        List<Student> students = new ArrayList<>();
        String sql = "";
        
        switch (category) {
            case "Student ID":
                sql = "SELECT * FROM student WHERE CAST(student_id AS CHAR) LIKE ?";
                break;
            case "First Name":
                sql = "SELECT * FROM student WHERE first_name LIKE ?";
                break;
            case "Last Name":
                sql = "SELECT * FROM student WHERE last_name LIKE ?";
                break;
            case "Email":
                sql = "SELECT * FROM student WHERE email LIKE ?";
                break;
            default: // "All"
                sql = "SELECT * FROM student WHERE CAST(student_id AS CHAR) LIKE ? OR first_name LIKE ? OR last_name LIKE ?";
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
                    Student student = new Student();
                    student.setStudentId(rs.getInt("student_id"));
                    student.setFirstName(rs.getString("first_name"));
                    student.setLastName(rs.getString("last_name"));
                    student.setAge(rs.getInt("age"));
                    student.setEmail(rs.getString("email"));
                    students.add(student);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }
}