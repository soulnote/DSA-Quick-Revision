# Union-Find (DSU) Kya Hai?

Union-Find ek data structure hai jo elements ke disjoint (non-overlapping) sets ke collection ko manage karta hai. Ismein do primary operations hote hain:

1.  **`find` (ya `findSet` / `getRoot`):** Check karta hai ki ek given element kis set ka hissa hai. Ye us set ke "representative" (ya "root") ko return karta hai.
2.  **`union` (ya `unionSets` / `merge`):** Do alag-alag sets ko ek single set mein merge karta hai.

**Example:** Socho aapke paas kuch log hain, aur aapko unke friendship groups track karne hain. Jab koi naye dost bante hain, toh unke groups merge ho jaate hain. `find` se aap pata kar sakte ho ki kaun kiske group mein hai, aur `union` se aap groups ko jod sakte ho.

### Union-Find Ka Structure:

DSU ko represent karne ke liye usually ek array (`parent[]`) ka use hota hai.

  * `parent[i]` store karta hai `i` ke parent ka index.
  * Agar `parent[i] == i` hai, toh `i` apne set ka **root** (representative) hai.

Initial mein, har element apne aap ka parent hota hai, yani har element ek alag set mein hota hai.

### Core Operations with Optimizations:

DSU ki efficiency badhane ke liye do optimizations bohot important hain:

1.  **Path Compression (for `find` operation):**

      * Jab `find(i)` call karte hain, toh hum `i` ke parent chain ko follow karte hue root tak pahunchte hain.
      * Path compression optimization mein, root tak pahunchne ke baad, hum path ke har node ko directly root ka child bana dete hain. Isse future `find` operations faster ho jaate hain.

2.  **Union by Rank / Size (for `union` operation):**

      * Jab do sets ko merge karte hain (say, `union(x, y)`), toh do roots `rootX` aur `rootY` milte hain.
      * Instead of randomly making one root child of another, hum chhote tree ko bade tree ke neeche jodte hain.
      * **Union by Rank:** `rank[]` array maintain karte hain jo tree ki approximate height store karta hai. Chhote rank wale tree ko bade rank wale tree ka child banate hain. Agar ranks same hain, toh kisi ek ko bana do aur uska rank badha do.
      * **Union by Size:** `size[]` array maintain karte hain jo set mein elements ki sankhya store karta hai. Chhote size wale tree ko bade size wale tree ka child banate hain.

Ye optimizations `find` aur `union` operations ko almost constant time (`O(α(N))`, jahan `α` inverse Ackermann function hai, jo practically 4 se kam hota hai for any realistic N) mein perform karte hain.

### Java Implementation Example:

```java
import java.util.Arrays;

public class DisjointSetUnion {

    private int[] parent; // parent[i] stores the parent of element i
    private int[] rank;   // rank[i] stores the approximate height of the tree rooted at i
                          // (used for Union by Rank optimization)
    private int numSets;  // To keep track of the number of disjoint sets

    /**
     * Constructor to initialize N disjoint sets.
     * Each element is initially in its own set.
     *
     * @param n The total number of elements (0 to n-1).
     */
    public DisjointSetUnion(int n) {
        parent = new int[n];
        rank = new int[n];
        numSets = n;

        // Initially, each element is its own parent (root of its own set)
        // and has a rank of 0.
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    /**
     * Finds the representative (root) of the set containing element 'i'.
     * Applies Path Compression optimization.
     *
     * @param i The element whose set representative is to be found.
     * @return The representative (root) of the set containing 'i'.
     */
    public int find(int i) {
        // If i is the parent of itself, it's the root of the set
        if (parent[i] == i) {
            return i;
        }

        // Path Compression: Recursively find the root and then
        // make the current node's parent directly point to the root.
        parent[i] = find(parent[i]);
        return parent[i];
    }

    /**
     * Unites (merges) the sets containing elements 'i' and 'j'.
     * Applies Union by Rank optimization.
     *
     * @param i One element from the first set.
     * @param j One element from the second set.
     * @return True if the sets were actually merged (i.e., they were different),
     * false otherwise (i.e., they were already in the same set).
     */
    public boolean union(int i, int j) {
        int rootI = find(i); // Find representative of i's set
        int rootJ = find(j); // Find representative of j's set

        // If they are already in the same set, do nothing
        if (rootI == rootJ) {
            return false;
        }

        // Union by Rank: Attach smaller rank tree under root of higher rank tree
        if (rank[rootI] < rank[rootJ]) {
            parent[rootI] = rootJ;
        } else if (rank[rootJ] < rank[rootI]) {
            parent[rootJ] = rootI;
        } else {
            // If ranks are same, make one root parent of other and increment its rank
            parent[rootJ] = rootI;
            rank[rootI]++;
        }
        numSets--; // Decrease the number of disjoint sets after merging
        return true;
    }

    /**
     * Returns the total number of disjoint sets currently.
     * @return The number of disjoint sets.
     */
    public int getNumSets() {
        return numSets;
    }

    public static void main(String[] args) {
        // Create a DSU with 5 elements (0, 1, 2, 3, 4)
        DisjointSetUnion dsu = new DisjointSetUnion(5);

        System.out.println("Initial number of sets: " + dsu.getNumSets()); // Expected: 5

        // Check initial parent array (each is its own parent)
        System.out.println("Initial parents: " + Arrays.toString(dsu.parent)); // Expected: [0, 1, 2, 3, 4]

        // Union(0, 1): Merges sets of 0 and 1
        System.out.println("\nUnion(0, 1): " + dsu.union(0, 1)); // Expected: true
        System.out.println("Root of 0: " + dsu.find(0)); // Expected: 0 or 1 (depending on rank)
        System.out.println("Root of 1: " + dsu.find(1)); // Expected: same as root of 0
        System.out.println("Number of sets after Union(0, 1): " + dsu.getNumSets()); // Expected: 4
        System.out.println("Parents after Union(0, 1): " + Arrays.toString(dsu.parent)); // parent[0] or parent[1] changed

        // Union(2, 3): Merges sets of 2 and 3
        System.out.println("\nUnion(2, 3): " + dsu.union(2, 3)); // Expected: true
        System.out.println("Root of 2: " + dsu.find(2));
        System.out.println("Root of 3: " + dsu.find(3));
        System.out.println("Number of sets after Union(2, 3): " + dsu.getNumSets()); // Expected: 3

        // Union(0, 2): Merges the set containing 0 and 1 with the set containing 2 and 3
        System.out.println("\nUnion(0, 2): " + dsu.union(0, 2)); // Expected: true
        System.out.println("Root of 0: " + dsu.find(0));
        System.out.println("Root of 1: " + dsu.find(1));
        System.out.println("Root of 2: " + dsu.find(2));
        System.out.println("Root of 3: " + dsu.find(3));
        System.out.println("Number of sets after Union(0, 2): " + dsu.getNumSets()); // Expected: 2

        // Check if 1 and 3 are in the same set
        System.out.println("\nAre 1 and 3 in the same set? " + (dsu.find(1) == dsu.find(3))); // Expected: true

        // Try to union elements already in the same set
        System.out.println("Union(1, 3): " + dsu.union(1, 3)); // Expected: false
        System.out.println("Number of sets after Union(1, 3): " + dsu.getNumSets()); // Expected: 2 (no change)

        // Element 4 is still in its own set
        System.out.println("Root of 4: " + dsu.find(4)); // Expected: 4
        System.out.println("Are 0 and 4 in the same set? " + (dsu.find(0) == dsu.find(4))); // Expected: false

        System.out.println("\nFinal parents (after path compression): " + Arrays.toString(dsu.parent));
        // Note: The parent array might not look intuitive because of path compression,
        // but find() will correctly return the root. For example, find(1) would flatten
        // the path for 1 to point directly to its root.
    }
}
```

