package entity ;
import java.time.LocalDate;


public class Credit {


    private LocalDate dateDeCredit;
    private double montantDemande;
    private double montantOctroye;
    private double tauxInteret;
    private int dureeEnMois;
    private String typeCredit;
    private Decision decision;
    private double plafond;
    private boolean status ;


    public enum Decision {
        ACCORD_IMMEDIAT,
        ETUDE_MANUELLE,
        REFUS_AUTOMATIQUE
    }

    public Credit() {
    }

    public Credit( double montantDemande, double tauxInteret,
                  int dureeEnMois) {
        this.dateDeCredit = LocalDate.now();
        this.montantDemande = montantDemande;
        this.tauxInteret = tauxInteret;
        this.dureeEnMois = dureeEnMois;

    }


    public void setMontantOctroye(double montantOctroye) {
        this.montantOctroye = montantOctroye;
    }

    public void setDecision(Decision decision) {
        this.decision = decision;
    }

    public LocalDate getDateDeCredit() {
        return dateDeCredit;
    }

    public void setDateDeCredit(LocalDate dateDeCredit) {
        this.dateDeCredit = dateDeCredit;
    }

    public Decision getDecision() {
        return decision;
    }

    public String getTypeCredit() {
        return typeCredit;
    }

    public void setTypeCredit(String typeCredit) {
        this.typeCredit = typeCredit;
    }

    public int getDureeEnMois() {
        return dureeEnMois;
    }

    public void setDureeEnMois(int dureeEnMois) {
        this.dureeEnMois = dureeEnMois;
    }

    public double getTauxInteret() {
        return tauxInteret;
    }

    public void setTauxInteret(double tauxInteret) {
        this.tauxInteret = tauxInteret;
    }

    public double getMontantOctroye() {
        return montantOctroye;
    }

    public double getMontantDemande() {
        return montantDemande;
    }

    public void setMontantDemande(double montantDemande) {
        this.montantDemande = montantDemande;
    }

    public boolean getStatus() {
        return status;
    }

    public  void setStatus(boolean status) {
        this.status = status;
    }


    public double getPlafond() {
        return plafond;
    }

    public void setPlafond(double plafond) {
        this.plafond = plafond;
    }

    public boolean isStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "dateDeCredit=" + dateDeCredit +
                ", montantDemande=" + montantDemande +
                ", montantOctroye=" + montantOctroye +
                ", tauxInteret=" + tauxInteret +
                ", dureeEnMois=" + dureeEnMois +
                ", typeCredit='" + typeCredit + '\'' +
                ", decision=" + decision + " plafond :" + plafond+
                '}';
    }
}
