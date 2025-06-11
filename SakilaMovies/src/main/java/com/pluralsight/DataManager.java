package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private final BasicDataSource dataSource;

    public DataManager(String url, String username, String password) {
        this.dataSource = new BasicDataSource();
        this.dataSource.setUrl(url);
        this.dataSource.setUsername(username);
        this.dataSource.setPassword(password);
    }

    public List<Actor> searchActorsByName(String name) {
        List<Actor> actors = new ArrayList<>();
        String query = "SELECT actor_id, first_name, last_name FROM actor " +
                "WHERE first_name LIKE ? OR last_name LIKE ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            String pattern = "%" + name + "%";
            stmt.setString(1, pattern);
            stmt.setString(2, pattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    actors.add(new Actor(
                            rs.getInt("actor_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching actors: " + e.getMessage());
        }

        return actors;
    }

    public List<Film> getFilmsByActorId(int actorId) {
        List<Film> films = new ArrayList<>();
        String query = """
            SELECT f.film_id, f.title, f.description, f.release_year, f.length
            FROM film f
            JOIN film_actor fa ON f.film_id = fa.film_id
            WHERE fa.actor_id = ?
            """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, actorId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    films.add(new Film(
                            rs.getInt("film_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getInt("release_year"),
                            rs.getInt("length")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving films: " + e.getMessage());
        }

        return films;
    }

    public void close() throws SQLException {
        if (dataSource != null) {
            dataSource.close();
        }
    }

    public BasicDataSource getDataSource() {
        return dataSource;
    }
}
