package org.park_easy_backend;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class Database {
    public Connection connection;

    public Database(){
        String host = "";
        String port = "";
        String dbname = "";
        String username = "";
        String passwd = "";

        try (BufferedReader reader = new BufferedReader(
                new FileReader(System.getProperty("user.dir") + "\\park-easy-backend\\src\\main\\java\\org\\park_easy_backend\\SPOILER_database.config"))) {

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
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException ee){
            throw new Exception("Driver file not found: " + ee.getMessage());
        }
    }

    public void executeQueryWithParams(String sql, Object... params) {
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            for(int i = 0; i < params.length; i++){
                stmt.setObject(i + 1, params[i]);
            }
            int rowsAffected = stmt.executeUpdate();

            if(rowsAffected == 0){
                throw new RuntimeException("Query failed: " + sql);
            }
            System.out.println("Query executed successfully: " + sql);
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            throw new RuntimeException("Query execution failed: " + sql, e);
        }
    }

    public void executeSimpleQuery(String sql) {
        try (Statement statement = this.connection.createStatement()) {
            // Execute the provided query
            statement.execute(sql);
            System.out.println("Query executed successfully: " + sql);
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            throw new RuntimeException("Query execution failed: " + sql, e);
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

    public static void main(String[] args){
        Database db = new Database();
        try {
//            String createTableQuery = "CREATE TABLE sex ("
//                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
//                    + "name VARCHAR(100) NOT NULL, "
//                    + "email VARCHAR(255) NOT NULL UNIQUE"
//                    + ")";
//            db.executeSimpleQuery(createTableQuery);

//            String sqlWithParams = "INSERT INTO users (name, email) values (?,?)";
//            db.executeQueryWithParams(sqlWithParams, "John", "John@email");

            System.out.println("All queries executed successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
