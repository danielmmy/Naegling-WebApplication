<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<h1 class="page-header">
		<i class="fa fa-sitemap"></i> <spring:message code="cluster.create.page.title" />
	</h1>
	<div>
		<form:form class="form-horizontal" action="${pageContext.request.contextPath}/cluster/create" commandName="cluster" method="POST">
            <div class="form-group">
                <form:label class="control-label col-sm-2" path="name"><spring:message code="cluster.label.name"/>:</form:label>
                <div class="col-sm-5">
                    <form:input class="form-control" path="name" size="20"/>
                    <form:errors path="name" cssClass="help-inline" element="div"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input class="btn btn-primary" type="submit" value="<spring:message code="cluster.create.page.submit.label"/>"/>
                </div>
            </div>
        </form:form>
	</div>
