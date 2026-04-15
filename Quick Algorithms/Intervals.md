# 💡 Intervals 
**Logic:** 
1. **Sort karo** (Usually start time ke hisaab se)
2. **Traverse karo** aur overlap check karo
3. **Merge ya Process** karo according to problem

```
Template:
Arrays.sort(intervals, (a, b) -> a[0] - b[0]); // Start time se sort
for (int[] interval : intervals) {
    if (noOverlap) {
        // Nayi interval start
    } else {
        // Merge with previous
    }
}
```

---

## 🎯 1. Merge Intervals
**Problem:** Overlapping intervals ko merge karke ek simplified list banao.
**Logic:** Sort by start time. Agar current interval ka start previous ke end se kam hai to overlap hai, merge karo.

```java
public int[][] merge(int[][] intervals) {
    if (intervals.length <= 1) return intervals;
    
    // Step 1: Start time se sort karo
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
    
    List<int[]> result = new ArrayList<>();
    int[] current = intervals[0];
    result.add(current);
    
    for (int[] interval : intervals) {
        int currentEnd = current[1];
        int nextStart = interval[0];
        int nextEnd = interval[1];
        
        // Overlap check: Agar next ka start current ke end se chhota ya barabar
        if (nextStart <= currentEnd) {
            // Merge karo: End ko max se update karo
            current[1] = Math.max(currentEnd, nextEnd);
        } else {
            // No overlap: Nayi interval start
            current = interval;
            result.add(current);
        }
    }
    
    return result.toArray(new int[result.size()][]);
}
```

---

## 🎯 2. Insert Interval
**Problem:** Sorted non-overlapping intervals mein ek naya interval insert karo aur merge karo agar zaroorat ho.
**Logic:** Teen parts mein divide karo:
1. Jo intervals naye interval se pehle khatam ho jayein (no overlap)
2. Jo intervals overlap karein (merge karo)
3. Jo intervals naye interval ke baad shuru ho (no overlap)

```java
public int[][] insert(int[][] intervals, int[] newInterval) {
    List<int[]> result = new ArrayList<>();
    int i = 0, n = intervals.length;
    
    // Part 1: Saare intervals jo newInterval se pehle khatam ho jayein
    while (i < n && intervals[i][1] < newInterval[0]) {
        result.add(intervals[i]);
        i++;
    }
    
    // Part 2: Overlap wale intervals ko merge karo
    while (i < n && intervals[i][0] <= newInterval[1]) {
        newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
        newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
        i++;
    }
    result.add(newInterval);
    
    // Part 3: Baaki bache hue intervals add karo
    while (i < n) {
        result.add(intervals[i]);
        i++;
    }
    
    return result.toArray(new int[result.size()][]);
}
```

---

## 🎯 3. Non-Overlapping Intervals (Min Removals)
**Problem:** Minimum kitne intervals hatao ki saare non-overlapping ho jayein.
**Logic:** Sort by **END time** (Important!). Jo interval jaldi khatam ho, use rakho. Greedy approach.

```java
public int eraseOverlapIntervals(int[][] intervals) {
    if (intervals.length == 0) return 0;
    
    // Yahan END time se sort karna important hai!
    Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
    
    int count = 1; // Kitne intervals rakh sakte hain
    int lastEnd = intervals[0][1];
    
    for (int i = 1; i < intervals.length; i++) {
        // Agar current interval ka start lastEnd se bada ya barabar hai to overlap nahi
        if (intervals[i][0] >= lastEnd) {
            lastEnd = intervals[i][1];
            count++;
        }
    }
    
    // Hatane wale intervals = total - rakhne wale
    return intervals.length - count;
}
```

---

## 🎯 4. Meeting Rooms I
**Problem:** Kya ek banda saari meetings attend kar sakta hai? (Koi overlap to nahi?)
**Logic:** Sort by start time. Check karo ki kisi meeting ka start pichli meeting ke end se pehle to nahi.

```java
public boolean canAttendMeetings(int[][] intervals) {
    // Start time se sort karo
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
    
    for (int i = 1; i < intervals.length; i++) {
        // Agar current meeting ka start pichli meeting ke end se pehle hai
        if (intervals[i][0] < intervals[i - 1][1]) {
            return false; // Overlap mil gaya
        }
    }
    return true;
}
```

---

## 🎯 5. Meeting Rooms II
**Problem:** Minimum kitne meeting rooms chahiye saari meetings ke liye?
**Logic:** Start aur end times ko alag arrays mein lo. Sort karo. Do pointers use karo.

```java
public int minMeetingRooms(int[][] intervals) {
    if (intervals.length == 0) return 0;
    
    int n = intervals.length;
    int[] startTimes = new int[n];
    int[] endTimes = new int[n];
    
    for (int i = 0; i < n; i++) {
        startTimes[i] = intervals[i][0];
        endTimes[i] = intervals[i][1];
    }
    
    Arrays.sort(startTimes);
    Arrays.sort(endTimes);
    
    int rooms = 0;
    int endPtr = 0;
    
    for (int i = 0; i < n; i++) {
        // Agar nayi meeting start ho rahi hai aur koi room free nahi hai
        if (startTimes[i] < endTimes[endPtr]) {
            rooms++;
        } else {
            // Room free ho gaya
            endPtr++;
        }
    }
    
    return rooms;
}
```

