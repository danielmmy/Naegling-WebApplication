<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

    <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href=""><i class="fa fa-sitemap"></i> N&aelig;gling</a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <sec:authorize access="hasRole('ROLE_ADMIN')">
            <li class="visible-xs visible-sm"><a href="${pageContext.request.contextPath}/account/list"><i class="fa fa-users"></i> Accounts</a></li>
            </sec:authorize>
            <li class="visible-xs visible-sm"><a href="${pageContext.request.contextPath}/template/list"><i class="fa fa-cube"></i> Templates</a></li>
            <li class="visible-xs visible-sm"><a href="${pageContext.request.contextPath}/virtualMachineHost/list"><i class="fa fa-hdd-o"></i> Hosts</a></li>
            <li class="visible-xs visible-sm"><a href="${pageContext.request.contextPath}/cluster/list"><i class="fa fa-sitemap"></i> Clusters</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> <sec:authentication property="principal.account.firstName" /><span class="caret"></span></a>
              <ul class="dropdown-menu" role="menu">
                <li class="dropdown-header">My Account</li>
                <!-- TODO look at link below -->
                <li><a href="${pageContext.request.contextPath}/account/edit/<sec:authentication property="principal.account.id" />"><i class="fa fa-wrench"></i> Edit</a></li>
                <li class="divider"></li>
                <li><a href="${pageContext.request.contextPath}/logout"><i class="fa fa-sign-out"></i> Logout</a></li>
              </ul>
            </li>
          </ul>
        </div>
      </div>
    </div>
