package engine;

public class CircularBuffer<T> {
    private final int capacity;
    private final Object[] buffer;
    private int head;
    private int tail;
    private boolean isEmpty=true;

    public CircularBuffer(int capacity) {
        this.capacity = capacity;
        this.buffer = new Object[capacity];
    }


    @SuppressWarnings("unchecked")
    public synchronized void add(T t) {
        if ((tail + 1) % capacity == head) {
            throw new IllegalStateException("Buffer is full");
        }
        buffer[tail] = t;
        tail = (tail + 1) % capacity;
    }
    @SuppressWarnings("unchecked")
    public synchronized T get(int index) {
        if (index >= capacity) throw new IndexOutOfBoundsException();
        int pos = (head - capacity + index + buffer.length) % buffer.length;
        return (T) buffer[pos];
    }
    public synchronized boolean isEmpty(){
        return isEmpty;
    }

    public synchronized int size() {
        if(isEmpty){
            return 0;
        }
        if (tail>head){
            return tail-head;
        }else{
            return capacity-head+tail;
        }
    }
    public synchronized T poll() {
        if (isEmpty) {
            return null;
        }

        T result = get(0);
        head = (head + 1) % capacity;
        if (head == tail) {
            isEmpty = true;
        }
        return result;
    }



    public synchronized boolean isFull(){
        return capacity == size();
    }

}
