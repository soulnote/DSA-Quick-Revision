
## 📌 BFS vs DFS Quick Recap

| **BFS (Breadth-First Search)** | **DFS (Depth-First Search)** |
|--------------------------------|------------------------------|
| Queue use karte hain | Stack/Recursion use karte hain |
| Shortest path in unweighted graph | Path existence, backtracking |
| Level order traversal | In-depth exploration |
| O(V + E) time | O(V + E) time |

---

### 1. Word Ladder
**Problem:** Shortest transformation sequence from beginWord to endWord, changing one letter at a time.

```java
public int ladderLength(String beginWord, String endWord, List<String> wordList) {
    Set<String> wordSet = new HashSet<>(wordList);
    if (!wordSet.contains(endWord)) return 0;
    
    Queue<String> queue = new LinkedList<>();
    queue.offer(beginWord);
    int level = 1;
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            String curr = queue.poll();
            if (curr.equals(endWord)) return level;
            
            char[] chars = curr.toCharArray();
            for (int j = 0; j < chars.length; j++) {
                char original = chars[j];
                for (char c = 'a'; c <= 'z'; c++) {
                    if (c == original) continue;
                    chars[j] = c;
                    String next = new String(chars);
                    if (wordSet.contains(next)) {
                        queue.offer(next);
                        wordSet.remove(next); // Visited
                    }
                }
                chars[j] = original;
            }
        }
        level++;
    }
    return 0;
}
```

BFS use karo. Har word ke har character ko 'a' se 'z' tak change karke dekho. Agar naya word wordList mein hai toh queue mein daalo. Pehli baar endWord milne par level return karo.

---

### 2. Word Ladder II
**Problem:** Return all shortest transformation sequences.

```java
public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
    List<List<String>> result = new ArrayList<>();
    Set<String> wordSet = new HashSet<>(wordList);
    if (!wordSet.contains(endWord)) return result;
    
    Map<String, List<String>> graph = new HashMap<>();
    Map<String, Integer> distance = new HashMap<>();
    
    // BFS to build graph and distance
    Queue<String> queue = new LinkedList<>();
    queue.offer(beginWord);
    distance.put(beginWord, 0);
    
    while (!queue.isEmpty()) {
        String curr = queue.poll();
        char[] chars = curr.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char original = chars[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (c == original) continue;
                chars[i] = c;
                String next = new String(chars);
                if (wordSet.contains(next)) {
                    graph.computeIfAbsent(curr, k -> new ArrayList<>()).add(next);
                    if (!distance.containsKey(next)) {
                        distance.put(next, distance.get(curr) + 1);
                        queue.offer(next);
                    }
                }
            }
            chars[i] = original;
        }
    }
    
    // DFS to find paths
    List<String> path = new ArrayList<>();
    path.add(beginWord);
    dfs(beginWord, endWord, graph, distance, path, result);
    return result;
}

private void dfs(String curr, String endWord, Map<String, List<String>> graph,
                 Map<String, Integer> distance, List<String> path, List<List<String>> result) {
    if (curr.equals(endWord)) {
        result.add(new ArrayList<>(path));
        return;
    }
    
    if (!graph.containsKey(curr)) return;
    for (String next : graph.get(curr)) {
        if (distance.get(next) == distance.get(curr) + 1) {
            path.add(next);
            dfs(next, endWord, graph, distance, path, result);
            path.remove(path.size() - 1);
        }
    }
}
```

Pehle BFS se distance map aur graph banao. Phir DFS se saare shortest paths dhundho (jahan distance increment by 1 ho).

---

### 3. Number of Islands
**Problem:** Count number of islands (connected '1's) in 2D grid.

```java
public int numIslands(char[][] grid) {
    if (grid == null || grid.length == 0) return 0;
    int m = grid.length, n = grid[0].length;
    int count = 0;
    
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (grid[i][j] == '1') {
                count++;
                dfs(grid, i, j);
            }
        }
    }
    return count;
}

private void dfs(char[][] grid, int i, int j) {
    if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != '1') {
        return;
    }
    grid[i][j] = '0'; // Mark as visited
    dfs(grid, i + 1, j);
    dfs(grid, i - 1, j);
    dfs(grid, i, j + 1);
    dfs(grid, i, j - 1);
}
```

Har '1' milne par DFS run karo aur saare connected '1's ko '0' mark kardo. Isse ek island count ho jayega.

