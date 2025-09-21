package Collection;

import java.util.NoSuchElementException;

public class BoundedQueue<E> {
    private final Object[] elements;
    private int head = 0;
    private int tail = 0;
    private int size = 0;
    private final int capacity;

    public BoundedQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Dung lượng phải lớn hơn 0");
        }
        this.capacity = capacity;
        this.elements = new Object[capacity];
    }

    // Thêm một phần tử vào cuối hàng đợi
    public boolean offer(E e) {
        if (e == null) {
            throw new NullPointerException("Không thể thêm phần tử null");
        }
        if (size == capacity) {
            return false;  // Hàng đợi đầy
        }
        elements[tail] = e;
        tail = (tail + 1) % capacity;
        size++;
        return true;
    }

    // Lấy và xóa phần tử ở đầu hàng đợi, trả về null nếu hàng đợi trống
    @SuppressWarnings("unchecked")
    public E poll() {
        if (size == 0) {
            return null;
        }
        E element = (E) elements[head];
        elements[head] = null;  // Giúp GC
        head = (head + 1) % capacity;
        size--;
        return element;
    }

    // Lấy và xóa phần tử ở đầu hàng đợi, ném ngoại lệ nếu hàng đợi trống
    public E remove() {
        E x = poll();
        if (x == null) {
            throw new NoSuchElementException("Hàng đợi trống");
        }
        return x;
    }

    // Xem phần tử ở đầu hàng đợi mà không xóa, trả về null nếu hàng đợi trống
    @SuppressWarnings("unchecked")
    public E peek() {
        if (size == 0) {
            return null;
        }
        return (E) elements[head];
    }

    // Xem phần tử ở đầu hàng đợi mà không xóa, ném ngoại lệ nếu hàng đợi trống
    public E element() {
        E x = peek();
        if (x == null) {
            throw new NoSuchElementException("Hàng đợi trống");
        }
        return x;
    }

    // Kiểm tra xem hàng đợi có trống không
    public boolean isEmpty() {
        return size == 0;
    }

    // Kiểm tra xem hàng đợi có đầy không
    public boolean isFull() {
        return size == capacity;
    }

    // Lấy số lượng phần tử trong hàng đợi
    public int size() {
        return size;
    }

    // Lấy dung lượng tối đa của hàng đợi
    public int capacity() {
        return capacity;
    }

    // Xóa tất cả các phần tử trong hàng đợi
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            elements[i] = null;
        }
        head = 0;
        tail = 0;
        size = 0;
    }
}