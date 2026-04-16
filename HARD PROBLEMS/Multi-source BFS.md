##  Multi-Source BFS Quick Recap

**Multi-Source BFS** ek technique hai jahan hum **multiple starting points** ko simultaneously queue mein daal kar BFS chalate hain.

**Why Multi-Source BFS?**
- Jab multiple sources se distance nikalni ho (e.g., nearest gate, nearest rotten orange)
- Parallel processing ki tarah kaam karta hai
- Time complexity O(V + E) same as normal BFS

**Template:**
```java
Queue<int[]> queue = new LinkedList<>();
for (each source) {
    queue.offer(source);
    visited[source] = true;
}
while (!queue.isEmpty()) {
    // Process level by level
}
```

---

### 1. 01 Matrix (Nearest Zero)
**Problem:** For each cell, find distance to nearest 0.

```java
public int[][] updateMatrix(int[][] mat) {
    int m = mat.length, n = mat[0].length;
    int[][] dist = new int[m][n];
    Queue<int[]> queue = new LinkedList<>();
    
    // Initialize: all zeros as sources
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (mat[i][j] == 0) {
                dist[i][j] = 0;
                queue.offer(new int[]{i, j});
            } else {
                dist[i][j] = Integer.MAX_VALUE;
            }
        }
    }
    
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    
    while (!queue.isEmpty()) {
        int[] curr = queue.poll();
        for (int[] dir : dirs) {
            int x = curr[0] + dir[0];
            int y = curr[1] + dir[1];
            if (x >= 0 && x < m && y >= 0 && y < n) {
                if (dist[x][y] > dist[curr[0]][curr[1]] + 1) {
                    dist[x][y] = dist[curr[0]][curr[1]] + 1;
                    queue.offer(new int[]{x, y});
                }
            }
        }
    }
    return dist;
}
```

Saare zeros ko source bana kar queue mein daalo. BFS se distance propagate karo. Har cell apne neighbor se ek step zyada distance lega.

---

### 2. Rotting Oranges (Multi-Source BFS)
**Problem:** Minimum time for all oranges to rot.

```java
public int orangesRotting(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    Queue<int[]> queue = new LinkedList<>();
    int fresh = 0;
    
    // Add all rotten oranges as sources
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (grid[i][j] == 2) {
                queue.offer(new int[]{i, j});
            } else if (grid[i][j] == 1) {
                fresh++;
            }
        }
    }
    
    if (fresh == 0) return 0;
    
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    int minutes = 0;
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        boolean rotted = false;
        
        for (int i = 0; i < size; i++) {
            int[] curr = queue.poll();
            for (int[] dir : dirs) {
                int x = curr[0] + dir[0];
                int y = curr[1] + dir[1];
                if (x >= 0 && x < m && y >= 0 && y < n && grid[x][y] == 1) {
                    grid[x][y] = 2;
                    queue.offer(new int[]{x, y});
                    fresh--;
                    rotted = true;
                }
            }
        }
        if (rotted) minutes++;
    }
    
    return fresh == 0 ? minutes : -1;
}
```

Saare rotten oranges ko source bana kar queue mein daalo. Level by level BFS se naye oranges rot karte jao. Har level ek minute represent karta hai.

---

### 3. Walls and Gates
**Problem:** Fill each empty room with distance to nearest gate.

```java
public void wallsAndGates(int[][] rooms) {
    if (rooms == null || rooms.length == 0) return;
    int m = rooms.length, n = rooms[0].length;
    Queue<int[]> queue = new LinkedList<>();
    
    // Add all gates as sources
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (rooms[i][j] == 0) {
                queue.offer(new int[]{i, j});
            }
        }
    }
    
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    
    while (!queue.isEmpty()) {
        int[] curr = queue.poll();
        for (int[] dir : dirs) {
            int x = curr[0] + dir[0];
            int y = curr[1] + dir[1];
            if (x >= 0 && x < m && y >= 0 && y < n && rooms[x][y] == Integer.MAX_VALUE) {
                rooms[x][y] = rooms[curr[0]][curr[1]] + 1;
                queue.offer(new int[]{x, y});
            }
        }
    }
}
```

Saare gates (0) ko source banao. BFS se distance fill karte jao. Wall (-1) ko ignore karo.

---

