
# 1\. Insertion Sort

Insertion Sort ek simple sorting algorithm hai jo us tarah se kaam karta hai jaise hum taash ke patton (playing cards) ko sort karte hain. Array ko do parts mein divide kiya jata hai: ek sorted part aur ek unsorted part. Har iteration mein, unsorted part se ek element uthaya jata hai aur use sorted part mein uski sahi position par insert kiya jata hai.

**Concept**:

  * Array ko logically do parts mein split kiya jaata hai: Left part sorted (shuru mein sirf pehla element), aur Right part unsorted.
  * Unsorted part se ek element liya jaata hai.
  * Us element ko sorted part mein uski correct position par shift karke insert kiya jaata hai. Jab tak current element usse chhota na ho, sorted part ke elements ko aage shift karte raho.

### Java Implementation (Hinglish Comments ke Saath)

```java
public class InsertionSortHinglish {

    public static void insertionSort(int[] arr) {
        int n = arr.length; // Array ka total size

        // Pehle element ko sorted maante hain, toh loop doosre element se shuru hoga
        for (int i = 1; i < n; i++) {
            int key = arr[i]; // Current element jise sorted part mein insert karna hai
            int j = i - 1;    // Sorted part ka last element

            // `key` ko sorted part mein uski sahi position par move karo
            // Jab tak j >= 0 (array bounds ke andar) aur current element (arr[j])
            // `key` se bada hai, tab tak elements ko ek position aage shift karte raho
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j]; // Element ko ek position aage shift karo
                j = j - 1;           // Pichle element par jao
            }
            arr[j + 1] = key; // `key` ko uski sahi position par insert karo
        }
    }

    // Utility method array print karne ke liye
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // Main method jahan hum insertion sort ko test karenge
    public static void main(String[] args) {
        int[] arr = {12, 11, 13, 5, 6};
        System.out.println("Original Array:");
        printArray(arr);

        insertionSort(arr);

        System.out.println("Sorted Array (Insertion Sort):");
        printArray(arr);

        int[] arr2 = {5, 1, 4, 2, 8};
        System.out.println("\nOriginal Array 2:");
        printArray(arr2);
        insertionSort(arr2);
        System.out.println("Sorted Array 2 (Insertion Sort):");
        printArray(arr2);
    }
}
```

### Time & Space Complexity (Insertion Sort)

  * **Time Complexity**:
      * **Best Case: $O(n)$**
          * Tab hota hai jab array pehle se hi sorted ho. Har element ko bas ek baar compare kiya jaata hai, aur koi shifting nahi hoti.
      * **Average Case: $O(n^2)$**
          * Jab elements random order mein hon.
      * **Worst Case: $O(n^2)$**
          * Tab hota hai jab array reverse sorted ho. Har element ko uske aage ke saare sorted elements ke saath compare aur shift karna padta hai.
  * **Space Complexity: $O(1)$**
      * Yeh ek **in-place** sorting algorithm hai, matlab ise sorting ke liye bahut kam (constant) extra memory ki zaroorat padti hai.

-----

# 2\. Bubble Sort 

Bubble Sort ek simple sorting algorithm hai jo baar-baar adjacent elements ke pairs ko compare karta hai aur agar wo galat order mein hon toh unhe swap karta hai. Yeh process tab tak chalti rehti hai jab tak koi swap na ho, jiska matlab hai ki array ab sorted hai. Iska naam "bubble" isliye hai kyuki sabse bade elements dheere-dheere array ke end tak "bubble up" karte hain.

**Concept**:

  * Har pass (iteration) mein, adjacent elements ko compare karo.
  * Agar left wala element right wale se bada hai, toh unhe swap karo.
  * Ek pass ke baad, sabse bada unsorted element apni sahi position par (end mein) pahunch jaata hai.
  * Yeh process $n-1$ passes tak repeat karo, ya tab tak jab tak koi swap na ho (optimised version).

### Java Implementation (Hinglish Comments ke Saath)

```java
public class BubbleSortHinglish {

    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        boolean swapped; // Ye flag track karega ki kisi pass mein koi swap hua ya nahi

        // Outer loop: n-1 passes ke liye
        for (int i = 0; i < n - 1; i++) {
            swapped = false; // Har pass ki beginning mein reset karo

            // Inner loop: Adjacent elements ko compare aur swap karne ke liye
            // Har pass ke baad, largest unsorted element end mein pahunch jaata hai,
            // isliye inner loop ko har baar ek kam element tak chalana hai (n - 1 - i)
            for (int j = 0; j < n - 1 - i; j++) {
                // Agar current element next wale se bada hai, toh swap karo
                if (arr[j] > arr[j + 1]) {
                    // Swap arr[j] aur arr[j+1]
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true; // Swap hua toh flag true karo
                }
            }

            // Agar is pass mein koi swap nahi hua, toh array already sorted hai
            // Break kar do loop se
            if (!swapped) {
                break;
            }
        }
    }

    // Utility method array print karne ke liye
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // Main method jahan hum bubble sort ko test karenge
    public static void main(String[] args) {
        int[] arr = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("Original Array:");
        printArray(arr);

        bubbleSort(arr);

        System.out.println("Sorted Array (Bubble Sort):");
        printArray(arr);

        int[] arr2 = {1, 2, 3, 4, 5}; // Already sorted array
        System.out.println("\nOriginal Array 2 (Already Sorted):");
        printArray(arr2);
        bubbleSort(arr2);
        System.out.println("Sorted Array 2 (Bubble Sort):");
        printArray(arr2);
    }
}
```

