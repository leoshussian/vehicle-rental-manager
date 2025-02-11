package com.vrm.vehicle;

/**
 * Stores and manages all company cars.
 * Handles vehicle creation and update. Stores fleet in arrays by type of vehicle.
 */
public class Fleet {
    private final int MAX_CAPACITY;
    private Vehicle[] vehicles;
    private int vehicleCount;

    // CONSTRUCTORS
    public Fleet(int capacity) {
        MAX_CAPACITY = capacity;
        vehicles = new Vehicle[capacity];
        vehicleCount = 0;
    }
    public Fleet(){
        MAX_CAPACITY = 10;
        vehicles = new Vehicle[MAX_CAPACITY];
        vehicleCount = 0;
    }
    public Fleet(Fleet fleet){
        this(fleet.MAX_CAPACITY);
    }

    // ARRAY OPERATIONS
    public boolean addVehicle(Vehicle vehicle) {
        // Check capacity
        if (vehicleCount >= MAX_CAPACITY) return false;

        // Check nonnull
        if (vehicle == null) return false;

        // Add vehicle
        vehicles[vehicleCount] = vehicle.clone();
        vehicleCount++;
        return true;
    }
    public boolean removeVehicle(int index) {
        // Check index valid
        if (index < 0 || index >= vehicleCount) return false;

        // Remove vehicle
        vehicles[index] = vehicles[vehicleCount - 1];
        vehicles[vehicleCount - 1] = null;
        vehicleCount--;
        return true;
    }
    public boolean updateVehicle(int index, Vehicle vehicle) {
        // Check index valid
        if (index < 0 || index >= vehicleCount) return false;

        // Check nonnull
        if (vehicles[index] == null) return false;

        // Overwrite old vehicle
        vehicles[index] = vehicle;
        return true;
    }
    public Vehicle retrieveVehicle(int index) {
        if (index < 0 || index >= vehicleCount) return null;
        return vehicles[index].clone();
    }
    public Vehicle retrieveVehicle(String plateNumber){
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getPlateNumber().equals(plateNumber)) return vehicle.clone();
        }
        return null;
    }

    // GETTERS
    public Vehicle[] getVehicles() {
        // Manual copy of index
        Vehicle[] returnArray = new Vehicle[vehicleCount];
        for (int i = 0; i < vehicleCount; i++) {
            returnArray[i] = vehicles[i] == null ? null : vehicles[i].clone();
        }
        return returnArray;
    }
    public int getVehicleCount() {
        return vehicleCount;
    }
    public int getMAX_CAPACITY() {
        return MAX_CAPACITY;
    }

    // BY CATEGORY

    /**
     * @param type Type of vehicle: Diesel Truck (DT), Electric Truck (ET), Electric Car (EC), Gas Car (GC).
     * @return array of vehicles of specified type
     */
    public Vehicle[] filterVehicles(String type) {
        Vehicle[] returnArray = new Vehicle[vehicleCount];
        int count = 0;
        for (int i = 0; i < vehicleCount; i++) {
            if (vehicles[i] == null) continue;
            // Check if the vehicle contains the requested type
            if (vehicles[i].toString().toLowerCase().contains(type.toLowerCase())){
                // Add vehicle clone to return array
                returnArray[count] = vehicles[i].clone();
                // Increment index
                count++;
            }
        }
        return returnArray;
    }

    // DISPLAY METHODS
    /**
     * Fetches array of specified type then calls {@link this.showArray()}
     * @param type Type of vehicle: Diesel Truck (DT), Electric Truck (ET), Electric Car (EC), Gas Car (GC).
     * @return String of array of vehicles with header.
     */
    public String showOfType(String type){
        if (vehicles == null || vehicles.length == 0) return "Fleet contains no vehicles.";
        Vehicle[] array = filterVehicles(type);
        return showArray(array, vehicleCount);
    }

    @Override
    public String toString(){
        return showArray(vehicles, vehicleCount);
    }

    // MISC.
    /**
     * Helper method.
     * @param vehicleArray of vehicles of same type.
     * @return String of array vehicles with header.
     */
    public static String showArray(Vehicle[] vehicleArray, int vehicleCount) {
        String returnString = "";

        // Check for empty or null fleet
        if (vehicleArray == null || vehicleArray.length == 0) return "Fleet contains no vehicles.";

        for (int i = 0; i < vehicleCount; i++){

            if (vehicleArray[i] == null) continue;

            // If the next vehicle is of a new type add header
            if (i == 0 || vehicleArray[i].getClass() != vehicleArray[i-1].getClass())
                returnString += vehicleArray[i].getHeader() + "\n";

            returnString += i + " " + vehicleArray[i].toString() + "\n";
        }
        return returnString;
    }
}
