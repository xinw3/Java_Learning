package cloudera;

public class BST {

	public class TreeNode<T> {
		T val;
		TreeNode<T> left, right;
		public TreeNode (T value) {
			val = value;
			left = null;
			right = null;
		}
	}
	public void RecTraversal(TreeNode root) {
		if(root != null) {
			RecTraversal(root.left);
			System.out.println(root.val);
			RecTraversal(root.right);
		}
	}
	public static void main(String[] args) {
        BST tree = new BST();
		TreeNode<Integer> root = tree.new TreeNode<Integer>(5);
        root.left = tree.new TreeNode<Integer>(2);
        root.right = tree.new TreeNode<Integer>(8);
        root.left.left = tree.new TreeNode<Integer>(1);
        root.left.right = tree.new TreeNode<Integer>(4);
        root.right.left = tree.new TreeNode<Integer>(7);
          
        tree.RecTraversal(root);

	}

}
