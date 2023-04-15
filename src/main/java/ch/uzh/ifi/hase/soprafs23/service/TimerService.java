package ch.uzh.ifi.hase.soprafs23.service;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class TimerService {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> scheduledTask;


    public void startTimer(Runnable task, long delayInSeconds) {
        scheduledTask = scheduler.schedule(task, delayInSeconds, TimeUnit.SECONDS);
    }

    public void stopTimer() {
        if (scheduledTask != null) {
            scheduledTask.cancel(false);
        }
    }
}
