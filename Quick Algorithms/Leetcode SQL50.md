
### Topic 1: SELECT & Basic Filtering (Basic)
Yeh sabse basic hai. Data ko table se chaantna aur specific columns dekhna.

**Important Clauses:** `SELECT`, `WHERE`, `AND`, `OR`, `NOT`, `IN`, `BETWEEN`, `LIKE`.

```sql
-- Problem: Recyclable and Low Fat Products
-- "product" table se un products ka naam batao jo Low Fat (Y) aur Recyclable (Y) dono hain.
SELECT product_id
FROM Products
WHERE low_fats = 'Y' AND recyclable = 'Y';
```

###  Topic 2: Basic Joins (Medium)
Jab data do ya do se zyada tables mein bikhra ho. Foreign Key se aapas mein juda hota hai.

**Important Types:**
- **INNER JOIN:** Sirf matching rows dono tables se.
- **LEFT JOIN:** Left table ki saari rows + Right table ki matching rows (nahi mili to NULL) .
- **SELF JOIN:** Table apne aap se join karna (e.g., Employees aur Managers) .

```sql
-- Problem: Replace Employee ID with Unique Identifier
-- Employee aur EmployeeUNI table join karo. Unique ID nahi hai to NULL dikhao.
SELECT eu.unique_id, e.name
FROM Employees e
LEFT JOIN EmployeeUNI eu ON e.id = eu.id;
```

###  Topic 3: Aggregate Functions & GROUP BY (Medium)
Data ko group karke uspe mathematical calculation karna (Jodna, Ginti karna, Average). `GROUP BY` ke saath use hota hai. Group hone ke baad filter lagana ho to **`HAVING`** use hota hai, `WHERE` nahi .

```sql
-- Problem: Customer Who Visited but Did Not Make Any Transactions
-- Visits table se un customers ki count karo jinki transaction_id NULL hai.
SELECT v.customer_id, COUNT(*) AS count_no_trans
FROM Visits v
LEFT JOIN Transactions t ON v.visit_id = t.visit_id
WHERE t.transaction_id IS NULL
GROUP BY v.customer_id;
```

###  Topic 4: Window Functions (Medium/Hard) 🔥
Interview ka sabse favorite topic. Bina rows group kiye ranking aur running total nikalna. `OVER()` clause define karta hai window ka size. `PARTITION BY` group banata hai, `ORDER BY` order set karta hai .

```sql
-- Problem: Department Top Three Salaries
-- Har department mein top 3 salary earners kaun hain?
-- Trick: DENSE_RANK use karo taaki same salary ko same rank mile.
SELECT Department, Employee, Salary
FROM (
    SELECT d.name AS Department, e.name AS Employee, e.salary AS Salary,
           DENSE_RANK() OVER (PARTITION BY e.departmentId ORDER BY e.salary DESC) AS rnk
    FROM Employee e
    JOIN Department d ON e.departmentId = d.id
) AS Ranked
WHERE rnk <= 3;
```

### Topic 5: Subqueries & CTEs (Advanced)
- **Subquery:** Query ke andar query. `WHERE IN (SELECT ...)`.
- **CTE (Common Table Expression):** Temporary table jaisa, `WITH` keyword se shuru. Code clean aur readable banata hai, specially recursion mein .

```sql
-- Problem: Restaurant Growth (Moving Average)
-- CTE use karke pehle daily sales nikalo, phir 7-day moving average lagao.
WITH DailySales AS (
    SELECT visited_on, SUM(amount) AS daily_sum
    FROM Customer
    GROUP BY visited_on
)
SELECT visited_on, amount, average_amount
FROM (
    SELECT visited_on,
           SUM(daily_sum) OVER (ORDER BY visited_on ROWS BETWEEN 6 PRECEDING AND CURRENT ROW) AS amount,
           ROUND(AVG(daily_sum) OVER (ORDER BY visited_on ROWS BETWEEN 6 PRECEDING AND CURRENT ROW), 2) AS average_amount
    FROM DailySales
) t
WHERE visited_on >= (SELECT MIN(visited_on) + INTERVAL 6 DAY FROM DailySales);
```

