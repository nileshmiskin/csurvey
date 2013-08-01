<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="<%=request.getContextPath()%>"></c:set>
<table style="align:justify">
	<c:forEach var="feedback" items="${feature.feedbacks}">
		<tr>
			<td><p><c:out value="${feedback.comment}"></c:out></p></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td><c:choose>
					<c:when test="${feedback.like eq true}">
						<img src="${ctx}/resources/images/like.jpg" height="20" width="20"></img>
					</c:when>
					<c:otherwise>
						<img src="${ctx}/resources/images/dislike.jpg" height="20"
							width="20"></img>
					</c:otherwise>
				</c:choose> <c:out value="- ${feedback.userVO.name}"></c:out></td>
		</tr>
	</c:forEach>
</table>
