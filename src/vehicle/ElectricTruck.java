package vehicle;

public class ElectricTruck extends Truck {
    private static int lastPlate = 1001;
    private static final String PLATE_PREFIX = "ET";
    private double range;

    // CONSTRUCTORS
    public ElectricTruck() {
        super();
        this.plateNumber = generatePlate();
        this.range = 0;
    }
    public ElectricTruck(String make, String model, int year, double weightCapacity, double range) {
        super(make, model, year, weightCapacity);
        this.plateNumber = generatePlate();
        this.range = range;
    }
    public ElectricTruck(ElectricTruck other) {
        super(other);
        this.plateNumber = generatePlate();
        this.range = other.range;
    }

    // SET/GET
    public void setRange(double range) {
        this.range = range;
    }
    public double getRange() {
        return range;
    }

    // MISC.
    @Override
    public ElectricTruck clone() {
        return new ElectricTruck(this);
    }
    @Override
    public String toString() {
        return super.toString() + " Battery Range: " + range + "km";
    }
    public boolean equals(ElectricTruck other) {
        return super.equals(other) && this.range == other.range;
    }

    /** Generates the next plate number **/
    private static String generatePlate() {
        return PLATE_PREFIX + lastPlate++;
    }
}
