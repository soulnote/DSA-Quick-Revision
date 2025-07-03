
## 🔢 1. **DATE Data Types in SQL**

### Common Types:

| Type        | Example                 | Description                         |
| ----------- | ----------------------- | ----------------------------------- |
| `DATE`      | `'2025-07-03'`          | Year, month, day                    |
| `TIME`      | `'14:30:00'`            | Hours, minutes, seconds             |
| `DATETIME`  | `'2025-07-03 14:30:00'` | Full date and time                  |
| `TIMESTAMP` | `'2025-07-03 14:30:00'` | DateTime stored with time zone info |

---

## 📅 2. **Getting Current Date and Time**

### MySQL:

```sql
SELECT CURRENT_DATE();      -- '2025-07-03'
SELECT CURRENT_TIME();      -- '14:30:00'
SELECT NOW();               -- '2025-07-03 14:30:00'
SELECT CURDATE();           -- Same as CURRENT_DATE
SELECT CURTIME();           -- Same as CURRENT_TIME
```

---

## 📤 3. **Extracting Parts from Date**

```sql
SELECT YEAR(order_date), MONTH(order_date), DAY(order_date)
FROM Orders;

-- Others:
SELECT WEEK(order_date), DAYOFWEEK(order_date), QUARTER(order_date)
```

---

## 🧮 4. **Date Arithmetic**

### Add or Subtract Intervals:

```sql
-- Add 7 days
SELECT order_date + INTERVAL 7 DAY FROM Orders;

-- Subtract 1 month
SELECT order_date - INTERVAL 1 MONTH FROM Orders;
```

### DATEDIFF:

```sql
SELECT DATEDIFF('2025-07-10', '2025-07-03'); -- returns 7
```

---

## 🧪 5. **Filtering by Date**

```sql
SELECT * FROM Orders
WHERE order_date = '2025-07-03';

-- Between two dates
SELECT * FROM Orders
WHERE order_date BETWEEN '2025-07-01' AND '2025-07-31';

-- Greater or less than
SELECT * FROM Orders
WHERE order_date >= '2025-07-01';
```

---

## 🪄 6. **Formatting Date (MySQL)**

```sql
SELECT DATE_FORMAT(order_date, '%Y-%m') AS month_year FROM Orders;
-- Output: '2025-07'
```

**Common Format Codes:**

* `%Y` → Year (e.g., 2025)
* `%m` → Month (01–12)
* `%d` → Day (01–31)
* `%H:%i:%s` → Time: hour, minute, second

---

## 🧱 7. **Group by Month/Year Example**

```sql
SELECT DATE_FORMAT(order_date, '%Y-%m') AS month, COUNT(*) as total_orders
FROM Orders
GROUP BY month;
```

---

## 🧼 8. **Common Mistakes to Avoid**

* **String vs Date Comparison**: Always use proper date format `'YYYY-MM-DD'`
* **Time Zones**: Use `TIMESTAMP` if you need timezone-sensitive data.
* **Typo in Date Formatting**: e.g., writing `'%m-%d-%Y'` instead of `'%Y-%m-%d'`.

---
