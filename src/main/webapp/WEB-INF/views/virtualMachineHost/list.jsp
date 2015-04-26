<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<h1 class="page-header">
	<i class="fa fa-hdd-o"></i> <spring:message code="virtualMachineHost.list.page.title" />
    <span class="pull-right">
        <sec:authorize access="hasRole('ROLE_ADMIN')">
    	<a href="${pageContext.request.contextPath}/virtualMachineHost/create" class="btn btn-info">
    		<i class="fa fa-plus"></i>
    	</a>
        </sec:authorize>
    </span>
</h1>
<table class="table table-hover">
	<thead>
		<tr>
			<th><spring:message code="virtualMachineHost.label.hostName" /></th>
			<th><spring:message code="virtualMachineHost.label.ip" /></th>
			<th><spring:message code="virtualMachineHost.label.naeglingPort" /></th>
			<th><spring:message code="virtualMachineHost.label.hypervisor" /></th>
			<th><spring:message code="virtualMachineHost.label.bridgeNetworkInterface" /></th>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
            <th><spring:message code="table.column.label.actions" /></th>
            </sec:authorize>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${virtualMachineHosts}" var="virtualMachineHost">
			<tr>
				<td><c:out value="${virtualMachineHost.hostName}" /></td>
				<td><c:out value="${virtualMachineHost.ip}" /></td>
				<td><c:out value="${virtualMachineHost.naeglingPort}" /></td>
				<td><c:out value="${virtualMachineHost.hypervisor}" /></td>
				<td><c:out value="${virtualMachineHost.bridgeNetworkInterface}" /></td>
				<sec:authorize access="hasRole('ROLE_ADMIN')">
                <td>
                    <div class="btn-group">
        			    <a href="${pageContext.request.contextPath}/virtualMachineHost/edit/<c:out value="${virtualMachineHost.id}"/>" class="btn btn-default">
                            <i class="fa fa-wrench"></i>
						</a>
					    <a href="${pageContext.request.contextPath}/virtualMachineHost/delete/<c:out value="${virtualMachineHost.id}"/>" class="btn btn-default">
                            <i class="fa fa-trash-o"></i>
				        </a>
                    </div>
                </td>
				</sec:authorize>
			</tr>
		</c:forEach>
	</tbody>
</table>
