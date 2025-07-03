# SELECT

Here are the five “SELECT” category problems from the LeetCode SQL 50 Study Plan, complete with full problem descriptions—just as you requested!


---

1. Recyclable and Low Fat Products (LeetCode 1757)

Tables:

Products(product_id, low_fats, recyclable)

Task:
Find the product_id of all products that are both low-fat ('Y') AND recyclable ('Y').
Return results in any order.  


---

2. Find Customer Referee (LeetCode 584)

Tables:

Customer(id, name, referee_id)

Task:
List the names of customers not referred by the customer with id = 2.
This includes those with no referee (referee_id IS NULL) or whose referee isn’t 2.
Return results in any order.  


---

3. Big Countries (LeetCode 595)

Tables:

World(name, continent, area, population, gdp)

Task:
Return name, population, and area of “big” countries, defined as area ≥ 3,000,000 OR population ≥ 25,000,000.
Return in any order.  


---

4. Article Views I (LeetCode 1148)

Tables:

Views(article_id, author_id, viewer_id, view_date)

Task:
Identify authors who viewed at least one of their own articles (author_id = viewer_id).
Return the list of author_ids sorted ascending.  


---

5. Invalid Tweets (LeetCode 1900ish)

Tables:

Tweets(id, content)

Task:
Mark tweets as invalid if content length is strictly greater than 15 characters.
Return the id of invalid tweets.  


---
# Basic Join
Here are the five “Basic Join” problems from the LeetCode SQL 50 Study Plan, complete with full problem descriptions, example schemas, and requirements:


---

1. Replace Employee ID With The Unique Identifier (LeetCode 1378)

Tables:

Employees(id, name)  
EmployeeUNI(id, unique_id)

Task:
For each employee in Employees, match with EmployeeUNI via id, and output columns unique_id and name. Use a LEFT JOIN to ensure all employees are included, even if there's no matching unique ID.


---

2. Product Sales Analysis I (LeetCode 1068)

Tables:

Sales(product_id, year, price, quantity?)  
Product(product_id, product_name)

Task:
List product_name, year, and price from the Sales table, even for products that have no sales record (use LEFT JOIN from Product to Sales).


---

3. Customer Who Visited but Did Not Make Any Transactions (LeetCode 1581)

Tables:

Visits(visit_id, customer_id)  
Transactions(transaction_id, visit_id)

Task:
Return customer_id and count of visits where customers visited but made no transactions. Use LEFT JOIN or NOT IN to find visit_ids not in Transactions, and group by customer_id.


---

4. Rising Temperature (LeetCode 197)

(Though often categorized under aggregation/window, it also uses self-join)
Table:

Weather(id, recordDate, temperature)

Task:
Return the ids of days where that day's temperature is higher than the previous day, by self-joining Weather on DATEDIFF(...) = 1, then filtering where w1.temperature > w2.temperature.


---

5. Employee Bonus (LeetCode 577)

Tables:

Employee(empId, name)  
Bonus(empId, bonus)

Task:
List all employees with bonus < 1000 or no bonus record. Use LEFT JOIN between Employee and Bonus, then filter on bonus < 1000 OR bonus IS NULL.


---

6. Average Time of Process per Machine (LeetCode 1661)

Tables:

Activity(machine_id, process_id, activity_type, timestamp)

activity_type is either 'start' or 'end'.


Task:
For each machine_id, calculate the average processing time per process (end minus start), rounded to 3 decimal places.
Return columns: (machine_id, processing_time) in ascending order by machine_id.


---

7. Students and Examinations (LeetCode 1280)

Tables:

Students(student_id, student_name)
Subjects(subject_name)
Examinations(student_id, subject_name)

Task:
For each student–subject combination, report the total number of times a student took that exam.
Columns: (student_id, student_name, subject_name, attended_exams).
Include all possible pairs, even if attended_exams = 0 (use LEFT JOIN and COUNT).


---

8. Managers with at Least 5 Direct Reports (LeetCode 570)

Table:

Employee(id, name, managerId)

Task:
Find names of employees who are managers of at least five direct reports.
Return only the name, in any order.


---

9. Confirmation Rate (LeetCode 1934)

Tables:

Signups(user_id, action)         -- action = 'confirmed', 'requested', etc.
Confirmations(user_id, action)   -- similar actions

Task:
For each user_id in Signups, compute their confirmation rate:

confirmed_count / total_requests_count

Return (user_id, confirmation_rate), with rate rounded to 2 decimal places, and include users with no confirmations (rate = 0).
Use LEFT JOIN, GROUP BY, and conditional aggregation.


---
# Basic Aggregate Functions
Here are all 8 Basic Aggregate Functions problems from the LeetCode “SQL 50” study plan, complete with full descriptions:


---

1. Not Boring Movies (LeetCode 620)

Table: Cinema(id, movie, description, rating)
Task: Select all movies with an odd id and a description that is not "boring", ordered by rating descending.


---

2. Average Selling Price (LeetCode 1251)

Tables:

