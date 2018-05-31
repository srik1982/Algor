package binarytree;

import java.util.Stack;

import binarytree.bst.BST;
import common.Node;

public class InorderSuccessorPredecessor {

	/**
	 * 				|------------ 8	------------|
	 * 				4							16
	 * 			2		6				12				20
	 * 		1	 3    5		7		11		14		18		22
	 *   
	 */
	public static Node inOrderSuccessor(Node root, int data, Stack<Node> parentStack){
		Node successor = null;
		/**
		 * if we have found the node, then
		 * 1. successor, in simplest case , is the left most node of the right child
		 * 2. if right child is not present
		 *    2.1) The matching node could be the left child or a node. In which case, parent is the inorder successor
		 *         E.g., if 14 was not there, in order successor of 12 is 16
		 *    2.2) Either successor is one of the nodes in parent stack or it is null.
		 *         for 22, there is no right child present and is also the last node in inorder traversal. 
		 *         2.2.1) If the node is only on the right side of all of its parents, i.,e 8 > 16 > 20 > 22. All are right child, then successor is null.
		 *                So, in the while loop, when you come out if parentNode is null, if yes its 2.2.1, successor is null
		 *         2.2.2) if this node or one of its parents is in the left of its parent, our search halts there. 
		 *                In case of 7, our search stops at parentNode = 8, because 8.right != 4. our inorder successor is 8
		 *         
		 */
		if(root.data == data){			
			if(root.right != null){
				successor = root.right;
				while(successor.left != null){
					successor = successor.left;
				}
			}else if(!parentStack.isEmpty()){
				Node parentNode = parentStack.pop();
				if(parentNode.left == root){
					successor = parentNode;
				}
				else {
					successor = root;
					while (parentNode.right == successor){
						if(parentStack.isEmpty()) {
							parentNode = null;
							break;
						}
						successor = parentNode;						
						parentNode = parentStack.pop();
					}
					
					successor = parentNode;
				}
			}
			return successor;
		}
		else if(data > root.data){
			parentStack.push(root);
			successor = inOrderSuccessor(root.right, data,parentStack);
		}
		else {
			parentStack.push(root);
			successor = inOrderSuccessor(root.left, data,parentStack);
		}
		
		return successor;
	}
	
	/**
	 * 				|------------ 8	------------|
	 * 				4							16
	 * 			2		6				12				20
	 * 		1	 3    5		7		11		14		18		22
	 *   
	 */
	public static Node inOrderPredecessor(Node root, int data, Stack<Node> parentStack){
		Node predecessor = null;
		if(root.data == data){			
			if(root.left != null){
				predecessor = root.left;
				while(predecessor.right != null){
					predecessor = predecessor.right;					
				}
			} else if(!parentStack.isEmpty()){
				Node parent = parentStack.pop();
				if(parent.right == root){
					predecessor = parent;
				}else if(!parentStack.isEmpty()){
					Node temp = root;
					while(parent.left == temp){
						if(parentStack.isEmpty()){
							parent = null;
							break;
						}
						temp = parent;
						parent = parentStack.pop();
					}
					
					predecessor = parent;
				}
				
			}
			return predecessor;
		}
		else if(data > root.data){
			parentStack.push(root);
			predecessor = inOrderPredecessor(root.right, data,parentStack);
		}
		else {
			parentStack.push(root);
			predecessor = inOrderPredecessor(root.left, data,parentStack);
		}
		
		return predecessor;
	}
	
	/**
	 * 				|------------ 8	------------|
	 * 				4							16
	 * 			2		6				12				20
	 * 		1	 3    5		7		11		14		18		22
	 *   
	 */
	
	public static void main(String args[]){
		
		Node root = BST.createBST();
		
		
//		System.out.println(inOrderSuccessor(newRoot, 8, new Stack<Node>()));
		System.out.println(inOrderPredecessor(root, 7, new Stack<Node>()));
	}
}
