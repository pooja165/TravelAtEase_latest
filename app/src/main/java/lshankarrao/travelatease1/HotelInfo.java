package lshankarrao.travelatease1;

public class HotelInfo {
    // protected int id;
    protected String hotel;
    protected String checkin_date, checkin_time;
    protected String checkout_date, checkout_time;
    protected String address;
    protected String confirmationNo;
    protected String notes;
    protected long eventId;

    public int getId() {
        return id;
    }

    protected int id;

    public HotelInfo() {

    }

    public HotelInfo(int id, String hotel, String address, String checkin_date, String checkout_date, String checkin_time, String checkout_time, String confirmationNo, String notes, long eventId) {
        this.id = id;
        this.address = address;
        this.hotel = hotel;
        this.checkin_date = checkin_date;
        this.checkout_date = checkout_date;
        this.checkin_time = checkin_time;
        this.checkout_time = checkout_time;
        this.checkout_time = checkout_time;
        this.confirmationNo = confirmationNo;
        this.notes = notes;
        this.eventId = eventId;
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

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getCheckin_date() {
        return checkin_date;
    }

    public void setCheckin_date(String checkin_date) {
        this.checkin_date = checkin_date;
    }

    public String getCheckout_date() {
        return checkout_date;
    }

    public void setCheckout_date(String checkout_date) {
        this.checkout_date = checkout_date;
    }

    public String getCheckin_time() {
        return checkin_time;
    }

    public void setCheckin_time(String checkin_time) {
        this.checkin_time = checkin_time;
    }

    public String getCheckout_time() {
        return checkout_time;
    }

    public void setCheckout_time(String checkout_time) {
        this.checkout_time = checkout_time;
    }

    public String getConfirmationNo() {
        return confirmationNo;
    }

    public void setConfirmationNo(String confirmationNo) {
        this.confirmationNo = confirmationNo;
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

    public void setEventId(long tripId) {
        this.eventId = eventId;
    }
}
