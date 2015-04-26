<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h1 class="page-header">
    <i class="fa fa-wrench"></i> <spring:message code="node.edit.page.title"/>
</h1>

<div>
    <form:form class="form-horizontal" action="${flowExecutionUrl}" modelAttribute="editNodeForm" method="POST">
        <div class="form-group">
			<form:label class="control-label col-sm-2" path="virtualNode.domain">
				<spring:message code="node.label.domain" />:</form:label>
            <div class="col-sm-5">
    			<form:input class="form-control" path="virtualNode.domain"  size="20" />
	    		<form:errors path="virtualNode.domain" cssClass="help-inline text-danger" element="div" />
            </div>
		</div>
		<div class="form-group">
			<form:label class="control-label col-sm-2" path="virtualNode.uuid">
				<spring:message code="node.label.uuid" />:</form:label>
            <div class="col-sm-5">
                <div class="input-group">
        			<form:input class="form-control" path="virtualNode.uuid" size="33" id="uuid" />
                    <span class="input-group-btn">
            			<button class="btn btn-default" id="genUuid" type="button">Generate</button>
                    </span>
                </div>
    			<form:errors path="virtualNode.uuid" cssClass="help-inline text-danger" element="div" />
            </div>
		</div>
		<div class="form-group">
			<form:label class="control-label col-sm-2" path="virtualNode.macs[0]">
				<spring:message code="node.label.mac" />:</form:label>
            <div class="col-sm-5">
                <div class="input-group">
        			<form:input class="form-control" path="virtualNode.macs[0]" size="20" id="mac"/>
                    <span class="input-group-btn">
            			<button class="btn btn-default" id="genMac" type="button">Generate</button>
                    </span>
                </div>
	    		<form:errors path="virtualNode.macs[0]" cssClass="help-inline text-danger"
    				element="div" />
            </div>
		</div>
		<div class="form-group">
			<form:label class="control-label col-sm-2" path="virtualNode.ramMemory">
				<spring:message code="node.label.ramMemory" />:</form:label>
            <div class="col-sm-5">
    			<form:input class="form-control" path="virtualNode.ramMemory" size="20" />
    			<form:errors path="virtualNode.ramMemory" cssClass="help-inline text-danger" element="div" />
            </div>
		</div>
		<div class="form-group">
			<form:label class="control-label col-sm-2" path="virtualNode.cpuQuantity">
				<spring:message code="node.label.cpuQuantity" />:</form:label>
            <div class="col-sm-5">
    			<form:input class="form-control" path="virtualNode.cpuQuantity" size="20" />
    			<form:errors path="virtualNode.cpuQuantity" cssClass="help-inline text-danger" element="div" />
            </div>
		</div>
		<div class="form-group">
			<form:label class="control-label col-sm-2" path="virtualNode.graphicalAccessPort">
				<spring:message code="node.label.graphicalAccessPort" />:</form:label>
            <div class="col-sm-5">
    			<form:input class="form-control" path="virtualNode.graphicalAccessPort" size="20" />
    			<form:errors path="virtualNode.graphicalAccessPort" cssClass="help-inline text-danger"
    				element="div" />
            </div>
		</div>
		<div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
    			<button class="btn btn-primary" type="submit" id="confirm" name="_eventId_confirm"><spring:message code="button.confirm"/></button>
    			<button class="btn btn-default" type="submit" id="cancel" name="_eventId_cancel"><spring:message code="button.cancel"/></button>
            </div>
		</div>

    </form:form>
</div>
<script>var ctx = "${pageContext.request.contextPath}"</script>
