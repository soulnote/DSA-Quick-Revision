# Theory - Simple Explanation

### Problem Statement Samjho
Tumhare paas ek array hai `[3, 1, 4, 1, 5, 9, 2, 6]`
Tumhe baar-baar karna hai:
1. **Range Sum** - index 2 se 5 tak ka sum nikaalna
2. **Point Update** - index 3 ki value change karna

**Naive approach** - Har query O(n) time. Agar 10^5 queries aayi toh TLE.

**Solution** - Segment Tree ya Fenwick Tree use karo. **O(log n)** per query.

---

### 🎯 Concept 1: Prefix Sum Array (Simple but Limited)

```java
// Prefix sum array
int[] arr = {3, 1, 4, 1, 5, 9, 2, 6};
int[] prefix = {3, 4, 8, 9, 14, 23, 25, 31};

// Range sum [l, r] = prefix[r] - prefix[l-1]
// Sum [2,5] = prefix[5] - prefix[1] = 23 - 4 = 19

// Problem: Update karna expensive O(n)
// arr[2] = 10 change karna hai toh saare prefix update karne padenge
```

**Limitation:** Update O(n) time. Isliye Segment Tree / Fenwick Tree.

---

### 🌳 Segment Tree - Visual Understanding

**Soch:** Array ko tree ki tarah represent karo. Har node ek range ka sum store karta hai.

```
Array: [3, 1, 4, 1, 5, 9, 2, 6]

Root: [0,7] sum = 31
Left Child [0,3] sum = 9, Right Child [4,7] sum = 22
[0,1] sum=4, [2,3] sum=5, [4,5] sum=14, [6,7] sum=8
[0]3, [1]1, [2]4, [3]1, [4]5, [5]9, [6]2, [7]6
```

**Properties:**
- Leaf nodes: Actual array elements
- Internal nodes: Sum of children
- Total nodes: ~4*n (safe size)

**Operations:**
1. **Build** - O(n) time
2. **Update** - O(log n) time
3. **Query** (range sum) - O(log n) time

---

### 🔄 Fenwick Tree (Binary Indexed Tree) - Simpler Alternative

**Soch:** Prefix sums ko binary representation mein store karo.

**Fenwick Tree ka magic:**
- Implementation **super easy** (15 lines code)
- Memory **kam** lagti hai (n+1 size)
- Sirf **prefix sums** aur **point updates** ke liye best

**Limitation:** 
- Sirf **associative** operations (sum, XOR, min, max)
- Range updates tricky hain

---

## 📊 Comparison Table

| Feature | Segment Tree | Fenwick Tree |
|---------|--------------|--------------|
| **Code Length** | 30-40 lines | 15-20 lines |
| **Memory** | 4*n | n+1 |
| **Range Sum** | O(log n) | O(log n) |
| **Point Update** | O(log n) | O(log n) |
| **Range Update** | Easy with lazy | Complex |
| **Min/Max Query** | Yes | No (only sum/xor) |
| **Use Case** | Complex queries | Simple prefix sums |

---

## 🏗️ Segment Tree Implementation (Step by Step)

### Step 1: Build the Tree

```java
class SegmentTree {
    int[] tree;
    int n;
    
    public SegmentTree(int[] arr) {
        n = arr.length;
        tree = new int[4 * n];
        build(arr, 0, 0, n - 1);
    }
    
    // node: current node index
    // start, end: range of current node
    private void build(int[] arr, int node, int start, int end) {
        if (start == end) {
            // Leaf node
            tree[node] = arr[start];
            return;
        }
        
        int mid = start + (end - start) / 2;
        int leftChild = 2 * node + 1;
        int rightChild = 2 * node + 2;
        
        build(arr, leftChild, start, mid);
        build(arr, rightChild, mid + 1, end);
        
        // Internal node = sum of children
        tree[node] = tree[leftChild] + tree[rightChild];
    }
}
```


- Base case: `start == end` → leaf node, array ki value store karo
- Left child: `[start, mid]`
- Right child: `[mid+1, end]`
- Parent sum = left sum + right sum

---

### Step 2: Range Sum Query

```java
public int query(int l, int r) {
    return query(0, 0, n - 1, l, r);
}

private int query(int node, int start, int end, int l, int r) {
    // Case 1: No overlap
    if (r < start || l > end) {
        return 0;
    }
    
    // Case 2: Complete overlap
    if (l <= start && end <= r) {
        return tree[node];
    }
    
    // Case 3: Partial overlap
    int mid = start + (end - start) / 2;
    int leftChild = 2 * node + 1;
    int rightChild = 2 * node + 2;
    
    int leftSum = query(leftChild, start, mid, l, r);
    int rightSum = query(rightChild, mid + 1, end, l, r);
    
    return leftSum + rightSum;
}
```


