<%--
  Created by IntelliJ IDEA.
  User: zhaiyeact
  Date: 2015/10/2
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.triad.tools.ErrorCode" %>
<%@ page import="com.triad.tools.LUBMQueries" %>
<%@ page session="true"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>TriAD Query</title>
    <link href="css/style.css" rel="stylesheet"/>
    <script type="text/javascript" src="js/jquery-2.1.4.js"></script>
    <script type="text/javascript" src="js/jquery.json.min.js"></script>
    <script type="text/javascript">
        $().ready(function(){
            ${selectHost};
            ${resultPage};
        });
        var resultArray = eval(${resultJson});
        var currentPage = 0;
        var LastPage = 0;
        function addMasterAddress(host,name){
            var selectObj = document.getElementById("mAddr");
            var newOption = new Option(host+" " + name, host);
            selectObj.options.add(newOption);
        }
        function lubmOnChange(){
            var selectObj = document.getElementById("lubmSelect");
            var option = selectObj.options[selectObj.selectedIndex].value;
            $.ajax({
                type: "get",
                dataType: "text",
                url: "/TriadService/lubmQuery",
                data:{option:option},
                success: function(data){
                    var textObj = document.getElementById("queryTextArea");
                    textObj.value = data;
                },
                error: function(){
                    alert("error");
                }
            });
        }
        function setResultPage(pageCount){
            LastPage = pageCount-1;
            if(parseInt(pageCount) == 0 ){
                return;
            }
            var divObj = document.getElementById("resultDiv");
            var div1 = document.createElement("div");
            div1.className = "pagging";
            var leftDiv = document.createElement("div");
            leftDiv.className = "left";
            leftDiv.innerText = "Showing 1-5 of " + pageCount;

            var newDiv = document.createElement("div");
            newDiv.id = "resultBtnDiv";
            newDiv.className = "right";
            var pre = document.createElement("a");
            pre.onclick = function(){
                preBtnOnclick();
            }
            pre.innerText = "Previous";
            newDiv.appendChild(pre);
            if(parseInt(pageCount)<=5) {
                for (var i = 0; i < parseInt(pageCount); i++) {
                    var a = document.createElement("a");
                    a.paramI = i;
                    a.onclick = function(){
                        resultPageOnclick(this.paramI);
                    }
                    a.innerText = (i+1).toString();
                    newDiv.appendChild(a);
                }
            }
            else{
                for(var i = 0;i<2;i++) {
                    var a = document.createElement("a");
                    a.paramI = i;
                    a.onclick = function() {
                        resultPageOnclick(this.paramI);
                    }
                    a.innerText = (i+1).toString();
                    newDiv.appendChild(a);
                }
                var span =document.createElement("span");
                span.innerText = "...";
                newDiv.appendChild(span);
            }
            var next = document.createElement("a");
            next.onclick = function(){
                nextBtnOnclick();
            }
            next.innerText = "Next";
            newDiv.appendChild(next);

            var last = document.createElement("a");
            last.onclick = function(){
                resultPageOnclick(LastPage);
            }
            last.innerText = "Last";
            newDiv.appendChild(last);
            div1.appendChild(leftDiv);
            div1.appendChild(newDiv);
            divObj.appendChild(div1);
        }
        function resultPageOnclick(page){
            currentPage = page;
            var result = document.getElementById("resultTextArea");
            result.value = resultArray[page];
        }
        function preBtnOnclick(){
            if(currentPage == 0){
                return;
            }
            else{
                currentPage-=1;
                var result = document.getElementById("resultTextArea");
                result.value = resultArray[currentPage];
            }
        }
        function nextBtnOnclick(){
            if(currentPage == LastPage){
                return;
            }
            else{
                currentPage+=1;
                var result = document.getElementById("resultTextArea");
                result.value = resultArray[currentPage];
            }
        }

    </script>
