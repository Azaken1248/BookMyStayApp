package com.seveneleven.bookmystayapp.room.services;

import java.util.HashMap;
import java.util.HashSet;

public class InventoryService {
	private HashMap<String, Integer> countMap;
	private HashMap<String, Double> priceMap;
	private HashSet<String> types;
	
	private static InventoryService currentInstance;
	
	/**
	 * Private constructor to initialize an inventory service
	 */
	private InventoryService() {
		this.countMap = new HashMap<>();
		this.priceMap = new HashMap<>();
		this.types = new HashSet<>();
	}
	
	/**
	 * Method to return a single instance of the Inventory service class
	 * 
	 * @return	An instance of the inventory service class
	 */
	public static InventoryService getInstance() {
		if(currentInstance == null) {
			currentInstance = new InventoryService();
			return currentInstance;
		}
		
		return currentInstance;
	}
	
	/**
	 * Method to add a room to the inventory
	 * 
	 * @param type	The type of the room
	 * @param price	The price per room per night
	 * @param count	The number of rooms available
	 */
	public void addRoom(String type, double price, int count) {
		if(price < 0) {
			System.out.println("Price cannot be negative!");
			return;
		}
		if(count < 0) {
			System.out.println("Count cannot be negative!");
			return;
		}
		if(type == null || type.isEmpty()) {
			System.out.println("type cannot be null or empty!");
			return;
		}
		if(types.contains(type)) {
			System.out.printf("Room type [%s] already present!\n", type);
			return;
		}
		
		types.add(type);
		priceMap.put(type, price);
		countMap.put(type, count);
		
		System.out.printf("Room type [%s] with [%d] rooms and Cost Rs.[%.2f] /room/night added successfully.\n", type, count, price);
	}
	
	/**
	 * Method to update the room count based on availability
	 * @param type	The type of the room whose count is to be updated
	 * @param count	The new count of that room type
	 */
	public void updateCount(String type, int count) {
		if(!types.contains(type)) {
			System.out.println("The entered room type does not exist!");
			return;
		}
		if(count < 0) {
			System.out.println("Count cannot be negative!");
			return;
		}
		
		countMap.put(type, count);
		
		System.out.printf("Count of [%s] type room updated to [%d]\n", type, count);
	}
	
	/**
	 * Method to update the price of the room
	 * @param type	The type of the room who's price is to be updated
	 * @param price	The new price of that room
	 */
	public void updatePrice(String type, double price) {
		if(!types.contains(type)) {
			System.out.println("The entered room type does not exist!");
			return;
		}
		if(price < 0) {
			System.out.println("Price cannot be negative!");
			return;
		}
		
		priceMap.put(type, price);

		System.out.printf("Price of [%s] type room updated to [%.2f]\n", type, price);
	}
	
	/**
	 * Method to display the entire inventory
	 */
	public void displayInventory() {
		System.out.println("-----------------INVENTORY-----------------");
		System.out.println("TYPE\tPRICE\tCOUNT");
		for(String type : types) {
			System.out.printf("%s\t%.2f\t%d\n", type, priceMap.get(type), countMap.get(type));
		}
		System.out.println("-------------------------------------------");
	}
	
}
