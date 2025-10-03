package view;

import entity.Credit;
import entity.Echeance;
import entity.Employe;
import entity.Person;
import service.CreditService;
import service.UserService;
import util.Session;

import java.util.List;
import java.util.Scanner;

public class CreditView {

    private CreditService creditService;
    private Scanner scanner;

    private UserService us;

    public CreditView() {
        this.creditService = new CreditService();
        this.scanner = new Scanner(System.in);

        this.us = new UserService();
    }



    public void showMenu() {
        int choice = -1;

        int exist = 0;

        while (true) {

            System.out.println("enter you id Or -1 to return " );

            int id = scanner.nextInt();

            if (id==-1){return;}


            Person client = us.getClientById(id);

            if (client != null) {


                Session.setUser(client);

                break;

            }

            System.out.println("this id does exist : ");
        }
        while (choice != 0) {
            System.out.println("\n===== MENU CREDIT =====");
            System.out.println("1. Lister mes crédits");
            System.out.println("2. Voir les échéances d’un crédit");
            System.out.println("3. Payer une échéance");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    listUserCredits();
                    break;
                case 2:
                    listEcheancesByCredit();
                    break;
                case 3:
                    makePayment();
                    break;
                case 0:
                    System.out.println("Retour au menu principal...");
                    break;
                default:
                    System.out.println("Choix invalide !");
                    break;
            }
        }
    }


    public void listUserCredits() {
        Employe user = (Employe) Session.getUser();
        if (user == null) {
            System.out.println("Aucun utilisateur connecté !");
            return;
        }

        try {
            List<Credit> credits = creditService.getAllC(user.getId());
            if (credits.isEmpty()) {
                System.out.println("Vous n'avez aucun crédit.");
            } else {
                System.out.println("Vos crédits :");
                for (Credit c : credits) {
                    System.out.println("ID: " + c.getId() +
                            " | Status de credit: " + (c.getStatus() ? "paid" : "not paid" )+
                            " | Montant octroyé: " + c.getMontantOctroye() +
                            " | Décision: " + c.getDecision() +
                            " | Durée: " + c.getDureeEnMois() + " mois");
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des crédits: " + e.getMessage());
        }
    }

    public void listEcheancesByCredit() {
        System.out.print("Entrez l'ID du crédit : ");
        int creditId = scanner.nextInt();

        List<Echeance> echeances = creditService.consulterEcheances(creditId);
        if (echeances.isEmpty()) {
            System.out.println("Aucune échéance trouvée pour ce crédit.");
        } else {
            System.out.println("Échéances du crédit " + creditId + " :");
            for (Echeance e : echeances) {
                System.out.println("Échéance ID: " + e.getId() +
                        " | Date: " + e.getDateEcheance() +
                        " | Mensualité: " + e.getMensualite() +
                        " | Statut: " + (e.getStatutPaiement() != null ? e.getStatutPaiement() : "NON PAYÉE"));
            }
        }
    }


    public void makePayment() {
        System.out.print("Entrez l'ID de l'échéance à payer : ");
        int echeanceId = scanner.nextInt();

        boolean success = creditService.makePayment(echeanceId);
        if (success) {
            System.out.println("Paiement effectué avec succès !");
        } else {
            System.out.println("Échec du paiement. Vérifiez l'ID ou le statut de l'échéance.");
        }
    }
}
