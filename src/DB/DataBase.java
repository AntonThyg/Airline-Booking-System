package DB;
import java.sql.*;
public class DataBase {
    String host, database, user, password;
    Connection conn = null;

    public DataBase(String host, String database, String user, String password){
        this.host=host;
        this.database=database;
        this.user=user;
        this.password=password;

        createConnection();

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
        String createTableSQL = "create table if not exists flight(\n" +
                "\tid int primary key,\n" +
                "    destination varchar(100),\n" +
                "    departure varchar(100),\n" +
                "    time datetime\n" +
                ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Table created.");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    void insertBooking(String table, int id, String name, String email, int salary, String dname) {
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
    String viewBooking(String database, int id) {
        String selectSQL = "SELECT * FROM "+database+" where id = "+Integer.toString(id)+";";
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
        return "";
    }


}