---

### 4. Max Area of Island
**Problem:** Find maximum area of island (connected '1's).

```java
public int maxAreaOfIsland(int[][] grid) {
    int maxArea = 0;
    for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[0].length; j++) {
            if (grid[i][j] == 1) {
                maxArea = Math.max(maxArea, dfs(grid, i, j));
            }
        }
    }
    return maxArea;
}

private int dfs(int[][] grid, int i, int j) {
    if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != 1) {
        return 0;
    }
    grid[i][j] = 0;
    return 1 + dfs(grid, i + 1, j) + dfs(grid, i - 1, j) + 
               dfs(grid, i, j + 1) + dfs(grid, i, j - 1);
}
```

DFS se area calculate karo. Har connected component ka area nikaal kar max store karo.

---

### 5. Pacific Atlantic Water Flow
**Problem:** Cells where water can flow to both Pacific and Atlantic oceans.

```java
public List<List<Integer>> pacificAtlantic(int[][] heights) {
    int m = heights.length, n = heights[0].length;
    boolean[][] pacific = new boolean[m][n];
    boolean[][] atlantic = new boolean[m][n];
    
    // DFS from borders
    for (int i = 0; i < m; i++) {
        dfs(heights, i, 0, pacific, Integer.MIN_VALUE);
        dfs(heights, i, n - 1, atlantic, Integer.MIN_VALUE);
    }
    for (int j = 0; j < n; j++) {
        dfs(heights, 0, j, pacific, Integer.MIN_VALUE);
        dfs(heights, m - 1, j, atlantic, Integer.MIN_VALUE);
    }
    
    List<List<Integer>> result = new ArrayList<>();
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (pacific[i][j] && atlantic[i][j]) {
                result.add(Arrays.asList(i, j));
            }
        }
    }
    return result;
}

private void dfs(int[][] heights, int i, int j, boolean[][] visited, int prevHeight) {
    if (i < 0 || i >= heights.length || j < 0 || j >= heights[0].length || 
        visited[i][j] || heights[i][j] < prevHeight) {
        return;
    }
    visited[i][j] = true;
    dfs(heights, i + 1, j, visited, heights[i][j]);
    dfs(heights, i - 1, j, visited, heights[i][j]);
    dfs(heights, i, j + 1, visited, heights[i][j]);
    dfs(heights, i, j - 1, visited, heights[i][j]);
}
```

Ocean borders se reverse DFS karo. Jo cells dono oceans se reachable hain wahi answer hain.

---

### 6. Surrounded Regions
**Problem:** Capture all 'O's surrounded by 'X's (convert to 'X').

```java
public void solve(char[][] board) {
    if (board == null || board.length == 0) return;
    int m = board.length, n = board[0].length;
    
    // Mark border 'O's and their connected 'O's as safe
    for (int i = 0; i < m; i++) {
        if (board[i][0] == 'O') dfs(board, i, 0);
        if (board[i][n - 1] == 'O') dfs(board, i, n - 1);
    }
    for (int j = 0; j < n; j++) {
        if (board[0][j] == 'O') dfs(board, 0, j);
        if (board[m - 1][j] == 'O') dfs(board, m - 1, j);
    }
    
    // Convert remaining 'O' to 'X', safe '#' back to 'O'
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (board[i][j] == 'O') board[i][j] = 'X';
            if (board[i][j] == '#') board[i][j] = 'O';
        }
    }
}

private void dfs(char[][] board, int i, int j) {
    if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] != 'O') {
        return;
    }
    board[i][j] = '#';
    dfs(board, i + 1, j);
    dfs(board, i - 1, j);
    dfs(board, i, j + 1);
    dfs(board, i, j - 1);
}
```

Border se connected 'O's safe hain (border tak pahunch sakte hain). Baaki sab 'O's surrounded hain, unhe 'X' kar do.

---

### 7. Clone Graph
**Problem:** Deep copy of a connected undirected graph.

```java
public Node cloneGraph(Node node) {
    if (node == null) return null;
    Map<Node, Node> map = new HashMap<>();
    return dfs(node, map);
}

private Node dfs(Node node, Map<Node, Node> map) {
    if (map.containsKey(node)) return map.get(node);
    
    Node copy = new Node(node.val);
    map.put(node, copy);
    
    for (Node neighbor : node.neighbors) {
        copy.neighbors.add(dfs(neighbor, map));
    }
    return copy;
}
```

