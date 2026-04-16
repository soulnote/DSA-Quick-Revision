# Heap Data Structure

## **Heap Basics**

Heap ek **complete binary tree** hai jo **heap property** follow karta hai. Priority Queue implement karne ke liye use hota hai.

### **Types of Heap**

| Type | Property | Use Case |
|------|----------|----------|
| **Max Heap** | Parent ≥ Children | Get maximum quickly |
| **Min Heap** | Parent ≤ Children | Get minimum quickly |

### **Key Characteristics**
- **Complete Binary Tree:** Last level ko chhodkar saare levels full hain, last level left to right filled hai
- **Array Representation:** Parent aur children ke beech formula-based access
- **Height:** O(log n)

### **Array Indexing Formulas (0-based index)**

```
For node at index i:
- Parent: (i - 1) / 2
- Left Child: 2*i + 1
- Right Child: 2*i + 2
```

### **Time Complexities**

| Operation | Time | Description |
|-----------|------|-------------|
| Get Min/Max | O(1) | Just peek at root |
| Insert | O(log n) | Heapify up |
| Extract Min/Max | O(log n) | Remove root, heapify down |
| Delete | O(log n) | Heapify up/down |
| Heapify (build) | O(n) | Build from array |
| Peek | O(1) | View root |

---

## **Basic Heap Implementation**

```java
// Min Heap Implementation
class MinHeap {
    private int[] heap;
    private int size;
    private int capacity;
    
    public MinHeap(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.heap = new int[capacity];
    }
    
    // Helper methods
    private int parent(int i) { return (i - 1) / 2; }
    private int leftChild(int i) { return 2 * i + 1; }
    private int rightChild(int i) { return 2 * i + 2; }
    
    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
    
    // Insert operation
    public void insert(int val) {
        if(size == capacity) {
            System.out.println("Heap is full");
            return;
        }
        
        heap[size] = val;
        size++;
        heapifyUp(size - 1);
    }
    
    private void heapifyUp(int i) {
        while(i > 0 && heap[parent(i)] > heap[i]) {
            swap(i, parent(i));
            i = parent(i);
        }
    }
    
    // Extract minimum
    public int extractMin() {
        if(size == 0) return -1;
        
        int root = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown(0);
        
        return root;
    }
    
    private void heapifyDown(int i) {
        int smallest = i;
        int left = leftChild(i);
        int right = rightChild(i);
        
        if(left < size && heap[left] < heap[smallest]) {
            smallest = left;
        }
        if(right < size && heap[right] < heap[smallest]) {
            smallest = right;
        }
        
        if(smallest != i) {
            swap(i, smallest);
            heapifyDown(smallest);
        }
    }
    
    // Get minimum without removing
    public int getMin() {
        return size > 0 ? heap[0] : -1;
    }
    
    // Get size
    public int getSize() {
        return size;
    }
    
    // Check if empty
    public boolean isEmpty() {
        return size == 0;
    }
    
    // Build heap from array (O(n) time)
    public void buildHeap(int[] arr) {
        if(arr.length > capacity) return;
        
        for(int i = 0; i < arr.length; i++) {
            heap[i] = arr[i];
        }
        size = arr.length;
        
        // Heapify from last non-leaf node
        for(int i = (size - 2) / 2; i >= 0; i--) {
            heapifyDown(i);
        }
    }
}

// Max Heap Implementation (just change comparison)
class MaxHeap {
    private int[] heap;
    private int size;
    
    public MaxHeap(int capacity) {
        heap = new int[capacity];
        size = 0;
    }
    
    private void heapifyUp(int i) {
        while(i > 0 && heap[parent(i)] < heap[i]) {  // Note: < changed to >
            swap(i, parent(i));
            i = parent(i);
        }
    }
    
    private void heapifyDown(int i) {
        int largest = i;
        int left = leftChild(i);
        int right = rightChild(i);
        
        if(left < size && heap[left] > heap[largest]) {  // > for max heap
            largest = left;
        }
        if(right < size && heap[right] > heap[largest]) {
            largest = right;
        }
        
        if(largest != i) {
            swap(i, largest);
            heapifyDown(largest);
        }
    }
    
    // Other methods similar to MinHeap
}
```

---

## **1. Kth Largest Element in Array** ⭐⭐⭐

### Theory
**Problem:** Array mein kth largest element find karo.

**Approaches:**
1. **Sorting:** O(n log n) time
2. **Min Heap of size k:** O(n log k) time
3. **Quick Select:** O(n) average, O(n²) worst

**Algorithm (Min Heap of size k):**
- Min heap mein first k elements daalo
- Baaki elements ke liye: agar element > heap root hai to root remove karo aur new element daalo
- End mein heap ka root hi kth largest hai

### Java Code
```java
class KthLargest {
    // Method 1: Min Heap (Most efficient for streaming)
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        
        // Add first k elements
        for(int i = 0; i < k; i++) {
            minHeap.add(nums[i]);
        }
        
        // Process remaining elements
        for(int i = k; i < nums.length; i++) {
            if(nums[i] > minHeap.peek()) {
                minHeap.poll();
                minHeap.add(nums[i]);
            }
        }
        
        return minHeap.peek();
    }
    
    // Method 2: Max Heap (Simpler to understand)
    public int findKthLargestMaxHeap(int[] nums, int k) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        
        for(int num : nums) {
            maxHeap.add(num);
        }
        
        for(int i = 0; i < k - 1; i++) {
            maxHeap.poll();
        }
        
        return maxHeap.peek();
    }
    
    // Method 3: Quick Select
    public int findKthLargestQuickSelect(int[] nums, int k) {
        return quickSelect(nums, 0, nums.length - 1, nums.length - k);
    }
    
    private int quickSelect(int[] nums, int left, int right, int k) {
        int pivot = partition(nums, left, right);
        
        if(pivot == k) return nums[pivot];
        else if(pivot < k) return quickSelect(nums, pivot + 1, right, k);
        else return quickSelect(nums, left, pivot - 1, k);
    }
    
    private int partition(int[] nums, int left, int right) {
        int pivot = nums[right];
        int i = left;
        
        for(int j = left; j < right; j++) {
            if(nums[j] <= pivot) {
                swap(nums, i, j);
                i++;
            }
        }
        swap(nums, i, right);
        return i;
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    // Time: O(n log k) for heap, Space: O(k)
}
```

---

