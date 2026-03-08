package com.seveneleven.bookmystayapp.addon;

public class AddOn {
    private String name;
    private double price;
    
    /**
     * Constructor to create a new Add-On
     * 
     * @param name	The name of the Add-On
     * @param price	The price of the Add-On
     */
    public AddOn(String name, double price) {
        this.name = name;
        this.price = price;
    }
    
    /**
     * Method to get the name of the Add-On
     * 
     * @return	The name of the Add-On
     */
    public String getName() {
        return name;
    }
    
    /**
     * Method to get the price of the Add-On
     * 
     * @return The price of the Add-On
     */
    public double getPrice() {
        return price;
    }
    
    /**
     * Method to get the Add-On as a String
     * 
     * @return The Add-On as a String
     */
    @Override
    public String toString() {
        return name + " (Rs." + price + ")";
    }
}