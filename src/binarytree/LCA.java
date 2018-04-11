package binarytree;

import java.util.Stack;

public class LCA {

	public static void main(String[] args) {
		Node root = BinaryTree.createBinaryTree();
		BinaryTree.printTreeLevelOrder(root);
		System.out.println(findLCA(root,5,3));
	}
	private static int findLCA(Node root, int d1, int d2){
		
		Stack<Node> s1 = new Stack<Node>(), s2 = new Stack<Node>();
		boolean found1 = findLCA(root,d1,s1);
		boolean found2 = findLCA(root,d2,s2);
		
//		int data = Integer.MIN_VALUE;
		if(found1 && found2)
		{
			while(!s1.isEmpty() && !s2.isEmpty() && !s1.peek().equals(s2.peek())){
				if(!s1.empty())
					s1.pop();
				if(!s2.empty())
					s2.pop();
			}
			return s1.isEmpty() ? Integer.MIN_VALUE : s1.pop().data;
		}else{
			return -1;
		}
		
		
	}
	private static boolean findLCA(Node root, int data, Stack<Node> s){
		if(root != null){
			s.push(root);
			if(root.data == data){
				return true;
			}else {
				boolean retVal = false;
				if(root.left != null)
					retVal = findLCA(root.left,data,s);
				if(!retVal && root.right != null)
					retVal = findLCA(root.right,data,s);
				
				if(!retVal)
				{
					s.pop();
					return false;
				}
				return true;
			}
		}
		return false;
	}

}
