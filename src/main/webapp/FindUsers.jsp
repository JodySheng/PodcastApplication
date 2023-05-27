<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find a User</title>
</head>
<body>
	<form action="findusers" method="post">
		<h1>Search for a User by UserName</h1>
		<p>
			<label for="username">UserName</label>
			<input id="username" name="username" value="${fn:escapeXml(param.username)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>
	<div id="userCreate"><a href="usercreate">Create User</a></div>
	<br/>
	<h1>Matching Users</h1>
        <table border="1">
            <tr>
                <th>UserId</th>
                <th>UserName</th>
                <th>PassWords</th>
                <th>PodcastReviews</th>
                <th>EpisodesReviews</th>
                <th>PodcastReviewComments</th>
                <th>EpisodeReviewComments</th>
                <th>PodcastReviewLikes</th>
                <th>RecommendationOfCategory</th>
                <th>Delete User</th>
                <th>Update User</th>
            </tr>
            <c:forEach items="${users}" var="user" >
                <tr>
                    <td><c:out value="${user.getUserId()}" /></td>
                    <td><c:out value="${user.getUserName()}" /></td>
                    <td><c:out value="${user.getPasswords()}" /></td>
                    <td><a href="podcastreviews?userid=<c:out value="${user.getUserId()}"/>">PodcastReviews</a></td>
                    <td><a href="episodereviews?userid=<c:out value="${user.getUserId()}"/>">EpisodesReviews</a></td>
                    <td><a href="podcastreviewcomments?userid=<c:out value="${user.getUserId()}"/>">PodcastReviewComments</a></td>
                    <td><a href="episodereviewcomments?userid=<c:out value="${user.getUserId()}"/>">EpisodesReviewComments</a></td>
                    <td><a href="podcastreviewlikes?userid=<c:out value="${user.getUserId()}"/>">PodcastReviewLikes</a></td>
                    <td><a href="recommendationofcategory?userid=<c:out value="${user.getUserId()}"/>">recommendation</a></td>
                    <td><a href="userdelete?username=<c:out value="${user.getUserName()}"/>">Delete</a></td>
                    <td><a href="userupdate?username=<c:out value="${user.getUserName()}"/>">Update</a></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
