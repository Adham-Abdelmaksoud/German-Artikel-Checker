package sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;

public class DatabaseConnector {
    private Connection connection;
    public DatabaseConnector(){
        try{
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/deutsch", "root", "root");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public Connection getConnection(){
        return connection;
    }

    public static void main(String[] args){
        try{
            DatabaseConnector connector = new DatabaseConnector();
            File file = new File("Die.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                PreparedStatement statement = connector.getConnection().prepareStatement("insert into artikeln values(?,?)");
                statement.setString(1, line);
                statement.setString(2, "Die");
                statement.execute();
            }
            br.close();
            fr.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
