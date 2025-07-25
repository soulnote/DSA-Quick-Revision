## 743\. Network Delay Time

Network Delay Time ek graph problem hai jismein humein source node se saare doosre nodes tak signal pahunchne mein kitna time lagega, woh calculate karna hai. Agar saare nodes tak signal nahi pahunchta, toh -1 return karna hai.

-----

### Description/Overview

Imagine karo ki tumhare paas ek network hai jismein servers (nodes) hain aur cables (edges) hain jo unhe connect karte hain. Har cable ka apna ek time hai jo signal ko ek server se doosre tak pahunchne mein lagta hai. Tumhein ek **starting server (source node)** diya jayega, aur tumhein pata karna hai ki is starting server se signal ko saare doosre servers tak pahunchne mein **minimum kitna time** lagega. Agar koi server aisa hai jahan tak signal pahunch hi nahi sakta, toh humein indicate karna hai ki yeh possible nahi hai.

Is problem ko solve karne ke liye hum **Dijkstra's Algorithm** use karte hain, jo ki shortest path finding ke liye bahut famous hai.

### Approach (Pahunch)

1.  **Graph Banayein:** Sabse pehle, jo `times` array diya gaya hai, usse ek **adjacency list** banayenge. Adjacency list mein har node ke liye ek list hogi jismein uske neighbors aur un tak pahunchne ka time store hoga. Yeh ek **directed graph** hoga, matlab signal sirf ek direction mein travel karta hai (U se V, V se U nahi necessarily).
2.  **Distance Array Initialize Karein:** Ek `distance` array banayenge jo har node tak pahunchne ka minimum time store karega. Isko `infinity` se initialize kar denge (ya `Integer.MAX_VALUE`), aur source node ka distance `0` set kar denge.
3.  **Min-Heap (PriorityQueue) Use Karein:** Hum ek `PriorityQueue` (min-heap) ka use karenge. Isme `[distance, node]` pairs store karenge, jahan `distance` current node tak pahunchne ka time hai. `PriorityQueue` automatically sabse kam distance wale node ko top par rakhega.
4.  **Dijkstra's Algorithm Apply Karein:**
      * `PriorityQueue` mein source node (`[0, k]`) ko daal do.
      * Jab tak `PriorityQueue` empty nahi hota:
          * Sabse chhota distance wala node `[d, u]` nikalo.
          * Agar `d` current `distance[u]` se zyada hai, toh is node ko skip kar do (kyunki humein pehle hi isse chhota path mil chuka hai).
          * Warna, `u` ke saare neighbors `v` ko dekho.
          * Agar `distance[u] + weight(u, v)` (current node tak ka time + neighbor tak ka edge time) `distance[v]` se kam hai, toh `distance[v]` ko update karo aur `[distance[v], v]` ko `PriorityQueue` mein add karo.
5.  **Result Calculate Karein:** Ek baar jab `PriorityQueue` empty ho jaye, `distance` array mein saare nodes tak ka minimum time hoga. Ab, `distance` array mein se **maximum value** find karo. Yahi hamara answer hoga.
6.  **Check for Unreachable Nodes:** Agar `distance` array mein koi bhi node `infinity` (ya `Integer.MAX_VALUE`) reh jata hai, toh iska matlab hai ki signal us node tak nahi pahunch paya. Is case mein, `-1` return kar do.

### Solution (Hal)

Hum Dijkstra's algorithm ke through nodes ko visit karenge, hamesha us node ko pick karenge jo source se sabse paas hai. Jaise hi hum ek node ko visit karte hain, hum uske neighbors ke distances ko update karne ki koshish karte hain. Agar humein koi naya chhota path milta hai, toh hum us neighbor ko priority queue mein add karte hain. Is process ko repeat karte hain jab tak saare reachable nodes ko process na kar lein. Ant mein, sabse lambe shortest path ko return karte hain.

### Code (Code)

