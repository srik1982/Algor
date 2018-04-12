package narytree;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;

public class DepthOfNaryTree {

	/**
	 *   0    1   2   3   4   5   6   7   8
	 * --------------------------------------
	 * | -1 | 0 | 1 | 6 | 6 | 0 | 0 | 2 | 7 |
	 * --------------------------------------
	 * @param arr
	 * @param searchForValue
	 * @return
	 */
	public static void main(String[] args) {
		NaryTree tree = new NaryTree();
		int [] arr = new int[]{-1,0,1,6,6,0,0,2,7};
		int [] depthArr = new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1};
		findDepth(arr,-1,depthArr);
		Arrays.stream(depthArr).max().ifPresent(p->System.out.println(p));
				
				
//		NaryNode root = tree.constructTree(arr, -1);
		
//		tree.printTree(root);
	}

	private static void findDepth(int[] arr, int searchElem, int[] depthArr) {
		for(int i=0;i<arr.length;i++){
			if(arr[i] == searchElem){
				if(searchElem == -1){
					depthArr[i] = 0;					
				}else{
					// we are searching for 0 and we find it at i=1
					// arr[i] = 0
					// what is depth[0] ? depth[0] = 0. we want 0 + 1
					// ie.,arr[arr[i]]+1
					int depth = depthArr[arr[i]] + 1;
					depthArr[i] = depth;					
				}
				findDepth(arr,i,depthArr);
			}
		}
		
	}

}
