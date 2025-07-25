
## 703\. Kth Largest Element in a Stream

Kth Largest Element in a Stream ek data structure design problem hai jismein humein ek stream se aate hue numbers mein se hamesha Kth largest element nikalna hota hai.

-----

### Description/Overview

Imagine karo ki aapko ek **endless stream of numbers** mil raha hai, aur aapko har baar jab koi naya number aata hai, toh us stream mein maujood **Kth largest element** batana hai. Yeh koi one-time calculation nahi hai; numbers aate rahenge aur aapko update karte rehna hai. Kth largest ka matlab hai, agar numbers ko descending order mein sort kiya jaaye, toh jo Kth position par hoga. Jaise, agar K=3 aur numbers hain `[1, 5, 2, 7, 3]`, toh sorted order `[7, 5, 3, 2, 1]` hoga aur 3rd largest `3` hai.

Is problem ko solve karne ke liye hum **Min-Heap (PriorityQueue)** ka use karte hain.

### Approach (Pahunch)

1.  **Min-Heap ki Property:** Agar humein Kth largest element chahiye, toh iska matlab hai ki humein apne paas sirf **top K largest elements** rakhne hain. Agar hum ek **Min-Heap** use karein jismein sirf `K` elements hon, toh heap ka **root (smallest element)** hamesha Kth largest element hoga\! Kyunki us heap mein `K` elements hain aur woh sabhi current Kth largest se bade ya barabar hain, aur heap ka root unmein se sabse chhota hai.

2.  **Constructor (Initialize KthLargest object):**

      * `K` value ko store karein.
      * Ek `PriorityQueue<Integer>` banayein (by default yeh min-heap hota hai).
      * Jo `nums` array diya gaya hai, uske har element ko `add` method ke through process karein.

3.  **`add(int val)` Method:**

      * Jab ek naya number `val` aata hai:
          * `val` ko heap mein add karo.
          * Agar heap ka size `K` se zyada ho jata hai, toh heap ke **smallest element ko remove kar do (poll())**. Yeh ensure karega ki heap mein hamesha `K` ya usse kam elements rahen, aur woh sabhi largest elements hon.
      * `add` karne ke baad, heap ka root (pehle element) hi current Kth largest element hoga. Usse `return` kar do (`pq.peek()`).

### Solution (Hal)

Hum ek Min-Heap (Java mein `PriorityQueue`) banate hain jiska size hum `K` tak maintain karte hain. Jab bhi koi naya number aata hai, hum use heap mein daal dete hain. Agar heap ka size `K` se zyada ho jaata hai, toh hum sabse chhota element (heap ka root) nikal dete hain. Isse yeh guaranteed hota hai ki heap mein hamesha `K` sabse bade elements rahenge, aur heap ka root element hi Kth largest element hoga.

### Code (Code)

```java
import java.util.PriorityQueue;

class KthLargest {
    private int k;
    private PriorityQueue<Integer> pq; // Min-Heap to store the K largest elements

    public KthLargest(int k, int[] nums) {
        this.k = k;
        this.pq = new PriorityQueue<>(); // Default is a min-heap

        // Add all initial numbers to the heap
        for (int num : nums) {
            add(num); // Use the add method logic to maintain size K
        }
    }

    public int add(int val) {
        // Naye value ko heap mein daalo
        pq.offer(val);

        // Agar heap ka size K se zyada ho gaya hai, toh sabse chota element nikal do
        // Isse heap mein hamesha K largest elements rahenge, aur root Kth largest hoga
        if (pq.size() > k) {
            pq.poll(); // Remove the smallest element (root of min-heap)
        }

        // Kth largest element hamesha heap ka root hoga
        return pq.peek();
    }
}

/**
 * Your KthLargest object will be instantiated and called as such:
 * KthLargest obj = new KthLargest(k, nums);
 * int param_1 = obj.add(val);
 */
```

-----

## 1046\. Last Stone Weight

Last Stone Weight ek heap problem hai jismein humein stones ke weights diye gaye hain aur unhe "smash" karne ke baad bacha hua akhri stone ka weight nikalna hota hai.

-----

### Description/Overview

