
## 57\. Insert Interval

**Insert Interval** problem mein aapko ek list of non-overlapping intervals di gayi hai jo sorted hain start time ke hisaab se. Aapko ek naya interval insert karna hai aur result mein saare intervals ko merge karna hai jahan overlapping ho.

-----

### Description/Overview

Aapko ek array `intervals` diya gaya hai, jismein `intervals[i] = [start_i, end_i]` non-overlapping intervals hain, aur woh `start_i` ke hisaab se sorted hain. Aapko ek naya interval `newInterval = [start, end]` insert karna hai `intervals` mein, aur saare overlapping intervals ko merge karke final list return karni hai.

For example:

  * `intervals = [[1,3],[6,9]]`, `newInterval = [2,5]`
      * Output: `[[1,5],[6,9]]`
      * Explanation: `[2,5]` `[1,3]` se overlap karta hai, toh `[1,5]` banega.
  * `intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]]`, `newInterval = [4,8]`
      * Output: `[[1,2],[3,10],[12,16]]`
      * Explanation: `[4,8]` `[3,5]`, `[6,7]`, `[8,10]` se overlap karta hai. Merge hone par `[3,10]` banega.

### Approach (How to do it)

Is problem ko solve karne ke liye hum teen phases mein approach karte hain:

1.  **Non-overlapping (before):** Saare intervals jo `newInterval` se pehle khatam hote hain aur overlap nahi karte, unhein seedhe result list mein add kar do.
2.  **Overlapping/Merge:** Jab `newInterval` overlap karna shuru kare ya `newInterval` se overlap ho, tab tak merge karte raho jab tak overlap khatam na ho jaye. `newInterval` ko update karte raho `[min(newInterval.start, currentInterval.start), max(newInterval.end, currentInterval.end)]` se.
3.  **Non-overlapping (after):** Baaki bache hue saare intervals jo `newInterval` ke merge hone ke baad bhi overlap nahi karte, unhein seedhe result list mein add kar do.

**Algorithm:**

1.  Ek empty `List<int[]>` `result` banao.

2.  `intervals` array par iterate karo:

      * **Case 1: Current interval `[currStart, currEnd]` `newInterval` se pehle khatam hota hai aur overlap nahi karta.**
          * `currEnd < newInterval.start`
          * Is interval ko `result` mein add kar do.
      * **Case 2: Current interval `newInterval` se overlap karta hai.**
          * `currStart <= newInterval.end` AND `currEnd >= newInterval.start`
          * `newInterval` ko update karo: `newInterval.start = Math.min(newInterval.start, currStart)`
          * `newInterval.end = Math.max(newInterval.end, currEnd)`
      * **Case 3: Current interval `newInterval` ke baad shuru hota hai aur overlap nahi karta.**
          * `currStart > newInterval.end`
          * Yahan pehle `newInterval` (jo ab tak merge ho chuka hai) ko `result` mein add kar do. Iske baad, baaki saare intervals (current aur remaining) bhi overlap nahi karenge, toh unhein bhi seedhe add kar do. Loop se break kar do.

3.  Loop ke baad (agar Case 3 mein break nahi hua), `newInterval` ko `result` mein add kar do.

4.  `result` list ko `int[][]` mein convert karke return karo.

### Solution (The Way to Solve)

Hum ek `ArrayList` banate hain results store karne ke liye. Phir, `intervals` array ko iterate karte hain. Jab tak current interval `newInterval` ke start time se pehle khatam hota hai, usko `result` mein add karte jaate hain. Jab current interval `newInterval` se overlap karna shuru kare (ya `newInterval` usse overlap kare), tab tak `newInterval` ke start aur end ko `Math.min` aur `Math.max` se update karke merge karte hain. Jab overlap khatam ho jaye, toh updated `newInterval` ko `result` mein add karte hain, aur baaki ke remaining intervals ko bhi seedhe `result` mein add kar dete hain.

### Code

