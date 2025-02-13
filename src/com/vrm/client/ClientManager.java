package com.vrm.client;

/**
 * Manages client list.
 * Handles adding and deleting clients.
 */
public class ClientManager {
    private Client[] clients;
    private int MAX_CLIENTS;
    private int clientCount;

    // CONSTRUCTORS
    public ClientManager() {
        MAX_CLIENTS = 10;
        clients = new Client[MAX_CLIENTS];
        clientCount = 0;
    }
    public ClientManager(int maxClients) {
        MAX_CLIENTS = maxClients;
        clients = new Client[maxClients];
        clientCount = 0;
    }
    public ClientManager(ClientManager clientManager) {
        MAX_CLIENTS = clientManager.MAX_CLIENTS;
        clients = new Client[MAX_CLIENTS];
        clientCount = clientManager.MAX_CLIENTS;
    }

    // GET
    public int getClientCount() {
        return clientCount;
    }
    public int getMaxClients() {
        return MAX_CLIENTS;
    }

    public Client retrieveClient(int index) {
        if (index < 0 || index >= clientCount) return null;
        return new Client(clients[index]);
    }
    public Client retrieveClient(String name) {
        for (int i = 0; i < MAX_CLIENTS; i++) {
            if (clients[i] == null) continue;
            if (clients[i].getName().equalsIgnoreCase(name)) return clients[i];
        }
        return null;
    }

    // CLIENT OPERATIONS
    public boolean addClient(Client client) {
        // Check client capacity
        if (clientCount >= MAX_CLIENTS) return false;

        // Add clone of client to array
        clients[clientCount] = new Client(client);

        // Increment
        clientCount++;

        return true;
    }
    public boolean removeClient(int index){
        // Check index (returns false if clientCount is 0 too)
        if (index < 0 || index >= clientCount) return false;

        // Put last client at index
        clients[index] = clients[clientCount - 1];
        // Obliterate last client
        clients[clientCount - 1] = null;

        // Decrement
        clientCount--;
        return true;
    }
    public boolean updateClient(int index, String name) {
        // Check valid index
        if (index < 0 || index >= clientCount) return false;

        // Check name does not exist
        if (retrieveClient(name) != null) return false;

        clients[index].setName(name);
        return true;
    }

    // MISC
    @Override
    public String toString() {
        String returnString = "";
        if (clients == null || clientCount == 0) return "No customers to display yet.";
        for (int i = 0; i < clientCount; i++) {
            returnString += "\n" + i + " | " + clients[i];
        }
        return returnString;
    }
}
