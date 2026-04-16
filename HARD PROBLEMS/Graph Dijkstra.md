
# Dijkstra's Algorithm Core Concepts 

**Dijkstra Kaise Kaam Karta Hai:**

```java
// Template
PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[1] - b[1]);
int[] dist = new int[n];
Arrays.fill(dist, Integer.MAX_VALUE);
dist[start] = 0;
pq.offer(new int[]{start, 0});

while (!pq.isEmpty()) {
    int[] curr = pq.poll();
    int u = curr[0], d = curr[1];
    
    if (d > dist[u]) continue;  // Outdated entry skip
    
    for (Edge e : graph[u]) {
        int v = e.to, w = e.weight;
        if (dist[u] + w < dist[v]) {
            dist[v] = dist[u] + w;
            pq.offer(new int[]{v, dist[v]});
        }
    }
}
```

**Key Points:**
- Priority queue (min-heap) - hamesha smallest distance wala node uthao
- Negative weights allowed nahi hai (Bellman-Ford use karo)
- Time complexity: O((V+E) log V)

---

## 📋 20 Hard Dijkstra-Based Problems

---

### 1. Network Delay Time (LeetCode 743)
**Problem:** N nodes, edges `[u, v, w]`, source se sabhi nodes tak signal pahunchne ka minimum time.

**Approach:** Standard Dijkstra - sabse lamba shortest distance return karo.

```java
public int networkDelayTime(int[][] times, int n, int k) {
    // Build graph
    List<int[]>[] graph = new ArrayList[n + 1];
    for (int i = 1; i <= n; i++) graph[i] = new ArrayList<>();
    for (int[] t : times) graph[t[0]].add(new int[]{t[1], t[2]});
    
    // Dijkstra
    int[] dist = new int[n + 1];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[k] = 0;
    
    PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[1] - b[1]);
    pq.offer(new int[]{k, 0});
    
    while (!pq.isEmpty()) {
        int[] curr = pq.poll();
        int u = curr[0], d = curr[1];
        
        if (d > dist[u]) continue;
        
        for (int[] edge : graph[u]) {
            int v = edge[0], w = edge[1];
            if (dist[u] + w < dist[v]) {
                dist[v] = dist[u] + w;
                pq.offer(new int[]{v, dist[v]});
            }
        }
    }
    
    // Find max distance
    int maxTime = 0;
    for (int i = 1; i <= n; i++) {
        if (dist[i] == Integer.MAX_VALUE) return -1;
        maxTime = Math.max(maxTime, dist[i]);
    }
    return maxTime;
}
```

>  Standard Dijkstra - source se sab nodes ka shortest path nikaalo. Jo max distance hai wahi time lagega sab tak pahunchne mein. Agar koi node unreachable hai toh -1 return karo.

**Time:** O((V+E) log V)

---

### 2. Path With Minimum Effort (LeetCode 1631)
**Problem:** Grid heights. Path from (0,0) to (m-1,n-1) jisme **maximum adjacent difference** minimize karo.

**Approach:** Dijkstra jisme "distance" = maximum effort so far. Priority queue min effort wale path pe based hai.

```java
public int minimumEffortPath(int[][] heights) {
    int m = heights.length, n = heights[0].length;
    int[][] dist = new int[m][n];
    for (int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE);
    dist[0][0] = 0;
    
    PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[2] - b[2]);
    pq.offer(new int[]{0, 0, 0});  // {x, y, effort}
    
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    
    while (!pq.isEmpty()) {
        int[] curr = pq.poll();
        int x = curr[0], y = curr[1], effort = curr[2];
        
        if (x == m-1 && y == n-1) return effort;
        
        if (effort > dist[x][y]) continue;
        
        for (int[] dir : dirs) {
            int nx = x + dir[0], ny = y + dir[1];
            if (nx < 0 || nx >= m || ny < 0 || ny >= n) continue;
            
            int newEffort = Math.max(effort, Math.abs(heights[nx][ny] - heights[x][y]));
            if (newEffort < dist[nx][ny]) {
                dist[nx][ny] = newEffort;
                pq.offer(new int[]{nx, ny, newEffort});
            }
        }
    }
    return 0;
}
```

