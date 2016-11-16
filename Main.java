/**
 * @author Luis Diego Sierra, Salvador Recinos, Carlos Solórzano
 * @since 14/11/2016
 */

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;

public class Main {

	// Vectores
	private static Vector<Node> men = new Vector<Node>();
	private static Vector<Node> women = new Vector<Node>();
	private static Vector<Person> found = null;
	
	// Componentes GUI
	private static JTextField lblUser, lblMusic, lblBooks,
	lblSeries, lblMovies;
	private JLabel lblSUser, lblSMusic, lblSBooks, 
	lblSSeries, lblSMovies;
	private JLabel lblParams;
	private static JCheckBox checkMan, checkWoman;
	private JLabel lblPMusic, lblPBooks, lblPSeries, lblPMovies;
	private static JTextField txtPMusic, txtPBooks, txtPSeries, txtPMovies;
	private static JButton btnNext, btnPrevious, btnSearch;
	private JLabel lblDUser;
	private static JTextField txtDUser;
	private static JButton btnDelete;
	private static JButton btnExit;
	private static JButton btnAdd;
	private JLabel lblAName, lblAMusic, lblABooks, 
	lblASeries, lblAMovies;
	private static JTextField txtAName, txtAMusic, txtABooks,
	txtASeries, txtAMovies;
	private static JRadioButton rbWoman, rbMan;
	
	private static JFrame frame;
	
	// Variables
	private static int index, maxNumber;
	private static DBGenerator db;
	
	/**
	 * Constructor
	 */
	public Main () {
		frame = new JFrame("ChafaTinder");
		frame.setSize(600, 710);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		maxNumber = 0;
		index = 0;
	}
	
