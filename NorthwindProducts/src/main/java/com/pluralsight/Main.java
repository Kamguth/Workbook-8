package com.pluralsight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = "root";
        String password = "kamyg1717";

        try {
            // Load the MySQL driver (optional in newer JDBC, but safe)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open connection
            Connection connection = DriverManager.getConnection(url, username, password);

            // Create statement
            Statement statement = connection.createStatement();

            // Execute query to get all products
            String query = "SELECT ProductName FROM products";
            ResultSet results = statement.executeQuery(query);

            // Process results
            while (results.next()) {
                String productName = results.getString("ProductName");
                System.out.println(productName);
            }

            // Close connection
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
