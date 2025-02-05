package vehicle;

abstract public class Vehicle {
    protected String plateNumber; // TODO Sanity check this!
    private String make;
    private String model;
    private int year;

    // CONSTRUCTORS
    /** Default vehicle constructor **/
    public Vehicle(){
        this.plateNumber = "UNASSIGNED";
        this.make = "Unknown";
        this.model = "Unknown";
        this.year = 0;
    }
    /** Parameterized constructor. **/
    public Vehicle(String make, String model, int year){
        this.plateNumber = "UNASSIGNED";
        this.make = make;
        this.model = model;
        this.year = year;
    }
    /** Copy constructor **/
    public Vehicle(Vehicle other){
        this(other.make, other.model, other.year);
        this.plateNumber = "UNASSIGNED";
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

    // MISC
    @Override
    public String toString(){
        return String.format("%10s - Make: %10s, Model: %10s, Year: %5d", plateNumber, make, model, year);
    }
    public boolean equals(Vehicle other){ // TODO This will probably change soon...
        if (other == null) return false;
        else if (other.getClass() != this.getClass()) return false;
        else return this.make.equals(other.make) && this.model.equals(other.model) && this.year == other.year;
    }
    protected abstract Vehicle clone();
}
