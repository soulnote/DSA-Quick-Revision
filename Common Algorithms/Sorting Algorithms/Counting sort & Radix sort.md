# Counting Sort

Counting Sort ek **non-comparison based sorting algorithm** hai. Iska matlab hai ki yeh elements ko compare karke sort nahi karta, balki unki counts ka use karta hai. Yeh tabhi efficient hota hai jab aapke paas **integers ki ek limited range** ho.

**Concept**:

1.  **Count Array Banayein**: Input array ke har element ki frequency (kitni baar aaya hai) count karein aur use ek `count` array mein store karein. `count` array ka size input array ke elements ki range par depend karta hai (e.g., agar elements 0 se 9 hain, toh `count` array ka size 10 hoga).
2.  **Cumulative Count Calculate Karein**: `count` array ko modify karein taaki har index par us index tak ke saare elements ki cumulative count store ho. Yeh cumulative count batati hai ki output array mein har element ki final position kya hogi.
3.  **Output Array Fill Karein**: Input array ko peeche se traverse karein. Har element `x` ke liye, `count[x]` se uski position dhundein, element ko `output` array mein us position par rakhein, aur phir `count[x]` ko decrease kar dein. Peeche se traverse karne se **stability** maintain hoti hai (yani, equal elements ka relative order maintain rehta hai).

### Java Implementation (Hinglish Comments ke Saath)

```java
import java.util.Arrays;

public class CountingSortHinglish {

    public static void countingSort(int[] arr) {
        int n = arr.length;

        // Agar array empty ya null hai, toh kuch nahi karna
        if (n == 0 || arr == null) {
            return;
        }

        // 1. Max element dhundo array mein, taaki count array ka size determine kar sakein
        int max = arr[0];
        for (int i = 1; i < n; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        // Output array banayein jismein sorted elements rakhenge
        int[] output = new int[n];

        // Count array banayein. Size (max + 1) hoga kyunki 0 se max tak ke numbers ho sakte hain.
        // Har index pe corresponding element ki frequency store hogi.
        int[] count = new int[max + 1];

        // Count array ko 0 se initialize karein (default Java mein 0 hota hai, but explicit achha hai)
        // Arrays.fill(count, 0); // Iski zaroorat nahi hai agar default behavior use kar rahe hain

        // 2. Har element ki frequency ko count array mein store karein
        for (int i = 0; i < n; i++) {
            count[arr[i]]++;
        }

        // 3. Count array ko modify karein taaki cumulative counts store hon
        // Ye positions batayega ki har element output array mein kahan aayega
        for (int i = 1; i <= max; i++) {
            count[i] += count[i - 1];
        }

        // 4. Output array ko fill karein input array ko peeche se traverse karte hue
        // Peeche se traverse karne se stability maintain hoti hai
        for (int i = n - 1; i >= 0; i--) {
            // arr[i] ki final position count[arr[i]] - 1 hai
            output[count[arr[i]] - 1] = arr[i];
            // Element ko place karne ke baad uski count ko decrease kar do
            count[arr[i]]--;
        }

        // Sorted elements ko original array mein copy karein
        for (int i = 0; i < n; i++) {
            arr[i] = output[i];
        }
    }

    // Utility method array print karne ke liye
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // Main method jahan hum Counting Sort ko test karenge
    public static void main(String[] args) {
        int[] arr = {4, 2, 2, 8, 3, 3, 1};
        System.out.println("Original Array:");
        printArray(arr);

        countingSort(arr);

        System.out.println("Sorted Array (Counting Sort):");
        printArray(arr);

        int[] arr2 = {10, 7, 5, 10, 2, 5, 7, 2, 1, 9};
        System.out.println("\nOriginal Array 2:");
        printArray(arr2);
        countingSort(arr2);
        System.out.println("Sorted Array 2 (Counting Sort):");
        printArray(arr2);
    }
}
```

### Time & Space Complexity (Counting Sort)

  * **Time Complexity**: $O(n + k)$

      * `n` input array ka size hai.
      * `k` input elements ki range hai (maximum element value - minimum element value + 1).
      * Max element dhundhne mein $O(n)$
      * Count array fill karne mein $O(n)$
      * Cumulative counts calculate karne mein $O(k)$
      * Output array fill karne mein $O(n)$
      * Overall: $O(n + k)$. Agar $k$ bahut bada na ho (comparable to $n$), toh yeh linear time mein sort kar deta hai, jo ki comparison sorts ($O(n \\log n)$) se fast hai.

  * **Space Complexity**: $O(n + k)$

      * `output` array ke liye $O(n)$ space.
      * `count` array ke liye $O(k)$ space.
      * Overall: $O(n + k)$. Yeh **in-place** nahi hai.

-----

# Radix Sort 

Radix Sort bhi ek **non-comparison based sorting algorithm** hai. Yeh Counting Sort ya Bucket Sort jaise linear sorting algorithms ko digits (ya bits) ke basis par repeatedly apply karta hai. Yeh sabse kam significant digit (LSD) se shuru karke sabse zyada significant digit (MSD) tak sort karta hai.

**Concept**:

1.  **Maximum Number of Digits Dhundein**: Input array mein sabse bade number mein kitne digits hain, yeh pata karein. Itni baar hi sorting passes lagengi.
2.  **Digit-by-Digit Sorting**: Sabse kam significant digit (units place) se shuru karke, har digit position par array ko stable sorting algorithm (jaise **Counting Sort**) ka use karke sort karein.
      * Pehle units place ke digits ke hisab se sort karo.
      * Phir tens place ke digits ke hisab se sort karo.
      * Phir hundreds place ke digits ke hisab se sort karo, aur aise hi aage badhte raho jab tak sabse bade number ka MSD sort na ho jaaye.