## **2. Kth Smallest Element in Array** ⭐⭐

### Theory
**Problem:** Array mein kth smallest element find karo.

**Algorithm (Max Heap of size k):**
- Max heap mein first k elements daalo
- Baaki elements ke liye: agar element < heap root hai to root remove karo aur new element daalo
- End mein heap ka root hi kth smallest hai

### Java Code
```java
class KthSmallest {
    // Method 1: Max Heap
    public int findKthSmallest(int[] nums, int k) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        
        for(int i = 0; i < k; i++) {
            maxHeap.add(nums[i]);
        }
        
        for(int i = k; i < nums.length; i++) {
            if(nums[i] < maxHeap.peek()) {
                maxHeap.poll();
                maxHeap.add(nums[i]);
            }
        }
        
        return maxHeap.peek();
    }
    
    // Method 2: Min Heap (for streaming)
    public int findKthSmallestMinHeap(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        
        for(int num : nums) {
            minHeap.add(num);
        }
        
        for(int i = 0; i < k - 1; i++) {
            minHeap.poll();
        }
        
        return minHeap.peek();
    }
    
    // Time: O(n log k), Space: O(k)
}
```

---

## **3. Merge K Sorted Lists** ⭐⭐⭐

### Theory
**Problem:** K sorted linked lists ko merge karo into one sorted list.

**Algorithm (Min Heap):**
1. Saare lists ke first nodes min heap mein daalo
2. Heap se smallest node nikalo aur result mein add karo
3. Us node ke next ko heap mein daalo (agar exist karta hai)
4. Repeat until heap empty

### Java Code
```java
class MergeKSortedLists {
    public ListNode mergeKLists(ListNode[] lists) {
        if(lists == null || lists.length == 0) return null;
        
        // Min heap comparing node values
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(
            (a, b) -> a.val - b.val
        );
        
        // Add first node of each list
        for(ListNode node : lists) {
            if(node != null) {
                minHeap.add(node);
            }
        }
        
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        while(!minHeap.isEmpty()) {
            ListNode smallest = minHeap.poll();
            current.next = smallest;
            current = current.next;
            
            if(smallest.next != null) {
                minHeap.add(smallest.next);
            }
        }
        
        return dummy.next;
    }
    
    // Time: O(n log k) where n = total nodes, k = number of lists
    // Space: O(k)
}
```

---

## **4. Top K Frequent Elements** ⭐⭐⭐

### Theory
**Problem:** Array mein top k frequent elements find karo.

**Algorithm (Min Heap of size k):**
1. Frequency map banao (element -> count)
2. Min heap mein (count, element) daalo
3. Agar heap size > k ho to smallest count wala remove karo

### Java Code
```java
class TopKFrequent {
    public int[] topKFrequent(int[] nums, int k) {
        // Step 1: Count frequencies
        Map<Integer, Integer> freqMap = new HashMap<>();
        for(int num : nums) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }
        
        // Step 2: Min heap of size k
        PriorityQueue<Map.Entry<Integer, Integer>> minHeap = 
            new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());
        
        for(Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            minHeap.add(entry);
            if(minHeap.size() > k) {
                minHeap.poll();
            }
        }
        
        // Step 3: Extract result
        int[] result = new int[k];
        for(int i = 0; i < k; i++) {
            result[i] = minHeap.poll().getKey();
        }
        
        return result;
    }
    
    // Alternative: Bucket Sort (O(n))
    public int[] topKFrequentBucketSort(int[] nums, int k) {
        Map<Integer, Integer> freqMap = new HashMap<>();
        for(int num : nums) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }
        
        // Bucket array: index = frequency
        List<Integer>[] buckets = new List[nums.length + 1];
        for(int key : freqMap.keySet()) {
            int freq = freqMap.get(key);
            if(buckets[freq] == null) {
                buckets[freq] = new ArrayList<>();
            }
            buckets[freq].add(key);
        }
        
        // Collect top k
        int[] result = new int[k];
        int index = 0;
        for(int i = buckets.length - 1; i >= 0 && index < k; i--) {
            if(buckets[i] != null) {
                for(int num : buckets[i]) {
                    result[index++] = num;
                    if(index == k) break;
                }
            }
        }
        
        return result;
    }
    
    // Time: O(n log k) for heap, O(n) for bucket sort
    // Space: O(n)
}
```

---

## **5. K Closest Points to Origin** ⭐⭐

### Theory
**Problem:** Origin (0,0) se k closest points find karo.

**Algorithm (Max Heap of size k):**
- Max heap mein (distance², point) store karo
- Heap size k se bada ho to largest distance wala remove karo
- End mein heap mein k closest points honge

### Java Code
```java
class KClosestPoints {
    public int[][] kClosest(int[][] points, int k) {
        // Max heap based on distance
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>(
            (a, b) -> (b[0]*b[0] + b[1]*b[1]) - (a[0]*a[0] + a[1]*a[1])
        );
        
        for(int[] point : points) {
            maxHeap.add(point);
            if(maxHeap.size() > k) {
                maxHeap.poll();
            }
        }
        
        int[][] result = new int[k][2];
        for(int i = 0; i < k; i++) {
            result[i] = maxHeap.poll();
        }
        
        return result;
    }
    
    // Quick Select approach (O(n) average)
    public int[][] kClosestQuickSelect(int[][] points, int k) {
        quickSelect(points, 0, points.length - 1, k);
        return Arrays.copyOfRange(points, 0, k);
    }
    
    private void quickSelect(int[][] points, int left, int right, int k) {
        int pivotIndex = partition(points, left, right);
        
        if(pivotIndex == k) return;
        else if(pivotIndex < k) quickSelect(points, pivotIndex + 1, right, k);
        else quickSelect(points, left, pivotIndex - 1, k);
    }
    
    private int partition(int[][] points, int left, int right) {
        int[] pivot = points[right];
        int pivotDist = pivot[0]*pivot[0] + pivot[1]*pivot[1];
        int i = left;
        
        for(int j = left; j < right; j++) {
            int dist = points[j][0]*points[j][0] + points[j][1]*points[j][1];
            if(dist <= pivotDist) {
                swap(points, i, j);
                i++;
            }
        }
        swap(points, i, right);
        return i;
    }
    
    private void swap(int[][] points, int i, int j) {
        int[] temp = points[i];
        points[i] = points[j];
        points[j] = temp;
    }
    
    // Time: O(n log k) for heap, O(n) average for quick select
    // Space: O(k) for heap, O(log n) for quick select recursion
}
```

