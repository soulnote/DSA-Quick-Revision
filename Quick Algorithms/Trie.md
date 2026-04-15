# Trie Data Structure 
## **Trie Basics**

Trie (pronounced "try") ek **tree-based data structure** hai jo strings ko efficient tarike se store aur search karne ke liye use hoti hai. Isse **Prefix Tree** bhi kehte hain.

### **Why Trie?**
- **Fast prefix search:** O(L) time mein word search kar sakte ho (L = length of word)
- **Auto-complete:** Prefix ke basis pe saare words dhundh sakte ho
- **Dictionary:** Spell checking, word games mein use hoti hai

### **Trie vs Other Data Structures**

| Operation | Trie | HashMap | Binary Search Tree |
|-----------|------|---------|-------------------|
| Search word | O(L) | O(L) average | O(L log n) |
| Search prefix | O(L) | O(n) | O(n log n) |
| Space | O(alphabets * total chars) | O(n) | O(n) |
| Auto-complete | ✅ Fast | ❌ Slow | ❌ Slow |

### **Trie Node Structure**

```java
class TrieNode {
    TrieNode[] children = new TrieNode[26];  // For lowercase English letters
    boolean isEndOfWord;  // Marks end of a word
    int count;  // Optional: count of words passing through
}
```

### **Visual Example**

```
Dictionary: ["cat", "car", "dog"]

        root
       /    \
      c      d
     / \      \
    a   a      o
   /     \      \
  t       r      g
  
  cat, car, dog
```

---

## **Basic Trie Implementation** ⭐

### Java Code
```java
class TrieNode {
    TrieNode[] children;
    boolean isEndOfWord;
    
    public TrieNode() {
        children = new TrieNode[26];  // 26 lowercase English letters
        isEndOfWord = false;
    }
}

class Trie {
    private TrieNode root;
    
    public Trie() {
        root = new TrieNode();
    }
    
    // Insert a word into trie
    public void insert(String word) {
        TrieNode current = root;
        
        for(char c : word.toCharArray()) {
            int index = c - 'a';
            if(current.children[index] == null) {
                current.children[index] = new TrieNode();
            }
            current = current.children[index];
        }
        current.isEndOfWord = true;
    }
    
    // Search for a complete word
    public boolean search(String word) {
        TrieNode node = searchPrefix(word);
        return node != null && node.isEndOfWord;
    }
    
    // Check if any word starts with given prefix
    public boolean startsWith(String prefix) {
        return searchPrefix(prefix) != null;
    }
    
    // Helper method to find node for a given prefix
    private TrieNode searchPrefix(String prefix) {
        TrieNode current = root;
        
        for(char c : prefix.toCharArray()) {
            int index = c - 'a';
            if(current.children[index] == null) {
                return null;
            }
            current = current.children[index];
        }
        return current;
    }
    
    // Delete a word from trie
    public boolean delete(String word) {
        return delete(root, word, 0);
    }
    
    private boolean delete(TrieNode node, String word, int depth) {
        if(depth == word.length()) {
            if(!node.isEndOfWord) return false;
            node.isEndOfWord = false;
            return hasNoChildren(node);
        }
        
        int index = word.charAt(depth) - 'a';
        if(node.children[index] == null) return false;
        
        boolean shouldDeleteChild = delete(node.children[index], word, depth + 1);
        
        if(shouldDeleteChild) {
            node.children[index] = null;
            return !node.isEndOfWord && hasNoChildren(node);
        }
        return false;
    }
    
    private boolean hasNoChildren(TrieNode node) {
        for(TrieNode child : node.children) {
            if(child != null) return false;
        }
        return true;
    }
    
    // Time: O(L) for insert/search/startsWith
    // Space: O(total characters * 26)
}
```

---

## **1. Implement Trie (Prefix Tree)** ⭐⭐

### Theory
**Problem:** Trie data structure implement karo with insert, search, and startsWith operations.

**LeetCode 208:** This is the standard Trie implementation.

