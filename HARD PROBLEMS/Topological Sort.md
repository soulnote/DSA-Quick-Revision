
# Topological Sorting Based - 20 Most Asked Hard Problems in SDE Interviews

## Topological Sort Quick Recap 

**Topological Sort** sirf **DAG (Directed Acyclic Graph)** pe possible hai. Cycle detect karna bhi important hai. Two main approaches:

1. **Kahn's Algorithm (BFS)** - In-degree count karo, queue mein 0 in-degree wale daalo
2. **DFS-based** - Recursion stack maintain karo, end mein reverse order

---

### 1. Course Schedule I
**Problem:** Can you finish all courses? `prerequisites[i] = [a, b]` means b → a (b se pehle a lena padega)

```java
public boolean canFinish(int numCourses, int[][] prerequisites) {
    List<Integer>[] graph = new ArrayList[numCourses];
    int[] inDegree = new int[numCourses];
    
    for (int i = 0; i < numCourses; i++) graph[i] = new ArrayList<>();
    
    for (int[] pre : prerequisites) {
        int course = pre[0];
        int prereq = pre[1];
        graph[prereq].add(course);
        inDegree[course]++;
    }
    
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < numCourses; i++) {
        if (inDegree[i] == 0) queue.offer(i);
    }
    
    int count = 0;
    while (!queue.isEmpty()) {
        int curr = queue.poll();
        count++;
        for (int neighbor : graph[curr]) {
            inDegree[neighbor]--;
            if (inDegree[neighbor] == 0) queue.offer(neighbor);
        }
    }
    
    return count == numCourses;
}
```

 Graph banao, in-degree array rakho. Jiski in-degree 0 hai usko queue mein daalo. Queue se nikaalo, uske neighbors ki in-degree decrease karo. Cycle hai toh saare courses complete nahi honge.

---

### 2. Course Schedule II
**Problem:** Return order of courses to finish all.

```java
public int[] findOrder(int numCourses, int[][] prerequisites) {
    List<Integer>[] graph = new ArrayList[numCourses];
    int[] inDegree = new int[numCourses];
    
    for (int i = 0; i < numCourses; i++) graph[i] = new ArrayList<>();
    
    for (int[] pre : prerequisites) {
        graph[pre[1]].add(pre[0]);
        inDegree[pre[0]]++;
    }
    
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < numCourses; i++) {
        if (inDegree[i] == 0) queue.offer(i);
    }
    
    int[] result = new int[numCourses];
    int index = 0;
    
    while (!queue.isEmpty()) {
        int curr = queue.poll();
        result[index++] = curr;
        for (int neighbor : graph[curr]) {
            inDegree[neighbor]--;
            if (inDegree[neighbor] == 0) queue.offer(neighbor);
        }
    }
    
    return index == numCourses ? result : new int[0];
}
```

 Same as Course Schedule I, bas jo queue se nikaal rahe ho usko result array mein store karte jao.

---

### 3. Alien Dictionary
**Problem:** Given sorted words in alien language, find order of letters.

```java
public String alienOrder(String[] words) {
    Map<Character, Set<Character>> graph = new HashMap<>();
    Map<Character, Integer> inDegree = new HashMap<>();
    
    // Initialize
    for (String word : words) {
        for (char c : word.toCharArray()) {
            graph.putIfAbsent(c, new HashSet<>());
            inDegree.putIfAbsent(c, 0);
        }
    }
    
    // Build graph
    for (int i = 0; i < words.length - 1; i++) {
        String w1 = words[i];
        String w2 = words[i + 1];
        int minLen = Math.min(w1.length(), w2.length());
        
        // Check for invalid case: "abc" before "ab"
        if (w1.length() > w2.length() && w1.startsWith(w2)) return "";
        
        for (int j = 0; j < minLen; j++) {
            char c1 = w1.charAt(j);
            char c2 = w2.charAt(j);
            if (c1 != c2) {
                if (!graph.get(c1).contains(c2)) {
                    graph.get(c1).add(c2);
                    inDegree.put(c2, inDegree.get(c2) + 1);
                }
                break;
            }
        }
    }
    
    // Topological sort
    Queue<Character> queue = new LinkedList<>();
    for (char c : inDegree.keySet()) {
        if (inDegree.get(c) == 0) queue.offer(c);
    }
    
    StringBuilder sb = new StringBuilder();
    while (!queue.isEmpty()) {
        char curr = queue.poll();
        sb.append(curr);
        for (char neighbor : graph.get(curr)) {
            inDegree.put(neighbor, inDegree.get(neighbor) - 1);
            if (inDegree.get(neighbor) == 0) queue.offer(neighbor);
        }
    }
    
    return sb.length() == inDegree.size() ? sb.toString() : "";
}
```

 Adjacent words compare karo, first mismatching character se edge banao (c1 → c2). Invalid case: "abc" "ab" (pehla bada hai aur prefix match) toh return "". Phir topological sort.