HashMap rakho jo original node se copied node ka mapping store kare. DFS se copy karte jao, agar node already copy hai toh seedha return karo.

---

### 8. Course Schedule (Cycle Detection in Directed Graph)
**Problem:** Can you finish all courses? (Cycle detection)

```java
public boolean canFinish(int numCourses, int[][] prerequisites) {
    List<Integer>[] graph = new ArrayList[numCourses];
    for (int i = 0; i < numCourses; i++) graph[i] = new ArrayList<>();
    
    for (int[] pre : prerequisites) {
        graph[pre[1]].add(pre[0]);
    }
    
    int[] visited = new int[numCourses]; // 0=unvisited, 1=visiting, 2=visited
    for (int i = 0; i < numCourses; i++) {
        if (hasCycle(i, graph, visited)) return false;
    }
    return true;
}

private boolean hasCycle(int node, List<Integer>[] graph, int[] visited) {
    if (visited[node] == 1) return true;
    if (visited[node] == 2) return false;
    
    visited[node] = 1;
    for (int neighbor : graph[node]) {
        if (hasCycle(neighbor, graph, visited)) return true;
    }
    visited[node] = 2;
    return false;
}
```

DFS se cycle detect karo. Three states - 0 unvisited, 1 visiting (current path mein hai), 2 fully visited.

---

### 9. Alien Dictionary (DFS version)
**Problem:** Find order of letters in alien language.

```java
public String alienOrder(String[] words) {
    Map<Character, Set<Character>> graph = new HashMap<>();
    Map<Character, Integer> visited = new HashMap<>();
    
    // Initialize
    for (String word : words) {
        for (char c : word.toCharArray()) {
            graph.putIfAbsent(c, new HashSet<>());
            visited.put(c, 0);
        }
    }
    
    // Build graph
    for (int i = 0; i < words.length - 1; i++) {
        String w1 = words[i];
        String w2 = words[i + 1];
        int minLen = Math.min(w1.length(), w2.length());
        
        if (w1.length() > w2.length() && w1.startsWith(w2)) return "";
        
        for (int j = 0; j < minLen; j++) {
            char c1 = w1.charAt(j);
            char c2 = w2.charAt(j);
            if (c1 != c2) {
                graph.get(c1).add(c2);
                break;
            }
        }
    }
    
    // DFS topological sort
    StringBuilder sb = new StringBuilder();
    for (char c : graph.keySet()) {
        if (visited.get(c) == 0) {
            if (dfs(c, graph, visited, sb)) return "";
        }
    }
    return sb.reverse().toString();
}

private boolean dfs(char c, Map<Character, Set<Character>> graph, 
                    Map<Character, Integer> visited, StringBuilder sb) {
    if (visited.get(c) == 1) return true; // Cycle
    if (visited.get(c) == 2) return false;
    
    visited.put(c, 1);
    for (char neighbor : graph.get(c)) {
        if (dfs(neighbor, graph, visited, sb)) return true;
    }
    visited.put(c, 2);
    sb.append(c);
    return false;
}
```

DFS se topological sort karo. Cycle detect karne ke liye visited states use karo. Reverse order mein answer milega.

---

### 10. Minimum Genetic Mutation
**Problem:** Minimum mutations to change gene string.

```java
public int minMutation(String start, String end, String[] bank) {
    Set<String> bankSet = new HashSet<>(Arrays.asList(bank));
    if (!bankSet.contains(end)) return -1;
    
    char[] genes = {'A', 'C', 'G', 'T'};
    Queue<String> queue = new LinkedList<>();
    queue.offer(start);
    int mutations = 0;
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            String curr = queue.poll();
            if (curr.equals(end)) return mutations;
            
            char[] chars = curr.toCharArray();
            for (int j = 0; j < chars.length; j++) {
                char original = chars[j];
                for (char gene : genes) {
                    if (gene == original) continue;
                    chars[j] = gene;
                    String next = new String(chars);
                    if (bankSet.contains(next)) {
                        queue.offer(next);
                        bankSet.remove(next);
                    }
                }
                chars[j] = original;
            }
        }
        mutations++;
    }
    return -1;
}
```

BFS use karo (Word Ladder jaisa). Har position pe 4 possible genes try karo.

---

### 11. Shortest Bridge
**Problem:** Minimum flips to connect two islands (1s) in binary matrix.

