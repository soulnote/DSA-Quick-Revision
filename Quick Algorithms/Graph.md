## 🎯 1. Graph Traversal (BFS / DFS)
**Theory:** Graph mein ghoomne ke do tareeke. **DFS** (Depth First) ek raasta pakad ke last tak jaata hai (Recursion/Stack use karta hai). **BFS** (Breadth First) level by level explore karta hai (Queue use karta hai) .
**Clue:** Jab "explore all paths", "connectivity check", ya "shortest path in unweighted graph" dikhe.

```java
import java.util.*;

public class GraphTraversal {
    // DFS Traversal (Recursive)
    public void dfs(int node, boolean[] visited, ArrayList<ArrayList<Integer>> adj, ArrayList<Integer> ans) {
        visited[node] = true;
        ans.add(node);
        for (int neighbor : adj.get(node)) {
            if (!visited[neighbor]) dfs(neighbor, visited, adj, ans);
        }
    }

    // BFS Traversal
    public ArrayList<Integer> bfs(int V, ArrayList<ArrayList<Integer>> adj) {
        boolean[] visited = new boolean[V];
        Queue<Integer> q = new LinkedList<>();
        ArrayList<Integer> ans = new ArrayList<>();
        q.add(0); visited[0] = true;
        
        while (!q.isEmpty()) {
            int node = q.poll();
            ans.add(node);
            for (int neighbor : adj.get(node)) {
                if (!visited[neighbor]) { visited[neighbor] = true; q.add(neighbor); }
            }
        }
        return ans;
    }
}
```

## 🎯 2. Cycle Detection
**Theory:**
- **Undirected Graph:** Agar DFS karte waqt koi visited neighbor mil jaye jo `parent` nahi hai, matlab cycle hai.
- **Directed Graph:** Recursion Stack (`recStack`) maintain karo. Agar koi neighbor stack mein pehle se hai, to cycle hai .

```java
// Directed Graph Cycle Detection
public boolean isCyclicDirected(int V, ArrayList<ArrayList<Integer>> adj) {
    boolean[] visited = new boolean[V];
    boolean[] recStack = new boolean[V];
    for (int i = 0; i < V; i++) if (dfsCheck(i, visited, recStack, adj)) return true;
    return false;
}
private boolean dfsCheck(int node, boolean[] vis, boolean[] rec, ArrayList<ArrayList<Integer>> adj) {
    vis[node] = true; rec[node] = true;
    for (int nbr : adj.get(node)) {
        if (!vis[nbr] && dfsCheck(nbr, vis, rec, adj)) return true;
        else if (rec[nbr]) return true;
    }
    rec[node] = false; return false;
}
```

## 🎯 3. Topological Sort
**Theory:** Sirf **Directed Acyclic Graph (DAG)** mein lagta hai. Kaam ka dependency order (e.g., Course Schedule). DFS use karte hain: DFS khatam hone ke baad node ko Stack mein push karo, end mein stack ulta order de dega .

```java
public int[] topoSort(int V, ArrayList<ArrayList<Integer>> adj) {
    Stack<Integer> st = new Stack<>();
    boolean[] vis = new boolean[V];
    for (int i = 0; i < V; i++) if (!vis[i]) dfsTopo(i, vis, adj, st);
    
    int[] ans = new int[V]; int i = 0;
    while (!st.isEmpty()) ans[i++] = st.pop();
    return ans;
}
```

## 🎯 4. Union Find (Disjoint Set)
**Theory:** Dynamic connectivity ke liye use hota hai. Do operations hote hain: `find` (kisi node ka baap/root dhundho) aur `union` (do alag components ko jodo). **Path Compression** se tree flat rahta hai .

```java
class DSU {
    int[] parent, rank;
    public DSU(int n) { 
        parent = new int[n]; rank = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i; 
    }
    public int find(int x) { 
        if (parent[x] != x) parent[x] = find(parent[x]); // Path Compression
        return parent[x]; 
    }
    public void union(int x, int y) {
        int xr = find(x), yr = find(y);
        if (xr == yr) return;
        if (rank[xr] < rank[yr]) parent[xr] = yr;
        else if (rank[xr] > rank[yr]) parent[yr] = xr;
        else { parent[yr] = xr; rank[xr]++; }
    }
}
```

## 🎯 5. Shortest Path Algorithms
Jab distances minimize karne ho.

- **Dijkstra's Algorithm:** **Non-Negative Weights** ke liye. **PriorityQueue** (Min Heap) use karta hai. Greedy approach: hamesha sabse kam distance wale node ko pehle process karo .
- **Bellman-Ford:** **Negative Weights** handle kar sakta hai aur Negative Cycle bhi detect kar leta hai. `V-1` baar saari edges ko relax karo .

```java
// Dijkstra's Algorithm (Using PriorityQueue)
public int[] dijkstra(int V, ArrayList<ArrayList<int[]>> adj, int S) {
    int[] dist = new int[V]; Arrays.fill(dist, Integer.MAX_VALUE);
    PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[1] - b[1]);
    dist[S] = 0; pq.add(new int[]{S, 0});
    
    while (!pq.isEmpty()) {
        int[] curr = pq.poll(); int node = curr[0], d = curr[1];
        if (d > dist[node]) continue;
        for (int[] nbr : adj.get(node)) {
            int edgeWeight = nbr[1], adjNode = nbr[0];
            if (d + edgeWeight < dist[adjNode]) {
                dist[adjNode] = d + edgeWeight;
                pq.add(new int[]{adjNode, dist[adjNode]});
            }
        }
    }
    return dist;
}
```

## 🎯 6. Minimum Spanning Tree (MST)
**Theory:** Graph ke saare nodes ko minimum cost/weight mein jodo bina cycle banaye.
- **Kruskal's Algorithm:** Saari edges ko weight ke hisaab se sort karo. **DSU** use karke check karo ki edge add karne se cycle to nahi ban rahi. Agar nahi, to add kar do .

```java
// Kruskal's MST (Using DSU)
public int kruskalMST(int V, ArrayList<int[]> edges) {
    Collections.sort(edges, (a,b) -> a[2] - b[2]); // Sort by weight
    DSU dsu = new DSU(V);
    int mstCost = 0, edgesUsed = 0;
    
    for (int[] edge : edges) {
        int u = edge[0], v = edge[1], w = edge[2];
        if (dsu.find(u) != dsu.find(v)) {
            dsu.union(u, v);
            mstCost += w; edgesUsed++;
            if (edgesUsed == V-1) break;
        }
    }
    return mstCost;
}
```

