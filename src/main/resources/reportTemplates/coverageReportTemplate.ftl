<html>

<head>
  <title>${appName} API coverage</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
</head>

<style>
body {
  font-family: 'lato', sans-serif;
  font-size: 12px;
  line-height: 1.42857143;
  color: #333;
  text-align: -webkit-center;
  background-color: #fff
}

h2 {
  font-size: 26px;
  margin-top: 20px;
  margin-bottom: 0px;
  text-align: center;
}
h2 small {
  font-size: 0.5em;
}
h3 {
  font-size: 13px;
  margin: 0px 0;
  text-align: center;
}
.mid-text-style {
  font-size: 13px;
  margin-top: 20px;
  text-align: center;
}
.container {
  max-width: 1000px;
  margin-left: auto;
  margin-right: auto;
  padding-left: 10px;
  padding-right: 10px;
}
.footer {
  margin-top: 35px;
  text-align: center;
}
.area-title {
  display: flex;
  align-items: center;
  padding: 25px 20px 10px 10px;
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
.responsive-table .detail-content {
  border-radius: 0px;
  border-bottom-left-radius: 3px;
  border-bottom-right-radius: 3px;
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
</style>

<body>

<#assign missedEndpointNum = (allEndpointsNumber - coveredEndpointsNumber)>

<script type="text/javascript">
    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawGlobalCoverageChart);
    google.charts.setOnLoadCallback(drawAreaWiseBarChart);

    function drawGlobalCoverageChart() {
      var data = google.visualization.arrayToDataTable([
        ['Coverage', 'Percentage'],
        ['Covered', ${coveredEndpointsNumber}],
        ['Missed', ${missedEndpointNum}]
      ]);

      var options = {
        'titlePosition': 'none',
        'width': 550,
        'height': 400,
        'slices': { 1: {offset: 0.2} },
        'colors': ['#49cc90', '#f93e3e']};
      var chart = new google.visualization.PieChart(document.getElementById('piechart'));
      chart.draw(data, options);
    }

    function drawAreaWiseBarChart() {
      var data = google.visualization.arrayToDataTable([
        ['Area', 'Covered', 'Missing', 'Average'],
        <#list endpointCoverage?keys as key>
          ['${key}', ${areaWiseEndpoints[key].getCoveredEndpoints()}, ${areaWiseEndpoints[key].getUncoveredEndpointNum()}, (${coveredEndpointsNumber}/${areaNumber})], ${'\n'}
        </#list>
      ]);

      var options = {
        titlePosition: 'none',
        vAxis: {title: 'Endpoints'},
        hAxis: {title: 'Areas'},
        animation: {startup: true, easing: 'inAndOut', duration: 700},
        seriesType: 'bars',
        colors: ['#49cc90', '#f93e3e', '#d35ebe'],
        series: {2: {type: 'line'}}
      };

      var chart = new google.visualization.ComboChart(document.getElementById('chart_div'));
        chart.draw(data, options);
    }

    function myFunction(detailId, endpointId) {
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
</script>

<div class="container">

<h2>${appName} <small>API Test Coverage</small></h2>
<h3>${currentDateAndTime}</h3>

<h3 class="mid-text-style">Global Endpoint Coverage</h3>
<div id="piechart"></div>

<h3>Area-wise Endpoint Coverages</h3>
<div id="chart_div" style="width: 900px; height: 500px;">Endpoint List</div>

<ul class="responsive-table">
    <#list endpointCoverage?keys as area>
        <h4 class="area-title">${area}</h4>
        <#list endpointCoverage[area] as endpoint>
            <#if endpoint.testCases?size gt 0>
              <#assign lineClass = 'table-row-success'>
              <#assign color = 'passed'>
            <#else>
              <#assign lineClass = 'table-row-danger'>
              <#assign color = 'failed'>
            </#if>

            <li id="${area}-endpoint-${endpoint?index}" onclick="myFunction('${area}-detail${endpoint?index}', '${area}-endpoint-${endpoint?index}')" class=${lineClass}>
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

<div class="footer">${footer}</div>

</div>

</body>
</html>
