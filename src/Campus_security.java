import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

/* Generated by Together */

/**
 * The Campus security staff actually activate and deactivate the barriers,
 * enter warnings for vehicles breaching the parking regulations and monitor the
 * state of the barrier system. This class represents the interactive interface
 * to these functions. Information about these functions is in the Campus
 * security use case diagram (hyperlinked from this class). The interface
 * comprises one screen with the various functions present on it, all on view at
 * once since there aren't many. There could be several instances of this class:
 * one in the central security office, and one at each entrance to the
 * University (in a staffed booth). The class implements Observer, and observes
 * the system status so that it can keep the displayed information up to date
 * (current date, barriers active or not, log of entries through the barriers).
 * 
 * @stereotype boundary
 */
@SuppressWarnings({ "deprecation", "serial", "unused" })
public class Campus_security extends JFrame implements Observer, ActionListener {
	/**
	 * Each instance of Campus_security has a navigable association to the vehicle
	 * list so that warnings can be recorded on the permit for vehicles that breach
	 * parking regulations. Note that this process goes via the vehicle list as the
	 * only information available about such a vehicle is its registration number.
	 * 
	 * @clientCardinality 1..*
	 * @supplierCardinality 1
	 * @label Record warning
	 * @directed
	 */
	private Vehicle_list lnkVehicle_list;

	/**
	 * Each instance of Campus_security has a navigable association to the system
	 * status so that it can both find out status information about the system, and
	 * send controlling messages to the system status (to activate/deactivate the
	 * system).
	 * 
	 * @clientCardinality 1..*
	 * @supplierCardinality 1
	 * @label Control/monitor
	 * @directed
	 */
	private System_status lnkSystem_status;

	/**
	 * Stores the date set by the timer.
	 */
	private int date;

	private JPanel contentPane;
	private JPanel header;
	private JPanel barrierControl;
	private JPanel regNoInput;
	private JPanel regNoPanel;
	private JLabel lblCampusSecurity;
	private JLabel lblBarrierStatus;
	private JLabel lblStatus;
	private JButton btn_activateBarrier;
	private JButton btn_deactivateBarrier;
	private JButton btn_issueWarning;
	private JButton btn_checkLog;
	private JTextField tf_regNo;
	private JTextPane tp_regNoPane;
	private JScrollPane sp_regNoScroll;
	private final Color DISABLE_BTN_COLOUR = new Color(211, 211, 211);
	private final Color ACTIVATE_BTN_BGKD = new Color(0, 179, 44);
	private final Color DEACTIVATE_BTN_BGKD = new Color(220, 61, 42);
	private final String WINDOW_TITLE;

