# Implement Trie (Prefix Tree)

Ek **Trie** (jise **Prefix Tree** bhi kehte hain) ek tree data structure hai jo strings ko efficient tareeke se store aur retrieve karne ke liye use hota hai. Yeh khaas kar **prefix-related queries** jaise **autocomplete** aur **spell checking** ke liye bohot useful hai.

Aapko `Trie` class ko nimnlikhit functionalities ke saath implement karna hai:

  * `Trie()`: Yeh **trie object** ko initialize karta hai.
  * `insert(String word)`: Yeh diye gaye **string `word`** ko trie mein insert karta hai.
  * `search(String word)`: Yeh `true` return karta hai agar `word` pehle insert kiya gaya tha, varna `false`.
  * `startsWith(String prefix)`: Yeh `true` return karta hai agar koi bhi previously inserted word diye gaye **prefix `prefix`** se shuru hota hai.

### Example

Input:

```
["Trie", "insert", "search", "search", "startsWith", "insert", "search"]
[[], ["apple"], ["apple"], ["app"], ["app"], ["app"], ["app"]]
```

Output:

```
[null, null, true, false, true, null, true]
```

### Java Code with Comments

```java
public class Trie {

    // Inner class jo Trie ke har node ko represent karti hai.
    // Har node ek character ko represent karta hai.
    class TrieNode {
        // children array: Har index 'a' se 'z' tak ke ek character ko represent karta hai.
        // Agar children[i] null hai, iska matlab is node se 'a'+i character ke liye koi path nahi hai.
        TrieNode[] children = new TrieNode[26]; // Lowercase English letters 'a' through 'z' ke liye

        // isEndOfWord: Yeh boolean flag batata hai ki kya yeh node kisi poore word ka aakhri character hai.
        // Example: Agar "app" insert kiya hai, toh 'p' wale node par isEndOfWord true hoga.
        // "apple" insert karne par 'e' wale node par bhi true hoga.
        boolean isEndOfWord = false;
    }

    // Trie ka root node. Yeh empty string ko represent karta hai.
    private TrieNode root;

    // Constructor: Trie object ko initialize karta hai.
    public Trie() {
        root = new TrieNode(); // Ek naya root node banate hain
    }

    /**
     * Ek word ko trie mein insert karta hai.
     * Har character ke liye, hum us character ke corresponding child node par jaate hain.
     * Agar child node exist nahi karta, toh hum use create karte hain.
     * Jab word ke saare characters travers ho jaate hain, toh aakhri node ko isEndOfWord = true mark karte hain.
     *
     * Time Complexity: O(L) jahan L word ki length hai.
     * Space Complexity: O(L) worst case mein (agar naye nodes banaye jaate hain).
     */
    public void insert(String word) {
        TrieNode node = root; // Root node se shuru karte hain
        for (char c : word.toCharArray()) { // Word ke har character par iterate karte hain
            int index = c - 'a'; // Character ko 0-25 ke index mein map karte hain ('a' -> 0, 'b' -> 1, ...)
            if (node.children[index] == null) {
                // Agar is character ke liye child node exist nahi karta, toh naya node banate hain
                node.children[index] = new TrieNode();
            }
            // Ab current node ko child node par move karte hain
            node = node.children[index];
        }
        // Jab word ke saare characters travers ho jaate hain, toh current node ko mark karte hain
        // ki yeh ek poore word ka ant hai.
        node.isEndOfWord = true;
    }

    /**
     * Check karta hai ki kya diya gaya word trie mein मौजूद hai.
     * Hum root se shuru karte hain aur word ke har character ke liye child node par jaate hain.
     * Agar kisi bhi point par character ka corresponding child node null milta hai,
     * toh iska matlab word trie mein nahi hai, toh false return karte hain.
     * Agar saare characters travers ho jaate hain, toh hum check karte hain ki
     * aakhri node isEndOfWord flag true hai ya nahi.
     *
     * Time Complexity: O(L) jahan L word ki length hai.
     * Space Complexity: O(1) (koi extra space use nahi hota search ke liye, bas pointers move karte hain).
     */
    public boolean search(String word) {
        TrieNode node = root; // Root node se shuru karte hain
        for (char c : word.toCharArray()) { // Word ke har character par iterate karte hain
            int index = c - 'a'; // Character ko index mein map karte hain
            if (node.children[index] == null) {
                // Agar is character ke liye path exist nahi karta, toh word trie mein nahi hai
                return false;
            }
            // Current node ko child node par move karte hain
            node = node.children[index];
        }
        // Agar saare characters successfully travers ho gaye hain,
        // toh check karte hain ki kya yeh node ek poore word ka ant hai.
        return node.isEndOfWord;
    }

    /**
     * Check karta hai ki kya koi previously inserted word diye gaye prefix se shuru hota hai.
     * Yeh 'search' method ke similar hai, lekin yahan hum isEndOfWord flag ko check nahi karte hain.
     * Bas yeh dekhte hain ki kya prefix ka path trie mein exist karta hai.
     *
     * Time Complexity: O(L) jahan L prefix ki length hai.
     * Space Complexity: O(1).
     */
    public boolean startsWith(String prefix) {
        TrieNode node = root; // Root node se shuru karte hain
        for (char c : prefix.toCharArray()) { // Prefix ke har character par iterate karte hain
            int index = c - 'a'; // Character ko index mein map karte hain
            if (node.children[index] == null) {
                // Agar is character ke liye path exist nahi karta, toh koi word is prefix se shuru nahi hota
                return false;
            }
            // Current node ko child node par move karte hain
            node = node.children[index];
        }
        // Agar saare characters successfully travers ho gaye hain, iska matlab prefix ka path exist karta hai.
        return true;
    }
}
```

