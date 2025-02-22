package com.vrm.driver;

/**
 * Prompts boot up, exit, and main menu.
 */
public class Menu {

    // Displays splash screen at launch and exit
    public static void displaySplashScreen() throws InterruptedException {
        String[] art = {
                "++++++++++++++++++++++++++++++++++++++++++++++++++++",
                "|                                                  |",
                "|          ░  ░░░░  ░░       ░░░  ░░░░  ░          |",
                "|          ▒  ▒▒▒▒  ▒▒  ▒▒▒▒  ▒▒   ▒▒   ▒          |",
                "|          ▓▓  ▓▓  ▓▓▓       ▓▓▓        ▓          |",
                "|          ███    ████  ███  ███  █  █  █          |",
                "|          ████  █████  ████  ██  ████  █          |",
                "|                                                  |",
                "|                                                  |",
                "|              VEHICLE RENTAL MANAGER              |",
                "|           Version 1.0   Leo & Co. Inc.           |",
                "++++++++++++++++++++++++++++++++++++++++++++++++++++",
        };
        for (String line : art) {
            System.out.println("\t" + Colour.BLACK + Colour.WHITE_BACKGROUND + line + Colour.RESET);
            Thread.sleep(50);
        }
    }

    /**
     * Welcomes user and prompts for testing.
     * @return true for testing mode
     */
    public static boolean bootUp(){
        System.out.printf("""
                ____________________________________________________________________
                          == Welcome to Vehicle Rental Manager 2025 ==
                %s
                --------------------------------------------------------------------
                """
                ,Colour.W_B + "> Press any key to access VRM 25 or press <0> to access TEST MODE. <" + Colour.RESET);
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
            default -> System.err.println("Unexpected choice in Menu main menu");
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
        try {
            Menu.displaySplashScreen();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Colour.GREEN + "    == Thank you for using Vehicle Rental Manager 2025 ==" + Colour.RESET);

        Driver.key.close();
        System.exit(status);
    }
}
