# Sorting Algorithms 

## **Sorting Basics**

Sorting ka matlab hai data ko **specific order** (ascending/descending) mein arrange karna. Sorting computer science ka fundamental topic hai aur interviews mein frequently asked hota hai.

### **Why Sorting is Important?**
- Binary search ke liye sorted array chahiye
- Data analysis mein common operation
- Many algorithms depend on sorting
- Interview questions mein building block

### **Sorting Algorithm Classifications**

| Classification | Types |
|----------------|-------|
| **Time Complexity** | O(n²), O(n log n), O(n) |
| **Space Complexity** | In-place vs Out-of-place |
| **Stability** | Stable vs Unstable |
| **Approach** | Comparison vs Non-comparison |

### **Comparison Table**

| Algorithm | Best Time | Average Time | Worst Time | Space | Stable |
|-----------|-----------|--------------|------------|-------|--------|
| Bubble Sort | O(n) | O(n²) | O(n²) | O(1) | Yes |
| Selection Sort | O(n²) | O(n²) | O(n²) | O(1) | No |
| Insertion Sort | O(n) | O(n²) | O(n²) | O(1) | Yes |
| Merge Sort | O(n log n) | O(n log n) | O(n log n) | O(n) | Yes |
| Quick Sort | O(n log n) | O(n log n) | O(n²) | O(log n) | No |
| Heap Sort | O(n log n) | O(n log n) | O(n log n) | O(1) | No |
| Counting Sort | O(n+k) | O(n+k) | O(n+k) | O(k) | Yes |
| Radix Sort | O(d*(n+k)) | O(d*(n+k)) | O(d*(n+k)) | O(n+k) | Yes |
| Bucket Sort | O(n+k) | O(n+k) | O(n²) | O(n+k) | Yes |

---

## **1. Bubble Sort** ⭐

### Theory (Hinglish)
**Kaam kaise karta hai:** 
- Adjacent elements compare karo
- Agar galat order mein hai to swap karo
- Har pass mein largest element apni sahi jagah pahunch jata hai

**Visual Example:**
```
[5, 3, 8, 1] → Compare 5,3 → swap → [3,5,8,1]
[3,5,8,1] → Compare 5,8 → no swap → [3,5,8,1]
[3,5,8,1] → Compare 8,1 → swap → [3,5,1,8]
After pass 1: [3,5,1,8] (8 is in correct position)

Next pass: [3,1,5,8]
Next pass: [1,3,5,8]
```

### Java Code
```java
class BubbleSort {
    // Basic Bubble Sort
    public void bubbleSort(int[] arr) {
        int n = arr.length;
        
        for(int i = 0; i < n - 1; i++) {
            for(int j = 0; j < n - i - 1; j++) {
                if(arr[j] > arr[j + 1]) {
                    // Swap
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
    
    // Optimized Bubble Sort (with early termination)
    public void bubbleSortOptimized(int[] arr) {
        int n = arr.length;
        
        for(int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            
            for(int j = 0; j < n - i - 1; j++) {
                if(arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            
            // If no swapping, array is already sorted
            if(!swapped) break;
        }
    }
    
    // Time: O(n²) average/worst, O(n) best (optimized)
    // Space: O(1)
    // Stable: Yes
}
```

---

## **2. Selection Sort** ⭐

### Theory (Hinglish)
**Kaam kaise karta hai:**
- Har iteration mein **minimum element** find karo
- Usse current position ke saath swap karo
- Array ke left side sorted hota jata hai

**Visual Example:**
```
[5, 3, 8, 1] → Find minimum (1) → swap with index 0 → [1,3,8,5]
[1,3,8,5] → Find minimum in remaining (3) → already at index 1 → [1,3,8,5]
[1,3,8,5] → Find minimum in remaining (5) → swap with index 2 → [1,3,5,8]
```

### Java Code
```java
class SelectionSort {
    public void selectionSort(int[] arr) {
        int n = arr.length;
        
        for(int i = 0; i < n - 1; i++) {
            int minIndex = i;
            
            // Find minimum element in unsorted part
            for(int j = i + 1; j < n; j++) {
                if(arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            
            // Swap with current position
            if(minIndex != i) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }
    }
    
    // Time: O(n²) for all cases
    // Space: O(1)
    // Stable: No (can be made stable with modifications)
}
```

---

## **3. Insertion Sort** ⭐⭐

### Theory (Hinglish)
**Kaam kaise karta hai:**
- Jaise cards arrange karte ho
- Current element ko sorted part mein sahi jagah insert karo
- Small arrays ke liye efficient

**Visual Example:**
```
[5, 3, 8, 1]
Step 1: [5,3,8,1] → Insert 3 → [3,5,8,1]
Step 2: [3,5,8,1] → Insert 8 → [3,5,8,1] (already correct)
Step 3: [3,5,8,1] → Insert 1 → [1,3,5,8]
```

### Java Code
```java
class InsertionSort {
    public void insertionSort(int[] arr) {
        int n = arr.length;
        
        for(int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            
            // Shift elements greater than key to right
            while(j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            
            // Place key in correct position
            arr[j + 1] = key;
        }
    }
    
    // Binary Insertion Sort (using binary search to find position)
    public void binaryInsertionSort(int[] arr) {
        int n = arr.length;
        
        for(int i = 1; i < n; i++) {
            int key = arr[i];
            int pos = binarySearch(arr, key, 0, i - 1);
            
            // Shift elements
            for(int j = i - 1; j >= pos; j--) {
                arr[j + 1] = arr[j];
            }
            arr[pos] = key;
        }
    }
    
    private int binarySearch(int[] arr, int key, int left, int right) {
        while(left <= right) {
            int mid = left + (right - left) / 2;
            if(arr[mid] == key) return mid + 1;
            if(arr[mid] < key) left = mid + 1;
            else right = mid - 1;
        }
        return left;
    }
    
    // Time: O(n²) worst/average, O(n) best
    // Space: O(1)
    // Stable: Yes
}
```

---

## **4. Merge Sort** ⭐⭐⭐

### Theory (Hinglish)
**Kaam kaise karta hai (Divide & Conquer):**
1. **Divide:** Array ko do halves mein divide karo
2. **Conquer:** Dono halves ko recursively sort karo
3. **Combine:** Sorted halves ko merge karo

