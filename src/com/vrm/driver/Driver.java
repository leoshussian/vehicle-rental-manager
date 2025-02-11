package com.vrm.driver;

import com.vrm.client.ClientManager;
import com.vrm.client.LeaseManager;
import com.vrm.vehicle.DieselTruck;
import com.vrm.vehicle.Fleet;

import java.util.Random;
import java.util.Scanner;

public class Driver {

    static Scanner key = new Scanner(System.in);
    static Fleet fleet = new Fleet(30);
    static ClientManager clientManager = new ClientManager();
    static LeaseManager leaseManager = new LeaseManager(fleet);

    public static void main(String[] args) {
        Random rand = new Random();
        System.out.println();
        System.out.println();
        String[] makes = {"Ford", "Chevy", "Ram", "Volvo", "Peterbilt"};
        String[] models = {"F-150", "Silverado", "1500", "VNL", "579"};

        for (int i = 0; i < 20; i++) {
            String make = makes[rand.nextInt(makes.length)];
            String model = models[rand.nextInt(models.length)];
            int year = rand.nextInt(24) + 2000; // Random year between 2000 and 2023
            double weightCapacity = rand.nextDouble() * 30000 + 5000; // Between 5000 and 35000
            double fuelCapacity = rand.nextDouble() * 200 + 50; // Between 50 and 250

            DieselTruck truck = new DieselTruck(make, model, year, weightCapacity, fuelCapacity);
            fleet.addVehicle(truck);
        }

        System.out.println("20 trucks added to the fleet.");
        FleetController.showOfType();
        FleetController.getLargestTruck();
        FleetController.copyElectricTrucks();
        ClientController.createClient();
        ClientController.createClient();
        ClientController.updateClient();
        ClientController.deleteClient();
        ClientController.createClient();

        LeaseController.showAll();
        LeaseController.leaseVehicle();
        LeaseController.leaseVehicle();
        LeaseController.showByClient();
        LeaseController.endLease();
        LeaseController.showAll();

        FleetController.createVehicle();
        FleetController.updateVehicle();
        FleetController.deleteVehicle();
    }
}
