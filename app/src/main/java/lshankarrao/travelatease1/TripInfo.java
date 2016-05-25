package lshankarrao.travelatease1;

public class TripInfo {
   // protected int id;
    protected String title;
    protected String city;
    protected String state;
    protected String country;
    protected String notes;
    protected String startDate;
    protected String endDate;
    protected String startTime;
    protected String endTime;
    //protected String events;

    public TripInfo() {

    }

    public TripInfo(String title, String city, String state, String country,
                    String startDate, String endDate, String startTime, String endTime, String notes) {
        //this.id = id;
        this.title = title;
        this.city = city;
        this.state = state;
        this.country = country;
        this.notes = notes;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        //this.events = events;
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

    public String getEndDate() {
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    /*
    public String getEvents() {
        return events;
    }

    public void setEvents(String events) {
        this.events = events;
    }*/

    @Override
    public String toString(){
        return "";
    }
}
