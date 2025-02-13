package com.vrm.driver;

import com.vrm.vehicle.*;
import static com.vrm.driver.Driver.fleet;

/**
 * UI to deal with anything that has to do with the fleet.
 */
public class FleetDriver {

    private static final String TYPE_MENU = """
                        ------------------------------
                         VEHICLE TYPES
                        ______________________________
                        CARS:
                        1 | Electric Car
                        2 | Gasoline Car
                        TRUCKS:
                        3 | Electric Truck
                        4 | Diesel Truck""";

    public static void getMenu(){

        final int MAX_MAIN_OPTIONS = 8;
        int choice = 0;

        // Loop menu until user wants to return to main
        while (choice != MAX_MAIN_OPTIONS) {

            final String MAIN_MENU = String.format("""
                ------------------------------------- ░  ░░░░  ░
                                                      ▒  ▒▒▒▒  ▒
                                                      ▓▓  ▓▓  ▓▓
                 VEHICLE MANAGEMENT                   ███    ███
                 Number of vehicles: %02d               ████  ████
                ______________________________________________
                1 | Add a vehicle
                2 | Delete a vehicle
                3 | Update vehicle information
                4 | Show all vehicles
                5 | Show by category
                6 | Display largest truck
                7 | Duplicate electric trucks
                8 | Back
              """, fleet.getVehicleCount());

            choice = Selector.pickItem(MAIN_MENU, 1, MAX_MAIN_OPTIONS + 1);
            boolean isSuccessful = switch (choice) {
                case 1 -> createVehicle();
                case 2 -> deleteVehicle();
                case 3 -> updateVehicle();
                case 4 -> showAll();
                case 5 -> showOfType();
                case 6 -> {getLargestTruck(); yield true;}
                case 7 -> copyElectricTrucks();
                case 8 -> true;
                default -> {
                    System.err.println("Unexpected choice in FleetDriver main menu");
                    yield false;
                }
            };
            if (!isSuccessful) {
                System.err.println("Please try again later.");
            }
        }
    }


    /**
     * Initialize any vehicle and adds it to fleet.
     * @return true if successful.
     */
    public static boolean createVehicle() {
        // Pick type
        System.out.println("What type of vehicle would you like to create?");
        int choice = Selector.pickItem(TYPE_MENU, 1, 4 + 1);

        // Prompt general vehicle info
        System.out.print("Vehicle Make: ");
        String make = Driver.key.nextLine();
        System.out.print("Vehicle Model: ");
        String model = Driver.key.nextLine();
        System.out.print("Year of production: ");
        int year = Driver.key.nextInt();
        Driver.key.nextLine(); // Flush!

        // Type specific info
        Vehicle vehicle = switch (choice) {
            case 1 -> { // Electric car
                System.out.print("Passenger capacity: ");
                int passengerCapacity = Driver.key.nextInt();

                System.out.print("Battery range, in kilometres: ");
                double range = Driver.key.nextDouble();

                yield new ElectricCar(make, model, year, passengerCapacity, range);
            }
            case 2 -> { // Gas car
                System.out.print("Passenger capacity: ");
                int passengerCapacity = Driver.key.nextInt();

                yield new GasolineCar(make, model, year, passengerCapacity);
            }
            case 3 -> { // Electric Truck
                System.out.print("Maximum weight, in kilograms: ");
                double maximumWeight = Driver.key.nextDouble();

                System.out.print("Battery range, in kilometres: ");
                double range = Driver.key.nextDouble();

                yield new ElectricTruck(make, model, year, maximumWeight, range);
            }
            case 4 -> {
                System.out.print("Maximum weight, in kilograms: ");
                double maximumWeight = Driver.key.nextDouble();

                System.out.print("Fuel capacity, in litres: ");
                double fuelCapacity = Driver.key.nextDouble();

                yield new DieselTruck(make, model, year, maximumWeight, fuelCapacity);
            }
            default -> null;
        };

        Driver.key.nextLine(); // Flush!

        // Check vehicle non null
        if (vehicle == null) return false;

        // Add vehicle to fleet
        if (fleet.addVehicle(vehicle)) return true;
        else {
            System.err.println("""
                    ERROR!
                        Sorry, there is no space for a new vehicle in your fleet.
                        Please remove a vehicle, then try again.""");
            return false;
        }
    }
    public static boolean deleteVehicle() {
        if (fleet.getVehicleCount() == 0) {
            System.err.println("There are no vehicles to delete yet. Add a vehicle, then try again.");
            return false;
        }

        // Prompt for vehicle index
        System.out.println("Please pick a vehicle to delete: ");
        int index = Selector.pickItem(fleet.toString(), 0, fleet.getVehicleCount());

        // Check vehicle is not leased
        Vehicle target = fleet.retrieveVehicle(index);
        if (target == null) return false;
        if (target.getIsLeased()) {
            System.err.println("""
                    ERROR!
                        Sorry, this vehicle is currently leased.
                        Please terminate the lease then try again.""");
            return false;
        }

        // Prompt confirmation
        System.out.println("Are you sure you want to delete this vehicle?");
        if (Selector.pressToConfirm()){
            System.out.println("This vehicle was successfully deleted.");
            return fleet.removeVehicle(index);
        }
        else {
            System.out.println("Okay, this vehicle will not be deleted.");
            return true;
        }
    }