```java
public int shortestBridge(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    Queue<int[]> queue = new LinkedList<>();
    boolean found = false;
    
    // DFS to find first island and add all its cells to queue
    for (int i = 0; i < m && !found; i++) {
        for (int j = 0; j < n && !found; j++) {
            if (grid[i][j] == 1) {
                dfs(grid, i, j, queue);
                found = true;
            }
        }
    }
    
    // BFS to expand and find second island
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    int steps = 0;
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            int[] curr = queue.poll();
            for (int[] dir : dirs) {
                int x = curr[0] + dir[0];
                int y = curr[1] + dir[1];
                if (x >= 0 && x < m && y >= 0 && y < n) {
                    if (grid[x][y] == 1) return steps;
                    if (grid[x][y] == 0) {
                        grid[x][y] = 2;
                        queue.offer(new int[]{x, y});
                    }
                }
            }
        }
        steps++;
    }
    return -1;
}

private void dfs(int[][] grid, int i, int j, Queue<int[]> queue) {
    if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != 1) {
        return;
    }
    grid[i][j] = 2;
    queue.offer(new int[]{i, j});
    dfs(grid, i + 1, j, queue);
    dfs(grid, i - 1, j, queue);
    dfs(grid, i, j + 1, queue);
    dfs(grid, i, j - 1, queue);
}
```

Pehle island ko DFS se dhundho aur queue mein daalo. Phir BFS se expand karo. Jab second island ka '1' mile, steps return karo.

---

### 12. Walls and Gates
**Problem:** Fill each empty room with distance to nearest gate.

```java
public void wallsAndGates(int[][] rooms) {
    if (rooms == null || rooms.length == 0) return;
    int m = rooms.length, n = rooms[0].length;
    Queue<int[]> queue = new LinkedList<>();
    
    // Add all gates to queue
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (rooms[i][j] == 0) queue.offer(new int[]{i, j});
        }
    }
    
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    
    while (!queue.isEmpty()) {
        int[] curr = queue.poll();
        for (int[] dir : dirs) {
            int x = curr[0] + dir[0];
            int y = curr[1] + dir[1];
            if (x >= 0 && x < m && y >= 0 && y < n && rooms[x][y] == Integer.MAX_VALUE) {
                rooms[x][y] = rooms[curr[0]][curr[1]] + 1;
                queue.offer(new int[]{x, y});
            }
        }
    }
}
```

Saare gates ko queue mein daalo. Multi-source BFS karo, har cell apne neighbor se ek step zyada distance lega.

---

### 13. Rotting Oranges
**Problem:** Minimum time for all oranges to rot.

```java
public int orangesRotting(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    Queue<int[]> queue = new LinkedList<>();
    int fresh = 0;
    
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (grid[i][j] == 2) queue.offer(new int[]{i, j});
            else if (grid[i][j] == 1) fresh++;
        }
    }
    
    if (fresh == 0) return 0;
    
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    int minutes = 0;
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        boolean rotted = false;
        for (int i = 0; i < size; i++) {
            int[] curr = queue.poll();
            for (int[] dir : dirs) {
                int x = curr[0] + dir[0];
                int y = curr[1] + dir[1];
                if (x >= 0 && x < m && y >= 0 && y < n && grid[x][y] == 1) {
                    grid[x][y] = 2;
                    queue.offer(new int[]{x, y});
                    fresh--;
                    rotted = true;
                }
            }
        }
        if (rotted) minutes++;
    }
    
    return fresh == 0 ? minutes : -1;
}
```

Multi-source BFS. Saare rotten oranges se start karo, har minute mein neighbors ko rot karo. Fresh count maintain karo.

---

### 14. Snake and Ladder
**Problem:** Minimum moves to reach last cell (like board game).

```java
public int snakesAndLadders(int[][] board) {
    int n = board.length;
    int[] flat = new int[n * n + 1];
    boolean leftToRight = true;
    int idx = 1;
    
    // Flatten the board
    for (int i = n - 1; i >= 0; i--) {
        if (leftToRight) {
            for (int j = 0; j < n; j++) flat[idx++] = board[i][j];
        } else {
            for (int j = n - 1; j >= 0; j--) flat[idx++] = board[i][j];
        }
        leftToRight = !leftToRight;
    }
    
    int[] dist = new int[n * n + 1];
    Arrays.fill(dist, -1);
    dist[1] = 0;
    Queue<Integer> queue = new LinkedList<>();
    queue.offer(1);
    
    while (!queue.isEmpty()) {
        int curr = queue.poll();
        for (int dice = 1; dice <= 6 && curr + dice <= n * n; dice++) {
            int next = curr + dice;
            if (flat[next] != -1) next = flat[next];
            if (dist[next] == -1) {
                dist[next] = dist[curr] + 1;
                queue.offer(next);
            }
        }
    }
    return dist[n * n];
}
```

