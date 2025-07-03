
## 🧠 Conceptual Difference

| Function  | Purpose                        | Returns                      |
| --------- | ------------------------------ | ---------------------------- |
| `COUNT()` | Counts rows or non-null values | Integer                      |
| `SUM()`   | Adds numeric values            | Integer or Decimal (numeric) |

---

## ✅ 1. **Basic Syntax**

```sql
SELECT COUNT(*) FROM Orders;
SELECT COUNT(price) FROM Orders;
SELECT SUM(price) FROM Orders;
```

---

## 🎯 2. **When to Use `COUNT()`**

### ➤ a. Count all rows (even with NULLs):

```sql
SELECT COUNT(*) FROM Users;
```

### ➤ b. Count only non-null values:

```sql
SELECT COUNT(email) FROM Users;
-- Ignores users where email IS NULL
```

### ➤ c. Count per group:

```sql
SELECT department, COUNT(*) FROM Employees GROUP BY department;
```

---

## 💰 3. **When to Use `SUM()`**

### ➤ a. Total amount, salary, price:

```sql
SELECT SUM(amount) FROM Transactions;
```

### ➤ b. Sum per group:

```sql
SELECT customer_id, SUM(total_price)
FROM Orders
GROUP BY customer_id;
```

---

## 🔄 4. **Use Together**

```sql
SELECT department,
       COUNT(*) AS total_employees,
       SUM(salary) AS total_salary
FROM Employees
GROUP BY department;
```

---

## ⚠️ 5. **Common Mistakes**

| Mistake                          | Why it’s wrong                           |
| -------------------------------- | ---------------------------------------- |
| `SUM(non_numeric_column)`        | Only works on numeric columns            |
| `COUNT(column)` expects non-NULL | NULL values are ignored                  |
| Using `SUM(*)`                   | ❌ Invalid. `SUM()` needs a numeric value |

---

## 📈 6. **Real-World Examples**

### 💵 Average Order Value per User:

```sql
SELECT user_id,
       COUNT(order_id) AS total_orders,
       SUM(order_amount) AS total_spent,
       ROUND(SUM(order_amount) / COUNT(order_id), 2) AS avg_order_value
FROM Orders
GROUP BY user_id;
```

---

## ✅ Summary: When to Use Which?

| Use Case                                | Use Function                  |
| --------------------------------------- | ----------------------------- |
| Count rows or entries                   | `COUNT(*)`                    |
| Count only non-null entries in a column | `COUNT(column)`               |
| Add up total values in a column         | `SUM(column)`                 |
| Total up numeric values per group       | `SUM(column)` with `GROUP BY` |
| Track frequency or item count           | `COUNT()`                     |

---
