package narytree;

public class BuildNaryTree {

	public static void main(String args[]){
		//				1
		//		2		3		4
		//   5  6  7 | 8 9 10 | 11 12 13
		//5->14,15,16|8->23,24,25|11->32,33,34
		//6->17,18,19|9->26,27,28|12->35,36,37
		//7->20,21,22|10->29,30,31|13>18,39,40
		//preorder array = 1 2 5 6 7 3 8 9 10 4 11 12 13
		int[] preOrderArr = new int[] {1, 2, 5, 14, 15, 16, 6, 17, 18, 19, 7, 20, 21, 22, 3, 8, 23, 24, 25, 9, 
				26, 27, 28, 10, 29, 30, 31, 4, 11, 32, 33, 34, 12, 35, 36, 37, 13, 38, 39, 40};
		NaryNode root = buildTree(preOrderArr, preOrderArr.length,3);
		printPreorder(root);// of course this should just print the same content as preOrderArr
	}
	
	//build a nary tree from preorder traversal
	public static NaryNode buildTree(int [] preorderArr, int numberOfNodes, int k){
		
		//first get depth.
		int depth = findDepth(numberOfNodes,k);
		
		//root node is at index 0 in preorder traversal
		NaryNode root = new NaryNode();
		root.data = preorderArr[0];
		addChildrenRecursively(preorderArr, 1, depth, root,0, k);
		
		return root;
	}
	
	static void addChildrenRecursively(int [] preOrderArr, int currentDepth, int maxDepth, NaryNode parentNode, int parentNodeIndex, int k){		
		//first child is always at parent's index + 1
		int childIndex = parentNodeIndex + 1;
		int depthDiff = maxDepth - currentDepth;
		for(int i=0;i<k;i++){
			int data = preOrderArr[childIndex];
			NaryNode node = new NaryNode(data);
			parentNode.childNodes.add(node);
			if(currentDepth<maxDepth){
				addChildrenRecursively(preOrderArr, currentDepth + 1, maxDepth, node, childIndex, k);
			}
			childIndex = childIndex + getGeometricSum(k, depthDiff);
		}
	}
	
	static int getGeometricSum(int k, int power){
		int sum = 0;
		for(int i=0;i<=power;i++){
			sum += Math.pow(k, i);
		}
		return (int)sum; //assuming indexes are within limits for simplicity
	}

	/**
	 * use geometric series sum to calculate depth of tree
	 * a[k^(d+1)-]
	 * ----------- = sum 
	 *  k - 1
	 *  So,
	 *  a=k^0 = 1 : k is the number of children , its a k-ary tree
	 *  d = depth of the tree
	 *  sum = number of nodes 
	 *  
	 * @param numberOfNodes
	 * @param k
	 * @return
	 */
	private static int findDepth(int numberOfNodes, int k) {
		int sum = numberOfNodes * (k -1);
		sum = sum + 1;
		
		int levels = 0;
		for(int temp=1;temp<sum;temp=temp*k){		
			levels++;
		}
		
		return levels - 1; // depth starts from 0. if there are 4 levels, max depth is 3. 
		
	}
	
	public static void printPreorder(NaryNode root){
		if(root!=null){
			System.out.print(root.data + " ");
			if(root.childNodes!=null){
				for(int i = 0; i< root.childNodes.size();i++){
					printPreorder(root.childNodes.get(i));
				}
			}
		}
	}
}
