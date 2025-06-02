
# 📘 Inclusion-Exclusion Principle (Hinglish me)

### Kya hota hai?

Jab hume **do ya zyada sets ke union me total unique elements count karne hote hain**, to direct add karna galat hota hai kyunki elements double-count ho sakte hain.

Is problem ko solve karne ke liye **Inclusion-Exclusion Principle** use karte hain.

---

### Basic Idea:

* **Step 1:** Sab sets ke sizes ka sum karte hain (include karte hain)
* **Step 2:** Jahan elements overlap karte hain (intersections), unko subtract karte hain (exclude karte hain)
* **Step 3:** Agar teen ya zyada sets overlap karte hain, to phir un intersections ko dobara add karna hota hai
* … aur aise hi alternate addition-subtraction chalta rehta hai.

---

### Formula:

Agar 2 sets hain: A aur B

```
|A ∪ B| = |A| + |B| - |A ∩ B|
```

Agar 3 sets hain: A, B, C

```
|A ∪ B ∪ C| = |A| + |B| + |C| 
              - |A ∩ B| - |B ∩ C| - |A ∩ C| 
              + |A ∩ B ∩ C|
```

---

### Intuition:

* Pehle sabko add kiya (toh overlap do baar count hua)
* Phir 2-set intersections subtract kiye (toh overlap wapas ek baar kam hua)
* Lekin 3-set intersections ab exclude ho gaye, to unko wapas add kiya

Isi tarah 4-set, 5-set intersections ke liye bhi pattern continue hota hai — **alternate add-subtract karte hain**.

---

### Example (3 sets):

Maan lo:

* A me 10 elements hain
* B me 8 elements hain
* C me 6 elements hain
* A ∩ B me 4 elements hain
* B ∩ C me 3 elements hain
* A ∩ C me 2 elements hain
* A ∩ B ∩ C me 1 element hain

To total unique elements in `A ∪ B ∪ C`:

```
= 10 + 8 + 6 - 4 - 3 - 2 + 1
= 24 - 9 + 1
= 16
```

---

### kab use karte hain?

* Jab problem me **sets ke multiple intersections** count karne ho
* Counting problems with constraints (e.g. "total numbers divisible by 2 or 3 or 5" etc.)
* Probability problems, combinatorics

---
Chalo ek simple example leke **Inclusion-Exclusion Principle** ko solve karte hain, step by step.

---

## Problem Statement:

**1 se 100 ke beech kitne numbers hain jo 2 ya 3 ya 5 se divisible hain?**

---

### Step 1: Define sets

* A = numbers divisible by 2
* B = numbers divisible by 3
* C = numbers divisible by 5

---

### Step 2: Find sizes of sets

* |A| = floor(100/2) = 50
* |B| = floor(100/3) = 33
* |C| = floor(100/5) = 20

---

### Step 3: Find intersections of pairs

* |A ∩ B| = divisible by LCM(2,3) = 6 → floor(100/6) = 16
* |B ∩ C| = divisible by LCM(3,5) = 15 → floor(100/15) = 6
* |A ∩ C| = divisible by LCM(2,5) = 10 → floor(100/10) = 10

---

### Step 4: Find intersection of all three

* |A ∩ B ∩ C| = divisible by LCM(2,3,5) = 30 → floor(100/30) = 3

---

### Step 5: Apply Inclusion-Exclusion Principle

```
Total = |A| + |B| + |C| 
        - |A ∩ B| - |B ∩ C| - |A ∩ C| 
        + |A ∩ B ∩ C|
```

Substitute values:

```
= 50 + 33 + 20 - 16 - 6 - 10 + 3
= 103 - 32 + 3
= 74
```

---

### Answer:

**1 se 100 ke beech 74 numbers hain jo 2 ya 3 ya 5 se divisible hain.**

---

## ✅ Java Code for this problem:

```java
public class InclusionExclusionExample {

    // Function to count numbers divisible by d up to n
    static int countDivisible(int n, int d) {
        return n / d;
    }

    // Function to compute LCM of two numbers
    static int lcm(int a, int b) {
        return (a / gcd(a, b)) * b;
    }

    // Function to compute GCD using Euclid's algorithm
    static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static void main(String[] args) {
        int n = 100;

        int count2 = countDivisible(n, 2);
        int count3 = countDivisible(n, 3);
        int count5 = countDivisible(n, 5);

        int count23 = countDivisible(n, lcm(2, 3));
        int count35 = countDivisible(n, lcm(3, 5));
        int count25 = countDivisible(n, lcm(2, 5));

        int count235 = countDivisible(n, lcm(2, lcm(3, 5)));

        int total = count2 + count3 + count5 - count23 - count35 - count25 + count235;

        System.out.println("Count of numbers divisible by 2 or 3 or 5 between 1 and " + n + " is: " + total);
    }
}
```

---

### Output:

```
Count of numbers divisible by 2 or 3 or 5 between 1 and 100 is: 74
```

