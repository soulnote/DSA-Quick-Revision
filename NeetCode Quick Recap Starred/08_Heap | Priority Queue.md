# ⭐ Task Scheduler

Aapko CPU tasks ka ek array diya gaya hai, jinka label `A` se `Z` tak hai. Aur ek non-negative integer `n` diya gaya hai jo do same tasks ke beech ka **cooldown period** represent karta hai.

  * Har CPU interval mein **ek task** execute ho sakta hai ya CPU **idle** reh sakta hai.
  * Tasks ko **kisi bhi order** mein kiya ja sakta hai.
  * Do **same task** ke do executions ke beech mein kam se kam `n` intervals ka gap hona chahiye.
  * Sabhi tasks ko complete karne ke liye required **minimum number of CPU intervals** return karein.

-----

### 📌 Example

#### Input:

```
tasks = ["A","A","A","B","B","B"], n = 2
```

#### Output:

```
8
```

**Explanation**:
Ek valid schedule hai:
`A -> B -> idle -> A -> B -> idle -> A -> B`

Task A complete karne ke baad, aapko A ko dobara execute karne se pehle 2 intervals wait karna hoga. Yahi rule task B par bhi apply hota hai. Idle intervals cooldown gaps ko fill karte hain.

-----

### Intuition (Samajh):

Is problem ka core idea hai ki humein tasks ko schedule karna hai taki same tasks ke beech `n` intervals ka cooldown ho. Humara goal hai total time ko minimum karna.

Isse handle karne ka ek greedy approach hai: **Hamesha us task ko execute karo jiski frequency sabse zyada hai aur jo cooldown period mein nahi hai.** Aisa karne se hum sabse frequent tasks ko jaldi se jaldi complete kar paate hain, jisse overall schedule compact banta hai.

Agar sabse frequent task cooldown mein hai, toh hum CPU ko idle rakhte hain ya koi doosra available task chalate hain.

-----

### Approach (Tareeka): Greedy with Max Heap and Queue

1.  **Edge Case**: Agar `n` (cooldown) `0` hai, toh koi cooldown nahi hai, iska matlab total time bas tasks ki total length hogi. Return `tasks.length`.
2.  **Frequency Count**: Ek `frequency` array (`int[26]`) banao tasks `A` se `Z` ki counts store karne ke liye.
3.  **Max Heap**: Ek `PriorityQueue<Integer>` (max-heap) banao. Isme tasks ki remaining counts store karenge. `Collections.reverseOrder()` use karenge takay max-heap ki tarah act kare (highest count top par ho). Saari non-zero frequencies ko max-heap mein add kar do.
4.  **Time Counter**: `int time = 0` initialize karo jo total CPU intervals count karega.
5.  **Wait Queue**: Ek `Queue<Pair<Integer, Integer>> waitQueue` banao. Is queue mein woh tasks rahenge jo abhi cooldown period mein hain. Har `Pair` mein task ki remaining count aur uska **next available time** (jab woh phir se execute ho sakta hai) store hoga. (Java mein `Pair` class nahi hoti by default, hum ek simple `class` bana sakte hain ya `AbstractMap.SimpleEntry` use kar sakte hain ya directly `int[]` use kar sakte hain for `[count, time]`).
6.  **Simulation Loop**: `while (!maxHeap.isEmpty() || !waitQueue.isEmpty())` loop chalao. Jab tak koi bhi task process hone ke liye pending hai ya cooldown mein hai, loop chalta rahega.
      * `time++`: Har iteration mein, ek time unit badhao.
      * **Execute Task from Max Heap**:
          * `if (!maxHeap.isEmpty())`: Agar max-heap empty nahi hai, matlab koi task abhi execute ho sakta hai.
          * `int currentTaskCount = maxHeap.poll() - 1`: Sabse frequent task ko max-heap se nikalo aur uski count 1 se kam karo (kyunki humne use execute kar diya).
          * `if (currentTaskCount > 0)`: Agar is task ki abhi bhi remaining counts hain, toh use `waitQueue` mein add karo.
          * `waitQueue.add(new Pair<>(currentTaskCount, time + n))`: `Pair` mein task ki updated count aur uska next available time (`current time + cooldown period`) store karo.
      * **Move Tasks from Wait Queue to Max Heap**:
          * `if (!waitQueue.isEmpty() && waitQueue.peek().getValue() == time)`: Check karo ki `waitQueue` mein sabse pehle wale task ka cooldown period khatam ho gaya hai kya (yani, uska `nextAvailableTime` current `time` ke barabar hai).
          * `maxHeap.add(waitQueue.poll().getKey())`: Agar haan, toh us task ko `waitQueue` se nikalo aur uski remaining count ko `maxHeap` mein wapas add karo. Ab woh task phir se schedule hone ke liye ready hai.