Imagine karo ki aapke paas bahut saare **stones** hain, aur har stone ka apna ek **weight** hai. Aapko ek special process follow karna hai:

  * Har baar, **do sabse bhaari stones** uthao.
  * Agar unka weight equal hai (`x == y`), toh dono destroy ho jaate hain.
  * Agar unka weight alag hai (`x != y`), toh chhota wala stone (`x`) destroy ho jaata hai, aur bade wale stone (`y`) ka weight `y - x` ho jaata hai. Yeh bacha hua stone wapas stones ke set mein daal diya jaata hai.
    Aapko yeh process tab tak repeat karna hai jab tak **ek bhi stone na bache ya sirf ek stone bache**. Akhri mein jo stone bachega, uska weight return karna hai. Agar koi nahi bachta, toh `0` return karna hai.

Is problem ko solve karne ke liye hum **Max-Heap (PriorityQueue)** ka use karte hain.

### Approach (Pahunch)

1.  **Max-Heap ka Use:** Kyunki humein hamesha **do sabse bade stones** chahiye, ek **Max-Heap** iske liye perfect hai. Max-Heap hamesha sabse bade element ko root par rakhta hai, jisse hum `pq.poll()` karke easily nikal sakte hain.

2.  **Heap Initialization:**

      * Ek `PriorityQueue<Integer>` banayein, lekin ise **Max-Heap** banane ke liye ek custom comparator pass karein: `new PriorityQueue<>(Collections.reverseOrder())`.
      * `stones` array ke saare weights ko is `Max-Heap` mein add kar do.

3.  **Smashing Process:**

      * Jab tak heap mein **ek se zyada stones** hain (yani `pq.size() > 1`):
          * Heap se sabse bada stone (`y`) nikalo (`pq.poll()`).
          * Heap se doosra sabse bada stone (`x`) nikalo (`pq.poll()`).
          * Agar `x` aur `y` equal nahi hain (`y != x`):
              * Naya stone (`y - x`) ko heap mein wapas daal do (`pq.offer(y - x)`).

4.  **Result:**

      * Loop khatam hone ke baad:
          * Agar heap empty hai (`pq.isEmpty()`), toh iska matlab hai saare stones destroy ho gaye, `0` return karo.
          * Warna, heap mein ek hi stone bacha hoga, uska weight return karo (`pq.peek()`).

### Solution (Hal)

Hum ek Max-Heap ka use karte hain taaki hum hamesha do sabse bade stones ko efficiently nikal sakein. Jab bhi hum do stones ko smash karte hain, agar unka weight alag hota hai, toh bache hue stone ko wapas heap mein daal dete hain. Yeh process tab tak chalta hai jab tak heap mein ek ya zero stones na bachein. Ant mein, jo stone bacha uska weight return kar dete hain, ya `0` agar koi nahi bacha.

### Code (Code)

```java
import java.util.Collections;
import java.util.PriorityQueue;

class Solution {
    public int lastStoneWeight(int[] stones) {
        // Max-Heap banayein taaki hum sabse bade elements nikal sakein
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());

        // Saare stones ko heap mein add karein
        for (int stone : stones) {
            pq.offer(stone);
        }

        // Jab tak heap mein 1 se zyada stones hain (yani kam se kam 2 stones hain smash karne ke liye)
        while (pq.size() > 1) {
            // Sabse bada stone (y) nikalo
            int y = pq.poll();
            // Doosra sabse bada stone (x) nikalo
            int x = pq.poll();

            // Agar weights alag hain, toh naya stone bachega
            if (y != x) {
                pq.offer(y - x); // Naye stone ko wapas heap mein daal do
            }
            // Agar weights equal hain, toh dono destroy ho jaate hain, kuch nahi karna
        }

        // Loop khatam hone ke baad:
        // Agar heap empty hai (saare stones destroy ho gaye), toh 0 return karo
        // Warna, jo ek akela stone bacha hai uska weight return karo
        return pq.isEmpty() ? 0 : pq.peek();
    }
}
```

-----

## 973\. K Closest Points to Origin

K Closest Points to Origin ek sorting/heap problem hai jismein humein origin (0,0) se K sabse nazdeek points dhundhne hote hain.

-----

### Description/Overview

Imagine karo ki aapko ek 2D plane par kuch **points** diye gaye hain. Har point ke coordinates `(x, y)` hain. Aapko **origin `(0, 0)` se K sabse nazdeek points** find karne hain. Do points ke beech ki distance **Euclidean distance** se calculate hoti hai: $\\sqrt{(x\_1 - x\_2)^2 + (y\_1 - y\_2)^2}$. Lekin, kyunki humein sirf comparison karna hai, hum square root ko ignore kar sakte hain aur sirf squared Euclidean distance (`x^2 + y^2`) ka use kar sakte hain, kyuki square root ek monotonically increasing function hai (agar $A \< B$ toh $\\sqrt{A} \< \\sqrt{B}$).

