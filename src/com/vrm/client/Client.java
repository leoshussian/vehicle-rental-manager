package com.vrm.client;
import com.vrm.vehicle.Fleet;
import com.vrm.vehicle.Vehicle;

public class Client {
    private String name;

    // CONSTRUCTORS
    public Client() {
        this.name = "Unknown";
    }
    public Client(String name) {
        this.name = name;
    }
    public Client(Client client){
        this.name = client.name;
    }

    // GET + SET
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Misc.
    @Override
    public boolean equals(Object obj){
        if (obj instanceof Client other) {
            return this.name.equals(other.name);
        }
        else return false;
    }
    @Override
    public String toString() {
        return this.name.toUpperCase();
    }
}
