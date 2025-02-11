package com.vrm.driver;

import com.vrm.client.Client;
import com.vrm.client.ClientManager;

public class ClientController {
    private static final ClientManager clientManager = Driver.clientManager;

    public static boolean createClient(){
        // Check for space
        if (clientManager.getClientCount() >= clientManager.getMaxClients()) {
            System.err.println("""
                    ERROR!
                        Sorry, there is no space for a new client in your store.
                        Please remove a client, then try again.""");
            return false;
        }

        System.out.println("NEW CLIENT");

        System.out.print("First and Last Name: ");
        String name = Driver.key.nextLine();

        // Check name does not exist
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
        int index = MenuHelper.pickClient();

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
        if (!MenuHelper.pressToConfirm()) {
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
        int index = MenuHelper.pickClient();

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
        if (MenuHelper.pressToConfirm()) {
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
