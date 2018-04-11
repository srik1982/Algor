package binarytree;

public class DeleteTreeNodes {

	public static void main(String[] args) {
		Node node = BinaryTree.createBinaryTree();
		deleteNodes(node);
	}

	private static void deleteNodes(Node node) {
		if(node != null){
			deleteNodes(node.left);
			deleteNodes(node.right);
			
			node.left = null;
			node.right = null;
			
			System.out.println("setting left and right links to null "+node.data);
			node = null;
		}
	}

}