7.  **Return `time`**: Loop khatam hone par, `time` mein sabhi tasks ko complete karne ke liye required minimum CPU intervals honge.

-----

### Java Code with Comments:

```java
import java.util.*;
// import javafx.util.Pair; // Ye JavaFX ka Pair class hai, jo आमतौर पर competitive programming में उपलब्ध नहीं होता.
// Instead, we can create our own simple Pair class or use Map.Entry.
// For simplicity in competitive programming, sometimes int[] or a custom class is used.

// Custom Pair class for Java competitive programming, if javafx.util.Pair is not available
class Pair<K, V> {
    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}


class Solution {
    public int leastInterval(char[] tasks, int n) {
        // Edge Case: Agar cooldown period 0 hai, toh koi wait nahi karna.
        // Total time bas tasks ki total number ke barabar hogi.
        if (n == 0) return tasks.length;

        // Step 1: Har task ki frequency calculate karo.
        // 'A' se 'Z' tak ke 26 possible tasks ke liye ek array.
        int[] frequency = new int[26];
        for (char task : tasks) {
            frequency[task - 'A']++; // 'A' ko index 0, 'B' ko 1, etc.
        }

        // Step 2: Max-Heap (PriorityQueue in reverse order) banao.
        // Isme tasks ki remaining frequencies honge, jise hum hamesha highest frequency wale task ko pick karne ke liye use karenge.
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for (int count : frequency) {
            if (count > 0) { // Sirf un tasks ko add karo jo actually exist karte hain.
                maxHeap.add(count);
            }
        }

        // Step 3: 'time' variable initialize karo jo total CPU intervals count karega.
        int time = 0;

        // Step 4: waitQueue banao उन tasks के लिए जो cooldown period में हैं.
        // Queue mein Pair<remainingCount, nextAvailableTime> store hoga.
        Queue<Pair<Integer, Integer>> waitQueue = new LinkedList<>();

        // Step 5: Main simulation loop.
        // Jab tak maxHeap mein koi task available hai ya waitQueue mein koi task cooldown se bahar aane wala hai.
        while (!maxHeap.isEmpty() || !waitQueue.isEmpty()) {
            time++; // Har iteration mein ek time unit badhao.

            // Step 5.1: MaxHeap se sabse frequent task ko execute karo.
            if (!maxHeap.isEmpty()) {
                int currentTaskCount = maxHeap.poll() - 1; // Sabse frequent task liya aur uski count 1 kam ki.
                if (currentTaskCount > 0) {
                    // Agar task abhi bhi bacha hai (count > 0), toh use waitQueue mein daal do.
                    // Yeh ab 'time + n' intervals ke baad available hoga.
                    waitQueue.add(new Pair<>(currentTaskCount, time + n));
                }
            }

            // Step 5.2: WaitQueue check karo ki koi task cooldown se bahar aa gaya hai kya.
            // Agar waitQueue empty nahi hai AND queue mein sabse pehle task ka nextAvailableTime,
            // current 'time' ke barabar hai (yaani, cooldown khatam).
            if (!waitQueue.isEmpty() && waitQueue.peek().getValue() <= time) { // Corrected: should be <= time
                // Agar cooldown khatam ho gaya hai, toh task ko waitQueue se nikalo
                // aur uski remaining count ko wapas maxHeap mein daal do takay woh schedule ho sake.
                maxHeap.add(waitQueue.poll().getKey());
            }
        }

        // Sabhi tasks complete hone ke baad total time return karo.
        return time;
    }
}
```

-----

# ⭐ Design Twitter

