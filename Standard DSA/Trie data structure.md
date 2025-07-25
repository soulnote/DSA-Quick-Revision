## 🧠 What is a Trie?

A **Trie** (pronounced "try") is a special **tree-like data structure** used to **store a collection of words or strings**, especially when many of them share **common prefixes**.

It is mainly used for:

* 🔍 **Searching words quickly** (like in autocomplete)
* 📚 **Storing a dictionary**
* 🔠 **Prefix searching** (like "How many words start with 'ap'?")

---

## 📦 Real-Life Analogy

Imagine a **contact list** in your phone. If you type **"Al"**, it shows:

* Alice
* Allen
* Albert

It gives suggestions starting with that prefix. Behind the scenes, something like a **Trie** might be used!

---

## 📐 Basic Structure of a Trie

Each node in a Trie:

* Represents a **character**.
* Has **children nodes** for possible next characters.
* Has a flag to say if a **word ends** here.

### Example:

Let's insert the words: `"cat"`, `"car"`, and `"cap"` into a Trie.

```
        (root)
         |
         c
         |
         a
     /   |   \
    t    r    p
(end) (end) (end)
```

* All three words share the prefix `"ca"`.
* After that, they branch out: `"t"`, `"r"`, and `"p"`.

---

## ⚙️ Basic Trie Operations

### 1. **Insert a word**

Go character by character:

* If the character doesn't exist, create a node.
* Move to the next character.
* Mark the end of the word.

### 2. **Search a word**

* Follow character by character.
* If any character doesn’t exist, the word is not in the Trie.
* Check if it's a word end.

### 3. **Starts with a prefix**

* Same as search, but we **don’t care** if it’s the end of a word.

---

## 🧑‍💻 Simple Trie Code in Java

```java
class TrieNode {
    TrieNode[] children = new TrieNode[26]; // For 'a' to 'z'
    boolean isEndOfWord = false;
}

class Trie {
    TrieNode root = new TrieNode();

    // Insert a word
    void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a'; // Map 'a' to 0, 'b' to 1, etc.
            if (node.children[index] == null)
                node.children[index] = new TrieNode();
            node = node.children[index];
        }
        node.isEndOfWord = true;
    }

    // Search a word
    boolean search(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (node.children[index] == null)
                return false;
            node = node.children[index];
        }
        return node.isEndOfWord;
    }

    // Check if prefix exists
    boolean startsWith(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            int index = c - 'a';
            if (node.children[index] == null)
                return false;
            node = node.children[index];
        }
        return true;
    }
}
```

---

## 📝 Summary

| Operation     | Time Complexity |
| ------------- | --------------- |
| Insert word   | O(L)            |
| Search word   | O(L)            |
| Search prefix | O(L)            |

(L = length of the word or prefix)



## 🧩 Problem: Implement Trie (Prefix Tree)

A **Trie** (pronounced "try") is a **tree-like data structure** that efficiently stores and retrieves strings, especially when many of them share **common prefixes**.

### 🚩 You need to implement a class `Trie` with the following methods:

* `Trie()` – Initializes the trie object.
* `void insert(String word)` – Inserts the word into the trie.
* `boolean search(String word)` – Returns `true` if the word exists in the trie.
* `boolean startsWith(String prefix)` – Returns `true` if **any word** in the trie starts with the given prefix.

---

## ✅ Solution: Trie using Tree Structure

We’ll use a **TrieNode** class to represent each node in the trie:

* Each node stores **26 children** (for lowercase 'a' to 'z').
* Each node has a **flag** to mark if it is the **end of a word**.

We then implement the three operations using simple traversal logic.

---

### 💡 Java Implementation

```java
class Trie {

    // Each node of the Trie
    class TrieNode {
        TrieNode[] children = new TrieNode[26]; // One for each letter
        boolean isEndOfWord = false; // True if this node marks the end of a word
    }

    private TrieNode root;

    // Constructor: initialize root
    public Trie() {
        root = new TrieNode();
    }

    // Inserts a word into the trie.
    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a'; // Map character to index
            if (node.children[index] == null) {
                node.children[index] = new TrieNode(); // Create node if not present
            }
            node = node.children[index]; // Move to the next node
        }
        node.isEndOfWord = true; // Mark the end of the word
    }

    // Returns true if the word is in the trie.
    public boolean search(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (node.children[index] == null) {
                return false; // Character path doesn’t exist
            }
            node = node.children[index];
        }
        return node.isEndOfWord; // Must end at a word boundary
    }

    // Returns true if there is any word in the trie that starts with the given prefix.
    public boolean startsWith(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            int index = c - 'a';
            if (node.children[index] == null) {
                return false; // Prefix path doesn't exist
            }
            node = node.children[index];
        }
        return true; // Prefix exists in trie
    }
}
```

