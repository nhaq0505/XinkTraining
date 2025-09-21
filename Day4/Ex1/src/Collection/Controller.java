package Collection;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class Controller {
    public static void testBoundedQueue() {
        System.out.println("1. BoundedQueue Test");
        System.out.println("-------------------");

        BoundedQueue<String> queue = new BoundedQueue<>(3);
        System.out.println("Created queue with capacity: " + queue.capacity());

        System.out.println("Adding items: A, B, C");
        queue.offer("A");
        queue.offer("B");
        queue.offer("C");
        System.out.println("Queue size: " + queue.size());
        System.out.println("Queue full? " + queue.isFull());

        System.out.println("Trying to add another item: " + queue.offer("D"));

        System.out.println("Polling items:");
        System.out.println("  Poll: " + queue.poll());
        System.out.println("  Poll: " + queue.poll());
        System.out.println("  Poll: " + queue.poll());
        System.out.println("  Poll: " + queue.poll());

        System.out.println("Queue empty? " + queue.isEmpty());
        System.out.println();
    }

    public static void testBiMap() {
        System.out.println("2. BiMap Test");
        System.out.println("-------------");

        BiMap<Integer, String> biMap = new BiMap<>();
        System.out.println("Adding mappings:");
        biMap.put(1, "One");
        biMap.put(2, "Two");
        biMap.put(3, "Three");

        System.out.println("Forward lookups:");
        System.out.println("  1 -> " + biMap.get(1));
        System.out.println("  2 -> " + biMap.get(2));
        System.out.println("  3 -> " + biMap.get(3));

        System.out.println("Reverse lookups:");
        System.out.println("  One -> " + biMap.getKey("One"));
        System.out.println("  Two -> " + biMap.getKey("Two"));
        System.out.println("  Three -> " + biMap.getKey("Three"));

        System.out.println("Adding duplicate value with different key:");
        biMap.put(4, "One");
        System.out.println("  4 -> " + biMap.get(4));
        System.out.println("  1 -> " + biMap.get(1)); // Should be null now
        System.out.println("  One -> " + biMap.getKey("One")); // Should return 4 now

        System.out.println("BiMap size: " + biMap.size());
        System.out.println();
    }

    public static void testTrie() {
        System.out.println("3. Trie Test");
        System.out.println("------------");

        Trie trie = new Trie();
        System.out.println("Inserting words: apple, app, banana, ball");
        trie.insert("apple");
        trie.insert("app");
        trie.insert("banana");
        trie.insert("ball");

        System.out.println("Word searches:");
        System.out.println("  apple: " + trie.search("apple"));
        System.out.println("  app: " + trie.search("app"));
        System.out.println("  apples: " + trie.search("apples"));
        System.out.println("  ban: " + trie.search("ban"));

        System.out.println("Prefix searches:");
        System.out.println("  app: " + trie.startsWith("app"));
        System.out.println("  ban: " + trie.startsWith("ban"));
        System.out.println("  car: " + trie.startsWith("car"));

        System.out.println("Deleting 'apple'");
        trie.delete("apple");
        System.out.println("  apple: " + trie.search("apple"));
        System.out.println("  app: " + trie.search("app"));

        System.out.println("All words in trie: " + trie.getAllWords());
        System.out.println();
    }

    public static void testLRUCache() {
        System.out.println("4. LRU Cache Test");
        System.out.println("----------------");

        LRUCache<String, Integer> cache = new LRUCache<>(3);
        System.out.println("Created LRU cache with capacity 3");

        System.out.println("Adding items:");
        cache.put("A", 1);
        cache.put("B", 2);
        cache.put("C", 3);

        System.out.println("Cache size: " + cache.size());

        System.out.println("Accessing 'A': " + cache.get("A"));

        System.out.println("Adding 'D' (should evict 'B' as least recently used)");
        cache.put("D", 4);

        System.out.println("After adding 'D', checking cache:");
        System.out.println("  A: " + cache.get("A"));
        System.out.println("  B: " + cache.get("B")); // Should be null
        System.out.println("  C: " + cache.get("C"));
        System.out.println("  D: " + cache.get("D"));

        System.out.println("Updating 'C' to 30");
        cache.put("C", 30);
        System.out.println("  C: " + cache.get("C"));
        System.out.println();
    }


    public static void testMultiMap() {
        System.out.println("5. MultiMap Test");
        System.out.println("---------------");

        // Explicitly specify the type parameters to prevent type inference issues
        MultiMap<String, String> multiMap = new MultiMap<String, String>();
        System.out.println("Adding mappings:");
        multiMap.put("Fruits", "Apple");
        multiMap.put("Fruits", "Banana");
        multiMap.put("Fruits", "Orange");
        multiMap.put("Colors", "Red");
        multiMap.put("Colors", "Blue");

        System.out.println("Values for 'Fruits': " + multiMap.get("Fruits"));
        System.out.println("Values for 'Colors': " + multiMap.get("Colors"));

        System.out.println("Contains 'Fruits' -> 'Apple': " + multiMap.containsEntry("Fruits", "Apple"));
        System.out.println("Contains 'Fruits' -> 'Grape': " + multiMap.containsEntry("Fruits", "Grape"));

        System.out.println("Removing 'Fruits' -> 'Banana'");
        multiMap.remove("Fruits", "Banana");
        System.out.println("Values for 'Fruits' after removal: " + multiMap.get("Fruits"));

        System.out.println("Total entries in MultiMap: " + multiMap.size());
        System.out.println("All values in MultiMap: " + multiMap.values());
        System.out.println();
    }

    public static void testCircularBuffer() {
        System.out.println("6. CircularBuffer Test");
        System.out.println("--------------------");

        CircularBuffer<Integer> buffer = new CircularBuffer<>(3);
        System.out.println("Created circular buffer with capacity 3");

        System.out.println("Adding items 1, 2, 3:");
        buffer.add(1);
        buffer.add(2);
        buffer.add(3);
        System.out.println("Buffer size: " + buffer.size());
        System.out.println("Buffer full? " + buffer.isFull());

        System.out.println("Trying to add 4: " + buffer.add(4)); // Should fail

        System.out.println("Testing overwriting buffer:");
        CircularBuffer<Integer> overwriteBuffer = new CircularBuffer<>(3, true);
        overwriteBuffer.add(1);
        overwriteBuffer.add(2);
        overwriteBuffer.add(3);
        System.out.println("  Before overwrite: " + Arrays.toString(overwriteBuffer.toArray()));
        System.out.println("  Adding 4 with overwrite: " + overwriteBuffer.add(4));
        System.out.println("  After overwrite: " + Arrays.toString(overwriteBuffer.toArray()));

        System.out.println("Removing from original buffer:");
        try {
            System.out.println("  Remove: " + buffer.remove());
            System.out.println("  Peek: " + buffer.peek());
            System.out.println("  Remove: " + buffer.remove());
            System.out.println("  Remove: " + buffer.remove());

            // The following line will throw an exception since the buffer is now empty
            buffer.remove();
        } catch (NoSuchElementException e) {
            System.out.println("  Exception when buffer is empty: " + e.getMessage());
        }
        System.out.println("  Buffer empty? " + buffer.isEmpty());
        System.out.println();
    }

    public static void testSkipList() {
        System.out.println("7. SkipList Test");
        System.out.println("---------------");

        SkipList<Integer> skipList = new SkipList<>();
        System.out.println("Inserting values: 30, 40, 10, 50, 20, 60");
        skipList.insert(30);
        skipList.insert(40);
        skipList.insert(10);
        skipList.insert(50);
        skipList.insert(20);
        skipList.insert(60);

        System.out.println("SkipList content: " + skipList);
        System.out.println("SkipList size: " + skipList.size());

        System.out.println("Contains checks:");
        System.out.println("  Contains 10: " + skipList.contains(10));
        System.out.println("  Contains 25: " + skipList.contains(25));
        System.out.println("  Contains 60: " + skipList.contains(60));

        System.out.println("Removing 40");
        skipList.remove(40);
        System.out.println("After removal: " + skipList);

        System.out.println("Removing 10");
        skipList.remove(10);
        System.out.println("After removal: " + skipList);

        System.out.println("SkipList final size: " + skipList.size());
        System.out.println();
    }
}