```java
import java.util.*;

class Solution {
    public int networkDelayTime(int[][] times, int n, int k) {
        // Step 1: Adjacency list banayein
        // key: source node, value: list of [target node, time]
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int[] time : times) {
            int u = time[0]; // source
            int v = time[1]; // target
            int w = time[2]; // weight/time
            graph.computeIfAbsent(u, x -> new ArrayList<>()).add(new int[]{v, w});
        }

        // Step 2: distance array initialize karein
        // distance[i] stores min time to reach node i from source k
        int[] dist = new int[n + 1]; // Nodes are 1-indexed
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k] = 0; // Source node ka distance 0

        // Step 3: Min-Heap (PriorityQueue) use karein
        // Stores [current_distance, node_id]
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{0, k}); // Add source node

        // Step 4: Dijkstra's Algorithm Apply karein
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int d = current[0]; // distance to current node
            int u = current[1]; // current node

            // Agar yeh path pehle se hi lamba hai, toh skip karein
            if (d > dist[u]) {
                continue;
            }

            // Current node ke neighbors ko dekhein
            if (graph.containsKey(u)) { // Check if 'u' has outgoing edges
                for (int[] neighbor : graph.get(u)) {
                    int v = neighbor[0]; // Neighbor node
                    int weight = neighbor[1]; // Weight to neighbor

                    // Agar naya path chhota hai toh update karein aur PQ mein add karein
                    if (dist[u] + weight < dist[v]) {
                        dist[v] = dist[u] + weight;
                        pq.offer(new int[]{dist[v], v});
                    }
                }
            }
        }

        // Step 5 & 6: Result calculate karein aur unreachable nodes check karein
        int maxTime = 0;
        for (int i = 1; i <= n; i++) {
            if (dist[i] == Integer.MAX_VALUE) {
                return -1; // Koi node unreachable hai
            }
            maxTime = Math.max(maxTime, dist[i]);
        }

        return maxTime;
    }
}
```

-----

## 332\. Reconstruct Itinerary

Reconstruct Itinerary ek graph traversal problem hai jismein humein flight tickets ki list di gayi hai aur unse ek valid itinerary banana hai, lexicographically smallest order mein.

-----

### Description/Overview

Imagine karo ki tumhare paas flights ki ek list hai. Har flight mein **departure airport** aur **arrival airport** diya hua hai. Tumhein in saari flights ka use karke ek **itinerary (यात्रा सूची)** banani hai. Itinerary ko lexicographically (alphabetical order) sabse chhota hona chahiye, aur agar multiple valid itineraries hain, toh woh chunna hai jo sabse pehle "JFK" se start hota hai. Saari tickets ko exactly ek baar use karna hai.

Yeh problem **Eulerian Path** ya **Hierholzer's Algorithm** se solve ki ja sakti hai, jo ki graph traversal ki ek technique hai.

### Approach (Pahunch)

1.  **Graph Banayein:** Sabse pehle, `tickets` se ek **adjacency list** banayenge. Har airport (node) ke liye, usse jaane wali flights ki list store karenge. Important baat yeh hai ki yeh lists **lexicographical order** mein honi chahiye (yani alphabetically sorted), kyunki humein smallest itinerary chahiye. Iske liye hum `TreeMap` ya `PriorityQueue` ka use kar sakte hain `values` store karne ke liye, ya fir `ArrayList` ko sort kar sakte hain.
2.  **Eulerian Path Dhoondein (DFS ka use karke):** Hum ek **Depth-First Search (DFS)** approach use karenge. DFS ko "JFK" se shuru karenge.
      * DFS function ko current airport (`u`) pass karenge.
      * Jab tak `u` se koi outgoing flight hai:
          * **Lexicographically smallest (next) flight** choose karo. Iske liye `PriorityQueue` ka use karna sabse best hai, jo apne aap sorted rakhega.
          * Woh flight (edge) ko graph se remove kar do (kyunki humein har ticket ek hi baar use karni hai).
          * Us next airport par DFS call karo.
      * Jab current airport `u` se aur koi outgoing flights na hon (yani `u` ke adjacency list empty ho jaye), toh is airport ko **itinerary list mein add kar do**. Lekin yahan trick hai: hum airports ko **reverse order** mein add karenge. Jab DFS recursive calls return hone lagenge, tab hum airports ko list mein add karte jayenge. Isse final list correct order mein banegi.