---

### 🧪 Example Usage

```java
public class Main {
    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("apple");
        System.out.println(trie.search("apple"));   // true
        System.out.println(trie.search("app"));     // false
        System.out.println(trie.startsWith("app")); // true
        trie.insert("app");
        System.out.println(trie.search("app"));     // true
    }
}
```

---

### ⏱ Time & Space Complexity

| Operation    | Time Complexity | Space Complexity |
| ------------ | --------------- | ---------------- |
| insert(word) | O(L)            | O(L)             |
| search(word) | O(L)            | O(1)             |
| startsWith() | O(L)            | O(1)             |

> Where **L = length of the word or prefix**


## 🧩 Problem: Design Add and Search Words Data Structure

We need to implement a class `WordDictionary` that supports:

1. `WordDictionary()` – Initializes the object.
2. `void addWord(String word)` – Adds a word into the data structure.
3. `boolean search(String word)` – Returns `true` if **any previously added word** matches the given `word`, where:

   * `word` may contain the special character `.` (dot), which can match **any single letter**.

---

### 📌 Key Constraint

* The search word **can contain the dot `.`**, which works like a **wildcard**.
* So `"d.g"` should match `"dog"`, `"dig"`, `"dug"`, etc., **if** those words were added before.

---

## ✅ Solution: Trie + DFS for Wildcard Support

### 🔧 Why Trie?

We use a **Trie** (Prefix Tree) to store words efficiently:

* Shared prefixes take less space.
* Search can be done **character by character**.
* Great for string-based problems.

### 🔍 Why DFS (Depth-First Search)?

We need DFS because:

* When we encounter a dot (`.`), we must **try all 26 possible characters** at that level.
* This is like **branching recursively** — hence, we use DFS.

---

## 💡 Step-by-Step Java Implementation

### 🧱 Step 1: Define the Trie Node

```java
class TrieNode {
    TrieNode[] children = new TrieNode[26]; // For 'a' to 'z'
    boolean isEndOfWord = false; // True if word ends here
}
```

### 🏗 Step 2: WordDictionary Class

```java
class WordDictionary {

    private TrieNode root;

    public WordDictionary() {
        root = new TrieNode(); // Start with an empty root node
    }

    // Add a word to the Trie
    public void addWord(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a'; // Map 'a'-'z' to 0-25
            if (node.children[index] == null) {
                node.children[index] = new TrieNode(); // Create if not exist
            }
            node = node.children[index]; // Move to next node
        }
        node.isEndOfWord = true; // Mark end of word
    }

    // Search with support for '.' wildcard
    public boolean search(String word) {
        return dfs(word, 0, root);
    }

    // Helper function: DFS for searching
    private boolean dfs(String word, int index, TrieNode node) {
        // Base case: end of the word
        if (index == word.length()) {
            return node.isEndOfWord;
        }

        char c = word.charAt(index);

        if (c == '.') {
            // Try all 26 letters if '.' is found
            for (int i = 0; i < 26; i++) {
                if (node.children[i] != null) {
                    if (dfs(word, index + 1, node.children[i])) {
                        return true;
                    }
                }
            }
            return false; // No match found
        } else {
            int childIndex = c - 'a';
            TrieNode nextNode = node.children[childIndex];
            if (nextNode == null) return false;
            return dfs(word, index + 1, nextNode);
        }
    }
}
```

---

## 🧪 Example Usage

```java
public class Main {
    public static void main(String[] args) {
        WordDictionary dict = new WordDictionary();
        dict.addWord("bad");
        dict.addWord("dad");
        dict.addWord("mad");

        System.out.println(dict.search("pad")); // false
        System.out.println(dict.search("bad")); // true
        System.out.println(dict.search(".ad")); // true (matches "bad", "dad", "mad")
        System.out.println(dict.search("b..")); // true (matches "bad")
    }
}
```