###  Topic 6: String Functions & Regex (Advanced)
Text data ko saaf karna ya search karna.
- **CONCAT, UPPER, LOWER, SUBSTRING**: Text ko todna-marodna .
- **REGEXP (or LIKE)**: Pattern match karna. Jaise emails valid hain ya nahi .

```sql
-- Problem: Fix Names in a Table
-- Naam ka pehla akshar Capital (A) aur baaki chhota (lice) -> Alice.
SELECT user_id, CONCAT(UPPER(LEFT(name, 1)), LOWER(SUBSTRING(name, 2))) AS name
FROM Users
ORDER BY user_id;
```

###  Exam Hall Strategy (Hinglish Tips)

1.  **Problem Padho Dhyaan Se:** Output mein kaunse columns maange hain? Koi **NULL** condition to nahi?
2.  **Join Pehchano:** Agar ek se zyada tables hain, to `LEFT JOIN` ya `INNER JOIN`? Zyadatar **Foreign Key** `_id` column se match hogi .
3.  **Date Logic:** Dates ke saath `DATEDIFF` ya `DATE_ADD` ka dhyaan rakho .
4.  **Duplicate Data:** `GROUP BY` lagane se pehle socho ki `DISTINCT` ki zaroorat to nahi?

# SQL50
### 📚 Category 1: SELECT (Easy)
Database se data retrieve karne ke basic problems.

**1. Recyclable and Low Fat Products**
- **Task:** `Products` table se un products ka `product_id` dhundho jo **Low Fat** (`low_fats = 'Y'`) aur **Recyclable** (`recyclable = 'Y'`) dono ho.
- **Solution:**
```sql
SELECT product_id
FROM Products
WHERE low_fats = 'Y' AND recyclable = 'Y';
```

**2. Find Customer Referee**
- **Task:** `Customer` table mein `referee_id` **2 nahi hai** ya `NULL` hai, un customers ke names batao.
- **Solution:** (Note: SQL mein `NULL` check ke liye `IS NULL` use hota hai)
```sql
SELECT name
FROM Customer
WHERE referee_id != 2 OR referee_id IS NULL;
```

**3. Big Countries**
- **Task:** Un countries ka naam, population aur area batao jinka area kam se kam **3 million (3000000)** ho ya population kam se kam **25 million (25000000)** ho.
- **Solution:**
```sql
SELECT name, population, area
FROM World
WHERE area >= 3000000 OR population >= 25000000;
```

**4. Article Views I**
- **Task:** `Views` table mein un authors ka `author_id` dhundho jinhone apni hi article ko view kiya ho (`author_id = viewer_id`). Result unique hone chahiye aur `author_id` ke ascending order mein sorted ho.
- **Solution:**
```sql
SELECT DISTINCT author_id AS id
FROM Views
WHERE author_id = viewer_id
ORDER BY id ASC;
```

**5. Invalid Tweets**
- **Task:** `Tweets` table mein un tweets ka `tweet_id` dhundho jinka `content` **15 characters se zyada** lamba ho.
- **Solution:**
```sql
SELECT tweet_id
FROM Tweets
WHERE LENGTH(content) > 15;
```

### 📚 Category 2: Basic Joins (Easy/Medium)
Do ya do se zyada tables ko aapas mein join karna seekho.

**6. Replace Employee ID With The Unique Identifier**
- **Task:** `Employees` aur `EmployeeUNI` table ko join karo. Employee ka `unique_id` dikhao. Agar kisi employee ke liye unique ID nahi hai to **NULL** dikhao.
- **Solution:**
```sql
SELECT eu.unique_id, e.name
FROM Employees e
LEFT JOIN EmployeeUNI eu ON e.id = eu.id;
```

**7. Product Sales Analysis I**
- **Task:** `Sales` aur `Product` table se har product ka `product_name` aur sales ka `year` aur `price` fetch karo.
- **Solution:**
```sql
SELECT p.product_name, s.year, s.price
FROM Sales s
INNER JOIN Product p ON s.product_id = p.product_id;
```

