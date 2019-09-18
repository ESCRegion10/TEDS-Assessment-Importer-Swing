/*---------------------------------------------------------------------------------------------------------------------------
 * 
 * Class:		Main.java
 * 
 * Purpose:		Contains all components and event handlers for TEDS-AI.
 * 	
 * History:
 * Date			Author			Remarks
 * 20190328		T. Esposito		original version.
 * 20190516		T. Esposito		added View and Save event handlers on Configuration tab.
 * 								added input validation on Configuration tab.
 * 20190517		T. Esposito		adjusted size of JFrame.
 * 								added Note to bottom of all tab screens.
 * 								added code to center JFrame on any screen.
 * 								added message for successful Save of configuration.
 * 20190519		T. Esposito		added About menuItem.
 * 								modified log output textarea to scrollable.
 * 20190522		T. Esposito		added log4j (version 1.2.14) logging.
 * 								added Eduphoria refresh and Home page clear log buttons.
 * 20190525		T. Esposito		get current directory from System.getProperties().getProperty("user.dir").
 * 20190527		T. Esposito		populate output dir field on home page with output dir in context file. 
 * 20190528		T. Esposito		check that output dir in context file exists and if not create it.
 * 								build with external libs deployed in folder TEDS-AI_lib.
 * 20190530		T. Esposito		ensure Output Directory text field shows backslashes.
 * 								filter "For input" from Log Output text area - text comes from Talend batch.log file.
 * 20190613		T. Esposito		added code to create output of test ids and test names to be displayed in a JTable (table).
 * 20190617		T. Esposito		code added for testing to Eduphoria Aware database on Configuration tab.
 * 20190625		T. Esposito		Output Directory on Home tab changed to only a text field and is populated from context file. 
 * 20190626		T. Esposito		all fields get populated with the values in the context file.
 * 20190710		T. Esposito		added event handler for Collection Year on Configuration tab.
 * 20190723		T. Esposito		refine log4j logging.
 * 20190730		T. Esposito		corrected bug in UI log entry (Student Parent XML filename was being truncated one character).
 * 20190731		T. Esposito		added menu "Logs" and menu items "UI", "College" and "Aware" to allow viewing, via the UI, 
 * 								of detailed logs for each type of test and UI interaction.
 * 20190801		T. Esposito		added progress bar to Configuration page to monitor progress of Test button used to test Aware
 * 								database connectivity.
 * 20190802		T. Esposito		modified Look-And-Feel to TEA SRS specs.
 * 								added getMessage() to all logger.error calls/exceptions.
 * 								modified all Buttons to TEA SRS specs.
 * 								modified Menu Bar to TEA SRS specs.
 * 								added note as per TEA SRS specs before processing any tests.
 * 20190804		T. Esposito		added code to create a blank log file, if one does not exist, 
 * 								when viewing a log file.
 * 20190805		T. Esposito		corrected bug with file choosers - would generate error message if no file/directory chosen even
 * 								when field was populated.
 * 
 *-------------------------------------------------------------------------------------------------------------------------*/

package teds_ai.views;

import java.awt.BorderLayout;
import java.io.FileInputStream;
import com.adobe.acrobat.Viewer;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTabbedPane;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.ComponentOrientation;
import javax.swing.DropMode;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import org.apache.log4j.Logger;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Cursor;
import javax.swing.border.CompoundBorder;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Component;

/**
 * 
 * Create Main class
 * @author Tony Esposito
 * 
 */
public class Main extends JFrame {
	
	final static Logger logger = Logger.getLogger(Main.class);

	// Components
	private static final long serialVersionUID = 1L;
	private JFrame frame2;
	private GroupLayout gl_pnlCollege;
	private GroupLayout gl_pnlAware;
	private GroupLayout gl_pnlConfig;
	private JTabbedPane tabbedPane;
	private JPanel contentPane;
	private JPanel pnlCollege;
	private JPanel pnlAware;
	private JPanel pnlConfig;
	private JScrollPane scrollLogOutput;
	private JScrollPane scrollLogOutputAware;
	private JTextPane txtpnNeedHelpFirst; 
	private JTextPane txtpnNeedHelpFirst_1;
	private JMenuBar menuBar;
	private JMenu mnHelp;
	private JMenu mnFile;
	private JMenuItem mntmAbout;
	private JMenuItem mntmExit;
	private JRadioButton rdbtnSAT;
	private JRadioButton rdbtnPSAT10;
	private JRadioButton rdbtnPSAT89;
	private JRadioButton rdbtnACT;
	private JRadioButton rdbtnAP;
	private JRadioButton rdbtnTSI;
	private JRadioButton rdbtnCollegeReadiness;
	private JRadioButton rdbtnAwareButton;
	private JButton btnTest;
	private JButton btnViewButton;
	private JButton btnExitConfig;
	private JButton btnSaveButton;
	private JButton btnRunButton;
	private JButton btnChooseInput;
	private JButton btnChooseXML;
	private JButton btnChooseXMLAware;
	private JButton btnChooseDirectorydefault;
	private JButton btnExitButton;
	private JButton btnClearButton;
    private JButton btnClearScreen;
	private JButton btnGetTests;
	private JButton btnExitAware;
	private JButton btnRunAware;
	private JButton btnClearOutputAware;
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private final ButtonGroup buttonGroup_2 = new ButtonGroup();
	private JFileChooser jfcChooseInput;
	private JFileChooser jfcChooseXML;
	private JFileChooser jfcChooseXMLAware;
//	private JFileChooser jfcChooseDir;
	private JFileChooser jfcChooseDirectorydefault;
	private JTextArea txtLogOutputAware;
	private JTextArea txtLogOutput;
	private JTextField xmlFile;
	private JTextField inputFile;
	private JTextField outputDir;
	private JTextField outputDirDefault;
	private JTextField txtHost;
	private JTextField txtPort;
	private JTextField txtDatabase;
	private JTextField txtUsername;
	private JTextField txtPassword;
	private JTextField txtCDN;
	private JTextField outputDirAware;
    private JTextField xmlFileAware;    
	private JProgressBar progressBar;
	private JProgressBar progressBarAware; 
	private JProgressBar progressBarTests;
	private JProgressBar progressBarTestDB;
	private JLabel lblUILog;
	private JLabel lblTEDS_AI;
	private JLabel lblXMLFile;
	private JLabel lblStatusHome;
	private JLabel lblOutputDirectory;
	private JLabel lblInputFile;
	private JLabel lblChooseAssessmentGrade;
	private JLabel lblStatusAware;
	private JLabel lblCDN;
	private JLabel lblChooseAssessmentTest;
	private JLabel lblAssessment;
	private JLabel hyperlinkTIMS;
	private JLabel lblTSDSSupport;
	private JLabel lblOutputDirectoryAware;
	private JLabel lblXMLFileAware;
	private JLabel lblSelectSubject;
	private JLabel lblSelectTests;
	private JLabel lblSelectGrade;
	private JLabel lblCollectionYear;
	private JLabel lblOutputDirectorydefault;
	private JLabel lblDatabaseHost;
	private JLabel lblDatabasePortNumber;
	private JLabel lblDatabaseName;
	private JLabel lblDatabaseUsername;
	private JLabel lblDatabasePassword;
	private JComboBox<String> cmbSubjectBox;
	private JSpinner spinCollectYr;
	private JSpinner spinSelectGrade;
	private JScrollPane scrollPane_1;
	private JTable table;
	private DefaultTableModel model;
	private static Main frame;
	private String selectedInputType;
	private String strinputFile = new String(); 
	private String stroutputDir = new String(); 
	private String strxmlFile = new String();
	private String strCollectionYear = new String(); 
	private String strCollectionYearSQL = new String(); 
	private String strLeaCdn = new String();
	private String strAbout = new String();
	private String strDbHost = new String();
	private String strDbPort = new String();
	private String strDatabase = new String();
	private String strDbUsername = new String();
	private String strDbPassword = new String();
	private JTextPane txtCopyRight;
	final String OS = System.getProperty("os.name").toLowerCase();
	String currentDir = System.getProperties().getProperty("user.dir");
	int testId;
	int subjectCode;
    String subject = new String();
    String gradeLevel = new String();
    String subjectFromCombo = new String();
    private JMenu mnLogs;
    private JMenuItem mntmUi;
    private JMenuItem mntmCollege;
    private JMenuItem mntmAware;
    private JMenuItem mntmDocumentation;
    private Viewer viewer;
    
	/**
	 * 
	 * Launch the application.
	 * @param args no command-line arguments
	 * 
	 */
	public static void main(String[] args) {
		
		 try {
             adjustLAF();
         } catch (Exception e) {
        	 logger.error("Exception during adjustment of Look-And-Feel : " + e.getMessage());
             e.printStackTrace();
         }
		 
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Main();
					frame.setLocationRelativeTo(null);   // center frame
					frame.setVisible(true);
				} catch (Exception e) {
					logger.error("Exception during creation of JFrame : " + e.getMessage());
				}
					
