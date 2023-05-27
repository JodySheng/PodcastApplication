package podcast.model;

import java.util.Date;

public class Episodes {
	
	protected String episodeId;
	protected String episodesTitle;
	protected Date publishDate;
	protected String descriptions;
	protected int audioLength;
	protected String url;
	protected Podcast podcast;
	

	public Episodes(String episodeId, String episodesTitle, Date publishDate, String descriptions, int audioLength, String url, Podcast podcast) {
		this.episodeId = episodeId;
		this.episodesTitle = episodesTitle;
		this.publishDate = publishDate;
		this.descriptions = descriptions;
		this.audioLength = audioLength;
		this.url = url;
		this.podcast = podcast;
	}

  	public Episodes(String episodesTitle) {
		this.episodesTitle = episodesTitle;}


  	public String getEpisodeId() {
		return episodeId;}


	public void setEpisodeId(String episodeId) {
		this.episodeId = episodeId;
	}


	public String getEpisodesTitle() {
		return episodesTitle;
	}


	public void setEpisodesTitle(String episodesTitle) {
		this.episodesTitle = episodesTitle;
	}


	public Date getPublishDate() {
		return publishDate;
	}


	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}


	public String getDescriptions() {
		return descriptions;
	}


	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}


	public int getAudioLength() {
		return audioLength;
	}


	public void setAudioLength(int audioLength) {
		this.audioLength = audioLength;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public Podcast getPodcast() {
		return podcast;
	}


	public void setPodcast(Podcast podcast) {
		this.podcast = podcast;
	}


}
