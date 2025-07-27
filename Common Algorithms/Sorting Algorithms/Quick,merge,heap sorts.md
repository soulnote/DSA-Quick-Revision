
# Quicksort 
Quicksort ek efficient **sorting algorithm** hai. Yeh **divide-and-conquer strategy** par kaam karta hai. Ismein ek **pivot element** choose kiya jata hai aur array ko us pivot ke around partition kiya jata hai. Ismein average case mein iski **time complexity O(n log n)** hoti hai.


```java
public class QuickSortHinglish {

    // Main quicksort method jo array aur uske boundaries leta hai
    public static void quickSort(int[] arr, int low, int high) {
        // Agar low high se kam hai, toh partitioning aur sorting ki zaroorat hai
        if (low < high) {
            // Partition index dhundho
            int pi = partition(arr, low, high);

            // Pivot ke left part ko sort karo
            quickSort(arr, low, pi - 1);

            // Pivot ke right part ko sort karo
            quickSort(arr, pi + 1, high);
        }
    }

    // Partition method jo pivot element choose karta hai aur elements ko rearrange karta hai
    public static int partition(int[] arr, int low, int high) {
        // Last element ko pivot choose karte hain
        int pivot = arr[high];
        // i pointer smaller element ko track karega
        int i = (low - 1);

        // Array ke har element par iterate karo
        for (int j = low; j < high; j++) {
            // Agar current element pivot se chota ya barabar hai
            if (arr[j] <= pivot) {
                // i ko increment karo
                i++;

                // arr[i] aur arr[j] ko swap karo
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // Pivot element ko uski sahi jagah par rakho
        // arr[i+1] aur arr[high] (jo pivot hai) ko swap karo
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        // Pivot ka final index return karo
        return i + 1;
    }
}
```

### 1. Time Complexity (Samay Lagne Wali Complexity)

Quicksort ki time complexity is baat par depend karti hai ki hum pivot element ko kitni acchi tarah se select karte hain aur array kitni evenly divide hota hai.

* **Best Case Time Complexity: $O(n \log n)$**
    * Yeh case tab hota hai jab pivot element array ko har baar lagbhag do barabar parts mein divide karta hai.
    * Har "division" step mein, hum array ko traverse karte hain ($O(n)$ time lagta hai). Aur aise $\log n$ levels hote hain division ke. Isliye, total time $n \times \log n$ hota hai.
    * **Example**: Agar array ko exactly aadha-aadha divide kiya ja raha hai har step mein.

* **Average Case Time Complexity: $O(n \log n)$**
    * Practically, average case mein bhi Quicksort ki performance best case ke aas-paas hi rehti hai.
    * Jab pivot selection random ya reasonable hoti hai (yani, array ko bohot zyada unbalanced parts mein divide nahi karta), toh average time complexity bhi $O(n \log n)$ hoti hai.
    * Yeh hi wajah hai ki Quicksort real-world applications mein bohot popular hai.

* **Worst Case Time Complexity: $O(n^2)$**
    * Yeh case tab hota hai jab pivot selection bohot hi kharab ho, aur array ko har baar ek bohot unbalanced tareeke se divide kiya jae.
    * **Example**: Agar array pehle se hi sorted ho (ascending ya descending order mein), aur aap har baar last (ya first) element ko pivot choose karein. Is situation mein, partition function har baar sirf ek element ko alag karta hai aur baaki sab ko ek taraf rakhta hai.
    * Har step mein, aapko $n, n-1, n-2, \ldots, 1$ elements ko process karna padta hai, jiska sum $n(n+1)/2$ hota hai, jo ki $O(n^2)$ ke barabar hai.

### 2. Space Complexity (Jagah Lagne Wali Complexity)

Space complexity is baat par depend karti hai ki recursive calls ke liye call stack mein kitni extra memory use hoti hai.

* **Best Case Space Complexity: $O(\log n)$**
    * Jab Quicksort array ko evenly divide karta hai (best case time complexity ki tarah), recursive call stack ki depth $\log n$ hoti hai.
    * Har call stack entry kuch constant space leti hai, isliye total space $O(\log n)$ hota hai.

