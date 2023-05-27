package podcast.model;

public class EpisodesReviews {
	protected  int episodeReviewId;
	protected Users user;
	protected Episodes episode;
	protected String reviewsContent;

	public EpisodesReviews(int episodeReviewId, Users user, Episodes episode, String reviewsContent) {
		this.episodeReviewId = episodeReviewId;
		this.user = user;
		this.episode = episode;
		this.reviewsContent = reviewsContent;
	}

  	public EpisodesReviews(int episodeReviewId) {
		this.episodeReviewId = episodeReviewId;
  }

	public int getEpisodeReviewId() {
		return episodeReviewId;
	}

	public void setEpisodeReviewId(int episodeReviewId) {
		this.episodeReviewId = episodeReviewId;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Episodes getEpisode() {
		return episode;
	}

	public void setEpisode(Episodes episode) {
		this.episode = episode;
	}

	public String getReviewsContent() {
		return reviewsContent;
	}

	public void setReviewsContent(String reviewsContent) {
		this.reviewsContent = reviewsContent;
	}

}
