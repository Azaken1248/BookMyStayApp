# BookMyStayApp

A console-based Java application designed to manage hotel room bookings, demonstrating how core data structures and software engineering principles solve complex real-time inventory management and reservation allocation problems while maintaining code quality and scalability.

## [Use Case 1](https://github.com/Azaken1248/bookmystayapp/tree/feature/UC1-BookMyStayApp)

To maintain a single source of truth for hotel room inventory by initializing room types, counts, and prices dynamically.

#### key concept

(1) Using **HashMap** and **HashSet** data structures to establish centralized inventory management and ensure O(1) fast lookup for room counts and pricing.

```java
public class InventoryService {
	private HashMap<String, Integer> countMap;
	private HashMap<String, Double> priceMap;
	private HashSet<String> types;

	public void addRoom(String type, double price, int count) {
		// validation logic...
		types.add(type);
		priceMap.put(type, price);
		countMap.put(type, count);
	}
}

```

(2) **Data Consistency** to prevent the overbooking and outdated counts commonly caused by manual registers, providing real-time availability status.

## [Use Case 2](https://github.com/Azaken1248/bookmystayapp/tree/feature/UC2-BookMyStayApp)

To allow guests to search rooms and view pricing securely without altering the underlying inventory.

#### key concept

(1) Implementing **Read-Only Access** and **Defensive Checks** via a dedicated Service class to iterate through the data structures safely.

```java
public class SearchService {
	public void displayAvailableRooms() {
		for(String type : inventoryService.getAllRoomTypes()) {
			int count = inventoryService.getAvailableCount(type);
			if(count > 0) { 
				System.out.printf("%s\t%.2f\t%d\n", type, inventoryService.getPrice(type), count);
			}
		}
	}
}

```

(2) **Availability Validation** to accurately filter and display only available room types, guaranteeing guests cannot proceed with booking unavailable rooms.

## [Use Case 3](https://github.com/Azaken1248/bookmystayapp/tree/feature/UC3-BookMyStayApp)

To ensure booking fairness during peak demand by accepting requests and enforcing strict arrival order.

#### key concept

(1) Utilizing a **Queue** (LinkedList) to enforce the **FIFO (First-In-First-Out)** principle, creating a predictable booking order without race conditions.

```java
public class BookingQueueService {
	private Queue<Reservation> bookingQueue;

	public void addBookingRequest(Reservation reservation) {
		// validation logic...
		bookingQueue.offer(reservation); 
		System.out.printf("Current position in queue: %d\n", bookingQueue.size());
	}
}

```

(2) **Awaiting Processing** to handle high-traffic scenarios by allowing requests to enqueue securely rather than processing them in parallel and causing inconsistent allocations.

## [Use Case 4](https://github.com/Azaken1248/bookmystayapp/tree/feature/UC4-BookMyStayApp)

To guarantee zero double-booking by securely assigning unique room IDs and instantly synchronizing inventory.

#### key concept

(1) Using a combination of **Set** (HashSet) and **HashMap** to enforce uniqueness and prevent the reuse of room IDs.

```java
public class BookingService {
	public String allocateRoom(String type) {
		// availability check...
		int roomNumber = assignedRoomsForType.size() + 1;
		String uniqueRoomId = type.toUpperCase() + "-" + roomNumber;
		
		while (bookedRoomIds.contains(uniqueRoomId)) {
			roomNumber++;
			uniqueRoomId = type.toUpperCase() + "-" + roomNumber;
		}
		
		bookedRoomIds.add(uniqueRoomId);
		inventoryService.updateCount(type, availableCount - 1);
		return uniqueRoomId;
	}
}

```

(2) **Atomic Allocation** (logical) to ensure that assigning a room ID, adding it to the sets, and decrementing the total count happen sequentially for conflict-free allocations.

## [Use Case 5](https://github.com/Azaken1248/bookmystayapp/tree/feature/UC5-BookMyStayApp)

To enhance bookings with optional services by attaching multiple add-ons and calculating additional costs automatically.

#### key concept

(1) Implementing a **One-to-Many Mapping** using a Map that maps a single reservation ID to a List of complex objects, allowing multiple services per booking.

```java
public class AddOnService {
	private Map<String, List<AddOn>> reservationServicesMap;

	public void addServiceToReservation(String reservationId, AddOn service) {
		reservationServicesMap.putIfAbsent(reservationId, new ArrayList<>());
		reservationServicesMap.get(reservationId).add(service);
	}
}

```

(2) **Composition** and an **Extensible Service Model** to flexibly attach items (like breakfast or spa access) and calculate exact bills, eliminating manual billing errors.

## [Use Case 6](https://github.com/Azaken1248/bookmystayapp/tree/feature/UC6-BookMyStayApp)

To maintain a complete booking history by storing confirmed reservations and supporting cancellations and audits.

#### key concept

(1) Utilizing a **List** data structure for **Ordered Data Storage** to persist records of confirmed reservations in the sequence they were processed.

```java
public class ReportingService {
	private List<Reservation> bookingHistory;

	public void addConfirmedBooking(Reservation reservation) {
		reservation.setStatus("CONFIRMED");
		bookingHistory.add(reservation);
	}
}

```

(2) **Historical Tracking** and **Audit Support** to provide administrators with a reliable trail for generating reports, reviewing system usage, and gracefully handling customer cancellations.

## Integration Pipeline

Each feature is developed on a separate branch merged to [dev](https://github.com/Azaken1248/bookmystayapp/tree/dev) after testing.
