package mk.ukim.finki.rmandroid.model;

import java.io.Serializable;

public class Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int ID;
	private String backgroundImage;
	private String content;
	private String description;
	private String groupKey;
	private String subtitle;
	private String title;

	public Item() {
		super();
	}

	public Item(int id, String backgroundImage, String content,
			String description, String groupID, String subtitle, String title) {
		super();
		this.ID = id;
		this.backgroundImage = backgroundImage;
		this.content = content;
		this.description = description;
		this.groupKey = groupID;
		this.subtitle = subtitle;
		this.title = title;
	}

	public String getBackgroundImage() {
		return backgroundImage;
	}

	public String getContent() {
		return content;
	}

	public String getDescription() {
		return description;
	}

	public String getGroupKey() {
		return groupKey;
	}

	public int getID() {
		return ID;
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

	public void setContent(String content) {
		this.content = content;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setGroupKey(String groupKey) {
		this.groupKey = groupKey;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