**Visual Example:**
```
[5,3,8,1,6,2,7,4]
           ↓
    [5,3,8,1]    [6,2,7,4]
       ↓              ↓
  [5,3]  [8,1]   [6,2]  [7,4]
    ↓      ↓       ↓      ↓
 [5][3] [8][1]  [6][2] [7][4]
    ↓      ↓       ↓      ↓
  [3,5]  [1,8]   [2,6]  [4,7]
       ↓              ↓
    [1,3,5,8]    [2,4,6,7]
           ↓
    [1,2,3,4,5,6,7,8]
```

### Java Code
```java
class MergeSort {
    public void mergeSort(int[] arr, int left, int right) {
        if(left < right) {
            int mid = left + (right - left) / 2;
            
            // Recursively sort halves
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            
            // Merge sorted halves
            merge(arr, left, mid, right);
        }
    }
    
    private void merge(int[] arr, int left, int mid, int right) {
        // Sizes of two subarrays
        int n1 = mid - left + 1;
        int n2 = right - mid;
        
        // Create temporary arrays
        int[] leftArr = new int[n1];
        int[] rightArr = new int[n2];
        
        // Copy data
        for(int i = 0; i < n1; i++) {
            leftArr[i] = arr[left + i];
        }
        for(int j = 0; j < n2; j++) {
            rightArr[j] = arr[mid + 1 + j];
        }
        
        // Merge arrays
        int i = 0, j = 0, k = left;
        
        while(i < n1 && j < n2) {
            if(leftArr[i] <= rightArr[j]) {
                arr[k] = leftArr[i];
                i++;
            } else {
                arr[k] = rightArr[j];
                j++;
            }
            k++;
        }
        
        // Copy remaining elements
        while(i < n1) {
            arr[k] = leftArr[i];
            i++;
            k++;
        }
        
        while(j < n2) {
            arr[k] = rightArr[j];
            j++;
            k++;
        }
    }
    
    // In-place merge sort (more complex, O(1) space)
    // Time: O(n log n) for all cases
    // Space: O(n) for temporary arrays
    // Stable: Yes
}
```

---

## **5. Quick Sort** ⭐⭐⭐

### Theory (Hinglish)
**Kaam kaise karta hai (Divide & Conquer):**
1. **Pivot choose karo** (last element, first, random, median)
2. **Partition:** Elements ko pivot ke around arrange karo (smaller left, larger right)
3. **Recursively** left aur right parts sort karo

**Partition Process:**
```
Array: [5,3,8,1,6,2,7,4]  pivot = 4
       
After partition:
[3,1,2] [4] [5,8,6,7]
   ↓      ↓       ↓
  sort   pivot   sort
```

### Java Code
```java
class QuickSort {
    // Standard Quick Sort
    public void quickSort(int[] arr, int low, int high) {
        if(low < high) {
            int pivotIndex = partition(arr, low, high);
            
            // Recursively sort left and right
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }
    
    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];  // Choose last element as pivot
        int i = low - 1;  // Index of smaller element
        
        for(int j = low; j < high; j++) {
            if(arr[j] <= pivot) {
                i++;
                // Swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        
        // Place pivot in correct position
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        
        return i + 1;
    }
    
    // Quick Sort with Random Pivot (avoid worst case)
    public void quickSortRandom(int[] arr, int low, int high) {
        if(low < high) {
            int pivotIndex = randomPartition(arr, low, high);
            quickSortRandom(arr, low, pivotIndex - 1);
            quickSortRandom(arr, pivotIndex + 1, high);
        }
    }
    
    private int randomPartition(int[] arr, int low, int high) {
        int randomIndex = low + (int)(Math.random() * (high - low + 1));
        // Swap random element with last element
        int temp = arr[randomIndex];
        arr[randomIndex] = arr[high];
        arr[high] = temp;
        return partition(arr, low, high);
    }
    
    // 3-Way Quick Sort (for arrays with duplicates)
    public void quickSort3Way(int[] arr, int low, int high) {
        if(low >= high) return;
        
        int lt = low;
        int gt = high;
        int pivot = arr[low];
        int i = low;
        
        while(i <= gt) {
            if(arr[i] < pivot) {
                swap(arr, lt, i);
                lt++;
                i++;
            } else if(arr[i] > pivot) {
                swap(arr, i, gt);
                gt--;
            } else {
                i++;
            }
        }
        
        quickSort3Way(arr, low, lt - 1);
        quickSort3Way(arr, gt + 1, high);
    }
    
    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    // Time: O(n log n) average, O(n²) worst (rare with random pivot)
    // Space: O(log n) for recursion stack
    // Stable: No
}
```

---

## **6. Heap Sort** ⭐⭐

### Theory (Hinglish)
**Kaam kaise karta hai:**
1. Array se **max heap** build karo (O(n) time)
2. Root (maximum) ko last element ke saath swap karo
3. Heap size reduce karo aur heapify karo
4. Repeat until array sorted

**Visual Example:**
```
Array: [5,3,8,1,6,2,7,4]
Step 1: Build Max Heap → [8,6,7,4,3,2,5,1]
Step 2: Swap root with last → [1,6,7,4,3,2,5,8]
Step 3: Heapify → [7,6,5,4,3,2,1,8]
Step 4: Repeat...
```

### Java Code
```java
class HeapSort {
    public void heapSort(int[] arr) {
        int n = arr.length;
        
        // Step 1: Build max heap
        for(int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
        
        // Step 2: Extract elements from heap one by one
        for(int i = n - 1; i > 0; i--) {
            // Move current root to end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            
            // Heapify reduced heap
            heapify(arr, i, 0);
        }
    }
    
    private void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        
        // If left child is larger
        if(left < n && arr[left] > arr[largest]) {
            largest = left;
        }
        
        // If right child is larger
        if(right < n && arr[right] > arr[largest]) {
            largest = right;
        }
        
        // If largest is not root
        if(largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            
            // Recursively heapify affected subtree
            heapify(arr, n, largest);
        }
    }
    
    // Time: O(n log n) for all cases
    // Space: O(1)
    // Stable: No
}
```

---

## **7. Counting Sort** ⭐⭐

### Theory (Hinglish)
**Kaam kaise karta hai (Non-comparison sort):**
- **Range of input** pata hona chahiye (0 to k)
- Frequency count karo har element ki
- Cumulative frequency se position nikaalo

