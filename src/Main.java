import entity.Person;
import entity.Professionnel;
import repos.UserDao;
import util.GetConst;

import entity.Employe;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {


        GetConst constants = new GetConst();

//        UserDao uD =new UserDao();
//
//
//
//        uD.deleteClient(1);
//
//
//        for (Person e : uD.listClients()){
//
//            System.out.println(e.toString());
//
//        }



        System.out.println("=== CATEGORIE REGLE ===");
        GetConst.categorieRegle.forEach((categorie, map) -> {
            System.out.println("Categorie: " + categorie);
            map.forEach((cle, points) -> {
                System.out.println("  " + cle + " -> " + points + " pts");
            });
        });

        System.out.println("\n=== REGLES NUMERIQUES ===");
        GetConst.regles.forEach((categorie, map) -> {
            System.out.println("Categorie: " + categorie);
            map.forEach((valeur, points) -> {
                System.out.println("  " + valeur + " -> " + points + " pts");
            });
        });




    }





//
//        Professionnel pro = new Professionnel(
//                "El Amrani",                // nom
//                "Youssef",                  // prenom
//                LocalDate.of(1990, 3, 12),  // dateNaissance
//                "Rabat",                    // ville
//                2,                          // nombreEnfants
//                20000.0,                    // investissement
//                15000.0,                    // placement
//                "Marié",                    // situation_familiale
//                LocalDateTime.now(),        // createdAt
//                0,                          // score (initial)
//                12000.0,                    // revenu
//                "IF123456",                 // immatriculation fiscale
//                "Commerce",                 // secteur activité
//                "Boutiquier"                // activité
//        );








//        uD.createClient(
//                 new Employe(
//                        "Dupont",                  // nom
//                        "Jean",                    // prenom
//                        LocalDate.of(1985, 3, 15), // dateNaissance
//                        "Paris",                   // ville
//                        2,                         // nombreEnfants
//                        5000.0,                    // investissement
//                        3000.0,                    // placement
//                        "Marié",                   // situationFamiliale
//                        LocalDateTime.now(),       // createdAt
//                        85.5,                      // score
//                        3500.0,                    // salaire
//                        10,                        // anciennete
//                        "Développeur",             // poste
//                        "CDI",                     // typeContrat
//                        "Grande_Entreprise"        // secteur
//                )
//        );




}