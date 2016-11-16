/**
 * @author Luis Diego Sierra, Salvador Recinos, Carlos Solórzano
 * @since 14/11/2016
 */
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
	
	/**
	 * 
	 * @param name Ruta de la base de datos
	 * Construye o abre una base de datos
	 */
	public DBGenerator(String name) {
		factory = new GraphDatabaseFactory();
		database = new File(name);
		db = factory.newEmbeddedDatabase(database);
	}
	
	/**
	 * 
	 * @param key La clave del nodo
	 * @param val El valor del nodo
	 * @return Los nodos coincidentes
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
	 * @param key La clave del nodo
	 * @param val El valor del nodo
	 * @return El nodo encontrado
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
	 * @return true Si se pudo eliminar
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
	 * @return Transacción iniciada
	 */
	public Transaction trans() {
		return db.beginTx();
	}
	
	/**
	 * 
	 * @param name El nombre de la persona
	 * @param genre F = femenino, M = masculino
	 * @return La persona creada
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
	 * @param name El nombre del nodo
	 * @param type El tipo del nodo
	 * @param label La etiqueta del nodo
	 * @return El nodo creado
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
	 * @param origin Nodo origen
	 * @param destiny Nodo destino
	 * @param relationship El tipo de relación implementada
	 */
	public void createRelationship(Node origin, Node destiny, RelationshipType relationship) {
		try (Transaction tx = trans()) {
			origin.createRelationshipTo(destiny, relationship);
			tx.success();
		}
	}
}