### Union-Find Ke Use Cases:

Union-Find data structure kai algorithms aur problems mein use hota hai, jinme se kuch pramukh hain:

1.  **Kruskal's Algorithm for MST:** Kruskal's algorithm mein edges ko sort karke add karte hain, aur `union-find` ka use karte hain yeh check karne ke liye ki kya edge add karne se cycle banega (agar dono endpoints already same set mein hain, toh cycle banega).
2.  **Connected Components:** Ek graph mein kitne connected components hain, ya kya do nodes ek hi connected component mein hain, yeh determine karne ke liye.
3.  **Grid Problems:** Jahaan aapko cells ke groups ya regions ko track karna hota hai, jaise "Number of Islands" (ek classic problem).
4.  **Cycle Detection in Undirected Graphs:** Graph mein cycles detect karne ke liye. Agar `union(u, v)` karte waqt `u` aur `v` already same set mein hain, toh `u` aur `v` ke beech edge add karne se cycle ban jayega.
5.  **Percolation (Physics/Simulation):** Models mein jahan connectivity (jaise liquid ka flow) track karni hoti hai.

Union-Find ek bohot efficient aur versatile data structure hai jo connected components aur set merging se related problems ko elegantly solve karta hai.

-----

## Question 1: Number of Connected Components in a Graph

**Problem:** Aapko ek undirected graph diya gaya hai (vertices `0` se `N-1` tak). Aapko batana hai ki graph mein kitne connected components hain.

**Input:**

  * `n`: Number of vertices.
  * `edges`: Ek 2D array jahan `edges[i] = [u, v]` matlab `u` aur `v` ke beech ek edge hai.

**Output:**

  * Graph mein connected components ki total sankhya.

**Solution Approach:**

Har vertex ko shuru mein ek alag set mein rakho. Phir har edge `(u, v)` ke liye, `u` aur `v` ke sets ko `union` kar do. Jab saare edges process ho jayen, toh DSU structure mein jitne disjoint sets bachenge, wahi connected components ki sankhya hogi. `getNumSets()` method iske liye useful rahega.

**Java Code:**

```java
import java.util.Arrays;

// Reuse the DisjointSetUnion class defined previously
// (Assuming DisjointSetUnion class with find, union, getNumSets, parent, rank is available)

public class ConnectedComponents {

    // Inner class for DSU, if not imported from elsewhere
    private static class DSU {
        private int[] parent;
        private int[] rank;
        private int numSets;

        public DSU(int n) {
            parent = new int[n];
            rank = new int[n];
            numSets = n;
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        public int find(int i) {
            if (parent[i] == i) {
                return i;
            }
            return parent[i] = find(parent[i]);
        }

        public boolean union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);
            if (rootI == rootJ) {
                return false;
            }
            if (rank[rootI] < rank[rootJ]) {
                parent[rootI] = rootJ;
            } else if (rank[rootJ] < rank[rootI]) {
                parent[rootJ] = rootI;
            } else {
                parent[rootJ] = rootI;
                rank[rootI]++;
            }
            numSets--;
            return true;
        }

        public int getNumSets() {
            return numSets;
        }
    }

    public int countConnectedComponents(int n, int[][] edges) {
        DSU dsu = new DSU(n);

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            dsu.union(u, v); // Merge the sets of u and v
        }

        return dsu.getNumSets(); // The number of remaining disjoint sets is the answer
    }

    public static void main(String[] args) {
        ConnectedComponents solver = new ConnectedComponents();

        // Example 1: 5 nodes, 4 edges
        // Graph: 0-1, 1-2, 3-4.
        // Expected components: 2 ({0,1,2}, {3,4})
        int n1 = 5;
        int[][] edges1 = {{0, 1}, {1, 2}, {3, 4}};
        System.out.println("Graph 1 Connected Components: " + solver.countConnectedComponents(n1, edges1)); // Expected: 2

        // Example 2: 6 nodes, 3 edges
        // Graph: 0-1, 2-3, 4-5.
        // Expected components: 3 ({0,1}, {2,3}, {4,5})
        int n2 = 6;
        int[][] edges2 = {{0, 1}, {2, 3}, {4, 5}};
        System.out.println("Graph 2 Connected Components: " + solver.countConnectedComponents(n2, edges2)); // Expected: 3

        // Example 3: 4 nodes, 3 edges (all connected)
        // Graph: 0-1, 1-2, 2-3
        // Expected components: 1 ({0,1,2,3})
        int n3 = 4;
        int[][] edges3 = {{0, 1}, {1, 2}, {2, 3}};
        System.out.println("Graph 3 Connected Components: " + solver.countConnectedComponents(n3, edges3)); // Expected: 1

        // Example 4: 3 nodes, 0 edges (each node is a component)
        int n4 = 3;
        int[][] edges4 = {};
        System.out.println("Graph 4 Connected Components: " + solver.countConnectedComponents(n4, edges4)); // Expected: 3
    }
}
```

