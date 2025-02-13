package com.vrm.driver;

public class Menu {

    // Displays splash screen at launch and exit
    public static void displaySplashScreen() {
        String[] art = {"""

        ++++++++++++++++++++++++++++++++++++++++++++++++++++
        |                                                  |
        |          ░  ░░░░  ░░       ░░░  ░░░░  ░          |
        |          ▒  ▒▒▒▒  ▒▒  ▒▒▒▒  ▒▒   ▒▒   ▒          |
        |          ▓▓  ▓▓  ▓▓▓       ▓▓▓        ▓          |
        |          ███    ████  ███  ███  █  █  █          |
        |          ████  █████  ████  ██  ████  █          |
        |                                                  |
        |                                                  |
        |              VEHICLE RENTAL MANAGER              |
        |           Version 1.0   Leo & Co. Inc.           |
        ++++++++++++++++++++++++++++++++++++++++++++++++++++
    """
        };
        for (String line : art) {
            System.out.println(line);
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Welcomes user and prompts for testing.
     * @return true for testing mode
     */
    public static boolean bootUp(){
        System.out.println("""
                ____________________________________________________________________
                          == Welcome to Vehicle Rental Manager 2025 ==
                > Press any key to access VRM 25 or press <0> to access TEST MODE. <
                --------------------------------------------------------------------""");
        String choice = Driver.key.nextLine();
        return choice.equals("0");
    }

    // Main Menu
    public static void getMainMenu(){
        final String MAIN_MENU = """
                --------------------------------------- ░  ░░░░  ░
                                                        ▒   ▒▒   ▒
                                                        █  █  █  █
                 VEHICLE RENTAL MANAGER 2025            █  ████  █
                 What would you like to do today?       █  ████  █
                ________________________________________________
                1 | Vehicle Management
                2 | Client Management
                3 | Leasing Operations
                4 | Exit
              """;
        final int MAX_OPTIONS = 4;

        int choice = Selector.pickItem(MAIN_MENU, 1, MAX_OPTIONS + 1);
        switch (choice) {
            case 1 -> FleetDriver.getMenu();
            case 2 -> ClientDriver.getMenu();
            case 3 -> LeaseDriver.getMenu();
            case 4 -> Menu.exit(0);
            default -> {System.err.println("Unexpected choice in Menu main menu");}
        }
    }

    // Exit method
    public static void exit(int status){

        // Confirm if exiting from menu
        if (status == 0){
            System.out.println("Are you sure you want to shut down?");
            if (!Selector.pressToConfirm()){
                return;
            }
        }

        Menu.displaySplashScreen();
        System.out.println("== Thank you for using Vehicle Rental Manager 2025 ==");

        System.exit(status);
    }
}