---

## 🔍 Explanation for Beginners

### addWord("bad")

* Adds `'b' → 'a' → 'd'` into the Trie.
* The `'d'` node is marked as the **end of a word**.

### search("b..")

* `'b'` → direct match.
* `'.'` → try all children at the second level (we try all letters from `'a'` to `'z'`).
* `'.'` again → try all children of the third level.
* If we find a valid path that ends in a word, return `true`.

---

## ⏱ Time and Space Complexity

| Operation         | Time Complexity | Space Complexity |
| ----------------- | --------------- | ---------------- |
| addWord           | O(L)            | O(L) per word    |
| search (no `.`)   | O(L)            |                  |
| search (with `.`) | Worst: O(26^d)  |                  |

* `L` = length of the word
* `d` = number of dots (`.`) — each dot branches into 26 possibilities.


## 🧩 Problem: Word Search II

You're given:

* A 2D grid `board` of characters (size `m x n`)
* A list of words `words`

### ✅ Goal:

Return **all the words** from `words` that can be formed by a **path of adjacent** cells in `board`.

### 📌 Rules:

* Letters must be **adjacent** (horizontally or vertically).
* **A cell cannot be reused** in the same word.

---

## ✅ Solution: Trie + DFS Backtracking

### 🔧 Why Trie?

* Avoids redundant search: Common prefixes are shared, so we **don’t repeat work** for similar starting letters.
* Early termination: If the current path isn't a prefix to any word, we can **stop DFS early**.

### 🔍 Why DFS?

* We need to **explore every possible path** from each cell to build a word.
* DFS helps explore all valid neighbor cells recursively.

---

## 💡 Step-by-Step Java Implementation

### 🧱 Step 1: Define the Trie Node

```java
class TrieNode {
    TrieNode[] children = new TrieNode[26];
    String word = null; // Store full word at end node
}
```

### 🏗 Step 2: Full Solution Class

```java
class Solution {
    public List<String> findWords(char[][] board, String[] words) {
        List<String> result = new ArrayList<>();

        // Step 1: Build Trie from words list
        TrieNode root = buildTrie(words);

        // Step 2: Search each cell
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                dfs(board, row, col, root, result);
            }
        }

        return result;
    }

    private void dfs(char[][] board, int row, int col, TrieNode node, List<String> result) {
        char c = board[row][col];

        // Out of bounds or visited
        if (c == '#' || node.children[c - 'a'] == null) return;

        node = node.children[c - 'a'];

        // Found a word
        if (node.word != null) {
            result.add(node.word);
            node.word = null; // Avoid duplicates
        }

        // Mark visited
        board[row][col] = '#';

        // Visit neighbors (up, down, left, right)
        int[] dx = { -1, 1, 0, 0 };
        int[] dy = { 0, 0, -1, 1 };

        for (int i = 0; i < 4; i++) {
            int newRow = row + dx[i];
            int newCol = col + dy[i];

            if (newRow >= 0 && newRow < board.length &&
                newCol >= 0 && newCol < board[0].length) {
                dfs(board, newRow, newCol, node, result);
            }
        }

        // Backtrack: restore cell
        board[row][col] = c;
    }

    private TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode();

        for (String word : words) {
            TrieNode node = root;

            for (char c : word.toCharArray()) {
                int index = c - 'a';
                if (node.children[index] == null) {
                    node.children[index] = new TrieNode();
                }
                node = node.children[index];
            }

            node.word = word; // Mark end of word
        }

        return root;
    }
}
```

---

## 🧠 Explanation for Beginners

### Step-by-Step:

1. **Build a Trie**:

   * Insert all words from the list into a Trie.
   * Store the full word at the terminal node for easy retrieval.

2. **Run DFS from every cell in the board**:

   * Start DFS only if the character exists as a child in the Trie.
   * Move in 4 directions (up, down, left, right).
   * Use `'#'` to mark visited cells and **backtrack** to restore them.

3. **Collect results**:

   * When we reach a Trie node with `node.word != null`, we’ve found a full word.
   * Add to result and set `node.word = null` to avoid duplicates.

---

## 🧪 Example