- **No overlap** (l > end ya r < start) → return 0
- **Complete overlap** (l <= start && end <= r) → return node value
- **Partial overlap** → left aur right child se query karo

---

### Step 3: Point Update

```java
public void update(int idx, int val) {
    update(0, 0, n - 1, idx, val);
}

private void update(int node, int start, int end, int idx, int val) {
    if (start == end) {
        tree[node] = val;
        return;
    }
    
    int mid = start + (end - start) / 2;
    int leftChild = 2 * node + 1;
    int rightChild = 2 * node + 2;
    
    if (idx <= mid) {
        update(leftChild, start, mid, idx, val);
    } else {
        update(rightChild, mid + 1, end, idx, val);
    }
    
    tree[node] = tree[leftChild] + tree[rightChild];
}
```


- Leaf node tak pahuncho aur value update karo
- Wapas aate time sabhi parent nodes ka sum recalculate karo

---

## 🔧 Fenwick Tree (Binary Indexed Tree) Implementation

### Core Theory

Fenwick Tree ek array hai `BIT[]` jisme `BIT[i]` store karta hai sum of range `(i - (i & -i) + 1)` to `i`

**Key Formula:**
- `i & -i` → Last set bit (LSB) nikalta hai
- Example: i = 6 (110) → LSB = 2 (010)

```
Array:    [3, 1, 4, 1, 5, 9, 2, 6]
Index:     1  2  3  4  5  6  7  8  (1-based)

Fenwick Tree:
BIT[1] = arr[1] = 3
BIT[2] = arr[1] + arr[2] = 3 + 1 = 4
BIT[3] = arr[3] = 4
BIT[4] = arr[1..4] = 3+1+4+1 = 9
BIT[5] = arr[5] = 5
BIT[6] = arr[5] + arr[6] = 5+9 = 14
BIT[7] = arr[7] = 2
BIT[8] = arr[1..8] = 31
```

### Complete Implementation

```java
class FenwickTree {
    int[] bit;
    int n;
    
    public FenwickTree(int n) {
        this.n = n;
        bit = new int[n + 1]; // 1-based indexing
    }
    
    // Build from array
    public FenwickTree(int[] arr) {
        this(arr.length);
        for (int i = 0; i < arr.length; i++) {
            update(i + 1, arr[i]); // 1-based index
        }
    }
    
    // Point update: add delta at index i (1-based)
    public void update(int i, int delta) {
        while (i <= n) {
            bit[i] += delta;
            i += i & -i; // Move to parent
        }
    }
    
    // Prefix sum from 1 to i
    public int prefixSum(int i) {
        int sum = 0;
        while (i > 0) {
            sum += bit[i];
            i -= i & -i; // Move to previous range
        }
        return sum;
    }
    
    // Range sum [l, r] (1-based inclusive)
    public int rangeSum(int l, int r) {
        return prefixSum(r) - prefixSum(l - 1);
    }
}
```

**Explanation:**

```
Update Operation (i = 5):
BIT[5] update karo
Then i = 5 + (5 & -5) = 5 + 1 = 6 → BIT[6] update
Then i = 6 + (6 & -6) = 6 + 2 = 8 → BIT[8] update
Stop when i > n

Prefix Sum (i = 7):
Sum = BIT[7]
i = 7 - (7 & -7) = 7 - 1 = 6 → Add BIT[6]
i = 6 - (6 & -6) = 6 - 2 = 4 → Add BIT[4]
i = 4 - (4 & -4) = 4 - 4 = 0 → Stop
```

---

## 📋 Top 10 Most Asked Problems

---

### 1. Range Sum Query - Mutable
**Problem:** Array hai, point update aur range sum query.

```java
class NumArray {
    FenwickTree ft;
    int[] nums;
    
    public NumArray(int[] nums) {
        this.nums = nums;
        ft = new FenwickTree(nums.length);
        for (int i = 0; i < nums.length; i++) {
            ft.update(i + 1, nums[i]);
        }
    }
    
    public void update(int index, int val) {
        int delta = val - nums[index];
        nums[index] = val;
        ft.update(index + 1, delta);
    }
    
    public int sumRange(int left, int right) {
        return ft.rangeSum(left + 1, right + 1);
    }
}
```

Fenwick Tree se direct ho jayega. Update mein delta calculate karo.

---

### 2. Count of Smaller Numbers After Self
**Problem:** Har element ke liye count karo kitne smaller elements uske right mein hain.

