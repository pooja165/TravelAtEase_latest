package lshankarrao.travelatease1;

public class EventInfo {
   // protected int id;
    protected String title;
    protected String city;
    protected String state;
    protected String country;
    protected String address;
    protected String startDate;
    protected String endDate;
    protected String startTime;
    protected String endTime;
    protected long tripId;

    public EventInfo() {

    }

    public EventInfo(String title, String startDate, String endDate, String startTime, String endTime, String address, String city, String state, String country, long tripId) {
        //this.id = id;
        this.title = title;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tripId = tripId;
        this.startTime = startTime;
        this.endTime = endTime;
    }
/*
    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }*/

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public long getTripId() {
        return tripId;
    }

    public void setEndDate(long tripId) {
        this.tripId = tripId;
    }
}
