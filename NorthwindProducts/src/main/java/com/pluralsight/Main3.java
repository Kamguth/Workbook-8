package com.pluralsight;


import java.sql.*;
import java.util.Scanner; // Import the Scanner class

public class Main3 {
    public static void main(String[] args) {

        String url = "jdbc:mysql://127.0.0.1:3306/northwind";
        String user = "root";
        String password = "kamyg1717";

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nWhat do you want to do?");
            System.out.println("1) Display all products");
            System.out.println("2) Display all customers");
            System.out.println("0) Exit");
            System.out.print("Select an option: ");

            //ensures looping of menu and processes invalid entries
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                System.out.print("Select an option: ");
            }
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    //Logic to display all products
                    String productQuery = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products";
                    ResultSet productResults = null;
                    Connection productConnection = null;
                    PreparedStatement productStatement = null;

                    try {
                        productConnection = DriverManager.getConnection(url, user, password);
                        productStatement = productConnection.prepareStatement(productQuery);
                        productResults = productStatement.executeQuery();

                        System.out.println("\n--- All Products ---");
                        System.out.printf("%-10s %-35s %-12s %-15s%n", "ProductId", "ProductName", "UnitPrice", "UnitsInStock");
                        System.out.println("------------------------------------------------------------------------");

                        while (productResults.next()) {
                            int productId = productResults.getInt("ProductID");
                            String productName = productResults.getString("ProductName");
                            double unitPrice = productResults.getDouble("UnitPrice");
                            int unitsInStock = productResults.getInt("UnitsInStock");
                            System.out.printf("%-10s %-35s %-12s %-15s%n", productId, productName, unitPrice, unitsInStock);
                        }

                    } catch (SQLException e) {
                        System.err.println("Error displaying products: " + e.getMessage());
                        e.printStackTrace();
                    } finally {
                        //Ensure all resources are closed
                        if (productResults != null) {
                            try {
                                productResults.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (productStatement != null) {
                            try {
                                productStatement.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (productConnection != null) {
                            try {
                                productConnection.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;

                case 2:
                    //Logic to display all customers
                    String customerQuery = "SELECT ContactName, CompanyName, City, Country, Phone FROM Customers ORDER BY Country";
                    ResultSet customerResults = null;
                    Connection customerConnection = null;
                    PreparedStatement customerStatement = null;

                    try {
                        customerConnection = DriverManager.getConnection(url, user, password);
                        customerStatement = customerConnection.prepareStatement(customerQuery);
                        customerResults = customerStatement.executeQuery();

                        System.out.println("\n--- All Customers ---");
                        System.out.printf("%-25s %-35s %-20s %-20s %-20s%n", "Contact Name", "Company Name", "City", "Country", "Phone");
                        System.out.println("------------------------------------------------------------------------------------------------------------------");

                        while (customerResults.next()) {
                            String contactName = customerResults.getString("ContactName");
                            String companyName = customerResults.getString("CompanyName");
                            String city = customerResults.getString("City");
                            String country = customerResults.getString("Country");
                            String phone = customerResults.getString("Phone");
                            System.out.printf("%-25s %-35s %-20s %-20s %-20s%n", contactName, companyName, city, country, phone);
                        }

                    } catch (SQLException e) {
                        System.err.println("Error displaying customers: " + e.getMessage());
                        e.printStackTrace();
                    } finally {
                        // Ensure all resources are closed
                        if (customerResults != null) {
                            try {
                                customerResults.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (customerStatement != null) {
                            try {
                                customerStatement.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (customerConnection != null) {
                            try {
                                customerConnection.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;

                case 0:
                    System.out.println("Exiting program. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
            System.out.println("\n");

        } while (choice != 0); //Keep looping until user enters 0

        scanner.close();
    }
}