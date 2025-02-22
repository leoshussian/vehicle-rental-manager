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
    public boolean appendArray(Vehicle[] newVehicles) {
        if (newVehicles == null) return false;
        if (this.vehicleCount + newVehicles.length >= MAX_CAPACITY) return false;

        for (Vehicle vehicle : newVehicles) {
            if (vehicle == null) {
                continue;
            }
            this.vehicles[vehicleCount] = vehicle.clone();
            this.vehicleCount++;
        }
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
        vehicles[index] = vehicle.clone();
        return true;
    }
    public Vehicle retrieveVehicle(int index) {
        if (index < 0 || index >= vehicleCount) return null;
        return vehicles[index].clone();
    }
    public Vehicle retrieveVehicle(String plateNumber){
        for (Vehicle vehicle : vehicles) {
            if (vehicle != null && vehicle.getPlateNumber().equals(plateNumber))
                return vehicle.clone();
        }
        return null;
    }
    public boolean toggleLeased(String plateNumber) {
        int count = 0;
        for (Vehicle vehicle : vehicles) {
            if (vehicle == null) continue;
            else if (vehicle.getPlateNumber().equals(plateNumber)) {
                vehicle.setIsLeased(!vehicle.getIsLeased());
                count++;
            }
        }
        return count != 0;
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
    // TODO this is broken
    public Vehicle[] filterVehicles(String type) {
        // Create return array
        Vehicle[] returnArray = new Vehicle[vehicleCount];
        int count = 0;

        // Get type
        Vehicle dummyVehicle = switch (type) {
            case "DT", "Diesel Truck", "diesel truck", "DieselTruck" -> new DieselTruck();
            case "ET", "Electric Truck", "electric truck", "ElectricTruck" -> new ElectricTruck();
            case "EC", "Electric Car", "electric car", "ElectricCar" -> new ElectricCar();
            case "GC", "Gasoline Car", "gasoline car", "GasolineCar" -> new GasolineCar();
            default -> null;
        };

        // If null return
        if (dummyVehicle == null) return null;

        // Loop through
        for (int i = 0; i < vehicleCount; i++) {
            if (vehicles[i] == null) continue;
            // Check if the vehicle contains the requested type
            if (vehicles[i].getClass() == dummyVehicle.getClass()){
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
     * Fetches array of specified type then calls this.showArray().
     * @param type Type of vehicle: Diesel Truck (DT), Electric Truck (ET), Electric Car (EC), Gas Car (GC).
     * @return String of array of vehicles with header.
     */
    public String showOfType(String type){
        if (vehicles == null || vehicles.length == 0) return "Fleet contains no vehicles.";
        Vehicle[] array = filterVehicles(type);
        return showArray(array, vehicleCount);
    }
    public String showNotLeased(){
        // Check not empty
        if (vehicles == null || vehicles.length == 0) return "Fleet contains no available vehicles.";
        Vehicle[] returnArray = new Vehicle[vehicleCount];
        int count = 0;

        // Add not leased vehicles
        for (Vehicle vehicle : vehicles) {
            if (vehicle == null) continue;
            if (vehicle.getIsLeased()) continue;
            returnArray[count] = vehicle;
            count++;
        }
        // Return string
        return showArray(returnArray, count);
    }

    @Override
    public String toString(){
        if (vehicleCount == 0) return """
                ____________________________________________
                There are no vehicles in your fleet yet.
                Add a vehicle, then come back and try again.
                --------------------------------------------
                """;
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

            returnString += String.format("%02d %s\n", i, vehicleArray[i]);
        }
        return returnString;
    }
    public DieselTruck getLargestTruck(){
        DieselTruck dummy = new DieselTruck();
        for (Vehicle vehicle : vehicles) {

            // Find nonnull Diesel Truck
            if (vehicle != null && vehicle.getClass() == dummy.getClass()) {

                // Downcast
                DieselTruck truck = (DieselTruck) vehicle;

                // Check if weight greater than previous pick
                if (truck.getWeightCapacity() > dummy.getWeightCapacity())
                    // if so make it the new target
                    dummy = truck;
            }
        }
        return dummy;
    }
    public static ElectricTruck[] copyVehicles(ElectricTruck[] array){
        ElectricTruck[] returnArray = new ElectricTruck[array.length];
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) continue;
            returnArray[i] = new ElectricTruck(array[i]);
        }
        return returnArray;
    }
}
