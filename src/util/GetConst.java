package util;

import java.sql.*;
import java.util.*;

public class GetConst {

    private static Connection conn = DB.getInstance().getConnection();

    public static RuleChain chain = new RuleChain();

    public GetConst() {
        loadPathRules();
        loadRangeRules();
    }

    // --------------------- Path rules ---------------------
    private void loadPathRules() {
        String sql = "SELECT * FROM path_rules";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String category = rs.getString("category");
                String subCategory = rs.getString("sub_category"); // optional
                String value = rs.getString("value");
                int points = rs.getInt("points");

                if (subCategory != null && !subCategory.isEmpty()) {
                    chain.addRule(points, category, subCategory, value);
                } else {
                    chain.addRule(points, category, value);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching path rules: " + e.getMessage());
        }
    }

    // --------------------- Range rules ---------------------
    private void loadRangeRules() {
        String sql = "SELECT * FROM range_rules";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Double minValue = rs.getObject("min_value") != null ? rs.getDouble("min_value") : 0;
                Double maxValue = rs.getObject("max_value") != null ? rs.getDouble("max_value") : null;
                int points = rs.getInt("points");

                chain.addRangeRule(points, minValue, maxValue);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching range rules: " + e.getMessage());
        }
    }
}
