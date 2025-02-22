<!doctype html>
<html lang="en">

<head>
  <title>${appName} API coverage</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>

<#assign missedEndpointNum = (allEndpointsNumber - coveredEndpointsNumber - ignoredEndpointNumber)>
<#assign coveragePercent = ((coveredEndpointsNumber / allEndpointsNumber) * 100)>
<#assign areaCoveragePercent = (((coveredAreasNumber + partCoveredAreasNumber) / areaNumber) * 100)>
<#assign barHeight = (areaNumber / 0.016)>
<#assign barInnerHeight = (areaNumber / 0.015)>
<#if coveragePercent == 100>
   <#assign left = '84px'>
<#elseif coveragePercent gt 10>
   <#assign left = '81px'>
<#else>
   <#assign left = '90px'>
</#if>

<style>
* {
  box-sizing: content-box;
}

body {
  font-family: 'lato', sans-serif;
  font-size: 12px;
  line-height: 1.42857143;
  text-align: -webkit-left;
  background-color: #efefef;
}

.container {
  padding-left: 90px;
  padding-top: 10px;
  margin-left: 0!important;
  margin-right: 0!important;
}

.area-title {
  padding: 5px 0px 5px 5px;
  color: #3b4151;
  font-size: 15;
}

.stat-container {
    margin-bottom: 6px;
    margin-right: 6px;
    box-shadow: 1px 1px 4px #d9d2d2;
}

.app-name {
    font-size: 22px;
}

.main-stat-num {
    font-size: 64px;
}

.main-stat-icon {
    font-size: 64px;
    text-align: center;
}

.main-stat-desc {
    font-size: 15px;
    font-weight: bold;
}

.test-case-list-title {
  display: flex;
  align-items: center;
  padding: 10px 20px 10px 10px;
  font-size: 14px;
  max-width: 700px;
  margin: 0 0 5px;
  font-family: sans-serif;
  border-bottom: 1px solid rgba(59,65,81,.3);
  color: #3b4151;
}

.responsive-table li {
  border-radius: 3px;
  padding: 10px 10px;
  max-width: 700px;
  display: flex;
  margin-top: 15px;
  align-items: center;
}
.responsive-table .table-header {
  border-radius: 0px;
  font-size: 14px;
  text-transform: uppercase;
  letter-spacing: 0.03em;
  border-bottom-color: rgb(187, 187, 187);
  border-bottom-style: solid;
  border-bottom-width: 1px;
}
.responsive-table .table-row-success {
  background-color: rgba(73, 204, 144, 0.15);
  box-shadow: 0px 0px 9px 0px rgba(73, 204, 144, 0.1);
  border: 1px solid #49cc90;
  cursor: pointer;
}
.responsive-table .table-row-danger {
  background-color: rgba(249, 62, 62, 0.15);
  box-shadow: 0px 0px 9px 0px rgba(249, 62, 62, 0.1);
  border: 1px solid #f93e3e;
}
.responsive-table .table-row-ignore {
  background-color: rgba(128, 209, 217, 0.15);
  box-shadow: 0px 0px 9px 0px rgba(89, 195, 205, 0.1);
  border: 1px solid #59C3CD;
}
.responsive-table .detail-content {
  border-radius: 0px;
  border-bottom-left-radius: 3px;
  border-bottom-right-radius: 3px;
  background-color: rgba(1, 1, 1, 0);
  margin-top: 0px;
  border-top: 0px
}
.responsive-table .test-case-list {
  box-shadow: 0px 0px 0px 0px;
  margin: 10px;
  background: rgba(0, 0, 0, 0);
  border: 0px;
}
.responsive-table .col-1 {
  flex-basis: 10%;
}
.responsive-table .col-2 {
  flex-basis: 40%;
  font-weight: 700;
  max-width: 80px;
  padding: 6px 15px;
  text-align: center;
  border-radius: 3px;
  text-shadow: 0 1px 0 rgba(0,0,0,.1);
  font-family: sans-serif;
  color: #fff;
}
.responsive-table .col-2-PUT {
  background: #fca130;
}
.responsive-table .col-2-POST {
  background: #49cc90;
}
.responsive-table .col-2-GET {
  background: #61affe;
}
.responsive-table .col-2-PATCH {
  background: #50e3c2;
}
.responsive-table .col-2-DELETE {
  background: #f93e3e;
}
.responsive-table .col-3 {
  flex-basis: 25%;
  font-weight: 600;
  padding-left: 20px;
  text-align: left;
}
.responsive-table .col-test-case-name {
  flex-basis: 75%;
  font-weight: 600;
  padding-left: 0px;
  text-align: left;
}
.responsive-table .col-4 {
  text-align: right;
  flex-basis: 0%;
  flex-grow: 1;
}
.responsive-table .passed {
  color: green;
}
.responsive-table .failed {
  color: red;
}
.responsive-table .skipped {
  color: orange;
}
.responsive-table .ignored {
  color: #59C3CD;
}
.responsive-table .hed-1 {
  flex-basis: 10%;
}
.responsive-table .hed-2 {
  flex-basis: 40%;
  max-width: 80px;
}
.responsive-table .hed-3 {
  flex-basis: 25%;
}
.responsive-table .hed-4 {
  flex-basis: 25%;
}
@media all and (max-width: 767px) {
  .responsive-table .table-header {
    display: none;
  }
  .responsive-table li {
    display: block;
  }
  .responsive-table .col {
    flex-basis: 100%;
  }
  .responsive-table .col {
    display: flex;
    padding: 10px 0;
  }
  .responsive-table .col:before {
    color: #6C7A89;
    padding-right: 10px;
    content: attr(data-label);
    flex-basis: 50%;
    text-align: right;
  }
}

