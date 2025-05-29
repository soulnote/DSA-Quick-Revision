
# ⭐ Clone Graph

Given a reference of a node in a connected undirected graph, return a **deep copy (clone)** of the graph.

Each node contains a value (`int val`) and a list of its neighbors (`List<Node>`).


### 📌 Example

Input:

```
adjList = [[2,4],[1,3],[2,4],[1,3]]

```
![image](https://assets.leetcode.com/uploads/2019/11/04/133_clone_graph_question.png)
Output:

```
[[2,4],[1,3],[2,4],[1,3]]
```

Explanation:

* There are 4 nodes in the graph.
* Node 1’s neighbors are nodes 2 and 4.
* Node 2’s neighbors are nodes 1 and 3.
* Node 3’s neighbors are nodes 2 and 4.
* Node 4’s neighbors are nodes 1 and 3.


### ✅ Java Code with Comments

```java
// Definition for a Node.
class Node {
    public int val;
    public List<Node> neighbors;

    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }

    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }

    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}

class Solution {
    public Node cloneGraph(Node node) {
        if (node == null) {
            return null; // If the graph is empty, return null
        }

        Map<Node, Node> map = new HashMap<>(); // Map original nodes to their clones

        Node clone = new Node(node.val); // Clone the start node
        map.put(node, clone);

        Queue<Node> queue = new LinkedList<>(); // BFS queue
        queue.add(node);

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            // Iterate over each neighbor
            for (Node neighbor : current.neighbors) {
                if (!map.containsKey(neighbor)) {
                    // Clone neighbor if not cloned yet
                    Node neighborClone = new Node(neighbor.val);
                    map.put(neighbor, neighborClone);
                    queue.add(neighbor); // Add neighbor to queue for processing
                }

                // Link clone of current node to clone of neighbor
                map.get(current).neighbors.add(map.get(neighbor));
            }
        }

        return clone; // Return cloned graph starting node
    }
}
```

**Explanation:**

* Use BFS to traverse the original graph starting from the given node.
* Maintain a map to store original nodes and their corresponding cloned nodes.
* For each node, clone its neighbors and link them appropriately.
* Return the clone of the starting node, which leads to the deep cloned graph.

---
Certainly! Here is your **Pacific Atlantic Water Flow** problem formatted as per your requested style:

---

# ⭐ Pacific Atlantic Water Flow

There is an m x n rectangular island that borders both the Pacific Ocean and Atlantic Ocean.

* The Pacific Ocean touches the island's left and top edges.
* The Atlantic Ocean touches the island's right and bottom edges.

You are given an m x n integer matrix `heights` where `heights[r][c]` represents the height above sea level of the cell at coordinate `(r, c)`.

Rain water can flow from a cell to its neighboring cells (north, south, east, and west) if the neighbor's height is **less than or equal to** the current cell's height. Water can flow from any cell adjacent to an ocean into that ocean.

Return a 2D list of grid coordinates where water can flow to **both** the Pacific and Atlantic oceans.


### 📌 Example

Input:

```
heights = [
  [1,2,2,3,5],
  [3,2,3,4,4],
  [2,4,5,3,1],
  [6,7,1,4,5],
  [5,1,1,2,4]
]
```

Output:

```
[[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]]
```

Explanation:

![image](https://assets.leetcode.com/uploads/2021/06/08/waterflow-grid.jpg)

 Cells like `[0,4]`, `[1,3]`, `[1,4]` etc. can flow water to **both** oceans following valid paths according to the height rules.

### ✅ Java Code with Comments

```java
import java.util.*;

class Solution {
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        int m = heights.length, n = heights[0].length;
        int[][] pacific = new int[m][n];
        int[][] atlantic = new int[m][n];
        Queue<int[]> q = new LinkedList<>();

        // Mark and enqueue cells adjacent to Pacific ocean (left and top edges)
        for (int i = 0; i < m; i++) {
            pacific[i][0] = 1;
            q.add(new int[]{i, 0});
        }
        for (int j = 0; j < n; j++) {
            pacific[0][j] = 1;
            q.add(new int[]{0, j});
        }
        bfs(q, 'P', heights, pacific, atlantic);

        q.clear();

        // Mark and enqueue cells adjacent to Atlantic ocean (right and bottom edges)
        for (int j = 0; j < n; j++) {
            atlantic[m - 1][j] = 2;
            q.add(new int[]{m - 1, j});
        }
        for (int i = 0; i < m; i++) {
            atlantic[i][n - 1] = 2;
            q.add(new int[]{i, n - 1});
        }
        bfs(q, 'A', heights, pacific, atlantic);

        List<List<Integer>> ans = new ArrayList<>();
        // Collect cells reachable by both oceans
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (pacific[i][j] == 1 && atlantic[i][j] == 2) {
                    ans.add(Arrays.asList(i, j));
                }
            }
        }
        return ans;
    }

    // Directions arrays for up, down, left, right movements
    int[] dR = new int[]{1, -1, 0, 0};
    int[] dC = new int[]{0, 0, 1, -1};

    // BFS function to traverse and mark reachable cells for a given ocean
    public void bfs(Queue<int[]> q, char ocean, int[][] grid, int[][] pacific, int[][] atlantic) {
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int[] present = q.poll();
                int row = present[0];
                int col = present[1];

                for (int k = 0; k < 4; k++) {
                    int nRow = row + dR[k];
                    int nCol = col + dC[k];

                    if (nRow >= 0 && nRow < grid.length && nCol >= 0 && nCol < grid[0].length
                        && grid[nRow][nCol] >= grid[row][col]) {  // Only flow to higher or equal height
                        
                        if (ocean == 'P' && pacific[nRow][nCol] == 0) {
                            pacific[nRow][nCol] = 1;
                            q.offer(new int[]{nRow, nCol});
                        }
                        if (ocean == 'A' && atlantic[nRow][nCol] == 0) {
                            atlantic[nRow][nCol] = 2;
                            q.offer(new int[]{nRow, nCol});
                        }
                    }
                }
            }
        }
    }
}
```

**Explanation:**

* We perform two BFS traversals — one starting from all Pacific border cells, one from all Atlantic border cells.
* We mark which cells can reach each ocean.
* Finally, cells that can reach both oceans are collected and returned.
* The BFS proceeds only from lower height cells to equal or higher height neighbors to simulate water flow rules.

---
Certainly! Here's the **Course Schedule** problem and solution in your requested format:

---

# ⭐ Course Schedule

You have `numCourses` courses labeled from `0` to `numCourses - 1`.
Given an array `prerequisites` where `prerequisites[i] = [a_i, b_i]` means you must take course `b_i` before course `a_i`.

Return `true` if you can finish all courses, otherwise return `false`.

### 📌 Example

Input:

```
numCourses = 2, prerequisites = [[1, 0]]
```

Output:

```
true
```

Explanation:

* To take course 1, you must first finish course 0.
* Since course 0 has no prerequisites, it is possible to finish all courses.

### ✅ Java Code with Comments

```java
import java.util.*;

class Solution {
    public boolean canFinish(int n, int[][] prerequisites) {
        // Adjacency list to represent graph of courses and their next courses
        List<Integer>[] adj = new List[n];
        // Array to keep track of number of prerequisites (indegree) for each course
        int[] indegree = new int[n];
        // List to record order of courses we can take
        List<Integer> ans = new ArrayList<>();

        // Build the graph and indegree array
        for (int[] pair : prerequisites) {
            int course = pair[0];
            int prerequisite = pair[1];
            if (adj[prerequisite] == null) {
                adj[prerequisite] = new ArrayList<>();
            }
            adj[prerequisite].add(course);
            indegree[course]++;
        }

        // Queue for courses with no prerequisites (indegree = 0)
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }

        // Process courses in topological order
        while (!queue.isEmpty()) {
            int current = queue.poll();
            ans.add(current);

            if (adj[current] != null) {
                for (int next : adj[current]) {
                    indegree[next]--;
                    if (indegree[next] == 0) {
                        queue.offer(next);
                    }
                }
            }
        }

        // If we have taken all courses, return true
        return ans.size() == n;
    }
}
```

### Explanation:

* We model the courses as nodes in a directed graph, edges represent prerequisites.
* Use indegree array to track number of prerequisites left for each course.
* Initialize queue with all courses having zero prerequisites.
* Perform a topological sort by processing these courses, reducing indegree of neighbors.
* If we can process all courses, it means no cycle exists, so we can finish all courses.
* Otherwise, return false.

---

Sure! Here's the **Course Schedule II** problem and solution in your requested format:

---

# ⭐ Course Schedule II

You have `numCourses` courses labeled from `0` to `numCourses - 1`.
Given an array `prerequisites` where `prerequisites[i] = [a_i, b_i]` means you must take course `b_i` before course `a_i`.

Return **any valid ordering** of courses you should take to finish all courses.
If it is impossible to finish all courses (due to a cycle), return an empty array.

### 📌 Example

Input:

```
numCourses = 2, prerequisites = [[1, 0]]
```

Output:

```
[0, 1]
```

Explanation:

* To take course 1, you must finish course 0 first.
* So the valid course order is \[0, 1].

### ✅ Java Code with Comments

```java
import java.util.*;

class Solution {
    public int[] findOrder(int n, int[][] prerequisites) {
        // Adjacency list to represent graph of courses and their next courses
        List<Integer>[] adj = new List[n];
        // Array to track number of prerequisites (indegree) for each course
        int[] indegree = new int[n];
        // List to record the order of courses
        List<Integer> ans = new ArrayList<>();

        // Build the graph and indegree array
        for (int[] pair : prerequisites) {
            int course = pair[0];
            int prerequisite = pair[1];
            if (adj[prerequisite] == null) {
                adj[prerequisite] = new ArrayList<>();
            }
            adj[prerequisite].add(course);
            indegree[course]++;
        }

        // Initialize queue with courses that have no prerequisites
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }

        // BFS topological sort to find course order
        while (!queue.isEmpty()) {
            int current = queue.poll();
            ans.add(current);

            if (adj[current] != null) {
                for (int next : adj[current]) {
                    indegree[next]--;
                    if (indegree[next] == 0) {
                        queue.offer(next);
                    }
                }
            }
        }

        // If all courses are taken, convert list to array and return
        if (ans.size() == n) {
            int[] res = new int[ans.size()];
            for (int i = 0; i < ans.size(); i++) {
                res[i] = ans.get(i);
            }
            return res;
        }

        // Cycle detected - return empty array
        return new int[0];
    }
}
```

### Explanation:

* Model courses as nodes in a directed graph.
* Use `indegree` to count prerequisites for each course.
* Start with courses having zero prerequisites.
* Use BFS topological sort to find a valid order.
* If all courses can be processed, return the order.
* Otherwise, return an empty array indicating a cycle.

Sure! Here's the **Redundant Connection** problem in your requested format with a visual diagram included for the example.

---

# 🌲 Redundant Connection

A *tree* is a connected undirected graph with no cycles.
You're given a graph that **started as a tree** with `n` nodes (labeled from `1` to `n`), and **one additional edge** added.

Return the edge that can be removed so the graph becomes a tree again.
If multiple answers exist, **return the one that occurs last** in the input.

### 📌 Example

#### Input:

```java
edges = [[1,2],[1,3],[2,3]]
```

```
    1
   / \
  2 - 3
```

#### Output:

```java
[2,3]
```

Explanation:

* The original tree could be:

  ```
      1
     / \
    2   3
  ```
* Adding edge `[2,3]` creates a cycle: `2 → 1 → 3 → 2`
* So `[2,3]` is the redundant edge that must be removed.

### ✅ Java Code with Comments

```java
class Solution {

    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        // Initialize adjacency list for the graph
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        // Try adding each edge, check if it creates a cycle using DFS
        for (int[] edge : edges) {
            int u = edge[0] - 1; // 1-based to 0-based index
            int v = edge[1] - 1;
            boolean[] visited = new boolean[n]; 

            // If u and v are already connected, then adding this edge makes a cycle
            if (dfs(graph, u, v, visited)) {
                return edge; // This edge is redundant
            }

            // Otherwise, add the edge to the graph
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        return new int[0]; // No redundant edge found (shouldn't occur per problem constraint)
    }

    // DFS to check if there's a path from u to v
    private boolean dfs(ArrayList<ArrayList<Integer>> graph, int u, int v, boolean[] visited) {
        if (u == v) return true;

        visited[u] = true;
        for (int next : graph.get(u)) {
            if (!visited[next]) {
                if (dfs(graph, next, v, visited)) {
                    return true;
                }
            }
        }
        return false;
    }
}
```

### 🧠 Key Insight:

* You're building a tree step-by-step by adding edges.
* Before adding a new edge, check if there's already a path between its nodes using DFS.
* If a path exists → adding this edge will form a cycle → it's the **redundant** edge.

---
Here’s your **Word Ladder** problem in the structured format with an explanation and comments:

---

# 🔤 Word Ladder

Given:

* A `beginWord`
* An `endWord`
* A `wordList` containing valid intermediate words

Transform `beginWord` to `endWord` such that:

1. Only one letter can be changed at a time.
2. Each transformed word must exist in the `wordList`.

Return the **number of words in the shortest transformation sequence**, including `beginWord` and `endWord`.
If no such sequence exists, return `0`.


### 📌 Example

#### Input:

```java
beginWord = "hit"
endWord = "cog"
wordList = ["hot","dot","dog","lot","log","cog"]
```

#### Output:

```java
5
```

#### Explanation:

One shortest transformation is:

```
"hit" → "hot" → "dot" → "dog" → "cog"
```

This path has 5 words.


### ✅ Java Code with Comments

```java
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // Convert wordList to a set for O(1) lookups
        HashSet<String> set = new HashSet<>(wordList);
        HashSet<String> visited = new HashSet<>();

        // If the end word is not in the list, no possible transformation
        if (!set.contains(endWord)) return 0;

        Queue<String> q = new LinkedList<>();
        q.add(beginWord);
        int changes = 1; // Start with 1 (include beginWord)
        int n = beginWord.length();

        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                String current = q.poll();

                if (current.equals(endWord)) return changes;

                // Try changing every character
                for (int j = 0; j < n; j++) {
                    char[] arr = current.toCharArray();
                    for (char c = 'a'; c <= 'z'; c++) {
                        arr[j] = c;
                        String newWord = new String(arr);

                        // If valid and not visited
                        if (set.contains(newWord) && !visited.contains(newWord)) {
                            q.add(newWord);
                            visited.add(newWord);
                        }
                    }
                }
            }
            changes++; // Increase transformation level
        }

        return 0; // No path found
    }
}
```

### 🧠 Key Insight:

This is a **shortest path problem in an unweighted graph**, where:

* Each word is a node.
* An edge exists between two words if they differ by one letter.

So, use **Breadth-First Search (BFS)** to find the shortest path from `beginWord` to `endWord`.

---

# Word Ladder II 

Given:

* A `beginWord`
* An `endWord`
* A `wordList` containing valid intermediate words

Transform `beginWord` to `endWord` such that:

1. Only one letter can be changed at a time.
2. Each transformed word must exist in the `wordList`.
3. Return **all the shortest transformation sequences** from `beginWord` to `endWord`.

Each sequence should be returned as a list of words `[beginWord, s1, s2, ..., endWord]`.

If no such sequence exists, return an empty list.

---

###  Example

#### Input:

```java
beginWord = "hit"
endWord = "cog"
wordList = ["hot","dot","dog","lot","log","cog"]
```

#### Output:

```java
[["hit","hot","dot","dog","cog"],["hit","hot","lot","log","cog"]]
```

#### Explanation:

There are 2 shortest transformation sequences:

```
"hit" → "hot" → "dot" → "dog" → "cog"
"hit" → "hot" → "lot" → "log" → "cog"
```

---

```java
class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);

        // If endWord is not in wordList, return empty result
        if (!wordSet.contains(endWord)) return new ArrayList<>();

        // Map to store reverse edges: child -> list of parent words
        Map<String, List<String>> parents = new HashMap<>();

        // Current level in BFS
        Set<String> currentLevel = new HashSet<>();
        currentLevel.add(beginWord);

        // Track visited words globally to prevent revisiting
        Set<String> visited = new HashSet<>();
        boolean found = false;

        // Start BFS
        while (!currentLevel.isEmpty() && !found) {
            Set<String> nextLevel = new HashSet<>();
            visited.addAll(currentLevel);

            for (String word : currentLevel) {
                char[] chars = word.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    char original = chars[i];

                    // Try replacing each character
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == original) continue;
                        chars[i] = c;
                        String nextWord = new String(chars);

                        // Skip if not in dictionary or already visited
                        if (!wordSet.contains(nextWord) || visited.contains(nextWord)) continue;

                        // Add parent for backtracking
                        parents.computeIfAbsent(nextWord, k -> new ArrayList<>()).add(word);

                        // If endWord is reached, flag found
                        if (nextWord.equals(endWord)) found = true;

                        nextLevel.add(nextWord);
                    }
                    chars[i] = original;
                }
            }

            // Move to next level
            currentLevel = nextLevel;
        }

        // Result list
        List<List<String>> result = new ArrayList<>();

        // Start backtracking from endWord to beginWord
        if (found) {
            List<String> path = new LinkedList<>();
            backtrack(endWord, beginWord, parents, path, result);
        }

        return result;
    }

    // Helper DFS method to backtrack all paths
    private void backtrack(String word, String beginWord, Map<String, List<String>> parents,
                           List<String> path, List<List<String>> result) {
        // Base case: reached beginWord
        if (word.equals(beginWord)) {
            path.add(0, word);
            result.add(new ArrayList<>(path));
            path.remove(0);
            return;
        }

        // If no parents, path invalid
        if (!parents.containsKey(word)) return;

        path.add(0, word);
        for (String parent : parents.get(word)) {
            backtrack(parent, beginWord, parents, path, result);
        }
        path.remove(0);
    }
}
```

---

###  Key Insight:

* This is a **shortest path problem** where we must **return all valid paths**, not just count them.
* The approach is:

  1. Use **BFS** to build a **reverse graph** (`child → parents`) level by level.
  2. Use **DFS** to **backtrack from `endWord` to `beginWord`**, collecting only shortest paths.
* BFS stops **early** when `endWord` is found to improve performance.
