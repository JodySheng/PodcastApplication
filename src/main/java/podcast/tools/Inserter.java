package podcast.tools;
import podcast.dal.*;
import podcast.model.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;


public class Inserter {
	
	 public static void main(String[] args) throws SQLException {
	  
		  // DAO instances.
		  PersonsDao personsDao = PersonsDao.getInstance();
		  AdministratorDao administratorDao = AdministratorDao.getInstance();
		  UsersDao usersDao = UsersDao.getInstance();
		  PodcastDao podcastDao = PodcastDao.getInstance();
		  EpisodesDao episodesDao = EpisodesDao.getInstance();
		  PodcastReviewsDao podcastReviewsdDao = PodcastReviewsDao.getInstance();
		  EpisodesReviewsDao episodesReviewsDao = EpisodesReviewsDao.getInstance();
		  PodcastReviewCommentsDao podcastReviewCommentsDao = PodcastReviewCommentsDao.getInstance();
		  PodcastReviewLikesDao podcastReviewLikesDao = PodcastReviewLikesDao.getInstance();
		  EpisodeReviewCommentsDao episodeReviewCommentsDao = EpisodeReviewCommentsDao.getInstance();
		  
		  // INSERT objects from our model.
		  Persons person = new Persons("1", "Zuo", "password");
		  person = personsDao.create(person);
		  
		  Date date = new Date();
		  Administrator administrator = new Administrator("2", "Zhao", "password", date);
		  administrator = administratorDao.create(administrator);
		  
		  Users user = new Users("3", "Li", "password", date);
		  user = usersDao.create(user);
		  
		  Podcast podcast = new Podcast("1", "Podcast1", "Zuo", "Funny", "Eng");
		  podcast = podcastDao.create(podcast);
		  
		  Episodes episode =  new Episodes("1", "episode1" , date, "description", 100, "https:", podcast);
		  episode = episodesDao.create(episode);
		  
		  PodcastReviews podcastReview = new PodcastReviews(1, podcast, "ok", 1, user);
		  podcastReview = podcastReviewsdDao.create(podcastReview);
		  
		  EpisodesReviews episodesReview = new EpisodesReviews(1, user, episode, "Good");
		  episodesReview = episodesReviewsDao.create(episodesReview);
		  
		  PodcastReviewComments podcastReviewComment = new PodcastReviewComments(1, user, podcastReview, "nice");
		  podcastReviewComment = podcastReviewCommentsDao.create(podcastReviewComment);
		  
		  PodcastReviewLikes podcastReviewLike = new PodcastReviewLikes(user, podcastReview, true);
		  podcastReviewLike = podcastReviewLikesDao.create(podcastReviewLike);
		  
		  EpisodeReviewComments episodeReviewComment = new EpisodeReviewComments(1, "not bad", user, episodesReview);
		  episodeReviewComment = episodeReviewCommentsDao.create(episodeReviewComment);
		  
		  
		  // READ.
		  Persons p1 = personsDao.getPersonFromUserId("1");
		  
		  System.out.format("Reading person: Id:%s userName:%s password:%s \n",
		   p1.getUserId(), p1.getUserName(), p1.getPasswords());
		  // 为什么要get persons from username?
		  List<Persons> p2 = personsDao.getPersonsFromUserName("Zuo");
		  for(Persons p : p2) {
		   System.out.format("Looping persons: Id:%s userName:%s password:%s \n",
		    p.getUserId(), p.getUserName(), p.getPasswords());
		  }
		  
		  Administrator a1 = administratorDao.getAdministratorFromUserId("2");
		  System.out.format("Reading administrator: Id:%s userName:%s password:%s \n",
		    a1.getUserId(), a1.getUserName(), a1.getPasswords());
		  
		  // 为什么userName 可以get这么多admin？
		  List<Administrator> a2 = administratorDao.getAdministratorFromUserName("Zhao");
		  for(Persons a : a2) {
		   System.out.format("Looping persons: Id:%s userName:%s password:%s \n",
		    a.getUserId(), a.getUserName(), a.getPasswords());
		  }
		  
		  Users u1 = usersDao.getUserFromUserId("3");
		  System.out.println("User " + u1.getUserName());
		  
		  Podcast pod1 = podcastDao.getPodcastById("1");
		  System.out.format("Reading podcast: %s, %s, %s, %s, %s \n",
		    pod1.getPodcastId(), pod1.getPodcastTitle(), pod1.getAuthor(), pod1.getCategories(), pod1.getLangaugeType());
		
		  Episodes e1 = episodesDao.getEpisodesById("1");
		  System.out.format("Reading episode: %s, %s, %s, %d, %s, %s \n",
		    e1.getEpisodeId(), e1.getEpisodesTitle(), e1.getDescriptions(), e1.getAudioLength(), e1.getAudioLength(), e1.getPodcast().getPodcastId());  
		 
		  PodcastReviews podRev1 = podcastReviewsdDao.getPodcastReviewsById(1);
		  System.out.format("Reading podcastReviews: %d, %s, %s, %s, %s \n",
		    podRev1.getPodcastReviewId(), podRev1.getPodcast().getPodcastId(), pod1.getAuthor(), pod1.getCategories(), pod1.getLangaugeType());
		  
		  EpisodesReviews epiRev1 = episodesReviewsDao.getEpisodesReviewsById("1");
		  System.out.format("Reading episodesReviews: %d, %s, %s, %s \n",
		    epiRev1.getEpisodeReviewId(), epiRev1.getUser().getUserId(),epiRev1.getEpisode().getEpisodeId(), epiRev1.getReviewsContent());
		  
		  PodcastReviewComments com1 = podcastReviewCommentsDao.getPodcastReviewCommentsById(1);
		  System.out.format("podcastReviewComment: %d, %s, %d, %s \n",
		    com1.getPodcastCommentId(), com1.getUsers().getUserId(), com1.getPodcastReviews().getPodcastReviewId(), com1.getCommentContent());
		  
		  List<PodcastReviewLikes> likes = podcastReviewLikesDao.getPodcastReviewLikesForUser(user);
		  for(PodcastReviewLikes l : likes) {
		   System.out.format("Looping likesForUser: uid:%s poid:%s like:%sb \n",
		    l.getUsers().getUserId(), l.getPodcastReviews().getPodcastReviewId(), l.isLikes());
		  }
		  
		  List<PodcastReviewLikes> likes2 = podcastReviewLikesDao.getPodcastReviewLikesForReview(podcastReview);
		  for(PodcastReviewLikes l : likes2) {
		   System.out.format("Looping likesForReview: uid:%s poid:%s like:%sb \n",
		    l.getUsers().getUserId(), l.getPodcastReviews().getPodcastReviewId(), l.isLikes());
		  }
		  
		  EpisodeReviewComments com2 = episodeReviewCommentsDao.getEpisodeReviewCommentsById(1);
		  System.out.format("episdoeReviewComment: %d, %s, %s, %d \n",
		    com2.getEpisodeCommentId(), com2.getCommentContent(), com2.getUser().getUserId(), com2.getEpisodesReview().getEpisodeReviewId());
		  
		// UPDATE.
		  p1 = personsDao.updateUserName(p1, "newPerson1");
		  System.out.format("Reading newPerson: Id:%s userName:%s password:%s \n",
		    p1.getUserId(), p1.getUserName(), p1.getPasswords());
		  
		  a1 = administratorDao.updateUserName(a1, "newAdministrator1");
		  System.out.format("Reading newAdministrator: Id:%s userName:%s password:%s \n",
		    a1.getUserId(), a1.getUserName(), a1.getPasswords());
		  
		  u1 = usersDao.updateUserName(u1, "newUser1");
		  System.out.println("newUser: " + u1.getUserName());
		  
		  pod1 = podcastDao.updatePodcastTitle(pod1, "newTitle");
		  System.out.format("Reading newPodcast: %s, %s, %s, %s, %s \n",
		    pod1.getPodcastId(), pod1.getPodcastTitle(), pod1.getAuthor(), pod1.getCategories(), pod1.getLangaugeType());
	
		  e1 = episodesDao.updateEpisodesTitle(e1, "newTitle");
		  System.out.format("Reading newEpisode: %s, %s, %s, %d, %s, %s \n",
		    e1.getEpisodeId(), e1.getEpisodesTitle(), e1.getDescriptions(), e1.getAudioLength(), e1.getAudioLength(), e1.getPodcast().getPodcastId());
		  
		  // podcastReviewsDao 要加入update
		  podRev1 = podcastReviewsdDao.updatePodcastReviewsContent(podRev1, "newContent");
		  System.out.format("Reading newPodcastReviews: %d, %s, %s, %s, %s \n",
		    podRev1.getPodcastReviewId(), podRev1.getPodcast().getPodcastId(), pod1.getAuthor(), pod1.getCategories(), pod1.getLangaugeType());
		  
		  epiRev1 = episodesReviewsDao.updateEpisodesReviewsContent(epiRev1, "newContent");
		  System.out.format("Reading mewEpisodesReviews: %d, %s, %s, %s \n",
		    epiRev1.getEpisodeReviewId(), epiRev1.getUser().getUserId(),epiRev1.getEpisode().getEpisodeId(), epiRev1.getReviewsContent());
		  
		  com1 = podcastReviewCommentsDao.updateContent(com1, "newContent");
		  System.out.format("newPodcastReviewComment: %d, %s, %d, %s \n",
		    com1.getPodcastCommentId(), com1.getUsers().getUserId(), com1.getPodcastReviews().getPodcastReviewId(), com1.getCommentContent());
		  
		  // likes 我没有写update，因为感觉like直接取消delete就好？
		  
		  // episodeReviewCommentsDao 要加入update
		  com2 = episodeReviewCommentsDao.updateEpisodeReviewCommentsContent(com2, "newContent");
		  System.out.format("episdoeReviewComment: %d, %s, %s, %d \n",
		    com2.getEpisodeCommentId(), com2.getCommentContent(), com2.getUser().getUserId(), com2.getEpisodesReview().getEpisodeReviewId());
		 
		// DELETE.
		  p1 = personsDao.delete(p1);
		  try {
			  System.out.println(p1.getUserId());
		  } catch(Exception e) {
			  System.out.println("No p1 exists.");
		  }
	
		  a1 = administratorDao.delete(a1);
		  try {
			  System.out.println(a1.getUserId());
		  } catch(Exception e) {
			  System.out.println("No a1 exists.");
		  }
		  
		  
		  u1 = usersDao.delete(u1);
		  try {
			  System.out.println(u1.getUserId());
		  } catch(Exception e) {
			  System.out.println("No u1 exists.");
		  }
		  
		  pod1 = podcastDao.delete(pod1);
		  try {
			  System.out.println(pod1.getPodcastId());
		  } catch(Exception e) {
			  System.out.println("No pod1 exists.");
		  }
		  
		  e1 = episodesDao.delete(e1);
		  try {
			  System.out.println(e1.getEpisodeId());
		  } catch(Exception e) {
			  System.out.println("No e1 exists.");
		  }
		  
		  podRev1 = podcastReviewsdDao.delete(podRev1);
		  try {
			  System.out.println(podRev1.getPodcastReviewId());
		  } catch(Exception e) {
			  System.out.println("No podRev1 exists.");
		  }
		  
		  epiRev1 = episodesReviewsDao.delete(epiRev1);
		  try {
			  System.out.println(epiRev1.getEpisodeReviewId());
		  } catch(Exception e) {
			  System.out.println("No epiRev1 exists.");
		  }
		  
		  com1 = podcastReviewCommentsDao.delete(com1);
		  try {
			  System.out.println(com1.getPodcastCommentId());
		  } catch(Exception e) {
			  System.out.println("No com1 exists.");
		  }
		  
		  com2 = episodeReviewCommentsDao.delete(com2);
		  try {
			  System.out.println(com2.getEpisodeCommentId());
		  } catch(Exception e) {
			  System.out.println("No com2 exists.");
		  }
		  
		  podcastReviewLike = podcastReviewLikesDao.delete(podcastReviewLike);
		  try {
			  System.out.println(podcastReviewLike.getUsers().getUserId());
		  } catch(Exception e) {
			  System.out.println("No podcastReviewLike exists.");
		  }
	  
	 }  
}