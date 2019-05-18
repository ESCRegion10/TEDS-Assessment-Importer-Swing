/*--------------------------------------------------------------------------------------
 * 
 * Class:	Main.java
 * 
 * Purpose:	Contains all components and event handlers for TEDS-AI.
 * 	
 * History:
 * Date			Author			Remarks
 * 20190328		T. Esposito		original version.
 * 20190516		T. Esposito		added View and Save event handlers on Configuration tab.
 * 								added input validation on Configuration tab.
 * 20190617		T. Esposito		adjusted size of JFrame.
 * 								added Note to bottom of all tab screens.
 * 								added code to center JFrame on any screen.
 * 								added message for successful Save of configuration.
 * 
 *-------------------------------------------------------------------------------------*/

package teds_ai.views;

import java.awt.EventQueue;

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
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
//import javax.swing.event.DocumentEvent;
//import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.ComponentOrientation;

import javax.swing.DropMode;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class Main extends JFrame {

	// Components
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;
	private JPanel contentPane;
	private JPanel pnlCollege;
	private JMenuBar menuBar;
	private JMenu mnHelp;
	private JMenuItem mntmAbout;
	private JMenu mnFile;
	private JMenuItem mntmExit;
	private JRadioButton rdbtnSAT;
	private JRadioButton rdbtnPSAT10;
	private JRadioButton rdbtnPSAT89;
	private JRadioButton rdbtnACT;
	private JRadioButton rdbtnAP;
	private JRadioButton rdbtnTSI;
	private JRadioButton rdbtnCollegeReadiness;
//	private JPanel pnlBar;  -- was going to be used for blue bar
	private JButton btnRunButton;
	private JButton btnChooseInput;
	private JButton btnChooseXML;
	private JButton btnChooseDir;
	private JButton btnChooseDirectorydefault;
	private JButton btnExitButton;
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private final ButtonGroup buttonGroup_2 = new ButtonGroup();
	private JFileChooser jfcChooseInput;
	private JFileChooser jfcChooseXML;
	private JFileChooser jfcChooseDir;
	private JFileChooser jfcChooseDirectorydefault;
	private JTextArea txtrLogOutput;
	private JTextField xmlFile;
	private JTextField inputFile;
	private JTextField outputDir;
	private JProgressBar progressBar;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblXMLFile;
	private JLabel lblNewLabel_3;
	private JLabel lblOutputDirectory;
	private JLabel lblInputFile;
	private JLabel lblChooseAssessmentGrade;
	final String OS = System.getProperty("os.name").toLowerCase();
	String selectedInputType;
	String strinputFile = new String(); 
	String stroutputDir = new String(); 
	String strxmlFile = new String();
	private JSpinner spinSelectSubject;
	private JSpinner spinCollectYr;
	private JSpinner spinSelectGrade;
	private JTextArea textArea_1;
	private JLabel label_1;
	private JButton btnHome;
	private JLabel lblCDN;
	private JLabel lblChooseAssessmentTest;
	private JTextField outputDirDefault;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField txtCDN;
	private JButton btnTest;
	private JButton btnViewButton;
	private JButton button_2;
	private JButton btnExit_1;
	private JButton btnSaveButton;
	private JRadioButton rdbtnAwareButton;
	private static Main frame;
		
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//Main frame = new Main();
					frame = new Main();
					frame.setLocationRelativeTo(null);   // center frame
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setTitle("TEDS-AI");
		
		initComponents();
		createEvents();
	}
	
	///////////////////////////////////////////////////
	// The method that follows contains all of the code  
	// for creating and initializing components.
	///////////////////////////////////////////////////

	/**
	 * initialize all components on Main page
	 */
	private void initComponents() {

		setForeground(Color.WHITE);
		setBackground(Color.WHITE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/teds_ai/resources/favicon-16x16.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 840, 860);   // size of JFrame
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mntmExit = new JMenuItem("Exit");
		mntmExit.setIcon(null);
		mnFile.add(mntmExit);
		
		mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Main.class.getResource("/teds_ai/resources/R10Logo.PNG")));
		
		lblNewLabel_1 = new JLabel("TEDS-AI");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setForeground(Color.GRAY);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 26));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 358, GroupLayout.PREFERRED_SIZE)
					.addGap(304)
					.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
					.addGap(18))
				.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 785, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE))
		);
		
		pnlCollege = new JPanel();
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
		
		btnRunButton = new JButton("Run");
		btnRunButton.setSelected(true);
		btnRunButton.setToolTipText("Run TEDS-AI tool to process input file");
		btnRunButton.setForeground(Color.BLACK);
		btnRunButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnRunButton.setBackground(Color.GREEN);
		
		btnExitButton = new JButton("Exit");
		btnExitButton.setSelected(true);
		btnExitButton.setToolTipText("Exit TEDS-AI UI");
		btnExitButton.setForeground(Color.BLACK);
		btnExitButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnExitButton.setBackground(Color.RED);
		
		lblNewLabel_3 = new JLabel("Status");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		txtrLogOutput = new JTextArea();
		txtrLogOutput.setToolTipText("Output of TEDS-AI backend log file");
		txtrLogOutput.setText("Log output:");
		txtrLogOutput.setRows(10);
		txtrLogOutput.setName("Log output:");
		txtrLogOutput.setLineWrap(true);
		txtrLogOutput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtrLogOutput.setEditable(false);
		txtrLogOutput.setDropMode(DropMode.INSERT);
		txtrLogOutput.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		txtrLogOutput.setColumns(100);
		txtrLogOutput.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		lblXMLFile = new JLabel("Student-Parent XML File");
		lblXMLFile.setHorizontalAlignment(SwingConstants.LEFT);
		lblXMLFile.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		xmlFile = new JTextField();
		xmlFile.setHorizontalAlignment(SwingConstants.LEFT);
		xmlFile.setFont(new Font("Tahoma", Font.PLAIN, 14));
		xmlFile.setColumns(10);
		
		lblOutputDirectory = new JLabel("Output Directory");
		lblOutputDirectory.setHorizontalAlignment(SwingConstants.LEFT);
		lblOutputDirectory.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		lblInputFile = new JLabel("Input File");
		lblInputFile.setHorizontalAlignment(SwingConstants.LEFT);
		lblInputFile.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		outputDir = new JTextField();
		outputDir.setHorizontalAlignment(SwingConstants.LEFT);
		outputDir.setFont(new Font("Tahoma", Font.PLAIN, 14));
		outputDir.setColumns(10);
		
		inputFile = new JTextField();
		inputFile.setHorizontalAlignment(SwingConstants.LEFT);
		inputFile.setFont(new Font("Tahoma", Font.PLAIN, 14));
		inputFile.setColumns(10);
		
		btnChooseXML = new JButton("Choose file");
		btnChooseXML.setSelected(true);
		btnChooseXML.setToolTipText("Get Student-Parent XML file");
		btnChooseXML.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnChooseXML.setBorder(UIManager.getBorder("Button.border"));
		btnChooseXML.setBackground(new Color(51, 153, 255));
		jfcChooseXML = new JFileChooser();
		
		btnChooseDir = new JButton("Choose directory");
		btnChooseDir.setToolTipText("Enter directory location of XML output files");
		btnChooseDir.setSelected(true);
		btnChooseDir.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnChooseDir.setBackground(new Color(51, 153, 255));
		jfcChooseDir = new JFileChooser();
		
		btnChooseInput = new JButton("Choose file");
		btnChooseInput.setToolTipText("Get input assessment file to process");
		btnChooseInput.setSelected(true);
		btnChooseInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnChooseInput.setBackground(new Color(51, 153, 255));
		jfcChooseInput = new JFileChooser();
		
		JLabel label = new JLabel("Choose Assessment Type to Process");
		label.setForeground(new Color(255, 153, 0));
		label.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		JTextPane txtpnNeedHelpFirst = new JTextPane();
		txtpnNeedHelpFirst.setText("Need help? First, consult the online docs (use Help on Menu).  If further help is needed, please submit a TIMS ticket to TEA support via your Level 2 ESC or vendor support contact.  Be sure to specify \"TEDS-AI\" in your ticket with subsystem \"studentGPS\".\r\nCopyright 2018-19, TEA and Region 10 ESC, All rights reserved.  Alpha Release 0.5.0 | Region 10 Education Service Center");
		GroupLayout gl_pnlCollege = new GroupLayout(pnlCollege);
		gl_pnlCollege.setHorizontalGroup(
			gl_pnlCollege.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlCollege.createSequentialGroup()
					.addGroup(gl_pnlCollege.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlCollege.createSequentialGroup()
							.addGroup(gl_pnlCollege.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlCollege.createSequentialGroup()
									.addGap(10)
									.addGroup(gl_pnlCollege.createParallelGroup(Alignment.LEADING, false)
										.addComponent(lblInputFile, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblXMLFile, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblOutputDirectory, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_pnlCollege.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(btnRunButton, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(btnExitButton, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))))
								.addGroup(gl_pnlCollege.createSequentialGroup()
									.addContainerGap()
									.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_pnlCollege.createSequentialGroup()
									.addContainerGap()
									.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlCollege.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_pnlCollege.createSequentialGroup()
									.addGroup(gl_pnlCollege.createParallelGroup(Alignment.LEADING)
										.addComponent(outputDir, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
										.addComponent(inputFile, GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
										.addComponent(xmlFile, GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_pnlCollege.createParallelGroup(Alignment.LEADING)
										.addComponent(btnChooseInput, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
										.addComponent(btnChooseDir, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnChooseXML, GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)))
								.addComponent(txtrLogOutput, GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)))
						.addGroup(gl_pnlCollege.createSequentialGroup()
							.addGap(11)
							.addComponent(rdbtnSAT)
							.addGap(18)
							.addComponent(rdbtnPSAT10)
							.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
							.addComponent(rdbtnPSAT89)
							.addGap(18)
							.addComponent(rdbtnACT)
							.addGap(18)
							.addComponent(rdbtnAP)
							.addGap(18)
							.addComponent(rdbtnTSI))
						.addGroup(gl_pnlCollege.createSequentialGroup()
							.addContainerGap()
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 342, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING, gl_pnlCollege.createSequentialGroup()
							.addContainerGap()
							.addComponent(txtpnNeedHelpFirst, GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_pnlCollege.setVerticalGroup(
			gl_pnlCollege.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlCollege.createSequentialGroup()
					.addGap(27)
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
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
								.addComponent(inputFile, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblInputFile, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
							.addGap(49))
						.addGroup(gl_pnlCollege.createSequentialGroup()
							.addGroup(gl_pnlCollege.createParallelGroup(Alignment.BASELINE)
								.addComponent(xmlFile, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblXMLFile, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnChooseXML))
							.addGap(7)
							.addComponent(btnChooseInput)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlCollege.createParallelGroup(Alignment.BASELINE)
								.addComponent(outputDir, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblOutputDirectory, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnChooseDir))
							.addGap(18)))
					.addGroup(gl_pnlCollege.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlCollege.createSequentialGroup()
							.addGroup(gl_pnlCollege.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnRunButton, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnExitButton, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
							.addGap(27)
							.addComponent(lblNewLabel_3)
							.addGap(18)
							.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
						.addComponent(txtrLogOutput, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
					.addComponent(txtpnNeedHelpFirst, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
					.addGap(26))
		);
		pnlCollege.setLayout(gl_pnlCollege);
		
		JPanel pnlAware = new JPanel();
		pnlAware.setBackground(Color.WHITE);
		pnlAware.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pnlAware.setBorder(new TitledBorder(null, "Eduphoria Aware Assessment", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tabbedPane.addTab("Aware", null, pnlAware, "Home for Eduphoria Assessment exam");
		
		JLabel lblSelectGrade = new JLabel("Select Grade Level");
		lblSelectGrade.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		spinSelectGrade = new JSpinner();
		spinSelectGrade.setBackground(new Color(153, 255, 255));
		spinSelectGrade.setToolTipText("Select grade level");
		spinSelectGrade.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinSelectGrade.setModel(new SpinnerListModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		
		JLabel lblSelectSubject = new JLabel("Select Subject");
		lblSelectSubject.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		spinSelectSubject = new JSpinner();
		spinSelectSubject.setBackground(new Color(153, 255, 255));
		spinSelectSubject.setToolTipText("Select test subject");
		spinSelectSubject.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinSelectSubject.setModel(new SpinnerListModel(new String[] {"English", "Math", "Science", "Social Studies"}));
		
		lblChooseAssessmentGrade = new JLabel("Choose Assessment Test by Grade and Subject to Process");
		lblChooseAssessmentGrade.setForeground(new Color(255, 153, 0));
		lblChooseAssessmentGrade.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		JTextArea textArea = new JTextArea();
		textArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JLabel lblNewLabel_4 = new JLabel("Select Test(s)");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		textArea_1 = new JTextArea();
		textArea_1.setText("Log output:");
		textArea_1.setRows(10);
		textArea_1.setName("Log output:");
		textArea_1.setLineWrap(true);
		textArea_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textArea_1.setEditable(false);
		textArea_1.setDropMode(DropMode.INSERT);
		textArea_1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		textArea_1.setColumns(100);
		textArea_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JButton btnRun = new JButton("Run");
		btnRun.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JButton btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JProgressBar progressBar_1 = new JProgressBar();
		
		label_1 = new JLabel("Status");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		btnHome = new JButton("");
		btnHome.setIcon(new ImageIcon(Main.class.getResource("/teds_ai/resources/icon_Home_SkyBlue.png")));
		btnHome.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JTextPane textPane = new JTextPane();
		textPane.setText("Need help? First, consult the online docs (use Help on Menu).  If further help is needed, please submit a TIMS ticket to TEA support via your Level 2 ESC or vendor support contact.  Be sure to specify \"TEDS-AI\" in your ticket with subsystem \"studentGPS\".\r\nCopyright 2018-19, TEA and Region 10 ESC, All rights reserved.  Alpha Release 0.5.0 | Region 10 Education Service Center");
		GroupLayout gl_pnlAware = new GroupLayout(pnlAware);
		gl_pnlAware.setHorizontalGroup(
			gl_pnlAware.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlAware.createSequentialGroup()
					.addGap(26)
					.addGroup(gl_pnlAware.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlAware.createSequentialGroup()
							.addComponent(lblNewLabel_4)
							.addContainerGap())
						.addGroup(gl_pnlAware.createSequentialGroup()
							.addGroup(gl_pnlAware.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlAware.createSequentialGroup()
									.addGroup(gl_pnlAware.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_pnlAware.createSequentialGroup()
											.addGroup(gl_pnlAware.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_pnlAware.createSequentialGroup()
													.addComponent(lblSelectGrade, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
													.addGap(32))
												.addGroup(gl_pnlAware.createSequentialGroup()
													.addComponent(lblSelectSubject, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
													.addPreferredGap(ComponentPlacement.RELATED)))
											.addGroup(gl_pnlAware.createParallelGroup(Alignment.LEADING, false)
												.addComponent(spinSelectSubject, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(spinSelectGrade, GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE))
											.addPreferredGap(ComponentPlacement.RELATED, 358, Short.MAX_VALUE))
										.addComponent(lblChooseAssessmentGrade, GroupLayout.DEFAULT_SIZE, 744, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED))
								.addGroup(gl_pnlAware.createSequentialGroup()
									.addGroup(gl_pnlAware.createParallelGroup(Alignment.TRAILING, false)
										.addGroup(gl_pnlAware.createSequentialGroup()
											.addComponent(btnRun, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
											.addGap(32)
											.addComponent(btnHome, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE))
										.addComponent(textArea, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 312, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
									.addGroup(gl_pnlAware.createParallelGroup(Alignment.LEADING, false)
										.addComponent(textArea_1, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 378, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_pnlAware.createSequentialGroup()
											.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
											.addGap(18)
											.addComponent(progressBar_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
							.addGap(27))))
				.addGroup(Alignment.TRAILING, gl_pnlAware.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 777, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_pnlAware.setVerticalGroup(
			gl_pnlAware.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlAware.createSequentialGroup()
					.addGap(24)
					.addComponent(lblChooseAssessmentGrade, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_pnlAware.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_pnlAware.createSequentialGroup()
							.addGap(26)
							.addComponent(lblSelectGrade, GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE))
						.addGroup(gl_pnlAware.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinSelectGrade, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(28)
					.addGroup(gl_pnlAware.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlAware.createSequentialGroup()
							.addComponent(lblSelectSubject)
							.addGap(30)
							.addComponent(lblNewLabel_4))
						.addComponent(spinSelectSubject, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pnlAware.createParallelGroup(Alignment.BASELINE)
						.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE)
						.addComponent(textArea_1, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE))
					.addGap(19)
					.addGroup(gl_pnlAware.createParallelGroup(Alignment.TRAILING)
						.addComponent(progressBar_1, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
						.addGroup(gl_pnlAware.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnRun, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnExit, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnHome, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
							.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)))
					.addGap(43)
					.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		pnlAware.setLayout(gl_pnlAware);
		
		JPanel pnlConfig = new JPanel();
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
		rdbtnAwareButton.setEnabled(false);
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
		
		JLabel lblCollectionYear = new JLabel("Collection Year");
		lblCollectionYear.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		spinCollectYr = new JSpinner();
		spinCollectYr.setModel(new SpinnerListModel(new String[] {"2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025"}));
		spinCollectYr.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblOutputDirectorydefault = new JLabel("Output Directory (default)");
		lblOutputDirectorydefault.setHorizontalAlignment(SwingConstants.LEFT);
		lblOutputDirectorydefault.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		outputDirDefault = new JTextField();
		outputDirDefault.setHorizontalAlignment(SwingConstants.LEFT);
		outputDirDefault.setFont(new Font("Tahoma", Font.PLAIN, 14));
		outputDirDefault.setColumns(10);
		
		btnChooseDirectorydefault = new JButton("Choose directory");
		btnChooseDirectorydefault.setToolTipText("Enter directory location of XML output files");
		btnChooseDirectorydefault.setSelected(true);
		btnChooseDirectorydefault.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnChooseDirectorydefault.setBackground(new Color(51, 153, 255));
		jfcChooseDirectorydefault = new JFileChooser();
		
		JLabel lblNewLabel_6 = new JLabel("Database Host IP");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		textField_2 = new JTextField();
		textField_2.setEnabled(false);
		textField_2.setColumns(10);
		
		JLabel lblDatabasePortNumber = new JLabel("Database Port Number");
		lblDatabasePortNumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		textField_3 = new JTextField();
		textField_3.setEnabled(false);
		textField_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_3.setColumns(10);
		
		JLabel lblDatabaseName = new JLabel("Database Name");
		lblDatabaseName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		textField_4 = new JTextField();
		textField_4.setEnabled(false);
		textField_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_4.setColumns(10);
		
		JLabel lblDatabaseUsername = new JLabel("Database Username");
		lblDatabaseUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblDatabasePassword = new JLabel("Database Password");
		lblDatabasePassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		textField_5 = new JTextField();
		textField_5.setEnabled(false);
		textField_5.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_5.setColumns(10);
		
		textField_6 = new JTextField();
		textField_6.setEnabled(false);
		textField_6.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_6.setColumns(10);
		
		btnSaveButton = new JButton("Save");
		btnSaveButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		btnTest = new JButton("Test");
		btnTest.setEnabled(false);
		btnTest.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		btnViewButton = new JButton("View");
		btnViewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		button_2 = new JButton("");  // Home button
		button_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		button_2.setIcon(new ImageIcon(Main.class.getResource("/teds_ai/resources/icon_Home_SkyBlue.png")));
		
		btnExit_1 = new JButton("Exit");
		btnExit_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setText("Need help? First, consult the online docs (use Help on Menu).  If further help is needed, please submit a TIMS ticket to TEA support via your Level 2 ESC or vendor support contact.  Be sure to specify \"TEDS-AI\" in your ticket with subsystem \"studentGPS\".\r\nCopyright 2018-19, TEA and Region 10 ESC, All rights reserved.  Alpha Release 0.5.0 | Region 10 Education Service Center");
		GroupLayout gl_pnlConfig = new GroupLayout(pnlConfig);
		gl_pnlConfig.setHorizontalGroup(
			gl_pnlConfig.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlConfig.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlConfig.createParallelGroup(Alignment.LEADING)
						.addComponent(lblChooseAssessmentTest, GroupLayout.PREFERRED_SIZE, 744, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_pnlConfig.createSequentialGroup()
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
									.addComponent(btnSaveButton)
									.addGap(38)
									.addComponent(btnTest)
									.addGap(34)
									.addComponent(button_2)))
							.addGroup(gl_pnlConfig.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlConfig.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_pnlConfig.createParallelGroup(Alignment.LEADING)
										.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblNewLabel_6)
										.addGroup(gl_pnlConfig.createSequentialGroup()
											.addGroup(gl_pnlConfig.createParallelGroup(Alignment.LEADING)
												.addComponent(lblDatabasePortNumber)
												.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblDatabaseUsername, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
												.addComponent(textField_5, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE))
											.addGap(45)
											.addGroup(gl_pnlConfig.createParallelGroup(Alignment.LEADING)
												.addComponent(lblDatabasePassword, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
												.addComponent(textField_6, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblDatabaseName, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
												.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)))
										.addComponent(rdbtnAwareButton)))
								.addGroup(gl_pnlConfig.createSequentialGroup()
									.addGap(37)
									.addComponent(btnViewButton)
									.addGap(31)
									.addComponent(btnExit_1))))
						.addGroup(gl_pnlConfig.createSequentialGroup()
							.addComponent(lblOutputDirectorydefault, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnChooseDirectorydefault, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE))
						.addComponent(outputDirDefault, 380, 380, 380)
						.addComponent(textPane_1, GroupLayout.PREFERRED_SIZE, 777, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
						.addComponent(lblNewLabel_6))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pnlConfig.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtCDN, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_pnlConfig.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlConfig.createSequentialGroup()
							.addGroup(gl_pnlConfig.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlConfig.createSequentialGroup()
									.addComponent(lblCollectionYear)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(spinCollectYr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_pnlConfig.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblDatabaseName, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGap(26)
							.addGroup(gl_pnlConfig.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblOutputDirectorydefault, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnChooseDirectorydefault, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblDatabaseUsername, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblDatabasePassword, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_pnlConfig.createSequentialGroup()
							.addComponent(lblDatabasePortNumber)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlConfig.createParallelGroup(Alignment.LEADING)
						.addComponent(outputDirDefault, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_5, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_6, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
					.addGap(30)
					.addGroup(gl_pnlConfig.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_pnlConfig.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnTest)
							.addComponent(btnSaveButton))
						.addComponent(button_2)
						.addGroup(gl_pnlConfig.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnExit_1)
							.addComponent(btnViewButton)))
					.addGap(35)
					.addComponent(textPane_1, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(112, Short.MAX_VALUE))
		);
		pnlConfig.setLayout(gl_pnlConfig);
		contentPane.setLayout(gl_contentPane);
	}
	
	///////////////////////////////////////
	// The method that follows contains all 
	// of the code for creating events.
	///////////////////////////////////////

	/**
	 * create event handlers for all components on Main page
	 */
	private void createEvents() {

		/**
		 * Event handlers for buttons 
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
					JOptionPane.showMessageDialog(
							frame,  
							"Required button for Assessment Test not selected", 
							"TEDS-AI",
							JOptionPane.ERROR_MESSAGE
							);
				} 

				if (JOptionPane.showConfirmDialog(frame, "Do you wish to process this file?", "TEDS-AI",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

					if(OS.substring(0,3).equals("win")) {
						strinputFile = inputFile.getText();
						strinputFile = strinputFile.replace("\\", "/");
						stroutputDir = outputDir.getText();
						stroutputDir = stroutputDir.replace("\\", "/");
						strxmlFile = xmlFile.getText();
						strxmlFile = strxmlFile.replace("\\", "/");
					}

					class MyWorker extends SwingWorker<String, Object> {

						protected String doInBackground() {

							txtrLogOutput.setText("Log output:");

							progressBar.setIndeterminate(true);
							progressBar.setString("Processing ...");
							progressBar.setFont(new Font("Tahoma", Font.PLAIN, 14));
							progressBar.setStringPainted(true);
							progressBar.setVisible(true); 

							// need to get CollectionYear and CDN from context file -- now hard-coded
							
							//String cmd = "C:/TEDSAI_GUI/resources/app/TEDSAI/Controller/Controller/Controller_run.bat " + "2018" + " " + '"' + "C:/TEDSAI_GUI/resources/app/TEDSAI/Input/TEDS-AI-Template-Master.xlsx"
							//		+ '"' + " "	+ '"' + strinputFile + '"' + " " + selectedInputType + " " + "166903" + " " + '"' + stroutputDir + '/' + '"' + " " + '"' + strxmlFile + '"'; 

							String cmd = "C:/TEDSAI_GUI/resources/app/TEDSAI/Controller/Controller/Controller_run.bat " + "2018" + " " + '"' + "C:/TEDSAI_GUI/resources/app/TEDSAI/Input/TEDS-AI-Template-Master.xlsx"
									+ '"' + " "	+ '"' + strinputFile + '"' + " " + selectedInputType + " " + "701603" + " " + '"' + stroutputDir + '/' + '"' + " " + '"' + strxmlFile + '"'; 
							
							System.out.println(cmd);
							Process p = null;
							try {
								p = Runtime.getRuntime().exec(cmd);
							} catch (IOException ex) {
								ex.printStackTrace();
								System.exit(-1);
							} 
							try {
								p.waitFor();
							} catch (InterruptedException ite) {
								ite.printStackTrace();
								System.exit(-2);
							}
							return "done";
						}

						protected void done() {
							progressBar.setIndeterminate(false);
							progressBar.setString("Completed ...");
							JOptionPane.showMessageDialog(frame, "Processing completed.", "TEDS-AI", JOptionPane.INFORMATION_MESSAGE);
  	     
							// Read log file
							File file = new File("C:\\TEDSAI_GUI\\resources\\app\\TEDSAI\\Output\\TEDSAI_batchlog.txt");
							BufferedReader input = null;
							try {
								input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
							} catch (FileNotFoundException e1) {
								JOptionPane.showMessageDialog(
										frame,  
										"No batchlog file found", 
										"TEDS-AI",
										JOptionPane.ERROR_MESSAGE
										);
								e1.printStackTrace();
							}
							try {
								txtrLogOutput.read(input, "Reading log file");
							} catch (IOException e1) {
								JOptionPane.showMessageDialog(
										frame,  
										"Error reading batchlog file", 
										"TEDS-AI",
										JOptionPane.ERROR_MESSAGE
										); 
								e1.printStackTrace();
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

		/**
		 * Event handlers for file choosers 
		 */
		btnChooseXML.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jfcChooseXML.setCurrentDirectory(new java.io.File("C:\\TEDSAI_GUI\\resources\\app"));
				FileNameExtensionFilter filterXML = new FileNameExtensionFilter("XML files","xml");
				jfcChooseXML.setDialogTitle("Choose Student-Parent XML File");
				jfcChooseXML.setFileSelectionMode(JFileChooser.FILES_ONLY);  // show XML files
				jfcChooseXML.setFileFilter(filterXML);
				jfcChooseXML.setDialogTitle("Choose Student-Parent XML File");
				jfcChooseXML.showDialog(btnChooseXML, null);
				try {
					xmlFile.setText(jfcChooseXML.getSelectedFile().toString());
				} catch (NullPointerException npe1) {
					JOptionPane.showMessageDialog(
							frame,  
							"Required field Student-Parent XML File is empty", 
							"TEDS-AI",
							JOptionPane.ERROR_MESSAGE
							); 
				}
			}
		});

		btnChooseInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jfcChooseInput.setCurrentDirectory(new java.io.File("C:\\TEDSAI_GUI\\resources\\app"));
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
				}
			}
		});

		btnChooseDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jfcChooseDir.setCurrentDirectory(new java.io.File("C:\\TEDSAI_GUI\\resources\\app"));
				jfcChooseDir.setDialogTitle("TEDS-AI");
				jfcChooseDir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  // show directories only
				jfcChooseDir.setAcceptAllFileFilterUsed(false);
				jfcChooseDir.setDialogTitle("Choose Output Directory");
				jfcChooseDir.showDialog(btnChooseDir, null);
				try {
					outputDir.setText(jfcChooseDir.getSelectedFile().toString());
				} catch (NullPointerException npe1) {
					JOptionPane.showMessageDialog(
							frame,  
							"Required field Output Directory is empty", 
							"TEDS-AI",
							JOptionPane.ERROR_MESSAGE
							); 
				}
			}
		});
		
		btnChooseDirectorydefault.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jfcChooseDirectorydefault.setCurrentDirectory(new java.io.File("C:\\TEDSAI_GUI\\resources\\app"));
				jfcChooseDirectorydefault.setDialogTitle("TEDS-AI");
				jfcChooseDirectorydefault.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  // show directories only
				jfcChooseDirectorydefault.setAcceptAllFileFilterUsed(false);
				jfcChooseDirectorydefault.setDialogTitle("Choose Output Directory");
				jfcChooseDirectorydefault.showDialog(btnChooseDirectorydefault, null);
				try {
					outputDirDefault.setText(jfcChooseDirectorydefault.getSelectedFile().toString());
				} catch (NullPointerException npe1) {
					JOptionPane.showMessageDialog(
							frame,  
							"Required field Output Directory is empty", 
							"TEDS-AI",
							JOptionPane.ERROR_MESSAGE
							); 
				}
			}
		});
		
		/**
		 * Aware radio button
		 */
		rdbtnAwareButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				rdbtnAwareButton = (JRadioButton)e.getSource();
				textField_2.setEnabled(rdbtnAwareButton.isSelected());
				textField_3.setEnabled(rdbtnAwareButton.isSelected());
				textField_4.setEnabled(rdbtnAwareButton.isSelected());
				textField_5.setEnabled(rdbtnAwareButton.isSelected());
				textField_6.setEnabled(rdbtnAwareButton.isSelected());
			}
		});

		/**
		 * district cdn text field event handler - contents check
		 */
		
		txtCDN.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
				String regex = "\\d+";				// digits only
				String strTxt = txtCDN.getText();
				 if (strTxt.matches(regex) && strTxt.length() < 7) { 
					  System.out.println("Source: " + strTxt);  
				 } else {
					  JOptionPane.showMessageDialog(frame,
					          "Error: Please enter 6 digit number only", "Error Message",
					          JOptionPane.ERROR_MESSAGE);
				 }
			}

			@Override
			public void keyPressed(KeyEvent e) {}   // not used so do nothing
		
			@Override
			public void keyTyped(KeyEvent e) {}		// not used so do nothing
		});
		
		/** 
		 * collection year spinner event handler
		 */
		spinCollectYr.addChangeListener (new ChangeListener() {
		      public void stateChanged(ChangeEvent e) {
		          System.out.println("Collection Year: " + spinCollectYr.getValue());
		        }
		      });
		
		/** 
		 * grade spinner event handler
		 */
		spinSelectGrade.addChangeListener (new ChangeListener() {
		      public void stateChanged(ChangeEvent e) {
		    	  System.out.println("Grade: " + spinSelectGrade.getValue());
		      }
	    });
			
		/** 
		 * subject spinner event handler
		 */
		spinSelectSubject.addChangeListener (new ChangeListener() {
		      public void stateChanged(ChangeEvent e) {
		    	  System.out.println("Subject: " + spinSelectSubject.getValue());
		      }
		});
		
		/**
		 * menu File->Exit event handler
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
		 * Configuration tab, Exit button event handler
		 */
		btnExit_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(frame, "Do you wish to Exit?", "TEDS-AI",
						JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					System.exit(ABORT);
				} 
			}
		});
		
		/** 
		 * Home button on Configuration tab event handler
		 */
		button_2.addActionListener(new ActionListener() {
		       public void actionPerformed(ActionEvent e) {
		    	   tabbedPane.setSelectedIndex(0);
		       }
		});
		
		/** 
		 * Save button on Configuration tab event handler
		 */
		btnSaveButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	Path path = Paths.get("C:/TEDSAI_GUI/resources/app/TEDSAI/Contexts/TEDSAI_Contexts.txt");
		   		Charset charset = StandardCharsets.UTF_8;
		   		String content = null;
		   		
		   		try {
		   			String replaceTxtCDN = txtCDN.getText();
		   			String replaceCollectYr = (String) spinCollectYr.getValue();
		   			String replaceOutputDirDefault = outputDirDefault.getText().replaceAll("\\\\", "/");
		   			if (replaceTxtCDN.length() != 6) {
		   				JOptionPane.showMessageDialog(frame,
					          "Error: Please enter 6 digit number only", "Error Message",
					          JOptionPane.ERROR_MESSAGE);
		   			} else if (replaceOutputDirDefault.length() == 0) {
		   				JOptionPane.showMessageDialog(frame,
					          "Error: Please enter output directory", "Error Message",
					          JOptionPane.ERROR_MESSAGE);
		   			} else {
		   				try {
		   					content = new String(Files.readAllBytes(path), charset);
		   				} catch (IOException e1) {
		   					e1.printStackTrace();
		   				}
		   		
		   				content = content.replaceAll("LEACDN=[ -~]*", "LEACDN=" + replaceTxtCDN);
		   				content = content.replaceAll("CollectionYear=[ -~]*", "CollectionYear=" + replaceCollectYr);
		   				content = content.replaceAll("OutputPath=[ -~]*", "OutputPath=" + replaceOutputDirDefault + "/");
					
		   				try {
		   					Files.write(path, content.getBytes(charset));
		   				} catch (IOException e1) {
		   					e1.printStackTrace();
		   				}
		   				JOptionPane.showMessageDialog(frame,
							"Configuration saved.", "TEDS-AI", 
							JOptionPane.INFORMATION_MESSAGE);
		            }
		   	    } catch (Exception allEx) {
		   	    	JOptionPane.showMessageDialog(frame,
				          "Error: exception during Save button operation", "Error Message",
				          JOptionPane.ERROR_MESSAGE);
		   	    	allEx.printStackTrace();
		   	    } 
		    }	
		});
		
		/** 
		 * View button on Configuration tab event handler
		 */
		btnViewButton.addActionListener(new ActionListener() {
	       public void actionPerformed(ActionEvent e) {
	    	   Path path = Paths.get("C:/TEDSAI_GUI/resources/app/TEDSAI/Contexts/TEDSAI_Contexts.txt");
	    	   Charset charset = StandardCharsets.UTF_8;
	    	   JTextArea textArea = new JTextArea(35, 80);
	    	   JScrollPane scrollPane = new JScrollPane(textArea);
	    	   String content = null;

	    	   try {
	    		   content = new String(Files.readAllBytes(path), charset);
	    		   textArea.setText(content);
	    		   textArea.setEditable(false);   // not editable - set to true if want to edit context file
	    		   JOptionPane.showMessageDialog(frame, scrollPane, "Context file", JOptionPane.INFORMATION_MESSAGE); 
	    	   } catch (IOException e12) {
	    		   JOptionPane.showMessageDialog(frame,
					          "Error: Context file not viewable", "Error Message",
					          JOptionPane.ERROR_MESSAGE);
	    		   e12.printStackTrace();
	    	   }		
		    }	
		});
		
		/** 
		 * Home button on Aware tab event handler
		 */
		btnHome.addActionListener(new ActionListener() {
		       public void actionPerformed(ActionEvent e) {
		    	   tabbedPane.setSelectedIndex(0);
		       }
		});
	}
}