-----

# Design Add and Search Words Data Structure

Ek data structure design karo jo new words add karne aur search karne ko support karta hai ki kya koi string kisi previously added string se match karti hai, jahan search word mein dot `'.'` character ho sakta hai jo kisi bhi letter ko represent karta hai.

Aapko `WordDictionary` class ko nimnlikhit functionalities ke saath implement karna hai:

  * `WordDictionary()`: Yeh data structure ko initialize karta hai.
  * `addWord(word)`: Yeh **string `word`** ko data structure mein add karta hai.
  * `search(word)`: Yeh `true` return karta hai agar koi previously added word `word` se match karta hai (jahan `'.'` kisi bhi letter ko match kar sakta hai), varna `false`.

### Example

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
wordDictionary.search("pad"); // returns false (kyunki "pad" add nahi kiya gaya tha)
wordDictionary.search("bad"); // returns true (kyunki "bad" add kiya gaya tha)
wordDictionary.search(".ad"); // returns true (kyunki "bad", "dad", "mad" sab ".ad" se match karte hain)
wordDictionary.search("b.."); // returns true (kyunki "bad" "b.." se match karta hai)
```

### Java Code with Comments

```java
// TrieNode class ko public banaya gaya hai, takay WordDictionary class mein use kiya ja sake.
// Isse alag file mein bhi define kar sakte hain, ya WordDictionary ke andar bhi.
public class TrieNode {
    TrieNode[] children;  // Har child node ek character 'a' se 'z' ko represent karta hai.
    boolean word;         // True agar yeh node kisi poore word ka ant hai.

    public TrieNode() {
        children = new TrieNode[26]; // 26 letters ke liye array
        word = false; // By default, koi word yahan khatam nahi hota
    }
}

class WordDictionary {

    private TrieNode root; // Trie ka root node

    // Constructor: WordDictionary object ko initialize karta hai.
    public WordDictionary() {
        root = new TrieNode(); // Naya root node banate hain
    }

