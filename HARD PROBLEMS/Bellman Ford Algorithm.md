
##  Bellman-Ford Algorithm Core Concepts 

**Bellman-Ford Kaise Kaam Karta Hai:**

```java
// Bellman-Ford Template
int[] dist = new int[n];
Arrays.fill(dist, Integer.MAX_VALUE);
dist[src] = 0;

// Relax edges V-1 times
for (int i = 0; i < n - 1; i++) {
    for (int[] edge : edges) {
        int u = edge[0], v = edge[1], w = edge[2];
        if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
            dist[v] = dist[u] + w;
        }
    }
}

// Check for negative cycle (Vth iteration)
for (int[] edge : edges) {
    int u = edge[0], v = edge[1], w = edge[2];
    if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
        // Negative cycle exists
    }
}
```

**Key Points:**
- **Negative edges allowed** (unlike Dijkstra)
- **Negative cycle detection** possible
- Slower than Dijkstra: **O(V×E) vs O(E log V)**
- Use when graph has **negative weights** or you need **negative cycle detection**

---

## 📋 20 Hard Bellman-Ford Based Problems

---

### 1. Cheapest Flights Within K Stops (Bellman-Ford Version)
**Problem:** src se dst tak at most k stops mein cheapest price. Negative prices nahi hain but Bellman-Ford se bhi ho sakta hai.

**Approach:** Bellman-Ford with stop limit. Har iteration ek stop ke corresponding relax karo.

```java
public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[src] = 0;
    
    // Relax edges k+1 times (k stops = k+1 edges)
    for (int i = 0; i <= k; i++) {
        int[] temp = Arrays.copyOf(dist, n);
        for (int[] flight : flights) {
            int u = flight[0], v = flight[1], w = flight[2];
            if (dist[u] != Integer.MAX_VALUE && dist[u] + w < temp[v]) {
                temp[v] = dist[u] + w;
            }
        }
        dist = temp;
    }
    
    return dist[dst] == Integer.MAX_VALUE ? -1 : dist[dst];
}
```

>  Bellman-Ford ka modified version. Har iteration ek extra stop ke corresponding edges relax karo. K+1 iterations ke baad jo distance milega woh at most k stops wala cheapest price hoga.

**Time:** O(k × E)

---

### 2. Network Delay Time (Bellman-Ford Version)
**Problem:** N nodes, edges [u,v,w], source se sabhi nodes tak signal pahunchne ka minimum time.

**Approach:** Standard Bellman-Ford - V-1 iterations.

