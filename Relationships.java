import org.neo4j.graphdb.RelationshipType;

public enum Relationships implements RelationshipType {
	READS, WATCHES, LISTENS, LIKES;
}
