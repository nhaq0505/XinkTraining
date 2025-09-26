package engine;
import java.util.*;

public class TopKTracker<T> {
    private final int k;
    private final Map<T,Integer> counts = new HashMap<>();

    public TopKTracker(int k) {
        this.k=k;
    }

    public synchronized void update(T t) {
        counts.put(t, counts.getOrDefault(t, 0) + 1);
    }

    public synchronized List<Map.Entry<T,Integer>> getTopK() {

        PriorityQueue<Map.Entry<T,Integer>> heap = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));
        for(Map.Entry<T,Integer> entry : counts.entrySet()){
            heap.offer(entry);
            if(heap.size()>k) heap.poll();
        }

        List<Map.Entry<T,Integer>> result = new ArrayList<>();
        while(!heap.isEmpty())
        {result.add(heap.poll());

        }
        result.sort((a,b)->b.getValue()-a.getValue());
        return result;

    }
}
