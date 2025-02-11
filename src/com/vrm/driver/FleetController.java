package com.vrm.driver;

import com.vrm.vehicle.*;

/**
 * UI to deal with anything that has to do with the fleet.
 */
public class FleetController {

    /**
     * Initialize any vehicle and adds it to fleet.
     * @return true if successful.
     */
    public static boolean createVehicle() {
        // Pick type
        System.out.println("What type of vehicle would you like to create?");
        int choice = MenuHelper.pickType();

        // Prompt general vehicle info
        System.out.print("Make: ");
        String make = Driver.key.nextLine();
        System.out.print("Model: ");
        String model = Driver.key.nextLine();
        System.out.print("Year: ");
        int year = Driver.key.nextInt();
        Driver.key.nextLine(); // Flush!

        // Type specific info
        Vehicle vehicle = switch (choice) {
            case 1 -> { // Electric car
                System.out.print("Passenger capacity: ");
                int passengerCapacity = Driver.key.nextInt();

                System.out.print("Battery range: ");
                double range = Driver.key.nextDouble();

                yield new ElectricCar(make, model, year, passengerCapacity, range);
            }
            case 2 -> { // Gas car
                System.out.print("Passenger capacity: ");
                int passengerCapacity = Driver.key.nextInt();

                yield new GasolineCar(make, model, year, passengerCapacity);
            }
            case 3 -> { // Electric Truck
                System.out.print("Maximum weight: ");
                double maximumWeight = Driver.key.nextDouble();

                System.out.print("Battery range: ");
                double range = Driver.key.nextDouble();

                yield new ElectricTruck(make, model, year, maximumWeight, range);
            }
            case 4 -> {
                System.out.print("Maximum weight: ");
                double maximumWeight = Driver.key.nextDouble();

                System.out.print("Fuel capacity: ");
                double fuelCapacity = Driver.key.nextDouble();

                yield new DieselTruck(make, model, year, maximumWeight, fuelCapacity);
            }
            default -> null;
        };

        Driver.key.nextLine(); // Flush!

        // Check vehicle non null
        if (vehicle == null) return false;

        // Add vehicle to fleet
        if (Driver.fleet.addVehicle(vehicle)) return true;
        else {
            System.err.println("""
                    ERROR!
                        Sorry, there is no space for a new vehicle in your fleet.
                        Please remove a vehicle, then try again.""");
            return false;
        }
    }

    public static boolean deleteVehicle() {
        // Prompt for vehicle index
        System.out.println("Please pick a vehicle to delete: ");
        int index = MenuHelper.pickVehicleIndex();

        // Check vehicle is not leased
        Vehicle target = Driver.fleet.retrieveVehicle(index);
        if (target == null) return false;
        if (target.getIsLeased()) {
            System.out.println("""
                    ERROR!
                        Sorry, this vehicle is currently leased.
                        Please terminate the lease then try again.""");
        }

        // Prompt confirmation
        System.out.println("Are you sure you want to delete this vehicle?");
        if (MenuHelper.pressToConfirm()){
            System.out.println("This vehicle was successfully deleted.");
            return Driver.fleet.removeVehicle(index);
        }
        else return true;
    }

    /**
     * Fetch vehicle from fleet, display attributes, then update requested attribute.
     * Fetch vehicle from fleet, display attributes, then update requested attribute.
     * @return true if successful.
     */
    public static boolean updateVehicle() {
        // Get vehicle to update
        System.out.println("Please pick a vehicle to update:");
        int index = MenuHelper.pickVehicleIndex();
        Vehicle vehicle = Driver.fleet.retrieveVehicle(index);

        // Check nonnull
        if (vehicle == null) return false;

        // Display vehicle
        System.out.println(vehicle.getHeader() + "\n0 " + vehicle + "\n");

        boolean isValid = false;
        while (!isValid) {
            // General changes
            System.out.println("""
                UPDATE VEHICLE
                What would you like to update?
                
                1. | Make
                2. | Model
                3. | Year
                4. | More options""");

            int choice = Driver.key.nextInt();
            Driver.key.nextLine(); // Flush!

            isValid = switch (choice) {
                case 1 -> {
                    System.out.print("Please type a new make: ");
                    String make = Driver.key.nextLine();
                    vehicle.setMake(make);
                    yield true;
                }
                case 2 -> {
                    System.out.print("Please type a new model name: ");
                    String model = Driver.key.nextLine();
                    vehicle.setModel(model);
                    yield true;
                }
                case 3 -> {
                    System.out.print("Please type a new year: ");
                    int year = Driver.key.nextInt();
                    Driver.key.nextLine(); // Flush!

                    vehicle.setYear(year);
                    yield true;
                }
                case 4 -> switch (vehicle) {
                    case ElectricCar electricCar -> updateEC(electricCar);
                    case GasolineCar gasolineCar -> updateGC(gasolineCar);
                    case DieselTruck dieselTruck -> updateDT(dieselTruck);
                    case ElectricTruck electricTruck -> updateET(electricTruck);
                    default -> false;
                };
                default -> {
                    System.err.println("The option you selected is invalid!");
                    yield false;
                }
            }; // End of switch
        } // End of while

        // Show new vehicle
        System.out.println(vehicle.getHeader());
        System.out.println("0 " + vehicle + "\n");

        // Confirm
        System.out.println("Are you sure you want to update this vehicle?");
        boolean hasConfirmed = MenuHelper.pressToConfirm();
        // Quit if user has changed their mind.
        if (!hasConfirmed) {
            System.out.println("Okay, the vehicle will not be updated.");
            return true;
        }

        // Update fleet
        System.out.println("The vehicle has been updated successfully.");
        return Driver.fleet.updateVehicle(index, vehicle);
    }

