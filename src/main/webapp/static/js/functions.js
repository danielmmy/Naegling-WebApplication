/**
 * 
 */
$(".updateContainer").click( function() {
	var get = $.get(this.getAttribute("href"));

	get.done(function(data) {
		$(".container").html(data);
	});
	return false;
});



$("#compute").click(function() {
	var posting = $.post(ctx+"/template/create/compute", {
		filePath : $("#filePath").val()
	});

	$("#compute").html("<i class='fa fa-spin fa-cog'></i>");

	posting.done(function(data) {
		if (data) {
			$("#result").val(data);
			$("#send").removeAttr("disabled");
		}else{
			bootbox.alert("<br/><div class='alert alert-danger'>Could not locate file on specified path.</div>");
		}
		$("#compute").html("Compute MD5 Sum");

	});
});


function reCompute() {
	$("#send").attr("disabled", "disabled");
	$("#result").val("");
}	



$("#genMac").click(function() {
	var posting = $.post(ctx + "/node/create/generateMac", {});

	posting.done(function(data) {
		if (data) {
			$("#mac").val(data);
		} else {
			bootbox.alert("<br/><div class='alert alert-danger'>Error generating mac address.</div>");
		}

	});
});

$("#genUuid").click(function() {
	var posting = $.post(ctx + "/node/create/generateUuid", {});

	posting.done(function(data) {
		if (data) {
			$("#uuid").val(data);
		} else {
			bootbox.alert("<br/><div class='alert alert-danger'>Error generating UUID.</div>");
		}

	});
});
