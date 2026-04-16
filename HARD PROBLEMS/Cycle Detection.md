##  Cycle Detection Core Concepts 
### Different Approaches for Different Graph Types:

| Graph Type | Algorithm | Detection Method | Time Complexity |
|------------|-----------|------------------|-----------------|
| **Undirected Graph** | DFS / Union-Find | If neighbor visited and not parent → cycle | O(V+E) |
| **Directed Graph** | DFS with 3 states | If neighbor is "visiting" (in current stack) → cycle | O(V+E) |
| **Directed Graph** | Kahn's Algorithm (BFS) | If topological sort mein saare nodes process nahi hue → cycle | O(V+E) |
| **Union-Find** | DSU | If find(u) == find(v) before union → cycle | O(E × α(V)) |

### DFS 3-State Tracking for Directed Graphs:
```java
// 0 = unvisited, 1 = visiting (in current recursion stack), 2 = fully processed
int[] state = new int[n];
if (state[v] == 1) → Cycle detected!
```

---

## 📋 20 Hard Cycle Detection Problems

---

### 1. Course Schedule (LeetCode 207) - Detect Cycle in Directed Graph
**Problem:** n courses, prerequisites [[1,0]] means course 0 before 1. Kya sab courses complete kar sakte ho?

**Algorithm:** DFS with 3 states

```java
public boolean canFinish(int numCourses, int[][] prerequisites) {
    List<Integer>[] graph = new ArrayList[numCourses];
    for (int i = 0; i < numCourses; i++) graph[i] = new ArrayList<>();
    
    for (int[] pre : prerequisites) {
        graph[pre[1]].add(pre[0]);
    }
    
    int[] state = new int[numCourses];  // 0=unvisited, 1=visiting, 2=visited
    
    for (int i = 0; i < numCourses; i++) {
        if (hasCycle(i, graph, state)) return false;
    }
    return true;
}

private boolean hasCycle(int node, List<Integer>[] graph, int[] state) {
    if (state[node] == 1) return true;   // Cycle detected
    if (state[node] == 2) return false;  // Already processed
    
    state[node] = 1;  // Mark as visiting
    
    for (int neighbor : graph[node]) {
        if (hasCycle(neighbor, graph, state)) return true;
    }
    
    state[node] = 2;  // Mark as fully processed
    return false;
}
```

> Har node ke liye 3 states maintain karo - 0 (unvisited), 1 (visiting - current stack mein hai), 2 (processed). Agar visiting state mein dubara node mile toh cycle hai. Course schedule tabhi possible hai jab cycle na ho.

**Time:** O(V+E)

---

### 2. Course Schedule II (LeetCode 210) - Topological Order with Cycle Detection
**Problem:** Courses ka order batao jisme sab complete ho sakein. Agar cycle hai toh empty array return karo.

**Algorithm:** Kahn's Algorithm (BFS) with indegree

```java
public int[] findOrder(int numCourses, int[][] prerequisites) {
    List<Integer>[] graph = new ArrayList[numCourses];
    int[] indegree = new int[numCourses];
    
    for (int i = 0; i < numCourses; i++) graph[i] = new ArrayList<>();
    
    for (int[] pre : prerequisites) {
        graph[pre[1]].add(pre[0]);
        indegree[pre[0]]++;
    }
    
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < numCourses; i++) {
        if (indegree[i] == 0) queue.offer(i);
    }
    
    int[] result = new int[numCourses];
    int index = 0;
    
    while (!queue.isEmpty()) {
        int course = queue.poll();
        result[index++] = course;
        
        for (int next : graph[course]) {
            indegree[next]--;
            if (indegree[next] == 0) queue.offer(next);
        }
    }
    
    // Cycle exists if not all courses processed
    return index == numCourses ? result : new int[0];
}
```

> Indegree zero wale nodes se start karo. Unhe remove karo aur neighbors ki indegree kam karo. Agar saare nodes process ho gaye, topological order mil gaya. Agar kuch nodes bache (indegree > 0), matlab cycle hai.

**Time:** O(V+E)

---

### 3. Redundant Connection (LeetCode 684) - Cycle in Undirected Graph
**Problem:** Tree mein ek extra edge add kardi. Remove the extra edge that creates cycle.

**Algorithm:** Union-Find (DSU)