Board ko flatten karo (snake pattern). BFS use karo, har step pe 1-6 dice roll karo. Agar snake/ladder hai toh jump karo.

---

### 15. Open the Lock
**Problem:** Minimum moves to reach target lock combination.

```java
public int openLock(String[] deadends, String target) {
    Set<String> dead = new HashSet<>(Arrays.asList(deadends));
    Set<String> visited = new HashSet<>();
    
    if (dead.contains("0000")) return -1;
    
    Queue<String> queue = new LinkedList<>();
    queue.offer("0000");
    visited.add("0000");
    int moves = 0;
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            String curr = queue.poll();
            if (curr.equals(target)) return moves;
            
            for (int j = 0; j < 4; j++) {
                char[] chars = curr.toCharArray();
                char original = chars[j];
                
                // Turn up
                chars[j] = original == '9' ? '0' : (char)(original + 1);
                String up = new String(chars);
                if (!dead.contains(up) && !visited.contains(up)) {
                    visited.add(up);
                    queue.offer(up);
                }
                
                // Turn down
                chars[j] = original == '0' ? '9' : (char)(original - 1);
                String down = new String(chars);
                if (!dead.contains(down) && !visited.contains(down)) {
                    visited.add(down);
                    queue.offer(down);
                }
                chars[j] = original;
            }
        }
        moves++;
    }
    return -1;
}
```

BFS use karo. Har wheel ko up/down turn karke 8 possible combinations generate karo. Deadends avoid karo.

---

### 16. Minimum Knight Moves
**Problem:** Minimum moves for knight to reach target from (0,0) (infinite chessboard).

```java
public int minKnightMoves(int x, int y) {
    x = Math.abs(x);
    y = Math.abs(y);
    
    if (x == 0 && y == 0) return 0;
    if (x == 1 && y == 1) return 2;
    
    Queue<int[]> queue = new LinkedList<>();
    queue.offer(new int[]{0, 0});
    Set<String> visited = new HashSet<>();
    visited.add("0,0");
    
    int[][] dirs = {{2,1},{2,-1},{-2,1},{-2,-1},{1,2},{1,-2},{-1,2},{-1,-2}};
    int moves = 0;
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            int[] curr = queue.poll();
            if (curr[0] == x && curr[1] == y) return moves;
            
            for (int[] dir : dirs) {
                int nx = curr[0] + dir[0];
                int ny = curr[1] + dir[1];
                if (nx >= -2 && ny >= -2 && nx <= x + 2 && ny <= y + 2) {
                    String key = nx + "," + ny;
                    if (!visited.contains(key)) {
                        visited.add(key);
                        queue.offer(new int[]{nx, ny});
                    }
                }
            }
        }
        moves++;
    }
    return -1;
}
```

BFS use karo. Symmetry use karo (x,y ko positive mein convert karo). Boundary optimization lagao.

---

### 17. Cut Off Trees for Golf Event
**Problem:** Cut trees in increasing height order, min total steps.

```java
public int cutOffTree(List<List<Integer>> forest) {
    int m = forest.size(), n = forest.get(0).size();
    List<int[]> trees = new ArrayList<>();
    
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            int height = forest.get(i).get(j);
            if (height > 1) trees.add(new int[]{height, i, j});
        }
    }
    
    Collections.sort(trees, (a, b) -> a[0] - b[0]);
    
    int total = 0;
    int[] start = {0, 0};
    
    for (int[] tree : trees) {
        int steps = bfs(forest, start, new int[]{tree[1], tree[2]});
        if (steps == -1) return -1;
        total += steps;
        start = new int[]{tree[1], tree[2]};
    }
    return total;
}

private int bfs(List<List<Integer>> forest, int[] start, int[] target) {
    int m = forest.size(), n = forest.get(0).size();
    boolean[][] visited = new boolean[m][n];
    Queue<int[]> queue = new LinkedList<>();
    queue.offer(new int[]{start[0], start[1], 0});
    visited[start[0]][start[1]] = true;
    
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    
    while (!queue.isEmpty()) {
        int[] curr = queue.poll();
        if (curr[0] == target[0] && curr[1] == target[1]) return curr[2];
        
        for (int[] dir : dirs) {
            int x = curr[0] + dir[0];
            int y = curr[1] + dir[1];
            if (x >= 0 && x < m && y >= 0 && y < n && 
                !visited[x][y] && forest.get(x).get(y) != 0) {
                visited[x][y] = true;
                queue.offer(new int[]{x, y, curr[2] + 1});
            }
        }
    }
    return -1;
}
```

