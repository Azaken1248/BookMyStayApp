package com.seveneleven.bookmystayapp.room.services;

public class SearchService {
	
	private static SearchService currentInstance;
	private final InventoryService inventoryService;
	
	/**
	 * Private constructor to initialize a SearchService Instance
	 */
	private SearchService() {
		this.inventoryService = InventoryService.getInstance();
	}
	
	/**
	 * Method to create a single instance of SearchService
	 * 
	 * @return	A single SearchService instance
	 */
	public static SearchService getInstance() {
		if(currentInstance == null) {
			currentInstance = new SearchService();
		}
		return currentInstance;
	}
	
	/**
	 * Method to display all available rooms
	 */
	public void displayAvailableRooms() {
		System.out.println("-------------AVAILABLE ROOMS---------------");
		System.out.println("TYPE\tPRICE\tAVAILABLE");
		boolean hasAvailableRooms = false;
		
		for(String type : inventoryService.getAllRoomTypes()) {
			int count = inventoryService.getAvailableCount(type);
			if(count > 0) { 
				System.out.printf("%s\t%.2f\t%d\n", type, inventoryService.getPrice(type), count);
				hasAvailableRooms = true;
			}
		}
		
		if (!hasAvailableRooms) {
			System.out.println("Sorry, no rooms are currently available.");
		}
		System.out.println("-------------------------------------------");
	}
	
	/**
	 * Method to check availability of a certain room type
	 * 
	 * @param type	The type of room who's availibility is to be checked
	 * @return	True if the room is available else false
	 */
	public boolean checkAvailability(String type) {
		if(type == null || type.isEmpty()) {
			System.out.println("Search query cannot be empty!");
			return false;
		}
		if(!inventoryService.roomExists(type)) {
			System.out.println("We do not offer this room type.");
			return false;
		}
		
		int count = inventoryService.getAvailableCount(type);
		if(count <= 0) {
			System.out.printf("Sorry, [%s] rooms are currently sold out.\n", type);
			return false;
		}
		
		System.out.printf("Room Available! Type: [%s] | Price: Rs.[%.2f] | Remaining: %d\n", type, inventoryService.getPrice(type), count);
		return true; 
	}
}
