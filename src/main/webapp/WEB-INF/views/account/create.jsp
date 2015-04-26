<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

    <h1 class="page-header">
        <i class="fa fa-user"></i> <spring:message code="account.create.page.title"/>
    </h1>
    <div>
        <form:form class="form-horizontal" action="${pageContext.request.contextPath}/account/create" commandName="createAccount" method="POST">
            <div class="form-group">
                <form:label class="control-label col-sm-2" path="firstName"><spring:message code="account.label.firstName"/>:</form:label>
                <div class="col-sm-5">
                    <form:input path="firstName" size="20" class="form-control"/>
                    <form:errors path="firstName" cssClass="help-inline text-danger" element="div"/>
                </div>
            </div>
            <div class="form-group">
                <form:label class="control-label col-sm-2" path="lastName"><spring:message code="account.label.lastName"/>:</form:label>
                <div class="col-sm-5">
                    <form:input path="lastName" size="20" class="form-control"/>
                    <form:errors path="lastName" cssClass="help-inline text-danger" element="div"/>
                </div>
            </div>
            <div class="form-group">
                <form:label class="control-label col-sm-2" path="userName"><spring:message code="account.label.userName"/>:</form:label>
                <div class="col-sm-5">
                    <form:input class="form-control" path="userName" size="20"/>
                    <form:errors path="userName" cssClass="help-inline text-danger" element="div"/>
                </div>
            </div>
            <div class="form-group">
                <form:label class="control-label col-sm-2" path="email"><spring:message code="account.label.email"/>:</form:label>
                <div class="col-sm-5">
                    <form:input class="form-control" path="email" size="20"/>
                    <form:errors path="email" cssClass="help-inline text-danger" element="div"/>
                </div>
            </div>
            <div class="form-group">
                <form:label class="control-label col-sm-2" path="passwd"><spring:message code="account.label.passwd"/>:</form:label>
                <div class="col-sm-5">
                    <form:password class="form-control" path="passwd" size="20"/>
                    <form:errors path="passwd" cssClass="help-inline text-danger" element="div"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="submit" class="btn btn-primary" value="<spring:message code="account.create.page.submit.label"/>"/>
                </div>
            </div>
        </form:form>
    </div>