3.  **Result Reverse Karein:** Jab DFS complete ho jaye, toh jo list bani hai use reverse kar do. Yahi hamara final itinerary hoga.

### Solution (Hal)

Yeh problem back-tracking aur greedy approach ka combination hai. Hum DFS ka use karte hain. Har step par, hum available tickets mein se lexicographically smallest destination choose karte hain. Jaise hi hum ek path explore karte hain aur us path mein koi aur nikalne wala edge nahi bachta, toh hum us airport ko itinerary mein add kar dete hain. Yeh essentially ek Eulerian path algorithm hai. Jab DFS stack unwinds hota hai, toh airports sahi order mein itinerary mein add hote jaate hain.

### Code (Code)

```java
import java.util.*;

class Solution {
    // Adjacency list: Map airport -> PriorityQueue of destinations
    // PriorityQueue ensures lexicographical order
    private Map<String, PriorityQueue<String>> graph;
    // To store the final itinerary in reverse order during DFS
    private List<String> itinerary;

    public List<String> findItinerary(List<List<String>> tickets) {
        graph = new HashMap<>();
        itinerary = new LinkedList<>(); // Use LinkedList for efficient adds to front/end

        // Step 1: Graph banayein
        for (List<String> ticket : tickets) {
            String from = ticket.get(0);
            String to = ticket.get(1);
            // Agar 'from' pehle se map mein nahi hai, toh ek naya PriorityQueue banayein
            graph.computeIfAbsent(from, k -> new PriorityQueue<>()).add(to);
        }

        // Step 2: Eulerian Path dhoondein (DFS ka use karke)
        dfs("JFK");

        // Step 3: Result reverse karein (optional, agar LinkedList mein addFirst() use kiya hai)
        // Agar add() use kiya hai aur end mein reverse kar rahe hain
        // Collections.reverse(itinerary);

        return itinerary;
    }

    private void dfs(String u) {
        // Jab tak current airport 'u' se koi outgoing flight hai
        while (graph.containsKey(u) && !graph.get(u).isEmpty()) {
            // Lexicographically smallest next destination
            String v = graph.get(u).poll(); // Remove the ticket from graph
            dfs(v); // Recursively call DFS for the next airport
        }
        // Jab 'u' se koi aur outgoing flight nahi bachi, toh 'u' ko itinerary mein add karo
        // LinkedList mein addFirst() se reverse order mein add hota jaata hai
        ((LinkedList<String>) itinerary).addFirst(u);
    }
}
```

-----

## 1584\. Min Cost to Connect All Points

Min Cost to Connect All Points ek Minimum Spanning Tree (MST) problem hai jismein humein saare points ko connect karne ka minimum cost nikalna hai. Cost Manhattan distance ke hisaab se calculate hota hai.

-----

### Description/Overview

Imagine karo ki tumhare paas 2D plane par kuch points diye hue hain. Tumhein in saare points ko is tarah connect karna hai ki **saare points ek doosre se connected hon** (directly ya indirectly), aur total **connection ka cost minimum** ho. Do points ke beech ka connection cost unke **Manhattan Distance** se define hota hai: $|x\_1 - x\_2| + |y\_1 - y\_2|$.

Yeh problem ek classic **Minimum Spanning Tree (MST)** problem hai. Isko solve karne ke liye hum **Prim's Algorithm** ya **Kruskal's Algorithm** ka use kar sakte hain.

### Approach (Pahunch)

Hum Prim's Algorithm ka use karenge, jo ki Dijkstra's Algorithm jaisa hi hai, bas thoda alag concept hai.

