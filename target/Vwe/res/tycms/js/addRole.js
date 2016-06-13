$(document).ready( function() {
	$(".checboxtag").change(function(){
		//console.log($(this).attr("checked"));
		$("."+$(this).val()).attr("checked",$(this).attr("checked"));
	});
});