Trees ko height se sort karo. Har consecutive tree pair ke beech BFS se shortest path nikaalo.

---

### 18. Bus Routes
**Problem:** Minimum number of buses to travel from source to target.

```java
public int numBusesToDestination(int[][] routes, int source, int target) {
    if (source == target) return 0;
    
    Map<Integer, List<Integer>> stopToBuses = new HashMap<>();
    for (int i = 0; i < routes.length; i++) {
        for (int stop : routes[i]) {
            stopToBuses.computeIfAbsent(stop, k -> new ArrayList<>()).add(i);
        }
    }
    
    Queue<Integer> queue = new LinkedList<>();
    Set<Integer> visitedStops = new HashSet<>();
    Set<Integer> visitedBuses = new HashSet<>();
    
    queue.offer(source);
    visitedStops.add(source);
    int buses = 0;
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            int stop = queue.poll();
            if (stop == target) return buses;
            
            for (int bus : stopToBuses.getOrDefault(stop, new ArrayList<>())) {
                if (visitedBuses.contains(bus)) continue;
                visitedBuses.add(bus);
                
                for (int nextStop : routes[bus]) {
                    if (!visitedStops.contains(nextStop)) {
                        visitedStops.add(nextStop);
                        queue.offer(nextStop);
                    }
                }
            }
        }
        buses++;
    }
    return -1;
}
```

Stop se bus mapping banao. BFS karo - har stop pe us stop ki saari buses lo, aur un bus ke saare stops explore karo.

---

### 19. Sliding Puzzle
**Problem:** Minimum moves to solve 2x3 sliding puzzle.

```java
public int slidingPuzzle(int[][] board) {
    String target = "123450";
    StringBuilder start = new StringBuilder();
    for (int i = 0; i < 2; i++) {
        for (int j = 0; j < 3; j++) {
            start.append(board[i][j]);
        }
    }
    
    if (start.toString().equals(target)) return 0;
    
    int[][] neighbors = {
        {1, 3}, {0, 2, 4}, {1, 5},
        {0, 4}, {1, 3, 5}, {2, 4}
    };
    
    Queue<String> queue = new LinkedList<>();
    Set<String> visited = new HashSet<>();
    queue.offer(start.toString());
    visited.add(start.toString());
    int moves = 0;
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            String curr = queue.poll();
            if (curr.equals(target)) return moves;
            
            int zeroPos = curr.indexOf('0');
            for (int neighbor : neighbors[zeroPos]) {
                char[] chars = curr.toCharArray();
                chars[zeroPos] = chars[neighbor];
                chars[neighbor] = '0';
                String next = new String(chars);
                if (!visited.contains(next)) {
                    visited.add(next);
                    queue.offer(next);
                }
            }
        }
        moves++;
    }
    return -1;
}
```

2x3 board ko string mein flatten karo. BFS se 0 ko adjacent positions se swap karte jao. Target string "123450" tak pahuncho.

---

### 20. Minimum Moves to Move a Box to Their Target Location
**Problem:** Push box to target with minimum pushes (player can move).

