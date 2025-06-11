package com.pluralsight;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASS");

        if (url == null) {
            url = "jdbc:mysql://127.0.0.1:3306/sakila";
        }
        if (user == null || password == null) {
            System.err.println("Please set DB_USER and DB_PASS environment variables.");
            return;
        }

        DataManager dataManager = new DataManager(url, user, password);

        try {
            ActorDAO actorDAO = new ActorDAO(dataManager);
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter an actor's last name: ");
            String lastName = scanner.nextLine().trim();
            List<Actor> actors = actorDAO.getActorsByLastName(lastName);

            if (!actors.isEmpty()) {
                System.out.println("\nMatching actors:");
                actors.forEach(System.out::println);
            }

            System.out.print("\nEnter the actor's FIRST name: ");
            String firstName = scanner.nextLine().trim();
            System.out.print("Enter the actor's LAST name: ");
            lastName = scanner.nextLine().trim();

            List<Film> films = actorDAO.getFilmsByActorName(firstName, lastName);

            if (!films.isEmpty()) {
                System.out.println("\nMovies starring " + firstName + " " + lastName + ":");
                films.forEach(System.out::println);
            }

            scanner.close();
        } finally {
            try {
                dataManager.close();
                System.out.println("\nDataManager closed.");
            } catch (SQLException e) {
                System.err.println("Error closing DataManager: " + e.getMessage());
            }
        }
    }
}