## 📋 Summary: Kab Kya Lagana Hai (Cheat Sheet)

| Problem Type | Algorithm/Data Structure | Key Indicator |
| :--- | :--- | :--- |
| **Connectivity / Traversal** | DFS, BFS | "Explore all paths", "Connected components" |
| **Unweighted Shortest Path** | BFS | Grid problems, "Minimum steps" |
| **Cycle in Undirected** | DFS (parent check), DSU | "Tree validation", "Redundant connection" |
| **Cycle in Directed** | DFS (recStack), Kahn's Algo | "Course Schedule", "Deadlock detection" |
| **Dependency Order** | Topological Sort | "Prerequisites", "Build Order" |
| **Weighted Shortest Path** | Dijkstra (Positive), Bellman-Ford (Negative) | "Network Delay", "Cheapest Flight" |
| **Connect Nodes Min. Cost** | Kruskal's (Sparse), Prim's (Dense) | "Min cost to connect all points" |


## 🎯 7. Bipartite Graph Check (Graph Coloring)
**Theory:** Graph ko do colors se aise color karo ki koi bhi do adjacent nodes ka color same na ho.
- **Clue:** Agar graph mein **Odd Length Cycle** hai, to bipartite nahi ho sakta.
- **Logic:** BFS/DFS karte waqt alternating colors assign karo (`0` aur `1`). Agar kabhi adjacent node same color ka mile to `false`.

```java
public boolean isBipartite(int V, ArrayList<ArrayList<Integer>> adj) {
    int[] color = new int[V];
    Arrays.fill(color, -1); // -1 means uncolored
    
    for (int i = 0; i < V; i++) {
        if (color[i] == -1) {
            Queue<Integer> q = new LinkedList<>();
            q.add(i); color[i] = 0;
            while (!q.isEmpty()) {
                int node = q.poll();
                for (int nbr : adj.get(node)) {
                    if (color[nbr] == -1) {
                        color[nbr] = 1 - color[node]; // Alternate color
                        q.add(nbr);
                    } else if (color[nbr] == color[node]) {
                        return false; // Conflict mil gaya
                    }
                }
            }
        }
    }
    return true;
}
```

## 🎯 8. Strongly Connected Components (Kosaraju's Algorithm)
**Theory:** Directed Graph mein aise components jahan har node se har doosri node tak jaaya ja sakta hai.
- **Steps:**
    1. **Step 1:** DFS karke finish time ke order mein Stack mein push karo.
    2. **Step 2:** Graph ki saari edges ko **Reverse** (Transpose) karo.
    3. **Step 3:** Stack se pop karke Transpose Graph pe DFS lagao. Jitni baar DFS call hogi, utne SCC hain.

```java
public int kosaraju(int V, ArrayList<ArrayList<Integer>> adj) {
    // Step 1: Ordering via DFS
    Stack<Integer> st = new Stack<>();
    boolean[] vis = new boolean[V];
    for (int i = 0; i < V; i++) if (!vis[i]) dfsOrder(i, vis, adj, st);
    
    // Step 2: Transpose Graph (Reverse Edges)
    ArrayList<ArrayList<Integer>> transpose = new ArrayList<>();
    for (int i = 0; i < V; i++) transpose.add(new ArrayList<>());
    for (int i = 0; i < V; i++) {
        for (int nbr : adj.get(i)) transpose.get(nbr).add(i);
    }
    
    // Step 3: DFS on Transpose
    Arrays.fill(vis, false);
    int sccCount = 0;
    while (!st.isEmpty()) {
        int node = st.pop();
        if (!vis[node]) {
            dfsSimple(node, vis, transpose);
            sccCount++;
        }
    }
    return sccCount;
}
private void dfsOrder(int node, boolean[] vis, ArrayList<ArrayList<Integer>> adj, Stack<Integer> st) {
    vis[node] = true;
    for (int nbr : adj.get(node)) if (!vis[nbr]) dfsOrder(nbr, vis, adj, st);
    st.push(node);
}
private void dfsSimple(int node, boolean[] vis, ArrayList<ArrayList<Integer>> adj) {
    vis[node] = true;
    for (int nbr : adj.get(node)) if (!vis[nbr]) dfsSimple(nbr, vis, adj);
}
```

## 🎯 9. Bridges & Articulation Points (Tarjan's Algorithm)
**Theory:** Graph mein wo edges jinke hat jaane se graph **2 ya zyada components** mein toot jaata hai (Bridges), ya wo nodes jinke hat jaane se graph toot jaata hai (Articulation Points).
- **Logic:** `tin` (Time of Insertion) aur `low` (Lowest time reachable without using parent edge) maintain karte hain. Agar `low[nbr] > tin[node]`, to wo edge ek **Bridge** hai .

```java
class TarjanBridge {
    private int timer = 0;
    
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (List<Integer> conn : connections) {
            adj.get(conn.get(0)).add(conn.get(1));
            adj.get(conn.get(1)).add(conn.get(0));
        }
        
        boolean[] vis = new boolean[n];
        int[] tin = new int[n], low = new int[n];
        List<List<Integer>> bridges = new ArrayList<>();
        
        for (int i = 0; i < n; i++) if (!vis[i]) dfsBridge(i, -1, vis, tin, low, adj, bridges);
        return bridges;
    }
    
    private void dfsBridge(int node, int parent, boolean[] vis, int[] tin, int[] low, 
                           ArrayList<ArrayList<Integer>> adj, List<List<Integer>> bridges) {
        vis[node] = true;
        tin[node] = low[node] = timer++;
        
        for (int nbr : adj.get(node)) {
            if (nbr == parent) continue;
            if (!vis[nbr]) {
                dfsBridge(nbr, node, vis, tin, low, adj, bridges);
                low[node] = Math.min(low[node], low[nbr]);
                // Bridge Condition
                if (low[nbr] > tin[node]) {
                    bridges.add(Arrays.asList(node, nbr));
                }
            } else {
                // Back Edge
                low[node] = Math.min(low[node], tin[nbr]);
            }
        }
    }
}
```

