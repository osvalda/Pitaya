<html>

<head>
  <title>${appName} API coverage</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
</head>

<#assign missedEndpointNum = (allEndpointsNumber - coveredEndpointsNumber - ignoredEndpointNumber)>
<#assign coveragePercent = ((coveredEndpointsNumber / allEndpointsNumber) * 100)>
<#if coveragePercent == 100>
   <#assign left = '224px'>
<#elseif coveragePercent gt 10>
   <#assign left = '230px'>
<#else>
   <#assign left = '237px'>
</#if>

<style>
body {
  font-family: 'lato', sans-serif;
  font-size: 12px;
  line-height: 1.42857143;
  color: #333;
  text-align: -webkit-left;
  background-color: #efefef
}

h2 {
  font-size: 26px;
  margin-top: 20px;
  margin-bottom: 0px;
  text-align: left;
}
h2 small {
  font-size: 0.5em;
}
h3 {
  font-size: 13px;
  margin: 0px 0;
  text-align: left;
}
.container {
  max-width: 1000px;
  padding-left: 90px;
}
.footer {
  margin-top: 35px;
  text-align: left;
}
.area-title {
  display: flex;
  align-items: center;
  padding: 25px 20px 10px 0px;
  font-size: 24px;
  max-width: 700;
  margin: 0 0 5px;
  font-family: sans-serif;
  color: #3b4151;
}
.test-case-list-title {
  display: flex;
  align-items: center;
  padding: 10px 20px 10px 10px;
  font-size: 14px;
  max-width: 700;
  margin: 0 0 5px;
  font-family: sans-serif;
  border-bottom: 1px solid rgba(59,65,81,.3);
  color: #3b4151;
}
.responsive-table li {
  border-radius: 3px;
  padding: 10px 10px;
  max-width: 700;
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
  box-sizing: border-box;
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
  flex-basis: 100%;
  text-align: right;
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

#donutchart,
#piechart {
  width: 550px;
  height: 400px;
  font-family: Arial;
}

#donutchart {
    position: relative;
}

#labelOverlay {
    position: absolute;
    top: 190px;
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

/* COVERAGE TABLE */

.coverage-table {
  border: 1px solid black;
  background-color: white;
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

    function drawGlobalCoverageChart() {
      var data = google.visualization.arrayToDataTable([
        ['Coverage', 'Percentage'],
        ['Covered', ${coveredEndpointsNumber}],
        ['Missed', ${missedEndpointNum}],
        ['Ignored', ${ignoredEndpointNumber}]
      ]);

      var options = {
        'titlePosition': 'none',
        'width': 550,
        'height': 400,
        'pieHole': 0.75,
        'legend': {
        	position: 'none'
        },
        'pieSliceText': 'none',
        'colors': ['#7DCEA0', '#D98880', '#80D1D9']};
      var chart = new google.visualization.PieChart(document.getElementById('piechart'));
      chart.draw(data, options);
    }

    function drawAreaWiseBarChart() {
      var data = google.visualization.arrayToDataTable([
        ['Area', 'Covered', 'Missing', 'Ignored'],
        <#list endpointCoverage?keys as key>
          ['${key}', ${areaWiseEndpoints[key].getCoveredEndpoints()}, ${areaWiseEndpoints[key].getUncoveredEndpointNum()}, ${areaWiseEndpoints[key].getIgnoredEndpoints()}], ${'\n'}
        </#list>
      ]);

      var options = {
        titlePosition: 'none',
        vAxis: {title: 'Endpoints'},
        hAxis: {title: 'Areas'},
        animation: {startup: true, easing: 'inAndOut', duration: 700},
        seriesType: 'bars',
        colors: ['7DCEA0', '#D98880', '#80D1D9'],
        isStacked: true
      };

      var chart = new google.visualization.ComboChart(document.getElementById('chart_div'));
        chart.draw(data, options);
    }

    function openEndpointRow(detailId, endpointId) {
      var testCaseList = document.getElementById(detailId);
      var endpoint = document.getElementById(endpointId);
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
      var charts = document.getElementById("charts");
      var table = document.getElementById("table");
      if (menuItem == "table") {
          if (table != null && charts != null) {
            charts.style.display = "none";
            table.style.display = "block";
            document.getElementById("tableItem").style.color = '#f1f1f1';
            document.getElementById("homeItem").style.color = '#818181';
          }
      } else {
          if (table != null && charts != null) {
            charts.style.display = "block";
            table.style.display = "none";
            document.getElementById("tableItem").style.color = '#818181';
            document.getElementById("homeItem").style.color = '#f1f1f1';
          }
      }
    }
</script>

<body onload="menuBarClick('charts')"">

<div class="sidenav">
  <div>Pitaya</div>
  <a id="homeItem" onclick="menuBarClick('charts')">&#8962</a>
  <a id="tableItem" onclick="menuBarClick('table')">&#128462;</a>
</div>

<div class="container">

<h2>${appName} <small>API Test Coverage</small></h2>
<h3>${currentDateAndTime}</h3>

<div id="charts">
<table>
<tr>
<td>

<h4 class="area-title">Global Endpoint Coverage</h4>
<div id="donutchart" class="col-xs-12 col-sm-6 col-md-4">
  <div id="piechart"></div>
  <div id="labelOverlay">
    <p class="used-size">${coveragePercent?string["##0.0"]}%</p>
  </div>
</div>

</td>

<td>

<table style="background: white">
  <tr>
    <td>Covered</td>
    <td>Missing</td>
    <td>Ignored</td>
  </tr>
  <tr>
    <td>${coveredEndpointsNumber}</td>
    <td>${missedEndpointNum}</td>
    <td>${ignoredEndpointNumber}</td>
  </tr>
</table>

</td>
</tr>
</table>

<h4 class="area-title">Area-wise Endpoint Coverages</h4>
<#if barChartHeight?has_content>
    <div id="chart_div" style="width:  ${barChartWidth}px; height:  ${barChartHeight}px;">Endpoint List</div>
<#else>
    <div id="chart_div" style="width: 900px; height: 500px;">Endpoint List</div>
</#if>
</div>

<ul id="table" class="responsive-table" style="padding-inline-start: 0px; padding-left: 0px; margin-top: 0px;">
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

</div>

</body>
</html>
