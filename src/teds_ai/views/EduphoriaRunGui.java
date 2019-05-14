package teds_ai.views;

import teds_ai.common.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
import javax.swing.JButton;

import java.awt.Toolkit;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JList;
import javax.swing.AbstractListModel;

import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.SpinnerNumberModel;
import javax.swing.JToggleButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPasswordField;
import javax.swing.JTextPane;

import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.DropMode;


public class EduphoriaRunGui extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Components
	private JPanel ctpMain;
	
	// Component models
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmExit;
	private JMenu mnHelp;
	private JMenuItem mntmAbout;
	private JPanel pnlUsrAction;
	private JPanel pnlCfg;
	private JPanel pnlBenchMarks;
	private JMenu mnHome;
	private JMenuItem menuItem_1;
	private JMenu mnLogs;
	private JMenuItem menuItem_2;
	private JMenu mnTools;
	private JMenuItem menuItem_3;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JPanel pnlBar;
	private JButton btnHome;
	private JButton btnSave;
	private JRadioButton rdbtnNewRadioButton;
	private JTextArea txtrCountyDistrictNumber;
	private JTextField textField_2;
	private JTextField textField;
	private JTextArea txtrCollectionYear;
	private JTextArea textArea_1;
	private JTextField textField_1;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					EduphoriaRunGui frame = new EduphoriaRunGui();
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EduphoriaRunGui()
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage(EduphoriaRunGui.class.getResource("/windowBuilder/resources/R10TEDSAI.png")));
		
		initComponents();
		createEvents();
	}

	//////////////////////////////////////////////////////////////
	// This method contains all of the code for creating and
	// initializing components.
	//////////////////////////////////////////////////////////////
	private void initComponents()
	{
		setTitle("TEDS Assessment Importer");
	//	setIconImage(Toolkit.getDefaultToolkit().getImage(FirstWbGui.class.getResource("/windowBuilder/resources/R10TEDSAI.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 800, 463);
		setBounds(300, 300, 800, 700);
		
		menuBar = new JMenuBar();
		menuBar.setForeground(UIManager.getColor("MenuBar.foreground"));
		menuBar.setBackground(new Color(65, 105, 225));
		setJMenuBar(menuBar);
		
		mnHome = new JMenu("Home");
		mnHome.setBackground(new Color(0, 191, 255));
		menuBar.add(mnHome);
		
		menuItem_1 = new JMenuItem("Exit");
		mnHome.add(menuItem_1);
		
		JMenu mnConfig = new JMenu("Configuration");
		menuBar.add(mnConfig);
		
		JMenuItem menuItem = new JMenuItem("Exit");
		mnConfig.add(menuItem);
		
		mnLogs = new JMenu("Logs");
		menuBar.add(mnLogs);
		
		menuItem_2 = new JMenuItem("Exit");
		mnLogs.add(menuItem_2);
		
		mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		mntmAbout = new JMenuItem("About");
		
				mnHelp.add(mntmAbout);
		
		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mntmExit = new JMenuItem("Exit");
	//	mntmExit.setIcon(new ImageIcon(FirstWbGui.class.getResource("/windowBuilder/resources/exit_16.png")));

		mnFile.add(mntmExit);
		
		mnTools = new JMenu("Tools");
		mnTools.setBackground(new Color(255, 204, 0));
		menuBar.add(mnTools);
		
		menuItem_3 = new JMenuItem("Exit");
		mnTools.add(menuItem_3);
		ctpMain = new JPanel();
		ctpMain.setBackground(UIManager.getColor("Label.disabledShadow"));
		ctpMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(ctpMain);
		
		pnlUsrAction = new JPanel();
		pnlUsrAction.setBackground(Color.WHITE);
		pnlUsrAction.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "User Action", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		JButton btnNewButton_4 = new JButton("Exit");
		btnNewButton_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton_4.setForeground(Color.BLACK);
		btnNewButton_4.setBackground(Color.RED);
		
		btnHome = new JButton("Home");
		btnHome.setForeground(Color.BLACK);
		btnHome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnHome.setBackground(Color.YELLOW);
		
		btnSave = new JButton("Run");
		btnSave.setForeground(Color.BLACK);
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSave.setBackground(Color.GREEN);
		GroupLayout gl_pnlUsrAction = new GroupLayout(pnlUsrAction);
		gl_pnlUsrAction.setHorizontalGroup(
			gl_pnlUsrAction.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlUsrAction.createSequentialGroup()
					.addContainerGap(212, Short.MAX_VALUE)
					.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
					.addGap(55)
					.addComponent(btnHome)
					.addGap(57)
					.addComponent(btnNewButton_4, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
					.addGap(215))
		);
		gl_pnlUsrAction.setVerticalGroup(
			gl_pnlUsrAction.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlUsrAction.createSequentialGroup()
					.addGroup(gl_pnlUsrAction.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnHome, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_4, GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE))
					.addContainerGap())
		);
		pnlUsrAction.setLayout(gl_pnlUsrAction);
		
		pnlCfg = new JPanel();
		pnlCfg.setBackground(Color.WHITE);
		pnlCfg.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Benchmark Assessment Test Information", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		txtrCountyDistrictNumber = new JTextArea();
		txtrCountyDistrictNumber.setText("Select Grade Level");
		txtrCountyDistrictNumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		
		textField = new JTextField();
		textField.setColumns(10);
		
		txtrCollectionYear = new JTextArea();
		txtrCollectionYear.setText("Select Subject");
		txtrCollectionYear.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		textArea_1 = new JTextArea();
		textArea_1.setBackground(Color.CYAN);
		
		JTextArea txtrSelectTests = new JTextArea();
		txtrSelectTests.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtrSelectTests.setText("Select Test(s)");
		
		JTextArea txtrStudentparentXmlFile = new JTextArea();
		txtrStudentparentXmlFile.setText("Student-Parent XML File");
		txtrStudentparentXmlFile.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		GroupLayout gl_pnlCfg = new GroupLayout(pnlCfg);
		gl_pnlCfg.setHorizontalGroup(
			gl_pnlCfg.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlCfg.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlCfg.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlCfg.createSequentialGroup()
							.addGroup(gl_pnlCfg.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlCfg.createSequentialGroup()
									.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
									.addGroup(gl_pnlCfg.createParallelGroup(Alignment.LEADING)
										.addComponent(textField, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
										.addComponent(txtrCollectionYear, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)))
								.addComponent(textArea_1, GroupLayout.PREFERRED_SIZE, 336, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtrStudentparentXmlFile, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE))
							.addGap(19))
						.addGroup(gl_pnlCfg.createSequentialGroup()
							.addComponent(txtrCountyDistrictNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(240, Short.MAX_VALUE))
						.addGroup(gl_pnlCfg.createSequentialGroup()
							.addComponent(txtrSelectTests, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(269, Short.MAX_VALUE))))
		);
		gl_pnlCfg.setVerticalGroup(
			gl_pnlCfg.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlCfg.createSequentialGroup()
					.addGroup(gl_pnlCfg.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtrCountyDistrictNumber, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtrCollectionYear, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlCfg.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtrSelectTests, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(textArea_1, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtrStudentparentXmlFile, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(7))
		);
		pnlCfg.setLayout(gl_pnlCfg);
		
		JTextPane txtpnNeedHelpFirst = new JTextPane();
		txtpnNeedHelpFirst.setText("Need help? First, consult the online docs (use Help on Menu).\r\nIf further help is needed, please submit a TIMS ticket to TEA support via your Level 2 ESC or vendor support contact. \r\nBe sure to specify \"TEDS-AI\" in your ticket with subsystem \"studentGPS\".");
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(EduphoriaRunGui.class.getResource("/windowBuilder/resources/R10Logo.PNG")));
		
		lblNewLabel_1 = new JLabel("TEDS Assessment Importer");
		lblNewLabel_1.setForeground(UIManager.getColor("TextField.shadow"));
		lblNewLabel_1.setBackground(UIManager.getColor("Button.disabledShadow"));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		pnlBar = new JPanel();
		pnlBar.setBackground(new Color(204, 255, 255));
		
		JTextPane txtpnTeaAnd = new JTextPane();
		txtpnTeaAnd.setText("Copyright 2018, TEA and Region 10 ESC, All rights reserved \r\nAlpha Release 0.4.0 | Region 10 Education Service Center");
		
		pnlBenchMarks = new JPanel();
		pnlBenchMarks.setBackground(Color.WHITE);
		pnlBenchMarks.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Choose Benchmark Assessment Test to Execute", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		rdbtnNewRadioButton = new JRadioButton("Eduphoria! Aware");
		rdbtnNewRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnNewRadioButton.setBackground(Color.WHITE);
		
		JTextArea txtrNoteBeSure = new JTextArea();
		txtrNoteBeSure.setForeground(Color.RED);
		txtrNoteBeSure.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtrNoteBeSure.setText("NOTE: Be sure to complete your test Configuration before doing\r\nany processing.");
		GroupLayout gl_pnlBenchMarks = new GroupLayout(pnlBenchMarks);
		gl_pnlBenchMarks.setHorizontalGroup(
			gl_pnlBenchMarks.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlBenchMarks.createSequentialGroup()
					.addContainerGap()
					.addComponent(rdbtnNewRadioButton)
					.addContainerGap(222, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_pnlBenchMarks.createSequentialGroup()
					.addContainerGap(11, Short.MAX_VALUE)
					.addComponent(txtrNoteBeSure, GroupLayout.PREFERRED_SIZE, 344, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_pnlBenchMarks.setVerticalGroup(
			gl_pnlBenchMarks.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlBenchMarks.createSequentialGroup()
					.addGap(16)
					.addComponent(rdbtnNewRadioButton)
					.addGap(130)
					.addComponent(txtrNoteBeSure, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(31))
		);
		pnlBenchMarks.setLayout(gl_pnlBenchMarks);

		GroupLayout gl_ctpMain = new GroupLayout(ctpMain);
		gl_ctpMain.setHorizontalGroup(
			gl_ctpMain.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_ctpMain.createSequentialGroup()
					.addComponent(lblNewLabel)
					.addGap(18)
					.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 301, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(97, Short.MAX_VALUE))
				.addGroup(gl_ctpMain.createSequentialGroup()
					.addComponent(txtpnNeedHelpFirst, GroupLayout.DEFAULT_SIZE, 1086, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_ctpMain.createSequentialGroup()
					.addComponent(txtpnTeaAnd, GroupLayout.PREFERRED_SIZE, 352, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(744, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_ctpMain.createSequentialGroup()
					.addGroup(gl_ctpMain.createParallelGroup(Alignment.TRAILING)
						.addComponent(pnlBar, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE)
						.addComponent(pnlUsrAction, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_ctpMain.createSequentialGroup()
							.addComponent(pnlBenchMarks, GroupLayout.PREFERRED_SIZE, 377, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(pnlCfg, GroupLayout.PREFERRED_SIZE, 377, Short.MAX_VALUE)))
					.addGap(336))
		);
		gl_ctpMain.setVerticalGroup(
			gl_ctpMain.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_ctpMain.createSequentialGroup()
					.addGroup(gl_ctpMain.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(pnlBar, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addGroup(gl_ctpMain.createParallelGroup(Alignment.BASELINE)
						.addComponent(pnlBenchMarks, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(pnlCfg, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlUsrAction, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtpnNeedHelpFirst, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtpnTeaAnd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(47))
		);
		
		initCustomerList();
		ctpMain.setLayout(gl_ctpMain);
	}
	
	private void initCustomerList()
	{
		// Adding some "random" people and superheroes to the customerList
		//customerList.addElement(new Person("Dan", "Grissom", 'M', 29));
		//customerList.addElement(new Person("Jay", "Brown", 'M', 46));
		//customerList.addElement(new Person("Jane", "Smith", 'F', 12));
		//customerList.addElement(new Person("Jimmy", "Bartholomew", 'M', 20));
		//customerList.addElement(new Person("Esther", "Obama", 'F', 73));
		//customerList.addElement(new SuperHero("Clark", "Kent", 'M', 20, "X-Ray Vision", "Kryptonite", "Wonder Woman"));
		//customerList.addElement(new SuperHero("Larry", "Boy", 'M', 30, "Joy", "Can't Read", "Archibald"));
		//customerList.addElement(new Person("Montrell", "Thigpen", 'M', 19));
		//customerList.addElement(new Person("Wallace", "Grommit", 'M', 44));
		//customerList.addElement(new Person("Edward", "Kim", 'M', 21));
		
	}

	//////////////////////////////////////////////////////////////
	// This method contains all of the code for creating events
	//////////////////////////////////////////////////////////////
	private void createEvents()
	{
		
		//mntmAbout.addActionListener(new ActionListener() {
		//	public void actionPerformed(ActionEvent e) {
		//		About about = new About();
		//		about.setModal(true);
		//		about.setVisible(true);
		//	}
		//});
		
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ret = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
				if (ret == JOptionPane.YES_OPTION)
				{
					// Elegantly shut down program
					// Save any work into file or database, etc.
					System.exit(0);
				}
			}
		});
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
