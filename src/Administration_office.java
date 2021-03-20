import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JTextPane;

/* Generated by Together */

/**
 * This class represents the interactive interface to PACSUS administration
 * functions carried out in the Estates and Campus Services Office. Information
 * about these functions is in the Administration use case diagram (hyperlinked
 * from this class).
 *
 * The interface comprises one screen with all the functions present on it: they
 * could all be on view at once, or perhaps in alternative JPanels (in a JFrame
 * with JTabbedPane); the current date (day number) is always displayed.
 *
 * There could be any number of instances of this class, potentially one for
 * each workstation in the office, with different staff carrying different
 * functions.
 *
 * The class implements Observer, and observes the system status so that it can
 * keep the displayed current date correct.
 * 
 * @stereotype boundary
 */
@SuppressWarnings({ "deprecation", "serial", "unused" })
public class Administration_office extends JFrame implements Observer, ActionListener {
	/**
	 * Each instance of Administration_office has a navigable association to the
	 * permit list so that it can enquire about/add/delete/modify permits.
	 * 
	 * @supplierCardinality 1
	 * @clientCardinality 1..*
	 * @label Administration functions
	 * @directed
	 */

	private Permit_list lnkPermit_list;


	/**
	 * Each instance of Administration_office has a navigable association to the
	 * vehicle list so that it can enquire about/add/delete/modify vehicle details.
	 * 
	 * @clientCardinality 1..*
	 * @supplierCardinality 1
	 * @label Administration functions
	 * @directed
	 */
	private Vehicle_list lnkVehicle_list;

	/**
	 * Each instance of Administration_office has a navigable association to the
	 * system status so that it can find out status information about the system.
	 * 
	 * @clientCardinality 1..*
	 * @supplierCardinality 1
	 * @label See date
	 * @directed
	 */
	private System_status lnkSystem_status;

	private JPanel contentPane;
	private JButton submitBtn;
	private JComboBox<String> cb_PermitTypeAdd;
	private JTextField tf_NameAdd;
	private JTextField tf_regNumberAdd;
	private JTextField tf_CarMakeAdd;
	private JTextField tf_CarModelAdd;
	private JTextField tf_CarColorAdd;
	private JTextField tf_VisitDateAdd;
	private JTextField tf_HostNameAdd;
	private JTextField tf_RegNumberWarning;
	private JTextField tf_WarningNumber;
	private JTextField tf_PermitNumberCanc;
	private JTextField tf_Status;


	private JTextField tf_PermitNumberMod;
	private JTextField tf_NameMode;
	private JTextField tf_RegNumberMod;
	private JTextField tf_CarMakeMod;
	private JTextField tf_CarModelMod;
	private JTextField tf_CarColorMod;
	private JTextField tf_VisitDateMod;

	private JTextField tf_HostNameMod;
	
	private int date;

