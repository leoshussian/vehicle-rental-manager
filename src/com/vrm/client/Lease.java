package com.vrm.client;

import com.vrm.vehicle.Vehicle;

public class Lease {
    private static int nextID = 0;
    private final int ID;
    private final Client client;
    private final String plateNumber;

    Lease(Client client, String plateNumber) {
        this.client = client;
        this.plateNumber = plateNumber;
        this.ID = nextID++;
    }
    Lease(Lease lease){
        this.ID = nextID++;
        this.plateNumber = lease.plateNumber;
        this.client = lease.client;
    }

    public int getID(){
        return ID;
    }
    public Client getClient(){
        return client;
    }
    public String getPlateNumber(){
        return plateNumber;
    }

    public String getHeader(){
        return """
                | ID | CLIENT NAME          |  PLATE ID  |""";
    }
    @Override
    public String toString(){
        return String.format("""
                | %2d | %-20s | %-10s |""", ID, client, plateNumber);
    }
}
