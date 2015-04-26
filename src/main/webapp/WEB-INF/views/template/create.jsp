<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

	<h1 class="page-header">
		<i class="fa fa-cube"></i> <spring:message code="template.create.page.title" />
	</h1>
	<div>
		<form:form class="form-horizontal" action="${pageContext.request.contextPath}/template/create" commandName="template" method="POST">
			<div class="form-group">
				<form:label class="control-label col-sm-2" path="name">
					<spring:message code="template.label.name" />:</form:label>
				<div class="col-sm-5">
					<form:input class="form-control" path="name" size="20" />
				    <form:errors path="name" cssClass="help-inline text-danger" element="div" />
				</div>
			</div>
			<div class="form-group">
				<form:label for="filePath" class="control-label col-sm-2" path="path">
					<spring:message code="template.label.path" />:</form:label>
				<div class="col-sm-5">
					<div class="input-group">
						<form:input class="form-control"
							onkeydown="javascript:reCompute()"
							onchange="javascript:reCompute()"
							onclick="javascript:reCompute()" id="filePath" path="path"
							size="20" />
						<span class="input-group-btn"> <button
								class="btn btn-default" id="compute" type="button">Compute MD5 Sum</button></span>
					</div>
				    <form:errors path="path" cssClass="help-inline text-danger" element="div" />
				</div>
			</div>
			<div class="form-group">
				<form:label for="result" class="control-label col-sm-2" path="md5Sum">
					<spring:message code="template.label.md5Sum" />:</form:label>
				<div class=" col-sm-5">
					<form:input class="form-control" id="result" path="md5Sum"
						readonly="true" />
				    <form:errors path="md5Sum" cssClass="help-inline text-danger" element="div" />
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<input id="send" class="btn btn-primary" type="submit" disabled
						value="<spring:message code="template.create.page.submit.label"/>" />
				</div>
			</div>
		</form:form>
	</div>
<script>var ctx = "${pageContext.request.contextPath}"</script>