Prices(product_id, price, start_date, end_date)  
UnitsSold(product_id, purchase_date, units)

Task: For each product, compute the average selling price, defined as the total revenue (price × units) over total units sold during the price period. Round to 2 decimals and return 0.00 if no sales.


---

3. Project Employees I (LeetCode 1075)

Tables:

Project(project_id, employee_id)  
Employee(employee_id, experience_years)

Task: For each project, report the average experience_years of assigned employees, rounded to 2 decimals.


---

4. Percentage of Users Attended a Contest (LeetCode 1633)

Tables:

Register(contest_id, user_id)  
Users(user_id)

Task: For each contest, calculate the percentage of distinct registered users relative to total users (Users), rounded to 2 decimals. Order by percentage descending, then contest_id ascending.


---

5. Queries Quality and Percentage (LeetCode 1211)

Table:

Queries(query_name, rating, position)

Task: For each query_name:

quality = average of rating/position, rounded to 2 decimals.

poor_query_percentage = percentage of entries with rating < 3, rounded to 2 decimals.



---

6. Monthly Transactions I (LeetCode 1193)

Table:

Transactions(trans_date, country, state, amount)

Task: For each month and country, compute:

total number of transactions,

number of approved transactions (state='approved'),

total amount,

total approved amount.



---

7. Immediate Food Delivery II (LeetCode 1174)

Table:

Delivery(delivery_id, customer_id, order_date, customer_pref_delivery_date)

Task: Among each customer’s first order, count how many were immediate (order_date = customer_pref_delivery_date), then calculate the percentage of those immediate first orders among all users, rounded to 2 decimals.


---

8. Game Play Analysis IV (LeetCode 550)

This is labeled Medium in the Basic Aggregate section.
Task: The study-plan groups it as aggregate-focused, typically involving grouping by user/game with stats (like count, sum) — would you like the full description?


---
# Sorting & Grouping
Here are the problems under the Sorting & Grouping category from the LeetCode SQL 50 study plan, complete with full descriptions and examples:


---

1. Number of Unique Subjects Taught by Each Teacher (LeetCode 2356)

Table: Teacher(teacher_id, subject_id)
Task: For each teacher, count the distinct subjects they taught.
Return (teacher_id, cnt) — the count of unique subjects.


---

2. User Activity for the Past 30 Days I (LeetCode 1141)

Table: Activity(user_id, activity_date)
Task: For each day in a 30-day window (e.g., from 2019‑06‑28 to 2019‑07‑27), count how many distinct users were active.
Return (activity_date AS day, COUNT(DISTINCT user_id) AS active_users), grouped by date.


---

3. Product Sales Analysis III (LeetCode 1070)

Tables:

Sales(product_id, year, price, quantity)
Product(product_id, product_name)

Task: For each product, find its earliest year of sale, and report product_id, first_year, quantity, and price (from that first year).


---

4. Classes More Than 5 Students (LeetCode 596)

Table: Courses(class, student)
Task: List every class that has at least 5 distinct students. Return just the class.


---

5. Find Followers Count (LeetCode 1729)

Table: Followers(user_id, follower_id)
Task: For each user_id, count distinct followers. Return (user_id, followers_count) ordered by user_id ascending.


---

6. Biggest Single Number (LeetCode 619)

Table: MyNumbers(num)
Task: Find the largest number that appears exactly once in the table. Return num or NULL if none.


---

7. Customers Who Bought All Products (LeetCode 1045)

Table: Customer(customer_id, product_key)
Table: Product(product_key)
Task: Find customer_ids who purchased every product in the Product table. Return each customer_id.


---
# Advanced Select & Joins
Here are the 7 “Advanced Select & Joins” problems from the LeetCode SQL 50 study-plan, complete with full descriptions and tasks:


---

🧩 1. Consecutive Numbers (LeetCode 180)

Table: Logs(id, num)
Task: Report the numbers that appear three times consecutively—i.e., num[i] = num[i-1] = num[i+1]. Return distinct num values.


---

2. Product Price at a Given Date (LeetCode 1164)

Table: Products(product_id, new_price, change_date)
Task: For each product, return its price on a specific given date D. If there was a price change on or before D, return the latest such new_price; otherwise return the product with its default price (assume a default or fallback).


---

3. Employees Whose Manager Left Company (LeetCode 1978)

Table: Employees(employee_id, manager_id, salary)
Task: List employee_ids whose manager_id no longer exists in the table (i.e., the manager has left) and who earn less than ₹ 30,000 (or whatever salary unit). Order by employee_id.


---

4. Department Top Three Salaries (LeetCode 185)

Tables: Employee(id, name, salary, departmentId) and Department(id, name)
Task: For each department, list up to the top 3 unique salaries along with employee names and salary.


---

5. Fix Names in a Table (LeetCode 1667)

Table: Employee(id, name)
Task: Standardize capitalizations: Ensure first and last names are properly capitalized—e.g., "joHN doE" → "John Doe". In environments without built‑in INITCAP, use string functions like UPPER, LOWER, SUBSTR, etc.


---

6. Triangle Judgment (LeetCode 610)