				// TODO: To be done by TEA - add warning message if application version is out-of-date.
				/* 
				boolean outOfDateVersion = TeaApplicationVersionUtil.checkAndUpdateApplication(strVersion, updateInstallerPath);
		        if(outOfDateVersion) {
		            JLabel lblVersionStatus = new JLabel("Out-of-Date Version");
		            lblVersionStatus.setForeground(Color.white);
		            JMenu menuBar;
		        menuBar.add(Box.createGlue());
			        menuBar.add(lblVersionStatus);
			    }
				*/
			}
		});
	}
	
	/**
	 * 
	 * Adjust Look-And-Feel of UI Manager
	 * 
	 */
	private static void adjustLAF() throws ClassNotFoundException,
    InstantiationException, IllegalAccessException,
    UnsupportedLookAndFeelException {
		
		try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            UIManager.put("MenuBar.background", new Color(0, 131, 204));
            UIManager.put("MenuItem.selectionBackground", Color.white);
            UIManager.put("MenuItem.selectionForeground", new Color(0, 131, 204));
            UIManager.put("Menu.selectionBackground", Color.white);
            UIManager.put("Menu.selectionForeground", new Color(0, 131, 204));
            UIManager.put("MenuBar.selectionBackground", Color.white);
            UIManager.put("MenuBar.selectionForeground", new Color(0, 131, 204));
        } catch (Exception e) {
            logger.error("Exception during adjustment of Look-And-Feel : " + e.getMessage());
        }
	}

	/**
	 * 
	 * Create a class constructor for the Main class
	 * where constructor calls methods to initialize
	 * components and create events
	 * 
	 */
	public Main() {
		
		logger.debug("Current working directory is: " +currentDir);
		setTitle("TEDS-AI");
		initComponents();
		createEvents();
		logger.info("Current working directory: " + currentDir);
		
	}
	
	/**
	 * 
	 * populate the Collection Year in context file
	 * 
	 */
	private void saveCollectionYr(String collectionYear) {
    	
	   	String pathname = null;
	   	   	
	   	if (rdbtnCollegeReadiness.isSelected()) {
    		pathname = currentDir + "/Contexts/TEDSAI_Contexts.txt";
    	} else if (rdbtnAwareButton.isSelected()) {
    		pathname = currentDir + "/TEDSAI_Eduphoria/Contexts/TEDSAI_Contexts.txt";
    	}
		
	    Path path = Paths.get(pathname);
	    Charset charset = StandardCharsets.UTF_8;
	   	String content = null;
	   	
	   	try {
	   		content = new String(Files.readAllBytes(path), charset);
	   	} catch (IOException e1) {
	   		e1.printStackTrace();
	   		logger.error("Exception during reading of Collection Year : " + e1.getMessage());
	   	}
	   	
	   	content = content.replaceAll("CollectionYear=[ -~]*", "CollectionYear=" + collectionYear);
	  	   			
	   	try {
	   		Files.write(path, content.getBytes(charset));
	   	} catch (IOException e1) {
	   	   	JOptionPane.showMessageDialog(frame,
		          "Exception during save of Collection Year", "Error Message",
		          JOptionPane.ERROR_MESSAGE);
	   	    logger.error("Exception during saving of Collection Year : " + e1.getMessage());
	   	}
    }	
	
	/**
	 * 
	 * populate the Student Parent XML file name in context file
	 * 
	 */
	private void saveStuParFile(String tabname) {
	    	
	   	String pathname = null;
	   	String replaceStuParXML = null;
	   	if (tabname.equals("Home")) {
	    	pathname = currentDir + "/Contexts/TEDSAI_Contexts.txt";
	    	replaceStuParXML = xmlFile.getText().replaceAll("\\\\", "/");
	   	} else if (tabname.equals("Aware")) {
	    	pathname = currentDir + "/TEDSAI_Eduphoria/Contexts/TEDSAI_Contexts.txt";
	    	replaceStuParXML = xmlFileAware.getText().replaceAll("\\\\", "/");
	    }
	    		
	    Path path = Paths.get(pathname);
	    Charset charset = StandardCharsets.UTF_8;
	   	String content = null;
	   	
	   	try {
	   		content = new String(Files.readAllBytes(path), charset);
	   	} catch (IOException e1) {
	   		logger.error("Exception during reading of Student Parent XML file name : " + e1.getMessage());
	   	}
	   	
	   	content = content.replaceAll("StudentXMLFile=[ -~]*", "StudentXMLFile=" + replaceStuParXML);
	  	   			
	   	try {
	   		Files.write(path, content.getBytes(charset));
	   	} catch (IOException e1) {
	   	   	JOptionPane.showMessageDialog(frame,
		          "Exception during save of Student Parent XML file", "Error Message",
		          JOptionPane.ERROR_MESSAGE);
	   	   	logger.error("Exception during saving of Student Parent XML file name : " + e1.getMessage());
	   	}
    }	
	
	/**
	 * 
	 * populate the field Output Directory Used from context file
	 * 
	 */
	private void populateStuParXML(String tabname) {
		
		List<String> lines = null;
		try {
			if (tabname.equals("Home")) {
				lines = Files.readAllLines(Paths.get(currentDir + "/Contexts/TEDSAI_Contexts.txt"));
	 	    } else if (tabname.equals("Aware")) {
	 	    	lines = Files.readAllLines(Paths.get(currentDir + "/TEDSAI_Eduphoria/Contexts/TEDSAI_Contexts.txt"));
	 	    }
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(
				frame,  
				"Aware context file not found", 
				"TEDS-AI",
				JOptionPane.ERROR_MESSAGE
				);
			logger.error("IOException Aware context file not found while populating Student Parent XML file : " + e1.getMessage());
		}
		
		// populate field with Output Directory from context file   
		for (String line : lines) {
			if (line.startsWith("StudentXMLFile=")) { 
				if (tabname.equals("Home")) {
					xmlFile.setText(line.substring(15,line.length()).replace("/", "\\"));
		 	    } else if (tabname.equals("Aware")) {
		 	    	xmlFileAware.setText(line.substring(15,line.length()).replace("/", "\\"));
		 	    }
				logger.info("Student Parent XML file entered: " + line.substring(15,line.length()));
				break;
			}	
		}
	}
	
	/**
	 * 
	 * populate the field Output Directory Used from context file
	 * 
	 */
	private void populateOutputDir(String tabname) {
		
		List<String> lines = null;
		try {
			if (tabname.equals("Home")) {
				lines = Files.readAllLines(Paths.get(currentDir + "/Contexts/TEDSAI_Contexts.txt"));
	 	    } else if (tabname.equals("Aware")) {
	 	    	lines = Files.readAllLines(Paths.get(currentDir + "/TEDSAI_Eduphoria/Contexts/TEDSAI_Contexts.txt"));
	 	    }
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(
				frame,  
				"Aware context file not found", 
				"TEDS-AI",
				JOptionPane.ERROR_MESSAGE
				);
			logger.error("IOException Aware context file not found while populating Output path : " + e1.getMessage());
		}
		
		// populate field with Output Directory from context file   
		for (String line : lines) {
			if (line.startsWith("OutputPath=")) { 
				if (tabname.equals("Home")) {
					outputDir.setText(line.substring(11,line.length()-1).replace("/", "\\"));
		 	    } else if (tabname.equals("Aware")) {
		 	    	outputDirAware.setText(line.substring(11,line.length()-1).replace("/", "\\"));
		 	    }
				//outputDir.setText(line.substring(11,line.length()-1).replace("/", "\\"));
				logger.info("Output directory entered: " + line.substring(11,line.length()-1));
			
				File dir = new File(line.substring(11,line.length()-1));
				// Tests whether the directory exists.
		        boolean exists = dir.exists();
		        if (!exists) {
		        	Path path = Paths.get(line.substring(11,line.length()-1));
		        	try {
						Files.createDirectories(path);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(
							frame,  
							"Required Output Directory defined in context file cannot be created.", 
							"TEDS-AI",
							JOptionPane.ERROR_MESSAGE
						); 
						logger.error("Required Output Directory defined in context file cannot be created : " + e.getMessage());
					}
		        }
		        
				break;
			}	
		}
	}
	
	/**
	 * 
	 * Get contexts for College Readiness 
	 * 
	 */
	private void getCollegeContexts() {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(currentDir + "/Contexts/TEDSAI_Contexts.txt"));
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(
					frame,  
					"College Readiness context file not found", 
					"TEDS-AI",
					JOptionPane.ERROR_MESSAGE
					);
			logger.error("IOException College Readiness context file not found : " + e1.getMessage());
		}
		
		for (String line : lines) {
	        if (line.startsWith("LEACDN=")) {
	            strLeaCdn = line.substring(7);
	            logger.info("LEACDN entered " + strLeaCdn);
	        } else if (line.startsWith("CollectionYear=")) {
	        	strCollectionYear = line.substring(15);
	        	logger.info("Collection year entered: " + strCollectionYear);
	        } else if (line.startsWith("OutputPath=")) {  // if output dir not already enter from UI then use what is in context file   
	        	stroutputDir = line.substring(11,line.length()-1);
	        	logger.info("Output directory entered: " + stroutputDir);
	        } else if (line.startsWith("CollectionYear=")) {
		       	strCollectionYear = line.substring(15);
		       	logger.info("Collection year entered: " + strCollectionYear);
	        }	
	    }
	}
	
	/**
	 * 
	 * Get contexts for Eduphoria Aware
	 * 
	 */
	private void getAwareContexts() {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(currentDir + "/TEDSAI_Eduphoria/Contexts/TEDSAI_Contexts.txt"));
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(
					frame,  
					"Aware context file not found while getting database contexts", 
					"TEDS-AI",
					JOptionPane.ERROR_MESSAGE
					);
			logger.error("IOException Aware context file not found while getting database contexts : " + e1.getMessage());
		}
		
		for (String line : lines) {
	        if (line.startsWith("LEACDN=")) {
	            strLeaCdn = line.substring(7);
	            logger.info("LEACDN entered " + strLeaCdn);
	        } else if (line.startsWith("CollectionYear=")) {
	        	strCollectionYear = line.substring(15);
	        	logger.info("Collection year entered: " + strCollectionYear);
	        } else if (line.startsWith("OutputPath=")) {    
	        	stroutputDir = line.substring(11,line.length()-1);
	        	logger.info("Output directory entered: " + stroutputDir);
	        } else if (line.startsWith("StudentXMLFile=")) { 
	        	strxmlFile = line.substring(15);
	        	logger.info("Stu-Par XML file entered: " + strxmlFile);
	        } else if (line.startsWith("Host=")) {
	        	strDbHost = line.substring(5);
	        	logger.info("AWS Db Host: " + strDbHost);
	        } else if (line.startsWith("Port=")) {
	        	strDbPort = line.substring(5);
	        	logger.info("AWS Db Port: " + strDbPort);
	        } else if (line.startsWith("Database=")) {
	            strDatabase = line.substring(9);
	            logger.info("AWS Database: " + strDatabase);
            } else if (line.startsWith("Username=")) {
	        	strDbUsername = line.substring(9);
	        	logger.info("AWS Database Username: " + strDbUsername);
	        } else if (line.startsWith("Password=")) {
	        	strDbPassword = line.substring(9);
	        	logger.info("AWS Database Password: " + strDbPassword);
	        }
	    }
	}
		
	///////////////////////////////////////////////////
	// The method that follows contains all of the code  
	// for creating and initializing components.
	///////////////////////////////////////////////////

	/**
	 * 
	 * initialize all components on Main page
	 * 
	 */
	private void initComponents() {

		setForeground(Color.WHITE);
		setBackground(Color.WHITE);
		
		Image image = null;
		try {
			image = ImageIO.read(this.getClass().getResource("/teds_ai/resources/favicon-96x96.png"));
		} catch (IOException e2) {
			logger.error("Exception during reading of UI icon : " + e2.getMessage());
		}
		setIconImage(image);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 950, 880);   // size of JFrame
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
				
		mnFile = new JMenu("File");
		mnFile.setMnemonic('F');
		mnFile.setMnemonic(KeyEvent.VK_F);
		mnFile.setForeground(new Color(255, 255, 255));
		menuBar.add(mnFile);
		
		mntmExit = new JMenuItem("Exit");
		mntmExit.setMnemonic('E');
		mntmExit.setMnemonic(KeyEvent.VK_E);
		mntmExit.setBackground(new Color(16, 129, 197));
		mntmExit.setForeground(new Color(255, 255, 255));
		mntmExit.setIcon(null);
		mnFile.add(mntmExit);
		
		mnLogs = new JMenu("Logs");
		mnLogs.setMnemonic('L');
		mnLogs.setMnemonic(KeyEvent.VK_L);
		mnLogs.setForeground(new Color(255, 255, 255));
		menuBar.add(mnLogs);
		
		mntmUi = new JMenuItem("UI");
		mntmUi.setMnemonic('U');
		mntmUi.setMnemonic(KeyEvent.VK_U);
		mntmUi.setForeground(new Color(255, 255, 255));
		mntmUi.setBackground(new Color(16, 129, 197));
		mnLogs.add(mntmUi);
		
		mntmCollege = new JMenuItem("College");
		mntmCollege.setMnemonic('C');
		mntmCollege.setMnemonic(KeyEvent.VK_C);
		mntmCollege.setBackground(new Color(16, 129, 197));
		mntmCollege.setForeground(new Color(255, 255, 255));
		mnLogs.add(mntmCollege);
		
		mntmAware = new JMenuItem("Aware");
		mntmAware.setMnemonic('A');
		mntmAware.setMnemonic(KeyEvent.VK_A);
		mntmAware.setForeground(new Color(255, 255, 255));
		mntmAware.setBackground(new Color(16, 129, 197));
		mnLogs.add(mntmAware);
		
		mnHelp = new JMenu("Help");
		mnHelp.setMnemonic('H');
		mnHelp.setMnemonic(KeyEvent.VK_H);
		mnHelp.setForeground(new Color(255, 255, 255));
		menuBar.add(mnHelp);
		
		mntmDocumentation = new JMenuItem("Documentation");
		mntmDocumentation.setMnemonic('D');
		mntmDocumentation.setMnemonic(KeyEvent.VK_D);
		mntmDocumentation.setForeground(new Color(255, 255, 255));
		mntmDocumentation.setBackground(new Color(16, 129, 197));
		mnHelp.add(mntmDocumentation);
				
		mntmAbout = new JMenuItem("About");
		mntmAbout.setMnemonic('B');
		mntmAbout.setMnemonic(KeyEvent.VK_B);
		mntmAbout.setForeground(new Color(255, 255, 255));
		mntmAbout.setBackground(new Color(16, 129, 197));
		mnHelp.add(mntmAbout);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		lblUILog = new JLabel("");
		lblUILog.setIcon(new ImageIcon(Main.class.getResource("/teds_ai/resources/R10Logo.PNG")));
		
		lblTEDS_AI = new JLabel("TEDS-AI");
		lblTEDS_AI.setHorizontalAlignment(SwingConstants.LEFT);
		lblTEDS_AI.setForeground(Color.GRAY);
		lblTEDS_AI.setFont(new Font("Tahoma", Font.PLAIN, 26));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(29)
					.addComponent(lblUILog, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE)
					.addGap(304)
					.addComponent(lblTEDS_AI, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(53, Short.MAX_VALUE))
				.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 919, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblTEDS_AI, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblUILog, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 698, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		pnlCollege = new JPanel();
		pnlCollege.setAlignmentX(Component.LEFT_ALIGNMENT);
		pnlCollege.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tabbedPane.addTab("", new ImageIcon(Main.class.getResource("/teds_ai/resources/icon_Home_SkyBlue.png")), pnlCollege, "Home for College Readiness exams");
		tabbedPane.setForegroundAt(0, Color.WHITE);
		tabbedPane.setEnabledAt(0, true);
		tabbedPane.setBackgroundAt(0, Color.WHITE);
		pnlCollege.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "College Readiness Assessments", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlCollege.setBackground(Color.WHITE);
		
		rdbtnSAT = new JRadioButton("SAT (CSV)");
		rdbtnSAT.setSelected(true);
		buttonGroup_1.add(rdbtnSAT);
		rdbtnSAT.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnSAT.setBackground(Color.WHITE);
		
		rdbtnPSAT10 = new JRadioButton("PSAT 10/NMSQT (CSV)");
		buttonGroup_1.add(rdbtnPSAT10);
		rdbtnPSAT10.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnPSAT10.setBackground(Color.WHITE);
		
		rdbtnPSAT89 = new JRadioButton("PSAT 8/9 (CSV)");
		buttonGroup_1.add(rdbtnPSAT89);
		rdbtnPSAT89.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnPSAT89.setBackground(Color.WHITE);
		
		rdbtnACT = new JRadioButton("ACT (Fixed-width)");
		buttonGroup_1.add(rdbtnACT);
		rdbtnACT.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnACT.setBackground(Color.WHITE);
		
		rdbtnAP = new JRadioButton("AP (CSV)");
		buttonGroup_1.add(rdbtnAP);
		rdbtnAP.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnAP.setBackground(Color.WHITE);
		
		rdbtnTSI = new JRadioButton("TSI (CSV)");
		buttonGroup_1.add(rdbtnTSI);
		rdbtnTSI.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnTSI.setBackground(Color.WHITE);
		
		progressBar = new JProgressBar();
		progressBar.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		btnRunButton = new JButton("Run");
		btnRunButton.setOpaque(false);
		btnRunButton.setFocusPainted(false);
		btnRunButton.setBorder(null);
		btnRunButton.setContentAreaFilled(false);
		btnRunButton.setBorderPainted(false);
		btnRunButton.setIcon(new ImageIcon(Main.class.getResource("/teds_ai/resources/TEDSAIbutton_Run.png")));
		btnRunButton.setSelected(true);
		btnRunButton.setToolTipText("Run TEDS-AI tool to process input file");
		btnRunButton.setForeground(new Color(255, 255, 255));
		btnRunButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnRunButton.setBackground(Color.WHITE);
		
		btnExitButton = new JButton("");
		btnExitButton.setFocusPainted(false);
		btnExitButton.setContentAreaFilled(false);
		btnExitButton.setBorder(null);
		btnExitButton.setBorderPainted(false);
		btnExitButton.setOpaque(false);
		btnExitButton.setIcon(new ImageIcon(Main.class.getResource("/teds_ai/resources/TEDSAIbutton_Exit.png")));
		btnExitButton.setSelected(true);
		btnExitButton.setToolTipText("Exit TEDS-AI ");
		btnExitButton.setForeground(new Color(255, 255, 255));
		btnExitButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnExitButton.setBackground(Color.WHITE);
		
		lblStatusHome = new JLabel("Status");
		lblStatusHome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollLogOutput = new JScrollPane();
		
		lblXMLFile = new JLabel("Student Parent XML File");
		lblXMLFile.setHorizontalAlignment(SwingConstants.LEFT);
		lblXMLFile.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		lblOutputDirectory = new JLabel("Output Directory Used");
		lblOutputDirectory.setHorizontalAlignment(SwingConstants.LEFT);
		lblOutputDirectory.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		lblInputFile = new JLabel("Input File");
		lblInputFile.setHorizontalAlignment(SwingConstants.LEFT);
		lblInputFile.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		xmlFile = new JTextField();
		xmlFile.setAlignmentX(Component.LEFT_ALIGNMENT);
		xmlFile.setHorizontalAlignment(SwingConstants.LEFT);
		xmlFile.setFont(new Font("Tahoma", Font.PLAIN, 14));
		xmlFile.setColumns(10);
		
		populateStuParXML("Home");
		
		inputFile = new JTextField();
		inputFile.setAlignmentX(Component.LEFT_ALIGNMENT);
		inputFile.setHorizontalAlignment(SwingConstants.LEFT);
		inputFile.setFont(new Font("Tahoma", Font.PLAIN, 14));
		inputFile.setColumns(100);
		
		outputDir = new JTextField();
		outputDir.setAlignmentX(Component.LEFT_ALIGNMENT);
		outputDir.setHorizontalAlignment(SwingConstants.LEFT);
		outputDir.setFont(new Font("Tahoma", Font.PLAIN, 14));
		outputDir.setColumns(10);
		
		populateOutputDir("Home");
	
		btnChooseXML = new JButton("");
		btnChooseXML.setContentAreaFilled(false);
		btnChooseXML.setFocusPainted(false);
		btnChooseXML.setBorderPainted(false);
		btnChooseXML.setIgnoreRepaint(true);
		btnChooseXML.setSelected(true);
		btnChooseXML.setOpaque(false);
		btnChooseXML.setSelectedIcon(new ImageIcon(Main.class.getResource("/teds_ai/resources/CVTbutton_Browse.png")));
		btnChooseXML.setIcon(new ImageIcon("C:\\###_TEDS-AI_source_code_from_TEA_###\\ConverterValidatorTool\\src\\main\\resources\\image\\CVTbutton_Browse.png"));
		btnChooseXML.setForeground(new Color(255, 255, 255));
		btnChooseXML.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		btnChooseXML.setToolTipText("Get Student Parent XML file");
		btnChooseXML.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnChooseXML.setBorder(null);
		btnChooseXML.setBackground(new Color(30, 144, 255));
		jfcChooseXML = new JFileChooser();
				
		btnChooseInput = new JButton("");
		btnChooseInput.setContentAreaFilled(false);
		btnChooseInput.setOpaque(false);
		btnChooseInput.setFocusPainted(false);
		btnChooseInput.setBorder(null);
		btnChooseInput.setIcon(new ImageIcon(Main.class.getResource("/teds_ai/resources/CVTbutton_Browse.png")));
		btnChooseInput.setForeground(new Color(255, 255, 255));
		btnChooseInput.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		btnChooseInput.setToolTipText("Get input assessment file to process");
		btnChooseInput.setSelected(true);
		btnChooseInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnChooseInput.setBackground(new Color(51, 153, 255));
		jfcChooseInput = new JFileChooser();
		
		lblAssessment = new JLabel("Choose Assessment Type to Process");
		lblAssessment.setForeground(new Color(255, 153, 0));
		lblAssessment.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		txtpnNeedHelpFirst = new JTextPane();
		txtpnNeedHelpFirst.setAlignmentX(Component.LEFT_ALIGNMENT);
		txtpnNeedHelpFirst.setText("Need help? First, consult the online docs (use Help on Menu).  If further help is needed, please submit a TIMS ticket to TEA support via your Level 2 ESC or vendor support contact.  Be sure to specify \"TEDS-AI\" in your ticket with subsystem \"studentGPS\".\r\nCopyright 2019 Texas Education Agency (TEA).  All rights reserved.");
		
		btnClearButton = new JButton(" Clear Output");
		btnClearButton.setFocusPainted(false);
		btnClearButton.setContentAreaFilled(false);
		btnClearButton.setBorderPainted(false);
		btnClearButton.setBorder(null);
		btnClearButton.setIcon(new ImageIcon(Main.class.getResource("/teds_ai/resources/TEDSAIbutton_ClearOutput.png")));
		btnClearButton.setOpaque(false);
		btnClearButton.setForeground(new Color(255, 255, 255));
		btnClearButton.setSelected(true);
		btnClearButton.setBackground(Color.WHITE);
		btnClearButton.setToolTipText("Clears log output box");
		btnClearButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		hyperlinkTIMS = new JLabel("TSDS Support");
		hyperlinkTIMS.setFont(new Font("Tahoma", Font.PLAIN, 14));
		hyperlinkTIMS.setForeground(Color.BLUE.darker());
		hyperlinkTIMS.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		lblTSDSSupport = new JLabel("For help go to");
		
		gl_pnlCollege = new GroupLayout(pnlCollege);
		gl_pnlCollege.setHorizontalGroup(
			gl_pnlCollege.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlCollege.createSequentialGroup()
					.addGroup(gl_pnlCollege.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlCollege.createSequentialGroup()
							.addGap(70)
							.addComponent(rdbtnSAT, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(rdbtnPSAT10, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)
							.addGap(26)
							.addComponent(rdbtnPSAT89, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(rdbtnACT, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(rdbtnAP, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(rdbtnTSI, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlCollege.createSequentialGroup()
							.addGroup(gl_pnlCollege.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlCollege.createSequentialGroup()
									.addGap(40)
									.addGroup(gl_pnlCollege.createParallelGroup(Alignment.TRAILING)
										.addComponent(btnExitButton, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblInputFile, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblXMLFile, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblOutputDirectory, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_pnlCollege.createSequentialGroup()
									.addGap(26)
									.addGroup(gl_pnlCollege.createParallelGroup(Alignment.LEADING)
										.addComponent(lblStatusHome, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_pnlCollege.createSequentialGroup()
											.addComponent(lblTSDSSupport)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(hyperlinkTIMS, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE))
										.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_pnlCollege.createSequentialGroup()
									.addGap(87)
									.addComponent(btnClearButton, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)))
							.addGap(29)
							.addGroup(gl_pnlCollege.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_pnlCollege.createSequentialGroup()
									.addGroup(gl_pnlCollege.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(xmlFile, GroupLayout.PREFERRED_SIZE, 425, GroupLayout.PREFERRED_SIZE)
										.addComponent(inputFile, GroupLayout.PREFERRED_SIZE, 425, GroupLayout.PREFERRED_SIZE))
									.addGap(18)
									.addGroup(gl_pnlCollege.createParallelGroup(Alignment.LEADING, false)
										.addComponent(btnChooseInput, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnChooseXML, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)))
								.addComponent(outputDir, GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
								.addComponent(scrollLogOutput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_pnlCollege.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnRunButton, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlCollege.createSequentialGroup()
							.addGap(21)
							.addComponent(lblAssessment, GroupLayout.PREFERRED_SIZE, 342, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlCollege.createSequentialGroup()
							.addContainerGap()
							.addComponent(txtpnNeedHelpFirst, GroupLayout.PREFERRED_SIZE, 753, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(14, Short.MAX_VALUE))
		);
		gl_pnlCollege.setVerticalGroup(
			gl_pnlCollege.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlCollege.createSequentialGroup()
					.addGroup(gl_pnlCollege.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlCollege.createSequentialGroup()
							.addGap(27)
							.addComponent(lblAssessment, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addGroup(gl_pnlCollege.createParallelGroup(Alignment.BASELINE)
								.addComponent(rdbtnPSAT10)
								.addComponent(rdbtnSAT)
								.addComponent(rdbtnTSI)
								.addComponent(rdbtnAP)
								.addComponent(rdbtnACT)
								.addComponent(rdbtnPSAT89))
							.addGap(26)
							.addGroup(gl_pnlCollege.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_pnlCollege.createSequentialGroup()
									.addGroup(gl_pnlCollege.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblInputFile, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
										.addComponent(inputFile, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnChooseInput, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
									.addGap(49))
								.addGroup(gl_pnlCollege.createSequentialGroup()
									.addGroup(gl_pnlCollege.createParallelGroup(Alignment.BASELINE)
										.addComponent(xmlFile, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblXMLFile, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
									.addGap(38)
									.addGroup(gl_pnlCollege.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblOutputDirectory, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
										.addComponent(outputDir, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGap(18))))
						.addGroup(gl_pnlCollege.createSequentialGroup()
							.addGap(108)
							.addComponent(btnChooseXML, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)))
					.addGroup(gl_pnlCollege.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_pnlCollege.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollLogOutput, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlCollege.createSequentialGroup()
							.addGap(9)
							.addGroup(gl_pnlCollege.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnExitButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnRunButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(24)
							.addComponent(lblStatusHome)
							.addGap(18)
							.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnClearButton)
							.addGap(11)))
					.addGap(7)
					.addGroup(gl_pnlCollege.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTSDSSupport)
						.addComponent(hyperlinkTIMS))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtpnNeedHelpFirst, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(309))
		);
		
		txtLogOutput = new JTextArea();
		scrollLogOutput.setViewportView(txtLogOutput);
		txtLogOutput.setRows(10);
		txtLogOutput.setLineWrap(true);
		txtLogOutput.setEditable(false);
		txtLogOutput.setColumns(50);
		txtLogOutput.setDropMode(DropMode.INSERT);
		txtLogOutput.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtLogOutput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtLogOutput.setName("Log output:");
		txtLogOutput.setText("Log output:");
		txtLogOutput.setAlignmentX(Component.LEFT_ALIGNMENT);
		pnlCollege.setLayout(gl_pnlCollege);
		
		model = new DefaultTableModel(); 
		model.addColumn("Test Id");
		model.addColumn("Test Name");
		
		pnlAware = new JPanel();
		pnlAware.setAlignmentX(Component.LEFT_ALIGNMENT);
		pnlAware.setBackground(Color.WHITE);
		pnlAware.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pnlAware.setBorder(new TitledBorder(null, "Eduphoria Aware Assessment", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tabbedPane.addTab("Aware", null, pnlAware, "Home for Eduphoria Assessment exam");
		
		lblSelectGrade = new JLabel("Select Grade Level");
		lblSelectGrade.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		spinSelectGrade = new JSpinner();
		
		spinSelectGrade.setBackground(new Color(153, 255, 255));
		spinSelectGrade.setToolTipText("Select grade level");
		spinSelectGrade.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinSelectGrade.setModel(new SpinnerListModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		
		lblSelectSubject = new JLabel("Select Subject");
		lblSelectSubject.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		lblChooseAssessmentGrade = new JLabel("Choose Assessment Test by Grade and Subject to Process");
		lblChooseAssessmentGrade.setForeground(new Color(255, 153, 0));
		lblChooseAssessmentGrade.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		lblSelectTests = new JLabel("Select test(s) from table:");
		lblSelectTests.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollLogOutputAware = new JScrollPane();
		
		btnRunAware = new JButton("Run");
		btnRunAware.setToolTipText("Process chosen Aware test(s)");
		btnRunAware.setOpaque(false);
		btnRunAware.setFocusPainted(false);
		btnRunAware.setBorderPainted(false);
		btnRunAware.setContentAreaFilled(false);
		btnRunAware.setBorder(null);
		btnRunAware.setIcon(new ImageIcon(Main.class.getResource("/teds_ai/resources/TEDSAIbutton_Run.png")));
		btnRunAware.setForeground(new Color(255, 255, 255));
		btnRunAware.setBackground(Color.WHITE);
		btnRunAware.setSelected(true);
		btnRunAware.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		btnExitAware = new JButton("Exit");
		btnExitAware.setToolTipText("Exit TEDS-AI");
		btnExitAware.setFocusPainted(false);
		btnExitAware.setOpaque(false);
		btnExitAware.setIcon(new ImageIcon(Main.class.getResource("/teds_ai/resources/TEDSAIbutton_Exit.png")));
		btnExitAware.setContentAreaFilled(false);
		btnExitAware.setBorderPainted(false);
		btnExitAware.setBorder(null);
		btnExitAware.setForeground(new Color(255, 255, 255));
		btnExitAware.setBackground(Color.WHITE);
		btnExitAware.setSelected(true);
		btnExitAware.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		progressBarAware = new JProgressBar();
		progressBarAware.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		lblStatusAware = new JLabel("Status");
		lblStatusAware.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		txtpnNeedHelpFirst_1 = new JTextPane();
		txtpnNeedHelpFirst_1.setText("Need help? First, consult the online docs (use Help on Menu).  If further help is needed, please submit a TIMS ticket to TEA support via your Level 2 ESC or vendor support contact.  Be sure to specify \"TEDS-AI\" in your ticket with subsystem \"studentGPS\".\r\nCopyright 2019 Texas Education Agency (TEA).  All rights reserved.");
		
		cmbSubjectBox = new JComboBox<String>();
		cmbSubjectBox.setEditable(true);
		cmbSubjectBox.setBorder(new CompoundBorder());
		cmbSubjectBox.setRequestFocusEnabled(false);
		cmbSubjectBox.setOpaque(false);
		cmbSubjectBox.setLightWeightPopupEnabled(false);
		cmbSubjectBox.setForeground(new Color(255, 255, 255));
		cmbSubjectBox.setBackground(new Color(16, 129, 197));
		cmbSubjectBox.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		cmbSubjectBox.setToolTipText("Subject area of test");
		cmbSubjectBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cmbSubjectBox.setModel(new DefaultComboBoxModel<String>(new String[] {"English", "Math", "Science", "Social Studies"}));
		cmbSubjectBox.setMaximumRowCount(4);
		
		btnGetTests = new JButton("Get tests");
		btnGetTests.setIcon(new ImageIcon(Main.class.getResource("/teds_ai/resources/TEDSAIbutton_GetTests.png")));
		btnGetTests.setOpaque(false);
		btnGetTests.setContentAreaFilled(false);
		btnGetTests.setBorderPainted(false);
		btnGetTests.setBorder(null);
		btnGetTests.setFocusPainted(false);
		btnGetTests.setForeground(new Color(255, 255, 255));
		btnGetTests.setBackground(Color.WHITE);
		btnGetTests.setSelected(true);
		btnGetTests.setToolTipText("Get tests from Aware");
		btnGetTests.setHorizontalTextPosition(SwingConstants.LEFT);
		btnGetTests.setHorizontalAlignment(SwingConstants.LEFT);
		btnGetTests.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		scrollPane_1 = new JScrollPane();
		
		btnClearOutputAware = new JButton("Clear Output");
		btnClearOutputAware.setToolTipText("Clear Log Output text area");
		btnClearOutputAware.setContentAreaFilled(false);
		btnClearOutputAware.setBorderPainted(false);
		btnClearOutputAware.setBorder(null);
		btnClearOutputAware.setFocusPainted(false);
		btnClearOutputAware.setOpaque(false);
		btnClearOutputAware.setIcon(new ImageIcon(Main.class.getResource("/teds_ai/resources/TEDSAIbutton_ClearOutput.png")));
		btnClearOutputAware.setForeground(new Color(255, 255, 255));
		btnClearOutputAware.setSelected(true);
		btnClearOutputAware.setBackground(Color.WHITE);
		btnClearOutputAware.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		lblOutputDirectoryAware = new JLabel("Output Directory Used");
		lblOutputDirectoryAware.setHorizontalAlignment(SwingConstants.LEFT);
		lblOutputDirectoryAware.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		outputDirAware = new JTextField();
		outputDirAware.setAlignmentX(Component.LEFT_ALIGNMENT);
		outputDirAware.setHorizontalAlignment(SwingConstants.LEFT);
		outputDirAware.setFont(new Font("Tahoma", Font.PLAIN, 14));
		outputDirAware.setColumns(10);
		
		populateOutputDir("Aware");
		
		lblXMLFileAware = new JLabel("Student Parent XML File");
		lblXMLFileAware.setHorizontalAlignment(SwingConstants.LEFT);
		lblXMLFileAware.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		xmlFileAware = new JTextField();
		xmlFileAware.setAlignmentX(Component.LEFT_ALIGNMENT);
		xmlFileAware.setHorizontalAlignment(SwingConstants.LEFT);
		xmlFileAware.setFont(new Font("Tahoma", Font.PLAIN, 14));
		xmlFileAware.setColumns(100);
		
		populateStuParXML("Aware");
		
		btnChooseXMLAware = new JButton("");
		btnChooseXMLAware.setContentAreaFilled(false);
		btnChooseXMLAware.setBorderPainted(false);
		btnChooseXMLAware.setFocusPainted(false);
		btnChooseXMLAware.setIcon(new ImageIcon(Main.class.getResource("/teds_ai/resources/CVTbutton_Browse.png")));
		btnChooseXMLAware.setOpaque(false);
		btnChooseXMLAware.setForeground(new Color(255, 255, 255));
		btnChooseXMLAware.setToolTipText("Get Student Parent XML file");
		btnChooseXMLAware.setSelected(true);
		btnChooseXMLAware.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnChooseXMLAware.setBorder(null);
		btnChooseXMLAware.setBackground(new Color(30, 144, 255));
		jfcChooseXMLAware = new JFileChooser();
		
		populateStuParXML("Home");
		
		progressBarTests = new JProgressBar();
		progressBarTests.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		btnClearScreen = new JButton("Clear Screen");
		btnClearScreen.setOpaque(false);
		btnClearScreen.setIcon(new ImageIcon(Main.class.getResource("/teds_ai/resources/TEDSAIbutton_ClearScreen.png")));
		btnClearScreen.setFocusPainted(false);
		btnClearScreen.setContentAreaFilled(false);
		btnClearScreen.setBorderPainted(false);
		btnClearScreen.setBorder(null);
		btnClearScreen.setForeground(new Color(255, 255, 255));
		btnClearScreen.setToolTipText("Reset screen output");
		btnClearScreen.setSelected(true);
		btnClearScreen.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnClearScreen.setBackground(Color.WHITE);
		
		gl_pnlAware = new GroupLayout(pnlAware);
		gl_pnlAware.setHorizontalGroup(
			gl_pnlAware.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlAware.createSequentialGroup()
					.addContainerGap()
					.addComponent(txtpnNeedHelpFirst_1, GroupLayout.PREFERRED_SIZE, 777, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(100, Short.MAX_VALUE))
				.addGroup(gl_pnlAware.createSequentialGroup()
					.addGroup(gl_pnlAware.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlAware.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblChooseAssessmentGrade, GroupLayout.DEFAULT_SIZE, 822, Short.MAX_VALUE))
						.addGroup(gl_pnlAware.createSequentialGroup()
							.addGap(22)
							.addGroup(gl_pnlAware.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_pnlAware.createSequentialGroup()
									.addGroup(gl_pnlAware.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_pnlAware.createSequentialGroup()
											.addGroup(gl_pnlAware.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_pnlAware.createParallelGroup(Alignment.TRAILING)
													.addGroup(gl_pnlAware.createSequentialGroup()
														.addComponent(lblSelectGrade, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
														.addGap(18))
													.addGroup(gl_pnlAware.createSequentialGroup()
														.addComponent(lblSelectSubject, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)))
												.addComponent(btnGetTests))
											.addGap(4)
											.addGroup(gl_pnlAware.createParallelGroup(Alignment.LEADING)
												.addComponent(spinSelectGrade, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
												.addGroup(gl_pnlAware.createParallelGroup(Alignment.TRAILING)
													.addComponent(lblSelectTests)
													.addComponent(cmbSubjectBox, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE))))
										.addComponent(lblStatusAware, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
										.addComponent(progressBarTests, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addGroup(gl_pnlAware.createParallelGroup(Alignment.LEADING)
											.addGroup(gl_pnlAware.createSequentialGroup()
												.addComponent(btnClearOutputAware, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
												.addGap(52)
												.addComponent(btnClearScreen, 0, 0, Short.MAX_VALUE))
											.addGroup(gl_pnlAware.createParallelGroup(Alignment.TRAILING, false)
												.addGroup(Alignment.LEADING, gl_pnlAware.createSequentialGroup()
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(btnRunAware, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
													.addComponent(btnExitAware, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE))
												.addComponent(progressBarAware, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 278, GroupLayout.PREFERRED_SIZE))))
									.addGap(29)
									.addGroup(gl_pnlAware.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 405, GroupLayout.PREFERRED_SIZE)
										.addComponent(scrollLogOutputAware, GroupLayout.PREFERRED_SIZE, 404, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_pnlAware.createSequentialGroup()
									.addGroup(gl_pnlAware.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(lblXMLFileAware, Alignment.LEADING)
										.addGroup(gl_pnlAware.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(lblOutputDirectoryAware, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_pnlAware.createParallelGroup(Alignment.LEADING, false)
										.addGroup(gl_pnlAware.createSequentialGroup()
											.addComponent(xmlFileAware, GroupLayout.PREFERRED_SIZE, 419, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(btnChooseXMLAware, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE))
										.addComponent(outputDirAware, GroupLayout.PREFERRED_SIZE, 566, GroupLayout.PREFERRED_SIZE))))
							.addGap(70)))
					.addGap(55))
		);
		gl_pnlAware.setVerticalGroup(
			gl_pnlAware.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlAware.createSequentialGroup()
					.addGap(12)
					.addComponent(lblChooseAssessmentGrade, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_pnlAware.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlAware.createSequentialGroup()
							.addGroup(gl_pnlAware.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblSelectGrade, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
								.addComponent(spinSelectGrade, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_pnlAware.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblSelectSubject)
								.addComponent(cmbSubjectBox, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_pnlAware.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnGetTests)
								.addComponent(lblSelectTests))
							.addGap(20)
							.addComponent(progressBarTests, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_pnlAware.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_pnlAware.createParallelGroup(Alignment.LEADING)
							.addComponent(lblXMLFileAware, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_pnlAware.createSequentialGroup()
								.addGap(2)
								.addComponent(xmlFileAware, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)))
						.addComponent(btnChooseXMLAware, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pnlAware.createParallelGroup(Alignment.BASELINE)
						.addComponent(outputDirAware, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblOutputDirectoryAware, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_pnlAware.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlAware.createSequentialGroup()
							.addGroup(gl_pnlAware.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnRunAware, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnExitAware))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblStatusAware, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(progressBarAware, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addGap(38)
							.addGroup(gl_pnlAware.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnClearOutputAware)
								.addComponent(btnClearScreen)))
						.addComponent(scrollLogOutputAware, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(txtpnNeedHelpFirst_1, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
					.addGap(4))
		);
		
		txtLogOutputAware = new JTextArea();
		txtLogOutputAware.setAlignmentX(Component.LEFT_ALIGNMENT);
		scrollLogOutputAware.setViewportView(txtLogOutputAware);
		txtLogOutputAware.setText("Log output:");
		txtLogOutputAware.setRows(10);
		txtLogOutputAware.setName("Log output:");
		txtLogOutputAware.setLineWrap(true);
		txtLogOutputAware.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtLogOutputAware.setEditable(false);
		txtLogOutputAware.setDropMode(DropMode.INSERT);
		txtLogOutputAware.setColumns(100);
		txtLogOutputAware.setBorder(new LineBorder(new Color(0, 0, 0)));
		table = new JTable(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setAlignmentX(Component.LEFT_ALIGNMENT);
		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		table.setToolTipText("Test ids and test names appear here");
		
		table.setMaximumSize(new Dimension(30, 30));
		table.setMinimumSize(new Dimension(30, 30));
		table.setPreferredSize(new Dimension(10, 169));
		scrollPane_1.setViewportView(table);
		pnlAware.setLayout(gl_pnlAware);
		
		pnlConfig = new JPanel();
		pnlConfig.setAlignmentX(Component.LEFT_ALIGNMENT);
		pnlConfig.setBorder(new TitledBorder(null, "Exam Configuration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlConfig.setBackground(Color.WHITE);
		pnlConfig.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tabbedPane.addTab("Configuration", null, pnlConfig, "Configuration window for assessments and exams");
		
		rdbtnCollegeReadiness = new JRadioButton("College Readiness");
		rdbtnCollegeReadiness.setSelected(true);
		buttonGroup_2.add(rdbtnCollegeReadiness);
		rdbtnCollegeReadiness.setBackground(Color.WHITE);
		rdbtnCollegeReadiness.setVerticalTextPosition(SwingConstants.TOP);
		rdbtnCollegeReadiness.setVerticalAlignment(SwingConstants.TOP);
		rdbtnCollegeReadiness.setHorizontalTextPosition(SwingConstants.RIGHT);
		rdbtnCollegeReadiness.setHorizontalAlignment(SwingConstants.LEFT);
		rdbtnCollegeReadiness.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		rdbtnAwareButton = new JRadioButton("Aware");
		rdbtnAwareButton.setEnabled(true);
		buttonGroup_2.add(rdbtnAwareButton);
		rdbtnAwareButton.setBackground(Color.WHITE);
		rdbtnAwareButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnAwareButton.setHorizontalAlignment(SwingConstants.TRAILING);
		rdbtnAwareButton.setHorizontalTextPosition(SwingConstants.RIGHT);
		rdbtnAwareButton.setVerticalTextPosition(SwingConstants.TOP);
		rdbtnAwareButton.setVerticalAlignment(SwingConstants.TOP);
		
		lblCDN = new JLabel("Count District Number (6 digits, no dash)");
		lblCDN.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		lblChooseAssessmentTest = new JLabel("Choose Assessment Type to configure");
		lblChooseAssessmentTest.setForeground(new Color(255, 153, 0));
		lblChooseAssessmentTest.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		txtCDN = new JTextField();
		txtCDN.setColumns(6);
		if (rdbtnCollegeReadiness.isSelected()) {
 	    	getCollegeContexts();
 	    } else if (rdbtnAwareButton.isSelected()) {
 	    	getAwareContexts();
 	    }
    	txtCDN.setText(strLeaCdn);
		
		lblCollectionYear = new JLabel("Collection Year");
		lblCollectionYear.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		spinCollectYr = new JSpinner();
		spinCollectYr.setModel(new SpinnerListModel(new String[] {"2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025"}));
		spinCollectYr.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinCollectYr.setValue("2020");
		
		lblOutputDirectorydefault = new JLabel("Output Directory");
		lblOutputDirectorydefault.setHorizontalAlignment(SwingConstants.LEFT);
		lblOutputDirectorydefault.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		outputDirDefault = new JTextField();
		outputDirDefault.setHorizontalAlignment(SwingConstants.LEFT);
		outputDirDefault.setFont(new Font("Tahoma", Font.PLAIN, 14));
		outputDirDefault.setColumns(10);
		if (rdbtnCollegeReadiness.isSelected()) {
 	    	getCollegeContexts();
 	    } else if (rdbtnAwareButton.isSelected()) {
 	    	getAwareContexts();
 	    }

		outputDirDefault.setText(stroutputDir.replace("/", "\\"));
		
		btnChooseDirectorydefault = new JButton("");
		btnChooseDirectorydefault.setFocusPainted(false);
		btnChooseDirectorydefault.setContentAreaFilled(false);
		btnChooseDirectorydefault.setBorderPainted(false);
		btnChooseDirectorydefault.setIcon(new ImageIcon(Main.class.getResource("/teds_ai/resources/CVTbutton_Browse.png")));
		btnChooseDirectorydefault.setBorder(null);
		btnChooseDirectorydefault.setOpaque(false);
		btnChooseDirectorydefault.setForeground(new Color(255, 255, 255));
		btnChooseDirectorydefault.setToolTipText("Enter directory location of XML output files");
		btnChooseDirectorydefault.setSelected(true);
		btnChooseDirectorydefault.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnChooseDirectorydefault.setBackground(Color.WHITE);
		jfcChooseDirectorydefault = new JFileChooser();
		
		lblDatabaseHost = new JLabel("Database Host IP");
		lblDatabaseHost.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		txtHost = new JTextField();
		txtHost.setEnabled(false);
		txtHost.setColumns(10);
		
		lblDatabasePortNumber = new JLabel("Database Port Number");
		lblDatabasePortNumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		txtPort = new JTextField();
		txtPort.setEnabled(false);
		txtPort.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtPort.setColumns(10);
		
		lblDatabaseName = new JLabel("Database Name");
		lblDatabaseName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		txtDatabase = new JTextField();
		txtDatabase.setEnabled(false);
		txtDatabase.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtDatabase.setColumns(10);
		
		lblDatabaseUsername = new JLabel("Database Username");
		lblDatabaseUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		lblDatabasePassword = new JLabel("Database Password");
		lblDatabasePassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		txtUsername = new JTextField();
		txtUsername.setEnabled(false);
		txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtUsername.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setEnabled(false);
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtPassword.setColumns(10);
		
		btnSaveButton =  new JButton("Save");
		btnSaveButton.setIcon(new ImageIcon(Main.class.getResource("/teds_ai/resources/TEDSAIbutton_Save.png")));
		btnSaveButton.setOpaque(false);
		btnSaveButton.setContentAreaFilled(false);
		btnSaveButton.setBorderPainted(false);
		btnSaveButton.setBorder(null);
		btnSaveButton.setFocusPainted(false);
		btnSaveButton.setForeground(new Color(255, 255, 255));
		btnSaveButton.setSelected(true);
		btnSaveButton.setToolTipText("Save configuration information");
		btnSaveButton.setBackground(Color.WHITE);
		btnSaveButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		btnTest = new JButton("");
		btnTest.setActionCommand("Test");
		btnTest.setOpaque(false);
		btnTest.setContentAreaFilled(false);
		btnTest.setBorderPainted(false);
		btnTest.setBorder(null);
		btnTest.setFocusPainted(false);
		btnTest.setIcon(new ImageIcon(Main.class.getResource("/teds_ai/resources/TEDSAIbutton_Test.png")));
		btnTest.setForeground(new Color(255, 255, 255));
		btnTest.setToolTipText("Test Aware database connection using saved configuration values");
		btnTest.setBackground(Color.WHITE);
		btnTest.setEnabled(false);
		btnTest.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		btnViewButton = new JButton("View");
		btnViewButton.setBorderPainted(false);
		btnViewButton.setFocusPainted(false);
		btnViewButton.setContentAreaFilled(false);
		btnViewButton.setBorder(null);
		btnViewButton.setOpaque(false);
		btnViewButton.setIcon(new ImageIcon(Main.class.getResource("/teds_ai/resources/TEDSAIbutton_View.png")));
		btnViewButton.setForeground(new Color(255, 255, 255));
		btnViewButton.setSelected(true);
		btnViewButton.setToolTipText("View configuration file");
		btnViewButton.setBackground(Color.WHITE);
		btnViewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		btnExitConfig = new JButton("Exit");
		btnExitConfig.setBorder(null);
		btnExitConfig.setBorderPainted(false);
		btnExitConfig.setContentAreaFilled(false);
		btnExitConfig.setOpaque(false);
		btnExitConfig.setIcon(new ImageIcon(Main.class.getResource("/teds_ai/resources/TEDSAIbutton_Exit.png")));
		btnExitConfig.setFocusPainted(false);
		btnExitConfig.setForeground(new Color(255, 255, 255));
		btnExitConfig.setSelected(true);
		btnExitConfig.setToolTipText("Exit TEDS-AI");
		btnExitConfig.setBackground(Color.WHITE);
		btnExitConfig.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		txtCopyRight = new JTextPane();
		txtCopyRight.setText("Need help? First, consult the online docs (use Help on Menu).  If further help is needed, please submit a TIMS ticket to TEA support via your Level 2 ESC or vendor support contact.  Be sure to specify \"TEDS-AI\" in your ticket with subsystem \"studentGPS\".\r\nCopyright 2019 Texas Education Agency (TEA).  All rights reserved.");
		
		progressBarTestDB = new JProgressBar();
		
		JLabel lblTestStatus = new JLabel("Test Status");
		lblTestStatus.setFont(new Font("Tahoma", Font.PLAIN, 14));
		gl_pnlConfig = new GroupLayout(pnlConfig);
		gl_pnlConfig.setHorizontalGroup(
			gl_pnlConfig.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlConfig.createSequentialGroup()
					.addGroup(gl_pnlConfig.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlConfig.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_pnlConfig.createParallelGroup(Alignment.LEADING)
								.addComponent(lblChooseAssessmentTest, GroupLayout.PREFERRED_SIZE, 744, GroupLayout.PREFERRED_SIZE)
								.addComponent(outputDirDefault, 380, 380, 380)
								.addComponent(txtCopyRight, GroupLayout.PREFERRED_SIZE, 777, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_pnlConfig.createSequentialGroup()
									.addGroup(gl_pnlConfig.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_pnlConfig.createParallelGroup(Alignment.TRAILING)
											.addGroup(gl_pnlConfig.createSequentialGroup()
												.addGroup(gl_pnlConfig.createParallelGroup(Alignment.LEADING)
													.addComponent(lblCDN)
													.addComponent(txtCDN, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
													.addComponent(lblCollectionYear)
													.addComponent(spinCollectYr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addGap(26)
												.addComponent(rdbtnCollegeReadiness)
												.addGap(18))
											.addGroup(gl_pnlConfig.createSequentialGroup()
												.addComponent(btnSaveButton, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
												.addGap(18)
												.addComponent(btnViewButton)
												.addGap(6)))
										.addGroup(gl_pnlConfig.createSequentialGroup()
											.addComponent(lblOutputDirectorydefault, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(btnChooseDirectorydefault, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_pnlConfig.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_pnlConfig.createSequentialGroup()
											.addComponent(btnTest)
											.addGap(42)
											.addComponent(btnExitConfig, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
										.addComponent(txtHost, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblDatabaseHost)
										.addGroup(gl_pnlConfig.createSequentialGroup()
											.addGroup(gl_pnlConfig.createParallelGroup(Alignment.LEADING)
												.addComponent(lblDatabasePortNumber)
												.addComponent(txtPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblDatabaseUsername, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
												.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE))
											.addGap(45)
											.addGroup(gl_pnlConfig.createParallelGroup(Alignment.LEADING)
												.addComponent(lblDatabasePassword, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
												.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblDatabaseName, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
												.addComponent(txtDatabase, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)))
										.addComponent(rdbtnAwareButton)))))
						.addGroup(gl_pnlConfig.createSequentialGroup()
							.addGap(255)
							.addComponent(lblTestStatus)
							.addGap(30)
							.addComponent(progressBarTestDB, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(942, Short.MAX_VALUE))
		);
		gl_pnlConfig.setVerticalGroup(
			gl_pnlConfig.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlConfig.createSequentialGroup()
					.addGap(21)
					.addComponent(lblChooseAssessmentTest, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_pnlConfig.createParallelGroup(Alignment.BASELINE)
						.addComponent(rdbtnCollegeReadiness)
						.addComponent(rdbtnAwareButton))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pnlConfig.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCDN)
						.addComponent(lblDatabaseHost))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pnlConfig.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtCDN, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtHost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_pnlConfig.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlConfig.createSequentialGroup()
							.addComponent(lblCollectionYear)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(spinCollectYr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlConfig.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblDatabaseName, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(txtDatabase, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlConfig.createSequentialGroup()
							.addComponent(lblDatabasePortNumber)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(txtPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(26)
					.addGroup(gl_pnlConfig.createParallelGroup(Alignment.LEADING)
						.addComponent(btnChooseDirectorydefault, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_pnlConfig.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblOutputDirectorydefault, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblDatabaseUsername, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblDatabasePassword, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlConfig.createParallelGroup(Alignment.LEADING)
						.addComponent(outputDirDefault, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
					.addGap(38)
					.addGroup(gl_pnlConfig.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_pnlConfig.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnViewButton)
							.addComponent(btnSaveButton))
						.addComponent(btnTest)
						.addComponent(btnExitConfig))
					.addGap(31)
					.addGroup(gl_pnlConfig.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblTestStatus, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(progressBarTestDB, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
					.addGap(31)
					.addComponent(txtCopyRight, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(224, Short.MAX_VALUE))
		);
		pnlConfig.setLayout(gl_pnlConfig);
		contentPane.setLayout(gl_contentPane);
	}
	
	///////////////////////////////////////
	// The method that follows contains all 
	// of the code for creating events.
	///////////////////////////////////////

	/**
	 * 
	 * create event handlers for all components on Main page
	 * 
	 */
	private void createEvents() {

		/**
		 * 
		 * Event handlers for buttons 
		 * 
		 */
		btnRunButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try { 
					if (rdbtnSAT.isSelected()) {
						selectedInputType = "SATCSV";	// latest version of Controller
					} else if (rdbtnPSAT10.isSelected()) {
						selectedInputType = "PSAT10";
					} else if (rdbtnPSAT89.isSelected()) {
						selectedInputType = "PSAT89";
					} else if (rdbtnACT.isSelected()) {
						selectedInputType = "ACT";
					} else if (rdbtnAP.isSelected()) {
						selectedInputType = "AP";
					} else if (rdbtnTSI.isSelected()) {
						selectedInputType = "TSICSV";	
					} else {
						throw new NullPointerException();
					}
				} catch (NullPointerException npe2) {
					logger.error("No test type selected : " + npe2.getMessage());
					JOptionPane.showMessageDialog(
							frame,  
							"Required button for Assessment Test not selected", 
							"TEDS-AI",
							JOptionPane.ERROR_MESSAGE
							);
				} 

				if (JOptionPane.showConfirmDialog(frame, "<html><font color='red'>NOTE: Configurations must be saved prior to processing.</font></html>\nDo you wish to process this file?", "TEDS-AI",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

					if(OS.substring(0,3).equals("win")) {
						strinputFile = inputFile.getText();
						strinputFile = strinputFile.replace("\\", "/");
						stroutputDir = outputDir.getText();
						stroutputDir = stroutputDir.replace("\\", "/");
						strxmlFile = xmlFile.getText();
						strxmlFile = strxmlFile.replace("\\", "/");
						currentDir = currentDir.replace("\\", "/");
					}
					
					getCollegeContexts();
	
				    /**
				     * 
				     * MyWorker is a SwingWorker object that runs the Talend 
				     * College Readiness command-line process in a background thread
				     *
				     */
				    					
					class MyWorker extends SwingWorker<String, Object> {

						protected String doInBackground() {

							String javaHome = System.getProperty("java.home").concat("\\bin\\java.exe");
							logger.debug("Current Java Home setting for Assessments is: " + javaHome);
							
							txtLogOutput.setText("Log output:");
							progressBar.setIndeterminate(true);
							progressBar.setString("Processing ...");
							progressBar.setFont(new Font("Tahoma", Font.PLAIN, 14));
							progressBar.setStringPainted(true);
							progressBar.setVisible(true); 

							logger.info("Processing started");
							String cmd = currentDir + "/Controller/Controller/Controller_run.bat " + strCollectionYear + " " + '"' + currentDir + "/Input/TEDS-AI-Template-Master.xlsx"
									+ '"' + " "	+ '"' + strinputFile + '"' + " " + selectedInputType + " " + strLeaCdn + " " + '"' + stroutputDir + '/' + '"' + " " + '"' + strxmlFile + '"' + " " + '"' 
									+ currentDir + "/Contexts/TEDSAI_Contexts.txt" + '"' + " " + '"' + javaHome + '"'; 
							logger.debug("Talend Java command line in Home Tab SwingWorker is: " + cmd);
																					
							Process p = null;
							try {
								p = Runtime.getRuntime().exec(cmd);
							} catch (IOException ex) {
								logger.error("IOException with SwingWorker process : " + ex.getMessage());
							} 
							try {
								p.waitFor();
							} catch (InterruptedException ite) {
								logger.error("InterruptedException with SwingWorker process : " + ite.getMessage());
							}
							logger.info("Processing completed");
					
							return "done";
						}

						protected void done() {
							
							File file = new File(stroutputDir + "/TEDSAI_batchlog.txt");
							BufferedReader input = null;
							progressBar.setIndeterminate(false);
							progressBar.setString("Completed ...");
							stroutputDir = stroutputDir.replace("/", "\\");
							JOptionPane.showMessageDialog(frame, "Processing completed.\nXML files in folder " + stroutputDir, "TEDS-AI", JOptionPane.INFORMATION_MESSAGE);
  	     
							// Read log file
							try {
								input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
							} catch (FileNotFoundException e1) {
								JOptionPane.showMessageDialog(
										frame,  
										"No batchlog file found", 
										"TEDS-AI",
										JOptionPane.ERROR_MESSAGE
										);
								logger.error("FileNotFoundException when reading Talend batch log file : " + e1.getMessage());
							}
							
							String line = null;
						    try {
								while ((line = input.readLine()) != null) {
									if (line.contains("For input ")) {
									   continue;
									} else {
									  // txtLogOutput.read(line, "Reading log file");
									//	txtLogOutput.setText(line);
										line = line.replace("/", "\\");
										txtLogOutput.append("\n" + line);
									}
                                }
							} catch (IOException ioe) {
								JOptionPane.showMessageDialog(
										frame,  
										"Error reading batchlog file", 
										"TEDS-AI",
										JOptionPane.ERROR_MESSAGE
										); 
								logger.error("IOException with SwingWorker process : " + ioe.getMessage());
							}   
						}
					}

					new MyWorker().execute();   // run ProgressBar

				} // end of if for Yes/No to Run button
			}
		});

		btnExitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(frame, "Do you wish to Exit?", "TEDS-AI",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					System.exit(ABORT);
				} 
			}
		});
		
		btnClearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(frame, "Do you wish to clear Log Output box?", "TEDS-AI",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					txtLogOutput.setText("Log output:");
				} 
			}
		});
				
		/**
		 * 
		 * Event handlers for file choosers 
		 * 
		 */
		btnChooseXML.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jfcChooseXML.setCurrentDirectory(new java.io.File(currentDir));
				FileNameExtensionFilter filterXML = new FileNameExtensionFilter("XML files","xml");
				jfcChooseXML.setFileSelectionMode(JFileChooser.FILES_ONLY);  // show XML files
				jfcChooseXML.setFileFilter(filterXML);
				jfcChooseXML.setDialogTitle("Choose Student Parent XML File");
				jfcChooseXML.showDialog(btnChooseXML, null);
				if (xmlFile.getText().length() == 0) {
					try {
						xmlFile.setText(jfcChooseXML.getSelectedFile().toString());
					} catch (NullPointerException npe1) {
						JOptionPane.showMessageDialog(
							frame,  
							"Required field Student Parent XML File is empty", 
							"TEDS-AI",
							JOptionPane.ERROR_MESSAGE
							); 
						logger.info("NullPointerException Student Parent XML File in Home not chosen : " + npe1.getMessage());
					}
				} else {
					try {
					  xmlFile.setText(jfcChooseXML.getSelectedFile().toString());
					} catch (NullPointerException npe1) {
							; // do nothing since field is populated already
					}
				}	
				saveStuParFile("Home");
			}
		});

		btnChooseInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jfcChooseInput.setCurrentDirectory(new java.io.File(currentDir));
				FileNameExtensionFilter filterInput = new FileNameExtensionFilter("Text files", "csv");
				if (rdbtnACT.isSelected()) {
					filterInput = new FileNameExtensionFilter("Excel files", "xlsx");
				}  
				jfcChooseInput.setFileSelectionMode(JFileChooser.FILES_ONLY);  // show CSV or Excel based on button selected
				jfcChooseInput.setFileFilter(filterInput);
				jfcChooseInput.setDialogTitle("Choose Input File");
				jfcChooseInput.showDialog(btnChooseInput, null);
				try {
					inputFile.setText(jfcChooseInput.getSelectedFile().toString());
				} catch (NullPointerException npe1) {
					JOptionPane.showMessageDialog(
							frame,  
							"Required field Input File is empty", 
							"TEDS-AI",
							JOptionPane.ERROR_MESSAGE
							); 
					logger.info("NullPointerException Input File not chosen : " + npe1.getMessage());
				}
			}
		});
		
		btnChooseDirectorydefault.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jfcChooseDirectorydefault.setCurrentDirectory(new java.io.File(currentDir));
				jfcChooseDirectorydefault.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  // show directories only
				jfcChooseDirectorydefault.setAcceptAllFileFilterUsed(false);
				jfcChooseDirectorydefault.setDialogTitle("Choose Output Directory");
				jfcChooseDirectorydefault.showDialog(btnChooseDirectorydefault, null);
				if (outputDirDefault.getText().length() == 0) {
					try {
						outputDirDefault.setText(jfcChooseDirectorydefault.getSelectedFile().toString());
					} catch (NullPointerException npe1) {
						JOptionPane.showMessageDialog(
							frame,  
							"Required field Output Directory is empty", 
							"TEDS-AI",
							JOptionPane.ERROR_MESSAGE
							); 
						logger.info("NullPointerException Default Output Directory not chosen", npe1);
					}
				} else {
					try {
						outputDirDefault.setText(jfcChooseDirectorydefault.getSelectedFile().toString());
					} catch (NullPointerException npe1) {
							; // do nothing since field is populated already
					}
				}		
			}
		});
				
		/**
		 * 
		 * Aware tab button event handlers
		 * 
		 */
		
		/**
		 * 
		 * Exit button on Aware tab event handler
		 * 
		 */
		btnExitAware.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(frame, "Do you wish to Exit?", "TEDS-AI",
						JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					System.exit(ABORT);
				} 
			}
		});
		
		/**
		 *  
		 * Clear LogOuput button on Aware tab event handler
		 * 
		 */
		btnClearOutputAware.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(frame, "Do you wish to clear Log Output box?", "TEDS-AI",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					txtLogOutputAware.setText("Log output:");
				} 
			}
		});
		
		/** 
		 * 
		 * Run button on Aware tab event handler
		 * 
		 */
		btnRunAware.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (JOptionPane.showConfirmDialog(frame, "<html><font color='red'>NOTE: Configurations must be saved prior to processing.</font></html>\nDo you wish to process selected test(s)?", "TEDS-AI",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					
					File file = new  File(currentDir + "/TEDSAI_Eduphoria/Input/TestIds.csv");
					FileWriter fw = null;
					BufferedWriter bw = null;
				
					// delete and recreate TestIds.txt file in order to truncate previous tests chosen
					try {
						file.delete();
						file.createNewFile();
					} catch (IOException e2) {
						logger.error("Exception during re-creation of TestIds.csv file : " + e2.getMessage());
					}

					try {
						fw = new FileWriter(file.getAbsoluteFile(),true);
						bw = new BufferedWriter(fw);
					} catch (IOException e1) {
						logger.error("Exception during write to TestIds.csv file : " + e1.getMessage());
					}
		
					// loop thru table to get chosen tests
					for(int i = 0; i < model.getRowCount(); i++){
						if (table.isRowSelected(i)) {  // write chosen tests to file
							try {
								bw.write(model.getValueAt(i,0).toString());
								bw.write(":000" + i + "\n");
								bw.flush();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				
					// close TestIds.csv file
					try {
						bw.close();
					} catch (IOException e) {
						logger.error("Exception during close to TestIds.csv file : " + e.getMessage());
					}
				
					if (!file.exists() || file.length() == 0) {
						JOptionPane.showMessageDialog(
							frame,  
							"No tests chosen", 
							"TEDS-AI",
							JOptionPane.ERROR_MESSAGE
							);
						logger.error("Aware TestIds.csv file is empty, possibly no test chosen.");
						return;
					}
	
					List<String> lines = null;
					try {
						lines = Files.readAllLines(Paths.get(currentDir + "/TEDSAI_Eduphoria/Contexts/TEDSAI_Contexts.txt"));
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(
							frame,  
							"Aware context file not found", 
							"TEDS-AI",
							JOptionPane.ERROR_MESSAGE
							);
						logger.error("IOException Aware context file not found : " + e1.getMessage());
					}
				
					for (String line : lines) {
						if (line.startsWith("LEACDN=")) {
							strLeaCdn = line.substring(7);
							logger.info("LEACDN entered " + strLeaCdn);
						} else if (line.startsWith("CollectionYear=")) {
							strCollectionYear = line.substring(15);
							logger.info("Collection year entered: " + strCollectionYear);
						} else if (line.startsWith("OutputPath=")) {    
							stroutputDir = line.substring(11,line.length()-1);
							logger.info("Output directory entered: " + stroutputDir);
						} else if (line.startsWith("StudentXMLFile=")) { 
							strxmlFile = line.substring(15);
							logger.info("Stu-Par XML file entered: " + strxmlFile);
						} else if (line.startsWith("Host=")) {
							strDbHost = line.substring(5);
							logger.info("AWS Db Host: " + strDbHost);
						} else if (line.startsWith("Port=")) {
							strDbPort = line.substring(5);
							logger.info("AWS Db Port: " + strDbPort);
						} else if (line.startsWith("Database=")) {
							strDatabase = line.substring(9);
							logger.info("AWS Database: " + strDatabase);
						} else if (line.startsWith("Username=")) {
							strDbUsername = line.substring(9);
							logger.info("AWS Database Username: " + strDbUsername);
						} else if (line.startsWith("Password=")) {
							strDbPassword = line.substring(9);
							logger.info("AWS Database Password: " + strDbPassword);
						}
					}
				
					if(OS.substring(0,3).equals("win")) {
						stroutputDir = stroutputDir.replace("\\", "/");
						strxmlFile = strxmlFile.replace("\\", "/");
						currentDir = currentDir.replace("\\", "/");
					}
				
					logger.debug("Output directory defined as: " + stroutputDir);
					logger.debug("Student Parent XML file defined as: " + strxmlFile);
					logger.debug("Current directory defined as: " + currentDir);
				
					/**
					 * 
					 * MyWorker is a SwingWorker object that runs the Talend 
					 * Eduphoria Aware assessment command-line process in a 
					 * background thread.  Used to allow UI to show progress bar.
					 * 
					 */
			    					
					class MyWorker extends SwingWorker<String, Object> {

						protected String doInBackground() {

							String javaHome = System.getProperty("java.home").concat("\\bin\\java.exe");
							logger.debug("Current Java Home setting for Aware is: " + javaHome);
						
							txtLogOutputAware.setText("Log output:");
							progressBarAware.setIndeterminate(true);
							progressBarAware.setString("Processing ...");
							progressBarAware.setFont(new Font("Tahoma", Font.PLAIN, 14));
							progressBarAware.setStringPainted(true);
							progressBarAware.setVisible(true); 

							logger.info("Processing started");
					
							//for Eduphoria, the HomeDir has to be set to currentDir + "/TEDSAI_Eduphoria"
							String cmd = currentDir + "/TEDSAI_Eduphoria/Eduphoria_Controller/Eduphoria_Controller/Eduphoria_Controller_run.bat " + strCollectionYear  + " " + "TEDS-AI-Template-Master.xlsx"
								+  " " + "TestIds.csv" + " " + "Eduphoria" + " " + strLeaCdn + " " + '"' + stroutputDir + '/' + '"' + " " + strxmlFile + " " + '"' + currentDir + "/TEDSAI_Eduphoria" + '"' + " " + '"' + javaHome + '"'; ;

								logger.debug("Talend Java command line in Aware Tab SwingWorker is: " + cmd);		
				
								Process p = null;
								try {
									p = Runtime.getRuntime().exec(cmd);
								} catch (IOException ex) {
									logger.error("IOException with SwingWorker process : " + ex.getMessage());
								} 
								try {
									p.waitFor();
								} catch (InterruptedException ite) {
									logger.error("InterruptedException with SwingWorker process : " + ite.getMessage());
								}
						
								if(logger.isInfoEnabled()){
									logger.info("Processing completed");
								}	
								return "done";
						}

						protected void done() {
						
							File file = new File(currentDir + "/TEDSAI_Eduphoria/Logs/TEDSAI_batchlog.txt");
							BufferedReader input = null;
							progressBarAware.setIndeterminate(false);
							progressBarAware.setString("Completed ...");
							stroutputDir = stroutputDir.replace("/", "\\");
							JOptionPane.showMessageDialog(frame, "Processing completed.\nXML files in folder " + stroutputDir, "TEDS-AI", JOptionPane.INFORMATION_MESSAGE);
	     
							// Read log file
							try {
								input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
							} catch (FileNotFoundException e1) {
								JOptionPane.showMessageDialog(
									frame,  
									"No batchlog file found", 
									"TEDS-AI",
									JOptionPane.ERROR_MESSAGE
									);
								logger.error("FileNotFoundException when reading Talend batch log file : " + e1.getMessage());
							}
						
							String line = null;
							try {
								while ((line = input.readLine()) != null) {
									if (line.contains("Warning: ")) {
										continue;
									} else {
										line = line.replace("/", "\\");
										txtLogOutputAware.append("\n" + line);
									}
								}
							} catch (IOException ioe) {
								JOptionPane.showMessageDialog(
									frame,  
									"Error reading batchlog file", 
									"TEDS-AI",
									JOptionPane.ERROR_MESSAGE
									); 
								logger.error("IOException with SwingWorker process : " + ioe.getMessage());
							}   
						}
					}

					new MyWorker().execute();   // run ProgressBarAware

				}
			}	
		});
	
		/**
		 * 
		 * Aware radio button
		 * 
		 */
		rdbtnAwareButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				rdbtnAwareButton = (JRadioButton)e.getSource();
				txtHost.setEnabled(rdbtnAwareButton.isSelected());
				txtPort.setEnabled(rdbtnAwareButton.isSelected());
				txtDatabase.setEnabled(rdbtnAwareButton.isSelected());
				txtUsername.setEnabled(rdbtnAwareButton.isSelected());
				txtPassword.setEnabled(rdbtnAwareButton.isSelected());
				btnTest.setEnabled(rdbtnAwareButton.isSelected());
				getAwareContexts();
				txtCDN.setText(strLeaCdn);
		    	outputDirDefault.setText(stroutputDir.replace("/", "\\"));
				txtHost.setText(strDbHost);
				txtPort.setText(strDbPort);
				txtDatabase.setText(strDatabase);
				txtUsername.setText(strDbUsername);
				txtPassword.setText(strDbPassword);
			}
		});
		
		/**
		 * 
		 * CollegeReadiness radio button
		 * 
		 */
		rdbtnCollegeReadiness.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				rdbtnCollegeReadiness = (JRadioButton)e.getSource();
				getCollegeContexts();
		 	   	txtCDN.setText(strLeaCdn);
		 	    outputDirDefault.setText(stroutputDir.replace("/", "\\"));
		 	    txtHost.setText("");
				txtPort.setText("");
				txtDatabase.setText("");
				txtUsername.setText("");
				txtPassword.setText("");
			}
		});

		/**
		 * 
		 * district cdn text field event handler - contents check
		 * 
		 */
		txtCDN.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
				String regex = "\\d+";				// digits only
				String strTxt = txtCDN.getText();
				 if (!(strTxt.matches(regex) && strTxt.length() < 7)) { 
					  JOptionPane.showMessageDialog(frame,
					          "Please enter 6-digit number only", "Error Message",
					          JOptionPane.ERROR_MESSAGE);
					  logger.info("CDN 6 digit number not enter correctly.  Entered: " + strTxt);
				 }
			}

			@Override
			public void keyPressed(KeyEvent e) {}   // not used so do nothing
		
			@Override
			public void keyTyped(KeyEvent e) {}		// not used so do nothing
		});
		
		/** 
		 * 
		 * grade level spinner event handler
		 * 
		 */
		spinSelectGrade.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
		    	  gradeLevel = (String) spinSelectGrade.getValue();
		    	  logger.debug("Grade level value from Aware page spinner is: " + gradeLevel);
		      }
		});
							
		/** 
		 * 
		 * subject comboBox event handler
		 * 
		 */
		cmbSubjectBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				subjectFromCombo = (String) cmbSubjectBox.getSelectedItem(); 
				logger.debug("Subject value from Aware page dropdown is: " + subjectFromCombo);
				
				if (subjectFromCombo.equals("English")) {
					subjectCode = 1;
				} else if (subjectFromCombo.equals("Math")) {
					subjectCode = 2;
				} else if (subjectFromCombo.equals("Social Studies")) {
					subjectCode = 3;
				} else if (subjectFromCombo.equals("Science")) {
					subjectCode = 4;
				}	
			}
	    });
			
		btnGetTests.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					model.setRowCount(0);		// clear JTable that contains all tests
				
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
					} catch (ClassNotFoundException ce) {
						logger.error("MySQL JDBC driver not found", ce);
					}
					
					/**
					 * 
					 * MyWorker is a SwingWorker object that runs the SQL 
					 * to get the Eduphoria Aware assessment test ids and 
					 * test names in a background thread.  Used to allow
					 * UI to show progress bar.
					 * 
					 */
										
					class MyWorker extends SwingWorker<String, Object> {

						protected String doInBackground() {
							
							progressBarTests.setIndeterminate(true);
							progressBarTests.setString("Retrieving tests ...");
							progressBarTests.setFont(new Font("Tahoma", Font.PLAIN, 14));
							progressBarTests.setStringPainted(true);
							progressBarTests.setVisible(true); 

							logger.info("MySQL JDBC driver registered");
							Connection conn = null;
		  	    
							getAwareContexts();
		  		  	    
							try {
								conn = DriverManager.		         
										getConnection("jdbc:mysql://" + strDbHost + ":" + strDbPort + "/" + strDatabase + "?verifyServerCertificate=true&useSSL=true&requireSSL=true", strDbUsername, strDbPassword);
							} catch (SQLException ce1) {
								logger.error("AWS MySQL Eduphoria database connection failed : " + ce1.getMessage());
								logger.debug("Connection to AWS Eduphoria database failed : " + ce1.getMessage());
							}

							if (conn != null) {
								logger.info("AWS MySQL Eduphoria database connection successful");
							} else {
								logger.info("AWS MySQL Eduphoria database failed to make connection");
							}
		  	    
							List<String> lines = null;
							try {
								lines = Files.readAllLines(Paths.get(currentDir + "/TEDSAI_Eduphoria/Contexts/TEDSAI_Contexts.txt"));
							} catch (IOException e1) {
								JOptionPane.showMessageDialog(
										frame,  
										"Aware context file not found", 
										"TEDS-AI",
										JOptionPane.ERROR_MESSAGE
										);
								logger.error("IOException Aware context file not found : " + e1.getMessage());
							}
				
							for (String line : lines) {
								if (line.startsWith("CollectionYear=")) {
									strCollectionYearSQL = line.substring(15);
									logger.info("Collection year value for SQL: " + strCollectionYearSQL);
									// for debug only -- we only have tests for 2016
									// strCollectionYearSQL = "2016";
									continue;
								}	
							}
		
							Statement statement = null;
							try {
								logger.info("Creating SQL statement");
								statement = conn.createStatement();
								String sql;
								sql = "select t.TestId ,t.Title " +
									"from aw_test t join (select count(*) as recs ,t.TestId ,t.Year " +
                                               "from aw_test t, aw_test_entry e, aw_response r " +
                                               "where t.TestId = e.TestId " +
                                               "and e.TestEntryId = r.TestEntryId " +
                                               "and t.GradeLevel = '" +  gradeLevel + "' " +
                                               "and t.Subject =  '" + subjectCode + "' " +
                                               "and t.Year = '" + strCollectionYearSQL + "' " +
                                               "group by t.TestId, t.Year) c " +
                                               "on t.TestId = c.TestId " +
                                               "and t.Year = c.Year " +
                                               "and c.recs > 0 " +
                                               "order by t.TestId";
								logger.info("SQL statement to retrieve Aware tests is: " + sql); 
								
								ResultSet rs = statement.executeQuery(sql);
								int rowCount = rs.last() ? rs.getRow() : 0;
								if (rowCount == 0) {
									progressBarTests.setIndeterminate(false);
									progressBarTests.setString("No tests found ...");
									JOptionPane.showMessageDialog(
										frame,  
										"No tests found for the grade and subject selected", 
										"TEDS-AI",
										JOptionPane.INFORMATION_MESSAGE
									); 	
								}

								rs.beforeFirst();		// set result set back to before first row so loop works correctly
								// Extract data from result set
								while (rs.next()) {
									//  Retrieve data by column name
									int testId = rs.getInt("TestId");
									String testTitle = rs.getString("Title");
									logger.debug("Test value returned from SQL is: " + testId + " " + testTitle);
									model.addRow(new Object[] {testId,testTitle});
								}			        
								// Clean-up environment
								rs.close();
								statement.close();
								conn.close();
							} catch (SQLException se) { //  Handle errors for JDBC
								logger.error("SQLException JDBC error retrieving Aware tests : " + se.getMessage());
							} catch (Exception ae) { 	//  Handle errors for Class.forName
								logger.error("General exception retrieving Aware tests : " + ae.getMessage());
							} finally {					//  finally block used to close resources
								try {
									if (statement != null)
										statement.close();
								} catch (SQLException se2) {  // nothing we can do
								}
								try {
									if (conn != null)
										conn.close();
								} catch (SQLException se) {
									logger.error("SQLException closing Aware database connection : " + se.getMessage());
								} //  end finally try
							} //  end try
						
							return "done";
						}
			
						protected void done() {
							progressBarTests.setIndeterminate(false);
							progressBarTests.setString("Test retrieval completed ...");
						}
					}	
	
					new MyWorker().execute();   // run ProgressBarTests
			}
		});
		
		/**
		 * 
		 * choose Student Parent XML for Aware event handler
		 *
		 */
		
		btnChooseXMLAware.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jfcChooseXMLAware.setCurrentDirectory(new java.io.File(currentDir));
				FileNameExtensionFilter filterXML = new FileNameExtensionFilter("XML files","xml");
				jfcChooseXMLAware.setFileSelectionMode(JFileChooser.FILES_ONLY);  // show XML files
				jfcChooseXMLAware.setFileFilter(filterXML);
				jfcChooseXMLAware.setDialogTitle("Choose Student Parent XML File");
				jfcChooseXMLAware.showDialog(btnChooseXMLAware, null);
				if (xmlFileAware.getText().length() == 0) {
					try {
						xmlFileAware.setText(jfcChooseXMLAware.getSelectedFile().getName().toString());  // get file name only
					} catch (NullPointerException npe1) {
						JOptionPane.showMessageDialog(
								frame,  
								"Required field Student Parent XML File is empty", 
								"TEDS-AI",
								JOptionPane.ERROR_MESSAGE
						); 
						logger.info("NullPointerException Student Parent XML File for Aware not chosen : " + npe1.getMessage());
					}
				} else {
					try {
						xmlFileAware.setText(jfcChooseXMLAware.getSelectedFile().getName().toString());  // get file name only
					} catch (NullPointerException npe1) {
							; // do nothing since field is populated already
					}
				}		
				saveStuParFile("Aware");
			}
		});
		
		btnClearScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(frame, "Do you wish to clear screen?", "TEDS-AI",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					txtLogOutputAware.setText("Log output:");
					model.setRowCount(0);
					progressBarAware.setString("");
					progressBarTests.setString("");
					progressBar.setString("");
				} 
			}
		});
		
		/**
		 * Configuration event handlers
		 * 
		 */
		
		/**
		 * 
		 * Configuration tab, collection year spinner event handler
		 * 
		 */
		spinCollectYr.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				saveCollectionYr((String) spinCollectYr.getValue());
			}
		});
						
		/**
		 * 
		 * Configuration tab, Exit button event handler
		 * 
		 */
		btnExitConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(frame, "Do you wish to Exit?", "TEDS-AI",
						JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					System.exit(ABORT);
				} 
			}
		});
		
		/** 
		 * 
		 * Save button on Configuration tab event handler
		 * 
		 */
		btnSaveButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	
		    	String pathname = null;
		    	if (rdbtnCollegeReadiness.isSelected()) {
		    		pathname = currentDir + "/Contexts/TEDSAI_Contexts.txt";
		    	} else if (rdbtnAwareButton.isSelected()) {
		    		pathname = currentDir + "/TEDSAI_Eduphoria/Contexts/TEDSAI_Contexts.txt";
		    	}
		    		
		    	Path path = Paths.get(pathname);
		    	Charset charset = StandardCharsets.UTF_8;
		   		String content = null;
		   		
		   		try {
		   			String replaceTxtCDN = txtCDN.getText();
		   			String replaceCollectYr = (String) spinCollectYr.getValue();
		   			String replaceOutputDirDefault = outputDirDefault.getText().replaceAll("\\\\", "/");
		   			if (replaceTxtCDN.length() != 6) {
		   				JOptionPane.showMessageDialog(frame,
					          "Please enter 6-digit number only", "Error Message",
					          JOptionPane.ERROR_MESSAGE);
		   			} else if (replaceOutputDirDefault.length() == 0) {
		   				JOptionPane.showMessageDialog(frame,
					          "Please enter output directory", "Error Message",
					          JOptionPane.ERROR_MESSAGE);
		   			} else {
		   				try {
		   					content = new String(Files.readAllBytes(path), charset);
		   				} catch (IOException e1) {
		   					logger.error("IOException reading context file : " + e1.getMessage());
		   				}
		   		
		   				content = content.replaceAll("LEACDN=[ -~]*", "LEACDN=" + replaceTxtCDN);
		   				content = content.replaceAll("CollectionYear=[ -~]*", "CollectionYear=" + replaceCollectYr);
		   				content = content.replaceAll("OutputPath=[ -~]*", "OutputPath=" + replaceOutputDirDefault + "/");
		   				
		   				if (rdbtnAwareButton.isSelected()) {
		   					String replaceHost = txtHost.getText();
		   					String replacePort = txtPort.getText();
		   					String replaceDatabase = txtDatabase.getText();
		   					String replaceUsername = txtUsername.getText();
		   					String replacePassword = txtPassword.getText();
		   					content = content.replaceAll("Host=[ -~]*", "Host=" + replaceHost);
		   					content = content.replaceAll("Port=[ -~]*", "Port=" + replacePort);
		   					content = content.replaceAll("Database=[ -~]*", "Database=" + replaceDatabase);
		   					content = content.replaceAll("Username=[ -~]*", "Username=" + replaceUsername);
		   					content = content.replaceAll("Password=[ -~]*", "Password=" + replacePassword);
		   				}
		   				
		   				try {
		   					Files.write(path, content.getBytes(charset));
		   				} catch (IOException e1) {
		   					logger.error("IOException writing context file : " + e1.getMessage());
		   				}
		   				JOptionPane.showMessageDialog(frame,
							"Configuration saved.", "TEDS-AI", 
							JOptionPane.INFORMATION_MESSAGE);
		            }
		   	    } catch (Exception ae) {
		   	    	JOptionPane.showMessageDialog(frame,
				          "Exception during Save button operation", "Error Message",
				          JOptionPane.ERROR_MESSAGE);
		   	    	logger.error("Exception saving context file : " + ae.getMessage());
		   	    } 
		   		
		   		if (rdbtnCollegeReadiness.isSelected()) {
		   			populateOutputDir("Home");
		    	} else if (rdbtnAwareButton.isSelected()) {
		    		populateOutputDir("Aware");
		    	}
		   		
		    }	
		});
		
		/** 
		 * 
		 * Test button on Configuration tab event handler
		 * 
		 */
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
		  	        Class.forName("com.mysql.cj.jdbc.Driver");
		  	    } catch (ClassNotFoundException ce) {
		  	    	logger.error("MySQL JDBC driver not found during database connection test : " + ce.getMessage());
		   	    }

		  	    logger.info("MySQL JDBC driver registered during Test");
	 		
		  	    /**
				  * 
				  * MyWorker is a SwingWorker object that runs the Eduphoria 
				  * Aware database connection test process in a background 
			      * thread.  Used to allow UI to show progress bar.
				  * 
			    */
				    					
				class MyWorker extends SwingWorker<String, Object> {

						protected String doInBackground() {

							Connection conn = null;
							progressBarTestDB.setIndeterminate(true);
							progressBarTestDB.setString("Testing db connection ...");
							progressBarTestDB.setFont(new Font("Tahoma", Font.PLAIN, 14));
							progressBarTestDB.setStringPainted(true);
							progressBarTestDB.setVisible(true); 

							logger.info("Testing started");
		  	    
							getAwareContexts();
							try {
								conn = DriverManager.		         
										getConnection("jdbc:mysql://" + strDbHost + ":" + strDbPort + "/" + strDatabase + "?verifyServerCertificate=true&useSSL=true&requireSSL=true", strDbUsername, strDbPassword);
							} catch (SQLException ce1) {
								logger.error("AWS MySQL Eduphoria database connection failed during Test : " + ce1.getMessage());
								logger.debug("Connection to AWS Eduphoria database failed during Test : " + ce1.getMessage());
								JOptionPane.showMessageDialog(
										frame,  
										"Eduphoria database connection failed", 
										"TEDS-AI",
										JOptionPane.ERROR_MESSAGE
										); 
							}

							if (conn != null) {
								logger.info("AWS MySQL Eduphoria database connection successful during Test");
								logger.debug("AWS MySQL Eduphoria database connection successful during Test");
								JOptionPane.showMessageDialog(
										frame,  
										"Eduphoria database connection succeeded", 
										"TEDS-AI",
										JOptionPane.INFORMATION_MESSAGE
										); 
							}
							
							return "done";
						}

						protected void done() {
							
							progressBarTestDB.setIndeterminate(false);
							progressBarTestDB.setString("Testing completed ...");
						}
					}
				
					new MyWorker().execute();   // run ProgressBarTestDB
				}	
		});
				
		/** 
		 * 
		 * View button on Configuration tab event handler
		 * 
		 */
		btnViewButton.addActionListener(new ActionListener() {
	       public void actionPerformed(ActionEvent e) {
	    	  
	    	   String pathname = null;
	    	   if (rdbtnCollegeReadiness.isSelected()) {
	    		   pathname = currentDir + "/Contexts/TEDSAI_Contexts.txt";
	    	   } else if (rdbtnAwareButton.isSelected()) {
	    		   pathname = currentDir + "/TEDSAI_Eduphoria/Contexts/TEDSAI_Contexts.txt";
	    	   }
	    	   
	    	   Path path = Paths.get(pathname);
	    	   Charset charset = StandardCharsets.UTF_8;
	    	   JTextArea textArea = new JTextArea(35, 80);
	    	   JScrollPane scrollPane = new JScrollPane(textArea);
	    	   String content = null;

	    	   try {
	    		   content = new String(Files.readAllBytes(path), charset);
	    		   textArea.setText(content);
	    		   textArea.setEditable(false);   // not editable - set to true if want to edit context file
	    		   JOptionPane.showMessageDialog(frame, scrollPane, "Configuration file", JOptionPane.INFORMATION_MESSAGE); 
	    	   } catch (IOException e12) {
	    		   JOptionPane.showMessageDialog(frame,
					          "Configuration file not viewable", "Error Message",
					          JOptionPane.ERROR_MESSAGE);
	    		   logger.error("Configuration file not viewable : " + e12.getMessage());
	    	   }		
		    }	
		});
		
		
		/**
		 * 
		 * menu File->Exit event handler
		 * 
		 */
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(frame, "Do you wish to Exit?", "TEDS-AI",
						JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					System.exit(ABORT);
				} 
			}
		});
		
		/** 
		 * 
		 * Documentation subMenu item event handler
		 * 
		 */
		
		mntmDocumentation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String filename = currentDir + "/Docs/TEDS-AI-Intro.pdf";
				try {
					viewer = new Viewer();
				} catch (Exception e4) {
					logger.error("Exception during Adobe viewer creation : " + e4.getMessage());
				}
				
				FileInputStream fis = null;
				try {
					fis = new FileInputStream(filename);
				} catch (FileNotFoundException e3) {
					logger.error("Exception during PDF file read : " + e3.getMessage());
					e3.printStackTrace();
				}
				try {
					viewer.setDocumentInputStream(fis);
				} catch (Exception e2) {
					logger.error("Exception during setting file to Adobe viwer : " + e2.getMessage());
					e2.printStackTrace();
				}
				getContentPane().add(viewer, BorderLayout.CENTER);
				try {
					viewer.activate();
				} catch (Exception e1) {
					logger.error("Exception activating Adobe viewer : " + e1.getMessage());
					e1.printStackTrace();
				} 
								
				frame2 = new JFrame("TEDS-AI documentation");
				frame2.setSize(1024, 986);
				frame2.setLocationRelativeTo(null);
				frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame2.setVisible(true);
				Image image2 = null;
				try {
					image2 = ImageIO.read(this.getClass().getResource("/teds_ai/resources/favicon-96x96.png"));
				} catch (IOException e6) {
					logger.error("Exception during reading of UI icon for documentation frame : " + e6.getMessage());
				}
				frame2.setIconImage(image2);
				frame2.getContentPane().add(viewer);

	    	}
		});
		
		/** 
		 * 
		 * About subMenu item event handler
		 * 
		 */
		mntmAbout.addActionListener(new ActionListener() {
		       public void actionPerformed(ActionEvent e) {
		    	   strAbout = "TEDS Assessment Importer\n" + 
		                      "version 1.0.0 (beta release)\n\n" +
		    			      "This software is a utility for converting assessment scores into\n" +
		                      "Texas Education Standard / (TEDS) Ed-Fi® 1.0 format for import\n" + 
		    			      "into the Texas Student Data System studentGPS® dashboards.\n\n" +
		                      "Initial release developed by Education Service Center Region 10,\n" +
		    			      "Technology & Data Services department, under a grant from the\n" +
		                      "Michael & Susan Dell Foundation, with technology provided by the\n" +
		    			      "Ed-Fi® Alliance.  Subsequent development funded by the Texas Education\n" +
		                      "Agency (TEA) as part of the TSDS project.\n\n" +
		    			      "The Ed-Fi® marks are trademarks of the Ed-Fi® Alliance and are used under\n" +
		                      "license.\n\n" +
		    			      "Developers:\n" +
		                      "Adam Warner, Tony Esposito, Chris Bull, Don Huffman\n" +
		    			      "Education Service Center Region 10\n" +
		                      "Richardson, Texas\n" +
		    			      "Email: reveal@region10.org\n\n" +
		    			      "© Copyright 2019 Texas Education Agency";
		    	   JOptionPane.showMessageDialog(frame,
					          strAbout, "About TEDS-AI",
					          JOptionPane.INFORMATION_MESSAGE);
		       }
		});
		
		/** 
		 * 
		 * Logs subMenu item event handlers
		 * 
		 */
		mntmUi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				File file = new File(currentDir + "/Logs/TEDS-AI-UI.log");
				
				if (!file.exists()) {
					try {
						file.createNewFile();
					} catch (IOException e1) {
						logger.error("IOException UI log file does not exist and cannot be created : " + e1.getMessage());
					}
				}
				
				JTextArea textArea = new JTextArea(40, 125);
				BufferedReader buff = null;
				try {
					buff = new BufferedReader(new FileReader(currentDir + "/Logs/TEDS-AI-UI.log"));
					String str;
				    while ((str = buff.readLine()) != null) {
				    	textArea.append("\n"+str);
				    }
				} catch (IOException ex) {
				    JOptionPane.showMessageDialog(frame,
					          "Log file not viewable", "UI Detailed Log",
					          JOptionPane.ERROR_MESSAGE);
				    logger.error("UI log file not viewable : " + ex.getMessage());
				}
			    textArea.setEditable(false);
			    JScrollPane scrollPane = new JScrollPane(textArea);
			    JOptionPane.showMessageDialog(frame, scrollPane, "UI Detailed Log", JOptionPane.INFORMATION_MESSAGE );
			    try {
					buff.close();
				} catch (IOException ex1) {
					logger.error("IOException closing UI log file : " + ex1.getMessage());
				} 
			}
		});
		
		mntmCollege.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				File file = new File(currentDir + "/Logs/TEDS-AI-College.log");
				
				if (!file.exists()) {
					try {
						file.createNewFile();
					} catch (IOException e1) {
						logger.error("IOException College log file does not exist and cannot be created : " + e1.getMessage());
					}
				}
				
				JTextArea textArea = new JTextArea(40, 125);
				BufferedReader buff = null;
				try {
					buff = new BufferedReader(new FileReader(currentDir + "/Logs/TEDS-AI-College.log"));
					String str;
				    while ((str = buff.readLine()) != null) {
				    	textArea.append("\n"+str);
				    }
				} catch (IOException exl) {
				    JOptionPane.showMessageDialog(frame,
					          "Log file not viewable", "College Readiness Detailed Log",
					          JOptionPane.ERROR_MESSAGE);
				    logger.error("College log file not viewable : " + exl.getMessage());
				}
			    textArea.setEditable(false);
			    JScrollPane scrollPane = new JScrollPane(textArea);
			    JOptionPane.showMessageDialog(frame, scrollPane, "College Readiness Detailed Log", JOptionPane.INFORMATION_MESSAGE );
			    try {
					buff.close();
				} catch (IOException ex1) {
					logger.error("IOException closing College log file : " + ex1.getMessage());
				} 
			}
		});
		
		mntmAware.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				File file = new File(currentDir + "/TEDSAI_Eduphoria/Logs/TEDS-AI-Aware.log");
				
				if (!file.exists()) {
					try {
						file.createNewFile();
					} catch (IOException e1) {
						logger.error("IOException Aware log file does not exist and cannot be created : " + e1.getMessage());
					}
				}
				
				JTextArea textArea = new JTextArea(40, 125);
				BufferedReader buff = null;
				try {
					buff = new BufferedReader(new FileReader(currentDir + "/TEDSAI_Eduphoria/Logs/TEDS-AI-Aware.log"));
					String str;
				    while ((str = buff.readLine()) != null) {
				    	textArea.append("\n"+str);
				    }
				} catch (IOException exl) {
				    JOptionPane.showMessageDialog(frame,
					          "Log file not viewable", "Aware Detailed Log",
					          JOptionPane.ERROR_MESSAGE);
				    logger.error("Aware log file not viewable : " + exl.getMessage());
				}
			    textArea.setEditable(false);
			    JScrollPane scrollPane = new JScrollPane(textArea);
			    JOptionPane.showMessageDialog(frame, scrollPane, "Aware Detailed Log", JOptionPane.INFORMATION_MESSAGE );
			    try {
					buff.close();
				} catch (IOException ex1) {
					logger.error("IOException closing Aware log file : " + ex1.getMessage());
				} 
			}
		});
		
		/** 
		 * 
		 * TSDS Support hyperlink event handler
		 * 
		 */
		hyperlinkTIMS.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			    try {
			        Desktop.getDesktop().browse(new URI("https://tealprod.tea.state.tx.us/TSDS/Support"));
			    } catch (IOException | URISyntaxException ex1) {
			    	logger.error("Exception resolving TSDS Support URL : " + ex1.getMessage());
			    }
			}
			
			@Override
		    public void mouseEntered(MouseEvent e) {
				hyperlinkTIMS.setText("<html><a href=''>TSDS Support</a></html>");
		    }
			
			@Override
		    public void mouseExited(MouseEvent e) {
				hyperlinkTIMS.setText("TSDS Support");
		    }
		});
	
	}
}