```java
public int[] findRedundantConnection(int[][] edges) {
    int n = edges.length;
    int[] parent = new int[n + 1];
    for (int i = 1; i <= n; i++) parent[i] = i;
    
    for (int[] edge : edges) {
        int u = edge[0], v = edge[1];
        if (find(parent, u) == find(parent, v)) {
            return edge;  // This edge creates cycle
        }
        union(parent, u, v);
    }
    return new int[0];
}

private int find(int[] parent, int x) {
    if (parent[x] != x) parent[x] = find(parent, parent[x]);
    return parent[x];
}

private void union(int[] parent, int x, int y) {
    int rootX = find(parent, x);
    int rootY = find(parent, y);
    if (rootX != rootY) parent[rootX] = rootY;
}
```

> Union-Find se nodes ko connect karte jao. Jab kisi edge pe dono nodes already same set mein ho, woh edge cycle bana rahi hai - woh return karo.

**Time:** O(E × α(V))

---

### 4. Redundant Connection II (LeetCode 685) - Cycle in Directed Graph with Special Case
**Problem:** Directed graph hai jo almost tree hai (rooted tree + one extra edge). Remove the extra edge.

**Algorithm:** Detect two cases: node with two parents, or cycle

```java
public int[] findRedundantDirectedConnection(int[][] edges) {
    int n = edges.length;
    int[] parent = new int[n + 1];
    int[] cand1 = null, cand2 = null;
    
    // Step 1: Find node with two parents
    for (int[] edge : edges) {
        int u = edge[0], v = edge[1];
        if (parent[v] == 0) {
            parent[v] = u;
        } else {
            cand1 = new int[]{parent[v], v};
            cand2 = new int[]{u, v};
            edge[1] = 0;  // Mark second edge as invalid temporarily
        }
    }
    
    // Step 2: Union-Find to detect cycle
    for (int i = 1; i <= n; i++) parent[i] = i;
    
    for (int[] edge : edges) {
        if (edge[1] == 0) continue;  // Skip the invalid edge
        
        int u = edge[0], v = edge[1];
        if (find(parent, u) == find(parent, v)) {
            if (cand1 == null) return edge;  // Case 2: cycle
            return cand1;  // Case 1: two parents + cycle
        }
        union(parent, u, v);
    }
    return cand2;  // Case 1: two parents, no cycle
}
```

> Directed graph mein do cases hote hain: (1) kisi node ke do parents hain, (2) cycle hai. Pehle do parents wala case detect karo. Phir cycle detection karo. Dono cases handle karo.

**Time:** O(n)

---

### 5. Detect Cycle in Directed Graph (Standard Problem)
**Problem:** Directed graph mein cycle hai ya nahi?

**Algorithm:** DFS with 3 states

```java
public boolean hasCycle(int n, List<Integer>[] graph) {
    int[] state = new int[n];  // 0=unvisited, 1=visiting, 2=visited
    
    for (int i = 0; i < n; i++) {
        if (state[i] == 0 && dfsCycle(i, graph, state)) {
            return true;
        }
    }
    return false;
}

private boolean dfsCycle(int node, List<Integer>[] graph, int[] state) {
    if (state[node] == 1) return true;
    if (state[node] == 2) return false;
    
    state[node] = 1;
    for (int neighbor : graph[node]) {
        if (dfsCycle(neighbor, graph, state)) return true;
    }
    state[node] = 2;
    return false;
}
```

> 3-state tracking use karo. Jab current recursion stack mein node dubara mile, cycle hai. Ye algorithm interview mein sabse zyada pucha jata hai.

**Time:** O(V+E)

---

### 6. Detect Cycle in Undirected Graph (DFS)
**Problem:** Undirected graph mein cycle hai ya nahi?

**Algorithm:** DFS with parent tracking

```java
public boolean hasCycleUndirected(int n, List<Integer>[] graph) {
    boolean[] visited = new boolean[n];
    
    for (int i = 0; i < n; i++) {
        if (!visited[i]) {
            if (dfsCycle(i, -1, graph, visited)) return true;
        }
    }
    return false;
}

private boolean dfsCycle(int node, int parent, List<Integer>[] graph, boolean[] visited) {
    visited[node] = true;
    
    for (int neighbor : graph[node]) {
        if (neighbor == parent) continue;
        if (visited[neighbor]) return true;  // Cycle found
        if (dfsCycle(neighbor, node, graph, visited)) return true;
    }
    return false;
}
```

> DFS karte jao aur parent track karo. Agar kisi neighbor ko visit karo jo parent nahi hai aur already visited hai, toh cycle hai.