**When to use:** Jab range small ho (e.g., ages, marks, 0-100)

### Java Code
```java
class CountingSort {
    public void countingSort(int[] arr) {
        if(arr.length == 0) return;
        
        // Find range
        int max = arr[0];
        int min = arr[0];
        for(int num : arr) {
            max = Math.max(max, num);
            min = Math.min(min, num);
        }
        
        int range = max - min + 1;
        int[] count = new int[range];
        int[] output = new int[arr.length];
        
        // Count frequencies
        for(int num : arr) {
            count[num - min]++;
        }
        
        // Calculate cumulative frequency
        for(int i = 1; i < range; i++) {
            count[i] += count[i - 1];
        }
        
        // Build output array (stable sort)
        for(int i = arr.length - 1; i >= 0; i--) {
            int index = arr[i] - min;
            output[count[index] - 1] = arr[i];
            count[index]--;
        }
        
        // Copy back
        System.arraycopy(output, 0, arr, 0, arr.length);
    }
    
    // Time: O(n + k) where k = range
    // Space: O(k)
    // Stable: Yes
}
```

---

## **8. Radix Sort** ⭐⭐

### Theory (Hinglish)
**Kaam kaise karta hai:**
- Numbers ko **digit by digit** sort karo
- LSD (Least Significant Digit) se start karo
- Stable sort use karo (counting sort) for each digit

**Example:**
```
[170, 45, 75, 90, 802, 24, 2, 66]
Sort by units: [170,90,802,2,24,45,75,66]
Sort by tens:  [802,2,24,45,66,170,75,90]
Sort by hundreds: [2,24,45,66,75,90,170,802]
```

### Java Code
```java
class RadixSort {
    public void radixSort(int[] arr) {
        if(arr.length == 0) return;
        
        // Find maximum number to know number of digits
        int max = arr[0];
        for(int num : arr) {
            max = Math.max(max, num);
        }
        
        // Do counting sort for every digit
        for(int exp = 1; max / exp > 0; exp *= 10) {
            countingSortByDigit(arr, exp);
        }
    }
    
    private void countingSortByDigit(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10];  // digits 0-9
        
        // Count occurrences of each digit
        for(int num : arr) {
            int digit = (num / exp) % 10;
            count[digit]++;
        }
        
        // Calculate cumulative frequency
        for(int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
        
        // Build output array (stable)
        for(int i = n - 1; i >= 0; i--) {
            int digit = (arr[i] / exp) % 10;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }
        
        // Copy back
        System.arraycopy(output, 0, arr, 0, n);
    }
    
    // For negative numbers
    public void radixSortWithNegatives(int[] arr) {
        // Separate positive and negative
        List<Integer> positive = new ArrayList<>();
        List<Integer> negative = new ArrayList<>();
        
        for(int num : arr) {
            if(num >= 0) positive.add(num);
            else negative.add(-num);
        }
        
        // Sort positive
        int[] posArr = positive.stream().mapToInt(i -> i).toArray();
        radixSort(posArr);
        
        // Sort negative (reverse order after sorting)
        int[] negArr = negative.stream().mapToInt(i -> i).toArray();
        radixSort(negArr);
        
        // Merge back
        int index = 0;
        for(int i = negArr.length - 1; i >= 0; i--) {
            arr[index++] = -negArr[i];
        }
        for(int num : posArr) {
            arr[index++] = num;
        }
    }
    
    // Time: O(d * (n + b)) where d = digits, b = base (10)
    // Space: O(n + b)
    // Stable: Yes
}
```

---

## **9. Bucket Sort** ⭐⭐

### Theory (Hinglish)
**Kaam kaise karta hai:**
- Elements ko **buckets** mein distribute karo
- Har bucket ko individually sort karo
- Buckets concatenate karo

**When to use:** Uniformly distributed data

### Java Code
```java
class BucketSort {
    public void bucketSort(float[] arr) {
        if(arr.length == 0) return;
        
        int n = arr.length;
        
        // Create buckets
        @SuppressWarnings("unchecked")
        List<Float>[] buckets = new List[n];
        for(int i = 0; i < n; i++) {
            buckets[i] = new ArrayList<>();
        }
        
        // Put elements into buckets
        for(float num : arr) {
            int bucketIndex = (int) (num * n);
            buckets[bucketIndex].add(num);
        }
        
        // Sort each bucket
        for(int i = 0; i < n; i++) {
            Collections.sort(buckets[i]);
        }
        
        // Concatenate buckets
        int index = 0;
        for(int i = 0; i < n; i++) {
            for(float num : buckets[i]) {
                arr[index++] = num;
            }
        }
    }
    
    // For integers
    public void bucketSortInt(int[] arr, int bucketSize) {
        if(arr.length == 0) return;
        
        int min = arr[0];
        int max = arr[0];
        for(int num : arr) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }
        
        int bucketCount = (max - min) / bucketSize + 1;
        List<List<Integer>> buckets = new ArrayList<>(bucketCount);
        for(int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }
        
        // Distribute elements
        for(int num : arr) {
            int index = (num - min) / bucketSize;
            buckets.get(index).add(num);
        }
        
        // Sort and merge
        int index = 0;
        for(List<Integer> bucket : buckets) {
            Collections.sort(bucket);
            for(int num : bucket) {
                arr[index++] = num;
            }
        }
    }
    
    // Time: O(n + k) average, O(n²) worst
    // Space: O(n + k)
    // Stable: Yes (if stable sort used for buckets)
}
```

---

## **10. Tim Sort** ⭐⭐⭐ (Python/Java's Default Sort)

### Theory (Hinglish)
**Kaam kaise karta hai:**
- **Hybrid sorting algorithm** (Merge + Insertion)
- Real-world data ke liye optimized
- Java's `Arrays.sort()` for objects uses TimSort
- Python's default sort

**Key Features:**
1. Small arrays (size < 32) → Insertion Sort
2. Large arrays → Merge Sort with optimizations
3. Detects and uses existing order (runs)

