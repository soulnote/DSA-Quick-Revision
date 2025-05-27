# ⭐ Task Scheduler

You are given an array of CPU tasks labeled from `A` to `Z`, and a non-negative integer `n` which represents the cooldown period between two same tasks.

* Each CPU interval can execute **one task** or be **idle**.
* Tasks can be done in **any order**.
* There must be at least `n` intervals between two executions of the **same task**.
* Return the **minimum number of CPU intervals** required to complete all tasks.

---

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
A valid schedule is:
`A -> B -> idle -> A -> B -> idle -> A -> B`

After completing task A, you must wait 2 intervals before executing A again. The same applies for task B. Idle intervals fill the cooldown gaps.

---

### ✅ Java Code with Comments

```java
import java.util.*;

class Solution {
    public int leastInterval(char[] tasks, int n) {
        if (n == 0) return tasks.length;  // No cooldown means just tasks length

        // Frequency array for tasks 'A' to 'Z'
        int[] frequency = new int[26];
        for (char task : tasks) {
            frequency[task - 'A']++;
        }

        // Max heap to get the task with highest remaining count
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for (int count : frequency) {
            if (count > 0) {
                maxHeap.add(count);
            }
        }

        int time = 0;  // Total intervals count
        // Queue to hold tasks waiting for cooldown with pair (remainingCount, nextAvailableTime)
        Queue<Pair<Integer, Integer>> waitQueue = new LinkedList<>();

        // Process until all tasks are done
        while (!maxHeap.isEmpty() || !waitQueue.isEmpty()) {
            time++;  // One time unit passes

            if (!maxHeap.isEmpty()) {
                int currentTaskCount = maxHeap.poll() - 1;  // Execute task once
                if (currentTaskCount > 0) {
                    // Add task to cooldown queue with next available time
                    waitQueue.add(new Pair<>(currentTaskCount, time + n));
                }
            }

            // Check if any task's cooldown finished and is ready to be scheduled again
            if (!waitQueue.isEmpty() && waitQueue.peek().getValue() == time) {
                maxHeap.add(waitQueue.poll().getKey());
            }
        }

        return time;  // Total intervals needed to finish all tasks
    }
}
```

---
Here’s a detailed explanation and the complete code for your **Design Twitter** problem:

---

# Design Twitter

### Problem Summary:

* Users can **post tweets**.
* Users can **follow/unfollow** other users.
* Users can **get their news feed**: 10 most recent tweets from themselves and people they follow.
* Tweets are ordered by recency.

---

### Explanation:

* Use a global timestamp to track the order of tweets.
* Store each user's tweets with timestamps.
* Store each user's followees.
* For getting the news feed, gather tweets from the user and their followees, and get the top 10 most recent.


### Key points:

* Tweets stored per user in a list with timestamp.
* Followers tracked with a HashMap mapping follower → set of followees.
* Use a max heap (priority queue) to get the 10 most recent tweets from all followees + self efficiently.

---

### Code:

```java
import java.util.*;

class Twitter {

    private static int timeStamp = 0;  // Global timestamp for ordering tweets
    private Map<Integer, Set<Integer>> followers;  // followerId -> set of followeeIds
    private Map<Integer, List<Tweet>> tweets;  // userId -> list of tweets

    // Tweet class to store tweet id and its timestamp
    private class Tweet {
        int tweetId;
        int time;

        public Tweet(int tweetId, int time) {
            this.tweetId = tweetId;
            this.time = time;
        }
    }

    public Twitter() {
        followers = new HashMap<>();
        tweets = new HashMap<>();
    }

    // Compose a new tweet by a user
    public void postTweet(int userId, int tweetId) {
        tweets.putIfAbsent(userId, new ArrayList<>());
        tweets.get(userId).add(new Tweet(tweetId, timeStamp++));
    }

    // Retrieve 10 most recent tweet IDs in the user's news feed
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> feed = new PriorityQueue<>((a, b) -> b.time - a.time);

        // Add user's own tweets
        if (tweets.containsKey(userId)) {
            feed.addAll(tweets.get(userId));
        }

        // Add tweets from people the user follows
        if (followers.containsKey(userId)) {
            for (int followeeId : followers.get(userId)) {
                if (tweets.containsKey(followeeId)) {
                    feed.addAll(tweets.get(followeeId));
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        int count = 0;
        while (!feed.isEmpty() && count < 10) {
            result.add(feed.poll().tweetId);
            count++;
        }

        return result;
    }

    // Follow a user
    public void follow(int followerId, int followeeId) {
        if (followerId == followeeId) return;  // Cannot follow self

        followers.putIfAbsent(followerId, new HashSet<>());
        followers.get(followerId).add(followeeId);
    }

    // Unfollow a user
    public void unfollow(int followerId, int followeeId) {
        if (followers.containsKey(followerId)) {
            followers.get(followerId).remove(followeeId);
        }
    }
}
```
Your provided solution for **Find Median from Data Stream** is correct, clean, and efficient. Here’s a bit more detailed explanation along with the code to help solidify understanding:

---

# Find Median from Data Stream

### Approach:

* Use **two heaps**:

  * **Max-heap (`small`)** to store the smaller half of the numbers.
  * **Min-heap (`large`)** to store the larger half of the numbers.

* Keep the heaps balanced such that:

  * When total count is **even**, both heaps have the same number of elements.
  * When total count is **odd**, `small` (max-heap) has one extra element.

* When adding a new number:

  * Add it to one heap and then balance by moving the top element between heaps.

* Finding the median:

  * If even total elements: median is average of tops of both heaps.
  * If odd total elements: median is top of `small` (max-heap).

---

### Why does this work?

* Max-heap stores all smaller elements, so its root is the max of smaller half.
* Min-heap stores all larger elements, so its root is the min of larger half.
* This allows quick median calculation from roots of heaps.

---

### Code:

```java
import java.util.Collections;
import java.util.PriorityQueue;

class MedianFinder {
    private PriorityQueue<Integer> small = new PriorityQueue<>(Collections.reverseOrder()); // Max-heap
    private PriorityQueue<Integer> large = new PriorityQueue<>(); // Min-heap
    private boolean even = true; // Track parity of total number of elements

    // Adds a number into the data structure
    public void addNum(int num) {
        if (even) {
            // Add to large first, then move smallest from large to small
            large.offer(num);
            small.offer(large.poll());
        } else {
            // Add to small first, then move largest from small to large
            small.offer(num);
            large.offer(small.poll());
        }
        even = !even; // Flip parity
    }

    // Returns the median of current data stream
    public double findMedian() {
        if (even) {
            return (small.peek() + large.peek()) / 2.0;
        } else {
            return small.peek();
        }
    }
}
```
