# River Crossing / Constraints Puzzles

### 1. Fox, Chicken, and Grain/Corn River Crossing

**Question:** Ek kisaan ko ek lomdi (fox), ek murgi (chicken), aur ek anaaj ki bori (bag of grain) ko nadi ke paar le jaana hai. Uski naav mein woh aur sirf ek aur cheez hi aa sakti hai. Agar lomdi ko akela chhod diya jaye toh woh murgi ko kha jayegi, aur agar murgi ko akela chhod diya jaye toh woh anaaj kha jayegi. Kisaan in teeno ko surakshit roop se doosri taraf kaise le jayega?

**Solution:**

Farmer ko smart tareeke se cheezon ko cross karwana hoga, yeh sochte hue ki kya cheez kiske saath akeli chhod sakte hain:

1.  Farmer pehle **chicken** ko river ke paar leke jayega. Fox aur grain ko akela chhod do, unhe koi problem nahi hogi.
2.  Farmer akela **wapas aayega**.
3.  Farmer **fox** ko river ke paar leke jayega.
4.  Ab yahan tricky part hai: Fox aur chicken ko akela nahi chhod sakte. Toh, farmer **chicken ko wapas** leke aayega.
5.  Farmer chicken ko chodega, aur **grain** ko river ke paar leke jayega. Grain aur fox ko akela chhod do, unhe koi problem nahi hogi.
6.  Farmer akela **wapas aayega**.
7.  Finally, farmer **chicken** ko river ke paar leke jayega.

Ab sabhi cheezein surakshit roop se river ke paar hain.

---

### 2. Bridge Crossing (Night, Flashlight)

**Question:** Char logon ko raat mein ek kamzor pul (rickety bridge) paar karna hai. Unke paas sirf ek flashlight hai, aur pul par ek baar mein sirf do log hi ja sakte hain. Har insaan alag speed se cross karta hai (jaise 1, 2, 7, 10 minutes). Sabko pul paar karne mein minimum kitna time lagega?

**Solution:**

Minimum time ke liye sabse slow person ko manage karna hota hai, aur sabse tez log flashlight wapas laate hain. Let's assume speeds: A=1 min, B=2 min, C=7 min, D=10 min.

1.  **A aur B cross karenge.** (Time taken: **2 minutes**, B ka speed, kyuki dono mein se slow B hai). Flashlight B ke paas hai.
2.  **A wapas aayega** flashlight leke. (Time taken: **1 minute**, A ka speed).
3.  **C aur D cross karenge.** (Time taken: **10 minutes**, D ka speed). Yeh sabse crucial step hai, sabse slow logon ko saath cross karwao. Flashlight D ke paas hai.
4.  **B wapas aayega** flashlight leke. (Time taken: **2 minutes**, B ka speed). Kyunki B fastest available person hai jo wapas aa sakta hai.
5.  **A aur B cross karenge.** (Time taken: **2 minutes**, B ka speed).

Total Time: $2 + 1 + 10 + 2 + 2 = \mathbf{17 \text{ minutes}}$.

---
