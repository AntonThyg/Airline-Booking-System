package DB;
import com.sun.source.tree.LambdaExpressionTree;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

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

    public void insertFlight(int id, String destination, String departure, String time){
        String insertSQL = "INSERT INTO flight (id, destination, departure, time) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertSQL)) {
            stmt.setInt(1, id);
            stmt.setString(2, destination);
            stmt.setString(3, departure);
            stmt.setString(4, time);
            stmt.executeUpdate();
            System.out.println("Flight inserted.");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
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

    public ArrayList<Booking> viewAllBookings() {
        String selectSQL = "SELECT * FROM booking";
        ArrayList<Booking> results = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(selectSQL);
            while (resultSet.next()) {
                results.add(new Booking(
                        resultSet.getInt("booking_id"),
                        resultSet.getString("name"),
                        resultSet.getInt("flight_id")
                ));
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return results;
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

    public ArrayList<Object[]> searchFlightByName(String searchText){
        String query = """
            SELECT b.id AS booking_id, b.name, b.flight_id, f.destination, f.departure, f.time
            FROM booking b
            JOIN flight f ON b.flight_id = f.id
            WHERE b.name LIKE ?;
        """;
        ArrayList<Object[]> results = new ArrayList<>();
        try (var stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + searchText + "%");
            try (ResultSet resultSet = stmt.executeQuery()) {
                results.add(new Object[]{
                        resultSet.getInt("booking_id"),
                        resultSet.getString("name"),
                        resultSet.getInt("flight_id"),
                        resultSet.getString("destination"),
                        resultSet.getString("departure"),
                        resultSet.getString("time")
                });
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return results;
    }

    public ArrayList<Object[]> getMerged(){
        String query = """
            SELECT b.id AS booking_id, b.name, b.flight_id, f.destination, f.departure, f.time
            FROM booking b
            JOIN flight f ON b.flight_id = f.id;
        """;
        ArrayList<Object[]> results = new ArrayList<>();
        try (ResultSet resultSet = conn.createStatement().executeQuery(query)) {
            while (resultSet.next()) {
                results.add(new Object[]{
                        resultSet.getInt("booking_id"),
                        resultSet.getString("name"),
                        resultSet.getInt("flight_id"),
                        resultSet.getString("destination"),
                        resultSet.getString("departure"),
                        resultSet.getString("time")
                });
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return results;
    }
}