---

## **6. Sort Characters By Frequency** ⭐⭐

### Theory
**Problem:** String ko characters ke frequency ke descending order mein sort karo.

**Algorithm (Max Heap):**
1. Frequency map banao
2. Max heap mein (frequency, character) daalo
3. Heap se pop karke characters repeat karo

### Java Code
```java
class SortByFrequency {
    public String frequencySort(String s) {
        // Count frequencies
        Map<Character, Integer> freqMap = new HashMap<>();
        for(char c : s.toCharArray()) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }
        
        // Max heap
        PriorityQueue<Map.Entry<Character, Integer>> maxHeap = 
            new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
        
        maxHeap.addAll(freqMap.entrySet());
        
        // Build result
        StringBuilder result = new StringBuilder();
        while(!maxHeap.isEmpty()) {
            Map.Entry<Character, Integer> entry = maxHeap.poll();
            char c = entry.getKey();
            int count = entry.getValue();
            
            for(int i = 0; i < count; i++) {
                result.append(c);
            }
        }
        
        return result.toString();
    }
    
    // Bucket Sort approach (O(n))
    public String frequencySortBucket(String s) {
        Map<Character, Integer> freqMap = new HashMap<>();
        int maxFreq = 0;
        
        for(char c : s.toCharArray()) {
            int freq = freqMap.getOrDefault(c, 0) + 1;
            freqMap.put(c, freq);
            maxFreq = Math.max(maxFreq, freq);
        }
        
        // Buckets: index = frequency
        List<Character>[] buckets = new List[maxFreq + 1];
        for(char c : freqMap.keySet()) {
            int freq = freqMap.get(c);
            if(buckets[freq] == null) {
                buckets[freq] = new ArrayList<>();
            }
            buckets[freq].add(c);
        }
        
        // Build result
        StringBuilder result = new StringBuilder();
        for(int i = maxFreq; i >= 0; i--) {
            if(buckets[i] != null) {
                for(char c : buckets[i]) {
                    for(int j = 0; j < i; j++) {
                        result.append(c);
                    }
                }
            }
        }
        
        return result.toString();
    }
    
    // Time: O(n log n) for heap, O(n) for bucket sort
}
```

---

## **7. Find Median from Data Stream** ⭐⭐⭐

### Theory
**Problem:** Data stream se real-time median find karo.

**Algorithm (Two Heaps):**
1. **Max heap** for left half (smaller numbers)
2. **Min heap** for right half (larger numbers)
3. Invariant: maxHeap.size() == minHeap.size() OR maxHeap.size() == minHeap.size() + 1
4. Median = maxHeap.peek() if sizes differ, else (maxHeap.peek() + minHeap.peek()) / 2

### Java Code
```java
class MedianFinder {
    private PriorityQueue<Integer> maxHeap;  // Left half (smaller numbers)
    private PriorityQueue<Integer> minHeap;  // Right half (larger numbers)
    
    public MedianFinder() {
        maxHeap = new PriorityQueue<>((a, b) -> b - a);
        minHeap = new PriorityQueue<>();
    }
    
    public void addNum(int num) {
        // Add to max heap first
        maxHeap.add(num);
        
        // Balance: move max from maxHeap to minHeap
        minHeap.add(maxHeap.poll());
        
        // Maintain size property
        if(minHeap.size() > maxHeap.size()) {
            maxHeap.add(minHeap.poll());
        }
    }
    
    public double findMedian() {
        if(maxHeap.size() > minHeap.size()) {
            return maxHeap.peek();
        } else {
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        }
    }
    
    // Time: O(log n) for addNum, O(1) for findMedian
    // Space: O(n)
}
```

---

## **8. Sliding Window Median** ⭐⭐⭐

### Theory
**Problem:** Har sliding window ka median find karo.

**Algorithm (Two Heaps + Lazy Removal):**
1. Two heaps maintain karo (maxHeap for left, minHeap for right)
2. Element remove karne ke liye lazy removal use karo (HashMap)
3. Balance maintain karo

### Java Code
```java
class SlidingWindowMedian {
    private PriorityQueue<Integer> maxHeap;
    private PriorityQueue<Integer> minHeap;
    private Map<Integer, Integer> toRemove;
    private int maxHeapSize, minHeapSize;
    
    public double[] medianSlidingWindow(int[] nums, int k) {
        maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b, a));
        minHeap = new PriorityQueue<>();
        toRemove = new HashMap<>();
        maxHeapSize = 0;
        minHeapSize = 0;
        
        double[] result = new double[nums.length - k + 1];
        
        for(int i = 0; i < nums.length; i++) {
            // Add current element
            addNum(nums[i]);
            
            // Remove element leaving the window
            if(i >= k) {
                removeNum(nums[i - k]);
            }
            
            // Get median
            if(i >= k - 1) {
                result[i - k + 1] = getMedian();
            }
        }
        
        return result;
    }
    
    private void addNum(int num) {
        if(maxHeap.isEmpty() || num <= maxHeap.peek()) {
            maxHeap.add(num);
            maxHeapSize++;
        } else {
            minHeap.add(num);
            minHeapSize++;
        }
        rebalance();
    }
    
    private void removeNum(int num) {
        toRemove.put(num, toRemove.getOrDefault(num, 0) + 1);
        
        if(num <= maxHeap.peek()) {
            maxHeapSize--;
            if(maxHeap.peek() == num) {
                lazyRemove(maxHeap);
            }
        } else {
            minHeapSize--;
            if(minHeap.peek() == num) {
                lazyRemove(minHeap);
            }
        }
        rebalance();
    }
    
    private void lazyRemove(PriorityQueue<Integer> heap) {
        while(!heap.isEmpty() && toRemove.containsKey(heap.peek())) {
            int num = heap.poll();
            int count = toRemove.get(num);
            if(count == 1) {
                toRemove.remove(num);
            } else {
                toRemove.put(num, count - 1);
            }
        }
    }
    
    private void rebalance() {
        if(maxHeapSize > minHeapSize + 1) {
            minHeap.add(maxHeap.poll());
            maxHeapSize--;
            minHeapSize++;
            lazyRemove(maxHeap);
        } else if(maxHeapSize < minHeapSize) {
            maxHeap.add(minHeap.poll());
            maxHeapSize++;
            minHeapSize--;
            lazyRemove(minHeap);
        }
    }
    
    private double getMedian() {
        if(maxHeapSize > minHeapSize) {
            return (double) maxHeap.peek();
        } else {
            return ((double) maxHeap.peek() + (double) minHeap.peek()) / 2.0;
        }
    }
    
    // Time: O(n log k), Space: O(n)
}
```