---

### 4. Sequence Reconstruction
**Problem:** Check if `seqs` uniquely reconstructs `org` (1 to n permutation).

```java
public boolean sequenceReconstruction(int[] org, List<List<Integer>> seqs) {
    int n = org.length;
    Map<Integer, Set<Integer>> graph = new HashMap<>();
    Map<Integer, Integer> inDegree = new HashMap<>();
    
    // Initialize
    for (int i = 1; i <= n; i++) {
        graph.put(i, new HashSet<>());
        inDegree.put(i, 0);
    }
    
    // Check if seqs is empty or has elements outside 1..n
    boolean hasElement = false;
    for (List<Integer> seq : seqs) {
        for (int num : seq) {
            if (num < 1 || num > n) return false;
            hasElement = true;
        }
        for (int i = 0; i < seq.size() - 1; i++) {
            int from = seq.get(i);
            int to = seq.get(i + 1);
            if (!graph.get(from).contains(to)) {
                graph.get(from).add(to);
                inDegree.put(to, inDegree.get(to) + 1);
            }
        }
    }
    
    if (!hasElement) return false;
    
    // Topological sort
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 1; i <= n; i++) {
        if (inDegree.get(i) == 0) queue.offer(i);
    }
    
    int index = 0;
    while (!queue.isEmpty()) {
        if (queue.size() > 1) return false; // Not unique
        int curr = queue.poll();
        if (curr != org[index++]) return false;
        for (int neighbor : graph.get(curr)) {
            inDegree.put(neighbor, inDegree.get(neighbor) - 1);
            if (inDegree.get(neighbor) == 0) queue.offer(neighbor);
        }
    }
    
    return index == n;
}
```

 Graph banao sequences se. Topological sort karo, har step pe queue size 1 hona chahiye (unique order). Jo order milta hai woh `org` se match karna chahiye.

---

### 5. Minimum Height Trees
**Problem:** Find roots that make tree of minimum height (undirected graph).

```java
public List<Integer> findMinHeightTrees(int n, int[][] edges) {
    if (n == 1) return Collections.singletonList(0);
    
    List<Set<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) graph.add(new HashSet<>());
    
    for (int[] edge : edges) {
        graph.get(edge[0]).add(edge[1]);
        graph.get(edge[1]).add(edge[0]);
    }
    
    // Start with all leaves (degree = 1)
    List<Integer> leaves = new ArrayList<>();
    for (int i = 0; i < n; i++) {
        if (graph.get(i).size() == 1) leaves.add(i);
    }
    
    int remaining = n;
    while (remaining > 2) {
        remaining -= leaves.size();
        List<Integer> newLeaves = new ArrayList<>();
        for (int leaf : leaves) {
            int neighbor = graph.get(leaf).iterator().next();
            graph.get(neighbor).remove(leaf);
            if (graph.get(neighbor).size() == 1) newLeaves.add(neighbor);
        }
        leaves = newLeaves;
    }
    
    return leaves;
}
```

 Leaves (degree 1) ko repeatedly remove karte jao. Jab 2 ya 1 node bache, wahi answer hai. MHT ki root wahi nodes hoti hain.

---

### 6. Parallel Courses III
**Problem:** Minimum time to finish all courses (each course has time, can take parallel if dependencies met).

