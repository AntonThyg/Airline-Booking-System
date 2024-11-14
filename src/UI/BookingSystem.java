package UI;

import DB.DataBase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class BookingSystem implements ActionListener {
    ArrayList<JButton> buttons = new ArrayList<>();
    JFrame frame = new JFrame();

    JLabel text = new JLabel();
    Font font = new Font("font",Font.PLAIN,40);
    boolean reset=false;
    DataBase db;

    public BookingSystem(DataBase db){
        this.db=db;
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
