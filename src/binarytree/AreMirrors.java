package binarytree;

public class AreMirrors {

	public static void main(String[] args) {
		Node root = BinaryTree.createBinaryTree();
		Node copy = BinaryTree.deepCopyTree(root);
		Mirror.mirror(copy);
		BinaryTree.printTreeLevelOrder(root);
		BinaryTree.printTreeLevelOrder(copy);
		System.out.println(_areMirrors(root, copy));
	}
	
	private static boolean _areMirrors(Node root1, Node root2){
		if(root1 == null && root2 == null)
			return true;
		
		if(root1 == null || root2 == null)
			return false;
		
		boolean contentSame = root1.data == root2.data;
		
		if(contentSame){
			return _areMirrors(root1.left , root2.right);
		}else{
			return false;
		}
	}
	
	
	
}