**8. Customer Who Visited but Did Not Make Any Transactions**
- **Task:** Un customers ka `customer_id` aur unki **number of visits** count karo jinhone visit to kiya lekin koi transaction nahi ki (yani `transaction_id` NULL hai).
- **Solution:**
```sql
SELECT v.customer_id, COUNT(*) AS count_no_trans
FROM Visits v
LEFT JOIN Transactions t ON v.visit_id = t.visit_id
WHERE t.transaction_id IS NULL
GROUP BY v.customer_id;
```

**9. Rising Temperature**
- **Task:** Un dates ka `id` batao jinka temperature pichle din (`yesterday`) ke temperature se zyada tha.
- **Solution:**
```sql
SELECT w1.id
FROM Weather w1
JOIN Weather w2 ON w1.recordDate = DATE_ADD(w2.recordDate, INTERVAL 1 DAY)
WHERE w1.temperature > w2.temperature;
```

**10. Average Time of Process per Machine**
- **Task:** Factory machine ka data diya hai (`Activity` table). Har machine ke liye **average processing time** calculate karo (`end` timestamp - `start` timestamp ka average, rounded to 3 decimal places).
- **Solution:**
```sql
SELECT a1.machine_id, 
       ROUND(AVG(a2.timestamp - a1.timestamp), 3) AS processing_time
FROM Activity a1
JOIN Activity a2 ON a1.machine_id = a2.machine_id 
                AND a1.process_id = a2.process_id
                AND a1.activity_type = 'start' 
                AND a2.activity_type = 'end'
GROUP BY a1.machine_id;
```

**11. Employee Bonus**
- **Task:** `Employee` aur `Bonus` table se employee `name` aur `bonus` amount fetch karo. Un employees ko bhi include karo jinka bonus **1000 se kam** ho ya **bonus record NULL** ho.
- **Solution:**
```sql
SELECT e.name, b.bonus
FROM Employee e
LEFT JOIN Bonus b ON e.empId = b.empId
WHERE b.bonus < 1000 OR b.bonus IS NULL;
```

**12. Students and Examinations**
- **Task:** Har student ka naam aur unke dwara diye gaye har subject ke exams ki count nikalo. Agar kisi subject ka exam nahi diya to **0** count dikhao. Result ko `student_id` aur `subject_name` se order karo.
- **Solution:**
```sql
SELECT s.student_id, s.student_name, sub.subject_name, 
       COUNT(e.subject_name) AS attended_exams
FROM Students s
CROSS JOIN Subjects sub
LEFT JOIN Examinations e ON s.student_id = e.student_id 
                        AND sub.subject_name = e.subject_name
GROUP BY s.student_id, s.student_name, sub.subject_name
ORDER BY s.student_id, sub.subject_name;
```

**13. Managers with at Least 5 Direct Reports**
- **Task:** Un managers ke names batao jo kam se kam **5 direct reports** (employees) ko manage karte hain.
- **Solution:**
```sql
SELECT m.name
FROM Employee e
JOIN Employee m ON e.managerId = m.id
GROUP BY m.id, m.name
HAVING COUNT(e.id) >= 5;
```

**14. Confirmation Rate**
- **Task:** Har user ka **confirmation rate** nikalo. Confirmation rate = `confirmed` actions ki ginti / total actions (2 decimal places tak round karo).
- **Solution:**
```sql
SELECT s.user_id, 
       ROUND(AVG(CASE WHEN c.action = 'confirmed' THEN 1 ELSE 0 END), 2) AS confirmation_rate
FROM Signups s
LEFT JOIN Confirmations c ON s.user_id = c.user_id
GROUP BY s.user_id;
```

### 📚 Category 3: Basic Aggregate Functions (Medium)
`GROUP BY` ke saath aggregate functions ka use karna seekho.

**15. Not Boring Movies**
- **Task:** Un movies ki list banao jinka `id` **odd** ho aur `description` **"boring"** na ho. Result ko `rating` ke **descending** order mein sort karo.
- **Solution:**
```sql
SELECT *
FROM Cinema
WHERE id % 2 = 1 AND description != 'boring'
ORDER BY rating DESC;
```

