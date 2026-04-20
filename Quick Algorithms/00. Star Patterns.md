# 💡 Star Patterns
**Logic:** 
1. **Outer Loop** = Rows (`i`)
2. **Inner Loops** = Columns (Spaces + Stars) (`j`)
3. **Formula** banao: Har row mein kitne spaces aur kitne stars.

```
Template:
for (int i = 1; i <= n; i++) {
    // Spaces print karo
    for (int j = 1; j <= spaces; j++) System.out.print(" ");
    
    // Stars print karo
    for (int j = 1; j <= stars; j++) System.out.print("*");
    
    System.out.println();
}
```

---

## 📘 BASIC PATTERNS (Foundation)

### 🎯 1. Square Star Pattern
```
* * * * *
* * * * *
* * * * *
* * * * *
* * * * *
```

```java
int n = 5;
for (int i = 1; i <= n; i++) {
    for (int j = 1; j <= n; j++) {
        System.out.print("* ");
    }
    System.out.println();
}
```

---

### 🎯 2. Right Triangle (Increasing)
```
*
* *
* * *
* * * *
* * * * *
```

```java
int n = 5;
for (int i = 1; i <= n; i++) {
    for (int j = 1; j <= i; j++) {
        System.out.print("* ");
    }
    System.out.println();
}
```

---

### 🎯 3. Right Triangle (Decreasing)
```
* * * * *
* * * *
* * *
* *
*
```

```java
int n = 5;
for (int i = 1; i <= n; i++) {
    for (int j = i; j <= n; j++) {
        System.out.print("* ");
    }
    System.out.println();
}
```

---

### 🎯 4. Left Triangle (Right Aligned)
```
        *
      * *
    * * *
  * * * *
* * * * *
```

```java
int n = 5;
for (int i = 1; i <= n; i++) {
    // Spaces = n - i
    for (int j = 1; j <= n - i; j++) {
        System.out.print("  ");
    }
    // Stars = i
    for (int j = 1; j <= i; j++) {
        System.out.print("* ");
    }
    System.out.println();
}
```

---

### 🎯 5. Left Triangle (Decreasing Right Aligned)
```
* * * * *
  * * * *
    * * *
      * *
        *
```

```java
int n = 5;
for (int i = 1; i <= n; i++) {
    // Spaces = i - 1
    for (int j = 1; j <= i - 1; j++) {
        System.out.print("  ");
    }
    // Stars = n - i + 1
    for (int j = 1; j <= n - i + 1; j++) {
        System.out.print("* ");
    }
    System.out.println();
}
```

---

## 📗 PYRAMID PATTERNS

### 🎯 6. Full Pyramid
```
        *
      * * *
    * * * * *
  * * * * * * *
* * * * * * * * *
```

```java
int n = 5;
for (int i = 1; i <= n; i++) {
    // Spaces = n - i
    for (int j = 1; j <= n - i; j++) {
        System.out.print("  ");
    }
    // Stars = 2 * i - 1
    for (int j = 1; j <= 2 * i - 1; j++) {
        System.out.print("* ");
    }
    System.out.println();
}
```

---

### 🎯 7. Inverted Full Pyramid
```
* * * * * * * * *
  * * * * * * *
    * * * * *
      * * *
        *
```

```java
int n = 5;
for (int i = 1; i <= n; i++) {
    // Spaces = i - 1
    for (int j = 1; j <= i - 1; j++) {
        System.out.print("  ");
    }
    // Stars = 2 * (n - i) + 1
    for (int j = 1; j <= 2 * (n - i) + 1; j++) {
        System.out.print("* ");
    }
    System.out.println();
}
```

---

### 🎯 8. Diamond Pattern
```
        *
      * * *
    * * * * *
  * * * * * * *
* * * * * * * * *
  * * * * * * *
    * * * * *
      * * *
        *
```

```java
int n = 5;
// Upper half
for (int i = 1; i <= n; i++) {
    for (int j = 1; j <= n - i; j++) System.out.print("  ");
    for (int j = 1; j <= 2 * i - 1; j++) System.out.print("* ");
    System.out.println();
}
// Lower half
for (int i = n - 1; i >= 1; i--) {
    for (int j = 1; j <= n - i; j++) System.out.print("  ");
    for (int j = 1; j <= 2 * i - 1; j++) System.out.print("* ");
    System.out.println();
}
```

---

### 🎯 9. Hollow Diamond
```
        *
      *   *
    *       *
  *           *
*               *
  *           *
    *       *
      *   *
        *
```

