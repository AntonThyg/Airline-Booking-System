package UI;

import DB.DataBase;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingSystem {
    private DataBase database;
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField idField, nameField, flightIdField, searchField;

    public BookingSystem(DataBase database) {
        this.database = database;
        initComponents();
    }

    private void initComponents() {
        frame = new JFrame("Booking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLayout(new BorderLayout());

        // Table for displaying bookings with flight info
        tableModel = new DefaultTableModel(new Object[]{"Booking ID", "Name", "Flight ID", "Destination", "Departure", "Time"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Panel for booking inputs and actions
        JPanel panel = new JPanel(new GridLayout(3, 1));
        JPanel inputPanel = new JPanel(new FlowLayout());
        idField = new JTextField(5);
        nameField = new JTextField(10);
        flightIdField = new JTextField(5);
        searchField = new JTextField(10);

        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Flight ID:"));
        inputPanel.add(flightIdField);

        // Button for booking
        JButton bookButton = new JButton("Book");
        bookButton.addActionListener(e -> bookFlight());
        inputPanel.add(bookButton);
        panel.add(inputPanel);

        // Panel for view and cancel buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton viewButton = new JButton("View Reservations");
        viewButton.addActionListener(e -> {
            try {
                loadBookings();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        buttonPanel.add(viewButton);

        JButton cancelButton = new JButton("Cancel Reservation");
        cancelButton.addActionListener(e -> {
            try {
                cancelBooking();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        buttonPanel.add(cancelButton);

        panel.add(buttonPanel);

        // Search panel
//        JPanel searchPanel = new JPanel(new FlowLayout());
//        searchPanel.add(new JLabel("Search by Name:"));
//        searchPanel.add(searchField);
//        JButton searchButton = new JButton("Search");
//        searchButton.addActionListener(e -> {
//            try {
//                searchBookingsByName();
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
//        });
//        searchPanel.add(searchButton);
//        panel.add(searchPanel);

        frame.add(panel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void bookFlight() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            int flightId = Integer.parseInt(flightIdField.getText());

            database.insertBooking(id, name, flightId);
            JOptionPane.showMessageDialog(frame, "Booking added successfully.");
            clearFields();
            loadBookings();
        } catch (NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter valid numbers for ID and Flight ID.");
        }
    }

    private void loadBookings() throws SQLException {
        tableModel.setRowCount(0); // Clear existing rows
        var resultSet = database.getMerged();
        for (Object[] o: resultSet) {
            tableModel.addRow(o);
        }
    }

    private void cancelBooking() throws SQLException {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a booking to cancel.");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        database.deleteBooking(id);
        JOptionPane.showMessageDialog(frame, "Booking canceled successfully.");
        loadBookings();
    }

    private void searchBookingsByName() throws SQLException {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a name to search.");
            return;
        }

        tableModel.setRowCount(0); // Clear existing rows before adding search results

        var resultSet = database.searchFlightByName(searchText);
        for (Object[] o: resultSet) tableModel.addRow(o);
    }


    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        flightIdField.setText("");
    }
}