### Problem Summary:

  * Users can **post tweets**.
  * Users can **follow/unfollow** other users.
  * Users can **get their news feed**: Ismein 10 most recent tweets honge jo unhone khud post kiye hain aur un logo ne post kiye hain jinhe woh follow karte hain.
  * Tweets recency ke order mein hote hain (latest first).

-----

### Intuition (Samajh):

Ek Twitter jaisi functionality design karne ke liye, humein teen main cheezein store karni hongi:

1.  **Users ke tweets**: Har user ke apne tweets ko store karna. Har tweet ke saath uska timestamp bhi hona chahiye taaki hum unhe recency ke hisab se order kar sakein.
2.  **Follow relationships**: Kaun kisko follow karta hai.
3.  **News feed generation**: Yeh sabse complex part hai. Jab koi user apna feed dekhta hai, toh humein us user ke apne tweets aur unke followees ke tweets ko combine karna hoga aur phir unmein se 10 sabse recent tweets nikalne honge.

-----

### Approach (Tareeka): Using HashMaps, Lists, and PriorityQueue

1.  **Global Timestamp**: Ek static `int timeStamp` variable rakho. Jab bhi koi naya tweet post hota hai, is timestamp ko increment karo aur use tweet ke saath store karo. Yeh globally unique aur increasing order provide karega.
2.  **`Tweet` Class**: Ek inner class `Tweet` banao jismein `tweetId` aur `time` (timestamp) store ho.
3.  **Data Structures**:
      * `Map<Integer, Set<Integer>> followers`: Yeh map `followerId` ko `Set<followeeId>` se map karega. Ek `Set` use karte hain kyunki ek user ek followee ko do baar follow nahi kar sakta, aur `Set` duplicate entries ko handle karta hai.
      * `Map<Integer, List<Tweet>> tweets`: Yeh map `userId` ko us user ke saare `Tweet` objects ki `List` se map karega. `List` mein tweets ko add karte rahenge.
4.  **`Twitter()` Constructor**: `followers` aur `tweets` HashMaps ko initialize karo.
5.  **`postTweet(int userId, int tweetId)`**:
      * `tweets.putIfAbsent(userId, new ArrayList<>())`: Agar `userId` ke liye abhi tak koi tweet list nahi hai, toh ek empty `ArrayList` banao.
      * `tweets.get(userId).add(new Tweet(tweetId, timeStamp++))`: `userId` ki list mein naya `Tweet` object add karo, current `tweetId` aur incremented `timeStamp` ke saath.
6.  **`getNewsFeed(int userId)`**:
      * `PriorityQueue<Tweet> feed`: Ek `PriorityQueue` (max-heap) banao jo `Tweet` objects ko `time` ke descending order mein sort karega (latest tweet top par). Comparator `(a, b) -> b.time - a.time` use karo.
      * **Add User's Own Tweets**: `if (tweets.containsKey(userId)) { feed.addAll(tweets.get(userId)); }` User ke saare tweets heap mein add karo.
      * **Add Followees' Tweets**:
          * `if (followers.containsKey(userId))`: Agar user kisi ko follow karta hai.
          * `for (int followeeId : followers.get(userId))`: Har `followeeId` ke liye:
              * `if (tweets.containsKey(followeeId)) { feed.addAll(tweets.get(followeeId)); }` Followee ke saare tweets heap mein add karo.
      * **Extract Top 10**:
          * `List<Integer> result = new ArrayList<>()`: Final result ke liye list.
          * `int count = 0`: Counter for 10 tweets.
          * `while (!feed.isEmpty() && count < 10)`: Jab tak heap empty nahi hoti aur humne 10 tweets nahi nikale hain:
              * `result.add(feed.poll().tweetId)`: Heap se sabse recent tweet nikalo aur uski `tweetId` result mein add karo.
              * `count++`.
      * Return `result`.
7.  **`follow(int followerId, int followeeId)`**:
      * `if (followerId == followeeId) return;`: User khud ko follow nahi kar sakta.
      * `followers.putIfAbsent(followerId, new HashSet<>())`: Agar `followerId` ke liye set nahi hai, toh ek empty `HashSet` banao.
      * `followers.get(followerId).add(followeeId)`: `followeeId` ko `followerId` ke set mein add karo.