### Java Implementation (Hinglish Comments ke Saath)

```java
import java.util.Arrays;

public class RadixSortHinglish {

    // A utility function to get maximum value in arr[]
    // Array mein sabse bada element dhundo
    static int getMax(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

    // A function to do counting sort of arr[] according to the digit represented by exp.
    // Given 'exp' (exponent like 1, 10, 100...), array ko digits ke basis par sort karta hai
    // Yeh Counting Sort ka modified version hai for Radix Sort (stable sort)
    static void countSortForRadix(int[] arr, int n, int exp) {
        int[] output = new int[n]; // Output array
        int[] count = new int[10]; // 0-9 digits ke liye (size 10)
        Arrays.fill(count, 0); // Count array ko 0 se initialize karo

        // Har digit ki frequency ko count array mein store karein
        // (arr[i] / exp) % 10 se current digit milta hai
        for (int i = 0; i < n; i++) {
            count[(arr[i] / exp) % 10]++;
        }

        // Cumulative count calculate karein
        // Har index pe us index tak ke digits ki cumulative count
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        // Output array ko fill karein, input array ko peeche se traverse karte hue
        // Peeche se traverse karne se stability maintain hoti hai
        for (int i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
        }

        // Sorted elements ko original array mein copy karein
        for (int i = 0; i < n; i++) {
            arr[i] = output[i];
        }
    }

    // Main Radix Sort function
    public static void radixSort(int[] arr) {
        int n = arr.length;

        // Agar array empty ya null hai, toh kuch nahi karna
        if (n == 0 || arr == null) {
            return;
        }

        // 1. Array mein sabse bada number dhundo taaki digits ki count pata chale
        int max = getMax(arr);

        // 2. Har digit position par Counting Sort apply karo
        // (exp = 1 for units place, 10 for tens, 100 for hundreds, etc.)
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countSortForRadix(arr, n, exp);
        }
    }

    // Utility method array print karne ke liye
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // Main method jahan hum Radix Sort ko test karenge
    public static void main(String[] args) {
        int[] arr = {170, 45, 75, 90, 802, 24, 2, 66};
        System.out.println("Original Array:");
        printArray(arr);

        radixSort(arr);

        System.out.println("Sorted Array (Radix Sort):");
        printArray(arr);

        int[] arr2 = {123, 45, 9, 2345, 567, 1};
        System.out.println("\nOriginal Array 2:");
        printArray(arr2);
        radixSort(arr2);
        System.out.println("Sorted Array 2 (Radix Sort):");
        printArray(arr2);
    }
}
```

### Time & Space Complexity (Radix Sort)

  * **Time Complexity**: $O(d \\cdot (n + k))$

      * `d` sabse bade number mein digits ki sankhya hai. (Example: agar numbers 1000 tak hain, toh d = 4)
      * `n` input array ka size hai.
      * `k` har digit ke liye possible values ki range hai (usually 10, for 0-9 digits).
      * Har digit position ke liye, hum `countSortForRadix` call karte hain, जिसकी complexity $O(n+k)$ होती है। चूंकि `d` iterations होती हैं, कुल complexity $O(d \\cdot (n+k))$ हो जाती है।
      * Agar numbers `N` range mein hain, toh `d` approximately $\\log\_k N$ hota hai. So, complexity $O(n \\log\_k N)$.
      * Jab `k` (base of numbers) aur `d` constant ho ya chote hon, toh Radix Sort **linear time** ($O(n)$) mein perform karta hai.

  * **Space Complexity**: $O(n + k)$

      * `output` array ke liye $O(n)$ space.
      * `count` array ke liye $O(k)$ space (jo digits ke liye hai, usually 10).
      * Overall: $O(n + k)$. Yeh bhi **in-place** नहीं है।

-----

### Comparison (तुलना):

| Feature            | Counting Sort                                  | Radix Sort                                     |
| :----------------- | :--------------------------------------------- | :--------------------------------------------- |
| **Type** | Non-Comparison Based                           | Non-Comparison Based                           |
| **Best Use Case** | Limited range of integers                      | Large range of integers (with more digits)     |
| **Time Complexity**| $O(n + k)$                                     | $O(d \\cdot (n + k))$                           |
| **Space Complexity**| $O(n + k)$                                     | $O(n + k)$                                     |
| **Stability** | Yes (agar sahi se implement kiya jaye)         | Yes (agar inner sorting stable ho, jaise Counting Sort) |
| **In-place** | No                                             | No                                             |
| **Limitation** | Only for integers (ya discrete items with integer keys) and efficient only for limited range of `k`. | Only for integers (ya data jo digits/bits mein represent ho sake) |

**Jab use karna chahiye?**

  * **Counting Sort**: Jab aapke paas integers ka ek array ho aur un integers ki range (`k`) input array size (`n`) ke comparably chhoti ho. Jaise, 0-100 tak ke marks ko sort karna.
  * **Radix Sort**: Jab aapke paas bade integers ka array ho, jahan numbers mein digits zyada hon, lekin har digit ki range chhoti ho (jaise 0-9). Yeh Sorting ke liye comparison operations se zyada efficient ho sakta hai agar `d` aur `k` bahut bade na hon. Filesystem sorting ya large integer keys ko sort karne mein use hota hai.