```java
public int networkDelayTime(int[][] times, int n, int k) {
    int[] dist = new int[n + 1];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[k] = 0;
    
    // Relax V-1 times
    for (int i = 1; i < n; i++) {
        for (int[] time : times) {
            int u = time[0], v = time[1], w = time[2];
            if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
                dist[v] = dist[u] + w;
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

>  Dijkstra ki jagah Bellman-Ford use karo. V-1 baar saare edges relax karo. End mein max distance return karo.

**Time:** O(V × E)

---

### 3. Negative Cycle Detection (Standard Problem)
**Problem:** Graph mein negative cycle hai ya nahi?

**Approach:** Bellman-Ford run karo, Vth iteration mein agar koi distance update hota hai toh negative cycle hai.

```java
public boolean hasNegativeCycle(int n, int[][] edges) {
    int[] dist = new int[n];
    Arrays.fill(dist, 0);  // All nodes as source
    
    // Relax V-1 times
    for (int i = 0; i < n - 1; i++) {
        boolean updated = false;
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], w = edge[2];
            if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
                dist[v] = dist[u] + w;
                updated = true;
            }
        }
        if (!updated) break;
    }
    
    // Check for negative cycle
    for (int[] edge : edges) {
        int u = edge[0], v = edge[1], w = edge[2];
        if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
            return true;  // Negative cycle found
        }
    }
    return false;
}
```

>  Bellman-Ford run karo. V-1 iterations ke baad bhi agar distance update hota hai, iska matlab negative cycle hai. Negative cycle ka matlab shortest path undefined hai (infinite loop mein chala jayega).

**Time:** O(V × E)

---

### 4. Minimum Cost to Reach Destination with Negative Weights
**Problem:** Graph mein negative weights hain. src se dst tak shortest path find karo.

**Approach:** Standard Bellman-Ford.

```java
public int shortestPathWithNegativeWeights(int n, int[][] edges, int src, int dst) {
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[src] = 0;
    
    // Relax V-1 times
    for (int i = 0; i < n - 1; i++) {
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], w = edge[2];
            if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
                dist[v] = dist[u] + w;
            }
        }
    }
    
    // Check for negative cycle
    for (int[] edge : edges) {
        int u = edge[0], v = edge[1], w = edge[2];
        if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
            return Integer.MIN_VALUE;  // Negative cycle present
        }
    }
    
    return dist[dst] == Integer.MAX_VALUE ? -1 : dist[dst];
}
```

>  Negative weights hone par Dijkstra fail ho jata hai. Bellman-Ford use karo. V-1 iterations mein shortest path mil jayega.

**Time:** O(V × E)

---

### 5. Minimum Weight Cycle in a Graph
**Problem:** Graph mein minimum weight cycle find karo (sum of weights).

**Approach:** Har edge ko remove karke shortest path find karo. Bellman-Ford use karo.

```java
public int findMinWeightCycle(int n, int[][] edges) {
    int minCycle = Integer.MAX_VALUE;
    
    for (int[] edge : edges) {
        int u = edge[0], v = edge[1], w = edge[2];
        
        // Remove this edge and find shortest path from v to u
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[v] = 0;
        
        for (int i = 0; i < n - 1; i++) {
            for (int[] e : edges) {
                if (e == edge) continue;  // Skip the removed edge
                int a = e[0], b = e[1], weight = e[2];
                if (dist[a] != Integer.MAX_VALUE && dist[a] + weight < dist[b]) {
                    dist[b] = dist[a] + weight;
                }
            }
        }
        
        if (dist[u] != Integer.MAX_VALUE) {
            minCycle = Math.min(minCycle, dist[u] + w);
        }
    }
    return minCycle == Integer.MAX_VALUE ? -1 : minCycle;
}
```

>  Har edge (u-v) ke liye, edge ko remove karo aur v se u tak shortest path find karo. Path + edge weight = cycle weight. Minimum cycle weight return karo.

**Time:** O(E × V × E) = O(V × E²) - slow but works for small graphs

---

### 6. Arbitrage Detection (Currency Exchange)
**Problem:** Currency exchange rates di hain. Kya arbitrage possible hai? (Negative cycle in log graph)

**Approach:** Exchange rates ko log mein convert karo. Negative cycle detect karo.

```java
public boolean hasArbitrage(double[][] rates) {
    int n = rates.length;
    double[] dist = new double[n];
    Arrays.fill(dist, 0);
    dist[0] = 0;
    
    // Convert to log: log(1/rate) = -log(rate)
    // Bellman-Ford for V-1 iterations
    for (int i = 0; i < n - 1; i++) {
        for (int u = 0; u < n; u++) {
            for (int v = 0; v < n; v++) {
                if (u == v) continue;
                double weight = -Math.log(rates[u][v]);
                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                }
            }
        }
    }
    
    // Check for negative cycle (arbitrage)
    for (int u = 0; u < n; u++) {
        for (int v = 0; v < n; v++) {
            if (u == v) continue;
            double weight = -Math.log(rates[u][v]);
            if (dist[u] + weight < dist[v]) {
                return true;  // Arbitrage possible
            }
        }
    }
    return false;
}
```

>  Arbitrage means profit without risk. Exchange rates ko log scale mein convert karo. Agar negative cycle mila toh arbitrage possible hai. Bellman-Ford se negative cycle detect karo.

**Time:** O(n³)

---

### 7. Shortest Path with At Most K Edges (Modified Bellman-Ford)
**Problem:** src se dst tak at most k edges mein shortest path.

**Approach:** Bellman-Ford run karo but sirf k iterations.

```java
public int shortestPathWithKEdges(int n, int[][] edges, int src, int dst, int k) {
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[src] = 0;
    
    for (int i = 0; i < k; i++) {
        int[] temp = Arrays.copyOf(dist, n);
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], w = edge[2];
            if (dist[u] != Integer.MAX_VALUE && dist[u] + w < temp[v]) {
                temp[v] = dist[u] + w;
            }
        }
        dist = temp;
    }
    
    return dist[dst] == Integer.MAX_VALUE ? -1 : dist[dst];
}
```

>  Exactly k iterations karo, har iteration ek edge ke corresponding relax karo. K iterations ke baad jo distance milega woh at most k edges wala shortest path hoga.

**Time:** O(k × E)

---

### 8. Minimum Cost to Make at Least One Valid Path (Bellman-Ford)
**Problem:** Grid mein directions hain. Direction change karne ki cost 1. Minimum cost.

**Approach:** Graph banao, Bellman-Ford run karo.

```java
public int minCost(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    int totalCells = m * n;
    int[][] dirs = {{0,1}, {0,-1}, {1,0}, {-1,0}};
    
    // Create edges: from each cell to its 4 neighbors
    List<int[]> edges = new ArrayList<>();
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            int u = i * n + j;
            for (int d = 0; d < 4; d++) {
                int ni = i + dirs[d][0];
                int nj = j + dirs[d][1];
                if (ni >= 0 && ni < m && nj >= 0 && nj < n) {
                    int v = ni * n + nj;
                    int cost = (grid[i][j] == d + 1) ? 0 : 1;
                    edges.add(new int[]{u, v, cost});
                }
            }
        }
    }
    
    // Bellman-Ford
    int[] dist = new int[totalCells];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[0] = 0;
    
    for (int i = 0; i < totalCells - 1; i++) {
        boolean updated = false;
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], w = edge[2];
            if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
                dist[v] = dist[u] + w;
                updated = true;
            }
        }
        if (!updated) break;
    }
    
    return dist[totalCells - 1];
}
```

>  Har cell se 4 neighbors mein edge banao. Current arrow wale neighbor mein cost 0, warna 1. Bellman-Ford se shortest path find karo.

**Time:** O(V × E) = O(mn × 4mn) = O(m²n²) - slow but works for small grids

---

### 9. Path with Minimum Effort (Bellman-Ford Version)
**Problem:** Grid mein path jisme maximum adjacent difference minimize karo.

**Approach:** Binary search + Bellman-Ford ya direct Bellman-Ford.

```java
public int minimumEffortPath(int[][] heights) {
    int m = heights.length, n = heights[0].length;
    int totalCells = m * n;
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    
    // Binary search on answer
    int left = 0, right = 1_000_000;
    int answer = right;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        // Bellman-Ford to check if path exists with max effort <= mid
        int[] dist = new int[totalCells];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[0] = 0;
        
        for (int i = 0; i < totalCells - 1; i++) {
            for (int x = 0; x < m; x++) {
                for (int y = 0; y < n; y++) {
                    int u = x * n + y;
                    if (dist[u] == Integer.MAX_VALUE) continue;
                    
                    for (int[] dir : dirs) {
                        int nx = x + dir[0], ny = y + dir[1];
                        if (nx < 0 || nx >= m || ny < 0 || ny >= n) continue;
                        
                        int effort = Math.abs(heights[nx][ny] - heights[x][y]);
                        if (effort <= mid) {
                            int v = nx * n + ny;
                            if (dist[u] + 1 < dist[v]) {
                                dist[v] = dist[u] + 1;
                            }
                        }
                    }
                }
            }
        }
        
        if (dist[totalCells - 1] != Integer.MAX_VALUE) {
            answer = mid;
            right = mid - 1;
        } else {
            left = mid + 1;
        }
    }
    return answer;
}
```

>  Binary search on maximum allowed effort. Mid ke liye Bellman-Ford check karo ki path exist karta hai ya nahi. Minimum mid return karo.

**Time:** O(log(max) × V × E)

---

### 10. Number of Ways to Arrive at Destination (With Negative Weights)
**Problem:** src se dst tak shortest paths ka count. Graph mein negative weights ho sakte hain.

**Approach:** Bellman-Ford se shortest distance nikaalo, phir DP se count karo.

```java
public int countPaths(int n, int[][] edges, int src, int dst) {
    int MOD = 1_000_000_007;
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[src] = 0;
    
    // Bellman-Ford to find shortest distances
    for (int i = 0; i < n - 1; i++) {
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], w = edge[2];
            if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
                dist[v] = dist[u] + w;
            }
        }
    }
    
    // DP to count paths
    int[] ways = new int[n];
    ways[src] = 1;
    Integer[] order = getTopologicalOrderIfDAG(); // For DAG only
    
    for (int u : order) {
        for (int[] edge : edges) {
            if (edge[0] == u) {
                int v = edge[1], w = edge[2];
                if (dist[u] + w == dist[v]) {
                    ways[v] = (ways[v] + ways[u]) % MOD;
                }
            }
        }
    }
    
    return ways[dst];
}
```

>  Pehle Bellman-Ford se shortest distances nikaalo. Phir sirf un edges ko follow karo jo shortest path mein contribute karti hain. DP se count karo.

**Time:** O(V × E + V + E)

---

### 11. Is There a Valid Path in a Grid with Negative Costs? (LeetCode 2000+)
**Problem:** Grid mein negative costs bhi ho sakte hain. (0,0) se (m-1,n-1) tak shortest path.

**Approach:** Bellman-Ford on grid.

```java
public int shortestPathWithNegativeGrid(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    int totalCells = m * n;
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    
    int[] dist = new int[totalCells];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[0] = grid[0][0];
    
    for (int i = 0; i < totalCells - 1; i++) {
        for (int x = 0; x < m; x++) {
            for (int y = 0; y < n; y++) {
                int u = x * n + y;
                if (dist[u] == Integer.MAX_VALUE) continue;
                
                for (int[] dir : dirs) {
                    int nx = x + dir[0], ny = y + dir[1];
                    if (nx < 0 || nx >= m || ny < 0 || ny >= n) continue;
                    
                    int v = nx * n + ny;
                    if (dist[u] + grid[nx][ny] < dist[v]) {
                        dist[v] = dist[u] + grid[nx][ny];
                    }
                }
            }
        }
    }
    
    return dist[totalCells - 1];
}
```

>  Grid ke har cell se 4 neighbors mein edge banao. Cost = neighbor cell ki value. Bellman-Ford se shortest path find karo.

**Time:** O(m²n²)

---

### 12. Minimum Cost to Connect All Nodes (Bellman-Ford Based)
**Problem:** Graph mein kuch nodes mandatory hain. Unhe connect karne ka minimum cost. Negative weights allowed.

**Approach:** Bellman-Ford se mandatory nodes ke beech shortest path nikaalo, phir MST-like approach.

```java
public int minCostToConnectMandatory(int n, int[][] edges, List<Integer> mandatory) {
    int k = mandatory.size();
    int[][] shortest = new int[k][k];
    
    // Bellman-Ford from each mandatory node
    for (int i = 0; i < k; i++) {
        int src = mandatory.get(i);
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;
        
        for (int iter = 0; iter < n - 1; iter++) {
            for (int[] edge : edges) {
                int u = edge[0], v = edge[1], w = edge[2];
                if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                }
                if (dist[v] != Integer.MAX_VALUE && dist[v] + w < dist[u]) {
                    dist[u] = dist[v] + w;
                }
            }
        }
        
        for (int j = 0; j < k; j++) {
            shortest[i][j] = dist[mandatory.get(j)];
        }
    }
    
    // Now find minimum spanning tree on mandatory nodes
    return minSpanningTree(shortest);
}
```

>  Pehle mandatory nodes ke beech shortest path nikaalo (Bellman-Ford se). Phir mandatory nodes pe MST (minimum spanning tree) banao.

**Time:** O(k × V × E)

---

### 13. Find if There is a Path of Length Exactly K
**Problem:** src se dst tak path of exactly k edges exist karta hai ya nahi? (Negative weights allowed)

**Approach:** Bellman-Ford run karo exactly k iterations, check if dist[dst] updated.

```java
public boolean hasPathOfLengthK(int n, int[][] edges, int src, int dst, int k) {
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[src] = 0;
    
    for (int i = 0; i < k; i++) {
        int[] temp = Arrays.copyOf(dist, n);
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], w = edge[2];
            if (dist[u] != Integer.MAX_VALUE && dist[u] + w < temp[v]) {
                temp[v] = dist[u] + w;
            }
        }
        dist = temp;
    }
    
    return dist[dst] != Integer.MAX_VALUE;
}
```

>  Exactly k iterations of Bellman-Ford. Har iteration ek edge add karta hai. K iterations ke baad agar dst reachable hai toh path exists.

**Time:** O(k × E)

---

### 14. Minimum Number of Edges to Reverse to Reach Destination
**Problem:** Directed graph mein src se dst tak pahunchne ke liye minimum edges reverse karo.

**Approach:** Bellman-Ford on graph with 0/1 weights (reverse cost 1, normal cost 0).

```java
public int minEdgesToReverse(int n, int[][] edges, int src, int dst) {
    List<int[]>[] graph = new ArrayList[n];
    for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
    
    for (int[] edge : edges) {
        int u = edge[0], v = edge[1];
        graph[u].add(new int[]{v, 0});  // Normal edge, cost 0
        graph[v].add(new int[]{u, 1});  // Reverse edge, cost 1
    }
    
    // Bellman-Ford
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[src] = 0;
    
    for (int i = 0; i < n - 1; i++) {
        for (int u = 0; u < n; u++) {
            if (dist[u] == Integer.MAX_VALUE) continue;
            for (int[] edge : graph[u]) {
                int v = edge[0], w = edge[1];
                if (dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                }
            }
        }
    }
    
    return dist[dst] == Integer.MAX_VALUE ? -1 : dist[dst];
}
```

>  Graph banao jisme original edge ki cost 0 hai, reverse edge ki cost 1 hai. Bellman-Ford se shortest path find karo. Cost = edges to reverse.

**Time:** O(V × E)

---

### 15. Parallel Courses III (With Negative Weights?)
**Problem:** Courses with prerequisites, each course has time. Minimum semesters? (DAG, but Bellman-Ford can also work)

**Approach:** Bellman-Ford for longest path (negate weights).

```java
public int minimumSemesters(int n, int[][] relations, int[] time) {
    // Convert to negative weights for longest path
    List<int[]> edges = new ArrayList<>();
    for (int[] rel : relations) {
        edges.add(new int[]{rel[0] - 1, rel[1] - 1, -time[rel[1] - 1]});
    }
    
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[0] = -time[0];  // Start with first course
    
    // Bellman-Ford
    for (int i = 0; i < n - 1; i++) {
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], w = edge[2];
            if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
                dist[v] = dist[u] + w;
            }
        }
    }
    
    int minDist = Integer.MAX_VALUE;
    for (int d : dist) minDist = Math.min(minDist, d);
    return -minDist;
}
```

>  Longest path find karna hai toh weights ko negate karo. Phir Bellman-Ford se shortest path nikaalo (which becomes longest in original).

**Time:** O(V × E)

---

### 16. Minimum Cost Flow with Negative Costs
**Problem:** Flow network mein negative costs allowed. Minimum cost flow.

**Approach:** Bellman-Ford for shortest path in residual graph.

```java
public int minCostFlow(int n, int[][] edges, int src, int sink, int flow) {
    // Bellman-Ford based min cost flow (Successive Shortest Path)
    int totalCost = 0;
    int remainingFlow = flow;
    
    while (remainingFlow > 0) {
        int[] dist = new int[n];
        int[] parent = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;
        
        // Bellman-Ford to find shortest path
        for (int i = 0; i < n - 1; i++) {
            for (int[] edge : edges) {
                int u = edge[0], v = edge[1], w = edge[2], cap = edge[3];
                if (cap > 0 && dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    parent[v] = u;
                }
            }
        }
        
        if (dist[sink] == Integer.MAX_VALUE) return -1;
        
        // Augment path
        int pathFlow = remainingFlow;
        for (int v = sink; v != src; v = parent[v]) {
            // Find min capacity along path
        }
        
        totalCost += dist[sink] * pathFlow;
        remainingFlow -= pathFlow;
    }
    return totalCost;
}
```

>  Successive Shortest Path algorithm. Har step pe Bellman-Ford se shortest path find karo (negative costs allowed), phir us path pe flow push karo.

**Time:** O(flow × V × E)

---

### 17. Find the City With the Smallest Number of Neighbors (Bellman-Ford)
**Problem:** Graph mein negative weights allowed. Har city se threshold distance ke andar kitne cities hain?

**Approach:** Bellman-Ford from each node.

```java
public int findTheCity(int n, int[][] edges, int distanceThreshold) {
    int bestCity = -1;
    int minReachable = n;
    
    for (int src = 0; src < n; src++) {
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;
        
        // Bellman-Ford from src
        for (int i = 0; i < n - 1; i++) {
            for (int[] edge : edges) {
                int u = edge[0], v = edge[1], w = edge[2];
                if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                }
                if (dist[v] != Integer.MAX_VALUE && dist[v] + w < dist[u]) {
                    dist[u] = dist[v] + w;
                }
            }
        }
        
        int reachable = 0;
        for (int i = 0; i < n; i++) {
            if (i != src && dist[i] <= distanceThreshold) reachable++;
        }
        
        if (reachable < minReachable || (reachable == minReachable && src > bestCity)) {
            minReachable = reachable;
            bestCity = src;
        }
    }
    return bestCity;
}
```

>  Har node se Bellman-Ford run karo. Threshold ke andar kitne nodes reachable hain count karo. Minimum reachable wala city return karo.

**Time:** O(V² × E)

---

### 18. Minimum Time to Visit a Cell In a Grid with Negative Delays
**Problem:** Grid mein negative time delays allowed. Minimum time to reach destination.

**Approach:** Bellman-Ford on grid.

```java
public int minTimeWithNegativeDelays(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    int totalCells = m * n;
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    
    int[] dist = new int[totalCells];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[0] = 0;
    
    for (int iter = 0; iter < totalCells - 1; iter++) {
        for (int x = 0; x < m; x++) {
            for (int y = 0; y < n; y++) {
                int u = x * n + y;
                if (dist[u] == Integer.MAX_VALUE) continue;
                
                for (int[] dir : dirs) {
                    int nx = x + dir[0], ny = y + dir[1];
                    if (nx < 0 || nx >= m || ny < 0 || ny >= n) continue;
                    
                    int v = nx * n + ny;
                    int time = grid[nx][ny];
                    if (dist[u] + time < dist[v]) {
                        dist[v] = dist[u] + time;
                    }
                }
            }
        }
    }
    
    return dist[totalCells - 1];
}
```

>  Grid ke har cell ki value delay hai (negative ho sakti hai). Bellman-Ford se shortest path find karo.

**Time:** O(m²n²)

---

### 19. Minimum Cost to Reach Destination with Toll Tax (Negative Toll Allowed)
**Problem:** Graph mein toll tax (negative allowed). src se dst tak minimum total toll.

**Approach:** Standard Bellman-Ford.

```java
public int minTollCost(int n, int[][] roads, int src, int dst) {
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[src] = 0;
    
    for (int i = 0; i < n - 1; i++) {
        for (int[] road : roads) {
            int u = road[0], v = road[1], toll = road[2];
            if (dist[u] != Integer.MAX_VALUE && dist[u] + toll < dist[v]) {
                dist[v] = dist[u] + toll;
            }
            if (dist[v] != Integer.MAX_VALUE && dist[v] + toll < dist[u]) {
                dist[u] = dist[v] + toll;
            }
        }
    }
    
    // Check for negative cycle
    for (int[] road : roads) {
        int u = road[0], v = road[1], toll = road[2];
        if (dist[u] != Integer.MAX_VALUE && dist[u] + toll < dist[v]) {
            return Integer.MIN_VALUE;  // Negative cycle - infinite profit
        }
    }
    
    return dist[dst] == Integer.MAX_VALUE ? -1 : dist[dst];
}
```

>  Toll tax negative bhi ho sakta hai (profit). Bellman-Ford se shortest path find karo. Agar negative cycle mila toh infinite profit possible hai.

**Time:** O(V × E)

---

### 20. Minimum Score of a Path Between Two Cities (With Negative Weights)
**Problem:** Graph mein har edge ka score hai. Path ka score = min edge score along path. Maximize this min score.

**Approach:** Bellman-Ford for maximin path (modify relaxation condition).

```java
public int maxMinScore(int n, int[][] edges, int src, int dst) {
    int[] maxMin = new int[n];
    Arrays.fill(maxMin, -1);
    maxMin[src] = Integer.MAX_VALUE;
    
    for (int i = 0; i < n - 1; i++) {
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], score = edge[2];
            if (maxMin[u] != -1) {
                int newScore = Math.min(maxMin[u], score);
                if (newScore > maxMin[v]) {
                    maxMin[v] = newScore;
                }
            }
            if (maxMin[v] != -1) {
                int newScore = Math.min(maxMin[v], score);
                if (newScore > maxMin[u]) {
                    maxMin[u] = newScore;
                }
            }
        }
    }
    
    return maxMin[dst] == -1 ? -1 : maxMin[dst];
}
```

>  Maximin path problem. Relaxation condition change karo: dist[v] = max(dist[v], min(dist[u], weight)). Bellman-Ford iterations se solve karo.

**Time:** O(V × E)

---

## 📊 Complete Summary Table

| # | Problem | Key Insight | Complexity |
|---|---------|-------------|------------|
| 1 | Cheapest Flights with K Stops | Bellman-Ford with stop limit | O(k×E) |
| 2 | Network Delay Time | Standard Bellman-Ford | O(V×E) |
| 3 | Negative Cycle Detection | Vth iteration update check | O(V×E) |
| 4 | Shortest Path with Negative Weights | Bellman-Ford | O(V×E) |
| 5 | Minimum Weight Cycle | Remove edge + shortest path | O(V×E²) |
| 6 | Arbitrage Detection | Log conversion + negative cycle | O(n³) |
| 7 | Shortest Path with K Edges | K iterations of Bellman-Ford | O(k×E) |
| 8 | Min Cost to Make Valid Path | Build graph + Bellman-Ford | O(m²n²) |
| 9 | Path with Minimum Effort | Binary search + Bellman-Ford | O(log max × V×E) |
| 10 | Count Paths with Negative Weights | Bellman-Ford + DP | O(V×E) |
| 11 | Shortest Path with Negative Grid | Grid to graph + Bellman-Ford | O(m²n²) |
| 12 | Connect Mandatory Nodes | Bellman-Ford + MST | O(k×V×E) |
| 13 | Path of Exactly K Edges | K iterations check | O(k×E) |
| 14 | Min Edges to Reverse | 0/1 BFS or Bellman-Ford | O(V×E) |
| 15 | Parallel Courses | Longest path via negation | O(V×E) |
| 16 | Min Cost Flow | Successive shortest path | O(flow×V×E) |
| 17 | City with Smallest Neighbors | Bellman-Ford from each node | O(V²×E) |
| 18 | Min Time with Negative Delays | Grid Bellman-Ford | O(m²n²) |
| 19 | Min Toll Cost | Standard Bellman-Ford | O(V×E) |
| 20 | Maximin Path | Modified relaxation | O(V×E) |

---

## 🎯 Bellman-Ford Interview Tips

1. **When to use Bellman-Ford over Dijkstra:**
   - Graph has **negative weight edges**
   - Need **negative cycle detection**
   - Graph is small (V×E manageable)
   - Need **k-edge constrained shortest path**

2. **Bellman-Ford Template Yaad Rakho:**
```java
int[] dist = new int[n];
Arrays.fill(dist, Integer.MAX_VALUE);
dist[src] = 0;

