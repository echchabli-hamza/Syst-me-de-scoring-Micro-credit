package service;

import entity.Credit;

public class CreditService {




    public static void evaluerCredit(Credit credit, double salaire, double score, boolean clientExistant) {
        double plafond = 0;

        if (!clientExistant) { // Nouveau client
            plafond = 4 * salaire;
        } else { // Client existant
            if (score > 80) {
                plafond = 10 * salaire;
            } else if (score >= 60) {
                plafond = 7 * salaire;
            } else {
                credit.setDecision(Credit.Decision.REFUS_AUTOMATIQUE);
                credit.setMontantOctroye(0);
                return;
            }
        }

        if (credit.getMontantDemande() <= plafond) {
            credit.setMontantOctroye(credit.getMontantDemande());
            credit.setDecision(Credit.Decision.ACCORD_IMMEDIAT);
        } else {
            credit.setMontantOctroye(plafond);
            credit.setDecision(Credit.Decision.ETUDE_MANUELLE);
        }
    }
}
