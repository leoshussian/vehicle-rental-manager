package vehicle;

abstract public class Truck extends Vehicle {
    private double weightCapacity;

    // CONSTRUCTORS
    public Truck() {
        super();
        this.weightCapacity = 0;
    }
    public Truck(String make, String model, int year, double weightCapacity) {
        super(make, model, year);
        this.weightCapacity = weightCapacity;
    }
    public Truck(Truck other){
        super(other);
        this.weightCapacity = other.weightCapacity;
    }

    // GETTERS
    public double getWeightCapacity(){
        return this.weightCapacity;
    }

    // SETTERS
    public void setWeightCapacity(double weightCapacity){
        this.weightCapacity = weightCapacity;
    }
    // User should not be able to change plate number

    // MISC.
    @Override
    public String toString(){
        return super.toString() + " Max Weight: " + weightCapacity + "kg";
    }
    public boolean equals(Truck other){
        return super.equals(other) && weightCapacity == other.weightCapacity;
    }

}
