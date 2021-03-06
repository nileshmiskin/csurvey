<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="w-row">
	<div class="w-col w-col-2"></div>
	<div class="w-col w-col-2">
		<div>
			<c:if test="${not empty feature}">
				<h3>
					<c:out value="${feature.name}"></c:out>
				</h3>
				<br />
				<img src="${feature.featureImageURL}" height="160" width="200"></img>
				<br />
				<br />
				<h5>
					<c:out value="${feature.description}"></c:out>
				</h5>

			</c:if>
		</div>
	</div>
	<div class="w-col w-col-3">
		<h3>Responses</h3>
		<div id="container" style="width: 250px; height: 250px; align: left"></div>
		<br />
	</div>
	<div id="comments" class="w-col w-col-4">
		<h3>Comments</h3>
		<div id="feedbackDiv">
			<%@ include file="feedbacks.jsp"%>
			<a id="viewMore" href="${ctx}/features/${feature.name}/limit/-1.do" target="_blank">View all comments...</a>
		</div>
	</div>
	<div class="w-col w-col-3"></div>
</div>
<script type="text/javascript">
	if($("#selectedApplication").val() == undefined)
	$("#viewMore").remove();
</script>

<input type="hidden" id="likeCount"
	value="${feature.featureStats.likeCount}"></input>
<input type="hidden" id="totalUsers"
	value="${feature.featureStats.totalUsers}"></input>