**16. Average Selling Price**
- **Task:** `Prices` aur `UnitsSold` table se har product ka **average selling price** calculate karo (2 decimal places tak round). Agar koi product sell nahi hua to price 0 maano.
- **Solution:**
```sql
SELECT p.product_id, 
       IFNULL(ROUND(SUM(p.price * u.units) / SUM(u.units), 2), 0) AS average_price
FROM Prices p
LEFT JOIN UnitsSold u ON p.product_id = u.product_id 
                     AND u.purchase_date BETWEEN p.start_date AND p.end_date
GROUP BY p.product_id;
```

**17. Project Employees I**
- **Task:** `Project` aur `Employee` table se har project ka **average experience years** calculate karo jo us project mein kaam kar rahe hain. (2 decimal places tak round).
- **Solution:**
```sql
SELECT p.project_id, ROUND(AVG(e.experience_years), 2) AS average_years
FROM Project p
JOIN Employee e ON p.employee_id = e.employee_id
GROUP BY p.project_id;
```

**18. Percentage of Users Attended a Contest**
- **Task:** Har contest ka registration percentage nikalo. Percentage = (us contest mein registered users / total users) * 100. **2 decimal places** tak round karo. Result ko `percentage` descending, phir `contest_id` ascending se order karo.
- **Solution:**
```sql
SELECT r.contest_id, 
       ROUND(COUNT(r.user_id) / (SELECT COUNT(*) FROM Users) * 100, 2) AS percentage
FROM Register r
GROUP BY r.contest_id
ORDER BY percentage DESC, r.contest_id ASC;
```

**19. Queries Quality and Percentage**
- **Task:** `Queries` table mein har query ke liye `quality` (`rating / position` ka average) aur `poor_query_percentage` (`rating < 3` ka percentage) nikalo. Dono ko 2 decimal places tak round karo.
- **Solution:**
```sql
SELECT query_name,
       ROUND(AVG(rating / position), 2) AS quality,
       ROUND(SUM(CASE WHEN rating < 3 THEN 1 ELSE 0 END) / COUNT(*) * 100, 2) AS poor_query_percentage
FROM Queries
GROUP BY query_name;
```

**20. Monthly Transactions I**
- **Task:** Har month aur country ke liye transactions ki count, total amount, aur **approved transactions** ki count aur total amount nikalo.
- **Solution:**
```sql
SELECT DATE_FORMAT(trans_date, '%Y-%m') AS month, country,
       COUNT(*) AS trans_count,
       SUM(CASE WHEN state = 'approved' THEN 1 ELSE 0 END) AS approved_count,
       SUM(amount) AS trans_total_amount,
       SUM(CASE WHEN state = 'approved' THEN amount ELSE 0 END) AS approved_total_amount
FROM Transactions
GROUP BY month, country;
```

**21. Immediate Food Delivery II**
- **Task:** `Delivery` table mein, agar `order_date` aur `customer_pref_delivery_date` same hai to use **immediate** order bolte hain. Pehle order (minimum `order_date`) per customer ke hisaab se, **immediate orders ka percentage** nikalo (2 decimal places).
- **Solution:**
```sql
WITH FirstOrders AS (
    SELECT customer_id, MIN(order_date) AS first_order_date
    FROM Delivery
    GROUP BY customer_id
)
SELECT ROUND(AVG(CASE WHEN d.order_date = d.customer_pref_delivery_date THEN 1 ELSE 0 END) * 100, 2) AS immediate_percentage
FROM Delivery d
JOIN FirstOrders f ON d.customer_id = f.customer_id AND d.order_date = f.first_order_date;
```

