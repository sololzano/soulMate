import java.io.File;
import java.util.Vector;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
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
				node = db.getNodeById(i);
				String value = (String)node.getProperty(key);
				if (value.equals(val)) {
					tx.success();
					return node;
				}
			}
			tx.success();
		}
		return null;
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
	public void createPerson(String name) {
		Node node = db.createNode(Labels.Person);
		node.setProperty("NAME", name.toUpperCase());		
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
