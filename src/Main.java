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






      UserView uv = new UserView();

        uv.showMenu();



    }

}