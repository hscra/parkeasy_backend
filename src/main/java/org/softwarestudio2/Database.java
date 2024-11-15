package org.softwarestudio2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
    public Connection connection;

    public Database(){
        String host = "";
        String port = "";
        String dbname = "";
        String username = "";
        String passwd = "";

        try (BufferedReader reader = new BufferedReader(
                new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\org\\softwarestudio2\\dbconfig.txt"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] keyValue = line.split("=", 2); // Split line into key and value
                if (keyValue.length == 2) {
                    switch (keyValue[0].trim()) {
                        case "HOST":
                            host = keyValue[1].trim();
                            break;
                        case "PORT":
                            port = keyValue[1].trim();
                            break;
                        case "USER":
                            username = keyValue[1].trim();
                            break;
                        case "PASSWORD":
                            passwd = keyValue[1].trim();
                            break;
                        case "DB":
                            dbname = keyValue[1].trim();
                            break;
                    }
                }
            }

            // Build the connection URL
            String url = "jdbc:mysql://" + host + ":" + port + "/" + dbname;

            // Load the driver and establish connection
            this.loadDriver();
            this.connection = DriverManager.getConnection(url, username, passwd);
        } catch (IOException e) {
            throw new RuntimeException("Error reading configuration file: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new RuntimeException("Error establishing database connection: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }

    private void loadDriver() throws Exception{
        try{
            Class.forName("org.postgresql.Driver");
        }catch (ClassNotFoundException ee){
            throw new Exception("Driver file not found: " + ee.getMessage());
        }
    }

    public void executeQuery(String[] queries, Object[][] parameters) {
        try {
            // Begin transaction
            this.connection.setAutoCommit(false);

            for (int i = 0; i < queries.length; i++) {
                try (PreparedStatement preparedStatement = this.connection.prepareStatement(queries[i])) {
                    // Set query parameters
                    if (parameters != null && i < parameters.length) {
                        for (int j = 0; j < parameters[i].length; j++) {
                            preparedStatement.setObject(j + 1, parameters[i][j]);
                        }
                    }

                    // Execute query
                    preparedStatement.execute();
                }
            }

            // Commit transaction
            this.connection.commit();
        } catch (SQLException e) {
            try {
                // Rollback on error
                this.connection.rollback();
                throw new RuntimeException("Error executing queries. Transaction rolled back: " + e.getMessage(), e);
            } catch (SQLException rollbackEx) {
                throw new RuntimeException("Error during rollback: " + rollbackEx.getMessage(), rollbackEx);
            }
        } finally {
            try {
                // Reset auto-commit to default
                this.connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException("Error resetting auto-commit: " + e.getMessage(), e);
            }
        }
    }

    public void terminateConnection() throws Exception{
        try {
            if(this.connection.isValid(60)){
                this.connection.close();
            }else{
                System.out.println("No connection to close");
            }
        }catch(SQLException ee){
            throw new Exception("Invalid connection: " + ee.getMessage());
        }
    }
}
