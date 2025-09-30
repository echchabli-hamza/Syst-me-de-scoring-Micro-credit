package repos;

import util.DB;
import entity.Person;
import entity.Employe;
import entity.Professionnel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private Connection conn = DB.getInstance().getConnection();


    public void createClient(Person client) {
        String sqlPerson = "INSERT INTO Person " +
                "(nom, prenom, date_de_naissance, ville, nombre_enfants, investissement, placement, situation_familiale, created_at, score) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";

        try (PreparedStatement ps = conn.prepareStatement(sqlPerson)) {
            ps.setString(1, client.getNom());
            ps.setString(2, client.getPrenom());
            ps.setDate(3, Date.valueOf(client.getDateDeNaissance()));
            ps.setString(4, client.getVille());
            ps.setInt(5, client.getNombreEnfants());
            ps.setDouble(6, client.getInvestissement());
            ps.setDouble(7, client.getPlacement());
            ps.setString(8, client.getSituationFamiliale());
            ps.setTimestamp(9, Timestamp.valueOf(client.getCreatedAt()));
            ps.setDouble(10, client.getScore());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int PersonId = rs.getInt("id");

                // If the client is an Employe
                if (client instanceof Employe) {
                    Employe e = (Employe) client;
                    String sqlEmp = "INSERT INTO employe (Person_id, salaire, anciennete, poste, type_contrat, secteur) VALUES (?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement psEmp = conn.prepareStatement(sqlEmp)) {
                        psEmp.setInt(1, PersonId);
                        psEmp.setDouble(2, e.getSalaire());
                        psEmp.setInt(3, e.getAnciennete());
                        psEmp.setString(4, e.getPoste());
                        psEmp.setString(5, e.getTypeContrat());
                        psEmp.setString(6, e.getSecteur());
                        psEmp.executeUpdate();
                    }
                }

                // If the client is a Professionnel
                if (client instanceof Professionnel) {
                    Professionnel p = (Professionnel) client;
                    String sqlPro = "INSERT INTO professionnel (Person_id, revenu, immatriculation_fiscale, secteur_activite, activite) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement psPro = conn.prepareStatement(sqlPro)) {
                        psPro.setInt(1, PersonId);
                        psPro.setDouble(2, p.getRevenu());
                        psPro.setString(3, p.getImmatriculationFiscale());
                        psPro.setString(4, p.getSecteurActivite());
                        psPro.setString(5, p.getActivite());
                        psPro.executeUpdate();
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public void updateClient(Person client) {
        String sql = "UPDATE Person SET nom=?, prenom=?, date_de_naissance=?, ville=?, nombre_enfants=?, " +
                "investissement=?, placement=?, situation_familiale=?, score=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, client.getNom());
            ps.setString(2, client.getPrenom());
            ps.setDate(3, Date.valueOf(client.getDateDeNaissance()));
            ps.setString(4, client.getVille());
            ps.setInt(5, client.getNombreEnfants());
            ps.setDouble(6, client.getInvestissement());
            ps.setDouble(7, client.getPlacement());
            ps.setString(8, client.getSituationFamiliale());
            ps.setDouble(9, client.getScore());
            ps.setInt(10, client.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Person getClient(int id) {
        String sql = "SELECT * FROM Person WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // return Person object (or you can JOIN to get Employe/Professionnel)
                Person p = new Person(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getDate("date_de_naissance").toLocalDate(),
                        rs.getString("ville"),
                        rs.getInt("nombre_enfants"),
                        rs.getDouble("investissement"),
                        rs.getDouble("placement"),
                        rs.getString("situation_familiale"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getDouble("score")
                ) {};
                p.setId(id);
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteClient(int id) {
        String sql = "DELETE FROM Person WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Person> listClients() {
        List<Person> clients = new ArrayList<>();
        String sql = "SELECT * FROM Person";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Person p = new Person(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getDate("date_de_naissance").toLocalDate(),
                        rs.getString("ville"),
                        rs.getInt("nombre_enfants"),
                        rs.getDouble("investissement"),
                        rs.getDouble("placement"),
                        rs.getString("situation_familiale"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getDouble("score")
                ) {};
                p.setId(rs.getInt("id"));
                clients.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }
}
