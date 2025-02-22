package com.vrm.vehicle;

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
    private ElectricTruck(String plateNumber, ElectricTruck other) {
        super(other);
        this.plateNumber = plateNumber;
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
    protected ElectricTruck clone() {
        return new ElectricTruck(this.plateNumber, this);
    }
    @Override
    public String getHeader(){
        return "ELECTRIC TRUCKS:\nid " + super.getHeader() + " RANGE     | TYPE           |";
    }
    @Override
    public String toString(){
        return super.toString() + String.format(" %06.2f km | Electric Truck |", range);
    }
    @Override
    public boolean equals(Object other){
        return super.equals(other) && this.range == ((ElectricTruck)other).range;
    }

    /** Generates the next plate number **/
    private static String generatePlate() {
        return PLATE_PREFIX + lastPlate++;
    }


}