	/**
	 * 
	 */
	private void showWindow() {
		// Estilos de fuentes
		Font resus = new Font("Courier New", Font.BOLD, 12);
		Font labels = new Font("Arial", Font.BOLD, 14);
		
		// Inicializar componentes
		lblUser = new JTextField("");
		lblUser.setFont(resus);
		lblUser.setEnabled(false);
		lblMusic = new JTextField("");
		lblMusic.setFont(resus);
		lblMusic.setEnabled(false);
		lblBooks = new JTextField("");
		lblBooks.setFont(resus);
		lblBooks.setEnabled(false);
		lblSeries = new JTextField("");
		lblSeries.setFont(resus);
		lblSeries.setEnabled(false);
		lblMovies = new JTextField("");
		lblMovies.setEnabled(false);
		lblMovies.setFont(resus);
		lblSUser = new JLabel("Usuario:    ");
		lblSMusic = new JLabel("Música:     ");
		lblSBooks = new JLabel("Libros:        ");
		lblSSeries = new JLabel("Series:        ");
		lblSMovies = new JLabel("Películas:  ");
		lblParams = new JLabel("Parámetros de Búsqueda");
		lblParams.setFont(labels);
		checkMan = new JCheckBox("Hombres");
		checkMan.setEnabled(false);
		checkWoman = new JCheckBox("Mujeres");
		checkWoman.setEnabled(false);
		lblPMusic = new JLabel("Música:   ");
		lblPBooks = new JLabel("Libros:     ");
		lblPSeries = new JLabel("Series:     ");
		lblPMovies = new JLabel("Películas:");
		txtPMusic = new JTextField();
		txtPBooks = new JTextField();
		txtPSeries = new JTextField();
		txtPMovies = new JTextField();
		btnNext = new JButton("Siguiente");
		btnNext.setEnabled(false);
		btnPrevious = new JButton("Anterior");
		btnPrevious.setEnabled(false);
		btnSearch = new JButton("Buscar");
		btnSearch.setEnabled(false);
		btnDelete = new JButton("Eliminar");
		btnDelete.setEnabled(false);
		lblDUser = new JLabel("Usuario:");
		txtDUser = new JTextField();
		lblAName = new JLabel("Nombre: ");
		lblAMusic = new JLabel("Música:  ");
		lblABooks = new JLabel("Libros:    ");
		lblASeries = new JLabel("Series:    ");
		lblAMovies = new JLabel("Películas:");
		txtAName = new JTextField();
		txtAMusic = new JTextField();
		txtABooks = new JTextField();
		txtASeries = new JTextField();
		txtAMovies = new JTextField();
		rbWoman = new JRadioButton("Mujer");
		rbMan = new JRadioButton("Hombre");
		rbWoman.setSelected(true);
		ButtonGroup buttons = new ButtonGroup();
		buttons.add(rbMan);
		buttons.add(rbWoman);
		rbMan.setEnabled(false);
		rbWoman.setEnabled(false);
		btnAdd = new JButton("Agregar");
		btnAdd.setEnabled(false);
		
		// Inicializar botones
		btnExit = new JButton("Salir");
		btnExit.setPreferredSize(new Dimension(100, 20));
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
			
		});
		
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();		
		
		JPanel mainPanel = new JPanel(layout);
		JPanel searchPanel = new JPanel();
		JPanel addPanel = new JPanel();
		JPanel delPanel = new JPanel();

		searchPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(),"Búsqueda"));
		searchPanel.setPreferredSize(new Dimension(590, 350));
		addPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(),"Agregar"));
		addPanel.setPreferredSize(new Dimension(590, 200));
		delPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(),"Eliminar"));
		delPanel.setPreferredSize(new Dimension(590, 100));
		
		// Search Panel
		GroupLayout groupSearch = new GroupLayout(searchPanel);
		searchPanel.setLayout(groupSearch);
		groupSearch.setAutoCreateContainerGaps(true);
		groupSearch.setAutoCreateGaps(true);
		
		// Add Panel
		GroupLayout groupAdd = new GroupLayout(addPanel);
		addPanel.setLayout(groupAdd);
		groupAdd.setAutoCreateContainerGaps(true);
		groupAdd.setAutoCreateGaps(true);
		
		// Delete Panel
		GroupLayout groupDelete = new GroupLayout(delPanel);
		delPanel.setLayout(groupDelete);
		groupDelete.setAutoCreateContainerGaps(true);
		groupDelete.setAutoCreateGaps(true);
		
		// Agregar componentes a panel búsqueda
		groupSearch.setHorizontalGroup(groupSearch.createParallelGroup()
				.addGroup(GroupLayout.Alignment.LEADING, groupSearch.createSequentialGroup()
						.addComponent(lblSUser)
						.addComponent(lblUser))
				.addGroup(GroupLayout.Alignment.LEADING, groupSearch.createSequentialGroup()
						.addComponent(lblSMusic)
						.addComponent(lblMusic))
				.addGroup(GroupLayout.Alignment.LEADING, groupSearch.createSequentialGroup()
						.addComponent(lblSBooks)
						.addGap(10)
						.addComponent(lblBooks))
				.addGroup(GroupLayout.Alignment.LEADING, groupSearch.createSequentialGroup()
						.addComponent(lblSSeries)
						.addGap(10)
						.addComponent(lblSeries))
				.addGroup(GroupLayout.Alignment.LEADING, groupSearch.createSequentialGroup()
						.addComponent(lblSMovies)
						.addComponent(lblMovies))
				.addGroup(GroupLayout.Alignment.CENTER, groupSearch.createSequentialGroup()
						.addComponent(lblParams))
				.addGroup(GroupLayout.Alignment.LEADING, groupSearch.createSequentialGroup()
						.addComponent(checkWoman)
						.addComponent(checkMan))
				.addGroup(GroupLayout.Alignment.LEADING, groupSearch.createSequentialGroup()
						.addComponent(lblPMusic)
						.addComponent(txtPMusic))
				.addGroup(GroupLayout.Alignment.LEADING, groupSearch.createSequentialGroup()
						.addComponent(lblPBooks)
						.addComponent(txtPBooks))
				.addGroup(GroupLayout.Alignment.LEADING, groupSearch.createSequentialGroup()
						.addComponent(lblPSeries)
						.addComponent(txtPSeries))
				.addGroup(GroupLayout.Alignment.LEADING, groupSearch.createSequentialGroup()
						.addComponent(lblPMovies)
						.addComponent(txtPMovies))
				.addGroup(GroupLayout.Alignment.CENTER, groupSearch.createSequentialGroup()
						.addComponent(btnSearch)
						.addComponent(btnNext)
						.addComponent(btnPrevious))
				);
		
		groupSearch.setVerticalGroup(groupSearch.createSequentialGroup()
				.addGroup(groupSearch.createParallelGroup()
						.addComponent(lblSUser)
						.addComponent(lblUser))
				.addGroup(groupSearch.createParallelGroup()
						.addComponent(lblSMusic)
						.addComponent(lblMusic))
				.addGroup(groupSearch.createParallelGroup()
						.addComponent(lblSBooks)
						.addComponent(lblBooks))
				.addGroup(groupSearch.createParallelGroup()
						.addComponent(lblSSeries)
						.addComponent(lblSeries))
				.addGap(5)
				.addGroup(groupSearch.createParallelGroup()
						.addComponent(lblSMovies)
						.addComponent(lblMovies))
				.addGap(10)
				.addComponent(lblParams)
				.addGroup(groupSearch.createParallelGroup()
						.addComponent(checkWoman)
						.addComponent(checkMan))
				.addGap(5)
				.addGroup(groupSearch.createParallelGroup()
						.addComponent(lblPMusic)
						.addComponent(txtPMusic))
				.addGroup(groupSearch.createParallelGroup()
						.addComponent(lblPBooks)
						.addComponent(txtPBooks))
				.addGroup(groupSearch.createParallelGroup()
						.addComponent(lblPSeries)
						.addComponent(txtPSeries))
				.addGroup(groupSearch.createParallelGroup()
						.addComponent(lblPMovies)
						.addComponent(txtPMovies))
				.addGroup(groupSearch.createParallelGroup()
						.addComponent(btnSearch)
						.addComponent(btnNext)
						.addComponent(btnPrevious))
				);
		
		// Agregar componentes a panel Añadir
		groupAdd.setHorizontalGroup(groupAdd.createParallelGroup()
				.addGroup(GroupLayout.Alignment.LEADING, groupAdd.createSequentialGroup()
						.addComponent(lblAName)
						.addComponent(txtAName))
				.addGroup(GroupLayout.Alignment.LEADING, groupAdd.createSequentialGroup()
						.addComponent(lblAMusic)
						.addComponent(txtAMusic))
				.addGroup(GroupLayout.Alignment.LEADING, groupAdd.createSequentialGroup()
						.addComponent(lblABooks)
						.addComponent(txtABooks))
				.addGroup(GroupLayout.Alignment.LEADING, groupAdd.createSequentialGroup()
						.addComponent(lblASeries)
						.addComponent(txtASeries))
				.addGroup(GroupLayout.Alignment.LEADING, groupAdd.createSequentialGroup()
						.addComponent(lblAMovies)
						.addComponent(txtAMovies))
				.addGroup(GroupLayout.Alignment.LEADING, groupAdd.createSequentialGroup()
						.addComponent(rbWoman)
						.addComponent(rbMan)
						.addComponent(btnAdd))
				);
		
		groupAdd.setVerticalGroup(groupAdd.createSequentialGroup()
				.addGroup(groupAdd.createParallelGroup()
						.addComponent(lblAName)
						.addComponent(txtAName))
				.addGroup(groupAdd.createParallelGroup()
						.addComponent(lblAMusic)
						.addComponent(txtAMusic))
				.addGroup(groupAdd.createParallelGroup()
						.addComponent(lblABooks)
						.addComponent(txtABooks))
				.addGroup(groupAdd.createParallelGroup()
						.addComponent(lblASeries)
						.addComponent(txtASeries))
				.addGroup(groupAdd.createParallelGroup()
						.addComponent(lblAMovies)
						.addComponent(txtAMovies))
				.addGroup(groupAdd.createParallelGroup()
						.addComponent(rbWoman)
						.addComponent(rbMan)
						.addComponent(btnAdd))
				);
		
		// Agregar componentes a panel Eliminar
		groupDelete.setHorizontalGroup(groupDelete.createParallelGroup()
				.addGroup(GroupLayout.Alignment.LEADING, groupDelete.createSequentialGroup()
						.addComponent(lblDUser)
						.addComponent(txtDUser))
				.addComponent(btnDelete)
				);
		
		groupDelete.setVerticalGroup(groupDelete.createSequentialGroup()
				.addGroup(groupDelete.createParallelGroup()
						.addComponent(lblDUser)
						.addComponent(txtDUser))
				.addGap(10)
				.addComponent(btnDelete)
				);
		
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		mainPanel.add(searchPanel, c);
		
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 1;
		mainPanel.add(addPanel, c);
		
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 2;
		mainPanel.add(delPanel, c);
		
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 4;
		mainPanel.add(btnExit, c);
		
		frame.add(mainPanel);
		
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
		String books = "";
		String music = "";
		String series = "";
		String movies = "";
		db = new DBGenerator("SoulDB");
		try (Transaction tx = db.trans()) {
			men = db.getNodes("GENRE", "M");
			women = db.getNodes("GENRE", "F");			
			
			tx.success();
			JOptionPane.showMessageDialog(frame, "Se ha realizado la conexion con la base de datos");
			
			// Activar aplicación
			btnAdd.setEnabled(true);
			btnDelete.setEnabled(true);
			//btnNext.setEnabled(true);
			btnSearch.setEnabled(true);
			//btnPrevious.setEnabled(true);
			checkMan.setEnabled(true);
			checkWoman.setEnabled(true);
			rbMan.setEnabled(true);
			rbWoman.setEnabled(true);
			
			lblMusic.setText(music);
			lblMovies.setText(movies);
			lblSeries.setText(series);
			lblBooks.setText(books);
			
			// Acción de botón siguiente
			index = 0;
			btnNext.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String[] get = getRelationships(found, increaseIndex(maxNumber));
					if (get != null) {
						lblUser.setText(get[0]);
						lblBooks.setText(get[1]);
						lblMusic.setText(get[2]);
						lblSeries.setText(get[3]);
						lblMovies.setText(get[4]);
					}
				}
			});
			
			// Acción de botón atrás
			btnPrevious.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String[] get = getRelationships(found, decreaseIndex(maxNumber));
					if (get != null) {
						lblUser.setText(get[0]);
						lblBooks.setText(get[1]);
						lblMusic.setText(get[2]);
						lblSeries.setText(get[3]);
						lblMovies.setText(get[4]);
					}
				}
			});
			
			// Acción de botón buscar
			btnSearch.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (btnSearch.getText().equals("Buscar")) {
						btnSearch.setText("Nueva búsqueda");
						checkMan.setEnabled(false);
						checkWoman.setEnabled(false);
						txtPMusic.setEnabled(false);
						txtPBooks.setEnabled(false);
						txtPSeries.setEnabled(false);
						txtPMovies.setEnabled(false);
						String genres = "";
						if (checkMan.isSelected() && !checkWoman.isSelected()) {
							genres = "M";
						} else if (!checkMan.isSelected() && checkWoman.isSelected()) {
							genres = "F";
						} else {
							genres = "MF";
						}
						String[] values = {genres, txtPBooks.getText().toUpperCase(), 
								txtPMusic.getText().toUpperCase(),
								txtPSeries.getText().toUpperCase(),
								txtPMovies.getText().toUpperCase()};
						found = setWeights(values);
						if (found.size() != 0) {
							maxNumber = found.size();
							String[] results = getRelationships(found, 0);
							lblUser.setText(results[0]);
							lblBooks.setText(results[1]);
							lblMusic.setText(results[2]);
							lblSeries.setText(results[3]);
							lblMovies.setText(results[4]);
							btnNext.setEnabled(true);
							btnPrevious.setEnabled(true);
						} else {
							JOptionPane.showMessageDialog(frame, "No se encontraron resultados");
						}
					} else {
						btnSearch.setText("Buscar");
						btnNext.setEnabled(false);
						btnPrevious.setEnabled(false);
						checkMan.setEnabled(true);
						checkWoman.setEnabled(true);
						checkMan.setSelected(false);
						checkWoman.setSelected(false);
						lblUser.setText("");
						lblMusic.setText("");
						lblBooks.setText("");
						lblSeries.setText("");
						lblMovies.setText("");
						txtPMusic.setText("");
						txtPBooks.setText("");
						txtPSeries.setText("");
						txtPMovies.setText("");
						txtPMusic.setEnabled(true);
						txtPBooks.setEnabled(true);
						txtPSeries.setEnabled(true);
						txtPMovies.setEnabled(true);
						txtPMusic.requestFocus();
					}
				}
			});
			
			// Acción de botón agregar
			btnAdd.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String[] data = {"", "", "", "", ""};
					String genre = rbMan.isSelected() ? "M" : "F";
					data[0] = txtAName.getText().toUpperCase();
					if (data[0].equals("")) {
						return;
					}
					Node node = db.getNode("NAME", data[0]);
					if (node != null) {
						JOptionPane.showMessageDialog(frame, "Usuario ya existe");
						return;
					}
					node = db.createPerson(data[0].toUpperCase(), genre);
					data[1] = txtABooks.getText().toUpperCase();
					data[2] = txtAMusic.getText().toUpperCase();
					data[3] = txtASeries.getText().toUpperCase();
					data[4] = txtAMovies.getText().toUpperCase();
					if (data[1].equals("") && data[2].equals("") &&
							data[3].equals("") && data[4].equals("")) {
						JOptionPane.showMessageDialog(frame, "Debe ingresar al "
								+ "menos uno de los campos");
					}
					String[] collect;
					String[] types = {"BOOKS", "MUSIC", "SERIES", "MOVIES"};
					Labels[] labels = {Labels.Books, Labels.Music, 
							Labels.Series, Labels.Movies};
					RelationshipType[] relationships = {Relationships.READS, 
							Relationships.LISTENS, Relationships.WATCHES, 
							Relationships.LIKES};
					for (int i = 1; i < data.length; i++) {
						collect = data[i].split(", ");
						for (int j = 0; j < collect.length; j++) {
							String value = collect[j].toUpperCase();
							if (!value.equals("")) {
								Node n = db.getNode("name", collect[j].toUpperCase());
								// Crear relación
								if (n == null) {
									n = db.createNode(value, types[i-1], labels[i-1]);	
								}
								db.createRelationship(node, n, relationships[i-1]);
							}
						}
					}
					txtAName.setText("");
					txtAMusic.setText("");
					txtABooks.setText("");
					txtASeries.setText("");
					txtAMovies.setText("");
					String user = "";
					try (Transaction tx = db.trans()) {
						user = (new Person(node)).getUser();
						tx.success();
					}
					JOptionPane.showMessageDialog(frame, "Se ha agregado el usuario " + user);
					men = db.getNodes("GENRE", "M");
					women = db.getNodes("GENRE", "F");
				}
			});
			
			// Acción de botón eliminar
			btnDelete.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String user = txtDUser.getText().toUpperCase();
					if (user.equals("")) {
						return;
					}
					Node n = db.getNode("USER", user);
					if (n != null) {
						boolean delete = db.deleteNode(n);
						if (delete) {
							JOptionPane.showMessageDialog(frame, "Se ha eliminado el usuario " + 
						user);
							txtDUser.setText("");
							men = db.getNodes("GENRE", "M");
							women = db.getNodes("GENRE", "F");
							return;
						}
					}
					JOptionPane.showMessageDialog(frame, "No se encuentra el usuario " + 
					user);
					txtDUser.setText("");
				}
			});
		}	
	}
	
	/**
	 * 
	 * @return 0 = User, 1 = Libros, 2 = Música, 3 = Series, 4 = Películas
	 */
	private static String[] getRelationships(Vector<Person> vector, int index) {
		try (Transaction tx = db.trans()) {
			if ((vector == null) || (vector.size() == 0)) {
				return null;
			}
			String[] results = {"", "", "", "", ""};
			Person per = vector.get(index);
			Node node = per.getNode();
			Iterator<Relationship> iterator = node.getRelationships().iterator();
			results[0] = per.getUser() + ", " + 
			(per.getNode().getProperty("GENRE").equals("F") ? "MUJER" : "HOMBRE");
			while (iterator.hasNext()){
				Node e = iterator.next().getEndNode();
				String type = (String)e.getProperty("TYPE");
				if (type.equals("BOOKS")){
					results[1] += (String)e.getProperty("name");
					if (iterator.hasNext()) results[1] += ", ";
				} else if (type.equals("MUSIC")) {
					results[2] += (String)e.getProperty("name");
					if (iterator.hasNext()) results[2] += ", ";
				} else if (type.equals("SERIES")) {
					results[3] += (String)e.getProperty("name");
					if (iterator.hasNext()) results[3] += ", ";
				} else if (type.equals("MOVIES")) {
					results[4] += (String)e.getProperty("name");
					if (iterator.hasNext()) results[4] += ", ";
				}
			}
			tx.success();
			return results;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private static int increaseIndex(int maxIndex) {
		if (index < maxIndex - 1) {
			index++;
		} else {
			index = 0;
		}
		return index;
	}
	
	/**
	 * 
	 * @param maxIndex
	 * @return
	 */
	private static int decreaseIndex(int maxIndex) {
		if (index == 0) {
			index = maxIndex - 1;
		} else {
			index--;
		}
		return index;
	}
	
	/**
	 * 
	 * @param values
	 */
	private static Vector<Person> setWeights(String[] values) {
		Vector<Person> pers = new Vector<Person>();
		Vector<Person> full = new Vector<Person>();
		if (values[0].equals("M")) {
			 for (int i = 0; i < men.size(); i++) {
				 full.add(new Person(men.get(i)));
			 }
		} else if (values[0].equals("F")) {
			for (int i = 0; i < women.size(); i++) {
				full.add(new Person(women.get(i)));
			}
		} else {
			for (int i = 0; i < men.size(); i++) {
				 full.add(new Person(men.get(i)));
			 }
			for (int i = 0; i < women.size(); i++) {
				full.add(new Person(women.get(i)));
			}
		}
		for (int i = 0; i < full.size(); i++) {
			String[] relations = getRelationships(full, i);
			for (int j = 1; j < relations.length; j++) {
				if (!(relations[j].equals("") || values[j].equals(""))){
					String[] rel = relations[j].substring(0, relations[j].length()).split(", ");
					String[] val = values[j].split(",");
					for (int k = 0; k < rel.length; k++) {
						for (int l = 0; l < val.length; l++) {
							if (val[l].equals(rel[k])) {
								full.get(i).incOccurrences();
							}
						}
					}
				}
			}
			if (full.get(i).getOcurrences() != -1) {
				pers.add(full.get(i));
			}
		}
		Collections.sort(pers);
		System.out.println("Pers: " + pers.size());
		return pers;
	}
}