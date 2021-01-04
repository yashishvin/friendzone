package Graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 * Filename:    SocialNetworkADT.java
 * Project:     Social Network
 * Authors:     Zhihao Shu
 *              Han Lintong
 *              Lakshay Goyal
 *              Sai Teja
 * Email: 	zshu9@wisc.edu
 *              lhan48@wisc.edu
 *              lgoyal@wisc.edu
 *              chokkarapu@wisc.edu
 * Directed and unweighed graph implementation
 */

public class Graph implements GraphADT {
	
	int size; //number of edges
	Set<List> adjList; 
	int order; // number of vertices
	
	
	/*
	 * Default no-argument constructor
	 */ 
	public Graph() {
		this.size = 0;
		this.order = 0;
		adjList = new HashSet<List>();
		for(int i = 0; i<order; i++) {
			adjList.add(new LinkedList<String>());
		}
		
	}

	/**
     * Add new vertex to the graph.
     *
     * If vertex is null or already exists,
     * method ends without adding a vertex or 
     * throwing an exception.
     * 
     * Valid argument conditions:
     * 1. vertex is non-null
     * 2. vertex is not already in the graph 
     */
	public void addVertex(String vertex) {
		
			if(vertex == null) {
				return;
			}
			for(List<String> v: adjList) {
				if(v.get(0).equals(vertex)) {
					return;
				}
			}
			//adding new vertex
			LinkedList<String> newlist = new LinkedList<String>();
			newlist.add(vertex);
			adjList.add(newlist);
			order++;//increasing order
	}

	/**
     * Remove a vertex and all associated 
     * edges from the graph.
     * 
     * If vertex is null or does not exist,
     * method ends without removing a vertex, edges, 
     * or throwing an exception.
     * 
     * Valid argument conditions:
     * 1. vertex is non-null
     * 2. vertex is not already in the graph 
     */
	public void removeVertex(String vertex) {
		try {
			if(vertex == null) {
				return;
			}
			boolean exsit = false;
			for(List<String> v: adjList) {
				if(v.get(0).equals(vertex)) {
					exsit = true;
				}
			}
			if(!exsit) {			
				return;
			}
			
			//set a new HashSet for removing
			Set<List> newlist = new HashSet<List>();
			newlist.addAll(adjList);
			
			
			
			//removing
			for(List<String> v: adjList) {
				//if the first one is the vertex remove the whole list
				if(v.get(0).equals(vertex)) {
					this.size = size - v.size()+1;
					newlist.remove(v);
					this.order--;
				}
			}
			//remove the vertex from every other list
			for(List<String> nv: newlist) {
				for(int i=0; i<nv.size();i++) {
					if(nv.get(i).equals(vertex)) {
						nv.remove(i);
						this.size--;
					}
				}
			}
			
			adjList = newlist;
			
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("fail to remove");
		}
		
		
	}

