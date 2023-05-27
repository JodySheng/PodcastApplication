package podcast.model;

public class PodcastReviewComments {
	protected int podcastCommentId;
	protected Users users;
	protected PodcastReviews podcastReviews;
	protected String commentContent;
	
	public PodcastReviewComments(int podcastCommentId, Users users,
			PodcastReviews podcastReviews, String commentContent) {
		this.podcastCommentId = podcastCommentId;
		this.users = users;
		this.podcastReviews = podcastReviews;
		this.commentContent = commentContent;
	}

	public PodcastReviewComments(Users users, PodcastReviews podcastReviews, String commentContent) {
		this.users = users;
		this.podcastReviews = podcastReviews;
		this.commentContent = commentContent;
	}
	
	public PodcastReviewComments(int podcastCommentId) {
		this.podcastCommentId = podcastCommentId;
	}
	
	public int getPodcastCommentId() {
		return podcastCommentId;
	}

	public void setPodcastCommentId(int podcastCommentId) {
		this.podcastCommentId = podcastCommentId;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public PodcastReviews getPodcastReviews() {
		return podcastReviews;
	}

	public void setPodcastReviews(PodcastReviews podcastReviews) {
		this.podcastReviews = podcastReviews;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

}
