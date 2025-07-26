# Dijkstra's Algorithm Kya Hai?

Dijkstra's Algorithm ek **greedy algorithm** hai jo ek single source node se graph ke baaki saare nodes tak ka **shortest path** dhundhne ke liye use hota hai.

**Imagine kijiye:** Aap Delhi mein hain aur aapko Mumbai, Bangalore, Kolkata, Chennai jaana hai. Har shehar ke beech ka rasta (edge) aur uski cost (weight) di gayi hai. Dijkstra aapko Delhi se har doosre shehar tak pahunchne ka sabse sasta (shortest) rasta batayega.

**Key Idea:**
Yeh algorithm step-by-step nearest, unvisited node ko select karta hai aur uske neighbours ke distances ko update karta rehta hai. Is process mein, ek **Priority Queue** ka use is liye karte hain taaki next shortest distance wale node ko efficiently pick kiya ja sake.

-----

### Algorithm Ke Steps (Using Priority Queue)

Algorithms mein kuch cheezein hum maintain karte hain:

1.  **`dist` array/map:** Har node tak ka current shortest distance store karta hai (initially `Infinity` sabke liye except source).
2.  **`pq` (Priority Queue):** Nodes ko unke current shortest distance ke hisaab se store karta hai. Priority Queue hamesha sabse kam distance wale node ko top par rakhti hai.
3.  **`visited` set/array:** Un nodes ko track karta hai jinhe humne already process kar liya hai aur unka shortest path final ho gaya hai.

**Steps:**

1.  **Initialization:**

      * Ek `dist` map (ya array) banao. **Source node** ka distance `0` set karo (`dist[source] = 0`), aur baaki saare nodes ka distance `Infinity` set karo.
      * Ek **Priority Queue** (`pq`) banao. Ismein (distance, node) pairs store honge, aur yeh distance ke basis par sort karegi (min-heap).
      * `pq` mein `(0, source_node)` ko add karo.
      * Ek `visited` set banao, jo initially empty hoga.

2.  **Processing (Loop):**

      * Loop tab tak chalao jab tak `pq` empty na ho jaaye.
      * Har iteration mein, `pq` se woh pair nikalo jiska **distance sabse kam** ho (yaani `(current_dist, u)`).
      * Agar `u` node **already `visited`** hai, toh is iteration ko skip karo (kyunki iska shortest path already final ho chuka hai).
      * `u` ko `visited` set mein add karo.

3.  **Explore Neighbors:**

      * `u` node ke har **neighbor `v`** aur unke beech ke **edge weight `w`** ke liye:
          * Check karo: `if (dist[u] + w < dist[v])`
              * Iska matlab hai ki `u` ke through `v` tak pahunchne ka naya rasta **chhota** hai current `dist[v]` se.
              * Agar haan, toh `dist[v]` ko `dist[u] + w` se update karo.
              * Aur `pq` mein `(dist[v], v)` ko add karo (ya update karo agar `v` already `pq` mein hai - Java ki `PriorityQueue` mein update functionality direct nahi hoti, isliye hum simply naya entry add kar dete hain. Puraani entry apne aap ignore ho jayegi jab woh `visited` ho jayega).

4.  **Result:**

      * Jab `pq` empty ho jayegi, toh `dist` map mein source node se har doosre node tak ka **shortest distance** store hoga.

-----

### Example (Code Structure in Java)

Chalo ek Java code structure dekhte hain ki yeh kaise implement hoga:

```java
import java.util.*;

class Edge {
    int to;    // Kahan jaana hai
    int weight; // Kitni cost lagegi

    public Edge(int to, int weight) {
        this.to = to;
        this.weight = weight;
    }
}

// Pair class for Priority Queue: (distance, node)
class NodeDistance implements Comparable<NodeDistance> {
    int node;
    int distance;

    public NodeDistance(int node, int distance) {
        this.node = node;
        this.distance = distance;
    }

    // Priority Queue minimum distance ke basis par sort karegi
    @Override
    public int compareTo(NodeDistance other) {
        return Integer.compare(this.distance, other.distance);
    }
}

public class DijkstraAlgorithm {

    // Adjacency list representation of the graph
    // graph[u] will contain a list of Edge objects from u
    private List<List<Edge>> graph;
    private int numNodes;

    public DijkstraAlgorithm(int numNodes) {
        this.numNodes = numNodes;
        graph = new ArrayList<>(numNodes);
        for (int i = 0; i < numNodes; i++) {
            graph.add(new ArrayList<>());
        }
    }

    public void addEdge(int from, int to, int weight) {
        // Directed graph ke liye, sirf ek taraf add karo
        // Undirected ke liye, dono taraf add karna padega
        graph.get(from).add(new Edge(to, weight));
    }

    public int[] dijkstra(int startNode) {
        // 1. Initialization
        int[] dist = new int[numNodes];
        Arrays.fill(dist, Integer.MAX_VALUE); // Sab distances ko Infinity set karo
        dist[startNode] = 0; // Source node ka distance 0

        // Priority Queue (distance, node) pairs store karegi
        // Ye sabse kam distance wale node ko pehle nikalti hai
        PriorityQueue<NodeDistance> pq = new PriorityQueue<>();
        pq.add(new NodeDistance(startNode, 0));

        // Visited set to keep track of processed nodes
        Set<Integer> visited = new HashSet<>();

        // 2. Processing Loop
        while (!pq.isEmpty()) {
            NodeDistance current = pq.poll(); // Sabse kam distance wala node nikalo
            int u = current.node;
            int currentDist = current.distance;

            // Agar yeh node already visited hai, ya agar currentDist
            // humare paas pehle se stored shortest dist se zyada hai, toh skip karo.
            // Ye condition redundant entries ko handle karti hai Priority Queue mein.
            if (visited.contains(u) || currentDist > dist[u]) {
                continue;
            }

            visited.add(u); // Node ko visited mark karo

            // 3. Explore Neighbors
            for (Edge edge : graph.get(u)) {
                int v = edge.to;
                int weight = edge.weight;

                // Agar v visited nahi hai aur naya rasta chhota hai
                if (!visited.contains(v) && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight; // Distance update karo
                    pq.add(new NodeDistance(v, dist[v])); // PQ mein add karo
                }
            }
        }

        return dist; // Sab nodes tak ka shortest distance array
    }

    public static void main(String[] args) {
        // Example Graph: 0-based indexing (0 to 4)
        // 0 --1--> 1 --1--> 2
        // |        |
        // 4        1
        // |        |
        // 3 --1--> 4
        // 0--1-->2
        //
        // Node count: 5 (0, 1, 2, 3, 4)
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(5);
        dijkstra.addEdge(0, 1, 1);
        dijkstra.addEdge(0, 2, 4); // Added 0 to 2 for better example
        dijkstra.addEdge(1, 2, 1);
        dijkstra.addEdge(1, 3, 2);
        dijkstra.addEdge(2, 3, 1);
        dijkstra.addEdge(3, 4, 1);

        int startNode = 0;
        int[] shortestDistances = dijkstra.dijkstra(startNode);

        System.out.println("Shortest distances from Node " + startNode + ":");
        for (int i = 0; i < shortestDistances.length; i++) {
            System.out.println("Node " + i + ": " + shortestDistances[i]);
        }
        /* Expected Output (roughly):
        Shortest distances from Node 0:
        Node 0: 0
        Node 1: 1
        Node 2: 2 (0->1->2)
        Node 3: 3 (0->1->2->3) or (0->1->3)
        Node 4: 4 (0->1->2->3->4) or (0->1->3->4)
        */
    }
}
```

-----

### Priority Queue Ka Role Kyun Zaroori Hai?

  * **Efficiency:** Priority Queue ensure karti hai ki hum hamesha us node ko pick karein jiska source se current shortest distance sabse kam hai (among all unvisited nodes). Isse hum correct shortest path ki taraf badhte hain aur unnecessary calculations se bachte hain.
  * **Optimization:** Agar hum plain queue (BFS ki tarah) ya stack (DFS ki tarah) use karte, toh humein har step par saare unvisited nodes ke distances ko scan karna padta, jo bahut inefficient hota. Priority Queue is selection process ko `O(log V)` (V nodes hain) mein karti hai.

-----

### Time Complexity

  * **V:** Number of Vertices (Nodes)
  * **E:** Number of Edges
  * **Using Adjacency List + Binary Heap (Priority Queue):** `O((V + E) log V)`
      * Har vertex ko `pq` mein `log V` time lagta hai add/remove karne mein.
      * Har edge ko ek baar process karte hain, aur agar distance update hota hai, toh `pq` mein `log V` operation hota hai.
      * Worst case mein, `E` edges update ho sakte hain, toh `E log V`. Plus har `V` vertex ko nikalne ke liye `V log V`.