* **Average Case Space Complexity: $O(\log n)$**
    * Average case mein bhi call stack ki depth typically $\log n$ hi hoti hai, isliye space complexity $O(\log n)$ rehti hai.

* **Worst Case Space Complexity: $O(n)$**
    * Worst case mein (jaise ki jab array har baar bohot unbalanced tareeke se divide ho), recursive call stack ki depth $n$ ho sakti hai.
    * Agar aap us example ko dekhein jahan array pehle se sorted ho aur aap last element ko pivot choose karein, toh call stack $n$ levels deep ja sakta hai.
    * Yeh space complexity isliye **in-place sorting algorithm** ke comparison mein thodi zyada ho sakti hai jo $O(1)$ extra space lete hain (jaise Selection Sort ya Bubble Sort).

### Summary Table (Saraansh):

| Complexity Type     | Best Case           | Average Case        | Worst Case          |
| :------------------ | :------------------ | :------------------ | :------------------ |
| **Time Complexity** | $O(n \log n)$       | $O(n \log n)$       | $O(n^2)$            |
| **Space Complexity**| $O(\log n)$         | $O(\log n)$         | $O(n)$              |

**Note**: Humare diye gaye Java implementation mein humne last element ko pivot choose kiya hai. Yeh implementation worst case mein $O(n^2)$ time complexity aur $O(n)$ space complexity dikha sakta hai agar array pehle se sorted ho. Isse bachne ke liye, **random pivot selection** ya **median-of-three pivot selection** jaise techniques use kiye ja sakte hain.
----

## Merge Sort 

Merge Sort bhi ek popular aur efficient **sorting algorithm** hai. Yeh bhi Quicksort ki tarah **divide-and-conquer strategy** par based hai. Ismein array ko baar-baar aadhe-aadhe mein divide kiya jata hai jab tak single elements na mil jaayen, phir unhe sorted tareeke se **merge** kiya jata hai. Iski **time complexity hamesha $O(n \\log n)$** rehti hai, chahe best, average, ya worst case ho.

Yahan Merge Sort ka Java implementation Hinglish comments ke saath diya gaya hai:

```java
public class MergeSortHinglish {

    // Main mergeSort method jo array, starting aur ending indices leta hai
    public static void mergeSort(int[] arr, int left, int right) {
        // Base case: Agar array mein ek ya koi element nahi hai, toh return kar do
        if (left < right) {
            // Middle point dhundo array ko do halves mein divide karne ke liye
            int mid = (left + right) / 2;

            // First half ko recursively sort karo
            mergeSort(arr, left, mid);
            // Second half ko recursively sort karo
            mergeSort(arr, mid + 1, right);

            // Ab dono sorted halves ko merge karo
            merge(arr, left, mid, right);
        }
    }

    // Yeh method do sorted sub-arrays ko merge karta hai
    public static void merge(int[] arr, int left, int mid, int right) {
        // Pehle sub-array ka size
        int n1 = mid - left + 1;
        // Doosre sub-array ka size
        int n2 = right - mid;

        // Temporary arrays banao elements copy karne ke liye
        int[] L = new int[n1];
        int[] R = new int[n2];

        // Pehle sub-array ke elements copy karo L[] mein
        for (int i = 0; i < n1; ++i) {
            L[i] = arr[left + i];
        }
        // Doosre sub-array ke elements copy karo R[] mein
        for (int j = 0; j < n2; ++j) {
            R[j] = arr[mid + 1 + j];
        }

        // Temporary arrays ko merge karo main array mein
        int i = 0, j = 0; // L[] aur R[] ke initial indices
        int k = left;     // Merged array (arr[]) ka initial index

        // Jab tak dono arrays mein elements hain, compare karo aur chote element ko arr[] mein rakho
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        // Agar L[] mein remaining elements hain, unhe copy karo
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        // Agar R[] mein remaining elements hain, unhe copy karo
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
}
```


