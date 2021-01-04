package graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import graph.Person;





public class SocialNetwork implements SocialNetworkADT {
	public Graph g = new Graph();
	List<String>status = new ArrayList<String>();
	public Person CenterUser;
	@Override
	public boolean addFriends(Person f1, Person f2) {
		// TODO Auto-generated method stub
		if(f1 == null||f2 == null) {
			return false;
		}
		if(!(f1 instanceof Person)||!(f2 instanceof Person)) {
			return false;
		}
		if(g.getAdjacentVerticesOf(f1).contains(f2)) {
			return false;
		}
		g.addEdge(f1, f2);
		g.addEdge(f2, f1);
		//status.add("a "+f1.getName()+" "+f1.getName());
		
		return true;
		
	}
	private boolean CheckValidName(String s) throws UnsupportedEncodingException
	{boolean res=true;
	byte[] bytes = s.getBytes("US-ASCII");
		for(int i : bytes) {
		if(!((i>=65&&i<=90)||(i>=97&&i<=122)||(i==95)||(i>=48&&i<=57)||i==39))res=false;
		}
		return res;
		}
	@Override
	public boolean removeFriends(Person f1, Person f2) {
		// TODO Auto-generated method stub
		if(f1 == null||f2 == null) {
			return false;
		}
		if(!(f1 instanceof Person)||!(f2 instanceof Person)) {
			return false;
		}
		if(!g.getAdjacentVerticesOf(f1).contains(f2)) {
			return false;
		}
		g.removeEdge(f1, f2);
		g.removeEdge(f2, f1);
		//	status.add("r "+" "+f1.getName()+" "+f2.getName());
		return true;
	}

