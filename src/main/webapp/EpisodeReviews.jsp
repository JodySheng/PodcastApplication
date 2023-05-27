<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>EpisodeReviews</title>
</head>
<body>
	<h1>${messages.title}</h1>
        <table border="1">
            <tr>
                <th>EpisodeReviewId</th>
                <th>EpisodeId</th>
                <th>UserName</th>
                <th>ReviewsContent</th>
                <th>Comments</th>
                <th>Delete EpisodeReview</th>
            </tr>
            <c:forEach items="${episodesReviews}" var="episodeReview" >
                <tr>
                    <td><c:out value="${episodeReview.getEpisodeReviewId()}" /></td>
                    <td><c:out value="${episodeReview.getEpisode().getEpisodesTitle()}" /></td>
                    <td><c:out value="${episodeReview.getUser().getUserName()}" /></td>
                    <td><c:out value="${episodeReview.getReviewsContent()}" /></td>
                    <td><a href="episodereviewcomments?episodereviewid=<c:out value="${episodeReview.getEpisodeReviewId()}"/>">EpisodesReviewComments</a></td>
                    <td><a href="deleteepisodereview?episodereviewid=<c:out value="${episodeReview.getEpisodeReviewId()}"/>">Delete</a></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
