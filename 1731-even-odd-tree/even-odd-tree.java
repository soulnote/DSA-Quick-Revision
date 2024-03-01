/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public boolean isEvenOddTree(TreeNode root) {
        Queue<TreeNode> q= new LinkedList<>();
        q.add(root);
        int level = 0;
        boolean result = true;
        if(root.val %2 == 0 ) return false;

        while(q.size()>0){
            TreeNode [] arr = new TreeNode[q.size()];
            int i=0;
            while(!q.isEmpty()){
                arr[i] = q.poll();
                i++;               
            }

            if(level % 2 ==0){
                int start = Integer.MIN_VALUE;
                for(int j=0;j<arr.length;j++){
                    if(arr[j].val % 2 == 0 || arr[j].val<=start ){
                        result = false;
                        break;
                    }
                    start = arr[j].val;
                }
            }


            if(level % 2 !=0){
                int start = Integer.MAX_VALUE;
                for(int j=0;j<arr.length;j++){
                    if(arr[j].val % 2 != 0 || arr[j].val>=start ){
                        result = false;
                        break;
                    }
                    start = arr[j].val;
                }
            }

            level++;
            for(int j=0;j<arr.length;j++){
                if (arr[j].left != null) {
                    q.add(arr[j].left);
                }
 
                if (arr[j].right != null) {
                    q.add(arr[j].right);
                }
            }
            
        }
        return result;
    }
}