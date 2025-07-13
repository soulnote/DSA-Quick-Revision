# Implementation in java

Binary search is an efficient algorithm for finding an element in a sorted array or list. It operates in \(O(\log n)\) time complexity, making it much faster than a linear search, especially for large datasets.

Here’s how binary search works:

1. **Initialization**: Start with two pointers, `left` and `right`, representing the range of the search space (initially, the entire array).
2. **Middle Element**: Calculate the middle index of the current search space.
3. **Comparison**: Compare the target value with the middle element:
   - If the target is equal to the middle element, return the index of the middle element.
   - If the target is less than the middle element, narrow the search to the left half of the array.
   - If the target is greater than the middle element, narrow the search to the right half of the array.
4. **Repeat**: Continue this process until the search space is exhausted (i.e., `left` exceeds `right`).

### Binary Search Algorithm in Java

#### 1. Iterative Approach

```java
public class BinarySearch {
    public static int binarySearch(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (array[mid] == target) {
                return mid; // Target found
            }

            if (array[mid] < target) {
                left = mid + 1; // Search in the right half
            } else {
                right = mid - 1; // Search in the left half
            }
        }

        return -1; // Target not found
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int target = 7;

        int result = binarySearch(array, target);
        if (result != -1) {
            System.out.println("Element found at index: " + result);
        } else {
            System.out.println("Element not found in the array.");
        }
    }
}
```

**Explanation**: This method iteratively narrows down the search space by updating the `left` and `right` pointers based on comparisons.

#### 2. Recursive Approach

```java
public class BinarySearchRecursive {
    public static int binarySearch(int[] array, int target, int left, int right) {
        if (left > right) {
            return -1; // Target not found
        }

        int mid = left + (right - left) / 2;

        if (array[mid] == target) {
            return mid; // Target found
        }

        if (array[mid] < target) {
            return binarySearch(array, target, mid + 1, right); // Search in the right half
        } else {
            return binarySearch(array, target, left, mid - 1); // Search in the left half
        }
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int target = 7;

        int result = binarySearch(array, target, 0, array.length - 1);
        if (result != -1) {
            System.out.println("Element found at index: " + result);
        } else {
            System.out.println("Element not found in the array.");
        }
    }
}
```

**Explanation**: This method recursively divides the search space. The base case handles when the search space is empty, and recursive calls adjust the search range.

### Key Points:
- **Precondition**: The array must be sorted for binary search to work correctly.
- **Efficiency**: Binary search is very efficient for large datasets due to its logarithmic time complexity.
- **Edge Cases**: Handle edge cases such as empty arrays or arrays with only one element.

Binary search is a fundamental algorithm with applications in various scenarios where fast lookups are required in sorted data.

# Interview questions and answers

### 1. **Basic Binary Search**

**Question**: Implement a binary search algorithm to find the index of a target value in a sorted array.

**Answer**:
```java
public class BinarySearch {
    public static int binarySearch(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (array[mid] == target) {
                return mid;
            } else if (array[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int target = 7;
        System.out.println("Index of target: " + binarySearch(array, target));
    }
}
```

---

### 2. **Binary Search in a Rotated Array**

**Question**: Implement binary search in a rotated sorted array. For example, if the array is `[4, 5, 6, 7, 0, 1, 2]`, and the target is `1`, find its index.

**Answer**:
```java
public class RotatedArrayBinarySearch {
    public static int search(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (array[mid] == target) {
                return mid;
            }

            if (array[left] <= array[mid]) {
                if (array[left] <= target && target < array[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                if (array[mid] < target && target <= array[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] array = {4, 5, 6, 7, 0, 1, 2};
        int target = 1;
        System.out.println("Index of target: " + search(array, target));
    }
}
```

---

### 3. **Find the First Occurrence of a Target Value**

**Question**: Modify the binary search algorithm to find the first occurrence of a target value in a sorted array.

**Answer**:
```java
public class FirstOccurrenceBinarySearch {
    public static int findFirstOccurrence(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (array[mid] == target) {
                result = mid;
                right = mid - 1;
            } else if (array[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 2, 2, 3, 4};
        int target = 2;
        System.out.println("First occurrence of target: " + findFirstOccurrence(array, target));
    }
}
```

---

### 4. **Find the Last Occurrence of a Target Value**

**Question**: Modify the binary search algorithm to find the last occurrence of a target value in a sorted array.

**Answer**:
```java
public class LastOccurrenceBinarySearch {
    public static int findLastOccurrence(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (array[mid] == target) {
                result = mid;
                left = mid + 1;
            } else if (array[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 2, 2, 3, 4};
        int target = 2;
        System.out.println("Last occurrence of target: " + findLastOccurrence(array, target));
    }
}
```

