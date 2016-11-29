<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: zhaiyeact
  Date: 2015/10/16
  Time: 10:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html >
<head>
  <meta http-equiv="Content-type" content="text/html; charset=utf-8" />
  <title>ScalaRDF Service</title>
  <link href="css/style.css" rel="stylesheet"/>
    <script type="text/javascript" src="js/jquery-2.1.4.js"></script>
    <script type="text/javascript">
        $().ready(function(){
            ${mRecordData}
        });
      function addRecordData(eleId, status, ip, hostName, port,role){
          var tableObj = document.getElementById(eleId);
          var row = tableObj.insertRow();
          var cell = row.insertCell(0);
          var length = tableObj.rows.length;

          if(length%2 == 0){
              row.className = "odd";
          }

          cell.innerHTML = "<h3><a href=\"#\" >"+ip+"</a></h3>";

          cell = row.insertCell(1);
          cell.innerHTML = "<a href=\"#\">"+hostName+"</a>";

          cell = row.insertCell(2);
          cell.innerHTML = status;

          cell = row.insertCell(3);
          cell.innerHTML = role;


      }
    </script>
</head>
<body>
<!-- Header -->
<div id="header">
  <div class="shell">
    <!-- Logo + Top Nav -->
    <div id="top">
      <h1><a href="#">ScalaRDF</a></h1>
    </div>
    <!-- End Logo + Top Nav -->

    <!-- Main Nav -->
    <div id="navigation">
      <ul>
        <li><a href="/TriadService/cluster" class="active"><span>Cluster</span></a></li>
        <li><a href="/TriadService/query"><span>Query Task</span></a></li>
      </ul>
    </div>
    <!-- End Main Nav -->
  </div>
</div>
<!-- End Header -->

<!-- Container -->
<div id="container">
  <div class="shell">
    <div id="main">
      <!-- Content -->
      <div id="content">
        <!-- Box -->
        <div class="box">
          <!-- Box Head -->
          <div class="box-head">
            <h2 class="left">Running Cluster</h2>
          </div>
          <!-- End Box Head -->

          <!-- Table -->
          <div class="table">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" id="clusterTable">
              <tr>
                <th>Ip</th>
                <th>HostName</th>
                <th>Status</th>
                <th>Identity</th>
              </tr>
            </table>

          </div>
          <!-- Table -->

        </div>
        <!-- End Box -->
      </div>
      <!-- End Content -->
    </div>
    <!-- Main -->
    <div class="cl">&nbsp;</div>
  </div>
</div>
<!-- End Container -->

<!-- Footer -->
<div id="footer">
  <div class="shell">
    <span class="left">&copy; provided by Zhaiye</span>
  </div>
</div>
<!-- End Footer -->

</body>
</html>
