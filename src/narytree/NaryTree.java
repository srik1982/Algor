package narytree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NaryTree {
	
	/**
	 *   0    1   2   3   4   5   6   7   8
	 * --------------------------------------
	 * | -1 | 0 | 1 | 6 | 6 | 0 | 0 | 2 | 7 |
	 * --------------------------------------
	 * @param arr
	 * @param searchForValue
	 * @return
	 */
	
	public NaryNode constructTree(int[] arr, int searchForValue){
		int foundAtIndex = Integer.MIN_VALUE;
		boolean found = false;
		NaryNode root = null;
		
		for(int i=0;i<arr.length;i++){
			if(arr[i] == searchForValue && !found){
				foundAtIndex = i;
				found = true;
				root = constructNode(i);
				break;
			}
		}	
		if(found){
			int [] indices = matchingIndices(arr,foundAtIndex);
			for(int i=0;i<indices.length;i++){
				root.childNodes.add(constructTree(arr,indices[i]));
			}
		}
		return root;
	}
	
	private int [] matchingIndices(int [] arr, int searchForValue){
		List<Integer> indices = new ArrayList<Integer>();
		for(int i=0;i<arr.length;i++){
			if(arr[i] == searchForValue){
				indices.add(i);
			}
		}
		return indices.stream().mapToInt(Integer::intValue).toArray();
	}
	
	public NaryNode constructNode(int data){
		NaryNode node = new NaryNode();
		node.data = data;
		return node;
	}
	
	public void printTree(NaryNode node){
		printTree(node, 0);
	}
	
	public void printTree(NaryNode node, int depth){
		for(int i=0;i<depth;i++)
			System.out.print(" ");
		
		System.out.println(node.data);
		for(int i = 0; i< node.childNodes.size(); i++){
			printTree(node.childNodes.get(i), depth + 4);
		}
	}
}
