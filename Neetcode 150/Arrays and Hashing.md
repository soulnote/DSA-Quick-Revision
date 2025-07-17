<img width="851" height="295" alt="image" src="https://github.com/user-attachments/assets/8d4a3f7e-59ba-4969-82e6-c07ca7736c50" />

### using PriorityQueue nlog(n) tc

```java
class Solution {
    public int longestConsecutive(int[] nums) {
        if(nums.length==0 || nums.length==1) return nums.length;
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for(int i=0;i<nums.length;i++){
            pq.add(nums[i]);
        }
        int val = pq.poll();
        int ans =1, count =1;
        while(pq.size()>0){
            int a = pq.poll();
            if(a==val)continue;
            else if(val+1 == a){
                count++;
                ans = Math.max(count, ans);
            }
            else{
                count =1;
            }
            val = a;
        }
        return ans;
    }
}
```
### Using set with  o(n) tc

```java
class Solution {
    public int longestConsecutive(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for(int num:nums){
            set.add(num);
        }
        int ans =0;
        for(int val:nums){
            if(!set.contains(val-1)){
                int count=1;
                while(set.contains(val+1)){
                    count++;
                    val++;
                }
                ans = Math.max(count,ans);
            }
        }
        return ans;
    }
}
```

<img width="851" height="335" alt="image" src="https://github.com/user-attachments/assets/3a8dbe24-2582-45f4-9f2f-4c19da0a699f" />

```java
class Solution {

    public boolean isValidSudoku(char[][] board) {
    
        //a set of the characters that we have already come across (excluding '.' which denotes an empty space)
        Set<Character> rowSet = null;
        Set<Character> colSet = null;


        for (int i = 0; i < 9; i++) {
            //reinitialize the sets so we don't carry over found characters from the previous run
            rowSet = new HashSet<>();
            colSet = new HashSet<>();
            for (int j = 0; j < 9; j++) {
                char r = board[i][j];
                char c = board[j][i];
                if (r != '.'){
                    if (rowSet.contains(r)){
                        return false;
                    } else {
                        rowSet.add(r);
                    }
                }
                if (c != '.'){
                    if (colSet.contains(c)){
                        return false;
                    } else {
                        colSet.add(c);
                    }
                }
            }
        }

        //block
        //loop controls advance by 3 each time to jump through the boxes
        for (int i = 0; i < 9; i = i + 3) {
            for (int j = 0; j < 9; j = j + 3) {
                //checkBlock will return true if valid
                if (!checkBlock(i, j, board)) {
                    return false;
                }
            }
        }
        //passed all tests, therefore valid board
        return true;
    }

    public boolean checkBlock(int idxI, int idxJ, char[][] board) {
        Set<Character> blockSet = new HashSet<>();
        //if idxI = 3 and indJ = 0
        //rows = 6 and cols = 3
        int rows = idxI + 3;
        int cols = idxJ + 3;
        //and because i initializes to idxI but only goes to rows, we loop 3 times (once for each row)
        for (int i = idxI; i < rows; i++) {
            //same for columns
            for (int j = idxJ; j < cols; j++) {
                if (board[i][j] == '.') {
                    continue;
                }
                
                if (blockSet.contains(board[i][j])) {
                    return false;
                }

                blockSet.add(board[i][j]);
            }
        }

        return true;
    }
}
```
<img width="851" height="305" alt="image" src="https://github.com/user-attachments/assets/e86023e9-5efe-4a80-819d-794a3f7d43ca" />

```java
class Solution {
    public int[] productExceptSelf(int[] nums) {
        int n  = nums.length;
        int[]prefixProduct = new int[n];
        int[]postfixProduct = new int[n];

        prefixProduct[0] = nums[0];
        for(int i=1;i<n;i++){
            prefixProduct[i] = prefixProduct[i-1]*nums[i];
        }
        postfixProduct[n-1] = nums[n-1];
        for(int i=n-2;i>=0;i--){
            postfixProduct[i] = postfixProduct[i+1]*nums[i];
        }
        int[]ans = new int[n];
        for(int i=0;i<n;i++){
            if(i==0){
                ans[i] = postfixProduct[i+1];
            }
            else if(i==n-1){
                ans[i] = prefixProduct[i-1];
            }
            else{
                ans[i] = prefixProduct[i-1]*postfixProduct[i+1];
            }
        }
        return ans;
    }
}
```
<img width="762" height="237" alt="image" src="https://github.com/user-attachments/assets/61347a42-cbcf-48b3-add3-1238284017a5" />

```java
class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int  i=0;i<nums.length;i++){
            map.put(nums[i], map.getOrDefault(nums[i],0)+1);
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
        for(int a : map.keySet()){
            int[] elm = new int[]{a, map.get(a)};
            pq.offer(elm);
            if(pq.size()>k){
                pq.poll();
            }
        }
        int[] ans = new int[k];
        for(int i=0;i<k;i++){
            int[] temp = pq.poll();
            ans[i] = temp[0];
        }
        return ans;

    }
}
```
<img width="682" height="379" alt="image" src="https://github.com/user-attachments/assets/d7199e0c-152b-48a3-bd6a-5a4cbb77fe69" />

```java
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> ans = new ArrayList<>();
        HashMap<String, List<String>> map = new HashMap<>();
        for(int i =0;i<strs.length;i++){
            String s = strs[i];
            char[] tempS = s.toCharArray();
            Arrays.sort(tempS);
            String sortedS = new String(tempS);
            if(!map.containsKey(sortedS)){
                map.put(sortedS, new ArrayList());
            }
            map.get(sortedS).add(s);
        }
        for (String key : map.keySet()) {
            ans.add(map.get(key));
        }
        return ans;
    }
}
```

<img width="796" height="328" alt="image" src="https://github.com/user-attachments/assets/b77b70d0-e33e-4f5c-b807-3aef2992596d" />

```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<nums.length;i++){
            if(map.containsKey(target - nums[i])){
                return new int[]{map.get(target - nums[i]), i};
            }
            else{
                map.put(nums[i], i);
            }
        }
        return new int[]{-1,-1};
    }
}
```
<img width="768" height="352" alt="image" src="https://github.com/user-attachments/assets/30e10621-6dec-4304-af8a-d4a42f1f2ebd" />

```java
class Solution {
    public boolean isAnagram(String s, String t) {
        if(s.length()!=t.length())return false;
        int[] arr = new int[26];
        for(int i=0;i<s.length();i++){
            int ch = s.charAt(i)-'a';
            arr[ch]++;
        }
        for(int i=0;i<t.length();i++){
            int ch = t.charAt(i)-'a';
            arr[ch]--;
            if(arr[ch]<0)return false;
        }
        return true;
    }
}
```
<img width="667" height="349" alt="image" src="https://github.com/user-attachments/assets/69fd5190-4b0c-41c1-b832-58b60fb2259b" />

```java
class Solution {
    public boolean containsDuplicate(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for(int i=0;i<nums.length;i++){
            if(set.contains(nums[i]))return true;
            else set.add(nums[i]);
        }
        return false;
    }
}
```