### Java Code
```java
class Trie {
    private TrieNode root;
    
    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isWord;
    }
    
    public Trie() {
        root = new TrieNode();
    }
    
    public void insert(String word) {
        TrieNode node = root;
        for(char c : word.toCharArray()) {
            int idx = c - 'a';
            if(node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }
            node = node.children[idx];
        }
        node.isWord = true;
    }
    
    public boolean search(String word) {
        TrieNode node = root;
        for(char c : word.toCharArray()) {
            int idx = c - 'a';
            if(node.children[idx] == null) return false;
            node = node.children[idx];
        }
        return node.isWord;
    }
    
    public boolean startsWith(String prefix) {
        TrieNode node = root;
        for(char c : prefix.toCharArray()) {
            int idx = c - 'a';
            if(node.children[idx] == null) return false;
            node = node.children[idx];
        }
        return true;
    }
}
```

---

## **2. Design Add and Search Words Data Structure** ⭐⭐

### Theory
**Problem:** Trie implement karo jisme '.' wildcard character support ho. '.' kisi bhi character ko match karega.

**Algorithm:**
- Insert: Normal Trie insertion
- Search: DFS use karo, '.' milne par saare children check karo

### Java Code
```java
class WordDictionary {
    private TrieNode root;
    
    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isWord;
    }
    
    public WordDictionary() {
        root = new TrieNode();
    }
    
    public void addWord(String word) {
        TrieNode node = root;
        for(char c : word.toCharArray()) {
            int idx = c - 'a';
            if(node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }
            node = node.children[idx];
        }
        node.isWord = true;
    }
    
    public boolean search(String word) {
        return search(root, word, 0);
    }
    
    private boolean search(TrieNode node, String word, int index) {
        if(index == word.length()) {
            return node.isWord;
        }
        
        char c = word.charAt(index);
        
        if(c == '.') {
            // Try all possible characters
            for(TrieNode child : node.children) {
                if(child != null && search(child, word, index + 1)) {
                    return true;
                }
            }
            return false;
        } else {
            int idx = c - 'a';
            if(node.children[idx] == null) return false;
            return search(node.children[idx], word, index + 1);
        }
    }
    
    // Time: Insert O(L), Search O(26^L) worst case for '.'
}
```

---

## **3. Longest Common Prefix** ⭐⭐

### Theory
**Problem:** Array of strings ka longest common prefix find karo.

**Trie Approach:**
1. Saari strings trie mein insert karo
2. Root se traverse karo jab tak single child hai aur word end nahi hai

**Alternative:** Horizontal/vertical scanning (simpler)

### Java Code
```java
class LongestCommonPrefix {
    // Method 1: Trie approach
    public String longestCommonPrefixTrie(String[] strs) {
        if(strs == null || strs.length == 0) return "";
        
        TrieNode root = new TrieNode();
        
        // Insert first word
        for(char c : strs[0].toCharArray()) {
            int idx = c - 'a';
            if(root.children[idx] == null) {
                root.children[idx] = new TrieNode();
            }
            root = root.children[idx];
        }
        
        // Check other words
        StringBuilder prefix = new StringBuilder();
        for(String str : strs) {
            TrieNode node = root;
            StringBuilder current = new StringBuilder();
            
            for(char c : str.toCharArray()) {
                int idx = c - 'a';
                if(node.children[idx] == null) break;
                current.append(c);
                node = node.children[idx];
            }
            
            if(prefix.length() == 0) {
                prefix = current;
            } else {
                prefix = new StringBuilder(prefix.substring(0, Math.min(prefix.length(), current.length())));
            }
        }
        
        return prefix.toString();
    }
    
    // Method 2: Horizontal scanning (simpler)
    public String longestCommonPrefix(String[] strs) {
        if(strs.length == 0) return "";
        
        String prefix = strs[0];
        for(int i = 1; i < strs.length; i++) {
            while(strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if(prefix.isEmpty()) return "";
            }
        }
        return prefix;
    }
    
    // Time: O(S) where S = sum of all characters, Space: O(1)
}
```

---

## **4. Word Search II** ⭐⭐⭐ (Hard)

### Theory
**Problem:** Board mein words search karo. Har word adjacent cells (up/down/left/right) se bana sakte ho.

**Algorithm (Trie + Backtracking):**
1. Saare words trie mein insert karo
2. Board ke har cell se DFS start karo
3. Trie mein traverse karte raho
4. Agar word milta hai to result mein add karo

