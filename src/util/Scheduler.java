package util;

import service.EcheanceService;
import service.IncidentService;
import java.time.*;
import java.util.concurrent.*;

public class Scheduler {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final EcheanceService echeanceService = new EcheanceService();
    private final IncidentService incidentService = new IncidentService();

    public void start() {
        Runnable task = () -> {
            echeanceService.updateAllEcheanceStatus();
            incidentService.generateIncidentsFromEcheances();
            System.out.println("Scheduler executed at: " + LocalTime.now());
        };

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextMidnight = now.plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        long initialDelay = 6;

        scheduler.scheduleAtFixedRate(task, initialDelay,  6 * 1000, TimeUnit.MILLISECONDS);
    }


}
