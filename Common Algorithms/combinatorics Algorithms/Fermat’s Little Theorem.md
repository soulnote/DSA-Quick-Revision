# 📘 **Fermat’s Little Theorem**

Fermat's Little Theorem ek powerful tool hai **modular inverse** nikalne ke liye, jab modulus `p` **prime** ho.

---

### 🔍 Theorem Statement:

Agar `p` ek **prime number** hai, aur `a` koi **integer** hai jise `p` divide nahi karta, to:

```
a^(p-1) ≡ 1 (mod p)
```

### 🔁 Rearranged to find Modular Inverse:

```
a^(p-2) ≡ a^(-1) (mod p)
```

Iska matlab:

> **a inverse mod p** = `a^(p-2) % p`

---

## 📌 Use-case: Why use Fermat’s Theorem?

Agar aapko `nCr % p` nikalna hai (modulo prime), to denominator me `r!` aur `(n-r)!` aate hain:

```
nCr = n! / (r! * (n-r)!)  ⟹  n! * inverse(r!) * inverse((n-r)!)
```

Normal division nahi hoti modulo ke andar, isiliye **modular inverse** use hota hai.

---

## 🧮 Example:

Let’s say:

```
a = 5, p = 7 (prime)
Find: 5^(-1) % 7
```

Using Fermat’s:

```
5^(-1) ≡ 5^(7-2) = 5^5 % 7
5^5 = 3125
3125 % 7 = 3
So, 5^(-1) % 7 = 3
```

---

## ✅ Java Code to compute Modular Inverse using Fermat’s Theorem

```java
public class FermatInverse {

    // Modular exponentiation: (a^b) % p
    static long power(long a, long b, int p) {
        long res = 1;
        a %= p;
        while (b > 0) {
            if ((b & 1) == 1)
                res = (res * a) % p;
            a = (a * a) % p;
            b >>= 1;
        }
        return res;
    }

    // Fermat’s Little Theorem: a^(p-2) % p = inverse of a mod p
    static long modInverse(long a, int p) {
        return power(a, p - 2, p);
    }

    public static void main(String[] args) {
        int a = 5;
        int p = 7; // Prime
        long inverse = modInverse(a, p);
        System.out.println("Modular inverse of " + a + " mod " + p + " is " + inverse);
    }
}
```

---

## Output:

```
Modular inverse of 5 mod 7 is 3
```

