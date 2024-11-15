package DB;

public class Flight {
    //id,destination,departure,time
    int id;
    String destination, departure, time;

    public Flight(int id, String destination, String departure, String time){
        this.id=id;
        this.destination=destination;
        this.departure=departure;
        this.time=time;
    }

    public int getId() {
        return id;
    }

    public String getDeparture() {
        return departure;
    }

    public String getDestination() {
        return destination;
    }

    public String getTime() {
        return time;
    }
}