// Relax V-1 times
for (int i = 0; i < n - 1; i++) {
    for (int[] edge : edges) {
        int u = edge[0], v = edge[1], w = edge[2];
        if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
            dist[v] = dist[u] + w;
        }
    }
}

// Check for negative cycle
for (int[] edge : edges) {
    int u = edge[0], v = edge[1], w = edge[2];
    if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
        // Negative cycle exists
    }
}
```

3. **Common Modifications:**
   - **K iterations:** For "at most K edges" problems
   - **Maximization:** Change comparison operator
   - **0/1 weights:** Can use BFS, but Bellman-Ford also works
   - **All-pairs:** Run Bellman-Ford from each node (O(V²×E))

4. **Negative Cycle Detection:**
   - Vth iteration mein agar koi distance update hota hai → negative cycle
   - Negative cycle ka matlab shortest path undefined (can go to -∞)
   - In real problems: arbitrage, infinite profit

5. **Time Complexity Considerations:**
   - Bellman-Ford is **O(V×E)** - slower than Dijkstra
   - Use only when necessary (negative edges)
   - For large graphs, consider SPFA (average O(E), worst O(V×E))

6. **Common Mistakes:**
   - Integer.MAX_VALUE + negative number overflow → use `Integer.MAX_VALUE / 2`
   - Forgetting undirected graphs need both directions
   - Not checking for negative cycles when required

7. **SPFA (Shortest Path Faster Algorithm):**
   - Bellman-Ford ka optimization using queue
   - Average case O(E), worst case O(V×E)
   - Good for most negative weight problems