**22. Game Play Analysis IV**
- **Task:** `Activity` table mein, pehli baar login karne ke baad **agle din** dobara login karne wale players ka fraction nikalo (2 decimal places).
- **Solution:**
```sql
WITH FirstLogins AS (
    SELECT player_id, MIN(event_date) AS first_login
    FROM Activity
    GROUP BY player_id
)
SELECT ROUND(COUNT(DISTINCT a.player_id) / (SELECT COUNT(DISTINCT player_id) FROM Activity), 2) AS fraction
FROM Activity a
JOIN FirstLogins f ON a.player_id = f.player_id 
WHERE a.event_date = DATE_ADD(f.first_login, INTERVAL 1 DAY);
```

### 📚 Category 4: Sorting and Grouping (Medium)
Data ko group karna aur sort karna.

**23. Number of Unique Subjects Taught by Each Teacher**
- **Task:** `Teacher` table se har teacher ke liye **unique subjects** ki count nikalo jo woh padhate hain.
- **Solution:**
```sql
SELECT teacher_id, COUNT(DISTINCT subject_id) AS cnt
FROM Teacher
GROUP BY teacher_id;
```

**24. User Activity for the Past 30 Days I**
- **Task:** `Activity` table mein `2019-07-27` se pichle 30 dinon mein active users ki daily count nikalo (including `2019-07-27`).
- **Solution:**
```sql
SELECT activity_date AS day, COUNT(DISTINCT user_id) AS active_users
FROM Activity
WHERE activity_date BETWEEN DATE_SUB('2019-07-27', INTERVAL 29 DAY) AND '2019-07-27'
GROUP BY activity_date;
```

**25. Product Sales Analysis III**
- **Task:** `Sales` table se har product ka **pehla year** (minimum year) jisme woh becha gaya tha, aur uss year mein bechi gayi `quantity` aur `price` batao.
- **Solution:**
```sql
SELECT s.product_id, s.year AS first_year, s.quantity, s.price
FROM Sales s
INNER JOIN (
    SELECT product_id, MIN(year) AS min_year
    FROM Sales
    GROUP BY product_id
) min_sales ON s.product_id = min_sales.product_id AND s.year = min_sales.min_year;
```

**26. Classes More Than 5 Students**
- **Task:** `Courses` table se un classes ka naam batao jisme kam se kam **5 students** enrolled hain.
- **Solution:**
```sql
SELECT class
FROM Courses
GROUP BY class
HAVING COUNT(student) >= 5;
```

**27. Find Followers Count**
- **Task:** `Followers` table se har user ke liye followers ki count nikalo. Result ko `user_id` se ascending order mein karo.
- **Solution:**
```sql
SELECT user_id, COUNT(follower_id) AS followers_count
FROM Followers
GROUP BY user_id
ORDER BY user_id ASC;
```

**28. Biggest Single Number**
- **Task:** `MyNumbers` table mein aisi **sabse badi number** dhundho jo sirf **ek baar** appear karti ho. Agar aisa koi number nahi hai to **NULL** return karo.
- **Solution:**
```sql
SELECT MAX(num) AS num
FROM MyNumbers
WHERE num IN (
    SELECT num
    FROM MyNumbers
    GROUP BY num
    HAVING COUNT(*) = 1
);
```

**29. Customers Who Bought All Products**
- **Task:** `Customer` table se un customers ka `customer_id` dhundho jinhone `Product` table ke **saare products** khareede hain.
- **Solution:**
```sql
SELECT customer_id
FROM Customer
GROUP BY customer_id
HAVING COUNT(DISTINCT product_key) = (SELECT COUNT(*) FROM Product);
```

### 📚 Category 5: Advanced Select and Joins (Medium/Hard)
Complex joins aur advanced SQL techniques.

**30. The Number of Employees Which Report to Each Employee**
- **Task:** `Employees` table se har manager ke liye `employee_id`, `name`, **number of direct reports**, aur **average age of reports** (nearest integer) nikalo.
- **Solution:**
```sql
SELECT m.employee_id, m.name, 
       COUNT(e.employee_id) AS reports_count, 
       ROUND(AVG(e.age)) AS average_age
FROM Employees e
JOIN Employees m ON e.reports_to = m.employee_id
GROUP BY m.employee_id, m.name
ORDER BY m.employee_id;
```