```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();
        int i = 0;
        int n = intervals.length;

        // Phase 1: Add all intervals that come before newInterval and don't overlap
        while (i < n && intervals[i][1] < newInterval[0]) {
            result.add(intervals[i]);
            i++;
        }

        // Phase 2: Merge newInterval with all overlapping intervals
        while (i < n && intervals[i][0] <= newInterval[1]) {
            // Update the start of newInterval to the minimum of current newInterval start and current interval start
            newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
            // Update the end of newInterval to the maximum of current newInterval end and current interval end
            newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            i++;
        }
        // Add the merged newInterval to the result list
        result.add(newInterval);

        // Phase 3: Add all remaining intervals (which come after the merged newInterval and don't overlap)
        while (i < n) {
            result.add(intervals[i]);
            i++;
        }

        // Convert the ArrayList to a 2D array and return
        return result.toArray(new int[result.size()][]);
    }
}
```

-----

## 56\. Merge Intervals

**Merge Intervals** problem mein aapko overlapping intervals ko merge karke non-overlapping intervals ki list banani hai.

-----

### Description/Overview

Aapko ek array `intervals` diya gaya hai jahan `intervals[i] = [start_i, end_i]` hai. Aapko saare overlapping intervals ko merge karna hai aur ek array return karna hai non-overlapping intervals ka jo `intervals` ke saare intervals ko cover kare.

For example:

  * `intervals = [[1,3],[2,6],[8,10],[15,18]]`
      * Output: `[[1,6],[8,10],[15,18]]`
      * Explanation: `[1,3]` aur `[2,6]` overlap karte hain, unko merge karke `[1,6]` bana.
  * `intervals = [[1,4],[4,5]]`
      * Output: `[[1,5]]`
      * Explanation: `[1,4]` aur `[4,5]` ko merge karke `[1,5]` bana.

### Approach (How to do it)

Is problem ko solve karne ke liye sabse pehle intervals ko unke start times ke hisaab se sort karna zaroori hai. Sorting ke baad, hum linear scan karke overlapping intervals ko merge kar sakte hain.

**Algorithm:**

1.  **Sort Intervals:** `intervals` array ko `start_i` ke hisaab se sort karo. `Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]))`.
2.  **Initialize Result:** Ek `List<int[]>` `mergedIntervals` banao.
3.  **Iterate and Merge:** `intervals` array par iterate karo.
      * Har `currentInterval` (`[currentStart, currentEnd]`) ke liye:
          * Agar `mergedIntervals` empty hai ya `currentInterval` ka `currentStart` `mergedIntervals` ke last interval ke `end` se bada hai (`mergedIntervals.get(mergedIntervals.size() - 1)[1] < currentStart`), toh koi overlap nahi hai. `currentInterval` ko `mergedIntervals` mein add kar do.
          * Agar overlap hai (matlab `currentInterval` ka `currentStart` `mergedIntervals` ke last interval ke `end` se chhota ya barabar hai), toh `mergedIntervals` ke last interval ke `end` ko update karo: `mergedIntervals.get(mergedIntervals.size() - 1)[1] = Math.max(mergedIntervals.get(mergedIntervals.size() - 1)[1], currentEnd)`.
4.  **Return Result:** `mergedIntervals` list ko `int[][]` mein convert karke return karo.

### Solution (The Way to Solve)

Sabse pehle, intervals ko unke start times ke according sort karte hain. Phir, ek `ArrayList` mein merged intervals ko store karte hain. Hum sorted intervals par iterate karte hain. Har interval ke liye, agar `mergedIntervals` empty hai ya current interval pichle merged interval se overlap nahi karta, toh current interval ko directly add kar dete hain. Agar overlap karta hai, toh `mergedIntervals` ke last interval ke end time ko current interval ke end time se `Math.max` lekar update karte hain. Yeh process tab tak chalta hai jab tak saare intervals process na ho jayein. Aakhir mein, `ArrayList` ko 2D array mein convert karke return kar dete hain.

