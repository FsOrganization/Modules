$(document).ready(function() {
	$('#tabs').height(($(window).height()-78)*0.99);
	$('#tabs').width($(window).width()*0.91);
	$(window).bind('resize', function(e) {
			clearTimeout(window.resizeEvt);
//			alert($(window).height());
			window.resizeEvt = setTimeout(function() {
				$('#tabs').height(($(window).height()-78)*0.99);
				$('#tabs').width($(window).width()*0.91);
//				$('#searchList').datagrid('resize',{
//					width : '100%',
//					height: 180
//				});
			}, 30);
	});
	var loginName = $('#loginTag').val();
	if ( loginName != 'admin') {
		$('#statistics').hide();
		$('#configSeting').hide();
		$('#customerSeting').hide();
		
		
	}
});

function getSelections() {
	var rows = $('#areaCodeGrid').datagrid('getSelections');
	return rows;
}
function openWindow(rowIndex, rowData) {
	var id = rowData.id;
	var name = rowData.patient_name;
	$('#id').val(id);
	$('#name').val(name);
	$('#detail').dialog('open');
}