### Java Code (Simplified Implementation)
```java
class TimSort {
    private static final int RUN = 32;
    
    public void timSort(int[] arr) {
        int n = arr.length;
        
        // Sort individual runs using insertion sort
        for(int i = 0; i < n; i += RUN) {
            insertionSort(arr, i, Math.min(i + RUN - 1, n - 1));
        }
        
        // Merge runs
        for(int size = RUN; size < n; size = 2 * size) {
            for(int left = 0; left < n; left += 2 * size) {
                int mid = left + size - 1;
                int right = Math.min(left + 2 * size - 1, n - 1);
                
                if(mid < right) {
                    merge(arr, left, mid, right);
                }
            }
        }
    }
    
    private void insertionSort(int[] arr, int left, int right) {
        for(int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;
            
            while(j >= left && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
    
    private void merge(int[] arr, int left, int mid, int right) {
        // Similar to merge sort's merge
        int n1 = mid - left + 1;
        int n2 = right - mid;
        
        int[] leftArr = new int[n1];
        int[] rightArr = new int[n2];
        
        for(int i = 0; i < n1; i++) leftArr[i] = arr[left + i];
        for(int j = 0; j < n2; j++) rightArr[j] = arr[mid + 1 + j];
        
        int i = 0, j = 0, k = left;
        
        while(i < n1 && j < n2) {
            if(leftArr[i] <= rightArr[j]) {
                arr[k] = leftArr[i];
                i++;
            } else {
                arr[k] = rightArr[j];
                j++;
            }
            k++;
        }
        
        while(i < n1) arr[k++] = leftArr[i++];
        while(j < n2) arr[k++] = rightArr[j++];
    }
    
    // Time: O(n log n) average/worst, O(n) best
    // Space: O(n)
    // Stable: Yes
}
```

---

## **11. Quick Sort vs Merge Sort Comparison**

| Aspect | Quick Sort | Merge Sort |
|--------|------------|------------|
| Approach | In-place partitioning | Extra arrays for merging |
| Time (Average) | O(n log n) | O(n log n) |
| Time (Worst) | O(n²) | O(n log n) |
| Space | O(log n) | O(n) |
| Stability | No | Yes |
| Cache Performance | Good (in-place) | Poor (extra memory) |
| Best for | Random data | Linked lists, external sorting |

```java
// When to use which?
// Quick Sort: Arrays, in-place sorting needed
// Merge Sort: Linked lists, stable sort needed, worst-case guarantees
```

---

## **12. Sorting Algorithm Selection Guide**

### **Small Arrays (n < 50)**
- **Insertion Sort** - Simple and efficient

### **Almost Sorted Arrays**
- **Insertion Sort** - O(n) best case
- **Tim Sort** - Detects runs

### **Large Arrays with Small Range**
- **Counting Sort** - O(n + k)
- **Radix Sort** - O(d * n)

### **General Purpose (Most Common)**
- **Quick Sort** - Fastest average case
- **Merge Sort** - Stable, guaranteed O(n log n)
- **Tim Sort** - Java/Python use this

### **Linked Lists**
- **Merge Sort** - No extra space needed
- **Insertion Sort** - For small lists

### **External Sorting (Data too large for memory)**
- **Merge Sort** - Can be adapted for external sort

---

## **13. Stability in Sorting**

### **What is Stability?**
Equal elements ka relative order maintain hota hai ya nahi.

```java
// Example: Sort by age, then by name
class Person {
    String name;
    int age;
}

// Input: [(A,20), (B,18), (C,20)]
// Stable sort by age: [(B,18), (A,20), (C,20)]
// Unstable sort by age: [(B,18), (C,20), (A,20)] - order of age=20 changed
```

### **Stable vs Unstable**

| Stable | Unstable |
|--------|----------|
| Bubble Sort | Selection Sort |
| Insertion Sort | Quick Sort |
| Merge Sort | Heap Sort |
| Counting Sort | |
| Radix Sort | |

---

## **14. Java's Built-in Sorting**

```java
class JavaBuiltInSort {
    public void sortingExamples() {
        // For primitives - Quick Sort (Dual-Pivot)
        int[] arr = {5, 3, 8, 1, 6};
        Arrays.sort(arr);  // O(n log n)
        
        // For objects - Tim Sort (Stable)
        String[] strings = {"banana", "apple", "cherry"};
        Arrays.sort(strings);
        
        // Sort with comparator
        Arrays.sort(strings, (a, b) -> b.compareTo(a));  // Descending
        
        // Sort part of array
        Arrays.sort(arr, 0, 3);  // Sort first 3 elements
        
        // Parallel sort (for large arrays)
        Arrays.parallelSort(arr);  // Uses Fork-Join framework
    }
}
```

---

## **15. Custom Sorting with Comparators**

```java
class CustomSorting {
    // Sort by multiple criteria
    public void multiLevelSorting() {
        List<Person> people = new ArrayList<>();
        people.add(new Person("Alice", 25));
        people.add(new Person("Bob", 20));
        people.add(new Person("Charlie", 25));
        
        // Sort by age, then by name
        people.sort((p1, p2) -> {
            if(p1.age != p2.age) return p1.age - p2.age;
            return p1.name.compareTo(p2.name);
        });
        
        // Using Comparator chaining
        people.sort(Comparator
            .comparingInt((Person p) -> p.age)
            .thenComparing(p -> p.name));
    }
    
    // Sort custom objects
    public void customObjectSort() {
        // Method 1: Implement Comparable
        // class Person implements Comparable<Person> {
        //     public int compareTo(Person other) {
        //         return this.age - other.age;
        //     }
        // }
        
        // Method 2: Use Comparator
        Arrays.sort(new Person[10], (a, b) -> a.age - b.age);
        
        // Method 3: Use Comparator.comparing
        Arrays.sort(new Person[10], Comparator.comparing(p -> p.age));
    }
}
```

---

## **Quick Revision Table**

| Algorithm | Best | Average | Worst | Space | Stable | When to Use |
|-----------|------|---------|-------|-------|--------|-------------|
| Bubble | O(n) | O(n²) | O(n²) | O(1) | Yes | Educational |
| Selection | O(n²) | O(n²) | O(n²) | O(1) | No | Small arrays |
| Insertion | O(n) | O(n²) | O(n²) | O(1) | Yes | Almost sorted |
| Merge | O(n log n) | O(n log n) | O(n log n) | O(n) | Yes | Guaranteed performance |
| Quick | O(n log n) | O(n log n) | O(n²) | O(log n) | No | General purpose |
| Heap | O(n log n) | O(n log n) | O(n log n) | O(1) | No | In-place sort |
| Counting | O(n+k) | O(n+k) | O(n+k) | O(k) | Yes | Small range |
| Radix | O(dn) | O(dn) | O(dn) | O(n) | Yes | Fixed-length keys |