**Alternate Logic using Min Heap:**
```java
public int minMeetingRoomsHeap(int[][] intervals) {
    if (intervals.length == 0) return 0;
    
    // Start time se sort
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
    
    // Min Heap end times store karega
    PriorityQueue<Integer> pq = new PriorityQueue<>();
    pq.add(intervals[0][1]);
    
    for (int i = 1; i < intervals.length; i++) {
        // Agar pichli meeting khatam ho gayi to room free
        if (intervals[i][0] >= pq.peek()) {
            pq.poll();
        }
        pq.add(intervals[i][1]);
    }
    
    return pq.size();
}
```

---

## 🎯 6. Interval List Intersections
**Problem:** Do sorted interval lists di hain. Unke intersections (overlapping parts) return karo.
**Logic:** Do pointers `i` aur `j` dono lists ke liye. Overlap check karo aur pointer aage badhao.

```java
public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
    List<int[]> result = new ArrayList<>();
    int i = 0, j = 0;
    
    while (i < firstList.length && j < secondList.length) {
        // Overlap ka start = max of both starts
        int start = Math.max(firstList[i][0], secondList[j][0]);
        // Overlap ka end = min of both ends
        int end = Math.min(firstList[i][1], secondList[j][1]);
        
        // Valid overlap check
        if (start <= end) {
            result.add(new int[]{start, end});
        }
        
        // Jiska end chhota hai usse aage badhao
        if (firstList[i][1] < secondList[j][1]) {
            i++;
        } else {
            j++;
        }
    }
    
    return result.toArray(new int[result.size()][]);
}
```

---

## 🎯 7. Minimum Number of Arrows to Burst Balloons
**Problem:** Balloons ke intervals diye hain. Ek arrow vertically jaati hai aur uss line mein saare balloons phod deti hai. Minimum arrows batao.
**Logic:** Sort by **END time**. Arrow ko balloon ke end pe maaro. Saare overlapping balloons phoot jayenge.

```java
public int findMinArrowShots(int[][] points) {
    if (points.length == 0) return 0;
    
    // END time se sort karo (Important!)
    Arrays.sort(points, (a, b) -> Integer.compare(a[1], b[1]));
    
    int arrows = 1;
    int arrowPos = points[0][1];
    
    for (int i = 1; i < points.length; i++) {
        // Agar current balloon ka start arrowPos se aage hai
        if (points[i][0] > arrowPos) {
            arrows++;
            arrowPos = points[i][1];
        }
    }
    
    return arrows;
}
```

---

## 🎯 8. Remove Covered Intervals
**Problem:** Agar ek interval doosre interval mein completely covered hai to use hatao. Batao kitne bache.
**Logic:** Sort by start time ascending, aur agar start same hai to end time descending. Phir track karo max end.

```java
public int removeCoveredIntervals(int[][] intervals) {
    // Start asc, agar start same to end desc
    Arrays.sort(intervals, (a, b) -> {
        if (a[0] != b[0]) return a[0] - b[0];
        return b[1] - a[1];
    });
    
    int count = 0;
    int maxEnd = 0;
    
    for (int[] interval : intervals) {
        // Agar current interval ka end maxEnd se bada hai to covered nahi hai
        if (interval[1] > maxEnd) {
            count++;
            maxEnd = interval[1];
        }
        // Warna ye interval covered hai, count nahi badhega
    }
    
    return count;
}
```

---

## 🎯 9. Employee Free Time
**Problem:** Employees ke busy intervals diye hain. Sabka common free time batao.
**Logic:** Saare intervals ko flatten karo, merge karo. Phir merged intervals ke beech ke gaps free time hain.

```java
public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
    List<Interval> allIntervals = new ArrayList<>();
    for (List<Interval> emp : schedule) {
        allIntervals.addAll(emp);
    }
    
    // Start time se sort
    Collections.sort(allIntervals, (a, b) -> a.start - b.start);
    
    List<Interval> merged = new ArrayList<>();
    Interval current = allIntervals.get(0);
    
    for (int i = 1; i < allIntervals.size(); i++) {
        Interval next = allIntervals.get(i);
        if (next.start <= current.end) {
            current.end = Math.max(current.end, next.end);
        } else {
            merged.add(current);
            current = next;
        }
    }
    merged.add(current);
    
    // Free time = gaps between merged intervals
    List<Interval> freeTime = new ArrayList<>();
    for (int i = 1; i < merged.size(); i++) {
        freeTime.add(new Interval(merged.get(i - 1).end, merged.get(i).start));
    }
    
    return freeTime;
}
```

---

## 🎯 10. Car Pooling
**Problem:** Car mein capacity `c` hai. Trips di hain `[passengers, from, to]`. Check karo ki saari trips possible hain ya nahi.
**Logic:** Ye interval nahi hai, **Line Sweep** / **Prefix Sum** hai. Har point pe kitne passengers hain track karo.