```java
char[][] board = {
  {'o','a','a','n'},
  {'e','t','a','e'},
  {'i','h','k','r'},
  {'i','f','l','v'}
};
String[] words = {"oath","pea","eat","rain"};

Solution sol = new Solution();
System.out.println(sol.findWords(board, words));
// Output: ["oath", "eat"]
```

---

## ⏱ Time and Space Complexity

### Time:

* **Building Trie:** O(N × L), where N = number of words, L = average word length
* **DFS Search:** Worst case O(M × N × 4^L), where M × N is the board size, and L is max word length

### Space:

* Trie: O(N × L)
* Recursion stack: O(L)


## 🧩 Problem: Word Search II (optimised)

You're given:

* A 2D board `board` of characters
* A list of strings `words`

### ✅ Objective:

Find all words from `words` that can be formed in the board using **adjacent letters** (horizontally or vertically), **without reusing** any letter cell.

---

## ✅ Solution: Trie + DFS

You’ve implemented a smart and efficient algorithm that uses:

* A **Trie** to store all `words`
* **DFS backtracking** from each cell to explore potential matches
* **Early pruning** using the Trie to avoid unnecessary work

---

## 🔍 Deep Explanation for Beginners

### 🎯 Step 1: Build a Trie from Words

We construct a **prefix tree** where each path is a word from the list.

```java
class TrieNode {
    TrieNode[] next = new TrieNode[26]; // for 'a' to 'z'
    String word; // non-null if this node completes a word
}
```

```java
public TrieNode buildTrie(String[] words) {
    TrieNode root = new TrieNode();
    for (String w : words) {
        TrieNode p = root;
        for (char c : w.toCharArray()) {
            int i = c - 'a';
            if (p.next[i] == null) p.next[i] = new TrieNode(); // Create node
            p = p.next[i];
        }
        p.word = w; // Store word at end node
    }
    return root;
}
```

---

### 🧭 Step 2: DFS from Each Cell

We perform **Depth-First Search** from every cell on the board.

#### Key Logic:

* We **match one character at a time**
* When we find a **complete word**, we:

  * Add it to result
  * Set `p.word = null` to avoid duplicates

```java
public void dfs(char[][] board, int i, int j, TrieNode p, List<String> res) {
    char c = board[i][j];

    // If out of bounds or already visited or no prefix match, stop
    if (c == '#' || p.next[c - 'a'] == null) return;

    p = p.next[c - 'a']; // Move down the Trie

    if (p.word != null) {   // Complete word found
        res.add(p.word);
        p.word = null;      // Avoid duplicate additions
    }

    board[i][j] = '#'; // Mark current cell as visited

    // Explore all 4 directions
    if (i > 0) dfs(board, i - 1, j ,p, res); 
    if (j > 0) dfs(board, i, j - 1, p, res);
    if (i < board.length - 1) dfs(board, i + 1, j, p, res); 
    if (j < board[0].length - 1) dfs(board, i, j + 1, p, res); 

    board[i][j] = c; // Backtrack (restore original character)
}
```

---

### 🚀 Final Driver Method

This method:

* Builds the Trie
* Starts DFS from every cell
* Collects matched words

```java
public List<String> findWords(char[][] board, String[] words) {
    List<String> res = new ArrayList<>();
    TrieNode root = buildTrie(words);

    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[0].length; j++) {
            dfs(board, i, j, root, res);
        }
    }

    return res;
}
```

---

## 🧪 Example

```java
char[][] board = {
  {'o','a','a','n'},
  {'e','t','a','e'},
  {'i','h','k','r'},
  {'i','f','l','v'}
};
String[] words = {"oath","pea","eat","rain"};

Solution sol = new Solution();
System.out.println(sol.findWords(board, words));
// Output: ["oath", "eat"]
```

---

## ⏱ Time and Space Complexity

| Operation     | Complexity                                 |
| ------------- | ------------------------------------------ |
| Build Trie    | O(N × L), N = number of words, L = avg len |
| DFS Traversal | Worst-case: O(M × N × 4^L)                 |
| Space (Trie)  | O(N × L)                                   |
| Space (DFS)   | O(L) recursion stack                       |

---

### ✅ Key Strengths of This Approach:

* **Prunes early** when paths don't lead to valid prefixes
* **Avoids duplicate words** using `p.word = null`
* **Efficient** due to Trie + backtracking combination