### 4. As Far from Land as Possible
**Problem:** Find water cell farthest from any land.

```java
public int maxDistance(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    Queue<int[]> queue = new LinkedList<>();
    
    // Add all land cells as sources
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (grid[i][j] == 1) {
                queue.offer(new int[]{i, j});
            }
        }
    }
    
    if (queue.size() == 0 || queue.size() == m * n) return -1;
    
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    int distance = -1;
    
    while (!queue.isEmpty()) {
        distance++;
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            int[] curr = queue.poll();
            for (int[] dir : dirs) {
                int x = curr[0] + dir[0];
                int y = curr[1] + dir[1];
                if (x >= 0 && x < m && y >= 0 && y < n && grid[x][y] == 0) {
                    grid[x][y] = distance + 1;
                    queue.offer(new int[]{x, y});
                }
            }
        }
    }
    return distance;
}
```

Saare land (1) ko source banao. BFS se water cells mein distance fill karo. Last level ka distance answer hoga.

---

### 5. Shortest Bridge (Multi-Source BFS after DFS)
**Problem:** Minimum flips to connect two islands (1s) in binary matrix.

```java
public int shortestBridge(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    Queue<int[]> queue = new LinkedList<>();
    boolean found = false;
    
    // DFS to find first island and add all its cells to queue
    for (int i = 0; i < m && !found; i++) {
        for (int j = 0; j < n && !found; j++) {
            if (grid[i][j] == 1) {
                dfs(grid, i, j, queue);
                found = true;
            }
        }
    }
    
    // Multi-source BFS to expand and find second island
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    int steps = 0;
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            int[] curr = queue.poll();
            for (int[] dir : dirs) {
                int x = curr[0] + dir[0];
                int y = curr[1] + dir[1];
                if (x >= 0 && x < m && y >= 0 && y < n) {
                    if (grid[x][y] == 1) return steps;
                    if (grid[x][y] == 0) {
                        grid[x][y] = 2;
                        queue.offer(new int[]{x, y});
                    }
                }
            }
        }
        steps++;
    }
    return -1;
}

private void dfs(int[][] grid, int i, int j, Queue<int[]> queue) {
    if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != 1) {
        return;
    }
    grid[i][j] = 2;
    queue.offer(new int[]{i, j});
    dfs(grid, i + 1, j, queue);
    dfs(grid, i - 1, j, queue);
    dfs(grid, i, j + 1, queue);
    dfs(grid, i, j - 1, queue);
}
```

Pehle island ko DFS se dhundho aur uske saare cells ko queue mein daalo (sources). Phir multi-source BFS se expand karo. Jab second island ka 1 mile, steps return karo.

---

### 6. Minimum Knight Moves (Multi-Source for multiple targets)
**Problem:** Minimum moves for knight to reach target from multiple positions.

```java
public int minKnightMoves(int x, int y) {
    x = Math.abs(x);
    y = Math.abs(y);
    
    if (x == 0 && y == 0) return 0;
    if (x == 1 && y == 1) return 2;
    
    Queue<int[]> queue = new LinkedList<>();
    queue.offer(new int[]{0, 0});
    Set<String> visited = new HashSet<>();
    visited.add("0,0");
    
    int[][] dirs = {{2,1},{2,-1},{-2,1},{-2,-1},{1,2},{1,-2},{-1,2},{-1,-2}};
    int moves = 0;
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            int[] curr = queue.poll();
            if (curr[0] == x && curr[1] == y) return moves;
            
            for (int[] dir : dirs) {
                int nx = curr[0] + dir[0];
                int ny = curr[1] + dir[1];
                if (nx >= -2 && ny >= -2 && nx <= x + 2 && ny <= y + 2) {
                    String key = nx + "," + ny;
                    if (!visited.contains(key)) {
                        visited.add(key);
                        queue.offer(new int[]{nx, ny});
                    }
                }
            }
        }
        moves++;
    }
    return -1;
}
```

Single source BFS hai lekin symmetry use karte hain. Target ko first quadrant mein convert karo for optimization.

---

### 7. Map of Highest Peak
**Problem:** Assign heights to water cells with distance to nearest land.