### Code

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public int[][] merge(int[][] intervals) {
        // Handle empty or null input
        if (intervals == null || intervals.length == 0) {
            return new int[0][0];
        }

        // Step 1: Sort the intervals based on their start times.
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        List<int[]> merged = new ArrayList<>();

        // Step 2: Iterate through the sorted intervals and merge overlapping ones.
        for (int[] interval : intervals) {
            // If the merged list is empty, or the current interval does not overlap
            // with the last interval in the merged list, just add it.
            if (merged.isEmpty() || interval[0] > merged.get(merged.size() - 1)[1]) {
                merged.add(interval);
            } else {
                // If there is an overlap, merge the current interval with the last interval
                // in the merged list by updating the end time of the last interval.
                merged.get(merged.size() - 1)[1] = Math.max(merged.get(merged.size() - 1)[1], interval[1]);
            }
        }

        // Convert the ArrayList to a 2D array and return.
        return merged.toArray(new int[merged.size()][]);
    }
}
```

-----

## 435\. Non-overlapping Intervals

**Non-overlapping Intervals** problem mein aapko minimum number of intervals remove karne hote hain takki bache hue intervals non-overlapping ho jayein.

-----

### Description/Overview

Aapko ek array `intervals` diya gaya hai jahan `intervals[i] = [start_i, end_i]` hai. Aapko minimum number of intervals remove karne hain taaki baaki ke bache hue intervals non-overlapping ho jayein.

For example:

  * `intervals = [[1,2],[2,3],[3,4],[1,3]]`
      * Output: 1
      * Explanation: `[1,3]` remove karo, baaki `[[1,2],[2,3],[3,4]]` non-overlapping hain.
  * `intervals = [[1,2],[1,2],[1,2]]`
      * Output: 2
      * Explanation: Do `[1,2]` remove karne padenge.
  * `intervals = [[1,2],[2,3]]`
      * Output: 0
      * Explanation: Already non-overlapping.

### Approach (How to do it)

Is problem ko greedy approach se solve kar sakte hain. Iska goal hai maximum number of non-overlapping intervals find karna. Minimum removals is just `total_intervals - max_non_overlapping_intervals`.

**Greedy Strategy:**
Intervals ko unke **end times** ke hisaab se sort karo. Jab end times sorted honge, toh hum hamesha us interval ko choose kar sakte hain jo sabse pehle khatam hota hai, taaki humare paas aage ke intervals ke liye zyada space bache.

**Algorithm:**

1.  **Sort Intervals:** `intervals` array ko unke `end_i` ke hisaab se sort karo. Agar end times same hain, toh start times ke hisaab se sort karo (though this usually doesn't matter much for end-time greedy). `Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]))`.
2.  **Initialize:**
      * `count = 0` (number of intervals removed).
      * `end = intervals[0][1]` (end time of the first chosen non-overlapping interval).
3.  **Iterate and Select:** Loop `i` from `1` to `intervals.length - 1`:
      * `currentInterval = intervals[i]`.
      * **Overlap Check:** Agar `currentInterval.start < end` hai, toh current interval pichle selected interval se overlap karta hai. Iska matlab hai ki humein `currentInterval` ko **remove** karna padega. `count++`.
      * **No Overlap:** Agar `currentInterval.start >= end` hai, toh current interval pichle selected interval se overlap nahi karta. Iska matlab hai ki hum `currentInterval` ko **include** kar sakte hain. Toh, `end = currentInterval.end` ko update karo.
4.  **Return Result:** `count` return karo.

### Solution (The Way to Solve)

Problem ko solve karne ke liye, hum intervals ko unke end times ke according sort karte hain. Is greedy choice se hum hamesha us interval ko select karte hain jo sabse pehle khatam hota hai, jisse aage ke intervals ke liye zyada room milta hai. Ek `end` variable maintain karte hain jo current non-overlapping set ke last interval ka end time store karta hai, aur `removed_count` ko 0 se initialize karte hain. Phir, sorted intervals par iterate karte hain. Agar current interval pichle selected interval se overlap karta hai (current start \< previous end), toh `removed_count` ko increment karte hain. Agar overlap nahi karta, toh current interval ko non-overlapping set mein include karte hain aur `end` ko current interval ke end time se update karte hain. Aakhir mein `removed_count` return karte hain.

### Code

```java
import java.util.Arrays;

