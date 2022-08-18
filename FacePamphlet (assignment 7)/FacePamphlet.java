
/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import acmx.export.java.util.Iterator;

import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the interactors in the
	 * application, and taking care of any other initialization that needs to be
	 * performed.
	 */
	public void init() {
		addInteractors();
		addActionListeners();

		data = new FacePamphletDatabase();
		canvas = new FacePamphletCanvas();
		add(canvas);

	}

	private void addInteractors() {
		nameField = new JTextField(TEXT_FIELD_SIZE);
		statusField = new JTextField(TEXT_FIELD_SIZE);
		imageField = new JTextField(TEXT_FIELD_SIZE);
		friendField = new JTextField(TEXT_FIELD_SIZE);
		add = new JButton("Add");
		delete = new JButton("Delete");
		lookup = new JButton("Lookup");
		status = new JButton("Change Status");
		picture = new JButton("Change Picture");
		friend = new JButton("Add Friend");

		add(new JLabel("Name "), NORTH);
		add(nameField, NORTH);
		add(add, NORTH);
		add(delete, NORTH);
		add(lookup, NORTH);

		add(statusField, WEST);
		add(status, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(imageField, WEST);
		add(picture, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(friendField, WEST);
		add(friend, WEST);

		statusField.addActionListener(this);
		imageField.addActionListener(this);
		friendField.addActionListener(this);
	}

	/**
	 * This class is responsible for detecting when the buttons are clicked or
	 * interactors are used, so you will have to add code to respond to these
	 * actions.
	 */
	public void actionPerformed(ActionEvent e) {

		// After clicking "Add" button adds profile or shows message
		if (e.getSource() == add && !nameField.getText().equals("")) {
			name = nameField.getText();
			if (!data.containsProfile(name)) { // profile does not exist
				createProfile();
			} else { // profile already exists
				prof1 = data.getProfile(name);
				canvas.displayProfile(prof1);
				canvas.showMessage("A profile with the name " + prof1.getName() + " already exists");
			}
		}

		// After clicking "Delete" button profile is deleted and removed from all
		// friendlists
		if (e.getSource() == delete && !nameField.getText().equals("")) {
			name = nameField.getText();
			prof1 = data.getProfile(name);
			if (data.containsProfile(name)) { // profile exists
				deleteProf();
			} else { // profile does not exist
				canvas.removeAll();
				canvas.showMessage("A profile with the name " + name + " does not exist");
			}
		}

		// After clicking "Lookup" button profile is displayed on canvas
		if (e.getSource() == lookup && !nameField.getText().equals("")) {
			name = nameField.getText();
			if (data.containsProfile(name)) { // profile exists
				lookUp();
			} else { // profile does not exist
				canvas.removeAll();
				canvas.showMessage("A profile with the name " + name + " does not exist");
			}
		}

		// After clicking "Change Status" button status is set and displayed
		if ((e.getSource() == statusField || e.getSource() == status) && !statusField.getText().equals("")) {
			name = nameField.getText();
			statusText = statusField.getText();
			prof1 = data.getProfile(name);
			statusField.setText(""); // clears textfield
			setSts();
		}

		// After clicking "Change Picture" button image is set
		if ((e.getSource() == imageField || e.getSource() == picture) && !imageField.getText().equals("")) {
			name = nameField.getText();
			prof1 = data.getProfile(name);
			setImg();
		}

		// After clicking "Add Friend" button given user is added to friends of a
		// current profile user
		if ((e.getSource() == friendField || e.getSource() == friend) && !friendField.getText().equals("")) {
			newFriend = friendField.getText();
			friendField.setText(""); // clears textfield
			name = nameField.getText();
			prof1 = data.getProfile(name);
			prof2 = data.getProfile(newFriend);
			addProfile();
		}
	}

	// Deletes profile
	private void deleteProf() {
		removeFromFriends();
		data.deleteProfile(name);
		canvas.removeAll();
		canvas.showMessage("Profile of " + name + " deleted");
	}

	// Looks up profile
	private void lookUp() {
		prof1 = data.getProfile(name);
		canvas.displayProfile(prof1);
		canvas.showMessage("Displaying " + name);
	}

	// Sets status and displays it on canvas
	private void setSts() {
		if (prof1 != null) {
			prof1.setStatus(statusText);
			data.addProfile(prof1);
			canvas.displayProfile(prof1);
			canvas.showMessage("Status updated to " + statusText);
		} else {
			canvas.removeAll();
			canvas.showMessage("Please select profile to change status");
		}
	}

	// Sets image and displays it on canvas
	private void setImg() {
		if (data.containsProfile(name)) {
			GImage image = null;
			try {
				filename = imageField.getText();
				imageField.setText(""); // clears textfield
				image = new GImage("./images/" + filename + ".jpg");
				prof1.setImage(image);
			} catch (ErrorException ex) {
				// Code that is executed in filename cannot be opened
			}

			if (image == null) { // image exists
				canvas.removeAll();
				canvas.displayProfile(prof1);
				canvas.showMessage("Unable to open image file: " + filename);
			} else { // image link is not valid
				data.addProfile(prof1);
				canvas.displayProfile(prof1);
				canvas.showMessage("Picture updated");
			}

		} else { // profile is not selected to set an image
			canvas.removeAll();
			canvas.showMessage("Please select a profile to change picture");
		}
	}

	// This method removes user from list of friends of his/her friends
	private void removeFromFriends() {
		java.util.Iterator<String> it = prof1.getFriends();
		while (it.hasNext()) {
			String x = it.next();
			prof1 = data.getProfile(x);
			prof1.removeFriend(name);
			data.addProfile(prof1);
		}
	}

	private void createProfile() {
		prof1 = new FacePamphletProfile(name);
		data.addProfile(prof1);
		canvas.displayProfile(prof1);
		canvas.showMessage("New profile created");
	}

	private void addProfile() {
		if (data.containsProfile(name)) {
			if (data.containsProfile(newFriend)) { // checks if profile of this name exists
				if (!prof1.equals(prof2)) { // users cannot add themselves in their friendlists
					if (prof1.addFriend(newFriend)) { // checks if given user is already in friendlist
						prof1.addFriend(newFriend);
						prof2.addFriend(name);
						data.addProfile(prof1);
						canvas.displayProfile(prof1);
						canvas.showMessage(newFriend + " added as friend");
					} else { // profile by given name is already in friendlist of a current profile
						canvas.removeAll();
						canvas.displayProfile(prof1);
						canvas.showMessage(name + " already has " + newFriend + " as a friend");
					}
				}
			} else { // profile by given name does not exist
				canvas.removeAll();
				canvas.displayProfile(prof1);
				canvas.showMessage(newFriend + " does not exist");
			}
		} else { // profile is not selected to add a friend
			canvas.removeAll();
			canvas.showMessage("Please select a profile to add friend");
		}
	}

	// Private instance variables
	private JTextField nameField;
	private JTextField statusField;
	private JTextField imageField;
	private JTextField friendField;

	private JButton add;
	private JButton delete;
	private JButton lookup;
	private JButton status;
	private JButton picture;
	private JButton friend;

	private String name;
	private String statusText;
	private String filename;
	private String newFriend;

	private FacePamphletDatabase data;
	private FacePamphletProfile prof1;
	private FacePamphletProfile prof2;
	private FacePamphletCanvas canvas;
}
