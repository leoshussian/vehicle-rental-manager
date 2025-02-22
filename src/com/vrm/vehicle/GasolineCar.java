package com.vrm.vehicle;

public class GasolineCar extends Car {
    /** Last used plate integer **/
    private static int lastPlate = 1001;
    private static final String PLATE_PREFIX = "GC";

    // CONSTRUCTORS
    public GasolineCar() {
        super();
        this.plateNumber = generatePlate();
    }
    public GasolineCar(String make, String model, int year, int passengerCapacity){
        super(make, model, year, passengerCapacity);
        this.plateNumber = generatePlate();
    }
    public GasolineCar (GasolineCar other){
        super(other);
        this.plateNumber = generatePlate();
    }
    public GasolineCar (String plateNumber, GasolineCar other){
        super(other);
        this.plateNumber = plateNumber;
    }

    /** Generates the next plate number **/
    private static String generatePlate() {
        return PLATE_PREFIX + lastPlate++;
    }

    @Override
    public String getHeader(){
        return "GASOLINE CARS:\nid " + super.getHeader();
    }
    @Override
    protected GasolineCar clone() {
        return new GasolineCar(this.plateNumber, this);
    }
}