### Java Code
```java
class WordSearchII {
    private TrieNode root;
    private List<String> result;
    private char[][] board;
    private boolean[][] visited;
    
    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        String word;  // Store complete word at end node
    }
    
    public List<String> findWords(char[][] board, String[] words) {
        this.board = board;
        this.result = new ArrayList<>();
        this.root = new TrieNode();
        this.visited = new boolean[board.length][board[0].length];
        
        // Insert all words into trie
        for(String word : words) {
            insert(word);
        }
        
        // Start DFS from each cell
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                char c = board[i][j];
                int idx = c - 'a';
                if(root.children[idx] != null) {
                    dfs(i, j, root.children[idx]);
                }
            }
        }
        
        return result;
    }
    
    private void insert(String word) {
        TrieNode node = root;
        for(char c : word.toCharArray()) {
            int idx = c - 'a';
            if(node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }
            node = node.children[idx];
        }
        node.word = word;  // Store word at end
    }
    
    private void dfs(int i, int j, TrieNode node) {
        // Check boundaries
        if(i < 0 || i >= board.length || j < 0 || j >= board[0].length) return;
        if(visited[i][j]) return;
        
        // Check if current cell matches
        char c = board[i][j];
        int idx = c - 'a';
        if(node.children[idx] == null) return;
        
        TrieNode nextNode = node.children[idx];
        
        // Found a word
        if(nextNode.word != null) {
            result.add(nextNode.word);
            nextNode.word = null;  // Avoid duplicates
        }
        
        visited[i][j] = true;
        
        // Explore all 4 directions
        dfs(i + 1, j, nextNode);
        dfs(i - 1, j, nextNode);
        dfs(i, j + 1, nextNode);
        dfs(i, j - 1, nextNode);
        
        visited[i][j] = false;
        
        // Optimization: Remove leaf nodes (optional)
        if(hasNoChildren(nextNode)) {
            node.children[idx] = null;
        }
    }
    
    private boolean hasNoChildren(TrieNode node) {
        for(TrieNode child : node.children) {
            if(child != null) return false;
        }
        return true;
    }
    
    // Time: O(M * N * 4^L) where L = max word length
    // Space: O(total characters in words)
}
```

---

## **5. Prefix and Suffix Search** ⭐⭐⭐

### Theory
**Problem:** Words ka dictionary hai. Prefix and suffix based search karni hai. Word dhundhna hai jiska given prefix aur suffix ho.

**Algorithm (Trie with special encoding):**
- Har word ke liye saari possible "suffix#prefix" combinations store karo
- Example: "apple" → "apple#apple", "pple#apple", "ple#apple", "le#apple", "e#apple"

### Java Code
```java
class WordFilter {
    private TrieNode root;
    
    class TrieNode {
        TrieNode[] children = new TrieNode[27];  // 26 letters + '#'
        int weight;  // Store highest weight/index
    }
    
    public WordFilter(String[] words) {
        root = new TrieNode();
        
        for(int weight = 0; weight < words.length; weight++) {
            String word = words[weight];
            String encoded = word + "{" + word;  // '{' is after 'z' in ASCII
            
            // Insert all suffixes
            for(int i = 0; i <= word.length(); i++) {
                String toInsert = word.substring(i) + "{" + word;
                insert(toInsert, weight);
            }
        }
    }
    
    private void insert(String word, int weight) {
        TrieNode node = root;
        for(char c : word.toCharArray()) {
            int idx = c == '{' ? 26 : c - 'a';
            if(node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }
            node = node.children[idx];
            node.weight = weight;  // Update max weight
        }
    }
    
    public int f(String prefix, String suffix) {
        String search = suffix + "{" + prefix;
        TrieNode node = root;
        
        for(char c : search.toCharArray()) {
            int idx = c == '{' ? 26 : c - 'a';
            if(node.children[idx] == null) return -1;
            node = node.children[idx];
        }
        
        return node.weight;
    }
    
    // Time: O(N * L^2) for building, O(L) for search
}
```

---

## **6. Replace Words** ⭐⭐

### Theory
**Problem:** Dictionary mein roots hain. Sentence ke words ko replace karo agar root match karta hai.

**Algorithm:**
- Saare roots trie mein insert karo
- Har word ke liye trie mein shortest prefix match dhundho

