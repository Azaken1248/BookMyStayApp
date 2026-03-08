package com.seveneleven.bookmystayapp.room.services;


import com.seveneleven.bookmystayapp.reservation.Reservation;

import java.util.LinkedList;
import java.util.Queue;


public class BookingQueueService {

    private Queue<Reservation> bookingQueue;
    private static BookingQueueService currentInstance;
    
    /**
     * Private constructor to instantiate a Booking Queue service
     */
    private BookingQueueService() {
        this.bookingQueue = new LinkedList<>();
    }
    
    /**
     * Method to get an instance of the booking queue service
     * 
     * @return	The current instance of the booking queue
     */
    public static BookingQueueService getInstance() {
        if(currentInstance == null) {
            currentInstance = new BookingQueueService();
        }
        return currentInstance;
    }
    
    /**
     * Method to add a booking request to queue
     * 
     * @param reservation	The reservation to be added
     */
    public void addBookingRequest(Reservation reservation) {
        if(reservation == null || reservation.getGuestName().isEmpty() || reservation.getRoomType().isEmpty()) {
            System.out.println("Invalid booking request details.");
            return;
        }
        
        bookingQueue.offer(reservation); 
        
        System.out.println("-------------------------------------------");
        System.out.printf("Booking request received for %s.\n", reservation.getGuestName());
        System.out.println("Status: Enqueued and awaiting processing.");
        System.out.printf("Current position in queue: %d\n", bookingQueue.size());
        System.out.println("-------------------------------------------");
    }
    
   
    /**
     * Get the entire booking queue
     * 
     * @return	The booking queue
     */
    public Queue<Reservation> getBookingQueue() {
        return bookingQueue;
    }
    
    /**'
     * Check if the queue is empty
     * 
     * @return	True if the queue is empty else false
     */
    public boolean isQueueEmpty() {
        return bookingQueue.isEmpty();
    }
}