-----

## Question 2: Detect Cycle in an Undirected Graph

**Problem:** Aapko ek undirected graph diya gaya hai. Batana hai ki graph mein cycle hai ya nahi.

**Input:**

  * `n`: Number of vertices.
  * `edges`: Ek 2D array jahan `edges[i] = [u, v]` matlab `u` aur `v` ke beech ek edge hai.

**Output:**

  * `true` agar graph mein cycle hai, `false` otherwise.

**Solution Approach:**

Same DSU logic apply hoga. Har edge `(u, v)` ko process karte waqt, `u` aur `v` ke roots find karo. Agar `find(u) == find(v)` hai (matlab `u` aur `v` already same set mein hain), toh `u` aur `v` ke beech edge add karne se cycle banega. Agar `find(u) != find(v)` hai, toh `union(u, v)` kar do. Agar kisi bhi point par cycle detect hota hai, toh `true` return kar do. Agar saare edges process ho gaye aur cycle detect nahi hua, toh `false` return karo.

**Java Code:**

```java
import java.util.Arrays;

// Reuse the DSU class defined previously
// (Assuming DSU class with find, union, parent, rank is available)

public class DetectCycle {

    // Inner class for DSU, if not imported from elsewhere
    private static class DSU {
        private int[] parent;
        private int[] rank;

        public DSU(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        public int find(int i) {
            if (parent[i] == i) {
                return i;
            }
            return parent[i] = find(parent[i]);
        }

        public boolean union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);
            if (rootI == rootJ) {
                return false; // They are already in the same set, so adding an edge creates a cycle
            }
            if (rank[rootI] < rank[rootJ]) {
                parent[rootI] = rootJ;
            } else if (rank[rootJ] < rank[rootI]) {
                parent[rootJ] = rootI;
            } else {
                parent[rootJ] = rootI;
                rank[rootI]++;
            }
            return true;
        }
    }

    public boolean hasCycle(int n, int[][] edges) {
        DSU dsu = new DSU(n);

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            // If u and v are already in the same set, adding this edge creates a cycle
            if (!dsu.union(u, v)) {
                return true; // Cycle detected
            }
        }
        return false; // No cycle found after processing all edges
    }

    public static void main(String[] args) {
        DetectCycle solver = new DetectCycle();

        // Example 1: Graph with a cycle (0-1, 1-2, 2-0)
        // 0--1
        // |  |
        // 2--
        int n1 = 3;
        int[][] edges1 = {{0, 1}, {1, 2}, {2, 0}};
        System.out.println("Graph 1 has cycle: " + solver.hasCycle(n1, edges1)); // Expected: true

        // Example 2: Graph without a cycle (tree-like structure)
        // 0--1--2--3
        int n2 = 4;
        int[][] edges2 = {{0, 1}, {1, 2}, {2, 3}};
        System.out.println("Graph 2 has cycle: " + solver.hasCycle(n2, edges2)); // Expected: false

        // Example 3: Disconnected graph, no cycle
        // 0--1   2--3
        int n3 = 4;
        int[][] edges3 = {{0, 1}, {2, 3}};
        System.out.println("Graph 3 has cycle: " + solver.hasCycle(n3, edges3)); // Expected: false

        // Example 4: Single edge, no cycle
        int n4 = 2;
        int[][] edges4 = {{0, 1}};
        System.out.println("Graph 4 has cycle: " + solver.hasCycle(n4, edges4)); // Expected: false

        // Example 5: Graph with multiple components, one with a cycle
        // 0--1--2--0  ,  3--4
        int n5 = 5;
        int[][] edges5 = {{0, 1}, {1, 2}, {2, 0}, {3, 4}};
        System.out.println("Graph 5 has cycle: " + solver.hasCycle(n5, edges5)); // Expected: true
    }
}
```

