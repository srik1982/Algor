package binarytree;

import java.util.LinkedList;
import java.util.Stack;

import common.Node;

public class AllPathsToLeaves {

	public static void main(String[] args) {		
		Node root = BinaryTree.createBinaryTree();
		pathsToLeaves(root);
		//			1
		//		2 		3
		//   4	  5	  6    7
		//
		//    4 5 2 6 7 3 1

	}
	
	private static void pathsToLeaves(Node node){
		Stack<Node> stack = new Stack<Node>();		
		pathsToLeaves(node, stack);
	}
	
	private static void pathsToLeaves(Node node, Stack<Node> stack){
//		if(node != null){
			stack.push(node);
			if(node.left != null)
				pathsToLeaves(node.left,stack);
			if(node.right != null)
				pathsToLeaves(node.right,stack);
			
			if(node.left == null && node.right == null){
				System.out.println(stack);
				stack.pop();
				return;
			}
			stack.pop();
//		}
	}
	

}