#donutchart {
    position: relative;
}

#labelOverlay {
    position: absolute;
    top: 115px;
    left: ${left};
    text-align: center;
    cursor: default;
}

#labelOverlay p {
  line-height: 0.3;
  padding:0;
  margin: 8px;
}

#labelOverlay p.used-size {
  line-height: 0.5;
  font-size: 20pt;
  color: #8e8e8e;
}

/* SIDE NAVIGATOR */

.sidenav {
  text-align: center;
  height: 100%;
  width: 70px;
  position: fixed;
  z-index: 1;
  top: 0;
  left: 0;
  background-color: #343434;
  overflow-x: hidden;
  padding-top: 20px;
}

.sidenav a {
  text-decoration: none;
  font-size: 35px;
  color: #818181;
  display: block;
}

.sidenav a:hover {
  color: #f1f1f1;
  cursor: pointer;
}

.sidenav div {
  font-size: 15px;
  padding: 0px 0px 20px 0px;
  color: #818181;
}

</style>

<script type="text/javascript">
    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawGlobalCoverageChart);
    google.charts.setOnLoadCallback(drawAreaWiseBarChart);
    google.charts.setOnLoadCallback(drawAreaCoveragePieChart);

    window.onload = drawAreaCoveragePieChart;
    window.onresize = drawAreaCoveragePieChart;

    function drawGlobalCoverageChart() {
      const data = google.visualization.arrayToDataTable([
        ['Coverage', 'Percentage'],
        ['Covered', ${coveredEndpointsNumber}],
        ['Missed', ${missedEndpointNum}],
        ['Ignored', ${ignoredEndpointNumber}]
      ]);

      const options = {
        'titlePosition': 'none',
        'width': 240,
        'height': 260,
        'pieHole': 0.6,
        'chartArea':{
            left:10,
            top:10,
            width:'90%',
            height:'90%'
        },
        'legend': {
        	position: 'bottom'
        },
        'pieSliceText': 'none',
        'colors': ['#7DCEA0', '#D98880', '#80D1D9']};
      let chart = new google.visualization.PieChart(document.getElementById('piechart'));
      chart.draw(data, options);
    }

    function drawAreaCoveragePieChart() {
      const data = google.visualization.arrayToDataTable([
        ['Coverage', 'Percentage'],
        ['Covered', ${coveredAreasNumber}],
        ['Missed', ${missedAreaNum}],
        ['Partialy covered', ${partCoveredAreasNumber}]
      ]);

      const options = {
        'titlePosition': 'none',
        'width': 260,
        'height': 260,
        'pieHole': 0.6,
        'chartArea':{
            left:10,
            top:10,
            width:'90%',
            height:'90%'
        },
        'legend': {
        	position: 'bottom'
        },
        'pieSliceText': 'none',
        'colors': ['#7DCEA0', '#D98880', '#cebb7d']};
      let chart = new google.visualization.PieChart(document.getElementById('areaPieChart'));
      chart.draw(data, options);
    }

    function drawAreaWiseBarChart() {
      const data = google.visualization.arrayToDataTable([
        ['Area', 'Covered', 'Missing', 'Ignored'],
        <#list endpointCoverage?keys as key>
          ['${key}', ${areaWiseEndpoints[key].getCoveredEndpoints()}, ${areaWiseEndpoints[key].getUncoveredEndpointNum()}, ${areaWiseEndpoints[key].getIgnoredEndpoints()}], ${'\n'}
        </#list>
      ]);

      const options = {
        'titlePosition': 'none',
        'legend': {
            position: 'none'
        },
        'seriesType': 'bars',
        'colors': ['7DCEA0', '#D98880', '#80D1D9'],
        'isStacked': 'percent',
        'chartArea':{
            left: 100,
            top: 10,
            width: 700,
            height: ${barInnerHeight?c}
        }
      };

      let chart = new google.visualization.BarChart(document.getElementById('chart_div'));
      chart.draw(data, options);
    }

    function openEndpointRow(detailId, endpointId) {
      let testCaseList = document.getElementById(detailId);
      let endpoint = document.getElementById(endpointId);
      if (testCaseList != null) {
          if (testCaseList.style.display === "none") {
            testCaseList.style.display = "block";
            endpoint.style.borderBottomLeftRadius = "0px";
            endpoint.style.borderBottomRightRadius = "0px";
          } else {
            testCaseList.style.display = "none";
            endpoint.style.borderBottomLeftRadius = "3px";
            endpoint.style.borderBottomRightRadius = "3px";
          }
      }
    }

    function menuBarClick(menuItem) {
      let charts = document.getElementById("charts");
      let table = document.getElementById("table");
      if (menuItem == "table") {
          if (table != null && charts != null) {
            charts.style.display = "none";
            statiticholder.style.display = "none";
            table.style.display = "block";
            document.getElementById("tableItem").style.color = '#f1f1f1';
            document.getElementById("homeItem").style.color = '#818181';
          }
      } else {
          if (table != null && charts != null) {
            charts.style.display = "flex";
            statiticholder.style.display = "flex";
            table.style.display = "none";
            document.getElementById("tableItem").style.color = '#818181';
            document.getElementById("homeItem").style.color = '#f1f1f1';
          }
      }
      window.scrollTo(0, 0);
    }
