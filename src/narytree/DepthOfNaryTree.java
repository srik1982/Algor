package narytree;

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
		NaryNode root = tree.constructTree(arr, -1);
		
		tree.printTree(root);
	}

}