```java
public int minimumTime(int n, int[][] relations, int[] time) {
    List<Integer>[] graph = new ArrayList[n + 1];
    int[] inDegree = new int[n + 1];
    int[] completionTime = new int[n + 1];
    
    for (int i = 1; i <= n; i++) graph[i] = new ArrayList<>();
    
    for (int[] rel : relations) {
        int prev = rel[0];
        int next = rel[1];
        graph[prev].add(next);
        inDegree[next]++;
    }
    
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 1; i <= n; i++) {
        if (inDegree[i] == 0) {
            queue.offer(i);
            completionTime[i] = time[i - 1];
        }
    }
    
    int result = 0;
    while (!queue.isEmpty()) {
        int curr = queue.poll();
        result = Math.max(result, completionTime[curr]);
        for (int neighbor : graph[curr]) {
            completionTime[neighbor] = Math.max(completionTime[neighbor], 
                                                completionTime[curr] + time[neighbor - 1]);
            inDegree[neighbor]--;
            if (inDegree[neighbor] == 0) queue.offer(neighbor);
        }
    }
    
    return result;
}
```

 Topological sort karte waqt, har course ka completion time = uske sabhi prerequisites ka max completion time + uski apni duration.

---

### 7. Find Eventual Safe States
**Problem:** Find nodes that eventually lead to terminal node (no outgoing edges).

```java
public List<Integer> eventualSafeNodes(int[][] graph) {
    int n = graph.length;
    int[] state = new int[n]; // 0=unvisited, 1=visiting, 2=safe
    
    List<Integer> result = new ArrayList<>();
    for (int i = 0; i < n; i++) {
        if (dfs(i, graph, state)) result.add(i);
    }
    return result;
}

private boolean dfs(int node, int[][] graph, int[] state) {
    if (state[node] != 0) return state[node] == 2;
    
    state[node] = 1; // visiting
    
    for (int neighbor : graph[node]) {
        if (!dfs(neighbor, graph, state)) return false;
    }
    
    state[node] = 2; // safe
    return true;
}
```

 DFS karo, cycle detect karo. Agar cycle mein hai toh woh safe nahi hai. Terminal node (no outgoing) safe hai. State array maintain karo - 0 unvisited, 1 visiting (cycle detection), 2 safe.

---

### 8. Largest Color Value in a Directed Graph
**Problem:** Find largest frequency of any color in any path of directed graph.

```java
public int largestPathValue(String colors, int[][] edges) {
    int n = colors.length();
    List<Integer>[] graph = new ArrayList[n];
    int[] inDegree = new int[n];
    
    for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
    
    for (int[] edge : edges) {
        graph[edge[0]].add(edge[1]);
        inDegree[edge[1]]++;
    }
    
    int[][] count = new int[n][26]; // count[节点][颜色]
    Queue<Integer> queue = new LinkedList<>();
    
    for (int i = 0; i < n; i++) {
        if (inDegree[i] == 0) queue.offer(i);
    }
    
    int result = 0, visited = 0;
    
    while (!queue.isEmpty()) {
        int curr = queue.poll();
        visited++;
        result = Math.max(result, ++count[curr][colors.charAt(curr) - 'a']);
        
        for (int neighbor : graph[curr]) {
            for (int c = 0; c < 26; c++) {
                count[neighbor][c] = Math.max(count[neighbor][c], count[curr][c]);
            }
            inDegree[neighbor]--;
            if (inDegree[neighbor] == 0) queue.offer(neighbor);
        }
    }
    
    return visited == n ? result : -1;
}
```

 Topological sort karte waqt, har node pe 26 colors ka count maintain karo. Neighbor ko max count pass karte jao. Cycle hai toh -1 return karo.

---

### 9. Sort Items by Groups Respecting Dependencies
**Problem:** Sort items with groups and inter-group dependencies.

