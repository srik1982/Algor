package binarytree;

import java.util.LinkedList;
import java.util.Queue;

import common.Node;

public class BinaryTree {
	
	public static Node createBinaryTree(){
		Node root = new Node();
		root.data = 1;
		
		Node temp = new Node();
		temp.data = 2;
		root.left = temp;
		
		temp = new Node();
		temp.data = 3;
		root.right = temp;
		
		temp = new Node();
		temp.data = 4;
		root.left.left=temp;
		
		temp = new Node();
		temp.data = 8;
		root.left.left.left=temp;
		
		temp = new Node();
		temp.data = 9;
		root.left.left.right=temp;
		
		temp = new Node();
		temp.data = 5;
		root.left.right=temp;
		
		temp = new Node();
		temp.data = 6;
		root.right.left=temp;
		
		temp = new Node();
		temp.data = 7;
		root.right.right=temp;
		
		temp = new Node();
		temp.data = 10;
		root.right.right.left=temp;
		
		temp = new Node();
		temp.data = 11;
		root.right.right.right=temp;
		
		return root;
	}
	
	public static void printInorder(Node node)
	    {
	        if (node == null)
	            return;
	 
	        /* first recur on left child */
	        printInorder(node.left);
	 
	        /* then print the data of node */
	        System.out.print(node.data + " ");
	 
	        /* now recur on right child */
	        printInorder(node.right);
	    }
	 
	public static void printTreeLevelOrder(Node root){
		Queue<Node> q = new LinkedList<Node>();
		q.offer(root);
		while(!q.isEmpty()){
			Node n = q.poll();
			System.out.print(n+" ");
			
			if(n.left != null)
				q.offer(n.left);
			
			if(n.right != null)
				q.offer(n.right);
		}
		System.out.println();
	}
		
	public static Node deepCopyTree(Node original){
		if(original != null){
			Node clone = new Node();
			clone.data = original.data;
			clone.left = deepCopyTree(original.left);
			clone.right = deepCopyTree(original.right);
			return clone;
		}
		return null;
	}
}
