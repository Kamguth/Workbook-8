/*package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActorDAO {
    private BasicDataSource dataSource;

    public ActorDAO(DataManager dataManager) {
        this.dataSource = dataManager.getDataSource();
    }

    public List<Actor> getActorsByLastName(String lastName) {
        List<Actor> actors = new ArrayList<>();
        String query = "SELECT actor_id, first_name, last_name FROM actor WHERE last_name = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, lastName);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    do {
                        Actor actor = new Actor(
                                rs.getInt("actor_id"),
                                rs.getString("first_name"),
                                rs.getString("last_name")
                        );
                        actors.add(actor);
                    } while (rs.next());
                } else {
                    System.out.println("No actors found with last name: " + lastName);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving actors: " + e.getMessage());
        }

        return actors;
    }

    public List<Film> getFilmsByActorName(String firstName, String lastName) {
        List<Film> films = new ArrayList<>();
        String query = """
                SELECT f.title 
                FROM film f
                JOIN film_actor fa ON f.film_id = fa.film_id
                JOIN actor a ON a.actor_id = fa.actor_id
                WHERE a.first_name = ? AND a.last_name = ?
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    do {
                        films.add(new Film(rs.getString("title")));
                    } while (rs.next());
                } else {
                    System.out.println("No films found for " + firstName + " " + lastName);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving films: " + e.getMessage());
        }

        return films;
    }
}*/