```java
public int[] sortItems(int n, int m, int[] group, List<List<Integer>> beforeItems) {
    // Assign unique IDs to items with group = -1
    for (int i = 0; i < n; i++) {
        if (group[i] == -1) group[i] = m++;
    }
    
    // Graph for items and groups
    List<Integer>[] itemGraph = new ArrayList[n];
    List<Integer>[] groupGraph = new ArrayList[m];
    int[] itemInDegree = new int[n];
    int[] groupInDegree = new int[m];
    
    for (int i = 0; i < n; i++) {
        itemGraph[i] = new ArrayList<>();
    }
    for (int i = 0; i < m; i++) {
        groupGraph[i] = new ArrayList<>();
    }
    
    // Build graphs
    for (int i = 0; i < n; i++) {
        for (int before : beforeItems.get(i)) {
            itemGraph[before].add(i);
            itemInDegree[i]++;
            if (group[before] != group[i]) {
                groupGraph[group[before]].add(group[i]);
                groupInDegree[group[i]]++;
            }
        }
    }
    
    // Topological sort items and groups
    List<Integer> itemOrder = topologicalSort(itemGraph, itemInDegree, n);
    List<Integer> groupOrder = topologicalSort(groupGraph, groupInDegree, m);
    
    if (itemOrder.isEmpty() || groupOrder.isEmpty()) return new int[0];
    
    // Group items by their group
    Map<Integer, List<Integer>> groupToItems = new HashMap<>();
    for (int item : itemOrder) {
        groupToItems.computeIfAbsent(group[item], k -> new ArrayList<>()).add(item);
    }
    
    // Build result
    List<Integer> result = new ArrayList<>();
    for (int g : groupOrder) {
        result.addAll(groupToItems.getOrDefault(g, new ArrayList<>()));
    }
    
    return result.stream().mapToInt(i -> i).toArray();
}

private List<Integer> topologicalSort(List<Integer>[] graph, int[] inDegree, int n) {
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < n; i++) {
        if (inDegree[i] == 0) queue.offer(i);
    }
    
    List<Integer> order = new ArrayList<>();
    while (!queue.isEmpty()) {
        int curr = queue.poll();
        order.add(curr);
        for (int neighbor : graph[curr]) {
            inDegree[neighbor]--;
            if (inDegree[neighbor] == 0) queue.offer(neighbor);
        }
    }
    
    return order.size() == n ? order : new ArrayList<>();
}
```

 Two-level topological sort - pehle items ka, phir groups ka. Group -1 walo ko unique group ID assign karo.

---

### 10. Minimum Number of Vertices to Reach All Nodes
**Problem:** Find smallest set of vertices from which all nodes are reachable.

```java
public List<Integer> findSmallestSetOfVertices(int n, List<List<Integer>> edges) {
    int[] inDegree = new int[n];
    for (List<Integer> edge : edges) {
        inDegree[edge.get(1)]++;
    }
    
    List<Integer> result = new ArrayList<>();
    for (int i = 0; i < n; i++) {
        if (inDegree[i] == 0) result.add(i);
    }
    return result;
}
```

 Jo nodes ki in-degree 0 hai (kisi ke dwara point nahi ki gayi), unhi se start karna padega. Simple!

---

### 11. All Ancestors of a Node in a Directed Acyclic Graph
**Problem:** For each node, find all its ancestors.

```java
public List<List<Integer>> getAncestors(int n, int[][] edges) {
    List<Integer>[] graph = new ArrayList[n];
    for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
    
    int[] inDegree = new int[n];
    for (int[] edge : edges) {
        graph[edge[0]].add(edge[1]);
        inDegree[edge[1]]++;
    }
    
    List<Set<Integer>> ancestors = new ArrayList<>();
    for (int i = 0; i < n; i++) ancestors.add(new TreeSet<>());
    
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < n; i++) {
        if (inDegree[i] == 0) queue.offer(i);
    }
    
    while (!queue.isEmpty()) {
        int curr = queue.poll();
        for (int neighbor : graph[curr]) {
            ancestors.get(neighbor).addAll(ancestors.get(curr));
            ancestors.get(neighbor).add(curr);
            inDegree[neighbor]--;
            if (inDegree[neighbor] == 0) queue.offer(neighbor);
        }
    }
    
    List<List<Integer>> result = new ArrayList<>();
    for (Set<Integer> set : ancestors) {
        result.add(new ArrayList<>(set));
    }
    return result;
}
```

 Topological sort karte waqt, current node ke ancestors ko neighbor ke ancestors mein add karte jao. Set use karo duplicates avoid karne ke liye.

---

### 12. Build a Matrix With Conditions
**Problem:** Build k×k matrix satisfying row and column order conditions.