---

## **9. Task Scheduler** ⭐⭐⭐

### Theory
**Problem:** Tasks ko schedule karo with cooldown period 'n' between same tasks. Minimum CPU intervals find karo.

**Algorithm (Greedy + Heap):**
1. Frequency count karo
2. Max heap se highest frequency task lo
3. Each cycle mein n+1 tasks execute karo
4. Tasks ko heap mein wapas daalo with decreased frequency

### Java Code
```java
class TaskScheduler {
    public int leastInterval(char[] tasks, int n) {
        // Count frequencies
        int[] freq = new int[26];
        for(char c : tasks) {
            freq[c - 'A']++;
        }
        
        // Max heap
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        for(int count : freq) {
            if(count > 0) {
                maxHeap.add(count);
            }
        }
        
        int time = 0;
        
        while(!maxHeap.isEmpty()) {
            List<Integer> temp = new ArrayList<>();
            int cycle = n + 1;
            
            // Execute tasks in this cycle
            while(cycle > 0 && !maxHeap.isEmpty()) {
                int count = maxHeap.poll();
                if(count > 1) {
                    temp.add(count - 1);
                }
                time++;
                cycle--;
            }
            
            // Add back remaining tasks
            for(int count : temp) {
                maxHeap.add(count);
            }
            
            // If heap is not empty, we need idle time
            if(!maxHeap.isEmpty()) {
                time += cycle;  // Add idle slots
            }
        }
        
        return time;
    }
    
    // Mathematical approach (more efficient)
    public int leastIntervalMath(char[] tasks, int n) {
        int[] freq = new int[26];
        int maxFreq = 0;
        
        for(char c : tasks) {
            freq[c - 'A']++;
            maxFreq = Math.max(maxFreq, freq[c - 'A']);
        }
        
        // Count tasks with max frequency
        int maxCount = 0;
        for(int count : freq) {
            if(count == maxFreq) {
                maxCount++;
            }
        }
        
        // Formula: (maxFreq - 1) * (n + 1) + maxCount
        int result = (maxFreq - 1) * (n + 1) + maxCount;
        
        return Math.max(result, tasks.length);
    }
    
    // Time: O(n log m) where m = distinct tasks, Space: O(m)
}
```

---

## **10. Reorganize String** ⭐⭐

### Theory
**Problem:** String ko reorganize karo taki adjacent characters same na ho.

**Algorithm (Max Heap):**
1. Frequency count karo
2. Max heap se highest frequency character lo
3. Previous character store karo aur next iteration mein use karo

### Java Code
```java
class ReorganizeString {
    public String reorganizeString(String s) {
        // Count frequencies
        int[] freq = new int[26];
        for(char c : s.toCharArray()) {
            freq[c - 'a']++;
            
            // If any character appears more than half, impossible
            if(freq[c - 'a'] > (s.length() + 1) / 2) {
                return "";
            }
        }
        
        // Max heap
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> b[1] - a[1]);
        for(int i = 0; i < 26; i++) {
            if(freq[i] > 0) {
                maxHeap.add(new int[]{i, freq[i]});
            }
        }
        
        StringBuilder result = new StringBuilder();
        int[] prev = new int[]{-1, 0};
        
        while(!maxHeap.isEmpty()) {
            int[] current = maxHeap.poll();
            result.append((char) (current[0] + 'a'));
            current[1]--;
            
            if(prev[1] > 0) {
                maxHeap.add(prev);
            }
            prev = current;
        }
        
        return result.toString();
    }
    
    // Time: O(n log 26) = O(n), Space: O(n)
}
```

---

## **11. Find K Pairs with Smallest Sums** ⭐⭐

### Theory
**Problem:** Do sorted arrays se k smallest pairs (u,v) find karo where u from nums1, v from nums2.

**Algorithm (Min Heap):**
1. nums1 ke first k elements aur nums2[0] ke saath pairs heap mein daalo
2. Smallest pair nikaalo
3. Us pair ke nums2 ke next index ke saath naya pair daalo

### Java Code
```java
class KPairsSmallestSums {
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> result = new ArrayList<>();
        if(nums1.length == 0 || nums2.length == 0 || k == 0) return result;
        
        // Min heap: [sum, index in nums1, index in nums2]
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        
        // Add first k pairs (nums1[i], nums2[0])
        for(int i = 0; i < Math.min(k, nums1.length); i++) {
            minHeap.add(new int[]{nums1[i] + nums2[0], i, 0});
        }
        
        while(k-- > 0 && !minHeap.isEmpty()) {
            int[] current = minHeap.poll();
            int i = current[1];
            int j = current[2];
            
            result.add(Arrays.asList(nums1[i], nums2[j]));
            
            // Add next pair (nums1[i], nums2[j+1])
            if(j + 1 < nums2.length) {
                minHeap.add(new int[]{nums1[i] + nums2[j + 1], i, j + 1});
            }
        }
        
        return result;
    }
    
    // Time: O(k log k), Space: O(k)
}
```

---

## **12. Minimum Cost to Connect Sticks** ⭐⭐

### Theory
**Problem:** Sticks ko connect karne ka minimum cost find karo. Cost = sum of lengths of two sticks.

**Algorithm (Min Heap):**
1. Saari sticks heap mein daalo
2. Do smallest sticks nikaalo, unhe connect karo
3. Cost add karo, new stick heap mein daalo
4. Repeat until one stick remains

### Java Code
```java
class ConnectSticks {
    public int connectSticks(int[] sticks) {
        if(sticks.length <= 1) return 0;
        
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for(int stick : sticks) {
            minHeap.add(stick);
        }
        
        int totalCost = 0;
        
        while(minHeap.size() > 1) {
            int first = minHeap.poll();
            int second = minHeap.poll();
            int cost = first + second;
            totalCost += cost;
            minHeap.add(cost);
        }
        
        return totalCost;
    }
    
    // Time: O(n log n), Space: O(n)
}
```

---

## **13. Last Stone Weight** ⭐

