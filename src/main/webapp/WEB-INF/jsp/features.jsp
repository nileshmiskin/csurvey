<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Features</title>
<script type="text/javascript" scr="/js/jquery-2.0.2.min.js"></script>

</head>
<body>
	<c:forEach var="feature" items="${features.featureList}">
		${feature.name}: ${feature.description}<br/>
		<img src="${feature.featureImageURL}"height="160" width="200"></img>
	</c:forEach>
</body>
</html>