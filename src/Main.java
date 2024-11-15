import DB.DataBase;
import UI.BookingSystem;

public class Main {
    public static void main(String[] args) {
        DataBase db = new DataBase(
                "jdbc:mysql://csci-cs418-22.dhcp.bsu.edu/",
                "Antone_Project2",
                "studentdba",
                "K*hKSu%6yZ"
        );
        // uncomment if you want to add a random set of data
        // DataParser dp = new DataParser(db);
        BookingSystem system = new BookingSystem(db);
    }
}
