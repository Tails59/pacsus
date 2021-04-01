import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

/* Generated by Together */

/**
 * This class represents the access control barriers. Information about the
 * barrier functions is in the Barrier use case diagram (hyperlinked from this
 * class). Although in reality this class will be realised by (programmable)
 * hardware with a moveable barrier, and a camera with a registration number
 * recognition system, here the interface comprises one screen with the various
 * functions present on it, all on view at once since there aren't many.
 *
 * There will be a large word PASS or STOP on display at all times: PASS when
 * the barrier is up because the system is inactive or temporarily when a
 * permitted vehicle is being allowed through, and STOP when the barrier is
 * down.
 *
 * There will be a text field to enter the registration number read by the
 * camera, and a button to indicate that the number has been read and is ready
 * for checking (and raising the barrier or not, as appropriate).
 *
 * To simulate the passage of a vehicle through the raised barrier, there is
 * another button to be clicked to simulate when a buried electronic sensor
 * indicates that the vehicle is now clear - the barrier can then be lowered
 * (unless, of course, the system has been deactivated by security staff in the
 * interim...).
 *
 * There could be many instances of this class: one at each entrance lane to the
 * University. The class implements Observer, and observes the system status so
 * that it can keep its activated/deactivated status up to date.
 * 
 * @stereotype boundary
 */
@SuppressWarnings({ "deprecation", "serial", "unused" })
public class Barrier extends JFrame implements Observer, ActionListener {
	/**
	 * Each instance of Barrier has a navigable association to the permit list so
	 * that when a vehicle's registration number has been recognized by the camera,
	 * the barrier can check whether to raise itself or not by checking the status
	 * of that vehicle's permit.
	 * 
	 * @clientCardinality 1..*
	 * @supplierCardinality 1
	 * @label Access permits
	 * @directed
	 */
	private Vehicle_list lnkVehicle_list;

	/**
	 * Each instance of Barrier has a navigable association to the system status so
	 * that it can check whether the barrier system as a whole is active or
	 * inactive, and so that it can send event messages to be recorded in the log.
	 * 
	 * @clientCardinality 1..*
	 * @supplierCardinality 1
	 * @label Fetch system status info
	 * @directed
	 */
	private System_status lnkSystem_status;

	/**
	 * This attribute indicates the active/inactive state of the barrier system - as
	 * notified by the system status when it changes (Barrier Observes System
	 * status). If false then the barrier must be up. If true then the barrier
	 * position is determined by attribute raised.
	 */
	private boolean active = false;

	/**
	 * If the barrier system is active, this attribute indicates whether the barrier
	 * is currently in its raised or lowered position. The position is controlled by
	 * the result of checking a registration number with the permitted vehicles
	 * list, and the "vehicle clear" button.
	 */
	private boolean raised = true;
	private boolean recorded = false;

	/**
	 * Stores the date set by the timer.
	 */
	private int date;

	private JPanel contentPane;
	private JPanel header;
	private JPanel regNoInput;
	private JPanel barrierControls;
	private JPanel barrierStatus;
	private JLabel lblBarrierHeader;
	private JLabel lblBarrierPosition;
	private JLabel lblInstruction;
	JTextField regNo;
	JButton submit;
	JButton vehicleClear;
	private final Color GO_COLOUR = new Color(0, 179, 44);
	private final Color STOP_COLOUR = new Color(220, 61, 42);
	private final Color DISABLE_BTN_COLOUR = new Color(211, 211, 211);
	private final Color BUTTON_BGKD = new Color(112, 128, 144);
	private String windowTitle;

