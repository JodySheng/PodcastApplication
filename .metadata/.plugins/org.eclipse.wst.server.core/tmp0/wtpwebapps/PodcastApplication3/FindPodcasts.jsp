<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find a Podcast</title>
</head>
<body>
	<form action="findpodcasts" method="post">
		<h1>Search for a Podcast by Title</h1>
		<p>
			<label for="title">Title</label>
			<input id="title" name="title" value="${fn:escapeXml(param.title)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<h1>Matching Podcasts</h1>
        <table border="1">
            <tr>
                <th>PodcastId</th>
                <th>PodcastTitle</th>
                <th>Author</th>
                <th>Categories</th>
                <th>LanguageType</th>
                <th>PodcastReviews</th>
                <th>Episodes</th>
                <th>Delete Podcast</th>
                <th>Update Podcast</th>
            </tr>
            <c:forEach items="${podcasts}" var="podcast" >
                <tr>
                    <td><c:out value="${podcast.getPodcastId()}" /></td>
                    <td><c:out value="${podcast.getPodcastTitle()}" /></td>
                    <td><c:out value="${podcast.getAuthor()}" /></td>
                    <td><c:out value="${podcast.getCategories()}" /></td>
                    <td><c:out value="${podcast.getLangaugeType()}" /></td>
                    <td><a href="podcastreviews?podcastid=<c:out value="${podcast.getPodcastId()}"/>">PodcastReviews</a></td>
                    <td><a href="findepisodes?podcastid=<c:out value="${podcast.getPodcastId()}"/>">Episodes</a></td>
                    <td><a href="podcastdelete?title=<c:out value="${podcast.getPodcastTitle()}"/>">Delete</a></td>
                    <td><a href="podcastupdate?title=<c:out value="${podcast.getPodcastTitle()}"/>">Update</a></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