</script>

<body onload="menuBarClick('charts')"">

<div class="container">
	<nav class="sidenav nav flex-column">
	  <div>Pitaya</div>
	  <a id="homeItem" onclick="menuBarClick('charts')">&#8962</a>
	  <a id="tableItem" onclick="menuBarClick('table')">&#128462;</a>
	</nav>

	<div id="statiticholder" class="row">
        <div class="col-md-auto stat-container card rounded-0 border-0">
		    <div class="card-body" style="align-content: center;">
			    <div class="app-name card-title">${appName}</div>
			    <div class="card-text">API Test Coverage<br>${currentDateAndTime}</div>
			</div>
		</div>

		<div class="col-md-auto stat-container card rounded-0 border-0">
			<div class="card-body">
				<div class="row">
					<div class="main-stat-desc card-title col">Number of Test Cases</div>
				</div>
				<div class="row">
					<div class="main-stat-num col">${testCaseNum}</div>
					<div class="main-stat-icon col">🧪</div>
				</div>
			</div>
		</div>

		<div class="col-md-auto stat-container card rounded-0 border-0">
			<div class="card-body">
				<div class="row">
					<div class="main-stat-desc card-title col">Total Endpoints</div>
				</div>
				<div class="row">
					<div class="main-stat-num col">${allEndpointsNumber}</div>
					<div class="main-stat-icon col">🌩️</div>
				</div>
			</div>
		</div>

		<div class="col-md-auto stat-container card rounded-0 border-0">
			<div class="card-body">
				<div class="row">
					<div class="main-stat-desc card-title col">Available Areas</div>
				</div>
				<div class="row">
					<div class="main-stat-num col">${areaNumber}</div>
					<div class="main-stat-icon col">🗺️️</div>
				</div>
			</div>
		</div>

		<div class="col-md-auto stat-container card rounded-0 border-0">
			<div class="card-body">
				<div class="row">
					<div class="main-stat-desc card-title col">Avg. Coverage per Area</div>
				</div>
				<div class="row">
					<div class="main-stat-num col">${averageCoveragePercentage}%</div>
					<div class="main-stat-icon col">📈</div>
				</div>
			</div>
		</div>

	</div>

		<div id="charts" class="row">

            <div class="col-md-auto" style="padding-right: 0px; padding-left: 0px">
                <div class="col-md-auto stat-container card rounded-0 border-0">
                    <div class="card-body">
                        <h4 class="card-title">Area-wise Endpoint Coverages</h4>
                        <div id="chart_div" style="width: 830px; height: ${barHeight?c}px;">Endpoint List</div>
                    </div>
                </div>
            </div>

            <div class="col-md-auto" style="padding-right: 0px; padding-left: 0px">
                <div class="col-md-auto stat-container card rounded-0 border-0">
                    <div class="card-body">
                        <h4 class="card-title">Endpoint Coverage</h4>
                        <div id="donutchart" >
                            <div id="piechart"></div>
                            <div id="labelOverlay">
                                <p class="used-size">${coveragePercent?string["##0.0"]}%</p>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-md-auto stat-container card rounded-0 border-0">
                    <div class="card-body">
                        <h4 class="card-title">Area Coverage</h4>
                        <div id="donutchart" >
                            <div id="areaPieChart"></div>
                            <div id="labelOverlay">
                                <p class="used-size">${areaCoveragePercent?string["##0.0"]}%</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

    	</div>

