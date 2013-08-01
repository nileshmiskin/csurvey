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
		var featureUrl = ctx + "/admin/dashboard/" + feature + ".do";
		$.ajax({
			headers: { 
		        Accept : "text/html, image/jpeg"
		    },
			type: "GET",
			url: featureUrl,
			success: function(response){
				$("#featureDiv").html(response);
				displayChart(feature, $("#likeCount").val(), $("#totalUsers").val());
			}
		}); 
	}
	
	function getFeatureLikedDetails(feature, liking) {
		var featureUrl = ctx + "/admin/dashboard/" + feature;
		if(liking == true)
			featureUrl = featureUrl + "/liked.do";
		else
			featureUrl = featureUrl + "/disliked.do";		
		$.ajax({
			headers: { 
		        Accept : "text/html, image/jpeg"
		    },
			type: "GET",
			url: featureUrl,
			success: function(response){
				$("#feedbackDiv").html(response);
			}
		}); 
	}
	
	function displayChart(feature, like, total){
		var likePercentage = like/total*100;
		var dislikePercentage = 100 - likePercentage;
		
		
		$('#container').highcharts({
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false
	        },
	        title: {
	            text: 'Responses'
	        },
	        tooltip: {
	    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: false,
	                    color: '#000000',
	                    connectorColor: '#000000',
	                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
	                },
	                showInLegend: true
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: 'Popularity',
	            data: [
	                ['Like', likePercentage],
	                ['Dislike', dislikePercentage],
	            ],
	            point:{
	    	        events: { 
	    	            click: function(e)
	    	            {
	    	            	var status=e.point.name;
	    	            	if(status=="Like"){
	    	            		getFeatureLikedDetails(feature, true);
	    	            	}else{
	    	            		getFeatureLikedDetails(feature, false);
	    	            	}
	    	            }
	    	         }
	            }
	        }]
	    });
	}
	
	function displayTopFeaturesChart(){
		
		$('#mcontainer').highcharts({
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false
	        },
	        title: {
	            text: 'Top trending features'
	        },
	        tooltip: {
	    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: false,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: false,
	                    color: '#000000',
	                    connectorColor: '#000000',
	                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
	                },
	                showInLegend: true
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: 'Popularity',
	            data: [
		            <c:forEach var="featureVO" items="${dashboardVO.topFeatures}">
						<c:out value="['${featureVO.name}', ${featureVO.likeCount}]," escapeXml="false"></c:out>
		            </c:forEach>
	            ]
	        }]
	    });
 	} 
	
</script>

</head>
<body>
	<form:form modelAttribute="dashboardVO" id="dashboardForm">
		<form:select id="selectedFeature" path="selectedFeature" onchange="getFeatureDetails(this.value)">
			<form:option value="-1">Select</form:option>
			<c:forEach var="feature" items="${dashboardVO.features}">
				<form:option value="${feature.name }">
					<c:out value="${feature.name }"></c:out>
				</form:option>
			</c:forEach>
		</form:select>
		<div id="mcontainer" style="width: 250px; height: 250px; align: left"></div>
		<div id="featureDiv"></div>
	</form:form>
	<script type="text/javascript">
	displayTopFeaturesChart();
	</script>
</body>
</html>