```java
public int[][] highestPeak(int[][] isWater) {
    int m = isWater.length, n = isWater[0].length;
    int[][] height = new int[m][n];
    Queue<int[]> queue = new LinkedList<>();
    
    // Initialize: water cells as sources (height 0)
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (isWater[i][j] == 1) {
                height[i][j] = 0;
                queue.offer(new int[]{i, j});
            } else {
                height[i][j] = -1;
            }
        }
    }
    
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    
    while (!queue.isEmpty()) {
        int[] curr = queue.poll();
        for (int[] dir : dirs) {
            int x = curr[0] + dir[0];
            int y = curr[1] + dir[1];
            if (x >= 0 && x < m && y >= 0 && y < n && height[x][y] == -1) {
                height[x][y] = height[curr[0]][curr[1]] + 1;
                queue.offer(new int[]{x, y});
            }
        }
    }
    return height;
}
```

Water cells (1) ko source banao. BFS se heights fill karo. Land cells (0) farthest se highest height milegi.

---

### 8. Minimum Operations to Convert All Ones to Zero (Multi-Source BFS)
**Problem:** Flip bits with given operations.

```java
public int minOperations(int[] nums) {
    int n = nums.length;
    Set<Integer> visited = new HashSet<>();
    Queue<Integer> queue = new LinkedList<>();
    
    // Convert array to bitmask
    int startMask = 0;
    for (int i = 0; i < n; i++) {
        if (nums[i] == 1) startMask |= (1 << i);
    }
    
    queue.offer(startMask);
    visited.add(startMask);
    int operations = 0;
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            int mask = queue.poll();
            if (mask == 0) return operations;
            
            // Try flipping each position
            for (int j = 0; j < n; j++) {
                int newMask = flipWithNeighbors(mask, j, n);
                if (!visited.contains(newMask)) {
                    visited.add(newMask);
                    queue.offer(newMask);
                }
            }
        }
        operations++;
    }
    return -1;
}

private int flipWithNeighbors(int mask, int pos, int n) {
    mask ^= (1 << pos);
    if (pos > 0) mask ^= (1 << (pos - 1));
    if (pos < n - 1) mask ^= (1 << (pos + 1));
    return mask;
}
```

Bitmask state space mein BFS. Har state ek configuration hai. Har operation flip karta hai current + neighbors.

---

### 9. Minimum Genetic Mutation (Multi-Source not typical, but pattern)
**Problem:** Minimum mutations from start to end.

```java
public int minMutation(String start, String end, String[] bank) {
    Set<String> bankSet = new HashSet<>(Arrays.asList(bank));
    if (!bankSet.contains(end)) return -1;
    
    Queue<String> queue = new LinkedList<>();
    queue.offer(start);
    bankSet.remove(start);
    
    char[] genes = {'A', 'C', 'G', 'T'};
    int mutations = 0;
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            String curr = queue.poll();
            if (curr.equals(end)) return mutations;
            
            char[] chars = curr.toCharArray();
            for (int j = 0; j < chars.length; j++) {
                char original = chars[j];
                for (char gene : genes) {
                    if (gene == original) continue;
                    chars[j] = gene;
                    String next = new String(chars);
                    if (bankSet.contains(next)) {
                        queue.offer(next);
                        bankSet.remove(next);
                    }
                }
                chars[j] = original;
            }
        }
        mutations++;
    }
    return -1;
}
```

Single source BFS. Har level pe ek character change karte hain. Bank mein existing strings hi explore karte hain.

---

### 10. Shortest Path to Get All Keys
**Problem:** Shortest path to collect all keys in a grid (with locks).

