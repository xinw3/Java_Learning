package cloudera;

import java.util.Stack;

public class BST<T> {

	private static class TreeNode<T> {
		T val;
		TreeNode<T> left, right;
		public TreeNode (T value) {
			val = value;
			left = null;
			right = null;
		}
	}
	public void RecTraversal(TreeNode<T> root) {
		if(root != null) {
			RecTraversal(root.left);
			System.out.println(root.val);
			RecTraversal(root.right);
		}
	}
	public void BSTIterator(TreeNode<T> root) {
		Stack<TreeNode<T>> stack = new Stack<TreeNode<T>>();
		while (root != null) {
			stack.push(root);
			root = root.left;
		}
		while (!stack.isEmpty()) {
			TreeNode<T> curr = stack.pop();
			T value = curr.val;
			if (curr.right != null) {
				curr = curr.right;
				while (curr != null) {
					stack.push(curr);
					curr = curr.left;
				}
			}
			System.out.println(value);
		}
	}
	public static void main(String[] args) {
        BST tree = new BST();
		TreeNode<Integer> root = new TreeNode<Integer>(5);
        root.left = new TreeNode<Integer>(2);
        root.right = new TreeNode<Integer>(8);
        root.left.left = new TreeNode<Integer>(1);
        root.left.right = new TreeNode<Integer>(4);
        root.right.left = new TreeNode<Integer>(7);

        //tree.RecTraversal(root);
        tree.BSTIterator(root);

	}

}
