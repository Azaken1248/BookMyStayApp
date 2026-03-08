package com.seveneleven.bookmystayapp.room.services;

import java.util.ArrayList;
import java.util.List;

import com.seveneleven.bookmystayapp.reservation.Reservation;

public class ReportingService {

    private List<Reservation> bookingHistory;
    private static ReportingService currentInstance;
    
    /**
     * Private constructor to instantiate a reporting service
     */
    private ReportingService() {
        this.bookingHistory = new ArrayList<>();
    }
    
    /**
     * Method to get the current instance of the reporting service
     * 
     * @return The current instance of the reporting service
     */
    public static ReportingService getInstance() {
        if (currentInstance == null) {
            currentInstance = new ReportingService();
        }
        return currentInstance;
    }
    
    /**
     * Method to confirm a booking
     * 
     * @param reservation	The reservation to confirm
     */
    public void addConfirmedBooking(Reservation reservation) {
        reservation.setStatus("CONFIRMED");
        bookingHistory.add(reservation);
    }

    /**
     * Method to cancel a booking
     * 
     * @param reservationId	The ID which is to be cancelled
     * @return	True on successful cancel else false
     */
    public boolean cancelBooking(String reservationId) {
        for (Reservation res : bookingHistory) {
            if (res.getReservationId() != null && res.getReservationId().equalsIgnoreCase(reservationId)) {
                if (res.getStatus().equals("CANCELLED")) {
                    System.out.println("This booking is already cancelled.");
                    return false;
                }
                res.setStatus("CANCELLED");
                System.out.println("Success: Booking [" + reservationId + "] has been cancelled.");
                return true;
            }
        }
        System.out.println("Error: Reservation ID not found in system.");
        return false;
    }
    
    /**
     * Method to generate a report on the entire history
     */
    public void generateReport() {
        System.out.println("\n========= BOOKING HISTORY REPORT =========");
        if (bookingHistory.isEmpty()) {
            System.out.println("No booking history available yet.");
        } else {
            int confirmed = 0;
            int cancelled = 0;
            
            for (Reservation res : bookingHistory) {
                System.out.println(res.toString());
                if (res.getStatus().equals("CONFIRMED")) confirmed++;
                else if (res.getStatus().equals("CANCELLED")) cancelled++;
            }
            
            System.out.println("------------------------------------------");
            System.out.printf("Total Records: %d | Active: %d | Cancelled: %d\n", 
                    bookingHistory.size(), confirmed, cancelled);
        }
        System.out.println("==========================================");
    }
}


