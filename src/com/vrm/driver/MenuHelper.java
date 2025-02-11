package com.vrm.driver;

/**
 * Prompts user from predefined menus.
 */
public class MenuHelper {

    // Vehicle Menus
    public static int pickType(){
        System.out.println(
                """
                        CARS:
                        1. | Electric Car
                        2. | Gasoline Car
                        TRUCKS:
                        3. | Electric Truck
                        4. | Diesel Truck"""
        );
        int choice;
        // Loop till valid
        while (true) {
            System.out.print("Enter your choice: ");
            choice = Driver.key.nextInt();
            if (choice < 1 || choice > 4){
                System.err.println("Sorry, that choice is invalid.");
            }
            else break;
        }
        Driver.key.nextLine(); // Flush!
        return choice;
    }

    /**
     * Displays vehicles and prompts for valid index choice.
     */
    public static int pickVehicleIndex(){
        System.out.println(Driver.fleet);
        int choice;
        while (true) {
            System.out.print("Please enter your choice: ");
            choice = Driver.key.nextInt();
            if (choice < 0 || choice >= Driver.fleet.getVehicleCount()){
                System.err.println("Your choice was not valid.");
            }
            else break;
        }
        Driver.key.nextLine(); // Flush!
        return choice;
    }

    // Client Menus
    public static int pickClient(){
        System.out.println(Driver.clientManager);
        int choice;
        while (true) {
            System.out.print("Please enter the requested client's index: ");
            choice = Driver.key.nextInt();
            if (choice < 0 || choice >= Driver.clientManager.getClientCount()){
                System.err.println("Your choice was not valid.");
            }
            else break;
        }
        Driver.key.nextLine(); // Flush!
        return choice;
    }

    /**
     * @return true if user confirms.
     */
    public static boolean pressToConfirm(){
        System.out.println("-> Press <ENTER> to continue, or type <N> to cancel <-");
        String input = Driver.key.nextLine();
        return !input.equalsIgnoreCase("N");
    }
}
