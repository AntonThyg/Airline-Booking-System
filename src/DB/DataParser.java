package DB;

import java.io.BufferedReader;
import java.io.FileReader;

public class DataParser {
    DataBase db;
    public DataParser(DataBase db){
        this.db=db;
        addFlights();
        addBookings();
    }
    public void addFlights(){
        String filePath = "C:\\Users\\athyg\\IdeaProjects\\Database programming\\src\\DB\\flights.csv";
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Read the header line
            line = br.readLine();
            if (line != null) {
                System.out.println("Headers: " + line);
            }

            // Read and process each subsequent line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                db.insertFlight(Integer.parseInt(values[0]),values[1],values[2],values[3]);

            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void addBookings(){
        String filePath = "C:\\Users\\athyg\\IdeaProjects\\Database programming\\src\\DB\\bookings.csv";
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Read the header line
            line = br.readLine();

            // Read and process each subsequent line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                db.insertBooking(Integer.parseInt(values[0]),values[1],Integer.parseInt(values[2]));
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
