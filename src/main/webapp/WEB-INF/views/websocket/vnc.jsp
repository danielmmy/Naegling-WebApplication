<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<title>WebSocket</title>
<link rel="stylesheet" href="<c:url value="/static/css/bootstrap.css"/>" type="text/css" />
<link rel="stylesheet" href="<c:url value="/static/font-awesome-4.1.0/css/font-awesome.css"/>" type="text/css" />
</head>
<body style="background-color:#999">
<input type="hidden" id="nodeId" value="${nodeId}"></input>
<div style="position: absolute;"><i class="fa fa-cog fa-spin fa-5x"></i></div>
<canvas style='cursor:none' id='display'></canvas>

<script src=" <c:url value="/static/js/jquery.js"/>"></script>
<script src="<c:url value="/static/js/bootstrap.js"/>"></script>
<script src="<c:url value="/static/js/VNC/eventHandlers.js"/>"></script>
<script src="<c:url value="/static/js/VNC/vnc.js"/>"></script>
<script>

var ctx = "${pageContext.request.contextPath}";
url = "wss://" + window.location.host + ctx + "/websocket/open";
onload = function() {
    vnc=new Vnc(url,"display");
}
	
</script>
</body>