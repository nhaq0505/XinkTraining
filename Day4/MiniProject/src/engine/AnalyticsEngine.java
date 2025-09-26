package engine;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AnalyticsEngine {

    private ConcurrentHashMap<String,MetricStore> metrics = new ConcurrentHashMap<>();

    private PriorityBlockingQueue<Event> eventQueue = new PriorityBlockingQueue<>(100,((o1, o2) -> Long.compare(o1.getTimestamp(),o2.getTimestamp())));

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    public void ingest(Event event) {
        eventQueue.offer(event); // đưa sự kiện vào hàng đợi
    }

    public void start() {
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Processing events... " + System.currentTimeMillis());
        }, 0, 1000, TimeUnit.MILLISECONDS);
        processEvents();// mỗi giây in 1 lần
    }

    private void processEvents() {
        Event event;
        while ((event = eventQueue.poll()) != null) {
            handleEvent(event);
        }
    }


    private void handleEvent(Event event) {
        // TODO: cập nhật vào MetricStore
        metrics.computeIfAbsent(event.getType(), k -> new MetricStore())
                .update(event);
    }




}
