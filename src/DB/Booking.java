package DB;

public class Booking {
    //id,name,flight_id
    int id, flightID;
    String name;
    public Booking(int id, String name, int flightID) {
        this.id=id;
        this.flightID =flightID;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public int getFlightID() {
        return flightID;
    }

    public int getId() {
        return id;
    }
}