```java
public int[][] buildMatrix(int k, int[][] rowConditions, int[][] colConditions) {
    int[] rowOrder = topologicalSortForBuild(k, rowConditions);
    int[] colOrder = topologicalSortForBuild(k, colConditions);
    
    if (rowOrder.length == 0 || colOrder.length == 0) return new int[0][0];
    
    int[][] result = new int[k][k];
    int[] rowPos = new int[k + 1];
    int[] colPos = new int[k + 1];
    
    for (int i = 0; i < k; i++) {
        rowPos[rowOrder[i]] = i;
        colPos[colOrder[i]] = i;
    }
    
    for (int num = 1; num <= k; num++) {
        result[rowPos[num]][colPos[num]] = num;
    }
    
    return result;
}

private int[] topologicalSortForBuild(int k, int[][] conditions) {
    List<Integer>[] graph = new ArrayList[k + 1];
    int[] inDegree = new int[k + 1];
    
    for (int i = 1; i <= k; i++) graph[i] = new ArrayList<>();
    
    for (int[] cond : conditions) {
        graph[cond[0]].add(cond[1]);
        inDegree[cond[1]]++;
    }
    
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 1; i <= k; i++) {
        if (inDegree[i] == 0) queue.offer(i);
    }
    
    int[] result = new int[k];
    int index = 0;
    while (!queue.isEmpty()) {
        int curr = queue.poll();
        result[index++] = curr;
        for (int neighbor : graph[curr]) {
            inDegree[neighbor]--;
            if (inDegree[neighbor] == 0) queue.offer(neighbor);
        }
    }
    
    return index == k ? result : new int[0];
}
```

 Row conditions aur column conditions ka alag-alag topological sort karo. Har number ki row position aur column position fix karo, phir matrix fill karo.

---

### 13. Maximum Number of Tasks You Can Assign
**Problem:** Assign tasks to workers with strength and pills (each pill doubles strength).

```java
public int maxTaskAssign(int[] tasks, int[] workers, int pills, int strength) {
    Arrays.sort(tasks);
    Arrays.sort(workers);
    
    int left = 0, right = Math.min(tasks.length, workers.length);
    int result = 0;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (canAssign(mid, tasks, workers, pills, strength)) {
            result = mid;
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    return result;
}

private boolean canAssign(int count, int[] tasks, int[] workers, int pills, int strength) {
    TreeMap<Integer, Integer> map = new TreeMap<>();
    for (int i = workers.length - count; i < workers.length; i++) {
        map.put(workers[i], map.getOrDefault(workers[i], 0) + 1);
    }
    
    int pillsUsed = 0;
    for (int i = count - 1; i >= 0; i--) {
        int task = tasks[i];
        Integer worker = map.ceilingKey(task);
        if (worker != null) {
            removeOne(map, worker);
        } else if (pillsUsed < pills) {
            worker = map.ceilingKey(task - strength);
            if (worker != null) {
                removeOne(map, worker);
                pillsUsed++;
            } else return false;
        } else return false;
    }
    return true;
}

private void removeOne(TreeMap<Integer, Integer> map, int key) {
    map.put(key, map.get(key) - 1);
    if (map.get(key) == 0) map.remove(key);
}
```

 Binary search on answer (max tasks). Greedy approach - hardest task first, assign weakest possible worker. Pills use karke strength badhao agar zaroori ho.

---

### 14. Minimum Time to Complete All Tasks (with cooldown)
**Problem:** Schedule tasks with cooldown between same tasks.

```java
public int leastInterval(char[] tasks, int n) {
    int[] freq = new int[26];
    for (char c : tasks) freq[c - 'A']++;
    
    PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
    for (int f : freq) {
        if (f > 0) pq.offer(f);
    }
    
    int time = 0;
    while (!pq.isEmpty()) {
        List<Integer> temp = new ArrayList<>();
        int cycle = n + 1;
        
        while (cycle > 0 && !pq.isEmpty()) {
            int f = pq.poll();
            if (f > 1) temp.add(f - 1);
            time++;
            cycle--;
        }
        
        for (int f : temp) pq.offer(f);
        if (pq.isEmpty()) break;
        time += cycle;
    }
    return time;
}
```

 Max-heap mein frequencies daalo. Har cycle mein (n+1) unique tasks execute karo. Agar tasks khatam ho gaye toh idle time add karo.

---

### 15. Loud and Rich
**Problem:** For each person, find richest person in their group with minimum quiet value.

