/**
 * 
 */
import org.neo4j.graphdb.Node;
/**
 * @author Hp
 *
 */
public class Person {

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

}
