package Collection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BiMap<K, V> {
    private final Map<K, V> forward = new HashMap<>();
    private final Map<V, K> backward = new HashMap<>();
    
    // Thêm một cặp khóa-giá trị
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new NullPointerException("Khóa và giá trị không được null");
        }
        
        // Xóa các ánh xạ hiện tại nếu có
        if (forward.containsKey(key)) {
            V oldValue = forward.get(key);
            backward.remove(oldValue);
        }
        
        if (backward.containsKey(value)) {
            K oldKey = backward.get(value);
            forward.remove(oldKey);
        }
        
        forward.put(key, value);
        backward.put(value, key);
        return value;
    }
    
    // Lấy giá trị từ khóa
    public V get(K key) {
        return forward.get(key);
    }
    
    // Lấy khóa từ giá trị
    public K getKey(V value) {
        return backward.get(value);
    }
    
    // Xóa theo khóa
    public V remove(K key) {
        V value = forward.remove(key);
        if (value != null) {
            backward.remove(value);
        }
        return value;
    }
    
    // Xóa theo giá trị
    public K removeValue(V value) {
        K key = backward.remove(value);
        if (key != null) {
            forward.remove(key);
        }
        return key;
    }
    
    // Kiểm tra có chứa khóa
    public boolean containsKey(K key) {
        return forward.containsKey(key);
    }
    
    // Kiểm tra có chứa giá trị
    public boolean containsValue(V value) {
        return backward.containsKey(value);
    }
    
    // Lấy tập hợp các khóa
    public Set<K> keySet() {
        return forward.keySet();
    }
    
    // Lấy tập hợp các giá trị
    public Set<V> values() {
        return backward.keySet();
    }
    
    // Kích thước của BiMap
    public int size() {
        return forward.size();
    }
    
    // Xóa tất cả các phần tử
    public void clear() {
        forward.clear();
        backward.clear();
    }
}
