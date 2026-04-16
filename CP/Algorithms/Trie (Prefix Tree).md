
Tumhare paas dictionary hai: `["apple", "app", "apricot", "ball", "bat"]`

Tumhe karna hai:
1. **Search** - "apple" exist karta hai?
2. **Prefix Search** - "ap" se start hone wale saare words?
3. **Auto-complete** - "ap" type karne par "apple", "apricot" suggest karo?

**Naive approach** - Har query ke liye saare words check karo. Time O(n * L) where L = word length.

**Trie solution** - O(L) time per query! 🚀

---

### 🎯 Concept - Visual Understanding

Socho ek tree jahan:
- **Root** empty string represent karta hai
- **Har node** ek character store karta hai
- **Path root se node tak** ek prefix represent karta hai

```
Dictionary: ["apple", "app", "apricot", "ball", "bat"]

        root
       /    \
      a      b
     /       \
    p         a
   / \       / \
  p   r     l   t
  |   |     |   |
  l   i     l   (ball end)
  |   |
  e   c
  |   |
(e end) o
      |
      t
      |
   (apricot end)
```

**Properties:**
- Har node ke max children = 26 (a to z)
- Word end mark karne ke liye boolean flag use karte hain
- Search prefix: O(L) time
- Search word: O(L) time

---

### 🏗️ Trie Node Structure

```java
class TrieNode {
    TrieNode[] children;
    boolean isEnd;
    
    public TrieNode() {
        children = new TrieNode[26];  // a to z
        isEnd = false;
    }
}
```


- `children[26]` - har character ke liye ek child node
- `isEnd` - true agar yahan koi word khatam hota hai

---

### 🔧 Basic Operations

#### 1. Insert

```java
public void insert(String word) {
    TrieNode node = root;
    for (char c : word.toCharArray()) {
        int idx = c - 'a';
        if (node.children[idx] == null) {
            node.children[idx] = new TrieNode();
        }
        node = node.children[idx];
    }
    node.isEnd = true;
}
```


- Root se start karo
- Har character ke liye child exist karta hai? Nahi toh banao
- Last character pe `isEnd = true` mark karo

#### 2. Search

```java
public boolean search(String word) {
    TrieNode node = root;
    for (char c : word.toCharArray()) {
        int idx = c - 'a';
        if (node.children[idx] == null) return false;
        node = node.children[idx];
    }
    return node.isEnd;
}
```


- Root se start karo
- Har character follow karo
- Agar koi character missing → false
- Last node pe `isEnd` check karo

#### 3. Starts With (Prefix Search)

```java
public boolean startsWith(String prefix) {
    TrieNode node = root;
    for (char c : prefix.toCharArray()) {
        int idx = c - 'a';
        if (node.children[idx] == null) return false;
        node = node.children[idx];
    }
    return true;
}
```


- Search jaisa hi, bas last node pe `isEnd` check nahi karte
- Agar prefix exist karta hai → true

---

## 📊 Time & Space Complexity

| Operation | Time Complexity | Space Complexity |
|-----------|----------------|------------------|
| Insert | O(L) | O(L) per word |
| Search | O(L) | O(1) |
| StartsWith | O(L) | O(1) |
| Delete | O(L) | O(1) |

**L = length of word**

---

## 🔄 Advanced Trie Concepts

### 1. Delete Word

```java
public boolean delete(String word) {
    return delete(root, word, 0);
}

private boolean delete(TrieNode node, String word, int idx) {
    if (idx == word.length()) {
        if (!node.isEnd) return false;
        node.isEnd = false;
        return node.childrenCount() == 0;
    }
    
    int charIdx = word.charAt(idx) - 'a';
    if (node.children[charIdx] == null) return false;
    
    boolean shouldDelete = delete(node.children[charIdx], word, idx + 1);
    
    if (shouldDelete) {
        node.children[charIdx] = null;
        return node.childrenCount() == 0 && !node.isEnd;
    }
    return false;
}

// Helper method
private int childrenCount() {
    int count = 0;
    for (TrieNode child : children) {
        if (child != null) count++;
    }
    return count;
}
```


