package com.vrm.driver;

import com.vrm.client.Client;
import com.vrm.client.ClientManager;

public class ClientDriver {
    private static ClientManager clientManager = Driver.clientManager;

    public static void getMenu(){

        final int MAX_MAIN_OPTIONS = 4;
        int choice = 0;

        // Loop menu until user wants to return to main
        while (choice != 4) {
            final String MAIN_MENU = String.format("""
                ------------------------------------- ░░      ░░
                                                      ▒  ▒▒▒▒  ▒
                                                      ▓  ▓▓▓▓▓▓▓
                 CLIENT MANAGEMENT                    █  ████  █
                 Number of clients: %02d                ██      ██
                ________________________________________________
                1 | Add a client
                2 | Delete a client
                3 | Update client data
                4 | Back
              """, clientManager.getClientCount());

            choice = Selector.pickItem(MAIN_MENU, 1, MAX_MAIN_OPTIONS + 1);
            boolean isValid = switch (choice) {
                case 1 -> createClient();
                case 2 -> deleteClient();
                case 3 -> updateClient();
                case 4 -> true;
                default -> {
                    System.err.println("Unexpected choice in ClientDriver main menu");
                    yield false;
                }
            };
        }
    }
    public static boolean createClient(){
        // 1. Check for space
        if (clientManager.getClientCount() >= clientManager.getMaxClients()) {
            System.err.println("""
                    ERROR!
                        Sorry, there is no space for a new client in your store.
                        Please remove a client, then try again.""");
            return false;
        }

        // 2. Prompt for new name
        System.out.println("NEW CLIENT");

        System.out.print("First and Last Name: ");
        String name = Driver.key.nextLine();

        // 3. Check name does not exist
        if (clientManager.retrieveClient(name) != null) {
            System.err.println("Sorry, a client with the same name already exists.");
            return false;
        };

        Client client = new Client(name);
        return clientManager.addClient(client);
    }
    public static boolean deleteClient(){
        // Check if there are clients to delete
        if (clientManager.getClientCount() <= 0) {
            System.err.println("""
                    ERROR!
                        Sorry, there are no clients in your store to delete.""");
            return false;
        }

        // Retrieve client
        System.out.println("Which client would you like to delete?");
        int index = Selector.pickItem(clientManager.toString(), 0, clientManager.getClientCount());

        // Fetch name for leaseManager
        String clientName = clientManager.retrieveClient(index).getName();

        // Verify client is safe to delete
        if (Driver.leaseManager.clientNumberOfLeases(clientName) != 0){
            System.err.println("""
                    ERROR!
                        Sorry, this client currently leases vehicles.
                        Please terminate all of this client's leases then try again.""");
            return false;
        }

        // Confirm
        System.out.println("Are you sure you want to delete " + clientName + "?");
        if (!Selector.pressToConfirm()) {
            System.out.println("Okay, this client will not be deleted.");
            return false;
        }
        // Remove
        else {
            System.out.println("Successfully deleted client.");
            return clientManager.removeClient(index);
        }
    }
    public static boolean updateClient(){
        if (clientManager.getClientCount() <= 0) {
            System.err.println("""
                    ERROR!
                        Sorry, there are no clients in your store to edit.""");
            return false;
        }

        System.out.println("Which client would you like to edit?");
        int index = Selector.pickItem(clientManager.toString(), 0, clientManager.getClientCount());

        // Display client
        Client client = clientManager.retrieveClient(index);
        System.out.println(client);
        System.out.print("Please enter a new name for your client: ");
        String name = Driver.key.nextLine();

        // Check if a customer with the same name does not exist.
        if (clientManager.retrieveClient(name) != null) {
            System.err.println("Sorry, a client with the same name already exists.");
            return false;
        }

        System.out.println("Are you sure you would like to proceed?");
        if (Selector.pressToConfirm()) {
            System.out.println("Successfully updated client.");
            // Push update
            return clientManager.updateClient(index, name);
        }
        else {
            System.out.println("Okay, this client will not be renamed.");
            return true;
        }
    }
}