	public Campus_security(System_status status, Vehicle_list veh) {

		// Record references to the system status and vehicle list
		this.lnkSystem_status = status;
		this.lnkVehicle_list = veh;

		// Window properties
		WINDOW_TITLE = "Campus Security";
		date = status.getToday().getDayNumber();
		final int PADDING = 20;
		final int X_LOCATION = 100;
		final int Y_LOCATION = 100;
		final int WIDTH = 775;
		final int HEIGHT = 435;

		// Configure the window
		setTitle(WINDOW_TITLE + "  [Date: " + date + "]");
		setLocation(X_LOCATION, Y_LOCATION);
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		Container window = getContentPane();
		window.setLayout(new FlowLayout(FlowLayout.LEFT));

		// Set up GUI elements
		// Add: Header
		final int HEADER_HEIGHT = 749;
		final int HEADER_WIDTH = 35;
		final Color HEADER_BACKGROUND = new Color(0, 105, 56);
		final Font HEADER_FONT = new Font("Calibri", Font.BOLD, 25);
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
		barrierControl = new JPanel();
		Border borderBarrierControl = BorderFactory.createTitledBorder("Barrier controls");
		barrierControl.setBorder(borderBarrierControl);

		btn_activateBarrier = new JButton("Activate Barrier");
		btn_activateBarrier.setBackground(ACTIVATE_BTN_BGKD);
		btn_activateBarrier.setForeground(Color.WHITE);
		btn_activateBarrier.setFocusPainted(false);
		btn_activateBarrier.addActionListener(this);
		barrierControl.add(btn_activateBarrier);

		btn_deactivateBarrier = new JButton("Deactivate Barrier");
		btn_deactivateBarrier.setBackground(DEACTIVATE_BTN_BGKD);
		btn_deactivateBarrier.setForeground(Color.WHITE);
		btn_deactivateBarrier.setFocusPainted(false);
		btn_deactivateBarrier.addActionListener(this);
		barrierControl.add(btn_deactivateBarrier);
		window.add(barrierControl);

		// Add: Registration number input
		final int REG_NO_INPUT_WIDTH = 20;
		final Color REG_NO_BTN_BGKD = new Color(112, 128, 144);

		regNoInput = new JPanel();
		Border borderRegNoInput = BorderFactory.createTitledBorder("Check registration number");
		regNoInput.setBorder(borderRegNoInput);
		tf_regNo = new JTextField(REG_NO_INPUT_WIDTH);
		regNoInput.add(tf_regNo);

		btn_checkLog = new JButton("Check log");
		btn_checkLog.setBackground(REG_NO_BTN_BGKD);
		btn_checkLog.setForeground(Color.WHITE);
		btn_checkLog.setFocusPainted(false);
		btn_checkLog.addActionListener(this);

		regNoInput.add(btn_checkLog);
		btn_issueWarning = new JButton("Issue warning");
		btn_issueWarning.setBackground(REG_NO_BTN_BGKD);
		btn_issueWarning.setForeground(Color.WHITE);
		btn_issueWarning.setFocusPainted(false);
		btn_issueWarning.addActionListener(this);
		regNoInput.add(btn_issueWarning);
		window.add(regNoInput);

		// Add: Registration number text pane
		final int REG_NO_SCROLL_WIDTH = 729;
		final int REG_NO_SCROLL_HEIGHT = 250;
		regNoPanel = new JPanel();
		Border BorderRegNoPanel = BorderFactory.createTitledBorder("System log");
		regNoPanel.setBorder(BorderRegNoPanel);
		tp_regNoPane = new JTextPane();
		tp_regNoPane.setText("[INFO] Barrier system is inactive");
		tp_regNoPane.setEditable(false);
		sp_regNoScroll = new JScrollPane(tp_regNoPane);
		sp_regNoScroll.setPreferredSize(new Dimension(REG_NO_SCROLL_WIDTH, REG_NO_SCROLL_HEIGHT));
		sp_regNoScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		regNoPanel.add(sp_regNoScroll);
		window.add(regNoPanel);

		// Display the frame
		setVisible(true);

		lnkSystem_status.addObserver(this);
	} // constructor

	/**
	 * Update the GUI
	 */
	@Override
	public void update(Observable o, Object arg) {
		boolean sysStatus = lnkSystem_status.getStatus();
		setTitle(WINDOW_TITLE + "  [Date: " + lnkSystem_status.getToday().getDayNumber() + "]");
		date = lnkSystem_status.getToday().getDayNumber();
		tp_regNoPane.setText("");

		if (sysStatus) {

			displayLog();
			btn_activateBarrier.setEnabled(false);
			btn_activateBarrier.setBackground(DISABLE_BTN_COLOUR);
			btn_deactivateBarrier.setEnabled(true);
			btn_deactivateBarrier.setBackground(DEACTIVATE_BTN_BGKD);
		} else {
			tp_regNoPane.setText(tp_regNoPane.getText() + "[Date: " + date + "]" + " Barrier system deactivated");
			btn_activateBarrier.setEnabled(true);
			btn_activateBarrier.setBackground(ACTIVATE_BTN_BGKD);
			btn_deactivateBarrier.setEnabled(false);
			btn_deactivateBarrier.setBackground(DISABLE_BTN_COLOUR);
		}
	} // update

