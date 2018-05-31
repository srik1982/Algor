package binarytree.bst;

import binarytree.BinaryTree;

import common.Node;

public class ClosestToGivenNumber {
	
	private static Node createBST(){
		Node root = new Node();
		root.data = 8;
		
		Node temp = new Node();
		temp.data = 4;
		root.left = temp;
		
		temp = new Node();
		temp.data = 12;
		root.right = temp;
		
		temp = new Node();
		temp.data = 2;
		root.left.left=temp;
		
		temp = new Node();
		temp.data = 5;
		root.left.right=temp;
		
		temp = new Node();
		temp.data = 10;
		root.right.left=temp;
		
		temp = new Node();
		temp.data = 14;
		root.right.right=temp;
		
		return root;
	}
	
	/**
	 * better than inorder which gives O(n). this is 2 * log(n)
	 * @param args
	 */
	public static void main(String[] args) {
		Node root = createBST();
		BinaryTree.printInorder(root);
		
		int floor = Floor.FloorInBST(root, 6);
		int ceil = Ceil.ceilInBST(root, 6);
		
		System.out.println();
		System.out.println(floor+" , "+ceil);
		
		if(Math.abs(floor - 6) > Math.abs(ceil - 6)){
			System.out.println("Closest is "+ceil);
		}else{
			System.out.println("Closest is "+floor);
		}

	}

}