---

## **Interview Tips (Hinglish)**

### **1. Pehle Pucho:**
- Array size kitna hai?
- Range kya hai (small/large)?
- Almost sorted hai?
- Stable sort chahiye?
- Extra space allowed hai?

### **2. Algorithm Choose Karne Ka Formula:**

```
if(n < 50) → Insertion Sort
else if(range is small) → Counting Sort
else if(need stable sort) → Merge Sort
else if(space is limited) → Heap Sort
else → Quick Sort
```

### **3. Common Follow-up Questions:**

**Q: Why Quick Sort is faster than Merge Sort in practice?**
- Better cache locality (in-place)
- Less memory allocation
- Works well with modern hardware

**Q: When does Quick Sort become O(n²)?**
- Already sorted array with bad pivot selection
- Solution: Random pivot or median-of-three

**Q: Which sort is used by Java's Arrays.sort()?**
- Primitives: Dual-Pivot Quick Sort
- Objects: Tim Sort (Merge + Insertion)

### **4. Code Writing Tips:**
- Edge cases handle karo (null, empty, single element)
- Use meaningful variable names
- Add comments for complex logic
- Test with different inputs

### **5. Complexity Analysis Yaad Rakho:**
```
Merge Sort: Always O(n log n)
Quick Sort: O(n log n) average
Heap Sort: O(n log n) in-place
Counting Sort: O(n + k) when k = O(n)
```

---

## **Practice Problems**

### **Easy**
1. Sort an array of 0s, 1s, 2s (Dutch National Flag)
2. Sort array with squares of sorted array
3. Sort by parity (even then odd)

### **Medium**
1. Sort colors (LeetCode 75)
2. Maximum gap (LeetCode 164)
3. Sort list (LeetCode 148) - Merge sort on linked list

### **Hard**
1. Count of smaller numbers after self (LeetCode 315)
2. Reverse pairs (LeetCode 493)
3. Maximum performance of a team (LeetCode 1383)

---

## **Pro Tips for Interviews**

1. **Pehle brute force batao** - "Mai Bubble/Insertion se start kar sakta hun"

2. **Trade-offs discuss karo** - "Quick sort fast hai but unstable, Merge sort stable hai but extra space"

3. **Implementation details** - Pivot selection, base case, merge process

4. **Real-world example do** - "Database sorting uses external merge sort"

5. **Optimizations batao** - "We can switch to insertion sort for small subarrays"
---
# Sorting Problems - Complete Solutions

## **EASY PROBLEMS**

---

## **1. Sort an Array of 0s, 1s, 2s (Dutch National Flag)** ⭐

### Theory (Hinglish)
**Problem:** Array mein sirf 0,1,2 hain. Inhe sort karo bina extra space ke.

**Algorithm (Dutch National Flag - 3 pointers):**
- `low` pointer: 0s ke liye
- `mid` pointer: current element
- `high` pointer: 2s ke liye

**Steps:**
1. `mid = 0, low = 0, high = n-1`
2. Agar `arr[mid] == 0` → swap with low, low++, mid++
3. Agar `arr[mid] == 1` → mid++
4. Agar `arr[mid] == 2` → swap with high, high-- (mid++ nahi kyunki swapped element check karna hai)

### Java Code
```java
class DutchNationalFlag {
    public void sortColors(int[] nums) {
        int low = 0;
        int mid = 0;
        int high = nums.length - 1;
        
        while(mid <= high) {
            if(nums[mid] == 0) {
                // Swap with low pointer
                swap(nums, low, mid);
                low++;
                mid++;
            }
            else if(nums[mid] == 1) {
                mid++;
            }
            else { // nums[mid] == 2
                // Swap with high pointer
                swap(nums, mid, high);
                high--;
                // Don't increment mid because swapped element needs checking
            }
        }
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    // Alternative: Counting approach
    public void sortColorsCounting(int[] nums) {
        int count0 = 0, count1 = 0, count2 = 0;
        
        for(int num : nums) {
            if(num == 0) count0++;
            else if(num == 1) count1++;
            else count2++;
        }
        
        int index = 0;
        while(count0-- > 0) nums[index++] = 0;
        while(count1-- > 0) nums[index++] = 1;
        while(count2-- > 0) nums[index++] = 2;
    }
    
    // Time: O(n), Space: O(1)
}
```

---

## **2. Sort Array With Squares of Sorted Array** ⭐

### Theory (Hinglish)
**Problem:** Sorted array diya hai (negative bhi ho sakte hain). Squares sort karke return karo.

**Algorithm (Two Pointers):**
- Negative numbers ke squares bade hote hain
- Two pointers: left (start), right (end)
- Compare absolute values, bada square end se daalo

### Java Code
```java
class SortedSquares {
    public int[] sortedSquares(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        int left = 0;
        int right = n - 1;
        int index = n - 1;  // Fill from end
        
        while(left <= right) {
            int leftSquare = nums[left] * nums[left];
            int rightSquare = nums[right] * nums[right];
            
            if(leftSquare > rightSquare) {
                result[index] = leftSquare;
                left++;
            } else {
                result[index] = rightSquare;
                right--;
            }
            index--;
        }
        
        return result;
    }
    
    // Alternative: Simple approach (O(n log n))
    public int[] sortedSquaresSimple(int[] nums) {
        for(int i = 0; i < nums.length; i++) {
            nums[i] = nums[i] * nums[i];
        }
        Arrays.sort(nums);
        return nums;
    }
    
    // Time: O(n), Space: O(n)
}
```

---

## **3. Sort by Parity (Even then Odd)** ⭐

### Theory (Hinglish)
**Problem:** Array ko sort karo taaki saare even numbers pehle aaye, phir odd numbers.

**Algorithm (Two Pointers):**
- `left` pointer even dhundhta hai
- `right` pointer odd dhundhta hai
- Swap karo