- Pehle word search karo
- End node ka `isEnd = false` karo
- Agar node ke koi children nahi hai toh parent se delete karo

### 2. Auto-complete (Find all words with given prefix)

```java
public List<String> autoComplete(String prefix) {
    List<String> result = new ArrayList<>();
    TrieNode node = root;
    
    // Reach the prefix node
    for (char c : prefix.toCharArray()) {
        int idx = c - 'a';
        if (node.children[idx] == null) return result;
        node = node.children[idx];
    }
    
    // DFS to find all words
    dfs(node, new StringBuilder(prefix), result);
    return result;
}

private void dfs(TrieNode node, StringBuilder current, List<String> result) {
    if (node.isEnd) {
        result.add(current.toString());
    }
    
    for (int i = 0; i < 26; i++) {
        if (node.children[i] != null) {
            current.append((char)('a' + i));
            dfs(node.children[i], current, result);
            current.deleteCharAt(current.length() - 1);
        }
    }
}
```


- Pehle prefix tak pahuncho
- Phir DFS karke saare words collect karo

---

## 📋 Top 10 Most Asked Problems

---

### 1. Implement Trie (Prefix Tree)
**Problem:** Basic Trie implementation with insert, search, startsWith.

```java
class Trie {
    class TrieNode {
        TrieNode[] children;
        boolean isEnd;
        
        TrieNode() {
            children = new TrieNode[26];
            isEnd = false;
        }
    }
    
    TrieNode root;
    
    public Trie() {
        root = new TrieNode();
    }
    
    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }
            node = node.children[idx];
        }
        node.isEnd = true;
    }
    
    public boolean search(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) return false;
            node = node.children[idx];
        }
        return node.isEnd;
    }
    
    public boolean startsWith(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) return false;
            node = node.children[idx];
        }
        return true;
    }
}
```

Basic implementation. Yahi foundation hai saari problems ka.

---

### 2. Design Add and Search Words Data Structure
**Problem:** Add word, search word with '.' wildcard (matches any character).

```java
class WordDictionary {
    class TrieNode {
        TrieNode[] children;
        boolean isEnd;
        
        TrieNode() {
            children = new TrieNode[26];
            isEnd = false;
        }
    }
    
    TrieNode root;
    
    public WordDictionary() {
        root = new TrieNode();
    }
    
    public void addWord(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }
            node = node.children[idx];
        }
        node.isEnd = true;
    }
    
    public boolean search(String word) {
        return search(root, word, 0);
    }
    
    private boolean search(TrieNode node, String word, int idx) {
        if (idx == word.length()) {
            return node.isEnd;
        }
        
        char c = word.charAt(idx);
        if (c == '.') {
            // Try all possible characters
            for (TrieNode child : node.children) {
                if (child != null && search(child, word, idx + 1)) {
                    return true;
                }
            }
            return false;
        } else {
            int childIdx = c - 'a';
            if (node.children[childIdx] == null) return false;
            return search(node.children[childIdx], word, idx + 1);
        }
    }
}
```


- Normal insertion
- Search mein '.' milne par saare children pe DFS karo
- Time complexity: O(26^L) worst case for '.' only

---

### 3. Longest Word in Dictionary
**Problem:** Find longest word that can be built one character at a time from dictionary.

