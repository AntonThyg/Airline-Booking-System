package UI;

import DB.DataBase;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingSystem {
    private DataBase database;
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField idField, nameField, flightIdField;

    public BookingSystem(DataBase database) {
        this.database = database;
        initComponents();
    }

    private void initComponents() {
        frame = new JFrame("Booking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLayout(new BorderLayout());

        // Table for displaying bookings
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Flight ID"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Panel for booking inputs and actions
        JPanel panel = new JPanel(new GridLayout(2, 1));
        JPanel inputPanel = new JPanel(new FlowLayout());
        idField = new JTextField(5);
        nameField = new JTextField(10);
        flightIdField = new JTextField(5);

        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Flight ID:"));
        inputPanel.add(flightIdField);

        // Button for booking
        JButton bookButton = new JButton("Book");
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookFlight();
            }
        });
        inputPanel.add(bookButton);
        panel.add(inputPanel);

        // Panel for view and cancel buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton viewButton = new JButton("View Reservations");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadBookings();
            }
        });
        buttonPanel.add(viewButton);

        JButton cancelButton = new JButton("Cancel Reservation");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelBooking();
            }
        });
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel);

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
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter valid numbers for ID and Flight ID.");
        }
    }

    private void loadBookings() {
        tableModel.setRowCount(0); // Clear existing rows
        try {
            ResultSet resultSet = database.viewAllBookings();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int flightId = resultSet.getInt("flight_id");
                tableModel.addRow(new Object[]{id, name, flightId});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error loading bookings.");
        }
    }

    private void cancelBooking() {
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

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        flightIdField.setText("");
    }
}

