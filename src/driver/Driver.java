package driver;

import vehicle.DieselTruck;
import vehicle.ElectricCar;
import vehicle.Fleet;
import vehicle.GasolineCar;

import java.util.Random;

public class Driver {
    public static void main(String[] args) {
        Fleet fleet = new Fleet(); // Assuming you have a Fleet class
        Random rand = new Random();

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

    }
}