</head>
<body>
<div id="header">
    <div class="shell">
        <!-- Logo + Top Nav -->
        <div id="top">
            <h1><a href="#">TriAD</a></h1>
        </div>
        <!-- End Logo + Top Nav -->

        <!-- Main Nav -->
        <div id="navigation">
            <ul>
                <li><a href="/TriadService/cluster"><span>Cluster</span></a></li>
                <li><a href="/TriadService/query" class="active"><span>Query Task</span></a></li>
            </ul>
        </div>
        <!-- End Main Nav -->
    </div>
</div>
<div id="container">
    <div class="shell">
        <div id="main">
            <div class="cl">&nbsp;</div>
            <div id="content">
                <div class="box">
                    <div class="box-head">
                        <h2 class="left">Query Execution</h2>
                    </div>
                    <div class="form">
                        <p>
                            <label>Local Query <span>(Optional)</span></label>
                            <form action="/TriadService/uploadQuery" method="post" enctype="multipart/form-data">
                                <input type="file" name="file"/>
                                <input type="submit" class="button" value="upload"/>
                            </form>
                        </p>
                    </div>
                    <form:form action="/TriadService/queryexecute" method="post" commandName="query">
                        <!-- Form -->
                        <div class="form">
                            <p>
                                <label>Master <span>(Required Field)</span></label>
                                <select class="field" id="mAddr" name="master"></select>
                            </p>
                            <p>
                                <label>SPARQL Query <span>(Required Field)</span></label>
                                <textarea class="field size1" rows="10" cols="30" name="request" id="queryTextArea">${queryString}</textarea>
                            </p>
                            <p>
                                <label>LUBM Query <span>(Optional)</span></label>
                                <select class="field" id="lubmSelect" name="lubmQuery" onchange="lubmOnChange()">
                                    <option value="None">None</option>
                                    <option value="Q1">Q1</option>
                                    <option value="Q2">Q2</option>
                                    <option value="Q3">Q3</option>
                                    <option value="Q4">Q4</option>
                                    <option value="Q5">Q5</option>
                                    <option value="Q6">Q6</option>
                                    <option value="Q7">Q7</option>
                                </select>
                            </p>
                        </div>
                        <!-- End Form -->
                        <!-- Form Buttons -->
                        <div class="buttons">
                            <input type="submit" class="button" value="submit" />
                        </div>
                        <!-- End Form Buttons -->
                    </form:form>
                    <!-- Result div-->
                    <div class="form" id="resultDiv">
                        <p>
                            <label>Result</label>
                            <textarea class="field size1" rows="10" cols="30" name="queryResult" contenteditable="false" id="resultTextArea">${queryResult}</textarea>
                        </p>
                    </div>
                </div>
            </div>
            <div class="cl">&nbsp;</div>
        </div>
    </div>
</div>
<!-- Footer -->
<div id="footer">
    <div class="shell">
        <span class="left">&copy; provided by Zhaiye</span>
    </div>
</div>
<!-- End Footer -->
<%
    if(session.getAttribute("errorCode")!=null) {
        if (session.getAttribute("errorCode").equals(ErrorCode.EMPTY_QUERY.getCode())) {
            response.getWriter().write("<script> alert(\"Query can not be null!\")</script>");
            session.removeAttribute("errorCode");
        } else if (session.getAttribute("errorCode").equals(ErrorCode.SOCKET_ERROR.getCode())) {
            response.getWriter().write("<script> alert(\"Can not connect to Triad Master!\")</script>");
            session.removeAttribute("errorCode");
        } else if (session.getAttribute("errorCode").equals(ErrorCode.SHUTTING_DOWN.getCode())) {
            response.getWriter().write("<script> alert(\"The Master selected is shutting down!\")</script>");
            session.removeAttribute("errorCode");
        }else if (session.getAttribute("errorCode").equals(ErrorCode.NO_MASTER.getCode())) {
            response.getWriter().write("<script> alert(\"No master selected!\")</script>");
            session.removeAttribute("errorCode");
        }
    }
%>
</body>
</html>