    // These methods fetch child class attributes and update them for updateVehicle()
    private static boolean updateEC(ElectricCar electricCar) {
        boolean isValid = false;
        while (!isValid) {
            // Display options
            System.out.println("""
                MORE OPTIONS
                What would you like to update?
                
                1. | Maximum number of passengers
                2. | Maximum autonomy range""");
            int choice = Driver.key.nextInt();

            Driver.key.nextLine(); // Flush!

            isValid = switch (choice) {
                case 1 -> {
                    System.out.print("Please enter the new maximum number of passengers: ");
                    int maxPassengers = Driver.key.nextInt();
                    Driver.key.nextLine(); // Flush!

                    electricCar.setPassengerCapacity(maxPassengers);
                    yield true;
                }
                case 2 -> {
                    System.out.print("Please enter the new maximum autonomy range, in kilometres: ");
                    double autonomyRange = Driver.key.nextDouble();
                    Driver.key.nextLine(); // Flush!

                    electricCar.setRange(autonomyRange);
                    yield true;
                }
                default -> {
                    System.err.println("The option you selected is invalid!");
                    yield false;
                }
            };

        }
        return true;
    }
    private static boolean updateGC(GasolineCar gasolineCar) {
        // I wish they were all this simple
        System.out.print("Please enter a new maximum number of passengers: ");
        int maxPassengers = Driver.key.nextInt();
        gasolineCar.setPassengerCapacity(maxPassengers);
        return true;
    }
    private static boolean updateDT(DieselTruck dieselTruck) {
        boolean isValid = false;
        while (!isValid) {
            // Display options
            System.out.println("""
                MORE OPTIONS
                What would you like to update?
                
                1. | Maximum weight capacity
                2. | Maximum fuel capacity""");
            int choice = Driver.key.nextInt();
            isValid = switch (choice) {
                case 1 -> {
                    System.out.print("Please enter the new maximum weight, in kilograms: ");
                    double maxWeight = Driver.key.nextDouble();
                    dieselTruck.setWeightCapacity(maxWeight);
                    yield true;
                }
                case 2 -> {
                    System.out.print("Please enter the new maximum fuel capacity, in litres: ");
                    double fuelCapacity = Driver.key.nextDouble();
                    dieselTruck.setFuelCapacity(fuelCapacity);
                    yield true;
                }
                default -> {
                    System.err.println("The option you selected is invalid!");
                    yield false;
                }
            };
        }
        return true;
    }
    private static boolean updateET(ElectricTruck electricTruck) {
        boolean isValid = false;
        while (!isValid) {
            // Display options
            System.out.println("""
                MORE OPTIONS
                What would you like to update?
                
                1. | Maximum weight capacity
                2. | Maximum autonomy range""");
            int choice = Driver.key.nextInt();
            isValid = switch (choice) {
                case 1 -> {
                    System.out.print("Please enter the new maximum weight, in kilograms: ");
                    double weightCapacity = Driver.key.nextInt();
                    electricTruck.setWeightCapacity(weightCapacity);
                    yield true;
                }
                case 2 -> {
                    System.out.print("Please enter the new maximum autonomy range, in kilometres: ");
                    double autonomyRange = Driver.key.nextDouble();
                    electricTruck.setRange(autonomyRange);
                    yield true;
                }
                default -> {
                    System.err.println("The option you selected is invalid!");
                    yield false;
                }
            };
        }
        return true;
    }

    public static void showAll(){
        System.out.println("ALL VEHICLES");
        System.out.println(Driver.fleet);
    }
    public static void showOfType(){
        System.out.println("What type of vehicle would you like to view?");
        int type = MenuHelper.pickType();
        String output = switch (type) {
            case 1 -> Driver.fleet.showOfType("EC");
            case 2 -> Driver.fleet.showOfType("GT");
            case 3 -> Driver.fleet.showOfType("DT");
            case 4 -> Driver.fleet.showOfType("ET");
            default -> "None found of this type.";
        };
        System.out.println(output);
    }

    public static void getLargestTruck(){
        DieselTruck truck = Driver.fleet.getLargestTruck();
        if (truck.getWeightCapacity() == 0){
            System.err.println("You do not have any diesel trucks in your fleet.");
        }
        System.out.println("The largest truck in your fleet is:");
        System.out.println(truck);
    }
    public static boolean copyElectricTrucks(){
        System.out.println("Are you sure you want to copy all electric trucks?");
        if (!MenuHelper.pressToConfirm()) return true;

        // Generate array
        Vehicle[] target = Driver.fleet.filterVehicles("Electric Truck");
        if (target == null || target[0] == null){
            System.err.println("You do not have any electric trucks in your fleet.");
            return false;
        }

        ElectricTruck[] electricTrucks = (ElectricTruck[]) target;


        // Copy!
        ElectricTruck[] copy = Fleet.copyVehicles(electricTrucks);

        // Add to fleet
        return Driver.fleet.appendArray(copy);
    }

}
