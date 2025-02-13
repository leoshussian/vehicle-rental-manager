package com.vrm.driver;

import com.vrm.client.Client;
import com.vrm.vehicle.Vehicle;

import static com.vrm.driver.Driver.*;

/**
 * Handles user interactions with LeaseManager and clients
 */
public class LeaseDriver {

    public static void getMenu(){

        if (clientManager.getClientCount() == 0 || fleet.getVehicleCount() == 0){
            System.out.println("This location currently has no clients or vehicles. \nPlease add clients and vehicles attempting a lease.");
            return;
        }
        final int MAX_MAIN_OPTIONS = 5;
        int choice = 0;

        // Loop menu until user wants to return to main
        while (choice != MAX_MAIN_OPTIONS) {
            final String MAIN_MENU = String.format("""
                    ------------------------------------ ░       ░░
                                                         ▒  ▒▒▒▒  ▒
                                                         ▓       ▓▓
                     RENTAL OPERATIONS                   █  ███  ██
                     Number of rentals: %02d               █  ████  █
                    ________________________________________________
                    1 | Rent vehicle to client
                    2 | Return vehicle and terminate contract
                    3 | Show all contracts
                    4 | Find client contracts
                    5 | Back
                  """, leaseManager.getLeasedVehiclesCount());

            choice = Selector.pickItem(MAIN_MENU, 1, MAX_MAIN_OPTIONS + 1);
            boolean isValid = switch (choice) {
                case 1 -> leaseVehicle();
                case 2 -> endLease();
                case 3 -> showAll();
                case 4 -> showByClient();
                case 5 -> true;
                default -> {
                    System.err.println("Unexpected choice in LeaseDriver main menu");
                    yield false;
                }
            };
        }
    }
    public static boolean leaseVehicle(){
        System.out.println("RENT VEHICLE");

        if (fleet.getVehicleCount() - leaseManager.getLeasedVehiclesCount() == 0){
            System.out.println("There are no vehicles available to rent.");
            return false;
        }
        // Pick customer
        System.out.println("Which client would you like to rent a vehicle to?");
        int index = Selector.pickItem(clientManager.toString(), 0, clientManager.getClientCount());
        Client client = clientManager.retrieveClient(index);

        // Pick vehicle
        System.out.println("Which vehicle would you like to rent to " + client + "?");

        index = Selector.pickItem(fleet.toString(), 0, fleet.getVehicleCount());
        Vehicle vehicle = fleet.retrieveVehicle(index);

        // Confirm
        System.out.println("Are you sure you want to rent the " + vehicle.getYear() + " " + vehicle.getMake() + " " + vehicle.getModel() + " to " + client + "?");
        if (!Selector.pressToConfirm()) {
            System.out.println("Got it. This lease was cancelled.");
            return true;
        }

        // Create lease
        int output = leaseManager.createLease(client, vehicle.getPlateNumber());
        switch (output){
            case 1 -> {
                System.err.println("""
                    ERROR 1: NULL VEHICLE ERROR
                        Sorry, it appears that this vehicle no longer exists.
                        Please try again later, or with another vehicle.""");
                return false;
            }
            case 2 -> {
                System.err.println("""
                    ERROR 2: VEHICLE ALREADY LEASED
                        Sorry, this vehicle is currently leased.
                        Please terminate that lease, then try again.""");
                return false;
            }
            case 3 -> {
                System.out.println("""
                    ERROR 3: LEASE ARRAY FULL
                        Sorry, there is no more space for leases.
                        Please contact customer support.""");
                return false;
            }
            case 0 -> {
                System.out.println("Lease successfully created.");
                System.out.println("Your lease ID is: " + leaseManager.getLastLeaseID());
                return true;
            }
            default -> {
                System.out.println("""
                    UNEXPECTED ERROR
                        An unexpected error has occurred.
                        Please contact customer support for more information.""");
                return false;
            }
        }
    }
    public static boolean endLease(){
        // Check that there is a lease to terminate
        if (Driver.leaseManager.getLeasedVehiclesCount() <= 0) {
            System.err.println("""
                    ERROR 1
                        There are currently no leased vehicles to return.
                        Please try again later.""");
            return false;
        }
        // Get lease
        System.out.println("Which lease would you like to terminate?");
        int leaseID = Selector.pickLease();

        // Confirm
        System.out.println("Are you sure you want to terminate this lease?");
        System.out.println(Driver.leaseManager.retrieveLease(leaseID));

        if (!Selector.pressToConfirm()) {
            System.out.println("Got it. This lease was not terminated.");
            return true;
        }

        int output = Driver.leaseManager.removeLease(leaseID);
        switch (output){
            case 0 -> {
                System.out.println("The lease was terminated successfully. The car was returned to the fleet.");
                return true;
            }
            case 1 -> {
                System.err.println("""
                    ERROR 1
                        There are currently no leased vehicles to return.
                        Please try again later.""");
                return false;
            }
            case 2 -> {
                System.err.println("""
                    ERROR 2
                        There are no leased vehicles with this ID.
                        Please try again later.""");
                return false;
            }
            default -> {
                System.err.println("""
                        UNEXPECTED ERROR
                            An unexpected error has occurred.
                            Please contact customer support for more information.""");
                return false;
            }
        }
    }

    public static boolean showAll(){
        System.out.println("ALL LEASED VEHICLES");
        System.out.println(leaseManager);
        System.out.println("Count: " + leaseManager.getLeasedVehiclesCount());
        Selector.pressToContinue();
        return true;
    }
    public static boolean showByClient(){
        System.out.println("Which client would you like to display?");

        int index = Selector.pickItem(clientManager.toString(), 0, clientManager.getClientCount());
        String clientName = clientManager.retrieveClient(index).getName();

        System.out.println("ALL VEHICLES LEASED BY " + clientName.toUpperCase());
        System.out.println(leaseManager.showClientLeases(clientName));
        Selector.pressToContinue();
        return true;
    }
}