```java
public int shortestPathAllKeys(String[] grid) {
    int m = grid.length, n = grid[0].length();
    int startX = -1, startY = -1;
    int totalKeys = 0;
    
    // Find start position and count total keys
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            char c = grid[i].charAt(j);
            if (c == '@') {
                startX = i;
                startY = j;
            } else if (c >= 'a' && c <= 'f') {
                totalKeys++;
            }
        }
    }
    
    // State: (x, y, keysMask)
    boolean[][][] visited = new boolean[m][n][1 << totalKeys];
    Queue<int[]> queue = new LinkedList<>();
    queue.offer(new int[]{startX, startY, 0, 0}); // x, y, keysMask, dist
    visited[startX][startY][0] = true;
    
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    
    while (!queue.isEmpty()) {
        int[] curr = queue.poll();
        int x = curr[0], y = curr[1], keys = curr[2], dist = curr[3];
        
        if (keys == (1 << totalKeys) - 1) return dist;
        
        for (int[] dir : dirs) {
            int nx = x + dir[0];
            int ny = y + dir[1];
            if (nx < 0 || nx >= m || ny < 0 || ny >= n) continue;
            
            char cell = grid[nx].charAt(ny);
            if (cell == '#') continue;
            
            if (cell >= 'A' && cell <= 'F') { // Lock
                int lockBit = cell - 'A';
                if ((keys & (1 << lockBit)) == 0) continue;
            }
            
            int newKeys = keys;
            if (cell >= 'a' && cell <= 'f') { // Key
                newKeys |= (1 << (cell - 'a'));
            }
            
            if (!visited[nx][ny][newKeys]) {
                visited[nx][ny][newKeys] = true;
                queue.offer(new int[]{nx, ny, newKeys, dist + 1});
            }
        }
    }
    return -1;
}
```

State space BFS - (x, y, keys collected). Keys ko bitmask mein store karo. Lock ke liye key honi chahiye.

---

### 11. Minimum Moves to Reach Target with Rotations
**Problem:** Snake (2 cells) movement in grid.

```java
public int minimumMoves(int[][] grid) {
    int n = grid.length;
    boolean[][][] visited = new boolean[n][n][2];
    Queue<int[]> queue = new LinkedList<>();
    queue.offer(new int[]{0, 0, 0, 0}); // x, y, orientation, dist
    visited[0][0][0] = true;
    
    // orientation: 0 = horizontal, 1 = vertical
    
    while (!queue.isEmpty()) {
        int[] curr = queue.poll();
        int x = curr[0], y = curr[1], ori = curr[2], dist = curr[3];
        
        if (x == n - 1 && y == n - 2 && ori == 0) return dist;
        
        // Move right
        if (ori == 0 && y + 2 < n && grid[x][y + 2] == 0 && !visited[x][y + 1][0]) {
            visited[x][y + 1][0] = true;
            queue.offer(new int[]{x, y + 1, 0, dist + 1});
        }
        
        // Move down
        if (ori == 1 && x + 2 < n && grid[x + 2][y] == 0 && !visited[x + 1][y][1]) {
            visited[x + 1][y][1] = true;
            queue.offer(new int[]{x + 1, y, 1, dist + 1});
        }
        
        // Move down (horizontal)
        if (ori == 0 && x + 1 < n && grid[x + 1][y] == 0 && grid[x + 1][y + 1] == 0) {
            if (!visited[x + 1][y][0]) {
                visited[x + 1][y][0] = true;
                queue.offer(new int[]{x + 1, y, 0, dist + 1});
            }
            // Rotate to vertical
            if (!visited[x][y][1]) {
                visited[x][y][1] = true;
                queue.offer(new int[]{x, y, 1, dist + 1});
            }
        }
        
        // Move right (vertical)
        if (ori == 1 && y + 1 < n && grid[x][y + 1] == 0 && grid[x + 1][y + 1] == 0) {
            if (!visited[x][y + 1][1]) {
                visited[x][y + 1][1] = true;
                queue.offer(new int[]{x, y + 1, 1, dist + 1});
            }
            // Rotate to horizontal
            if (!visited[x][y][0]) {
                visited[x][y][0] = true;
                queue.offer(new int[]{x, y, 0, dist + 1});
            }
        }
    }
    return -1;
}
```

Snake ki 2 orientations hain - horizontal (0) aur vertical (1). State space BFS karo. Move right, down, rotate possible.

---

### 12. Escape the Spreading Fire
**Problem:** Can person reach safe house before fire spreads?

