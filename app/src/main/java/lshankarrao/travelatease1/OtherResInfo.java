package lshankarrao.travelatease1;

public class OtherResInfo {
    // protected int id;
    protected String startDate, endDate;
    protected String startTime, endTime;
    protected String typeofEvent;
    protected String address, city, state, country;
    protected String notes;
    protected long eventId;

    public OtherResInfo() {

    }

    public OtherResInfo(String startDate, String endDate, String startTime, String endTime, String typeofEvent, String address, String city, String state, String country, String notes, long eventId) {
        //this.OtherResInfo = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.typeofEvent = typeofEvent;
        this.address = address;
        this.notes = notes;
        this.city = city;
        this.state = state;
        this.country = country;
        this.eventId = eventId;
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

    public String getTypeofEvent() {
        return typeofEvent;
    }

    public void setTypeofEvent(String typeofEvent) {
        this.typeofEvent = typeofEvent;
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

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }
}
