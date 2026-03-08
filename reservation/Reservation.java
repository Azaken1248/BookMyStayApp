package com.seveneleven.bookmystayapp.reservation;

public class Reservation {
    private String guestName;
    private String roomType;
    
    /**
     * Constructor to instantiate a new reservation
     * 
     * @param guestName	Name of the guest
     * @param roomType	Requested room types
     */
    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
    
    /**
     * Method to get the guest name
     * 
     * @return	The method to get the guest name
     */
    public String getGuestName() {
        return guestName;
    }
    
    /**
     * Method to get the room type
     * 
     * @return	The method to get the room type
     */
    public String getRoomType() {
        return roomType;
    }
    
    /**
     * Method to get a String representation of the reservation
     * 
     * @return String representation of the reservation
     */
    @Override
    public String toString() {
        return "Reservation [Guest: " + guestName + ", Room Type: " + roomType + "]";
    }
}