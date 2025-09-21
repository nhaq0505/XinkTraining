
import Collection.Controller;

public class Main {
    public static void main(String[] args) {
        System.out.println("Testing Custom Collection Classes");
        System.out.println("================================\n");

        Controller.testBoundedQueue();
        Controller.testBiMap();
        Controller.testTrie();
        Controller.testLRUCache();
        Controller.testMultiMap();
        Controller.testCircularBuffer();
        Controller.testSkipList();
    }


}