Is problem ko solve karne ke liye hum **Max-Heap (PriorityQueue)** ya **Quickselect (Partitioning)** algorithm ka use kar sakte hain. Max-Heap approach zyaada intuitive aur aasan hai.

### Approach (Pahunch) (Using Max-Heap)

1.  **Max-Heap ka Use:** Agar humein K sabse nazdeek points chahiye, toh hum ek **Max-Heap** use karenge. Heap mein `[distance_squared, point_x, point_y]` store karenge. Lekin distance ke base par sort karenge. Max-Heap mein humesha K points rakhenge. Agar naya point aata hai jiska distance heap ke top (maximum distance) se kam hai, toh hum top wale ko remove karke naye wale ko add kar denge.

2.  **Heap Initialization:**

      * Ek `PriorityQueue<int[]>` banayein. Is `int[]` mein `[x, y]` store hoga.
      * Comparator use karenge taaki yeh **Max-Heap** ban sake on the basis of **squared Euclidean distance**. Distance square calculate karne ke liye, `(point[0] * point[0] + point[1] * point[1])` use karenge. Comparator `(b.dist - a.dist)` jaisa hoga.

3.  **Processing Points:**

      * `points` array ke har `point` ke liye:
          * `point` ko heap mein add karo.
          * Agar heap ka size `K` se zyada ho jata hai, toh heap ke **root (maximum distance wale point)** ko remove kar do (`pq.poll()`).
      * Isse yeh ensure hoga ki heap mein hamesha `K` points rahenge, aur woh sabhi current `K` closest points honge.

4.  **Result:**

      * Jab saare points process ho jayein, toh heap mein jo `K` points bachenge, wahi hamare K closest points hain. Unhe ek `int[][]` array mein convert karke return kar do.

### Solution (Hal)

Hum ek Max-Heap maintain karte hain jismein K points hote hain. Heap ka top element (root) woh point hota hai jiski origin se distance sabse zyada hoti hai among the K points in the heap. Jab ek naya point aata hai, hum uski distance calculate karte hain. Agar yeh naya point heap ke top wale point se nazdeek hai, toh hum top wale ko nikal kar naye wale ko daal dete hain. Ant mein, heap mein jo K points bachenge, wahi K closest points honge.

### Code (Code)

```java
import java.util.PriorityQueue;

class Solution {
    public int[][] kClosest(int[][] points, int k) {
        // Max-Heap banayein jo points ko unki squared Euclidean distance ke basis par sort karega
        // (b[0]*b[0] + b[1]*b[1]) - (a[0]*a[0] + a[1]*a[1]) will make it a Max-Heap (largest distance at top)
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) ->
            (b[0] * b[0] + b[1] * b[1]) - (a[0] * a[0] + a[1] * a[1])
        );

        // Har point ko process karein
        for (int[] point : points) {
            pq.offer(point); // Point ko heap mein daalo

            // Agar heap ka size K se zyada ho gaya hai, toh sabse door wala point nikal do
            // (Max-Heap ka root, jismein sabse zyada distance hai)
            if (pq.size() > k) {
                pq.poll();
            }
        }

        // Result array mein heap ke points ko copy karein
        int[][] result = new int[k][2];
        for (int i = 0; i < k; i++) {
            result[i] = pq.poll();
        }

        return result;
    }
}
```

-----

## 215\. Kth Largest Element in an Array

Kth Largest Element in an Array ek classic sorting/selection problem hai jismein humein ek unsorted array mein se Kth largest element find karna hota hai.

-----

### Description/Overview

Aapko ek **unsorted integer array** `nums` diya gaya hai. Aapko is array mein se **Kth largest element** find karna hai. Dhyaan rahe, yeh unique Kth largest element hai, na ki Kth distinct element. Matlab, agar array sorted descending order mein ho, toh jo Kth position par element aayega, wohi answer hai.

For example, `nums = [3,2,1,5,6,4], k = 2` -\> sorted `[6,5,4,3,2,1]`, 2nd largest `5` hai.

Is problem ko solve karne ke kai tareeke hain:

1.  **Sorting:** Array ko sort karke direct answer nikal lo.
2.  **Min-Heap (PriorityQueue):** `K` largest elements ko track karne ke liye.
3.  **Quickselect (Partitioning):** Average case mein linear time complexity ke liye.

