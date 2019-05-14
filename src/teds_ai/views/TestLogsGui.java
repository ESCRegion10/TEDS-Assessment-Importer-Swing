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

import javax.swing.ListSelectionModel;
import javax.swing.DropMode;


@SuppressWarnings("unused")
public class TestLogsGui extends JFrame
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
	private JPanel pnlTestLog;
	private JPanel pnlLogFile;
	private JMenu mnHome;
	private JMenuItem menuItem_1;
	private JMenu mnLogs;
	private JMenuItem menuItem_2;
	private JMenu mnTools;
	private JMenuItem menuItem_3;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JPanel panel;
	
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
					TestLogsGui frame = new TestLogsGui();
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
	public TestLogsGui()
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage(TestLogsGui.class.getResource("/windowBuilder/resources/R10TEDSAI.png")));
		
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
		
		JButton btnHome = new JButton("Home");
		btnHome.setForeground(Color.BLACK);
		btnHome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnHome.setBackground(new Color(255, 255, 0));
		GroupLayout gl_pnlUsrAction = new GroupLayout(pnlUsrAction);
		gl_pnlUsrAction.setHorizontalGroup(
			gl_pnlUsrAction.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_pnlUsrAction.createSequentialGroup()
					.addContainerGap(247, Short.MAX_VALUE)
					.addComponent(btnHome)
					.addGap(118)
					.addComponent(btnNewButton_4, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
					.addGap(247))
		);
		gl_pnlUsrAction.setVerticalGroup(
			gl_pnlUsrAction.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlUsrAction.createSequentialGroup()
					.addGroup(gl_pnlUsrAction.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnNewButton_4, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
						.addComponent(btnHome, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		pnlUsrAction.setLayout(gl_pnlUsrAction);
		
		pnlTestLog = new JPanel();
		pnlTestLog.setBackground(new Color(255, 255, 255));
		pnlTestLog.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Choose Test Log File", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		JList<String> list = new JList<String>();
		list.setModel(new AbstractListModel<String>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1676470918366282683L;
			String[] values = new String[] {"SAT (CSV)", "PSAT 10/NMSQT (CSV)", "PSAT 8/9 (CSV)", "ACT (Fixed-width)", "AP (CSV)", "TSI (CSV)", "Eduphoria! Aware"};
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		list.setBackground(new Color(255, 255, 255));
		list.setToolTipText("SAT (CSV)\r\nPSAT 10/NMSQT (CSV)\r\nPSAT 8/9 (CSV)\r\nACT (Fixed-width)\r\nAP (CSV)\r\nTSI (CSV)\r\nEduphoria! Aware");
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(204, 255, 255), null));
		GroupLayout gl_pnlTestLog = new GroupLayout(pnlTestLog);
		gl_pnlTestLog.setHorizontalGroup(
			gl_pnlTestLog.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlTestLog.createSequentialGroup()
					.addContainerGap()
					.addComponent(list, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(142, Short.MAX_VALUE))
		);
		gl_pnlTestLog.setVerticalGroup(
			gl_pnlTestLog.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlTestLog.createSequentialGroup()
					.addContainerGap()
					.addComponent(list, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(71, Short.MAX_VALUE))
		);
		pnlTestLog.setLayout(gl_pnlTestLog);
		
		pnlLogFile = new JPanel();
		pnlLogFile.setBackground(Color.WHITE);
		pnlLogFile.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Log File Contents", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JTextArea textArea = new JTextArea();
		textArea.setBackground(new Color(204, 255, 255));
		textArea.setEditable(false);
		textArea.setDropMode(DropMode.INSERT);
		GroupLayout gl_pnlLogFile = new GroupLayout(pnlLogFile);
		gl_pnlLogFile.setHorizontalGroup(
			gl_pnlLogFile.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlLogFile.createSequentialGroup()
					.addGap(23)
					.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 323, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(19, Short.MAX_VALUE))
		);
		gl_pnlLogFile.setVerticalGroup(
			gl_pnlLogFile.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_pnlLogFile.createSequentialGroup()
					.addContainerGap()
					.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
					.addContainerGap())
		);
		pnlLogFile.setLayout(gl_pnlLogFile);
		
		JTextPane txtpnNeedHelpFirst = new JTextPane();
		txtpnNeedHelpFirst.setText("Need help? First, consult the online docs (use Help on Menu).\r\nIf further help is needed, please submit a TIMS ticket to TEA support via your Level 2 ESC or vendor support contact. \r\nBe sure to specify \"TEDS-AI\" in your ticket with subsystem \"studentGPS\".");
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(TestLogsGui.class.getResource("/windowBuilder/resources/R10Logo.PNG")));
		
		lblNewLabel_1 = new JLabel("TEDS Assessment Importer");
		lblNewLabel_1.setForeground(UIManager.getColor("TextField.shadow"));
		lblNewLabel_1.setBackground(UIManager.getColor("Button.disabledShadow"));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		panel = new JPanel();
		panel.setBackground(new Color(204, 255, 255));
		
		JTextPane txtpnTeaAnd = new JTextPane();
		txtpnTeaAnd.setText("Copyright 2018, TEA and Region 10 ESC, All rights reserved \r\nAlpha Release 0.4.0 | Region 10 Education Service Center");

		GroupLayout gl_ctpMain = new GroupLayout(ctpMain);
		gl_ctpMain.setHorizontalGroup(
			gl_ctpMain.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_ctpMain.createSequentialGroup()
					.addGroup(gl_ctpMain.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_ctpMain.createSequentialGroup()
							.addComponent(pnlTestLog, GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(pnlLogFile, GroupLayout.PREFERRED_SIZE, 377, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(Alignment.LEADING, gl_ctpMain.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
							.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 301, GroupLayout.PREFERRED_SIZE)
							.addGap(79)))
					.addGap(10))
				.addGroup(Alignment.LEADING, gl_ctpMain.createSequentialGroup()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(Alignment.LEADING, gl_ctpMain.createSequentialGroup()
					.addComponent(pnlUsrAction, GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_ctpMain.createSequentialGroup()
					.addComponent(txtpnNeedHelpFirst, GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(Alignment.LEADING, gl_ctpMain.createSequentialGroup()
					.addComponent(txtpnTeaAnd, GroupLayout.PREFERRED_SIZE, 352, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(422, Short.MAX_VALUE))
		);
		gl_ctpMain.setVerticalGroup(
			gl_ctpMain.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_ctpMain.createSequentialGroup()
					.addGroup(gl_ctpMain.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_ctpMain.createParallelGroup(Alignment.BASELINE)
						.addComponent(pnlTestLog, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE)
						.addComponent(pnlLogFile, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE))
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
/*	private static void addPopup(Component component, final JPopupMenu popup) {
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
*/	
}
