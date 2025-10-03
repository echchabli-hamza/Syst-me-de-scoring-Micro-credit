package service;

import entity.Credit;
import entity.Echeance;
import entity.Employe;
import repos.CreditRepo;
import repos.EcheanceRepo;
import util.Session;

import java.sql.SQLException;
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



    public List<Echeance> consulterEcheancesParUser(int userId) {
        List<Echeance> result = new ArrayList<>();
        try {

            Credit cr = creditrepo.findActiveById(userId);


                List<Echeance> echeances = er.consulterEcheancesParCredit(cr.getId());
                result.addAll(echeances);


        } catch (SQLException ex) {
            System.err.println("Erreur lors de la consultation des échéances: " + ex.getMessage());
        }
        return result;
    }


    public void createCridect(Credit c , int id) {



        int res = creditrepo.save(c, id);


        er.createEcheances(res ,c.getMontantOctroye() , c.getDureeEnMois() , c.getTauxInteret() );


    }




    }