-----

## Question 3: Kruskal's Algorithm for Minimum Spanning Tree (MST)

**Problem:** Aapko ek connected, undirected graph diya gaya hai jisme edges ke weights hain. Minimum Spanning Tree (MST) ka total weight find karo.

**Input:**

  * `n`: Number of vertices.
  * `edges`: Ek 2D array jahan `edges[i] = [u, v, weight]` matlab `u` aur `v` ke beech `weight` ka edge hai.

**Output:**

  * MST ka total minimum weight.

**Solution Approach:**

Kruskal's algorithm MST find karne ke liye Union-Find ka use karta hai. Steps ye hain:

1.  Saare edges ko unke weights ke according **sort** karo (ascending order mein).
2.  Ek DSU structure initialize karo, jisme har vertex shuru mein apna alag set hoga.
3.  Sort kiye hue edges par iterate karo. Har edge `(u, v, w)` ke liye:
      * Agar `find(u) != find(v)` hai (matlab `u` aur `v` alag-alag components mein hain, toh is edge ko add karne se cycle nahi banega), toh:
          * Is edge ko MST mein shamil kar lo (`w` ko total MST weight mein add karo).
          * `union(u, v)` perform karo (`u` aur `v` ke components ko merge karo).
4.  Ye process tab tak repeat karo jab tak `N-1` edges MST mein na shamil ho jayen (ya saare edges process na ho jayen).

**Java Code:**

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.util.List;

// Edge class for Kruskal's
class KruskalEdge implements Comparable<KruskalEdge> {
    int u, v, weight;

    public KruskalEdge(int u, int v, int weight) {
        this.u = u;
        this.v = v;
        this.weight = weight;
    }

    // Sort edges by weight in ascending order
    @Override
    public int compareTo(KruskalEdge other) {
        return this.weight - other.weight;
    }
}

// Reuse the DSU class defined previously (from ConnectedComponents or DetectCycle)
// private static class DSU { ... }
// Copying it here for completeness:
class DSU { // Changed visibility to public to be accessible
    private int[] parent;
    private int[] rank;
    private int numSets; // Not strictly needed for Kruskal's, but good to have

    public DSU(int n) {
        parent = new int[n];
        rank = new int[n];
        numSets = n;
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public int find(int i) {
        if (parent[i] == i) {
            return i;
        }
        return parent[i] = find(parent[i]);
    }

    public boolean union(int i, int j) {
        int rootI = find(i);
        int rootJ = find(j);
        if (rootI == rootJ) {
            return false;
        }
        if (rank[rootI] < rank[rootJ]) {
            parent[rootI] = rootJ;
        } else if (rank[rootJ] < rank[rootI]) {
            parent[rootJ] = rootI;
        } else {
            parent[rootJ] = rootI;
            rank[rootI]++;
        }
        numSets--;
        return true;
    }
}


public class KruskalsMST {