	/**
     * Add the edge from vertex1 to vertex2
     * to this graph.  (edge is directed and unweighted)
     * If either vertex does not exist,
     * add vertex, and add edge, no exception is thrown.
     * If the edge exists in the graph,
     * no edge is added and no exception is thrown.
     * 
     * Valid argument conditions:
     * 1. neither vertex is null
     * 2. both vertices are in the graph 
     * 3. the edge is not in the graph
	 */
	public void addEdge(String vertex1, String vertex2) {
		//chech v1 and v2 are not null
		if(vertex1 == null || vertex2 == null) {
			return;
		}
		//check if the edge already exist
		try {
			for(List v: adjList) {
				if(v.get(0).equals(vertex1)) { //not sure if the vertex2 is exist???
					for(int i=0; i<v.size();i++) {
						if(v.get(i).equals(vertex2)) {
							//throw new Exception();
							return;
							}
					}
				}
			}
		}
		catch (Exception e) {
			System.out.println("edge already exsit");
		}
		//if v1 or v2 does not exist
		boolean hasv1 = false;
		boolean hasv2 = false;
		for(List v: adjList) {
			if(v.get(0).equals(vertex1)) {
				hasv1 = true;
			}
			if(v.get(0).equals(vertex2)) {
				hasv2 = true;
			}
		}
		//both does not exist
		if(!hasv1 && !hasv2) {
			LinkedList l1 = new LinkedList();
			l1.add(vertex1);
			LinkedList l2 = new LinkedList();
			l2.add(vertex2);
			//add v2 to v1 list
			l1.add(vertex2);
			size++;
			//add to adj
			adjList.add(l1);
			order++;
			adjList.add(l2);
			order++;
		//have vertex1 but not vertex2
		}else if(hasv1 && !hasv2) {
			LinkedList l2 = new LinkedList();
			l2.add(vertex2);
			adjList.add(l2);
			order++;
			for(List v: adjList) {
				if(v.get(0).equals(vertex1)) {
					v.add(vertex2);
					size++; //be careful of duplicate v1
				}
			}
		//have vertex 2 but not vertex 1
		}else if(!hasv1 && hasv2) {
			LinkedList l1 = new LinkedList();
			l1.add(vertex1);
			l1.add(vertex2);
			size++;
			adjList.add(l1);
			order++;
		//both vertex exist and no duplicate edge
		}else if(hasv1 && hasv2) {
			for(List v: adjList) {
				if(v.get(0).equals(vertex1)) {
					v.add(vertex2);
					size++;
				}
			}
		}
	}
	
	/**
     * Remove the edge from vertex1 to vertex2
     * from this graph.  (edge is directed and unweighted)
     * If either vertex does not exist,
     * or if an edge from vertex1 to vertex2 does not exist,
     * no edge is removed and no exception is thrown.
     * 
     * Valid argument conditions:
     * 1. neither vertex is null
     * 2. both vertices are in the graph 
     * 3. the edge from vertex1 to vertex2 is in the graph
     */
	public void removeEdge(String vertex1, String vertex2) {
		if(vertex1 == null || vertex2 == null) { //if either v1 or v2 is null, return
			return;
		}
		boolean hasv1 = false;
		boolean hasv2 = false;
		for(List v: adjList) {
			if(v.get(0).equals(vertex1)) {
				hasv1 = true;
			}
			if(v.get(0).equals(vertex2)) {
				hasv2 = true;
			}
		}
		//if either one of v1 or v2 does not in the graph, return
		if(!hasv1 || !hasv2) {
			return;
		}
		boolean hasedge = false;
		for(List l: adjList) {
			if(l.get(0).equals(vertex1)) {
				//start from index 1
				for(int i=1;i<l.size();i++) {
					if(l.get(i).equals(vertex2)) {
						hasedge = true;
					}
				}
				if(!hasedge) {
					return;
				}else {
					l.remove(vertex2);
					size--;
				}
			}
		}
	}	

	/**
     * Returns a Set that contains all the vertices
     * 
	 */
	public Set<String> getAllVertices() {
		Set<String> listofV = new HashSet<String>();
		for(List<String> v: adjList) {
			listofV.add(v.get(0));
		}
		return listofV;
	}

	/**
     * Get all the neighbor (adjacent) vertices of a vertex
     *
	 */
	public List<String> getAdjacentVerticesOf(String vertex) {
		List<String> adjV = new LinkedList<String>();
		for(List<String> vl: adjList) {
			//locate that vertex
			if(vl.get(0).equals(vertex)) {
				//add the adj vertices
				for(String a: vl) {
					//System.out.println(vl.size());
					if(!a.equals(vertex)) {
						//System.out.println(a + "sssss");
						adjV.add(a);
					}
				}
			}
		}
		//System.out.println(adjV);
		return adjV;
	}
	
	/**
     * Returns the number of edges in this graph.
     */
    public int size() {
        return size;
    }

	/**
     * Returns the number of vertices in this graph.
     */
	public int order() {
        return order;
    }
}
