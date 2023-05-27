package podcast.model;

public class EpisodeReviewComments {

	protected int episodeCommentId;
	protected String commentContent;
	protected Users user;
	protected EpisodesReviews episodesReview;

	public EpisodeReviewComments(int episodeCommentId, String commentContent, Users user, EpisodesReviews episodesReview) {
		this.episodeCommentId = episodeCommentId;
		this.commentContent = commentContent;
		this.user = user;
		this.episodesReview = episodesReview;
	}

  public EpisodeReviewComments(int episodeCommentId) {
	  this.episodeCommentId = episodeCommentId;
  }

  public int getEpisodeCommentId() {
		return episodeCommentId;
	}

	public void setEpisodeCommentId(int episodeCommentId) {
		this.episodeCommentId = episodeCommentId;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public EpisodesReviews getEpisodesReview() {
		return episodesReview;
	}

	public void setEpisodesReview(EpisodesReviews episodesReview) {
		this.episodesReview = episodesReview;
	}

}
