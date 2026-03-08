package com.seveneleven.bookmystayapp.addon.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.seveneleven.bookmystayapp.addon.AddOn;

public class AddOnService {

    private Map<String, List<AddOn>> reservationServicesMap;

    private List<AddOn> availableServices;
    
    private static AddOnService currentInstance;
    
    /**
     * Private constructor to instantiate he AddOnService Instance
     */
    private AddOnService() {
        this.reservationServicesMap = new HashMap<>();
        this.availableServices = new ArrayList<>();

        availableServices.add(new AddOn("Breakfast", 500.0));
        availableServices.add(new AddOn("Spa Access", 1500.0));
        availableServices.add(new AddOn("Airport Pickup", 1000.0));
    }
    
    /**
     * Method to get the current AddOnService instance
     * 
     * @return The current AddOnService instance
     */
    public static AddOnService getInstance() {
        if (currentInstance == null) {
            currentInstance = new AddOnService();
        }
        return currentInstance;
    }
    
    /**
     * Method to display all available services
     */
    public void displayAvailableServices() {
        System.out.println("\n---- Available Add-On Services ----");
        for (int i = 0; i < availableServices.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, availableServices.get(i).toString());
        }
    }
   
    /**
     * Method to get a AddOn based on its index
     * 
     * @param index	The index to search
     * @return	The AddOn at that index
     */
    public AddOn getServiceByIndex(int index) {
        if (index >= 0 && index < availableServices.size()) {
            return availableServices.get(index);
        }
        return null;
    }
    
    /**
     * Method to add AddOns to the reservations
     * 
     * @param reservationId	The reservation ID of the Booking
     * @param service	The service to be added
     */
    public void addServiceToReservation(String reservationId, AddOn service) {
        if (reservationId == null || service == null) return;

        reservationServicesMap.putIfAbsent(reservationId, new ArrayList<>());

        reservationServicesMap.get(reservationId).add(service);
        System.out.printf("Success: [%s] added to Reservation ID [%s].\n", service.getName(), reservationId);
    }
    
    /**
     * Method to calculate the additional cost based on the Add-Ons
     * 
     * @param reservationId The reservation ID of the Booking
     * @return	Total additional cost for all added AddOns
     */
    public double calculateAdditionalCost(String reservationId) {
        List<AddOn> services = reservationServicesMap.getOrDefault(reservationId, new ArrayList<>());
        double totalCost = 0.0;
        
        for (AddOn service : services) {
            totalCost += service.getPrice();
        }
        return totalCost;
    }
    
    /**
     * Method to display the final bill
     * 
     * @param reservationId The reservation ID of the Booking
     */
    public void displayBillSummary(String reservationId) {
        List<AddOn> services = reservationServicesMap.getOrDefault(reservationId, new ArrayList<>());
        System.out.println("\n---- Add-On Summary for " + reservationId + " ----");
        if (services.isEmpty()) {
            System.out.println("No add-on services attached.");
            return;
        }
        
        for (AddOn service : services) {
            System.out.printf("- %s: Rs.%.2f\n", service.getName(), service.getPrice());
        }
        System.out.printf("Total Additional Cost: Rs.%.2f\n", calculateAdditionalCost(reservationId));
        System.out.println("----------------------------------------");
    }
}