**31. Primary Department for Each Employee**
- **Task:** `Employee` table mein, agar kisi employee ka `primary_flag` 'Y' hai to woh department uski primary department hai. Agar kisi employee ki sirf ek hi department hai to woh primary department maani jaegi, chahe flag 'N' ho. Har employee ki primary department batao.
- **Solution:**
```sql
SELECT employee_id, department_id
FROM Employee
WHERE primary_flag = 'Y' 
   OR employee_id IN (
       SELECT employee_id 
       FROM Employee 
       GROUP BY employee_id 
       HAVING COUNT(department_id) = 1
   );
```

**32. Triangle Judgement**
- **Task:** `Triangle` table mein diye gaye `x, y, z` lengths se triangle ban sakta hai ya nahi (`Yes`/`No`). **Triangle Inequality Theorem:** `x + y > z` aur `x + z > y` aur `y + z > x`.
- **Solution:**
```sql
SELECT x, y, z,
       CASE WHEN x + y > z AND x + z > y AND y + z > x THEN 'Yes' ELSE 'No' END AS triangle
FROM Triangle;
```

**33. Consecutive Numbers**
- **Task:** `Logs` table mein un numbers ko dhundho jo **kam se kam teen baar lagataar** (consecutive) aaye hain.
- **Solution:**
```sql
SELECT DISTINCT l1.num AS ConsecutiveNums
FROM Logs l1
JOIN Logs l2 ON l1.id = l2.id - 1 AND l1.num = l2.num
JOIN Logs l3 ON l2.id = l3.id - 1 AND l2.num = l3.num;
```

**34. Product Price at a Given Date**
- **Task:** `Products` table mein diye gaye products ki price change hoti rehti hai. `2019-08-16` ko har product ki kya price thi? Agar uss din koi price change record nahi hai to default price `10` maano.
- **Solution:**
```sql
SELECT p.product_id, 
       COALESCE((
           SELECT new_price 
           FROM Products p2 
           WHERE p2.product_id = p.product_id AND change_date <= '2019-08-16' 
           ORDER BY change_date DESC LIMIT 1
       ), 10) AS price
FROM (SELECT DISTINCT product_id FROM Products) p;
```

**35. Last Person to Fit in the Bus**
- **Task:** `Queue` table mein log bus mein chadh rahe hain (`turn` order). Bus ki capacity weight limit **1000 kg** hai. Aakhri person ka `person_name` batao jo bus mein chadha bina weight limit cross kiye.
- **Solution:**
```sql
WITH RunningTotal AS (
    SELECT person_name, weight, turn,
           SUM(weight) OVER (ORDER BY turn) AS total_weight
    FROM Queue
)
SELECT person_name
FROM RunningTotal
WHERE total_weight <= 1000
ORDER BY turn DESC
LIMIT 1;
```

**36. Count Salary Categories**
- **Task:** `Accounts` table mein salaries hain. Teen categories banao: **"Low Salary"** (income < 20000), **"Average Salary"** (20000 <= income <= 50000), aur **"High Salary"** (income > 50000). Har category mein kitne accounts hain?
- **Solution:**
```sql
SELECT 'Low Salary' AS category, COUNT(*) AS accounts_count FROM Accounts WHERE income < 20000
UNION ALL
SELECT 'Average Salary', COUNT(*) FROM Accounts WHERE income BETWEEN 20000 AND 50000
UNION ALL
SELECT 'High Salary', COUNT(*) FROM Accounts WHERE income > 50000;
```

### 📚 Category 6: Subqueries (Medium/Hard)
Query ke andar query likhna seekho.

**37. Employees Whose Manager Left the Company**
- **Task:** `Employees` table se un employees ki list banao jinka `manager_id` **NULL nahi hai**, lekin woh manager ab company mein nahi hai (`manager_id` `Employees` table mein `employee_id` ke roop mein nahi hai). In employees ko `employee_id` se ascending order karo.
- **Solution:**
```sql
SELECT employee_id
FROM Employees
WHERE salary < 30000 
  AND manager_id IS NOT NULL 
  AND manager_id NOT IN (SELECT employee_id FROM Employees)
ORDER BY employee_id;
```