### Time aur Space Complexity (Hinglish Mein):

Merge Sort ki sabse badi khaasiyat yeh hai ki iski time complexity **hamesha consistent** rehti hai, chahe data ki initial state kuch bhi ho.

### 1\. Time Complexity (Samay Lagne Wali Complexity)

  * **Best Case Time Complexity: $O(n \\log n)$**
  * **Average Case Time Complexity: $O(n \\log n)$**
  * **Worst Case Time Complexity: $O(n \\log n)$**

**Explanation**:

  * **Divide Step**: Array ko $\\log n$ times aadhe-aadhe mein divide kiya jata hai jab tak single elements na mil jaayen. Har division level par constant time lagta hai.
  * **Merge Step**: Har level par, `merge` function $O(n)$ time leta hai sabhi elements ko merge karne ke liye.
  * Kyuki $\\log n$ levels hain aur har level par $O(n)$ time lag raha hai merging mein, total time complexity $O(n \\log n)$ ho jaati hai.
  * Merge Sort ki performance input data ki arrangement (sorted, reverse sorted, random) par nirbhar nahi karti, isliye iski time complexity hamesha $O(n \\log n)$ rehti hai.

### 2\. Space Complexity (Jagah Lagne Wali Complexity)

  * **Space Complexity: $O(n)$**

**Explanation**:

  * Merge Sort ko **auxiliary space** (extra memory) ki zaroorat padti hai do temporary arrays (`L` aur `R`) banane ke liye `merge` operation ke dauran.
  * Worst case mein, in temporary arrays ko milakar $O(n)$ space ki zaroorat pad sakti hai (jab hum array ko do halves mein divide karte hain).
  * Isliye, Merge Sort **in-place sorting algorithm** nahi hai (jaise Quicksort ka partition part ya Bubble/Selection Sort), kyuki yeh sorting ke liye extra memory use karta hai.

### Summary Table (Saraansh):

| Complexity Type     | Best Case           | Average Case        | Worst Case          |
| :------------------ | :------------------ | :------------------ | :------------------ |
| **Time Complexity** | $O(n \\log n)$       | $O(n \\log n)$       | $O(n \\log n)$       |
| **Space Complexity**| $O(n)$              | $O(n)$              | $O(n)$              |

**Jab use karna chahiye?**
Merge Sort un situations ke liye best hai jahan **stability** (equal elements ka relative order maintain karna) aur **guaranteed $O(n \\log n)$ performance** critical ho, jaise ki large datasets ko sort karna ya linked lists par sorting karna (jahan random access mushkil hota hai).

-----

# Heap Sort
## Heap Sort Kya Hai?

Heap Sort ek efficient sorting algorithm hai jo do main steps mein kaam karta hai:

1.  **Build a Max-Heap:** Diye gaye array ko ek **Max-Heap** mein convert karna. Max-Heap ek special type ka binary tree hota hai jahan har parent node ki value uske children nodes se hamesha badi ya barabar hoti hai. Isse yeh ensure hota hai ki root node (array ka pehla element) hamesha sabse bada element hoga.

2.  **Extract Elements and Sort:** Max-Heap banne ke baad, hum root element (jo ki sabse bada element hai) ko array ke last element se swap karte hain. Phir, heap ka size ek se kam karte hain aur bache hue heap ko fir se Max-Heap banate hain (`heapify` operation). Is process ko tab tak repeat karte hain jab tak saare elements sort na ho jayein.

**Analogy:**
Imagine kijiye aapke paas kuch books hain aur aapko unhe height ke hisaab se line mein lagana hai.

  * **Step 1 (Build Max-Heap):** Aap un books ko ek aise stack mein jama karte hain jahan har parent book apne neeche wali books se lambi ho. Is tarah se, sabse upar wali book hamesha sabse lambi hogi.
  * **Step 2 (Extract and Sort):** Aap sabse upar wali (sabse lambi) book ko nikal kar line ke end mein rakh dete hain. Ab bache hue stack ko fir se theek karte hain (taki sabse upar phir sabse lambi book aa jaye). Is process ko tab tak repeat karte hain jab tak saari books line mein na lag jayein.

