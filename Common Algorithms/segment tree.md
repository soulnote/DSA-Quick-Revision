tutorial link - [https://www.hackerearth.com/practice/data-structures/advanced-data-structures/segment-trees/tutorial/]

## Segment Trees Tutorials & Notes

**Segment Tree** ek bohot hi versatile **data structure** hai. Iska istemaal aise cases mein hota hai jahan array par kaafi saari **range queries** (jaise $L$ se $R$ tak ka sum ya minimum nikalna) aur elements ke **modifications** (updates) karne padte hain.

### Segment Tree Kya Hai? (What is Segment Tree?)

**Segment Tree** basically ek **binary tree** hota hai jo **intervals** ya **segments** ko store karta hai.

  * Agar koi array $A$ size $N$ ka hai, toh Segment Tree ka **root** poore array $A[0:N-1]$ ko represent karta hai.
  * Har **leaf node** array ke ek single element $A[i]$ ko represent karti hai.
  * **Internal nodes** apne children nodes ke segments ke **union** ko represent karte hain.

Tree ki **structure** (banaawat):

1.  Root node poore array ko represent karti hai.
2.  Har node apne segment ko do equal halves mein divide kar deti hai, aur uske do children un halves ko represent karte hain.
3.  Is division ki wajah se, Segment Tree ki **height** $\mathbf{O(\log N)}$ hoti hai.
4.  Total nodes $\mathbf{2N-1}$ hote hain (N leaves aur N-1 internal nodes).

Ek baar Segment Tree ban jaaye toh uska structure change nahi hota, sirf nodes ki **values** ko update kiya jaa sakta hai.

-----

### Segment Tree Ke Do Main Operations (Main Operations)

Segment Tree mein do primary operations perform kiye jaate hain:

1.  **Update:** Array ke kisi element $A[i]$ ko change karna aur us change ko tree mein **reflect** karna.
2.  **Query:** Kisi diye gaye range $L$ se $R$ par koi sawal puchhna (jaise sum, minimum, ya maximum value find karna).

### Implementation (Kaise Banayein?)

Segment Tree ko hum ek simple **linear array** se represent karte hain (kyunki yeh ek complete binary tree hota hai).

#### 1\. Build Operation

Tree ko generally **recursion** (bottom-up approach) se banaya jaata hai:

  * **Start:** Leaf nodes se shuru karte hain jo single elements ko represent karte hain.
  * **Go Up:** Har step mein, do children nodes ka data use karke unke **internal parent node** ka data banaya jaata hai. Yeh merging operation har problem ke liye different ho sakta hai (jaise sum ke liye add karna, minimum ke liye `min` function use karna).
  * **End:** Recursion root node par khatam hota hai, jo poore array ko represent karta hai.
  * **Complexity:** $\mathbf{O(N)}$

#### 2\. Update Operation

Kisi element ko update karne ke liye:

  * Element ko represent karne waali **leaf node** tak pahuncho.
  * Element ki value update karo.
  * Phir **bottom-up approach** use karke us leaf se lekar **root** tak ke **path** mein aane waale sabhi nodes ko update karo.
  * **Complexity:** $\mathbf{O(\log N)}$

#### 3\. Query Operation

Range $L$ se $R$ par query karne ke liye, root se recursion shuru karte hain aur har node par teen (3) conditions check karte hain:

1.  **Completely Outside:** Agar node ka segment $(start, end)$ query range $(L, R)$ ke poori tarah **bahar** hai, toh simply **0** (ya identity value) return kar do.
2.  **Completely Inside:** Agar node ka segment $(start, end)$ query range $(L, R)$ ke poori tarah **andar** hai, toh node ki **stored value** return kar do.
3.  **Partial Overlap:** Agar segment partially overlap kar raha hai, toh **left child** aur **right child** par **recurse** karo aur unke results ko **combine** karke return kar do.

<!-- end list -->

  * **Complexity:** $\mathbf{O(\log N)}$

-----

### Range Sum Query Ka Example (Range Sum Query Example)

Ek array $A$ diya gaya hai aur humein $l$ se $r$ tak ke elements ka sum nikalna hai.

**Segment Tree se solution:** Har node mein uske corresponding segment ka **sum** store kiya jaayega.

#### **Build Function (Code Explanation)**

Yeh function tree ko banata hai, aur har internal node mein apne children ka sum store karta hai.

```c
void build(int node, int start, int end) {
    if(start == end) {
        // Jab leaf node (single element) par pahunche
        tree[node] = A[start];
    } else {
        int mid = (start + end) / 2;
        // Left child
        build(2*node, start, mid);
        // Right child
        build(2*node+1, mid+1, end);
        // Parent node = Left child ka sum + Right child ka sum
        tree[node] = tree[2*node] + tree[2*node+1];
    }
}
```

#### **Update Function (Code Explanation)**

Yeh function element $A[idx]$ ki value $val$ se badhaata hai aur path mein aane waale nodes ko update karta hai.

```c
void update(int node, int start, int end, int idx, int val) {
    if(start == end) {
        // Leaf node update
        A[idx] += val;
        tree[node] += val;
    } else {
        int mid = (start + end) / 2;
        if(start <= idx and idx <= mid) {
            // idx left side mein hai
            update(2*node, start, mid, idx, val);
        } else {
            // idx right side mein hai
            update(2*node+1, mid+1, end, idx, val);
        }
        // Wapas aate hue parent ka sum update karo
        tree[node] = tree[2*node] + tree[2*node+1];
    }
}
```

#### **Query Function (Code Explanation)**

Yeh function range $[l, r]$ mein sum nikalta hai.

```c
int query(int node, int start, int end, int l, int r) {
    // Agar node range poori tarah se query range ke BAHAR hai
    if(r < start or end < l) {
        return 0; // Sum query ke liye 0 return
    }

    // Agar node range poori tarah se query range ke ANDAR hai
    if(l <= start and end <= r) {
        return tree[node]; // Node ki value (sum) return karo
    }

    // Partial overlap, divide karke sum karo
    int mid = (start + end) / 2;
    int p1 = query(2*node, start, mid, l, r); // Left child se sum
    int p2 = query(2*node+1, mid+1, end, l, r); // Right child se sum
    
    return (p1 + p2);
}
```
