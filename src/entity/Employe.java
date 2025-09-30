package entity;

import entity.Person;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Employe extends Person {
    private double salaire;
    private int anciennete;
    private String poste;
    private String typeContrat;
    private String secteur;

    public Employe(String nom, String prenom, LocalDate dateDeNaissance, String ville,int nombreEnfants, double investissement, double placement,String situationFamiliale, LocalDateTime createdAt, double score,
                   double salaire, int anciennete, String poste,String typeContrat, String secteur) {

        super(nom, prenom, dateDeNaissance, ville, nombreEnfants, investissement, placement,
                situationFamiliale, createdAt, score);

        this.salaire = salaire;
        this.anciennete = anciennete;
        this.poste = poste;
        this.typeContrat = typeContrat;
        this.secteur = secteur;
    }

    public double getSalaire() {
        return salaire;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }

    public String getSecteur() {
        return secteur;
    }

    public void setSecteur(String secteur) {
        this.secteur = secteur;
    }

    public String getTypeContrat() {
        return typeContrat;
    }

    public void setTypeContrat(String typeContrat) {
        this.typeContrat = typeContrat;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public int getAnciennete() {
        return anciennete;
    }

    public void setAnciennete(int anciennete) {
        this.anciennete = anciennete;
    }
}