>  Normal Dijkstra mein distance sum hota hai, yahan distance = max difference so far. Priority queue min effort wale ko uthata hai. Jab destination pe pahunche, wahi effort return karo.

**Time:** O(mn log(mn))

---

### 3. Cheapest Flights Within K Stops (LeetCode 787)
**Problem:** src se dst tak at most k stops mein cheapest price.

**Approach:** Dijkstra variant jisme stops bhi track karo. Priority queue based on price.

```java
public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
    List<int[]>[] graph = new ArrayList[n];
    for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
    for (int[] f : flights) graph[f[0]].add(new int[]{f[1], f[2]});
    
    // {node, price, stops}
    PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[1] - b[1]);
    pq.offer(new int[]{src, 0, 0});
    
    int[] stopsCount = new int[n];
    Arrays.fill(stopsCount, Integer.MAX_VALUE);
    
    while (!pq.isEmpty()) {
        int[] curr = pq.poll();
        int u = curr[0], price = curr[1], stops = curr[2];
        
        if (u == dst) return price;
        
        if (stops > k) continue;
        
        for (int[] edge : graph[u]) {
            int v = edge[0], w = edge[1];
            if (stops + 1 <= k + 1 && stops + 1 < stopsCount[v]) {
                stopsCount[v] = stops + 1;
                pq.offer(new int[]{v, price + w, stops + 1});
            }
        }
    }
    return -1;
}
```

>  Priority queue price ke basis pe min-heap hai. Stops count bhi track karo. Agar stops limit exceed ho jaye toh skip karo. Pehli baar jab destination milega woh cheapest hoga.

**Time:** O(E log V)

---

### 4. Swim in Rising Water (LeetCode 778)
**Problem:** Grid heights. Time t pe water height t. (0,0) se (n-1,n-1) tak swim karna hai. Minimum time?

**Approach:** Dijkstra jisme "distance" = max height seen so far.

```java
public int swimInWater(int[][] grid) {
    int n = grid.length;
    int[][] dist = new int[n][n];
    for (int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE);
    dist[0][0] = grid[0][0];
    
    PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[2] - b[2]);
    pq.offer(new int[]{0, 0, grid[0][0]});
    
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    
    while (!pq.isEmpty()) {
        int[] curr = pq.poll();
        int x = curr[0], y = curr[1], time = curr[2];
        
        if (x == n-1 && y == n-1) return time;
        
        if (time > dist[x][y]) continue;
        
        for (int[] dir : dirs) {
            int nx = x + dir[0], ny = y + dir[1];
            if (nx < 0 || nx >= n || ny < 0 || ny >= n) continue;
            
            int newTime = Math.max(time, grid[nx][ny]);
            if (newTime < dist[nx][ny]) {
                dist[nx][ny] = newTime;
                pq.offer(new int[]{nx, ny, newTime});
            }
        }
    }
    return -1;
}
```

>  Water level time ke saath badhta hai. Path mein maximum height wala cell decide karta hai kitna time lagega. Dijkstra se min time find karo.

**Time:** O(n² log n)

---

### 5. Minimum Cost to Make at Least One Valid Path in a Grid (LeetCode 1368)
**Problem:** Grid mein har cell mein direction arrow hai (1:right,2:left,3:down,4:up). Cost 1 se direction change kar sakte ho. (0,0) se (m-1,n-1) tak minimum cost.

**Approach:** 0-1 BFS ya Dijkstra. Same direction mein cost 0, different mein cost 1.

```java
public int minCost(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    int[][] dist = new int[m][n];
    for (int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE);
    dist[0][0] = 0;
    
    PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[2] - b[2]);
    pq.offer(new int[]{0, 0, 0});
    
    int[][] dirs = {{0,1}, {0,-1}, {1,0}, {-1,0}};  // right, left, down, up
    
    while (!pq.isEmpty()) {
        int[] curr = pq.poll();
        int x = curr[0], y = curr[1], cost = curr[2];
        
        if (x == m-1 && y == n-1) return cost;
        
        if (cost > dist[x][y]) continue;
        
        for (int d = 0; d < 4; d++) {
            int nx = x + dirs[d][0];
            int ny = y + dirs[d][1];
            if (nx < 0 || nx >= m || ny < 0 || ny >= n) continue;
            
            int newCost = cost + (grid[x][y] == d + 1 ? 0 : 1);
            if (newCost < dist[nx][ny]) {
                dist[nx][ny] = newCost;
                pq.offer(new int[]{nx, ny, newCost});
            }
        }
    }
    return dist[m-1][n-1];
}
```

