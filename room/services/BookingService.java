package com.seveneleven.bookmystayapp.room.services;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class BookingService {

    private Set<String> bookedRoomIds;

    private HashMap<String, Set<String>> roomAllocations;
    
    private InventoryService inventoryService;
    private static BookingService currentInstance;
    
    /**
     * Private constructor to instantiate the BookingService
     */
    private BookingService() {
        this.bookedRoomIds = new HashSet<>();
        this.roomAllocations = new HashMap<>();
        this.inventoryService = InventoryService.getInstance();
    }
    
    /**
     * Method to return the current instance of BookingService
     * 
     * @return The current instance of BookingService
     */
    public static BookingService getInstance() {
        if (currentInstance == null) {
            currentInstance = new BookingService();
        }
        return currentInstance;
    }
    
    /**
     * Method to process a reservation to allocate the room
     * 
     * @param type	The type of room to be allocated
     * @return	The ID of the allocated room
     */
    public String allocateRoom(String type) {
        int availableCount = inventoryService.getAvailableCount(type);
        
        if (availableCount <= 0) {
            return null; 
        }
        
        Set<String> assignedRoomsForType = roomAllocations.getOrDefault(type, new HashSet<>());
        
        int roomNumber = assignedRoomsForType.size() + 1;
        String uniqueRoomId = type.toUpperCase() + "-" + roomNumber;
        
        while (bookedRoomIds.contains(uniqueRoomId)) {
            roomNumber++;
            uniqueRoomId = type.toUpperCase() + "-" + roomNumber;
        }
        
        assignedRoomsForType.add(uniqueRoomId);
        roomAllocations.put(type, assignedRoomsForType);
        bookedRoomIds.add(uniqueRoomId);
        
        inventoryService.updateCount(type, availableCount - 1);
        
        return uniqueRoomId;
    }
    
    /**
     * Method to check if a reservation ID is valid
     * 
     * @param reservationId The ID to be checked
     * @return	True if the reservation ID is valid else false
     */
    public boolean isValidReservation(String reservationId) {
    	return bookedRoomIds.contains(reservationId);
    }
    
    /**
     * Method to release a booked room
     * 
     * @param roomId	The ID of the room to be released
     * @return	True on successful release else false
     */
    public boolean releaseRoom(String roomId) {
        if (!bookedRoomIds.contains(roomId)) return false;
        
        bookedRoomIds.remove(roomId);
        String type = roomId.split("-")[0].toLowerCase();
        if (roomAllocations.containsKey(type)) {
            roomAllocations.get(type).remove(roomId);
        }
        
        int currentCount = inventoryService.getAvailableCount(type);
        inventoryService.updateCount(type, currentCount + 1);
        return true;
    }

}