    /**
     * Ek word ko data structure mein add karta hai.
     * Yeh functionality simple Trie ke 'insert' method jaisi hai.
     * Har character ke liye path banate hain, aur aakhri node ko mark karte hain.
     *
     * Time Complexity: O(L) jahan L word ki length hai.
     * Space Complexity: O(L) worst case mein.
     */
    public void addWord(String word) {
        TrieNode cur = root; // Root node se shuru karo
        for (char c : word.toCharArray()) { // Word ke har character par iterate karo
            if (cur.children[c - 'a'] == null) {
                // Agar character ke liye child node nahi hai, toh create karo
                cur.children[c - 'a'] = new TrieNode();
            }
            cur = cur.children[c - 'a']; // Agle node par move karo
        }
        cur.word = true; // Aakhri node ko mark karo as end of a word
    }

    /**
     * Ek word ko search karta hai, jismein '.' wildcard character ho sakta hai.
     * Yeh ek rekursive Depth-First Search (DFS) function use karta hai.
     *
     * Time Complexity:
     * Worst case: O(26^L) agar word mein bohot saare '.' hain (L = word length),
     * kyunki har '.' ke liye 26 branches explore ho sakti hain.
     * Average case: O(L) agar wildcards kam ya nahi hain.
     * Space Complexity: O(L) recursion stack space ke liye.
     */
    public boolean search(String word) {
        return dfs(word, 0, root); // DFS helper function ko call karo
    }

    /**
     * Depth-First Search (DFS) helper function jo '.' wildcard matching ko support karti hai.
     *
     * @param word  Search kiya jane wala word.
     * @param index Current character ka index jise hum process kar rahe hain.
     * @param node  Current TrieNode jahan se search shuru ho rahi hai.
     * @return True agar match milta hai, varna false.
     */
    private boolean dfs(String word, int index, TrieNode node) {
        // Jab hum word ke end tak pahunch jaate hain
        if (index == word.length()) {
            return node.word; // Check karo ki kya yeh node ek poore word ka ant hai
        }

        char c = word.charAt(index); // Current character

        if (c == '.') {
            // Agar character '.' hai, toh current node ke saare children ko explore karo
            for (TrieNode child : node.children) {
                // Agar child null nahi hai (yani, path exist karta hai)
                // aur us child se aage ke word ka match milta hai (recursive call)
                if (child != null && dfs(word, index + 1, child)) {
                    return true; // Match mil gaya
                }
            }
            return false; // Koi bhi child '.' ko match nahi kar paya
        } else {
            // Agar character normal letter hai
            // Check karo ki kya is character ke liye child node exist karta hai
            if (node.children[c - 'a'] == null) {
                return false; // Path exist nahi karta, no match
            }
            // Agle character aur corresponding child node ke liye recursive call karo
            return dfs(word, index + 1, node.children[c - 'a']);
        }
    }
}
```

-----

# Word Search II

Aapko `m x n` characters ka ek `board` aur strings ki ek `words` list di gayi hai. `board` par mile saare words ko return karo.

Har word ko **sequentially adjacent cells** (horizontally ya vertically neighboring) ke letters se banana hoga. Ek hi letter cell ko ek word mein ek se zyada baar use nahi kiya ja sakta.

### Example

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

### Java Code with Comments

```java
// TrieNode class ko public banaya gaya hai, takay Solution class mein use kiya ja sake.
class TrieNode {
    TrieNode[] children = new TrieNode[26];  // 'a' se 'z' tak ke children nodes.
    int idx = -1;     // Agar yeh node kisi word ka ant hai, toh us word ka original index 'words' array mein store karte hain.
                      // Warna -1.
    int refs = 0;     // Reference count: Yeh batata hai ki kitne words (ya prefixes) is node se hokar guzarte hain.
                      // Iska use Trie nodes ko prune (delete) karne ke liye kiya jata hai.