<!-- Endpoint list page starts here -->

<ul id="table" class="responsive-table" style="row padding-inline-start: 0px; padding-left: 0px; margin-top: 0px;">
    <#list endpointCoverage?keys as area>
        <h4 class="area-title">${area}</h4>
        <#list endpointCoverage[area] as endpoint>
            <#if endpoint.testCases?size gt 0>
                <#assign lineClass = 'table-row-success'>
                <#assign color = 'passed'>
            <#elseif endpoint.ignored>
                <#assign lineClass = 'table-row-ignore'>
                <#assign color = 'ignored'>
            <#else>
                <#assign lineClass = 'table-row-danger'>
                <#assign color = 'failed'>
            </#if>

            <li id="${area}-endpoint-${endpoint?index}" onclick="openEndpointRow('${area}-detail${endpoint?index}', '${area}-endpoint-${endpoint?index}')" class=${lineClass}>
                <div class="col col-2 col-2-${endpoint.method}">${endpoint.method}</div>
                <div class="col col-3">${endpoint.endpoint}</div>
                <div class="col col-4 ${color}"># of tests ${endpoint.testCases?size}</div>
            </li>
            <#if endpoint.testCases?size gt 0>
                <li id="${area}-detail${endpoint?index}" style="display:none" class="${lineClass} detail-content">
                    <h4 class="test-case-list-title">Related Test Cases</h4>
                    <#list endpoint.testCases as test>
                    <#if test.getStatus() == 1>
                        <#assign status = '<div class="col col-4 passed test-case-list">PASSED</div>'>
                    <#elseif test.getStatus() == 2>
                        <#assign status = '<div class="col col-4 failed test-case-list">FAILED</div>'>
                    <#elseif test.getStatus() == 3>
                        <#assign status = '<div class="col col-4 skipped test-case-list">SKIPPED</div>'>
                    </#if>
                        <div style="display:flex">
                            <div class="col col-test-case-name test-case-list" data-label="Tests">${test.getName()}</div>
                            ${status}
                        </div>
                    </#list>
                </li>
            </#if>
        </#list>
    </#list>
</ul>

<!-- Endpoint list page ends here -->

</div>

</body>
</html>
