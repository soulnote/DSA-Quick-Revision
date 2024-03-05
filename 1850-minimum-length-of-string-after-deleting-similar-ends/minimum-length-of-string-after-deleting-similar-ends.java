class Solution {
    public int minimumLength(String s) {
        int left = 0,right=s.length()-1;
        int count=0;
        while( left<right){
            char l = s.charAt(left);
            char r = s.charAt(right);
            if(left<s.length() && right>0 && l==r && left<right){
                while(left<s.length() && s.charAt(left)==l)left++;
                while(right>0 && s.charAt(right)==r)right--;
            }
            else break;
        }
        int ans = right-left+1;
        if(ans<0)return 0;
        else return ans;
    }
}