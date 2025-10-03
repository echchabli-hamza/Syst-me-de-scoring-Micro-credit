package repos;

import java.sql.*;

import entity.Credit;
import util.DB;

public class CreditRepo {

    private Connection conn;

    public CreditRepo() {
        this.conn = DB.getInstance().getConnection();
    }

    public int save(Credit credit , int id)   {
        String sql = "INSERT INTO Credit (person_id, date_de_credit, montant_demande, montant_octroye, taux_interet, duree_en_mois, type_credit, decision, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS)) {
            stmt.setObject(1, id);
            stmt.setObject(2, credit.getDateDeCredit());
            stmt.setDouble(3, credit.getMontantDemande());
            stmt.setDouble(4, credit.getMontantOctroye());
            stmt.setDouble(5, credit.getTauxInteret());
            stmt.setInt(6, credit.getDureeEnMois());
            stmt.setString(7, credit.getTypeCredit());
            stmt.setString(8, credit.getDecision() != null ? credit.getDecision().toString() : null);
            stmt.setBoolean(9,false );
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // return generated Credit.id
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());

        }
        return 0;
    }

    public Credit findById(Long id) throws SQLException {
        String sql = "SELECT * FROM Credit WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Credit c = new Credit();
                c.setDateDeCredit(rs.getObject("date_de_credit", java.time.LocalDate.class));
                c.setMontantDemande(rs.getDouble("montant_demande"));
                c.setMontantOctroye(rs.getDouble("montant_octroye"));
                c.setTauxInteret(rs.getDouble("taux_interet"));
                c.setDureeEnMois(rs.getInt("duree_en_mois"));
                c.setTypeCredit(rs.getString("type_credit"));
                c.setDecision(rs.getString("decision") != null ? Credit.Decision.valueOf(rs.getString("decision")) : null);
                c.setStatus(rs.getBoolean("status"));
                return c;
            }
        }
        return null;
    }


    public boolean hasActiveCredit(int personId) {
        String sql = "SELECT 1 FROM Credit WHERE person_id = ? AND status = false LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, personId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isAncienClient(int personId){
        String sql = "SELECT 1 FROM Credit WHERE person_id = ? LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, personId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
