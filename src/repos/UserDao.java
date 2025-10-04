package repos;

import util.DB;
import entity.Person;
import entity.Employe;
import entity.Professionnel;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDao {

    private Connection conn = DB.getInstance().getConnection();


    public boolean createClient(Person client) {
        String sqlPerson = "INSERT INTO Person " +
                "(nom, prenom, date_de_naissance, ville, nombre_enfants, investissement, placement, situation_familiale, created_at, score) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sqlPerson, Statement.RETURN_GENERATED_KEYS) ){
            ps.setString(1, client.getNom());
            ps.setString(2, client.getPrenom());
            System.out.println(client.getDateDeNaissance());
            System.out.println(Date.valueOf(client.getDateDeNaissance()));
            ps.setDate(3, Date.valueOf(client.getDateDeNaissance()));
            ps.setString(4, client.getVille());
            ps.setString(5, client.getNombreEnfants());
            ps.setString(6, client.getInvestissement());
            ps.setString(7, client.getPlacement());
            ps.setString(8, client.getSituationFamiliale());
            ps.setTimestamp(9, Timestamp.valueOf(client.getCreatedAt()));
            ps.setDouble(10, client.getScore());
            ps.executeUpdate();
            ResultSet id =ps.getGeneratedKeys();
            int PersonId = 0;
            if (id.next()) {

             PersonId = id.getInt(1);
            }
            System.out.println("person id"  +  PersonId);

                if (client instanceof Employe) {
                    Employe e = (Employe) client;
                    String sqlEmp = "INSERT INTO employe (Person_id, salaire, anciennete, poste, type_contrat, secteur) VALUES (?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement psEmp = conn.prepareStatement(sqlEmp)) {
                        psEmp.setInt(1, PersonId);
                        psEmp.setDouble(2, e.getSalaire());
                       psEmp.setString(3, e.getAnciennete());
                        psEmp.setString(4, e.getPoste());
                        psEmp.setString(5, e.getTypeContrat());
                        psEmp.setString(6, e.getSecteur());
                        return psEmp.executeUpdate()>0;
                    }
                }

                if (client instanceof Professionnel) {
                    Professionnel p = (Professionnel) client;
                    String sqlPro = "INSERT INTO professionnel (Person_id, revenu, immatriculation_fiscale, secteur_activite, activite) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement psPro = conn.prepareStatement(sqlPro)) {
                        psPro.setInt(1, PersonId);
                        psPro.setDouble(2, p.getSalaire());
                        psPro.setString(3, p.getImmatriculationFiscale());
                        psPro.setString(4, p.getSecteurActivite());
                        psPro.setString(5, p.getActivite());
                        return  psPro.executeUpdate()>0;
                    }
                }


        } catch (SQLException e) {
            e.printStackTrace();

        }

        return false ;
    }


    public Person getClient(int id) {
        try {

            String sqlPerson = "SELECT * FROM Person WHERE id = ?";

            String nom = null, prenom = null, ville = null, nombreEnfants = null;
            String investissement = null, placement = null, situationFamiliale = null;
            LocalDate dateDeNaissance = null;
            LocalDateTime createdAt = null;
            double score = 0.0;

            Person e;


            try (PreparedStatement ps = conn.prepareStatement(sqlPerson)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                System.out.println("before");
                if (rs.next()) {
                    System.out.println("hiif,after");

                    nom = rs.getString("nom");
                    prenom = rs.getString("prenom");
                    dateDeNaissance = rs.getDate("date_de_naissance").toLocalDate();
                    ville = rs.getString("ville");
                    nombreEnfants = rs.getString("nombre_enfants");
                    investissement = rs.getString("investissement");
                    placement = rs.getString("placement");
                    situationFamiliale = rs.getString("situation_familiale");
                    createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                    score = rs.getDouble("score");
                } else {
                    return null ;
                }
            }

            String sqlEmp = "SELECT * FROM employe WHERE Person_id = ?";
            try (PreparedStatement psEmp = conn.prepareStatement(sqlEmp)) {
                psEmp.setInt(1, id);
                ResultSet rsEmp = psEmp.executeQuery();
                if (rsEmp.next()) {
                     e = new Employe(
                            nom,
                            prenom,
                            dateDeNaissance,
                            ville,
                            nombreEnfants,
                            investissement,
                            placement,
                            situationFamiliale,
                            createdAt,
                            score,
                            rsEmp.getDouble("salaire"),
                            rsEmp.getString("anciennete"),
                            rsEmp.getString("poste"),
                            rsEmp.getString("type_contrat"),
                            rsEmp.getString("secteur")
                    );
                    e.setId(id);
                    return e;
                }
            }

            String sqlPro = "SELECT * FROM professionnel WHERE Person_id = ?";
            try (PreparedStatement psPro = conn.prepareStatement(sqlPro)) {
                psPro.setInt(1, id);
                ResultSet rsPro = psPro.executeQuery();
                if (rsPro.next()) {
                     e = new Professionnel(
                            nom,
                            prenom,
                            dateDeNaissance,
                            ville,
                            nombreEnfants,
                            investissement,
                            placement,
                            situationFamiliale,
                            createdAt,
                            score,
                            rsPro.getDouble("revenu"),
                            rsPro.getString("immatriculation_fiscale"),
                            rsPro.getString("secteur_activite"),
                            rsPro.getString("activite")
                    );
                    e.setId(id);
                    return e;
                }
            }


            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
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
                        rs.getString("nombre_enfants"),
                        rs.getString("investissement"),
                        rs.getString("placement"),
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

    public List<Integer> getUserIdByIncident(int Id) {
        List<Integer> result = new ArrayList<>();

        String sql = "SELECT p.id AS person_id, p.score FROM Person p JOIN Credit c ON p.id = c.person_id JOIN Echeance e ON c.id = e.credit_id WHERE e.id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                 result.add(rs.getInt("person_id"));
                result.add(rs.getInt("score"));

                return result ;
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'ID utilisateur par incident: " + e.getMessage());
        }
        return null;
    }

    public void updateScore(int userId, double newScore) {
        String sql = "UPDATE person SET score = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, newScore);
            stmt.setInt(2, userId);
            boolean r = stmt.executeUpdate() > 0 ;

        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du score: " + e.getMessage());

        }
    }


}
