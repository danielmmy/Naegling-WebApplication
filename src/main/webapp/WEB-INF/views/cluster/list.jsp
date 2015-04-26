<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	<h1 class="page-header">
		<i class="fa fa-sitemap"></i> <spring:message code="cluster.list.page.title" />
        <span class="pull-right">
	        <a href="${pageContext.request.contextPath}/cluster/create" class="btn btn-info">
                <i class="fa fa-plus"></i>
			</a>
        </span>
	</h1>
	<table class="table table-hover">
		<thead>
			<tr>
				<th><spring:message code="cluster.label.name" /></th>
                <th><spring:message code="table.column.label.actions" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${account.clusters}" var="cluster">
				<tr>
					<td>
                        <a href="${pageContext.request.contextPath}/cluster/details/<c:out value="${cluster.id}"/>" >
                            <c:out value="${cluster.name}" />
                        </a>
                    </td>
					<td>
                        <div class="btn-group">
                            <a href="${pageContext.request.contextPath}/cluster/edit/<c:out value="${cluster.id}"/>" class="btn btn-default">
                                <i class="fa fa-wrench"></i>
						    </a>
        					<a href="${pageContext.request.contextPath}/cluster/delete/<c:out value="${cluster.id}"/>" class="btn btn-default">
                                <i class="fa fa-trash-o"></i>
                            </a>
                        </div>
                    </td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
