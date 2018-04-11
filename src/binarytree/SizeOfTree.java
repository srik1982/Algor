package binarytree;

import java.util.LinkedList;
import java.util.Queue;

public class SizeOfTree {

	public static void main(String[] args) {
		Node node = BinaryTree.createBinaryTree();
		System.out.println(sizeOf(node));
	}

	private static int sizeOf(Node node) {
		Queue<Node> q = new LinkedList<Node>();
		q.offer(node);
		int size = 0;
		while(!q.isEmpty()){
			node = q.poll();
			size++;
			if(node.left != null)
				q.offer(node.left);
			if(node.right != null)
				q.offer(node.right);
		}
		return size;
	}
}