## 🎯 10. Word Ladder (BFS on Strings)
**Theory:** Ye **Shortest Path in Unweighted Graph** ka hi variant hai. Ek word se doosre word tak jaane ka minimum steps. Nodes words hain, edges un words ke beech mein hain jo ek character se differ karte hain. BFS is perfect here.

```java
public int ladderLength(String beginWord, String endWord, List<String> wordList) {
    Set<String> wordSet = new HashSet<>(wordList);
    if (!wordSet.contains(endWord)) return 0;
    
    Queue<String> q = new LinkedList<>();
    q.add(beginWord);
    int level = 1;
    
    while (!q.isEmpty()) {
        int size = q.size();
        for (int i = 0; i < size; i++) {
            String curr = q.poll();
            char[] arr = curr.toCharArray();
            for (int j = 0; j < arr.length; j++) {
                char original = arr[j];
                for (char c = 'a'; c <= 'z'; c++) {
                    if (c == original) continue;
                    arr[j] = c;
                    String next = new String(arr);
                    if (next.equals(endWord)) return level + 1;
                    if (wordSet.contains(next)) {
                        q.add(next);
                        wordSet.remove(next); // Visited mark kar do
                    }
                }
                arr[j] = original;
            }
        }
        level++;
    }
    return 0;
}
```

## 🎯 11. Flood Fill (DFS on Grid)
**Theory:** Graph traversal hi hai lekin **Matrix/Grid** pe. 2D Array mein DFS/BFS. 4-Directional (Up, Down, Left, Right) ya 8-Directional (Diagonals included) move kar sakte hain.

```java
public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
    int oldColor = image[sr][sc];
    if (oldColor == newColor) return image;
    dfsFill(image, sr, sc, oldColor, newColor);
    return image;
}
private void dfsFill(int[][] img, int r, int c, int oldColor, int newColor) {
    // Boundary Conditions
    if (r < 0 || c < 0 || r >= img.length || c >= img[0].length || img[r][c] != oldColor) return;
    
    img[r][c] = newColor;
    dfsFill(img, r+1, c, oldColor, newColor);
    dfsFill(img, r-1, c, oldColor, newColor);
    dfsFill(img, r, c+1, oldColor, newColor);
    dfsFill(img, r, c-1, oldColor, newColor);
}
```

## 🎯 12. 0-1 BFS (Shortest Path with Weights 0 and 1)
**Theory:** Agar graph ke edges ka weight sirf `0` ya `1` hai, to Dijkstra (O(E log V)) ki jagah **Deque** use karke O(V+E) mein solve kar sakte hain.
- **Logic:** Agar edge weight `0` hai to neighbor ko **front** mein push karo. Agar `1` hai to **back** mein push karo.

```java
public int zeroOneBFS(int V, ArrayList<ArrayList<int[]>> adj, int src, int dest) {
    int[] dist = new int[V]; Arrays.fill(dist, Integer.MAX_VALUE);
    Deque<Integer> dq = new LinkedList<>();
    dist[src] = 0; dq.addFirst(src);
    
    while (!dq.isEmpty()) {
        int node = dq.pollFirst();
        for (int[] nbrInfo : adj.get(node)) {
            int nbr = nbrInfo[0], wt = nbrInfo[1];
            if (dist[node] + wt < dist[nbr]) {
                dist[nbr] = dist[node] + wt;
                if (wt == 0) dq.addFirst(nbr);
                else dq.addLast(nbr);
            }
        }
    }
    return dist[dest];
}
```

## 📋 Full Cheat Sheet with Time Complexities

| Algorithm / Problem | Time Complexity | Memory Trick / Use Case |
| :--- | :--- | :--- |
| **BFS / DFS** | O(V + E) | Base template for everything |
| **Dijkstra** | O((V+E) log V) | Positive Weights, PQ |
| **Bellman-Ford** | O(V * E) | Negative Weights allowed |
| **Floyd-Warshall** | O(V³) | All Pairs Shortest Path |
| **Kruskal** | O(E log E) | Sparse Graphs MST |
| **Prim's** | O((V+E) log V) | Dense Graphs MST |
| **Kosaraju / Tarjan** | O(V + E) | Strongly Connected / Bridges |
| **Topological Sort** | O(V + E) | Dependencies, Kahn's / DFS |
| **0-1 BFS** | O(V + E) | Edges weights only 0 or 1 |

### 🔥 Advanced Patterns (Iska code thoda lamba hai, lekin logic important hai)

- **Multi-Source BFS:** Jab multiple starting points hon (e.g., Rotten Oranges). Saare sources ko ek saath Queue mein daal kar BFS chalao.
- **Eulerian Path/Circuit:** Graph mein har edge ko exactly ek baar visit karna. **Hierholzer's Algorithm** use hota hai (Post-order DFS).
- **A* Search:* Dijkstra ka advanced version. Heuristic function use karta hai taaki destination ki taraf smartly move kare.
Bilkul, ab hum Graph ke kuch **Advanced aur Twisted Patterns** dekhte hain jo FAANG interviews mein aksar poochhe jaate hain. Ye patterns basic BFS/DFS se thode hatke hain lekin inka logic samajhna bahut zaroori hai.

### 🎯 13. Dijkstra with State Tracking (Cheapest Flights with K Stops)
**Theory:** Normal Dijkstra sirf distance minimize karta hai. Lekin agar constraint ho jaise "**Sirf K stops** allowed hain", to Queue mein `(node, cost, stops)` store karna padta hai.
**Important:** Yahan visited array use nahi karte kyunki ho sakta hai ek node tak zyada cost mein pahuncho lekin kam stops mein, jo aage jaake faaydemand ho.