**Time:** O(V+E)

---

### 7. Alien Dictionary (LeetCode 269) - Cycle Detection in Character Graph
**Problem:** Sorted words list di hai (alien language mein). Characters ka order find karo. Agar cycle hai toh return empty string.

**Algorithm:** Build graph + Topological sort with cycle detection

```java
public String alienOrder(String[] words) {
    Map<Character, Set<Character>> graph = new HashMap<>();
    Map<Character, Integer> indegree = new HashMap<>();
    
    // Initialize
    for (String word : words) {
        for (char c : word.toCharArray()) {
            graph.putIfAbsent(c, new HashSet<>());
            indegree.putIfAbsent(c, 0);
        }
    }
    
    // Build graph
    for (int i = 0; i < words.length - 1; i++) {
        String w1 = words[i], w2 = words[i+1];
        int minLen = Math.min(w1.length(), w2.length());
        
        // Invalid case: "abc", "ab" - prefix but longer first
        if (w1.length() > w2.length() && w1.startsWith(w2)) return "";
        
        for (int j = 0; j < minLen; j++) {
            char c1 = w1.charAt(j), c2 = w2.charAt(j);
            if (c1 != c2) {
                if (!graph.get(c1).contains(c2)) {
                    graph.get(c1).add(c2);
                    indegree.put(c2, indegree.get(c2) + 1);
                }
                break;
            }
        }
    }
    
    // Topological sort with cycle detection
    Queue<Character> queue = new LinkedList<>();
    for (char c : indegree.keySet()) {
        if (indegree.get(c) == 0) queue.offer(c);
    }
    
    StringBuilder result = new StringBuilder();
    while (!queue.isEmpty()) {
        char c = queue.poll();
        result.append(c);
        for (char next : graph.get(c)) {
            indegree.put(next, indegree.get(next) - 1);
            if (indegree.get(next) == 0) queue.offer(next);
        }
    }
    
    // Cycle exists if not all characters processed
    return result.length() == indegree.size() ? result.toString() : "";
}
```