class Solution {
    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        // Step 1: Sort intervals by their end times.
        // This greedy choice helps us to always pick the interval that finishes earliest,
        // leaving maximum room for subsequent intervals.
        
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));

        int end = intervals[0][1]; // The end time of the first chosen non-overlapping interval
        int removedCount = 0;      // Counter for intervals that need to be removed

        // Iterate from the second interval
        for (int i = 1; i < intervals.length; i++) {
            // If the current interval's start time is less than the end time of the previously
            // chosen non-overlapping interval, it means they overlap.
            // We must remove one of them. Since we sorted by end times, the current interval
            // is guaranteed to have an end time >= the previous, so removing the current
            // (or rather, counting it as removed) is optimal to keep the end time as small as possible.
            if (intervals[i][0] < end) {
                removedCount++;
            } else {
                // If there is no overlap, then we can include this current interval
                // in our non-overlapping set. Update the 'end' to its end time.
                end = intervals[i][1];
            }
        }

        return removedCount;
    }
}
```

-----

## 253\. Meeting Rooms

**Meeting Rooms** problem (LeetCode Premium) mein aapko check karna hota hai ki kya saari meetings ko ek single meeting room mein accommodate kiya ja sakta hai.

-----

### Description/Overview

Aapko ek array `intervals` diya gaya hai jahan `intervals[i] = [start_i, end_i]` ek meeting ke start aur end times ko represent karta hai. Aapko return karna hai `true` agar ek person saari meetings attend kar sakta hai (matlab koi do meetings overlap nahi karti), `false` otherwise.

For example:

  * `intervals = [[0,30],[5,10],[15,20]]`
      * Output: `false`
      * Explanation: `[0,30]` overlaps with `[5,10]` and `[15,20]`.
  * `intervals = [[7,10],[2,4]]`
      * Output: `true`
      * Explanation: No overlaps.

### Approach (How to do it)

Is problem ko solve karne ka sabse simple tareeka hai ki meetings ko unke start times ke hisaab se sort kar do aur phir check karo ki kya koi consecutive meetings overlap karti hain.

**Algorithm:**

1.  **Sort Intervals:** `intervals` array ko `start_i` ke hisaab se sort karo. `Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]))`.
2.  **Check Overlaps:** Loop `i` from `0` to `intervals.length - 2` (last interval tak check karne ki zaroorat nahi).
      * `currentMeeting = intervals[i]`.
      * `nextMeeting = intervals[i+1]`.
      * Agar `currentMeeting.end > nextMeeting.start` hai, toh overlap hai. Return `false`.
3.  **Return Result:** Agar loop complete ho jata hai bina `false` return kiye, toh matlab koi overlap nahi hai. Return `true`.

### Solution (The Way to Solve)

Meeting intervals ko unke start times ke according sort karte hain. Phir, sorted intervals par iterate karte hain aur adjacent intervals ko compare karte hain. Agar `intervals[i]` ka end time `intervals[i+1]` ke start time se bada hai, toh overlap hai aur ek hi room mein saari meetings accommodate nahi ho sakti, toh `false` return karte hain. Agar loop bina `false` return kiye complete ho jata hai, toh matlab saari meetings ko ek single room mein accommodate kiya ja sakta hai, toh `true` return karte hain.

### Code

```java
import java.util.Arrays;