```java
public String longestWord(String[] words) {
    TrieNode root = new TrieNode();
    
    // Insert all words
    for (String word : words) {
        insert(root, word);
    }
    
    String[] result = {""};
    dfs(root, new StringBuilder(), result);
    return result[0];
}

private void dfs(TrieNode node, StringBuilder current, String[] result) {
    if (current.length() > result[0].length()) {
        result[0] = current.toString();
    } else if (current.length() == result[0].length()) {
        if (current.toString().compareTo(result[0]) < 0) {
            result[0] = current.toString();
        }
    }
    
    for (int i = 0; i < 26; i++) {
        if (node.children[i] != null && node.children[i].isEnd) {
            current.append((char)('a' + i));
            dfs(node.children[i], current, result);
            current.deleteCharAt(current.length() - 1);
        }
    }
}
```


- Sirf unhi nodes ko explore karo jahan `isEnd = true`
- DFS se longest word dhundho
- Lexicographically smallest bhi maintain karo

---

### 4. Word Search II
**Problem:** Find all words from dictionary in a 2D board.

```java
public List<String> findWords(char[][] board, String[] words) {
    List<String> result = new ArrayList<>();
    TrieNode root = buildTrie(words);
    
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[0].length; j++) {
            dfs(board, i, j, root, result);
        }
    }
    return result;
}

private void dfs(char[][] board, int i, int j, TrieNode node, List<String> result) {
    char c = board[i][j];
    if (c == '#' || node.children[c - 'a'] == null) return;
    
    node = node.children[c - 'a'];
    if (node.word != null) {
        result.add(node.word);
        node.word = null; // Avoid duplicates
    }
    
    board[i][j] = '#'; // Mark visited
    
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    for (int[] dir : dirs) {
        int x = i + dir[0];
        int y = j + dir[1];
        if (x >= 0 && x < board.length && y >= 0 && y < board[0].length) {
            dfs(board, x, y, node, result);
        }
    }
    
    board[i][j] = c; // Backtrack
}

private TrieNode buildTrie(String[] words) {
    TrieNode root = new TrieNode();
    for (String word : words) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }
            node = node.children[idx];
        }
        node.word = word;
    }
    return root;
}

class TrieNode {
    TrieNode[] children = new TrieNode[26];
    String word = null;
}
```


- Pehle saare words ka Trie banao
- Board pe har cell se DFS karo
- Trie ke saath traverse karte jao
- Agar `word != null` mil gaya toh result mein add karo

---

### 5. Replace Words
**Problem:** Replace words in sentence with their shortest root from dictionary.

```java
public String replaceWords(List<String> dictionary, String sentence) {
    TrieNode root = new TrieNode();
    
    // Insert all roots into Trie
    for (String word : dictionary) {
        insert(root, word);
    }
    
    String[] words = sentence.split(" ");
    StringBuilder result = new StringBuilder();
    
    for (String word : words) {
        if (result.length() > 0) result.append(" ");
        
        // Find shortest prefix that exists in Trie
        String replacement = findRoot(root, word);
        result.append(replacement != null ? replacement : word);
    }
    
    return result.toString();
}

private String findRoot(TrieNode root, String word) {
    TrieNode node = root;
    StringBuilder prefix = new StringBuilder();
    
    for (char c : word.toCharArray()) {
        int idx = c - 'a';
        if (node.children[idx] == null) break;
        
        prefix.append(c);
        node = node.children[idx];
        
        if (node.isEnd) {
            return prefix.toString();
        }
    }
    return null;
}
```


- Dictionary ke saare roots Trie mein daalo
- Har word ka shortest prefix match dhundho
- Match mila toh replace karo, nahi toh original rakho

---

### 6. Maximum XOR of Two Numbers in an Array
**Problem:** Find maximum XOR of any two numbers in array.