```java
public List<Integer> countSmaller(int[] nums) {
    int n = nums.length;
    List<Integer> result = new ArrayList<>();
    
    // Coordinate compression (kyuki values negative bhi ho sakti hain)
    int[] sorted = nums.clone();
    Arrays.sort(sorted);
    Map<Integer, Integer> rank = new HashMap<>();
    for (int i = 0; i < n; i++) {
        rank.put(sorted[i], i + 1);
    }
    
    FenwickTree ft = new FenwickTree(n);
    
    for (int i = n - 1; i >= 0; i--) {
        int r = rank.get(nums[i]);
        result.add(0, ft.prefixSum(r - 1)); // Count of numbers < current
        ft.update(r, 1);
    }
    
    return result;
}
```

Right se left traverse karo. Fenwick Tree mein frequencies store karo. Prefix sum se smaller elements count mil jayenge.

---

### 3. Reverse Pairs
**Problem:** Count pairs (i, j) where i < j and nums[i] > 2 * nums[j].

```java
public int reversePairs(int[] nums) {
    int n = nums.length;
    // Coordinate compression for nums and 2*nums
    long[] sorted = new long[2 * n];
    for (int i = 0; i < n; i++) {
        sorted[2 * i] = nums[i];
        sorted[2 * i + 1] = 2L * nums[i];
    }
    Arrays.sort(sorted);
    Map<Long, Integer> rank = new HashMap<>();
    for (int i = 0; i < 2 * n; i++) {
        rank.put(sorted[i], i + 1);
    }
    
    FenwickTree ft = new FenwickTree(2 * n);
    int count = 0;
    
    for (int i = n - 1; i >= 0; i--) {
        long target = 2L * nums[i];
        int idx = rank.get(target);
        count += ft.prefixSum(idx - 1);
        ft.update(rank.get((long)nums[i]), 1);
    }
    return count;
}
```

2*nums[i] ko bhi compress karo. Right se left traverse karo, prefix sum se count nikaalo.

---

### 4. Number of Longest Increasing Subsequence (Using Segment Tree)
**Problem:** Count of LIS.

```java
class Node {
    int length;
    int count;
    
    Node(int length, int count) {
        this.length = length;
        this.count = count;
    }
}

public int findNumberOfLIS(int[] nums) {
    int n = nums.length;
    // Coordinate compression
    int[] sorted = nums.clone();
    Arrays.sort(sorted);
    Map<Integer, Integer> rank = new HashMap<>();
    for (int i = 0; i < n; i++) {
        rank.put(sorted[i], i + 1);
    }
    
    SegmentTree seg = new SegmentTree(n);
    
    for (int num : nums) {
        int r = rank.get(num);
        Node best = seg.query(1, r - 1);
        int length = best.length + 1;
        int count = best.count;
        if (count == 0) count = 1;
        seg.update(r, new Node(length, count));
    }
    
    Node overall = seg.query(1, n);
    return overall.count;
}

class SegmentTree {
    Node[] tree;
    int n;
    
    SegmentTree(int n) {
        this.n = n;
        tree = new Node[4 * n];
        for (int i = 0; i < 4 * n; i++) {
            tree[i] = new Node(0, 0);
        }
    }
    
    Node merge(Node left, Node right) {
        if (left.length > right.length) return left;
        if (right.length > left.length) return right;
        return new Node(left.length, left.count + right.count);
    }
    
    void update(int idx, Node val) {
        update(1, 1, n, idx, val);
    }
    
    void update(int node, int start, int end, int idx, Node val) {
        if (start == end) {
            if (tree[node].length < val.length) {
                tree[node] = val;
            } else if (tree[node].length == val.length) {
                tree[node].count += val.count;
            }
            return;
        }
        int mid = (start + end) / 2;
        if (idx <= mid) update(node * 2, start, mid, idx, val);
        else update(node * 2 + 1, mid + 1, end, idx, val);
        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }
    
    Node query(int l, int r) {
        if (l > r) return new Node(0, 0);
        return query(1, 1, n, l, r);
    }
    
    Node query(int node, int start, int end, int l, int r) {
        if (l > end || r < start) return new Node(0, 0);
        if (l <= start && end <= r) return tree[node];
        int mid = (start + end) / 2;
        return merge(query(node * 2, start, mid, l, r),
                     query(node * 2 + 1, mid + 1, end, l, r));
    }
}
```

Segment Tree store karta hai (max_length, count). Query se pehle best LIS milega.

---

### 5. Range Sum Query 2D - Mutable
**Problem:** 2D grid mein point update aur submatrix sum.

