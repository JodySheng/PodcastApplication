package podcast.model;

import java.util.Date;


public class Podcast {
	protected String podcastId;
	protected String podcastTitle;
	protected String author;
	protected String categories;
	protected String langaugeType;
	// This constructor can be used for reading records from MySQL, where we have all fields,
	// including the podcastId.
	public Podcast(String podcastId, String podcastTitle, String author, String categories, String langaugeType) {
		this.podcastId = podcastId;
		this.podcastTitle = podcastTitle;
		this.author = author;
		this.categories = categories;
		this.langaugeType = langaugeType;
	}
	
	// This constructor can be used for reading records from MySQL, where we only have the podcastId,
	// such as a foreign key reference to podcastId.
	// Given podcastId, we can fetch the full podcast record.
	public Podcast(String podcastId) {
		this.podcastId = podcastId;
	}
	
	public String getPodcastId() {
		return podcastId;
	}

	public void setPodcastId(String podcastId) {
		this.podcastId = podcastId;
	}

	public String getPodcastTitle() {
		return podcastTitle;
	}

	public void setPodcastTitle(String podcastTitle) {
		this.podcastTitle = podcastTitle;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	public String getLangaugeType() {
		return langaugeType;
	}

	public void setLangaugeType(String langaugeType) {
		this.langaugeType = langaugeType;
	}

	
//	// Basically we will not use the following Constructor. Just prepare it if needed.
//	// This constructor can be used for creating new records, where the postId may not be
//	// assigned yet since it is auto-generated by MySQL.
//	public Podcast(String podcastTitle, String author, String categories,
//			String langaugeType) {
//		this.podcastTitle = podcastTitle;
//		this.author = author;
//		this.categories = categories;
//		this.langaugeType = langaugeType;
//	}
}
