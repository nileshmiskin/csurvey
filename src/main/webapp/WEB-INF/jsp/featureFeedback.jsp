<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
Feature Name:
<c:out value="${feature.name}"></c:out>
<br />
<img src="${feature.featureImageURL}"
	height="160" width="200"></img>
<br />
Feature Description:
<c:out value="${feature.description}"></c:out>
<br />
<c:forEach var="feedback" items="${feature.feedbacks}">
	<textarea>${feedback.comment}</textarea>
	<br />
	<input id="feedbackId" type="hidden" value="${feedback.feedbackId}"></input>
</c:forEach>