```java
public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
    // Adjacency List: 0 -> (1, 100), (2, 500)
    ArrayList<ArrayList<int[]>> adj = new ArrayList<>();
    for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
    for (int[] f : flights) adj.get(f[0]).add(new int[]{f[1], f[2]});
    
    // Queue stores: (node, cost, stops)
    Queue<int[]> q = new LinkedList<>();
    q.add(new int[]{src, 0, 0});
    
    int[] minCost = new int[n];
    Arrays.fill(minCost, Integer.MAX_VALUE);
    minCost[src] = 0;
    
    while (!q.isEmpty()) {
        int[] curr = q.poll();
        int node = curr[0], cost = curr[1], stops = curr[2];
        
        if (stops > k) continue; // K se zyada stops allowed nahi
        
        for (int[] nbrInfo : adj.get(node)) {
            int nbr = nbrInfo[0], price = nbrInfo[1];
            int newCost = cost + price;
            
            // Relaxation: Agar better cost mila OR (maybe thoda mehnga hai lekin isliye ignore nahi karenge)
            if (newCost < minCost[nbr] && stops <= k) {
                minCost[nbr] = newCost;
                q.add(new int[]{nbr, newCost, stops + 1});
            }
        }
    }
    return minCost[dst] == Integer.MAX_VALUE ? -1 : minCost[dst];
}
```

### 🎯 14. Kahn's Algorithm (Topological Sort BFS Way)
**Theory:** DFS wala Topological Sort stack use karta hai. Kahn's Algorithm **Indegree** use karta hai.
**Logic:** Jis node ki indegree (incoming edges) `0` ho, use Queue mein daalo. Use process karo aur uske neighbors ki indegree kam karo. Agar neighbor ki indegree `0` ho jaye to Queue mein daalo. Ye BFS ka hi ek roop hai.

```java
public int[] kahnTopoSort(int V, ArrayList<ArrayList<Integer>> adj) {
    int[] indegree = new int[V];
    for (int i = 0; i < V; i++) {
        for (int nbr : adj.get(i)) indegree[nbr]++;
    }
    
    Queue<Integer> q = new LinkedList<>();
    for (int i = 0; i < V; i++) if (indegree[i] == 0) q.add(i);
    
    int[] topo = new int[V];
    int idx = 0;
    int count = 0; // Cycle detection ke liye
    
    while (!q.isEmpty()) {
        int node = q.poll();
        topo[idx++] = node;
        count++;
        
        for (int nbr : adj.get(node)) {
            indegree[nbr]--;
            if (indegree[nbr] == 0) q.add(nbr);
        }
    }
    
    // Agar count != V, matlab cycle hai (Directed Graph mein)
    if (count != V) {
        System.out.println("Cycle detected! Topological sort possible nahi hai.");
        return new int[0];
    }
    return topo;
}
```

### 🎯 15. Clone a Graph (Deep Copy)
**Theory:** Graph pointer based hai (Node class). Deep copy banani hai. DFS ya BFS karte waqt **HashMap** maintain karo `Original Node -> New Node` ka mapping. Taaki infinite loop na fase.

```java
class Node {
    public int val;
    public List<Node> neighbors;
    // Constructor...
}

public Node cloneGraph(Node node) {
    if (node == null) return null;
    HashMap<Node, Node> map = new HashMap<>(); // Old -> New Mapping
    return dfsClone(node, map);
}

private Node dfsClone(Node node, HashMap<Node, Node> map) {
    if (map.containsKey(node)) return map.get(node); // Already cloned
    
    Node copy = new Node(node.val);
    map.put(node, copy); // Pehle map mein daalo taaki cycle na aaye
    
    for (Node nbr : node.neighbors) {
        copy.neighbors.add(dfsClone(nbr, map));
    }
    return copy;
}
```

### 🎯 16. Alien Dictionary (Topological Sort on Characters)
**Theory:** Ye Graph ka tricky application hai. Humein words ke order se pata lagana hai ki characters ka order kya hai.
**Logic:** Agar `word1` `word2` se pehle aata hai, to `word1[0]` -> `word2[0]` ek edge hai. Agar dono ka pehla char same hai to aage wale char compare karo. Fir in characters pe Topological Sort lagao.

```java
public String alienOrder(String[] words) {
    // Step 1: Graph banane ke liye saare unique characters collect karo
    Map<Character, Set<Character>> adj = new HashMap<>();
    Map<Character, Integer> indegree = new HashMap<>();
    for (String w : words) for (char c : w.toCharArray()) indegree.putIfAbsent(c, 0);
    
    // Step 2: Compare adjacent words
    for (int i = 0; i < words.length - 1; i++) {
        String w1 = words[i], w2 = words[i+1];
        // Invalid case check: "abc" "ab" (Prefix wala case)
        if (w1.length() > w2.length() && w1.startsWith(w2)) return "";
        
        int minLen = Math.min(w1.length(), w2.length());
        for (int j = 0; j < minLen; j++) {
            char c1 = w1.charAt(j), c2 = w2.charAt(j);
            if (c1 != c2) {
                adj.putIfAbsent(c1, new HashSet<>());
                if (!adj.get(c1).contains(c2)) {
                    adj.get(c1).add(c2);
                    indegree.put(c2, indegree.getOrDefault(c2, 0) + 1);
                }
                break; // Pehla different character hi order decide karega
            }
        }
    }
    
    // Step 3: Kahn's Algorithm (BFS Topo Sort)
    Queue<Character> q = new LinkedList<>();
    for (char c : indegree.keySet()) if (indegree.get(c) == 0) q.add(c);
    
    StringBuilder sb = new StringBuilder();
    while (!q.isEmpty()) {
        char c = q.poll(); sb.append(c);
        if (adj.containsKey(c)) {
            for (char nbr : adj.get(c)) {
                indegree.put(nbr, indegree.get(nbr) - 1);
                if (indegree.get(nbr) == 0) q.add(nbr);
            }
        }
    }
    // Agar length match nahi karti to cycle hai
    return sb.length() == indegree.size() ? sb.toString() : "";
}
```

### 🎯 17. Number of Islands (DFS on Grid)
**Theory:** Matrix mein connected '1's ka group. DFS/BFS karo aur visited ko mark karne ke liye us cell ko `'0'` (water) ya `'2'` (visited) bana do. Taki extra visited array na rakhna pade.

```java
public int numIslands(char[][] grid) {
    int count = 0;
    int rows = grid.length, cols = grid[0].length;
    
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            if (grid[i][j] == '1') {
                dfsIsland(grid, i, j);
                count++;
            }
        }
    }
    return count;
}

private void dfsIsland(char[][] grid, int r, int c) {
    if (r < 0 || c < 0 || r >= grid.length || c >= grid[0].length || grid[r][c] != '1') return;
    
    grid[r][c] = '0'; // Visited mark kar do (sink the island)
    
    dfsIsland(grid, r+1, c);
    dfsIsland(grid, r-1, c);
    dfsIsland(grid, r, c+1);
    dfsIsland(grid, r, c-1);
}
```

