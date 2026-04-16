
MST problems use either **Kruskal's Algorithm** (Union-Find / DSU) or **Prim's Algorithm** (Priority Queue / Min-Heap).


## 🎯 MST Core Concepts

**Minimum Spanning Tree = Graph ke saare nodes ko connect karne ka minimum total weight wala tree (V-1 edges, no cycles)**

### Two Main Algorithms:

| Algorithm | Approach | Data Structure | Best For | Complexity |
|-----------|----------|----------------|----------|------------|
| **Kruskal** | Sorted edges + Union-Find | DSU (Union-Find) | Sparse graphs, edge list available | O(E log E) |
| **Prim** | Grow from source + Min-Heap | Priority Queue | Dense graphs, adjacency matrix | O(E log V) |

**Kruskal Template:**
```java
// 1. Sort edges by weight
Collections.sort(edges, (a,b) -> a[2] - b[2]);
// 2. Union-Find to add edges without cycle
for (int[] edge : edges) {
    if (find(u) != find(v)) {
        union(u, v);
        totalWeight += w;
    }
}
```

**Prim Template:**
```java
// Priority queue on (weight, node)
PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[0] - b[0]);
boolean[] visited = new boolean[n];
pq.offer(new int[]{0, 0});  // start from node 0

while (!pq.isEmpty()) {
    int[] curr = pq.poll();
    if (visited[curr[1]]) continue;
    visited[curr[1]] = true;
    totalWeight += curr[0];
    for (int[] neighbor : graph[curr[1]]) {
        if (!visited[neighbor[0]]) pq.offer(new int[]{neighbor[1], neighbor[0]});
    }
}
```

---

## 📋 20 Hard MST Problems

---

### 1. Minimum Cost to Connect All Nodes (Kruskal)
**Problem:** N nodes, edges [u, v, w]. Sab nodes ko connect karne ka minimum cost.

**Algorithm:** Kruskal (Edge list sorted + Union-Find)