```java
int n = 5;
// Upper half
for (int i = 1; i <= n; i++) {
    for (int j = 1; j <= n - i; j++) System.out.print("  ");
    for (int j = 1; j <= 2 * i - 1; j++) {
        if (j == 1 || j == 2 * i - 1) System.out.print("* ");
        else System.out.print("  ");
    }
    System.out.println();
}
// Lower half
for (int i = n - 1; i >= 1; i--) {
    for (int j = 1; j <= n - i; j++) System.out.print("  ");
    for (int j = 1; j <= 2 * i - 1; j++) {
        if (j == 1 || j == 2 * i - 1) System.out.print("* ");
        else System.out.print("  ");
    }
    System.out.println();
}
```

---

## 📙 NUMBER PATTERNS (Same Logic, Different Printing)

### 🎯 10. Number Triangle (Increasing)
```
1
1 2
1 2 3
1 2 3 4
1 2 3 4 5
```

```java
int n = 5;
for (int i = 1; i <= n; i++) {
    for (int j = 1; j <= i; j++) {
        System.out.print(j + " ");
    }
    System.out.println();
}
```

---

### 🎯 11. Number Triangle (Same Number)
```
1
2 2
3 3 3
4 4 4 4
5 5 5 5 5
```

```java
int n = 5;
for (int i = 1; i <= n; i++) {
    for (int j = 1; j <= i; j++) {
        System.out.print(i + " ");
    }
    System.out.println();
}
```

---

### 🎯 12. Floyd's Triangle
```
1
2 3
4 5 6
7 8 9 10
11 12 13 14 15
```

```java
int n = 5;
int count = 1;
for (int i = 1; i <= n; i++) {
    for (int j = 1; j <= i; j++) {
        System.out.print(count + " ");
        count++;
    }
    System.out.println();
}
```

---

### 🎯 13. Pascal's Triangle
```
        1
       1 1
      1 2 1
     1 3 3 1
    1 4 6 4 1
```

```java
int n = 5;
for (int i = 0; i < n; i++) {
    // Spaces
    for (int j = 0; j < n - i; j++) {
        System.out.print(" ");
    }
    int num = 1;
    for (int j = 0; j <= i; j++) {
        System.out.print(num + " ");
        num = num * (i - j) / (j + 1); // Pascal's triangle formula
    }
    System.out.println();
}
```

---

## 📘 HOLLOW PATTERNS

### 🎯 14. Hollow Square
```
* * * * *
*       *
*       *
*       *
* * * * *
```

```java
int n = 5;
for (int i = 1; i <= n; i++) {
    for (int j = 1; j <= n; j++) {
        if (i == 1 || i == n || j == 1 || j == n) {
            System.out.print("* ");
        } else {
            System.out.print("  ");
        }
    }
    System.out.println();
}
```

---

### 🎯 15. Hollow Right Triangle
```
*
* *
*   *
*     *
* * * * *
```

```java
int n = 5;
for (int i = 1; i <= n; i++) {
    for (int j = 1; j <= i; j++) {
        if (j == 1 || j == i || i == n) {
            System.out.print("* ");
        } else {
            System.out.print("  ");
        }
    }
    System.out.println();
}
```

---

### 🎯 16. Hollow Inverted Right Triangle
```
* * * * *
*     *
*   *
* *
*
```

```java
int n = 5;
for (int i = 1; i <= n; i++) {
    for (int j = i; j <= n; j++) {
        if (i == 1 || j == i || j == n) {
            System.out.print("* ");
        } else {
            System.out.print("  ");
        }
    }
    System.out.println();
}
```

---

### 🎯 17. Hollow Pyramid
```
        *
      *   *
    *       *
  *           *
* * * * * * * * *
```

```java
int n = 5;
for (int i = 1; i <= n; i++) {
    for (int j = 1; j <= n - i; j++) System.out.print("  ");
    for (int j = 1; j <= 2 * i - 1; j++) {
        if (j == 1 || j == 2 * i - 1 || i == n) {
            System.out.print("* ");
        } else {
            System.out.print("  ");
        }
    }
    System.out.println();
}
```

---

## 📙 SPECIAL PATTERNS

### 🎯 18. Butterfly Pattern
```
*                 *
* *             * *
* * *         * * *
* * * *     * * * *
* * * * * * * * * *
* * * * * * * * * *
* * * *     * * * *
* * *         * * *
* *             * *
*                 *
```

