import entity.Credit;
import entity.Person;
import entity.Professionnel;
import service.CalculScore;
import service.CreditService;
import service.EcheanceService;
import service.IncidentService;
import util.GetConst;

import util.Scheduler;
import util.Session ;

import entity.Employe;
import util.RuleChain;
import view.UserView;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {


        Scheduler scheduler = new Scheduler();
        scheduler.start();



//        GetConst constants = new GetConst();
//
//        CalculScore cs =new CalculScore();
//
//
//
//
//
//
     UserView uv = new UserView();

        uv.showMenu();

//        IncidentService is = new IncidentService();
//
//        is.generateIncidentsFromEcheances();





    }

}