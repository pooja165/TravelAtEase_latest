package lshankarrao.travelatease1;

public class EventInfo {
   // protected int id;
    protected String city;
    protected String state;
    protected String country;
    protected String address;
    protected String startDate;
    protected String endDate;
    protected long tripId;

    public EventInfo() {

    }

    public EventInfo(String startDate, String endDate, String address, String city, String state, String country, long tripId) {
        //this.id = id;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tripId = tripId;
    }
/*
    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }*/

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String city) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDateDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public long getTripId() {
        return tripId;
    }

    public void setEndDate(long tripId) {
        this.tripId = tripId;
    }
}
