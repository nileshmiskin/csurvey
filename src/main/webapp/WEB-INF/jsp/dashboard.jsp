<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="<%=request.getContextPath()%>"></c:set>
<!DOCTYPE html>
<!-- This site was created in Webflow. http://www.webflow.com-->
<!-- Last Published: Thu Aug 01 2013 05:47:15 GMT+0000 (UTC) -->
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
	
	function getApplicationDetails(application){
		$("#dashboardForm").attr("action", ctx+"/admin/dashboard.do");
		$("#dashboardForm").submit();
	}
	
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
	
	function displayFeaturesForApp(){
		
		$('#fcontainer').highcharts({
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false
	        },
	        title: {
	            text: 'Features for '+$("#selectedApplication :selected").text()
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
		            <c:forEach var="featureVO" items="${dashboardVO.featureVOs}">
						<c:out value="['${featureVO.name}', ${featureVO.featureStats.likeCount}]," escapeXml="false"></c:out>
		            </c:forEach>
	            ],
	            point:{
	    	        events: { 
	    	            click: function(e)
	    	            {
	    	            	var feature =e.point.name;
	    	            	getFeatureDetails(feature);
	    	            }
	    	         }
	            }
	        }]
	    });
 	} 
	
	
	function displayAppUsersChart(){
		
	        $('#mcontainer').highcharts({
	            chart: {
	                type: 'column'
	            },
	            title: {
	                text: 'Applications User Base'
	            },
	            /*subtitle: {
	                text: 'Source: WorldClimate.com'
	            },*/
	            xAxis: {
	                categories: [
	                    'Application'
	                ]
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: 'Users'
	                }
	            },
	            tooltip: {
	                headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
	                    '<td style="padding:0"><b>{point.y:.1f} No</b></td></tr>',
	                footerFormat: '</table>',
	                shared: true,
	                useHTML: true
	            },
	            plotOptions: {
	            	column: {
	                    pointPadding: 0.2,
	                    borderWidth: 0
	                }
	            },
	            series: [
	                     <c:forEach var="applicationStat" items="${dashboardVO.applicationStats}">
	                    	 <c:out value="{name:'${applicationStat.name}', data: [${applicationStat.userCount}]}," escapeXml="false"></c:out>
	                     </c:forEach>
	              ]
	        });
	        
	}
</script>

</head>
<body>
	<form:form modelAttribute="dashboardVO" id="dashboardForm">
		<div class="nav-section">
			<div class="w-container">
				<div class="w-row">
					<div class="w-col w-col-2">
						<div class="company">
							Dashboard <br>
						</div>
					</div>
					<div class="w-col w-col-2">
						<form:select id="selectedApplication" path="selectedApplication"
							class="headSelect" onchange="getApplicationDetails(this.value)">
							<form:option value="-1">Application</form:option>
							<c:forEach var="application" items="${dashboardVO.applications}">
								<form:option value="${application.id}">
									<c:out value="${application.name }"></c:out>
								</form:option>
							</c:forEach>
						</form:select>
					</div>
					<div class="w-col w-col-2"></div>
					<div class="w-col w-col-2">
						<c:if
							test="${not empty dashboardVO.selectedApplication and dashboardVO.selectedApplication ne '-1'}">
							<form:select id="selectedFeature" path="selectedFeature"
								class="headSelect" onchange="getFeatureDetails(this.value)">
								<form:option value="-1">Select</form:option>
								<c:forEach var="feature" items="${dashboardVO.features}">
									<form:option value="${feature.name }">
										<c:out value="${feature.name }"></c:out>
									</form:option>
								</c:forEach>
							</form:select>
						</c:if>
					</div>
					<div class="w-col w-col-2"></div>
					<div class="w-col w-col-2">
						<div class="company">
							<img alt="Cognizant" src="${ctx}/resources/images/cogni.png"
								height="40" width="140"></img>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="section hero">
			<div class="w-container">
				<div class="w-row">
					<div id="mcontainer" class="w-col w-col-6">
						<img class="hero-image" src="images/image-placeholder.svg"
							width="auto" height="auto" alt="image-placeholder.svg">
					</div>
					<div class="w-col w-col-6">
						<div id="fcontainer">
							<article>
							<h4>Applications</h4>
							<c:forEach var="application" items="${dashboardVO.applications}">
								<p>
									<b><c:out value="${application.name}" /></b>
								<p>
									<c:out value="${application.description}" />
								</p>
								<br/>
							</c:forEach>
							</article>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="section">
			<div class="w-row">
				<div class="w-col w-col-6">
					<div></div>
				</div>
				<div id="comments" class="w-col w-col-6">
					<div></div>
				</div>
			</div>
		</div>
		<div id="featureDiv" class="section"></div>
		<div class="section footer">
			<div class="w-container">
				<div class="w-row">
					<div class="w-col w-col-6">
						<div>
							© Cognizant Technology Solutions Limited <br>
						</div>
					</div>
					<div class="w-col w-col-6">
						<a class="footer-link" href="http://www.cognizant.com">Cognizant</a>
					</div>
				</div>
			</div>
		</div>
	</form:form>
	<script type="text/javascript">
	displayAppUsersChart();
	if($("#selectedApplication").val()!= "-1")
		displayFeaturesForApp();
	</script>
</body>
</html>