```java
public int maximumMinutes(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    int[][] fireTime = new int[m][n];
    for (int[] row : fireTime) Arrays.fill(row, Integer.MAX_VALUE);
    
    // Multi-source BFS for fire spread
    Queue<int[]> fireQueue = new LinkedList<>();
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (grid[i][j] == 1) {
                fireQueue.offer(new int[]{i, j});
                fireTime[i][j] = 0;
            }
        }
    }
    
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    while (!fireQueue.isEmpty()) {
        int[] curr = fireQueue.poll();
        for (int[] dir : dirs) {
            int x = curr[0] + dir[0];
            int y = curr[1] + dir[1];
            if (x >= 0 && x < m && y >= 0 && y < n && grid[x][y] != 2 && fireTime[x][y] == Integer.MAX_VALUE) {
                fireTime[x][y] = fireTime[curr[0]][curr[1]] + 1;
                fireQueue.offer(new int[]{x, y});
            }
        }
    }
    
    // Binary search on waiting time
    int left = 0, right = m * n, ans = -1;
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (canReach(grid, fireTime, mid)) {
            ans = mid;
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    return ans;
}

private boolean canReach(int[][] grid, int[][] fireTime, int waitTime) {
    int m = grid.length, n = grid[0].length;
    boolean[][] visited = new boolean[m][n];
    Queue<int[]> queue = new LinkedList<>();
    
    if (fireTime[0][0] <= waitTime) return false;
    queue.offer(new int[]{0, 0, waitTime});
    visited[0][0] = true;
    
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    
    while (!queue.isEmpty()) {
        int[] curr = queue.poll();
        int x = curr[0], y = curr[1], time = curr[2];
        
        for (int[] dir : dirs) {
            int nx = x + dir[0];
            int ny = y + dir[1];
            if (nx == m - 1 && ny == n - 1) {
                if (fireTime[nx][ny] > time + 1) return true;
                if (fireTime[nx][ny] == Integer.MAX_VALUE) return true;
            }
            if (nx >= 0 && nx < m && ny >= 0 && ny < n && grid[nx][ny] != 2 && !visited[nx][ny]) {
                if (fireTime[nx][ny] > time + 1) {
                    visited[nx][ny] = true;
                    queue.offer(new int[]{nx, ny, time + 1});
                }
            }
        }
    }
    return false;
}
```

Pehle fire ka spread time calculate karo (multi-source BFS). Phir binary search on waiting time. Check karo ki person fire se pehle pahunch sakta hai ya nahi.

---

### 13. Minimum Time to Visit a Cell In a Grid
**Problem:** Minimum time to reach bottom-right with time constraints.

```java
public int minimumTime(int[][] grid) {
    if (grid[0][1] > 1 && grid[1][0] > 1) return -1;
    
    int m = grid.length, n = grid[0].length;
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    int[][] time = new int[m][n];
    for (int[] row : time) Arrays.fill(row, Integer.MAX_VALUE);
    
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
    pq.offer(new int[]{0, 0, 0});
    time[0][0] = 0;
    
    while (!pq.isEmpty()) {
        int[] curr = pq.poll();
        int x = curr[0], y = curr[1], t = curr[2];
        
        if (x == m - 1 && y == n - 1) return t;
        if (t > time[x][y]) continue;
        
        for (int[] dir : dirs) {
            int nx = x + dir[0];
            int ny = y + dir[1];
            if (nx < 0 || nx >= m || ny < 0 || ny >= n) continue;
            
            int wait = 0;
            if (grid[nx][ny] > t + 1) {
                int diff = grid[nx][ny] - (t + 1);
                wait = (diff + 1) / 2 * 2;
            }
            int newTime = t + 1 + wait;
            
            if (newTime < time[nx][ny]) {
                time[nx][ny] = newTime;
                pq.offer(new int[]{nx, ny, newTime});
            }
        }
    }
    return -1;
}
```

Dijkstra-like approach. Agar cell mein time constraint hai toh wait karna padega. Wait time parity based hota hai.

---

### 14. Minimum Number of Flips to Convert Binary Matrix (Multi-Source BFS)
**Problem:** Minimum flips to make all cells same.

```java
public int minFlips(int[][] mat) {
    int m = mat.length, n = mat[0].length;
    int startMask = 0;
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (mat[i][j] == 1) startMask |= (1 << (i * n + j));
        }
    }
    
    if (startMask == 0) return 0;
    
    Set<Integer> visited = new HashSet<>();
    Queue<Integer> queue = new LinkedList<>();
    queue.offer(startMask);
    visited.add(startMask);
    int flips = 0;
    
    int[][] dirs = {{0,0},{0,1},{0,-1},{1,0},{-1,0}};
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            int mask = queue.poll();
            if (mask == 0) return flips;
            
            for (int cell = 0; cell < m * n; cell++) {
                int newMask = mask;
                int x = cell / n;
                int y = cell % n;
                
                for (int[] dir : dirs) {
                    int nx = x + dir[0];
                    int ny = y + dir[1];
                    if (nx >= 0 && nx < m && ny >= 0 && ny < n) {
                        int pos = nx * n + ny;
                        newMask ^= (1 << pos);
                    }
                }
                
                if (!visited.contains(newMask)) {
                    visited.add(newMask);
                    queue.offer(newMask);
                }
            }
        }
        flips++;
    }
    return -1;
}
```