1.  **Edges Banayein aur Weights Calculate Karein:** Har do distinct points ke beech ka Manhattan distance calculate karke saare possible edges generate kar sakte hain. Lekin Prim's algorithm mein hum aisa nahi karte, hum dynamically edges explore karte hain.
2.  **Visited Array aur Min-Heap:**
      * Ek `visited` boolean array banayenge jo track karega ki kaun se points MST mein add ho chuke hain.
      * Ek `PriorityQueue` (min-heap) ka use karenge jo `[cost, point_index]` pairs store karega. `cost` current point ko MST se connect karne ka minimum cost hai.
3.  **Prim's Algorithm Apply Karein:**
      * Kisi bhi ek point se shuru karo (say, `points[0]`). Us point ko `visited` mark karo aur `PriorityQueue` mein `[0, 0]` add karo (cost 0, index 0).
      * Ek `totalCost` variable ko `0` se initialize karo.
      * Jab tak `PriorityQueue` empty nahi hota ya jab tak hum `n` points ko visit nahi kar lete:
          * `PriorityQueue` se sabse kam cost wala `[cost, point_index]` pair nikalo.
          * Agar yeh `point_index` pehle se `visited` hai, toh skip kar do.
          * Warna, `totalCost` mein `cost` add karo aur `point_index` ko `visited` mark karo. `connectedCount` ko increment karo.
          * Agar `connectedCount` `n` ke barabar ho gaya hai, toh humne saare points connect kar diye hain, loop break karo.
          * Ab, `point_index` se saare unvisited points tak ke edges ko dekho. Har `unvisited_neighbor_index` ke liye, `point_index` aur `unvisited_neighbor_index` ke beech ka Manhattan distance calculate karo aur `[distance, unvisited_neighbor_index]` ko `PriorityQueue` mein add karo.

### Solution (Hal)

Prim's algorithm ek greedy algorithm hai. Yeh ek starting point se shuru hota hai aur dheere-dheere MST ko expand karta hai. Har step par, yeh us edge ko choose karta hai jo current MST ko kisi naye (unvisited) point se sabse kam cost mein connect karta hai. Yeh process tab tak chalta hai jab tak saare points MST mein shamil nahi ho jaate.

### Code (Code)

```java
import java.util.*;

class Solution {
    public int minCostConnectPoints(int[][] points) {
        int n = points.length;
        if (n <= 1) {
            return 0; // Agar 0 ya 1 point hai toh cost 0
        }

        // boolean array to track visited points (part of MST)
        boolean[] visited = new boolean[n];
        // PriorityQueue to store [cost, point_index]
        // Sorts by cost (min-heap)
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        // Start Prim's algorithm from point 0
        pq.offer(new int[]{0, 0}); // Cost to connect point 0 to itself is 0
        int minCost = 0;
        int connectedPoints = 0;

        while (!pq.isEmpty() && connectedPoints < n) {
            int[] current = pq.poll();
            int cost = current[0];
            int idx = current[1];

            // Agar point pehle se visited hai, toh skip karein
            if (visited[idx]) {
                continue;
            }

            // Point ko MST mein add karein
            visited[idx] = true;
            minCost += cost;
            connectedPoints++;

            // Agar saare points connect ho gaye hain, toh loop break karein
            if (connectedPoints == n) {
                break;
            }

            // Current point 'idx' se saare unvisited points tak ke edges add karein
            for (int i = 0; i < n; i++) {
                if (!visited[i]) {
                    // Calculate Manhattan distance
                    int dist = Math.abs(points[idx][0] - points[i][0]) +
                               Math.abs(points[idx][1] - points[i][1]);
                    pq.offer(new int[]{dist, i});
                }
            }
        }

        return minCost;
    }
}
```

-----

## 778\. Swim in Rising Water

Swim in Rising Water ek shortest path (minimum of maximums) problem hai jise Dijkstra's algorithm ya Binary Search + BFS/DFS se solve kiya ja sakta hai.

-----

### Description/Overview

