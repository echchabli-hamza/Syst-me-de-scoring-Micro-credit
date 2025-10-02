import entity.Credit;
import entity.Person;
import entity.Professionnel;
import service.CalculScore;
import service.CreditService;
import util.GetConst;

import util.Session ;

import entity.Employe;
import util.RuleChain;
import view.UserView;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {


        GetConst constants = new GetConst();

        CalculScore cs =new CalculScore();



        Employe e = new Employe(
                "Echchabli",
                "Hamza",
                LocalDate.of(1998, 5, 10),
                "Casablanca",
                "ENFANTS_0",
                "INVEST_PLAC",
                "INVEST_PLAC",
                "CELIB",
                LocalDateTime.now(),
                0.0,
                15000.0,
                "ANCIENNETE_>=5",
                "Développeur",
                "CDD",
                "INTERIM"
        );
//

//
//        Professionnel professionnel = new Professionnel(
//                "Echchabli",
//                "Hamza",
//                LocalDate.of(1995, 3, 22),
//                "Rabat",
//                "ENFANTS_1-2",
//                "INVEST_PLAC",
//                "INVEST_PLAC",
//                "MARI",
//                LocalDateTime.now(),
//                0.0,
//                25000.0,
//                "IF123456789",
//                "PROF_LIBERALE",
//                "Import-Export"
//        );
//
//
//
//
       System.out.println(cs.employeScore(e));



//        Credit credit = new Credit(
//                LocalDate.now(),
//                120000,   // montant demandé
//                4.5,      // taux d’intérêt
//                60,       // durée en mois
//                "IMMOBILIER"
//        );
//
//        double salaire = 150000;
//        double score = 82;
//        boolean clientExistant = true;
//
     // CreditService.evaluerCredit(credit, salaire, score, clientExistant);
//
//        System.out.println(credit);

//
//        UserView uv = new UserView();
//
//        uv.showMenu();



    }

}