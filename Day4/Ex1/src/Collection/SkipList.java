package Collection;
import java.util.Random;

public class SkipList<E extends Comparable<E>> {
    private static final int MAX_LEVEL = 16;
    private final Random random;
    private Node<E> head;
    private int level;
    private int size;
    
    private static class Node<T> {
        T value;
        @SuppressWarnings("unchecked")
        Node<T>[] forward = (Node<T>[]) new Node[MAX_LEVEL];
        
        Node(T value, int level) {
            this.value = value;
        }
        
        Node(int level) {
            this(null, level);
        }
    }
    
    public SkipList() {
        this.random = new Random();
        this.head = new Node<>(MAX_LEVEL);
        this.level = 0;
        this.size = 0;
    }
    
    // Tạo ngẫu nhiên cấp độ cho nút mới
    private int randomLevel() {
        int lvl = 0;
        while (random.nextDouble() < 0.5 && lvl < MAX_LEVEL - 1) {
            lvl++;
        }
        return lvl;
    }
    
    // Chèn một giá trị vào skip list
    public void insert(E value) {
        if (value == null) {
            throw new NullPointerException("Không thể chèn giá trị null");
        }
        
        @SuppressWarnings("unchecked")
        Node<E>[] update = (Node<E>[]) new Node[MAX_LEVEL];
        Node<E> current = head;
        
        // Tìm vị trí để chèn
        for (int i = level; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].value.compareTo(value) < 0) {
                current = current.forward[i];
            }
            update[i] = current;
        }
        
        current = current.forward[0];
        
        // Nếu giá trị đã tồn tại, không chèn
        if (current != null && current.value.equals(value)) {
            return;
        }
        
        // Tạo cấp độ mới cho nút
        int newLevel = randomLevel();
        if (newLevel > level) {
            for (int i = level + 1; i <= newLevel; i++) {
                update[i] = head;
            }
            level = newLevel;
        }
        
        // Tạo và liên kết nút mới
        Node<E> newNode = new Node<>(value, newLevel);
        for (int i = 0; i <= newLevel; i++) {
            newNode.forward[i] = update[i].forward[i];
            update[i].forward[i] = newNode;
        }
        
        size++;
    }
    
    // Kiểm tra xem một giá trị có trong skip list không
    public boolean contains(E value) {
        if (value == null) {
            return false;
        }
        
        Node<E> current = head;
        
        for (int i = level; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].value.compareTo(value) < 0) {
                current = current.forward[i];
            }
        }
        
        current = current.forward[0];
        
        return current != null && current.value.equals(value);
    }
    
    // Xóa một giá trị khỏi skip list
    public boolean remove(E value) {
        if (value == null) {
            return false;
        }
        
        @SuppressWarnings("unchecked")
        Node<E>[] update = (Node<E>[]) new Node[MAX_LEVEL];
        Node<E> current = head;
        
        // Tìm vị trí để xóa
        for (int i = level; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].value.compareTo(value) < 0) {
                current = current.forward[i];
            }
            update[i] = current;
        }
        
        current = current.forward[0];
        
        // Nếu nút không tồn tại, trả về false
        if (current == null || !current.value.equals(value)) {
            return false;
        }
        
        // Xóa nút
        for (int i = 0; i <= level; i++) {
            if (update[i].forward[i] != current) {
                break;
            }
            update[i].forward[i] = current.forward[i];
        }
        
        // Cập nhật cấp độ
        while (level > 0 && head.forward[level] == null) {
            level--;
        }
        
        size--;
        return true;
    }
    
    // Kiểm tra xem skip list có trống không
    public boolean isEmpty() {
        return size == 0;
    }
    
    // Lấy số lượng phần tử
    public int size() {
        return size;
    }
    
    // Xóa tất cả các phần tử
    public void clear() {
        for (int i = 0; i < MAX_LEVEL; i++) {
            head.forward[i] = null;
        }
        level = 0;
        size = 0;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<E> current = head.forward[0];
        sb.append("[");
        while (current != null) {
            sb.append(current.value);
            current = current.forward[0];
            if (current != null) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