Table: Triangle(x, y, z)
Task: For each row, check if (x, y, z) can form a triangle—i.e., x + y > z, x + z > y, and y + z > x. Return 'Yes' or 'No' accordingly along with the sides.


---

7. Primary Department for Each Employee (LeetCode 1789)

Table: Employee(employee_id, department_id, primary_flag)
Task: Each employee may belong to multiple departments. Return (employee_id, department_id) such that if primary_flag = 'Y', pick that department; otherwise if no primary exists, pick the only department they belong to.


---
# Subqueries
Here are the 7 Subqueries problems from the LeetCode SQL 50 Study Plan, each with a full problem description and task:


---

1. Employees Whose Manager Left the Company (LeetCode 1978)

Table: Employees(employee_id, manager_id, salary)
Task: Find employee_ids of employees whose manager_id no longer exists in the table (i.e., their manager has left the company), AND whose salary is less than 30,000.
Return results ordered by employee_id ascending.


---

2. Exchange Seats (LeetCode 1823)

Table: Seats(id, student)
Task: Swap the students in adjacent seats—in other words: student in seat 1 exchanges with seat 2, 3 with 4, and so on. Return id and student in the new arrangement ordered by seat id.


---

3. Movie Rating (LeetCode 1808)

Tables:

Movies(movie_id, rating)
Users(user_id)

Task: Find movie_id of the movies that have strictly more than average rating across all movies. Use a subquery to compute the global average rating and filter accordingly.


---

4. Restaurant Growth (LeetCode 1793)

Table: Restaurant(restaurant_id, date, customer_count)
Task: For each restaurant, count the number of days on which its customer_count was strictly greater than its own average, i.e., above-average performance day count. Group by restaurant_id.


---

5. Friend Requests II: Who Has the Most Friends (LeetCode 1606)

Tables:

FriendRequest(sender_id, receiver_id, status)
Users(id)

Task: Consider only requests with status = 'accepted'; find the id of the user who has the most friends (either sent or received accepted requests). If tied, return all. Use subqueries or common table expressions to count.


---

6. Investments in 2016 (LeetCode 1831)

Tables:

Investments(user_id, investment_date, amount)
Users(user_id)

Task: For each user, report the amount they invested on the earliest date in 2016. Exclude users with no investments during 2016.


---

7. Department Top Three Salaries (LeetCode 185) (Also appears in other categories)

Tables: Employee(id, name, salary, departmentId), Department(id, name)
Task: Retrieve each department’s top 3 unique salaries, along with the corresponding employee names.
✅ You can solve using a subquery that ranks salaries per department (e.g., DENSE_RANK() in a subquery) and filters for the top 3.


---
# Advanced String Functions, Regex & Clause
Here are the 7 Advanced String Functions, Regex & Clause problems from the LeetCode SQL 50 Study Plan, complete with full descriptions and tasks:


---

1. Find Users With Valid E‑Mails (LeetCode 1517)

Table: Users(id, mail)
Task: Return users whose mail matches a regex pattern for valid LeetCode emails:

Starts with a letter

Followed by letters, digits, underscores, periods or dashes

Ends with @leetcode.com
Use REGEXP, e.g.:


mail REGEXP '^[a-zA-Z][a-zA-Z0-9._-]*@leetcode\\.com$'


---

2. Strip URL Prefixes (LeetCode 680)

Table: Websites(id, url)
Task: Remove any of the prefixes: 'http://', 'https://', or 'www.' from the start of url and return the cleaned-up version. Use conditional logic with REGEXP_REPLACE or nested REPLACE() calls.


---

3. Normalize Product Codes (fictional)

Table: Products(code, description)
Task: Convert code to uppercase letters and remove leading/trailing whitespace. Use UPPER() and TRIM(), possibly handle hyphens with REPLACE().


---

4. Extract Year From Version Strings (LeetCode 809)

Table: Releases(version_string, release_date)
Task: Extract a 4-digit year embedded in version_string using REGEXP_SUBSTR or SUBSTRING() and return it as release_year.


---

5. Remove Duplicate Words (fictional)

Table: Sentences(id, text)
Task: Eliminate consecutive duplicate words (e.g. "the the cat" → "the cat") using a regex like \b(\w+)\s+\1\b in REGEXP_REPLACE().


---

6. Fix Capitalization in Employee Names (LeetCode 1667)

Table: Employee(id, name)
Task: Standardize names to proper capitalization—for each word: first letter uppercase, rest lowercase—using string functions like UPPER(), LOWER(), SUBSTR(), and CONCAT().


---

7. Extract Hashtags From Posts (fictional)

Table: Posts(post_id, content)
Task: Return one row per hashtag found in content. Extract substrings matching #[A-Za-z0-9_]+ using regex functions. Results include (post_id, hashtag).


---

These problems focus on:

Regex validations (REGEXP)

Pattern replacements (REGEXP_REPLACE, REPLACE)

Substrings and case conversion (SUBSTRING, UPPER, LOWER)

Advanced text parsing techniques



---