>  Har cell ke 4 directions mein ja sakte ho. Agar current arrow same direction hai toh cost 0, warna 1. Dijkstra se minimum cost find karo.

**Time:** O(mn log(mn))

---

### 6. Minimum Obstacle Removal to Reach Corner (LeetCode 2290)
**Problem:** Grid 0=empty, 1=obstacle. (0,0) se (m-1,n-1) tak minimum obstacles remove karo.

**Approach:** 0-1 BFS ya Dijkstra. Empty cell cost 0, obstacle cost 1.

```java
public int minimumObstacles(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    int[][] dist = new int[m][n];
    for (int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE);
    dist[0][0] = 0;
    
    PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[2] - b[2]);
    pq.offer(new int[]{0, 0, 0});
    
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    
    while (!pq.isEmpty()) {
        int[] curr = pq.poll();
        int x = curr[0], y = curr[1], cost = curr[2];
        
        if (x == m-1 && y == n-1) return cost;
        
        if (cost > dist[x][y]) continue;
        
        for (int[] dir : dirs) {
            int nx = x + dir[0], ny = y + dir[1];
            if (nx < 0 || nx >= m || ny < 0 || ny >= n) continue;
            
            int newCost = cost + grid[nx][ny];
            if (newCost < dist[nx][ny]) {
                dist[nx][ny] = newCost;
                pq.offer(new int[]{nx, ny, newCost});
            }
        }
    }
    return dist[m-1][n-1];
}
```

>  0-1 BFS variant. Obstacle (1) pe cost 1, empty (0) pe cost 0. Dijkstra se minimum cost path find karo.

**Time:** O(mn log(mn))

---

### 7. Minimum Time to Visit a Cell in a Grid (LeetCode 2577)
**Problem:** Grid mein har cell (time[i][j]). Time t pe cell mein ho sakte ho only if t >= grid[i][j]. (0,0) se (m-1,n-1) tak minimum time.

**Approach:** Dijkstra jisme wait time bhi calculate karo.

```java
public int minimumTime(int[][] grid) {
    if (grid[0][1] > 1 && grid[1][0] > 1) return -1;
    
    int m = grid.length, n = grid[0].length;
    int[][] dist = new int[m][n];
    for (int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE);
    dist[0][0] = 0;
    
    PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[2] - b[2]);
    pq.offer(new int[]{0, 0, 0});
    
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    
    while (!pq.isEmpty()) {
        int[] curr = pq.poll();
        int x = curr[0], y = curr[1], time = curr[2];
        
        if (x == m-1 && y == n-1) return time;
        
        if (time > dist[x][y]) continue;
        
        for (int[] dir : dirs) {
            int nx = x + dir[0], ny = y + dir[1];
            if (nx < 0 || nx >= m || ny < 0 || ny >= n) continue;
            
            int newTime = time + 1;
            if (newTime < grid[nx][ny]) {
                // Need to wait
                int diff = grid[nx][ny] - newTime;
                newTime = grid[nx][ny] + (diff % 2 == 0 ? 0 : 1);
            }
            
            if (newTime < dist[nx][ny]) {
                dist[nx][ny] = newTime;
                pq.offer(new int[]{nx, ny, newTime});
            }
        }
    }
    return -1;
}
```

>  Agar cell ke time se pehle pahunche toh wait karna padega. Wait time calculate karo taki parity match ho (kyunki har move mein time +1 hota hai).

**Time:** O(mn log(mn))

---

### 8. Shortest Path in a Grid with Obstacles Elimination (LeetCode 1293)
**Problem:** Grid 0=empty,1=obstacle. At most k obstacles remove kar sakte ho. (0,0) se (m-1,n-1) tak shortest path.

