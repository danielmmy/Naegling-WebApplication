<!DOCTYPE HTML>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<html>
    <head>
    <title>N&aelig;gling</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<c:url value="/static/css/bootstrap.css"/>" type="text/css" />
    <!--DASHBOARD CSS -->
    <link rel="stylesheet" href="<c:url value="/static/css/dashboard.css"/>" type="text/css" />
    <link rel="stylesheet" href="<c:url value="/static/font-awesome-4.1.0/css/font-awesome.css"/>" type="text/css" />

    <!--[if Lt IE9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
     </head>
    <body>
        <tiles:insertAttribute name="header" />
        <div id="page" class="container-fluid">
          <div class="row">
            <!-- col-sm-3 col-md-2 sidebar -->
            <tiles:insertAttribute name="navigation_bar" />
            <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
                <tiles:insertAttribute name="body" />
            </div>
          </div>
        </div>
        <!-- <tiles:insertAttribute name="footer" /> -->

        <script src=" <c:url value="/static/js/jquery.js"/>"></script>
    	<script src="<c:url value="/static/js/bootstrap.js"/>"></script>
    	<script src="<c:url value="/static/js/bootbox.js"/>"></script>
    	<script src="<c:url value="/static/js/functions.js"/>"></script>
    </body>
</html>

