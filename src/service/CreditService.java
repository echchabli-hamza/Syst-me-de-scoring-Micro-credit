package service;

import entity.Credit;
import entity.Employe;
import repos.CreditRepo;
import util.Session;

import java.sql.SQLException;

public class CreditService {

    private CreditRepo creditrepo; // assume this is initialized via constructor or DI

    public CreditService() {

        this.creditrepo = new CreditRepo();
    }





    public  Credit handleCredit(double amount ,double tauxInteret ,int dureeEnMois){


        int id = ((Employe)Session.getUser()).getId();

        double salaire = ((Employe)Session.getUser()).getSalaire();

        double score = ((Employe)Session.getUser()).getScore();

        boolean   clientExistant = isAncienClient(id);

        System.out.println("");


        Credit c = new Credit(amount ,tauxInteret , dureeEnMois);


        return evaluerCredit(c , salaire ,score ,clientExistant);

    }


    public boolean isAncienClient(int personId)   {
        return creditrepo.isAncienClient(personId);
    }


    public boolean hasActiveCredit(int personId)   {
        return creditrepo.hasActiveCredit(personId);
    }

    public Credit evaluerCredit(Credit credit, double salaire, double score, boolean clientExistant) {
        double plafond = 0;

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

    public void createCridect(Credit c , int id) {

        creditrepo.save(c, id);


    }




}
