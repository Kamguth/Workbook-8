package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class Main4 {
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
            System.out.println("3) Display all categories");
            System.out.println("0) Exit");
            System.out.print("Select an option: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                System.out.print("Select an option: ");
            }

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    try (
                            Connection conn = DriverManager.getConnection(url, user, password);
                            PreparedStatement stmt = conn.prepareStatement("SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products");
                            ResultSet rs = stmt.executeQuery()
                    ) {
                        System.out.println("\n--- All Products ---");
                        System.out.printf("%-10s %-35s %-12s %-15s%n", "ProductId", "ProductName", "UnitPrice", "UnitsInStock");
                        System.out.println("------------------------------------------------------------------------");

                        while (rs.next()) {
                            int id = rs.getInt("ProductID");
                            String name = rs.getString("ProductName");
                            double price = rs.getDouble("UnitPrice");
                            int stock = rs.getInt("UnitsInStock");
                            System.out.printf("%-10d %-35s %-12.2f %-15d%n", id, name, price, stock);
                        }
                    } catch (SQLException e) {
                        System.err.println("Error displaying products: " + e.getMessage());
                    }
                    break;

                case 2:
                    try (
                            Connection conn = DriverManager.getConnection(url, user, password);
                            PreparedStatement stmt = conn.prepareStatement("SELECT ContactName, CompanyName, City, Country, Phone FROM Customers ORDER BY Country");
                            ResultSet rs = stmt.executeQuery()
                    ) {
                        System.out.println("\n--- All Customers ---");
                        System.out.printf("%-25s %-35s %-20s %-20s %-20s%n", "Contact Name", "Company Name", "City", "Country", "Phone");
                        System.out.println("------------------------------------------------------------------------------------------------------------------");

                        while (rs.next()) {
                            System.out.printf("%-25s %-35s %-20s %-20s %-20s%n",
                                    rs.getString("ContactName"),
                                    rs.getString("CompanyName"),
                                    rs.getString("City"),
                                    rs.getString("Country"),
                                    rs.getString("Phone"));
                        }
                    } catch (SQLException e) {
                        System.err.println("Error displaying customers: " + e.getMessage());
                    }
                    break;

                case 3:
                    // Display all categories
                    try (
                            Connection conn = DriverManager.getConnection(url, user, password);
                            PreparedStatement stmt = conn.prepareStatement("SELECT CategoryID, CategoryName FROM Categories ORDER BY CategoryID");
                            ResultSet rs = stmt.executeQuery()
                    ) {
                        System.out.println("\n--- All Categories ---");
                        System.out.printf("%-15s %-30s%n", "CategoryId", "CategoryName");
                        System.out.println("-----------------------------------------------");

                        while (rs.next()) {
                            int categoryId = rs.getInt("CategoryID");
                            String categoryName = rs.getString("CategoryName");
                            System.out.printf("%-15d %-30s%n", categoryId, categoryName);
                        }
                    } catch (SQLException e) {
                        System.err.println("Error displaying categories: " + e.getMessage());
                        break;
                    }

                    System.out.print("\nEnter a Category ID to view its products: ");
                    while (!scanner.hasNextInt()) {
                        System.out.print("Invalid input. Enter a valid Category ID: ");
                        scanner.next();
                    }
                    int selectedCategoryId = scanner.nextInt();

                    // Display products in selected category
                    try (
                            Connection conn = DriverManager.getConnection(url, user, password);
                            PreparedStatement stmt = conn.prepareStatement(
                                    "SELECT ProductID, ProductName, UnitPrice, UnitsInStock " +
                                            "FROM Products WHERE CategoryID = ?"
                            )
                    ) {
                        stmt.setInt(1, selectedCategoryId);
                        try (ResultSet rs = stmt.executeQuery()) {
                            System.out.println("\n--- Products in Category " + selectedCategoryId + " ---");
                            System.out.printf("%-10s %-35s %-12s %-15s%n", "ProductId", "ProductName", "UnitPrice", "UnitsInStock");
                            System.out.println("------------------------------------------------------------------------");

                            boolean found = false;
                            while (rs.next()) {
                                found = true;
                                int id = rs.getInt("ProductID");
                                String name = rs.getString("ProductName");
                                double price = rs.getDouble("UnitPrice");
                                int stock = rs.getInt("UnitsInStock");
                                System.out.printf("%-10d %-35s %-12.2f %-15d%n", id, name, price, stock);
                            }

                            if (!found) {
                                System.out.println("No products found for this category.");
                            }
                        }
                    } catch (SQLException e) {
                        System.err.println("Error displaying products in category: " + e.getMessage());
                    }

                    break;

                case 0:
                    System.out.println("Exiting program. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }

        } while (choice != 0);

        scanner.close();
    }
}
