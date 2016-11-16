import java.io.File;
import java.util.Vector;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class DBGenerator {

	private File database;
	private GraphDatabaseFactory factory;
	private GraphDatabaseService db;
	
	public DBGenerator(String name) {
		factory = new GraphDatabaseFactory();
		database = new File(name);
		db = factory.newEmbeddedDatabase(database);
	}
	
	/**
	 * 
	 * @param key
	 * @param val
	 * @return
	 */
	public Vector<Node> getNodes(String key, String val) {
		Node node;
		Vector<Node> nodes = new Vector<Node>();
		try (Transaction tx = trans()){
			for (int i = 0; i < 10000; i++) {
				try {
					node = db.getNodeById(i);
					String value = (String)node.getProperty(key);
					if (value.equals(val)) {
						nodes.add(node);
					}
				} catch (Exception e) {
					
				}
			}
			tx.success();
		}
		return nodes;
	}
	
	/**
	 * 
	 * @param key
	 * @param val
	 * @return
	 */
	public Node getNode(String key, String val) {
		Node node;
		try (Transaction tx = trans()) {
			for (int i = 0; i < 10000; i++) {
				try {
					node = db.getNodeById(i);
					String value = (String)node.getProperty(key);
					if (value.equals(val.toUpperCase())) {
						node = db.getNodeById(i);
						tx.success();
						return node;
					}
				} catch (Exception e) {
					
				}
			}
			tx.success();
		}
		return null;
	}
	
	/**
	 * 
	 * @param node Nodo a eliminar
	 */
	public boolean deleteNode(Node node) {
		try {
			Transaction tx = trans();
			Iterable<Relationship> iterator = node.getRelationships();
			for (Relationship relation : iterator) {
				relation.delete();
			}
			node.delete();
			tx.success();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public Transaction trans() {
		return db.beginTx();
	}
	
	/**
	 * 
	 */
	public Node createPerson(String name, String genre) {
		try (Transaction tx = trans()) {
			Node node = db.createNode(Labels.Person);
			node.setProperty("NAME", name.toUpperCase());
			String id = name.substring(0, 3) + 
					name.substring(name.length()-3) +
					node.getId();
			node.setProperty("USER", id);
			node.setProperty("GENRE", genre);
			tx.success();
			return node;
		}
	}
	
	/**
	 * 
	 * @param name
	 * @param type
	 * @param label
	 */
	public Node createNode(String name, String type, Label label) {
		try (Transaction tx = trans()) {
			Node node = db.createNode(label);
			node.setProperty("name", name);
			node.setProperty("TYPE", type);
			tx.success();
			return node;
		}
	}
	/**
	 * 
	 * @param origin
	 * @param destiny
	 * @param relationship
	 */
	public void createRelationship(Node origin, Node destiny, RelationshipType relationship) {
		try (Transaction tx = trans()) {
			origin.createRelationshipTo(destiny, relationship);
			tx.success();
		}
	}
}
