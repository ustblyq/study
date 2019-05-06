package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @Author: yiqiangliu
 * @Date: 2019/5/6
 */
public class BinaryTree {

    class TreeNode{
        TreeNode left;
        TreeNode right;
        int val;
    }

    public List<Integer> preorderTraversal(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        List<Integer> preorder = new ArrayList<>();

        if(root == null){
            return preorder;
        }
        stack.push(root);
        while(!stack.empty()){
            TreeNode node = stack.pop();
            preorder.add(node.val);
            // push right node first, due to the stack FILO
            if(node.right != null){
                stack.push(node.right);
            }

            if(node.left != null){
                stack.push(node.left);
            }
        }
        return preorder;
    }

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> s = new Stack<>();
        TreeNode cur = root;
        while (cur != null || !s.isEmpty()) {
            while (cur != null) {
                s.push(cur);
                cur = cur.left;
            }
            cur = s.pop();
            res.add(cur.val);
            cur = cur.right;
        }
        return res;
    }


    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> resultList = new ArrayList<Integer>();
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode current = root;
        while( current != null ) { // iteratively push left node into stack
            stack.push(current);
            current = current.left;
        }
        stack.push(current); // null left node also need to be pushed into stack
        while( !stack.isEmpty() ) {
            current = stack.pop();
            if( current != null )
                resultList.add(current.val);
            if( !stack.isEmpty() && current == stack.peek().left && stack.peek().right != null) { // traverse right part
                TreeNode left = stack.peek().right;
                while( left != null ) {
                    stack.push(left);
                    left = left.left;
                }
                stack.push(left);
            }
        }
        return resultList;
    }
}
