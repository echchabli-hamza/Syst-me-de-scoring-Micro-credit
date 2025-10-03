package service;


import entity.Echeance;
import repos.EcheanceRepo;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EcheanceService {

    private EcheanceRepo echeanceRepo;

    public EcheanceService() {
        this.echeanceRepo = new EcheanceRepo();
    }


    public void createEcheances(int creditId, double amount, int dureeEnMois, double tauxInteret) {



        double total = amount + (amount * tauxInteret / 100.0);


        double mensualite = total / dureeEnMois;

        LocalDate today = LocalDate.now();

        for (int i = 1; i <= dureeEnMois; i++) {
            LocalDate dateEcheance = today.plusMonths(i);

            Echeance e = new Echeance();
            e.setCreditId(creditId);
            e.setDateEcheance(dateEcheance);
            e.setMensualite(mensualite);
            e.setDateDePaiement(null);
            e.setStatutPaiement(null);

            echeanceRepo.save(e);

        }

    }

    public List<Echeance> consulterEcheances(int creditId) {
        System.out.println("from eche service before");

        try {

            System.out.println("from credit service after");

            return echeanceRepo.getAllByCreditId(creditId);


        } catch (SQLException e) {
            System.err.println("Erreur récupération échéances: " + e.getMessage());
            return new ArrayList<>();

        }
    }
    public boolean payerEcheance(int echeanceId) {
        boolean success =false ;
        try {
            Echeance e = echeanceRepo.getById(echeanceId);
            if (e == null) {
                System.out.println("Échéance introuvable.");
                return false;
            }

            if (e.getStatutPaiement() != null) {
                System.out.println("Cette échéance est déjà payée.");
                return false;
            }

            LocalDate today = LocalDate.now();
            LocalDate echeancePlus5 = e.getDateEcheance().plusDays(5);
            LocalDate echeancePlus30 = e.getDateEcheance().plusDays(30);



            if (!today.isAfter(echeancePlus5)) {

                 success = echeanceRepo.updateStatutPaiement(
                        echeanceId,
                        Echeance.StatutPaiement.PAYEATEMPS
                );


            } else if (!today.isAfter(echeancePlus30)) {

                  System.out.println("Today is between date1+5 and date1+30 days");

                 success = echeanceRepo.updateStatutPaiement(
                        echeanceId,
                        Echeance.StatutPaiement.PAYEENRETARD
                );

            } else{

                 success = echeanceRepo.updateStatutPaiement(
                        echeanceId,
                        Echeance.StatutPaiement.IMPAYEREGLE
                );


                  System.out.println("Today is after date1 + 30 days");
            }









        } catch (SQLException ex) {
            System.err.println("Erreur lors du paiement: " + ex.getMessage());
            return false;
        }
        return success ;
    }


    public void updateAllEcheanceStatus() {

        try {
            List<Echeance> allEcheances = echeanceRepo.getAll(); // get all echeances
            LocalDate today = LocalDate.now();

            for (Echeance e : allEcheances) {
                if (e.getStatutPaiement() != null) {
                    continue; // already paid or status set, skip
                }

                LocalDate echeancePlus5 = e.getDateEcheance().plusDays(5);
                LocalDate echeancePlus30 = e.getDateEcheance().plusDays(30);

                if (today.isAfter(echeancePlus30)) {
                    echeanceRepo.updateStatutPaiement(e.getId(), Echeance.StatutPaiement.IMPAYENONREGLE);
                    System.out.println("Échéance " + e.getId() + " : IMPAYENONREGLE");
                } else if (today.isAfter(echeancePlus5)) {
                    echeanceRepo.updateStatutPaiement(e.getId(), Echeance.StatutPaiement.ENRETARD);
                    System.out.println("Échéance " + e.getId() + " : ENRETARD");
                }
                // if none of the conditions match, skip
            }

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la mise à jour des échéances : " + ex.getMessage());
        }
    }


}