```java
class NumMatrix {
    FenwickTree2D ft;
    int[][] matrix;
    int m, n;
    
    public NumMatrix(int[][] matrix) {
        this.matrix = matrix;
        this.m = matrix.length;
        this.n = matrix[0].length;
        ft = new FenwickTree2D(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                ft.update(i + 1, j + 1, matrix[i][j]);
            }
        }
    }
    
    public void update(int row, int col, int val) {
        int delta = val - matrix[row][col];
        matrix[row][col] = val;
        ft.update(row + 1, col + 1, delta);
    }
    
    public int sumRegion(int row1, int col1, int row2, int col2) {
        return ft.sum(row2 + 1, col2 + 1) 
             - ft.sum(row1, col2 + 1)
             - ft.sum(row2 + 1, col1)
             + ft.sum(row1, col1);
    }
}

class FenwickTree2D {
    int[][] bit;
    int m, n;
    
    FenwickTree2D(int m, int n) {
        this.m = m;
        this.n = n;
        bit = new int[m + 2][n + 2];
    }
    
    void update(int x, int y, int delta) {
        for (int i = x; i <= m; i += i & -i) {
            for (int j = y; j <= n; j += j & -j) {
                bit[i][j] += delta;
            }
        }
    }
    
    int sum(int x, int y) {
        int s = 0;
        for (int i = x; i > 0; i -= i & -i) {
            for (int j = y; j > 0; j -= j & -j) {
                s += bit[i][j];
            }
        }
        return s;
    }
}
```

2D Fenwick Tree. Inclusion-exclusion principle se submatrix sum nikaalo.

---

### 6. Count of Range Sum
**Problem:** Count subarrays jinka sum [lower, upper] ke beech mein ho.

```java
public int countRangeSum(int[] nums, int lower, int upper) {
    int n = nums.length;
    long[] prefix = new long[n + 1];
    for (int i = 0; i < n; i++) {
        prefix[i + 1] = prefix[i] + nums[i];
    }
    
    // Coordinate compression
    long[] sorted = new long[3 * n + 3];
    for (int i = 0; i <= n; i++) {
        sorted[3 * i] = prefix[i];
        sorted[3 * i + 1] = prefix[i] - lower;
        sorted[3 * i + 2] = prefix[i] - upper;
    }
    Arrays.sort(sorted);
    Map<Long, Integer> rank = new HashMap<>();
    for (int i = 0; i < sorted.length; i++) {
        rank.put(sorted[i], i + 1);
    }
    
    FenwickTree ft = new FenwickTree(sorted.length);
    int count = 0;
    
    for (int i = 0; i <= n; i++) {
        int left = rank.get(prefix[i] - upper);
        int right = rank.get(prefix[i] - lower);
        count += ft.rangeSum(left, right);
        ft.update(rank.get(prefix[i]), 1);
    }
    return count;
}
```

Prefix sum array banao. prefix[j] - prefix[i] = sum[i+1..j]. Condition: lower ≤ prefix[j] - prefix[i] ≤ upper → prefix[j] - upper ≤ prefix[i] ≤ prefix[j] - lower.

---

### 7. Minimum Number of Increments on Subarrays to Form a Target Array (Using Segment Tree)
**Problem:** Minimum operations to convert zero array to target using range increments.

```java
public int minNumberOperations(int[] target) {
    int n = target.length;
    SegmentTree seg = new SegmentTree(target);
    return seg.solve(0, n - 1, 0);
}

class SegmentTree {
    int[] tree;
    int[] arr;
    
    SegmentTree(int[] arr) {
        this.arr = arr;
        tree = new int[4 * arr.length];
        build(1, 0, arr.length - 1);
    }
    
    void build(int node, int l, int r) {
        if (l == r) {
            tree[node] = l;
            return;
        }
        int mid = (l + r) / 2;
        build(node * 2, l, mid);
        build(node * 2 + 1, mid + 1, r);
        tree[node] = arr[tree[node * 2]] <= arr[tree[node * 2 + 1]] 
                     ? tree[node * 2] : tree[node * 2 + 1];
    }
    
    int query(int node, int l, int r, int ql, int qr) {
        if (ql > r || qr < l) return -1;
        if (ql <= l && r <= qr) return tree[node];
        int mid = (l + r) / 2;
        int left = query(node * 2, l, mid, ql, qr);
        int right = query(node * 2 + 1, mid + 1, r, ql, qr);
        if (left == -1) return right;
        if (right == -1) return left;
        return arr[left] <= arr[right] ? left : right;
    }
    
    int solve(int l, int r, int base) {
        if (l > r) return 0;
        int minIdx = query(1, 0, arr.length - 1, l, r);
        int minVal = arr[minIdx];
        int ops = (minVal - base);
        ops += solve(l, minIdx - 1, minVal);
        ops += solve(minIdx + 1, r, minVal);
        return ops;
    }
}
```

