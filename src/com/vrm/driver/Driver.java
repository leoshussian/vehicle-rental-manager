package com.vrm.driver;

import com.vrm.client.*;
import com.vrm.vehicle.*;

import java.util.Scanner;

public class Driver {

    // 0. Initialize all
    static Scanner key = new Scanner(System.in);
    static Fleet fleet = new Fleet(50);
    static ClientManager clientManager = new ClientManager();
    static LeaseManager leaseManager = new LeaseManager(fleet);

    public static void main(String[] args) {

        // 1. Greet user
        Menu.displaySplashScreen();

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
