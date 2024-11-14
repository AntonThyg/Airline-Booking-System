package DB;
import java.sql.*;

public class DataBase {
    String host, database, user, password;
    Connection conn = null;

    public DataBase(String host, String database, String user, String password){
        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;
        createConnection();
    }

    void createConnection() {
        try {
            conn = DriverManager.getConnection(
                    this.host + this.database,
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

    public void insertFlight(int id, String destination, String departure, int time){

    }

    public void insertBooking(int id, String name, int flightId) {
        String insertSQL = "INSERT INTO booking (id, name, flight_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertSQL)) {
            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setInt(3, flightId);
            stmt.executeUpdate();
            System.out.println("Booking inserted.");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public ResultSet viewAllBookings() {
        String selectSQL = "SELECT * FROM booking";
        try {
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(selectSQL);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return null;
    }

    public void deleteBooking(int id) {
        String deleteSQL = "DELETE FROM booking WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(deleteSQL)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Booking deleted.");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}
