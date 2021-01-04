package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;


//Find the shortest path!!!!
	//implementing dijasktra algorithm to find the shortest path
	public class DPQ {
	    HashMap<String,Integer> dist; 
	    private Set<String> settled; 
	    private PriorityQueue<Node> pq; 
	    private int V; // Number of vertices 
	    HashMap<String,List<Node> > adj; 
	    HashMap<String,String> parent;
	  
	    public DPQ(int V) 
	    { 
	        this.V = V; 
	        dist = new HashMap<String,Integer>();
	        settled = new HashSet<String>(); 
	        pq = new PriorityQueue<Node>(V, new Node());
	        parent = new HashMap<String,String>();
	    } 
	  
	    // Function for Dijkstra's Algorithm 
	    public void dijkstra(HashMap<String,List<Node> > adj, String src) 
	    { 
	        this.adj = adj; 
	        
	        for(Map.Entry<String,List<Node>> entry : adj.entrySet()) {
	        	dist.put(entry.getKey(),Integer.MAX_VALUE);
	        	parent.put(entry.getKey(),null);
	        }
	  
	        // Add source node to the priority queue 
	        pq.add(new Node(src, 0)); 
	  
	        // Distance to the source is 0 
	        dist.replace(src, 0); 
	        //parent of src is src
	        parent.replace(src, src);
	        while (settled.size() != V) { 
	  
	            // remove the minimum distance node  
	            // from the priority queue  
	            String u = pq.remove().node; 
	            // adding the node whose distance is 
	            // finalized 
	            settled.add(u); 
	  
	            e_Neighbours(u); 
	        } 
	    } 
	  
	    // Function to process all the neighbours  
	    // of the passed node 
	    private void e_Neighbours(String u) 
	    { 
	        int edgeDistance = -1; 
	        int newDistance = -1; 
	  
	       
	        for (int i = 0; i < adj.get(u).size(); i++) { 
	            Node v = adj.get(u).get(i); 
	  
	            // If current node hasn't already been processed 
	            if (!settled.contains(v.node)) { 
	                edgeDistance = v.cost; 
	                newDistance = dist.get(u) + edgeDistance; 
	  
	                // If new distance is cheaper in cost 
	                if (newDistance < dist.get(v.node)) {
	                	 dist.replace(v.node, newDistance); 
	                	 parent.replace(v.node,u);
	                }
	                   
	  
	                // Add the current node to the queue 
	                pq.add(new Node(v.node, dist.get(v.node))); 
	            } 
	        } 
	    } 
	
	}
