package repos;

import entity.Person;

import util.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



public class AnalyticsService {

    private  Connection conn ;

    public AnalyticsService() {
        this.conn = DB.getInstance().getConnection();
    }

    public List<Person> getEligibleClientsForCredit() {
        List<Person> result = new ArrayList<>();
        String sql = "SELECT p.* " +
                "FROM Person p " +
                "JOIN Employe e ON p.id = e.person_id " +
                "WHERE e.type_contrat = 'CDI' " +
                "AND p.score > 70 " +
                "AND p.situation_familiale = 'Marié' " +
                "AND DATE_PART('year', AGE(p.date_de_naissance)) BETWEEN 25 AND 50 " +
                "AND e.salaire > 4000";  // adjust if using Professionnel.revenu

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Person p = new Person();
                p.setId(rs.getInt("id"));
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString("prenom"));
                p.setDateDeNaissance(rs.getObject("date_de_naissance", LocalDate.class));
                p.setVille(rs.getString("ville"));
                p.setNombreEnfants(rs.getString("nombre_enfants"));
                p.setInvestissement(rs.getString("investissement"));
                p.setPlacement(rs.getString("placement"));
                p.setSituationFamiliale(rs.getString("situation_familiale"));
                p.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
                p.setScore(rs.getDouble("score"));
                result.add(p);
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des clients éligibles: " + ex.getMessage());
        }

        return result;
    }

    public List<Person> getClientsAtRisk() {
        List<Person> result = new ArrayList<>();

        // Calculate date 6 months ago
        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);

        String sql = "SELECT DISTINCT p.* FROM person p JOIN credit c ON p.id = c.person_idJOIN echeance e ON c.id = e.credit_id" +
          "JOIN incident i ON e.id = i.echeance_id WHERE p.score < 60 AND i.date_incident >= ? ORDER BY p.score DESC"+
           " LIMIT 10= ";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, sixMonthsAgo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Person p = new Person();
                p.setId(rs.getInt("id"));
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString("prenom"));
                p.setDateDeNaissance(rs.getObject("date_de_naissance", LocalDate.class));
                p.setVille(rs.getString("ville"));
                p.setNombreEnfants(rs.getString("nombre_enfants"));
                p.setSituationFamiliale(rs.getString("situation_familiale"));
                p.setScore(rs.getDouble("score"));
                result.add(p);
            }
        } catch (SQLException ex) {
            System.err.println("Erreur fetching clients at risk: " + ex.getMessage());
        }

        return result;
    }

}
