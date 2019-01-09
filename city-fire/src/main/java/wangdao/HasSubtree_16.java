package wangdao;

public class HasSubtree_16 {
    public boolean HasSubtree(TreeNode root1,TreeNode root2) {
        if (root1 == null || root2 == null) return false;
        return isHasSubtree(root1,root2)||HasSubtree(root1.right,root2)||HasSubtree(root1.left,root2);
    }
     public  boolean isHasSubtree(TreeNode root1,TreeNode root2){
        if (root1 == null && root2 == null) return true;
         if (root1 == null) return false;
         if (root2 == null) return true;
         if (root1.val == root2.val){
             return (isHasSubtree(root1.left,root2.left)&&isHasSubtree(root1.right,root2.right));
         }
         return false;
     }
}
