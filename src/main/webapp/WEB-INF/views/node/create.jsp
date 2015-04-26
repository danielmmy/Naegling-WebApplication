<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h1 class="page-header">
	<i class="fa fa-square"></i> <spring:message code="node.create.page.title" />
    <small class="text-muted">
        - Cluster ${cluster.name}
    </small>
</h1>
<div>
	<form:form class="form-horizontal"
		action="${pageContext.request.contextPath}/node/create"
		commandName="virtualNode" method="POST">
		<div>
			<input type="hidden" name="clusterId" value="${cluster.id}" />
		</div>
		<div class="form-group">
			<form:label class="control-label col-sm-2" path="domain">
				<spring:message code="node.label.domain" />:</form:label>
            <div class="col-sm-5">
    			<form:input class="form-control" path="domain" size="20" />
    			<form:errors path="domain" cssClass="help-inline text-danger" element="div" />
            </div>
		</div>
		<div class="form-group">
			<form:label class="control-label col-sm-2" path="uuid">
				<spring:message code="node.label.uuid" />:</form:label>
            <div class="col-sm-5">
                <div class="input-group">
        			<form:input class="form-control" path="uuid" size="33" id="uuid" />
                    <span class="input-group-btn">
            			<button type="button" class="btn btn-default" id="genUuid">Generate</button>
                    </span>
                </div>
    			<form:errors path="uuid" cssClass="help-inline text-danger" element="div" />
            </div>
		</div>
		<div class="form-group">
			<form:label for="mac" class="control-label col-sm-2" path="macs[0]">
				<spring:message code="node.label.mac" />:</form:label>
            <div class="col-sm-5">
                <div class="input-group">
        			<form:input class="form-control" path="macs[0]" size="20" id="mac"/>
                    <span class="input-group-btn">
            			<button type="button" class="btn btn-default" id="genMac">Generate</button>
                    </span>
                </div>
       			<form:errors path="macs[0]" cssClass="help-inline text-danger" element="div" />
            </div>
		</div>
		<div class="form-group">
			<form:label class="control-label col-sm-2" path="ramMemory">
				<spring:message code="node.label.ramMemory" />:</form:label>
            <div class="col-sm-5">
    			<form:input class="form-control" path="ramMemory" size="20" />
    			<form:errors path="ramMemory" cssClass="help-inline text-danger" element="div" />
            </div>
		</div>
		<div class="form-group">
			<form:label class="control-label col-sm-2" path="cpuQuantity">
				<spring:message code="node.label.cpuQuantity" />:</form:label>
            <div class="col-sm-5">
    			<form:input class="form-control" path="cpuQuantity" size="20" />
    			<form:errors path="cpuQuantity" cssClass="help-inline text-danger" element="div" />
            </div>
		</div>
		<div class="form-group">
			<form:label class="control-label col-sm-2" path="graphicalAccessPort">
				<spring:message code="node.label.graphicalAccessPort" />:</form:label>
            <div class="col-sm-5">
    			<form:input class="form-control" path="graphicalAccessPort" size="20" />
    			<form:errors path="graphicalAccessPort" cssClass="help-inline text-danger"
    				element="div" />
            </div>
		</div>
		<div class="form-group">
			<form:label class="control-label col-sm-2" path="host">
				<spring:message code="node.label.host" />:</form:label>
            <div class="col-sm-5">
    			<form:select class="form-control" path="host">
    				<c:forEach items="${hosts}" var="host">
    					<option>${host.hostName}</option>
    				</c:forEach>
    			</form:select>
    			<form:errors path="host" cssClass="help-inline text-danger"
    				element="div" />
            </div>
		</div>
		<div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
    			<input type="submit" class="btn btn-primary"
    				value="<spring:message code="node.create.page.submit.label"/>" />
            </div>
		</div>

	</form:form>
</div>
<script>var ctx = "${pageContext.request.contextPath}"</script>