class Solution {
    public boolean canAttendMeetings(int[][] intervals) {
        // Handle empty or null input
        if (intervals == null || intervals.length == 0) {
            return true;
        }

        // Step 1: Sort the intervals based on their start times.
        // This is crucial for efficiently checking overlaps with adjacent meetings.
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        // Step 2: Iterate through the sorted intervals and check for overlaps.
        for (int i = 0; i < intervals.length - 1; i++) {
            // If the end time of the current meeting is greater than the start time
            // of the next meeting, then there is an overlap.
            if (intervals[i][1] > intervals[i+1][0]) {
                return false; // Cannot attend all meetings in one room
            }
        }

        // If no overlaps are found after checking all adjacent meetings,
        // then all meetings can be attended in one room.
        return true;
    }
}
```

-----

## 352\. Meeting Rooms II

**Meeting Rooms II** problem (LeetCode Premium) mein aapko saari meetings accommodate karne ke liye minimum kitne meeting rooms ki zaroorat padegi, yeh find karna hota hai.

-----

### Description/Overview

Aapko ek array `intervals` diya gaya hai jahan `intervals[i] = [start_i, end_i]` ek meeting ke start aur end times ko represent karta hai. Aapko return karna hai ki saari meetings accommodate karne ke liye minimum kitne conference rooms ki zaroorat padegi.

For example:

  * `intervals = [[0,30],[5,10],[15,20]]`
      * Output: 2
      * Explanation: `[0,30]` requires one room. `[5,10]` starts while `[0,30]` is ongoing, so it needs another room. `[15,20]` starts while both are ongoing, but `[5,10]` would be over. Wait, no, `[15,20]` starts while `[0,30]` is still going. So `[0,30]` and `[5,10]` overlap, and `[0,30]` and `[15,20]` also overlap. Max concurrent meetings are 2 (e.g., at time 10, [0,30] and [5,10] are active, but [5,10] ends. At time 15, [0,30] and [15,20] are active).
  * `intervals = [[7,10],[2,4]]`
      * Output: 1
      * Explanation: No overlaps, one room sufficient.

### Approach (How to do it)

Is problem ko solve karne ke kai tareeke hain:

1.  **Min-Heap (Priority Queue):** Yeh sabse common aur efficient approach hai.

      * Intervals ko start time ke hisaab se sort karo.
      * Ek min-heap (Priority Queue) use karo jo meeting ke `end times` ko store karega. Heap ka top element sabse pehle khatam hone wali meeting ka end time hoga.
      * Har meeting par iterate karo:
          * Agar heap empty nahi hai aur current meeting ka `start time` heap ke top element (`earliest_ending_meeting_end_time`) se bada ya barabar hai, toh iska matlab hai ki current meeting ko ussi room mein accommodate kar sakte hain jo abhi free hua hai. Heap se `earliest_ending_meeting_end_time` ko remove kar do.
          * Current meeting ke `end time` ko heap mein add kar do.
      * Heap ka size hi minimum required rooms hoga.

2.  **Chronological Events (Sweep Line):** Yeh bhi ek effective approach hai.

      * Saare start times aur end times ko ek flat list mein daal do. Har time ke saath uska type bhi store karo (`+1` for start, `-1` for end).
      * Is list ko time ke hisaab se sort karo. Agar times same hain, toh end event (`-1`) ko start event (`+1`) se pehle process karo (takki same time par room free hoke use ho sake).
      * Ek `rooms = 0` aur `maxRooms = 0` variable maintain karo.
      * Sorted events par iterate karo:
          * Agar start event hai (`+1`), `rooms++`.
          * Agar end event hai (`-1`), `rooms--`.
          * Har step par `maxRooms = Math.max(maxRooms, rooms)` update karo.
      * `maxRooms` hi answer hoga.

**Algorithm (Min-Heap):**

1.  **Sort Intervals:** `intervals` array ko `start_i` ke hisaab se sort karo. `Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]))`.
2.  **Initialize Priority Queue:** `PriorityQueue<Integer> minHeap = new PriorityQueue<>();` (yeh end times store karega, smallest end time at top).
3.  **Process Meetings:**
      * `minHeap.add(intervals[0][1]);` (Pehli meeting ka end time heap mein add karo).
      * Loop `i` from `1` to `intervals.length - 1`:
          * `currentMeeting = intervals[i]`.
          * Agar `currentMeeting.start >= minHeap.peek()` hai, toh ek room free ho gaya hai. `minHeap.poll()` karo.
          * `minHeap.add(currentMeeting.end);` (Current meeting ke end time ko heap mein add karo).
4.  **Return Result:** `minHeap.size()` return karo.

### Solution (The Way to Solve)

Is problem ko solve karne ke liye, hum intervals ko unke start times ke according sort karte hain. Phir, ek min-heap (PriorityQueue) ka use karte hain jo meetings ke end times ko store karega, jisse humein sabse pehle free hone wale room ka end time mil sake.
Pehli meeting ke end time ko heap mein add karte hain. Phir, baaki ki meetings par iterate karte hain. Har meeting ke liye, agar current meeting ka start time heap ke top par मौजूद end time se bada ya barabar hai, toh iska matlab hai ki koi room free ho gaya hai aur hum us room ko current meeting ke liye use kar sakte hain. Is case mein, heap se top element ko remove karte hain. Finally, current meeting ke end time ko heap mein add karte hain. Loop ke end mein, heap ka size hi required minimum meeting rooms ki sankhya hogi.

### Code

```java
import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {
    public int minMeetingRooms(int[][] intervals) {
        // Handle empty or null input
        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        // Step 1: Sort the intervals based on their start times.
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        // Step 2: Use a min-heap (PriorityQueue) to store the end times of meetings.
        // The smallest end time will always be at the top.
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        // Add the end time of the first meeting to the heap.
        minHeap.add(intervals[0][1]);

        // Step 3: Iterate through the rest of the intervals.
        for (int i = 1; i < intervals.length; i++) {
            // Get the current interval
            int[] currentInterval = intervals[i];

            // If the current meeting's start time is greater than or equal to the earliest
            // ending meeting's end time (heap.peek()), it means a room has become free.
            // We can reuse that room. So, remove the earliest ending meeting.
            if (currentInterval[0] >= minHeap.peek()) {
                minHeap.poll();
            }
            
            // Add the end time of the current meeting to the heap.
            // This reserves a room for the current meeting. If a room was reused,
            // this effectively updates its end time. If a new room was needed,
            // it adds a new end time to track.
            minHeap.add(currentInterval[1]);
        }

        // The size of the minHeap at the end represents the maximum number of
        // concurrent meetings, which is the minimum number of rooms needed.
        return minHeap.size();
    }
}
```

-----

## 1851\. Minimum Interval to Include Each Query

**Minimum Interval to Include Each Query** problem mein aapko queries ke liye smallest interval find karna hota hai jo unhe contain karta ho.

-----

### Description/Overview

Aapko ek 2D integer array `intervals` diya gaya hai jahan `intervals[i] = [left_i, right_i]` hai, jo `left_i` se `right_i` tak ka inclusive interval hai. Aapko ek integer array `queries` diya gaya hai.
Har `queries[j]` ke liye, aapko smallest size ke interval `[left_i, right_i]` ko find karna hai jo `queries[j]` ko contain karta ho (`left_i <= queries[j] <= right_i`). Interval ka size `right_i - left_i + 1` hota hai. Agar koi aisa interval exist nahi karta, toh `-1` return karo.
Aapko ek array return karna hai containing answers for each query.

For example:

  * `intervals = [[1,4],[2,4],[3,6],[4,4]]`, `queries = [2,3,4,5]`
      * Output: `[3,3,1,4]`
      * Explanation:
          * `queries[0] = 2`: `[2,4]` size 3 (smallest)
          * `queries[1] = 3`: `[2,4]` size 3 (smallest)
          * `queries[2] = 4`: `[4,4]` size 1 (smallest)
          * `queries[3] = 5`: `[3,6]` size 4 (smallest)
  * `intervals = [[2,3],[2,5],[1,8],[20,25]]`, `queries = [2,19,5,22]`
      * Output: `[2,-1,4,6]`

### Approach (How to do it)

Yeh problem ek combination hai sorting, two-pointers, aur min-heap (Priority Queue) ka. Yeh sweep-line algorithm ke concepts se bhi related hai.

**Algorithm:**

1.  **Prepare Data:**

      * `intervals` ko `left_i` ke hisaab se sort karo.
      * `queries` ko unke values ke saath `original_index` store karke sort karo. Isse hum answers ko original order mein return kar payenge. Create `query_with_index` array: `[[query_val, original_idx], ...]`.

2.  **Initialize Structures:**

      * `result = new int[queries.length]` (answers store karne ke liye).
      * `minHeap = new PriorityQueue<>((a, b) -> (a[1] - a[0]) - (b[1] - b[0]))` (yeh intervals store karega `[left, right]`, sorted by their `size`).
      * `interval_pointer = 0` (intervals array ke liye two-pointer).

3.  **Process Sorted Queries:** Loop `query_with_index` ke har `[queryVal, originalIdx]` par:

      * **Add Relevant Intervals:** Jab tak `interval_pointer` `intervals.length` se chhota hai aur `intervals[interval_pointer][0] <= queryVal` hai:
          * Current `intervals[interval_pointer]` ko `minHeap` mein add karo.
          * `interval_pointer++`.
      * **Remove Irrelevant Intervals:** `minHeap` se un intervals ko remove karo jinhe `queryVal` contain nahi karta (`minHeap.peek()[1] < queryVal`).
      * **Get Answer:**
          * Agar `minHeap` empty nahi hai, toh `minHeap.peek()` ka size `result[originalIdx]` mein store karo.
          * Agar `minHeap` empty hai, toh `result[originalIdx]` mein `-1` store karo.

4.  **Return Result:** `result` array return karo.

### Solution (The Way to Solve)

Is problem ko solve karne ke liye, hum `intervals` ko unke `start` times ke according sort karte hain. `queries` ko bhi unke values ke according sort karte hain, lekin unke original indices ko store karke rakhte hain taaki answers ko sahi order mein return kar sakein.
Ek `minHeap` use karte hain jo intervals ko unke size ke according store karega. Aur ek `interval_pointer` rakhte hain `intervals` array ke liye.
Sorted `queries` par iterate karte hain. Har query `q` ke liye:

1.  Jab tak `intervals` mein `q` se shuru hone wale intervals hain, unhein `minHeap` mein add karte hain.
2.  `minHeap` se un intervals ko remove karte hain jin ka `end` time `q` se chhota hai (matlab jo `q` ko contain nahi karte).
3.  Agar `minHeap` empty nahi hai, toh `minHeap` ke top par मौजूद interval ka size `q` ke original index par `result` array mein store karte hain. Warna, `-1` store karte hain.
    Aakhir mein `result` array return karte hain.

### Code

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

class Solution {
    public int[] minInterval(int[][] intervals, int[] queries) {
        int n = intervals.length;
        int m = queries.length;

        // Step 1: Sort intervals by their start times.
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        // Step 2: Create a new array for queries to store their original indices.
        // Sort queries based on their values.
        int[][] queriesWithIndex = new int[m][2];
        for (int i = 0; i < m; i++) {
            queriesWithIndex[i][0] = queries[i]; // query value
            queriesWithIndex[i][1] = i;           // original index
        }
        Arrays.sort(queriesWithIndex, (a, b) -> Integer.compare(a[0], b[0]));

        int[] result = new int[m]; // Array to store answers for queries in their original order
        int intervalPointer = 0;   // Pointer for the sorted intervals array

        // Min-heap to store intervals that currently "cover" the query point.
        // The heap is ordered by interval size (right - left + 1).
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> (a[1] - a[0]) - (b[1] - b[0]));

        // Step 3: Process queries in their sorted order.
        for (int i = 0; i < m; i++) {
            int queryVal = queriesWithIndex[i][0];
            int originalIdx = queriesWithIndex[i][1];

            // Add all intervals whose left bound is <= current query value
            while (intervalPointer < n && intervals[intervalPointer][0] <= queryVal) {
                minHeap.add(intervals[intervalPointer]);
                intervalPointer++;
            }

            // Remove intervals from heap whose right bound is < current query value
            while (!minHeap.isEmpty() && minHeap.peek()[1] < queryVal) {
                minHeap.poll();
            }

            // The top of the heap (if not empty) now contains the smallest valid interval.
            if (!minHeap.isEmpty()) {
                int[] smallestInterval = minHeap.peek();
                result[originalIdx] = smallestInterval[1] - smallestInterval[0] + 1;
            } else {
                result[originalIdx] = -1; // No interval covers this query
            }
        }

        return result;
    }
}
```
