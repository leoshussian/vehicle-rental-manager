// ------------------------------------------------------------------------------------
// Assignment 1
// Leo Hussain
// ------------------------------------------------------------------------------------

/* VEHICLE RENTAL MANAGER 2025 (V.R.M. for short) is a rental manager system developed in-house at RoyalRentals.
 *
 * VRM allows RoyalRentals to register vehicles to the fleet and clients, and allows leases (rental contracts) to be created.
 * There are four categories of vehicles, electric trucks and cars, as well as traditional gasoline cars and diesel trucks.
 * VRM handles plate registration and lease IDs automatically.
 *
 * Full feature list:
 *
 *  VEHICLE MANAGEMENT:
 *      - Add and remove vehicles to fleet.
 *      - Edit any vehicle information.
 *      - Duplicate electric truck array
 *      - Retrieve the largest diesel truck.
 *  VRM prevents the user from compromising the integrity of fleet data by limiting the deletion of rented vehicles.
 *  The user may still edit any vehicle.
 *
 *  CLIENT MANAGEMENT
 *      - Add new clients
 *      - Remove clients that are not leasing vehicles
 *      - Update client names
 *
 *  LEASE/RENT MANAGEMENT
 *      - Create and terminate rental contracts
 *      - Terminate contracts
 */
package com.vrm.driver;

import com.vrm.client.*;
import com.vrm.vehicle.*;

import java.util.Scanner;

/**
 * Initializes manager classes and scanner, calls Menu and TestScenario methods.
 */
public class Driver {

    // 0. Initialize all
    static Scanner key = new Scanner(System.in);
    static Fleet fleet = new Fleet(50);
    static ClientManager clientManager = new ClientManager();
    static LeaseManager leaseManager = new LeaseManager(fleet);

    public static void main(String[] args) {

        // 1. Greet user
        try {
            Menu.displaySplashScreen();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 2. Check if user wants test menu
        if (Menu.bootUp()) {
            TestScenario.testScenario();
            Menu.exit(1);
        }

        // 3. Direct to menus
        while (true) {
            Menu.getMainMenu();
        }
    }
}
