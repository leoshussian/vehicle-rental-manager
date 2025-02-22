package com.vrm.client;

import com.vrm.vehicle.Fleet;
import com.vrm.vehicle.Vehicle;

/**
 * Responsible for managing vehicle leases within a fleet.
 * Allows for creation, retrieval, and removal of leases, as well as tracking leases by client.
 */
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

    /**
     * Creates a new lease for a vehicle and associates it with a client.
     * Updates the vehicle's leased status.
     *
     * @param client The client leasing the vehicle.
     * @param plateNumber The license plate number of the vehicle to lease.
     * @return 0 if successful, 1 if vehicle is null, 2 if vehicle is already leased,
     * 3 if lease array is full, 4 if vehicle lease toggle fails.
     */
    public int createLease(Client client, String plateNumber){
        // Get vehicle
        Vehicle vehicle = fleet.retrieveVehicle(plateNumber);

        // Check nonnull
        if (vehicle == null) return 1;

        // Check vehicle is not leased already
        if (vehicle.getIsLeased()) return 2;

        // Check if there is space (there should be, this is a sanity check)
        if (leasedVehiclesCount + 1 >= leasedVehicles.length) return 3;

        // Finally add the lease
        if (!fleet.toggleLeased(plateNumber)) return 4; // returns error if the toggle doesn't work
        leasedVehicles[leasedVehiclesCount++] = new Lease(client, plateNumber);
        return 0;
    }

    /**
     * Returns the Lease ID of the most recently created lease.
     *
     * @return The ID of the last lease.
     */
    public int getLastLeaseID(){
        return leasedVehicles[leasedVehiclesCount - 1].getID();
    }
    /**
     * Removes a lease by its ID.
     * Terminated leases set vehicle to not leased.
     *
     * @param iD The ID of the lease to remove.
     * @return 0 if successful, 1 if no leases exist, 2 if lease ID not found.
     */
    public int removeLease(int iD){
        if (leasedVehiclesCount == 0) return 1;

        // Get vehicle with requested iD
        int i = 0;
        for (; i < leasedVehiclesCount; i++) {
            // If ID matches
            if (leasedVehicles[i] != null && leasedVehicles[i].getID() == iD) {
                // Toggle isLeased
                String plateNumber = leasedVehicles[i].getPlateNumber();
                fleet.toggleLeased(plateNumber);

                // Delete
                leasedVehicles[i] = leasedVehicles[leasedVehiclesCount - 1];
                leasedVehicles[leasedVehiclesCount - 1] = null;
                leasedVehiclesCount--;
                return 0;
            }
        }
        return 2;
    }

    /**
     * Retrieves a lease by its ID.
     *
     * @param iD The ID of the lease to retrieve.
     * @return The Lease object if found, otherwise null.
     */
    public Lease retrieveLease(int iD){
        if (leasedVehiclesCount == 0) return null;
        for (Lease lease : leasedVehicles) {
            if (lease == null) continue;
            if (lease.getID() == iD) {
                return lease;
            }
        }
        return null;
    }

    /**
     * @return Array of lease references with matching client. Leases
     */
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

    /**
     * Returns the number of leases associated with a specific client.
     *
     * @param clientName The name of the client.
     * @return The number of leases the client has.
     */
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

    /**
     * Returns a formatted string showing all leases associated with a specific client.
     *
     * @param clientName The name of the client.
     * @return A formatted string of lease information or a message if none exist.
     */
    public String showClientLeases(String clientName) {
        if (leasedVehiclesCount == 0) return "This customer has no leases yet.";
        Lease[] clientLeases = getClientLeases(clientName);
        String returnString = " ID | Vehicle\n";
        for (Lease lease : clientLeases) {
            // Check nonnull
            if (lease == null) continue;

            // Fetch vehicle
            String plateID = lease.getPlateNumber();
            Vehicle vehicle = fleet.retrieveVehicle(plateID);

            // Display name
            String name = vehicle.getMake() + " " + vehicle.getModel() + " " + vehicle.getYear();

            returnString += String.format(" %2d | %s\n", lease.getID(), name);
        }
        return returnString;
    }
    @Override
    public String toString() {
        if (leasedVehicles == null || leasedVehicles[0] == null) return "There are no leased vehicles to display.";
        String returnString = leasedVehicles[0].getHeader() + " Vehicle\n";
        for (Lease lease : leasedVehicles) {
            // Skip null
            if (lease == null) continue;

            // Fetch vehicle
            String plateID = lease.getPlateNumber();
            Vehicle vehicle = fleet.retrieveVehicle(plateID);

            // Display name
            String name = vehicle.getYear() + " " + vehicle.getMake() + " " + vehicle.getModel();

            returnString += lease + " " + name + "\n";
        }
        return returnString;
    }
}
