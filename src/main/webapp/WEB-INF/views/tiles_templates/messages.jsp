<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<div class="feedbackMessage" style="display: none;">
	<br />
	<div class="alert alert-success ">
		<c:out value="${feedbackMessage}" />
	</div>
</div>
<div class="errorMessage" style="display: none;">
	<br />
	<div class="alert alert-danger">
		<c:out value="${errorMessage}" />
	</div>
</div>
<!-- Messages Scripts -->
<c:if test="${feedbackMessage != null}">
	<script type="text/javascript">
			bootbox.alert($(".feedbackMessage").html());
		</script>
</c:if>
<c:if test="${errorMessage != null}">
	<script type="text/javascript">
			bootbox.alert($(".errorMessage").html());
		</script>
</c:if>