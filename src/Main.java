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
      UserView uv = new UserView();
//
        uv.showMenu();



    }

}