Hum **Min-Heap** approach aur **Quickselect** dono ka overview denge, par code Min-Heap ka provide karenge kyunki woh zyaada common aur easy to implement hai.

### Approach (Pahunch) (Using Min-Heap)

1.  **Min-Heap ka Use:** Same as 703. Kth Largest Element in a Stream. Hum ek Min-Heap banayenge jismein hum `K` elements store karenge. Heap ka root hamesha Kth largest element hoga.

2.  **Heap Initialization and Population:**

      * Ek `PriorityQueue<Integer>` banayein (by default yeh min-heap hota hai).
      * `nums` array ke har element ko heap mein add karein.

3.  **Maintain Heap Size K:**

      * Har `num` ko heap mein add karne ke baad:
          * Agar heap ka size `K` se zyada ho jata hai, toh heap ke **smallest element ko remove kar do (poll())**.
      * Isse yeh ensure hoga ki heap mein hamesha `K` elements rahenge, aur woh sabhi current `K` largest elements honge.

4.  **Result:**

      * Jab saare numbers process ho jayein, toh heap ka root (`pq.peek()`) hi Kth largest element hoga. Usse return kar do.

### Approach (Pahunch) (Using Quickselect - Optional, Advanced)

Quickselect partitioning algorithm ka use karta hai (jo Quicksort mein hota hai) Kth element ko find karne ke liye. Average case mein iski time complexity $O(N)$ hoti hai, jo sorting ($O(N \\log N)$) aur heap ($O(N \\log K)$) se better hai.

1.  **Partitioning:** Ek pivot choose karte hain aur array ko do parts mein partition karte hain: pivot se chote elements aur pivot se bade elements.
2.  **Recursive Search:** Agar pivot ki position `(N - K)` hai (yani N-Kth smallest = Kth largest), toh humein mil gaya. Agar pivot position `(N - K)` se kam hai, toh right subarray mein search karo. Agar zyada hai, toh left subarray mein search karo.

### Solution (Hal) (Min-Heap based)

Hum ek Min-Heap use karte hain aur usmein array ke elements ko daalna shuru karte hain. Hum heap ka size `K` par control karte hain. Jab bhi heap ka size `K` se zyada ho jaata hai, hum heap ke sabse chote element ko nikal dete hain. Is tarah, jab saare elements process ho jaate hain, heap mein sirf `K` sabse bade elements bachte hain, aur unmein se sabse chota (heap ka root) hi Kth largest element hota hai.

### Code (Code) (Min-Heap based)

```java
import java.util.PriorityQueue;

class Solution {
    public int findKthLargest(int[] nums, int k) {
        // Min-Heap banayein
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        // Har number ko heap mein add karein
        for (int num : nums) {
            pq.offer(num);

            // Agar heap ka size K se zyada ho gaya, toh sabse chota element nikal do
            // Isse heap mein hamesha K largest elements rahenge
            if (pq.size() > k) {
                pq.poll();
            }
        }

        // Loop khatam hone ke baad, heap ka root hi Kth largest element hoga
        return pq.peek();
    }
}
```

-----

## 621\. Task Scheduler

Task Scheduler ek greedy/priority queue problem hai jismein humein CPU tasks ko schedule karna hota hai jismein same tasks ke beech cool-down period hota hai.

-----

### Description/Overview

Imagine karo ki aapke paas ek CPU hai aur kuch **tasks** hain (jaise `A, A, B, C, A`). Har task ko execute hone mein 1 unit time lagta hai. Ek **cool-down period `n`** diya gaya hai. Iska matlab hai ki, agar aapne `A` task abhi execute kiya hai, toh aapko `n` time units ka wait karna padega `A` ko dobara execute karne se pehle. Is cool-down period mein aap doosre tasks execute kar sakte hain ya CPU idle rakh sakte hain. Aapko **minimum time find karna hai saare tasks ko complete karne mein**.

For example, `tasks = ["A","A","A","B","B","B"], n = 2`
`A -> B -> idle -> A -> B -> idle -> A -> B` (total time 8)

Is problem ko solve karne ke liye hum **Greedy approach** aur **Max-Heap (PriorityQueue)** ka use karte hain.

### Approach (Pahunch)

1.  **Task Frequencies:** Sabse pehle, saare tasks ki **frequencies** calculate karo (kitni baar har task aata hai). Iske liye `Map` ya `int[]` array use kar sakte hain.