```java
int n = 5;
// Upper half
for (int i = 1; i <= n; i++) {
    // Left stars
    for (int j = 1; j <= i; j++) System.out.print("* ");
    // Spaces
    for (int j = 1; j <= 2 * (n - i); j++) System.out.print("  ");
    // Right stars
    for (int j = 1; j <= i; j++) System.out.print("* ");
    System.out.println();
}
// Lower half
for (int i = n; i >= 1; i--) {
    // Left stars
    for (int j = 1; j <= i; j++) System.out.print("* ");
    // Spaces
    for (int j = 1; j <= 2 * (n - i); j++) System.out.print("  ");
    // Right stars
    for (int j = 1; j <= i; j++) System.out.print("* ");
    System.out.println();
}
```

---

### 🎯 19. Sandglass Pattern
```
* * * * * * * * *
  * * * * * * *
    * * * * *
      * * *
        *
      * * *
    * * * * *
  * * * * * * *
* * * * * * * * *
```

```java
int n = 5;
// Upper half (inverted pyramid)
for (int i = 1; i <= n; i++) {
    for (int j = 1; j <= i - 1; j++) System.out.print("  ");
    for (int j = 1; j <= 2 * (n - i) + 1; j++) System.out.print("* ");
    System.out.println();
}
// Lower half (pyramid)
for (int i = n - 1; i >= 1; i--) {
    for (int j = 1; j <= i - 1; j++) System.out.print("  ");
    for (int j = 1; j <= 2 * (n - i) + 1; j++) System.out.print("* ");
    System.out.println();
}
```

---

### 🎯 20. Rhombus Pattern
```
        * * * * *
      * * * * *
    * * * * *
  * * * * *
* * * * *
```

```java
int n = 5;
for (int i = 1; i <= n; i++) {
    for (int j = 1; j <= n - i; j++) System.out.print("  ");
    for (int j = 1; j <= n; j++) System.out.print("* ");
    System.out.println();
}
```

---

### 🎯 21. Hollow Rhombus
```
        * * * * *
      *       *
    *       *
  *       *
* * * * *
```

```java
int n = 5;
for (int i = 1; i <= n; i++) {
    for (int j = 1; j <= n - i; j++) System.out.print("  ");
    for (int j = 1; j <= n; j++) {
        if (i == 1 || i == n || j == 1 || j == n) {
            System.out.print("* ");
        } else {
            System.out.print("  ");
        }
    }
    System.out.println();
}
```

---

## 📋 Star Patterns Cheat Sheet (Formula Yaad Rakho)

| Pattern | Spaces Formula | Stars Formula |
| :--- | :--- | :--- |
| **Right Triangle** | 0 | `i` |
| **Inverted Right Triangle** | 0 | `n - i + 1` |
| **Left Triangle (Right Aligned)** | `n - i` | `i` |
| **Pyramid** | `n - i` | `2 * i - 1` |
| **Inverted Pyramid** | `i - 1` | `2 * (n - i) + 1` |
| **Diamond** | Upper: `n - i`, Lower: `i` | Upper: `2i-1`, Lower: `2i-1` |
| **Hollow Conditions** | Same as solid | `if (j==1 \|\| j==stars \|\| i==1 \|\| i==n)` |
| **Butterfly Spaces** | `2 * (n - i)` | Left: `i`, Right: `i` |

---

## 🔥 Advanced Patterns (For Practice)

### 🎯 22. Zig Zag Pattern
```
    *       *       *
  *   *   *   *   *   *
*       *       *       *
```

### 🎯 23. Spiral Pattern
```
1  2  3  4  5
16 17 18 19 6
15 24 25 20 7
14 23 22 21 8
13 12 11 10 9
```

### 🎯 24. K Pattern
```
* * * * *
* * * *
* * *
* *
*
* *
* * *
* * * *
* * * * *
```

---

## 💡 Important Tips for Pattern Problems

1. **Row aur Column ka relation dhundho:**
   - Har row mein kitne elements?
   - Kya pattern symmetric hai?

2. **Spaces aur Stars alag socho:**
   - Pehle spaces ka formula banao
   - Phir stars ka formula banao

3. **Hollow patterns ke liye condition:**
   - First row / Last row
   - First column / Last column
   - Diagonals: `i == j` ya `i + j == n + 1`

4. **Number patterns ke liye:**
   - Row number print karna hai? (`i`)
   - Column number print karna hai? (`j`)
   - Counter maintain karna hai?

5. **Dry Run karna seekho:**
   - `n = 3` le kar pen-paper pe trace karo
   - Formula verify karo
