/*package com.example.swen2_ss2024.database;


import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class Database {
    public static Connection connect() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "postgres";

        return DriverManager.getConnection(url, user, password);
    }

    public static int saveTour(Tour tour) throws SQLException {
        String checkSql = "SELECT * FROM Tour WHERE \"name\" = ? AND \"description\" = ? AND \"from\" = ? AND \"to\" = ? AND \"transportType\" = ? AND \"distance\" = ? AND \"estimatedTime\" = ? AND \"imagePath\" = ?";

        try (Connection conn = Database.connect();
             PreparedStatement checkPstmt = conn.prepareStatement(checkSql)) {

            checkPstmt.setString(1, tour.getName());
            checkPstmt.setString(2, tour.getDescription());
            checkPstmt.setString(3, tour.getFrom());
            checkPstmt.setString(4, tour.getTo());
            checkPstmt.setString(5, tour.getTransportType());
            checkPstmt.setString(6, tour.getDistance());
            checkPstmt.setString(7, tour.getEstimatedTime());
            checkPstmt.setString(8, tour.getImagePath());

            ResultSet rs = checkPstmt.executeQuery();

            if (rs.next()) {
                // A tour with the same details already exists in the database, so return its id
                return rs.getInt("id");
            } else {
                // No tour with the same details exists in the database, so insert the new tour
                String insertSql = "INSERT INTO Tour (\"name\", \"description\", \"from\", \"to\", \"transportType\", \"distance\", \"estimatedTime\", \"imagePath\") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement insertPstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {

                    insertPstmt.setString(1, tour.getName());
                    insertPstmt.setString(2, tour.getDescription());
                    insertPstmt.setString(3, tour.getFrom());
                    insertPstmt.setString(4, tour.getTo());
                    insertPstmt.setString(5, tour.getTransportType());
                    insertPstmt.setString(6, tour.getDistance());
                    insertPstmt.setString(7, tour.getEstimatedTime());
                    insertPstmt.setString(8, tour.getImagePath());

                    int affectedRows = insertPstmt.executeUpdate();

                    if (affectedRows > 0) {
                        try (ResultSet rs2 = insertPstmt.getGeneratedKeys()) {
                            if (rs2.next()) {
                                return rs2.getInt(1);
                            }
                        }
                    }
                }
            }
        }

        return -1; // Return -1 if the tour was not added to the database
    }

    public static void createTourTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS tour (" +
                "id SERIAL PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "description TEXT," +
                "\"from\" TEXT," +
                "\"to\" TEXT," +
                "\"transportType\" TEXT," +
                "distance TEXT," +
                "\"estimatedTime\" TEXT," +
                "\"imagePath\" TEXT" +
                ")";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.executeUpdate();
        }
    }

    public static void updateTour(Tour tour) throws SQLException {
        String sql = "UPDATE Tour SET name = ?, description = ?, \"from\" = ?, \"to\" = ?, \"transportType\" = ?, \"distance\" = ?, \"estimatedTime\" = ?, \"imagePath\" = ? WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tour.getName());
            pstmt.setString(2, tour.getDescription());
            pstmt.setString(3, tour.getFrom());
            pstmt.setString(4, tour.getTo());
            pstmt.setString(5, tour.getTransportType());
            pstmt.setString(6, tour.getDistance());
            pstmt.setString(7, tour.getEstimatedTime());
            pstmt.setString(8, tour.getImagePath());
            pstmt.setInt(9, tour.getId());

            pstmt.executeUpdate();
        }
    }


    public static Tour getTour(int id) throws SQLException {
        String sql = "SELECT * FROM Tour WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Tour tour = new Tour(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("from"),
                        rs.getString("to"),
                        rs.getString("transportType"),
                        rs.getString("distance"),
                        rs.getString("estimatedTime"),
                        rs.getString("imagePath")
                );
                tour.setId(rs.getInt("id")); // Set the id after creating the Tour object
                return tour;
            }
        }

        return null; // Return null if no tour was found with the given id
    }

    public static Set<Tour> getAllTours() throws SQLException {
        Set<Tour> tours = new HashSet<>();
        String sql = "SELECT * FROM Tour";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Tour tour = new Tour(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("from"),
                        rs.getString("to"),
                        rs.getString("transportType"),
                        rs.getString("distance"),
                        rs.getString("estimatedTime"),
                        rs.getString("imagePath")
                );
                tour.setId(rs.getInt("id"));
                tours.add(tour);
            }
        }

        return tours;
    }

    public static boolean deleteTourById(int id) throws SQLException {
        String sql = "DELETE FROM Tour WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();

            return affectedRows > 0;
        }
    }

    public static Tour getTourByName(String name) throws SQLException {
        String sql = "SELECT * FROM Tour WHERE name = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Tour tour = new Tour(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("from"),
                        rs.getString("to"),
                        rs.getString("transportType"),
                        rs.getString("distance"),
                        rs.getString("estimatedTime"),
                        rs.getString("imagePath")
                );
                tour.setId(rs.getInt("id"));
                return tour;
            }
        }

        return null;
    }
}*/
