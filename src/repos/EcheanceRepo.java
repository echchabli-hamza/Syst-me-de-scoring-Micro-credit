package repos;


import entity.Echeance;
import util.DB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EcheanceRepo {

    private Connection conn;

    public EcheanceRepo() {
        this.conn = DB.getInstance().getConnection();
    }



    public boolean save(Echeance e) {
        String sql = "INSERT INTO Echeance (credit_id, date_echeance, mensualite, date_de_paiement, statut_paiement) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, e.getCreditId());
            stmt.setDate(2, Date.valueOf(e.getDateEcheance()));
            stmt.setDouble(3, e.getMensualite());
            stmt.setNull(4, Types.DATE);
            stmt.setString(5, null);

            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error saving echeance: " + ex.getMessage());
            return false;
        }
    }


    public Echeance getById(int id) throws SQLException {
        String sql = "SELECT * FROM Echeance WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Echeance e = new Echeance();
                e.setId(rs.getInt("id"));
                e.setCreditId(rs.getInt("credit_id"));
                e.setDateEcheance(rs.getDate("date_echeance").toLocalDate());
                e.setMensualite(rs.getDouble("mensualite"));
                Date datePaiement = rs.getDate("date_de_paiement");
                if (datePaiement != null) e.setDateDePaiement(datePaiement.toLocalDate());

                e.setStatutPaiement(
                        rs.getString("statut_paiement") != null
                                ? Echeance.StatutPaiement.valueOf(rs.getString("statut_paiement"))
                                : null
                );
                return e;
            }
            return null;
        }
    }


    public List<Echeance> getAllByCreditId(int creditId) throws SQLException {
        List<Echeance> list = new ArrayList<>(); System.out.println( "from aerga3");
        String sql = "SELECT * FROM Echeance WHERE credit_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, creditId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Echeance e = new Echeance();
                e.setId(rs.getInt("id"));
                e.setCreditId(rs.getInt("credit_id"));
                e.setDateEcheance(rs.getDate("date_echeance").toLocalDate());
                e.setMensualite(rs.getDouble("mensualite"));
                Date datePaiement = rs.getDate("date_de_paiement");
                if (datePaiement != null) e.setDateDePaiement(datePaiement.toLocalDate());

                e.setStatutPaiement(
                        rs.getString("statut_paiement") != null
                                ? Echeance.StatutPaiement.valueOf(rs.getString("statut_paiement"))
                                : null
                );

                list.add(e);


            }
        }
        return list;
    }

    public boolean updateStatutPaiement(int echeanceId, Echeance.StatutPaiement newStatut) throws SQLException {
        String sql = "UPDATE Echeance SET statut_paiement = ? , date_de_paiement = ? WHERE id = ? ";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatut.name());
            stmt.setDate(2,Date.valueOf( LocalDate.now()));
            stmt.setInt(3, echeanceId);
            return stmt.executeUpdate() > 0;
        }
    }



    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM Echeance WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