-----

## Binary Heap Data Structure

Heap Sort ko samajhne ke liye **Binary Heap** ko samajhna zaroori hai. Binary Heap ek **complete binary tree** hota hai (matlab saare levels filled hain, last level left se right filled hai) jo array ke form mein store hota hai.

Do tarah ke Binary Heaps hote hain:

1.  **Max-Heap:** Har parent node ki value uske children nodes se greater than or equal to hoti hai.

      * `parent >= child`
      * Root node hamesha sabse bada element hota hai.

2.  **Min-Heap:** Har parent node ki value uske children nodes se less than or equal to hoti hai.

      * `parent <= child`
      * Root node hamesha sabse chhota element hota hai.

Heap Sort mein hum **Max-Heap** ka use karte hain.

**Array representation mein relationships:**
Agar ek node `i` index par hai:

  * Uska **left child** `2*i + 1` par hoga.
  * Uska **right child** `2*i + 2` par hoga.
  * Uska **parent** `(i-1) / 2` par hoga (integer division).

-----

## Heap Sort Algorithm Ke Steps Detail Mein

Maante hain ki humein ek array `arr` ko ascending order mein sort karna hai.

### Phase 1: Build Max-Heap

Is phase mein, hum diye gaye array ko ek Max-Heap mein convert karte hain. Yeh bottom-up approach se kiya jaata hai.

1.  Array ke **last non-leaf node** se shuru karte hain aur root node tak peeche jaate hain.
      * Last non-leaf node ka index `(n/2) - 1` hota hai, jahan `n` array ka size hai.
2.  Har non-leaf node `i` ke liye, `heapify()` operation perform karte hain.
      * `heapify()` ek function hai jo guarantee karta hai ki `i` index par jo node hai aur uske children, woh Max-Heap property satisfy karein. Agar nahi karte, toh `i` ko uske largest child se swap karta hai aur recursively child par `heapify` call karta hai.

### `heapify()` Function Ka Logic:

`heapify(arr, n, i)`: (jahan `n` heap ka current size hai, aur `i` woh index hai jise heapify karna hai)

1.  `largest = i` set karo (maano current node hi largest hai).
2.  `left_child = 2*i + 1` aur `right_child = 2*i + 2` calculate karo.
3.  Agar `left_child` `n` se chhota hai (matlab child exist karta hai) aur `arr[left_child]` `arr[largest]` se bada hai, toh `largest = left_child`.
4.  Agar `right_child` `n` se chhota hai (matlab child exist karta hai) aur `arr[right_child]` `arr[largest]` se bada hai, toh `largest = right_child`.
5.  Agar `largest` ab bhi `i` ke barabar nahi hai (matlab `i` ka koi child usse bada nikla), toh `arr[i]` aur `arr[largest]` ko swap karo.
6.  Ab recursively `heapify` ko `arr, n, largest` par call karo, kyunki swap karne se largest child ki sub-tree ki heap property disturb ho sakti hai.

### Phase 2: Extract Elements and Sort

Max-Heap banne ke baad, root element (`arr[0]`) sabse bada hota hai. Hum isse nikal kar array ke end mein place karte hain.

1.  `n-1` se `0` tak loop chalao (yaani array ke last index se shuru karke pehle index tak). `i` current last element ka index hoga.
2.  `arr[0]` (root, largest element) ko `arr[i]` (last element) se swap karo.
3.  Heap ka size ek se kam karo (`n` ko `i` tak reduce karo).
4.  Ab naya root (`arr[0]`, jo pehle `arr[i]` tha) shayad Max-Heap property satisfy na kare. Isliye, `arr` par `heapify(arr, i, 0)` call karo. Ye `arr[0]` ko uski sahi position par le aayega aur baki bache hue elements mein se sabse bade ko root bana dega.

Jab loop khatam hoga, toh array sorted ho jayega.

-----

