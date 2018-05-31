package binarytree;

import common.Node;

public class LCAWithoutStack {

	public static void main(String[] args) {
		Node root = BinaryTree.createBinaryTree();
		System.out.println(findLCA(root,5,8));
	}

	private static Node findLCA(Node root, int i, int j) {
	
		if(root != null){
			
			if(root.data == i || root.data == j)
			{
				return root;
			}
			
			Node n1 = null, n2 = null;
			if(root.left != null)
				n1 = findLCA(root.left,i,j);
			if(root.right != null)
				n2 = findLCA(root.right,i,j);
			
			if(n1 != null || n2 != null){
				return root;				
			}else {
				return null;
			}
		}
		return null;
	}

}