	/**
	 * Display the log of last 20 entries
	 */
	private void displayLog() {
		//
		String[] log = lnkSystem_status.getLog();
		StringBuilder text = new StringBuilder();
		text.append(tp_regNoPane.getText() + "[Date: " + date + "]" + " Barrier system activated");
		//
		if (log[0] != null) {
			text.append("\nEntries:\n");
			//
			for (int i = 0; i < log.length; i++) {
				if (log[i] != null) {
					text.append("\n").append(log[i]);
				}
			}
		}
		tp_regNoPane.setText(text.toString());
	}

	/**
	 * Update gui if button are clicked
	 */
	public void actionPerformed(ActionEvent e) {
		final int REG_NO_LENGTH = 8;
		if (e.getSource() == btn_activateBarrier) // Activate the barrier system if the activate button is clicked.
		{
			lnkSystem_status.setStatus(true);
		} else if (e.getSource() == btn_deactivateBarrier) // Deactivate the barrier system if the deactivate button is
															// clicked.
		{
			lnkSystem_status.setStatus(false);
		} else if (e.getSource() == btn_checkLog) // Check the log for the registration number entered by the user.
		{
			if (tf_regNo.getText().equals("")) // Check for input in the text field and warn the user if nothing found.
			{
				displayAlert("Please enter a registration number.", 'w');
			} else if (!tf_regNo.getText().matches("^[A-Z0-9 _]*[A-Z0-9][A-Z0-9 _]*$")
					|| tf_regNo.getText().length() > REG_NO_LENGTH) // Validate the input and warn the user if invalid.
			{
				displayAlert("Please enter a valid registration number.", 'w');
				tf_regNo.setText("");
			} else {
				Permit toCheck = lnkVehicle_list.getAPermit(tf_regNo.getText()); // Get the permit using the
																					// registration number entered by
																					// the user.
				if (toCheck != null) // If a permit is found, fetch the details.
				{
					tp_regNoPane.setText(tp_regNoPane.getText() + "\n----------------------" + "\nLOG FOR "
							+ tf_regNo.getText() + "\n----------------------" + "\nPermit holder: "
							+ toCheck.getPermitHolder() + "\nEntered today: " + toCheck.entered() + "\nNo. of entries: "
							+ toCheck.getEntries() + "\nNo. of warnings: " + toCheck.getWarnings());
				} else {
					displayAlert("Could not find vehicle " + tf_regNo.getText(), 'e');
				}
				tf_regNo.setText("");
			}
		} else if (e.getSource() == btn_issueWarning) // Issue a warning to the specified vehicle using the registration
														// number.
		{
			if (tf_regNo.getText().equals("")) {
				displayAlert("Please enter a registration number.", 'w');
			} else if (!tf_regNo.getText().matches("^[A-Z0-9 _]*[A-Z0-9][A-Z0-9 _]*$")
					|| tf_regNo.getText().length() > REG_NO_LENGTH) {
				displayAlert("Please enter a valid registration number.", 'w');
				tf_regNo.setText("");
			} else {
				boolean warningIssued = lnkVehicle_list.issueWarning(tf_regNo.getText()); // Issue a warning through the
																							// vehicle list with the
																							// registration number.
				if (warningIssued == true) // If the warning was successfully issued, advise the user. If not, warn the
											// user the vehicle could not be found or the maximum number of warnings has
											// been reached.
				{
					displayAlert("Warning issued for vehicle " + tf_regNo.getText(), 'i');
				} else {
					displayAlert("Unable to issue warning to " + tf_regNo.getText()
							+ "\nThe maximum number of warnings has been reached or the vehicle could not be found.",
							'e');
				}
				tf_regNo.setText("");
			}
		}
	} // actionPerformed

	/**
	 * Display different type of alert if mistyped or null value are insterted
	 * 
	 * @param text the error
	 * @param type the type of error
	 */
	public void displayAlert(String text, char type) {
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
	} // displayAlert
}
