package binarytree;

import java.util.LinkedList;
import java.util.Queue;

import common.Node;

public class LevelOrderTraversal {

	public static void main(String[] args) {
		Node root = BinaryTree.createBinaryTree();
		traverse(root);
	}

	private static void traverse(Node root) {
		Queue<Node> q = new LinkedList<Node>();
		q.offer(root);
		while(!q.isEmpty()){
			Node n = q.poll();
			System.out.println(n.data);
			
			if(n.left !=null)
				q.offer(n.left);
			if(n.right != null)
				q.offer(n.right);
		}
		
	}

}