-----

### Limitations

  * **Non-negative edge weights:** Dijkstra's Algorithm sirf tabhi kaam karta hai jab graph mein koi **negative edge weights** na ho. Agar negative weights hain, toh **Bellman-Ford Algorithm** ya **SPFA Algorithm** ka use hota hai. Negative cycles ke case mein, shortest path undefined ho jaata hai.

Dijkstra's algorithm kaafi popular hai aur networks (routing), mapping applications (Google Maps, GPS), aur resource allocation jaise kayi real-world scenarios mein use hota hai.
-----

## Dijkstra's Algorithm Interview Questions (Java)

Dijkstra's Algorithm ek **single-source shortest path** algorithm hai jo non-negative edge weights wale graphs mein shortest path find karta hai. Interviews mein yeh kafi popular topic hai kyuki yeh graphs, priority queues aur greedy approach ko test karta hai. Yahan kuch common interview questions aur unke Java solutions hain:

-----

## Question 1: Shortest Path in a Graph

**Problem:** Aapko ek directed ya undirected graph diya gaya hai jisme non-negative edge weights hain. Aapko source node se baaki saare nodes tak ka shortest path find karna hai.

**Input:**

  * `V`: Number of vertices.
  * `adj`: Adjacency list, jahan `adj[u]` mein `Pair(v, weight)` hoga, matlab `u` se `v` tak `weight` ka edge hai.
  * `S`: Source node.

**Output:**

  * Ek array ya list jisme source node se har doosre node tak ka shortest distance ho. Agar koi node unreachable hai, toh uske liye `Integer.MAX_VALUE` ya `-1` store kar sakte hain.

