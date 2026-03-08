package com.seveneleven.bookmystayapp.reservation;

public class Reservation {
    private String guestName;
    private String roomType;
    private String reservationId; 
    private String status; 
    
    /**
     * Constructor to instantiate a new reservation
     * 
     * @param guestName	Name of the guest
     * @param roomType	Requested room types
     */
    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.status = "PENDING";
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
     * Method to get the ID of the reservation
     * 
     * @return The ID of the reservation
     */
    public String getReservationId() { 
    	return reservationId; 
    }
    
    /**
     * Method to get the status of the current reservation
     * 
     * @return The status of the current reservation
     */
    public String getStatus() { return status; }
    
    /**
     * Method to set the reservation ID of the reservation
     * 
     * @param reservationId	The reservation ID to set
     */
    public void setReservationId(String reservationId) { 
    	this.reservationId = reservationId; 
    }
    
    /**
     * Method to set the status of the current Reservation
     * 
     * @param status	The status to set	
     */
    public void setStatus(String status) { this.status = status; }
    
    /**
     * Method to get a String representation of the reservation
     * 
     * @return String representation of the reservation
     */
    @Override
    public String toString() {
        return String.format("Reservation [ID: %s | Guest: %s | Room: %s | Status: %s]", 
                (reservationId != null ? reservationId : "N/A"), guestName, roomType, status);
    }
}