### Java Code
```java
class SortByParity {
    // Method 1: Two pointers (in-place)
    public int[] sortArrayByParity(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        while(left < right) {
            // Find odd on left
            while(left < right && nums[left] % 2 == 0) {
                left++;
            }
            
            // Find even on right
            while(left < right && nums[right] % 2 == 1) {
                right--;
            }
            
            // Swap
            if(left < right) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
            }
        }
        
        return nums;
    }
    
    // Method 2: Using extra array
    public int[] sortArrayByParityExtra(int[] nums) {
        int[] result = new int[nums.length];
        int evenIndex = 0;
        int oddIndex = nums.length - 1;
        
        for(int num : nums) {
            if(num % 2 == 0) {
                result[evenIndex++] = num;
            } else {
                result[oddIndex--] = num;
            }
        }
        
        return result;
    }
    
    // Method 3: Stable sort (maintains relative order)
    public int[] sortArrayByParityStable(int[] nums) {
        Integer[] arr = Arrays.stream(nums).boxed().toArray(Integer[]::new);
        Arrays.sort(arr, (a, b) -> Integer.compare(a % 2, b % 2));
        return Arrays.stream(arr).mapToInt(Integer::intValue).toArray();
    }
    
    // Time: O(n), Space: O(1) for two pointers
}
```

---

## **MEDIUM PROBLEMS**

---

## **4. Sort Colors (LeetCode 75)** ⭐⭐

### Theory (Hinglish)
**Problem:** Exactly same as Dutch National Flag problem. Array mein 0,1,2 ko sort karo.

**Note:** Yeh problem 1st problem jaisi hi hai. LeetCode 75 ka famous problem hai.

### Java Code
```java
class SortColors {
    public void sortColors(int[] nums) {
        // Same as Dutch National Flag
        int low = 0, mid = 0, high = nums.length - 1;
        
        while(mid <= high) {
            if(nums[mid] == 0) {
                swap(nums, low, mid);
                low++;
                mid++;
            } else if(nums[mid] == 1) {
                mid++;
            } else {
                swap(nums, mid, high);
                high--;
            }
        }
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    // Follow-up: One pass, constant space - Already achieved above
    // Time: O(n), Space: O(1)
}
```

---

## **5. Maximum Gap (LeetCode 164)** ⭐⭐

### Theory (Hinglish)
**Problem:** Sorted array mein adjacent elements ka maximum difference find karo. O(n) time mein karna hai.

**Algorithm (Pigeonhole Principle - Bucket Sort):**
1. Min aur max find karo
2. Agar saare elements same hain → return 0
3. Bucket size = (max - min) / (n - 1)
4. Har element ko bucket mein daalo
5. Maximum gap = max(current bucket min - previous bucket max)

**Key Insight:** Maximum gap adjacent buckets ke beech mein hoga, bucket ke andar nahi.

### Java Code
```java
class MaximumGap {
    public int maximumGap(int[] nums) {
        if(nums == null || nums.length < 2) return 0;
        
        int n = nums.length;
        
        // Find min and max
        int min = nums[0];
        int max = nums[0];
        for(int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }
        
        if(min == max) return 0;
        
        // Bucket size and number of buckets
        int bucketSize = (int) Math.ceil((double)(max - min) / (n - 1));
        int bucketCount = (max - min) / bucketSize + 1;
        
        // Buckets store min and max
        int[] bucketMin = new int[bucketCount];
        int[] bucketMax = new int[bucketCount];
        Arrays.fill(bucketMin, Integer.MAX_VALUE);
        Arrays.fill(bucketMax, Integer.MIN_VALUE);
        
        // Fill buckets
        for(int num : nums) {
            int index = (num - min) / bucketSize;
            bucketMin[index] = Math.min(bucketMin[index], num);
            bucketMax[index] = Math.max(bucketMax[index], num);
        }
        
        // Calculate maximum gap
        int maxGap = 0;
        int prevMax = min;
        
        for(int i = 0; i < bucketCount; i++) {
            if(bucketMin[i] == Integer.MAX_VALUE) continue;  // Empty bucket
            
            maxGap = Math.max(maxGap, bucketMin[i] - prevMax);
            prevMax = bucketMax[i];
        }
        
        return maxGap;
    }
    
    // Time: O(n), Space: O(n)
}
```

---

## **6. Sort List (LeetCode 148) - Merge Sort on Linked List** ⭐⭐⭐

### Theory (Hinglish)
**Problem:** Linked list ko sort karo in O(n log n) time and O(1) space.

**Algorithm (Merge Sort on Linked List):**
1. Middle find karo (slow-fast pointer)
2. List ko do halves mein divide karo
3. Recursively dono halves sort karo
4. Sorted halves ko merge karo

**Why Merge Sort for Linked List?**
- Quick sort linked list mein efficient nahi hai (random access nahi hai)
- Merge sort linked list mein O(1) extra space mein ho sakta hai

### Java Code
```java
class SortList {
    public ListNode sortList(ListNode head) {
        if(head == null || head.next == null) return head;
        
        // Step 1: Find middle
        ListNode mid = findMiddle(head);
        ListNode rightHalf = mid.next;
        mid.next = null;  // Split the list
        
        // Step 2: Recursively sort both halves
        ListNode left = sortList(head);
        ListNode right = sortList(rightHalf);
        
        // Step 3: Merge sorted halves
        return merge(left, right);
    }
    
    private ListNode findMiddle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        ListNode prev = null;
        
        while(fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return prev;  // Return node before middle
    }
    
    private ListNode merge(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        while(l1 != null && l2 != null) {
            if(l1.val <= l2.val) {
                current.next = l1;
                l1 = l1.next;
            } else {
                current.next = l2;
                l2 = l2.next;
            }
            current = current.next;
        }
        
        if(l1 != null) current.next = l1;
        if(l2 != null) current.next = l2;
        
        return dummy.next;
    }
    
    // Bottom-up merge sort (O(1) space)
    public ListNode sortListBottomUp(ListNode head) {
        if(head == null || head.next == null) return head;
        
        int length = getLength(head);
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        for(int size = 1; size < length; size *= 2) {
            ListNode current = dummy.next;
            ListNode prev = dummy;
            
            while(current != null) {
                ListNode left = current;
                ListNode right = split(left, size);
                current = split(right, size);
                
                prev.next = merge(left, right);
                
                while(prev.next != null) {
                    prev = prev.next;
                }
            }
        }
        
        return dummy.next;
    }
    
    private ListNode split(ListNode head, int size) {
        if(head == null) return null;
        
        for(int i = 1; i < size && head.next != null; i++) {
            head = head.next;
        }
        
        ListNode next = head.next;
        head.next = null;
        return next;
    }
    
    private int getLength(ListNode head) {
        int length = 0;
        while(head != null) {
            length++;
            head = head.next;
        }
        return length;
    }
    
    // Time: O(n log n), Space: O(log n) for recursion, O(1) for bottom-up
}
```

