package org.softwarestudio2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    Connection connection;

    public DatabaseConnection(){
        BufferedReader reader;
        String host = "";
        String port = "";
        String dbname = "";
        String username = "";
        String passwd = "";

        try{
            //ADD FILE!!!!!!!!!
            reader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\org\\softwarestudio2\\FILE"));
            String line = reader.readLine();

            if(line != null){
                host = line;
                port = reader.readLine();
                dbname = reader.readLine();
                username = reader.readLine();
                passwd = reader.readLine();
            }
            reader.close();

            //ADD URL!!!!!!!!!!!
            String url = "URL";

            //Setup connection
            this.loadDriver();
            this.connection = DriverManager.getConnection(url, username, passwd);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadDriver() throws Exception{
        try{
            Class.forName("org.postgresql.Driver");
        }catch (ClassNotFoundException ee){
            throw new Exception("Driver file not found: " + ee.getMessage());
        }
    }

    public void executeQuery(String... s){
        //IMPLEMENT
        //GitHub testing sth jajebe
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
