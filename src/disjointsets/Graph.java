package disjointsets;

public class Graph {

	public int vNo;
	public int eNo;
	public Edge [] edges;
	
	public class Edge{
		int src;
		int dest;
	}
	
	public Graph(int vCount, int eCount){
		vNo = vCount;
		eNo = eCount;
		
		edges = new Edge[eCount];
		
		for(int i=0;i<eCount;i++){
			edges[i] = new Edge();
			edges[i].src = -1;
			edges[i].dest = -1;
		}
	}	
}