2.  **Max-Heap ka Use:**

      * Ek `PriorityQueue<Integer>` banayein (Max-Heap), jismein task frequencies store karenge.
      * Saari positive frequencies ko heap mein add kar do.

3.  **Scheduling Simulation:**

      * Total time ko `0` se initialize karo.
      * Jab tak heap empty nahi hota:
          * Ek temporary `List<Integer>` banayein, jismein current cycle mein execute hone wale tasks store karenge.
          * `n+1` tasks tak ka cycle iterate karein (current task + `n` cool-down slots).
          * Har iteration mein:
              * Agar heap empty nahi hai, toh top frequency (sabse zyada remaining instances wala task) nikalo (`pq.poll()`).
              * Frequency ko `1` se kam karo.
              * Agar frequency abhi bhi positive hai, toh use `tempList` mein add karo (yeh task future mein fir se execute hoga).
              * Time ko `1` se badhao (`totalTime++`).
              * Agar heap empty ho gaya aur `tempList` mein bhi koi tasks nahi hain, toh loop break kar do (saare tasks complete).
          * `tempList` ke saare tasks ko wapas heap mein add kar do.
          * Agar `tempList` mein `n+1` se kam tasks hain aur heap abhi bhi empty nahi hua, toh iska matlab hai ki cool-down ke karan kuch idle slots hain. To `totalTime` ko `n + 1 - current_cycle_tasks_count` se badha do. Lekin ek smart tareeka hai: `totalTime` ko bas `tempList.size()` se badhao, aur agar `pq` empty nahi hai, toh `totalTime` ko `n+1` se badhao instead of `tempList.size()`, isse idle slots apne aap handle ho jayenge. Ya fir, `totalTime` ko har iteration mein `1` se badhao, aur agar current cycle mein `n+1` slots fill nahi hue (matlab `tempList.size() < (n+1)`) aur `pq` abhi empty nahi hai, toh extra idle time add karo.

4.  **Optimized Scheduling:**

      * **Time Calculation:** Instead of complex `n+1` logic, hum simpler tareeke se calculate kar sakte hain.
      * Ek `Max-Heap` (PriorityQueue) mein task counts daalo.
      * Ek `Queue<int[]>` banayein jismein `[remaining_count, available_time]` store hoga. `available_time` woh time hai jab yeh task fir se execute ho sakta hai.
      * `time = 0` se shuru karein.
      * Jab tak `pq` empty nahi ya `waitQueue` empty nahi:
          * `time++`.
          * Agar `pq` empty nahi, toh top task ko `count = pq.poll() - 1` karke extract karo.
          * Agar `count > 0`, toh `[count, time + n]` ko `waitQueue` mein add karo.
          * Agar `waitQueue` ka front task ab `time` ke barabar ya kam time par available hai, toh use `pq` mein wapas move karo.

### Solution (Hal) (Greedy with Heap)

Hum greedy approach apnaate hain: hamesha us task ko execute karte hain jiski frequency sabse zyada hai, provided uska cool-down period complete ho gaya ho. Hum ek Max-Heap ka use karke sabse zyada frequent tasks ko track karte hain. Ek loop mein, hum `n+1` time slots tak tasks ko schedule karne ki koshish karte hain. Jo tasks schedule ho jaate hain aur unki frequency abhi bhi positive hai, unhe hum ek temporary list mein store karte hain aur phir n+1 slots ke baad wapas heap mein daal dete hain. Total time calculate karte waqt, agar slots empty reh jaate hain toh unhe idle time count kiya jaata hai.

### Code (Code)

```java
import java.util.*;

class Solution {
    public int leastInterval(char[] tasks, int n) {
        // Step 1: Task frequencies calculate karein
        Map<Character, Integer> counts = new HashMap<>();
        for (char task : tasks) {
            counts.put(task, counts.getOrDefault(task, 0) + 1);
        }

        // Step 2: Max-Heap mein frequencies add karein
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        for (int count : counts.values()) {
            pq.offer(count);
        }

        int time = 0; // Total time required

        // Step 3: Scheduling Simulation
        while (!pq.isEmpty()) {
            List<Integer> temp = new ArrayList<>(); // To hold tasks processed in current cycle

            // Try to schedule n+1 tasks (current task + n cool-down slots)
            for (int i = 0; i <= n; i++) {
                if (!pq.isEmpty()) {
                    int currentTaskCount = pq.poll();
                    currentTaskCount--; // Decrement frequency
                    if (currentTaskCount > 0) {
                        temp.add(currentTaskCount); // Agar bacha hai toh temp list mein daalo
                    }
                }
                time++; // Har slot ke liye time badhao

                // Agar sare tasks process ho gaye current cycle mein hi (pq aur temp dono empty)
                if (pq.isEmpty() && temp.isEmpty()) {
                    break;
                }
            }

            // Temp list ke tasks ko wapas heap mein daalo next cycle ke liye
            for (int remainingCount : temp) {
                pq.offer(remainingCount);
            }
        }

        return time;
    }
}
```