Imagine karo ki tum ek square grid par khade ho. Har cell `(r, c)` mein uski **height** (`grid[r][c]`) di hui hai. Baarish ho rahi hai aur paani ka level dheere-dheere badh raha hai. Tumhein `(0, 0)` se `(N-1, N-1)` tak jaana hai. Tum sirf tabhi move kar sakte ho jab current water level tumhare source aur destination cell dono ki heights se **zyada ya barabar** ho. Tumhein **minimum time 't'** find karna hai jismein tum `(0, 0)` se `(N-1, N-1)` tak pahunch sakte ho. Yahan 'time t' ka matlab hai ki water level 't' tak pahunch gaya hai.

Yeh problem ek variations hai shortest path problem ka, jismein hum minimum of maximums dhoondte hain. Isko solve karne ke liye **Dijkstra's Algorithm** with a slight modification ya **Binary Search over the answer combined with BFS/DFS** ka use kiya ja sakta hai. Dijkstra's approach zyada common hai.

### Approach (Pahunch) (Using Dijkstra's)

1.  **State aur Min-Heap:**
      * Dijkstra's mein hum `[distance, node]` store karte hain. Yahan `distance` ka matlab hai **maximum height encountered so far on the path**.
      * Ek `PriorityQueue` (min-heap) use karenge jo `[max_height, row, col]` tuples store karega. `max_height` current path ka sabse zyada height hai.
      * `visited` boolean array ya `dist` array use kar sakte hain taaki hum ek cell ko multiple times process na karein agar humein us tak pahunchne ka chhota `max_height` path mil chuka hai.
2.  **Initialization:**
      * Grid ki size `N` hai.
      * Ek `dist` 2D array banayenge, saari values `infinity` se initialize karenge.
      * `dist[0][0]` ko `grid[0][0]` se initialize karenge (starting point ka max\_height wahi cell ki height hai).
      * `PriorityQueue` mein `[grid[0][0], 0, 0]` add karenge.
3.  **Dijkstra's Algorithm Apply Karein:**
      * Jab tak `PriorityQueue` empty nahi hota:
          * Sabse chhota `max_height` wala cell `[h, r, c]` nikalo.
          * Agar `r, c` target cell `(N-1, N-1)` hai, toh `h` hi hamara answer hai, return kar do.
          * Agar `h` current `dist[r][c]` se zyada hai, toh skip kar do.
          * Current cell `(r, c)` ke saare 4 neighbors `(nr, nc)` ko dekho.
          * Har neighbor ke liye:
              * Naya `newMaxHeight` calculate karo: `Math.max(h, grid[nr][nc])`.
              * Agar `newMaxHeight` `dist[nr][nc]` se kam hai:
                  * `dist[nr][nc]` ko `newMaxHeight` se update karo.
                  * `[newMaxHeight, nr, nc]` ko `PriorityQueue` mein add karo.

### Solution (Hal)

Yeh Dijkstra's algorithm ka ek variation hai jahan edge weights fixed nahi hote, balki path ke maximum value par depend karte hain. Hum ek priority queue ka use karte hain jo hamesha us cell ko extract karta hai jise minimum `max_height` ke saath visit kiya ja sakta hai. Jaise hi hum target cell tak pahunchte hain, priority queue mein jo `max_height` store hota hai, wahi hamara answer hota hai.

### Code (Code)

