package entity;

import java.time.LocalDate;

public class Incident {
    private int id;
    private LocalDate dateIncident;
    private int echeanceId;
    private int score;
    private TypeIncident typeIncident;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getDateIncident() { return dateIncident; }
    public void setDateIncident(LocalDate dateIncident) { this.dateIncident = dateIncident; }

    public int getEcheanceId() { return echeanceId; }
    public void setEcheanceId(int echeanceId) { this.echeanceId = echeanceId; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public TypeIncident getTypeIncident() { return typeIncident; }
    public void setTypeIncident(TypeIncident typeIncident) { this.typeIncident = typeIncident; }


    @Override
    public String toString() {
        return "Incident{" +
                "id=" + id +
                ", dateIncident=" + dateIncident +
                ", echeanceId=" + echeanceId +
                ", score=" + score +
                ", typeIncident=" + typeIncident +
                '}';
    }
}
