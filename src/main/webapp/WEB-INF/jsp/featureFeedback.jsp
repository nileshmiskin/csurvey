<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="<%=request.getContextPath()%>"></c:set>
<html>
<head>
<meta charset="utf-8">
<title>dashboard</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" type="text/css"
	href="resources/css/normalize.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/webflow.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/csurvey.css">
<link rel="icon" type="image/x-icon"
	href="https://y7v4p6k4.ssl.hwcdn.net/placeholder/favicon.ico">
<link rel="apple-touch-icon"
	href="https://y7v4p6k4.ssl.hwcdn.net/51e088cb308929c46c000051/51e1a444bceaf5b67b00004d_thumbnail-starter.png">
<script type="text/javascript" src="${ctx}/resources/jquery-1.9.1.js"></script>
<script type="text/javascript" src="${ctx}/resources/highcharts.js"></script>
<script type="text/javascript">
var ctx = '${ctx}';
function getFeatureDetails(feature) {
	if(feature == "-1")
		return;
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
</script>
</head>
<body>
	<div class="nav-section">
		<div class="w-container">
			<div class="w-row">
				<div class="w-col w-col-4">
					<div class="company">
						<img alt="Cognizant" src="${ctx}/resources/images/cogni.png" height="40" width="120"></img>&nbsp;Dashboard <br>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="section hero">
		<div class="">
			<div class="w-row">
				<%-- Feature Name:
				<c:out value="${feature.name}"></c:out>
				<br /> <img src="${feature.featureImageURL}" height="160"
					width="200"></img> <br /> Feature Description:
				<c:out value="${feature.description}"></c:out>
				<br />
				<c:forEach var="feedback" items="${feature.feedbacks}">
					<textarea>${feedback.comment}</textarea>
					<br />
					<input id="feedbackId" type="hidden" value="${feedback.feedbackId}"></input>
				</c:forEach> --%>

				<%@include file="featureDetails.jsp"%>
			</div>
		</div>
	</div>
	<input id="featureName" type="hidden" value="${feature.name}"></input>
	<script type="text/javascript">
		var fname = $("#featureName").val();
		getFeatureDetails(fname);
	</script>
</body>