    public int kruskalMST(int n, int[][] edgesData) {
        List<KruskalEdge> edges = new ArrayList<>();
        for (int[] edgeData : edgesData) {
            edges.add(new KruskalEdge(edgeData[0], edgeData[1], edgeData[2]));
        }

        // Step 1: Sort all edges by their weight
        Collections.sort(edges);

        // Step 2: Initialize DSU
        DSU dsu = new DSU(n);

        int minCost = 0;
        int edgesInMST = 0; // Count of edges added to MST

        // Step 3: Iterate through sorted edges
        for (KruskalEdge edge : edges) {
            int u = edge.u;
            int v = edge.v;
            int weight = edge.weight;

            // If adding this edge doesn't form a cycle (i.e., u and v are in different sets)
            if (dsu.find(u) != dsu.find(v)) {
                dsu.union(u, v); // Merge their sets
                minCost += weight; // Add edge weight to total MST cost
                edgesInMST++;      // Increment MST edge count

                // Optimization: If N-1 edges are added, we have found the MST
                if (edgesInMST == n - 1) {
                    break;
                }
            }
        }

        // If graph is not connected, edgesInMST will be less than N-1
        // In such cases, a true MST cannot be formed that connects all vertices.
        // The problem statement usually implies a connected graph for MST.
        return minCost;
    }

    public static void main(String[] args) {
        KruskalsMST solver = new KruskalsMST();

        // Example Graph (same as Prim's example):
        // 0 -- 1 (2)
        // | \  / |
        // 6  8 5  10
        // |   / |
        // 2 -- 3 (4)
        // |    |
        // 7 -- 4 (9)
        int n1 = 5;
        int[][] edgesData1 = {
            {0, 1, 2}, // (0,1) weight 2
            {0, 3, 6}, // (0,3) weight 6
            {1, 2, 3}, // (1,2) weight 3
            {1, 3, 8}, // (1,3) weight 8
            {1, 4, 5}, // (1,4) weight 5
            {2, 4, 7}, // (2,4) weight 7
            {3, 4, 9}  // (3,4) weight 9
        };
        // Expected MST edges and total weight:
        // (0,1,2), (1,2,3), (1,4,5), (0,3,6)
        // Total MST Weight: 2 + 3 + 5 + 6 = 16
        System.out.println("MST Weight for Graph 1: " + solver.kruskalMST(n1, edgesData1)); // Expected: 16

        // Example 2:
        // 0 --(10)-- 1
        // | \      /
        // (6) (5) (15)
        // |    \ /
        // 2 --(4)-- 3
        int n2 = 4;
        int[][] edgesData2 = {
            {0, 1, 10},
            {0, 2, 6},
            {0, 3, 5},
            {1, 3, 15},
            {2, 3, 4}
        };
        // Expected MST (edges): (2,3,4), (0,3,5), (0,2,6) - No, (0,3,5) (3,2,4) (0,1,10)
        // (0,3,5) - include, MST_Cost=5, sets: {0,3}, {1}, {2}
        // (0,2,6) - include, MST_Cost=5+6=11, sets: {0,2,3}, {1}
        // (1,2,10) not 0,1,10 - (0,1,10) - include, MST_Cost=11+10=21, sets: {0,1,2,3}
        // Actually, order matters
        // Sorted: (2,3,4), (0,3,5), (0,2,6), (0,1,10), (1,3,15)
        // 1. (2,3,4): sets {2,3}, {0}, {1}, Cost=4
        // 2. (0,3,5): sets {0,2,3}, {1}, Cost=4+5=9
        // 3. (0,2,6): Roots are same (0 and 2 are in {0,2,3}), skip
        // 4. (0,1,10): sets {0,1,2,3}, Cost=9+10=19
        // MST Edges count = 3 (N-1), so stop.
        // Total MST Weight: 19
        System.out.println("MST Weight for Graph 2: " + solver.kruskalMST(n2, edgesData2)); // Expected: 19
    }
}
```

-----

Ye teen questions Union-Find ki fundamental understanding aur uske common applications ko cover karte hain. Interviews mein DSU ko implement karne aur uske optimizations ko explain karne ki ability bohot valuable hoti hai.
