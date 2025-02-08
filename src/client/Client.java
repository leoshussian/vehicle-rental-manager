package client;
import vehicle.Vehicle;

public class Client {
    final int ID;
    private static int nextId = 0;
    private String name;
    private static final int MAX_LEASES = 10;
    /**
     * All vehicles leased by client
     */
    private Vehicle[] leases;
    private int leaseCount = 0;

    // CONSTRUCTORS
    public Client(String name, Vehicle[] leases) {
        this.ID = nextId++;
        this.name = name;
        this.leases = leases;
    }
    public Client() {
        this.ID = nextId++;
        this.name = "Unknown";
        this.leases = new Vehicle[MAX_LEASES];
    }
    public Client(String name) {
        this.ID = nextId++;
        this.name = name;
        this.leases = new Vehicle[MAX_LEASES];
    }
    public Client(Client client){
        this.ID = nextId++;
        this.name = client.name;
        this.leases = client.getLeases(); // to get copy!
        this.leaseCount = client.leaseCount;
    }

    // GET + SET
    public int getID() {
        return ID;
    }
    public String getName() {
        return name;
    }
    public Vehicle[] getLeases() { // BY COPY
        Vehicle[] temp = new Vehicle[leases.length];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = leases[i];
        }
        return temp;
    }
    public int getLeaseCount() {
        return leaseCount;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Lease Operations

    /**
     * Rent a vehicle to a customer
     */
    public boolean addLease(Vehicle lease) {
        // Check capacity
        if (leaseCount >= MAX_LEASES) return false;

        // Check nonnull
        if (lease == null) return false;

        // Add vehicle
        leases[leaseCount] = lease.clone();
        leaseCount++;
        return true;
    }
    public boolean removeLease(Vehicle lease) {
        if (lease == null) return false;
        for (int i = 0; i < leaseCount; i++) {
            if (leases[i].equals(lease)) {
                leases[i] = leases[--leaseCount];
            }
        }
        return true;
    }

}
