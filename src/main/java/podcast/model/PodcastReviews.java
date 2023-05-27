package podcast.model;

public class PodcastReviews {
	protected int podcastReviewId;
	protected Podcast podcast;
	protected String reviewsContent;
	// Change the rating to type of integer instead of enum.
	protected int rating;
	protected Users user;
	
	public PodcastReviews(int podcastReviewId, Podcast podcast, String reviewsContent, int rating, Users user) {
		this.podcastReviewId = podcastReviewId;
		this.podcast = podcast;
		this.reviewsContent = reviewsContent;
		this.rating = rating;
		this.user = user;
	}
	
	public PodcastReviews(Podcast podcast, String reviewsContent, int rating, Users user) {
		this.podcast = podcast;
		this.reviewsContent = reviewsContent;
		this.rating = rating;
		this.user = user;
	}
	
	public PodcastReviews(int podcastReviewId, Podcast podcast, Users user) {
		this.podcastReviewId = podcastReviewId;
		this.podcast = podcast;
		this.user = user;
	}
	
	public PodcastReviews(Podcast podcast, Users user) {
		this.podcast = podcast;
		this.user = user;
	}
	
	public PodcastReviews(int podcastReviewId) {
		this.podcastReviewId = podcastReviewId;
	}
	public int getPodcastReviewId() {
		return podcastReviewId;
	}

	public void setPodcastReviewId(int podcastReviewId) {
		this.podcastReviewId = podcastReviewId;
	}

	public Podcast getPodcast() {
		return podcast;
	}

	public void setPodcast(Podcast podcast) {
		this.podcast = podcast;
	}

	public String getReviewsContent() {
		return reviewsContent;
	}

	public void setReviewsContent(String reviewsContent) {
		this.reviewsContent = reviewsContent;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}


}