---

### 5. **Find the Closest Element to the Target**

**Question**: Implement binary search to find the closest element to a target value in a sorted array.

**Answer**:
```java
public class ClosestElementBinarySearch {
    public static int findClosest(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;

        if (target <= array[left]) return array[left];
        if (target >= array[right]) return array[right];

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (array[mid] == target) {
                return array[mid];
            } else if (array[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        // Find the closest element
        if (Math.abs(array[left] - target) < Math.abs(array[right] - target)) {
            return array[left];
        } else {
            return array[right];
        }
    }

    public static void main(String[] args) {
        int[] array = {1, 3, 8, 10, 15};
        int target = 12;
        System.out.println("Closest element to target: " + findClosest(array, target));
    }
}
```

---

### 6. **Count Occurrences of a Target Value**

**Question**: Use binary search to count the number of occurrences of a target value in a sorted array.

**Answer**:
```java
public class CountOccurrences {
    public static int countOccurrences(int[] array, int target) {
        return findLastOccurrence(array, target) - findFirstOccurrence(array, target) + 1;
    }

    private static int findFirstOccurrence(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (array[mid] == target) {
                result = mid;
                right = mid - 1;
            } else if (array[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }

    private static int findLastOccurrence(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (array[mid] == target) {
                result = mid;
                left = mid + 1;
            } else if (array[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 2, 2, 3, 4};
        int target = 2;
        System.out.println("Count of target: " + countOccurrences(array, target));
    }
}
```

---

### 7. **Find the Smallest Element Greater Than or Equal to Target**

**Question**: Given a sorted array, find the smallest element greater than or equal to a target value.

**Answer**:
```java
public class SmallestGreaterThanOrEqual {
    public static int findSmallestGreaterThanOrEqual(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (array[mid] >= target) {
                result = array[mid];
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] array = {1, 3, 5, 7, 9};
        int target = 6;
        System.out.println("Smallest element >= target: " + findSmallestGreaterThanOrEqual(array, target));
    }
}
```

---

### 8. **Find the Largest Element Less Than or Equal to Target**

**Question**: Given a sorted array, find the largest element less than or equal to a target value.

**Answer**:
```java
public class LargestLessThanOrEqual {
    public static int findLargestLessThanOrEqual(int[] array, int target) {
        int

 left = 0;
        int right = array.length - 1;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (array[mid] <= target) {
                result = array[mid];
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] array = {1, 3, 5, 7, 9};
        int target = 6;
        System.out.println("Largest element <= target: " + findLargestLessThanOrEqual(array, target));
    }
}
```

---

### 9. **Find the Peak Element in a Bitonic Array**

**Question**: A bitonic array is an array that is initially increasing and then decreasing. Find the peak element using binary search.

**Answer**:
```java
public class BitonicArrayPeak {
    public static int findPeak(int[] array) {
        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if ((mid == 0 || array[mid - 1] <= array[mid]) &&
                (mid == array.length - 1 || array[mid + 1] <= array[mid])) {
                return array[mid];
            }

            if (mid > 0 && array[mid - 1] > array[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return -1; // This line should never be reached
    }

    public static void main(String[] args) {
        int[] array = {1, 3, 8, 12, 4, 2};
        System.out.println("Peak element: " + findPeak(array));
    }
}
```

---

### 10. **Search in an Infinite Sorted Array**

**Question**: Given an infinite sorted array, implement a binary search algorithm to find the target value. Assume you can only access elements by index and there’s no way to determine the array’s length.

**Answer**:
```java
public class InfiniteArrayBinarySearch {
    public static int searchInInfiniteArray(int[] array, int target) {
        int index = 1;
        while (true) {
            try {
                if (array[index] == target) {
                    return index;
                } else if (array[index] > target) {
                    break;
                }
                index *= 2;
            } catch (ArrayIndexOutOfBoundsException e) {
                break;
            }
        }

        return binarySearch(array, target, index / 2, index);
    }

    private static int binarySearch(int[] array, int target, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2;

            try {
                if (array[mid] == target) {
                    return mid;
                } else if (array[mid] < target) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                right = mid - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int target = 7;
        System.out.println("Index of target: " + searchInInfiniteArray(array, target));
    }
}
```

---

These questions cover a variety of scenarios where binary search can be applied, from basic implementations to more advanced variations. Understanding and practicing these will help you handle binary search-related problems effectively in interviews.
