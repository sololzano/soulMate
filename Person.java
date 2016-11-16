/**
 * @author Luis Diego Sierra, Salvador Recinos, Carlos Solórzano
 * @since 14/11/2016
 */
import org.neo4j.graphdb.Node;
public class Person implements Comparable<Person>{

	private Node node;
	private int occurrences;
	
	public Person(Node node) {
		this.node = node;
		occurrences = -1;
	}
	
	/**
	 * Incrementa en uno el valor del nodo
	 */
	public void incOccurrences() {
		occurrences++;
	}
	
	/**
	 * 
	 * @return El valor del nodo
	 */
	public int getOcurrences() {
		return occurrences;
	}
	
	/**
	 * 
	 * @return El nombre de la persona
	 */
	public String getName() {
		return (String) node.getProperty("NAME");
	}
	
	/**
	 * 
	 * @return El user del nodo
	 */
	public String getUser() {
		return (String) node.getProperty("USER");
	}
	
	/**
	 * 
	 * @return El nodo
	 */
	public Node getNode() {
		return this.node;
	}

	@Override
	public int compareTo(Person person) {
		return (person.getOcurrences() - this.getOcurrences());
	}

}