### Theory
**Problem:** Stones ko smash karo - heaviest two stones ko smash karo, difference wapas daalo. Last remaining stone weight find karo.

**Algorithm (Max Heap):**
1. Saare stones max heap mein daalo
2. Two heaviest stones nikaalo
3. Agar equal to dono remove, otherwise difference wapas daalo

### Java Code
```java
class LastStoneWeight {
    public int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        
        for(int stone : stones) {
            maxHeap.add(stone);
        }
        
        while(maxHeap.size() > 1) {
            int first = maxHeap.poll();
            int second = maxHeap.poll();
            
            if(first != second) {
                maxHeap.add(first - second);
            }
        }
        
        return maxHeap.isEmpty() ? 0 : maxHeap.peek();
    }
    
    // Time: O(n log n), Space: O(n)
}
```

---

## **14. Minimum Number of Refueling Stops** ⭐⭐⭐

### Theory
**Problem:** Car ko target tak pahunchne ke liye minimum refueling stops chahiye.

**Algorithm (Max Heap):**
1. Max heap mein available fuel stations ki fuel capacity store karo
2. Jahan tak fuel hai wahan tak travel karo
3. Jab fuel khatam ho to maximum fuel wala station use karo

### Java Code
```java
class MinRefuelStops {
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        int currentFuel = startFuel;
        int stops = 0;
        int i = 0;
        
        while(currentFuel < target) {
            // Add all stations we can reach with current fuel
            while(i < stations.length && stations[i][0] <= currentFuel) {
                maxHeap.add(stations[i][1]);
                i++;
            }
            
            // If no stations available, cannot reach
            if(maxHeap.isEmpty()) return -1;
            
            // Use station with most fuel
            currentFuel += maxHeap.poll();
            stops++;
        }
        
        return stops;
    }
    
    // Time: O(n log n), Space: O(n)
}
```

---

## **15. Find Kth Largest in Stream** ⭐⭐

### Theory
**Problem:** Data stream se kth largest element hamesha return karo.

**Algorithm (Min Heap of size k):**
- Min heap maintain karo of size k
- Heap ka root hamesha kth largest hoga

### Java Code
```java
class KthLargestInStream {
    private PriorityQueue<Integer> minHeap;
    private int k;
    
    public KthLargestInStream(int k, int[] nums) {
        this.k = k;
        minHeap = new PriorityQueue<>();
        
        for(int num : nums) {
            add(num);
        }
    }
    
    public int add(int val) {
        minHeap.add(val);
        
        if(minHeap.size() > k) {
            minHeap.poll();
        }
        
        return minHeap.peek();
    }
    
    // Time: O(log k) per add, Space: O(k)
}
```

---

## **Quick Revision Table**

| Problem | Approach | Time | Space |
|---------|----------|------|-------|
| Kth Largest | Min Heap of size k | O(n log k) | O(k) |
| Kth Smallest | Max Heap of size k | O(n log k) | O(k) |
| Merge K Lists | Min Heap | O(n log k) | O(k) |
| Top K Frequent | Min Heap + HashMap | O(n log k) | O(n) |
| K Closest Points | Max Heap | O(n log k) | O(k) |
| Median from Stream | Two Heaps | O(log n) | O(n) |
| Task Scheduler | Max Heap + Greedy | O(n log m) | O(m) |
| Connect Sticks | Min Heap | O(n log n) | O(n) |
| Last Stone Weight | Max Heap | O(n log n) | O(n) |

---

## **Common Heap Patterns**

### **Pattern 1: Kth Element (Min/Max Heap of size k)**
```java
// For Kth Largest: Use Min Heap of size k
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
for(int num : nums) {
    minHeap.add(num);
    if(minHeap.size() > k) minHeap.poll();
}
return minHeap.peek();
```

### **Pattern 2: Two Heaps (Median, Sliding Window Median)**
```java
// Max heap for left half, Min heap for right half
PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a,b) -> b-a);
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
```

### **Pattern 3: Merge K Sorted Lists**
```java
PriorityQueue<ListNode> minHeap = new PriorityQueue<>((a,b) -> a.val - b.val);
// Add first node of each list
// Poll and add next node
```

### **Pattern 4: Top K Frequent**
```java
// Min heap of size k based on frequency
PriorityQueue<Map.Entry<Integer, Integer>> minHeap = 
    new PriorityQueue<>((a,b) -> a.getValue() - b.getValue());
```

---

## **Heap vs PriorityQueue in Java**

```java
// Min Heap (default)
PriorityQueue<Integer> minHeap = new PriorityQueue<>();

// Max Heap
PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);

// Custom object heap
PriorityQueue<CustomClass> heap = new PriorityQueue<>(
    (a, b) -> a.field - b.field
);

// For storing arrays
PriorityQueue<int[]> heap = new PriorityQueue<>(
    (a, b) -> a[0] - b[0]  // Compare by first element
);
```

---

## **Interview Tips (Hinglish)**

### **1. Heap Kab Use Karein?**
- **Kth smallest/largest** dhundhna ho
- **Top K elements** chahiye ho
- **Median** maintain karna ho
- **Merge k sorted lists** karna ho
- **Streaming data** process karna ho

### **2. Heap vs Sorting**
| Scenario | Heap | Sorting |
|----------|------|---------|
| Kth element | O(n log k) | O(n log n) |
| Streaming data | ✅ | ❌ |
| All elements needed | ❌ | ✅ |

### **3. Yaad Rakhne Wali Baatein**
- Java ka `PriorityQueue` default **min heap** hai
- Max heap ke liye comparator reverse karo
- Heapify array: O(n) time, O(1) space
- Lazy removal technique use karo for deleting from heap

### **4. Common Mistakes**
- Heap mein `add()` aur `offer()` dono same hain
- `poll()` vs `peek()` - poll removes, peek doesn't
- Heap ko traverse karna sorted order nahi deta
- Heap sort O(n log n) hai, but in-place nahi hai

### **5. Complexities Yaad Rakho**
```
Build heap from array: O(n)
Insert: O(log n)
Extract min/max: O(log n)
Peek: O(1)
Heap sort: O(n log n)
```

---

## **Pro Tips for Interviews**

1. **PriorityQueue use karo** - Java mein direct implementation hai

2. **Comparator samjho** - Lambda expressions use karo for clarity

3. **Heap size limit karo** - Memory efficient ke liye heap size = k rakho

