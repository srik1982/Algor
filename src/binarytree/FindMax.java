package binarytree;

import common.Node;

public class FindMax {

	public static void main(String[] args) {
		Node node = BinaryTree.createBinaryTree();
		System.out.println(FindMax(node));
	}

	private static int FindMax(Node node) {
		int max = Integer.MIN_VALUE;
		if(node != null){			
			System.out.println("Visiting "+node.data);
			int left = FindMax(node.left);
			int right = FindMax(node.right);
			max = getMax(left,right,node.data);
			System.out.println("Max from this subtree is "+max);
			return max;
		}
		return max;
	}
	
	private static int getMax(int x, int y, int z){
		int max = Integer.MIN_VALUE;
		if( x >= y ){
			max = x;
		}
		else {
			max = y;
		}
		if(z>max){
			max = z;
		}
		return max;
	}

}
