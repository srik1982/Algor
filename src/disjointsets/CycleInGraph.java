package disjointsets;

public class CycleInGraph {

	public static void main(String[] args) {
		/* Let us create following graph
        0
       |  \
       |    \
       1-----2 */
		 int V = 3, E = 2;
	        Graph graph = new Graph(V, E);
	 
	        // add edge 0-1
	        graph.edges[0].src = 0;
	        graph.edges[0].dest = 1;
	 
	        // add edge 1-2
	        graph.edges[1].src = 1;
	        graph.edges[1].dest = 2;
	 
	        // add edge 0-2
//	        graph.edges[2].src = 0;
//	        graph.edges[2].dest = 2;
	 
	        if (DisjointSet.isCycle(graph))
	            System.out.println( "graph contains cycle" );
	        else
	            System.out.println( "graph doesn't contain cycle" );

	}

}
