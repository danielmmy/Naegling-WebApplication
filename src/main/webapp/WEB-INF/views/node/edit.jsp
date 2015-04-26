<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h1><spring:message code="node.edit.page.title"/></h1>
<div>
    <form:form action="${pageContext.request.contextPath}/node/update" commandName="virtualNode" method="POST">
        <form:hidden path="id"/>
<div>
			<form:label path="domain">
				<spring:message code="node.label.domain" />:</form:label>
			<form:input path="domain" size="20" />
			<form:errors path="domain" cssClass="error" element="div" />
		</div>
		<div>
			<form:label path="uuid">
				<spring:message code="node.label.uuid" />:</form:label>
			<form:input path="uuid" size="33" id="uuid" />
			<form:button class="btn btn-default" id="genUuid" type="button">Generate</form:button>
			<form:errors path="uuid" cssClass="error" element="div" />
		</div>
		<div>
			<form:label path="macs[0]">
				<spring:message code="node.label.mac" />:</form:label>
			<form:input path="macs[0]" size="20" id="mac"/>
			<form:button class="btn btn-default" id="genMac" type="button">Generate</form:button>
			<form:errors path="macs[0]" cssClass="error"
				element="div" />
		</div>
		<div>
			<form:label path="ramMemory">
				<spring:message code="node.label.ramMemory" />:</form:label>
			<form:input path="ramMemory" size="20" />
			<form:errors path="ramMemory" cssClass="error" element="div" />
		</div>
		<div>
			<form:label path="cpuQuantity">
				<spring:message code="node.label.cpuQuantity" />:</form:label>
			<form:input path="cpuQuantity" size="20" />
			<form:errors path="cpuQuantity" cssClass="error" element="div" />
		</div>
		<div>
			<form:label path="graphicalAccessPort">
				<spring:message code="node.label.graphicalAccessPort" />:</form:label>
			<form:input path="graphicalAccessPort" size="20" />
			<form:errors path="graphicalAccessPort" cssClass="error"
				element="div" />
		</div>
		<div>
			<form:label path="host">
				<spring:message code="node.label.host" />:</form:label>
			<form:select path="host">
				<c:forEach items="${hosts}" var="host">
					<option>${host.hostName}</option>
				</c:forEach>
			</form:select>
			<form:errors path="host" cssClass="error"
				element="div" />
		</div>
		<div>
			<input type="submit"
				value="<spring:message code="node.edit.page.submit.label"/>" />
		</div>

    </form:form>
</div>
