<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

	<h1 class="page-header">
		<i class="fa fa-group"></i> <spring:message code="account.list.page.title" />
        <span class="pull-right">
            <a href="${pageContext.request.contextPath}/account/create" class="btn btn-info"><i class="fa fa-plus"></i></a>
        </span>
	</h1>
	<table class="table table-hover">
		<thead>
			<tr>
				<th><spring:message code="account.label.lastName" /></th>
				<th><spring:message code="account.label.firstName" /></th>
				<th><spring:message code="account.label.userName" /></th>
				<th><spring:message code="account.label.roles" /></th>
                <th><spring:message code="table.column.label.actions" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${accounts}" var="account">
				<tr>
					<td><c:out value="${account.lastName}" /></td>
					<td><c:out value="${account.firstName}" /></td>
					<td><c:out value="${account.userName}" /></td>
					<td>
                        <div class="col-sm-5">
    					<select class="form-control">
	    					<c:forEach items="${account.roles}" var="role">
		        				<option>"${role.role}"</option>
				    		</c:forEach>
					    </select>
                        </div>
					</td>
                    <td>
                        <div class="btn-group">
	    				    <a href="${pageContext.request.contextPath}/account/edit/<c:out value="${account.id}"/>" class="btn btn-default"><i class="fa fa-wrench"></i></a>
        					<a href="${pageContext.request.contextPath}/account/delete/<c:out value="${account.id}"/>" class="btn btn-default"><i class="fa fa-trash-o"></i></a>
                        </div>
                    </td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
