package graph;

public class Person implements Comparable<Person> {
	public String name;
	
	public String getName() {
		return name;
	}

	public Person(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(Person o) {
		// TODO Auto-generated method stub
		if(this.name.equals( o.name)) return 1; 
	   
	    else                   return 0;
	}
}