```java
class BitTrieNode {
    BitTrieNode[] children = new BitTrieNode[2];
}

public int findMaximumXOR(int[] nums) {
    BitTrieNode root = new BitTrieNode();
    
    // Insert all numbers into bitwise Trie
    for (int num : nums) {
        BitTrieNode node = root;
        for (int i = 31; i >= 0; i--) {
            int bit = (num >> i) & 1;
            if (node.children[bit] == null) {
                node.children[bit] = new BitTrieNode();
            }
            node = node.children[bit];
        }
    }
    
    int maxXor = 0;
    
    // For each number, find best match
    for (int num : nums) {
        BitTrieNode node = root;
        int currXor = 0;
        
        for (int i = 31; i >= 0; i--) {
            int bit = (num >> i) & 1;
            // Want opposite bit for maximum XOR
            int desired = 1 - bit;
            
            if (node.children[desired] != null) {
                currXor |= (1 << i);
                node = node.children[desired];
            } else {
                node = node.children[bit];
            }
        }
        maxXor = Math.max(maxXor, currXor);
    }
    return maxXor;
}
```


- Har number ko binary mein Trie mein daalo (31 bits)
- Har number ke liye opposite bit prefer karo
- Maximum XOR mil jayega

---

### 7. Palindrome Pairs
**Problem:** Find all pairs (i, j) where words[i] + words[j] is palindrome.

```java
public List<List<Integer>> palindromePairs(String[] words) {
    List<List<Integer>> result = new ArrayList<>();
    TrieNode root = new TrieNode();
    
    // Insert all words into Trie (reverse order)
    for (int i = 0; i < words.length; i++) {
        insertReverse(root, words[i], i);
    }
    
    // Check each word
    for (int i = 0; i < words.length; i++) {
        search(root, words[i], i, result);
    }
    return result;
}

private void insertReverse(TrieNode root, String word, int idx) {
    TrieNode node = root;
    String rev = new StringBuilder(word).reverse().toString();
    
    for (int j = 0; j < rev.length(); j++) {
        int charIdx = rev.charAt(j) - 'a';
        if (node.children[charIdx] == null) {
            node.children[charIdx] = new TrieNode();
        }
        node = node.children[charIdx];
        
        // Store index if suffix of rev is palindrome
        if (isPalindrome(rev, j + 1, rev.length() - 1)) {
            node.suffixPalindromes.add(idx);
        }
    }
    node.wordIndex = idx;
    node.isEnd = true;
}

private void search(TrieNode root, String word, int idx, List<List<Integer>> result) {
    TrieNode node = root;
    
    for (int j = 0; j < word.length(); j++) {
        // Case 1: word ka prefix reverse se match ho aur baki part palindrome
        if (node.wordIndex != -1 && node.wordIndex != idx && 
            isPalindrome(word, j, word.length() - 1)) {
            result.add(Arrays.asList(idx, node.wordIndex));
        }
        
        int charIdx = word.charAt(j) - 'a';
        if (node.children[charIdx] == null) return;
        node = node.children[charIdx];
    }
    
    // Case 2: Exact match
    if (node.wordIndex != -1 && node.wordIndex != idx) {
        result.add(Arrays.asList(idx, node.wordIndex));
    }
    
    // Case 3: Word ke baad palindrome suffix
    for (int palinIdx : node.suffixPalindromes) {
        if (palinIdx != idx) {
            result.add(Arrays.asList(idx, palinIdx));
        }
    }
}

private boolean isPalindrome(String s, int left, int right) {
    while (left < right) {
        if (s.charAt(left++) != s.charAt(right--)) return false;
    }
    return true;
}

class TrieNode {
    TrieNode[] children = new TrieNode[26];
    int wordIndex = -1;
    List<Integer> suffixPalindromes = new ArrayList<>();
    boolean isEnd = false;
}
```


- Words ka reverse Trie mein daalo
- Har word ke liye check karo
- 3 cases: exact match, prefix match, suffix match

---

### 8. Stream of Characters
**Problem:** Check if any word in dictionary is suffix of current stream.

