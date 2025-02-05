package vehicle;

/**
 * Stores and manages all company cars.
 * Handles vehicle creation and update. Stores fleet in arrays by type of vehicle.
 */
public class Fleet {
    private final int CAPACITY;
    private static final int TYPES = 4;

    private DieselTruck[] dieselTrucks;
    private int dieselTruckCount;

    private ElectricTruck[] electricTrucks;
    private int electricTruckCount;

    private GasolineCar[] gasolineCars;
    private int gasolineCarCount;

    private ElectricCar[] electricCars;
    private int electricCarCount;

    // CONSTRUCTORS
    public Fleet() {
        this.CAPACITY = 10;
        this.dieselTrucks = new DieselTruck[CAPACITY];
        this.dieselTruckCount = 0;
        this.electricTrucks = new ElectricTruck[CAPACITY];
        this.electricTruckCount = 0;
        this.gasolineCars = new GasolineCar[CAPACITY];
        this.gasolineCarCount = 0;
        this.electricCars = new ElectricCar[CAPACITY];
        this.electricCarCount = 0;
    }
    public Fleet(int maxCapacity){
        this.CAPACITY = maxCapacity;
        this.dieselTrucks = new DieselTruck[CAPACITY];
        this.dieselTruckCount = 0;
        this.electricTrucks = new ElectricTruck[CAPACITY];
        this.electricTruckCount = 0;
        this.gasolineCars = new GasolineCar[CAPACITY];
        this.gasolineCarCount = 0;
        this.electricCars = new ElectricCar[CAPACITY];
        this.electricCarCount = 0;
    }

    /**
     * Adds vehicle to the appropriate vehicle array.
     * @return false if array is full or vehicle is invalid.
     */
    // TODO decide if I want to overload the method instead
    public boolean storeVehicle(Vehicle vehicle){
        switch (vehicle) {
            case DieselTruck dieselTruck -> {
                if (dieselTruckCount < CAPACITY) {
                    dieselTrucks[dieselTruckCount++] = dieselTruck;
                    return true;
                }
                return false;
            }
            case ElectricTruck electricTruck -> {
                if (electricTruckCount < CAPACITY) {
                    electricTrucks[electricTruckCount++] = electricTruck;
                    return true;
                }
                return false;
            }
            case GasolineCar gasolineCar -> {
                if (gasolineCarCount < CAPACITY) {
                    gasolineCars[gasolineCarCount++] = gasolineCar;
                    return true;
                }
                return false;
            }
            case ElectricCar electricCar -> {
                if (electricCarCount < CAPACITY) {
                    electricCars[electricCarCount++] = electricCar;
                    return true;
                }
                return false;
            }
            case null, default -> {
                return false;
            }
        }
    }

    /**
     * Remove specified vehicle of specified type from array by index
     * @param type (0 to 3) dieselTruck, electricTruck, gasolineCars, electricCars.
     * @param index valid array index
     * @return false if type or index is invalid
     */
    public boolean removeVehicle(int type, int index){
        // Check type is valid
        if (type >= TYPES || type < 0) return false;

        // Get array and count copies
        Vehicle[] targetArray = this.getArray(type);
        int targetCount = this.getArrayCount(type);

        // Check index is valid
        if (targetCount - 1 < index || index < 0) return false;

        // Remove vehicle
        targetArray[index] = targetArray[targetCount - 1];
        targetArray[targetCount - 1] = null;
        // Decrement
        targetCount--;

        // Push changes
        this.setArray(type, targetArray);
        this.setArrayCount(type, targetCount);
        return true;
    }

    /**
     * Retrieve copy of vehicle at specified index and type.
     * @param type (0 to 3) dieselTruck, electricTruck, gasolineCars, electricCars.
     * @param index valid array index
     * @return copy of vehicle
     */
    public Vehicle retrieveVehicle(int type, int index){
        // Check type is valid
        if (type >= TYPES || type < 0) return null;

        // Get array and count
        Vehicle[] targetArray = this.getArray(type);
        int targetCount = this.getArrayCount(type);

        // Check index is valid
        if (targetCount - 1 < index || index < 0) return null;

        // Return vehicle copy
        return targetArray[index].clone();
    }

    // GETTERS
    /**
     * Pass vehicle type array copy.
     * @param type (0 to 3) dieselTruck, electricTruck, gasolineCars, electricCars.
     * @return vehicle array copy.
     */
    public Vehicle[] getList(int type) {
        // Get correct array
        Vehicle[] targetArray = switch (type) {
            case 0 -> dieselTrucks;
            case 1 -> electricTrucks;
            case 2 -> gasolineCars;
            case 3 -> electricCars;
            default -> null;
        };
        // Check nonnull
        if (targetArray == null) return null;
        // Manual copy
        Vehicle[] returnArray = new Vehicle[CAPACITY];
        for (int i = 0; i < targetArray.length; i++) {
            if (targetArray[i] != null)
                returnArray[i] = targetArray[i];
        }
        return returnArray;
    }

    /**
     * @param type (0 to 3) dieselTruck, electricTruck, gasolineCars, electricCars.
     */
    public int getArrayCount(int type){
        return switch (type) {
            case 0 -> dieselTruckCount;
            case 1 -> electricTruckCount;
            case 2 -> gasolineCarCount;
            case 3 -> electricCarCount;
            default -> -1;
        };
    }

    // SETTERS
    /**
     * @param type (0 to 3) dieselTruck, electricTruck, gasolineCars, electricCars.
     */
    private void setArrayCount(int type, int count){
        switch (type) {
            case 0 -> dieselTruckCount = count;
            case 1 -> electricTruckCount = count;
            case 2 -> gasolineCarCount = count;
            case 3 -> electricCarCount = count;
        }
    }
    /**
     * @param type (0 to 3) dieselTruck, electricTruck, gasolineCars, electricCars.
     */
    private void setArray(int type, Vehicle[] array){
        switch (type) {
            case 0 -> dieselTrucks = (DieselTruck[]) array;
            case 1 -> electricTrucks = (ElectricTruck[]) array;
            case 2 -> gasolineCars = (GasolineCar[]) array;
            case 3 -> electricCars = (ElectricCar[]) array;
        }
    }
}
