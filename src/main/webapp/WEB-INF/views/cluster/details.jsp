<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h1 class="page-header">
    <i class="fa fa-sitemap"></i> ${cluster.name}
   
</h1> <span class="pull-right">
	    <a href="${pageContext.request.contextPath}/node/create/<c:out value="${cluster.id}"/>"
		    class="btn btn-info" title="node.title.addNode"><i
    		class="fa fa-plus"></i></a>
    </span>

<table class="table table-hover">
	<thead>
		<tr>
			<th><spring:message code="node.label.domain" /></th>
			<th><spring:message code="node.label.cpu" /></th>
			<th><spring:message code="node.label.ramMemory" /></th>
			<th><spring:message code="node.label.graphicalAccessPort" /></th>
            <th><spring:message code="table.column.label.actions" /></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${cluster.nodes}" var="node">
			<tr>
				<td><c:out value="${node.domain}" /></td>
				<td><c:out value="${node.cpuQuantity}" /></td>
				<td><c:out value="${node.ramMemory}" /></td>
				<td><c:out value="${node.graphicalAccessPort}" /></td>
				<td>
					<div class="btn-group">
						<c:choose>
							<c:when test="${node.status=='1'}">
								<button
									onclick="return changeStatus(${cluster.id},${node.id},this,'stop')"
									class="btn btn-default">
									<i class="fa fa-power-off"></i>
								</button>
								<a id="openGraphicsNode${node.id}" href="${pageContext.request.contextPath}/websocket/vnc/${node.id}" target="_blank"
									class="btn btn-default">
									<i class="fa fa-desktop"></i>
								</a>
								<a id="openGraphicsNode2${node.id}" href="${pageContext.request.contextPath}/node/novnc/${node.id}"
									class="btn btn-default">
									<i class="fa fa-desktop"></i>
								</a>
								<button id="editNode${node.id}"
									onclick="return editNode(${cluster.id},${node.id})"
									style="display: none" class="btn btn-default"
									title="node.title.edit">
									<i class="fa fa-wrench"></i>
								</button>
								<button id="deleteNode${node.id}"
									onclick="return deleteNode(${cluster.id},${node.id})"
									style="display: none" class="btn btn-default"
									title="node.title.delete">
									<i class="fa fa-trash-o"></i>
								</button>
							</c:when>
							<c:when test="${node.status=='0'}">
								<button
									onclick="return changeStatus(${cluster.id},${node.id},this,'start')"
									value="${node.id}" class="btn btn-default">
									<i class="fa fa-play"></i>
								</button>
								<a  id="openGraphicsNode${node.id}" href="${pageContext.request.contextPath}/websocket/vnc/${node.id}" target="_blank"
									style="display: none" class="btn btn-default" >
									<i class="fa fa-desktop"></i>
								</a>
								<a id="openGraphicsNode2${node.id}" href="${pageContext.request.contextPath}/node/novnc/${node.id}"
									class="btn btn-default">
									<i class="fa fa-desktop"></i>
								</a>
								<button id="editNode${node.id}"
									onclick="return editNode(${cluster.id},${node.id})"
									class="btn btn-default" title="node.title.edit">
									<i class="fa fa-wrench"></i>
								</button>
								<button id="deleteNode${node.id}"
									onclick="return deleteNode(${cluster.id},${node.id})"
									class="btn btn-default" title="node.title.delete">
									<i class="fa fa-trash-o"></i>
								</button>
							</c:when>
							<c:otherwise>
								<button
									onclick="return changeStatus(${cluster.id},${node.id},this,'start')"
									class="btn btn-default">
									<i class="fa fa-play"></i>
								</button>
								<a id="openGraphicsNode${node.id}" href="${pageContext.request.contextPath}/websocket/vnc/${node.id}" target="_blank"
									style="display: none" class="btn btn-default">
									<i class="fa fa-desktop"></i>
								</a>
								<a id="openGraphicsNode2${node.id}" href="${pageContext.request.contextPath}/node/novnc/${node.id}"
									class="btn btn-default">
									<i class="fa fa-desktop"></i>
								</a>
								<button id="editNode${node.id}"
									onclick="return editNode(${cluster.id},${node.id})"
									style="display: none" class="btn btn-default"
									title="node.title.edit">
									<i class="fa fa-wrench"></i>
								</button>
								<button id="deleteNode${node.id}"
									onclick="return deleteNode(${cluster.id},${node.id})"
									style="display: none" class="btn btn-default"
									title="node.title.delete">
									<i class="fa fa-trash-o"></i>
								</button>
							</c:otherwise>
						</c:choose>
					</div>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<form id="editNodeForm"
	action="${pageContext.request.contextPath}/editNode">
	<input id="clusterId" name="clusterId" hidden="true"
		value="${cluster.id}" /> <input id="nodeId" name="nodeId"
		hidden="true" />
</form>
<script type="text/javascript">
var ctx = "${pageContext.request.contextPath}"
function deleteNode(cluster,node){
	var deleting=$.post(ctx+"/node/delete", {
		clusterId : cluster,
		nodeId : node
	
	});
	deleting.done(function (data){
		window.location.reload();
	});
}
</script>
<script type="text/javascript">
var ctx = "${pageContext.request.contextPath}"
function changeStatus(cluster,node,button,action){
	var starting=$.post(ctx+"/node/"+action, {
		clusterId : cluster,
		nodeId : node
	
	});
	button.innerHTML="<i class='fa fa-spin fa-cog' ></i>";
	button.setAttribute("class", "btn btn-default");
	starting.done(function (data) {
		switch(data){
		case "0":
			button.setAttribute("onclick","return changeStatus("+cluster+","+node+",this,'start')");
			$("#openGraphicsNode"+node).hide();
			$("#openGraphicsNode2"+node).hide();
			$("#editNode"+node).show();
			$("#deleteNode"+node).show();
			button.innerHTML="<i class='fa fa-play' ></i>";
			break;
		case "1" :
			button.setAttribute("onclick","return changeStatus("+cluster+","+node+",this,'stop')");
			$("#openGraphicsNode"+node).show();
			$("#openGraphicsNode2"+node).show();
			$("#editNode"+node).hide();
			$("#deleteNode"+node).hide();
			button.innerHTML="<i class='fa fa-power-off' ></i>";
			break;
		default:
			button.setAttribute("onclick","return changeStatus("+cluster+","+node+",this,'start')");
			$("#openGraphicsNode"+node).hide();
			$("#openGraphicsNode2"+node).hide();
			$("#editNode"+node).hide();
			$("#deleteNode"+node).hide();
			button.innerHTML="<i class='fa fa-play' ></i>";
		
		}
		
	})
}
</script>
<script>
	function editNode(clusterId, nodeId){
		$("#nodeId").val(nodeId);
		$("#editNodeForm").submit();
	}
</script>
