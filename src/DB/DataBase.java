package DB;
import java.sql.*;
public class DataBase {
    String host, database, user, password;
    Connection conn = null;

    public DataBase(String host, String database, String user, String password){

    }

    void createConnection() {
        try {
            conn = DriverManager.getConnection(
                    this.host+this.database,
                    this.user,
                    this.password
            );
            System.out.println("Connection established.");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    void createTables() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS (" +
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



}