4. **Two heaps pattern master karo** - Median problems mein bahut useful hai

5. **Lazy removal technique** - Jab heap se delete karna ho to HashMap use karo


## Most Asked 20 Hard Heap/Priority Queue Problems in Interview

### 1. Merge k Sorted Lists
**Problem:** Merge `k` sorted linked lists into one sorted list.

```java
public ListNode mergeKLists(ListNode[] lists) {
    PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> a.val - b.val);
    for (ListNode node : lists) {
        if (node != null) pq.offer(node);
    }
    
    ListNode dummy = new ListNode(0);
    ListNode curr = dummy;
    
    while (!pq.isEmpty()) {
        ListNode node = pq.poll();
        curr.next = node;
        curr = curr.next;
        if (node.next != null) pq.offer(node.next);
    }
    return dummy.next;
}
```

 Saare lists ke first nodes ko heap mein daalo. Heap se sabse chhota node nikaalo, result mein lagao, aur uske next node ko heap mein daalo. Repeat until heap empty.

**Time:** O(N log k) where N = total nodes, k = number of lists

---

### 2. Top K Frequent Elements
**Problem:** Return k most frequent elements from array.

```java
public int[] topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> freq = new HashMap<>();
    for (int n : nums) freq.put(n, freq.getOrDefault(n, 0) + 1);
    
    PriorityQueue<Map.Entry<Integer, Integer>> pq = new PriorityQueue<>(
        (a, b) -> a.getValue() - b.getValue()  // min-heap by frequency
    );
    
    for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
        pq.offer(entry);
        if (pq.size() > k) pq.poll();
    }
    
    int[] result = new int[k];
    for (int i = 0; i < k; i++) result[i] = pq.poll().getKey();
    return result;
}
```

 Pehle frequency map banao. Phir ek **min-heap** of size k rakho. Heap mein sirf top k frequent elements hi rahenge. Agar size k se zyada ho jaye toh smallest frequency wala hata do.

---

### 3. Find Median from Data Stream
**Problem:** Real-time median find karna as numbers add hote rahein.

```java
class MedianFinder {
    PriorityQueue<Integer> maxHeap; // left half (smaller numbers)
    PriorityQueue<Integer> minHeap; // right half (larger numbers)
    
    public MedianFinder() {
        maxHeap = new PriorityQueue<>((a, b) -> b - a);
        minHeap = new PriorityQueue<>();
    }
    
    public void addNum(int num) {
        if (maxHeap.isEmpty() || num <= maxHeap.peek()) {
            maxHeap.offer(num);
        } else {
            minHeap.offer(num);
        }
        
        // Balance heaps: maxHeap size should be either equal or 1 more than minHeap
        if (maxHeap.size() > minHeap.size() + 1) {
            minHeap.offer(maxHeap.poll());
        } else if (minHeap.size() > maxHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }
    
    public double findMedian() {
        if (maxHeap.size() > minHeap.size()) {
            return maxHeap.peek();
        }
        return (maxHeap.peek() + minHeap.peek()) / 2.0;
    }
}
```

 Do heaps use karo - ek max-heap (left side) aur ek min-heap (right side). Max-heap ka root left side ka sabse bada hoga, min-heap ka root right side ka sabse chhota. Dono halves balance rakho.

---

### 4. K Closest Points to Origin
**Problem:** Find k points closest to origin (0,0).

```java
public int[][] kClosest(int[][] points, int k) {
    // Max-heap by distance (we want to keep k smallest)
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> 
        (b[0]*b[0] + b[1]*b[1]) - (a[0]*a[0] + a[1]*a[1])
    );
    
    for (int[] point : points) {
        pq.offer(point);
        if (pq.size() > k) pq.poll();
    }
    
    int[][] result = new int[k][2];
    for (int i = 0; i < k; i++) result[i] = pq.poll();
    return result;
}
```

 Ek **max-heap** of size k rakho. Har point ko daalo, agar size k se zyada ho gaya toh sabse door wala point hata do (kyunki woh top pe hoga max-heap mein).

---

### 5. Meeting Rooms II (Minimum Meeting Rooms)
**Problem:** Minimum meeting rooms required for given intervals.

```java
public int minMeetingRooms(int[][] intervals) {
    if (intervals.length == 0) return 0;
    
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]); // sort by start time
    PriorityQueue<Integer> pq = new PriorityQueue<>(); // min-heap of end times
    
    pq.offer(intervals[0][1]);
    
    for (int i = 1; i < intervals.length; i++) {
        if (intervals[i][0] >= pq.peek()) {
            pq.poll(); // room free ho gaya
        }
        pq.offer(intervals[i][1]);
    }
    return pq.size();
}
```

 Meetings ko start time se sort karo. Min-heap mein end times rakho. Agar next meeting ka start time heap ke top (earliest ending) se bada ya equal hai, toh woh room reuse kar sakte ho. Nahi toh naya room chahiye.

---