	@Override
	public boolean addUser(Person u1) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		if(!(u1 instanceof Person)) {
			return false;
		}
	//	if(CheckValidName(u1.getName())) {
		g.addVertex(u1);
		//status.add("a"+" "+u1.getName());}
		return true;
	}

	@Override
	public boolean removeUser(Person u1) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		if(!(u1 instanceof Person)) {
			return false;
		}
		if(!g.getAllVertices().contains(u1)) {
			return false;
		}
		g.removeVertex(u1);
		//status.add("r "+" "+u1.getName());
		return true;
		
	}

	@Override
	public Set<Person> getFriends(Person u1) {
		// TODO Auto-generated method stub
		if(!(u1 instanceof Person)) {
			return null;
		}
		List<Person> s = g.getAdjacentVerticesOf(u1);
		Set<Person> set = new HashSet<Person>();
		for (Person p: s) {
			set.add(p);
		}
		return set;
	}

	@Override
	public Set<Person> getMutualFriends(Person f1, Person f2) {
		// TODO Auto-generated method stub
		if(f1 == null||f2 == null) {
			return null;
		}
		
		if(!(f1 instanceof Person)||!(f2 instanceof Person)) {
			System.out.println("not valid person! (get mutual)");
			return new HashSet();
		}
		HashSet<Person> result = new HashSet<Person>();
		List<Person> s1 = g.getAdjacentVerticesOf(f1);
		List<Person> s2 = g.getAdjacentVerticesOf(f2);
		for(Person p1 : s1) {
			for(Person p2 : s2) {
				if(p1.equals(p2)) {
					result.add(p1);
				}
			}
		}
		return result;
	}


	@Override
	public Set<String> getShortestPath(Person f1, Person f2,Graph g1) {
		// TODO Auto-generated method stub
		if(f1 == null||f2 == null) {
			return null;
		}
		if(!(f1 instanceof Person)||!(f2 instanceof Person)) {
			System.out.println("not valid person! (get mutual)");
			return new HashSet();
		}
		//constructing the graph to find the shortest path
		int V = g1.getAllVertices().size();
		String src = f1.getName();
		
		// Adjacency list representation of the  
        // connected edges 
        HashMap<String,List<Node> > adj = new HashMap<String,List<Node> >();
     
        // Initialize list for every node 
        for(Person p: g1.getAllVertices()) {
        	List<Node> item = new ArrayList<Node>(); 
            adj.put(p.getName(), item);
        }
        
     // Inputs for the DPQ graph 
        for(List<Person> l: g1.adjList) {
        	for(int i=1;i<l.size();i++) {
        		adj.get(l.get(0).getName()).add(new Node(l.get(i).getName(),1));
        	}
        }
        // Calculate the single source shortest path 
        DPQ dpq = new DPQ(V); 
        dpq.dijkstra(adj, src); 
        
        //right now we have the distance map
        //and parent hashmap
        String dest = f2.getName();
        int cycle = 0;
        Set<String> result = new HashSet<String>();
        while(!dest.equals(f1.getName())) {
        	//check
        	if(cycle > V) {
        		System.out.println("something wrong!!!");
        		break;
        	}
        	cycle++;
        	result.add(dest);
        	dest = dpq.parent.get(dest);
        	
        	
        	
        }
       
		
		return result;
	}

	
	
	
	@Override
	public void loadFromFile(File f1) throws IOException {
		// TODO Auto-generated method stub
	
		BufferedReader br = new BufferedReader(new FileReader(f1));
		String st; 
		  while ((st = br.readLine()) != null) {
			  String[] splited = st.split("\\s+");
			  String instruction = splited[0];
			  switch(instruction) {
			  case "a":
				  if(splited.length == 2) {//only add vertex

						if(CheckValidName(splited[1])) {
							status.add("a"+" "+splited[1]);
					  this.addUser(new Person(splited[1]));}
				  }else if(splited.length == 3){
					  if(CheckValidName(splited[1])&&CheckValidName(splited[2])) {
					  this.addFriends(new Person(splited[1]), new Person(splited[2]));
					  
					  status.add("a"+" "+splited[1]+" "+splited[2]);}
				  }else {
					  System.out.println("invalid input!");
				  }
				  break;
			  case "r":
				  Set<List<Person>> adjList = g.adjList;
				  if(splited.length == 2) {//only remove vertex
					 Set<Person> set = g.getAllVertices();
					 for(Person p: set) {
						 if(p.name.equals(splited[1])){
							 if(CheckValidName(splited[1])) {
									status.add("r"+" "+splited[1]);
							  this.removeUser(new Person(splited[1]));}
						 }
					 }
				  }else if(splited.length == 3){
					  for(List<Person> l: adjList) {
						  if(l.get(0).name.equals(splited[1])) {
							  for(Person pp: l) {
								  if(pp.name.equals(splited[2])) {
									  if(CheckValidName(splited[1])) {
											status.add("r"+" "+l.get(0).getName()+" "+pp.getName());
									g.removeEdge(l.get(0), pp);}
								 
									  
									  break;
								  }
							  }
						  }
					  }
				  }else {
					  System.out.println("invalid input!");
				  }
				  break;
			  case "s":
				  Set<Person> set = g.getAllVertices();
				  for(Person p: set) {
					  if(p.name.equals(splited[1])) {
						  if(CheckValidName(splited[1])) {
								status.add("s"+" "+splited[1]);
						  CenterUser = new Person(splited[1]);
						  System.out.println("Set Center Person Successfully");}
						  break;
					  }
				  }
				 // System.out.println("Center user has problem!!");
				  break;
			  }
		  }
		  br.close();
		  
		
	}


	@Override
	public Set<Graph> getConnectedComponents(Graph g) {
	
		
	
		return new HashSet();
	}

	
	public List<String>  Log(){
		
		return status;
	};
	
	@Override
//	public void saveToFile(File f1) throws IOException {
//		// TODO Auto-generated method stub
//		FileWriter fw = new FileWriter(f1);
//		Set<List<Person>> adjList = g.adjList;
//		for(List<Person> l : adjList) {
//			for(Person p: l) {
//				fw.write(p.name + " ");
//			}
//			fw.write("\n");
//		}
//		System.out.println("Writing Successfully");
//		fw.close();
//	}
	
	public  void saveToFile(File  f1,List<String> status) throws IOException {
		// TODO Auto-generated method stub
		
		FileWriter fw = new FileWriter(f1);
		for(String l : status) {
			
				fw.write(l+ " ");
			
			fw.write("\n");
		}
		System.out.println("Writing Successfully");
		fw.close();
	}
	
	public static void main(String[] args) throws IOException  {
		SocialNetwork sw = new SocialNetwork();
		File file = new File("/Users/BUNNY/eclipse-workspace/HelloFX/src/train");
	//	sw.loadFromFile(file);
//		System.out.println(sw.g.order);
//		System.out.println(sw.g.size);
//		System.out.println(sw.CenterUser.name);
		
	}
	
	
}





