```java
class StreamChecker {
    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isEnd = false;
    }
    
    TrieNode root;
    StringBuilder stream;
    
    public StreamChecker(String[] words) {
        root = new TrieNode();
        stream = new StringBuilder();
        
        // Insert words in reverse order
        for (String word : words) {
            TrieNode node = root;
            for (int i = word.length() - 1; i >= 0; i--) {
                int idx = word.charAt(i) - 'a';
                if (node.children[idx] == null) {
                    node.children[idx] = new TrieNode();
                }
                node = node.children[idx];
            }
            node.isEnd = true;
        }
    }
    
    public boolean query(char letter) {
        stream.append(letter);
        TrieNode node = root;
        
        for (int i = stream.length() - 1; i >= 0; i--) {
            int idx = stream.charAt(i) - 'a';
            if (node.children[idx] == null) return false;
            node = node.children[idx];
            if (node.isEnd) return true;
        }
        return false;
    }
}
```


- Words ko reverse karke Trie mein daalo
- Stream ko reverse order mein traverse karo
- Agar koi word end mila toh true

---

### 9. Map Sum Pairs
**Problem:** Insert key-value pairs, get sum of values for keys with given prefix.

```java
class MapSum {
    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        int value = 0;
        int sum = 0;
    }
    
    TrieNode root;
    Map<String, Integer> map;
    
    public MapSum() {
        root = new TrieNode();
        map = new HashMap<>();
    }
    
    public void insert(String key, int val) {
        int delta = val - map.getOrDefault(key, 0);
        map.put(key, val);
        
        TrieNode node = root;
        for (char c : key.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }
            node = node.children[idx];
            node.sum += delta;
        }
        node.value = val;
    }
    
    public int sum(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) return 0;
            node = node.children[idx];
        }
        return node.sum;
    }
}
```


- Har node mein `sum` store karo (prefix sum)
- Update karte waqt delta calculate karo
- Prefix sum direct node se mil jayega

---

### 10. Concatenated Words
**Problem:** Find words that can be formed by concatenating other words.

```java
public List<String> findAllConcatenatedWordsInADict(String[] words) {
    List<String> result = new ArrayList<>();
    Set<String> dict = new HashSet<>(Arrays.asList(words));
    
    for (String word : words) {
        if (canForm(word, dict)) {
            result.add(word);
        }
    }
    return result;
}

private boolean canForm(String word, Set<String> dict) {
    if (word.isEmpty()) return false;
    
    int n = word.length();
    boolean[] dp = new boolean[n + 1];
    dp[0] = true;
    
    for (int i = 1; i <= n; i++) {
        for (int j = 0; j < i; j++) {
            if (dp[j] && dict.contains(word.substring(j, i))) {
                // Ensure we're not using the whole word itself
                if (!(j == 0 && i == n)) {
                    dp[i] = true;
                    break;
                }
            }
        }
    }
    return dp[n];
}
```


- DP approach
- `dp[i]` = can first i chars be formed by concatenation
- Ensure whole word itself is not counted as concatenation

---

## 📊 Problem Difficulty Summary

| Problem | Difficulty | Key Technique |
|---------|------------|---------------|
| Implement Trie | Easy | Basic operations |
| Add and Search Word | Medium | DFS with '.' |
| Longest Word in Dictionary | Medium | DFS on Trie |
| Word Search II | Hard | Trie + Backtracking |
| Replace Words | Medium | Prefix matching |
| Maximum XOR | Medium | Bitwise Trie |
| Palindrome Pairs | Hard | Reverse Trie + 3 cases |
| Stream of Characters | Hard | Reverse Trie |
| Map Sum Pairs | Medium | Node sum storage |
| Concatenated Words | Hard | DP + Set |

---

## ⚡ Pro Tips

1. **Space optimization** - Agar memory kam hai toh `HashMap<Character, TrieNode>` use karo instead of array[26]
2. **Delete operation** - Rarely asked, but reference count maintain karna helpful hai
3. **Bitwise Trie** - Maximum XOR problems mein 32-bit Trie use hota hai
4. **Reverse Trie** - Palindrome aur suffix problems mein helpful
5. **Auto-complete** - DFS se saare words collect karo
