import DB.DataBase;

public class Main {
    public static void main(String[] args) {
        DataBase db = new DataBase(
                "jdbc:mysql://csci-cs418-22.dhcp.bsu.edu/",
                "Antone_Project2",
                "studentdba",
                "K*hKSu%6yZ");
    }

}