## Heap Sort Ka Java Code Example

```java
public class HeapSort {

    // Main sorting function
    public void sort(int arr[]) {
        int n = arr.length;

        // Phase 1: Build Max-Heap (array ko heapify karo)
        // Last non-leaf node se shuru karte hain aur upar root tak jaate hain
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // Phase 2: Extract elements one by one from heap
        for (int i = n - 1; i > 0; i--) {
            // Current root (largest element) ko last element se swap karo
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // Chhote hue heap (n-1 size) par heapify operation perform karo
            // Root (0 index) ko uski sahi position par laao
            heapify(arr, i, 0);
        }
    }

    // Function to heapify a subtree rooted with node i
    // n is size of heap
    void heapify(int arr[], int n, int i) {
        int largest = i; // Root ko largest element maano
        int left = 2 * i + 1; // Left child ka index
        int right = 2 * i + 2; // Right child ka index

        // Agar left child exist karta hai aur root se bada hai
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        // Agar right child exist karta hai aur ab tak ke largest se bada hai
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        // Agar largest root nahi hai (matlab koi child bada nikla)
        if (largest != i) {
            // Swap karo root ko largest child se
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Recursively heapify karo affected sub-tree ko
            heapify(arr, n, largest);
        }
    }

    // Utility function to print array (for testing)
    static void printArray(int arr[]) {
        int n = arr.length;
        for (int i = 0; i < n; ++i)
            System.out.print(arr[i] + " ");
        System.out.println();
    }

    // Main method to test
    public static void main(String args[]) {
        int arr[] = {12, 11, 13, 5, 6, 7};
        System.out.println("Original array:");
        printArray(arr);

        HeapSort hs = new HeapSort();
        hs.sort(arr);

        System.out.println("Sorted array (using Heap Sort):");
        printArray(arr);

        int arr2[] = {4, 10, 3, 5, 1};
        System.out.println("\nOriginal array 2:");
        printArray(arr2);
        hs.sort(arr2);
        System.out.println("Sorted array 2 (using Heap Sort):");
        printArray(arr2);
    }
}
```

-----

## Heap Sort Ki Complexity

  * **Time Complexity:**

      * **Building the Max-Heap:** `O(n)` (This is a complex derivation, but it boils down to `O(n)`).
      * **Extracting elements and Heapifying:** Har `heapify` operation `O(log n)` time leti hai, aur hum ise `n-1` baar karte hain. Toh ye `O(n log n)` hua.
      * **Overall Time Complexity:** `O(n log n)`. Yeh average aur worst case dono ke liye true hai, jo isse Quicksort (worst-case `O(n^2)`) se zyada consistent banata hai.

  * **Space Complexity:** `O(1)` (In-place). Heap ko array mein hi store karte hain aur bahut kam auxiliary space use hota hai recursion stack ke liye (jo `O(log n)` ho sakta hai worst case mein).

-----

## Heap Sort Ke Fayde Aur Nuksan

**Fayde (Advantages):**

1.  **Guaranteed O(n log n):** Iski worst-case time complexity bhi `O(n log n)` hai, jo Quick Sort se better hai worst-case scenarios mein.
2.  **In-place Sorting:** Isse bahut kam extra memory ki zaroorat padti hai.
3.  **Reliable:** Iski performance input data ke order par zyada depend nahi karti.

**Nuksan (Disadvantages):**

1.  **Not Stable:** Agar array mein identical elements hain, toh unka relative order sort hone ke baad preserve nahi hota.
2.  **Cache Performance:** Iski cache performance Quick Sort ya Merge Sort jitni achhi nahi hoti, kyunki heap ek tree structure hai jo array mein scattered ho sakti hai, jisse memory access patterns random ho sakte hain.
3.  **Complex Implementation:** Compared to simpler sorts like Insertion or Selection sort, iska implementation thoda zyada complex lag sakta hai.

Heap Sort ek bahut hi robust aur efficient algorithm hai, khaas kar jab memory constraints hon aur worst-case performance ki guarantee zaroori ho.
