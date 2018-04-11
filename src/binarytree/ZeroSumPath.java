package binarytree;

import java.util.Stack;

public class ZeroSumPath {

	public static void main(String[] args) {
		Node root = BinaryTree.createBinaryTree();
		Stack<Node> stack = new Stack<Node>(); 
		findZeroSumPath(root,8,stack);
		//			1
		//		2 		3
		//   4	  5	  6    7
		//
		//    4 5 2 6 7 3 1
	}

	private static void findZeroSumPath(Node root,int sum,Stack<Node> stack) {
		if(root != null){
			
			sum -= root.data;
			stack.push(root);
			if(sum == 0){
				System.out.println("Found");
				System.out.println(stack);
			}
			
			if(root.left != null)
				findZeroSumPath(root.left, sum,stack);
			if(root.right != null)
				findZeroSumPath(root.right, sum,stack);			
			sum += root.data;
			stack.pop();
		}
		
	}

}
