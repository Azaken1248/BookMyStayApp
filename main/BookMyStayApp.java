package com.seveneleven.bookmystayapp.main;

import java.util.Scanner;

import com.seveneleven.bookmystayapp.addon.AddOn;
import com.seveneleven.bookmystayapp.addon.services.AddOnService;
import com.seveneleven.bookmystayapp.reservation.Reservation;
import com.seveneleven.bookmystayapp.room.services.BookingQueueService;
import com.seveneleven.bookmystayapp.room.services.BookingService;
import com.seveneleven.bookmystayapp.room.services.InventoryService;
import com.seveneleven.bookmystayapp.room.services.ReportingService;
import com.seveneleven.bookmystayapp.room.services.SearchService;

/**
 * Main Entry point of the BookMyStayApp 
 * 
 * @author Developer
 * @version 6.0
 */
public class BookMyStayApp {

	public static final InventoryService inventoryService= InventoryService.getInstance();
	public static final SearchService searchService = SearchService.getInstance();
	public static final BookingQueueService bookingQueueService = BookingQueueService.getInstance();
	public static final BookingService bookingService = BookingService.getInstance();
	public static final AddOnService addOnService = AddOnService.getInstance();
	public static final ReportingService reportingService = ReportingService.getInstance();
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
			System.out.println("6. Generate Booking History Report");
			System.out.println("7. Cancel Booking");
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
				String allocatedRoomId = bookingService.allocateRoom(requestedType);
				
				if(allocatedRoomId != null) {
					currentRequest.setReservationId(allocatedRoomId);
					reportingService.addConfirmedBooking(currentRequest);
					
					System.out.printf("Result: APPROVED! Room ID [%s] allocated to %s.\n", 
							allocatedRoomId, currentRequest.getGuestName());
				} else {
					System.out.println("Result: REJECTED! Sorry, this room type is sold out.");
				}
				yield true;
			}
			
			case "6" -> {
				reportingService.generateReport();
				yield true;
			}
			
			case "7" -> {
				System.out.println("---- Cancel Booking ----");
				System.out.print("Enter Reservation ID to cancel: ");
				String cancelId = scanner.nextLine().toUpperCase();
				
				if(reportingService.cancelBooking(cancelId)) {
					bookingService.releaseRoom(cancelId);
					System.out.println("Inventory updated: Room returned to available pool.");
				}
				yield true;
			}			case "0" -> {
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
			System.out.println("3. Request a Room Booking");
			System.out.println("4. Add Extra Services");
			System.out.println("5. Cancel Booking");
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

				if(searchService.checkAvailability(type)) {
					Reservation newReservation = new Reservation(guestName, type);
					bookingQueueService.addBookingRequest(newReservation);
				} else {
					System.out.println("Booking request aborted due to unavailability.");
				}
				yield true;
			}
			case "4" -> {
				System.out.println("---- Add Services to Booking ----");
				System.out.print("Enter your assigned Reservation/Room ID (e.g., DELUXE-1): ");
				String reservationId = scanner.nextLine().toUpperCase();
				
				if(!bookingService.isValidReservation(reservationId)) {
					System.out.println("Invalid Reservation ID. Please check with the admin.");
					yield true;
				}
				
				boolean addingServices = true;
				while(addingServices) {
					addOnService.displayAvailableServices();
					System.out.println("0. Done / View Summary");
					System.out.print("Select a service to add: ");
					int serviceChoice = scanner.nextInt();
					scanner.nextLine();
					if(serviceChoice == 0) {
						addOnService.displayBillSummary(reservationId);
						addingServices = false;
					} else {
						AddOn selectedService = addOnService.getServiceByIndex(serviceChoice - 1);
						
						if(selectedService != null) {
							addOnService.addServiceToReservation(reservationId, selectedService);
						} else {
							System.out.println("Invalid selection.");
						}
					}
				}
				yield true;
			}
			case "5" -> {
				System.out.println("---- Cancel Booking ----");
				System.out.print("Enter Reservation ID to cancel: ");
				String cancelId = scanner.nextLine().toUpperCase();
				
				if(reportingService.cancelBooking(cancelId)) {
					bookingService.releaseRoom(cancelId);
					System.out.println("Inventory updated: Room returned to available pool.");
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