**Solution Approach (Dijkstra's Algorithm):**

1.  **Distance Array:** `dist[]` naam ka ek array banao aur usme saare distances ko `Integer.MAX_VALUE` se initialize karo. `dist[S]` ko `0` set karo.
2.  **Priority Queue:** Ek `PriorityQueue` banao jo `Pair(distance, node)` store karegi. Yeh pair `distance` ke basis par sorted hoga, smallest distance sabse pehle. Isme `Pair(0, S)` add karo.
3.  **Traversal:** Jab tak priority queue empty na ho jaye, yeh steps follow karo:
      * Queue se `(d, u)` extract karo jisme `d` current shortest distance hai `u` tak.
      * Agar `d > dist[u]` hai, toh isko ignore karo (yeh already visited aur better path mil chuka hai).
      * `u` ke saare neighbours `v` par iterate karo. Agar `dist[u] + weight(u, v) < dist[v]` hai, toh `dist[v]` ko update karo aur `Pair(dist[v], v)` ko priority queue mein add karo.

**Java Code:**

```java
import java.util.*;

// Helper class to store pair of distance and node
class Pair {
    int distance;
    int node;

    public Pair(int distance, int node) {
        this.distance = distance;
        this.node = node;
    }
}

public class DijkstraShortestPath {

    public int[] dijkstra(int V, ArrayList<ArrayList<Pair>> adj, int S) {
        // dist[i] will store the shortest distance from source S to node i
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE); // Initialize all distances to infinity

        // PriorityQueue stores pairs of (distance, node) and sorts by distance
        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> a.distance - b.distance);

        // Distance to source node itself is 0
        dist[S] = 0;
        pq.add(new Pair(0, S)); // Add source node with distance 0 to PQ

        while (!pq.isEmpty()) {
            Pair current = pq.poll();
            int d = current.distance; // Current shortest distance to 'u'
            int u = current.node;     // Current node 'u'

            // If we found a shorter path to 'u' already, ignore this one
            if (d > dist[u]) {
                continue;
            }

            // Iterate over all neighbors of 'u'
            for (Pair neighbor : adj.get(u)) {
                int v = neighbor.node;
                int weight = neighbor.distance; // In our Pair, 'distance' is used for weight here

                // If a shorter path to 'v' is found through 'u'
                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    pq.add(new Pair(dist[v], v)); // Add updated path to PQ
                }
            }
        }
        return dist;
    }

    
```

-----

## Question 2: Path Reconstruction

**Problem:** Shortest distance find karne ke alawa, aapko actual shortest path (nodes ka sequence) bhi print karna hai source node se target node tak.

**Input:** Same as Question 1, plus `target` node.

**Output:**

  * Source se target tak ka shortest distance.
  * Path (ek list of nodes).

**Solution Approach:**

Dijkstra ke basic algorithm mein ek extra array maintain karenge, let's say `parent[]`, jo har node ke liye us node se pehle wala node store karega shortest path mein. Jab `dist[u] + weight(u, v) < dist[v]` condition satisfy hoti hai aur `dist[v]` update hota hai, tab `parent[v] = u` set kar do. Path reconstruct karne ke liye, `target` node se `parent[]` array ko backwards traverse karo jab tak source node tak na pahunch jao.

**Java Code:**

```java
import java.util.*;

// Reuse Pair class from previous example

public class DijkstraPathReconstruction {

    public List<Integer> getShortestPath(int V, ArrayList<ArrayList<Pair>> adj, int S, int target) {
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);

        int[] parent = new int[V]; // To store the parent node in the shortest path
        Arrays.fill(parent, -1); // Initialize parents to -1

        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> a.distance - b.distance);

        dist[S] = 0;
        pq.add(new Pair(0, S));

        while (!pq.isEmpty()) {
            Pair current = pq.poll();
            int d = current.distance;
            int u = current.node;

            if (d > dist[u]) {
                continue;
            }

            // Optimization: If target found and extracted from PQ, we can break
            // This is valid if we only need path to target, not all nodes
            // if (u == target) {
            //     break;
            // }

            for (Pair neighbor : adj.get(u)) {
                int v = neighbor.node;
                int weight = neighbor.distance;

                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    parent[v] = u; // Update parent
                    pq.add(new Pair(dist[v], v));
                }
            }
        }

        // Reconstruct the path
        List<Integer> path = new ArrayList<>();
        if (dist[target] == Integer.MAX_VALUE) {
            return new ArrayList<>(); // Target is unreachable
        }

        int curr = target;
        while (curr != -1) {
            path.add(curr);
            curr = parent[curr];
        }
        Collections.reverse(path); // Path will be reversed, so reverse it back

        return path;
    }

    public static void main(String[] args) {
        DijkstraPathReconstruction solver = new DijkstraPathReconstruction();

        int V = 6;
        ArrayList<ArrayList<Pair>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
        adj.get(0).add(new Pair(1, 4));
        adj.get(0).add(new Pair(2, 1));
        adj.get(1).add(new Pair(3, 1));
        adj.get(2).add(new Pair(1, 2));
        adj.get(2).add(new Pair(4, 5));
        adj.get(3).add(new Pair(4, 3));
        adj.get(4).add(new Pair(5, 1));

        int source = 0;
        int target = 5;

        List<Integer> path = solver.getShortestPath(V, adj, source, target);
        int[] distances = new DijkstraShortestPath().dijkstra(V, adj, source); // Get all distances to verify

        if (distances[target] == Integer.MAX_VALUE) {
            System.out.println("Path from " + source + " to " + target + " is unreachable.");
        } else {
            System.out.println("Shortest distance from " + source + " to " + target + ": " + distances[target]);
            System.out.println("Shortest path: " + path); // Expected: [0, 2, 1, 3, 4, 5]
        }
        
        // Test unreachable target
        int unreachableTarget = 7; // Assuming V is 6, node 7 is out of bounds or not connected
        List<Integer> unreachablePath = solver.getShortestPath(V, adj, source, 7);
        if (unreachablePath.isEmpty()) {
             System.out.println("\nPath from " + source + " to " + unreachableTarget + " is unreachable (as expected for non-existent node).");
        }
    }
}
```

-----

## Question 3: Minimum Cost Path in a Grid (Variations)

**Problem:** Aapko ek grid (matrix) diya gaya hai jisme har cell ka ek cost hai. Aapko top-left cell se bottom-right cell tak minimum cost path find karna hai. Aap sirf right, left, up, down move kar sakte ho.

**Input:**

  * `grid`: `m x n` integer matrix, jahan `grid[i][j]` cell `(i, j)` mein move karne ka cost hai.

**Output:**

  * Minimum total cost to reach bottom-right.

**Solution Approach:**

Is problem ko graph problem mein convert kiya ja sakta hai jahan har grid cell ek node hai aur adjacent cells ke beech edges hain jinka weight destination cell ka cost hai. Dijkstra's Algorithm yahan effectively work karega.

**Java Code:**

```java
import java.util.*;

// Reuse Pair class, or create a new one for (cost, row, col)
class Cell implements Comparable<Cell> {
    int cost;
    int row;
    int col;

    public Cell(int cost, int row, int col) {
        this.cost = cost;
        this.row = row;
        this.col = col;
    }

    @Override
    public int compareTo(Cell other) {
        return this.cost - other.cost;
    }
}

public class MinCostPathGrid {

    public int minCostPath(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        // dist[i][j] will store the minimum cost to reach cell (i, j)
        int[][] dist = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }

        PriorityQueue<Cell> pq = new PriorityQueue<>();

        // Starting point (top-left cell) cost is grid[0][0]
        dist[0][0] = grid[0][0];
        pq.add(new Cell(grid[0][0], 0, 0));

        // Possible moves: right, left, down, up
        int[] dr = {0, 0, 1, -1};
        int[] dc = {1, -1, 0, 0};

        while (!pq.isEmpty()) {
            Cell current = pq.poll();
            int currentCost = current.cost;
            int r = current.row;
            int c = current.col;

            // If we found a shorter path to this cell already, ignore this one
            if (currentCost > dist[r][c]) {
                continue;
            }

            // If we reached the destination, return its cost
            if (r == rows - 1 && c == cols - 1) {
                return currentCost;
            }

            // Explore neighbors
            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                // Check boundary conditions
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                    int newCost = currentCost + grid[nr][nc];
                    if (newCost < dist[nr][nc]) {
                        dist[nr][nc] = newCost;
                        pq.add(new Cell(newCost, nr, nc));
                    }
                }
            }
        }

        // Should not reach here if a path exists
        return -1; // Or throw an exception if path is guaranteed to exist
    }

    public static void main(String[] args) {
        MinCostPathGrid solver = new MinCostPathGrid();

        int[][] grid1 = {
            {1, 3, 1},
            {1, 5, 1},
            {4, 2, 1}
        };
        // Path: (0,0) -> (1,0) -> (2,0) -> (2,1) -> (2,2) with cost 1+1+4+2+1 = 9
        // Or (0,0) -> (0,1) -> (0,2) -> (1,2) -> (2,2) with cost 1+3+1+1+1 = 7
        // Or (0,0) -> (0,1) -> (1,1) -> (1,2) -> (2,2) with cost 1+3+5+1+1 = 11 (wrong for this example path)
        // Correct path (0,0) -> (0,1) -> (1,1) -> (1,2) -> (2,2) (1 + 3 + 5 + 1 + 1 = 11 is not the shortest for this path)
        // Path (0,0) -> (0,1) -> (0,2) -> (1,2) -> (2,2) has cost 1+3+1+1+1 = 7.
        // Path (0,0) -> (1,0) -> (1,1) -> (1,2) -> (2,2) has cost 1+1+5+1+1 = 9.
        System.out.println("Min cost for grid 1: " + solver.minCostPath(grid1)); // Expected: 7

        int[][] grid2 = {
            {1, 2, 3},
            {4, 5, 6}
        };
        // Path (0,0) -> (0,1) -> (0,2) -> (1,2) = 1+2+3+6 = 12
        // Path (0,0) -> (1,0) -> (1,1) -> (1,2) = 1+4+5+6 = 16
        System.out.println("Min cost for grid 2: " + solver.minCostPath(grid2)); // Expected: 12
    }
}
```

-----

### Key Takeaways for Dijkstra's Algorithm:

  * **Non-negative edge weights:** Dijkstra's sirf non-negative weights par kaam karta hai. Agar negative weights hon, toh Bellman-Ford algorithm ka use karna padega.
  * **Priority Queue:** `PriorityQueue` is crucial for efficiency (`O(E log V)` or `O(E + V log V)` depending on PQ implementation) because it always gives the unvisited node with the smallest known distance.
  * **Greedy Approach:** Har step par, algorithm locally optimal choice (smallest distance node) choose karta hai, jo eventually globally optimal solution (shortest path) deta hai.
  * **Graph Representation:** Adjacency list use karna recommended hai graphs ko represent karne ke liye, especially for sparse graphs (`E << V^2`).
  * **Variations:** Dijkstra's ko kayi variations mein apply kiya ja sakta hai, jaise grid problems, network routing, etc., jahan shortest/minimum path/cost find karna ho.
