<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PodcastReviews</title>
</head>
<body>
	<h1>${messages.title}</h1>
        <table border="1">
            <tr>
                <th>PodcastReviewId</th>
                <th>PodcastId</th>
                <th>UserName</th>
                <th>ReviewsContent</th>
                <th>Rating</th>
                <th>Comments</th>
                <th>Likes</th>
                <th>Delete PodcastReview</th>
            </tr>
            <c:forEach items="${podcastReviews}" var="podcastReview" >
                <tr>
                    <td><c:out value="${podcastReview.getPodcastReviewId()}" /></td>
                    <td><c:out value="${podcastReview.getPodcast().getPodcastId()}" /></td>
                    <td><c:out value="${podcastReview.getUser().getUserName()}" /></td>
                    <td><c:out value="${podcastReview.getReviewsContent()}" /></td>
                    <td><c:out value="${podcastReview.getRating()}" /></td>
                    <td><a href="podcastreviewcomments?podcastreviewid=<c:out value="${podcastReview.getPodcastReviewId()}"/>">PodcastReviewComments</a></td>
                    <td><a href="podcastreviewlikes?podcastreviewid=<c:out value="${podcastReview.getPodcastReviewId()}"/>">Likes</a></td>
                    <td><a href="deletepodcastreview?podcastreviewid=<c:out value="${podcastReview.getPodcastReviewId()}"/>">Delete</a></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
