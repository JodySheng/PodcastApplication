<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PodcastReviewComments</title>
</head>
<body>
	<h1>${messages.title}</h1>
        <table border="1">
            <tr>
                <th>PodcastReviewCommentId</th>
                <th>UserName</th>
                <th>PodcastReviewId</th>
                <th>CommentContent</th>
                <th>Delete PodcastReviewComment</th>
            </tr>
            <c:forEach items="${podcastReviewComments}" var="podcastReviewComment" >
                <tr>
                    <td><c:out value="${podcastReviewComment.getPodcastCommentId()}" /></td>
                    <td><c:out value="${podcastReviewComment.getUsers().getUserName()}" /></td>
                    <td><c:out value="${podcastReviewComment.getPodcastReviews().getPodcastReviewId()}" /></td>
                    <td><c:out value="${podcastReviewComment.getCommentContent()}" /></td>
                    <td><a href="deletepodcastreviewcomment?podcastreviewcommentid=<c:out value="${podcastReviewComment.getPodcastCommentId()}"/>">Delete</a></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
