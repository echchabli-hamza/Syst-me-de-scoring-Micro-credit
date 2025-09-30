package util;

import java.sql.*;
import java.util.*;

public class GetConst {

    private static Connection conn = DB.getInstance().getConnection();

    public static HashMap<String, HashMap<String, Integer>> categorieRegle = new HashMap<>();
    public static HashMap<String, HashMap<Double, Integer>> regles = new HashMap<>();

    public GetConst() {
        getReglesCategorique();
        getRegles();
    }

    private void getReglesCategorique() {
        String sql = "SELECT * FROM regles_categorie";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String categorie = rs.getString("categorie");
                String cle = rs.getString("cle");
                int points = rs.getInt("points");

                // computeIfAbsent creates inner map if missing
                categorieRegle.computeIfAbsent(categorie, k -> new HashMap<>())
                        .put(cle, points);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching categorical rules: " + e.getMessage());
        }
    }

    private void getRegles() {
        String sql = "SELECT * FROM regles";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String categorie = rs.getString("categorie");
                Double valeurMin = rs.getObject("valeur_min") != null ? rs.getDouble("valeur_min") : null;
                Integer points = rs.getInt("points");

                // Use Double keys even if nullable
                regles.computeIfAbsent(categorie, k -> new HashMap<>())
                        .put(valeurMin, points);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching rules: " + e.getMessage());
        }
    }
}