-----

## 355\. Design Twitter (Premium)

Design Twitter ek design problem hai jismein humein Twitter ki core functionalities (tweet post karna, news feed dekhna, follow/unfollow karna) implement karni hoti hain. Yeh LeetCode Premium problem hai, toh main sirf concept aur approach bata paunga, code shayad directly test na ho paye.

-----

### Description/Overview

Imagine karo ki aapko **Twitter ka ek simplified version** banana hai. Aapko teen main functionalities support karni hain:

1.  **`postTweet(userId, tweetId)`:** Ek user tweet post kar sakta hai.
2.  **`getNewsFeed(userId)`:** Ek user apni news feed dekh sakta hai, jismein uske apne tweets aur jin users ko woh follow karta hai, unke tweets latest 10 tweets ki order mein dikhen.
3.  **`follow(followerId, followeeId)`:** Ek user doosre user ko follow kar sakta hai.
4.  **`unfollow(followerId, followeeId)`:** Ek user doosre user ko unfollow kar sakta hai.

Yeh ek **design problem** hai, jismein sahi data structures aur algorithms ka selection important hai.

### Approach (Pahunch)

Humein multiple users, unke tweets, aur unke follow relationships ko store karna hai. Time-based order for news feed bhi zaroori hai.

1.  **User Data Structure:** Har user ke liye, humein:

      * Uske **followers** ki list/set.
      * Unke **tweets** ki list.
      * Iske liye `Map<Integer, Set<Integer>> followers` aur `Map<Integer, List<Tweet>> userTweets` use kar sakte hain.

2.  **Tweet Data Structure:** Har tweet ko track karne ke liye, humein:

      * `tweetId`
      * `timestamp` (jab tweet post hua)
        `Tweet` class bana sakte hain jismein yeh dono fields honge. Timestamp bahut important hai latest tweets ko retrieve karne ke liye. Hum `System.nanoTime()` ya `System.currentTimeMillis()` ka use kar sakte hain, ya simply ek global `time` counter ko increment kar sakte hain har `postTweet` call par.

3.  **Data Structures Overview:**

      * `Map<Integer, Set<Integer>> userFollows`: Stores `userId -> Set<followerIds>`. Better, `Map<Integer, Set<Integer>> followees`: `userId -> Set<users_they_follow>`. `self` ko bhi follow list mein add karein for simplicity in `getNewsFeed`.
      * `Map<Integer, List<Tweet>> userTweets`: Stores `userId -> List<Tweet_objects>`. Tweets ko `List` mein `add` karte rahenge, jo automatically `chronological order` mein honge.

4.  **`postTweet(userId, tweetId)`:**

      * Ek `Tweet` object banao jismein `tweetId` aur current `timestamp` ho.
      * Is `Tweet` object ko `userTweets.get(userId)` ki `List` mein add kar do. Global `timestamp` counter ko increment kar do.

5.  **`getNewsFeed(userId)`:**

      * Ek `PriorityQueue<Tweet>` (Min-Heap) use karenge. Is `Min-Heap` ko tweets ke `timestamp` ke basis par order karenge.
      * Target `userId` jin users ko follow karta hai (`followees.get(userId)`), un sabhi users ke tweets ko iterate karo.
      * Har followee ke tweets list se, latest tweets (usually last 10-15) ko `Min-Heap` mein add karo.
      * Agar heap ka size `10` se zyada ho jata hai, toh heap ka smallest (oldest) tweet `poll()` kar do. Isse heap mein hamesha `10` latest tweets rahenge.
      * Jab saare relevant tweets process ho jayein, toh `Min-Heap` mein `10` (ya kam) latest tweets honge. Unhe list mein `poll()` karke daalo (yeh reverse chronological order mein honge, toh list ko reverse karna padega) ya `LinkedList` mein `addFirst()` ka use karo.

