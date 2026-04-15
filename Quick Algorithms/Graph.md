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
