package chicstyle.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;

import chicstyle.abstraction.ChicEtStyle;
import chicstyle.abstraction.ChicEtStyleJDBC;
import chicstyle.abstraction.Principal;
import chicstyle.controle.ControlJButtonNext;
import chicstyle.controle.ControlJComboBoxClientVanessaStock;
import chicstyle.controle.ControlJComboBoxClients;
import chicstyle.tableaux.TabBoite1;
import chicstyle.tableaux.TabStockVanessa2;
import chicstyle.tableaux.TabStockVanessa1;


public class VanessaStock extends JFrame{

	private static final long serialVersionUID = 1L;
	static final int LABEL_SIZE = 18;
	private ImageChicEtStyle imageCetS;


	private TabStockVanessa2 modele = new TabStockVanessa2(); //
	private JTable tableau;
	private JComboBox<String> comboClients;
	private ChicEtStyle cets;
	private VanessaAccueil mere;

	public ChicEtStyle getCets() {
		return cets;
	}

	public void setCets(ChicEtStyle cets) {
		this.cets = cets;
	}

	public VanessaAccueil getMere() {
		return mere;
	}

	public void setMere(VanessaAccueil mere) {
		this.mere = mere;
	}

	public JComboBox<String> getComboClients() {
		return comboClients;
	}

	public void setComboClients(JComboBox<String> comboClients) {
		this.comboClients = comboClients;
	}

	public JTable getTableau() {
		return tableau;
	}

	public void setTableau(JTable tableau) {
		this.tableau = tableau;
	}

	public VanessaStock(VanessaAccueil mere, ChicEtStyle cets, boolean creation) {
		super("Chic & Style");
		this.mere=mere;
		this.cets=cets;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize((int)(tailleEcran.getWidth()*3/4),(int)(tailleEcran.getHeight()*3/4));
		this.setLocation((int)(tailleEcran.getWidth()/8),(int)(tailleEcran.getHeight()/8));

		Container conteneur = this.getContentPane();
		conteneur.setLayout(new BorderLayout());
		
		JPanel util = new JPanel();
		util.setLayout(new BoxLayout(util,BoxLayout.Y_AXIS));
		util.add(creerHaut());
		util.add(creerCentre());
		
		conteneur.add(creerWest(), BorderLayout.WEST);
		conteneur.add(util, BorderLayout.CENTER);


	}

	public JPanel creerWest(){

		JPanel panneauImage = new JPanel();
		panneauImage.setBackground(Color.BLACK);
		panneauImage.setLayout(new BoxLayout(panneauImage, BoxLayout.Y_AXIS));

		ImageIcon icon = new ImageIcon("images"+File.separator+"logo_C&S.jpg");
		//Image zoom = scaleImage(icon.getImage(), 0.5d);//facteur
		int pixels = this.getWidth();
		Image zoom = imageCetS.scaleImage(icon.getImage(), pixels/2);//taille en pixels
		Icon iconScaled = new ImageIcon(zoom);
		JLabel image = new JLabel(iconScaled);

		panneauImage.add(Box.createRigidArea(new Dimension(0,this.getHeight()/6)));

		panneauImage.add(image, BorderLayout.CENTER);
		return panneauImage;
	}

	public JPanel creerHaut() {
		JPanel panneauHaut = new JPanel();
		panneauHaut.setLayout(new BoxLayout(panneauHaut, BoxLayout.Y_AXIS));
		JLabel fonction = new JLabel("STOCK");
		fonction.setFont(new Font("Helvetica",Font.ITALIC, 48));
		fonction.setAlignmentX(CENTER_ALIGNMENT);
		panneauHaut.add(Box.createRigidArea(new Dimension(0,20)));
		panneauHaut.add(fonction);
		panneauHaut.add(Box.createRigidArea(new Dimension(0,20)));


		JPanel cl = new JPanel();
		JLabel client = new JLabel("Client: ");
		Vector<String> clients = cets.findAllClients();
		comboClients = new JComboBox<String>(clients);
		comboClients.setPreferredSize(new Dimension(150,20));
		comboClients.setEditable(false);
		cl.add(client);
		cl.add(comboClients);
		ControlJComboBoxClientVanessaStock controlComboClients = new ControlJComboBoxClientVanessaStock(cets,this,modele);
		comboClients.addActionListener(controlComboClients);

		panneauHaut.add(cl);

		return panneauHaut;
	}

	public JPanel creerCentre() {
		JPanel panneauCentre = new JPanel();
		panneauCentre.setLayout(new BoxLayout(panneauCentre, BoxLayout.Y_AXIS));
		tableau = new JTable(modele); //
		panneauCentre.add(new JScrollPane(tableau), BorderLayout.CENTER); //
		JPanel boutons = new JPanel(); //
		JButton precedent = new JButton("Précédent");
		boutons.add(precedent);
		ControlJButtonNext prec = new ControlJButtonNext(this, mere); //BOUTON
		precedent.addActionListener(prec); //BOUTON
		panneauCentre.add(boutons, BorderLayout.SOUTH);

		return panneauCentre;
	}

	public static void main(String[] args) {
		ChicEtStyle cets = new ChicEtStyleJDBC();
		Principal principal = new Principal();
		MainWindow grandmere = new MainWindow(principal);
		VanessaAccueil mere = new VanessaAccueil(grandmere, cets, true);
		new VanessaStock(mere, cets, true).setVisible(true);

	}
}
