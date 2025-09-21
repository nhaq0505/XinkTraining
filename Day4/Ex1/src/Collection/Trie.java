package Collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trie {
    private final TrieNode root;
    
    public Trie() {
        root = new TrieNode();
    }
    
    // Lớp nút trong cây Trie
    private static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean isEndOfWord;
    }
    
    // Thêm một từ vào Trie
    public void insert(String word) {
        if (word == null || word.isEmpty()) {
            return;
        }
        
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            current.children.putIfAbsent(c, new TrieNode());
            current = current.children.get(c);
        }
        current.isEndOfWord = true;
    }
    
    // Kiểm tra xem một từ có trong Trie không
    public boolean search(String word) {
        if (word == null || word.isEmpty()) {
            return false;
        }
        
        TrieNode node = getNode(word);
        return node != null && node.isEndOfWord;
    }
    
    // Kiểm tra xem có từ nào trong Trie bắt đầu bằng tiền tố không
    public boolean startsWith(String prefix) {
        if (prefix == null || prefix.isEmpty()) {
            return false;
        }
        
        return getNode(prefix) != null;
    }
    
    // Tìm nút tương ứng với chuỗi
    private TrieNode getNode(String str) {
        TrieNode current = root;
        for (char c : str.toCharArray()) {
            if (!current.children.containsKey(c)) {
                return null;
            }
            current = current.children.get(c);
        }
        return current;
    }
    
    // Xóa một từ khỏi Trie
    public boolean delete(String word) {
        if (word == null || word.isEmpty()) {
            return false;
        }
        
        return delete(root, word, 0);
    }
    
    private boolean delete(TrieNode current, String word, int index) {
        if (index == word.length()) {
            // Nếu từ không tồn tại
            if (!current.isEndOfWord) {
                return false;
            }
            
            current.isEndOfWord = false;
            // Trả về true nếu nút không có con, để xóa nó
            return current.children.isEmpty();
        }
        
        char c = word.charAt(index);
        TrieNode node = current.children.get(c);
        if (node == null) {
            return false;
        }
        
        boolean shouldDeleteNode = delete(node, word, index + 1);
        
        // Nếu con nên được xóa
        if (shouldDeleteNode) {
            current.children.remove(c);
            // Trả về true nếu nút không có con khác và không phải là kết thúc từ
            return current.children.isEmpty() && !current.isEndOfWord;
        }
        
        return false;
    }
    
    // Lấy tất cả các từ trong Trie
    public List<String> getAllWords() {
        List<String> words = new ArrayList<>();
        collectWords(root, "", words);
        return words;
    }
    
    private void collectWords(TrieNode node, String prefix, List<String> words) {
        if (node.isEndOfWord) {
            words.add(prefix);
        }
        
        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            collectWords(entry.getValue(), prefix + entry.getKey(), words);
        }
    }
}
