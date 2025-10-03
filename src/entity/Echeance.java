package entity;

import java.time.LocalDate;

public class Echeance {


    private int id;
    private int creditId;
    private LocalDate dateEcheance;
    private double mensualite;
    private LocalDate dateDePaiement;
    private StatutPaiement statutPaiement;

    public Echeance() {
    }

    public Echeance(int creditId, LocalDate dateEcheance, double mensualite,
                    StatutPaiement statutPaiement) {
        this.creditId = creditId;
        this.dateEcheance = dateEcheance;
        this.mensualite = mensualite;
        this.dateDePaiement = null;
        this.statutPaiement = statutPaiement;
    }

    public enum StatutPaiement {
        PAYEATEMPS,
        ENRETARD,
        PAYEENRETARD,
        IMPAYENONREGLE,
        IMPAYEREGLE
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreditId() {
        return creditId;
    }

    public void setCreditId(int creditId) {
        this.creditId = creditId;
    }

    public LocalDate getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(LocalDate dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    public double getMensualite() {
        return mensualite;
    }

    public void setMensualite(double mensualite) {
        this.mensualite = mensualite;
    }

    public LocalDate getDateDePaiement() {
        return dateDePaiement;
    }


    public void setDateDePaiement(LocalDate dateDePaiement) {
        this.dateDePaiement = dateDePaiement;
    }

    public StatutPaiement getStatutPaiement() {
        return statutPaiement;
    }

    public void setStatutPaiement(StatutPaiement statutPaiement) {
        this.statutPaiement = statutPaiement;
    }

    @Override
    public String toString() {
        return "Echeance{" +
                "id=" + id +
                ", creditId=" + creditId +
                ", dateEcheance=" + dateEcheance +
                ", mensualite=" + mensualite +
                ", dateDePaiement=" + dateDePaiement +
                ", statutPaiement=" + statutPaiement +
                '}';
    }
}
