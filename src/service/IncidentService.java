package service;

import entity.Echeance;
import entity.Incident;

import entity.TypeIncident;
import repos.EcheanceRepo;
import repos.IncidentRepo;

import java.time.LocalDate;
import java.util.List;

public class IncidentService {

    private EcheanceRepo echeanceRepo;
    private IncidentRepo incidentRepo;

    public IncidentService() {
        this.echeanceRepo = new EcheanceRepo();
        this.incidentRepo = new IncidentRepo();
    }

    public void generateIncidentsFromEcheances() {
        List<Echeance> echeances = echeanceRepo.getAll();



        for (Echeance e : echeances) {
           Echeance.StatutPaiement statut = e.getStatutPaiement();

            if (statut == null || statut == Echeance.StatutPaiement.PAYEATEMPS) {
                continue;
            }


            Incident incident = new Incident();
            incident.setDateIncident(LocalDate.now());
            incident.setEcheanceId(e.getId());

            switch (statut) {
                case ENRETARD:
                    incident.setTypeIncident(TypeIncident.ENRETARD);
                    incident.setScore(-3);
                    break;
                case PAYEENRETARD:
                    incident.setTypeIncident(TypeIncident.PAYEENRETARD);
                    incident.setScore(3);
                    break;
                case IMPAYENONREGLE:
                    incident.setTypeIncident(TypeIncident.IMPAYENONREGLE);
                    incident.setScore(-10);
                    break;
                case IMPAYEREGLE:
                    incident.setTypeIncident(TypeIncident.IMPAYEREGLE);
                    incident.setScore(5);
                    break;
                default:
                    continue;
            }

                incidentRepo.create(incident);
        }
    }

    public List<Incident> getAllIncidents() {
        return incidentRepo.getAll();
    }

    public void updateIncidentType(int id, TypeIncident newType, int newScore) {
        incidentRepo.updateTypeById(id, newType, newScore);
    }
}