```java
public int minPushBox(char[][] grid) {
    int m = grid.length, n = grid[0].length;
    int[] box = null, target = null, player = null;
    
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (grid[i][j] == 'B') box = new int[]{i, j};
            else if (grid[i][j] == 'T') target = new int[]{i, j};
            else if (grid[i][j] == 'S') player = new int[]{i, j};
        }
    }
    
    int[][][] dist = new int[m][n][4];
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            Arrays.fill(dist[i][j], Integer.MAX_VALUE);
        }
    }
    
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
    for (int d = 0; d < 4; d++) {
        int[] pushPos = getPushPos(box[0], box[1], d);
        if (canReach(player, pushPos, box, grid)) {
            dist[box[0]][box[1]][d] = 1;
            pq.offer(new int[]{box[0], box[1], d, 1});
        }
    }
    
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    int[][] reverseDir = {{0,-1},{0,1},{-1,0},{1,0}};
    
    while (!pq.isEmpty()) {
        int[] curr = pq.poll();
        int bx = curr[0], by = curr[1], dir = curr[2], pushes = curr[3];
        
        if (bx == target[0] && by == target[1]) return pushes;
        
        // Try to push in same direction
        int nx = bx + dirs[dir][0];
        int ny = by + dirs[dir][1];
        if (isValid(nx, ny, grid) && grid[nx][ny] != '#') {
            int[] pushFrom = getPushPos(bx, by, dir);
            if (canReach(new int[]{bx - reverseDir[dir][0], by - reverseDir[dir][1]}, 
                         pushFrom, new int[]{bx, by}, grid)) {
                if (pushes + 1 < dist[nx][ny][dir]) {
                    dist[nx][ny][dir] = pushes + 1;
                    pq.offer(new int[]{nx, ny, dir, pushes + 1});
                }
            }
        }
        
        // Try to change direction
        for (int nd = 0; nd < 4; nd++) {
            if (nd == dir) continue;
            int[] pushPos = getPushPos(bx, by, nd);
            if (canReach(new int[]{bx - reverseDir[dir][0], by - reverseDir[dir][1]}, 
                         pushPos, new int[]{bx, by}, grid)) {
                if (pushes + 1 < dist[bx][by][nd]) {
                    dist[bx][by][nd] = pushes + 1;
                    pq.offer(new int[]{bx, by, nd, pushes + 1});
                }
            }
        }
    }
    return -1;
}

private boolean canReach(int[] start, int[] target, int[] box, char[][] grid) {
    int m = grid.length, n = grid[0].length;
    boolean[][] visited = new boolean[m][n];
    Queue<int[]> queue = new LinkedList<>();
    queue.offer(start);
    visited[start[0]][start[1]] = true;
    
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    
    while (!queue.isEmpty()) {
        int[] curr = queue.poll();
        if (curr[0] == target[0] && curr[1] == target[1]) return true;
        
        for (int[] dir : dirs) {
            int x = curr[0] + dir[0];
            int y = curr[1] + dir[1];
            if (x >= 0 && x < m && y >= 0 && y < n && !visited[x][y] && 
                grid[x][y] != '#' && !(x == box[0] && y == box[1])) {
                visited[x][y] = true;
                queue.offer(new int[]{x, y});
            }
        }
    }
    return false;
}

private int[] getPushPos(int bx, int by, int dir) {
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    return new int[]{bx - dirs[dir][0], by - dirs[dir][1]};
}

private boolean isValid(int x, int y, char[][] grid) {
    return x >= 0 && x < grid.length && y >= 0 && y < grid[0].length && grid[x][y] != '#';
}
```

Box position + push direction as state. Dijkstra-style BFS (priority queue by pushes). Player ko box ke opposite side reach karna padta hai push karne ke liye.

---

## 📌 Quick Pattern Summary 

| Pattern | Approach | Example Problems |
|---------|----------|------------------|
| **Shortest Path (Unweighted)** | BFS from source | Word Ladder, Rotting Oranges |
| **Connected Components** | DFS/BFS on grid | Number of Islands, Max Area |
| **Cycle Detection** | DFS with 3 states | Course Schedule |
| **Multi-Source BFS** | All sources in queue initially | Walls and Gates, Rotting Oranges |
| **Bidirectional BFS** | Two directions simultaneously | Word Ladder (optimized) |
| **State BFS** | BFS on state space | Sliding Puzzle, Open Lock |
| **Dijkstra (Weighted)** | Priority Queue | Min Push Box |

## ⚡ Pro Tips 

1. **Grid BFS** - Use `int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}}`
2. **Visited array** - Always mark visited when adding to queue, not when polling
3. **2D to 1D mapping** - `index = i * n + j`
4. **BFS for shortest path** - Unweighted graph mein BFS shortest path deta hai
5. **DFS for backtracking** - Jab saare paths chahiye ho
6. **Multi-source BFS** - Multiple starting points ke liye queue mein sab daal do
7. **Bidirectional BFS** - Source aur target se simultaneously BFS for optimization
