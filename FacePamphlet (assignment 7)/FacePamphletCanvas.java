
/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */

import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas implements FacePamphletConstants {

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * display
	 */
	public FacePamphletCanvas() {
	}

	/**
	 * This method displays a message string near the bottom of the canvas. Every
	 * time this method is called, the previously displayed message (if any) is
	 * replaced by the new message text passed in.
	 */
	public void showMessage(String msg) {
		message = new GLabel(msg);
		message.setFont(MESSAGE_FONT);
		add(message, (getWidth() - message.getWidth()) / 2, getHeight() - BOTTOM_MESSAGE_MARGIN);
	}

	/**
	 * This method displays the given profile on the canvas. The canvas is first
	 * cleared of all existing items (including messages displayed near the bottom
	 * of the screen) and then the given profile is displayed. The profile display
	 * includes the name of the user from the profile, the corresponding image (or
	 * an indication that an image does not exist), the status of the user, and a
	 * list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		userName(profile);
		userImage(profile);
		userStatus(profile);
		userFriends(profile);

	}

	// This method displays name of the user
	private void userName(FacePamphletProfile profile) {
		name = new GLabel(profile.getName());
		name.setColor(Color.BLUE);
		name.setFont(PROFILE_NAME_FONT);
		add(name, LEFT_MARGIN, TOP_MARGIN + name.getAscent());

	}

	// This method shows image (or an indication that an image does not exist)
	private void userImage(FacePamphletProfile profile) {
		if (profile.getImage() == null) { // no image set
			noImage = new GRect(IMAGE_WIDTH, IMAGE_HEIGHT);
			add(noImage, LEFT_MARGIN, TOP_MARGIN + name.getAscent() + IMAGE_MARGIN);

			noImageLbl = new GLabel("No Image");
			noImageLbl.setFont(PROFILE_IMAGE_FONT);

			double x = LEFT_MARGIN + (IMAGE_WIDTH - noImageLbl.getWidth()) / 2;
			double y = TOP_MARGIN + name.getAscent() + IMAGE_MARGIN + (IMAGE_HEIGHT + noImageLbl.getAscent()) / 2;
			add(noImageLbl, x, y);
		} else { // image is updated
			image = profile.getImage();
			image.scale(IMAGE_WIDTH / image.getWidth(), IMAGE_HEIGHT / image.getHeight());
			add(image, LEFT_MARGIN, TOP_MARGIN + name.getAscent() + IMAGE_MARGIN);
		}
	}

	// This method displays status of the user
	private void userStatus(FacePamphletProfile profile) {
		if (profile.getStatus() != null) { // status is updated
			status = new GLabel(profile.getName() + " is " + profile.getStatus());
		} else { // no status set
			status = new GLabel("No current status");
		}
		status.setFont(PROFILE_STATUS_FONT);
		double x = LEFT_MARGIN;
		double y = TOP_MARGIN + name.getAscent() + IMAGE_MARGIN + IMAGE_HEIGHT + status.getAscent() + STATUS_MARGIN;
		add(status, x, y);
	}

	// This method displays list of the user's friends in the social network
	private void userFriends(FacePamphletProfile profile) {
		friendsLabel = new GLabel("Friends:");
		friendsLabel.setFont(PROFILE_FRIEND_LABEL_FONT);
		double x = getWidth() / 2;
		double y = TOP_MARGIN + name.getAscent() + IMAGE_MARGIN;
		add(friendsLabel, x, y);

		Iterator<String> it = profile.getFriends();
		while (it.hasNext()) {
			friends = new GLabel(it.next());
			friends.setFont(PROFILE_FRIEND_FONT);
			y += friends.getHeight();
			add(friends, x, y);
		}
	}

	private GLabel name;
	private GLabel status;
	private GImage image;
	private GLabel friendsLabel;
	private GLabel friends;
	private GRect noImage;
	private GLabel noImageLbl;
	private GLabel message;

}
