package com.vrm.driver;

import com.vrm.client.Client;
import com.vrm.client.LeaseManager;
import com.vrm.vehicle.Vehicle;

/**
 * Handles user interactions with LeaseManager and clients
 */
public class LeaseController {

    public static boolean leaseVehicle(){
        System.out.println("LEASE VEHICLE");

        // Pick customer
        System.out.println("Which client would you like to lease a vehicle to?");
        int index = MenuHelper.pickClient();
        Client client = Driver.clientManager.retrieveClient(index);

        // Pick vehicle
        System.out.println("Which vehicle would you like to lease to " + client + "?");
        index = MenuHelper.pickVehicleIndex();
        Vehicle vehicle = Driver.fleet.retrieveVehicle(index);

        // Confirm
        System.out.println("Are you sure you want to lease the " + vehicle.getYear() + " " + vehicle.getMake() + " " + vehicle.getModel() + " to " + client + "?");
        if (!MenuHelper.pressToConfirm()) {
            System.out.println("Got it. This lease was cancelled.");
            return true;
        }

        // Create lease
        int output = Driver.leaseManager.createLease(client, vehicle.getPlateNumber());
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
                        Please terminate that lease then try again.""");
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
                System.out.println("Your lease ID is: " + Driver.leaseManager.getLastLeaseID());
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
        int leaseID = MenuHelper.pickLease();

        // Confirm
        System.out.println("Are you sure you want to terminate this lease?");
        System.out.println(Driver.leaseManager.retrieveLease(leaseID));

        if (!MenuHelper.pressToConfirm()) {
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

    public static void showAll(){
        System.out.println("ALL LEASED VEHICLES");
        System.out.println(Driver.leaseManager);
        System.out.println("Count: " + Driver.leaseManager.getLeasedVehiclesCount());
        MenuHelper.pressToContinue();
    }
    public static void showByClient(){
        System.out.println("Which client would you like to display?");

        int index = MenuHelper.pickClient();
        String clientName = Driver.clientManager.retrieveClient(index).getName();

        System.out.println("ALL VEHICLES LEASED BY " + clientName.toUpperCase());
        System.out.println(Driver.leaseManager.showClientLeases(clientName));
        MenuHelper.pressToContinue();
    }
}