**Approach:** Dijkstra with state (x, y, k). Distance = steps.

```java
public int shortestPath(int[][] grid, int k) {
    int m = grid.length, n = grid[0].length;
    int[][][] dist = new int[m][n][k+1];
    for (int[][] d1 : dist) for (int[] d2 : d1) Arrays.fill(d2, Integer.MAX_VALUE);
    dist[0][0][k] = 0;
    
    PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[3] - b[3]);
    pq.offer(new int[]{0, 0, k, 0});  // x, y, remaining k, steps
    
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    
    while (!pq.isEmpty()) {
        int[] curr = pq.poll();
        int x = curr[0], y = curr[1], remainingK = curr[2], steps = curr[3];
        
        if (x == m-1 && y == n-1) return steps;
        
        if (steps > dist[x][y][remainingK]) continue;
        
        for (int[] dir : dirs) {
            int nx = x + dir[0], ny = y + dir[1];
            if (nx < 0 || nx >= m || ny < 0 || ny >= n) continue;
            
            int newK = remainingK;
            if (grid[nx][ny] == 1) newK--;
            if (newK < 0) continue;
            
            if (steps + 1 < dist[nx][ny][newK]) {
                dist[nx][ny][newK] = steps + 1;
                pq.offer(new int[]{nx, ny, newK, steps + 1});
            }
        }
    }
    return -1;
}
```

>  State (x, y, remainingK) hai. Obstacle pe jaana hai toh remainingK kam karo. Dijkstra se minimum steps find karo.

**Time:** O(mn k log(mnk))

---

### 9. Minimum Cost Path with Special Roads (LeetCode 2662)
**Problem:** Start (x1,y1) se end (x2,y2) tak jaana hai. Special roads bhi hain jo directly point se point connect karte hain with less cost.

**Approach:** Dijkstra on points. Har point se saare special roads try karo.

```java
public int minimumCost(int[] start, int[] target, int[][] specialRoads) {
    Map<String, Integer> dist = new HashMap<>();
    dist.put(start[0] + "," + start[1], 0);
    
    PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[2] - b[2]);
    pq.offer(new int[]{start[0], start[1], 0});
    
    while (!pq.isEmpty()) {
        int[] curr = pq.poll();
        int x = curr[0], y = curr[1], cost = curr[2];
        
        String key = x + "," + y;
        if (cost > dist.getOrDefault(key, Integer.MAX_VALUE)) continue;
        
        // Direct to target
        int directCost = Math.abs(target[0] - x) + Math.abs(target[1] - y);
        if (cost + directCost < dist.getOrDefault(target[0] + "," + target[1], Integer.MAX_VALUE)) {
            dist.put(target[0] + "," + target[1], cost + directCost);
            pq.offer(new int[]{target[0], target[1], cost + directCost});
        }
        
        // Try all special roads
        for (int[] road : specialRoads) {
            int toX = road[2], toY = road[3], specialCost = road[4];
            int normalCost = Math.abs(road[0] - x) + Math.abs(road[1] - y);
            int newCost = cost + normalCost + specialCost;
            
            String toKey = toX + "," + toY;
            if (newCost < dist.getOrDefault(toKey, Integer.MAX_VALUE)) {
                dist.put(toKey, newCost);
                pq.offer(new int[]{toX, toY, newCost});
            }
        }
    }
    return dist.get(target[0] + "," + target[1]);
}
```

>  Manhattan distance normal cost hai. Special roads se direct cheaper connection mil sakta hai. Har point se saare special roads try karo.

**Time:** O(n²) special roads ke liye

---

