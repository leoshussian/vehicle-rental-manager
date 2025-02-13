package com.vrm.driver;

import com.vrm.client.Client;
import com.vrm.client.ClientManager;
import com.vrm.client.LeaseManager;
import com.vrm.vehicle.*;

import java.util.Scanner;

public class TestScenario {
    public static void testScenario() {
        // 0. Greet and initialize
        System.out.println("""
                =============================
                Proceeding with Test Scenario
                =============================
                """);

        Fleet fleet = new Fleet(50);

        ClientManager clientManager = new ClientManager();
        LeaseManager leaseManager = new LeaseManager(fleet);

        // 1. Creating 3 test clients
        System.out.println("1 | Creating three test clients");
        Client client1 = new Client("Tatler Swist");
        Client client2 = new Client("Sabeena Car Renter");
        Client client3 = new Client("Charlene XDX");

        // Add them to ClientManager
        for (Client client : new Client[]{client1, client2, client3}){
            if (!clientManager.addClient(client)) System.out.println("An error has occurred!");
        }

        // 2. Create 3 x 4 vehicles
        System.out.println("2 | Initializing vehicles");
        Vehicle[] vehicles = new Vehicle[4*3];

        vehicles[0] = new DieselTruck("Ford", "F-150", 2003, 1800, 400);
        vehicles[1] = new DieselTruck("Toyota", "Tacoma", 2008, 800.34, 300.23);
        vehicles[2] = new DieselTruck();

        vehicles[3] = new ElectricTruck("Tesla", "Cybertruck", 2025, 200, 500.50);
        vehicles[4] = new ElectricTruck();
        vehicles[5] = new ElectricTruck((ElectricTruck) vehicles[3]);

        vehicles[6] = new GasolineCar("Toyota", "Camry", 1998, 5);
        vehicles[7] = new GasolineCar();
        vehicles[8] = new GasolineCar((GasolineCar) vehicles[6]);

        vehicles[9] = new ElectricCar("Chevrolet", "Equinox EV", 2020, 6, 400.5);
        vehicles[10] = new ElectricCar();
        vehicles[11] = new ElectricCar((ElectricCar) vehicles[9]);

        // Add array to fleet
        if (!fleet.appendArray(vehicles)){
            System.out.println("An error has occurred!");
        }
        else System.out.println("== Successfully added clients and vehicles ==\n");

        // Display info
        System.out.println("CLIENTS:\n" + clientManager);
        System.out.println(fleet);

        // 3. Test equals method
        System.out.println("\n3 | Testing equals methods");

        System.out.println("1: Two objects from different classes (expected output: false)");
        System.out.println(vehicles[0].equals(vehicles[11]));

        System.out.println("2: Two objects of the same class with different attribute values (expected output: false)");
        System.out.println(vehicles[0].equals(vehicles[1]));

        System.out.println("3: Two objects of the same class with identical attribute values (expected output: true)");
        System.out.println(vehicles[9].equals(vehicles[11]));

        // 4. Creating arrays
        System.out.println("\n4 | Creating Arrays");
        // note that the vehicle array was already created.
        DieselTruck[] dieselTrucks = new DieselTruck[5];
        dieselTrucks[0] = (DieselTruck) vehicles[0];
        dieselTrucks[1] = (DieselTruck) vehicles[1];
        dieselTrucks[2] = (DieselTruck) vehicles[2];

        ElectricTruck[] electricTrucks = new ElectricTruck[5];
        electricTrucks[0] = (ElectricTruck) vehicles[3];
        electricTrucks[1] = (ElectricTruck) vehicles[4];
        electricTrucks[2] = (ElectricTruck) vehicles[5];

        GasolineCar[] gasolineCars = new GasolineCar[5];
        gasolineCars[0] = (GasolineCar) vehicles[6];
        gasolineCars[1] = (GasolineCar) vehicles[7];
        gasolineCars[2] = (GasolineCar) vehicles[8];

        ElectricCar[] electricCars = new ElectricCar[5];
        electricCars[0] = (ElectricCar) vehicles[9];
        electricCars[1] = (ElectricCar) vehicles[10];
        electricCars[2] = (ElectricCar) vehicles[11];

        System.out.println("== Arrays successfully created ==");

        System.out.println("\n5 | Using getLargestTruck()");
        // 5. getLargestTruck uses the fleet so we don't have to pass anything
        System.out.println(fleet.getLargestTruck());

        System.out.println("\n6 | Using copyVehicles()");
        ElectricTruck[] copy = Fleet.copyVehicles(electricTrucks);
        System.out.println("Array copied successfully\n");

        System.out.println("COPIED ARRAY:");
        System.out.println(Fleet.showArray(copy, 5));

        System.out.println("\nORIGINAL ARRAY:");
        System.out.println(Fleet.showArray(electricTrucks, 5));
    }
}
