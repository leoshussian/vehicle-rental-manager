package com.vrm.vehicle;
import com.vrm.driver.Colour;

abstract public class Vehicle {
    protected String plateNumber; // TODO Sanity check this!
    private String make;
    private String model;
    private int year;
    private boolean isLeased;

    // CONSTRUCTORS
    /** Default vehicle constructor **/
    public Vehicle(){
        this.plateNumber = "UNASSIGNED";
        this.make = "Unknown";
        this.model = "Unknown";
        this.year = 0;
        this.isLeased = false;
    }
    /** Parameterized constructor. **/
    public Vehicle(String make, String model, int year){
        this.plateNumber = "UNASSIGNED";
        this.make = make;
        this.model = model;
        this.year = year;
        this.isLeased = false;
    }
    /** Copy constructor **/
    public Vehicle(Vehicle other){
        this(other.make, other.model, other.year);
        isLeased = other.isLeased;
    }

    // GETTERS
    public String getPlateNumber() {
        return plateNumber;
    }
    public String getMake() {
        return make;
    }
    public String getModel() {
        return model;
    }
    public int getYear() {
        return year;
    }
    public boolean getIsLeased() {
        return isLeased;
    }

    // SETTERS
    public void setMake(String make) {
        this.make = make;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public void setIsLeased(boolean isLeased) {
        this.isLeased = isLeased;
    }

    // MISC
    public String getHeader(){
        return "| ON HAND | PLATE ID | MAKE          | MODEL       | YEAR |";
    }
    @Override
    public String toString(){
        String leasedFlag = !this.isLeased ? Colour.GREEN_BACKGROUND + " YES     " + Colour.RESET: Colour.RED_BACKGROUND + " NO      " + Colour.RESET;
        return String.format("|%s| %-8s | %-13s | %-11s | %4d |", leasedFlag, plateNumber, make, model, year);
    }
    public boolean equals(Object other){
        if (other == null) return false;

        if (other.getClass() != this.getClass()) return false;

        Vehicle otherVehicle = (Vehicle) other;
        return this.make.equals(otherVehicle.make) && this.model.equals(otherVehicle.model) && this.year == otherVehicle.year && this.isLeased == otherVehicle.isLeased;
    }
    @Override
    protected abstract Vehicle clone();
}
