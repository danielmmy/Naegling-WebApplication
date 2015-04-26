<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

	<h1 class="page-header">
		<i class="fa fa-wrench"></i> <spring:message code="cluster.edit.page.title" />
	</h1>
	<div>
		<form:form class="form-horizontal" action="${pageContext.request.contextPath}/cluster/edit"	commandName="cluster" method="POST">
			<form:hidden path="id" />
			<div class="form-group">
				<form:label class="control-label col-sm-2" path="name">
					<spring:message code="cluster.label.name" />:</form:label>
                <div class="col-sm-5">
    				<form:input class="form-control" path="name" size="20" />
    				<form:errors path="name" cssClass="help-inline text-danger" element="div" />
                </div>
			</div>
		    <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
    				<input type="submit" class="btn btn-primary"
	    				value="<spring:message code="virtualMachineHost.create.page.submit.label"/>" />
                </div>
			</div>
		</form:form>
	</div>
