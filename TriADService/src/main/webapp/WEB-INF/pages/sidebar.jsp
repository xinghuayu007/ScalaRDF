<%--
  Created by IntelliJ IDEA.
  User: zhaiyeact
  Date: 2015/10/16
  Time: 15:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<!-- Sidebar -->
<div id="sidebar">

  <!-- Box -->
  <div class="box">

    <!-- Box Head -->
    <div class="box-head">
      <h2>Management</h2>
    </div>
    <!-- End Box Head-->

    <div class="box-content">
      <a href="#" class="add-button"><span>Add new Article</span></a>
      <div class="cl">&nbsp;</div>

      <p class="select-all"><input type="checkbox" class="checkbox" /><label>select all</label></p>
      <p><a href="#">Delete Selected</a></p>

      <!-- Sort -->
      <div class="sort">
        <label>Sort by</label>
        <select class="field">
          <option value="">Title</option>
        </select>
        <select class="field">
          <option value="">Date</option>
        </select>
        <select class="field">
          <option value="">Author</option>
        </select>
      </div>
      <!-- End Sort -->

    </div>
  </div>
  <!-- End Box -->
</div>
<!-- End Sidebar -->
</body>
</html>
