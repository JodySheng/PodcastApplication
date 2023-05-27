package podcast.model;

public class PodcastReviewLikes {
	protected Users users;
	protected PodcastReviews podcastReviews;
	protected boolean likes;
	
	public PodcastReviewLikes(Users users, PodcastReviews podcastReviews, boolean likes) {
		this.users = users;
		this.podcastReviews = podcastReviews;
		this.likes = likes;
	}

	public PodcastReviewLikes(Users users, PodcastReviews podcastReviews) {
		this.users = users;
		this.podcastReviews = podcastReviews;
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

	public boolean isLikes() {
		return likes;
	}

	public void setLikes(boolean likes) {
		this.likes = likes;
	}
}
