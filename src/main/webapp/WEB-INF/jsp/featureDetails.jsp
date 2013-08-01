<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="<%=request.getContextPath()%>"></c:set>
<c:if test="${not empty feature}">
	<h3>
		<c:out value="${feature.name}"></c:out>
	</h3>
	<br />
	<img src="${feature.featureImageURL}" height="160" width="200"></img>
	<br />
	<br />
	<h4>
		<c:out value="${feature.description}"></c:out>
	</h4>
	<br />
	<div id="container" style="width: 250px; height: 250px; align: left"></div>
	<br />
	<div id="feedbackDiv">
		<%@ include file="feedbacks.jsp"%>
	</div>
</c:if>
<input type="hidden" id="likeCount"
	value="${feature.featureStats.likeCount}"></input>
<input type="hidden" id="totalUsers"
	value="${feature.featureStats.totalUsers}"></input>