### Java Code
```java
class ReplaceWords {
    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isWord;
        String word;
    }
    
    public String replaceWords(List<String> dictionary, String sentence) {
        // Build trie
        TrieNode root = new TrieNode();
        for(String word : dictionary) {
            insert(root, word);
        }
        
        // Process sentence
        String[] words = sentence.split(" ");
        StringBuilder result = new StringBuilder();
        
        for(String word : words) {
            if(result.length() > 0) result.append(" ");
            
            String replacement = findRoot(root, word);
            result.append(replacement == null ? word : replacement);
        }
        
        return result.toString();
    }
    
    private void insert(TrieNode root, String word) {
        TrieNode node = root;
        for(char c : word.toCharArray()) {
            int idx = c - 'a';
            if(node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }
            node = node.children[idx];
        }
        node.isWord = true;
        node.word = word;
    }
    
    private String findRoot(TrieNode root, String word) {
        TrieNode node = root;
        for(char c : word.toCharArray()) {
            int idx = c - 'a';
            if(node.children[idx] == null) break;
            node = node.children[idx];
            if(node.isWord) return node.word;
        }
        return null;
    }
    
    // Time: O(total chars in dict + total chars in sentence), Space: O(total chars in dict)
}
```

---

## **7. Map Sum Pairs** ⭐⭐

### Theory
**Problem:** Key-value pairs store karo. Prefix sum return karo - sum of all values jinka key given prefix se start hota hai.

**Algorithm:**
- Har node mein sum store karo
- Insert karte time path ke saare nodes ka sum update karo

### Java Code
```java
class MapSum {
    private TrieNode root;
    private Map<String, Integer> map;  // To handle overwrites
    
    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        int sum;
    }
    
    public MapSum() {
        root = new TrieNode();
        map = new HashMap<>();
    }
    
    public void insert(String key, int val) {
        int delta = val - map.getOrDefault(key, 0);
        map.put(key, val);
        
        TrieNode node = root;
        for(char c : key.toCharArray()) {
            int idx = c - 'a';
            if(node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }
            node = node.children[idx];
            node.sum += delta;
        }
    }
    
    public int sum(String prefix) {
        TrieNode node = root;
        for(char c : prefix.toCharArray()) {
            int idx = c - 'a';
            if(node.children[idx] == null) return 0;
            node = node.children[idx];
        }
        return node.sum;
    }
    
    // Time: Insert O(L), Sum O(L), Space: O(total characters)
}
```

---

## **8. Longest Word in Dictionary** ⭐⭐

### Theory
**Problem:** Longest word dhundho jo dictionary mein build kiya ja sakta hai (har step mein prefix present hona chahiye).

**Algorithm:**
- Saare words trie mein insert karo
- BFS/DFS se longest word dhundho jiski path mein saare nodes isWord = true hain

### Java Code
```java
class LongestWordInDictionary {
    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isWord;
        String word;
    }
    
    public String longestWord(String[] words) {
        TrieNode root = new TrieNode();
        root.isWord = true;
        
        // Insert all words
        for(String word : words) {
            insert(root, word);
        }
        
        // BFS to find longest word
        Queue<TrieNode> queue = new LinkedList<>();
        queue.add(root);
        String result = "";
        
        while(!queue.isEmpty()) {
            TrieNode node = queue.poll();
            
            for(int i = 25; i >= 0; i--) {  // Reverse for lexicographical order
                if(node.children[i] != null && node.children[i].isWord) {
                    result = node.children[i].word;
                    queue.add(node.children[i]);
                }
            }
        }
        
        return result;
    }
    
    private void insert(TrieNode root, String word) {
        TrieNode node = root;
        for(char c : word.toCharArray()) {
            int idx = c - 'a';
            if(node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }
            node = node.children[idx];
        }
        node.isWord = true;
        node.word = word;
    }
    
    // Time: O(total characters), Space: O(total characters)
}
```

---

## **9. Palindrome Pairs** ⭐⭐⭐ (Hard)

### Theory
**Problem:** Saare pairs (i, j) find karo jahan words[i] + words[j] palindrome ho.

**Algorithm (Trie + HashMap):**
- Saare words trie mein insert karo (reverse order mein)
- Har word ke liye check karo palindrome pairs