8.  **`unfollow(int followerId, int followeeId)`**:
      * `if (followers.containsKey(followerId))`: Agar `followerId` exist karta hai:
          * `followers.get(followerId).remove(followeeId)`: `followeeId` ko set se remove karo.

-----

### Java Code with Comments:

```java
import java.util.*;

class Twitter {

    // Global static timestamp for ordering tweets.
    // Har naye tweet ko ek unique aur monotonically increasing timeStamp milta hai.
    private static int timeStamp = 0; 
    
    // followers: Map jo track karta hai ki kaun kisko follow karta hai.
    // Key: followerId (jo follow kar raha hai)
    // Value: Set of followeeIds (jinhe follow kiya ja raha hai)
    private Map<Integer, Set<Integer>> followers; 
    
    // tweets: Map jo har user ke tweets ko store karta hai.
    // Key: userId
    // Value: List of Tweet objects (us user ke saare tweets)
    private Map<Integer, List<Tweet>> tweets; 

    // Inner class Tweet: Har tweet ki id aur uske post hone ka time store karta hai.
    private class Tweet {
        int tweetId;
        int time; // Tweet ka timestamp

        public Tweet(int tweetId, int time) {
            this.tweetId = tweetId;
            this.time = time;
        }
    }

    // Constructor: Twitter object ko initialize karta hai.
    public Twitter() {
        followers = new HashMap<>();
        tweets = new HashMap<>();
    }

    /**
     * postTweet: Ek naya tweet compose karta hai user ke dwara.
     * @param userId: Tweet post karne wale user ki ID.
     * @param tweetId: Tweet ki ID.
     */
    public void postTweet(int userId, int tweetId) {
        // Agar userId ke liye tweets ki list abhi tak nahi bani hai, toh banao.
        tweets.putIfAbsent(userId, new ArrayList<>());
        // User ki tweets list mein naya Tweet object add karo.
        // timeStamp++ use karte hain taaki har naye tweet ko ek unique aur badhta hua time mile.
        tweets.get(userId).add(new Tweet(tweetId, timeStamp++));
    }

    /**
     * getNewsFeed: User ke news feed mein 10 sabse recent tweet IDs retrieve karta hai.
     * News feed mein user ke apne tweets aur un logon ke tweets shamil hote hain jinhe woh follow karta hai.
     * @param userId: News feed dekhne wale user ki ID.
     * @return List of 10 most recent tweet IDs.
     */
    public List<Integer> getNewsFeed(int userId) {
        // PriorityQueue (Max-Heap) ka use karte hain tweets ko recency ke hisab se order karne ke liye.
        // b.time - a.time ensures that Tweets with higher 'time' (more recent) are at the top.
        PriorityQueue<Tweet> feed = new PriorityQueue<>((a, b) -> b.time - a.time);

        // 1. User ke apne tweets add karo feed mein.
        if (tweets.containsKey(userId)) {
            feed.addAll(tweets.get(userId));
        }

        // 2. Un logon ke tweets add karo jinhe user follow karta hai.
        if (followers.containsKey(userId)) {
            for (int followeeId : followers.get(userId)) {
                // Agar followeeId user ki apni ID hai, toh use skip karo kyunki uske tweets already add ho chuke hain.
                if (followeeId == userId) continue; 

                if (tweets.containsKey(followeeId)) {
                    feed.addAll(tweets.get(followeeId));
                }
            }
        }

        // 3. Feed (PriorityQueue) se top 10 most recent tweets nikalo.
        List<Integer> result = new ArrayList<>();
        int count = 0;
        while (!feed.isEmpty() && count < 10) {
            result.add(feed.poll().tweetId); // Sabse recent tweet nikaal kar ID add karo.
            count++;
        }

        return result;
    }

    /**
     * follow: Ek user doosre user ko follow karta hai.
     * @param followerId: Follow karne wale user ki ID.
     * @param followeeId: Jisko follow kiya ja raha hai us user ki ID.
     */
    public void follow(int followerId, int followeeId) {
        // Ek user khud ko follow nahi kar sakta.
        if (followerId == followeeId) return; 

        // Agar followerId ke liye followers set abhi tak nahi bana hai, toh banao.
        followers.putIfAbsent(followerId, new HashSet<>());
        // followeeId ko followerId ke set mein add karo.
        followers.get(followerId).add(followeeId);
    }

    /**
     * unfollow: Ek user doosre user ko unfollow karta hai.
     * @param followerId: Unfollow karne wale user ki ID.
     * @param followeeId: Jisko unfollow kiya ja raha hai us user ki ID.
     */
    public void unfollow(int followerId, int followeeId) {
        // Sirf tab unfollow karo jab followerId ka entry exist karta ho aur wo followeeId ko follow karta ho.
        if (followers.containsKey(followerId)) {
            followers.get(followerId).remove(followeeId);
        }
    }
}
```

