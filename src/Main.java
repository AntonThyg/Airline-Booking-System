import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class Main {
    static Connection conn = null;

    public static void main(String[] args) {
        createConnection();
        if(conn!=null){
            createTable();
            insertInstructor(1, "John Doe", "johndoe@example.com", 50000, "Computer Science");
            selectAllInstructor();
        }

    }

    static void createConnection() {
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://csci-cs418-22.dhcp.bsu.edu/instructor",
                    "studentdba",
                    "K*hKSu%6yZ"
                    );

            System.out.println("Connection established.");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    static void createTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS instructor (" +
                "id INT PRIMARY KEY, " +
                "name VARCHAR(50), " +
                "email VARCHAR(100), " +
                "salary INT, " +
                "dname VARCHAR(30))";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Table created.");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    static void insertInstructor(int id, String name, String email, int salary, String dname) {
        String insertSQL = String.format(
                "INSERT INTO instructor (id, name, email, salary, dname) VALUES (%d, '%s', '%s', %d, '%s')",
                id, name, email, salary, dname);
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(insertSQL);
            System.out.println("Instructor inserted.");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    static void selectAllInstructor() {
        String selectSQL = "SELECT * FROM instructor";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Salary: " + rs.getInt("salary"));
                System.out.println("Department: " + rs.getString("dname"));
                System.out.println("----------");
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}
