package binarytree;

import java.util.LinkedList;
import java.util.Queue;

import common.Node;

public class FindMaxUsingLevelOrder {

	public static void main(String[] args) {
		Node node = BinaryTree.createBinaryTree();
		System.out.println(FindMax(node));
	}

	private static int FindMax(Node node) {
		Queue<Node> q = new LinkedList<Node>();
		q.offer(node);
		int max = Integer.MIN_VALUE;
		while(!q.isEmpty()){
			node = q.poll();
			
				if(node.data > max){
					max = node.data;
				}
				if(node.left != null)
					q.offer(node.left);
				if(node.right != null)
					q.offer(node.right);
		}
		return max;
	}

}
