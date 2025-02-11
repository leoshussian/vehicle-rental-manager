package com.vrm.client;

import com.vrm.vehicle.Fleet;
import com.vrm.vehicle.Vehicle;

public class LeaseManager {
    private Lease[] leasedVehicles;
    private int leasedVehiclesCount;
    private final Fleet fleet;

    public LeaseManager(Fleet fleet) {
        this.leasedVehicles = new Lease[fleet.getMAX_CAPACITY()];
        this.leasedVehiclesCount = 0;
        this.fleet = fleet;
    }
    public int getLeasedVehiclesCount(){
        return this.leasedVehiclesCount;
    }

    public boolean createLease(Client client, String plateNumber){
        // Get vehicle
        Vehicle vehicle = fleet.retrieveVehicle(plateNumber);

        // Check nonnull
        if (vehicle == null) return false;

        // Check vehicle is not leased already
        if (vehicle.getIsLeased()) return false;

        // Check if there is space (there should be, this is a sanity check)
        if (leasedVehiclesCount + 1 >= leasedVehicles.length) return false;

        // Finally add the lease
        vehicle.setIsLeased(true);
        leasedVehicles[leasedVehiclesCount++] = new Lease(client, plateNumber);
        return true;
    }
    public boolean removeLease(int iD){
        if (leasedVehiclesCount == 0) return false;

        // Get vehicle with requested iD
        for (Lease lease : leasedVehicles) {
            if (lease.getID() == iD) {
                leasedVehicles[leasedVehiclesCount] = leasedVehicles[leasedVehiclesCount-1];
                leasedVehicles[leasedVehiclesCount] = null;
                leasedVehiclesCount--;
                return true;
            }
        }
        return false;
    }
    public Lease retrieveLease(int iD){
        if (leasedVehiclesCount == 0) return null;
        for (Lease lease : leasedVehicles) {
            if (lease.getID() == iD) {
                return lease;
            }
        }
        return null;
    }
    public Vehicle retrieveVehicleFromLease(int iD){
        if (leasedVehiclesCount == 0) return null;
        for (Lease lease : leasedVehicles) {
            if (lease.getID() == iD) {
                String plateNumber = lease.getPlateNumber();
                return fleet.retrieveVehicle(plateNumber);
            }
        }
        // Else
        return null;
    }

    private Lease[] getClientLeases(String clientName) {
        Lease[] result = new Lease[leasedVehiclesCount];
        int count = 0;
        for (Lease lease : leasedVehicles) {
            if (lease == null) continue;
            if (lease.getClient().getName().equalsIgnoreCase(clientName)) {
                result[count] = lease;
                count++;
            }
        }
        return result;
    }
    public int clientNumberOfLeases(String clientName) {
        // Get client leases
        Lease[] clientLeases = getClientLeases(clientName);
        // Count nonnull
        int count = 0;
        for (Lease lease : clientLeases) {
            if (lease != null)
                count++;
        }
        return count;
    }
    public String showClientLeases(String clientName) {
        if (leasedVehiclesCount == 0) return "This customer has no leases yet.";
        Lease[] clientLeases = getClientLeases(clientName);
        String returnString = clientLeases[0].getHeader() + "| Vehicle "; //TODO Sanity check copy paste from toString()
        for (Lease lease : clientLeases) {
            // Fetch vehicle
            String plateID = lease.getPlateNumber();
            Vehicle vehicle = fleet.retrieveVehicle(plateID);

            // Display name
            String name = vehicle.getMake() + " " + vehicle.getModel() + " " + vehicle.getYear();
            int year = vehicle.getYear();

            returnString += lease + name + year + " |";
        }
        return returnString;
    }
    @Override
    public String toString() {
        String returnString = leasedVehicles[0].getHeader() + "| Vehicle ";
        for (Lease lease : leasedVehicles) {
            // Fetch vehicle
            String plateID = lease.getPlateNumber();
            Vehicle vehicle = fleet.retrieveVehicle(plateID);

            // Display name
            String name = vehicle.getMake() + " " + vehicle.getModel() + " " + vehicle.getYear();
            int year = vehicle.getYear();

            returnString += lease + name + year + " |";
        }
        return returnString;
    }
}