---

## **HARD PROBLEMS**

---

## **7. Count of Smaller Numbers After Self (LeetCode 315)** ⭐⭐⭐

### Theory (Hinglish)
**Problem:** Har element ke liye count karo kitne smaller elements uske right side mein hain.

**Example:** [5,2,6,1] → [2,1,1,0]

**Algorithm (Merge Sort Modified):**
- Merge sort ke merge step mein counting karo
- Jab right array ka element left se chota ho to count increment karo
- Indexes track karo

### Java Code
```java
class CountSmallerAfterSelf {
    public List<Integer> countSmaller(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        
        // Store (value, originalIndex) pairs
        Pair[] pairs = new Pair[n];
        for(int i = 0; i < n; i++) {
            pairs[i] = new Pair(nums[i], i);
        }
        
        mergeSort(pairs, 0, n - 1, result);
        
        List<Integer> resultList = new ArrayList<>();
        for(int count : result) {
            resultList.add(count);
        }
        return resultList;
    }
    
    private void mergeSort(Pair[] pairs, int left, int right, int[] result) {
        if(left >= right) return;
        
        int mid = left + (right - left) / 2;
        mergeSort(pairs, left, mid, result);
        mergeSort(pairs, mid + 1, right, result);
        merge(pairs, left, mid, right, result);
    }
    
    private void merge(Pair[] pairs, int left, int mid, int right, int[] result) {
        Pair[] temp = new Pair[right - left + 1];
        int i = left;
        int j = mid + 1;
        int k = 0;
        
        // Count smaller elements
        while(i <= mid && j <= right) {
            if(pairs[i].value <= pairs[j].value) {
                // All elements in right that are smaller have been counted
                result[pairs[i].index] += (j - (mid + 1));
                temp[k++] = pairs[i++];
            } else {
                temp[k++] = pairs[j++];
            }
        }
        
        // Remaining elements in left
        while(i <= mid) {
            result[pairs[i].index] += (j - (mid + 1));
            temp[k++] = pairs[i++];
        }
        
        // Remaining elements in right
        while(j <= right) {
            temp[k++] = pairs[j++];
        }
        
        // Copy back
        for(i = left; i <= right; i++) {
            pairs[i] = temp[i - left];
        }
    }
    
    class Pair {
        int value;
        int index;
        
        Pair(int value, int index) {
            this.value = value;
            this.index = index;
        }
    }
    
    // Using Binary Indexed Tree (Fenwick Tree) - Another approach
    public List<Integer> countSmallerBIT(int[] nums) {
        List<Integer> result = new ArrayList<>();
        
        // Coordinate compression
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        Map<Integer, Integer> rank = new HashMap<>();
        for(int i = 0; i < sorted.length; i++) {
            rank.put(sorted[i], i + 1);
        }
        
        FenwickTree bit = new FenwickTree(sorted.length);
        
        for(int i = nums.length - 1; i >= 0; i--) {
            int pos = rank.get(nums[i]);
            result.add(bit.query(pos - 1));
            bit.update(pos, 1);
        }
        
        Collections.reverse(result);
        return result;
    }
    
    class FenwickTree {
        int[] tree;
        
        FenwickTree(int size) {
            tree = new int[size + 2];
        }
        
        void update(int index, int delta) {
            while(index < tree.length) {
                tree[index] += delta;
                index += index & -index;
            }
        }
        
        int query(int index) {
            int sum = 0;
            while(index > 0) {
                sum += tree[index];
                index -= index & -index;
            }
            return sum;
        }
    }
    
    // Time: O(n log n), Space: O(n)
}
```

---

## **8. Reverse Pairs (LeetCode 493)** ⭐⭐⭐

### Theory (Hinglish)
**Problem:** Reverse pairs count karo jahan i < j aur nums[i] > 2 * nums[j].

**Algorithm (Merge Sort Modified):**
- Merge sort ke merge step mein count karo
- Condition: nums[i] > 2 * nums[j]
- Dono halves sorted hain, isliye two pointers use kar sakte hain

### Java Code
```java
class ReversePairs {
    public int reversePairs(int[] nums) {
        return mergeSort(nums, 0, nums.length - 1);
    }
    
    private int mergeSort(int[] nums, int left, int right) {
        if(left >= right) return 0;
        
        int mid = left + (right - left) / 2;
        int count = mergeSort(nums, left, mid) + mergeSort(nums, mid + 1, right);
        
        // Count reverse pairs
        int j = mid + 1;
        for(int i = left; i <= mid; i++) {
            while(j <= right && nums[i] > 2L * nums[j]) {
                j++;
            }
            count += (j - (mid + 1));
        }
        
        // Merge
        merge(nums, left, mid, right);
        
        return count;
    }
    
    private void merge(int[] nums, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left;
        int j = mid + 1;
        int k = 0;
        
        while(i <= mid && j <= right) {
            if(nums[i] <= nums[j]) {
                temp[k++] = nums[i++];
            } else {
                temp[k++] = nums[j++];
            }
        }
        
        while(i <= mid) temp[k++] = nums[i++];
        while(j <= right) temp[k++] = nums[j++];
        
        System.arraycopy(temp, 0, nums, left, temp.length);
    }
    
    // Using Fenwick Tree
    public int reversePairsBIT(int[] nums) {
        // Coordinate compression
        long[] sorted = new long[nums.length * 2];
        for(int i = 0; i < nums.length; i++) {
            sorted[i] = nums[i];
            sorted[i + nums.length] = (long) nums[i] * 2;
        }
        
        Arrays.sort(sorted);
        Map<Long, Integer> rank = new HashMap<>();
        for(int i = 0; i < sorted.length; i++) {
            rank.put(sorted[i], i + 1);
        }
        
        FenwickTree bit = new FenwickTree(sorted.length);
        int count = 0;
        
        for(int i = nums.length - 1; i >= 0; i--) {
            int pos = rank.get((long) nums[i]);
            int targetPos = rank.get((long) nums[i] * 2);
            count += bit.query(pos - 1);
            bit.update(targetPos, 1);
        }
        
        return count;
    }
    
    class FenwickTree {
        int[] tree;
        
        FenwickTree(int size) {
            tree = new int[size + 2];
        }
        
        void update(int index, int delta) {
            while(index < tree.length) {
                tree[index] += delta;
                index += index & -index;
            }
        }
        
        int query(int index) {
            int sum = 0;
            while(index > 0) {
                sum += tree[index];
                index -= index & -index;
            }
            return sum;
        }
    }
    
    // Time: O(n log n), Space: O(n)
}
```

