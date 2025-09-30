package entity;
import java.time.LocalDate;
import java.time.LocalDateTime;

 abstract public  class Person{
     private int id ;

    protected String nom;
    protected String prenom;
    protected LocalDate dateDeNaissance;
    protected String ville;
    protected int nombreEnfants;
    protected double investissement;
    protected double placement;
    protected String situationFamiliale;
    protected LocalDateTime createdAt;
    protected double score;

    public Person(String nom, String prenom, LocalDate dateDeNaissance, String ville,
                    int nombreEnfants, double investissement, double placement,
                    String situationFamiliale, LocalDateTime createdAt, double score) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateDeNaissance = dateDeNaissance;
        this.ville = ville;
        this.nombreEnfants = nombreEnfants;
        this.investissement = investissement;
        this.placement = placement;
        this.situationFamiliale = situationFamiliale;
        this.createdAt = createdAt;
        this.score = score;
    }


     public int getId() {
         return id;
     }

     public void setId(int id) {
         this.id = id;
     }

     public String getNom() {
         return nom;
     }

     public void setNom(String nom) {
         this.nom = nom;
     }

     public double getScore() {
         return score;
     }

     public void setScore(double score) {
         this.score = score;
     }

     public String getPrenom() {
         return prenom;
     }

     public void setPrenom(String prenom) {
         this.prenom = prenom;
     }

     public LocalDate getDateDeNaissance() {
         return dateDeNaissance;
     }

     public void setDateDeNaissance(LocalDate dateDeNaissance) {
         this.dateDeNaissance = dateDeNaissance;
     }

     public String getVille() {
         return ville;
     }

     public void setVille(String ville) {
         this.ville = ville;
     }

     public int getNombreEnfants() {
         return nombreEnfants;
     }

     public void setNombreEnfants(int nombreEnfants) {
         this.nombreEnfants = nombreEnfants;
     }

     public double getPlacement() {
         return placement;
     }

     public void setPlacement(double placement) {
         this.placement = placement;
     }

     public double getInvestissement() {
         return investissement;
     }

     public void setInvestissement(double investissement) {
         this.investissement = investissement;
     }

     public String getSituationFamiliale() {
         return situationFamiliale;
     }

     public void setSituationFamiliale(String situationFamiliale) {
         this.situationFamiliale = situationFamiliale;
     }

     public LocalDateTime getCreatedAt() {
         return createdAt;
     }

     public void setCreatedAt(LocalDateTime createdAt) {
         this.createdAt = createdAt;
     }

     @Override
     public String toString() {
         return "Person{" +
                 "id" + id +
                 "nom='" + nom + '\'' +
                 ", prenom='" + prenom + '\'' +
                 ", dateDeNaissance=" + dateDeNaissance +
                 ", ville='" + ville + '\'' +
                 ", nombreEnfants=" + nombreEnfants +
                 ", investissement=" + investissement +
                 ", placement=" + placement +
                 ", situationFamiliale='" + situationFamiliale + '\'' +
                 ", createdAt=" + createdAt +
                 ", score=" + score +
                 '}';
     }
 }
