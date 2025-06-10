package com.pluralsight;

import java.sql.*;

public class Main2 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = "root";
        String password = "kamyg1717";

        String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products";

        try {
            // Establish connection
            Connection connection = DriverManager.getConnection(url, username, password);

            // Create and execute query
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query);

            // Display product info
            while (results.next()) {
                int id = results.getInt("ProductID");
                String name = results.getString("ProductName");
                double price = results.getDouble("UnitPrice");
                int stock = results.getInt("UnitsInStock");

                System.out.println("Product Id: " + id);
                System.out.println("Name: " + name);
                System.out.println("Price: $" + price);
                System.out.println("Stock: " + stock);
                System.out.println("--------------------");
            }

            // Close connections
            results.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