```java
import java.util.*;

class Solution {
    public int swimInRisingWater(int[][] grid) {
        int n = grid.length;
        // dist[r][c] will store the minimum 't' (max height on path) to reach (r, c)
        int[][] dist = new int[n][n];
        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        // PriorityQueue stores [max_height_on_path, row, col]
        // Sorts by max_height_on_path (min-heap)
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        // Starting point (0, 0)
        dist[0][0] = grid[0][0]; // Max height to reach (0,0) is its own height
        pq.offer(new int[]{grid[0][0], 0, 0});

        // Directions for neighbors (up, right, down, left)
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int currentMaxHeight = current[0];
            int r = current[1];
            int c = current[2];

            // Agar target cell tak pahunch gaye, toh yahi answer hai
            if (r == n - 1 && c == n - 1) {
                return currentMaxHeight;
            }

            // Agar current path ka max height pehle se hi recorded max height se zyada hai, skip
            // Yeh visited array ki tarah kaam karta hai
            if (currentMaxHeight > dist[r][c]) {
                continue;
            }

            // Explore neighbors
            for (int[] dir : directions) {
                int nr = r + dir[0];
                int nc = c + dir[1];

                // Check boundary conditions
                if (nr >= 0 && nr < n && nc >= 0 && nc < n) {
                    // Calculate new max height for the path to (nr, nc)
                    int newMaxHeight = Math.max(currentMaxHeight, grid[nr][nc]);

                    // Agar naya path chhota hai toh update karein aur PQ mein add karein
                    if (newMaxHeight < dist[nr][nc]) {
                        dist[nr][nc] = newMaxHeight;
                        pq.offer(new int[]{newMaxHeight, nr, nc});
                    }
                }
            }
        }

        return -1; // Should not reach here if a path exists
    }
}
```

-----

## 269\. Alien Dictionary (Premium)

Alien Dictionary ek topological sort problem hai jismein humein words ke order se characters ke order ka pata lagana hota hai. Yeh LeetCode Premium problem hai, toh main sirf concept aur approach bata paunga, code shayad directly test na ho paye.

-----

### Description/Overview

Imagine karo ki tumhein kuch words diye gaye hain jo ek **alien language** ke hain. Yeh words lexicographically sorted hain us alien language ke alphabets ke hisaab se. Tumhein in words ka use karke **alien alphabet ka order** pata karna hai.

For example, agar words hain `["wrt", "wrf", "er", "ett", "rftt"]`:

  * `w` ke baad `e` aata hai (comparing `wrt` and `er`)
  * `r` ke baad `t` aata hai (comparing `wrt` and `wrf`)
  * `e` ke baad `r` aata hai (comparing `er` and `ett`)
  * `t` ke baad `f` aata hai (comparing `ett` and `rftt`)

Yeh problem **Topological Sort** ka ek classic example hai. Hum characters ke beech dependencies (precedence) find karte hain aur phir unhe sort karte hain.

### Approach (Pahunch)

1.  **Graph Banayein:**
      * **Nodes:** Alien language ke saare unique characters graph ke nodes honge.
      * **Edges:** Do adjacent words `word1` aur `word2` ko compare karke edges banayenge.
          * Iterate karo `word1` aur `word2` ke characters par jab tak mismatch na mile.
          * Jis pehle character par mismatch milega, say `c1` from `word1` aur `c2` from `word2`, toh iska matlab hai ki `c1` `c2` se pehle aata hai alien alphabet mein. Toh, ek directed edge `c1 -> c2` add karo.
          * Handle edge cases: Agar `word1` `word2` ka prefix hai (e.g., `"abc"`, `"ab"`) aur `word1.length() > word2.length()`, toh yeh invalid order hai aur empty string return karna chahiye.