	public Administration_office(System_status status, Vehicle_list veh, Permit_list permits) {
		this.lnkSystem_status = status;
		this.lnkVehicle_list = veh;
		this.lnkPermit_list = permits;
		
		final Color BUTTON_BGKD = new Color(112,128,144);
		final Color BUTTON_FGND = new Color(255,255,255);
		
		date = status.getToday().getDayNumber();
		
		setTitle("Administration Office \t [Date: " + date + "]");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(900, 35, 750, 500);
		contentPane = new JPanel();
		setResizable(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(BUTTON_BGKD);
		tabbedPane.setForeground(BUTTON_FGND);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		 // Add: Header
		final int HEADER_HEIGHT = 750;
        final int HEADER_WIDTH = 35;
        final Color HEADER_BACKGROUND = new Color(0,105,56);
        final Font  HEADER_FONT = new Font("Calibri", Font.BOLD, 25);
        JPanel header = new JPanel();
	    header.setBackground(HEADER_BACKGROUND);
	    header.setLayout(new FlowLayout(FlowLayout.LEFT));
	    header.setPreferredSize(new Dimension(HEADER_HEIGHT, HEADER_WIDTH));
	    JLabel lblAdminOffice = new JLabel("PACSUS - Administration Office");
	    lblAdminOffice.setFont(HEADER_FONT);
	    lblAdminOffice.setForeground(Color.WHITE);
	    header.add(lblAdminOffice);
		contentPane.add(header, BorderLayout.PAGE_START);
		
		//Add Permit Page  
		JPanel addPermit = new JPanel();
		tabbedPane.addTab("Add Permit", null, addPermit, null);
		GridBagLayout gbl_addPermit = new GridBagLayout();
		gbl_addPermit.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_addPermit.rowHeights = new int[] { 30, 30, 30, 30, 30, 30, 30, 30, 30, 30 };
		gbl_addPermit.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_addPermit.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		addPermit.setLayout(gbl_addPermit);

		JLabel lblName = new JLabel("Name: ");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.WEST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 1;
		gbc_lblName.gridy = 0;
		addPermit.add(lblName, gbc_lblName);

		tf_NameAdd = new JTextField();
		GridBagConstraints gbc_tfNameAdd = new GridBagConstraints();
		gbc_tfNameAdd.insets = new Insets(0, 0, 5, 0);
		gbc_tfNameAdd.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfNameAdd.gridx = 6;
		gbc_tfNameAdd.gridy = 0;
		addPermit.add(tf_NameAdd, gbc_tfNameAdd);
		tf_NameAdd.setColumns(10);


		JLabel lblRegistrationNumber = new JLabel("Registration Number:");
		GridBagConstraints gbc_lblRegistrationNumber = new GridBagConstraints();
		gbc_lblRegistrationNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblRegistrationNumber.gridx = 1;
		gbc_lblRegistrationNumber.gridy = 1;
		addPermit.add(lblRegistrationNumber, gbc_lblRegistrationNumber);

		tf_regNumberAdd = new JTextField();
		GridBagConstraints gbc_regNumberAdd = new GridBagConstraints();
		gbc_regNumberAdd.insets = new Insets(0, 0, 5, 0);
		gbc_regNumberAdd.fill = GridBagConstraints.HORIZONTAL;
		gbc_regNumberAdd.gridx = 6;
		gbc_regNumberAdd.gridy = 1;
		addPermit.add(tf_regNumberAdd, gbc_regNumberAdd);
		tf_regNumberAdd.setColumns(10);

		
		JLabel lblCarMakeAdd = new JLabel("Car make:");
		GridBagConstraints gbc_lblCarMakeAdd = new GridBagConstraints();
		gbc_lblCarMakeAdd.insets = new Insets(0, 0, 5, 5);
		gbc_lblCarMakeAdd.anchor = GridBagConstraints.WEST;
		gbc_lblCarMakeAdd.gridx = 1;
		gbc_lblCarMakeAdd.gridy = 2;
		addPermit.add(lblCarMakeAdd, gbc_lblCarMakeAdd);
		
		tf_CarMakeAdd = new JTextField();
		GridBagConstraints gbc_CarMakeAdd = new GridBagConstraints();
		gbc_CarMakeAdd.insets = new Insets(0, 0, 5, 0);
		gbc_CarMakeAdd.fill = GridBagConstraints.HORIZONTAL;
		gbc_CarMakeAdd.gridx = 6;
		gbc_CarMakeAdd.gridy = 2;
		addPermit.add(tf_CarMakeAdd, gbc_CarMakeAdd);
		tf_CarMakeAdd.setColumns(10);

		JLabel lblCarModelAdd = new JLabel("Car model:");
		GridBagConstraints gbc_lblCarModelAdd = new GridBagConstraints();
		gbc_lblCarModelAdd.insets = new Insets(0, 0, 5, 5);
		gbc_lblCarModelAdd.anchor = GridBagConstraints.WEST;
		gbc_lblCarModelAdd.gridx = 1;
		gbc_lblCarModelAdd.gridy = 3;
		addPermit.add(lblCarModelAdd, gbc_lblCarModelAdd);
		
		tf_CarModelAdd = new JTextField();
		GridBagConstraints gbc_CarModelAdd = new GridBagConstraints();
		gbc_CarModelAdd.insets = new Insets(0, 0, 5, 0);
		gbc_CarModelAdd.fill = GridBagConstraints.HORIZONTAL;
		gbc_CarModelAdd.gridx = 6;
		gbc_CarModelAdd.gridy = 3;
		addPermit.add(tf_CarModelAdd, gbc_CarModelAdd);
		tf_CarModelAdd.setColumns(10);
		
		JLabel lblCarColorAdd = new JLabel("Car color:");
		GridBagConstraints gbc_lblCarColorAdd = new GridBagConstraints();
		gbc_lblCarColorAdd.insets = new Insets(0, 0, 5, 5);
		gbc_lblCarColorAdd.anchor = GridBagConstraints.WEST;
		gbc_lblCarColorAdd.gridx = 1;
		gbc_lblCarColorAdd.gridy = 4;
		addPermit.add(lblCarColorAdd, gbc_lblCarColorAdd);
		
		tf_CarColorAdd = new JTextField();
		GridBagConstraints gbc_CarColorAdd = new GridBagConstraints();
		gbc_CarColorAdd.insets = new Insets(0, 0, 5, 0);
		gbc_CarColorAdd.fill = GridBagConstraints.HORIZONTAL;
		gbc_CarColorAdd.gridx = 6;
		gbc_CarColorAdd.gridy = 4;

		addPermit.add(tf_CarColorAdd, gbc_CarColorAdd);
		tf_CarColorAdd.setColumns(10);

		JLabel lblEnterPermitTypeAdd = new JLabel("Enter Permit Type:");
		GridBagConstraints gbc_lblEnterPermitTypeAdd= new GridBagConstraints();
		gbc_lblEnterPermitTypeAdd.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnterPermitTypeAdd.anchor = GridBagConstraints.WEST;
		gbc_lblEnterPermitTypeAdd.gridx = 1;
		gbc_lblEnterPermitTypeAdd.gridy = 5;
		addPermit.add(lblEnterPermitTypeAdd, gbc_lblEnterPermitTypeAdd);

		cb_PermitTypeAdd = new JComboBox<String>();
		cb_PermitTypeAdd.addItem("Day Visitor");
		cb_PermitTypeAdd.addItem("Regular Visitor");
		cb_PermitTypeAdd.addItem("Permanent Visitor");
		cb_PermitTypeAdd.addItem("University Member");
		GridBagConstraints gbc_cbPermitTypeAdd = new GridBagConstraints();
		gbc_cbPermitTypeAdd.insets = new Insets(0, 0, 5, 0);
		gbc_cbPermitTypeAdd.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbPermitTypeAdd.gridx = 6;
		gbc_cbPermitTypeAdd.gridy = 5;
		addPermit.add(cb_PermitTypeAdd, gbc_cbPermitTypeAdd);
		
		JLabel lblVisitDateAdd = new JLabel("Visit Date:");
		GridBagConstraints gbc_lblVisitDateAdd = new GridBagConstraints();
		gbc_lblVisitDateAdd.insets = new Insets(0, 0, 5, 5);
		gbc_lblVisitDateAdd.anchor = GridBagConstraints.WEST;
		gbc_lblVisitDateAdd.gridx = 1;
		gbc_lblVisitDateAdd.gridy = 6;
		addPermit.add(lblVisitDateAdd, gbc_lblVisitDateAdd);
		
		tf_VisitDateAdd = new JTextField();
		GridBagConstraints gbc_VisitDateAdd = new GridBagConstraints();
		gbc_VisitDateAdd.gridheight = 2;
		gbc_VisitDateAdd.insets = new Insets(0, 0, 5, 0);
		gbc_VisitDateAdd.fill = GridBagConstraints.BOTH;
		gbc_VisitDateAdd.gridx = 6;
		gbc_VisitDateAdd.gridy = 6;
		addPermit.add(tf_VisitDateAdd, gbc_VisitDateAdd);
		tf_VisitDateAdd.setColumns(10);

		
		JLabel lblHostNameAdd = new JLabel("Host Name:");
		GridBagConstraints gbc_lblHostNameAdd = new GridBagConstraints();
		gbc_lblHostNameAdd.insets = new Insets(0, 0, 5, 5);
		gbc_lblHostNameAdd.anchor = GridBagConstraints.WEST;
		gbc_lblHostNameAdd.gridx = 1;
		gbc_lblHostNameAdd.gridy = 8;
		addPermit.add(lblHostNameAdd, gbc_lblHostNameAdd);
		
		tf_HostNameAdd = new JTextField();
		GridBagConstraints gbc_HostNameAdd  = new GridBagConstraints();
		gbc_HostNameAdd.insets = new Insets(0, 0, 5, 0);
		gbc_HostNameAdd.fill = GridBagConstraints.HORIZONTAL;
		gbc_HostNameAdd.gridx = 6;
		gbc_HostNameAdd.gridy = 8;
		addPermit.add(tf_HostNameAdd, gbc_HostNameAdd);
		tf_HostNameAdd.setColumns(10);

		submitBtn = new JButton("Submit");
		submitBtn.setBackground(BUTTON_BGKD);
		submitBtn.setForeground(BUTTON_FGND);
		submitBtn.setFocusPainted(false);
		submitBtn.addActionListener(this);
		GridBagConstraints gbc_submitBtn = new GridBagConstraints();
		gbc_submitBtn.fill = GridBagConstraints.BOTH;
		gbc_submitBtn.insets = new Insets(0, 0, 0, 5);
		gbc_submitBtn.gridx = 1;
		gbc_submitBtn.gridy = 9;
		addPermit.add(submitBtn, gbc_submitBtn);

		// Record Warning Page

		JPanel recordWarning = new JPanel();
		tabbedPane.addTab("Record Warning", null, recordWarning, null);
		recordWarning.setLayout(null);

		JLabel lblRegistrationNumberRecord = new JLabel("Registration Number:");
		lblRegistrationNumberRecord.setBounds(29, 38, 140, 13);
		recordWarning.add(lblRegistrationNumberRecord);


		tf_RegNumberWarning = new JTextField();
		tf_RegNumberWarning.setBounds(179, 35, 516, 19);
		tf_RegNumberWarning.setColumns(10);
		recordWarning.add(tf_RegNumberWarning);

		JButton btnSubmitWarning = new JButton("Submit");
		btnSubmitWarning.setBounds(331, 90, 81, 21);
		btnSubmitWarning.setBackground(BUTTON_BGKD);
		btnSubmitWarning.setForeground(BUTTON_FGND);
		btnSubmitWarning.setFocusPainted(false);
		btnSubmitWarning.addActionListener(this);
		recordWarning.add(btnSubmitWarning);


		JTextPane tp_RecordWarning = new JTextPane();
		tp_RecordWarning.setBounds(29, 138, 666, 287);
		recordWarning.add(tp_RecordWarning);

		// Delete Warning Page

		JPanel deleteWarning = new JPanel();
		tabbedPane.addTab("Delete Warning", null, deleteWarning, null);

		JLabel lblWarningNumberCancel = new JLabel("Warning Number: ");
		lblWarningNumberCancel.setBounds(32, 38, 106, 13);
		deleteWarning.add(lblWarningNumberCancel);


		tf_WarningNumber = new JTextField();
		tf_WarningNumber.setBounds(179, 35, 516, 19);
		tf_WarningNumber.setColumns(10);
		deleteWarning.add(tf_WarningNumber);

		JButton btnSubmitWarningCanc = new JButton("Submit");
		btnSubmitWarningCanc.setBounds(331, 90, 81, 21);
		btnSubmitWarningCanc.setBackground(BUTTON_BGKD);
		btnSubmitWarningCanc.setForeground(BUTTON_FGND);
		btnSubmitWarningCanc.setFocusPainted(false);
		btnSubmitWarningCanc.addActionListener(this);
		deleteWarning.setLayout(null);
		deleteWarning.add(btnSubmitWarningCanc);


		JTextPane textPaneWarningCanc = new JTextPane();
		textPaneWarningCanc.setBounds(29, 138, 666, 287);
		deleteWarning.add(textPaneWarningCanc);

		// Cancel Permit Page

		JPanel cancelPermit = new JPanel();
		tabbedPane.addTab("Cancel Permit", null, cancelPermit, null);
		cancelPermit.setLayout(null);

		JLabel lblPermitNumberCanc = new JLabel("Permit Number: ");
		lblPermitNumberCanc.setBounds(29, 38, 140, 13);
		cancelPermit.add(lblPermitNumberCanc);


		tf_PermitNumberCanc = new JTextField();
		tf_PermitNumberCanc.setBounds(179, 35, 516, 19);
		cancelPermit.add(tf_PermitNumberCanc);
		tf_PermitNumberCanc.setColumns(10);


		JButton btnSubmitPermitCanc = new JButton("Submit");
		btnSubmitPermitCanc.setBackground(BUTTON_BGKD);
		btnSubmitPermitCanc.setForeground(BUTTON_FGND);
		btnSubmitPermitCanc.setFocusPainted(false);
		btnSubmitPermitCanc.setBounds(331, 90, 81, 21);
		btnSubmitPermitCanc.addActionListener(this);
		cancelPermit.add(btnSubmitPermitCanc);

		JTextPane textPanePermitCanc = new JTextPane();
		textPanePermitCanc.setBounds(29, 138, 666, 287);
		cancelPermit.add(textPanePermitCanc);

		// Status Enquiry Page

		JPanel statusEnquiry = new JPanel();
		tabbedPane.addTab("Status Enquiry", null, statusEnquiry, null);
		statusEnquiry.setLayout(null);

		JLabel lblStatusEnquiry = new JLabel("Permit Number: ");
		lblStatusEnquiry.setBounds(29, 38, 140, 13);
		statusEnquiry.add(lblStatusEnquiry);

		tf_Status = new JTextField();
		tf_Status.setBounds(179, 35, 516, 19);
		statusEnquiry.add(tf_Status);
		tf_Status.setColumns(10);

		JButton btnSubmitEnquiry = new JButton("Submit");
		btnSubmitEnquiry.setBackground(BUTTON_BGKD);
		btnSubmitEnquiry.setForeground(BUTTON_FGND);
		btnSubmitEnquiry.setFocusPainted(false);
		btnSubmitEnquiry.setBounds(331, 90, 81, 21);
		btnSubmitEnquiry.addActionListener(this);
		statusEnquiry.add(btnSubmitEnquiry);


		JTextPane tp_Enquiry = new JTextPane();
		tp_Enquiry.setBounds(29, 138, 666, 287);
		statusEnquiry.add(tp_Enquiry);

		// Modify Permit Page

		JPanel modifyPermit = new JPanel();
		tabbedPane.addTab("Modify Permit", null, modifyPermit, null);
		GridBagLayout gbl_modifyPermit = new GridBagLayout();
		gbl_modifyPermit.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_modifyPermit.rowHeights = new int[] { 30, 0, 30, 30, 30, 30, 30, 30, 30, 30, 30, 31 };
		gbl_modifyPermit.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_modifyPermit.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		modifyPermit.setLayout(gbl_modifyPermit);


		JLabel lblPermitMod = new JLabel("Permit Number:");
		GridBagConstraints gbc_lblPermitMod = new GridBagConstraints();
		gbc_lblPermitMod.anchor = GridBagConstraints.WEST;
		gbc_lblPermitMod.insets = new Insets(0, 0, 5, 5);
		gbc_lblPermitMod.gridx = 1;
		gbc_lblPermitMod.gridy = 0;

		modifyPermit.add(lblPermitMod, gbc_lblPermitMod);


		tf_PermitNumberMod = new JTextField();
		GridBagConstraints gbc_PermitNumberMod = new GridBagConstraints();
		gbc_PermitNumberMod.insets = new Insets(0, 0, 5, 0);
		gbc_PermitNumberMod.fill = GridBagConstraints.HORIZONTAL;
		gbc_PermitNumberMod.gridx = 6;
		gbc_PermitNumberMod.gridy = 0;
		modifyPermit.add(tf_PermitNumberMod, gbc_PermitNumberMod);
		tf_PermitNumberMod.setColumns(10);

		JLabel lblNameMod = new JLabel("Name:");
		GridBagConstraints gbc_lblNameMod = new GridBagConstraints();
		gbc_lblNameMod.anchor = GridBagConstraints.WEST;
		gbc_lblNameMod.insets = new Insets(0, 0, 5, 5);
		gbc_lblNameMod.gridx = 1;
		gbc_lblNameMod.gridy = 1;
		modifyPermit.add(lblNameMod, gbc_lblNameMod);

		tf_NameMode = new JTextField();
		GridBagConstraints gbc_NameMode = new GridBagConstraints();
		gbc_NameMode.insets = new Insets(0, 0, 5, 0);
		gbc_NameMode.fill = GridBagConstraints.HORIZONTAL;
		gbc_NameMode.gridx = 6;
		gbc_NameMode.gridy = 1;
		modifyPermit.add(tf_NameMode, gbc_NameMode);
		tf_NameMode.setColumns(10);

		JLabel lblRegistrationNumberMod = new JLabel("Registration Number:");
		GridBagConstraints gbc_lblRegistrationNumberMod = new GridBagConstraints();
		gbc_lblRegistrationNumberMod.insets = new Insets(0, 0, 5, 5);
		gbc_lblRegistrationNumberMod.gridx = 1;
		gbc_lblRegistrationNumberMod.gridy = 2;
		modifyPermit.add(lblRegistrationNumberMod, gbc_lblRegistrationNumberMod);

		tf_RegNumberMod = new JTextField();
		GridBagConstraints gbc_RegNumberMod = new GridBagConstraints();
		gbc_RegNumberMod.insets = new Insets(0, 0, 5, 0);
		gbc_RegNumberMod.fill = GridBagConstraints.HORIZONTAL;
		gbc_RegNumberMod.gridx = 6;
		gbc_RegNumberMod.gridy = 2;

		modifyPermit.add(tf_RegNumberMod, gbc_RegNumberMod);
		tf_RegNumberMod.setColumns(10);


		JLabel lblCarMakeMod = new JLabel("Car make:");
		GridBagConstraints gbc_lblCarMakeMod = new GridBagConstraints();
		gbc_lblCarMakeMod.insets = new Insets(0, 0, 5, 5);
		gbc_lblCarMakeMod.anchor = GridBagConstraints.WEST;
		gbc_lblCarMakeMod.gridx = 1;
		gbc_lblCarMakeMod.gridy = 3;
		modifyPermit.add(lblCarMakeMod, gbc_lblCarMakeMod);


		tf_CarMakeMod = new JTextField();
		GridBagConstraints gbc_CarMakeMod = new GridBagConstraints();

		gbc_CarMakeMod.insets = new Insets(0, 0, 5, 0);
		gbc_CarMakeMod.fill = GridBagConstraints.HORIZONTAL;
		gbc_CarMakeMod.gridx = 6;
		gbc_CarMakeMod.gridy = 3;
		modifyPermit.add(tf_CarMakeMod, gbc_CarMakeMod);
		tf_CarMakeMod.setColumns(10);

		JLabel lblCarModelMod = new JLabel("Car model:");
		GridBagConstraints gbc_lblCarModelMod = new GridBagConstraints();
		gbc_lblCarModelMod.insets = new Insets(0, 0, 5, 5);
		gbc_lblCarModelMod.anchor = GridBagConstraints.WEST;
		gbc_lblCarModelMod.gridx = 1;
		gbc_lblCarModelMod.gridy = 4;
		modifyPermit.add(lblCarModelMod, gbc_lblCarModelMod);

		tf_CarModelMod = new JTextField();
		GridBagConstraints gbc_CarModelMod = new GridBagConstraints();
		gbc_CarModelMod.insets = new Insets(0, 0, 5, 0);
		gbc_CarModelMod.fill = GridBagConstraints.HORIZONTAL;
		gbc_CarModelMod.gridx = 6;
		gbc_CarModelMod.gridy = 4;
		modifyPermit.add(tf_CarModelMod, gbc_CarModelMod);
		tf_CarModelMod.setColumns(10);

		JLabel lblCarColorMod = new JLabel("Car color:");
		GridBagConstraints gbc_lblCarColorMod = new GridBagConstraints();
		gbc_lblCarColorMod.insets = new Insets(0, 0, 5, 5);
		gbc_lblCarColorMod.anchor = GridBagConstraints.WEST;
		gbc_lblCarColorMod.gridx = 1;
		gbc_lblCarColorMod.gridy = 5;
		modifyPermit.add(lblCarColorMod, gbc_lblCarColorMod);

		tf_CarColorMod = new JTextField();
		GridBagConstraints gbc_CarColorMod = new GridBagConstraints();
		gbc_CarColorMod.insets = new Insets(0, 0, 5, 0);
		gbc_CarColorMod.fill = GridBagConstraints.HORIZONTAL;
		gbc_CarColorMod.gridx = 6;
		gbc_CarColorMod.gridy = 5;
		modifyPermit.add(tf_CarColorMod, gbc_CarColorMod);
		tf_CarColorMod.setColumns(10);

		JLabel lblEnterPermitTypeMod = new JLabel("Enter Permit Type:");
		GridBagConstraints gbc_lblEnterPermitTypeMod = new GridBagConstraints();
		gbc_lblEnterPermitTypeMod.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnterPermitTypeMod.anchor = GridBagConstraints.WEST;
		gbc_lblEnterPermitTypeMod.gridx = 1;
		gbc_lblEnterPermitTypeMod.gridy = 6;
		modifyPermit.add(lblEnterPermitTypeMod, gbc_lblEnterPermitTypeMod);

		JComboBox comboBoxMod = new JComboBox();
		GridBagConstraints gbc_comboBoxMod = new GridBagConstraints();
		gbc_comboBoxMod.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxMod.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxMod.gridx = 6;
		gbc_comboBoxMod.gridy = 6;
		modifyPermit.add(comboBoxMod, gbc_comboBoxMod);

		JLabel lblVisitDateMod = new JLabel("Visit Date:");
		GridBagConstraints gbc_lblVisitDateMod = new GridBagConstraints();
		gbc_lblVisitDateMod.insets = new Insets(0, 0, 5, 5);
		gbc_lblVisitDateMod.anchor = GridBagConstraints.WEST;
		gbc_lblVisitDateMod.gridx = 1;
		gbc_lblVisitDateMod.gridy = 7;
		modifyPermit.add(lblVisitDateMod, gbc_lblVisitDateMod);

		tf_VisitDateMod = new JTextField();
		GridBagConstraints gbc_VisitDateMod = new GridBagConstraints();
		gbc_VisitDateMod.gridheight = 2;
		gbc_VisitDateMod.insets = new Insets(0, 0, 5, 0);
		gbc_VisitDateMod.fill = GridBagConstraints.BOTH;
		gbc_VisitDateMod.gridx = 6;
		gbc_VisitDateMod.gridy = 7;
		modifyPermit.add(tf_VisitDateMod, gbc_VisitDateMod);
		tf_VisitDateMod.setColumns(10);

		JLabel lblHostNameMod = new JLabel("Host Name:");
		GridBagConstraints gbc_lblHostNameMod = new GridBagConstraints();
		gbc_lblHostNameMod.insets = new Insets(0, 0, 5, 5);
		gbc_lblHostNameMod.anchor = GridBagConstraints.WEST;
		gbc_lblHostNameMod.gridx = 1;
		gbc_lblHostNameMod.gridy = 9;
		modifyPermit.add(lblHostNameMod, gbc_lblHostNameMod);


		tf_HostNameMod = new JTextField();
		GridBagConstraints gbc_HostNameMod = new GridBagConstraints();

		gbc_HostNameMod.insets = new Insets(0, 0, 5, 0);
		gbc_HostNameMod.fill = GridBagConstraints.HORIZONTAL;
		gbc_HostNameMod.gridx = 6;
		gbc_HostNameMod.gridy = 9;
		modifyPermit.add(tf_HostNameMod, gbc_HostNameMod);
		tf_HostNameMod.setColumns(10);

		JButton btnGetInfo = new JButton("Get Info");
		GridBagConstraints gbc_btnGetInfo = new GridBagConstraints();
		btnGetInfo.setBackground(BUTTON_BGKD);
		btnGetInfo.setForeground(BUTTON_FGND);
		btnGetInfo.setFocusPainted(false);
		gbc_btnGetInfo.fill = GridBagConstraints.BOTH;
		gbc_btnGetInfo.insets = new Insets(0, 0, 5, 5);
		gbc_btnGetInfo.gridx = 1;
		gbc_btnGetInfo.gridy = 10;
		modifyPermit.add(btnGetInfo, gbc_btnGetInfo);

		JButton submitBtnMod = new JButton("Modify");
		submitBtnMod.setBackground(BUTTON_BGKD);
		submitBtnMod.setForeground(BUTTON_FGND);
		submitBtnMod.setFocusPainted(false);
		submitBtnMod.addActionListener(this);
		GridBagConstraints gbc_submitBtnMod = new GridBagConstraints();
		gbc_submitBtnMod.fill = GridBagConstraints.BOTH;
		gbc_submitBtnMod.insets = new Insets(0, 0, 5, 5);
		gbc_submitBtnMod.gridx = 1;
		gbc_submitBtnMod.gridy = 11;
		modifyPermit.add(submitBtnMod, gbc_submitBtnMod);

    	//
    	lnkSystem_status.addObserver(this);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submitBtn) {
			addNewPermit();
		}
	}

