<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find an Episode</title>
</head>
<body>
	<form action="findepisodes" method="post">
		<h1>Search for an Episode by Title</h1>
		<p>
			<label for="episodetitle">Title</label>
			<input id="episodetitle" name="episodetitle" value="${fn:escapeXml(param.episodetitle)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<h1>Matching Episodes</h1>
        <table border="1">
            <tr>
                <th>EpisodeId</th>
                <th>EpisodesTitle</th>
                <th>PublishDate</th>
                <th>Descriptions</th>
                <th>AudioLength</th>
                <th>Url</th>
                <th>PodcastTitle</th>
                <th>EpisodesReviews</th>
                <th>Delete Episode</th>
                <th>Update Episode</th>
            </tr>
            <c:forEach items="${episodes}" var="episode" >
                <tr>
                    <td><c:out value="${episode.getEpisodeId()}" /></td>
                    <td><c:out value="${episode.getEpisodesTitle()}" /></td>
                    <td><fmt:formatDate value="${episode.getPublishDate()}" pattern="yyyy-MM-dd"/></td>
                    <td><c:out value="${episode.getDescriptions()}" /></td>
                    <td><c:out value="${episode.getAudioLength()}" /></td>
                    <td><c:out value="${episode.getUrl()}" /></td>
                    <td><c:out value="${episode.getPodcast().getPodcastTitle()}" /></td>
                    <td><a href="episodereviews?episodeid=<c:out value="${episode.getEpisodeId()}"/>">EpisodeReviews</a></td>
                    <td><a href="episodedelete?episodeid=<c:out value="${episode.getEpisodeId()}"/>">Delete</a></td>
                    <td><a href="episodeupdate?episodeid=<c:out value="${episode.getEpisodeId()}"/>">Update</a></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