### Java Code
```java
class PalindromePairs {
    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        int index = -1;  // Word index at this node
        List<Integer> palindromeIndices;  // Indices of words that form palindrome from here
    }
    
    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> result = new ArrayList<>();
        TrieNode root = new TrieNode();
        root.palindromeIndices = new ArrayList<>();
        
        // Build trie
        for(int i = 0; i < words.length; i++) {
            insert(root, words[i], i);
        }
        
        // Search for pairs
        for(int i = 0; i < words.length; i++) {
            search(root, words, i, result);
        }
        
        return result;
    }
    
    private void insert(TrieNode root, String word, int index) {
        TrieNode node = root;
        
        // Insert reverse of word
        for(int i = word.length() - 1; i >= 0; i--) {
            char c = word.charAt(i);
            int idx = c - 'a';
            if(node.children[idx] == null) {
                node.children[idx] = new TrieNode();
                node.children[idx].palindromeIndices = new ArrayList<>();
            }
            
            // Check if the remaining prefix is palindrome
            if(isPalindrome(word, 0, i - 1)) {
                node.palindromeIndices.add(index);
            }
            
            node = node.children[idx];
        }
        
        node.index = index;
        node.palindromeIndices.add(index);
    }
    
    private void search(TrieNode root, String[] words, int index, List<List<Integer>> result) {
        TrieNode node = root;
        String word = words[index];
        
        for(int i = 0; i < word.length(); i++) {
            // Case 1: Word has palindrome suffix and trie has full word
            if(node.index != -1 && node.index != index && 
               isPalindrome(word, i, word.length() - 1)) {
                result.add(Arrays.asList(index, node.index));
            }
            
            int idx = word.charAt(i) - 'a';
            if(node.children[idx] == null) return;
            node = node.children[idx];
        }
        
        // Case 2: Trie has palindrome suffixes
        for(int palIndex : node.palindromeIndices) {
            if(palIndex != index) {
                result.add(Arrays.asList(index, palIndex));
            }
        }
    }
    
    private boolean isPalindrome(String s, int left, int right) {
        while(left < right) {
            if(s.charAt(left) != s.charAt(right)) return false;
            left++;
            right--;
        }
        return true;
    }
    
    // Time: O(n * L^2), Space: O(n * L)
}
```

---

## **10. Stream of Characters** ⭐⭐

### Theory
**Problem:** Data stream mein characters aate hain. Query karo ki suffix of stream dictionary mein word hai ya nahi.

**Algorithm (Reverse Trie):**
- Words ko reverse karke trie mein insert karo
- Stream characters store karo
- Stream ko reverse karke check karo

### Java Code
```java
class StreamChecker {
    private TrieNode root;
    private StringBuilder stream;
    
    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isWord;
    }
    
    public StreamChecker(String[] words) {
        root = new TrieNode();
        stream = new StringBuilder();
        
        // Insert reversed words
        for(String word : words) {
            insert(reverse(word));
        }
    }
    
    private void insert(String word) {
        TrieNode node = root;
        for(char c : word.toCharArray()) {
            int idx = c - 'a';
            if(node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }
            node = node.children[idx];
        }
        node.isWord = true;
    }
    
    public boolean query(char letter) {
        stream.append(letter);
        
        TrieNode node = root;
        // Check from end of stream
        for(int i = stream.length() - 1; i >= 0 && node != null; i--) {
            int idx = stream.charAt(i) - 'a';
            node = node.children[idx];
            if(node != null && node.isWord) {
                return true;
            }
        }
        return false;
    }
    
    private String reverse(String s) {
        return new StringBuilder(s).reverse().toString();
    }
    
    // Time: O(L) per query, Space: O(total characters)
}
```

---

## **Quick Reference Table**

| Problem | Key Technique | Time Complexity | Space Complexity |
|---------|--------------|----------------|------------------|
| Implement Trie | Basic trie | O(L) | O(total chars) |
| Word Dictionary | Trie + DFS for '.' | O(L) search | O(total chars) |
| Longest Common Prefix | Trie traversal | O(S) | O(total chars) |
| Word Search II | Trie + Backtracking | O(M*N*4^L) | O(total chars) |
| Prefix & Suffix | Special encoding | O(L) | O(N*L²) |
| Replace Words | Trie prefix match | O(S) | O(total chars) |
| Map Sum Pairs | Trie with sum | O(L) | O(total chars) |
| Longest Word | BFS on trie | O(S) | O(total chars) |
| Palindrome Pairs | Trie + palindrome check | O(n*L²) | O(n*L) |
| Stream Checker | Reverse trie | O(L) per query | O(total chars) |

