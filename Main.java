import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

public class Main{

	private static Vector<Node> men = new Vector<Node>();
	private static Vector<Node> women = new Vector<Node>();
	private static Vector<Node> books = new Vector<Node>();
	private static Vector<Node> music = new Vector<Node>();
	private static Vector<Node> series = new Vector<Node>();
	private static Vector<Node> movies = new Vector<Node>();
	
	private static JFrame frame;
	
	/**
	 * Constructor
	 */
	public Main () {
		frame = new JFrame("MySoulMate");
		frame.setSize(600, 600);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
	}
	
	/**
	 * 
	 */
	private void showWindow() {
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				frame.dispose();
			}
		});
		frame.setVisible(true);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Main window = new Main();
		window.showWindow();
		
		DBGenerator db = new DBGenerator("SoulDB");
		System.out.println("Conexión exitosa...");
		try (Transaction tx = db.trans()) {
			men = db.getNodes("GENRE", "M");
			women = db.getNodes("GENRE", "F");
			books = db.getNodes("TYPE", "BOOKS");
			music = db.getNodes("TYPE", "MUSIC");
			series = db.getNodes("TYPE", "SERIES");
			movies = db.getNodes("TYPE", "MOVIES");
			
			
			tx.success();
			JOptionPane.showMessageDialog(frame, "Se ha realizado la conexion con la base de datos");
		}	
	}
}
