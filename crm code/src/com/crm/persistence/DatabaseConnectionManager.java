package com.crm.persistence;

public class DatabaseConnectionManager {
    private static DatabaseConnectionManager instance;

    private DatabaseConnectionManager() {}

    public static synchronized DatabaseConnectionManager getInstance() {
        if (instance == null) instance = new DatabaseConnectionManager();
        return instance;
    }

    public void connect() {
        // Skeleton: open connection
    }

    public void disconnect() {
        // Skeleton: close connection
    }
}
