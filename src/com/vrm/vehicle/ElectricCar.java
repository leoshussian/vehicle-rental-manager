package com.vrm.vehicle;

public class ElectricCar extends Car {
    /** Last used plate integer **/
    private static int lastPlate = 1001;
    private static final String PLATE_PREFIX = "EC";
    private double range;

    public ElectricCar() {
        super();
        this.plateNumber = generatePlate();
        this.range = 0.0;
    }
    public ElectricCar(String make, String model, int year, int passengerCapacity, double range) {
        super(make, model, year, passengerCapacity);
        this.plateNumber = generatePlate();
        this.range = range;
    }
    public ElectricCar(ElectricCar other) {
        super(other);
        this.plateNumber = generatePlate();
        this.range = other.range;
    }
    private ElectricCar(String plateNumber, ElectricCar other){
        super(other);
        this.plateNumber = plateNumber;
        this.range = other.range;
    }

    // SET + GET
    public void setRange(double range){
        this.range = range;
    }
    public double getRange(){
        return range;
    }

    // MISC
    @Override
    protected ElectricCar clone(){
        return new ElectricCar(this.plateNumber, this);
    }
    @Override
    public String getHeader(){
        return "ELECTRIC CARS:\ni " + super.getHeader() + " RANGE     | TYPE         |";
    }
    @Override
    public String toString(){
        return super.toString() + String.format(" %4.2f km | Electric Car |", range);
    }
    public boolean equals(ElectricCar other){
        return super.equals(other) && range == other.range;
    }
    @Override
    public boolean equals(Object other){ // TODO Sanity check please
        return super.equals(other) && this.range == ((ElectricCar)other).range;
    }

    /** Generates the next plate number **/
    private static String generatePlate() {
        return PLATE_PREFIX + lastPlate++;
    }
}
