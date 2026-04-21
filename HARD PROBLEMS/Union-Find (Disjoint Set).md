

## Union-Find Quick Recap

### Part 1: Theory 

**DSU kya hai?**
Maano tumhare paas 10 dost hain. Pehle sab alag-alag groups mein hain.
- **Find:** Pucho "Tera Group Leader (Parent) kaun hai?"
- **Union:** Bolo "Ab se tum dono same group mein ho. Ek ko doosre ka leader bana do."

**Use case kahan hai?**
- **Connected Components** (Graph mein kitne islands hain)
- **Cycle Detection** (Graph mein cycle hai ya nahi)
- **MST** (Kruskal's Algorithm)

**Implementation Steps:**
1. `parent[]` array banao. Shuru mein sab apne parent khud hote hain (`parent[i] = i`).
2. `find(x)`: Agar `parent[x] == x`, toh `x` leader hai. Warna `find(parent[x])` karo.
3. `union(x, y)`: `x` ke leader ko `y` ke leader ka child bana do.

---

### Part 2: Optimized Java Template (Ye Yaad Rakhna)

Yeh woh code hai jo tumhe **CP (Competitive Programming)** mein bas copy-paste karna hai. Isme **Path Compression** aur **Union by Rank** dono hain.

```java
class DSU {
    int[] parent;
    int[] rank;

    // Constructor
    public DSU(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i; // Shuru mein sab apne baap
            rank[i] = 1;   // Sabki height 1
        }
    }

    // Find with Path Compression (Ye recursion tree ko flatten kar deta hai)
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // Magic Line: Parent ko seedha Leader se jod do
        }
        return parent[x];
    }

    // Union by Rank (Chhote ped ko bade ped ke neeche lagao)
    public boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY) {
            return false; // Already same set mein hain (Cycle mil gayi)
        }

        // Jiska rank bada, wo leader banega
        if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else {
            // Dono equal hain, kisi ko bhi leader banao aur rank badhao
            parent[rootY] = rootX;
            rank[rootX]++;
        }
        return true;
    }
    
    // Helper: Number of Connected Components nikalne ke liye
    public int countComponents() {
        int count = 0;
        for (int i = 0; i < parent.length; i++) {
            if (parent[i] == i) count++;
        }
        return count;
    }
}
```

---

### Part 3: Visual Example (Leetcode 547: Number of Provinces)

**Question:** `isConnected = [[1,1,0],[1,1,0],[0,0,1]]`
- City 0 aur 1 connected hain. City 2 alag hai.
- **Output:** 2 Provinces.

**Dry Run using DSU:**

1. Init: Parent = [0, 1, 2], Rank = [1, 1, 1]
2. Union(0, 1):
   - find(0) -> 0, find(1) -> 1.
   - Rank same hai (1==1). Parent[1] = 0. Rank[0] = 2.
   - Parent = [0, 0, 2]
3. Union(0, 2) -> No edge.
4. Union(1, 2) -> No edge.

**Result:** `Parent` array mein unique leaders = {0, 2}. **Count = 2.**

---

### Part 4: 10 Important DSU Questions (Beginner to Pro)

Ye list follow karo. Agar ye 10 solve kar liye toh DSU tumhara strong suit ban jayega.

| # | Question Name | Leetcode ID | Key Trick / Kyun Important Hai? |
| :-- | :-- | :-- | :-- |
| 1 | **Number of Provinces** | 547 | **Basic Implementation** (Graph Matrix se Union karna seekho) |
| 2 | **Redundant Connection** | 684 | **Cycle Detection in Graph** (Jab edge add karte waqt find(x) == find(y) ho jaye, wahi answer hai) |
| 3 | **Number of Connected Components in an Undirected Graph** | 323 | **Counting Sets** (Premium hai but must hai. `n` se start karo aur har successful union pe `n--` karo) |
| 4 | **Graph Valid Tree** | 261 | **Tree Property Check** (No cycle + Exactly n-1 edges + single component) |
| 5 | **Most Stones Removed with Same Row or Column** | 947 | **"DSU on Row and Column"** (Yahan dimaag khulta hai. Row aur Col ko node maanna padta hai) |
| 6 | **Satisfiability of Equality Equations** | 990 | **2-Pass DSU** (Pehle `==` wale union karo, phir `!=` wale check karo ki same set mein toh nahi) |
| 7 | **Accounts Merge** | 721 | **DSU on Strings** (Email ko parent ke saath map karna. String manipulation + DSU) |
| 8 | **Smallest String With Swaps** | 1202 | **Connected Components Sorting** (Jo characters ek component mein hain unhe sort karke place karna) |
| 9 | **Regions Cut By Slashes** | 959 | **Grid Partitioning** (Har cell ko 4 triangles mein todna. DSU ka High Level Visualization) |
| 10 | **Min Cost to Connect All Points** | 1584 | **Kruskal's Algorithm** (DSU ka classic MST use-case. Contest favourite) |

---

### Part 5: Solutions for First 3 Questions (For Practice)

#### Q1: Number of Provinces (547)
```java
public int findCircleNum(int[][] isConnected) {
    int n = isConnected.length;
    DSU dsu = new DSU(n);
    int provinces = n; // Initially sab alag

    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) { // Matrix symmetric hai, half hi check karo
            if (isConnected[i][j] == 1) {
                if (dsu.union(i, j)) {
                    provinces--; // Jab do alag group jude, total provinces kam hue
                }
            }
        }
    }
    return provinces;
}
```

#### Q2: Redundant Connection (684)
```java
public int[] findRedundantConnection(int[][] edges) {
    int n = edges.length;
    DSU dsu = new DSU(n + 1); // 1-based indexing
    
    for (int[] edge : edges) {
        // Agar dono already same parent hain, matlab ye edge cycle create kar rahi hai
        if (!dsu.union(edge[0], edge[1])) {
            return edge;
        }
    }
    return new int[0];
}
```

#### Q3: Number of Connected Components (323)
```java
public int countComponents(int n, int[][] edges) {
    DSU dsu = new DSU(n);
    int components = n;
    
    for (int[] edge : edges) {
        if (dsu.union(edge[0], edge[1])) {
            components--;
        }
    }
    return components;
}
```

#### Q5 (Bonus Logic): Most Stones Removed (947)
Yeh question tricky hai par DSU ka best example hai.
Logic: Row `r` aur Column `c` ko alag node maano.
Row node = `r` (0 to 9999)
Col node = `c + 10000` (10000 se 19999)
Ek stone ka matlab: Row `r` aur Col `c+10000` ko Union karo.
Agar `n` stones hain aur `k` connected components bane, toh answer = `n - k`.
*(Is logic ko ek baar dry run karna, maza aayega)*

### Last Advice:
Contest ke time pe ye 3 cheezein galti se mat bhoolna:
1. **Path Compression:** Agar `find()` recursive hai toh `parent[x] = find(parent[x])` likhna. (Iterative wale mein bhi karna padega).
2. **1-based vs 0-based:** Array size `n+1` lena jab 1-indexed nodes ho.
3. **Union by Rank:** Bina iske TLE aa sakta hai skewed tree pe.

In questions ko **Paper-Pen** pe `parent` array bana ke dry run karo. 2-3 baar karne ke baad DSU haath ki safai ho jayega.

```java
class UnionFind {
    int[] parent;
    int[] rank;
    
    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
    }
    
    public int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }
    
    public boolean union(int x, int y) {
        int px = find(x);
        int py = find(y);
        if (px == py) return false;
        if (rank[px] < rank[py]) parent[px] = py;
        else if (rank[px] > rank[py]) parent[py] = px;
        else {
            parent[py] = px;
            rank[px]++;
        }
        return true;
    }
}
```

---

### 1. Number of Provinces (Friend Circles)
**Problem:** Find number of friend circles (connected components).

```java
public int findCircleNum(int[][] isConnected) {
    int n = isConnected.length;
    UnionFind uf = new UnionFind(n);
    
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            if (isConnected[i][j] == 1) {
                uf.union(i, j);
            }
        }
    }
    
    int count = 0;
    for (int i = 0; i < n; i++) {
        if (uf.find(i) == i) count++;
    }
    return count;
}
```

 Har connected node pair ko union karo. Finally jitne unique roots honge, utne provinces hain.

---

### 2. Redundant Connection
**Problem:** Find extra edge that makes graph cyclic (tree mein extra edge).

```java
public int[] findRedundantConnection(int[][] edges) {
    int n = edges.length;
    UnionFind uf = new UnionFind(n + 1);
    
    for (int[] edge : edges) {
        if (!uf.union(edge[0], edge[1])) {
            return edge; // Edge joining already connected nodes
        }
    }
    return new int[0];
}
```

 Har edge ko process karo. Agar edge ke do endpoints already same component mein hain, toh yeh extra edge hai (cycle create kar rahi hai).

---

### 3. Redundant Connection II (Directed Graph)
**Problem:** Find extra edge in directed tree (more complex - 2 cases).

```java
public int[] findRedundantDirectedConnection(int[][] edges) {
    int n = edges.length;
    int[] parent = new int[n + 1];
    int[] cand1 = null, cand2 = null;
    
    // Find node with 2 parents
    for (int[] edge : edges) {
        if (parent[edge[1]] == 0) {
            parent[edge[1]] = edge[0];
        } else {
            cand1 = new int[]{parent[edge[1]], edge[1]};
            cand2 = new int[]{edge[0], edge[1]};
            edge[1] = 0; // Mark to skip this edge
        }
    }
    
    UnionFind uf = new UnionFind(n + 1);
    for (int[] edge : edges) {
        if (edge[1] == 0) continue; // Skip marked edge
        if (!uf.union(edge[0], edge[1])) {
            if (cand1 == null) return edge;
            return cand1;
        }
    }
    return cand2;
}
```

 Directed graph mein do cases - node with 2 parents (cand1, cand2), ya cycle. Pehle 2 parents wala case handle karo, phir cycle check karo.

---

### 4. Accounts Merge
**Problem:** Merge accounts with same email addresses.

```java
public List<List<String>> accountsMerge(List<List<String>> accounts) {
    Map<String, String> emailToName = new HashMap<>();
    Map<String, Integer> emailToId = new HashMap<>();
    UnionFind uf = new UnionFind(10000);
    int id = 0;
    
    // Assign IDs to emails
    for (List<String> account : accounts) {
        String name = account.get(0);
        for (int i = 1; i < account.size(); i++) {
            String email = account.get(i);
            emailToName.put(email, name);
            if (!emailToId.containsKey(email)) {
                emailToId.put(email, id++);
            }
            // Union first email with all others in same account
            uf.union(emailToId.get(account.get(1)), emailToId.get(email));
        }
    }
    
    // Group emails by root
    Map<Integer, List<String>> rootToEmails = new HashMap<>();
    for (String email : emailToName.keySet()) {
        int root = uf.find(emailToId.get(email));
        rootToEmails.computeIfAbsent(root, k -> new ArrayList<>()).add(email);
    }
    
    // Build result
    List<List<String>> result = new ArrayList<>();
    for (List<String> emails : rootToEmails.values()) {
        Collections.sort(emails);
        List<String> account = new ArrayList<>();
        account.add(emailToName.get(emails.get(0)));
        account.addAll(emails);
        result.add(account);
    }
    return result;
}
```

 Har email ko unique ID do. Same account ki saari emails ko union karo. Phir same root wali emails ko group karo aur sort karo.

---

### 5. Number of Islands II
**Problem:** Add land one by one, return number of islands after each addition.

```java
public List<Integer> numIslands2(int m, int n, int[][] positions) {
    List<Integer> result = new ArrayList<>();
    if (m == 0 || n == 0) return result;
    
    UnionFind uf = new UnionFind(m * n);
    boolean[][] isLand = new boolean[m][n];
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    int count = 0;
    
    for (int[] pos : positions) {
        int x = pos[0];
        int y = pos[1];
        int idx = x * n + y;
        
        if (isLand[x][y]) {
            result.add(count);
            continue;
        }
        
        isLand[x][y] = true;
        count++;
        
        for (int[] dir : dirs) {
            int nx = x + dir[0];
            int ny = y + dir[1];
            int nidx = nx * n + ny;
            if (nx >= 0 && nx < m && ny >= 0 && ny < n && isLand[nx][ny]) {
                if (uf.union(idx, nidx)) {
                    count--;
                }
            }
        }
        result.add(count);
    }
    return result;
}
```

 Har naya land add karo, initially count++. Uske 4 neighbors se union karo - agar successful union hota hai toh count-- (do islands merge ho gaye).

---

### 6. Satisfiability of Equality Equations
**Problem:** Check if equations (a==b, a!=b) are consistent.

```java
public boolean equationsPossible(String[] equations) {
    UnionFind uf = new UnionFind(26);
    
    // First pass: union all equal variables
    for (String eq : equations) {
        if (eq.charAt(1) == '=') {
            int a = eq.charAt(0) - 'a';
            int b = eq.charAt(3) - 'a';
            uf.union(a, b);
        }
    }
    
    // Second pass: check inequalities
    for (String eq : equations) {
        if (eq.charAt(1) == '!') {
            int a = eq.charAt(0) - 'a';
            int b = eq.charAt(3) - 'a';
            if (uf.find(a) == uf.find(b)) return false;
        }
    }
    return true;
}
```

 Pehle saare '==' equations ko union karo. Phir '!=' equations check karo - agar do variables same set mein hain toh impossible.

---

### 7. Most Stones Removed with Same Row or Column
**Problem:** Max stones remove karo where stones share row or column.

```java
public int removeStones(int[][] stones) {
    int n = stones.length;
    UnionFind uf = new UnionFind(20000); // Because coordinates up to 10000
    
    for (int[] stone : stones) {
        uf.union(stone[0], stone[1] + 10000); // Shift column by 10000
    }
    
    Set<Integer> uniqueRoots = new HashSet<>();
    for (int[] stone : stones) {
        uniqueRoots.add(uf.find(stone[0]));
    }
    
    return n - uniqueRoots.size();
}
```

 Har stone ke row aur column ko same component mein union karo (use +10000 offset). Jo stones connected hain, unme se ek ko chhod ke sab remove kar sakte ho.

---

### 8. Number of Connected Components in an Undirected Graph
**Problem:** Count connected components in given n nodes and edges.

```java
public int countComponents(int n, int[][] edges) {
    UnionFind uf = new UnionFind(n);
    for (int[] edge : edges) {
        uf.union(edge[0], edge[1]);
    }
    
    int count = 0;
    for (int i = 0; i < n; i++) {
        if (uf.find(i) == i) count++;
    }
    return count;
}
```

 Saare edges ko union karo. Jo nodes apne khud ke parent hain (root), wahi components count honge.

---

### 9. Similar String Groups
**Problem:** Group strings where two strings are similar if they differ by at most 2 positions.

```java
public int numSimilarGroups(String[] strs) {
    int n = strs.length;
    UnionFind uf = new UnionFind(n);
    
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            if (isSimilar(strs[i], strs[j])) {
                uf.union(i, j);
            }
        }
    }
    
    int count = 0;
    for (int i = 0; i < n; i++) {
        if (uf.find(i) == i) count++;
    }
    return count;
}

private boolean isSimilar(String a, String b) {
    int diff = 0;
    for (int i = 0; i < a.length(); i++) {
        if (a.charAt(i) != b.charAt(i)) diff++;
        if (diff > 2) return false;
    }
    return diff == 0 || diff == 2;
}
```

 Har pair check karo agar similar hai toh union karo. Finally unique components count karo.

---

### 10. Minimum Cost to Connect Sticks (with Union-Find style)
**Problem:** Connect sticks with cost = sum, minimize total cost.

```java
public int connectSticks(int[] sticks) {
    PriorityQueue<Integer> pq = new PriorityQueue<>();
    for (int s : sticks) pq.offer(s);
    
    int cost = 0;
    while (pq.size() > 1) {
        int a = pq.poll();
        int b = pq.poll();
        int sum = a + b;
        cost += sum;
        pq.offer(sum);
    }
    return cost;
}
```

 Though not typical Union-Find, this is greedy. Always connect two smallest sticks (like Huffman coding). Min-heap use karo.

---

### 11. Minimum Cost to Connect Cities (Kruskal's Algorithm)
**Problem:** Connect all cities with minimum total cost.

```java
public int minimumCost(int n, int[][] connections) {
    Arrays.sort(connections, (a, b) -> a[2] - b[2]); // Sort by cost
    UnionFind uf = new UnionFind(n + 1);
    
    int totalCost = 0;
    int edgesUsed = 0;
    
    for (int[] conn : connections) {
        if (uf.union(conn[0], conn[1])) {
            totalCost += conn[2];
            edgesUsed++;
        }
        if (edgesUsed == n - 1) break;
    }
    
    return edgesUsed == n - 1 ? totalCost : -1;
}
```

 Kruskal's algorithm - edges ko cost se sort karo. Minimum cost edge se start karo, agar union possible hai toh add karo. (n-1) edges lagenge.

---

### 12. Evaluate Division (Graph + Union-Find)
**Problem:** Evaluate equations like a/b = 2.0, find queries like a/c.

```java
class Node {
    String parent;
    double ratio;
    Node(String parent, double ratio) {
        this.parent = parent;
        this.ratio = ratio;
    }
}

public double[] calcEquation(List<List<String>> equations, double[] values, 
                             List<List<String>> queries) {
    Map<String, Node> map = new HashMap<>();
    
    for (int i = 0; i < equations.size(); i++) {
        String a = equations.get(i).get(0);
        String b = equations.get(i).get(1);
        double val = values[i];
        
        if (!map.containsKey(a) && !map.containsKey(b)) {
            map.put(a, new Node(b, val));
            map.put(b, new Node(b, 1.0));
        } else if (!map.containsKey(a)) {
            map.put(a, new Node(b, val));
        } else if (!map.containsKey(b)) {
            map.put(b, new Node(a, 1.0 / val));
        } else {
            union(map, a, b, val);
        }
    }
    
    double[] result = new double[queries.size()];
    for (int i = 0; i < queries.size(); i++) {
        String a = queries.get(i).get(0);
        String b = queries.get(i).get(1);
        if (!map.containsKey(a) || !map.containsKey(b)) {
            result[i] = -1.0;
        } else {
            Node na = find(map, a);
            Node nb = find(map, b);
            if (!na.parent.equals(nb.parent)) result[i] = -1.0;
            else result[i] = na.ratio / nb.ratio;
        }
    }
    return result;
}

private Node find(Map<String, Node> map, String s) {
    if (!map.get(s).parent.equals(s)) {
        Node node = find(map, map.get(s).parent);
        map.get(s).ratio *= node.ratio;
        map.get(s).parent = node.parent;
    }
    return map.get(s);
}

private void union(Map<String, Node> map, String a, String b, double val) {
    Node na = find(map, a);
    Node nb = find(map, b);
    if (!na.parent.equals(nb.parent)) {
        map.put(na.parent, new Node(nb.parent, nb.ratio * val / na.ratio));
    }
}
```

 Har variable ka parent aur ratio store karo (value/parent). Path compression karte waqt ratio bhi update karo.

---

### 13. Regions Cut By Slashes
**Problem:** n×n grid divided by slashes (/ or \), find number of regions.

```java
public int regionsBySlashes(String[] grid) {
    int n = grid.length;
    UnionFind uf = new UnionFind(4 * n * n); // Each cell split into 4 triangles
    
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            int idx = 4 * (i * n + j);
            char c = grid[i].charAt(j);
            
            // Union triangles within the cell
            if (c == '/') {
                uf.union(idx, idx + 3);
                uf.union(idx + 1, idx + 2);
            } else if (c == '\\') {
                uf.union(idx, idx + 1);
                uf.union(idx + 2, idx + 3);
            } else { // ' '
                uf.union(idx, idx + 1);
                uf.union(idx + 1, idx + 2);
                uf.union(idx + 2, idx + 3);
            }
            
            // Union with right cell
            if (j + 1 < n) {
                uf.union(idx + 1, 4 * (i * n + (j + 1)) + 3);
            }
            // Union with bottom cell
            if (i + 1 < n) {
                uf.union(idx + 2, 4 * ((i + 1) * n + j));
            }
        }
    }
    
    int count = 0;
    for (int i = 0; i < 4 * n * n; i++) {
        if (uf.find(i) == i) count++;
    }
    return count;
}
```

 Har cell ko 4 triangles mein divide karo. Slash ke hisaab se triangles ko union karo. Adjacent cells ke triangles bhi union karo. Count unique roots.

---

### 14. Checking Existence of Edge Length Limited Paths
**Problem:** Check if path exists between nodes with edges only ≤ limit.

```java
public boolean[] distanceLimitedPathsExist(int n, int[][] edgeList, int[][] queries) {
    // Sort edges by weight
    Arrays.sort(edgeList, (a, b) -> a[2] - b[2]);
    
    // Prepare queries with index
    int[][] q = new int[queries.length][4];
    for (int i = 0; i < queries.length; i++) {
        q[i] = new int[]{queries[i][0], queries[i][1], queries[i][2], i};
    }
    Arrays.sort(q, (a, b) -> a[2] - b[2]);
    
    UnionFind uf = new UnionFind(n);
    boolean[] result = new boolean[queries.length];
    int edgeIdx = 0;
    
    for (int[] query : q) {
        int u = query[0], v = query[1], limit = query[2], idx = query[3];
        
        // Add all edges with weight < limit
        while (edgeIdx < edgeList.length && edgeList[edgeIdx][2] < limit) {
            uf.union(edgeList[edgeIdx][0], edgeList[edgeIdx][1]);
            edgeIdx++;
        }
        
        result[idx] = uf.find(u) == uf.find(v);
    }
    return result;
}
```

 Edges ko weight se sort karo. Queries ko limit se sort karo. Ek pointer maintain karo - limit se chhoti saari edges union karte jao. Phir query ka answer check karo.

---

### 15. Smallest String With Swaps
**Problem:** Swap characters at given indices any number of times, get smallest lexicographical string.

```java
public String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
    int n = s.length();
    UnionFind uf = new UnionFind(n);
    
    // Union all swappable indices
    for (List<Integer> pair : pairs) {
        uf.union(pair.get(0), pair.get(1));
    }
    
    // Group indices by root
    Map<Integer, List<Integer>> rootToIndices = new HashMap<>();
    for (int i = 0; i < n; i++) {
        int root = uf.find(i);
        rootToIndices.computeIfAbsent(root, k -> new ArrayList<>()).add(i);
    }
    
    // Build result
    char[] result = new char[n];
    for (List<Integer> indices : rootToIndices.values()) {
        List<Character> chars = new ArrayList<>();
        for (int idx : indices) {
            chars.add(s.charAt(idx));
        }
        Collections.sort(chars);
        Collections.sort(indices);
        for (int i = 0; i < indices.size(); i++) {
            result[indices.get(i)] = chars.get(i);
        }
    }
    return new String(result);
}
```

 Jo indices swap kar sakte hain unhe union karo. Har connected component mein characters sort karo aur wapas assign karo.

---

### 16. Number of Operations to Make Network Connected
**Problem:** Min operations to connect all computers (extra cables can be moved).

```java
public int makeConnected(int n, int[][] connections) {
    if (connections.length < n - 1) return -1;
    
    UnionFind uf = new UnionFind(n);
    int extraEdges = 0;
    
    for (int[] conn : connections) {
        if (!uf.union(conn[0], conn[1])) {
            extraEdges++;
        }
    }
    
    int components = 0;
    for (int i = 0; i < n; i++) {
        if (uf.find(i) == i) components++;
    }
    
    return components - 1 <= extraEdges ? components - 1 : -1;
}
```

 N computers ko connect karne ke liye (n-1) edges chahiye. Extra edges count karo. (components - 1) extra edges chahiye.

---

### 17. Graph Connectivity With Threshold
**Problem:** Connect nodes if they share divisor > threshold.

```java
public List<Boolean> areConnected(int n, int threshold, int[][] queries) {
    UnionFind uf = new UnionFind(n + 1);
    
    // Connect numbers with common divisors
    for (int d = threshold + 1; d <= n; d++) {
        for (int multiple = 2 * d; multiple <= n; multiple += d) {
            uf.union(d, multiple);
        }
    }
    
    List<Boolean> result = new ArrayList<>();
    for (int[] query : queries) {
        result.add(uf.find(query[0]) == uf.find(query[1]));
    }
    return result;
}
```

 Threshold se bade har divisor ke liye, uske saare multiples ko union karo. Phir queries check karo.

---

### 18. Maximum Number of Accepted Invitations
**Problem:** Match boys and girls where each has preference list.

```java
public int maximumInvitations(int[][] grid) {
    int m = grid.length;
    int n = grid[0].length;
    int[] matchGirl = new int[n];
    Arrays.fill(matchGirl, -1);
    
    int result = 0;
    for (int i = 0; i < m; i++) {
        boolean[] visited = new boolean[n];
        if (dfs(i, grid, visited, matchGirl)) result++;
    }
    return result;
}

private boolean dfs(int boy, int[][] grid, boolean[] visited, int[] matchGirl) {
    int n = grid[0].length;
    for (int girl = 0; girl < n; girl++) {
        if (grid[boy][girl] == 1 && !visited[girl]) {
            visited[girl] = true;
            if (matchGirl[girl] == -1 || dfs(matchGirl[girl], grid, visited, matchGirl)) {
                matchGirl[girl] = boy;
                return true;
            }
        }
    }
    return false;
}
```

 Bipartite matching (Hopcroft-Karp style). DFS se maximum matching find karo.

---

### 19. Remove Max Number of Edges to Keep Graph Fully Traversable (Union-Find version)
**Problem:** Remove max edges so both Alice and Bob can traverse.

```java
public int maxNumEdgesToRemove(int n, int[][] edges) {
    UnionFind alice = new UnionFind(n);
    UnionFind bob = new UnionFind(n);
    int edgesUsed = 0;
    
    // Type 3 first (both can use)
    for (int[] edge : edges) {
        if (edge[0] == 3) {
            if (alice.union(edge[1] - 1, edge[2] - 1)) {
                bob.union(edge[1] - 1, edge[2] - 1);
                edgesUsed++;
            }
        }
    }
    
    // Type 1 (Alice only)
    for (int[] edge : edges) {
        if (edge[0] == 1) {
            if (alice.union(edge[1] - 1, edge[2] - 1)) edgesUsed++;
        }
    }
    
    // Type 2 (Bob only)
    for (int[] edge : edges) {
        if (edge[0] == 2) {
            if (bob.union(edge[1] - 1, edge[2] - 1)) edgesUsed++;
        }
    }
    
    if (alice.isFullyConnected() && bob.isFullyConnected()) {
        return edges.length - edgesUsed;
    }
    return -1;
}
```

 Union-Find with two instances - ek Alice ke liye, ek Bob ke liye. Type 3 edges pehle process karo (dono ke liye common).

---

### 20. Minimum Cost to Make at Least One Valid Path in a Grid
**Problem:** Change minimum arrow directions so path exists from (0,0) to (m-1,n-1).

```java
public int minCost(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    int[][] cost = new int[m][n];
    for (int[] row : cost) Arrays.fill(row, Integer.MAX_VALUE);
    
    Deque<int[]> deque = new ArrayDeque<>();
    cost[0][0] = 0;
    deque.offerFirst(new int[]{0, 0});
    
    while (!deque.isEmpty()) {
        int[] curr = deque.pollFirst();
        int x = curr[0], y = curr[1];
        
        for (int i = 0; i < 4; i++) {
            int nx = x + dirs[i][0];
            int ny = y + dirs[i][1];
            if (nx < 0 || nx >= m || ny < 0 || ny >= n) continue;
            
            int newCost = cost[x][y] + (grid[x][y] == i + 1 ? 0 : 1);
            if (newCost < cost[nx][ny]) {
                cost[nx][ny] = newCost;
                if (grid[x][y] == i + 1) {
                    deque.offerFirst(new int[]{nx, ny});
                } else {
                    deque.offerLast(new int[]{nx, ny});
                }
            }
        }
    }
    return cost[m - 1][n - 1];
}
```

 0-1 BFS use karo. Agar arrow direction match karta hai toh cost 0 (front of deque), warna cost 1 (back of deque).

---

## 📌 Quick Pattern Summary (Hinglish)

| Pattern | Approach | Example Problems |
|---------|----------|------------------|
| **Connected Components** | Count unique roots | Number of Provinces, Accounts Merge |
| **Cycle Detection** | Union returns false | Redundant Connection |
| **Minimum Spanning Tree** | Kruskal's (sort + union) | Min Cost to Connect Cities |
| **Dynamic Connectivity** | Process offline + sort | Edge Limited Paths |
| **Bipartite Matching** | DFS + visited array | Maximum Invitations |
| **Graph Building** | Custom union logic | Regions Cut By Slashes |
| **0-1 BFS** | Deque for costs | Min Cost Valid Path |

## ⚡ Pro Tips (Hinglish)

1. **Path Compression** mandatory hai for O(α(n)) performance
2. **Union by Rank/Size** se tree balance rehta hai
3. **Offline queries** - kabhi kabhi queries ko sort karna padta hai (edge limited paths)
4. **2D to 1D mapping** - grid problems mein `index = i * n + j` use karo
5. **Coordinate shifting** - rows and columns separate IDs dene ke liye offset use karo (stones problem)
