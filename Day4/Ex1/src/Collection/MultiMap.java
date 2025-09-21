package Collection;
import java.util.*;

public class MultiMap<K, V> {
    private final Map<K, Collection<V>> map;
    
    public MultiMap() {
        this.map = new HashMap<>();
    }
    
    // Thêm một giá trị vào khóa
    public boolean put(K key, V value) {
        if (key == null) {
            throw new NullPointerException("Khóa không được null");
        }
        
        Collection<V> values = map.computeIfAbsent(key, k -> new ArrayList<>());
        return values.add(value);
    }
    
    // Thêm tất cả các giá trị vào khóa
    public boolean putAll(K key, Collection<? extends V> values) {
        if (key == null) {
            throw new NullPointerException("Khóa không được null");
        }
        if (values == null || values.isEmpty()) {
            return false;
        }
        
        Collection<V> currentValues = map.computeIfAbsent(key, k -> new ArrayList<>());
        return currentValues.addAll(values);
    }
    
    // Lấy tất cả các giá trị cho một khóa
    public Collection<V> get(K key) {
        Collection<V> values = map.get(key);
        if (values == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableCollection(values);
    }
    
    // Kiểm tra xem khóa có chứa giá trị cụ thể không
    public boolean containsEntry(K key, V value) {
        Collection<V> values = map.get(key);
        return values != null && values.contains(value);
    }
    
    // Kiểm tra xem có chứa khóa không
    public boolean containsKey(K key) {
        return map.containsKey(key);
    }
    
    // Xóa cặp khóa-giá trị
    public boolean remove(K key, V value) {
        Collection<V> values = map.get(key);
        if (values == null) {
            return false;
        }
        
        boolean removed = values.remove(value);
        if (values.isEmpty()) {
            map.remove(key);
        }
        return removed;
    }
    
    // Xóa tất cả các giá trị cho một khóa
    public Collection<V> removeAll(K key) {
        Collection<V> values = map.remove(key);
        if (values == null) {
            return Collections.emptyList();
        }
        return values;
    }
    
    // Lấy tất cả các khóa
    public Set<K> keySet() {
        return map.keySet();
    }
    
    // Lấy tất cả các cặp khóa-giá trị
    public Set<Map.Entry<K, Collection<V>>> entrySet() {
        return map.entrySet();
    }
    
    // Lấy tất cả các giá trị trong map
    public Collection<V> values() {
        List<V> result = new ArrayList<>();
        for (Collection<V> values : map.values()) {
            result.addAll(values);
        }
        return result;
    }
    
    // Kích thước của map (tổng số cặp khóa-giá trị)
    public int size() {
        int size = 0;
        for (Collection<V> values : map.values()) {
            size += values.size();
        }
        return size;
    }
    
    // Kiểm tra map có trống không
    public boolean isEmpty() {
        return map.isEmpty();
    }
    
    // Xóa tất cả các phần tử
    public void clear() {
        map.clear();
    }
}
