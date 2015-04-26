<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

	<h1 class="page-header">
		<i class="fa fa-cube"></i> <spring:message code="template.list.page.title" />
        <span class="pull-right">
        	<sec:authorize access="hasRole('ROLE_ADMIN')">
        		<a href="${pageContext.request.contextPath}/template/create" class="btn btn-info"><i class="fa fa-plus"></i></a>
        	</sec:authorize>
        </span>
	</h1>
	<table class="table table-hover">
		<thead>
			<tr>
				<th><spring:message code="template.label.name" /></th>
				<th><spring:message code="template.label.path" /></th>
				<th><spring:message code="template.label.md5Sum" /></th>
		        <sec:authorize access="hasRole('ROLE_ADMIN')">
                <th><spring:message code="table.column.label.actions" /></th>
			    </sec:authorize>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${templates}" var="template">
				<tr>
					<td><c:out value="${template.name}" /></td>
					<td><c:out value="${template.path}" /></td>
					<td><c:out value="${template.md5Sum}" /></td>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
                    <td>
                        <div class="btn-group">
                          <a href="${pageContext.request.contextPath}/template/edit/<c:out value="${template.id}"/>" class="btn btn-default">
                            <i class="fa fa-wrench"></i>
                          </a>
                          <a href="${pageContext.request.contextPath}/template/delete/<c:out value="${template.id}"/>" class="btn btn-default">
                            <i class="fa fa-trash-o"></i>
                          </a>
                        </div>
                    </td>
					</sec:authorize>
				</tr>
			</c:forEach>
		</tbody>
	</table>
