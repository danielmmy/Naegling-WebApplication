<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

	<h1 class="page-header">
		<i class="fa fa-wrench"></i> <spring:message code="virtualMachineHost.edit.page.title" />
	</h1>
	<div>
		<form:form class="form-horizontal" action="${pageContext.request.contextPath}/virtualMachineHost/edit"	commandName="virtualMachineHost" method="POST">
			<form:hidden path="id" />
			<div class="form-group">
				<form:label class="control-label col-sm-2" path="hostName">
					<spring:message code="virtualMachineHost.label.hostName" />:</form:label>
                <div class="col-sm-5">
    				<form:input class="form-control" path="hostName" size="20" />
    				<form:errors path="hostName" cssClass="help-inline text-danger" element="div" />
                </div>
			</div>
			<div class="form-group">
				<form:label class="control-label col-sm-2" path="ip">
					<spring:message code="virtualMachineHost.label.ip" />:</form:label>
                <div class="col-sm-5">
    				<form:input class="form-control" path="ip" size="20" />
    				<form:errors path="ip" cssClass="help-inline text-danger" element="div" />
                </div>
			</div>
			<div class="form-group">
				<form:label class="control-label col-sm-2" path="naeglingPort">
					<spring:message code="virtualMachineHost.label.naeglingPort" />:</form:label>
                <div class="col-sm-5">
    				<form:input class="form-control" path="naeglingPort" size="20" />
    				<form:errors path="naeglingPort" cssClass="help-inline text-danger" element="div" />
                </div>
			</div>
			<div class="form-group">
				<form:label class="control-label col-sm-2" path="hypervisor">
					<spring:message code="virtualMachineHost.label.hypervisor" />:</form:label>
                <div class="col-sm-5">
		    		<form:input class="form-control" path="hypervisor" size="20" />
	    			<form:errors path="hypervisor" cssClass="help-inline text-danger" element="div" />
                </div>
			</div>
			<div class="form-group">
				<form:label class="control-label col-sm-2" path="bridgeNetworkInterface">
					<spring:message
						code="virtualMachineHost.label.bridgeNetworkInterface" />:</form:label>
                <div class="col-sm-5">
			    	<form:input class="form-control" path="bridgeNetworkInterface" size="20" />
				    <form:errors path="bridgeNetworkInterface" cssClass="help-inline text-danger"
					    element="div" />
                </div>
			</div>
			<div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
    				<input class="btn btn-primary" type="submit"
    					value="<spring:message code="virtualMachineHost.create.page.submit.label"/>" />
                </div>
			</div>
		</form:form>
	</div>
