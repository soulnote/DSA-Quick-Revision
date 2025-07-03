
## 🔢 1. **`ROUND()` Function in SQL**

### ✅ Syntax:

```sql
ROUND(number, decimal_places)
```

* `number`: The numeric value you want to round.
* `decimal_places`: Optional. Number of digits to keep after the decimal point.

---

### 📘 Examples:

```sql
SELECT ROUND(123.4567, 2);   -- 123.46
SELECT ROUND(123.4567, 0);   -- 123
SELECT ROUND(123.5567, 1);   -- 123.6
SELECT ROUND(-123.456, 1);   -- -123.5
```

If you omit the second argument:

```sql
SELECT ROUND(123.4567);      -- 123
```

---

## 🧮 2. **Other Related Functions**

### 🔹 `CEIL()` / `CEILING()`: Always rounds *up* to next integer

```sql
SELECT CEIL(123.1);    -- 124
SELECT CEIL(-123.1);   -- -123
```

### 🔹 `FLOOR()`: Always rounds *down* to previous integer

```sql
SELECT FLOOR(123.9);   -- 123
SELECT FLOOR(-123.1);  -- -124
```

### 🔹 `TRUNCATE(number, decimals)`: Cuts off digits (does NOT round)

```sql
SELECT TRUNCATE(123.4567, 2);  -- 123.45
SELECT TRUNCATE(-123.4567, 0); -- -123
```

---

## 💼 3. **Use Cases in Real Queries**

### a. **Rounding average to 2 decimals**

```sql
SELECT department, ROUND(AVG(salary), 2) AS avg_salary
FROM Employees
GROUP BY department;
```

### b. **Percentage calculation with rounding**

```sql
SELECT ROUND(100 * confirmed / total, 2) AS confirmation_rate
FROM Stats;
```

### c. **Rounding currency**

```sql
SELECT ROUND(price, 2) AS rounded_price FROM Products;
```

---

## ⚠️ 4. **Precision and Pitfalls**

* `ROUND()` uses traditional rounding (5 and above rounds up).
* `TRUNCATE()` simply removes digits — **no rounding**.
* Use `CAST()` or `FORMAT()` for **string representation with fixed decimal places**.

---