```java
public int minCostToConnect(int n, int[][] edges) {
    // Sort edges by weight
    Arrays.sort(edges, (a, b) -> a[2] - b[2]);
    
    int[] parent = new int[n];
    for (int i = 0; i < n; i++) parent[i] = i;
    
    int totalCost = 0;
    int edgesUsed = 0;
    
    for (int[] edge : edges) {
        int u = edge[0], v = edge[1], w = edge[2];
        if (find(parent, u) != find(parent, v)) {
            union(parent, u, v);
            totalCost += w;
            edgesUsed++;
            if (edgesUsed == n - 1) break;
        }
    }
    
    return edgesUsed == n - 1 ? totalCost : -1;
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

>  Sabse chhoti weight wali edge se start karo. Edge add karo agar cycle nahi bana rahi (Union-Find se check karo). Jab tak V-1 edges add na ho jayein tab tak karo.

**Time:** O(E log E)

---

### 2. Minimum Cost to Connect All Nodes (Prim)
**Problem:** Same as above but using Prim's algorithm.

**Algorithm:** Prim (Priority Queue + Visited array)

```java
public int minCostToConnectPrim(int n, int[][] edges) {
    // Build adjacency list
    List<int[]>[] graph = new ArrayList[n];
    for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
    
    for (int[] edge : edges) {
        int u = edge[0], v = edge[1], w = edge[2];
        graph[u].add(new int[]{v, w});
        graph[v].add(new int[]{u, w});
    }
    
    boolean[] visited = new boolean[n];
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
    pq.offer(new int[]{0, 0});  // {node, weight}
    
    int totalCost = 0;
    int nodesVisited = 0;
    
    while (!pq.isEmpty() && nodesVisited < n) {
        int[] curr = pq.poll();
        int node = curr[0], weight = curr[1];
        
        if (visited[node]) continue;
        
        visited[node] = true;
        totalCost += weight;
        nodesVisited++;
        
        for (int[] neighbor : graph[node]) {
            if (!visited[neighbor[0]]) {
                pq.offer(new int[]{neighbor[0], neighbor[1]});
            }
        }
    }
    
    return nodesVisited == n ? totalCost : -1;
}
```

>  Kisi bhi node se start karo (node 0). Priority queue mein uske neighbors daalo. Hamesha smallest weight wala node uthao aur visited mark karo. Jab tak saare nodes visited na ho jayein tab tak karo.

**Time:** O(E log V)

---

### 3. Connecting Cities With Minimum Cost (LeetCode 1135)
**Problem:** Cities ko connect karne ka minimum cost. Connections array di hai.

**Algorithm:** Kruskal

```java
public int minimumCost(int n, int[][] connections) {
    Arrays.sort(connections, (a, b) -> a[2] - b[2]);
    
    int[] parent = new int[n + 1];
    for (int i = 1; i <= n; i++) parent[i] = i;
    
    int totalCost = 0;
    int edgesUsed = 0;
    
    for (int[] conn : connections) {
        int u = conn[0], v = conn[1], cost = conn[2];
        if (find(parent, u) != find(parent, v)) {
            union(parent, u, v);
            totalCost += cost;
            edgesUsed++;
        }
    }
    
    return edgesUsed == n - 1 ? totalCost : -1;
}
```

>  Classic MST problem. Kruskal use karo - edges sort karo, Union-Find se cycle check karo.

**Time:** O(E log E)

---

### 4. Minimum Cost to Connect Sticks (LeetCode 1167)
**Problem:** Sticks ko connect karna hai. Cost = sum of lengths. Minimum total cost.

**Algorithm:** Prim-like (Greedy using Min-Heap) - Actually Huffman Coding

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

>  Yeh Huffman coding problem hai, MST jaisa hi concept hai. Hamesha do smallest sticks uthao, unhe jodo, wapas heap mein daalo. Jab tak ek stick na bache tab tak karo.

**Time:** O(n log n)

---

### 5. Min Cost to Connect All Points (LeetCode 1584)
**Problem:** Points [x,y] diye hain. Sab points ko connect karne ka minimum cost. Distance = |x1-x2| + |y1-y2|.

**Algorithm:** Prim (Better for dense graphs) ya Kruskal

```java
public int minCostConnectPoints(int[][] points) {
    int n = points.length;
    boolean[] visited = new boolean[n];
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
    pq.offer(new int[]{0, 0});  // {point index, cost}
    
    int totalCost = 0;
    int nodesVisited = 0;
    
    while (!pq.isEmpty() && nodesVisited < n) {
        int[] curr = pq.poll();
        int u = curr[0], cost = curr[1];
        
        if (visited[u]) continue;
        
        visited[u] = true;
        totalCost += cost;
        nodesVisited++;
        
        for (int v = 0; v < n; v++) {
            if (!visited[v]) {
                int dist = Math.abs(points[u][0] - points[v][0]) + 
                           Math.abs(points[u][1] - points[v][1]);
                pq.offer(new int[]{v, dist});
            }
        }
    }
    return totalCost;
}
```

>  Prim's algorithm use karo because complete graph hai (har point har point se connected). Priority queue se smallest distance wala point uthao.

**Time:** O(n² log n) with Prim, O(n² log n) with Kruskal (n² edges)

---

### 6. Minimum Spanning Tree Verification (Check if given edges form MST)
**Problem:** Diye gaye edges se MST banta hai ya nahi? Unique MST hai ya nahi?

**Algorithm:** Kruskal + Check

```java
public boolean isMST(int n, int[][] edges, int[][] mstEdges) {
    // Sort both by weight
    Arrays.sort(edges, (a, b) -> a[2] - b[2]);
    Arrays.sort(mstEdges, (a, b) -> a[2] - b[2]);
    
    int[] parent = new int[n];
    for (int i = 0; i < n; i++) parent[i] = i;
    
    int totalWeight = 0;
    int edgesUsed = 0;
    int mstIndex = 0;
    
    for (int[] edge : edges) {
        if (mstIndex < mstEdges.length && edge[2] == mstEdges[mstIndex][2] &&
            sameEdge(edge, mstEdges[mstIndex])) {
            // This edge is in candidate MST
            if (find(parent, edge[0]) != find(parent, edge[1])) {
                union(parent, edge[0], edge[1]);
                totalWeight += edge[2];
                edgesUsed++;
            }
            mstIndex++;
        } else {
            // Regular Kruskal
            if (find(parent, edge[0]) != find(parent, edge[1])) {
                union(parent, edge[0], edge[1]);
                totalWeight += edge[2];
                edgesUsed++;
            }
        }
    }
    
    return edgesUsed == n - 1 && mstIndex == mstEdges.length;
}
```

>  Kruskal algorithm run karo. Jo edges candidate MST mein hain, unhe priority do. Agar same weight wali edges hain, check karo ki candidate MST valid hai ya nahi.

**Time:** O(E log E)

---

### 7. Second Best MST (Kruskal + Brute Force)
**Problem:** Second minimum spanning tree find karo.

**Algorithm:** Kruskal se first MST find karo, then har edge remove karke try karo.

```java
public int secondBestMST(int n, int[][] edges) {
    Arrays.sort(edges, (a, b) -> a[2] - b[2]);
    
    // Find first MST and store edges used
    int[] parent = new int[n];
    for (int i = 0; i < n; i++) parent[i] = i;
    
    List<int[]> mstEdges = new ArrayList<>();
    int firstMSTWeight = 0;
    int edgesUsed = 0;
    
    for (int[] edge : edges) {
        if (find(parent, edge[0]) != find(parent, edge[1])) {
            union(parent, edge[0], edge[1]);
            firstMSTWeight += edge[2];
            mstEdges.add(edge);
            edgesUsed++;
            if (edgesUsed == n - 1) break;
        }
    }
    
    if (edgesUsed != n - 1) return -1;
    
    // Try removing each MST edge and find next best
    int secondBest = Integer.MAX_VALUE;
    
    for (int[] skipEdge : mstEdges) {
        // Reset parent
        for (int i = 0; i < n; i++) parent[i] = i;
        
        int weight = 0;
        int count = 0;
        
        for (int[] edge : edges) {
            if (edge == skipEdge) continue;
            if (find(parent, edge[0]) != find(parent, edge[1])) {
                union(parent, edge[0], edge[1]);
                weight += edge[2];
                count++;
                if (count == n - 1) break;
            }
        }
        
        if (count == n - 1 && weight > firstMSTWeight) {
            secondBest = Math.min(secondBest, weight);
        }
    }
    
    return secondBest == Integer.MAX_VALUE ? -1 : secondBest;
}
```

>  Pehle first MST find karo (Kruskal se). Phir first MST ki har edge ko remove karke dubara MST find karo. Jo sabse chhota weight ho (lekin first se bada) woh second best MST hai.

**Time:** O(E log E + V × E)

---

### 8. Maximum Spanning Tree
**Problem:** Maximum weight wala spanning tree.

**Algorithm:** Kruskal with edges sorted in descending order.

```java
public int maxSpanningTree(int n, int[][] edges) {
    // Sort in descending order
    Arrays.sort(edges, (a, b) -> b[2] - a[2]);
    
    int[] parent = new int[n];
    for (int i = 0; i < n; i++) parent[i] = i;
    
    int totalWeight = 0;
    int edgesUsed = 0;
    
    for (int[] edge : edges) {
        int u = edge[0], v = edge[1], w = edge[2];
        if (find(parent, u) != find(parent, v)) {
            union(parent, u, v);
            totalWeight += w;
            edgesUsed++;
            if (edgesUsed == n - 1) break;
        }
    }
    
    return edgesUsed == n - 1 ? totalWeight : -1;
}
```

>  Maximum spanning tree ke liye edges ko descending order mein sort karo (sabse bada weight pehle). Phir Kruskal laga do.

**Time:** O(E log E)

---

### 9. Minimum Cost to Separate Components (Reverse MST)
**Problem:** Graph ko k components mein todna hai. Minimum cost to remove edges.

**Algorithm:** Kruskal in reverse (add edges to connect, not disconnect)

```java
public int minCostToSeparate(int n, int[][] edges, int k) {
    // We want exactly k components
    // MST gives 1 component with minimum cost
    // To get k components, remove k-1 edges from MST
    // But removal cost is sum of removed edges
    
    Arrays.sort(edges, (a, b) -> a[2] - b[2]);
    
    int[] parent = new int[n];
    for (int i = 0; i < n; i++) parent[i] = i;
    
    int components = n;
    int totalCost = 0;
    
    for (int[] edge : edges) {
        if (find(parent, edge[0]) != find(parent, edge[1])) {
            union(parent, edge[0], edge[1]);
            components--;
            totalCost += edge[2];
            if (components == k) {
                // We have exactly k components now
                // The remaining edges are not needed
                return totalCost;
            }
        }
    }
    
    return -1;
}
```

>  MST mein V-1 edges hoti hain. Exactly k components chahiye toh k-1 edges remove karni hongi. Isliye Kruskal tab tak karo jab tak components == k na ho jaye.

**Time:** O(E log E)

---

### 10. MST After Adding/Removing Edges (Dynamic MST)
**Problem:** Original MST hai. Ek naya edge add kiya. New MST find karo.

**Algorithm:** Add edge, find cycle, remove max weight edge in cycle

```java
public int addEdgeToMST(int n, int[][] mstEdges, int[] newEdge) {
    // mstEdges is the original MST (n-1 edges)
    // Add new edge and find cycle
    List<int[]> edges = new ArrayList<>();
    for (int[] e : mstEdges) edges.add(e);
    edges.add(newEdge);
    
    // Build graph
    List<int[]>[] graph = new ArrayList[n];
    for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
    
    for (int[] e : edges) {
        graph[e[0]].add(new int[]{e[1], e[2]});
        graph[e[1]].add(new int[]{e[0], e[2]});
    }
    
    // Find cycle and maximum weight edge in cycle
    // Using DFS from newEdge's endpoints
    int[] parent = new int[n];
    int[] parentWeight = new int[n];
    Arrays.fill(parent, -1);
    
    // DFS to find path between newEdge[0] and newEdge[1]
    Stack<Integer> stack = new Stack<>();
    stack.push(newEdge[0]);
    parent[newEdge[0]] = newEdge[0];
    
    while (!stack.isEmpty()) {
        int u = stack.pop();
        if (u == newEdge[1]) break;
        
        for (int[] neighbor : graph[u]) {
            int v = neighbor[0], w = neighbor[1];
            if (parent[v] == -1) {
                parent[v] = u;
                parentWeight[v] = w;
                stack.push(v);
            }
        }
    }
    
    // Find max weight in cycle
    int maxWeight = newEdge[2];
    int maxEdgeU = newEdge[0], maxEdgeV = newEdge[1];
    int curr = newEdge[1];
    
    while (curr != newEdge[0]) {
        if (parentWeight[curr] > maxWeight) {
            maxWeight = parentWeight[curr];
            maxEdgeU = parent[curr];
            maxEdgeV = curr;
        }
        curr = parent[curr];
    }
    
    // New MST weight = old MST weight + newEdge weight - maxWeight
    int oldWeight = 0;
    for (int[] e : mstEdges) oldWeight += e[2];
    
    return oldWeight + newEdge[2] - maxWeight;
}
```

>  New edge add karne se cycle ban jayegi. Cycle mein maximum weight wali edge remove karo. New MST weight = old MST weight + new edge weight - max weight in cycle.

**Time:** O(V + E)

---

### 11. Minimum Product Spanning Tree
**Problem:** Spanning tree jisme edges ke product ko minimize karna hai.

**Algorithm:** Convert to sum using logarithms, then Kruskal.

```java
public double minProductSpanningTree(int n, int[][] edges) {
    // Product minimization = sum of logs minimization
    double[][] logEdges = new double[edges.length][3];
    for (int i = 0; i < edges.length; i++) {
        logEdges[i][0] = edges[i][0];
        logEdges[i][1] = edges[i][1];
        logEdges[i][2] = Math.log(edges[i][2]);
    }
    
    Arrays.sort(logEdges, (a, b) -> Double.compare(a[2], b[2]));
    
    int[] parent = new int[n];
    for (int i = 0; i < n; i++) parent[i] = i;
    
    double product = 1.0;
    int edgesUsed = 0;
    
    for (double[] edge : logEdges) {
        int u = (int) edge[0], v = (int) edge[1];
        if (find(parent, u) != find(parent, v)) {
            union(parent, u, v);
            product *= Math.exp(edge[2]);  // Convert back
            edgesUsed++;
            if (edgesUsed == n - 1) break;
        }
    }
    
    return edgesUsed == n - 1 ? product : -1;
}
```

>  Product minimize karna hai toh log le lo. Log(weight) ka sum minimize karo. Phir normal Kruskal laga do.

**Time:** O(E log E)

---

### 12. Minimum Bottleneck Spanning Tree
**Problem:** Spanning tree jisme maximum edge weight minimize karo.

**Algorithm:** Kruskal automatically gives minimum bottleneck (MST property).

```java
public int minBottleneckSpanningTree(int n, int[][] edges) {
    Arrays.sort(edges, (a, b) -> a[2] - b[2]);
    
    int[] parent = new int[n];
    for (int i = 0; i < n; i++) parent[i] = i;
    
    int maxWeight = 0;
    int edgesUsed = 0;
    
    for (int[] edge : edges) {
        int u = edge[0], v = edge[1], w = edge[2];
        if (find(parent, u) != find(parent, v)) {
            union(parent, u, v);
            maxWeight = Math.max(maxWeight, w);
            edgesUsed++;
            if (edgesUsed == n - 1) break;
        }
    }
    
    return edgesUsed == n - 1 ? maxWeight : -1;
}
```

>  Kruskal algorithm automatically minimum bottleneck spanning tree deta hai. Kyunki ye smallest edges ko pehle add karta hai, maximum edge weight automatically minimized ho jati hai.

**Time:** O(E log E)

---

### 13. Minimum Cost to Make Graph Connected (Adding Edges)
**Problem:** Disconnected graph hai. Minimum cost to make it connected.

**Algorithm:** Kruskal to find components, then cheapest edges between components.

```java
public int minCostToConnectGraph(int n, int[][] edges, int[][] newEdges) {
    // First connect using existing edges
    int[] parent = new int[n];
    for (int i = 0; i < n; i++) parent[i] = i;
    
    for (int[] edge : edges) {
        if (find(parent, edge[0]) != find(parent, edge[1])) {
            union(parent, edge[0], edge[1]);
        }
    }
    
    // Find components
    Map<Integer, List<Integer>> components = new HashMap<>();
    for (int i = 0; i < n; i++) {
        int root = find(parent, i);
        components.computeIfAbsent(root, k -> new ArrayList<>()).add(i);
    }
    
    int numComponents = components.size();
    if (numComponents == 1) return 0;
    
    // Sort new edges and use Kruskal to connect components
    Arrays.sort(newEdges, (a, b) -> a[2] - b[2]);
    
    int totalCost = 0;
    int connectionsMade = 0;
    
    for (int[] edge : newEdges) {
        int u = edge[0], v = edge[1], cost = edge[2];
        int rootU = find(parent, u);
        int rootV = find(parent, v);
        
        if (rootU != rootV) {
            union(parent, rootU, rootV);
            totalCost += cost;
            connectionsMade++;
            if (connectionsMade == numComponents - 1) break;
        }
    }
    
    return connectionsMade == numComponents - 1 ? totalCost : -1;
}
```

>  Pehle existing edges se components banao. Phir new edges ko sort karo aur components ko connect karo (jaise MST mein components connect karte hain).

**Time:** O((E + E_new) log E_new)

---

### 14. Minimum Cost to Connect Two Nodes with Minimum Spanning Tree
**Problem:** Node A aur B ko connect karna hai using MST properties.

**Algorithm:** Kruskal run karo, jab A aur B connected ho jayein, wahi answer.

```java
public int minCostToConnectTwoNodes(int n, int[][] edges, int a, int b) {
    Arrays.sort(edges, (x, y) -> x[2] - y[2]);
    
    int[] parent = new int[n];
    for (int i = 0; i < n; i++) parent[i] = i;
    
    for (int[] edge : edges) {
        int u = edge[0], v = edge[1], w = edge[2];
        if (find(parent, u) != find(parent, v)) {
            union(parent, u, v);
            if (find(parent, a) == find(parent, b)) {
                return w;  // This is the maximum weight in path
            }
        }
    }
    return -1;
}
```

>  Kruskal se edges add karte jao. Jab pehli baar A aur B connected ho jayein, jo edge add ki wahi answer hai. Ye minimum bottleneck path ka weight hota hai.

**Time:** O(E log E)

---

### 15. Number of Minimum Spanning Trees
**Problem:** Graph mein kitne distinct MSTs hain?

**Algorithm:** Kruskal + Counting principle for equal weight edges.

```java
public int countMSTs(int n, int[][] edges) {
    Arrays.sort(edges, (a, b) -> a[2] - b[2]);
    
    int[] parent = new int[n];
    for (int i = 0; i < n; i++) parent[i] = i;
    
    int totalWays = 1;
    int i = 0;
    
    while (i < edges.length) {
        int j = i;
        int weight = edges[i][2];
        
        // Get all edges with same weight
        List<int[]> sameWeightEdges = new ArrayList<>();
        while (j < edges.length && edges[j][2] == weight) {
            sameWeightEdges.add(edges[j]);
            j++;
        }
        
        // Count how many of these edges can be added without cycle
        // This is a matching problem in the component graph
        List<int[]> validEdges = new ArrayList<>();
        for (int[] edge : sameWeightEdges) {
            if (find(parent, edge[0]) != find(parent, edge[1])) {
                validEdges.add(edge);
            }
        }
        
        // Count number of ways to choose edges from validEdges
        int ways = countWaysToChooseEdges(parent, validEdges, n);
        totalWays = (totalWays * ways) % MOD;
        
        // Actually add the edges (any valid set works)
        for (int[] edge : sameWeightEdges) {
            if (find(parent, edge[0]) != find(parent, edge[1])) {
                union(parent, edge[0], edge[1]);
            }
        }
        
        i = j;
    }
    
    return totalWays;
}
```

>  Same weight wali edges ke liye, kitne combinations possible hain jo cycle nahi banate, woh count karo. Saare weight groups ke counts multiply karo.

**Time:** O(E log E + V² × 2^(max edges per weight))

---

### 16. MST on Complete Graph with Manhattan Distance
**Problem:** Points [x,y] diye hain. Har pair ke beech distance = |x1-x2| + |y1-y2|. MST find karo.

**Algorithm:** Prim (n² edges, so Prim is better)

```java
public int manhattanMST(int[][] points) {
    int n = points.length;
    boolean[] visited = new boolean[n];
    int[] minDist = new int[n];
    Arrays.fill(minDist, Integer.MAX_VALUE);
    minDist[0] = 0;
    
    int totalCost = 0;
    
    for (int i = 0; i < n; i++) {
        // Find unvisited node with minimum distance
        int u = -1;
        for (int j = 0; j < n; j++) {
            if (!visited[j] && (u == -1 || minDist[j] < minDist[u])) {
                u = j;
            }
        }
        
        visited[u] = true;
        totalCost += minDist[u];
        
        // Update distances
        for (int v = 0; v < n; v++) {
            if (!visited[v]) {
                int dist = Math.abs(points[u][0] - points[v][0]) + 
                           Math.abs(points[u][1] - points[v][1]);
                if (dist < minDist[v]) {
                    minDist[v] = dist;
                }
            }
        }
    }
    
    return totalCost;
}
```

>  Complete graph mein Prim's algorithm O(n²) mein kaam karta hai. Har step pe ek node uthao aur uske saare neighbors ke distances update karo.

**Time:** O(n²)

---

### 17. Minimum Cost to Connect Cities with Tolls (Some cities have toll)
**Problem:** Cities connect karni hain. Kuch cities mein toll lagta hai. Minimum total cost.

**Algorithm:** Prim with toll included in node weight

```java
public int minCostWithTolls(int n, int[][] roads, int[] tolls) {
    List<int[]>[] graph = new ArrayList[n];
    for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
    
    for (int[] road : roads) {
        int u = road[0], v = road[1], w = road[2];
        graph[u].add(new int[]{v, w});
        graph[v].add(new int[]{u, w});
    }
    
    boolean[] visited = new boolean[n];
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
    pq.offer(new int[]{0, tolls[0]});  // Start with toll of first city
    
    int totalCost = 0;
    int nodesVisited = 0;
    
    while (!pq.isEmpty() && nodesVisited < n) {
        int[] curr = pq.poll();
        int u = curr[0], cost = curr[1];
        
        if (visited[u]) continue;
        
        visited[u] = true;
        totalCost += cost;
        nodesVisited++;
        
        for (int[] neighbor : graph[u]) {
            int v = neighbor[0], roadCost = neighbor[1];
            if (!visited[v]) {
                pq.offer(new int[]{v, roadCost + tolls[v]});
            }
        }
    }
    
    return nodesVisited == n ? totalCost : -1;
}
```

>  Toll ko edge weight mein add karo. Phir Prim's algorithm laga do. Har node pe pahunchne par toll add hota hai.

**Time:** O(E log V)

---

### 18. Minimum Cost to Connect All Nodes with Limited Connections
**Problem:** Har node max degree constraint hai (k connections se zyada nahi).

**Algorithm:** Modified Prim with degree tracking

```java
public int minCostWithDegreeConstraint(int n, int[][] edges, int maxDegree) {
    List<int[]>[] graph = new ArrayList[n];
    for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
    
    for (int[] edge : edges) {
        int u = edge[0], v = edge[1], w = edge[2];
        graph[u].add(new int[]{v, w});
        graph[v].add(new int[]{u, w});
    }
    
    int[] degree = new int[n];
    boolean[] visited = new boolean[n];
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
    pq.offer(new int[]{0, -1, 0});  // {node, parent, weight}
    
    int totalCost = 0;
    int nodesVisited = 0;
    
    while (!pq.isEmpty() && nodesVisited < n) {
        int[] curr = pq.poll();
        int u = curr[0], parent = curr[1], w = curr[2];
        
        if (visited[u]) continue;
        if (parent != -1 && degree[parent] >= maxDegree) continue;
        
        visited[u] = true;
        if (parent != -1) {
            degree[parent]++;
            degree[u]++;
            totalCost += w;
        }
        nodesVisited++;
        
        for (int[] neighbor : graph[u]) {
            int v = neighbor[0], weight = neighbor[1];
            if (!visited[v] && degree[u] < maxDegree) {
                pq.offer(new int[]{v, u, weight});
            }
        }
    }
    
    return nodesVisited == n ? totalCost : -1;
}
```

>  Prim's algorithm mein degree check karte jao. Agar kisi node ki degree maxDegree cross ho jaye toh us node se aur edges add mat karo.

**Time:** O(E log V)

---

### 19. Minimum Cost to Connect Components with Special Nodes
**Problem:** Kuch nodes "special" hain. Inhe connect karna compulsory hai. Minimum cost.

**Algorithm:** Kruskal + Force include special edges

```java
public int minCostConnectSpecialNodes(int n, int[][] edges, List<Integer> specialNodes) {
    Arrays.sort(edges, (a, b) -> a[2] - b[2]);
    
    int[] parent = new int[n];
    for (int i = 0; i < n; i++) parent[i] = i;
    
    // First, connect all special nodes using special edges only
    // Actually, we need to ensure they end up in same component
    int totalCost = 0;
    
    for (int[] edge : edges) {
        int u = edge[0], v = edge[1], w = edge[2];
        if (find(parent, u) != find(parent, v)) {
            union(parent, u, v);
            totalCost += w;
        }
        
        // Check if all special nodes are connected
        Set<Integer> roots = new HashSet<>();
        for (int special : specialNodes) {
            roots.add(find(parent, special));
        }
        if (roots.size() == 1) break;
    }
    
    // Now add remaining nodes using regular MST
    for (int[] edge : edges) {
        int u = edge[0], v = edge[1], w = edge[2];
        if (find(parent, u) != find(parent, v)) {
            union(parent, u, v);
            totalCost += w;
        }
    }
    
    // Check if all nodes connected
    int root = find(parent, 0);
    for (int i = 1; i < n; i++) {
        if (find(parent, i) != root) return -1;
    }
    
    return totalCost;
}
```

>  Pehle special nodes ko connect karo (jaise bhi ho). Phir baaki nodes ko normal MST ki tarah connect karo.

**Time:** O(E log E)

---

### 20. Minimum Cost to Build Bridges (MST with Constraints)
**Problem:** Islands (nodes) ko bridges se connect karna hai. Kuch bridges mandatory hain. Minimum cost.

**Algorithm:** Kruskal with mandatory edges first

```java
public int minCostToBuildBridges(int n, int[][] bridges, int[][] mandatoryBridges) {
    // First add mandatory bridges
    int[] parent = new int[n];
    for (int i = 0; i < n; i++) parent[i] = i;
    
    int totalCost = 0;
    int edgesUsed = 0;
    
    // Add mandatory bridges first
    for (int[] bridge : mandatoryBridges) {
        int u = bridge[0], v = bridge[1], w = bridge[2];
        if (find(parent, u) != find(parent, v)) {
            union(parent, u, v);
            totalCost += w;
            edgesUsed++;
        } else {
            return -1;  // Mandatory bridge creates cycle - impossible
        }
    }
    
    // Sort remaining bridges
    List<int[]> remainingBridges = new ArrayList<>();
    for (int[] bridge : bridges) {
        remainingBridges.add(bridge);
    }
    remainingBridges.sort((a, b) -> a[2] - b[2]);
    
    // Add remaining bridges using Kruskal
    for (int[] bridge : remainingBridges) {
        int u = bridge[0], v = bridge[1], w = bridge[2];
        if (find(parent, u) != find(parent, v)) {
            union(parent, u, v);
            totalCost += w;
            edgesUsed++;
            if (edgesUsed == n - 1) break;
        }
    }
    
    return edgesUsed == n - 1 ? totalCost : -1;
}
```

>  Pehle mandatory bridges ko add karo (check cycle nahi hona chahiye). Phir baaki bridges ko normal Kruskal ki tarah add karo.

**Time:** O(E log E)

---

## 📊 Complete Summary Table

| # | Problem | Algorithm | Key Insight | Complexity |
|---|---------|-----------|-------------|------------|
| 1 | Min Cost to Connect All Nodes | Kruskal | Union-Find + sorted edges | O(E log E) |
| 2 | Min Cost to Connect All Nodes | Prim | Priority Queue + visited | O(E log V) |
| 3 | Connecting Cities | Kruskal | Classic MST | O(E log E) |
| 4 | Connect Sticks | Prim-like (Huffman) | Always merge smallest | O(n log n) |
| 5 | Min Cost Connect Points | Prim | Manhattan distance | O(n² log n) |
| 6 | MST Verification | Kruskal | Check edge by edge | O(E log E) |
| 7 | Second Best MST | Kruskal + Brute | Remove each MST edge | O(E log E + V×E) |
| 8 | Maximum Spanning Tree | Kruskal | Sort descending | O(E log E) |
| 9 | Separate Components | Kruskal | Stop at k components | O(E log E) |
| 10 | Dynamic MST | Cycle + Max edge | Add edge, remove max | O(V+E) |
| 11 | Min Product MST | Kruskal + Log | Convert product to sum | O(E log E) |
| 12 | Min Bottleneck MST | Kruskal | MST property | O(E log E) |
| 13 | Connect Graph | Kruskal | Connect components | O((E+E_new) log E_new) |
| 14 | Connect Two Nodes | Kruskal | Stop when connected | O(E log E) |
| 15 | Count MSTs | Kruskal + Counting | Count equal weight edges | O(E log E + V²×2^k) |
| 16 | Manhattan MST | Prim | Complete graph Prim | O(n²) |
| 17 | MST with Tolls | Prim | Add toll to edge cost | O(E log V) |
| 18 | Degree Constraint MST | Modified Prim | Track degrees | O(E log V) |
| 19 | Connect Special Nodes | Kruskal | Priority to special | O(E log E) |
| 20 | MST with Mandatory Edges | Kruskal | Add mandatory first | O(E log E) |

---

## 🎯 MST Interview Tips

### Kruskal vs Prim - Kab Kya Use Karein?

| Scenario | Recommended | Reason |
|----------|-------------|--------|
| Sparse graph (E ≈ V) | Kruskal | Edge sorting efficient |
| Dense graph (E ≈ V²) | Prim | O(V²) vs O(E log E) |
| Edge list already given | Kruskal | Directly sort edges |
| Adjacency matrix given | Prim | Easy to implement |
| Need to process edges in order | Kruskal | Natural edge-based |
| Graph is complete | Prim | Better complexity |

### Algorithm Templates Yaad Rakho:

**Kruskal:**
```java
sort edges by weight
DSU parent array
for each edge (u, v, w):
    if find(u) != find(v):
        union(u, v)
        total += w
```

**Prim:**
```java
visited array, min-heap
start from node 0
while heap not empty:
    (w, u) = heap.poll()
    if visited[u] continue
    visited[u] = true
    total += w
    for each neighbor (v, weight):
        if not visited[v]:
            heap.offer(weight, v)
```

### Common Mistakes:
1. **Kruskal mein Union-Find path compression bhoolna** → TLE
2. **Prim mein visited check bhoolna** → Infinite loop
3. **Graph disconnected handle na karna** → Return -1
4. **Integer overflow** → Use long for large sums
5. **Undirected graph mein edge dono direction add karna bhoolna**

### MST Properties Yaad Rakho:
1. **Cut Property:** Kisi bhi cut mein sabse chhoti edge MST mein hogi
2. **Cycle Property:** Kisi bhi cycle mein sabse badi edge MST mein nahi hogi
3. **V-1 edges** hamesha
4. **Unique weights** → Unique MST
5. **MST is minimum bottleneck spanning tree** (max edge minimized)

### Real World Applications:
- **Network design** (cable, fiber, roads)
- **Clustering algorithms**
- **Approximation for TSP**
- **Image segmentation**
- **Circuit design**