### 6. Task Scheduler
**Problem:** Schedule tasks with cooldown period between same tasks.

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
        time += cycle; // idle time
    }
    return time;
}
```

 Sabse zyada frequency wale task ko pehle schedule karo (max-heap). Ek cycle mein (n+1) unique tasks daal sakte ho. Agar cycle khatam ho jaye aur tasks bachein toh idle time add karo.

---

### 7. Kth Largest Element in an Array
**Problem:** Find kth largest element.

```java
public int findKthLargest(int[] nums, int k) {
    PriorityQueue<Integer> pq = new PriorityQueue<>(); // min-heap
    
    for (int num : nums) {
        pq.offer(num);
        if (pq.size() > k) pq.poll();
    }
    return pq.peek();
}
```

 Min-heap of size k maintain karo. Heap mein hamesha k largest elements rahenge. End mein peek kth largest hoga.

---

### 8. Sliding Window Maximum
**Problem:** Maximum in every sliding window of size k.

```java
public int[] maxSlidingWindow(int[] nums, int k) {
    int n = nums.length;
    int[] result = new int[n - k + 1];
    // Max-heap: store (value, index)
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[0] - a[0]);
    
    for (int i = 0; i < n; i++) {
        pq.offer(new int[]{nums[i], i});
        
        // Remove elements outside current window
        while (pq.peek()[1] <= i - k) pq.poll();
        
        if (i >= k - 1) result[i - k + 1] = pq.peek()[0];
    }
    return result;
}
```

 Max-heap mein (value, index) daalo. Window ke bahar ke indices ko heap se hata do. Top element current window ka maximum hoga.

---

### 9. Reorganize String
**Problem:** Rearrange string so no two adjacent chars are same.

```java
public String reorganizeString(String s) {
    int[] freq = new int[26];
    for (char c : s.toCharArray()) freq[c - 'a']++;
    
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[1] - a[1]);
    for (int i = 0; i < 26; i++) {
        if (freq[i] > 0) pq.offer(new int[]{i, freq[i]});
    }
    
    StringBuilder sb = new StringBuilder();
    int[] prev = null;
    
    while (!pq.isEmpty()) {
        int[] curr = pq.poll();
        sb.append((char)(curr[0] + 'a'));
        curr[1]--;
        
        if (prev != null && prev[1] > 0) pq.offer(prev);
        prev = curr;
    }
    
    return sb.length() == s.length() ? sb.toString() : "";
}
```

 Sabse zyada frequency wale char ko pehle use karo. Ek baar use karne ke baad agle iteration mein daalo (taaki adjacent na ho).

---

### 10. Minimum Cost to Connect Sticks
**Problem:** Connect sticks with cost = sum of lengths, minimize total cost.

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

 Hamesha do sabse chhote sticks uthao, unhe jodo, cost add karo, aur wapas heap mein daalo. Repeat until ek stick bache.

---

### 11. Last Stone Weight
**Problem:** Smash two largest stones, add difference back.

```java
public int lastStoneWeight(int[] stones) {
    PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
    for (int s : stones) pq.offer(s);
    
    while (pq.size() > 1) {
        int a = pq.poll();
        int b = pq.poll();
        if (a != b) pq.offer(a - b);
    }
    return pq.isEmpty() ? 0 : pq.peek();
}
```

 Max-heap mein saari stones daalo. Do sabse bade uthao, smash karo, difference wapas daalo.

---

### 12. Sort Characters By Frequency
**Problem:** Sort string by decreasing frequency.

```java
public String frequencySort(String s) {
    Map<Character, Integer> freq = new HashMap<>();
    for (char c : s.toCharArray()) freq.put(c, freq.getOrDefault(c, 0) + 1);
    
    PriorityQueue<Map.Entry<Character, Integer>> pq = new PriorityQueue<>(
        (a, b) -> b.getValue() - a.getValue()
    );
    pq.addAll(freq.entrySet());
    
    StringBuilder sb = new StringBuilder();
    while (!pq.isEmpty()) {
        Map.Entry<Character, Integer> entry = pq.poll();
        sb.append(String.valueOf(entry.getKey()).repeat(entry.getValue()));
    }
    return sb.toString();
}
```

---

### 13. Kth Smallest Element in a Sorted Matrix
**Problem:** Kth smallest in row-wise and col-wise sorted matrix.

```java
public int kthSmallest(int[][] matrix, int k) {
    int n = matrix.length;
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
    
    // Add first column of each row
    for (int i = 0; i < n; i++) {
        pq.offer(new int[]{matrix[i][0], i, 0});
    }
    
    for (int i = 0; i < k - 1; i++) {
        int[] curr = pq.poll();
        if (curr[2] + 1 < n) {
            pq.offer(new int[]{matrix[curr[1]][curr[2] + 1], curr[1], curr[2] + 1});
        }
    }
    return pq.peek()[0];
}
```

 Min-heap mein har row ka first element daalo. K-1 baar smallest element nikaalo aur us row ka next element daalo. K-th time jo aayega woh answer hai.

---

### 14. Maximum Performance of a Team
**Problem:** Choose at most k engineers to maximize speed * min(efficiency).

```java
public int maxPerformance(int n, int[] speed, int[] efficiency, int k) {
    int[][] engineers = new int[n][2];
    for (int i = 0; i < n; i++) {
        engineers[i] = new int[]{efficiency[i], speed[i]};
    }
    
    Arrays.sort(engineers, (a, b) -> b[0] - a[0]); // sort by efficiency descending
    
    PriorityQueue<Integer> pq = new PriorityQueue<>(); // min-heap of speeds
    long sumSpeed = 0, maxPerformance = 0;
    
    for (int[] eng : engineers) {
        pq.offer(eng[1]);
        sumSpeed += eng[1];
        
        if (pq.size() > k) {
            sumSpeed -= pq.poll();
        }
        
        maxPerformance = Math.max(maxPerformance, sumSpeed * eng[0]);
    }
    return (int)(maxPerformance % 1000000007);
}
```

 Engineers ko efficiency ke hisaab se sort karo (high to low). Min-heap mein speeds rakho. Jab size k se zyada ho jaye toh slowest speed hata do. Har baar current efficiency * sum(speeds) se max calculate karo.

---

### 15. Minimum Interval to Include Each Query
**Problem:** For each query, find smallest interval containing that point.

```java
public int[] minInterval(int[][] intervals, int[] queries) {
    int n = intervals.length;
    int m = queries.length;
    
    // Sort intervals by start
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
    
    // Store queries with original indices
    int[][] sortedQueries = new int[m][2];
    for (int i = 0; i < m; i++) {
        sortedQueries[i] = new int[]{queries[i], i};
    }
    Arrays.sort(sortedQueries, (a, b) -> a[0] - b[0]);
    
    // Min-heap by interval length
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> (a[1] - a[0]) - (b[1] - b[0]));
    
    int[] result = new int[m];
    Arrays.fill(result, -1);
    int idx = 0;
    
    for (int[] q : sortedQueries) {
        int query = q[0];
        int originalIdx = q[1];
        
        // Add all intervals that start <= query
        while (idx < n && intervals[idx][0] <= query) {
            pq.offer(intervals[idx]);
            idx++;
        }
        
        // Remove intervals that end < query
        while (!pq.isEmpty() && pq.peek()[1] < query) {
            pq.poll();
        }
        
        if (!pq.isEmpty()) {
            int[] smallest = pq.peek();
            result[originalIdx] = smallest[1] - smallest[0] + 1;
        }
    }
    return result;
}
```

 Intervals ko start se sort karo, queries ko value se sort karo. Query ke liye saare relevant intervals (start <= query) heap mein daalo (sorted by length). Heap ke top mein smallest interval milega jo query cover karta hai.

---

### 16. Maximum Number of Events That Can Be Attended
**Problem:** Attend maximum events, one per day, each event has [start, end].

```java
public int maxEvents(int[][] events) {
    Arrays.sort(events, (a, b) -> a[0] - b[0]);
    PriorityQueue<Integer> pq = new PriorityQueue<>();
    
    int i = 0, day = 0, count = 0;
    int n = events.length;
    
    while (i < n || !pq.isEmpty()) {
        if (pq.isEmpty()) day = events[i][0];
        
        // Add all events starting today
        while (i < n && events[i][0] <= day) {
            pq.offer(events[i][1]);
            i++;
        }
        
        // Remove expired events
        while (!pq.isEmpty() && pq.peek() < day) {
            pq.poll();
        }
        
        // Attend one event today
        if (!pq.isEmpty()) {
            pq.poll();
            count++;
        }
        day++;
    }
    return count;
}
```

---

### 17. IPO (Initial Public Offering)
**Problem:** At most k projects, each needs capital, gives profit. Maximize final capital.

```java
public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
    int n = profits.length;
    int[][] projects = new int[n][2];
    for (int i = 0; i < n; i++) {
        projects[i] = new int[]{capital[i], profits[i]};
    }
    
    Arrays.sort(projects, (a, b) -> a[0] - b[0]);
    PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a); // max-heap of profits
    
    int i = 0;
    for (int j = 0; j < k; j++) {
        while (i < n && projects[i][0] <= w) {
            pq.offer(projects[i][1]);
            i++;
        }
        if (pq.isEmpty()) break;
        w += pq.poll();
    }
    return w;
}
```

 Projects ko required capital se sort karo. Available capital ke andar wale saare projects ke profits max-heap mein daalo. Sabse bada profit wala project select karo aur capital badhao. K baar repeat karo.

---

### 18. Maximum Sum Obtained of Any Permutation
**Problem:** Given requests (L,R), maximize sum of chosen indices after permutation.

```java
public int maxSumRangeQuery(int[] nums, int[][] requests) {
    int n = nums.length;
    int[] freq = new int[n];
    
    for (int[] req : requests) {
        freq[req[0]]++;
        if (req[1] + 1 < n) freq[req[1] + 1]--;
    }
    
    for (int i = 1; i < n; i++) {
        freq[i] += freq[i - 1];
    }
    
    Arrays.sort(nums);
    Arrays.sort(freq);
    
    long sum = 0;
    for (int i = 0; i < n; i++) {
        sum += (long) nums[i] * freq[i];
    }
    return (int)(sum % 1000000007);
}
```

 Difference array se har index ki frequency calculate karo. Frequency aur nums dono ko sort karo. Sabse bada number sabse zyada baar aane wale index pe assign karo.

---

### 19. The Skyline Problem
**Problem:** Return skyline outline of buildings [L,R,height].

```java
public List<List<Integer>> getSkyline(int[][] buildings) {
    List<List<Integer>> result = new ArrayList<>();
    List<int[]> points = new ArrayList<>();
    
    for (int[] b : buildings) {
        points.add(new int[]{b[0], -b[2]}); // start point
        points.add(new int[]{b[1], b[2]});  // end point
    }
    
    points.sort((a, b) -> {
        if (a[0] != b[0]) return a[0] - b[0];
        return a[1] - b[1];
    });
    
    PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
    pq.offer(0);
    int prevHeight = 0;
    
    for (int[] p : points) {
        if (p[1] < 0) {
            pq.offer(-p[1]); // start: add height
        } else {
            pq.remove(p[1]); // end: remove height (O(n) but ok for n<=10^4)
        }
        
        int currHeight = pq.peek();
        if (currHeight != prevHeight) {
            result.add(Arrays.asList(p[0], currHeight));
            prevHeight = currHeight;
        }
    }
    return result;
}
```

 Har building ke start aur end point banao (start height negative, end positive). Sort karo. Max-heap maintain karo current heights ka. Jab height change ho toh result mein point daalo.

---

### 20. Minimum Cost to Hire K Workers
**Problem:** Hire k workers, each has quality and min wage ratio.

```java
public double mincostToHireWorkers(int[] quality, int[] wage, int k) {
    int n = quality.length;
    Worker[] workers = new Worker[n];
    for (int i = 0; i < n; i++) {
        workers[i] = new Worker(quality[i], wage[i]);
    }
    
    Arrays.sort(workers, (a, b) -> Double.compare(a.ratio, b.ratio));
    
    PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a); // max-heap of quality
    int sumQuality = 0;
    double minCost = Double.MAX_VALUE;
    
    for (Worker w : workers) {
        pq.offer(w.quality);
        sumQuality += w.quality;
        
        if (pq.size() > k) {
            sumQuality -= pq.poll();
        }
        
        if (pq.size() == k) {
            minCost = Math.min(minCost, sumQuality * w.ratio);
        }
    }
    return minCost;
}

