package com.pluralsight;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASS");

        if (url == null) url = "jdbc:mysql://127.0.0.1:3306/sakila";
        if (user == null) user = "root";
        if (password == null) password = "kamyg1717";

        DataManager dataManager = new DataManager(url, user, password);

        try {
            Scanner scanner = new Scanner(System.in);

            // Step 1: Search actor by name
            System.out.print("Enter a name to search for actors: ");
            String searchName = scanner.nextLine();

            List<Actor> actors = dataManager.searchActorsByName(searchName);
            if (actors.isEmpty()) {
                System.out.println("No actors found.");
                return;
            }

            System.out.println("\nActors Found:");
            for (Actor actor : actors) {
                System.out.println(actor);
            }

            // Step 2: Pick actor by ID
            System.out.print("\nEnter an actor ID to view their films: ");
            int actorId = scanner.nextInt();
            scanner.nextLine(); // flush newline

            List<Film> films = dataManager.getFilmsByActorId(actorId);
            if (films.isEmpty()) {
                System.out.println("No films found for that actor.");
            } else {
                System.out.println("\nFilms:");
                for (Film film : films) {
                    System.out.println(film + "\n");
                }
            }

        } finally {
            try {
                dataManager.close();
            } catch (SQLException e) {
                System.err.println("Error closing DataManager: " + e.getMessage());
            }
        }
    }
}
