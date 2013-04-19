package mk.ukim.finki.rmandroid.model;

import java.io.Serializable;

public class Group implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ID;
	private String backgroundImage;
	private String description;
	private String key;
	private String subtitle;
	private String title;

	public Group() {
		super();
	}

	public Group(int id, String backgroundImage, String description,
			String key, String subtitle, String title) {
		super();
		this.ID = id;
		this.backgroundImage = backgroundImage;
		this.description = description;
		this.key = key;
		this.subtitle = subtitle;
		this.title = title;
	}

	public String getBackgroundImage() {
		return backgroundImage;
	}

	public String getDescription() {
		return description;
	}

	public int getID() {
		return ID;
	}

	public String getKey() {
		return key;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public String getTitle() {
		return title;
	}

	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
