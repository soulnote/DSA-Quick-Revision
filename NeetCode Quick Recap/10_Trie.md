# ⭐ Implement Trie (Prefix Tree)


A trie (prefix tree) is a tree data structure used to efficiently store and retrieve strings, especially for prefix-related queries like autocomplete and spell checking.

Implement the `Trie` class with the following functionalities:

* `Trie()` initializes the trie object.
* `insert(String word)` inserts the string `word` into the trie.
* `search(String word)` returns `true` if `word` was inserted before, otherwise `false`.
* `startsWith(String prefix)` returns `true` if any previously inserted word starts with the prefix `prefix`.
### 📌 Example

Input:

```
["Trie", "insert", "search", "search", "startsWith", "insert", "search"]
[[], ["apple"], ["apple"], ["app"], ["app"], ["app"], ["app"]]
```

Output:

```
[null, null, true, false, true, null, true]
```

### ✅ Java Code with Comments

```java
public class Trie {

    // Inner class representing each node in the Trie
    class TrieNode {
        TrieNode[] children = new TrieNode[26]; // For 'a' to 'z'
        boolean isEndOfWord = false;             // True if node is end of a word
    }

    private TrieNode root;

    public Trie() {
        root = new TrieNode(); // Initialize root node
    }

    // Inserts a word into the trie
    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a'; // Map char to index 0-25
            if (node.children[index] == null) {
                node.children[index] = new TrieNode(); // Create node if absent
            }
            node = node.children[index]; // Move to the child node
        }
        node.isEndOfWord = true; // Mark end of word
    }

    // Returns true if the word is in the trie
    public boolean search(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (node.children[index] == null) {
                return false; // Word path doesn't exist
            }
            node = node.children[index];
        }
        return node.isEndOfWord; // Return true if it's a complete word
    }

    // Returns true if any word starts with the given prefix
    public boolean startsWith(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            int index = c - 'a';
            if (node.children[index] == null) {
                return false; // Prefix path doesn't exist
            }
            node = node.children[index];
        }
        return true; // Prefix found
    }
}
```

**Explanation:**

* Each node has an array of 26 children, one for each lowercase letter.
* `insert` walks down the tree creating nodes if necessary.
* `search` checks if the entire word exists and is marked as an end.
* `startsWith` verifies if the prefix path exists in the trie.

---
# ⭐ Design Add and Search Words Data Structure

Design a data structure that supports adding new words and searching if a string matches any previously added string, where the search word may contain the dot `'.'` character representing any letter.

Implement the `WordDictionary` class with the following functionalities:

* `WordDictionary()` initializes the data structure.
* `addWord(word)` adds the string `word` into the data structure.
* `search(word)` returns `true` if any previously added word matches `word` (where `'.'` can match any letter), otherwise `false`.

### 📌 Example

Input:

```
["WordDictionary","addWord","addWord","addWord","search","search","search","search"]
[[],["bad"],["dad"],["mad"],["pad"],["bad"],[".ad"],["b.."]]
```

Output:

```
[null,null,null,null,false,true,true,true]
```

Explanation:

```java
WordDictionary wordDictionary = new WordDictionary();
wordDictionary.addWord("bad");
wordDictionary.addWord("dad");
wordDictionary.addWord("mad");
wordDictionary.search("pad"); // returns false
wordDictionary.search("bad"); // returns true
wordDictionary.search(".ad"); // returns true
wordDictionary.search("b.."); // returns true
```

### ✅ Java Code with Comments

