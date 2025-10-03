package service;

import entity.Credit;
import entity.Echeance;
import entity.Employe;
import repos.CreditRepo;
import repos.EcheanceRepo;
import util.Session;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class CreditService {

    private CreditRepo creditrepo;
    private EcheanceService er;

    public CreditService() {

        this.creditrepo = new CreditRepo();
        this.er = new EcheanceService();
    }





    public  Credit handleCredit(double amount ,double tauxInteret ,int dureeEnMois){


        int id = ((Employe)Session.getUser()).getId();

        double salaire = ((Employe)Session.getUser()).getSalaire();

        double score = ((Employe)Session.getUser()).getScore();

        boolean   clientExistant = isAncienClient(id);



        Credit c = new Credit(amount ,tauxInteret , dureeEnMois);


        return evaluerCredit(c , salaire ,score ,clientExistant , id);

    }


    public boolean isAncienClient(int personId)   {
        return creditrepo.isAncienClient(personId);
    }


    public boolean hasActiveCredit(int personId)   {
        return creditrepo.hasActiveCredit(personId);
    }

    public Credit evaluerCredit(Credit credit, double salaire, double score, boolean clientExistant , int id) {
        double plafond = 0;

        int anc = checkAncian(id);
        score+=anc;

        if (!clientExistant) {

            if (score < 70) {
                credit.setDecision(Credit.Decision.REFUS_AUTOMATIQUE);
                credit.setMontantOctroye(0);
                return credit;
            } else if (score < 80) {
                credit.setPlafond( 4 * salaire);
                credit.setDecision(Credit.Decision.ETUDE_MANUELLE);
            } else {
                credit.setPlafond( 4 * salaire);
                credit.setDecision(Credit.Decision.ACCORD_IMMEDIAT);

                System.out.println("from ACCORD_IMMEDIAT");
            }
        } else {

            if (score < 60) {
                credit.setDecision(Credit.Decision.REFUS_AUTOMATIQUE);
                credit.setMontantOctroye(0);
                return credit;
            } else if (score < 80) {
                credit.setPlafond( 7 * salaire);
                credit.setDecision(Credit.Decision.ETUDE_MANUELLE);
            } else {
                credit.setPlafond( 10 * salaire);
                credit.setDecision(Credit.Decision.ACCORD_IMMEDIAT);
            }
        }

        return credit;
    }




    public List<Echeance> consulterEcheances(int creditId) {
        List<Echeance> result = new ArrayList<>();
        System.out.println("from credit service before");


            System.out.println("from credit service after");

                List<Echeance> echeances = er.consulterEcheances(creditId);
                result.addAll(echeances);



        return result;
    }


    public void createCridect(Credit c , int id) {



        int res = creditrepo.save(c, id);


        er.createEcheances(res ,c.getMontantOctroye() , c.getDureeEnMois() , c.getTauxInteret() );


    }


    public boolean makePayment(int id){

         return  er.payerEcheance(id);

    }

    public List<Credit> getAllC(int id){


        return creditrepo.findAllByUserId(id);


    }


    public int checkAncian(int personId) {
        LocalDate oldestCreditDate = creditrepo.getOldestCredit(personId);

        if (oldestCreditDate == null) {

            return 0;
        }

        long years = ChronoUnit.YEARS.between(oldestCreditDate, LocalDate.now());

        if (years > 3) {
            return 10;
        } else if (years >= 1) {
            return 8;
        } else {
            return 5;
        }
    }






}





