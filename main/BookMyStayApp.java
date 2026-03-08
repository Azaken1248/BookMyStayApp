package com.seveneleven.bookmystayapp.main;

import java.util.Scanner;

import com.seveneleven.bookmystayapp.room.services.InventoryService;

public class BookMyStayApp {
	
	public static final InventoryService inventoryService= InventoryService.getInstance();
	public static final Scanner scanner = new Scanner(System.in);
	
	public static void handleAdminFlow() {
		
		boolean inAdminMenu = true;
		
		while(inAdminMenu) {
			System.out.println("---- Admin Panel ----");
			System.out.println("1. Add Room Type");
			System.out.println("2. Update Price");
			System.out.println("3. Update Count");
			System.out.println("4. Display Inventory");
			System.out.println("0. Exit");
			System.out.print("Enter your choice: ");
			String choice = scanner.nextLine();
			
			inAdminMenu = switch(choice) {
				case "1" -> {
					System.out.println("---- Add Room ----");
					
					System.out.print("Enter type: ");
					String type = scanner.nextLine().toLowerCase();
					
					System.out.print("Enter price: ");
					double price = scanner.nextDouble();
					
					System.out.print("Enter count: ");
					int count = scanner.nextInt();
					
					inventoryService.addRoom(type, price, count);
					
					scanner.nextLine();
					
					yield true;
				}
				
				case "2" -> {
					System.out.println("---- Update Price ----");
					
					System.out.print("Enter type: ");
					String type = scanner.nextLine().toLowerCase();
					
					System.out.print("Enter updated price: ");
					double price = scanner.nextDouble();
					
					inventoryService.updatePrice(type, price);
					
					scanner.nextLine();
					
					yield true;
				}
				
				case "3" -> {
					System.out.println("---- Update Count ----");
					
					System.out.print("Enter type: ");
					String type = scanner.nextLine().toLowerCase();
					
					System.out.print("Enter updated count: ");
					int count = scanner.nextInt();
					
					inventoryService.updateCount(type, count);
					
					scanner.nextLine();
					
					yield true;
				}
				
				case "4" -> {
					inventoryService.displayInventory();
					yield true;
				}
				
				case "0" -> {
					System.out.println("Exiting to main menu!");
					yield false;
				}
				
				default -> {
					System.out.println("Invalid Choice");
					yield true;
				}
			};
		}
	}
	
	public static void main(String[]args) {
		
		boolean inMainMenu = true;
		
		while(inMainMenu) {
			System.out.println("---- Main Menu ----");
			System.out.println("1. Login as admin");
			System.out.println("2. Login as user");
			System.out.println("0. Exit");
			System.out.print("Enter your choice: ");
			String choice = scanner.nextLine();
			
			inMainMenu = switch(choice) {
				case "1" -> {
					handleAdminFlow();
					yield true;
				}
				case "2" -> {
					System.out.println("User flow will be added here");
					yield true;
				}
				case "0" -> {
					System.out.println("Thank you!!");
					yield false;
				}
				default -> {
					System.out.println("Invalid choice!");
					yield true;
				}
			};
		}
		
	}
}