2.  **Indegree Calculate Karein:** Har character (node) ke liye uski **indegree** (number of incoming edges) calculate karo.
3.  **Topological Sort (Kahn's Algorithm - BFS based):**
      * Ek `Queue` mein saare characters (nodes) add karo जिनकी indegree `0` है.
      * Ek `StringBuilder` ya `List` banayenge jismein hum sorted characters add karte jayenge.
      * Jab tak `Queue` empty nahi hota:
          * Ek character `c` `Queue` se nikalo aur use `StringBuilder` mein add karo.
          * `c` ke saare neighbors `n` ko dekho (yani `c -> n` edges).
          * `n` ki indegree `1` se kam karo.
          * Agar `n` ki indegree `0` ho jati hai, toh `n` ko `Queue` mein add karo.
4.  **Cycle Detection / Validation:**
      * Agar final sorted string ki length unique characters ki total count ke barabar nahi hai, toh iska matlab hai ki graph mein **cycle** hai. Ek cycle ka matlab hai ki valid ordering possible nahi hai, toh empty string return karo. (e.g., `a -> b` aur `b -> a`)

### Solution (Hal)

Alien dictionary problem ko solve karne ke liye, hum pehle diye gaye words se character dependencies ka ek directed graph banate hain. Har `c1 -> c2` edge ka matlab hai ki `c1` alien alphabet mein `c2` se pehle aata hai. Phir, hum is graph par topological sort (Kahn's algorithm ya DFS based) apply karte hain. Topological sort allows us to find a linear ordering of vertices for a directed acyclic graph (DAG). Agar graph cyclic hai, toh koi valid order exist nahi karta.

### Code (Code)

Yeh ek premium question hai, toh code ko detail mein discuss karenge, par direct copy-paste test environment mein nahi hoga.

```java
import java.util.*;

class Solution {
    public String alienOrder(String[] words) {
        // Step 1: Graph aur indegree map banayein
        Map<Character, List<Character>> adj = new HashMap<>();
        Map<Character, Integer> inDegree = new HashMap<>();

        // Initialize all unique characters with 0 indegree
        for (String word : words) {
            for (char c : word.toCharArray()) {
                inDegree.put(c, 0);
                adj.put(c, new ArrayList<>()); // Also initialize adjacency list for all characters
            }
        }

        // Build the graph and calculate indegrees
        for (int i = 0; i < words.length - 1; i++) {
            String w1 = words[i];
            String w2 = words[i + 1];

            // Handle invalid case: "abc", "ab" -> means 'c' should come before nothing, which is invalid
            if (w1.length() > w2.length() && w1.startsWith(w2)) {
                return "";
            }

            int len = Math.min(w1.length(), w2.length());
            boolean foundDiff = false;
            for (int j = 0; j < len; j++) {
                char char1 = w1.charAt(j);
                char char2 = w2.charAt(j);
                if (char1 != char2) {
                    // Add edge char1 -> char2
                    adj.get(char1).add(char2);
                    inDegree.put(char2, inDegree.get(char2) + 1);
                    foundDiff = true;
                    break; // Only the first differing character matters
                }
            }
        }

        // Step 2 & 3: Topological Sort (Kahn's Algorithm)
        Queue<Character> q = new LinkedList<>();
        for (char c : inDegree.keySet()) {
            if (inDegree.get(c) == 0) {
                q.offer(c);
            }
        }

        StringBuilder result = new StringBuilder();
        int countOfProcessedChars = 0; // To detect cycles

        while (!q.isEmpty()) {
            char u = q.poll();
            result.append(u);
            countOfProcessedChars++;

            // For each neighbor v of u
            for (char v : adj.get(u)) {
                inDegree.put(v, inDegree.get(v) - 1);
                if (inDegree.get(v) == 0) {
                    q.offer(v);
                }
            }
        }

        // Step 4: Cycle detection
        if (result.length() != inDegree.size()) { // If not all characters are processed, there's a cycle
            return "";
        }

        return result.toString();
    }
}
```

-----

## 787\. Cheapest Flights Within K Stops

Cheapest Flights Within K Stops ek shortest path problem hai jise Bellman-Ford algorithm ya Modified Dijkstra's algorithm se solve kiya ja sakta hai.

-----

### Description/Overview

Imagine karo ki tumhein ek city se doosri city tak flight leni hai. Tumhein flights ki list di gayi hai, jismein **source city**, **destination city**, aur **flight ka cost** hai. Tumhein ek **starting city (`src`)**, ek **ending city (`dst`)**, aur **maximum stops (`k`)** diye jayenge. Tumhein `src` se `dst` tak pahunchne ka **sabse sasta tareeka** find karna hai, lekin tum `k` se zyada stops nahi le sakte. Agar koi valid path exist nahi karta, toh `-1` return karna hai.

Yeh problem **Shortest Path** ka ek variation hai, jismein stops par restriction hai. Isko solve karne ke liye hum **Bellman-Ford algorithm** (ya uski BFS-like iterative approach) ya **Modified Dijkstra's Algorithm** ka use kar sakte hain. Bellman-Ford yahan zyada suitable hai kyunki K stops par restriction hai.

### Approach (Pahunch) (Using Bellman-Ford like iterative DP)

1.  **Distance Array Initialize Karein:**
      * Ek `dist` array banayenge `n` size ka, jismein har city tak pahunchne ka minimum cost store hoga. Saari values `infinity` se initialize karenge (`Integer.MAX_VALUE`), aur `dist[src]` ko `0` set karenge.
2.  **Iterative Relaxation (K+1 iterations):**
      * Hum `k+1` baar iterate karenge (max `k` stops ka matlab hai `k+1` segments/flights).
      * Har iteration में, hum `dist` array ki ek copy बनाएंगे (`tempDist`) taaki current iteration ke updates next iteration ke calculations ko affect na karein immediately.
      * Saari flights (`flights` array) par iterate karenge:
          * Har flight `(u, v, price)` ke liye:
              * Agar `dist[u]` `infinity` nahi hai (yani `u` tak pahuncha ja sakta hai):
                  * Check karo: `dist[u] + price < tempDist[v]`.
                  * Agar haan, toh `tempDist[v]` ko `dist[u] + price` se update karo.
      * Iteration ke end mein, `dist` ko `tempDist` se update kar do.
3.  **Result:**
      * `k+1` iterations ke baad, `dist[dst]` mein `dst` tak pahunchne ka minimum cost store hoga `k` stops ke andar.
      * Agar `dist[dst]` abhi bhi `infinity` hai, toh iska matlab hai ki `dst` tak pahuncha nahi ja sakta `k` stops ke andar, toh `-1` return karo. Warna, `dist[dst]` return karo.

### Solution (Hal)

Yeh Bellman-Ford algorithm ka ek direct application hai. Hum costs ko `k` steps tak "relax" karte hain. Har step `i` (representing `i` flights taken) par, hum saari flights ko check karte hain aur un destinations ke costs ko update karte hain jinhe `i` flights mein reach kiya ja sakta hai. Hum `k+1` iterations karte hain, jo `k` stops ya `k+1` edges ko cover karta hai. Temporary array ka use important hai taaki current iteration ke updates agle iteration ke liye valid hon, na ki current iteration mein hi.

### Code (Code)

```java
import java.util.*;

class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        // Step 1: Distance array initialize karein
        // dist[i] will store the minimum cost to reach city i
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0; // Source city ka cost 0

        // Iterate k+1 times (for k stops, we need k+1 flights/edges)
        // K stops means maximum k edges
        // 0 stops -> 1 edge (direct flight)
        // 1 stop -> 2 edges
        // k stops -> k+1 edges
        for (int i = 0; i <= k; i++) {
            // Create a temporary array to store distances for current iteration
            // This is crucial to avoid propagating updates within the same "stop count"
            int[] tempDist = Arrays.copyOf(dist, n); // Copy current state of distances

            // Relax all edges (flights)
            for (int[] flight : flights) {
                int u = flight[0]; // Source city of flight
                int v = flight[1]; // Destination city of flight
                int price = flight[2]; // Price of flight

                // Agar source city 'u' tak pahuncha ja sakta hai (cost finite hai)
                // aur naya path 'v' tak pahle se sasta hai
                if (dist[u] != Integer.MAX_VALUE) {
                    // Update tempDist[v] if a cheaper path is found
                    // from u to v
                    if (dist[u] + price < tempDist[v]) {
                        tempDist[v] = dist[u] + price;
                    }
                }
            }
            // Update the main dist array with the results from this iteration
            dist = tempDist;
        }

        // Step 3: Result
        // Agar dst tak pahuncha ja sakta hai toh dist[dst] return karein, warna -1
        return dist[dst] == Integer.MAX_VALUE ? -1 : dist[dst];
    }
}
```
