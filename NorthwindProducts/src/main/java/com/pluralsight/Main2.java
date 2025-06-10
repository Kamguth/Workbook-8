package com.pluralsight;

import java.sql.*;

public class Main2 {
    public static void main(String[] args) {

        String url = "jdbc:mysql://127.0.0.1:3306/northwind";
        String user = "root";
        String password = "kamyg1717";

        String query = "SELECT * FROM Products";
        ResultSet results = null;
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Establishing connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.prepareStatement(query);

            // Executing query
            results = statement.executeQuery();

            System.out.printf("%-10s %-35s %-12s %-15s%n", "ProductId", "ProductName", "UnitPrice", "UnitsInStock");
            System.out.println("------------------------------------------------------------------------");

            // Processing the result set
            while (results.next()) {
                int productId = results.getInt("ProductID");
                String productName = results.getString("ProductName");
                double unitPrice = results.getDouble("UnitPrice");
                int unitsInStock = results.getInt("UnitsInStock");
                System.out.printf("%-10s %-35s %-12s %-15s%n", productId, productName, unitPrice, unitsInStock);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (results != null) {
                try {
                    results.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e ) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}