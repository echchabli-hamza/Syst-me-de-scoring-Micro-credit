package view;

import entity.Credit;
import service.CalculScore;
import service.CreditService;
import service.UserService;
import entity.Person;
import entity.Employe;
import entity.Professionnel;
import util.Session;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class UserView {

    private UserService userService = new UserService();
    private CreditService creditS = new CreditService();
    private CalculScore cs = new CalculScore();
    private Scanner scanner = new Scanner(System.in);

    public void showMenu() {
        int choice = -1;
        while (choice != 0) {
            System.out.println("\n===== User Menu =====");
            System.out.println("1. Create Account");
            System.out.println("2. show user");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    createAccount();
                    break;

                case 2 :
                   getCridet() ;break;

                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }

    private void createAccount() {

        System.out.println("Choose account type: ");
        System.out.println("1. Employé");
        System.out.println("2. Prof");

        int type = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter first name: ");
        String prenom = scanner.nextLine();
        System.out.print("Enter last name: ");
        String nom = scanner.nextLine();
        System.out.print("Enter city: ");
        String ville = scanner.nextLine();


        System.out.print("Enter date of birth (yyyy-MM-dd): ");
        String dobInput = scanner.nextLine();
        LocalDate dateNaissance = LocalDate.parse(dobInput);


        System.out.println("Choose number of children: ");
        System.out.println("1. 0 enfant");
        System.out.println("2. 1-2 enfants");
        System.out.println("3. >2 enfants");
        int childChoice = scanner.nextInt();
        scanner.nextLine();

        String nombreEnfants = "";
        switch (childChoice) {
            case 1:
                nombreEnfants = "ENFANTS_0";
                break;
            case 2:
                nombreEnfants = "ENFANTS_1-2";
                break;
            case 3:
                nombreEnfants = "ENFANTS_>2";
                break;
            default:
                nombreEnfants = "ENFANTS_0";
                System.out.println("Invalid choice, set to 0 enfant.");
        }


        System.out.println("1. Aucun placement");
        System.out.println("2.  investissement");

        System.out.println("oui ou non");


        String anvit = scanner.nextLine();

        String investissement = "";
        String placement = "";


        if (anvit.equals("oui")) {

            investissement = "INVEST_PLAC";
            placement = "INVEST_PLAC";
        }

        System.out.print("Est-ce que la personne est mariée ? (oui/non) : ");
        String input = scanner.nextLine().trim().toLowerCase();

        String situationFamiliale = "Célibataire";


        if (input.equals("oui")) {
            situationFamiliale = "Marié";
        }

        // employee data

        if(type==1){
            System.out.print("Entrez le salaire : ");
            double salaire = 0;
            while (true) {
                try {
                    salaire = Double.parseDouble(scanner.nextLine());
                    if (salaire < 0) {
                        System.out.print("Le salaire doit être positif. Réessayez : ");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Entrée invalide. Entrez un nombre pour le salaire : ");
                }
            }


            System.out.println("Choisissez l'ancienneté de la relation :");
            System.out.println("1. Ancienneté relation > 3 ans");
            System.out.println("2. Ancienneté relation 1-3 ans");
            System.out.println("3. Ancienneté relation < 1 an");

            int choice = scanner.nextInt();
            scanner.nextLine();

            String anciennete;

            switch (choice) {
                case 1:
                    anciennete = "ANCIENNETE_>3";
                    break;
                case 2:
                    anciennete = "ANCIENNETE_1-3";
                    break;
                case 3:
                    anciennete = "ANCIENNETE_<1";
                    break;
                default:
                    anciennete = "ANCIENNETE_<1";
                    System.out.println("Choix invalide, valeur par défaut ANCIENNETE_<1 appliquée.");
            }


            System.out.print("Entrez le poste : ");
            String poste = scanner.nextLine().trim();


            System.out.println("Choisissez le type de contrat :");
            System.out.println("1. CDI ");
            System.out.println("4. CDD / Intérim");

            int typeE = scanner.nextInt();
            scanner.nextLine();

            String typeContrat = "";

            switch (typeE) {
                case 1:
                    typeContrat = "CDI";
                    break;
                case 2:
                    typeContrat = "CDD";
                    break;

            }

            System.out.println("Choisissez le secteur :");
            System.out.println("1. Public");
            System.out.println("2. Privé (grande entreprise)");
            System.out.println("3. Privé (PME)");
            System.out.println("4. Intérim");

            int secteurChoix = scanner.nextInt();
            scanner.nextLine();

            String secteur;

            switch (secteurChoix) {
                case 1:
                    secteur = "PUBLIC";
                    break;
                case 2:
                    secteur = "GRANDE_ENTRE";
                    break;
                case 3:
                    secteur = "PME";
                    break;
                case 4:
                    secteur = "INTERIM";
                    break;
                default:
                    secteur = "PUBLIC";
                    System.out.println("Choix invalide, valeur par défaut 'PUBLIC' appliquée.");
            }


            LocalDateTime createdAt = LocalDateTime.now();
            double score = 0.0; 

            Employe employeC = new Employe(
                    nom,
                    prenom,
                    dateNaissance,
                    ville,
                    nombreEnfants,
                    investissement,
                    placement,
                    situationFamiliale,
                    createdAt,
                    score,
                    salaire,
                    anciennete,
                    poste,
                    typeContrat,
                    secteur
            );

            int ss=cs.employeScore(employeC);

             employeC.setScore(ss);
            System.out.println(ss);
            Session.setUser(employeC);


            userService.createClient(employeC);

        }else{


            System.out.print("Entrez le revenu : ");
            double revenu = 0;
            while (true) {
                try {
                    revenu = Double.parseDouble(scanner.nextLine());
                    if (revenu < 0) {
                        System.out.print("Le revenu doit être positif. Réessayez : ");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Entrée invalide. Entrez un nombre pour le revenu : ");
                }
            }

            System.out.print("Entrez l'immatriculation fiscale : ");
            String immatriculationFiscale = scanner.nextLine().trim();


            System.out.println("Choisissez le secteur d'activité :");
            System.out.println("1. Profession libérale stable");
            System.out.println("2. Auto-entrepreneur");

            int secteurChoice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            String secteurActivite;

            switch (secteurChoice) {
                case 1:
                    secteurActivite = "PROF_LIBERALE";
                    break;
                case 2:
                    secteurActivite = "AUTO_ENTREPRENEUR";
                    break;
                default:
                    secteurActivite = "PROF_LIBERALE";
                    System.out.println("Choix invalide, valeur par défaut 'PROF_LIBERALE' appliquée.");
            }



            System.out.print("Entrez l'activité : ");
            String activite = scanner.nextLine().trim();
            
            LocalDateTime createdAt = LocalDateTime.now();
            double score = 0.0; 

            Professionnel pro = new Professionnel(
                    nom,
                    prenom,
                    dateNaissance,
                    ville,
                    nombreEnfants,
                    investissement,
                    placement,
                    situationFamiliale,
                    createdAt,
                    score,
                    revenu,
                    immatriculationFiscale,
                    secteurActivite,
                    activite
            );

            int ss=cs.proScore(pro);
            System.out.println(ss);
            pro.setScore(ss);

            userService.createClient(pro);

            System.out.println(pro.toString());

        }




    }


    private void desition(int score) {
        if (score >= 80) {
            System.out.println("Accord immédiat");
        } else if (score >= 60) {
            System.out.println("Orientation vers étude manuelle");
        } else {
            System.out.println("Refus automatique");
        }
    }

    public void getCridet() {

        System.out.println("enter you id ");
        int id = scanner.nextInt();
        Person client = userService.getClientById(id);

        if (client != null) {


            Session.setUser(client);

            boolean active = creditS.hasActiveCredit(id);
            if(active){

                System.out.print("you have active credit you cant get one ");

                return ;

            }


            System.out.println("enter the amount you want");

            double amount = scanner.nextDouble();

            scanner.nextLine();

            System.out.println("entrer le tauxInteret ");

            double tauxInteret = scanner.nextDouble();

            scanner.nextLine();
            System.out.println("entrer le tauxInteret ");

            int dureeEnMois = scanner.nextInt();

            scanner.nextLine();

            Credit res = creditS.handleCredit(amount , tauxInteret , dureeEnMois);


            handleRes(res ,id );




        } else {

            System.out.println("Aucun client trouvé avec l'id " + id);

        }
    }

    private void handleRes(Credit res , int id) {


        if (res.getDecision() == Credit.Decision.ACCORD_IMMEDIAT) {

            while (res.getMontantDemande() > res.getPlafond()) {
                System.out.println("You can get up to " + res.getPlafond() + ". Enter a new montant:");
                double newMontant = scanner.nextDouble();
                res.setMontantDemande(newMontant);
            }

            // After valid amount is entered
            res.setMontantOctroye(res.getMontantDemande());
            System.out.println("Immediate approval: montant octroye = " + res.getMontantOctroye());
            creditS.createCridect(res  ,id );

        } else if (res.getDecision() == Credit.Decision.ETUDE_MANUELLE) {

            while (res.getMontantDemande() > res.getPlafond()) {
                System.out.println("Maximum allowed: " + res.getPlafond() + ". Enter a new montant:");
                double newMontant = scanner.nextDouble();
                res.setMontantDemande(newMontant);
            }
            System.out.println("Manual review needed. oui ou non");
            String answer = scanner.nextLine().trim().toLowerCase();

            if (answer.equals("non")) {

                System.out.println("your are not allowed to get a credit");

                return;

            }



            System.out.println("Manual review completed: montant octroye = " + res.getMontantOctroye());

            creditS.createCridect(res  ,id );

            System.out.println(res.toString());


            return ;
        }
         else if (res.getDecision() == Credit.Decision.REFUS_AUTOMATIQUE) {
            System.out.println("votre score n'est pas suffisant ") ;
            return ;
        } else {
            System.out.println("Unknown decision");
            return ;
        }

    }








}