    /**
     * Fetch vehicle from fleet, display attributes, then update requested attribute.
     * Fetch vehicle from fleet, display attributes, then update requested attribute.
     * @return true if successful.
     */
    public static boolean updateVehicle() {
        // Get vehicle to update
        System.out.println("Please pick a vehicle to update:");

        if (fleet.getVehicleCount() == 0) {
            System.err.println("There are no vehicles to update yet.");
            return false;
        }

        int index = Selector.pickItem(fleet.toString(), 0, fleet.getVehicleCount());
        Vehicle vehicle = fleet.retrieveVehicle(index);

        // Check nonnull
        if (vehicle == null) return false;

        // Display vehicle
        System.out.println(vehicle.getHeader() + "\n0 " + vehicle + "\n");

        boolean isValid = false;
        while (!isValid) {
            // General changes
            final String UPDATE_MENU = """
                
                ------------------------------------------------
                UPDATE VEHICLE
                What would you like to update?
                ________________________________________________
                1. | Make
                2. | Model
                3. | Year
                4. | More options""";

            int choice = Selector.pickItem(UPDATE_MENU, 1, 4 + 1);

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
        System.out.println("   " + vehicle + "\n");

        // Confirm
        System.out.println("Are you sure you want to update this vehicle?");
        boolean hasConfirmed = Selector.pressToConfirm();
        // Quit if user has changed their mind.
        if (!hasConfirmed) {
            System.out.println("Okay, the vehicle will not be updated.");
            return true;
        }

        // Update fleet
        System.out.println("The vehicle has been updated successfully.");
        return fleet.updateVehicle(index, vehicle);
    }

    // These methods fetch child class attributes and update them for updateVehicle()
    private static boolean updateEC(ElectricCar electricCar) {
        boolean isValid = false;
        while (!isValid) {
            // Display options
            final String EC_MENU  = """
                ------------------------------------------------
                MORE OPTIONS
                What would you like to update?
                ________________________________________________
                1. | Maximum number of passengers
                2. | Maximum autonomy range""";
            int choice = Selector.pickItem(EC_MENU, 1, 4 + 1);

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
            final String DT_MENU  = """
                ------------------------------------------------
                MORE OPTIONS
                What would you like to update?
                ________________________________________________
                1. | Maximum weight capacity
                2. | Maximum fuel capacity""";
            int choice = Selector.pickItem(DT_MENU, 1, 4 + 1);

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
            final String ET_MENU  = """
                ------------------------------------------------
                MORE OPTIONS
                What would you like to update?
                ________________________________________________
                1. | Maximum weight capacity
                2. | Maximum autonomy range""";
            int choice = Selector.pickItem(ET_MENU, 1, 4 + 1);
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

    // Display methods
    public static boolean showAll(){
        System.out.println("\nALL VEHICLES");
        System.out.println(fleet);
        Selector.pressToContinue();
        return true;
    }
    public static boolean showOfType(){
        System.out.println("What type of vehicle would you like to view?");
        int type = Selector.pickItem(TYPE_MENU, 1, 4 + 1);
        System.out.println();
        String output = switch (type) {
            case 1 -> fleet.showOfType("EC");
            case 2 -> fleet.showOfType("GT");
            case 3 -> fleet.showOfType("ET");
            case 4 -> fleet.showOfType("DT");
            default -> "None found of this type.";
        };
        System.out.println(output);
        Selector.pressToContinue();
        return true;
    }

    // Requested methods
    public static void getLargestTruck(){
        DieselTruck truck = fleet.getLargestTruck();
        if (truck.getWeightCapacity() == 0){
            System.err.println("You do not have any diesel trucks in your fleet.");
            return;
        }
        System.out.println("The largest truck in your fleet is:");
        System.out.println(truck);
    }
    public static boolean copyElectricTrucks(){
        if (fleet.getVehicleCount() == 0){
            System.err.println("You do not have any vehicles in your fleet.");
            return false;
        }
        System.out.println("This option copies your electric trucks and adds the new trucks to your fleet. \nAre you sure you want to copy all electric trucks?");
        if (!Selector.pressToConfirm()) return true;

        // Generate array
        Vehicle[] target = fleet.filterVehicles("Electric Truck");
        if (target == null || target[0] == null){
            System.err.println("You do not have any electric trucks in your fleet.");
            return true;
        }

        ElectricTruck[] electricTrucks = new ElectricTruck[target.length];
        for (int i = 0;  i < target.length; i++){
            if (target[i] == null || !(target[i] instanceof ElectricTruck)) continue;
            electricTrucks[i] = (ElectricTruck)  target[i];
        }

        // Copy!
        ElectricTruck[] copy = Fleet.copyVehicles(electricTrucks);

        // Add to fleet
        return fleet.appendArray(copy);
    }
}