6.  **`follow(followerId, followeeId)`:**

      * `followees.get(followerId)` ke `Set` mein `followeeId` add kar do.

7.  **`unfollow(followerId, followeeId)`:**

      * `followees.get(followerId)` ke `Set` se `followeeId` remove kar do. Edge case: `followerId` should not unfollow `followerId` itself.

### Solution (Hal)

Hum `HashMap`s ka use karke users ke follow relationships aur unke tweets store karte hain. Tweets ko `Tweet` objects ke roop mein store karte hain jismein `tweetId` aur `timestamp` hota hai, jisse hum unhe time ke according sort kar sakein. `getNewsFeed` ke liye, hum target user aur uske followees ke tweets ko ek `Min-Heap` mein daalte hain, jiska size `10` tak maintain karte hain. Heap mein hamesha `10` latest tweets rahenge, aur unhe nikal kar final news feed banate hain.

### Code (Code)

```java
import java.util.*;

class Twitter {

    // Global timestamp counter for tweets
    private static int timestamp = 0;

    // A Tweet class to store tweetId and its creation timestamp
    class Tweet {
        public int tweetId;
        public int time;

        public Tweet(int tweetId, int time) {
            this.tweetId = tweetId;
            this.time = time;
        }
    }

    // Map: userId -> List of Tweet objects (chronological order)
    private Map<Integer, List<Tweet>> userTweets;
    // Map: userId -> Set of userIds they follow (for quick lookup)
    private Map<Integer, Set<Integer>> followees;

    public Twitter() {
        userTweets = new HashMap<>();
        followees = new HashMap<>();
        timestamp = 0; // Reset for each new Twitter instance, though for LeetCode it's usually static
    }

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        userTweets.computeIfAbsent(userId, k -> new ArrayList<>()).add(new Tweet(tweetId, timestamp++));
    }

    /** Retrieve the 10 most recent tweet IDs in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        // Min-Heap to store tweets, ordered by time (oldest at top)
        // We want the 10 most recent, so we keep 10 elements.
        // If a new one comes that's more recent than the oldest, we remove the oldest.
        PriorityQueue<Tweet> pq = new PriorityQueue<>((t1, t2) -> t1.time - t2.time);

        // Add user's own tweets
        if (userTweets.containsKey(userId)) {
            for (Tweet tweet : userTweets.get(userId)) {
                pq.offer(tweet);
                if (pq.size() > 10) {
                    pq.poll(); // Keep only the 10 most recent
                }
            }
        }

        // Add tweets from followees
        if (followees.containsKey(userId)) {
            for (int followeeId : followees.get(userId)) {
                // Skip if followeeId is the user themselves (already handled)
                if (followeeId == userId) continue;

                if (userTweets.containsKey(followeeId)) {
                    for (Tweet tweet : userTweets.get(followeeId)) {
                        pq.offer(tweet);
                        if (pq.size() > 10) {
                            pq.poll();
                        }
                    }
                }
            }
        }

        // Convert heap to list and reverse for most recent to least recent order
        List<Integer> feed = new LinkedList<>(); // Use LinkedList to add to front efficiently
        while (!pq.isEmpty()) {
            ((LinkedList<Integer>) feed).addFirst(pq.poll().tweetId);
        }
        return feed;
    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        // A user cannot follow him/herself is typically implied.
        // Leetcode says: "If the operation is invalid, it should be a no-op."
        // So, if followerId == followeeId, no-op.
        if (followerId == followeeId) return;

        followees.computeIfAbsent(followerId, k -> new HashSet<>()).add(followeeId);
    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        // A user cannot unfollow him/herself.
        if (followerId == followeeId) return;
        
        // Only unfollow if followerId actually follows followeeId
        if (followees.containsKey(followerId)) {
            followees.get(followerId).remove(followeeId);
        }
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */
```

-----

## 295\. Find Median from Data Stream

Find Median from Data Stream ek data structure design problem hai jismein humein stream se aate hue numbers mein se hamesha median nikalna hota hai.

-----

### Description/Overview

Imagine karo ki aapko ek **infinite stream of integers** mil raha hai. Aapko ek data structure design karna hai jo do operations support kare:

1.  **`addNum(int num)`:** Stream mein ek naya integer add kare.
2.  **`findMedian()`:** Current stream mein maujood saare numbers ka **median** return kare.

