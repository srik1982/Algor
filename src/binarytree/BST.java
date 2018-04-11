package binarytree;

public class BST {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Node root = createBST();
		insertNode(-1,root);
		printInorder(root);
	}
	
	 static void printInorder(Node node)
	    {
	        if (node == null)
	            return;
	 
	        /* first recur on left child */
	        printInorder(node.left);
	 
	        /* then print the data of node */
	        System.out.print(node.data + " ");
	 
	        /* now recur on right child */
	        printInorder(node.right);
	    }
	 
	public static Node createBST(){
		Node root = new Node();
		root.data = 4;
		
		Node temp = new Node();
		temp.data = 2;
		root.left = temp;
		
		temp = new Node();
		temp.data = 6;
		root.right = temp;
		
		temp = new Node();
		temp.data = 1;
		root.left.left=temp;
		
		temp = new Node();
		temp.data = 3;
		root.left.right=temp;
		
		temp = new Node();
		temp.data = 5;
		root.right.left=temp;
		
		temp = new Node();
		temp.data = 7;
		root.right.right=temp;
		
		Node newRoot = new Node();
		newRoot.data = 8;
		newRoot.left = root;
		
		Node n = new Node();
		n.data = 16;
		newRoot.right = n;
		
		n = new Node();
		n.data = 12;
		newRoot.right.left = n;
		
		n = new Node();
		n.data = 20;
		newRoot.right.right = n;
		
		n = new Node();
		n.data = 11;
		newRoot.right.left.left = n;
		
		n = new Node();
		n.data = 14;
		newRoot.right.left.right = n;
		
		n = new Node();
		n.data = 18;
		newRoot.right.right.left = n;
		
		n = new Node();
		n.data = 22;
		newRoot.right.right.right = n;
		
		newRoot.left = root;
		root = newRoot;
		return root;
	}
	
	public static void insertNode(int data, Node root){
		Node n = new Node();
		n.data = data;
		
		insertNode(n,root);
	}

	private static void insertNode(Node n, Node root) {
		
		
		if(n.data > root.data)
		{
			if( root.right != null)
				insertNode(n,root.right);
			else
				root.right = n;
		}
		else if(n.data < root.data)
		{
			if(root.left != null)
				insertNode(n, root.left);
			else
				root.left = n;
		}
	}
}
