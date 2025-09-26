package engine;

import java.util.*;

public class MetricStore {
    private final long windowSize = 60_000;
    private final Deque<Event> window = new LinkedList<>();
    private CircularBuffer<DataPoint> dataPoints = new CircularBuffer<>(1000);
    private final TopKTracker<String> topk= new TopKTracker<>(3);

    public synchronized void update(Event event) {
        long now = System.currentTimeMillis();
        window.addLast(event);
        topk.update(event.getType());


        // Loại bỏ event cũ quá hạn
        while (!window.isEmpty() && window.peekFirst().getTimestamp() < now - windowSize) {
            window.pollFirst();
        }
        double count = window.size();
        dataPoints.add(new DataPoint(now, count));
    }
    public synchronized List<Map.Entry<String, Integer>> getTopKEvents() {
        return topk.getTopK();
    }

    public synchronized long getCount() {
        return window.size();
    }

    public synchronized void printDataPoints() {
        for (int i = 0; i < dataPoints.size(); i++) {
            System.out.println(dataPoints.get(i));
        }
    }







}