**38. Exchange Seats**
- **Task:** `Seat` table mein alternate students ki seat ids swap karni hai. Agar students ki ginti odd hai to last student ki seat id change nahi hogi.
- **Solution:**
```sql
SELECT 
    CASE 
        WHEN MOD(id, 2) = 1 AND id = (SELECT MAX(id) FROM Seat) THEN id
        WHEN MOD(id, 2) = 1 THEN id + 1
        ELSE id - 1
    END AS id, student
FROM Seat
ORDER BY id;
```

**39. Movie Rating**
- **Task:** Do cheezein dhundhni hain: (1) Woh user jisne sabse zyada movies ko rating di hai (tie hone par lexicographically chhota naam). (2) February 2020 mein sabse zyada average rating wali movie (tie pe lexicographically chhota title). Result mein dono ko `UNION ALL` karke dikhana hai.
- **Solution:**
```sql
(SELECT u.name AS results
 FROM Users u
 JOIN MovieRating mr ON u.user_id = mr.user_id
 GROUP BY u.user_id, u.name
 ORDER BY COUNT(*) DESC, u.name ASC LIMIT 1)
UNION ALL
(SELECT m.title AS results
 FROM Movies m
 JOIN MovieRating mr ON m.movie_id = mr.movie_id
 WHERE DATE_FORMAT(mr.created_at, '%Y-%m') = '2020-02'
 GROUP BY m.movie_id, m.title
 ORDER BY AVG(mr.rating) DESC, m.title ASC LIMIT 1);
```

**40. Restaurant Growth**
- **Task:** `Customer` table mein daily payments hain. Har din ke liye pichle 7 dinon (including current day) ka **moving average amount** aur **total amount** nikalo. Average 2 decimal places tak round karo. Result `visited_on` se ascending order mein ho.
- **Solution:**
```sql
WITH DailyTotals AS (
    SELECT visited_on, SUM(amount) AS daily_total
    FROM Customer
    GROUP BY visited_on
),
MovingCalc AS (
    SELECT visited_on,
           SUM(daily_total) OVER (ORDER BY visited_on ROWS BETWEEN 6 PRECEDING AND CURRENT ROW) AS amount,
           ROUND(AVG(daily_total) OVER (ORDER BY visited_on ROWS BETWEEN 6 PRECEDING AND CURRENT ROW), 2) AS average_amount
    FROM DailyTotals
)
SELECT *
FROM MovingCalc
WHERE visited_on >= (SELECT MIN(visited_on) + INTERVAL 6 DAY FROM DailyTotals)
ORDER BY visited_on;
```

**41. Friend Requests II: Who Has the Most Friends**
- **Task:** `RequestAccepted` table mein `requester_id` aur `accepter_id` se friend requests track hoti hain. Us insaan ka `id` aur **number of friends** batao jiske sabse zyada friends hain.
- **Solution:**
```sql
WITH AllFriends AS (
    SELECT requester_id AS id FROM RequestAccepted
    UNION ALL
    SELECT accepter_id AS id FROM RequestAccepted
)
SELECT id, COUNT(*) AS num
FROM AllFriends
GROUP BY id
ORDER BY num DESC
LIMIT 1;
```

**42. Investments in 2016**
- **Task:** `Insurance` table se un policyholders ka **total investment value** (`TIV_2016`) nikalo jo:
    1. 2015 mein kam se kam ek aise policyholder ke saath share karte hain jiska `TIV_2015` same ho.
    2. Aur unka location (latitude, longitude) unique ho (kisi aur policyholder ke saath same na ho).
    Result ko 2 decimal places tak round karo.
- **Solution:**
```sql
SELECT ROUND(SUM(TIV_2016), 2) AS TIV_2016
FROM Insurance i1
WHERE i1.TIV_2015 IN (SELECT TIV_2015 FROM Insurance i2 WHERE i1.PID != i2.PID)
  AND (i1.LAT, i1.LON) NOT IN (
      SELECT LAT, LON FROM Insurance i3 WHERE i1.PID != i3.PID
  );
```

