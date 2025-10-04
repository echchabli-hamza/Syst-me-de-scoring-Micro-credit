package repos;

import entity.Incident;
import entity.TypeIncident;
import service.UserService;
import util.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class IncidentRepo {

    private Connection conn;
    private UserService ud;

    public IncidentRepo() {
        this.conn = DB.getInstance().getConnection();
        this.ud = new UserService();
    }

    public void create(Incident incident) {
        String sql = "INSERT INTO incident (date_incident, echeance_id, score, type_incident) " +
                "VALUES (?, ?, ?, ?) " +
                "ON CONFLICT (echeance_id, type_incident) DO NOTHING";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, Date.valueOf(incident.getDateIncident()));
            stmt.setInt(2, incident.getEcheanceId());
            stmt.setInt(3, incident.getScore());
            stmt.setString(4, incident.getTypeIncident().name());

            int affected = stmt.executeUpdate();

            if (affected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {

                    int id = rs.getInt(1);

                    ud.updateUserScore(incident );


                }
            }
        } catch (SQLException e) {
            System.err.println( e.getMessage());
        }

    }


    public List<Incident> getAll() {
        List<Incident> incidents = new ArrayList<>();
        String sql = "SELECT * FROM incident";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Incident incident = new Incident();
                incident.setId(rs.getInt("id"));
                incident.setDateIncident(rs.getDate("date_incident").toLocalDate());
                incident.setEcheanceId(rs.getInt("echeance_id"));
                incident.setScore(rs.getInt("score"));
                incident.setTypeIncident(TypeIncident.valueOf(rs.getString("type_incident")));
                incidents.add(incident);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return incidents;
    }

    public void updateTypeById(int id, TypeIncident newType, int newScore) {
        String sql = "UPDATE incident SET type_incident = ?, score = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newType.name());
            stmt.setInt(2, newScore);
            stmt.setInt(3, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Incident findById(int id) {
        String sql = "SELECT * FROM incident WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Incident incident = new Incident();
                incident.setId(rs.getInt("id"));
                incident.setDateIncident(rs.getDate("date_incident").toLocalDate());
                incident.setEcheanceId(rs.getInt("echeance_id"));
                incident.setScore(rs.getInt("score"));
                incident.setTypeIncident(TypeIncident.valueOf(rs.getString("type_incident")));
                return incident;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM incident WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Incident> getByUserId(int userId) {
        List<Incident> list = new ArrayList<>();
        String sql = "SELECT i.* FROM incident i " +
                "JOIN echeance e ON i.echeance_id = e.id " +
                "JOIN credit c ON e.credit_id = c.id " +
                "WHERE c.person_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Incident incident = new Incident();
                incident.setId(rs.getInt("id"));
                incident.setDateIncident(rs.getDate("date_incident").toLocalDate());
                incident.setEcheanceId(rs.getInt("echeance_id"));
                incident.setTypeIncident(TypeIncident.valueOf(rs.getString("type_incident")));
                incident.setScore(rs.getInt("score"));
                list.add(incident);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching incidents by userId: " + e.getMessage());
        }
        return list;
    }
}
