package com.vrm.driver;


/**
 * Utility methods for user input selection in the console.
 */
public class Selector {

    /**
     * Display and prompt user to select an option.
     *
     * @param menu The menu options to display.
     * @param min (inclusive).
     * @param max (exclusive).
     * @return Choice.
     */
    public static int pickItem(String menu, int min, int max){
        System.out.println();
        // Show selection
        System.out.println(menu);
        int choice;

        // Loop till valid
        while (true) {
            System.out.print(Colour.W_B + "\n> Enter your choice, then press <ENTER>: " + Colour.RESET);
            choice = Driver.key.nextInt();
            if (choice < min || choice >= max) {
                System.err.println(Colour.RED + "Sorry, your choice is invalid. Try again." + Colour.RESET);
                continue;
            }
            break;
        }
        Driver.key.nextLine(); // Flush!
        System.out.println();
        return choice;
    }

    // Leases have different selection logic
    public static int pickLease(){
        System.out.println(Driver.leaseManager);
        int choice;
        while (true) {
            System.out.print("\n> Enter the requested lease ID, then press <ENTER>: ");
            choice = Driver.key.nextInt();
            if (Driver.leaseManager.retrieveLease(choice) == null){
                System.err.println("Your choice was invalid.");
            }
            else break;
        }
        Driver.key.nextLine(); // Flush!
        return choice;
    }

    /**
     * @return True if the user confirms, false if canceled.
     */
    public static boolean pressToConfirm(){
        System.out.println(Colour.W_B + "-> Press <ENTER> to continue, or type <N> to cancel <-" + Colour.RESET);
        String input = Driver.key.nextLine();
        return !input.equalsIgnoreCase("N");
    }
    public static void pressToContinue(){
        System.out.println(Colour.W_B + "-> Press <ENTER> to continue <-" + Colour.RESET);
        Driver.key.nextLine();
    }
}