-----

# ⭐ Find Median from Data Stream

### Intuition (Samajh):

Numbers ke ek stream se median dhundhna ek common problem hai. Median woh number hota hai jo data set ko do barabar halves mein divide karta hai (agar sorted ho). Odd number of elements mein, median beech ka element hota hai; even number of elements mein, median do beech ke elements ka average hota hai.

Ek simple approach hai ki har baar saare numbers ko store karo, sort karo, aur phir median nikalo. Lekin yeh `addNum` operation ko `O(N log N)` bana dega, jo efficient nahi hai agar bohot saare numbers add ho rahe hain.

Is problem ko efficient tareeke se handle karne ke liye, hum **do heaps** ka use karte hain:

1.  **Max-heap (`small`)**: Yeh heap data stream ke **smaller half** ke numbers ko store karega. Max-heap hone ke karan, iska root (top element) us smaller half ka **sabse bada** number hoga.
2.  **Min-heap (`large`)**: Yeh heap data stream ke **larger half** ke numbers ko store karega. Min-heap hone ke karan, iska root (top element) us larger half ka **sabse chota** number hoga.

-----

### Approach (Tareeka): Two Heaps

Hum in do heaps ko is tarah se maintain karte hain ki:

  * `small` heap mein numbers `large` heap ke numbers se **hamesha chhote ya barabar** honge.
  * Heaps ka size balanced rahega:
      * Agar total number of elements `even` hain, toh `small` aur `large` dono heaps ka size barabar hoga.
      * Agar total number of elements `odd` hain, toh `small` heap mein `large` heap se ek extra element hoga. (Hum `small` ko extra rakhte hain taaki median hamesha `small` heap ke root par ho, jo easy access deta hai).

**`addNum(int num)` ka process:**

1.  **Parity Check**: Hum `even` naam ka ek boolean flag rakhte hain jo batata hai ki abhi tak total elements `even` hain ya `odd`.
2.  **Add to `large` then `small` (if `even` before adding `num`)**:
      * Agar `even` `true` hai (matlab abhi tak total elements `even` hain), toh naya `num` `large` heap mein add karo.
      * Phir `large` heap se sabse chota element (`large.poll()`) nikalo aur use `small` heap mein add kar do.
      * Isse `small` heap ka size `large` heap se ek zyada ho jata hai, aur `even` `false` ho jata hai (total elements `odd`).
3.  **Add to `small` then `large` (if `odd` before adding `num`)**:
      * Agar `even` `false` hai (matlab abhi tak total elements `odd` hain), toh naya `num` `small` heap mein add karo.
      * Phir `small` heap se sabse bada element (`small.poll()`) nikalo aur use `large` heap mein add kar do.
      * Isse `small` aur `large` heaps ka size barabar ho jata hai, aur `even` `true` ho jata hai (total elements `even`).

**`findMedian()` ka process:**

  * Agar `even` `true` hai (total elements `even`): Median `small.peek()` (max of smaller half) aur `large.peek()` (min of larger half) ka average hoga.
  * Agar `even` `false` hai (total elements `odd`): Median `small.peek()` (jo beech ka element hai) hoga.

-----

