package lshankarrao.travelatease1;

public class HotelInfo {
    // protected int id;
    protected String hotel;
    protected String checkin;
    protected String checkout;
    protected String address;
    protected String confirmationNo;
    protected String notes;
    protected long eventId;

    public HotelInfo() {

    }

    public HotelInfo(String hotel, String address, String checkin, String checkout, String confirmationNo, String notes, long eventId) {
        //this.id = id;
        this.address = address;
        this.hotel = hotel;
        this.checkin = checkin;
        this.checkout = checkout;
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

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
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
