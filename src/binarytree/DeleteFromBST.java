package binarytree;

import java.util.Stack;

public class DeleteFromBST {

	public static void main(String[] args) {
		Node root = BST.createBST();
		delete(root,2,null);
	}
	/**
	 * 				|------------ 8	------------|
	 * 				4							16
	 * 			2		6				12				20
	 * 		1	 3    5		7		11		14		18		22
	 *   
	 */
	
	/**
	 * OK how do we delete?
	 * 1) leaf node. Just set the parent node -> right/left to null
	 * 2) has 1 child tree. copy the child's node content to parent node . then call delete recursively
	 * 3) Both children. Find inorder successor. copy the data of inorder succesor to the node to be deleted. Then call deleteSingleChild(childNode)
	 *    
	 * preorder traversal
	 * when the node is found, based the case, we can either delete that node or call some method recursively
	 * @param root
	 * @param i
	 */
	// Totally inefficient solution
	// how many stacks are there? Time complexity is to much.
	// can't we link findNode to inOrderSuccessor? we can, just use in order traversal and stop when you find inorder successor of the given node.
	private static void delete(Node root, int i, Node parent) {
		if(root == null)
			return;
		
		if(i > root.data){
			delete(root.right,i,root);			
		}else if(i<root.data){
			delete(root.left,i,root);
		}else {
			//Leaf Node - case 1
			if(isLeaf(root)){
				if(parent != null && parent.right == root){
					parent.right = null;
					System.out.println("deleting "+root);
				}else if(parent != null && parent.left == root){
					parent.left = null;
					System.out.println("deleting "+root);
				}
			}
			else if(hasSingleChild(root)){
				int childData = root.right == null ? root.left.data : root.right.data;
				root.data = childData;
				System.out.println("copying "+childData+" to "+root);
				delete((root.right == null ? root.left : root.right),childData,root);
			}
			else {				
				Node[] node = minValue(root.right,root);
				System.out.println("copying "+node[0]+" to "+root);
				root.data = node[0].data;				
				delete(node[0],node[0].data,node[1]);
			}
		}
	}
	
	static Node[] minValue(Node rightChild,Node parent) {
		Node[] node = new Node[2]; 
		node[0] = rightChild;
		node[1] = parent;
		while(node[0].left != null){
			node[1] = node[0];
			node[0] = node[0].left;
		}
		return node;
	}
	
	static boolean isLeaf(Node node){
		return node.right == null && node.left == null;
	}
	
	static boolean hasSingleChild(Node node){
		return node.right != null && node.left == null || node.right == null && node.left != null;
	}

	private static Node deleteLeafNode(Node retVal, Stack<Node> parents,
			Node node) {
		if(!parents.isEmpty()){ // if parents is empty, this means the root node itself doesn't have any children and this is the node to delete
			Node parent = parents.pop();
			if(parent.right == node){
				parent.right = null;
			}else {
				parent.left = null;
			}
		}else{
			retVal = null;
		}
		return retVal;
	}

	private static Node findNode(Node root, int i,Stack<Node> parents) {
		// TODO Auto-generated method stub
		return null;
	}

}
