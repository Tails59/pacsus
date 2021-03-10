import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

/* Generated by Together */

/**
 * The Campus security staff actually activate and deactivate the barriers, enter warnings for
 * vehicles breaching the parking regulations and monitor the state of the barrier system.
 * This class represents the interactive interface to these functions.  Information about these
 * functions is in the Campus security use case diagram (hyperlinked from this class).
 * The interface comprises one screen with the various functions present on it, all on view at
 * once since there aren't many.
 * There could be several instances of this class: one in the central security office, and one
 * at each entrance to the University (in a staffed booth).
 * The class implements Observer, and observes the system status so that it can keep the displayed
 * information up to date (current date, barriers active or not, log of entries through the barriers).
 * @stereotype boundary
 */
@SuppressWarnings({ "deprecation", "serial", "unused"})
public class Campus_security extends JFrame implements Observer, ActionListener {
    /**
     * Each instance of Campus_security has a navigable association to the vehicle list so that
     * warnings can be recorded on the permit for vehicles that breach parking regulations.
     * Note that this process goes via the vehicle list as the only information available about such a
     * vehicle is its registration number.
     * @clientCardinality 1..*
     * @supplierCardinality 1
     * @label Record warning
     * @directed
     */
    private Vehicle_list lnkVehicle_list;

    /**
     * Each instance of Campus_security has a navigable association to the system status so that it
     * can both find out status information about the system, and send controlling messages to the
     * system status (to activate/deactivate the system).
     * @clientCardinality 1..*
     * @supplierCardinality 1
     * @label Control/monitor
     * @directed 
     */
    private System_status lnkSystem_status;
    
    private JPanel header;
    private JPanel barrierControl;
    private JPanel regNoInput;
    private JPanel regNoPanel;
    private JLabel lblCampusSecurity;
    private JLabel lblBarrierStatus;
    private JLabel lblStatus;
    private JButton activateBarrier;
    private JButton deactivateBarrier;
    private JButton issueWarning;
    private JButton checkLog;
    private JTextField regNo;
    private JTextPane regNoPane;
    private JScrollPane regNoScroll;
    private final Color DISABLE_BTN_COLOUR = new Color(211,211,211);
    
    public Campus_security(System_status status, Vehicle_list veh) {
    	
    	// Record references to the system status and vehicle list
    	this.lnkSystem_status = status;
    	this.lnkVehicle_list = veh;
    	
    	// Window properties
    	final String WINDOW_TITLE = "Campus Security";
        final int PADDING = 20;
        final int X_LOCATION = 100;
        final int Y_LOCATION = 100;
        final int WIDTH = 775;
        final int HEIGHT = 435;
        
    	// Configure the window 	
    	setTitle(WINDOW_TITLE);
    	setLocation(X_LOCATION,Y_LOCATION);
    	setSize(WIDTH, HEIGHT);
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	setResizable(false);
        Container window = getContentPane();
        window.setLayout(new FlowLayout(FlowLayout.LEFT));  
        
        // Set up GUI elements   
        // Add: Header
        final int HEADER_HEIGHT = 749;
        final int HEADER_WIDTH = 35;
        final Color HEADER_BACKGROUND = new Color(0,105,56);
        final Font  HEADER_FONT = new Font("Calibri", Font.BOLD, 25);
        header = new JPanel();
	        header.setBackground(HEADER_BACKGROUND);
	        header.setPreferredSize(new Dimension(HEADER_HEIGHT, HEADER_WIDTH));
	        header.setLayout(new FlowLayout(FlowLayout.LEFT));
        lblCampusSecurity = new JLabel("PACSUS - Campus Security");
	        lblCampusSecurity.setFont(HEADER_FONT);
	        lblCampusSecurity.setForeground(Color.WHITE);
			header.add(lblCampusSecurity);
		window.add(header);
    	
		// Add: Barrier controls
        final Color ACTIVATE_BTN_BGKD = new Color(0,179,44);
        final Color DEACTIVATE_BTN_BGKD = new Color(220,61,42);
		barrierControl = new JPanel();
			Border borderBarrierControl = BorderFactory.createTitledBorder("Barrier controls");
			barrierControl.setBorder(borderBarrierControl);	
		activateBarrier = new JButton("Activate Barrier");
			activateBarrier.setBackground(ACTIVATE_BTN_BGKD);
			activateBarrier.setForeground(Color.WHITE);
			activateBarrier.setFocusPainted(false);
			activateBarrier.addActionListener(this);
			barrierControl.add(activateBarrier);
		deactivateBarrier = new JButton("Deactivate Barrier");
			deactivateBarrier.setBackground(DEACTIVATE_BTN_BGKD);
			deactivateBarrier.setForeground(Color.WHITE);
			deactivateBarrier.setFocusPainted(false);
			deactivateBarrier.addActionListener(this);
			barrierControl.add(deactivateBarrier);	
		window.add(barrierControl);
		
		// Add: Registration number input
		final int REG_NO_INPUT_WIDTH = 20;
		final Color REG_NO_BTN_BGKD = new Color(112,128,144);
		regNoInput = new JPanel();
			Border borderRegNoInput = BorderFactory.createTitledBorder("Check registration number");
			regNoInput.setBorder(borderRegNoInput);
			regNo = new JTextField(REG_NO_INPUT_WIDTH);
			regNoInput.add(regNo);
		checkLog = new JButton("Check log");
			checkLog.setBackground(REG_NO_BTN_BGKD);
			checkLog.setForeground(Color.WHITE);
			checkLog.setFocusPainted(false);
			checkLog.addActionListener(this);
			regNoInput.add(checkLog);
		issueWarning = new JButton("Issue warning");
			issueWarning.setBackground(REG_NO_BTN_BGKD);
			issueWarning.setForeground(Color.WHITE);
			issueWarning.setFocusPainted(false);
			issueWarning.addActionListener(this);
			regNoInput.add(issueWarning);
		window.add(regNoInput);
		
		// Add: Registration number text pane
		final int REG_NO_SCROLL_WIDTH = 729;
		final int REG_NO_SCROLL_HEIGHT = 250;
		regNoPanel = new JPanel();
			Border BorderRegNoPanel = BorderFactory.createTitledBorder("Registration number history");
			regNoPanel.setBorder(BorderRegNoPanel);
		regNoPane = new JTextPane();
		regNoScroll = new JScrollPane(regNoPane);
			regNoScroll.setPreferredSize(new Dimension(REG_NO_SCROLL_WIDTH, REG_NO_SCROLL_HEIGHT));
			regNoScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			regNoPanel.add(regNoScroll);
		window.add(regNoPanel);
		
        // Display the frame
        setVisible(true);
        status.addObserver(this);
    	
    } // constructor
    
	public void update(Observable o, Object arg) {
		
		
	} // update


	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == activateBarrier) {

			
		}
		else if (e.getSource() == deactivateBarrier) {
			

		}
		else if (e.getSource() == checkLog) {
			
		}
		else if (e.getSource() == issueWarning) {
			
		}
		
	} // actionPerformed
}
