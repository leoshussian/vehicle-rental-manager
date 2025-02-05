package vehicle;

abstract public class Car extends Vehicle {
    private int passengerCapacity;

    // CONSTRUCTORS
    public Car() {
        super();
        this.passengerCapacity = 0;
    }
    public Car(String make, String model, int year, int passengerCapacity) {
        super(make, model, year);
        this.passengerCapacity = passengerCapacity;
    }
    public Car(Car other){
        super(other);
        this.passengerCapacity = other.passengerCapacity;
    }

    // SET + GET
    public int getPassengerCapacity() {
        return passengerCapacity;
    }
    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    // MISC
    @Override
    public String toString(){
        return super.toString() + " Max Capacity: " + passengerCapacity + " passengers";
    }
    public boolean equals(Car other){
        return super.equals(other) && passengerCapacity == other.passengerCapacity;
    }
}
