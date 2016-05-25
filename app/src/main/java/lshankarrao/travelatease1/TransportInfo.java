package lshankarrao.travelatease1;

public class TransportInfo {
    // protected int id;
    protected String fromPlace, fromDate,fromTime;
    protected String toPlace, toDate, toTime;
    protected String typeofTransport;
    protected String confNo;
    protected String flightNo;
    protected String notes;
    protected long eventId;

    public TransportInfo() {

    }

    public TransportInfo(String fromPlace, String fromDate, String fromTime, String toPlace, String toDate, String toTime, String typeofTransport, String confNo, String flightNo, String notes, long eventId) {
        //this.id = id;
        this.fromPlace = fromPlace;
        this.fromDate = fromDate;
        this.fromTime = fromTime;
        this.toPlace = toPlace;
        this.toDate = toDate;
        this.toTime = toTime;
        this.notes = notes;
        this.typeofTransport = typeofTransport;
        this.confNo = confNo;
        this.flightNo = flightNo;
        this.eventId = eventId;
    }
/*
    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }*/

    public String getFromPlace() {
        return fromPlace;
    }

    public void setFromPlace(String fromPlace) {
        this.fromPlace = fromPlace;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToPlacePlace() {
        return toPlace;
    }

    public void setToPlace(String toPlace) {
        this.toPlace = toPlace;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getTypeofTransport() {
        return typeofTransport;
    }

    public void setTypeofTransport(String typeofTransport) {
        this.typeofTransport = typeofTransport;
    }

    public String getConfNo() {
        return confNo;
    }

    public void setConfNo(String confNo) {
        this.confNo = confNo;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
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