Divide and conquer + Segment Tree. Minimum value dhundho, usko base se subtract karo, left aur right recursively solve karo.

---

### 8. Create Sorted Array Through Instructions
**Problem:** Count cost to insert elements in sorted order.

```java
public int createSortedArray(int[] instructions) {
    int MOD = 1000000007;
    int max = 100000;
    FenwickTree ft = new FenwickTree(max + 2);
    long cost = 0;
    
    for (int i = 0; i < instructions.length; i++) {
        int num = instructions[i];
        int leftCount = ft.prefixSum(num - 1);
        int rightCount = ft.prefixSum(max) - ft.prefixSum(num);
        cost = (cost + Math.min(leftCount, rightCount)) % MOD;
        ft.update(num, 1);
    }
    return (int) cost;
}
```

Fenwick Tree mein frequencies store karo. Left count = elements < num. Right count = elements > num. Minimum cost add karo.

---

### 9. Number of Ways to Build House of Cards (Segment Tree DP)
**Problem:** Count ways to build stable house of cards.

```java
public int houseOfCards(int n) {
    int MOD = 1000000007;
    int maxHeight = (int) Math.sqrt(n * 2);
    
    // DP[h][c] = ways to build height h with c cards
    int[][] dp = new int[maxHeight + 2][n + 1];
    dp[0][0] = 1;
    
    SegmentTree[] segTrees = new SegmentTree[maxHeight + 2];
    for (int i = 0; i <= maxHeight + 1; i++) {
        segTrees[i] = new SegmentTree(n + 2);
    }
    segTrees[0].update(0, 1);
    
    for (int h = 1; h <= maxHeight; h++) {
        int cardsNeeded = h * (3 * h - 1) / 2;
        for (int c = cardsNeeded; c <= n; c++) {
            dp[h][c] = segTrees[h - 1].rangeSum(0, c - cardsNeeded);
            segTrees[h].update(c, dp[h][c]);
        }
    }
    
    int ans = 0;
    for (int h = 1; h <= maxHeight; h++) {
        ans = (ans + dp[h][n]) % MOD;
    }
    return ans;
}
```

DP with Segment Tree optimization. `dp[h][c] = sum(dp[h-1][c - cardsNeeded])`

---

### 10. Maximum Sum of 3 Non-Overlapping Subarrays (Using Segment Tree for optimization)
**Problem:** Find max sum of 3 non-overlapping subarrays of size k.

```java
public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
    int n = nums.length;
    int[] sum = new int[n - k + 1];
    int s = 0;
    for (int i = 0; i < n; i++) {
        s += nums[i];
        if (i >= k) s -= nums[i - k];
        if (i >= k - 1) sum[i - k + 1] = s;
    }
    
    int m = sum.length;
    int[] left = new int[m];
    int[] right = new int[m];
    
    left[0] = 0;
    for (int i = 1; i < m; i++) {
        left[i] = sum[left[i - 1]] >= sum[i] ? left[i - 1] : i;
    }
    
    right[m - 1] = m - 1;
    for (int i = m - 2; i >= 0; i--) {
        right[i] = sum[right[i + 1]] > sum[i] ? right[i + 1] : i;
    }
    
    int[] ans = new int[3];
    int max = 0;
    for (int i = k; i < m - k; i++) {
        int l = left[i - k];
        int r = right[i + k];
        int total = sum[l] + sum[i] + sum[r];
        if (total > max) {
            max = total;
            ans = new int[]{l, i, r};
        }
    }
    return ans;
}
```

Prefix sum se har k-size subarray ka sum nikaalo. Left aur right max arrays banao. Middle subarray fix karke check karo.

---

## 📊 Complexity Summary

| Operation | Segment Tree | Fenwick Tree |
|-----------|--------------|--------------|
| Build | O(n) | O(n log n) |
| Point Update | O(log n) | O(log n) |
| Range Query | O(log n) | O(log n) |
| Space | O(n) | O(n) |

## ⚡ Pro Tips

1. **Fenwick Tree simple hai** - Range sum + point update ke liye hamesha Fenwick use karo
2. **Segment Tree complex queries** - Min/Max, GCD, custom operations ke liye Segment Tree
3. **Coordinate compression** - Jab values bahut badi hon toh rank assign karo
4. **1-based indexing** - Fenwick Tree mein hamesha 1-based index use karo
5. **Lazy propagation** - Range updates ke liye Segment Tree mein lazy propagation seekh lo
