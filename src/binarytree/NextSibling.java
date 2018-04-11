package binarytree;

import java.util.LinkedList;
import java.util.Queue;

public class NextSibling {

	public static void main(String[] args) {
		NextSibling nextSibling = new NextSibling();
		Node root = nextSibling.createTree();
		nextSibling.setSiblings(root);
	}
	
	private class Node {
		int data;
		Node left;
		Node right;
		Node sibling;
	}
	
	private Node createTree(){
		Node root = new Node();
		root.data = 1;
		
		Node temp = new Node();
		temp.data = 2;
		root.left = temp;
		
		temp = new Node();
		temp.data = 3;
		root.right = temp;
		
		temp = new Node();
		temp.data = 4;
		root.left.left=temp;
		
		temp = new Node();
		temp.data = 5;
		root.left.right=temp;
		
		temp = new Node();
		temp.data = 6;
		root.right.left=temp;
		
		temp = new Node();
		temp.data = 7;
		root.right.right=temp;
		
		return root;	
	}
	
	private void setSiblings(Node root){
		
		Queue<Node> q = new LinkedList<Node>();
		q.offer(root);
		
		while(!q.isEmpty()){
			
			Node node = q.poll();
			
			if(node.left != null && node.right != null) {
				node.left.sibling = node.right;
				node.right.sibling = node.left;
				System.out.println(node.left.data +" --> "+node.right.data);
				System.out.println(node.right.data +" --> "+node.left.data);
			}
			
			if(node.left != null)
				q.offer(node.left);
			
			if(node.right != null)
				q.offer(node.right);
			
		}
	}

}