Bitmask BFS. Har flip operation cell aur uske neighbors ko toggle karta hai. Jab mask 0 ho jaye, answer mil gaya.

---

### 15. Maximum Number of Fish in a Grid (Multi-Source BFS for multiple components)
**Problem:** Find maximum fish in connected component.

```java
public int findMaxFish(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    int maxFish = 0;
    
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (grid[i][j] > 0) {
                maxFish = Math.max(maxFish, bfs(grid, i, j));
            }
        }
    }
    return maxFish;
}

private int bfs(int[][] grid, int i, int j) {
    int m = grid.length, n = grid[0].length;
    Queue<int[]> queue = new LinkedList<>();
    queue.offer(new int[]{i, j});
    int fish = grid[i][j];
    grid[i][j] = 0;
    
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    
    while (!queue.isEmpty()) {
        int[] curr = queue.poll();
        for (int[] dir : dirs) {
            int x = curr[0] + dir[0];
            int y = curr[1] + dir[1];
            if (x >= 0 && x < m && y >= 0 && y < n && grid[x][y] > 0) {
                fish += grid[x][y];
                grid[x][y] = 0;
                queue.offer(new int[]{x, y});
            }
        }
    }
    return fish;
}
```

Har water cell (positive fish) se BFS/DFS karo. Connected component ka total fish nikaalo. Maximum store karo.

---

### 16. Minimum Time to Spread Infection (Tree)
**Problem:** Minimum time to infect entire tree starting from given nodes.

```java
public int minTimeToInfect(int n, int[][] edges, int[] startNodes) {
    List<Integer>[] graph = new ArrayList[n];
    for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
    
    for (int[] edge : edges) {
        graph[edge[0]].add(edge[1]);
        graph[edge[1]].add(edge[0]);
    }
    
    Queue<Integer> queue = new LinkedList<>();
    int[] time = new int[n];
    Arrays.fill(time, -1);
    
    for (int start : startNodes) {
        queue.offer(start);
        time[start] = 0;
    }
    
    int maxTime = 0;
    
    while (!queue.isEmpty()) {
        int curr = queue.poll();
        maxTime = Math.max(maxTime, time[curr]);
        
        for (int neighbor : graph[curr]) {
            if (time[neighbor] == -1) {
                time[neighbor] = time[curr] + 1;
                queue.offer(neighbor);
            }
        }
    }
    return maxTime;
}
```

Multiple sources se BFS. Har node pe infection pahunchne ka time calculate karo. Maximum time answer hoga.

---

### 17. Shortest Path in a Grid with Obstacles Elimination (Multi-Source not, but similar)
**Problem:** Shortest path from top-left to bottom-right eliminating at most k obstacles.

```java
public int shortestPath(int[][] grid, int k) {
    int m = grid.length, n = grid[0].length;
    boolean[][][] visited = new boolean[m][n][k + 1];
    Queue<int[]> queue = new LinkedList<>();
    queue.offer(new int[]{0, 0, k, 0}); // x, y, remaining, steps
    visited[0][0][k] = true;
    
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    
    while (!queue.isEmpty()) {
        int[] curr = queue.poll();
        int x = curr[0], y = curr[1], rem = curr[2], steps = curr[3];
        
        if (x == m - 1 && y == n - 1) return steps;
        
        for (int[] dir : dirs) {
            int nx = x + dir[0];
            int ny = y + dir[1];
            if (nx < 0 || nx >= m || ny < 0 || ny >= n) continue;
            
            int newRem = rem;
            if (grid[nx][ny] == 1) newRem--;
            
            if (newRem >= 0 && !visited[nx][ny][newRem]) {
                visited[nx][ny][newRem] = true;
                queue.offer(new int[]{nx, ny, newRem, steps + 1});
            }
        }
    }
    return -1;
}
```

