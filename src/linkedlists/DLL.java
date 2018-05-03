package linkedlists;

import common.Node;

public class DLL {

	public static Node createSortedDLL(){
		Node root = new Node();
		root.data = 1;
		
		Node prev = root;
		for(int i=2;i<7;i++){
			Node n = new Node();
			n.data = i;
			prev.right = n;
			n.left = prev;
			prev = n;
		}
		
		return root;
	}
}
