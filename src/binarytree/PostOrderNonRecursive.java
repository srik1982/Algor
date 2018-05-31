package binarytree;
import java.util.Stack;

import common.Node;


public class PostOrderNonRecursive {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Node root = BinaryTree.createBinaryTree();
		postOrder(root);
		//			1
		//		2 		3
		//   4	  5	  6    7
		//
		//    4 5 2 6 7 3 1
	}

	private static void postOrder(Node root) {
		Stack<Node> stack = new Stack<Node>();
		Node previous = null; 
		do{
			while(root != null){
				stack.push(root);
				root = root.left;			
			}
			while(!stack.isEmpty()){
				root = stack.peek();
				if(root.right == null || root.right == previous){
					System.out.println(root);
					previous = stack.pop();
				}else{
					root = root.right;
					break;
				}
			}
		}while(!stack.isEmpty());
	}

}