### 🎯 18. Reconstruct Itinerary (Eulerian Path)
**Theory:** Tumhe flights ke tickets diye gaye hain `[from, to]`. Tumhe lexicographically smallest itinerary banani hai jo saari tickets use kare. Ye **Eulerian Path** problem hai.
**Logic:** `PriorityQueue` use karo neighbors ko lexicographically sort rakhne ke liye. Jab DFS khatam ho jaye, to node ko result mein add karo (Post-order). Ant mein result reverse karo.

```java
public List<String> findItinerary(List<List<String>> tickets) {
    Map<String, PriorityQueue<String>> graph = new HashMap<>();
    for (List<String> t : tickets) {
        graph.putIfAbsent(t.get(0), new PriorityQueue<>());
        graph.get(t.get(0)).add(t.get(1));
    }
    
    LinkedList<String> result = new LinkedList<>();
    dfsEuler("JFK", graph, result);
    return result;
}

private void dfsEuler(String airport, Map<String, PriorityQueue<String>> graph, LinkedList<String> res) {
    PriorityQueue<String> destinations = graph.get(airport);
    while (destinations != null && !destinations.isEmpty()) {
        String next = destinations.poll(); // Use kar rahe hain edge ko
        dfsEuler(next, graph, res);
    }
    res.addFirst(airport); // Post-order addition (ye trick hai)
}
```

### 📋 Special Mention: Floyd-Warshall (All Pairs Shortest Path)

Iska code dekhna important hai kyunki **Dynamic Programming** pe based hai aur teen nested loops mein kaam karta hai.

```java
public int[][] floydWarshall(int n, int[][] edges) {
    int[][] dist = new int[n][n];
    for (int i = 0; i < n; i++) {
        Arrays.fill(dist[i], Integer.MAX_VALUE);
        dist[i][i] = 0;
    }
    // Initialization
    for (int[] e : edges) dist[e[0]][e[1]] = e[2];
    
    // DP Logic: dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j])
    for (int k = 0; k < n; k++) {         // Intermediate
        for (int i = 0; i < n; i++) {     // Source
            for (int j = 0; j < n; j++) { // Destination
                if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE) {
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }
    }
    return dist;
}
```
Here are the **20 most asked hard graph problems** in coding interviews, with Java solutions and Hinglish explanations.



## 📋 20 Hard Graph Problems

---

### 1. Number of Islands
**Problem:** 2D grid mein '1' (land) aur '0' (water). Kitne islands hain? (Connected horizontally/vertically)

**Approach:** DFS/BFS se har island ko visit karo aur mark karo.

```java
public int numIslands(char[][] grid) {
    if (grid == null || grid.length == 0) return 0;
    
    int count = 0;
    for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[0].length; j++) {
            if (grid[i][j] == '1') {
                count++;
                dfs(grid, i, j);
            }
        }
    }
    return count;
}

private void dfs(char[][] grid, int i, int j) {
    if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length) return;
    if (grid[i][j] != '1') return;
    
    grid[i][j] = '2';  // Mark visited
    
    dfs(grid, i+1, j);
    dfs(grid, i-1, j);
    dfs(grid, i, j+1);
    dfs(grid, i, j-1);
}
```

>  Har '1' pe jaake DFS karo aur saare connected '1' ko visit mark kar do. Jitni baar DFS call karni pade, utne islands.

**Time:** O(m × n)

---

### 2. Course Schedule (Cycle Detection in Directed Graph)
**Problem:** n courses, prerequisites [[1,0]] means course 0 before 1. Kya sab courses complete kar sakte ho?

**Approach:** Cycle detection in directed graph using DFS (3 states: unvisited, visiting, visited).

```java
public boolean canFinish(int numCourses, int[][] prerequisites) {
    List<Integer>[] graph = new ArrayList[numCourses];
    for (int i = 0; i < numCourses; i++) graph[i] = new ArrayList<>();
    
    for (int[] pre : prerequisites) {
        graph[pre[1]].add(pre[0]);
    }
    
    int[] visited = new int[numCourses];  // 0=unvisited, 1=visiting, 2=visited
    
    for (int i = 0; i < numCourses; i++) {
        if (hasCycle(i, graph, visited)) return false;
    }
    return true;
}

private boolean hasCycle(int course, List<Integer>[] graph, int[] visited) {
    if (visited[course] == 1) return true;  // Cycle detected
    if (visited[course] == 2) return false;
    
    visited[course] = 1;  // Visiting
    
    for (int next : graph[course]) {
        if (hasCycle(next, graph, visited)) return true;
    }
    
    visited[course] = 2;  // Fully processed
    return false;
}
```

>  DFS se cycle detect karo. Teen states: 0 = abhi dekha nahi, 1 = currently visiting (cycle ka suspect), 2 = safely processed. Agar visiting state pe dubara aa gaye, toh cycle hai.

**Time:** O(V + E)

---

### 3. Course Schedule II (Topological Order)
**Problem:** Courses ka order batao jisse sab complete ho sakein.