class Worker {
    int quality, wage;
    double ratio;
    Worker(int q, int w) {
        quality = q;
        wage = w;
        ratio = (double) w / q;
    }
}
```

 Workers ko wage/quality ratio ke hisaab se sort karo. Har worker ko potential captain maano. Max-heap mein qualities rakho (k workers ki). Total quality * current ratio = total cost. Minimum cost nikaalo.

---

## 📌 Key Patterns Summary (Hinglish)

| Pattern | Heap Type | Example Problems |
|---------|-----------|------------------|
| **Top K Elements** | Min-heap of size k | K Closest Points, Top K Frequent |
| **Kth Element** | Min/Max-heap | Kth Largest, Kth Smallest |
| **Median / Running Order** | Two heaps (max + min) | Median from Stream |
| **Scheduling / Intervals** | Min-heap of end times | Meeting Rooms, Task Scheduler |
| **Merging** | Min-heap | Merge K Lists |
| **Greedy with Heap** | Max-heap | IPO, Maximum Performance |
| **Real-time Min/Max** | Heap with lazy deletion | Sliding Window Maximum |

## ⚡ Quick Tips

1. **Java PriorityQueue default = min-heap** - Use `(a,b) -> b - a` for max-heap
2. **Lazy deletion** - Jab element heap ke top pe ho aur invalid ho tabhi remove karo
3. **Time complexity** - Heap operations `O(log n)`, building heap `O(n)`
4. **Custom comparator** - Use lambda expressions for custom ordering
5. **Space** - Usually `O(n)` or `O(k)` for heap

