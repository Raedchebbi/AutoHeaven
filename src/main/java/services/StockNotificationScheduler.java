package services;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StockNotificationScheduler {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private NotificationService stockNotificationService;

    public StockNotificationScheduler(NotificationService service) {
        this.stockNotificationService = service;
    }

    public void startMonitoring() {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                stockNotificationService.checkStockLevels();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                stockNotificationService.removeResolvedNotifications();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, 0, 10, TimeUnit.MINUTES);
    }
}