> Adjacent words se character order deduce karo. Graph mein cycle detect karne ke liye topological sort (Kahn's algorithm) use karo. Agar saare characters process nahi hue, cycle hai.

**Time:** O(C) where C = total characters

---

### 8. Graph Valid Tree (LeetCode 261) - Check if Graph is a Tree
**Problem:** n nodes and edges. Kya ye valid tree hai? (Connected and no cycle)

**Algorithm:** Check: edges == n-1 AND graph connected AND no cycle

```java
public boolean validTree(int n, int[][] edges) {
    if (edges.length != n - 1) return false;  // Tree must have n-1 edges
    
    List<Integer>[] graph = new ArrayList[n];
    for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
    
    for (int[] edge : edges) {
        graph[edge[0]].add(edge[1]);
        graph[edge[1]].add(edge[0]);
    }
    
    boolean[] visited = new boolean[n];
    if (hasCycle(graph, visited, 0, -1)) return false;
    
    // Check if all nodes visited (connected)
    for (boolean v : visited) if (!v) return false;
    return true;
}

private boolean hasCycle(List<Integer>[] graph, boolean[] visited, int curr, int parent) {
    visited[curr] = true;
    for (int neighbor : graph[curr]) {
        if (neighbor == parent) continue;
        if (visited[neighbor]) return true;
        if (hasCycle(graph, visited, neighbor, curr)) return true;
    }
    return false;
}
```

> Tree ke conditions: edges = n-1 AND no cycle AND connected. DFS se cycle detect karo aur visited check karo.

**Time:** O(V+E)

---

### 9. Find Eventual Safe States (LeetCode 802) - Reverse Cycle Detection
**Problem:** Directed graph. Terminal nodes (outdegree 0) aur jo nodes only terminal tak jaate hain, wo safe hain. Safe nodes find karo.

**Algorithm:** Reverse graph + Topological sort (Kahn's algorithm) - BFS from terminal nodes

```java
public List<Integer> eventualSafeNodes(int[][] graph) {
    int n = graph.length;
    List<Integer>[] reverseGraph = new ArrayList[n];
    for (int i = 0; i < n; i++) reverseGraph[i] = new ArrayList<>();
    
    int[] outdegree = new int[n];
    
    for (int i = 0; i < n; i++) {
        outdegree[i] = graph[i].length;
        for (int neighbor : graph[i]) {
            reverseGraph[neighbor].add(i);
        }
    }
    
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < n; i++) {
        if (outdegree[i] == 0) queue.offer(i);
    }
    
    boolean[] safe = new boolean[n];
    while (!queue.isEmpty()) {
        int node = queue.poll();
        safe[node] = true;
        for (int prev : reverseGraph[node]) {
            outdegree[prev]--;
            if (outdegree[prev] == 0) queue.offer(prev);
        }
    }
    
    List<Integer> result = new ArrayList<>();
    for (int i = 0; i < n; i++) {
        if (safe[i]) result.add(i);
    }
    return result;
}
```

> Terminal nodes (outdegree 0) se start karo. Reverse graph mein BFS karo. Jo nodes ke outdegree 0 ho jayein, woh safe hain. Cycle mein jo nodes hain, woh safe nahi hongi.

**Time:** O(V+E)

---

### 10. Minimum Height Trees (LeetCode 310) - Cycle Detection via Leaf Removal
**Problem:** Undirected tree mein root karke min height wale trees ke roots find karo.

**Algorithm:** Leaves ko repeatedly remove karo (topological sort-like)

```java
public List<Integer> findMinHeightTrees(int n, int[][] edges) {
    if (n == 1) return Arrays.asList(0);
    
    List<Set<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) graph.add(new HashSet<>());
    
    for (int[] edge : edges) {
        graph.get(edge[0]).add(edge[1]);
        graph.get(edge[1]).add(edge[0]);
    }
    
    List<Integer> leaves = new ArrayList<>();
    for (int i = 0; i < n; i++) {
        if (graph.get(i).size() == 1) leaves.add(i);
    }
    
    while (n > 2) {
        n -= leaves.size();
        List<Integer> newLeaves = new ArrayList<>();
        for (int leaf : leaves) {
            int neighbor = graph.get(leaf).iterator().next();
            graph.get(neighbor).remove(leaf);
            if (graph.get(neighbor).size() == 1) {
                newLeaves.add(neighbor);
            }
        }
        leaves = newLeaves;
    }
    return leaves;
}
```

> Leaves (degree 1 wale nodes) ko repeatedly remove karo. Jab 1 ya 2 nodes bachein, wahi answer hain. Isse center of tree mil jata hai.

**Time:** O(V)

---

### 11. Cycle Detection in a 2D Grid (LeetCode 1559)
**Problem:** 2D grid mein cycle detect karo. Same character se bana cycle (length >= 4) .

**Algorithm:** DFS with parent tracking

```java
public boolean containsCycle(char[][] grid) {
    int m = grid.length, n = grid[0].length;
    boolean[][] visited = new boolean[m][n];
    
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (!visited[i][j]) {
                if (dfs(grid, i, j, -1, -1, visited, grid[i][j])) {
                    return true;
                }
            }
        }
    }
    return false;
}

private boolean dfs(char[][] grid, int i, int j, int pi, int pj, boolean[][] visited, char target) {
    if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length) return false;
    if (grid[i][j] != target) return false;
    
    if (visited[i][j]) return true;  // Cycle found
    
    visited[i][j] = true;
    
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    for (int[] dir : dirs) {
        int ni = i + dir[0], nj = j + dir[1];
        if (ni == pi && nj == pj) continue;  // Skip parent
        if (dfs(grid, ni, nj, i, j, visited, target)) return true;
    }
    return false;
}
```

> DFS karte jao aur parent track karo. Agar kisi neighbor pe jaao jo parent nahi hai aur already visited hai, toh cycle hai. Cycle length >= 4 ensure karne ke liye parent skip karo.

**Time:** O(m×n)

---

### 12. Largest Cycle in a Graph (LeetCode 2360)
**Problem:** Graph jisme har node ka outdegree 1 hai. Longest cycle length find karo.

**Algorithm:** DFS with time array (Tarjan-like)

```java
public int longestCycle(int[] edges) {
    int n = edges.length;
    int[] visitedTime = new int[n];
    Arrays.fill(visitedTime, -1);
    
    int maxCycle = -1;
    
    for (int i = 0; i < n; i++) {
        if (visitedTime[i] != -1) continue;
        
        int curr = i;
        int time = 0;
        
        while (curr != -1 && visitedTime[curr] == -1) {
            visitedTime[curr] = time++;
            curr = edges[curr];
        }
        
        if (curr != -1 && visitedTime[curr] != -1) {
            // Cycle detected, calculate length
            int cycleLength = time - visitedTime[curr];
            if (cycleLength > maxCycle) {
                maxCycle = cycleLength;
            }
        }
    }
    return maxCycle;
}
```

> Har node ka outdegree 1 hai, isliye path eventually cycle mein khatam hota hai. DFS se time stamp store karo. Jab already visited node mile, cycle length = current_time - visited_time[that_node].

**Time:** O(n)

---

### 13. Find All Cycles in a Directed Graph (Tarjan's Algorithm)
**Problem:** Directed graph mein saare cycles find karo.

**Algorithm:** Tarjan's algorithm for SCC (Strongly Connected Components)

```java
public List<List<Integer>> findAllCycles(int n, List<Integer>[] graph) {
    int[] disc = new int[n];
    int[] low = new int[n];
    boolean[] inStack = new boolean[n];
    Stack<Integer> stack = new Stack<>();
    List<List<Integer>> cycles = new ArrayList<>();
    int[] time = {0};
    
    Arrays.fill(disc, -1);
    
    for (int i = 0; i < n; i++) {
        if (disc[i] == -1) {
            dfsTarjan(i, graph, disc, low, inStack, stack, cycles, time);
        }
    }
    return cycles;
}

private void dfsTarjan(int u, List<Integer>[] graph, int[] disc, int[] low, 
                       boolean[] inStack, Stack<Integer> stack, 
                       List<List<Integer>> cycles, int[] time) {
    disc[u] = low[u] = ++time[0];
    stack.push(u);
    inStack[u] = true;
    
    for (int v : graph[u]) {
        if (disc[v] == -1) {
            dfsTarjan(v, graph, disc, low, inStack, stack, cycles, time);
            low[u] = Math.min(low[u], low[v]);
        } else if (inStack[v]) {
            low[u] = Math.min(low[u], disc[v]);
        }
    }
    
    // If u is root of SCC (cycle)
    if (low[u] == disc[u]) {
        List<Integer> component = new ArrayList<>();
        int w;
        do {
            w = stack.pop();
            inStack[w] = false;
            component.add(w);
        } while (w != u);
        
        if (component.size() > 1) cycles.add(component);
    }
}
```

> Tarjan's algorithm har Strongly Connected Component (SCC) find karta hai. SCC jiska size > 1 hai, woh cycle represent karta hai. Low link values use hote hain.

**Time:** O(V+E)

---

### 14. Minimum Number of Vertices to Reach All Nodes (LeetCode 1557)
**Problem:** Directed graph. Kitne nodes se start karo ki saare nodes reachable ho? (No cycle version)

**Algorithm:** Nodes with indegree 0 are answer

```java
public List<Integer> findSmallestSetOfVertices(int n, List<List<Integer>> edges) {
    int[] indegree = new int[n];
    
    for (List<Integer> edge : edges) {
        indegree[edge.get(1)]++;
    }
    
    List<Integer> result = new ArrayList<>();
    for (int i = 0; i < n; i++) {
        if (indegree[i] == 0) result.add(i);
    }
    return result;
}
```

> DAG mein indegree 0 wale nodes hi starting points hote hain. Cycle ho toh thoda different hota hai, but generally ye approach kaam karti hai.

**Time:** O(V+E)

---

### 15. Cycle Detection in a Linked List (Floyd's Cycle Detection)
**Problem:** Linked list mein cycle hai ya nahi? Cycle start node kya hai?

**Algorithm:** Floyd's Tortoise and Hare (Slow-Fast pointers)

```java
public boolean hasCycle(ListNode head) {
    if (head == null) return false;
    
    ListNode slow = head;
    ListNode fast = head;
    
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow == fast) return true;
    }
    return false;
}

public ListNode detectCycle(ListNode head) {
    ListNode slow = head, fast = head;
    
    // Find meeting point
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow == fast) break;
    }
    
    if (fast == null || fast.next == null) return null;
    
    // Find cycle start
    slow = head;
    while (slow != fast) {
        slow = slow.next;
        fast = fast.next;
    }
    return slow;
}
```

> Slow 1 step, fast 2 step chalega. Cycle hai toh fast slow ko pakad lega. Cycle start dhundhne ke liye, meeting point ke baad slow ko head pe le jao, phir dono 1 step se chalo - jaha mile wahi start.

**Time:** O(n)

---

### 16. Find All People With Secret (LeetCode 2092) - Cycle + Time Based
**Problem:** Meetings [time, person1, person2]. Secret initially with person 0. Jo secret janta hai, wo meeting time pe share karta hai. Find all who know secret.

**Algorithm:** BFS with time ordering + Union-Find with time

```java
public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {
    // Sort meetings by time
    Arrays.sort(meetings, (a, b) -> a[2] - b[2]);
    
    Map<Integer, List<int[]>> timeMeetings = new TreeMap<>();
    for (int[] m : meetings) {
        timeMeetings.computeIfAbsent(m[2], k -> new ArrayList<>()).add(new int[]{m[0], m[1]});
    }
    
    boolean[] knows = new boolean[n];
    knows[0] = true;
    knows[firstPerson] = true;
    
    for (int time : timeMeetings.keySet()) {
        List<int[]> meetingsAtTime = timeMeetings.get(time);
        
        // Build graph for this time
        Map<Integer, List<Integer>> graph = new HashMap<>();
        Set<Integer> participants = new HashSet<>();
        
        for (int[] m : meetingsAtTime) {
            int u = m[0], v = m[1];
            graph.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
            graph.computeIfAbsent(v, k -> new ArrayList<>()).add(u);
            participants.add(u);
            participants.add(v);
        }
        
        // BFS for each participant who knows secret
        Queue<Integer> queue = new LinkedList<>();
        for (int p : participants) {
            if (knows[p]) queue.offer(p);
        }
        
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            for (int neighbor : graph.getOrDefault(curr, new ArrayList<>())) {
                if (!knows[neighbor]) {
                    knows[neighbor] = true;
                    queue.offer(neighbor);
                }
            }
        }
    }
    
    List<Integer> result = new ArrayList<>();
    for (int i = 0; i < n; i++) {
        if (knows[i]) result.add(i);
    }
    return result;
}
```

> Meetings ko time se group karo. Har time group mein graph banao aur BFS se secret spread karo. Isme cycle detection indirectly BFS se hoti hai - secret already know karne wala node se BFS karo.

**Time:** O(N log N + M)

---

### 17. Maximum Employees to Be Invited to a Meeting (LeetCode 2127)
**Problem:** Favorites array (each employee has one favorite). Maximum cycle-based invitation.

**Algorithm:** Find cycles + handle 2-cycles with chains

```java
public int maximumInvitations(int[] favorite) {
    int n = favorite.length;
    int[] indegree = new int[n];
    
    // Count indegree for topological sort
    for (int f : favorite) indegree[f]++;
    
    // Topological sort to remove non-cycle nodes
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < n; i++) {
        if (indegree[i] == 0) queue.offer(i);
    }
    
    int[] depth = new int[n];
    while (!queue.isEmpty()) {
        int u = queue.poll();
        int v = favorite[u];
        depth[v] = Math.max(depth[v], depth[u] + 1);
        if (--indegree[v] == 0) queue.offer(v);
    }
    
    // Find cycles and 2-cycle chains
    int maxCycle = 0;
    int twoCycleSum = 0;
    
    for (int i = 0; i < n; i++) {
        if (indegree[i] == 0) continue;
        
        // Count cycle length
        int cycleLen = 0;
        int curr = i;
        while (indegree[curr] != 0) {
            indegree[curr] = 0;  // Mark visited
            cycleLen++;
            curr = favorite[curr];
        }
        
        if (cycleLen == 2) {
            // For 2-cycle, add chains from both nodes
            twoCycleSum += 2 + depth[i] + depth[favorite[i]];
        } else {
            maxCycle = Math.max(maxCycle, cycleLen);
        }
    }
    
    return Math.max(maxCycle, twoCycleSum);
}
```

> Pehle topological sort se non-cycle nodes remove karo. Phir cycles detect karo. 2-cycle special case hai - usme chains bhi add ho sakti hain.

**Time:** O(n)

---

### 18. Parallel Courses III (LeetCode 2050) - Longest Path in DAG
**Problem:** Courses with prerequisites, each course has time. Minimum semesters? (DAG with longest path)

**Algorithm:** Topological sort + DP for longest path

```java
public int minimumTime(int n, int[][] relations, int[] time) {
    List<Integer>[] graph = new ArrayList[n];
    int[] indegree = new int[n];
    
    for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
    
    for (int[] rel : relations) {
        int u = rel[0] - 1, v = rel[1] - 1;
        graph[u].add(v);
        indegree[v]++;
    }
    
    Queue<Integer> queue = new LinkedList<>();
    int[] dist = new int[n];
    
    for (int i = 0; i < n; i++) {
        if (indegree[i] == 0) {
            queue.offer(i);
            dist[i] = time[i];
        }
    }
    
    while (!queue.isEmpty()) {
        int u = queue.poll();
        for (int v : graph[u]) {
            dist[v] = Math.max(dist[v], dist[u] + time[v]);
            if (--indegree[v] == 0) queue.offer(v);
        }
    }
    
    int maxTime = 0;
    for (int t : dist) maxTime = Math.max(maxTime, t);
    return maxTime;
}
```

> Cycle detection nahi hai directly, lekin prerequisite graph mein cycle check karna important hai. Yahan DAG assume kiya hai. Topological sort + DP se longest path find karo.

**Time:** O(V+E)

---

### 19. Critical Connections in a Network (LeetCode 1192) - Tarjan's Bridge Detection
**Problem:** Bridges in graph. Wo edges jinko remove karne se graph disconnected ho jaye.

**Algorithm:** Tarjan's algorithm with discovery time and low link values

```java
List<List<Integer>> result = new ArrayList<>();
int time = 0;

public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
    List<Integer>[] graph = new ArrayList[n];
    for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
    
    for (List<Integer> conn : connections) {
        graph[conn.get(0)].add(conn.get(1));
        graph[conn.get(1)].add(conn.get(0));
    }
    
    int[] disc = new int[n];
    int[] low = new int[n];
    Arrays.fill(disc, -1);
    
    dfs(graph, disc, low, 0, -1);
    return result;
}

private void dfs(List<Integer>[] graph, int[] disc, int[] low, int u, int parent) {
    disc[u] = low[u] = ++time;
    
    for (int v : graph[u]) {
        if (v == parent) continue;
        
        if (disc[v] == -1) {
            dfs(graph, disc, low, v, u);
            low[u] = Math.min(low[u], low[v]);
            
            // Bridge condition
            if (low[v] > disc[u]) {
                result.add(Arrays.asList(u, v));
            }
        } else {
            low[u] = Math.min(low[u], disc[v]);
        }
    }
}
```

> Tarjan's algorithm - har node ke discovery time aur low link value calculate karo. Agar low[v] > disc[u], toh edge u-v bridge hai. Back edge se low update hota hai.

**Time:** O(V+E)

---

### 20. Minimum Number of Days to Disconnect Island (LeetCode 1568)
**Problem:** Island (grid of 1's) ko disconnect karne ke liye minimum cells remove karo.

**Algorithm:** Cycle detection + Articulation points (Tarjan's for grid)

```java
public int minDays(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    
    // Count islands
    if (countIslands(grid) != 1) return 0;
    
    // Try removing one cell
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (grid[i][j] == 1) {
                grid[i][j] = 0;
                if (countIslands(grid) != 1) return 1;
                grid[i][j] = 1;
            }
        }
    }
    
    // If one cell removal doesn't work, answer is 2
    return 2;
}

private int countIslands(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    boolean[][] visited = new boolean[m][n];
    int count = 0;
    
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (grid[i][j] == 1 && !visited[i][j]) {
                dfsCount(grid, visited, i, j);
                count++;
            }
        }
    }
    return count;
}

private void dfsCount(int[][] grid, boolean[][] visited, int i, int j) {
    if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length) return;
    if (grid[i][j] == 0 || visited[i][j]) return;
    
    visited[i][j] = true;
    dfsCount(grid, visited, i+1, j);
    dfsCount(grid, visited, i-1, j);
    dfsCount(grid, visited, i, j+1);
    dfsCount(grid, visited, i, j-1);
}
```

> Pehle check karo ki already disconnected hai ya nahi. Phir har cell ko remove karke check karo ki disconnected ho jata hai ya nahi. Agar nahi, toh answer 2 hai (kisi bhi 1×1 island ko disconnect karne ke liye 2 cells remove karne padte hain).

**Time:** O((m×n)²)

---

## 📊 Complete Summary Table

| # | Problem | Algorithm | Detection Method | Complexity |
|---|---------|-----------|------------------|------------|
| 1 | Course Schedule | DFS with 3 states | Visiting state | O(V+E) |
| 2 | Course Schedule II | Kahn's Algorithm | Indegree count | O(V+E) |
| 3 | Redundant Connection | Union-Find | Same root before union | O(E×α(V)) |
| 4 | Redundant Connection II | Union-Find + Parent tracking | Two cases | O(n) |
| 5 | Detect Cycle Directed | DFS with 3 states | Visiting state | O(V+E) |
| 6 | Detect Cycle Undirected | DFS with parent | Visited neighbor not parent | O(V+E) |
| 7 | Alien Dictionary | Kahn's Algorithm | Topological sort incomplete | O(C) |
| 8 | Graph Valid Tree | DFS + Edge count | Cycle detection | O(V+E) |
| 9 | Eventual Safe States | Reverse Kahn's | Terminal nodes BFS | O(V+E) |
| 10 | Minimum Height Trees | Leaf removal | Degree-based | O(V) |
| 11 | Cycle in 2D Grid | DFS with parent | Same char cycle | O(m×n) |
| 12 | Largest Cycle | DFS with time array | Time stamp comparison | O(n) |
| 13 | Find All Cycles | Tarjan's SCC | Low link values | O(V+E) |
| 14 | Smallest Set of Vertices | Indegree analysis | Indegree 0 nodes | O(V+E) |
| 15 | Linked List Cycle | Floyd's Cycle | Slow-Fast pointers | O(n) |
| 16 | Find All People With Secret | BFS + Time ordering | Secret propagation | O(N log N + M) |
| 17 | Maximum Invitations | Topological sort + Cycle | Remove non-cycle nodes | O(n) |
| 18 | Parallel Courses | Topological sort + DP | DAG longest path | O(V+E) |
| 19 | Critical Connections | Tarjan's Bridge | low[v] > disc[u] | O(V+E) |
| 20 | Disconnect Island | Articulation points | Island counting | O((mn)²) |

---

## 🎯 Cycle Detection Interview Tips

### 1. Algorithm Selection:

| Scenario | Best Algorithm | Reason |
|----------|----------------|--------|
| Directed graph, small | DFS with 3 states | Simple, intuitive |
| Directed graph, large | Kahn's Algorithm | BFS, no recursion |
| Undirected graph | DFS with parent | Straightforward |
| Dynamic connectivity | Union-Find | Online queries |
| Need cycle length | Floyd's / Time array | Efficient for outdegree 1 |

### 2. DFS 3-State Template (MOST IMPORTANT):
```java
int[] state = new int[n];  // 0=unvisited, 1=visiting, 2=processed

boolean hasCycle(int u) {
    if (state[u] == 1) return true;
    if (state[u] == 2) return false;
    
    state[u] = 1;
    for (int v : graph[u]) {
        if (hasCycle(v)) return true;
    }
    state[u] = 2;
    return false;
}
```

### 3. Union-Find Template for Undirected Cycle:
```java
int[] parent = new int[n];
for (int i = 0; i < n; i++) parent[i] = i;

for (int[] edge : edges) {
    if (find(edge[0]) == find(edge[1])) {
        // Cycle detected
    } else {
        union(edge[0], edge[1]);
    }
}
```

### 4. Common Mistakes:
- **Directed vs Undirected:** Parent check bhoolna (undirected mein parent skip karna important hai)
- **Recursion stack overflow:** Large graphs ke liye iterative approach (Kahn's) use karo
- **Visited state incomplete:** Directed graphs mein sirf visited boolean kaafi nahi, 3 states chahiye

### 5. Cycle Detection Applications:

| Real World Problem | Cycle Detection Use |
|-------------------|---------------------|
| Package dependencies | Deadlock detection |
| Course prerequisites | Circular dependencies |
| Git merge conflicts | Circular branch dependencies |
| Database transaction | Deadlock detection |
| Scheduling | Circular wait detection |

### 6. Quick Reference - Graph Types:

```java
// Directed Graph - Cycle Detection
boolean hasCycleDirected(List<Integer>[] graph) {
    int[] state = new int[n];
    for (int i = 0; i < n; i++) {
        if (state[i] == 0 && dfsCycle(i, graph, state)) return true;
    }
    return false;
}

// Undirected Graph - Cycle Detection
boolean hasCycleUndirected(List<Integer>[] graph) {
    boolean[] visited = new boolean[n];
    for (int i = 0; i < n; i++) {
        if (!visited[i] && dfsCycle(i, -1, graph, visited)) return true;
    }
    return false;
}
```