```java
public boolean carPooling(int[][] trips, int capacity) {
    int[] timeline = new int[1001]; // Constraints ke hisaab se
    
    for (int[] trip : trips) {
        timeline[trip[1]] += trip[0]; // Pickup pe add
        timeline[trip[2]] -= trip[0]; // Drop pe subtract
    }
    
    int currentPassengers = 0;
    for (int i = 0; i < 1001; i++) {
        currentPassengers += timeline[i];
        if (currentPassengers > capacity) {
            return false;
        }
    }
    return true;
}
```

---

## 🎯 11. Maximum Number of Events That Can Be Attended
**Problem:** Events diye hain `[start, end]`. Har din ek event attend kar sakte ho. Maximum kitne events attend kar sakte ho?
**Logic:** Sort by start day. Min Heap use karo end day ke hisaab se. Har day ke liye available events mein se jo jaldi khatam ho raha ho use attend karo.

```java
public int maxEvents(int[][] events) {
    // Start day se sort
    Arrays.sort(events, (a, b) -> a[0] - b[0]);
    
    PriorityQueue<Integer> pq = new PriorityQueue<>(); // Min heap of end days
    int i = 0, n = events.length;
    int day = 1, count = 0;
    
    while (i < n || !pq.isEmpty()) {
        // Current day pe shuru hone wale saare events add karo
        while (i < n && events[i][0] == day) {
            pq.add(events[i][1]);
            i++;
        }
        
        // Expired events hatao
        while (!pq.isEmpty() && pq.peek() < day) {
            pq.poll();
        }
        
        // Agar koi valid event hai to attend karo
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

## 🎯 12. Partition Labels
**Problem:** String ko maximum parts mein todo taaki har character sirf ek part mein aaye.
**Logic:** Har character ka last occurrence track karo. Jab current index last occurrence ke barabar ho, partition karo.

```java
public List<Integer> partitionLabels(String s) {
    int[] lastIndex = new int[26];
    
    // Har character ka last index store karo
    for (int i = 0; i < s.length(); i++) {
        lastIndex[s.charAt(i) - 'a'] = i;
    }
    
    List<Integer> result = new ArrayList<>();
    int start = 0, end = 0;
    
    for (int i = 0; i < s.length(); i++) {
        end = Math.max(end, lastIndex[s.charAt(i) - 'a']);
        
        // Partition point mil gaya
        if (i == end) {
            result.add(end - start + 1);
            start = i + 1;
        }
    }
    
    return result;
}
```

---

## 📋 Intervals Cheat Sheet

| Problem Type | Sorting Logic | Key Trick |
| :--- | :--- | :--- |
| **Merge Intervals** | Sort by START | Track current interval, update end |
| **Insert Interval** | Already sorted | Three parts: before, merge, after |
| **Non-overlapping (Min Remove)** | Sort by **END** | Greedy: Keep intervals with earliest end |
| **Meeting Rooms I** | Sort by START | Check adjacent overlap |
| **Meeting Rooms II** | Sort START & END separately | Two pointers OR Min Heap |
| **Interval Intersection** | Two pointers | `max(start), min(end)` |
| **Burst Balloons / Arrows** | Sort by **END** | Arrow at balloon's end |
| **Remove Covered Intervals** | Start ASC, End DESC | Track maxEnd |
| **Employee Free Time** | Flatten + Merge | Gaps between merged intervals |
| **Car Pooling** | Line Sweep | Timeline array with +pickup, -drop |
| **Max Events Attended** | Sort by START | Min Heap of end days |
| **Partition Labels** | No sorting | Last occurrence array |

---

## 🔥 Important Patterns Summary

### Pattern 1: Merge karna hai
```java
Arrays.sort(intervals, (a,b) -> a[0] - b[0]);
for (interval : intervals) {
    if (overlap) update end = max(end, curr[1]);
    else new interval start;
}
```

### Pattern 2: Count karna hai (Min Rooms, Arrows)
```java
Arrays.sort(intervals, (a,b) -> a[1] - b[1]); // Sort by END
for (interval : intervals) {
    if (curr[0] > lastEnd) { count++; lastEnd = curr[1]; }
}
```

### Pattern 3: Intersection nikalna hai
```java
start = max(a.start, b.start);
end = min(a.end, b.end);
if (start <= end) add intersection;
```

### Pattern 4: Line Sweep (Timeline)
```java
int[] timeline = new int[MAX];
timeline[start] += val;
timeline[end] -= val;
// Prefix sum karo
```

---

## 💡 Tricky Points Yaad Rakho

1. **Sort by START ya END?**
   - Merge/Insert/Meeting Rooms I → Sort by START
   - Non-overlapping/Arrows → Sort by END
   - Remove Covered → Start ASC, End DESC

2. **Overlap Condition:**
   - `curr.start <= prev.end` → Overlap hai

3. **Min Heap kab use karna hai?**
   - Meeting Rooms II, Max Events (jahan dynamic ending track karni ho)

4. **Line Sweep vs Intervals:**
   - Agar "at any point" type ka sawaal hai → Line Sweep (Timeline Array)
