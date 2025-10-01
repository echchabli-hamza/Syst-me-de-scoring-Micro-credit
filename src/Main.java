import entity.Person;
import service.CalculScore;
import util.GetConst;

import entity.Employe;
import util.RuleChain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {


        GetConst constants = new GetConst();

        CalculScore cs =new CalculScore();

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


//       GetConst.chain.printRules();


        Employe e = new Employe(
                "Echchabli",               // nom
                "Hamza",                   // prenom
                LocalDate.of(1998, 5, 10), // dateDeNaissance
                "Casablanca",              // ville
                "ENFANTS_0",                         // nombreEnfants
                "INVEST_PLAC",                   // investissement
                "INVEST_PLAC",                    // placement
                "CELIB",             // situationFamiliale
                LocalDateTime.now(),       // createdAt
                0.0,                      // score
                15000.0,                   // salaire
                "ANCIENNETE_>=5",                         // anciennete (years)
                "Développeur",             // poste
                "CDI",                     // typeContrat
                "PUBLIC"             // secteur
        );

        cs.index(e);



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