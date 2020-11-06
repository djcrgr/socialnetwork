package com.getjavajob.training.karpovn.socialnetwork.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Properties;

public class ConnectionPool implements Runnable {

    private String driver;
    private String url;
    private String username;
    private String password;
    private int maxConnections;
    private boolean waitIfBusy;
    private LinkedList<Connection> availableConnections;
    private LinkedList<Connection> busyConnections;
    private boolean connectionPending = false;
    private static ConnectionPool instance = null;


    public static ConnectionPool getInstance() throws SQLException, IOException {
        Properties properties = new Properties();
        properties.load(ConnectionPool.class.getClassLoader().getResourceAsStream("database.properties"));
        String driver = properties.getProperty("database.driver");
        String url = properties.getProperty("database.url");
        String username = properties.getProperty("database.username");
        String password = properties.getProperty("database.password");
        if(instance == null) {
            synchronized(ConnectionPool.class) {
                if(instance == null) {
                    instance = new ConnectionPool(driver, url, username, password, 5, 9, true);
                }
            }
        }

        return instance;
    }

    private ConnectionPool(String driver , String url, String username,
                                             String password, int initialConnections, int maxConnections,
                                             boolean waitIfBusy) throws SQLException {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
        this.maxConnections = maxConnections;
        this.waitIfBusy = waitIfBusy;
        if (initialConnections > maxConnections) {
            initialConnections = maxConnections;
        }
        availableConnections = new LinkedList<>();
        busyConnections = new LinkedList<>();
        for (int i = 0; i < initialConnections; i++) {
            availableConnections.add(makeNewConnection());
        }
    }

    public synchronized Connection getConnection() throws SQLException, ClassNotFoundException {
//        Class.forName("java.po.Connection");
        if (!availableConnections.isEmpty()) {
            Connection existingConnection = (Connection) availableConnections.getLast();///
            int lastIndex = availableConnections.size() - 1;
            availableConnections.removeLast();
            if (existingConnection.isClosed()) {
                notifyAll(); // Freed up a spot for anybody waiting
                return (getConnection());
            } else {
                busyConnections.add(existingConnection);
                return (existingConnection);
            }
        } else {
            if ((totalConnections() < maxConnections) && !connectionPending) {
                makeBackgroundConnection();
            } else if (!waitIfBusy) {
                throw new SQLException("Connection limit reached");
            }
            try {
                wait();
            } catch (InterruptedException ie) {
            }
            return (getConnection());
        }
    }


    private void makeBackgroundConnection() {
        connectionPending = true;
        try {
            Thread connectThread = new Thread(this);
            connectThread.start();
        } catch (OutOfMemoryError oome) {
        }
    }


    public void run() {
        try {
            Connection connection = makeNewConnection();
            synchronized (this) {
                availableConnections.add(connection);
                connectionPending = false;
                notifyAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Connection makeNewConnection() throws SQLException {
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username,
                    password);
            return (connection);
        } catch (Exception cnfe) {
            cnfe.printStackTrace();
            throw new SQLException(
                    "com.getjavajob.training.karpovn.socialnetwork.dao.ConnectionPool:: SQLException encountered:: "
                            + cnfe.getMessage());
        }
    }

    public synchronized void free(Connection connection) {
        busyConnections.remove(connection);
        availableConnections.add(connection);
        // Wake up threads that are waiting for a connection
        notifyAll();
    }

    public synchronized int totalConnections() {
        return (availableConnections.size() + busyConnections.size());
    }

    public synchronized void closeAllConnections() {
        closeConnections(availableConnections);
        availableConnections = new LinkedList<>();
        closeConnections(busyConnections);
        busyConnections = new LinkedList<>();
    }

    private void closeConnections(LinkedList<Connection> connections) {
        try {
            for (int i = 0; i < connections.size(); i++) {
                Connection connection = (Connection) connections.get(i);
                if (!connection.isClosed()) {
                    connection.close();
                }
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            // Ignore errors; garbage collect anyhow
        }
    }

    public synchronized String toString() {
        String info = "com.getjavajob.training.karpovn.socialnetwork.dao.ConnectionPool(" + url + "," + username + ")"
                + ", available=" + availableConnections.size() + ", busy="
                + busyConnections.size() + ", max=" + maxConnections;
        return (info);
    }
}
