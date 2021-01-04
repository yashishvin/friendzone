package graph;

import java.util.Comparator;

//import graph.SocialNetwork.Node;

//Class to represent a node in the graph 
	  public  class Node implements Comparator<Node> { 
	        public String node; 
	        public int cost; 
	      
	        public Node() 
	        { 
	        } 
	      
	        public Node(String node, int cost) 
	        { 
	            this.node = node; 
	            this.cost = cost; 
	        } 
	      
	        @Override
	        public int compare(Node node1, Node node2) 
	        { 
	            if (node1.cost < node2.cost) 
	                return -1; 
	            if (node1.cost > node2.cost) 
	                return 1; 
	            return 0; 
	        } 
	    } 
