package Collection;
import java.util.NoSuchElementException;

public class CircularBuffer<E> {
    private final Object[] buffer;
    private int head = 0;
    private int tail = 0;
    private int size = 0;
    private final int capacity;
    private final boolean overwrite;
    
    public CircularBuffer(int capacity) {
        this(capacity, false);
    }
    
    public CircularBuffer(int capacity, boolean overwrite) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Dung lượng phải lớn hơn 0");
        }
        this.capacity = capacity;
        this.buffer = new Object[capacity];
        this.overwrite = overwrite;
    }
    
    // Thêm phần tử vào bộ đệm
    public boolean add(E element) {
        if (element == null) {
            throw new NullPointerException("Không thể thêm phần tử null");
        }
        
        if (size == capacity) {
            if (!overwrite) {
                return false;  // Bộ đệm đầy và không được ghi đè
            }
            // Ghi đè phần tử cũ nhất (tại head)
            buffer[tail] = element;
            tail = (tail + 1) % capacity;
            head = (head + 1) % capacity; // Di chuyển head khi ghi đè
            return true;
        }
        
        buffer[tail] = element;
        tail = (tail + 1) % capacity;
        size++;
        return true;
    }
    
    // Lấy và xóa phần tử từ bộ đệm
    @SuppressWarnings("unchecked")
    public E remove() {
        if (size == 0) {
            throw new NoSuchElementException("Bộ đệm trống");
        }
        
        E element = (E) buffer[head];
        buffer[head] = null;  // Giúp GC
        head = (head + 1) % capacity;
        size--;
        return element;
    }
    
    // Xem phần tử tiếp theo mà không xóa
    @SuppressWarnings("unchecked")
    public E peek() {
        if (size == 0) {
            throw new NoSuchElementException("Bộ đệm trống");
        }
        
        return (E) buffer[head];
    }
    
    // Kiểm tra xem bộ đệm có trống không
    public boolean isEmpty() {
        return size == 0;
    }
    
    // Kiểm tra xem bộ đệm có đầy không
    public boolean isFull() {
        return size == capacity;
    }
    
    // Lấy số lượng phần tử trong bộ đệm
    public int size() {
        return size;
    }
    
    // Lấy dung lượng của bộ đệm
    public int capacity() {
        return capacity;
    }
    
    // Xóa tất cả các phần tử
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            buffer[i] = null;
        }
        head = 0;
        tail = 0;
        size = 0;
    }
    
    // Chuyển đổi thành mảng
    @SuppressWarnings("unchecked")
    public Object[] toArray() {
        Object[] result = new Object[size];
        int index = head;
        for (int i = 0; i < size; i++) {
            result[i] = buffer[index];
            index = (index + 1) % capacity;
        }
        return result;
    }
}
