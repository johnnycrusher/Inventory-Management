/**
 * A class providing GUI functionality to the project
 * @author John Huynh
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
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JOptionPane;

import CSV.CSVMachine;
import Delivery.Manifest;
import Exception.CSVFormatException;
import Exception.DeliveryException;
import Exception.StockException;
import Stock.Item;
import Stock.Stock;
import Stock.Store;

public class GUIApplication extends JFrame implements ActionListener, Runnable{
	
	public static final int WIDTH = 700;
	public static final int HEIGHT = 700;
	
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
	private Object[] columnNames = {"Item Name", "Manifacturing Cost($)","Sell Price ($)","Reorder Point","Reorder Ammount", "Temperature (C)","Quantity"};
	private Manifest manifest;
	private Stock inventory;
	private Stock intialInventory;
	private Store myStore = Store.getInstance();
	
	/**
	 * @param arg0 - arugments to put into GUIApplication
	 * @throws HeadlessException when there is a headless exception error
	 */
	public GUIApplication(String arg0) throws HeadlessException {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @throws StockException
	 */
	private void createGUI() throws StockException {
		//Sets the Size of the GUI
		setSize(WIDTH, HEIGHT);
		//Sets the Close Operation
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    //Sets the Layout
	    setLayout(new BorderLayout());
	    //Sets the title of the Application
	    this.setTitle("CAB302 Group 102 Inventory Management Application");
	    
	    //Creating the necessary panels and setting them as a specific color
	    pnlBtn = createPanel(new Color(135,206,250,155));
	    
	    pnlTop = createPanel(new Color(135,206,250,155));
	    
	    pnlMiddle = createPanel(new Color(135,206,250,155));
	    
	    pnlDisplay = createPanel(new Color(135,206,250,155));
	    
	    pnlDisplay.setLayout(new BorderLayout());
	     
	    //Create the required buttons
	    btnExtractMan = createButton("Extract Manifest");
	    btnLoadMan = createButton("Load Manifest");
	    btnUpdateItems = createButton("Update Items");
	    btnLoadSales = createButton("Load Sales Log");
	    
	    //Calling the required Panel Setting methods
	    layoutButtonPanel(); 
	    layoutTextPanel();
	    layoutJTablePanel();
	    
	    //add the panels to the JFrame application
	    this.getContentPane().add(pnlDisplay,BorderLayout.CENTER);
	    this.getContentPane().add(pnlTop,BorderLayout.NORTH);
	    this.getContentPane().add(pnlMiddle, BorderLayout.CENTER);
	    this.getContentPane().add(pnlBtn,BorderLayout.SOUTH);
	    
	    //Open with an Input Dialog for Store name
	    String storeName = JOptionPane.showInputDialog(pnlDisplay,"Whats the Store Name?");
	    if(storeName.equals("")) {
	    	while(storeName.equals("")) {
	    		storeName = JOptionPane.showInputDialog(pnlDisplay,"Invalid Store Name: Whats the Store Name?");
	    	}
	    }
	    //Set store name with the result of the Input Dialog
	    myStore.setName(storeName);
	    
	    //Set the Store label as the Store Name
	    storeNameLabel.setText(myStore.getName());
	    
	    //get the capital of the store and display it
	    double capital = myStore.getCapital();
	    String capitalValue = String.format("%.02f", capital);
		capitalValueLabel.setText("$" + capitalValue);
	    
		//Set the JFrame to Visible
	    repaint(); 
	    this.setVisible(true);
	}
	//A method used to create a Pannel
	private JPanel createPanel(Color c) {
		JPanel jp = new JPanel();
		jp.setBackground(c);
		return jp;
	}
	
	//A method used to create a JButton Component
	private JButton createButton(String str) {
		JButton jb = new JButton(str);
		jb.addActionListener(this);
		return jb;
	}
	
	/**A method that creates a GridBagLayout
	 * and populates it with buttons within
	 * certain position in the grid
	 */
	private void layoutButtonPanel() {
		//set GridBagLayout
		GridBagLayout layout = new GridBagLayout();
	    pnlBtn.setLayout(layout);
	    
	    //add components to grid
	    GridBagConstraints constraints = new GridBagConstraints(); 
	    
	    //Defaults
	    constraints.fill = GridBagConstraints.NONE;
	    constraints.anchor = GridBagConstraints.CENTER;
	    constraints.weightx = 100;
	    constraints.weighty = 100;
	    
	    //Add buttons to the Grid Layout
	    addToPanel(pnlBtn, btnExtractMan,constraints,0,0,2,1);
	    addToPanel(pnlBtn, btnLoadMan,constraints,2,0,2,1);
	    addToPanel(pnlBtn, btnUpdateItems,constraints,4,0,2,1);	
	    addToPanel(pnlBtn, btnLoadSales,constraints,6,0,2,1);
	}
	
	/**A method that creates a GridBagLayout
	 * and populates it with labels placed at
	 * certain position in the grid
	 */
	private void layoutTextPanel() {
		//set GridBagLayout
		GridBagLayout layout = new GridBagLayout();
		pnlTop.setLayout(layout);
		
		GridBagConstraints constraints = new GridBagConstraints();
		
		 //Defaults
	    constraints.fill = GridBagConstraints.NONE;
	    constraints.anchor = GridBagConstraints.CENTER;
	    constraints.weightx = 0.5;
	    constraints.weighty = 0.5;
	    //Create the Required Labels
	    storeNameLabel = new JLabel("Store Name");
	    capitalTextLabel = new JLabel("Store Capital:");
	    capitalValueLabel = new JLabel("0");
	    storeNameLabel.setFont(new Font("Serif", Font.PLAIN,30));
	    capitalTextLabel.setFont(new Font("Serif",Font.PLAIN, 20));
	    capitalValueLabel.setFont(new Font("Serif",Font.PLAIN, 20));
	    
	    //Add the Labels to the Grid Bag Layout
	    addToPanel(pnlTop,storeNameLabel,constraints,2,0,4,1);
	    addToPanel(pnlTop,capitalTextLabel,constraints,1,1,4,1);
	    addToPanel(pnlTop,capitalValueLabel,constraints,3,1,4,1);
	    
	}
	
	/**A method that creates a GridBagLayout
	 * and populates a JTable inside that GridBagLayout
	 * with defined contraints
	 */
	private void layoutJTablePanel() {
		//set GridBagLayout
		GridBagLayout layout = new GridBagLayout();
		pnlMiddle.setLayout(layout);
		
		GridBagConstraints constraints = new GridBagConstraints();
		 //Defaults
	    constraints.fill = GridBagConstraints.NONE;
	    constraints.anchor = GridBagConstraints.CENTER;
	    constraints.weightx = 1;
	    constraints.weighty = 1;
	    
	    //Setup JTable
		Object[][] rowItems = {};
	    stockTable = new JTable(rowItems,columnNames);
	    JScrollPane scrollPane = new JScrollPane(stockTable);
	    //Add the JTable to the Panel
	    addToPanel(pnlMiddle,scrollPane,constraints,0,10,1,100);
	}
	
	
	/** 
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

	
	@Override
	/**
	 * A method to run the swingGUI Utilities
	 */
	public void run() {
		try {
			createGUI();
		} catch (StockException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	/**main method to set Application Look and intialise the App
	 * @param args - arguments to input into main
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JFrame.setDefaultLookAndFeelDecorated(false);
        SwingUtilities.invokeLater(new GUIApplication("BorderLayout"));
        
	}

	/**
	 *  A Method that is used to handle Event 
	 *  @param ActionEvent events
	 */
	@Override
	public void actionPerformed(ActionEvent events) {
		//object used to track the action performed
		Object eventAction = events.getSource();
		
		//if load sales log button is clicked
		if(eventAction == btnLoadSales) {
			//opens an Open Dialog Box
			String fileLocation = initialiseFileExplorer();	
			try {
				//calls the CSV Reader with the file location
				HashMap<String,Integer> salesLog = CSVMachine.readSalesLog(fileLocation);
				
				//determines the cost of the sales
				double cost = 0;
				myStore.removeInventroy(salesLog);
				for(Map.Entry<String, Integer> items: salesLog.entrySet()) {
					double sellCost = inventory.getItem(items.getKey()).getSellCost();
					double numberOfSold = items.getValue();
					cost += (sellCost * numberOfSold);
				}
				
				//add the cost to the capital and update the JTable and store capital
				myStore.addCapital(cost);
				updateStoreCapital();
				updateTable();
				repaint();
				
			} catch (CSVFormatException | IOException error) {
				JOptionPane.showMessageDialog(this, "CSV Error :" + error.getMessage(),"A CSV Error",JOptionPane.ERROR_MESSAGE);
			} catch (StockException error) {
				JOptionPane.showMessageDialog(this, "Stock Error : " + error.getMessage(), "An Error Message",JOptionPane.ERROR_MESSAGE);
			} catch (Exception error) {
				JOptionPane.showMessageDialog(this,"An Application Error : no sales log was imported or no inventory","An Application Error",JOptionPane.ERROR_MESSAGE);
			}
		}
		
		//if update items button is clicked
		if(eventAction == btnUpdateItems) {
			//intialise open dialog box
			String fileLocation = initialiseFileExplorer();
			try {
				//calls CSVreader to read the item properties file
				inventory = CSVMachine.readItemProperties(fileLocation);
				intialInventory = inventory;
				//set the store inventory as the csvreader output
				myStore.setInventory(inventory);
				updateTable();
			} catch (CSVFormatException | IOException  error) {
				JOptionPane.showMessageDialog(this, "CSV Error :" + error.getMessage(),"A CSV Error",JOptionPane.ERROR_MESSAGE);
			} catch (StockException error) {
				JOptionPane.showMessageDialog(this,"Stock Error : " + error.getMessage(), "An Error Message",JOptionPane.ERROR_MESSAGE);
			}catch (Exception error) {
				JOptionPane.showMessageDialog(this,"An Application Error : no item properties was selected","An Application Error",JOptionPane.ERROR_MESSAGE);
			}
		}
		
		//if extract manifest button is clicked
		if(eventAction == btnExtractMan) {
			//intialise a new manifest object
			manifest = new Manifest();
			try {
				//add current inventory to manifest
				manifest.addItemStock(inventory);
				// create the required trucks
				manifest.createTrucks();
				//sort the cargo into optimised cargo order
				manifest.sortStock();
				//load all the cargo into the trucks
				manifest.loadCargoToTrucks();
				//open a save dialog box
				String filePath = initialiseSaveExplorer();
				//write a manifest to the save file location
				CSVMachine.writeManifest(manifest, filePath);
			} catch (CSVFormatException | IOException error) {
				JOptionPane.showMessageDialog(this, "CSV Error : " + error.getMessage(),"A CSV Error",JOptionPane.ERROR_MESSAGE);
			} catch (StockException error) {
				JOptionPane.showMessageDialog(this,"Stock Error : " + error.getMessage(), "An Error Message",JOptionPane.ERROR_MESSAGE);
			} catch (DeliveryException error) {
				JOptionPane.showMessageDialog(this,"Delivery Error : " + error.getMessage(), "An Error Message",JOptionPane.ERROR_MESSAGE);
			}catch (Exception error) {
				JOptionPane.showMessageDialog(this,"An Application Error : Cannot export manifest when there is no inventory","An Application Error",JOptionPane.ERROR_MESSAGE);
			}
		}
		
		//if load manifest button is clicked
		if(eventAction == btnLoadMan) {
			//intialise an open dialog box and manifest obj
			String fileLocation = initialiseFileExplorer();
			Manifest importedManifest = new Manifest();
			try {
				//reads a manifest and creates an object
				importedManifest = CSVMachine.readManifest(fileLocation,intialInventory);
				//get the manifest cargo stock
				Stock importedStock = importedManifest.getCargoStock();
				//add the manifest cargo into inventory
				myStore.addInventory(importedStock);
				//get the stores inventory
				inventory = myStore.getInventory();
				//subtract the capital with the manifest cost and update the store capital
				myStore.subtractCapital(importedManifest.getManifestCost());
				updateStoreCapital();
				
				updateTable();
				repaint();
			} catch (CSVFormatException | IOException error) {
				JOptionPane.showMessageDialog(this, "CSV Error : " + error.getMessage(),"A CSV Error",JOptionPane.ERROR_MESSAGE);
			} catch (StockException error) {
				JOptionPane.showMessageDialog(this,"Stock Error : " + error.getMessage(),"A Stock Error",JOptionPane.ERROR_MESSAGE);
			} catch (DeliveryException error) {
				JOptionPane.showMessageDialog(this,"Delivery Error : " + error.getMessage(),"A Delivery Error",JOptionPane.ERROR_MESSAGE);
			} catch (Exception error) {
				JOptionPane.showMessageDialog(this,"An Application Error : no manifest selected or importing manifest when no item in inventory","An Application Error",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	/**A method that opens an open dialog box.
	 * @return the file location if a file is selected, or
	 * 			return null if no file has been selected.
	 */
	private String initialiseFileExplorer() {
		File workingDirectory = new File(System.getProperty("user.dir"));
		JFileChooser jfc = new JFileChooser();
		jfc.setCurrentDirectory(workingDirectory);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Comma Seperated Values Files","csv");
		jfc.setFileFilter(filter);
		int returnValue = jfc.showOpenDialog(null);
		if(returnValue == jfc.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			String fileLocation = selectedFile.getAbsolutePath();
			return fileLocation;
		}
		return null;
	}
	
	
	/**A method that opens a save dialog box.
	 * @return the file path of the saved file, or
	 * 			return null if no file has been saved.
	 */
	private String initialiseSaveExplorer() {
		File workingDirectory = new File(System.getProperty("user.dir"));
		JFileChooser jfc= new JFileChooser();
		jfc.setCurrentDirectory(workingDirectory);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Comma Seperated Values Files","csv");
		jfc.setFileFilter(filter);
		int returnValue = jfc.showSaveDialog(this);
		if(returnValue == jfc.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			return (selectedFile.getAbsolutePath() + ".csv");
		}
		return null;
	}
	
	/**
	 * A method used to update the JTable
	 */
	private void updateTable() {
		Object[][] invenTable = inventory.convertStockIntoTable();
		TableModel mode = new DefaultTableModel(invenTable,columnNames);
		stockTable.setModel(mode);
	}
	
	/**
	 * A method used to update the store capital
	 */
	private void updateStoreCapital() {
		double capital = myStore.getCapital();
		String capitalValue = String.format("%.02f", capital);
		capitalValueLabel.setText("$" + capitalValue);
	}
	
}