### Why does this work? (Gehrai se samajh)

  * `small` (max-heap) mein hamesha data stream ka woh part hota hai jo `median` se chhota ya barabar hota hai.
  * `large` (min-heap) mein hamesha data stream ka woh part hota hai jo `median` se bada ya barabar hota hai.
  * Jab hum `addNum(num)` karte hain:
      * Agar `num` ko सीधे `small` में डालते और फिर `small.peek()` को `large` में डालते, तो `small` का सबसे बड़ा तत्व (`small.peek()`) हमेशा सही median या median pair का निचला हिस्सा होता।
      * अगर `num` को सीधे `large` में डालते और फिर `large.peek()` को `small` में डालते, तो `large` का सबसे छोटा तत्व (`large.peek()`) हमेशा सही median pair का ऊपरी हिस्सा होता।
      * Humari implementation mein, hum `even` flag ka use karke ensure karte hain ki `small` heap mein `large` heap se ek zyada element ho jab total elements `odd` hon. Isse `small.peek()` hamesha median hota hai. Agar total elements `even` hain, toh `small.peek()` aur `large.peek()` beech ke do elements hote hain.
      * Yeh balancing trick (`large.offer(num); small.offer(large.poll());` ya vice-versa) ensure karta hai ki `small` ke saare elements `large` ke elements se chhote ya barabar rahen, aur size balance bhi maintain ho.

-----

### Java Code with Comments:

```java
import java.util.Collections;
import java.util.PriorityQueue;

class MedianFinder {
    // small: Max-heap. Yeh numbers ke smaller half ko store karta hai.
    // Iska root (peek()) smaller half ka sabse bada element hoga.
    private PriorityQueue<Integer> small = new PriorityQueue<>(Collections.reverseOrder()); 
    
    // large: Min-heap. Yeh numbers ke larger half ko store karta hai.
    // Iska root (peek()) larger half ka sabse chota element hoga.
    private PriorityQueue<Integer> large = new PriorityQueue<>(); 
    
    // even: Ek boolean flag jo track karta hai ki abhi tak total elements ka count even hai ya odd.
    // True ka matlab total elements even hain, False ka matlab total elements odd hain.
    private boolean even = true; 

    // Constructor: MedianFinder object ko initialize karta hai. (Default constructor is fine)
    public MedianFinder() {
        // PriorityQueues are initialized above
    }

    /**
     * addNum: Ek naya number data structure mein add karta hai.
     * Yeh method heaps ko balance bhi karta hai taaki median property maintain rahe.
     *
     * @param num: Woh number jise data stream mein add karna hai.
     */
    public void addNum(int num) {
        if (even) { // Agar abhi tak total elements even hain (matlab agle add ke baad odd ho jayenge)
            // Step 1: Naye number ko pehle 'large' (min-heap) mein daalo.
            // Isse 'large' ke roots property maintain rahegi.
            large.offer(num); 
            // Step 2: 'large' (min-heap) se sabse chota element nikalo (jo naya add kiya gaya 'num' ho sakta hai ya koi pehle se hi).
            // Aur use 'small' (max-heap) mein daal do.
            // Isse ensure hota hai ki 'small' mein hamesha smaller half ke elements rahenge.
            // Aur 'small' ka size 'large' se 1 zyada ho jayega.
            small.offer(large.poll()); 
        } else { // Agar abhi tak total elements odd hain (matlab agle add ke baad even ho jayenge)
            // Step 1: Naye number ko pehle 'small' (max-heap) mein daalo.
            // Isse 'small' ke roots property maintain rahegi.
            small.offer(num); 
            // Step 2: 'small' (max-heap) se sabse bada element nikalo.
            // Aur use 'large' (min-heap) mein daal do.
            // Isse ensure hota hai ki 'large' mein hamesha larger half ke elements rahenge.
            // Aur 'small' aur 'large' ka size barabar ho jayega.
            large.offer(small.poll()); 
        }
        even = !even; // Har add ke baad parity (even/odd) ko flip karo.
    }

    /**
     * findMedian: Current data stream ka median return karta hai.
     *
     * @return Current median value (double).
     */
    public double findMedian() {
        if (even) { // Agar total elements even hain.
            // Median do beech ke elements ka average hoga.
            // small.peek() -> Smaller half ka max element.
            // large.peek() -> Larger half ka min element.
            return (small.peek() + large.peek()) / 2.0; 
        } else { // Agar total elements odd hain.
            // Median 'small' (max-heap) ka root hoga.
            // Kyunki humne 'small' ko hamesha 1 extra element rakha hai jab total elements odd hon.
            return small.peek(); 
        }
    }
}
```