### 10. Minimum Time to Visit a Cell In a Grid (LeetCode 2577)
**(Same as #7 - already covered)**

---

### 11. Minimum Number of Refueling Stops (LeetCode 871)
**Problem:** Car start fuel se start hoti hai. Stations [position, fuel] hain. Target tak pahunchne ke liye minimum refueling stops.

**Approach:** Max-heap based greedy (Dijkstra variant). Priority queue se max fuel wala station choose karo.

```java
public int minRefuelStops(int target, int startFuel, int[][] stations) {
    PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> b - a);  // max-heap
    int maxReach = startFuel;
    int stops = 0;
    int i = 0;
    int n = stations.length;
    
    while (maxReach < target) {
        // Add all stations within reach
        while (i < n && stations[i][0] <= maxReach) {
            pq.offer(stations[i][1]);
            i++;
        }
        
        if (pq.isEmpty()) return -1;
        
        // Use station with max fuel
        maxReach += pq.poll();
        stops++;
    }
    return stops;
}
```

>  Dijkstra jaisa approach - jitna door tak ja sakte ho, un stations ko max-heap mein daalo. Jab fuel khatam ho, max fuel wala station use karo.

**Time:** O(n log n)

---

### 12. Minimum Cost to Connect Sticks (LeetCode 1167)
**Problem:** Sticks ko connect karna hai. Cost = sum of lengths. Minimum total cost.

**Approach:** Greedy + min-heap (Huffman coding). Dijkstra ka simple version.

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

>  Hamesha do smallest sticks uthao, unhe jodo, wapas heap mein daalo. Yeh greedy approach optimal hai.

**Time:** O(n log n)

---

### 13. Reachable Nodes in Subdivided Graph (LeetCode 882)
**Problem:** Graph jisme edges ke beech nodes add kar diye hain. Moves limited hain. Kitne nodes reachable hain?

**Approach:** Dijkstra se max moves track karo.

```java
public int reachableNodes(int[][] edges, int maxMoves, int n) {
    List<int[]>[] graph = new ArrayList[n];
    for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
    
    for (int[] e : edges) {
        graph[e[0]].add(new int[]{e[1], e[2]});
        graph[e[1]].add(new int[]{e[0], e[2]});
    }
    
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[0] = 0;
    
    PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[1] - b[1]);
    pq.offer(new int[]{0, 0});
    
    while (!pq.isEmpty()) {
        int[] curr = pq.poll();
        int u = curr[0], d = curr[1];
        
        if (d > dist[u]) continue;
        
        for (int[] edge : graph[u]) {
            int v = edge[0], w = edge[1];
            if (dist[u] + w + 1 < dist[v]) {
                dist[v] = dist[u] + w + 1;
                pq.offer(new int[]{v, dist[v]});
            }
        }
    }
    
    int result = 0;
    for (int i = 0; i < n; i++) {
        if (dist[i] <= maxMoves) result++;
    }
    
    for (int[] e : edges) {
        int u = e[0], v = e[1], cnt = e[2];
        int left = Math.max(0, maxMoves - dist[u]);
        int right = Math.max(0, maxMoves - dist[v]);
        result += Math.min(cnt, left + right);
    }
    return result;
}
```

>  Pehle Dijkstra se har original node ka distance nikaalo. Phir har subdivided edge ke beech kitne nodes reachable hain calculate karo.

**Time:** O(E log V)

---

### 14. Path with Maximum Probability (LeetCode 1514)
**Problem:** Graph jisme edges ki probability hai. Start se end tak maximum probability path.

**Approach:** Dijkstra variant jisme sum ki jagah product use karo. Max-heap use karo.

```java
public double maxProbability(int n, int[][] edges, double[] succProb, int start, int end) {
    List<double[]>[] graph = new ArrayList[n];
    for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
    
    for (int i = 0; i < edges.length; i++) {
        int u = edges[i][0], v = edges[i][1];
        double prob = succProb[i];
        graph[u].add(new double[]{v, prob});
        graph[v].add(new double[]{u, prob});
    }
    
    double[] dist = new double[n];
    dist[start] = 1.0;
    
    PriorityQueue<double[]> pq = new PriorityQueue<>((a,b) -> Double.compare(b[1], a[1]));
    pq.offer(new double[]{start, 1.0});
    
    while (!pq.isEmpty()) {
        double[] curr = pq.poll();
        int u = (int) curr[0];
        double prob = curr[1];
        
        if (u == end) return prob;
        if (prob < dist[u]) continue;
        
        for (double[] edge : graph[u]) {
            int v = (int) edge[0];
            double p = edge[1];
            if (dist[u] * p > dist[v]) {
                dist[v] = dist[u] * p;
                pq.offer(new double[]{v, dist[v]});
            }
        }
    }
    return 0.0;
}
```

>  Normal Dijkstra sum hota hai, yahan product hai. Max probability chahiye toh max-heap use karo.

**Time:** O((V+E) log V)

---

### 15. Minimum Cost to Reach Destination in Time (LeetCode 1928)
**Problem:** Graph, har edge ka time aur cost hai. MaxTime ke andar destination tak pahunchne ka minimum cost.

**Approach:** Dijkstra with state (node, time). Priority queue cost ke basis pe.

```java
public int minCost(int maxTime, int[][] edges, int[] passingFees) {
    int n = passingFees.length;
    List<int[]>[] graph = new ArrayList[n];
    for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
    
    for (int[] e : edges) {
        graph[e[0]].add(new int[]{e[1], e[2]});
        graph[e[1]].add(new int[]{e[0], e[2]});
    }
    
    int[][] dist = new int[n][maxTime + 1];
    for (int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE);
    dist[0][0] = passingFees[0];
    
    PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[2] - b[2]);
    pq.offer(new int[]{0, 0, passingFees[0]});  // node, time, cost
    
    while (!pq.isEmpty()) {
        int[] curr = pq.poll();
        int u = curr[0], time = curr[1], cost = curr[2];
        
        if (u == n-1) return cost;
        if (cost > dist[u][time]) continue;
        
        for (int[] edge : graph[u]) {
            int v = edge[0], t = edge[1];
            int newTime = time + t;
            if (newTime > maxTime) continue;
            
            int newCost = cost + passingFees[v];
            if (newCost < dist[v][newTime]) {
                dist[v][newTime] = newCost;
                pq.offer(new int[]{v, newTime, newCost});
            }
        }
    }
    return -1;
}
```

>  State (node, time) hai. Time limit ke andar minimum cost path dhundho. Priority queue cost ke basis pe.

**Time:** O(V * maxTime * log(V * maxTime))

---

### 16. Second Minimum Time to Reach Destination (LeetCode 2045)
**Problem:** Graph mein second shortest time find karo. Edges ka travel time same hai.

**Approach:** Dijkstra variant jo shortest aur second shortest track kare.

```java
public int secondMinimum(int n, int[][] edges, int time, int change) {
    List<Integer>[] graph = new ArrayList[n+1];
    for (int i = 1; i <= n; i++) graph[i] = new ArrayList<>();
    for (int[] e : edges) {
        graph[e[0]].add(e[1]);
        graph[e[1]].add(e[0]);
    }
    
    int[] dist1 = new int[n+1];
    int[] dist2 = new int[n+1];
    Arrays.fill(dist1, Integer.MAX_VALUE);
    Arrays.fill(dist2, Integer.MAX_VALUE);
    dist1[1] = 0;
    
    PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[1] - b[1]);
    pq.offer(new int[]{1, 0});
    
    while (!pq.isEmpty()) {
        int[] curr = pq.poll();
        int u = curr[0], t = curr[1];
        
        for (int v : graph[u]) {
            int newTime = t + time;
            
            // Add waiting time if in red signal
            int cycle = newTime / change;
            if (cycle % 2 == 1) {
                newTime = (cycle + 1) * change;
            }
            
            if (newTime < dist1[v]) {
                dist2[v] = dist1[v];
                dist1[v] = newTime;
                pq.offer(new int[]{v, newTime});
            } else if (newTime > dist1[v] && newTime < dist2[v]) {
                dist2[v] = newTime;
                pq.offer(new int[]{v, newTime});
            }
        }
    }
    return dist2[n];
}
```

>  Shortest aur second shortest distance track karo. Traffic signal ki wajah se wait time bhi add karo.

**Time:** O(E log V)

---

### 17. Minimum Time to Visit a Cell In a Grid (Repeated)

---

### 18. Maximum Number of Points With Cost (LeetCode 1937)
**Problem:** Matrix mein har row se ek column choose karna hai. Points = sum of values - |col_prev - col_curr|. Maximum points.

**Approach:** Dijkstra nahi, DP with optimization. But understanding helps.

```java
public long maxPoints(int[][] points) {
    int m = points.length, n = points[0].length;
    long[] dp = new long[n];
    
    for (int i = 0; i < n; i++) dp[i] = points[0][i];
    
    for (int i = 1; i < m; i++) {
        long[] left = new long[n];
        long[] right = new long[n];
        long[] newDp = new long[n];
        
        left[0] = dp[0];
        for (int j = 1; j < n; j++) left[j] = Math.max(left[j-1] - 1, dp[j]);
        
        right[n-1] = dp[n-1];
        for (int j = n-2; j >= 0; j--) right[j] = Math.max(right[j+1] - 1, dp[j]);
        
        for (int j = 0; j < n; j++) {
            newDp[j] = points[i][j] + Math.max(left[j], right[j]);
        }
        dp = newDp;
    }
    
    long max = 0;
    for (long val : dp) max = Math.max(max, val);
    return max;
}
```

>  Direct Dijkstra nahi hai but DP with optimization. Har row ke liye left aur right se best previous value calculate karo.

**Time:** O(m*n)

---

### 19. Minimum Cost Homecoming of a Robot in a Grid (LeetCode 2087)
**Problem:** Grid mein robot start se end tak jaana hai. Row cost aur col cost alag hai. Minimum cost.

**Approach:** Greedy - sirf row aur col move karo, no Dijkstra needed.

```java
public int minCost(int[] startPos, int[] homePos, int[] rowCosts, int[] colCosts) {
    int cost = 0;
    int row = startPos[0], col = startPos[1];
    int targetRow = homePos[0], targetCol = homePos[1];
    
    while (row != targetRow) {
        row += (row < targetRow) ? 1 : -1;
        cost += rowCosts[row];
    }
    
    while (col != targetCol) {
        col += (col < targetCol) ? 1 : -1;
        cost += colCosts[col];
    }
    return cost;
}
```

>  Simple greedy - pehle row move karo, phir col move karo. Dijkstra ki zaroorat nahi.

**Time:** O(|row diff| + |col diff|)

---

### 20. Minimum Weighted Subgraph With Required Paths (LeetCode 2203)
**Problem:** Graph mein src1 se dest aur src2 se dest tak paths chahiye. Minimum total weight.

**Approach:** Dijkstra from src1, src2, and reverse from dest. Then try all meeting points.
You're very close—just need to complete the final aggregation logic and ensure proper edge-case handling.

### ✅ Complete Code (Java)

```java
public long minimumWeight(int n, int[][] edges, int src1, int src2, int dest) {
    List<int[]>[] graph = new ArrayList[n];
    List<int[]>[] reverseGraph = new ArrayList[n];
    
    for (int i = 0; i < n; i++) {
        graph[i] = new ArrayList<>();
        reverseGraph[i] = new ArrayList<>();
    }
    
    // Build graph and reverse graph
    for (int[] e : edges) {
        graph[e[0]].add(new int[]{e[1], e[2]});
        reverseGraph[e[1]].add(new int[]{e[0], e[2]});
    }
    
    // Run Dijkstra from src1, src2 and dest (reverse graph)
    long[] dist1 = dijkstra(graph, src1, n);
    long[] dist2 = dijkstra(graph, src2, n);
    long[] distDest = dijkstra(reverseGraph, dest, n);
    
    long minWeight = Long.MAX_VALUE;
    
    // Try all possible meeting points
    for (int i = 0; i < n; i++) {
        if (dist1[i] != Long.MAX_VALUE && 
            dist2[i] != Long.MAX_VALUE && 
            distDest[i] != Long.MAX_VALUE) {
            
            long total = dist1[i] + dist2[i] + distDest[i];
            minWeight = Math.min(minWeight, total);
        }
    }
    
    return minWeight == Long.MAX_VALUE ? -1 : minWeight;
}

// Standard Dijkstra
private long[] dijkstra(List<int[]>[] graph, int src, int n) {
    long[] dist = new long[n];
    Arrays.fill(dist, Long.MAX_VALUE);
    
    PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[1], b[1]));
    pq.offer(new long[]{src, 0});
    dist[src] = 0;
    
    while (!pq.isEmpty()) {
        long[] curr = pq.poll();
        int node = (int) curr[0];
        long currDist = curr[1];
        
        if (currDist > dist[node]) continue;
        
        for (int[] neighbor : graph[node]) {
            int next = neighbor[0];
            long weight = neighbor[1];
            
            if (dist[node] + weight < dist[next]) {
                dist[next] = dist[node] + weight;
                pq.offer(new long[]{next, dist[next]});
            }
        }
    }
    
    return dist;
}
```

---

## 💡 Intuition (Important for Interviews)

Yeh problem ka core idea hai:

* Tumhe **2 sources (src1, src2)** se **same destination (dest)** tak jaana hai
* Direct 2 separate paths lene se optimal solution nahi milega
* Isliye ek **common meeting node (i)** assume karte hain

### Final formula:

```
dist(src1 → i) + dist(src2 → i) + dist(i → dest)
```

---

##  Key Trick (Most Important Insight)

* `dist1[i]` → src1 se i
* `dist2[i]` → src2 se i
* `distDest[i]` → i se dest

⚠️ But i → dest ka Dijkstra kaise?

👉 Reverse graph bana ke:

```
dest → i (reverse graph) = i → dest (original graph)
```

---

## ⏱️ Time Complexity

* 3 × Dijkstra → **O(E log V)**
* Total: **O(E log V)**
Great—yeh 3 questions interviewer almost pakka poochta hai. Let’s break them with **clear intuition + crisp explanation** 👇

---

# 1️⃣ Why Reverse Graph? (Direction Problem)

### ❓ Problem

Hume chahiye:

```
dist(i → dest)
```

But Dijkstra normally kya karta hai?

```
dist(source → all nodes)
```

👉 Matlab agar hum direct graph pe Dijkstra chalayen:

* We can get: `dest → i` ❌ (wrong direction)
* But we need: `i → dest` ✅

---

### 💡 Solution: Reverse Graph

Edges reverse kar do:

```
u → v  (original)
v → u  (reverse)
```

Ab agar hum:

```
Dijkstra(dest) on reverse graph
```

Toh actually calculate ho raha hai:

```
dest → i (reverse graph) = i → dest (original graph)
```

---

### 🔥 Intuition (Real-life analogy)

Socho roads one-way hain:

* Tumhe jaana hai **i se dest**
* But tum sirf calculate kar sakte ho **dest se outward distances**

👉 Toh road direction reverse kar do, fir:

* dest se chalke sabka shortest path mil jayega

---

# 2️⃣ Why Meeting Point? (Shared Path Optimization)

### ❓ Problem

Do independent paths:

```
src1 → dest
src2 → dest
```

Agar separately jaoge:

* Total cost = dist1 + dist2 (NOT optimal)

---

### 💡 Key Insight

Dono paths **merge ho sakte hain** kisi node `i` pe:

```
src1 → i
src2 → i
i → dest (common path)
```

---

### 🧠 Why optimal?

* i → dest part **shared hai**
* Isliye duplicate cost avoid hota hai

---

### 🔥 Visualization

```
src1 ----\
           >---- i ----> dest
src2 ----/
```

Instead of:

```
src1 ----------> dest
src2 ----------> dest
```

👉 Shared tail = cost reduce

---

### 🧮 Final Formula

```
dist1[i] + dist2[i] + distDest[i]
```

Har node ko meeting point assume karo → minimum le lo

---

# 3️⃣ Why NOT BFS? (Weighted Graph Issue)

### ❓ BFS kab use hota hai?

* Jab edges ka weight same ho (usually = 1)

---

### ❌ Problem yahan

Graph me weights arbitrary hain:

```
u → v = 2
u → w = 10
```

BFS kya karega?

* Dono ko equal treat karega ❌

---

### 💡 Why Dijkstra?

* It always picks **minimum distance node first**
* Handles variable weights correctly

---

### 🔥 Example

```
A → B (1)
A → C (10)
B → D (1)
C → D (1)
```

Correct shortest path:

```
A → B → D = 2
```

BFS may wrongly prefer:

```
A → C → D = 11 ❌
```

