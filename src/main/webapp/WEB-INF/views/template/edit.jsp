<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h1 class="page-header"><i class="fa fa-wrench"></i> <spring:message code="template.edit.page.title"/></h1>
<div>
    <form:form action="${pageContext.request.contextPath}/template/edit" commandName="template" method="POST" class="form-horizontal">
        <form:hidden path="id"/>
        <div class="form-group">
            <form:label path="name" class="control-label col-sm-2"><spring:message code="template.label.name"/>:</form:label>
            <div class="col-sm-5">
                <form:input path="name" class="form-control" size="20"/>
                <form:errors path="name" cssClass="help-inline text-danger" element="div"/>
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
            <form:label for="result" path="md5Sum" class="control-label col-sm-2"><spring:message code="template.label.md5Sum"/>:</form:label>
            <div class="col-sm-5">
                <form:input class="form-control" path="md5Sum" id="result"
                        readonly="true" />
                <form:errors path="md5Sum" cssClass="help-inline text-danger" element="div"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <input type="submit" id="send" class="btn btn-primary" value="<spring:message code="template.edit.page.submit.label"/>" disabled />
            </div>
        </div>
    </form:form>
</div>
<script>var ctx = "${pageContext.request.contextPath}"</script>
