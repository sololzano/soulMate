/**
 * @author Luis Diego Sierra, Salvador Recinos, Carlos Solórzano
 * @since 14/11/2016
 */
import org.neo4j.graphdb.RelationshipType;

public enum Relationships implements RelationshipType {
	READS, WATCHES, LISTENS, LIKES;
}