### Time & Space Complexity (Bubble Sort)

  * **Time Complexity**:
      * **Best Case: $O(n)$**
          * Tab hota hai jab array pehle se hi sorted ho. `swapped` flag ki wajah se, yeh sirf ek pass mein check kar lega aur break ho jaayega.
      * **Average Case: $O(n^2)$**
          * Jab elements random order mein hon.
      * **Worst Case: $O(n^2)$**
          * Tab hota hai jab array reverse sorted ho. Har element ko har pass mein travel karna padta hai.
  * **Space Complexity: $O(1)$**
      * Yeh bhi ek **in-place** sorting algorithm hai.

-----

# 3\. Selection Sort 

Selection Sort bhi ek simple comparison sorting algorithm hai. Yeh har pass mein unsorted part se sabse chhota (ya sabse bada) element dhundhta hai aur use sorted part ke starting position par place karta hai.

**Concept**:

  * Array ko do parts mein split karo: sorted aur unsorted. Shuru mein, sorted part empty hai aur unsorted part poora array hai.
  * Har pass mein:
      * Unsorted part se **minimum element** dhundho.
      * Us minimum element ko unsorted part ke **first element** ke saath swap karo. Isse minimum element sorted part mein apni sahi jagah par aa jaata hai.
  * Yeh process $n-1$ times repeat karo.

### Java Implementation (Hinglish Comments ke Saath)

```java
public class SelectionSortHinglish {

    public static void selectionSort(int[] arr) {
        int n = arr.length;

        // Outer loop: Har pass ke liye, n-1 elements tak chalega
        // Kyunki last element apne aap sort ho jaayega
        for (int i = 0; i < n - 1; i++) {
            // Unsorted part se minimum element ka index dhundo
            int min_idx = i; // Current element ko initially minimum man lo

            // Inner loop: Baaki unsorted elements mein compare karo
            for (int j = i + 1; j < n; j++) {
                // Agar arr[j] current minimum se chota hai, toh update karo min_idx
                if (arr[j] < arr[min_idx]) {
                    min_idx = j;
                }
            }

            // Minimum element milne ke baad, use current position (i) ke saath swap karo
            // Agar min_idx original i se alag hai, tabhi swap ki zaroorat hai
            if (min_idx != i) {
                int temp = arr[min_idx];
                arr[min_idx] = arr[i];
                arr[i] = temp;
            }
        }
    }

    // Utility method array print karne ke liye
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // Main method jahan hum selection sort ko test karenge
    public static void main(String[] args) {
        int[] arr = {64, 25, 12, 22, 11};
        System.out.println("Original Array:");
        printArray(arr);

        selectionSort(arr);

        System.out.println("Sorted Array (Selection Sort):");
        printArray(arr);

        int[] arr2 = {5, 4, 3, 2, 1};
        System.out.println("\nOriginal Array 2:");
        printArray(arr2);
        selectionSort(arr2);
        System.out.println("Sorted Array 2 (Selection Sort):");
        printArray(arr2);
    }
}
```

### Time & Space Complexity (Selection Sort)

  * **Time Complexity**:
      * **Best Case: $O(n^2)$**
      * **Average Case: $O(n^2)$**
      * **Worst Case: $O(n^2)$**
          * Selection Sort ki time complexity hamesha $O(n^2)$ rehti hai, chahe array sorted ho ya reverse sorted. Har pass mein, use poore unsorted part ko traverse karna padta hai minimum element dhundhne ke liye, aur phir swap karna padta hai. Number of comparisons ($n(n-1)/2$) hamesha same rehte hain.
  * **Space Complexity: $O(1)$**
      * Yeh bhi ek **in-place** sorting algorithm hai.

-----

### Summary Table (Saraansh):

| Algorithm        | Time Complexity (Best) | Time Complexity (Avg) | Time Complexity (Worst) | Space Complexity | Notes                                                               |
| :--------------- | :--------------------- | :-------------------- | :---------------------- | :--------------- | :------------------------------------------------------------------ |
| **Insertion Sort** | $O(n)$                 | $O(n^2)$              | $O(n^2)$                | $O(1)$           | Small datasets ke liye ya partially sorted arrays ke liye efficient. |
| **Bubble Sort** | $O(n)$                 | $O(n^2)$              | $O(n^2)$                | $O(1)$           | Simple, lekin large datasets ke liye inefficient.                   |
| **Selection Sort** | $O(n^2)$               | $O(n^2)$              | $O(n^2)$                | $O(1)$           | Minimum swaps ki guarantee deta hai, useful for writes-expensive memory. |

Ye teeno algorithms "simple" ya "elementary" sorting algorithms hain. Jabki ye conceptual roop se samajhne mein aasan hain, bade datasets ke liye ye utne efficient nahi hain jitne Quicksort, Merge Sort, ya Heap Sort ( जिनकी $O(n \\log n)$ time complexity होती है).