**43. Department Top Three Salaries (Hard)**
- **Task:** Har department mein **top 3 unique salaries** paane wale employees ki list banao. Agar ek se zyada employees ki same salary hai aur woh top 3 mein aati hai to sabko include karo.
- **Solution:**
```sql
WITH RankedSalaries AS (
    SELECT d.name AS Department, e.name AS Employee, e.salary AS Salary,
           DENSE_RANK() OVER (PARTITION BY e.departmentId ORDER BY e.salary DESC) AS rnk
    FROM Employee e
    JOIN Department d ON e.departmentId = d.id
)
SELECT Department, Employee, Salary
FROM RankedSalaries
WHERE rnk <= 3;
```

### 📚 Category 7: Advanced String Functions / Regex / Clause (Medium/Hard)
String manipulation aur pattern matching ke advanced concepts.

**44. Fix Names in a Table**
- **Task:** `Users` table mein `name` column ko fix karna hai. Sirf **first character uppercase** aur baaki **lowercase** hona chahiye. Result `user_id` se ascending order mein karo.
- **Solution:**
```sql
SELECT user_id, 
       CONCAT(UPPER(LEFT(name, 1)), LOWER(SUBSTRING(name, 2))) AS name
FROM Users
ORDER BY user_id;
```

**45. Patients With a Condition**
- **Task:** `Patients` table se un patients ka `patient_id`, `patient_name` aur `conditions` nikalo jinko **Type I Diabetes** hai. (Yani `conditions` column mein **DIAB1** word shuru mein ho ya space ke baad ho).
- **Solution:**
```sql
SELECT patient_id, patient_name, conditions
FROM Patients
WHERE conditions LIKE 'DIAB1%' OR conditions LIKE '% DIAB1%';
```

**46. Delete Duplicate Emails**
- **Task:** `Person` table se duplicate emails delete karni hain, **sirf woh record rakhna hai jiska `id` sabse chhota ho**.
- **Solution:**
```sql
DELETE p1
FROM Person p1
INNER JOIN Person p2 ON p1.email = p2.email AND p1.id > p2.id;
```

**47. Second Highest Salary**
- **Task:** `Employee` table se **second highest distinct salary** nikalo. Agar koi second highest nahi hai to **NULL** return karo.
- **Solution:**
```sql
SELECT MAX(salary) AS SecondHighestSalary
FROM Employee
WHERE salary < (SELECT MAX(salary) FROM Employee);
```

**48. Group Sold Products By The Date**
- **Task:** `Activities` table mein har din beche gaye products ko **comma-separated list** mein group karo. Result `sell_date` se ascending order mein karo.
- **Solution:**
```sql
SELECT sell_date, 
       COUNT(DISTINCT product) AS num_sold,
       GROUP_CONCAT(DISTINCT product ORDER BY product ASC SEPARATOR ',') AS products
FROM Activities
GROUP BY sell_date
ORDER BY sell_date;
```

**49. List the Products Ordered in a Period**
- **Task:** `Products` aur `Orders` table se un products ke names aur **total units sold** nikalo jo **February 2020** mein **kam se kam 100 units** beche gaye hon.
- **Solution:**
```sql
SELECT p.product_name, SUM(o.unit) AS unit
FROM Products p
JOIN Orders o ON p.product_id = o.product_id
WHERE DATE_FORMAT(o.order_date, '%Y-%m') = '2020-02'
GROUP BY p.product_name
HAVING SUM(o.unit) >= 100;
```

**50. Find Users With Valid E-Mails**
- **Task:** `Users` table se un users ko dhundho jinka email **valid format** mein ho. Valid email ka **prefix** letter (a-z), digit (0-9), underscore (_), period (.), ya dash (-) se shuru ho sakta hai aur uske baad **@leetcode.com** hona chahiye.
- **Solution:**
```sql
SELECT user_id, name, mail
FROM Users
WHERE mail REGEXP '^[a-zA-Z][a-zA-Z0-9._-]*@leetcode\\.com$';
```
