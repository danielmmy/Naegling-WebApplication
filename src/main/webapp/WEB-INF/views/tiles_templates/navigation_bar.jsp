<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>

<sec:authentication property="principal.account" var="account"
	scope="request" />

        <div class="col-sm-3 col-md-2 sidebar">
          <div class="navbar-collapse collapse">
              <ul class="nav nav-sidebar">
	    	    <sec:authorize access="hasRole('ROLE_ADMIN')">
		    	    <li><a href="${pageContext.request.contextPath}/account/list"><i class="fa fa-users"></i> Accounts</a></li>
    		    </sec:authorize>
        		<li><a href="${pageContext.request.contextPath}/template/list"><i class="fa fa-cube"></i> Templates</a></li>
    		    <li><a href="${pageContext.request.contextPath}/virtualMachineHost/list"><i class="fa fa-hdd-o"></i> Hosts</a></li>
    		    <li><a href="${pageContext.request.contextPath}/cluster/list"><i class="fa fa-sitemap"></i> Clusters</a></li>
              </ul>
          </div>
        </div>