State space BFS - (x, y, remaining eliminations). 3D visited array maintain karo.

---

### 18. Minimum Cost to Make at Least One Valid Path in a Grid (0-1 BFS)
**Problem:** Minimum cost to make path from (0,0) to (m-1,n-1).

```java
public int minCost(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    int[][] cost = new int[m][n];
    for (int[] row : cost) Arrays.fill(row, Integer.MAX_VALUE);
    cost[0][0] = 0;
    
    Deque<int[]> deque = new ArrayDeque<>();
    deque.offerFirst(new int[]{0, 0});
    
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    
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

0-1 BFS. Agar direction match karta hai toh cost 0 (front of deque), warna cost 1 (back of deque).

---

### 19. Maximum Number of People That Can Be Caught in Tag (Multi-Source BFS)
**Problem:** Maximum people catchers can catch within given time.

```java
public int catchMaximumAmountofPeople(int[] team, int dist) {
    int n = team.length;
    List<Integer> catchers = new ArrayList<>();
    List<Integer> runners = new ArrayList<>();
    
    for (int i = 0; i < n; i++) {
        if (team[i] == 1) catchers.add(i);
        else runners.add(i);
    }
    
    int i = 0, j = 0, caught = 0;
    
    while (i < catchers.size() && j < runners.size()) {
        int catcher = catchers.get(i);
        int runner = runners.get(j);
        
        if (Math.abs(catcher - runner) <= dist) {
            caught++;
            i++;
            j++;
        } else if (runner < catcher - dist) {
            j++;
        } else {
            i++;
        }
    }
    return caught;
}
```

Two-pointer approach. Catchers aur runners ko sort karo. Greedy matching - har catcher nearest runner ko catch karega.

---

### 20. Minimum Moves to Spread Stones Over Grid (Multi-Source BFS on bipartite)
**Problem:** Minimum moves to make all cells have exactly one stone.

```java
public int minimumMoves(int[][] grid) {
    List<int[]> empty = new ArrayList<>();
    List<int[]> extra = new ArrayList<>();
    
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            if (grid[i][j] == 0) empty.add(new int[]{i, j});
            else if (grid[i][j] > 1) {
                for (int k = 0; k < grid[i][j] - 1; k++) {
                    extra.add(new int[]{i, j});
                }
            }
        }
    }
    
    return minMoves(empty, extra, 0, 0);
}

private int minMoves(List<int[]> empty, List<int[]> extra, int idx, int usedMask) {
    if (idx == empty.size()) return 0;
    
    int min = Integer.MAX_VALUE;
    for (int i = 0; i < extra.size(); i++) {
        if ((usedMask & (1 << i)) == 0) {
            int dist = Math.abs(empty.get(idx)[0] - extra.get(i)[0]) + 
                       Math.abs(empty.get(idx)[1] - extra.get(i)[1]);
            min = Math.min(min, dist + minMoves(empty, extra, idx + 1, usedMask | (1 << i)));
        }
    }
    return min;
}
```

Empty cells aur extra stones ki positions find karo. Minimum bipartite matching - har empty cell ko nearest extra stone assign karo.

---

## 📌 Quick Pattern Summary 

| Pattern | When to Use | Example Problems |
|---------|-------------|------------------|
| **Distance from multiple sources** | Nearest X from multiple points | 01 Matrix, Walls and Gates |
| **Parallel propagation** | Fire spread, infection spread | Rotting Oranges, Escape Fire |
| **Level-by-level expansion** | Find farthest cell | As Far from Land |
| **Two-phase BFS** | DFS + Multi-source BFS | Shortest Bridge |
| **State space BFS** | Multiple states (keys, masks) | Get All Keys, Flips |
| **0-1 BFS** | Two different costs | Min Cost Valid Path |
| **Binary Search + BFS** | Time-based problems | Escape Spreading Fire |

## ⚡ Pro Tips 

1. **Multi-source initialization** - Saare sources ko queue mein ek saath daalo
2. **Distance array** - Initialize sources with 0, others with INF
3. **Level tracking** - Queue size se level count karo ya distance array use karo
4. **3D visited** - Jab extra state ho (keys, remaining eliminations)
5. **Bidirectional BFS** - For better performance in large state space
6. **Parity matters** - Kabhi kabhi waiting time parity pe depend karta hai
