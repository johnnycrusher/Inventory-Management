/**
 * 
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import CSV.CSVMachine;
import Exception.CSVFormatException;

/**
 * @author John Huynh
 *
 */
public class GUIApplication extends JFrame implements ActionListener, Runnable{
	
	public static final int WIDTH = 700;
	public static final int HEIGHT = 900;
	
	private JPanel pnlDisplay;
	private JButton btnExtractMan;
	private JButton btnLoadMan;
	private JButton btnUpdateItems;
	private JButton btnLoadSales;
	private JPanel pnlBtn;
	private JPanel pnlTop;
	private JPanel pnlMiddle;
	private JLabel storeNameLabel;
	private JLabel capitalTextLabel;
	private JLabel capitalValueLabel;
	private JTable stockTable;
	
	/**
	 * @param arg0
	 * @throws HeadlessException
	 */
	public GUIApplication(String arg0) throws HeadlessException {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	private void createGUI() {
		setSize(WIDTH, HEIGHT);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLayout(new BorderLayout());
	    
	    
	    pnlBtn = createPanel(Color.WHITE);
	    
	    pnlTop = createPanel(Color.WHITE);
	    
	    pnlMiddle = createPanel(Color.WHITE);
	    
	    pnlDisplay = createPanel(Color.WHITE);
	    
	    pnlDisplay.setLayout(new BorderLayout());
	    
	    
	    btnExtractMan = createButton("Extract Manifest");
	    btnLoadMan = createButton("Load Manifest");
	    btnUpdateItems = createButton("Update Items");
	    btnLoadSales = createButton("Load Sales Log");
	    
	    
	 
	    layoutButtonPanel(); 
	    layoutTextPanel();
	    layoutJTablePanel();
	    this.getContentPane().add(pnlDisplay,BorderLayout.CENTER);
	    this.getContentPane().add(pnlTop,BorderLayout.NORTH);
	    this.getContentPane().add(pnlMiddle, BorderLayout.CENTER);
	    this.getContentPane().add(pnlBtn,BorderLayout.SOUTH);
	    repaint(); 
	    this.setVisible(true);
	}
	//Create JPannel
	private JPanel createPanel(Color c) {
		JPanel jp = new JPanel();
		jp.setBackground(c);
		return jp;
	}
	
	//Create JButton
	private JButton createButton(String str) {
		JButton jb = new JButton(str);
		jb.addActionListener(this);
		return jb;
	}
	
	private void layoutButtonPanel() {
		GridBagLayout layout = new GridBagLayout();
	    pnlBtn.setLayout(layout);
	    
	    //add components to grid
	    GridBagConstraints constraints = new GridBagConstraints(); 
	    
	    //Defaults
	    constraints.fill = GridBagConstraints.NONE;
	    constraints.anchor = GridBagConstraints.CENTER;
	    constraints.weightx = 100;
	    constraints.weighty = 100;
	    
	    addToPanel(pnlBtn, btnExtractMan,constraints,0,0,2,1);
	    addToPanel(pnlBtn, btnLoadMan,constraints,2,0,2,1);
	    addToPanel(pnlBtn, btnUpdateItems,constraints,4,0,2,1);	
	    addToPanel(pnlBtn, btnLoadSales,constraints,6,0,2,1);
	}
	
	private void layoutTextPanel() {
		GridBagLayout layout = new GridBagLayout();
		pnlTop.setLayout(layout);
		
		GridBagConstraints constraints = new GridBagConstraints();
		 //Defaults
	    constraints.fill = GridBagConstraints.NONE;
	    constraints.anchor = GridBagConstraints.CENTER;
	    constraints.weightx = 0.5;
	    constraints.weighty = 0.5;
	    
	    storeNameLabel = new JLabel("Store Name");
	    capitalTextLabel = new JLabel("Store Capital:");
	    capitalValueLabel = new JLabel("0");
	    storeNameLabel.setFont(new Font("Serif", Font.PLAIN,30));
	    capitalTextLabel.setFont(new Font("Serif",Font.PLAIN, 20));
	    capitalValueLabel.setFont(new Font("Serif",Font.PLAIN, 20));
	    
	    addToPanel(pnlTop,storeNameLabel,constraints,2,0,4,1);
	    addToPanel(pnlTop,capitalTextLabel,constraints,1,1,4,1);
	    addToPanel(pnlTop,capitalValueLabel,constraints,3,1,4,1);
	    
	}
	
	private void layoutJTablePanel() {
		GridBagLayout layout = new GridBagLayout();
		pnlMiddle.setLayout(layout);
		
		
		GridBagConstraints constraints = new GridBagConstraints();
		 //Defaults
	    constraints.fill = GridBagConstraints.NONE;
	    constraints.anchor = GridBagConstraints.CENTER;
	    constraints.weightx = 1;
	    constraints.weighty = 1;
	    
	    Object[] columnNames = {"Item Name", "Manifacturing Cost($)","Sell Price ($)","Reorder Point","Reorder Ammount", "Temperature (C)","Quantity"};
		Object[][] rowItems = {{"Potato","2","3","300","500","NULL","400"},
				{"RedBean","2","3","300","500","NULL","400"},
				{"Apple","2","3","300","500","NULL","400"},
				{"Apple","2","3","300","500","NULL","400"},
				{"Apple","2","3","300","500","NULL","400"},
				{"Apple","2","3","300","500","NULL","400"}};
	    stockTable = new JTable(rowItems,columnNames);
	    JScrollPane scrollPane = new JScrollPane(stockTable);
	    int don = JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS;
	    addToPanel(pnlMiddle,scrollPane,constraints,0,10,1,100);  
	   
	}
	
	
	/**
     * 
     * A convenience method to add a component to given grid bag
     * layout locations.
     *
     * @param c the component to add
     * @param constraints the grid bag constraints to use
     * @param x the x grid position
     * @param y the y grid position
     * @param w the grid width
     * @param h the grid height
     */
   private void addToPanel(JPanel jp,Component c, GridBagConstraints constraints, int x, int y, int w, int h) {  
      constraints.gridx = x;
      constraints.gridy = y;
      constraints.gridwidth = w;
      constraints.gridheight = h;
      jp.add(c, constraints);
   }

	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		createGUI(); 
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new GUIApplication("BorderLayout"));
        

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object[][] rowItems = {{"Apple","2","3","300","500","NULL","400"},
				{"Apple","2","3","300","500","NULL","400"},
				{"Apple","2","3","300","500","NULL","400"},
				{"Apple","2","3","300","500","NULL","400"}};
		Object src = e.getSource();
		if(src == btnLoadSales) {
		}
		
	}

}