**Approach:** Topological sort using Kahn's algorithm (BFS with indegree).

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
    
    return index == numCourses ? result : new int[0];
}
```

>  Indegree zero wale nodes se start karo. Unhe remove karo aur unke neighbors ki indegree kam karo. End mein agar saare nodes process ho gaye, toh order mil gaya.

**Time:** O(V + E)

---

### 4. Network Delay Time (Dijkstra's Algorithm)
**Problem:** N nodes, edges [[u,v,w]], source se sabhi nodes tak signal pahunchne ka minimum time.

**Approach:** Dijkstra's algorithm - priority queue se shortest path.

```java
public int networkDelayTime(int[][] times, int n, int k) {
    Map<Integer, List<int[]>> graph = new HashMap<>();
    for (int[] time : times) {
        graph.computeIfAbsent(time[0], x -> new ArrayList<>()).add(new int[]{time[1], time[2]});
    }
    
    int[] dist = new int[n + 1];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[k] = 0;
    
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
    pq.offer(new int[]{k, 0});
    
    while (!pq.isEmpty()) {
        int[] curr = pq.poll();
        int node = curr[0], time = curr[1];
        
        if (time > dist[node]) continue;
        
        if (graph.containsKey(node)) {
            for (int[] edge : graph.get(node)) {
                int next = edge[0], weight = edge[1];
                int newTime = time + weight;
                if (newTime < dist[next]) {
                    dist[next] = newTime;
                    pq.offer(new int[]{next, newTime});
                }
            }
        }
    }
    
    int maxTime = 0;
    for (int i = 1; i <= n; i++) {
        if (dist[i] == Integer.MAX_VALUE) return -1;
        maxTime = Math.max(maxTime, dist[i]);
    }
    return maxTime;
}
```

>  Dijkstra - priority queue se hamesha shortest distance wala node uthao. Uske neighbors update karo agar shorter path milta hai. End mein maximum distance answer hoga.

**Time:** O(E log V)

---

### 5. Word Ladder
**Problem:** beginWord se endWord tak transform karna, har step mein ek character change. Word list di hai. Shortest length?

**Approach:** BFS - har word ke all possible next words generate karo.

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
            String word = queue.poll();
            if (word.equals(endWord)) return level;
            
            char[] chars = word.toCharArray();
            for (int j = 0; j < chars.length; j++) {
                char original = chars[j];
                for (char c = 'a'; c <= 'z'; c++) {
                    if (c == original) continue;
                    chars[j] = c;
                    String newWord = new String(chars);
                    if (wordSet.contains(newWord)) {
                        queue.offer(newWord);
                        wordSet.remove(newWord);
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

>  BFS se shortest path. Har word ke har character ko 'a' se 'z' tak change karke dekho ki new word list mein hai ya nahi. Level wise travel karo.

**Time:** O(n × L² × 26) where L = word length

---

### 6. Cheapest Flights Within K Stops
**Problem:** n cities, flights [from, to, price], src to dst, at most k stops. Cheapest price?

**Approach:** Bellman-Ford variation ya BFS with stops limit.

```java
public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[src] = 0;
    
    // Relax edges k+1 times (k stops = k+1 edges)
    for (int i = 0; i <= k; i++) {
        int[] temp = Arrays.copyOf(dist, n);
        for (int[] flight : flights) {
            int from = flight[0], to = flight[1], price = flight[2];
            if (dist[from] != Integer.MAX_VALUE) {
                temp[to] = Math.min(temp[to], dist[from] + price);
            }
        }
        dist = temp;
    }
    
    return dist[dst] == Integer.MAX_VALUE ? -1 : dist[dst];
}
```

>  Bellman-Ford: k+1 baar relax karo (k stops). Har iteration mein previous distances se update karo. Isse at most k stops wala cheapest path mil jayega.

**Time:** O(k × E)

---

### 7. Pacific Atlantic Water Flow
**Problem:** Matrix heights. Pacific (top/left) aur Atlantic (bottom/right) dono mein pani flow kar sakta hai? Return cells.

**Approach:** Do BFS/DFS - ek Pacific border se, ek Atlantic border se. Dono mein reachable cells answer.

```java
public List<List<Integer>> pacificAtlantic(int[][] heights) {
    List<List<Integer>> result = new ArrayList<>();
    int m = heights.length, n = heights[0].length;
    
    boolean[][] pacific = new boolean[m][n];
    boolean[][] atlantic = new boolean[m][n];
    
    // DFS from borders
    for (int i = 0; i < m; i++) {
        dfs(heights, i, 0, pacific, Integer.MIN_VALUE);
        dfs(heights, i, n-1, atlantic, Integer.MIN_VALUE);
    }
    for (int j = 0; j < n; j++) {
        dfs(heights, 0, j, pacific, Integer.MIN_VALUE);
        dfs(heights, m-1, j, atlantic, Integer.MIN_VALUE);
    }
    
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
    if (i < 0 || i >= heights.length || j < 0 || j >= heights[0].length) return;
    if (visited[i][j] || heights[i][j] < prevHeight) return;
    
    visited[i][j] = true;
    dfs(heights, i+1, j, visited, heights[i][j]);
    dfs(heights, i-1, j, visited, heights[i][j]);
    dfs(heights, i, j+1, visited, heights[i][j]);
    dfs(heights, i, j-1, visited, heights[i][j]);
}
```

>  Pacific borders (top, left) se DFS karo, Atlantic borders (bottom, right) se DFS karo. Jo cells dono DFS mein visit hue, wahi answer.

**Time:** O(m × n)

---

### 8. Clone Graph
**Problem:** Connected graph clone karna hai (deep copy).

**Approach:** BFS/DFS with HashMap for visited nodes.

```java
public Node cloneGraph(Node node) {
    if (node == null) return null;
    
    Map<Node, Node> map = new HashMap<>();
    Queue<Node> queue = new LinkedList<>();
    
    queue.offer(node);
    map.put(node, new Node(node.val));
    
    while (!queue.isEmpty()) {
        Node curr = queue.poll();
        
        for (Node neighbor : curr.neighbors) {
            if (!map.containsKey(neighbor)) {
                map.put(neighbor, new Node(neighbor.val));
                queue.offer(neighbor);
            }
            map.get(curr).neighbors.add(map.get(neighbor));
        }
    }
    return map.get(node);
}
```

>  Original node se copy node ka mapping store karo. BFS se traverse karo aur har neighbor ka copy banao. Har copy node mein neighbors add karo.

**Time:** O(V + E)

---

### 9. Alien Dictionary
**Problem:** Sorted words list di hai (alien language mein). Characters ka order find karo.

**Approach:** Graph banao, topological sort. Adjacent words se edge deduce karo.

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
        String word1 = words[i];
        String word2 = words[i+1];
        int minLen = Math.min(word1.length(), word2.length());
        
        // Check invalid case
        if (word1.length() > word2.length() && word1.startsWith(word2)) {
            return "";
        }
        
        for (int j = 0; j < minLen; j++) {
            char c1 = word1.charAt(j);
            char c2 = word2.charAt(j);
            if (c1 != c2) {
                if (!graph.get(c1).contains(c2)) {
                    graph.get(c1).add(c2);
                    indegree.put(c2, indegree.get(c2) + 1);
                }
                break;
            }
        }
    }
    
    // Topological sort (BFS)
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
    
    return result.length() == indegree.size() ? result.toString() : "";
}
```

