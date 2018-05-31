package binarytree.bst;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import binarytree.BinaryTree;
import common.Node;

public class Floor {
	
	static int FloorInBST(Node root,int data)
    {
      if(root == null)
      {
          return -1;//when MinNode of tree is greater than the input value
      }
      if(root.data == data) return data;
      
      //go to left
      if(root.data > data)
      {
          return FloorInBST(root.left, data);//MUST be in left subtree 
      }
      else if (root.data < data)
      {
          //MAY be in right subtree OR root itself
          int floor = FloorInBST(root.right, data);
          return (root.data >= floor ? root.data:floor);
      }
      return -1;
    }
	
	public static void main(String args[]){
		
		Node root = BST.createBST();
		BinaryTree.printInorder(root);
		int floor = FloorInBST(root, 5);
		System.out.println("\n"+floor);
	}

	private static <T extends LinkedList<String>> T[] getSomething(){
		List<LinkedList<String>> listOfList = new ArrayList<LinkedList<String>>();
		
		T [] array = null;
		array = listOfList.toArray(array);
		
		return array;
	}
}