---

## **9. Maximum Performance of a Team (LeetCode 1383)** ⭐⭐⭐

### Theory (Hinglish)
**Problem:** n engineers hain, har engineer ki speed aur efficiency hai. At most k engineers choose karne hain. Team ka performance = (sum of speeds) * (min efficiency of team). Maximum performance find karo.

**Algorithm (Greedy + Min Heap):**
1. Engineers ko efficiency ke descending order mein sort karo
2. Min heap maintain karo of size k for speeds
3. Efficiency decreasing order mein iterate karo
4. Current engineer ki efficiency minimum hogi (kyunki descending order mein hai)
5. Sum of speeds maintain karo
6. Performance = sum * current efficiency

### Java Code
```java
class MaximumPerformance {
    private static final int MOD = 1_000_000_007;
    
    public int maxPerformance(int n, int[] speed, int[] efficiency, int k) {
        // Create pairs of (efficiency, speed)
        Engineer[] engineers = new Engineer[n];
        for(int i = 0; i < n; i++) {
            engineers[i] = new Engineer(efficiency[i], speed[i]);
        }
        
        // Sort by efficiency descending
        Arrays.sort(engineers, (a, b) -> b.efficiency - a.efficiency);
        
        // Min heap to store speeds
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        
        long sumSpeed = 0;
        long maxPerformance = 0;
        
        for(Engineer eng : engineers) {
            // Add current engineer's speed
            minHeap.add(eng.speed);
            sumSpeed += eng.speed;
            
            // If more than k engineers, remove the smallest speed
            if(minHeap.size() > k) {
                sumSpeed -= minHeap.poll();
            }
            
            // Calculate performance with current min efficiency
            long performance = sumSpeed * eng.efficiency;
            maxPerformance = Math.max(maxPerformance, performance);
        }
        
        return (int) (maxPerformance % MOD);
    }
    
    class Engineer {
        int efficiency;
        int speed;
        
        Engineer(int efficiency, int speed) {
            this.efficiency = efficiency;
            this.speed = speed;
        }
    }
    
    // Time: O(n log n + n log k), Space: O(n + k)
}
```

---

## **Additional Practice Problems**

### **Bonus Problem 1: Find All Duplicates in Array** ⭐⭐

```java
class FindAllDuplicates {
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> result = new ArrayList<>();
        
        for(int i = 0; i < nums.length; i++) {
            int index = Math.abs(nums[i]) - 1;
            if(nums[index] < 0) {
                result.add(index + 1);
            } else {
                nums[index] = -nums[index];
            }
        }
        
        return result;
    }
    
    // Time: O(n), Space: O(1) excluding output
}
```

### **Bonus Problem 2: Find Missing Positive** ⭐⭐

```java
class FirstMissingPositive {
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        
        // Place each number in its correct position
        for(int i = 0; i < n; i++) {
            while(nums[i] > 0 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                swap(nums, i, nums[i] - 1);
            }
        }
        
        // Find first missing positive
        for(int i = 0; i < n; i++) {
            if(nums[i] != i + 1) {
                return i + 1;
            }
        }
        
        return n + 1;
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    // Time: O(n), Space: O(1)
}
```

### **Bonus Problem 3: Largest Number** ⭐⭐

```java
class LargestNumber {
    public String largestNumber(int[] nums) {
        // Convert to string array
        String[] strs = new String[nums.length];
        for(int i = 0; i < nums.length; i++) {
            strs[i] = String.valueOf(nums[i]);
        }
        
        // Custom sort: compare concatenated strings
        Arrays.sort(strs, (a, b) -> (b + a).compareTo(a + b));
        
        // Handle case where all numbers are 0
        if(strs[0].equals("0")) return "0";
        
        // Build result
        StringBuilder result = new StringBuilder();
        for(String str : strs) {
            result.append(str);
        }
        
        return result.toString();
    }
    
    // Time: O(n log n * L) where L is average length, Space: O(n)
}
```

---

## **Quick Reference Table**

| Problem | Key Technique | Time | Space |
|---------|--------------|------|-------|
| Dutch National Flag | 3 Pointers | O(n) | O(1) |
| Sorted Squares | Two Pointers | O(n) | O(n) |
| Sort by Parity | Two Pointers | O(n) | O(1) |
| Maximum Gap | Bucket Sort | O(n) | O(n) |
| Sort List | Merge Sort on LL | O(n log n) | O(log n) |
| Count Smaller | Modified Merge Sort | O(n log n) | O(n) |
| Reverse Pairs | Modified Merge Sort | O(n log n) | O(n) |
| Max Performance | Sort + Min Heap | O(n log n) | O(n) |

---

## **Interview Tips Summary**

### **For Easy Problems:**
- Dutch National Flag: 3 pointers ka concept yaad rakho
- Sorted squares: Negative numbers ka dhyan rakho
- Sort by parity: Simple two pointer

### **For Medium Problems:**
- Maximum gap: Bucket sort ka intuition important hai
- Sort list: Merge sort on linked list master karo

### **For Hard Problems:**
- Count smaller: Merge sort mein counting ka logic samjho
- Reverse pairs: Condition check karte time overflow se bacho
- Max performance: Greedy + heap combination

### **Common Mistakes to Avoid:**
1. Integer overflow (use long for multiplications)
2. Off-by-one errors in merge sort
3. Not handling empty or single element arrays
4. Forgetting to reverse result for count smaller