    // Ek word ko Trie mein add karta hai, uske original index ke saath.
    public void addWord(String word, int i) {
        TrieNode cur = this; // Current node ko 'this' (jo ki root node hoga) se initialize karo.
        cur.refs++; // Root node ka reference count badhao.

        for (char c : word.toCharArray()) { // Word ke har character par iterate karo.
            int index = c - 'a';
            if (cur.children[index] == null) {
                // Agar is character ke liye child node nahi hai, toh create karo.
                cur.children[index] = new TrieNode();
            }
            cur = cur.children[index]; // Agle node par move karo.
            cur.refs++; // Har node ka reference count badhao jisse yeh word guzarta hai.
        }
        cur.idx = i; // Jab word khatam ho jaye, toh aakhri node par word ka index store karo.
    }
}

public class Solution {
    List<String> res = new ArrayList<>(); // Found words ko store karne ke liye list.

    public List<String> findWords(char[][] board, String[] words) {
        TrieNode root = new TrieNode();
        // Sabhi words ko Trie mein add karo. Isse hum board par search karte samay efficient prefix matching kar payenge.
        for (int i = 0; i < words.length; i++) {
            root.addWord(words[i], i);
        }

        // Board ke har cell se DFS (Depth-First Search) shuru karo.
        // Har cell ek word ka starting point ho sakta hai.
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                dfs(board, root, r, c, words);
            }
        }
        return res; // Saare found words return karo.
    }

    /**
     * Recursive DFS function jo board par words search karti hai.
     *
     * @param board The character board.
     * @param node  Current TrieNode jo current character sequence ko represent karta hai.
     * @param r     Current row index on the board.
     * @param c     Current column index on the board.
     * @param words Original array of words, jismein se indices refer kiye jayenge.
     */
    private void dfs(char[][] board, TrieNode node, int r, int c, String[] words) {
        // Boundary conditions check:
        // 1. Cell board ke andar hai ya nahi.
        // 2. Cell pehle se visited ('*') hai ya nahi.
        // 3. Current character ke liye Trie mein path exist karta hai ya nahi (pruning).
        if (r < 0 || c < 0 || r >= board.length || c >= board[0].length ||
            board[r][c] == '*' || node.children[board[r][c] - 'a'] == null) {
            return; // Invalid move ya path nahi hai, return.
        }

        char temp = board[r][c]; // Current cell ke character ko temp mein store karo.
        board[r][c] = '*'; // Current cell ko '*' se mark karo, takay dobara visit na ho is path mein.

        // Trie mein agle node par move karo.
        // 'prev' node ko save kiya gaya hai pruning ke liye.
        TrieNode prev = node;
        node = node.children[temp - 'a'];

        // Check karo ki kya current node kisi poore word ka ant hai.
        if (node.idx != -1) {
            res.add(words[node.idx]); // Agar haan, toh word ko result list mein add karo.
            node.idx = -1;           // Word ko mark karo as found (-1), takay dobara add na ho.
        }

        // Reference count ko decrement karo.
        // Agar word mil gaya hai, toh is path ka ek word complete ho gaya, so references for this path reduce.
        // Ye pruning ke liye crucial hai.
        node.refs--;

        // Pruning: Agar current Trie node ka reference count 0 ho gaya hai
        // (yani, koi bhi baki word ya prefix is path se nahi guzarta hai),
        // toh is node ko Trie se hata do (memory optimize karne ke liye).
        if (node.refs == 0) {
            // Is node ko null set karo,effectively isko Trie se delete kar do.
            // Aur parent node ke children array mein bhi is entry ko null kar do.
            prev.children[temp - 'a'] = null;
            board[r][c] = temp; // Board character ko restore karo recursion se wapas aate samay.
            return; // Is path se aage search karne ka koi matlab nahi hai.
        }

        // Charo directions mein DFS call karo (up, down, left, right).
        dfs(board, node, r + 1, c, words); // Down
        dfs(board, node, r - 1, c, words); // Up
        dfs(board, node, r, c + 1, words); // Right
        dfs(board, node, r, c - 1, words); // Left

        // Recursion call return hone ke baad board character ko restore karo.
        // Yeh backtracking ke liye zaroori hai takay doosre paths is cell ko use kar sakein.
        board[r][c] = temp;
    }
}
```