```java
public class TrieNode {
    TrieNode[] children;  // Children nodes for each letter a-z
    boolean word;         // True if this node marks the end of a word

    public TrieNode() {
        children = new TrieNode[26];
        word = false;
    }
}

class WordDictionary {
    
    private TrieNode root;

    public WordDictionary() {
        root = new TrieNode(); // Initialize root of Trie
    }

    // Adds a word into the data structure
    public void addWord(String word) {
        TrieNode cur = root;
        for (char c : word.toCharArray()) {
            if (cur.children[c - 'a'] == null) {
                cur.children[c - 'a'] = new TrieNode(); // Create node if absent
            }
            cur = cur.children[c - 'a']; // Move to child node
        }
        cur.word = true; // Mark end of word
    }

    // Searches a word with possible '.' wildcards
    public boolean search(String word) {
        return dfs(word, 0, root);
    }

    // Depth-first search helper to support '.' wildcard matching
    private boolean dfs(String word, int index, TrieNode node) {
        TrieNode cur = node;

        for (int i = index; i < word.length(); i++) {
            char c = word.charAt(i);

            if (c == '.') {
                // If '.', check all possible children nodes
                for (TrieNode child : cur.children) {
                    if (child != null && dfs(word, i + 1, child)) {
                        return true;
                    }
                }
                return false; // No matching child found
            } else {
                // For normal character, check specific child node
                if (cur.children[c - 'a'] == null) {
                    return false; // Path doesn't exist
                }
                cur = cur.children[c - 'a']; // Move forward in trie
            }
        }
        return cur.word; // Check if this node marks end of a valid word
    }
}
```

**Explanation:**

* Uses a Trie (prefix tree) to store words.
* `addWord` inserts characters one-by-one, creating new nodes if needed.
* `search` supports `'.'` wildcard by trying all possible child nodes recursively.
* `dfs` helps recursively explore the trie to match wildcard characters.

---
# ⭐ Word Search II

Given an `m x n` board of characters and a list of strings `words`, return all words found on the board.

Each word must be constructed from letters of sequentially adjacent cells (horizontally or vertically neighboring). The same letter cell cannot be used more than once in a word.

### 📌 Example

Input:

```
board = [
  ["o","a","a","n"],
  ["e","t","a","e"],
  ["i","h","k","r"],
  ["i","f","l","v"]
]

words = ["oath","pea","eat","rain"]
```

Output:

```
["eat","oath"]
```

### ✅ Java Code with Comments

```java
class TrieNode {
    TrieNode[] children = new TrieNode[26];  // Children for 'a' to 'z'
    int idx = -1;   // Index of word if this node marks end of word, else -1
    int refs = 0;   // Reference count to prune Trie nodes

    // Adds a word to the Trie with its index in words array
    public void addWord(String word, int i) {
        TrieNode cur = this;
        cur.refs++;  // Increment reference count for root
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (cur.children[index] == null) {
                cur.children[index] = new TrieNode();
            }
            cur = cur.children[index];
            cur.refs++;  // Increment reference count for each node
        }
        cur.idx = i;  // Mark the end of the word with its index
    }
}

public class Solution {
    List<String> res = new ArrayList<>();

    public List<String> findWords(char[][] board, String[] words) {
        TrieNode root = new TrieNode();
        for (int i = 0; i < words.length; i++) {
            root.addWord(words[i], i);  // Build Trie with all words
        }

        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                dfs(board, root, r, c, words);  // DFS from each cell
            }
        }
        return res;
    }

    private void dfs(char[][] board, TrieNode node, int r, int c, String[] words) {
        // Boundary and visited check, plus Trie path existence check
        if (r < 0 || c < 0 || r >= board.length || c >= board[0].length ||
            board[r][c] == '*' || node.children[board[r][c] - 'a'] == null) {
            return;
        }

        char temp = board[r][c];
        board[r][c] = '*';  // Mark as visited

        TrieNode prev = node;
        node = node.children[temp - 'a'];

        // If this node marks the end of a word, add it to result
        if (node.idx != -1) {
            res.add(words[node.idx]);
            node.idx = -1;    // Avoid duplicate addition
            node.refs--;      // Decrement reference count

            // Prune the Trie if no references left to this node
            if (node.refs == 0) {
                node = null;
                prev.children[temp - 'a'] = null;
                board[r][c] = temp;  // Restore board before return
                return;
            }
        }

        // DFS in 4 directions
        dfs(board, node, r + 1, c, words);
        dfs(board, node, r - 1, c, words);
        dfs(board, node, r, c + 1, words);
        dfs(board, node, r, c - 1, words);

        board[r][c] = temp;  // Restore the character after DFS
    }
}
```

**Explanation:**

* Uses a Trie to store all words for efficient prefix checking.
* `refs` keeps track of how many words use a Trie node to prune it once no words pass through.
* DFS explores all possible paths on the board matching prefixes in the Trie.
* Mark visited cells as `'*'` to avoid revisiting during the current path.
* When a complete word is found, add to result and mark it so it won't be found again.

---
