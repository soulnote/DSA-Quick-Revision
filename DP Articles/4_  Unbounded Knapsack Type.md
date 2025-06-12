
## 🔍 **Unbounded Knapsack Problem Pehchaanne ke Signs:**

---

### ✅ 1. **Har item ko **bar-bar** (multiple times) use karne ki ijazat ho**

> Problem explicitly ya implicitly bole:
> **"Aap ek item ko jitni baar chahein use kar sakte ho"**

**Example phrases**:

* “Infinite supply of coins/rods”
* “You can use an item any number of times”
* “You can cut the rod into multiple pieces of same length”
* “Repeat allowed”

---

### ✅ 2. **Target sum / capacity / total cost / weight optimize karna ho**

> Problem me aapko kuch **target banana ho** aur aapko allowed ho ki **same item ko repeat karke** uss target ko achieve karo.

**Typical Goals**:

* Min number of items
* Count of ways
* Max profit/value
* Whether target can be formed or not

---

### ✅ 3. **Knapsack state me i aur w (ya target) ho lekin i par repeat allowed ho**

* Normal 0/1 Knapsack me `dp[i][w]` me `i` next item me jaata hai (one-time use)
* Unbounded me `i` wahi par rahta hai (same item baar-baar allowed)

---

## 🧪 Examples Se Pehchaan:

---

### 🔸 1. **Coin Change (Minimum Coins)**

> Infinite supply of coins, target sum banana hai with min number of coins.

```java
dp[i][j] = min coins to make sum j using coins[0..i]
```

Same coin bar-bar use allowed hai → **Unbounded**

---

### 🔸 2. **Coin Change 2 (Number of Combinations)**

> Kitne tareeke se coins ko repeat karke target sum bana sakte ho?

---

### 🔸 3. **Rod Cutting Problem**

> Ek rod ko multiple pieces me kaat ke max profit nikaalo (piece ka length repeat ho sakta hai)

---

### 🔸 4. **Integer Break**

> Ek integer ko positive numbers me break karo — maximize product

```java
dp[n] = max(dp[n - i] * i)
```

Number i ko bar-bar use kar sakte ho → Unbounded feel

---

### 🔸 5. **Max Ribbon Cut**

> Ribbon ko cut karo given sizes me — max number of pieces.

---

## 🔁 Comparison Table:

| Feature                  | 0/1 Knapsack | Unbounded Knapsack |
| ------------------------ | ------------ | ------------------ |
| Har item ek baar         | ✅ Yes        | ❌ No               |
| Har item bar-bar         | ❌ No         | ✅ Yes              |
| Transition               | `dp[i+1][w]` | `dp[i][w]`         |
| Coin/Rod/Integer related | ❌ Not always | ✅ Mostly           |

---

## 🧠 Transition Formula:

```java
// Unbounded Knapsack
if (wt[i] <= W)
    dp[i][W] = max(dp[i][W], val[i] + dp[i][W - wt[i]]);
```

Note: `i` doesn’t increase → item repeat allowed.

---