>  Har adjacent pair se edge deduce karo jaha pehla different character mile. Phir topological sort karo. Cycle detect karo - agar cycle hai toh invalid.

**Time:** O(C) where C = total characters

---

### 10. Minimum Height Trees
**Problem:** Undirected tree (n nodes). Root karke min height wale trees ke roots find karo.

**Approach:** Leaves ko repeatedly remove karo (topological sort-like).

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

>  Leaves (degree 1 wale nodes) ko repeatedly remove karo. Jab 1 ya 2 nodes bachein, wahi answer hain. Isse center of tree mil jata hai.

**Time:** O(V)

---

### 11. Graph Valid Tree
**Problem:** n nodes and edges. Kya ye valid tree hai? (Connected and no cycle)

**Approach:** Check: edges == n-1 AND graph connected (no cycle using DFS/Union-Find).

```java
public boolean validTree(int n, int[][] edges) {
    if (edges.length != n - 1) return false;
    
    List<Integer>[] graph = new ArrayList[n];
    for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
    
    for (int[] edge : edges) {
        graph[edge[0]].add(edge[1]);
        graph[edge[1]].add(edge[0]);
    }
    
    boolean[] visited = new boolean[n];
    if (hasCycle(graph, visited, 0, -1)) return false;
    
    // Check if all nodes visited
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

>  Tree ke conditions: edges = n-1 AND no cycle AND connected. DFS se cycle detect karo aur visited check karo.

**Time:** O(V + E)

---

### 12. Redundant Connection
**Problem:** Tree mein ek extra edge add kardi. Remove the extra edge that creates cycle.

**Approach:** Union-Find. Pehli edge jaha dono nodes already connected ho, wahi answer.

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
    if (parent[x] != x) {
        parent[x] = find(parent, x);
    }
    return parent[x];
}

private void union(int[] parent, int x, int y) {
    int rootX = find(parent, x);
    int rootY = find(parent, y);
    if (rootX != rootY) {
        parent[rootX] = rootY;
    }
}
```

>  Union-Find se nodes ko connect karte jao. Jab kisi edge pe dono nodes already same set mein ho, toh woh edge cycle bana rahi hai - woh return karo.

**Time:** O(E × α(V))

---

### 13. Critical Connections in a Network (Tarjan's Algorithm)
**Problem:** Bridges in graph. Wo edges jinko remove karne se graph disconnected ho jaye.

**Approach:** Tarjan's algorithm - DFS with discovery time and low link values.

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
            
            if (low[v] > disc[u]) {
                result.add(Arrays.asList(u, v));  // Bridge found
            }
        } else {
            low[u] = Math.min(low[u], disc[v]);
        }
    }
}
```

>  Tarjan's algorithm - har node ke discovery time aur low link value calculate karo. Agar low[v] > disc[u], toh edge u-v bridge hai. Back edge se low update hota hai.

**Time:** O(V + E)

---

### 14. Swim in Rising Water
**Problem:** Grid heights. Time t pe water height t. Path from (0,0) to (n-1,n-1) find karo jisme maximum height minimum ho.

**Approach:** Binary search + BFS/DFS or Dijkstra (minimize max weight).

```java
public int swimInWater(int[][] grid) {
    int n = grid.length;
    int left = grid[0][0], right = n * n - 1;
    
    while (left < right) {
        int mid = left + (right - left) / 2;
        if (canSwim(grid, mid)) {
            right = mid;
        } else {
            left = mid + 1;
        }
    }
    return left;
}

private boolean canSwim(int[][] grid, int time) {
    int n = grid.length;
    if (grid[0][0] > time) return false;
    
    boolean[][] visited = new boolean[n][n];
    visited[0][0] = true;
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    
    Queue<int[]> queue = new LinkedList<>();
    queue.offer(new int[]{0,0});
    
    while (!queue.isEmpty()) {
        int[] curr = queue.poll();
        if (curr[0] == n-1 && curr[1] == n-1) return true;
        
        for (int[] dir : dirs) {
            int x = curr[0] + dir[0];
            int y = curr[1] + dir[1];
            if (x >= 0 && x < n && y >= 0 && y < n && !visited[x][y] && grid[x][y] <= time) {
                visited[x][y] = true;
                queue.offer(new int[]{x, y});
            }
        }
    }
    return false;
}
```

>  Time pe binary search karo. Given time mein BFS/DFS se path exist karta hai ya nahi check karo. Minimum time find karo jisse path possible ho.

**Time:** O(log(n²) × n²)

---

### 15. Reconstruct Itinerary
**Problem:** Flights list [from, to]. Lexicographically smallest itinerary starting from "JFK".

**Approach:** Hierholzer's algorithm for Eulerian path. DFS with priority queue.

```java
public List<String> findItinerary(List<List<String>> tickets) {
    Map<String, PriorityQueue<String>> graph = new HashMap<>();
    
    for (List<String> ticket : tickets) {
        graph.computeIfAbsent(ticket.get(0), k -> new PriorityQueue<>()).add(ticket.get(1));
    }
    
    List<String> result = new LinkedList<>();
    dfs("JFK", graph, result);
    return result;
}