	private void addNewPermit() {
		//
		String name = tf_NameAdd.getText();
		String regNum = tf_regNumberAdd.getText();
		String carMake = tf_CarMakeAdd.getText();
		String carModel = tf_CarModelAdd.getText();
		String carColor = tf_CarColorAdd.getText();
		String permitType = String.valueOf(cb_PermitTypeAdd.getSelectedItem());
		String visitDate = tf_VisitDateAdd.getText();
		String hostName = tf_HostNameAdd.getText();

		//
		if (!name.matches("^[\\p{L} .'-]+$") || name.equals("")) {
			displayAlert("Wrong name entered!", 'w');
		} else if (regNum.equals("")) {
			displayAlert("No registration number entered!", 'w');
		} else if (carMake.equals("")) {
			displayAlert("No car make entered!", 'w');
		} else if (carModel.equals("")) {
			displayAlert("No car model entered!", 'w');
		} else if (!carColor.matches("[A-Za-z ]*") || carColor.equals("")) {
			displayAlert("Wrong car color entered!", 'w');
		} else if (permitType.equals("")) {
			displayAlert("No permit type selected!", 'w');
		} else if (visitDate.equals("")) {
			displayAlert("No visit date entered!", 'w');
		} else if (hostName.equals("")) {
			displayAlert("No host name enetered!", 'w');
		} else {
			// check for existing permit and vehicles
			if (lnkPermit_list.checkPermit(name)) {
				displayAlert("Visitor already has a permit!", 'w');
			} else if (lnkVehicle_list.getVehicleList().contains(regNum)) {
				displayAlert("Vechile is already permitted!", 'w');
			} else {
				// add new permit depending on type
				switch (cb_PermitTypeAdd.getSelectedIndex()) {
				case 0:
					// Day visitor permit

					break;
				case 1:
					// Regular visitor permit

					break;
				case 2:
					// Permanent visitor permit

					break;
				case 3:
					// University member permit

					break;
				}
				// success message
				displayAlert("Permit has been created!", 'i');
			}
		}
	}

	public void displayAlert(String text, char type) {
		//
		switch (type) {
		case 'i':
			JOptionPane.showMessageDialog(contentPane, text, "Success", JOptionPane.PLAIN_MESSAGE);
			break;
		case 'w':
			JOptionPane.showMessageDialog(contentPane, text, "Attention", JOptionPane.WARNING_MESSAGE);
			break;
		case 'e':
			JOptionPane.showMessageDialog(contentPane, text, "Error", JOptionPane.ERROR_MESSAGE);
			break;
		default:
			JOptionPane.showMessageDialog(contentPane, "There was an issue while displaying a message!", "Error",
					JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		//
		Date newDate = lnkSystem_status.getToday();
		if (newDate.getDayNumber() != date) {
			setTitle("Administration Office \t [Date: " + newDate.getDayNumber() + "]");
		}
	}
}
