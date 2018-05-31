package disjointsets;

import java.util.Arrays;

public class DisjointSet {
	int [] parent, rank;
	int n;
	
	public DisjointSet(int noOfElements){
		n = noOfElements;
		parent = new int[n];
		rank = new int[n];
		
		for(int i=0;i<n;i++){
			parent[i] = i;
		}
	}
	
	public int find(int x){
		if(parent[x] == x){
			return x;
		}
		int result = find(parent[x]);
		
		parent[x] = result;
		
		return result;
	}
	
	public void union(int i, int j){
		
		int iRep = find(i);
		int jRep = find(j);
		
		if(iRep != jRep){
//			parent[iRep] = jRep;
//			System.out.println("setting "+iRep+" parent to "+jRep);
			int iRank = rank[iRep];
			int jRank = rank[jRep];
			
			if(iRank == jRank){
				parent[iRep] = jRep;
				rank[jRep]++;
			}else if(iRank > jRank){
				parent[jRep] = iRep;
			}else{
				parent[iRep] = jRep;
			}
		}
	}
	
	public void printSet(){
		
		for(int i=0;i<n;i++)
			System.out.print(i+" | ");
		
		System.out.println();
		
		for(int i=0;i<n;i++)
			System.out.print(parent[i]+" | ");
	}
	
	public static boolean isCycle(Graph graph){
		DisjointSet set = new DisjointSet(graph.vNo);
		
		for(Graph.Edge edge : graph.edges){
			int i = edge.src;
			int j = edge.dest;
			
			int iRep = set.find(i);
			int jRep = set.find(j);
			
			if(iRep == jRep) return true;
			
			set.union(i, j);
		}
		return false;
		
	}
	

	public static void main(String[] args) {
		DisjointSet set = new DisjointSet(10);
		set.union(1, 3);
		set.union(1, 4);
		set.union(2, 5);
		set.union(3, 6);
		set.union(3, 7);
		
		set.printSet();
	}

}
