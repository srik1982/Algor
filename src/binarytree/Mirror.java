package binarytree;

import java.util.Stack;

import common.Node;

public class Mirror {

	public static void main(String[] args) {
		Node root = BinaryTree.createBinaryTree();
		System.out.println("Before");
		BinaryTree.printTreeLevelOrder(root);
		mirror(root);
		System.out.println("After");
		BinaryTree.printTreeLevelOrder(root);
		//			1								1
		//		2 		3						3		2
		//   4	  5	  6    7				 7	  6   5   4
		//
		//  post:  4 5 2 6 7 3 1 inorder: 4 2 5 1 6 3 7  preorder 1 2 4 5 3 6 7
	}

	public static void mirror(Node root) {
		Node temp = root.left;
		root.left = root.right;
		root.right = temp;
		
		if(root.left != null)
			mirror(root.left);
		if(root.right != null)
			mirror(root.right);
		
	}

}
