package binarytree;

public class TopView {

	public static void main(String[] args) {
		Node root = BinaryTree.createBinaryTree();
		top_view(root);

	}
	static void top_view(Node root)
	{   
	   top_view(root.left,-1);
	   System.out.print(root.data+" ");
	   top_view(root.right,1);
	}
	static void top_view(Node root, int direction){    
		if(root != null) {
	    if(direction < 0)
	    {
	        top_view(root.left,direction);
	        System.out.print(root.data+" ");
	    }
	    else {
	        System.out.print(root.data+" ");
	        top_view(root.right,direction);
	    }
		}
	    
	}
}
