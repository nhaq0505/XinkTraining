package Collection;
import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> {
    private final int capacity;
    private final Map<K, Node<K, V>> cache;
    private final Node<K, V> head; // Most recently used
    private final Node<K, V> tail; // Least recently used
    
    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev;
        Node<K, V> next;
        
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
        
        public Node() {
            this.key = null;
            this.value = null;
        }
    }
    
    public LRUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Dung lượng phải lớn hơn 0");
        }
        
        this.capacity = capacity;
        this.cache = new HashMap<>();
        
        // Tạo danh sách liên kết với các nút đầu và đuôi giả
        this.head = new Node<>();
        this.tail = new Node<>();
        head.next = tail;
        tail.prev = head;
    }
    
    // Lấy giá trị từ cache
    public V get(K key) {
        Node<K, V> node = cache.get(key);
        if (node == null) {
            return null;
        }
        
        // Di chuyển nút đã truy cập lên đầu (đã sử dụng gần đây nhất)
        moveToHead(node);
        return node.value;
    }
    
    // Thêm hoặc cập nhật một mục trong cache
    public void put(K key, V value) {
        Node<K, V> node = cache.get(key);
        
        if (node == null) {
            // Tạo nút mới
            Node<K, V> newNode = new Node<>(key, value);
            
            // Thêm vào cache và danh sách
            cache.put(key, newNode);
            addToHead(newNode);
            
            // Xóa phần tử ít dùng nhất nếu vượt quá dung lượng
            if (cache.size() > capacity) {
                Node<K, V> tail = removeTail();
                cache.remove(tail.key);
            }
        } else {
            // Cập nhật giá trị và di chuyển nút lên đầu
            node.value = value;
            moveToHead(node);
        }
    }
    
    // Thêm nút mới vào đầu danh sách
    private void addToHead(Node<K, V> node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }
    
    // Xóa một nút khỏi danh sách
    private void removeNode(Node<K, V> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    
    // Di chuyển một nút lên đầu danh sách
    private void moveToHead(Node<K, V> node) {
        removeNode(node);
        addToHead(node);
    }
    
    // Xóa và trả về nút ở cuối danh sách (ít được sử dụng nhất)
    private Node<K, V> removeTail() {
        Node<K, V> lastNode = tail.prev;
        removeNode(lastNode);
        return lastNode;
    }
    
    // Lấy kích thước hiện tại của cache
    public int size() {
        return cache.size();
    }
    
    // Kiểm tra xem cache có trống không
    public boolean isEmpty() {
        return cache.isEmpty();
    }
    
    // Xóa tất cả các mục trong cache
    public void clear() {
        cache.clear();
        head.next = tail;
        tail.prev = head;
    }
}
