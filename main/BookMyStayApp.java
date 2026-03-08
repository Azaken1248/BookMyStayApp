package com.seveneleven.bookmystayapp.main;

import java.util.Scanner;

import com.seveneleven.bookmystayapp.reservation.Reservation;
import com.seveneleven.bookmystayapp.room.services.BookingQueueService;
import com.seveneleven.bookmystayapp.room.services.BookingService;
import com.seveneleven.bookmystayapp.room.services.InventoryService;
import com.seveneleven.bookmystayapp.room.services.SearchService;

/**
 * Main Entry point of the BookMyStayApp 
 * 
 * @author Developer
 * @version 4.0
 */
public class BookMyStayApp {

	public static final InventoryService inventoryService= InventoryService.getInstance();
	public static final SearchService searchService = SearchService.getInstance();
	public static final BookingQueueService bookingQueueService = BookingQueueService.getInstance();
	public static final BookingService bookingService = BookingService.getInstance();
	public static final Scanner scanner = new Scanner(System.in);

	/**
	 * Method to handle admin panel flow
	 */
	public static void handleAdminFlow() {

		boolean inAdminMenu = true;

		while(inAdminMenu) {
			System.out.println("---- Admin Panel ----");
			System.out.println("1. Add Room Type");
			System.out.println("2. Update Price");
			System.out.println("3. Update Count");
			System.out.println("4. Display Inventory");
			System.out.println("5. Process Bookings");
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
			case "5" -> {
				System.out.println("---- Process Booking ----");

				if(bookingQueueService.isQueueEmpty()) {
					System.out.println("The queue is empty. No pending booking requests!");
					yield true;
				}

				Reservation currentRequest = bookingQueueService.processNextRequest();
				String requestedType = currentRequest.getRoomType();

				System.out.printf("Processing request: Guest [%s] for Room [%s]...\n", 
						currentRequest.getGuestName(), requestedType);

				String allocatedRoomId = bookingService.allocateRoom(requestedType);

				if(allocatedRoomId != null) {
					System.out.printf("Result: Booking APPROVED! Room ID [%s] allocated to %s.\n", 
							allocatedRoomId, currentRequest.getGuestName());
				} else {
					System.out.println("Result: Booking REJECTED! Sorry, this room type is sold out.");
				}

				System.out.printf("Remaining people in queue: %d\n", bookingQueueService.getBookingQueue().size());
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

	/**
	 * Method to handle the guest menu flow
	 */
	public static void handleGuestFlow() {
		boolean inGuestMenu = true;

		while(inGuestMenu) {
			System.out.println("\n---- Guest Panel ----");
			System.out.println("1. View All Available Rooms");
			System.out.println("2. Search Specific Room Availability");
			System.out.println("3. Request a Room Booking"); // New option
			System.out.println("0. Exit");
			System.out.print("Enter your choice: ");
			String choice = scanner.nextLine();

			inGuestMenu = switch (choice) {
			case "1" -> {
				searchService.displayAvailableRooms();
				yield true;
			}
			case "2" -> {
				System.out.print("Enter room type to search: ");
				String type = scanner.nextLine().toLowerCase();
				searchService.checkAvailability(type);
				yield true;
			}
			case "3" -> {
				System.out.println("---- Book a Room ----");
				System.out.print("Enter your name: ");
				String guestName = scanner.nextLine();

				System.out.print("Enter room type to book: ");
				String type = scanner.nextLine().toLowerCase();

				// Basic validation: Check if room type exists before queueing
				if(searchService.checkAvailability(type)) {
					Reservation newReservation = new Reservation(guestName, type);
					bookingQueueService.addBookingRequest(newReservation);
				} else {
					System.out.println("Booking request aborted due to unavailability.");
				}
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

	/**
	 * Main method which acts as a runner for the app
	 * 
	 * @param args	Command-Line Arguments
	 */
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
				handleGuestFlow();
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