private void dfs(String airport, Map<String, PriorityQueue<String>> graph, List<String> result) {
    PriorityQueue<String> destinations = graph.get(airport);
    while (destinations != null && !destinations.isEmpty()) {
        dfs(destinations.poll(), graph, result);
    }
    result.add(0, airport);  // Add to front (post-order)
}
```

>  Lexicographical order ke liye PriorityQueue use karo. DFS se path find karo. Post-order mein result mein add karo (reverse order).

**Time:** O(E log E)

---

### 16. Minimum Cost to Connect Sticks (Prim's MST)
**Problem:** Sticks ko connect karna hai. Cost = sum of lengths. Minimum total cost?

**Approach:** Always connect two smallest sticks (Huffman coding using min-heap).

```java
public int connectSticks(int[] sticks) {
    PriorityQueue<Integer> pq = new PriorityQueue<>();
    for (int stick : sticks) pq.offer(stick);
    
    int totalCost = 0;
    while (pq.size() > 1) {
        int first = pq.poll();
        int second = pq.poll();
        int cost = first + second;
        totalCost += cost;
        pq.offer(cost);
    }
    return totalCost;
}
```

>  Min-heap se hamesha do smallest sticks uthao, unhe jodo, wapas heap mein daalo. Ye Huffman coding algorithm hai.

**Time:** O(n log n)

---

### 17. Minimize Malware Spread
**Problem:** Graph nodes (computers), some infected. Remove one node to minimize malware spread.

**Approach:** Find connected components. Component mein agar ek hi infected node hai, toh uska size matter karta hai.

```java
public int minMalwareSpread(int[][] graph, int[] initial) {
    int n = graph.length;
    int[] parent = new int[n];
    for (int i = 0; i < n; i++) parent[i] = i;
    
    // Union connected nodes
    for (int i = 0; i < n; i++) {
        for (int j = i+1; j < n; j++) {
            if (graph[i][j] == 1) union(parent, i, j);
        }
    }
    
    int[] size = new int[n];
    for (int i = 0; i < n; i++) {
        size[find(parent, i)]++;
    }
    
    int[] malwareCount = new int[n];
    for (int node : initial) {
        malwareCount[find(parent, node)]++;
    }
    
    Arrays.sort(initial);
    int result = initial[0];
    int maxSaved = 0;
    
    for (int node : initial) {
        int root = find(parent, node);
        if (malwareCount[root] == 1) {  // Only one infected in this component
            if (size[root] > maxSaved) {
                maxSaved = size[root];
                result = node;
            }
        }
    }
    return result;
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

>  Union-Find se components banao. Har component ka size aur infected count nikaalo. Jaha infected count = 1 hai, waha infected node remove karne se poora component save hoga.

**Time:** O(n²)

---

### 18. Shortest Path Visiting All Nodes (BFS with Bitmask)
**Problem:** n nodes graph. All nodes visit karne ka shortest path.

**Approach:** BFS with state (node, visitedMask). 2ⁿ states possible.

```java
public int shortestPathLength(int[][] graph) {
    int n = graph.length;
    int targetMask = (1 << n) - 1;
    
    Queue<int[]> queue = new LinkedList<>();  // [node, mask]
    boolean[][] visited = new boolean[n][1 << n];
    
    for (int i = 0; i < n; i++) {
        queue.offer(new int[]{i, 1 << i});
        visited[i][1 << i] = true;
    }
    
    int steps = 0;
    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            int[] curr = queue.poll();
            int node = curr[0], mask = curr[1];
            
            if (mask == targetMask) return steps;
            
            for (int neighbor : graph[node]) {
                int newMask = mask | (1 << neighbor);
                if (!visited[neighbor][newMask]) {
                    visited[neighbor][newMask] = true;
                    queue.offer(new int[]{neighbor, newMask});
                }
            }
        }
        steps++;
    }
    return -1;
}
```

>  BFS karo jisme state (node, visited bitmask) hai. Har step pe visited nodes ka mask update karo. Jab mask = all bits set, answer mil gaya.

**Time:** O(n × 2ⁿ)

---

### 19. Path With Minimum Effort (Dijkstra Variation)
**Problem:** Grid heights. Path from (0,0) to (m-1,n-1) jisme max adjacent difference minimize karo.

**Approach:** Dijkstra where cost = max effort so far.

```java
public int minimumEffortPath(int[][] heights) {
    int m = heights.length, n = heights[0].length;
    int[][] dist = new int[m][n];
    for (int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE);
    dist[0][0] = 0;
    
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
    pq.offer(new int[]{0, 0, 0});  // {x, y, effort}
    
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    
    while (!pq.isEmpty()) {
        int[] curr = pq.poll();
        int x = curr[0], y = curr[1], effort = curr[2];
        
        if (x == m-1 && y == n-1) return effort;
        
        for (int[] dir : dirs) {
            int nx = x + dir[0], ny = y + dir[1];
            if (nx >= 0 && nx < m && ny >= 0 && ny < n) {
                int newEffort = Math.max(effort, Math.abs(heights[nx][ny] - heights[x][y]));
                if (newEffort < dist[nx][ny]) {
                    dist[nx][ny] = newEffort;
                    pq.offer(new int[]{nx, ny, newEffort});
                }
            }
        }
    }
    return 0;
}
```

>  Dijkstra jisme cost = max difference so far. Priority queue hamesha minimum effort wala path uthata hai. End tak pahunchte hi effort return kar do.

**Time:** O(mn log(mn))

---

### 20. Find Eventual Safe States (Topological Sort Reverse)
**Problem:** Directed graph. Terminal nodes (outdegree 0) aur jo nodes only terminal tak jaate hain, wo safe hain.

**Approach:** Reverse graph + topological sort (Kahn's algorithm).

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

>  Terminal nodes (outdegree 0) se start karo. Reverse graph mein BFS karo. Jo nodes ke outdegree 0 ho jayein, woh safe hain.

**Time:** O(V + E)

---

## 🎯 Hard Graph Interview Tips

1. **Algorithm Selection:**
   - Shortest path (non-negative) → Dijkstra
   - Shortest path (negative) → Bellman-Ford
   - Smallest/largest in grid → Binary Search + BFS/DFS
   - Connectivity/Components → Union-Find or DFS/BFS
   - Cycle detection → DFS (3 states) or Union-Find
   - Topological order → Kahn's algorithm (BFS)
   - Bridges/SCC → Tarjan's algorithm
   - All nodes visit → BFS + Bitmask

2. **Graph Representation:**
   - Adjacency List: Most common, efficient
   - Adjacency Matrix: For dense graphs
   - Edge List: For Bellman-Ford
   - Implicit Graph: Grid, word transformations

3. **Optimization Techniques:**
   - Visited array/state always use karo (cycles rokne ke liye)
   - PriorityQueue for Dijkstra
   - Early termination jahan possible ho
   - Bitmask for small n (n ≤ 20)

4. **Common Mistakes:**
   - Directed vs Undirected bhoolna
   - Graph disconnected case handle na karna
   - Integer overflow (use long where needed)
   - Visited mark karne se pehle check karna

5. **When to use which approach:**
   - **BFS:** Shortest path (unweighted), level order
   - **DFS:** Cycle detection, backtracking, connectivity
   - **Dijkstra:** Weighted shortest path
   - **Union-Find:** Dynamic connectivity, cycles
   - **Topological Sort:** Dependency resolution
   - **Tarjan's:** Bridges, SCCs
