package service;


import entity.Echeance;
import repos.EcheanceRepo;

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
            e.setDateDePaiement(null); // not paid yet
            e.setStatutPaiement(null);

            echeanceRepo.save(e);

        }

    }

}
