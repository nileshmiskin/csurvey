<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="<%=request.getContextPath()%>"></c:set>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Dashboard</title>
<script type="text/javascript" src="${ctx}/resources/jquery-1.9.1.js"></script>
<script type="text/javascript" src="${ctx}/resources/highcharts.js"></script>
<script type="text/javascript">
	var ctx = '${ctx}';
	function getFeatureDetails(feature) {
		var url = ctx + "/admin/dashboard/" + feature + ".do";
		alert(url);
		$("#dashboardForm").attr("action", url);
		$("#dashboardForm").submit();
	}
</script>

</head>
<body>
	<form:form modelAttribute="dashboardVO" id="dashboardForm">
		<form:select path="selectedFeature"
			onchange="getFeatureDetails(this.value)">
			<form:option value="-1">Select</form:option>
			<c:forEach var="feature" items="${dashboardVO.features}">
				<form:option value="${feature.name }">
					<c:out value="${feature.name }"></c:out>
				</form:option>
			</c:forEach>
		</form:select>

		<div>
			Feature Name:
			<c:out value="${feature.name}"></c:out>
			<br /> <img src="${feature.featureImageURL}" height="160"
				width="200"></img> <br /> Feature Description:
			<c:out value="${feature.description}"></c:out>
			<br />
			<c:forEach var="feedback" items="${feature.feedbacks}">
				<textarea>${feedback.comment}</textarea>
				<br />
				<input id="feedbackId" type="hidden" value="${feedback.feedbackId}"></input>
			</c:forEach>
		</div>

	</form:form>
</body>
</html>