---

## **Trie Variations**

### **1. Compressed Trie (Radix Tree)**
```java
// Instead of single characters, store strings
class CompressedTrieNode {
    Map<String, CompressedTrieNode> children;
    boolean isWord;
}
```

### **2. Ternary Search Tree**
```java
// Memory efficient alternative
class TSTNode {
    char data;
    TSTNode left, middle, right;
    boolean isWord;
}
```

### **3. Bitwise Trie (Binary Trie)**
```java
// For integers (used in XOR problems)
class BitTrieNode {
    BitTrieNode[] children = new BitTrieNode[2];  // 0 and 1
}
```

---

## **Common Trie Patterns**

### **Pattern 1: Prefix Search**
```java
public boolean startsWith(String prefix) {
    TrieNode node = root;
    for(char c : prefix.toCharArray()) {
        int idx = c - 'a';
        if(node.children[idx] == null) return false;
        node = node.children[idx];
    }
    return true;
}
```

### **Pattern 2: Wildcard Search**
```java
public boolean search(String word, int index, TrieNode node) {
    if(index == word.length()) return node.isWord;
    
    char c = word.charAt(index);
    if(c == '.') {
        for(TrieNode child : node.children) {
            if(child != null && search(word, index + 1, child)) {
                return true;
            }
        }
        return false;
    } else {
        int idx = c - 'a';
        return node.children[idx] != null && 
               search(word, index + 1, node.children[idx]);
    }
}
```

### **Pattern 3: Auto-complete**
```java
public List<String> autoComplete(String prefix) {
    List<String> result = new ArrayList<>();
    TrieNode node = searchPrefix(prefix);
    if(node != null) {
        collectWords(node, new StringBuilder(prefix), result);
    }
    return result;
}

private void collectWords(TrieNode node, StringBuilder prefix, List<String> result) {
    if(node.isWord) result.add(prefix.toString());
    
    for(int i = 0; i < 26; i++) {
        if(node.children[i] != null) {
            prefix.append((char)('a' + i));
            collectWords(node.children[i], prefix, result);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }
}
```

---

## **Interview Tips (Hinglish)**

### **1. Kab Use Karein Trie?**
- Prefix based search (auto-complete, dictionary)
- Multiple string matching (Word Search II)
- Dictionary type problems
- Constraints: Only lowercase letters (26) to keep space manageable

### **2. Trie vs HashMap**

| Scenario | Choose |
|----------|--------|
| Prefix search | ✅ Trie |
| Exact search | HashMap |
| Auto-complete | ✅ Trie |
| Memory efficient | HashMap |
| Wildcard search | ✅ Trie |

### **3. Optimizations**

```java
// 1. Use array instead of HashMap for children (faster)
TrieNode[] children = new TrieNode[26];

// 2. Store word at end node (avoid extra traversal)
node.word = word;

// 3. Early termination for empty tries
if(root.children[c - 'a'] == null) return false;

// 4. Remove leaf nodes to save memory (Word Search II)
if(hasNoChildren(node)) {
    parent.children[idx] = null;
}
```

### **4. Common Mistakes**

1. **Forgot to mark end of word** - `isWord` flag set karna mat bhoolna
2. **Not handling duplicates** - Multiple same words store karne ka case handle karo
3. **Memory leak** - Large alphabets (Unicode) mein array use mat karo
4. **Off-by-one errors** - Children index calculation sahi karo

### **5. Complexity Analysis**

```
Time Complexity:
- Insert: O(L) where L = word length
- Search: O(L)
- Prefix search: O(L)
- Delete: O(L)

Space Complexity:
- Worst case: O(N * L * alphabetSize)
- With sharing: O(total unique prefixes)
```



## **Pro Tips for Interviews**

1. **Pehle brute force batao** - "Mai HashMap use kar sakta hun, lekin prefix search efficient nahi hai"

2. **Trade-offs discuss karo** - "Trie fast hai lekin memory zyada leta hai"

3. **Implementation details** - "Children array use karunga 26 size ka for lowercase letters"

4. **Edge cases handle karo** - Empty string, duplicate words, very long words

5. **Optimizations batao** - "Agar alphabets large hain to HashMap use karunga children ke liye"
