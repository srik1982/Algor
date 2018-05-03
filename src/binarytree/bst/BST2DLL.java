package binarytree;

import java.util.Stack;

public class BST2DLL {

	public static void main(String[] args) {
		Node root = BST.createBST();		
		BST.printInorder(root);
		System.out.println();
		Node head = convert2DLL(root);
		printDLL(head);
	}

	private static void printDLL(Node head) {
		while(head !=null){
			System.out.print(head.data+" ");
			head = head.right;
		}
		
	}

	private static Node convert2DLL(Node root) {
		// Inorder traversal with stack and iteration
		Stack<Node> s = new Stack<Node>();
		Node head = null,prev = null;
		while(true){
			while(root !=null){
				s.push(root);
				root = root.left;
			}
			
			if(s.empty())break;
			
			while(!s.empty()){
				Node n = s.pop();
				
				if(head == null){
					head = n;
				}
				
				if(prev!=null){
					prev.right = n;
					n.left = prev;
				}
				prev = n;
				
				if(n.right!=null){
					s.push(n.right);
					if(n.right.left != null){
						root = n.right.left;
						break;
					}
				}
			}
		}
		return head;		
	}

}
