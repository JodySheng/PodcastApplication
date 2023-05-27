<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>EpisodeReviewComments</title>
</head>
<body>
	<h1>${messages.title}</h1>
        <table border="1">
            <tr>
                <th>EpisodeCommentId</th>
                <th>CommentContent</th>
                <th>UserName</th>
                <th>EpisodeReviewId</th>
                <th>Delete EpisodeReviewComment</th>
            </tr>
            <c:forEach items="${episodeReviewComments}" var="episodeReviewComment" >
                <tr>
                    <td><c:out value="${episodeReviewComment.getEpisodeCommentId()}" /></td>
                    <td><c:out value="${episodeReviewComment.getCommentContent()}" /></td>
                    <td><c:out value="${episodeReviewComment.getUsers().getUserName()}" /></td>
                    <td><c:out value="${episodeReviewComment.getEpisodesReview().getEpisodeReviewId()}" /></td>
                    <td><a href="deleteepisodereviewcomment?episodereviewcommentid=<c:out value="${episodeReviewComment.getEpisodeCommentId()}"/>">Delete</a></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
