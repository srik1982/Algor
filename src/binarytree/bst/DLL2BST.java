package binarytree.bst;

import common.Node;

import linkedlists.DLL;

public class DLL2BST {

	public static void main(String[] args) {
		Node head = DLL.createSortedDLL();
		Node root = createBST(head,null);
		BST.printInorder(root);
	}
	
	private static Node clone(Node n){
		Node n1 = new Node();
		n1.data = n.data;
		n1.left = n.left;
		n1.right = n.right;
		return n1;
	}
	
	private static Node createBST(Node head, Node tail){		
		if(head == null && tail == null){
			return null;
		}else if(head != null && tail == null && head.right==null){
			head.left = head.right = null;
			return head;
		}else if(head == null && tail!=null && tail.left==null){
			tail.left = tail.right = null;
			return tail;
		}else if(head == tail){
			head.left = head.right = null;
			return head;
		}else if(tail!=null && head.right == tail && tail.left == head){ 
			head.left = null;
			tail.left = tail.right = null;
			return head;
		}
		Node middleNode = findMiddle(head, tail);
		if(middleNode!=null){
			middleNode.right = createBST(middleNode.right,tail);
			middleNode.left = createBST(head,middleNode.left);
		}
		return middleNode;
	}
	
	private static Node findMiddle(Node start, Node end){
		Node p1 = null, p2 = null;
		
		if(start!=null){
			p1 = start;
			p2 = start.right;
			
			while(p2!=null && p2!=end && p2.right!=null){
				p1 = p1.right;
				p2 = p2.right!=null? p2.right.right : null;
			}
			return p1;
		}
		else{
			return null;
		}
	}

}
