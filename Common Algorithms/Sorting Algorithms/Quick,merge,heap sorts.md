
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

    // Utility method array print karne ke liye
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // Main method jahan hum quicksort ko test karenge
    public static void main(String[] args) {
        int[] arr = {10, 7, 8, 9, 1, 5};
        int n = arr.length;

        System.out.println("Original Array:");
        printArray(arr);

        // Quicksort function ko call karo
        quickSort(arr, 0, n - 1);

        System.out.println("Sorted Array:");
        printArray(arr);

        int[] arr2 = {25, 2, 89, 12, 7, 0, 56};
        int n2 = arr2.length;
        System.out.println("\nOriginal Array 2:");
        printArray(arr2);
        quickSort(arr2, 0, n2 - 1);
        System.out.println("Sorted Array 2:");
        printArray(arr2);
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

    // Utility method array print karne ke liye
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // Main method jahan hum merge sort ko test karenge
    public static void main(String[] args) {
        int[] arr = {12, 11, 13, 5, 6, 7};
        int n = arr.length;

        System.out.println("Original Array:");
        printArray(arr);

        // Merge Sort function ko call karo
        mergeSort(arr, 0, n - 1);

        System.out.println("Sorted Array:");
        printArray(arr);

        int[] arr2 = {38, 27, 43, 3, 9, 82, 10};
        int n2 = arr2.length;
        System.out.println("\nOriginal Array 2:");
        printArray(arr2);
        mergeSort(arr2, 0, n2 - 1);
        System.out.println("Sorted Array 2:");
        printArray(arr2);
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

## Heap Sort

Heap Sort ek comparison-based sorting algorithm hai jo **Binary Heap data structure** ka use karta hai. Yeh ek efficient, **in-place** sorting algorithm hai jiski **time complexity worst case mein bhi $O(n \\log n)$** rehti hai. Heap Sort do main steps mein kaam karta hai:

1.  **Build a Max-Heap**: Diye gaye array ko ek Max-Heap mein convert karna. Max-Heap mein, parent node ki value uske children nodes se hamesha badi ya barabar hoti hai.
2.  **Extract Elements**: Heap ke root (jo ki largest element hota hai Max-Heap mein) ko baar-baar extract karna aur use array ke end mein place karna, aur bache hue heap ko phir se adjust karna (heapify).

```java
public class HeapSortHinglish {

    // Main heapSort method jo array leta hai
    public static void heapSort(int[] arr) {
        int n = arr.length;

        // Step 1: Array ko Max-Heap mein convert karo (Build Max-Heap)
        // Hum non-leaf nodes se shuru karte hain aur upar root tak jaate hain
        // Last non-leaf node ka index n/2 - 1 hota hai
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // Step 2: Ek-ek karke elements ko heap se extract karo
        for (int i = n - 1; i > 0; i--) {
            // Current root (largest element) ko last element ke saath swap karo
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // Chhote hue heap par heapify call karo
            // Current heap size i hai
            heapify(arr, i, 0);
        }
    }

    // Heapify method: Yeh ek sub-tree ko heap property maintain karne ke liye adjust karta hai
    // n: heap ka size
    // i: root node ka index jahan se heapify shuru karna hai
    public static void heapify(int[] arr, int n, int i) {
        int largest = i; // Sabse bade element ko root man lo
        int left = 2 * i + 1; // Left child ka index
        int right = 2 * i + 2; // Right child ka index

        // Agar left child largest se bada hai
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        // Agar right child (ab naye) largest se bada hai
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        // Agar largest, root (i) nahi hai
        if (largest != i) {
            // Largest element ko current root ke saath swap karo
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Recursively affected sub-tree par heapify call karo
            heapify(arr, n, largest);
        }
    }

    // Utility method array print karne ke liye
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // Main method jahan hum heap sort ko test karenge
    public static void main(String[] args) {
        int[] arr = {12, 11, 13, 5, 6, 7};
        int n = arr.length;

        System.out.println("Original Array:");
        printArray(arr);

        // Heap Sort function ko call karo
        heapSort(arr);

        System.out.println("Sorted Array:");
        printArray(arr);

        int[] arr2 = {38, 27, 43, 3, 9, 82, 10};
        int n2 = arr2.length;
        System.out.println("\nOriginal Array 2:");
        printArray(arr2);
        heapSort(arr2);
        System.out.println("Sorted Array 2:");
        printArray(arr2);
    }
}
```

### Code Explanation (Hinglish Mein):

1.  **`heapSort(int[] arr)`**:

      * Yeh main method hai jo array ko sort karta hai.
      * **`n = arr.length;`**: Array ka total size store karta hai.
      * **Step 1: Build Max-Heap**:
          * `for (int i = n / 2 - 1; i >= 0; i--)`: Yeh loop array ko Max-Heap mein convert karta hai.
          * `n/2 - 1` **first non-leaf node** ka index hota hai. Hum last non-leaf node se shuru karte hain aur backwards (`i--`) root (`0`) tak jaate hain.
          * Har non-leaf node par **`heapify`** function call kiya jata hai taaki us node ke subtree ko heap property maintain karne ke liye adjust kiya ja sake.
          * Is step ke baad, `arr[0]` (root) sabse bada element hoga.
      * **Step 2: Extract Elements and Sort**:
          * `for (int i = n - 1; i > 0; i--)`: Yeh loop sorted elements ko array ke end mein place karta hai.
          * **Swap**: Current largest element (jo `arr[0]` par hai) ko array ke **last unsorted element** (`arr[i]`) ke saath swap kiya jata hai. Isse largest element apni sahi sorted position par aa jata hai.
          * **`heapify(arr, i, 0)`**: Swap karne ke baad, heap ka size 1 se kam ho jata hai (`i` naya size hai), aur naya `arr[0]` galat position par ho sakta hai. Isliye, bache hue heap (size `i`) par `heapify` call kiya jata hai, `0` se shuru karte hue, taaki heap property phir se maintain ho sake.

2.  **`heapify(int[] arr, int n, int i)`**:

      * Yeh helper function ek subtree ko Max-Heap property maintain karne ke liye adjust karta hai, jiska root `i` index par hai aur total heap size `n` hai.
      * **`largest = i`**: Initially, hum current node `i` ko hi largest मान लेते hain.
      * **`left = 2 * i + 1`** aur **`right = 2 * i + 2`**: Binary heap mein, left child ka index `2i + 1` aur right child ka index `2i + 2` hota hai.
      * **Comparisons**:
          * `if (left < n && arr[left] > arr[largest])`: Check karta hai ki left child heap boundary ke andar hai aur root se bada hai. Agar haan, toh `largest` ko left child ka index bana deta hai.
          * `if (right < n && arr[right] > arr[largest])`: Same check right child ke liye.
      * **Swap and Recurse**:
          * `if (largest != i)`: Agar `largest` abhi bhi `i` nahi hai (matlab, koi child `i` se bada nikla), toh `arr[i]` aur `arr[largest]` ko **swap** kiya jata hai.
          * `heapify(arr, n, largest)`: Swap ke baad, affected subtree par recursively `heapify` call kiya jata hai taaki heap property poore subtree mein maintain ho sake.


### Time aur Space Complexity (Hinglish Mein):

Heap Sort ki time aur space complexity is tarah hai:

### 1\. Time Complexity (Samay Lagne Wali Complexity)

  * **Best Case Time Complexity: $O(n \\log n)$**
  * **Average Case Time Complexity: $O(n \\log n)$**
  * **Worst Case Time Complexity: $O(n \\log n)$**

**Explanation**:

  * **Building the Heap**: Array ko Max-Heap mein convert karne mein $O(n)$ time lagta hai. Although loop $n$ times chalta hai, `heapify` function ki complexity height (`log n`) par depend karti hai, aur har node ke liye heapify call nahi hota. Poore heap ko build karne ki cumulative complexity $O(n)$ hoti hai.
  * **Extracting Elements**: `n-1` elements ko extract kiya jata hai. Har extraction operation mein (root ko swap karna aur phir `heapify` call karna) $O(\\log n)$ time lagta hai. Isliye, $n$ elements ke liye $n \\times O(\\log n) = O(n \\log n)$ time lagta hai.
  * Dono steps ko mila kar, Heap Sort ki total time complexity $O(n \\log n)$ hoti hai. Yeh iski sabse badi khaasiyat hai ki yeh **worst case mein bhi $O(n \\log n)$ performance guarantee karta hai**, Quicksort ke vipreet jo worst case mein $O(n^2)$ ho sakta hai.

### 2\. Space Complexity (Jagah Lagne Wali Complexity)

  * **Space Complexity: $O(1)$**

**Explanation**:

  * Heap Sort ek **in-place sorting algorithm** hai. Iska matlab hai ki sorting ke liye ise **bahut kam (constant) extra memory** ki zaroorat padti hai, jo ki input array ke size par depend nahi karta.
  * `heapify` function recursive hai, lekin stack space ki zaroorat $O(\\log n)$ hoti hai, jo ki constant space mein consider ki jaati hai agar input size bohot bada na ho. Technically, $O(1)$ auxiliary space tab hota hai jab recursion stack ko ignore kiya jaye ya iterative version use kiya jaye. Lekin, competitive programming mein $O(\\log n)$ recursion stack space ko bhi $O(1)$ mana jata hai, especially comparison with $O(N)$ space algorithms like Merge Sort.

### Summary Table (Saraansh):

| Complexity Type     | Best Case           | Average Case        | Worst Case          |
| :------------------ | :------------------ | :------------------ | :------------------ |
| **Time Complexity** | $O(n \\log n)$       | $O(n \\log n)$       | $O(n \\log n)$       |
| **Space Complexity**| $O(1)$              | $O(1)$              | $O(1)$              |

-----

**Jab use karna chahiye?**
Heap Sort un situations ke liye behtar hai jahan **guaranteed $O(n \\log n)$ time complexity** aur **$O(1)$ auxiliary space** dono important ho. Yeh Quicksort ki tarah fast nahi hota average case mein, lekin iski worst-case performance consistent hoti hai, jo ise real-time systems aur embedded environments ke liye ek accha option banati hai.