Median kya hota hai?

  * Agar numbers odd hain, toh median bich ka number hota hai (jab numbers sorted hon). Ex: `[2, 3, 4]`, median `3`.
  * Agar numbers even hain, toh median bich ke do numbers ka average hota hai. Ex: `[2, 3, 4, 5]`, median `(3 + 4) / 2 = 3.5`.

Is problem ko solve karne ke liye hum **do Heaps (PriorityQueues)** ka use karte hain: ek **Max-Heap** aur ek **Min-Heap**.

### Approach (Pahunch)

1.  **Do Heaps ka Concept:**

      * Ek **Max-Heap (`maxHeap`)** banayenge jo stream ke **chote aadhay (lower half)** numbers ko store karega. Iska root hamesha lower half ka largest number hoga.
      * Ek **Min-Heap (`minHeap`)** banayenge jo stream ke **bade aadhay (upper half)** numbers ko store karega. Iska root hamesha upper half का smallest number होगा.
      * Goal yeh hai ki `maxHeap` mein `minHeap` ke barabar ya ek zyada elements hon. Is balance ko maintain karne se median nikalna aasan ho jaata hai.

2.  **`addNum(int num)` Method:**

      * Naye `num` ko `maxHeap` mein add karo (by default, yeh assume kar rahe hain ki `num` chota half mein jayega).
      * Ab `maxHeap` ka largest element (root) `minHeap` ke smallest element (root) se bada ho sakta hai. Is imbalance ko theek karne ke liye: `maxHeap` se root (`maxHeap.poll()`) nikalo aur use `minHeap` mein add karo.
      * **Balance Heaps:** Ab humein size balance karna hai.
          * Agar `minHeap` ka size `maxHeap` ke size se zyada ho jata hai, toh `minHeap` se root (`minHeap.poll()`) nikalo aur use `maxHeap` mein add karo.
          * (Alternative logic: Always add to maxHeap, then move maxHeap.poll() to minHeap. If maxHeap.size() \< minHeap.size(), move minHeap.poll() to maxHeap. This ensures maxHeap is always equal or 1 larger).

3.  **`findMedian()` Method:**

      * **Odd number of elements:** Agar `maxHeap` ka size `minHeap` ke size se bada hai (yani total elements odd hain), toh `maxHeap` ka root (`maxHeap.peek()`) hi median hoga.
      * **Even number of elements:** Agar `maxHeap` aur `minHeap` ka size barabar hai (yani total elements even hain), toh median `maxHeap.peek()` aur `minHeap.peek()` ka average hoga.

### Solution (Hal)

Hum do Priority Queues (heaps) ka use karte hain: ek Max-Heap (chote numbers ke liye) aur ek Min-Heap (bade numbers ke liye). Hum yeh maintain karte hain ki Max-Heap mein Min-Heap ke barabar ya ek zyada elements hon. Jab naya number aata hai, hum use Max-Heap mein daalte hain, phir ensure karte hain ki Max-Heap ka top element Min-Heap ke top element se chota ya barabar ho by moving elements. Phir hum heaps ke sizes ko balance karte hain. `findMedian` method simply heaps ke top elements ko dekh kar median calculate karta hai.

### Code (Code)

```java
import java.util.Collections;
import java.util.PriorityQueue;

class MedianFinder {

    // Max-Heap to store the smaller half of the numbers
    // Top element is the largest in the smaller half
    private PriorityQueue<Integer> maxHeap;

    // Min-Heap to store the larger half of the numbers
    // Top element is the smallest in the larger half
    private PriorityQueue<Integer> minHeap;

    public MedianFinder() {
        maxHeap = new PriorityQueue<>(Collections.reverseOrder()); // Max-Heap
        minHeap = new PriorityQueue<>(); // Min-Heap
    }

    public void addNum(int num) {
        // Step 1: Add num to maxHeap first
        maxHeap.offer(num);

        // Step 2: Ensure maxHeap's largest is not greater than minHeap's smallest
        // Move maxHeap's top to minHeap
        // This ensures all elements in maxHeap <= all elements in minHeap
        minHeap.offer(maxHeap.poll());

        // Step 3: Balance the sizes of the two heaps
        // maxHeap should always have equal or one more element than minHeap
        if (maxHeap.size() < minHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }

    public double findMedian() {
        if (maxHeap.size() == minHeap.size()) {
            // Even number of elements: median is average of tops of both heaps
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        } else {
            // Odd number of elements: median is top of maxHeap (which holds the extra element)
            return maxHeap.peek();
        }
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
```