```java
public int[] loudAndRich(int[][] richer, int[] quiet) {
    int n = quiet.length;
    List<Integer>[] graph = new ArrayList[n];
    for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
    
    int[] inDegree = new int[n];
    for (int[] r : richer) {
        graph[r[0]].add(r[1]);
        inDegree[r[1]]++;
    }
    
    int[] result = new int[n];
    for (int i = 0; i < n; i++) result[i] = i;
    
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < n; i++) {
        if (inDegree[i] == 0) queue.offer(i);
    }
    
    while (!queue.isEmpty()) {
        int curr = queue.poll();
        for (int neighbor : graph[curr]) {
            if (quiet[result[curr]] < quiet[result[neighbor]]) {
                result[neighbor] = result[curr];
            }
            inDegree[neighbor]--;
            if (inDegree[neighbor] == 0) queue.offer(neighbor);
        }
    }
    
    return result;
}
```

 Richer se poorer ki taraf edge banao. Topological sort karte waqt, poorer person ke answer ko update karo agar richer person ki quiet value kam hai.

---

### 16. Maximum Number of Edges to Remove to Keep Graph Fully Traversable
**Problem:** Remove max edges so graph remains traversable by both Alice and Bob.

```java
public int maxNumEdgesToRemove(int n, int[][] edges) {
    UnionFind alice = new UnionFind(n);
    UnionFind bob = new UnionFind(n);
    
    int edgesUsed = 0;
    
    // Type 3 edges first (both can use)
    for (int[] edge : edges) {
        if (edge[0] == 3) {
            if (alice.union(edge[1] - 1, edge[2] - 1)) {
                bob.union(edge[1] - 1, edge[2] - 1);
                edgesUsed++;
            }
        }
    }
    
    // Type 1 edges (Alice only)
    for (int[] edge : edges) {
        if (edge[0] == 1) {
            if (alice.union(edge[1] - 1, edge[2] - 1)) edgesUsed++;
        }
    }
    
    // Type 2 edges (Bob only)
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

class UnionFind {
    int[] parent;
    int[] rank;
    int components;
    
    UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        components = n;
        for (int i = 0; i < n; i++) parent[i] = i;
    }
    
    int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }
    
    boolean union(int x, int y) {
        int px = find(x);
        int py = find(y);
        if (px == py) return false;
        if (rank[px] < rank[py]) parent[px] = py;
        else if (rank[px] > rank[py]) parent[py] = px;
        else {
            parent[py] = px;
            rank[px]++;
        }
        components--;
        return true;
    }
    
    boolean isFullyConnected() {
        return components == 1;
    }
}
```

 Union-Find use karo. Type 3 edges pehle process karo (dono ke liye common). Phir type 1 aur type 2 alag-alag. Jo edges use nahi hue woh remove kar sakte ho.

---

### 17. Possible Bipartition
**Problem:** Can we split people into two groups such that dislikes are in different groups?

```java
public boolean possibleBipartition(int n, int[][] dislikes) {
    List<Integer>[] graph = new ArrayList[n + 1];
    for (int i = 1; i <= n; i++) graph[i] = new ArrayList<>();
    
    for (int[] d : dislikes) {
        graph[d[0]].add(d[1]);
        graph[d[1]].add(d[0]);
    }
    
    int[] color = new int[n + 1]; // 0=uncolored, 1=groupA, -1=groupB
    
    for (int i = 1; i <= n; i++) {
        if (color[i] == 0) {
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(i);
            color[i] = 1;
            
            while (!queue.isEmpty()) {
                int curr = queue.poll();
                for (int neighbor : graph[curr]) {
                    if (color[neighbor] == color[curr]) return false;
                    if (color[neighbor] == 0) {
                        color[neighbor] = -color[curr];
                        queue.offer(neighbor);
                    }
                }
            }
        }
    }
    return true;
}
```

 Graph coloring problem (bipartite check). BFS/DFS se color karo - adjacent nodes ka color opposite hona chahiye.

---

### 18. Minimum Number of Semesters to Cover All Courses
**Problem:** Minimum semesters to take all courses (max k courses per semester, dependencies).

```java
public int minNumberOfSemesters(int n, int[][] relations, int k) {
    int[] pre = new int[n];
    for (int[] r : relations) {
        pre[r[1] - 1] |= (1 << (r[0] - 1));
    }
    
    int[] dp = new int[1 << n];
    Arrays.fill(dp, Integer.MAX_VALUE);
    dp[0] = 0;
    
    for (int mask = 0; mask < (1 << n); mask++) {
        if (dp[mask] == Integer.MAX_VALUE) continue;
        
        // Find available courses
        int available = 0;
        for (int i = 0; i < n; i++) {
            if ((mask & (1 << i)) == 0 && (pre[i] & mask) == pre[i]) {
                available |= (1 << i);
            }
        }
        
        // Generate subsets of size <= k
        int subset = available;
        while (subset > 0) {
            if (Integer.bitCount(subset) <= k) {
                dp[mask | subset] = Math.min(dp[mask | subset], dp[mask] + 1);
            }
            subset = (subset - 1) & available;
        }
    }
    
    return dp[(1 << n) - 1];
}
```

 Bitmask DP. `pre[i]` bitmask store karta hai ki course i ke liye kaunse prerequisites chahiye. Available courses mein se ≤ k size ka subset choose karo.

