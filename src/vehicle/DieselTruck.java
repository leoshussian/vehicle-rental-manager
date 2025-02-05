package vehicle;

public class DieselTruck extends Truck {
    /** Last used plate integer **/
    private static int lastPlate = 1001;
    private static final String PLATE_PREFIX = "DT";
    private double fuelCapacity;

    // CONSTRUCTORS
    public DieselTruck() {
        super();
        this.plateNumber = generatePlate();
        this.fuelCapacity = 0;
    }
    public DieselTruck(String make, String model, int year, double weightCapacity, double fuelCapacity){
        super(make, model, year, weightCapacity);
        this.plateNumber = generatePlate();
        this.fuelCapacity = fuelCapacity;
    }
    public DieselTruck(DieselTruck other){
        super(other);
        this.plateNumber = generatePlate();
        this.fuelCapacity = other.fuelCapacity;
    }

    // SETTER
    public void setFuelCapacity(double fuelCapacity){
        this.fuelCapacity = fuelCapacity;
    }
    // GETTER
    public double getFuelCapacity(){
        return fuelCapacity;
    }

    // MISC
    @Override
    public DieselTruck clone(){
        return new DieselTruck(this);
    }
    @Override
    public String toString(){
        return super.toString() + " Fuel Capacity: " + fuelCapacity + "litres";
    }
    public boolean equals(DieselTruck other){
        return super.equals(other) && fuelCapacity == other.fuelCapacity;
    }

    /** Generates the next plate number **/
    private static String generatePlate() {
        return PLATE_PREFIX + lastPlate++;
    }
}
