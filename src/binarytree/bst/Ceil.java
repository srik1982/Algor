package binarytree.bst;

import binarytree.BinaryTree;
import common.Node;

public class Ceil {

	public static int ceilInBST(Node root, int data){
		if(root!=null){
			if(root.data == data) return data;
			
			if(data > root.data){
				return ceilInBST(root.right, data);
			}else{
				int ceil = ceilInBST(root.left,data);
				if(ceil == -1){
					return root.data;
				}else{
					return ceil;
				}
			}
			
			
		}
		return -1;
	}
	public static void main(String[] args) {
		Node root = BST.createBST();
		BinaryTree.printInorder(root);
		int floor = ceilInBST(root, 11);
		System.out.println("\n"+floor);
	}

}