---

### 19. Longest Path With Different Adjacent Characters
**Problem:** Longest path in tree where no two adjacent nodes have same char.

```java
int result = 0;

public int longestPath(int[] parent, String s) {
    int n = parent.length;
    List<Integer>[] tree = new ArrayList[n];
    for (int i = 0; i < n; i++) tree[i] = new ArrayList<>();
    
    for (int i = 1; i < n; i++) {
        tree[parent[i]].add(i);
    }
    
    dfs(0, tree, s);
    return result;
}

private int dfs(int node, List<Integer>[] tree, String s) {
    int max1 = 0, max2 = 0;
    
    for (int child : tree[node]) {
        int childLen = dfs(child, tree, s);
        if (s.charAt(child) == s.charAt(node)) continue;
        
        if (childLen > max1) {
            max2 = max1;
            max1 = childLen;
        } else if (childLen > max2) {
            max2 = childLen;
        }
    }
    
    result = Math.max(result, max1 + max2 + 1);
    return max1 + 1;
}
```

 Tree DFS. Har node se neeche ki taraf longest path (with different adjacent chars) nikaalo. Do longest children ko combine karke path banao.

---

### 20. Find All Possible Recipes from Given Supplies
**Problem:** Given recipes and ingredients, find which recipes can be made.

```java
public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {
    Map<String, List<String>> graph = new HashMap<>();
    Map<String, Integer> inDegree = new HashMap<>();
    Set<String> available = new HashSet<>(Arrays.asList(supplies));
    
    // Build graph: ingredient -> recipes that need it
    for (int i = 0; i < recipes.length; i++) {
        String recipe = recipes[i];
        inDegree.put(recipe, ingredients.get(i).size());
        for (String ing : ingredients.get(i)) {
            graph.computeIfAbsent(ing, k -> new ArrayList<>()).add(recipe);
        }
    }
    
    Queue<String> queue = new LinkedList<>(available);
    List<String> result = new ArrayList<>();
    
    while (!queue.isEmpty()) {
        String curr = queue.poll();
        if (inDegree.containsKey(curr)) result.add(curr);
        
        if (graph.containsKey(curr)) {
            for (String recipe : graph.get(curr)) {
                inDegree.put(recipe, inDegree.get(recipe) - 1);
                if (inDegree.get(recipe) == 0) queue.offer(recipe);
            }
        }
    }
    
    return result;
}
```

 Ingredient se recipe ki taraf edge banao. Supplies queue mein daalo. Jis recipe ki saari ingredients available ho jaye, use queue mein daalo.

---

## 📌 Quick Pattern Summary 

| Pattern | Approach | Example Problems |
|---------|----------|------------------|
| **Basic Topological Sort** | Kahn's algorithm (BFS) | Course Schedule I, II |
| **Dictionary Order** | Compare adjacent words | Alien Dictionary |
| **Tree to DAG** | Remove leaves repeatedly | Minimum Height Trees |
| **Two-Level Sort** | Sort items + groups | Sort Items by Groups |
| **Bipartite Check** | Graph coloring | Possible Bipartition |
| **Union-Find** | Connected components | Max Edges to Remove |
| **DFS with State** | Track visiting/safe | Eventual Safe States |
| **Bitmask DP** | Subset generation | Min Semesters |

## ⚡ Pro Tips

1. **Cycle Detection** - Topological sort se cycle detect hoti hai (agar final count < n)
2. **In-degree array** - Kahn's algorithm ke liye mandatory hai
3. **Multiple queues** - Kabhi kabhi group-wise topological sort karna padta hai
4. **Reverse graph** - Kabhi kabhi reverse edges se sort karna easier hota hai
5. **Time complexity** - O(V + E) for topological sort