	public Barrier(System_status status, Vehicle_list veh, String wt, int xLocation, int yLocation) {

		// Record references to the system status and vehicle list
		this.lnkSystem_status = status;
		this.lnkVehicle_list = veh;

		// Window properties
		final int PADDING = 20;
		final int WIDTH = 400;
		final int HEIGHT = 290;
		date = status.getToday().getDayNumber();
		windowTitle = wt;

		// Configure the window
		setTitle(windowTitle + "  [Date: " + date + "]");
		setLocation(xLocation, yLocation);
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		Container window = getContentPane();
		window.setLayout(new FlowLayout(FlowLayout.LEFT));

		// Add: Header
		final int HEADER_HEIGHT = 374;
		final int HEADER_WIDTH = 35;
		final Color HEADER_BACKGROUND = new Color(0, 105, 56);
		final Font HEADER_FONT = new Font("Calibri", Font.BOLD, 25);
		header = new JPanel();
		header.setBackground(HEADER_BACKGROUND);
		header.setPreferredSize(new Dimension(HEADER_HEIGHT, HEADER_WIDTH));
		header.setLayout(new FlowLayout(FlowLayout.LEFT));
		lblBarrierHeader = new JLabel("PACSUS - " + windowTitle);
		lblBarrierHeader.setFont(HEADER_FONT);
		lblBarrierHeader.setForeground(Color.WHITE);
		header.add(lblBarrierHeader);
		window.add(header);

		// Add: Registration number input
		final int REG_NO_INPUT_WIDTH = 32;
		regNoInput = new JPanel();
		Border borderRegNoInput = BorderFactory.createTitledBorder("Enter registration number");
		regNoInput.setBorder(borderRegNoInput);
		regNo = new JTextField(REG_NO_INPUT_WIDTH);
		regNoInput.add(regNo);
		window.add(regNoInput);

		// Add: Barrier controls
		barrierControls = new JPanel();
		submit = new JButton("Submit");
		submit.setBackground(BUTTON_BGKD);
		submit.setForeground(Color.WHITE);
		submit.setFocusPainted(false);
		submit.addActionListener(this);
		barrierControls.add(submit);
		vehicleClear = new JButton("Vehicle Clear");
		vehicleClear.setBackground(BUTTON_BGKD);
		vehicleClear.setForeground(Color.WHITE);
		vehicleClear.setFocusPainted(false);
		vehicleClear.addActionListener(this);
		barrierControls.add(vehicleClear);
		window.add(barrierControls);

		// Add: Barrier status
		final int BARRIER_STATUS_WIDTH = 374;
		final int BARRIER_STATUS_HEIGHT = 100;
		final Color BARRIER_STATUS_BGKD = new Color(211, 211, 211);
		final Font INSTRUCTION_FONT = new Font("", Font.PLAIN, 45);
		barrierStatus = new JPanel();
		barrierStatus.setBackground(BARRIER_STATUS_BGKD);
		barrierStatus.setPreferredSize(new Dimension(BARRIER_STATUS_WIDTH, BARRIER_STATUS_HEIGHT));
		barrierStatus.setLayout(new GridBagLayout());
		lblBarrierPosition = new JLabel("Barrier status undefined");
		barrierStatus.add(lblBarrierPosition);
		lblInstruction = new JLabel("");
		lblInstruction.setFont(INSTRUCTION_FONT);
		barrierStatus.add(lblInstruction);
		window.add(barrierStatus);

		setVisible(true);
		lnkSystem_status.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		active = lnkSystem_status.getStatus();
		Date newDate = lnkSystem_status.getToday();

		if (newDate.getDayNumber() != date) {
			setTitle(windowTitle + "  [Date: " + lnkSystem_status.getToday().getDayNumber() + "]");
		}

		if (active == false) {
			raised = true;
			submit.setEnabled(false);
			submit.setBackground(DISABLE_BTN_COLOUR);
			vehicleClear.setEnabled(false);
			vehicleClear.setBackground(DISABLE_BTN_COLOUR);
			regNo.setText("");
			regNo.setEnabled(false);
			lblBarrierPosition.setText("System inactive");
			lblInstruction.setText(" GO");
			barrierStatus.setBackground(GO_COLOUR);
		} else if (active == true && !recorded) {
			raised = false;
			submit.setEnabled(true);
			submit.setBackground(BUTTON_BGKD);
			vehicleClear.setEnabled(true);
			vehicleClear.setBackground(BUTTON_BGKD);
			regNo.setEnabled(true);
			lblBarrierPosition.setText("The barrier is lowered");
			lblInstruction.setText(" STOP");
			barrierStatus.setBackground(STOP_COLOUR);
		}
		//
		recorded = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final int REG_NO_LENGTH = 8;
		if (e.getSource() == submit && active == true) // Check if the event source is the submit button and if the
														// system is active.
		{
			if (regNo.getText().equals("")) // Check for user input.
			{
				displayAlert("Please enter a registration number.", 'w');
			} else if (!regNo.getText().matches("^[A-Z0-9 _]*[A-Z0-9][A-Z0-9 _]*$")
					|| regNo.getText().length() > REG_NO_LENGTH) // Validate input and warn user if invalid.
			{
				displayAlert("Please enter a valid registration number.", 'w');
				regNo.setText("");
			} else {
				Permit toCheck = lnkVehicle_list.getAPermit(regNo.getText()); // Get the permit using the registration
																				// number entered by the user.
				if (toCheck != null) // If the permit has been successfully found, then check if the vehicle can pass
										// the barrier.
				{
					if (lnkVehicle_list.canPass(toCheck)) {
						//
						recordEntry(toCheck, true);
						// Raise the barrier
						raised = true;
						regNo.setText("");
						lblBarrierPosition.setText("The barrier is raised");
						lblInstruction.setText(" GO");
						barrierStatus.setBackground(GO_COLOUR);
					} else {
						//
						recordEntry(toCheck, false);
						// Warn the user that access is denied for the vehicle.
						raised = false;
						regNo.setText("");
						displayAlert("Access is denied for this vehicle.", 'w');
					}
				} else {
					displayAlert("No permit found for this vehicle.", 'w'); // Alert the user that no permit was found
																			// with the registration number entered.
					regNo.setText("");
				}
			}
		} else if (e.getSource() == vehicleClear && active == true) // Check if the event source is vehicle clear and if
																	// the system is active.
		{
			if (raised == true) // Check if the barrier is raised and lower if true.
			{
				raised = false;
				lblBarrierPosition.setText("The barrier is lowered");
				lblInstruction.setText(" STOP");
				barrierStatus.setBackground(STOP_COLOUR);
			} else // Warn the user that the barrier is already lowered.
			{
				displayAlert("The barrier is already lowered.", 'w');
			}
		} else {
			displayAlert("The system is inactive. Please activate the system to use this function.", 'w');
		}
	}

	public void recordEntry(Permit p, boolean state) {
		recorded = true; // might need to implement if check with state so it is only true when state is
							// true
		Vehicle_info[] vehicles = lnkVehicle_list.getVehicles(p);
		for (int i = 0; i < vehicles.length; i++) {
			if (vehicles[i].getRegistration().equals(regNo.getText())) {
				lnkSystem_status.addEntry(vehicles[i], state);
			}
		}
		lnkSystem_status.setStatus(lnkSystem_status.getStatus());
	}

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

	public boolean isRaised() {
		return this.raised;
	}
}
